package com.oiangie.lcuhelper.query;

import java.util.ArrayList;
import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.SchoolMap;
import com.oiangie.lcuhelper.query.QueryResult;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QueryResult extends Activity{
	private ListView lv;
	private TextView Title;
	private ImageView map1;
	private int key=0;
	private String title=" ";
	Boolean isEdit=false;
	private ImageView back=null;
	
	private Workspace m_wokspace = null;
	private DatasetVector m_datasetVector = null;
	public static String SUPERMAPPATH = android.os.Environment
			.getExternalStorageDirectory() + "/SuperMap/";

	private void openData() {
		m_wokspace = new Workspace();
		
		WorkspaceConnectionInfo wsInfo = new WorkspaceConnectionInfo();
		wsInfo.setServer(SUPERMAPPATH + "SampleData/LCU.smwu");
		wsInfo.setType(WorkspaceType.SMWU);

		if (m_wokspace.open(wsInfo)) {	
			
			Datasource datasource = m_wokspace.getDatasources().get("ditushuju");
			if (datasource != null) {
				m_datasetVector = (DatasetVector) datasource.getDatasets().get("POI");
			}
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 		
		this.setContentView(R.layout.query_result);
			//初始化超图环境
			Environment.setLicensePath(SUPERMAPPATH + "license");
			Environment.initialization(this);
			openData();//打开数据	
		
			
			Intent intent=getIntent();
			Bundle bundle = intent.getExtras();
			
			
		 		title=bundle.getString("title");
		 		isEdit=bundle.getBoolean("isEdit");
		 		
		 		//返回键事件
		 		back=(ImageView)this.findViewById(R.id.query_result_back);
		 		back.setOnClickListener(new ImageView.OnClickListener(){
		 				@Override
		 			public void onClick(View v) {
		 					// TODO Auto-generated method stub
		 				QueryResult.this.finish();
		 				}});
		 		
		 		
		 		lv=(ListView)findViewById(R.id.result_lv);
		 		
		 		
		 		Title=(TextView) findViewById(R.id.result_title);
		 		Title.setText(title);//使查询结果的界面上题目显示为正在查询的内容
		 		
		 		map1=(ImageView) findViewById(R.id.result_location);
		 		
		 		if(isEdit){   
		 				queryByName(title);
		 			}
		 			else{
		 				queryByLoction(title);
		 				Log.e("开始查找了", title);
		 			}
		 		
		 		
		 			
		 			map1.setOnClickListener(new OnClickListener(){
		 				@Override
		 				public void onClick(View v) {
		 					// TODO Auto-generated method stub
		 					MyAdapter adapter=(MyAdapter) lv.getAdapter();
		 					ArrayList<String> names=adapter.getName();
		 					LocationApplication app = (LocationApplication) getApplicationContext();
		 					//app.setDoShowMappoint(1);
		 					app.setArraySearchName(names);
		 					app.setShowMappointWay(2);
		 					app.setonNewIntentway(2);
		 					Intent it=new Intent();
		 					it.setClass(QueryResult.this, SchoolMap.class);
		 					it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 					//Bundle bundle = new Bundle();
		 					//bundle.putInt("isall", 1);
//		 					bundle.putInt("key", key);
//		 					bundle.putStringArrayList("nameall",names);
		 					//it.putExtras(bundle);
		 					QueryResult.this.startActivity(it);
		 				}
		
		 			});
	}
	

	

	private void queryByName(String str) {
		if (m_datasetVector != null) {
			str.trim();
			String strFilter = "SEARCHNAME like '%" + str + "%'";
			Recordset recordset = m_datasetVector.query(strFilter,
					CursorType.STATIC);
			
			showRecord(recordset);
			
		}
	}
	
	
	private void queryByLoction(String title) {
		
		Intent intent=getIntent();
		Bundle bundle = intent.getExtras();

		Double  dLong= bundle.getDouble("x");
		Double  dLat= bundle.getDouble("y");

			

		if (m_datasetVector != null) {


			Point2D point = new Point2D( dLong, dLat);
			Point2Ds pnts = new Point2Ds();
			pnts.add(point);

			PrjCoordSys prjCoordSys = m_datasetVector.getPrjCoordSys();

			CoordSysTranslator.forward(pnts, prjCoordSys);

			GeoPoint geoPoint = new GeoPoint(pnts.getItem(0));
			// 用点坐标查询，给一个缓冲半径200米
			Recordset recordset = m_datasetVector.query(geoPoint, 150,
					CursorType.STATIC);
			int a = recordset.getRecordCount();
			showRecord2(recordset,title);
		}
	}

	private void showRecord(Recordset recordset) {
		
		ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String, Object>>();
		int Count =0; 
		if (recordset.getRecordCount() > 0) {
		    //Map map = m_mapView.getMapControl().getMap();
			Count=recordset.getRecordCount();
			recordset.moveFirst();
			while (!recordset.isEOF()) {
				HashMap<String, Object> hsmap=new HashMap<String, Object>();
				//!recordset.isEOF()判断当前记录的位置是否在记录集中的最后一条记录的后面，如果是，返回 true；否则返回 false。
				String name = recordset.getFieldValue("name").toString();
				String url = recordset.getFieldValue("image").toString();
				String infor=recordset.getFieldValue("info").toString();
				GeoPoint geoPoint = (GeoPoint)recordset.getGeometry();
				if(infor==null||infor.equals("")){
					infor="暂无简介";
				}
				if(!url.contains(".")){//检查字符中是否含有"."
					url=CommUtil.imageurl+CommUtil.image;
				}
				else{
					url=CommUtil.imageurl+url;
				}
				hsmap.put("name", name);
				hsmap.put("url", url);
				hsmap.put("infor", infor);
				data.add(hsmap);
				recordset.moveNext();
			}
			
			if(data.size()==Count){
				MyAdapter adapter = new MyAdapter(this,data,lv,QueryResult.this,key);
		    	lv.setAdapter(adapter);
			}

		} else {
			Toast.makeText(this, "没有符合条件的记录，请重新输入", Toast.LENGTH_SHORT).show();
		}
		recordset.dispose();
	}	
	
	private void showRecord2(Recordset recordset , String title) {
		ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String, Object>>();
		int Count =0; 
		if (recordset.getRecordCount() > 0) {
		    //Map map = m_mapView.getMapControl().getMap();
			Count=recordset.getRecordCount();
			recordset.moveFirst();
			while (!recordset.isEOF()) {
				HashMap<String, Object> hsmap=new HashMap<String, Object>();
				//!recordset.isEOF()判断当前记录的位置是否在记录集中的最后一条记录的后面，如果是，返回 true；否则返回 false。
				String name = recordset.getFieldValue("name").toString();
				String searchname = recordset.getFieldValue("searchname").toString();
				String url = recordset.getFieldValue("image").toString();
				String infor=recordset.getFieldValue("info").toString();
				GeoPoint geoPoint = (GeoPoint)recordset.getGeometry();
				if(infor==null||infor.equals("")){
					infor="暂无简介";
				}
				if(!url.contains(".")){//检查字符中是否含有"."
					url=CommUtil.imageurl+CommUtil.image;
				}
				else{
					url=CommUtil.imageurl+url;
				}
				hsmap.put("name", name);
				hsmap.put("url", url);
				hsmap.put("infor", infor);
				//name.indexOf(title)!=-1
				if (searchname.contains(title)) {
					Log.e("附近测试", "题目符合进入方法");
					data.add(hsmap);
				}
				
				Log.e("demo", "我已经添加好一个hsmap");
				recordset.moveNext();
			}
			
				MyAdapter adapter = new MyAdapter(this,data,lv,QueryResult.this,key);
		    	lv.setAdapter(adapter);

		} else {
			Toast.makeText(this, "没有符合条件的记录，请重新输入", Toast.LENGTH_SHORT).show();
		}
		recordset.dispose();
	}	

	OnItemClickListener Listener = new OnItemClickListener(){

		@SuppressWarnings("unchecked")
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = (HashMap<String, String>) arg0
					.getItemAtPosition(arg2);
			String name=map.get("name");
			String url=map.get("url");
			String infor=map.get("infor");
			Bundle bundle=new Bundle();
			ArrayList<String> data=new ArrayList<String>();
			data.add(name);
			data.add(url);
			data.add(infor);
			bundle.putStringArrayList("data", data);
		}
		
	};
	
	@Override
	public void onDestroy() {
		m_wokspace.close();
		super.onDestroy();
	}


}
