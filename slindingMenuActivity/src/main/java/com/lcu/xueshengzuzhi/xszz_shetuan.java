package com.lcu.xueshengzuzhi;

import java.util.ArrayList;
import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class xszz_shetuan extends Activity {
	private ListView lv = null;
	private ProgressDialog progressDialog = null;
	private Handler handler1;
	boolean isExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.xszz_shetuan);
		lv = (ListView) findViewById(R.id.news_lv_notow);
		doThread();
		
	}

	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			ArrayList<String> datas = m.getData().getStringArrayList("data");
			ActivityResult(datas);
		};
	};

	private void ActivityResult(ArrayList<String> datas) {
		ArrayList<HashMap<String, Object>> mydata = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < datas.size();) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (datas.get(i) != null) {
				map.put("StName", datas.get(i));
				i++;
				map.put("StIntdro", datas.get(i));
				i++;
				map.put("StDesc", datas.get(i));
				i++;
				map.put("StPhone", datas.get(i));
				i++;
				map.put("StUrl", datas.get(i));
				i++;
				map.put("StIntdros", datas.get(i));
				i++;
				mydata.add(map);
			}
		}
		LeagueAdapter adapter = new LeagueAdapter(mydata);
		lv.setAdapter(adapter);
		progressDialog.dismiss();
	}

	// ListView中Adapter
	class LeagueAdapter extends BaseAdapter {

		private ArrayList<HashMap<String, Object>> data;
		private MyView views;

		public LeagueAdapter(ArrayList<HashMap<String, Object>> data) {

			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int indext = position;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.st_item, null);
				views = new MyView();
				views.StName = (TextView) convertView.findViewById(R.id.StName);
				views.StIntdros = (TextView) convertView.findViewById(R.id.StIntdros);
				convertView.setTag(views);
			} else {
				views = (MyView) convertView.getTag();
			}
			
			
			final String StName = data.get(position).get("StName").toString();
			final String StIntdro = data.get(position).get("StIntdro").toString();
			final String StDesc = data.get(position).get("StDesc").toString();
			final String StPhone = data.get(position).get("StPhone").toString();
			final String StUrl = data.get(position).get("StUrl").toString();
			final String StIntdros = data.get(position).get("StIntdros").toString();
			
			
			views.StName.setText(StName);
			views.StIntdros.setText(StIntdros);
			
			
			views.StName.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(xszz_shetuan.this,zuzhi_in.class);
					intent.putExtra("way", 1);
					intent.putExtra("StName", StName);
					intent.putExtra("StIntdro", StIntdro);
					intent.putExtra("StDesc", StDesc);
					intent.putExtra("StPhone", StPhone);
					intent.putExtra("StUrl", StUrl);
					xszz_shetuan.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+StName);
				}
			});
			
			views.StIntdros.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(xszz_shetuan.this,zuzhi_in.class);
					intent.putExtra("way", 1);
					intent.putExtra("StName", StName);
					intent.putExtra("StIntdro", StIntdro);
					intent.putExtra("StDesc", StDesc);
					intent.putExtra("StPhone", StPhone);
					intent.putExtra("StUrl", StUrl);
					xszz_shetuan.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+StName);
				}
			});
			
	
			return convertView;
		}

		class MyView {
			public TextView StName;
			public TextView StIntdros;
		}
	}

	// 开始连接webservice
	@SuppressWarnings("deprecation")
	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private void doThread() {
		Log.e("demo", "开始连接No_Cowebservice");
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			progressDialog = new ProgressDialog(xszz_shetuan.this);
			progressDialog.setTitle("请稍等...");
			progressDialog.setMessage("数据正在加载中......");
			progressDialog.setButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 删除消息队列中的消息，来停止计时器。
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
			String methodName = "GetST";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("StName", "");
			CallWebservice callWeb = new CallWebservice(handler);
			callWeb.doStart(url, methodName, params);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else {
			Toast.makeText(xszz_shetuan.this, "加载失败，请检查网络", 2000).show();
		}
	}
	
	
   
      
}
