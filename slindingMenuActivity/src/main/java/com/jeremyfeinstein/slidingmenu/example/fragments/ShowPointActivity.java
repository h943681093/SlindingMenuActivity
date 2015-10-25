package com.jeremyfeinstein.slidingmenu.example.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.oiangie.lcuhelper.JavaTool.ImageTools;
import com.oiangie.lcuhelper.JavaTool.SerializableBitmap;
import com.oiangie.lcuhelper.JavaTool.getImage;
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


public class ShowPointActivity extends Activity {
	private MapView m_mapView2 = null;
	private Workspace m_wokspace2 = null;
	private DatasetVector m_datasetVector2 = null;
	public static String SUPERMAPPATH = android.os.Environment
			.getExternalStorageDirectory() + "/SuperMap/";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Environment.setLicensePath(SUPERMAPPATH + "license");
		Environment.initialization(this);
		setContentView(R.layout.gonggong_show_point_activity);
		openData();//������  ���ò�ѯͼ��
		initButtons();//��ʼ��ZoomControlsʵ�ֹ����ռ�Ĳ���
		Log.e("querybyname", "��ȥ��ʾ��ķ���");
		String putString =  getIntent().getExtras().getString("losename");
	   	String Address =  getIntent().getExtras().getString("Address");
	   	String content = getIntent().getExtras().getString("content");
	   	try {
	   		String url =  getIntent().getExtras().getString("url");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("��ʾ��", "û�д���url");
		}
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
	   	addCallout(point2d,putString,content,"xunwuzhushou.jpg");
	}
	private void openData() {
		m_wokspace2 = new Workspace();
		m_mapView2 = (MapView) findViewById(R.id.Map_view2);
		//��Ҫ���õ�ͼ�빤���ռ���й����������޷���ʾ
		m_mapView2.getMapControl().getMap().setWorkspace(m_wokspace2);
		WorkspaceConnectionInfo wsInfo = new WorkspaceConnectionInfo();
		wsInfo.setServer(SUPERMAPPATH + "SampleData/LCU.smwu");
		wsInfo.setType(WorkspaceType.SMWU);
		if (m_wokspace2.open(wsInfo)) {
			String mapString = m_wokspace2.getMaps().get(0);
			m_mapView2.getMapControl().getMap().open(mapString);
			Datasource datasource = m_wokspace2.getDatasources().get("ditushuju");
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
					// TODO �Զ����ɵķ������
					show(name, info, url,point2d);
					Log.e("urld", url);
				}
			});
			Double x = point2d.getX();
			Log.e("������ʾ", ""+x);
			Double y = point2d.getY();
			Log.e("������ʾ", ""+y);
			CallOut callout = new CallOut(ShowPointActivity.this);
			callout.setContentView(calloutLayout);
			callout.setCustomize(true);
			callout.setLocation(point2d.getX(),point2d.getY());
			m_mapView2.addCallout(callout);
			m_mapView2.getMapControl().getMap().setCenter(point2d);
			m_mapView2.getMapControl().getMap().setScale(1.0/1500);
			m_mapView2.getMapControl().getMap().refresh();
		}
		//dialog��ϸչʾ��Ĳ���
		@SuppressLint("HandlerLeak")
		private void show(final String names,String infors,String urls,final Point2D dot){
			//�����ַ�����׼����������
			  String name=null;
			  String information=null;
			  //String url=CommUtil.imageurl+CommUtil.image;
			  //�½�һ��dialog
			  final Dialog dialog=new Dialog(ShowPointActivity.this,R.style.dialog);
			  //���ز���
				View view=getLayoutInflater().inflate(R.layout.point_dialog, null);	
				TextView infor=(TextView) view.findViewById(R.id.point_dialog_tv);
				infor.setMovementMethod(ScrollingMovementMethod.getInstance());
				Button query=(Button)view.findViewById(R.id.point_dialog_nearby);
				final ImageView image=(ImageView) view.findViewById(R.id.point_dialog_iv);
				final	TextView tname=(TextView) view.findViewById(R.id.point_dialog_name);
				//�½�һ����
				name=names;
				information=infors;
				//url=urls;
				//��װ����
				tname.setText(name);
				//��װ������Ϣ
				infor.setText(information);
				//����һ���µ��߳�
				Handler handlerBit=new Handler(){
					@Override
					public void handleMessage(Message m) {
						SerializableBitmap mag = (SerializableBitmap) m.getData().getSerializable("data");
						final Bitmap imag = mag.getBitmap();
						final Bitmap bitmap = ImageTools.ResizebBtmap(imag, 1.0);
						image.setImageBitmap(bitmap);
						image.setOnClickListener(new OnClickListener(){
								//���ͼƬ�Ŵ�ͼƬ
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Dialog dialog=new Dialog(ShowPointActivity.this,R.style.dialog);
								dialog.setCanceledOnTouchOutside(true);
								//û�е�ͼƬ
								dialog.setContentView(R.layout.image);
								//�½���һ������������һ��imanigewiew
								ImageView iv=(ImageView)dialog.findViewById(R.id.image_image);
								//����һ��ͼƬ��С
								Bitmap newBitmap= ImageTools.ResizebBtmap(imag, 2.0);
								
								iv.setImageBitmap(newBitmap);
								
								dialog.show();
							}
						});
					}
				};
				getImage GetImage = new getImage(handlerBit);
				//void JavaTool.getImage.doStart(String url)
				GetImage.doStart(urls);
				//���������������
				query.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						String str = names;
						Log.e("������ť����¼�", str);
						Intent intent = new Intent(ShowPointActivity.this, Nearby.class);
						Bundle bundle = new Bundle();
						bundle.putString("namea", str);
						bundle.putInt("come", 2);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						dialog.cancel();
					}
				});
				//׼�����ֿ�ʼ��ʾ  ���ƶ���ͼ �㵽����
				 Window win = dialog.getWindow();//��ȡ����window
				 LayoutParams params = win.getAttributes();//��ȡLayoutParams
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

