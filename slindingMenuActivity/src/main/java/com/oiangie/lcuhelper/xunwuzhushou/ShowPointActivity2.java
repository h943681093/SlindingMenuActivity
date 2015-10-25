package com.oiangie.lcuhelper.xunwuzhushou;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.query.Nearby;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.MapView;


public class ShowPointActivity2 extends Activity {
	private MapView m_mapView2 = null;
	private Workspace m_wokspace2 = null;
	private DatasetVector m_datasetVector2 = null;
	public static String SUPERMAPPATH = android.os.Environment.getExternalStorageDirectory() + "/SuperMap/";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Environment.setLicensePath(SUPERMAPPATH + "license");
		Environment.initialization(this);
		setContentView(R.layout.gonggong_show_point_activity);
		openData();//打开数据  设置查询图层
		initButtons();//初始化ZoomControls实现工作空间的操作
		Log.e("querybyname", "进去显示点的方法");
		String putString =  getIntent().getExtras().getString("losename");
	   	String Address =  getIntent().getExtras().getString("Address");
	   	String content = getIntent().getExtras().getString("content");
		String [] Addresses = Address.split(",");
		String AddressX =  Addresses[0];
		Double addressX = Double.valueOf(AddressX);
		String AddressY =  Addresses[1];
		Double addressY = Double.valueOf(AddressY);
	
		Point2D point2d = new Point2D();
		point2d.setX(addressX);
		Log.e("addressX", ""+addressX);
		point2d.setY(addressY);	
		Log.e("addressY", ""+addressY);
	   	addCallout(point2d,putString,content,"image.png");
	}
	private void openData() {
		m_wokspace2 = new Workspace();
		m_mapView2 = (MapView) findViewById(R.id.Map_view2);
		//需要设置地图与工作空间进行关联，否则无法显示
		m_mapView2.getMapControl().getMap().setWorkspace(m_wokspace2);
		WorkspaceConnectionInfo wsInfo = new WorkspaceConnectionInfo();
		wsInfo.setServer(SUPERMAPPATH + "SampleData/LCU.smwu");
		wsInfo.setType(WorkspaceType.SMWU);
		if (m_wokspace2.open(wsInfo)) {
			String mapString = m_wokspace2.getMaps().get(0);
			m_mapView2.getMapControl().getMap().open(mapString);
			Datasource datasource = m_wokspace2.getDatasources().get("测试点");
			if (datasource != null) {
				m_datasetVector2 = (DatasetVector) datasource.getDatasets().get("POI");
			}
		}
	}

	private void initButtons() {
		ZoomControls zoom = (ZoomControls)findViewById(R.id.zoomControls2);
		zoom.setIsZoomInEnabled(true);
		zoom.setIsZoomOutEnabled(true);
		zoom.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_mapView2.getMapControl().getMap().zoom(2);
				m_mapView2.getMapControl().getMap().refresh();
			}
		});
		zoom.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				m_mapView2.getMapControl().getMap().zoom(0.5);
				m_mapView2.getMapControl().getMap().refresh();
			}
		});
	}
	private void addCallout(final Point2D point2d, String nameString, String inforString,final String url) {
			final String name = nameString;
			Log.e("xianshidian", name);
			final String info = inforString;
			Log.e("xianshidian", info);
			LayoutInflater lfCallOut = getLayoutInflater();
			View calloutLayout = lfCallOut.inflate(R.layout.callout, null);
			ImageView tView = (ImageView) calloutLayout.findViewById(R.id.imageViewcall);
			tView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					show(name, info, url,point2d);
					Log.e("urldA", url);
				}
			});
			Double x = point2d.getX();
			Log.e("最终显示", ""+x);
			Double y = point2d.getY();
			Log.e("最终显示", ""+y);
			CallOut callout = new CallOut(ShowPointActivity2.this);
			callout.setContentView(calloutLayout);
			callout.setCustomize(true);
			callout.setLocation(point2d.getX(),point2d.getY());
			m_mapView2.addCallout(callout);
			m_mapView2.getMapControl().getMap().setCenter(point2d);
			m_mapView2.getMapControl().getMap().setScale(1.0/1500);
			m_mapView2.getMapControl().getMap().refresh();
		}
		//dialog详细展示点的部分
		@SuppressLint("HandlerLeak")
		private void show(final String names,String infors,String urls,final Point2D dot){
			//建立字符变量准备接收数据
			  String name=null;
			  String information=null;
			  //String url=CommUtil.imageurl+CommUtil.image;
			  //新建一个dialog
			  final Dialog dialog=new Dialog(ShowPointActivity2.this,R.style.dialog);
			  //加载布局
				View view=getLayoutInflater().inflate(R.layout.point_dialog_benbuben, null);	
				TextView infor=(TextView) view.findViewById(R.id.point_dialog_tv);
				infor.setMovementMethod(ScrollingMovementMethod.getInstance());
				Button query=(Button)view.findViewById(R.id.point_dialog_nearby);
				final	TextView tname=(TextView) view.findViewById(R.id.point_dialog_name);
				//新建一个点
				name=names;
				information=infors;
				//url=urls;
				//填装文字
				tname.setText(name);
				//填装介绍信息
				infor.setText(information);
				//点击进入搜索附近
				query.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						String str = names;
						Log.e("附近按钮点击事件", str);
						Intent intent = new Intent(ShowPointActivity2.this, Nearby.class);
						Bundle bundle = new Bundle();
						bundle.putString("namea", str);
						bundle.putInt("come", 2);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						dialog.cancel();
					}
				});
				//准备布局开始显示  并移动地图 点到中心
				 Window win = dialog.getWindow();//获取所在window
				 LayoutParams params = win.getAttributes();//获取LayoutParams
				 win.setGravity(Gravity.CENTER);	
				 params.width=android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
				 params.height=android.view.ViewGroup.LayoutParams.WRAP_CONTENT;	
				 params.y=-170;
				 params.x=10;
				 win.setAttributes(params);
				 dialog.setCanceledOnTouchOutside(true);
				 dialog.setContentView(view);
				 if(name!=null){
				 dialog.show();
				 m_mapView2.getMapControl().getMap().setCenter(dot);
				 m_mapView2.refresh();
				 }
		}	
	@Override
	public void onDestroy() {
		m_mapView2.getMapControl().getMap().close();
		m_wokspace2.close();
		super.onDestroy();   
	}
}

