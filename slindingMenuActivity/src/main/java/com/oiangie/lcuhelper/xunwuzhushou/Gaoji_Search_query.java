package com.oiangie.lcuhelper.xunwuzhushou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.Login;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
import com.supermap.data.Color;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.GeoCircle;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.Geometrist;
import com.supermap.data.Geometry;
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
import com.supermap.mapping.TrackingLayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.widget.AdapterView.OnItemSelectedListener;

public class Gaoji_Search_query extends Activity {

	private MapView m_Se_mapView = null;
	private Workspace m_Se_wokspace = null;
	private PrjCoordSys mPrjCoordSys;
	public static String SUPERMAPPATH = android.os.Environment.getExternalStorageDirectory() + "/SuperMap/";
	private MapControl m_MapControl;
	private Spinner fanweiSpinner;
	private ArrayAdapter<?> Fadapter;
	private Double radiu= 100.0;
	private TrackingLayer mTrackingLayer;
	private double doux,douy;
	private ProgressDialog progressDialog = null;
	private String searchstr;
	private Handler handler1;
	private ArrayList<HashMap<String, Object>> mydata;
	private int num=0;
	private ImageView back;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置环境
		Environment.setLicensePath(SUPERMAPPATH + "license");
		Environment.initialization(this);
		
		setContentView(R.layout.gaoji_search_query);
		
		searchstr = getIntent().getExtras().getString("searchstr");
		Log.e("接收的", searchstr);
		
		doThread();
		
		openData();
		
		getPrjCoordSys();
		
		initButtons();
		
		Toast.makeText(Gaoji_Search_query.this, "请长按屏幕选择您遗失该物品的位置", Toast.LENGTH_LONG).show();
		
