package com.lcu.helper.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.ImageTools;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
import com.oiangie.lcuhelper.JavaTool.SerializableBitmap;
import com.oiangie.lcuhelper.JavaTool.getImage;
import com.oiangie.lcuhelper.query.Nearby;
import com.oiangie.lcuhelper.query.Query;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.Environment;
import com.supermap.data.GeoPoint;
import com.supermap.data.Point2D;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.Map;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class SchoolMap extends Activity {
	private MapControl m_mapControl = null;
	private Workspace m_wokspace;
	private MapView m_mapView;
	private ZoomControls m_zoom;
	private EditText mEditText;
	private Button mlocbt,mnearby;
	private DatasetVector m_datasetVector = null;
	private Button shownews;
	private Button showactivity;
	private ArrayList<HashMap<String, Object>> mydataA;
	private ArrayList<HashMap<String, Object>> mydataN;
	private LocationClient mLocationClient;
	private SharedPreferences sp;
	private String LocationResult;
	private Float Locationx, Locationy;
	private ProgressDialog progressDialog = null; // ������
	private Handler handler1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Environment.setLicensePath("sdcard/supermap/license/");
		Environment.initialization(this);

		this.setContentView(R.layout.schoolmap);

		m_wokspace = new Workspace();
		WorkspaceConnectionInfo info = new WorkspaceConnectionInfo();
		info.setServer("/sdcard/SuperMap/SampleData/LCU.smwu");
		info.setType(WorkspaceType.SMWU);
		m_wokspace.open(info);

		// ����ͼ��ʾ�ؼ��͹����ռ����
		m_mapView = (MapView) findViewById(R.id.Map_view);
		m_mapControl = m_mapView.getMapControl();
		m_mapControl.getMap().setWorkspace(m_wokspace);

		// �򿪹����ռ��еĵ�һ����ͼ
		String mapName = m_wokspace.getMaps().get(0);
		m_mapControl.getMap().open(mapName);

		// ������
		Datasource datasource = m_wokspace.getDatasources().get("ditushuju");
		if (datasource != null) {
			m_datasetVector = (DatasetVector) datasource.getDatasets().get(
					"POI");
		}
		m_mapView = (MapView) findViewById(R.id.Map_view);

		m_mapControl.getMap().refresh();

		mEditText = (EditText) findViewById(R.id.etSearch);
		mEditText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SchoolMap.this, Query.class));
			}
		});
		mnearby = (Button) findViewById(R.id.nearby_btn);
		mnearby.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				mLocationClient.start();
//				sp = getActivity().getSharedPreferences("location",
//						Context.MODE_PRIVATE);
//				String mSLocationx = sp.getString("latitude", "0.0");
//				String mSLocationy = sp.getString("longitude", "0.0");
//				Double mLocationx = Double.valueOf(mSLocationx);
//				Double mLocationy = Double.valueOf(mSLocationy);
//				mLocationx = mLocationx - 750;
//				mLocationy = mLocationy + 24770;
//				Point2D point2d = new Point2D();
//				point2d.setX(mLocationx);
//				point2d.setY(mLocationy);
//
//				GeoPoint gpsGeoPoint = new GeoPoint();
//				gpsGeoPoint.setX(mLocationx);
//				gpsGeoPoint.setY(mLocationy);
//
//				addCallOutByName(gpsGeoPoint, point2d);
				
				
				Intent intent = new Intent(SchoolMap.this, Nearby.class);
				Bundle bundle = new Bundle();
				bundle.putInt("come", 1);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
						| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// ���߱����ϣ����¿�ʼMainActivity
				startActivity(intent);
			}
		});

		// ��λ
		mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
		Log.e("map_fragment", "1");
		((LocationApplication)getApplication()).mLocationResult = LocationResult;
		Log.e("map_fragment", "2");

		mlocbt = (Button) findViewById(R.id.btnlocation);
		mlocbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				// �����ʼ��λ

				// �趨�ö�λ�ķ���
				LocationClientOption option = new LocationClientOption();
				option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
				option.setCoorType("bd09");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
											// ����ּ��ܾ�γ�� bd9II �ٶȼ��ܾ�γ��
				option.setScanSpan(3000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
				option.setIsNeedAddress(true);// �Ƿ���Ҫ����������
				mLocationClient.setLocOption(option);
				if (mlocbt.getText().equals("��λ")) {
					mLocationClient.start();

					mlocbt.setText("ֹͣ");
					sp = getSharedPreferences("location",
							Context.MODE_PRIVATE);
					String mSLocationx = sp.getString("latitude", "0.0");
					String mSLocationy = sp.getString("longitude", "0.0");
					Double mLocationx = Double.valueOf(mSLocationx);
					Double mLocationy = Double.valueOf(mSLocationy);
					mLocationx = mLocationx - 750;
					mLocationy = mLocationy + 24770;

					Point2D point2d = new Point2D();
					point2d.setX(mLocationx);
					point2d.setY(mLocationy);

					GeoPoint gpsGeoPoint = new GeoPoint();
					gpsGeoPoint.setX(mLocationx);
					gpsGeoPoint.setY(mLocationy);

					addCallOutByName(gpsGeoPoint, point2d);

				} else {
					mLocationClient.stop();
					mlocbt.setText("��λ");
				}

			}
		});

