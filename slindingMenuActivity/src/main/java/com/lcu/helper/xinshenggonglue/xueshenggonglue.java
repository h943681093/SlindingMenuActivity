package com.lcu.helper.xinshenggonglue;

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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class xueshenggonglue extends Activity {

	private ListView lv;
	private EditText serEditText;
	private String Searchstr = "";
	private ImageView wrbImageView;
	private ProgressDialog progressDialog = null;  //进度条
	private Handler handler1;
	private int SearchHelperway = 0;
	private ImageView newsnotice_back1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xueshenggonglue);
		
		lv=(ListView)findViewById(R.id.Sc_lv);
		newsnotice_back1 = (ImageView) findViewById(R.id.newsnotice_back1);
		newsnotice_back1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Log.i("点击返回", "结束");
				xueshenggonglue.this.finish();
			}
		});
		  doThread();
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
    	ArrayList<HashMap<String, Object>> mydata = new ArrayList<HashMap<String, Object>>();
    	for(int i=0;i<datas.size();){
       	 HashMap<String, Object> map = new HashMap<String, Object>();
       	 if(datas.get(i)!=null){
       		map.put("NewsName", datas.get(i));
       		 i++;
       		map.put("NewsAbs", datas.get(i));
       		 i++;
       		map.put("NewsContent",  datas.get(i));
       		 i++;
       		map.put("NewsTime",  datas.get(i));
       		 i++;
       		map.put("NewsSource",  datas.get(i));
       		 i++;
       		map.put("NewsAddress",  datas.get(i));
      		 i++;
      		map.put("NewsImages",  datas.get(i));
      		 i++;
      		map.put("NewsClass",  datas.get(i));
      		 i++;
       		mydata.add(map);
       		
       	 }
        }    	
    	LeagueAdapter adapter=new LeagueAdapter(mydata);
    	lv.setAdapter(adapter);	
    	progressDialog.dismiss();
    }  
    
    
    
    
    //ListView中Adapter
    class LeagueAdapter extends BaseAdapter{
    	
    	private ArrayList<HashMap<String, Object>> data;
    	private MyView views;
    	public LeagueAdapter(ArrayList<HashMap<String, Object>>  data){
    		
    		this.data=data;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int indext=position;//定义第三变量
			if(convertView==null){
				convertView = getLayoutInflater().inflate(R.layout.scnews_item, null);
				views=new MyView();	
				views.NewsName=(TextView)convertView.findViewById(R.id.newsactName);
				views.NewsAbs=(TextView)convertView.findViewById(R.id.newsactAbstract);
				views.NewsSource=(TextView)convertView.findViewById(R.id.newsactSource);
				views.NewsTime=(TextView)convertView.findViewById(R.id.newsactTime);
				views.map=(ImageButton)convertView.findViewById(R.id.newsorgDepart_map);
				views.NewsClassImages = (ImageView) convertView.findViewById(R.id.newstIv);
				convertView.setTag(views);
			}else{
				views=(MyView)convertView.getTag();
			}
			//收取并处理名字
			final String NewsName = data.get(position).get("NewsName").toString();
			//收取并处理时间
			final String NewsTime = data.get(position).get("NewsTime").toString();
			//收取并处理abs
			final String NewsAbs = data.get(position).get("NewsAbs").toString();
			//收取处理content
			final String NewsContent = data.get(position).get("NewsContent").toString();
			//收取并处理地址
			final String NewsAddress = data.get(position).get("NewsAddress").toString();
			//收取处理来源
			final String NewsSource =  data.get(position).get("NewsSource").toString();
			//收取处理新闻图片
			final String NewsImages = data.get(position).get("NewsImages").toString();
			//收取并处理新闻种类NewsClass  通过class值判断新闻种类现实的是哪个图片
			final String NewsClass = data.get(position).get("NewsClass").toString();
			Log.e("图片选择", "NewsClass"+NewsClass);
			
			views.NewsName.setText(NewsName);
			views.NewsAbs.setText(NewsAbs);
			views.NewsTime.setText(NewsTime);
			//views.NewsContent.setText(contents);
			views.NewsSource.setText(NewsSource);
			//点击菜单和地图的事件
			if (NewsClass.indexOf("1")!=-1) {
				//精华	
				views.NewsClassImages.setImageDrawable(getResources().getDrawable(R.drawable.news_jh));
			}else if (NewsClass.indexOf("2")!=-1) {
				//官方
				views.NewsClassImages.setImageDrawable(getResources().getDrawable(R.drawable.news_gf));
			}else if (NewsClass.indexOf("3")!=-1) {
				//新鲜事
				views.NewsClassImages.setImageDrawable(getResources().getDrawable(R.drawable.news_flesh));
			}else if (NewsClass.indexOf("4")!=-1) {
				//生活
				views.NewsClassImages.setImageDrawable(getResources().getDrawable(R.drawable.news_life));
			}
		
			views.map.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						//Intent intent = new Intent(xueshenggonglue.this,ShowPointActivity.class);
						//Bundle bundle = new Bundle();
						//bundle.putString("losename", NewsName);
						//bundle.putString("content", NewsAbs);
						//bundle.putString("Address", NewsAddress);
						//intent.putExtras(bundle);
						//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
						//startActivity(intent);
					
				}
				
			});
			
			views.NewsName.setOnClickListener(new OnClickListener(){

				@Override
			public void onClick(View v) {
//					// TODO Auto-generated method stub
					Intent intent=new Intent(xueshenggonglue.this,Gonglue_in.class);
					intent.putExtra("NewsName", NewsName);
					intent.putExtra("NewsTime", NewsTime);
					intent.putExtra("NewsAbs", NewsAbs);
					intent.putExtra("NewsContent", NewsContent);
					intent.putExtra("NewsAddress", NewsAddress);
					intent.putExtra("NewsSource", NewsSource);
					intent.putExtra("NewsImages", NewsImages);
					intent.putExtra("NewsClass", NewsClass);
					xueshenggonglue.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+NewsName);
				}
				
			});
			views.NewsAbs.setOnClickListener(new OnClickListener(){
				
				@Override
			public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(xueshenggonglue.this,Gonglue_in.class);
					intent.putExtra("NewsName", NewsName);
					intent.putExtra("NewsTime", NewsTime);
					intent.putExtra("NewsAbs", NewsAbs);
					intent.putExtra("NewsContent", NewsContent);
					intent.putExtra("NewsAddress", NewsAddress);
					intent.putExtra("NewsSource", NewsSource);
					intent.putExtra("NewsImages", NewsImages);
					intent.putExtra("NewsClass", NewsClass);
					xueshenggonglue.this.startActivity(intent);
					Log.e("进入详情", "现在进入了"+NewsName);
					Log.e("进入详情", "现在进入了"+NewsName);
				}
				
			});
			return convertView;
		}
		class MyView{
			   public TextView NewsName;
			   public TextView NewsTime;
			   public TextView NewsAbs;
			   public TextView NewsContent;
			   public TextView NewsAddress;
			   public TextView NewsSource;
			   public ImageView NewsClassImages;
			   public ImageButton map;
		}
    }
    //开始连接webservice
	@SuppressWarnings("deprecation")
	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private void doThread(){
		Log.e("demo", "开始连接webservice");
			ConnectivityManager  connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
			//ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
	      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo(); 
	       if(networkInfo != null &&  networkInfo.isAvailable()){
	    	   progressDialog=new ProgressDialog(this);
	    	   progressDialog.setTitle("请稍等...");
	    	   progressDialog.setMessage("数据正在加载中......");
	    	   progressDialog.setButton("取消", new DialogInterface.OnClickListener(){
	    		   @Override
	               public void onClick(DialogInterface dialog, int which)
	               {
	                   // 删除消息队列中的消息，来停止计时器。
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
		 String methodName="GetGL";
		 HashMap<String, Object> params = new HashMap<String, Object>();
		 params.put("GlName", "");
		 CallWebservice callWeb=new CallWebservice(handler);
		 callWeb.doStart(url, methodName, params);
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
	       }
	       else{    	 
	    	   Toast.makeText(this, "加载失败，请检查网络", 2000).show();
	    	   }
    }
	
}
