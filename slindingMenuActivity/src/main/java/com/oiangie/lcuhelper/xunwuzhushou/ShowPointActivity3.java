package com.oiangie.lcuhelper.xunwuzhushou;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Toast;
import android.widget.ZoomControls;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.Login;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.MapView;


public class ShowPointActivity3 extends Activity {
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
			CallOut callout = new CallOut(ShowPointActivity3.this);
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
			
			

			  String name=null;
			  String information=null;
			  String Phone=null;
			  String timeString=null;
			  
			  final Dialog dialog=new Dialog(ShowPointActivity3.this,R.style.dialog);
			  View view=getLayoutInflater().inflate(R.layout.gaoji_search_point_dialog, null);	
			  //内容
			  TextView infor=(TextView) view.findViewById(R.id.point_dialog_tv2);
			  infor.setMovementMethod(ScrollingMovementMethod.getInstance());
			  //名称
			  final	TextView tname=(TextView) view.findViewById(R.id.point_dialog_name2);
			  //时间
			  final	TextView mtime=(TextView) view.findViewById(R.id.ditime2);
			  //电话
			  final	Button mphone=(Button) view.findViewById(R.id.diPhone2);
			  
			  name = names;
			  information = infors;
			  //timeString = Time;
			  
				//填装名字
				tname.setText(name);
				//填装介绍
				infor.setText(information);
				//填装时间
				mtime.setText(timeString);
				//点击认领
				mphone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						//先判断是否登录
						LocationApplication app = (LocationApplication)getApplicationContext();
						int ISin = app.getISin();
						
						if (ISin == 0) {
							
							AlertDialog.Builder builder = new AlertDialog.Builder(ShowPointActivity3.this);
							builder.setMessage("您没有登录，是否登录？")
							       .setCancelable(false)
							       .setPositiveButton("是", new DialogInterface.OnClickListener() {
							           @Override
									public void onClick(DialogInterface dialog, int id) {
							        	   Intent it=new Intent();
											it.setClass(ShowPointActivity3.this, Login.class);
											it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
											Bundle bundle = new Bundle();
											bundle.putInt("value", 5);
											it.putExtras(bundle);
											ShowPointActivity3.this.startActivity(it);
							           }
							       })
							       .setNegativeButton("否", new DialogInterface.OnClickListener() {
							           @Override
									public void onClick(DialogInterface dialog, int id) {
							        	   Toast.makeText(ShowPointActivity3.this,"请您先登录，再提交信息",Toast.LENGTH_SHORT).show(); 
							               dialog.cancel();
							               ShowPointActivity3.this.finish();
							           }
							       });
							builder.show();
						}else if (ISin == 1) {
							String releasename = app.getusername();
							Intent intent = new Intent(ShowPointActivity3.this,loser_release.class);
							Bundle bundle = new Bundle();
							bundle.putString("releasename", releasename);
							intent.putExtras(bundle);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
							ShowPointActivity3.this.startActivity(intent);
						}
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

