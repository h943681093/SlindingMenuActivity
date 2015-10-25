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

public class xszz_xueshenghui extends Activity {
	private ListView lv = null;
	private ProgressDialog progressDialog = null;
	private Handler handler1;
	boolean isExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.xszz_xueshenghui);
		lv = (ListView) findViewById(R.id.news_lv_notow2);
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
				map.put("SuName", datas.get(i));
				i++;
				map.put("SuIntdro", datas.get(i));
				i++;
				map.put("SuDesc", datas.get(i));
				i++;
				map.put("SuPhone", datas.get(i));
				i++;
				map.put("SuUrl", datas.get(i));
				i++;
				map.put("SuIntdros", datas.get(i));
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
				convertView = getLayoutInflater().inflate(R.layout.su_item, null);
				views = new MyView();
				views.SuName = (TextView) convertView.findViewById(R.id.SuName);
				views.SuIntdros = (TextView) convertView.findViewById(R.id.SuIntdros);
				convertView.setTag(views);
			} else {
				views = (MyView) convertView.getTag();
			}
			
			
			final String SuName = data.get(position).get("SuName").toString();
			final String SuIntdro = data.get(position).get("SuIntdro").toString();
			final String SuDesc = data.get(position).get("SuDesc").toString();
			final String SuPhone = data.get(position).get("SuPhone").toString();
			final String SuUrl = data.get(position).get("SuUrl").toString();
			final String SuIntdros = data.get(position).get("SuIntdros").toString();
			
			
			views.SuName.setText(SuName);
			views.SuIntdros.setText(SuIntdros);
	
			
			views.SuName.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(xszz_xueshenghui.this,zuzhi_in.class);
					intent.putExtra("way", 2);
					intent.putExtra("SuName", SuName);
					intent.putExtra("SuIntdro", SuIntdro);
					intent.putExtra("SuDesc", SuDesc);
					intent.putExtra("SuPhone", SuPhone);
					intent.putExtra("SuUrl", SuUrl);
					xszz_xueshenghui.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+SuName);
				}
			});
			
			views.SuIntdros.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Intent intent=new Intent(xszz_xueshenghui.this,zuzhi_in.class);
					intent.putExtra("way", 2);
					intent.putExtra("SuName", SuName);
					intent.putExtra("SuIntdro", SuIntdro);
					intent.putExtra("SuDesc", SuDesc);
					intent.putExtra("SuPhone", SuPhone);
					intent.putExtra("SuUrl", SuUrl);
					xszz_xueshenghui.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+SuName);
				}
			});
			
			
			return convertView;
		}

		class MyView {
			public TextView SuName;
			public TextView SuIntdros;
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
			progressDialog = new ProgressDialog(xszz_xueshenghui.this);
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
			String methodName = "GetSU";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("SuName", "");
			CallWebservice callWeb = new CallWebservice(handler);
			callWeb.doStart(url, methodName, params);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else {
			Toast.makeText(xszz_xueshenghui.this, "加载失败，请检查网络", 2000).show();
		}
	}
}
