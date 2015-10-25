package com.oiangie.lcuhelper.query;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.SchoolMap;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.GeoPoint;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Nearby extends Activity {

	private ImageView back = null;
	private Button bc1, bc2, bc3, bc4, bc5, bc6, bc7, bc8, bs1, bs2, bs3, bs4,
			bs5, bs6, bs7, bs8, bs9, bs10, bs11, bs12, bs13;
	private Integer[] bcs = CommUtil.cater;
	private Integer[] bss = CommUtil.service;
	private SharedPreferences sp;
	private Float locationX = (float) 0;
	private Float locationY = (float) 0;
	private Bundle bundle;
	private Double x,y;

	private Workspace m_wokspace = null;
	private DatasetVector m_datasetVector = null;
	public static String SUPERMAPPATH = android.os.Environment
			.getExternalStorageDirectory() + "/SuperMap/";
	private PrjCoordSys mPrjCoordSys;

	private void openData() {
		m_wokspace = new Workspace();

		WorkspaceConnectionInfo wsInfo = new WorkspaceConnectionInfo();
		wsInfo.setServer(SUPERMAPPATH + "SampleData/LCU.smwu");
		wsInfo.setType(WorkspaceType.SMWU);

		if (m_wokspace.open(wsInfo)) {

			Datasource datasource = m_wokspace.getDatasources().get("ditushuju");
			if (datasource != null) {
				m_datasetVector = (DatasetVector) datasource.getDatasets().get(
						"POI");
			}
		getPrjCoordSys();
		}
	}

	private void getPrjCoordSys() {
		Datasource myDatasource = m_wokspace.getDatasources().get(0);
		mPrjCoordSys = myDatasource.getDatasets().get(0).getPrjCoordSys();
		// .getDatasets() 得到数据源 .getPrjCoordSys();得到数据源的投影信息
	}

	// 将地图坐标转化为经纬坐标
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.nearby);

		Environment.setLicensePath(SUPERMAPPATH + "license");
		Environment.initialization(this);
		openData();// 打开数据

		back = (ImageView) this.findViewById(R.id.ny_back);
		sp = getSharedPreferences("example", Context.MODE_PRIVATE);

		bc1 = (Button) findViewById(bcs[0]);bc1.setOnClickListener(l);
		bc2 = (Button) findViewById(bcs[1]);bc2.setOnClickListener(l);
		bc3 = (Button) findViewById(bcs[2]);bc3.setOnClickListener(l);
		bc4 = (Button) findViewById(bcs[3]);bc4.setOnClickListener(l);
		bc5 = (Button) findViewById(bcs[4]);bc5.setOnClickListener(l);
		bc6 = (Button) findViewById(bcs[5]);bc6.setOnClickListener(l);
		bc7 = (Button) findViewById(bcs[6]);bc7.setOnClickListener(l);
		bc8 = (Button) findViewById(bcs[7]);bc8.setOnClickListener(l);
		bs1 = (Button) findViewById(bss[0]);bs1.setOnClickListener(l);
		bs2 = (Button) findViewById(bss[1]);bs2.setOnClickListener(l);
		bs3 = (Button) findViewById(bss[2]);bs3.setOnClickListener(l);
		bs4 = (Button) findViewById(bss[3]);bs4.setOnClickListener(l);
		bs5 = (Button) findViewById(bss[4]);bs5.setOnClickListener(l);
		bs6 = (Button) findViewById(bss[5]);bs6.setOnClickListener(l);
		bs7 = (Button) findViewById(bss[6]);bs7.setOnClickListener(l);
		bs8 = (Button) findViewById(bss[7]);bs8.setOnClickListener(l);
		bs9 = (Button) findViewById(bss[8]);bs9.setOnClickListener(l);
		bs10 = (Button) findViewById(bss[9]);bs10.setOnClickListener(l);
		bs11 = (Button) findViewById(bss[10]);bs11.setOnClickListener(l);
		bs12 = (Button) findViewById(bss[11]);bs12.setOnClickListener(l);
		bs13 = (Button) findViewById(bss[12]);bs13.setOnClickListener(l);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Nearby.this,SchoolMap.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);// 如果不传值就不要加flags
				Bundle bundle = new Bundle();
				// 初始化一下way的值
				bundle.putInt("way", 0);
				intent.putExtras(bundle);
				Nearby.this.startActivity(intent);
				Nearby.this.finish();
			}
		});
	}

	private OnClickListener l = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button button = (Button) v;
			String name = button.getText().toString();
			setStrs(name);

		}
	};

	/**
	 * 三个方向 （实为两个） 通过得到的定点的 xy和关键字来查询 通过得到的当前定位坐标和关键字来查询 判断是否之前传进来定点的坐标值
	 * 
	 * @param title
	 */
	private void setStrs(String title) {

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int come = bundle.getInt("come");
		
		String Sx = "";
		String Sy = "";
		if (come == 1) {
			Sx =sp.getString("latitude", "1.2913773459910963E7");// 第二个参数是无法获取时的缺省值
			Sy =sp.getString("longitude", "4359776.690930412");
			x = Double.valueOf(Sx);
			y = Double.valueOf(Sy);
			Point2D pntMap = new Point2D();
			pntMap.setX(x);
			pntMap.setY(y);
			Point2D pntJWD = prjConvertPoint(pntMap, false);
			x = pntJWD.getX();
			y = pntJWD.getY();
			Log.i("附近坐标转换：经纬坐标", "X" + x);
			Log.i("附近坐标转换：经纬坐标", "Y" + y);

			
		} else if (come == 2) {
			String str = bundle.getString("namea");

			if (m_datasetVector != null) {
				
				str.trim();
				String strFilter = "SEARCHNAME like '%" + str + "%'";
				Recordset recordset = m_datasetVector.query(strFilter,
						CursorType.STATIC);
				recordset.moveFirst();
				while (!recordset.isEOF()) {
					GeoPoint geoPoint = (GeoPoint) recordset.getGeometry();
					// 得到地图坐标
					double ditux = geoPoint.getX();
					double dituy = geoPoint.getY();
					Point2D pntMap = new Point2D();
					pntMap.setX(ditux);
					pntMap.setY(dituy);
					Point2D pntJWD = prjConvertPoint(pntMap, false);
					x = pntJWD.getX();
					y = pntJWD.getY();
					recordset.moveNext();
				}

			}
		}
		bundle.putDouble("x", x);
		bundle.putDouble("y", y);
		intent = new Intent(Nearby.this, QueryResult.class);
		bundle.putString("title", title);
		bundle.putBoolean("isEdit", false);
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

	}

}
