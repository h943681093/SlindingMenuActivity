package com.oiangie.lcuhelper.xunwuzhushou;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class ShowPoint_search extends Activity {

	private MapView m_Se_mapView = null;
	private Workspace m_Se_wokspace = null;
	private PrjCoordSys mPrjCoordSys;// 投影坐标系类
	public static String SUPERMAPPATH = android.os.Environment
			.getExternalStorageDirectory() + "/SuperMap/";
	private String address;
	private MapControl m_MapControl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Environment.setLicensePath(SUPERMAPPATH + "license");
		Environment.initialization(this);

		setContentView(R.layout.showpoint_search);
		openData();
		getPrjCoordSys();
		initButtons();
		
		Toast.makeText(ShowPoint_search.this, "长按屏幕，选定您需要的位置", Toast.LENGTH_LONG).show();
	}

	@SuppressWarnings("deprecation")
	private void openData() {
		m_Se_wokspace = new Workspace();
		m_Se_mapView = (MapView) findViewById(R.id.Search_Map_view);
		m_MapControl = m_Se_mapView.getMapControl();
		//设置长按监听
		m_MapControl.setGestureDetector(new GestureDetector(mGestrueListener));
		m_Se_mapView.getMapControl().getMap().setWorkspace(m_Se_wokspace);

		WorkspaceConnectionInfo wsInfo = new WorkspaceConnectionInfo();
		wsInfo.setServer(SUPERMAPPATH + "SampleData/LCU.smwu");
		wsInfo.setType(WorkspaceType.SMWU);
		if (m_Se_wokspace.open(wsInfo)) {
			String mapString = m_Se_wokspace.getMaps().get(0);
			m_Se_mapView.getMapControl().getMap().open(mapString);
		}
	}

	private void getPrjCoordSys() {
		Datasource myDatasource = m_Se_wokspace.getDatasources().get(0);
		mPrjCoordSys = myDatasource.getDatasets().get(0).getPrjCoordSys();
	}

	private void initButtons() {

		ZoomControls zoom = (ZoomControls) findViewById(R.id.Search_zoomControls);
		zoom.setIsZoomInEnabled(true);
		zoom.setIsZoomOutEnabled(true);
		zoom.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_Se_mapView.getMapControl().getMap().zoom(2);
				m_Se_mapView.getMapControl().getMap().refresh();
			}
		});
		zoom.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_Se_mapView.getMapControl().getMap().zoom(0.5);
				m_Se_mapView.getMapControl().getMap().refresh();
			}
		});
	}

	private GestureDetector.SimpleOnGestureListener mGestrueListener = new SimpleOnGestureListener() {
		@Override
		public void onLongPress(MotionEvent e) {
			Point pntClick = new Point((int) e.getX(), (int) e.getY());
			Point2D pntMap = m_Se_mapView.getMapControl().getMap()
					.pixelToMap(pntClick);
			final double doux = pntMap.getX();
			final double douy = pntMap.getY();
			String x = Double.toString(doux);
			String y = Double.toString(douy);
			addCallOutByloction(pntMap, R.drawable.location);

			Point2D pntJWD = prjConvertPoint(pntMap, false);
			double JWDx = pntJWD.getX();
			double JWDy = pntJWD.getY();
			address = x + "," + y;
			AlertDialog.Builder builder = new AlertDialog.Builder(ShowPoint_search.this);
			builder.setMessage("确定这个位置吗?")
			       .setCancelable(false)
			       .setPositiveButton("是", new DialogInterface.OnClickListener() {
			           @Override
					public void onClick(DialogInterface dialog, int id) {
							goback(doux,douy);
			           }
			       })
			       .setNegativeButton("否", new DialogInterface.OnClickListener() {
			           @Override
					public void onClick(DialogInterface dialog, int id) {
			        	   m_Se_mapView.removeAllCallOut();
			               dialog.cancel();
			           }
			       });
			builder.show();
		};
	};

	
	// 将地图点转为经纬度
	private Point2D prjConvertPoint(Point2D pntIn, boolean bForward) {
		Point2Ds point2Ds = new Point2Ds();
		point2Ds.add(pntIn);
		boolean bConvert = false;
		if (bForward) {
			bConvert = CoordSysTranslator.forward(point2Ds, mPrjCoordSys);
		} else {
			bConvert = CoordSysTranslator.inverse(point2Ds, mPrjCoordSys);
		}
		if (bConvert) {
			return point2Ds.getItem(0);
		} else {
			return null;
		}
	}
	//根据位置坐标callout
	private void addCallOutByloction(Point2D loction, int drawbleID) {
		CallOut callout = new CallOut(ShowPoint_search.this);
		callout.setStyle(CalloutAlignment.CENTER);
		callout.setCustomize(true);
		callout.setLocation(loction.getX(), loction.getY());
		ImageView image = new ImageView(ShowPoint_search.this);
		image.setBackgroundResource(drawbleID);
		callout.setContentView(image);
		m_Se_mapView.addCallout(callout);
	}
	
	
	//返回并传输数据
	public void goback(Double doux,Double douy) {
		ArrayList<String> data = getIntent().getExtras().getStringArrayList(
				"data");
		Intent it = new Intent();
		Bundle bundle = new Bundle();
		int where = getIntent().getExtras().getInt("where");
		if (where==1) {
			it.setClass(ShowPoint_search.this, Search_helper_release.class);
		}else if (where==2) {
			//it.setClass(ShowPoint_search.this, Activity_release.class);
		}else if (where==3) {
			//it.setClass(ShowPoint_search.this, Course_release.class);
		}
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		bundle.putString("address", address);
		bundle.putStringArrayList("data", data);
		it.putExtras(bundle);
		ShowPoint_search.this.startActivity(it);
	}
	
	@Override
	public void onDestroy() {
		m_Se_mapView.getMapControl().getMap().close();
		m_Se_wokspace.close();
		super.onDestroy();
	}

}