//		shownews = (Button) getActivity().findViewById(R.id.show_news);
//		shownews.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO �Զ����ɵķ������
//				doThread("NewsName","Getnews",1);
//			}
//		});
//		
//		showactivity = (Button) getActivity().findViewById(R.id.show_activity);
//		showactivity.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO �Զ����ɵķ������
//				doThread("Contents","GetActivity",2);
//			}
//		});
//	
	}
	
	private void addCallOutByName(GeoPoint gps, Point2D point2d) {
		// TODO �Զ����ɵķ������
		Log.e("location", "��ʾ��");
		m_mapView.removeAllCallOut();
		CallOut callout = new CallOut(this);
		callout.setStyle(CalloutAlignment.CENTER);
		callout.setCustomize(true);
		callout.setLocation(gps.getX(), gps.getY());
		Log.i("demo", "X" + gps.getX());
		Log.i("demo", "Y" + gps.getY());
		ImageView image = new ImageView(this);
		image.setBackgroundResource(R.drawable.locat);
		callout.setContentView(image);
		Map map = m_mapView.getMapControl().getMap();
		map.setCenter(point2d);
		map.setScale(1.0 / 1500);
		map.refresh();
		m_mapView.addCallout(callout);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.d("", "onResume");

		LocationApplication app = (LocationApplication)getApplicationContext();
		int onNewIntentway = app.getonNewIntentway();

		if (onNewIntentway == 4) {
			// ���onNewIntentway=4��
			Log.e("��ʼ��ʾ", "��ʼ��ʾ");
			HashMap<String, Object> map = app.getshowpointerwei();
			String location = map.get("ewlocation1").toString();
			String name = map.get("ewname1").toString();
			String info = map.get("ewinfo1").toString();
			String url = map.get("ewurl1").toString();
			
			String[] Addresses = location.split(",");
			String AddressX = Addresses[0];
			Double addressX = Double.valueOf(AddressX);
			String AddressY = Addresses[1];
			Double addressY = Double.valueOf(AddressY);
			GeoPoint geoPoint = new GeoPoint();
			geoPoint.setX(addressX);
			geoPoint.setY(addressY);
			
			//Point2D point2d = new Point2D();
			//point2d.setX(addressX);
			//point2d.setY(addressY);
			//show(name, info,url, point2d);
			addCallout(geoPoint,name,info,url);
			
			
		} else if (onNewIntentway == 3) {
			// ���onNewIntentway=3��ͨ����õ�������ʾ������

			String name = app.getStringSearchName();
			String address = app.getlocationAddress();
			String[] Addresses = address.split(",");
			String AddressX = Addresses[0];
			Double addressX = Double.valueOf(AddressX);
			String AddressY = Addresses[1];
			Double addressY = Double.valueOf(AddressY);
			Point2D point2d = new Point2D();
			point2d.setX(addressX);
			point2d.setY(addressY);

			show(name, "", "", point2d);
			
		} else if (onNewIntentway == 2) {
			// ���onNewIntentway=2��ͨ�����ݼ��е�������γɵ�������ʾ�����
			queryByName();

		} else if (onNewIntentway == 1) {
			// ���onNewIntentway=1��ͨ�����ݼ��е��������ʾ������
			queryByName();
		}

		super.onResume();
	}

	/**
	 * ���ݵõ��Ĺؼ��ֽ���ģ����ѯ ��Ѱ��������ݼ�
	 */
	private void queryByName() {
		// �õ��ַ��� ��ָʾ����
		LocationApplication app = (LocationApplication)getApplicationContext();
		int ways = app.getShowMappointWay();
		Log.e("��ʾ��querybyname", "ways" + ways);

		if (ways == 1) {

			String inputString = app.getStringSearchName();
			if (inputString.length() == 0) {
				Toast.makeText(this, "����������", Toast.LENGTH_SHORT)
						.show();
				return;
			}

			if (m_datasetVector != null) {
				inputString.trim();
				String strFilter = "SEARCHNAME like '%" + inputString + "%'";
				Recordset recordset = m_datasetVector.query(strFilter,
						CursorType.STATIC);
				showRecord(recordset);// �õ�������ݼ�
				app.setStringSearchName(null);
			}
		} else if (ways == 2) {

			ArrayList<String> names = app.getArraySearchName();
			int size = names.size();
			if (names.size() == 0) {
				Toast.makeText(this, "û�п���ʾ������", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			for (int i = 0; i < size; i++) {
				String name = names.get(i);
				if (m_datasetVector != null) {
					name.trim();
					String strFilter = "SEARCHNAME like '%" + name + "%'";
					Recordset recordset = m_datasetVector.query(strFilter,
							CursorType.STATIC);
					showRecord(recordset);// �õ�������ݼ�
				}

			}
			app.setArraySearchName(null);
		}
	}

	/**
	 * ��ѯ���չʾ
	 */
	private void showRecord(Recordset recordset) {
		if (recordset.getRecordCount() > 0) {
			Map map = m_mapView.getMapControl().getMap();
			recordset.moveFirst();
			int i = 0;
			while (!recordset.isEOF() && i <= 20) {

				String nameFieldString = recordset.getFieldValue("name")
						.toString();
				String addString = recordset.getFieldValue("info").toString();
				String url = recordset.getFieldValue("image").toString();
				Log.e("urlA", url);
				if (!url.contains(".")) {
					url = CommUtil.imageurl + CommUtil.image;
				} else {
					url = CommUtil.imageurl + url;
				}
				Log.e("urlB", url);
				GeoPoint geoPoint = (GeoPoint) recordset.getGeometry();
				addCallout(geoPoint, nameFieldString, addString, url);
				recordset.moveNext();
				i++;
			}
			if (recordset.getRecordCount() == 1) {
				recordset.moveFirst();
				GeoPoint geoPoint = (GeoPoint) recordset.getGeometry();
				Point2D point2d = new Point2D(geoPoint.getX(), geoPoint.getY());
				map.setCenter(point2d);
				map.setScale(1.0 / 1500);
				map.refresh();
			} else {
				Rectangle2D rc = recordset.getBounds();
				map.setViewBounds(rc);
				map.zoom(6);
				map.refresh();
			}
		} else {
			Toast.makeText(this, "û�з��������ļ�¼������������", Toast.LENGTH_SHORT)
					.show();
		}
		recordset.dispose();
	}

	/**
	 * ������ʾ��Ȥ������ƺ͵�ַ
	 * 
	 * @param geoPoint
	 *            ���ݶ�Ӧ�ĵ�
	 * @param nameString
	 *            ��Ȥ������
	 * @param addrString
	 *            ��Ȥ���ַ ��Ȥ���ͼƬ��ַ
	 */
	private void addCallout(GeoPoint geoPoint, String nameString,
			String addrString, final String url) {
		final String name = nameString;
		final String info = addrString;
		final Point2D point2d = new Point2D(geoPoint.getX(), geoPoint.getY());

		Log.i("demo", "X" + geoPoint.getX());
		Log.i("demo", "Y" + geoPoint.getY());
		LayoutInflater lfCallOut = getLayoutInflater();
		View calloutLayout = lfCallOut.inflate(R.layout.callout, null);
		ImageView tView = (ImageView) calloutLayout
				.findViewById(R.id.imageViewcall);
		tView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				show(name, info, url, point2d);
				Log.e("urld", url);

			}
		});

		CallOut callout = new CallOut(this);
		callout.setContentView(calloutLayout);
		callout.setCustomize(true);
		callout.setLocation(geoPoint.getX(), geoPoint.getY());
		m_mapView.addCallout(callout);
		m_mapView.getMapControl().getMap().setCenter(point2d);
		m_mapView.refresh();
	}

	// dialog��ϸչʾ��Ĳ���
	@SuppressLint("HandlerLeak")
	private void show(final String names, String infors, String urls,
			final Point2D dot) {
		// �����ַ�����׼����������
		String name = null;
		String information = null;
		// String url=CommUtil.imageurl+CommUtil.image;

		// �½�һ��dialog
		final Dialog dialog = new Dialog(this, R.style.dialog);
		// ���ز���
		View view = getLayoutInflater().inflate(R.layout.point_dialog, null);
		TextView infor = (TextView) view.findViewById(R.id.point_dialog_tv);
		infor.setMovementMethod(ScrollingMovementMethod.getInstance());
		Button query = (Button) view.findViewById(R.id.point_dialog_nearby);
		final ImageView image = (ImageView) view
				.findViewById(R.id.point_dialog_iv);
		final TextView tname = (TextView) view
				.findViewById(R.id.point_dialog_name);
		// �½�һ����
		name = names;
		information = infors;
		// url=urls;
		// ��װ����
		tname.setText(name);
		// ��װ������Ϣ
		infor.setText(information);
		// ����һ���µ��߳�
		Handler handlerBit = new Handler() {
			@Override
			public void handleMessage(Message m) {
				SerializableBitmap mag = (SerializableBitmap) m.getData()
						.getSerializable("data");
				final Bitmap imag = mag.getBitmap();
				final Bitmap bitmap = ImageTools.ResizebBtmap(imag, 1.0);
				image.setImageBitmap(bitmap);
				image.setOnClickListener(new OnClickListener() {
					// ���ͼƬ�Ŵ�ͼƬ
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Dialog dialog = new Dialog(SchoolMap.this,R.style.dialog);
						dialog.setCanceledOnTouchOutside(true);
						// û�е�ͼƬ
						dialog.setContentView(R.layout.image);
						// �½���һ������������һ��imanigewiew
						ImageView iv = (ImageView) dialog.findViewById(R.id.image_image);
						// ����һ��ͼƬ��С
						Bitmap newBitmap = ImageTools.ResizebBtmap(imag, 2.0);

						iv.setImageBitmap(newBitmap);

						dialog.show();
					}

				});
			}
		};

		getImage GetImage = new getImage(handlerBit);
		GetImage.doStart(urls);

		// ���������������
		query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				String str = names;
				Log.e("������ť����¼�", str);
				Intent intent = new Intent(SchoolMap.this, Nearby.class);
				Bundle bundle = new Bundle();
				bundle.putString("namea", str);
				bundle.putInt("come", 2);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				dialog.cancel();
			}

		});

		// ׼�����ֿ�ʼ��ʾ ���ƶ���ͼ �㵽����
		Window win = dialog.getWindow();// ��ȡ����window
		LayoutParams params = win.getAttributes();// ��ȡLayoutParams
		win.setGravity(Gravity.CENTER);
		params.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		params.y = -170;
		params.x = 10;
		win.setAttributes(params);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		if (name != null) {
			dialog.show();
			m_mapView.getMapControl().getMap().setCenter(dot);
			m_mapView.refresh();
		}
	}

	// �����߳�
	@SuppressLint("HandlerLeak")
	private Handler handlerN = new Handler() {
		@Override
		public void handleMessage(Message m) {
			ArrayList<String> datasN = m.getData().getStringArrayList("data");
			doResultN(datasN);
		};
	};

	// ��ʾwebservice���   shownews
	private void doResultN(ArrayList<String> datas) {
		mydataN = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 81;) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (datas.get(i) != null) {
				map.put("NewsName", datas.get(i));
				i++;
				map.put("NewsAbs", datas.get(i));
				i++;
				map.put("NewsContent", datas.get(i));
				i++;
				map.put("NewsTime", datas.get(i));
				i++;
				map.put("NewsSource", datas.get(i));
				i++;
				map.put("NewsAddress", datas.get(i));
				i++;
				map.put("NewsImages", datas.get(i));
				i++;
				map.put("NewsClass", datas.get(i));
				i++;
				mydataN.add(map);
			}
		}
		progressDialog.dismiss();

		Log.e("��ʼ��������", "��ʼ����");
		for (int i = 0; i < 9; i++) {
			String NewsName = (String) mydataN.get(i).get("NewsName");
			Log.e("��ʼ��������", "��ʼ����" + NewsName);
			String NewsAbs = (String) mydataN.get(i).get("NewsAbs");
			String NewsContent = (String) mydataN.get(i).get("NewsContent");
			String NewsTime = (String) mydataN.get(i).get("NewsTime");
			String NewsSource = (String) mydataN.get(i).get("NewsSource");
			String NewsAddress = (String) mydataN.get(i).get("NewsAddress");
			String NewsImages = (String) mydataN.get(i).get("NewsImages");
			String NewsClass = (String) mydataN.get(i).get("NewsClass");
			
			String [] Addresses = NewsAddress.split(",");
			String AddressX =  Addresses[0];
			Double addressX = Double.valueOf(AddressX);
			String AddressY =  Addresses[1];
			Double addressY = Double.valueOf(AddressY);
			
			GeoPoint geoPoint = new GeoPoint(addressX, addressY);
			
			addCallout(geoPoint, NewsName,NewsAbs, NewsImages);
			
		}
	}

	

	// �����߳�
	@SuppressLint("HandlerLeak")
	private Handler handlerA = new Handler() {
		@Override
		public void handleMessage(Message m) {
			ArrayList<String> datasA = m.getData().getStringArrayList("data");
			doResultA(datasA);
		};
	};

	// ��ʾwebservice���   shownews
	private void doResultA(ArrayList<String> datas) {
		mydataA = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 51;) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (datas.get(i) != null) {
				map.put("name", datas.get(i));
				Log.e("����ֵ", datas.get(i));
				i++;
				map.put("introduce", datas.get(i));
				i++;
				map.put("time", datas.get(i));
				i++;
				map.put("content", datas.get(i));
				i++;
				map.put("address", datas.get(i));
				i++;
				mydataA.add(map);
			}
		}
		progressDialog.dismiss();

		Log.e("��ʼ��������", "��ʼ����");
		for (int i = 0; i < 9; i++) {
			String name = (String) mydataA.get(i).get("name");
			String introduce = (String) mydataA.get(i).get("introduce");
			String time = (String) mydataA.get(i).get("time");
			String content = (String) mydataA.get(i).get("content");
			String address = (String) mydataA.get(i).get("address");

			
			String [] Addresses = address.split(",");
			String AddressX =  Addresses[0];
			Double addressX = Double.valueOf(AddressX);
			String AddressY =  Addresses[1];
			Double addressY = Double.valueOf(AddressY);
			
			GeoPoint geoPoint = new GeoPoint(addressX, addressY);
			
			addCallout(geoPoint,name,introduce, "image.jpg");
			
		}
	}

	
	
	// ��ʼ����webservice
	@SuppressWarnings("deprecation")
	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private void doThread(String searchname  , String methodName ,int way) {
		Log.e("demo", "��ʼ����webservice");
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		// ConnectivityManager connectivityManager =
		// (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("���Ե�...");
			progressDialog.setMessage("�������ڼ�����......");
			progressDialog.setButton("ȡ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ɾ����Ϣ�����е���Ϣ����ֹͣ��ʱ����
							handler1.removeMessages(1);
							progressDialog.setProgress(0);
						}
					});
			progressDialog.show();
			handler1 = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					progressDialog.dismiss();
				}
			};
			String url = "Study.asmx";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(searchname, "");
			
			if (way==1) {
				CallWebservice callWebN = new CallWebservice(handlerN);
				callWebN.doStart(url, methodName, params);
				m_mapView.removeAllCallOut();
			}else if (way==2) {
				CallWebservice callWebA = new CallWebservice(handlerA);
				callWebA.doStart(url, methodName, params);
				m_mapView.removeAllCallOut();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "����ʧ�ܣ���������", 2000).show();
		}
	}
	
	
	

}