		back = (ImageView) findViewById(R.id.gaoji_search_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Gaoji_Search_query.this.finish();
			}
		});
		
		
		fanweiSpinner = (Spinner) findViewById(R.id.Spiner1);
		
		
		Fadapter = ArrayAdapter.createFromResource(this, R.array.fanwei,android.R.layout.simple_spinner_item);
		Fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fanweiSpinner.setAdapter(Fadapter);
		
		
		fanweiSpinner.setSelection(0, true);
		fanweiSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub		
				if(arg2!=0){
					String str = arg0.getItemAtPosition(arg2).toString();
					String[] strs = str.split("米");
				    radiu = Double.valueOf(strs[0]);//单位是千米
				    mTrackingLayer = m_MapControl.getMap().getTrackingLayer();
					mTrackingLayer.clear();
					m_Se_mapView.removeAllCallOut();
					m_MapControl.getMap().refresh();
				}		
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				arg0.setVisibility(View.VISIBLE);
				mTrackingLayer = m_MapControl.getMap().getTrackingLayer();
				mTrackingLayer.clear();
				m_Se_mapView.removeAllCallOut();
				m_MapControl.getMap().refresh();
			}
		});
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}
	
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}
	
	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	
	
	private void search() {
		Log.i("BUG", "1B");
				mTrackingLayer = m_MapControl.getMap().getTrackingLayer();
				mTrackingLayer.clear();
				Point2D point2D = new Point2D();
				point2D.setX(doux);
				point2D.setY(douy);
				GeoCircle cir = new GeoCircle();
				cir.setCenter(point2D);
				cir.setRadius(radiu);
				GeoRegion geoRegion = new GeoRegion(cir.convertToRegion(50));
				GeoStyle style = new GeoStyle();	
				style.setLineColor(new Color(23, 180, 235));
				style.setFillForeColor(new Color(0, 251, 207));
				style.setLineWidth(0.5);
				style.setFillOpaqueRate(5);
				geoRegion.setStyle(style);
				mTrackingLayer.add(geoRegion, "搜索范围");
				int ID = mTrackingLayer.GetEvent("搜索范围");
				mTrackingLayer.setVisible(true);
				m_MapControl.getMap().refresh();
				Log.i("BUG", "2B");	
			for (int i = 0; i < mydata.size(); i++) {
				Log.i("BUG", "3B");	
				String SearchName=(String) mydata.get(i).get("SearchName");
				String SearchAddress=(String) mydata.get(i).get("SearchAddress");
				String SearchTime=(String) mydata.get(i).get("SearchTime");
				String SearchContent=(String) mydata.get(i).get("SearchContent");
				String SearchPhone=(String) mydata.get(i).get("ReleaseName");
				String [] Addresses = SearchAddress.split(",");
				String AddressX =  Addresses[0];
				String AddressY =  Addresses[1];
				Point2D point2dshow = new Point2D();
				point2dshow.setX(Double.parseDouble(AddressX));
				point2dshow.setY(Double.parseDouble(AddressY));
				GeoPoint geoPoint = new GeoPoint(Double.parseDouble(AddressX),Double.parseDouble(AddressY));
				Geometry  geometry = mTrackingLayer.get(ID);	
				boolean iscanContain = Geometrist.canContain(geometry,geoPoint);
				if (iscanContain) {
					Log.e(SearchName, SearchName+"这个点符合要求");
					addCallout(point2dshow,SearchName,SearchContent,SearchPhone,SearchTime);
					num = num+1;
				} else {
					Log.e(SearchName, SearchName+"这个点不符合要求");
				}	
			}
			
			m_Se_mapView.getMapControl().getMap().setCenter(point2D);
			m_Se_mapView.getMapControl().getMap().setScale(1.0/1500);
			m_Se_mapView.getMapControl().getMap().refresh();
			
			if (num!=0) {
				Toast.makeText(Gaoji_Search_query.this, "查找到"+num+"个符合要求的位置，点击可查看详情", Toast.LENGTH_LONG).show();
				num = 0;
			}else {
				Toast.makeText(Gaoji_Search_query.this, "抱歉！没有找到您想要的 您可以扩大范围试试", Toast.LENGTH_LONG).show();
			}
	}
	
	@SuppressWarnings("deprecation")
	private void openData() {
		m_Se_wokspace = new Workspace();
		m_Se_mapView = (MapView) findViewById(R.id.Map_view_gaoji);
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

		ZoomControls zoom = (ZoomControls) findViewById(R.id.zoomControls_gaoji);
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
	
	//实现长按监听
	private GestureDetector.SimpleOnGestureListener mGestrueListener = new SimpleOnGestureListener() {
		@Override
		public void onLongPress(MotionEvent e) {
			 playBeepSoundAndVibrate();
			m_Se_mapView.removeAllCallOut();
			Point pntClick = new Point((int) e.getX(), (int) e.getY());
			Point2D pntMap = m_Se_mapView.getMapControl().getMap()
					.pixelToMap(pntClick);
			doux = pntMap.getX();
			douy = pntMap.getY();
			String x = Double.toString(doux);
			String y = Double.toString(douy);
			addCallOutByloction(pntMap, R.drawable.location_blue);
			AlertDialog.Builder builder = new AlertDialog.Builder(Gaoji_Search_query.this);
			builder.setMessage("确定这个位置吗?")
			       .setCancelable(false)
			       .setPositiveButton("是", new DialogInterface.OnClickListener() {
			           @Override
					public void onClick(DialogInterface dialog, int id) {
			        	   search();
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


	//根据位置坐标callout
	private void addCallOutByloction(Point2D loction, int drawbleID) {
		CallOut callout = new CallOut(Gaoji_Search_query.this);
		callout.setStyle(CalloutAlignment.CENTER);
		callout.setCustomize(true);
		callout.setLocation(loction.getX(), loction.getY());
		Log.i("X坐标", ""+loction.getX());
		Log.i("Y坐标", ""+loction.getY());
		ImageView image = new ImageView(Gaoji_Search_query.this);
		image.setBackgroundResource(drawbleID);
		callout.setContentView(image);
		m_Se_mapView.addCallout(callout);
	}
	
	
	
//开启线程
			@SuppressLint("HandlerLeak")
			private Handler handler=new Handler()
		    {
		        @Override
				public void handleMessage(Message m)
		        {
		        	 ArrayList<String> datas = m.getData().getStringArrayList("data");      	 
		        	 doResult(datas);        	
		        };        
		    };
		    
//显示webservice结果
		    private void doResult( ArrayList<String> datas){
		    	mydata = new ArrayList<HashMap<String, Object>>();
		    for(int i=0;i<datas.size();){
		       	HashMap<String, Object> map = new HashMap<String, Object>();
		       	if(datas.get(i)!=null){
		       		map.put("SearchName", datas.get(i));
		       		 i++;
		       		map.put("SearchTime", datas.get(i));
		       		 i++;
		       		map.put("SearchContent",  datas.get(i));
		       		 i++;
		       		map.put("SearchAddress",  datas.get(i));
		      		 i++; 
		      		map.put("ReleaseName",  datas.get(i));
		       		 i++;
		       		mydata.add(map); 		
		       	 }
		        }  
			progressDialog.dismiss();
		    }
		    

		    
//开始连接webservice
		    @SuppressWarnings("deprecation")
			@SuppressLint({ "HandlerLeak", "ShowToast"})
			
			private void doThread(){
				Log.e("demo", "开始连接webservice");
				
					ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
			      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo(); 
			       if(networkInfo != null &&  networkInfo.isAvailable()){
			    	   progressDialog=new ProgressDialog(Gaoji_Search_query.this);
			    	   progressDialog.setTitle("请稍等...");
			    	   progressDialog.setMessage("数据正在加载中......");
			    	   progressDialog.setButton("取消", new DialogInterface.OnClickListener(){
			    		   @Override
			               public void onClick(DialogInterface dialog, int which)
			               {
			                   handler1.removeMessages(1);			                  
			                   progressDialog.setProgress(0);
			               }
			    	   });
			    	   progressDialog.show();
			    	   handler1 = new Handler()
			           {
			               @Override
						public void handleMessage(Message msg)
			               {
			                   super.handleMessage(msg);
			                   progressDialog.dismiss();			                   
			               }}; 
				 String url = "Study.asmx";
				 String methodName="GetSearchGoods";
				 HashMap<String, Object> params = new HashMap<String, Object>();
				 params.put("SearchName",searchstr);
				 CallWebservice callWeb=new CallWebservice(handler);
				 callWeb.doStart(url, methodName, params);
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
				
						
						e.printStackTrace();
					}
			       }
			       else{    	 
			    	   Toast.makeText(Gaoji_Search_query.this, "数据加载失败，请检查网络", 2000).show();
			    	   }
		    }
	
			
		private void addCallout(final Point2D point2d, String nameString, String inforString,final String SearchPhone,final String SearchTime) {
				final String name = nameString;
				final String info = inforString;
				Log.e("addcallout方法里", nameString+inforString);
				Log.e("addcallout方法里", SearchPhone);
				Log.e("addcallout方法里", SearchTime);
				LayoutInflater lfCallOut = getLayoutInflater();
				View calloutLayout = lfCallOut.inflate(R.layout.callout, null);
				ImageView tView = (ImageView) calloutLayout.findViewById(R.id.imageViewcall);
				tView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						show(name, info, SearchPhone,SearchTime,point2d);
					}
				});
				Double x = point2d.getX();
				Double y = point2d.getY();
				CallOut callout = new CallOut(Gaoji_Search_query.this);
				callout.setContentView(calloutLayout);
				callout.setCustomize(true);
				callout.setLocation(point2d.getX(),point2d.getY());
				m_Se_mapView.addCallout(callout);

			}
			//dialog详细展示点的部分
			@SuppressLint("HandlerLeak")
			private void show(final String names,String infors,final String SearchPhone,String Time,final Point2D dot){
				  String name=null;
				  String information=null;
				  String Phone=null;
				  String timeString=null;
				  
				  final Dialog dialog=new Dialog(Gaoji_Search_query.this,R.style.dialog);
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
				  Phone = SearchPhone;
				  timeString = Time;
				  
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
							
							LocationApplication app = (LocationApplication)getApplicationContext();
							int ISin = app.getISin();
							if (ISin == 0) {
								
								AlertDialog.Builder builder = new AlertDialog.Builder(Gaoji_Search_query.this);
								builder.setMessage("您没有登录，是否登录？")
								       .setCancelable(false)
								       .setPositiveButton("是", new DialogInterface.OnClickListener() {
								           @Override
										public void onClick(DialogInterface dialog, int id) {
								        	   Intent it=new Intent();
												it.setClass(Gaoji_Search_query.this, Login.class);
												it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
												Bundle bundle = new Bundle();
												bundle.putInt("value", 4);
												it.putExtras(bundle);
												Gaoji_Search_query.this.startActivity(it);
								           }
								       })
								       .setNegativeButton("否", new DialogInterface.OnClickListener() {
								           @Override
										public void onClick(DialogInterface dialog, int id) {
								        	   Toast.makeText(Gaoji_Search_query.this,"请您先登录，再提交信息",Toast.LENGTH_SHORT).show(); 
								               dialog.cancel();
								               Gaoji_Search_query.this.finish();
								           }
								       });
								builder.show();
							}else if (ISin == 1) {
								Intent intent = new Intent(Gaoji_Search_query.this,loser_release.class);
								Bundle bundle = new Bundle();
								bundle.putString("releasename", SearchPhone);
								intent.putExtras(bundle);
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
								Gaoji_Search_query.this.startActivity(intent);
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
					 m_Se_mapView.getMapControl().getMap().setCenter(dot);
					 m_Se_mapView.refresh();
					 }
			}	
}
