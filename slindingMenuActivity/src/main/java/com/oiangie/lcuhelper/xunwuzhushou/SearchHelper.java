package com.oiangie.lcuhelper.xunwuzhushou;

import java.util.ArrayList;
import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.Login;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class SearchHelper extends Activity {
	private ListView lv;
	private EditText serEditText;
	private String Searchstr = "";
	private ImageView wrbImageView;
	private ProgressDialog progressDialog = null;  //进度条
	private Handler handler1;
	private int SearchHelperway = 0;
	private ImageView se_refesh;
	private ImageView information_back;
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_searchhelper);
		lv=(ListView) findViewById(R.id.Sc_lv);
	    serEditText = (EditText) findViewById(R.id.search_search);
	    
	    information_back = (ImageView) findViewById(R.id.information_back);
	    information_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				SearchHelper.this.finish();
			}
		});
	    
	    //刷新按钮
	    se_refesh = (ImageView) findViewById(R.id.se_refesh);
	    se_refesh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				doThread();
			}
		});
	    //上传按钮
	    wrbImageView = (ImageView) findViewById(R.id.information_Write);
	    wrbImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//intent到写入界面
				startActivity(new Intent(SearchHelper.this, Search_helper_release.class));
			}
		});
	    //搜索栏
	    serEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(SearchHelper.this,Search_query.class));
			}
		});
		}
	
	
	@Override  
	public void onResume() {  
	    // TODO Auto-generated method stub  
	    LocationApplication app = (LocationApplication)getApplicationContext();
	    
	    SearchHelperway = app.getSearchHelperWay();//取值
	    
	    if (SearchHelperway == 0) {
	    	 doThread();
	    	
		}
	    else if (SearchHelperway == 1) {
	    	
		    try {
			    
		        Searchstr = app.getSearchHelperName();//取值
			    Log.e("接受全局变量", Searchstr);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("接受全局变量", "没有值");
			}
		    doThread();
		    
		    //初始化全局变量
		    app.setSearchHelperName("");
		    app.setSearchHelperWay(0);
		}
	  //Fragment对象的ui可以与用户交互时调用。
	    super.onResume();  
	    
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
					convertView = getLayoutInflater().inflate(R.layout.search_helper_item, null);
					views=new MyView();	
					views.Name=(TextView)convertView.findViewById(R.id.wpName);
					views.Time=(TextView)convertView.findViewById(R.id.Sc_Time);
					views.Time2=(TextView)convertView.findViewById(R.id.Sc_Time2);
					views.Content=(TextView)convertView.findViewById(R.id.actContent);
					views.map=(ImageButton)convertView.findViewById(R.id.ScDepart_map);
					views.renlingButton=(Button)convertView.findViewById(R.id.actPhone);
					convertView.setTag(views);
				}else{
					views=(MyView)convertView.getTag();
				}
				
				//收取并处理时间
				String time = data.get(position).get("SearchTime").toString();
				String [] times = time.split(",");
				String timeD =  times[0];
				String timeS =  times[1];
				final String Address = data.get(position).get("SearchAddress").toString();
				final String loname = data.get(position).get("SearchName").toString();
				final String contents = data.get(position).get("SearchContent").toString();
				final String releasename = data.get(position).get("ReleaseName").toString();
				views.Name.setText(loname);
				views.Time.setText(timeD);
				views.Time2.setText(timeS);
				views.Content.setText(contents);
				views.renlingButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						
						//先判断是否登录
						LocationApplication app = (LocationApplication)getApplicationContext();
						int ISin = app.getISin();
						if (ISin == 0) {
							
							AlertDialog.Builder builder = new AlertDialog.Builder(SearchHelper.this);
							builder.setMessage("您没有登录，是否登录？")
							       .setCancelable(false)
							       .setPositiveButton("是", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							        	   Intent it=new Intent();
											it.setClass(SearchHelper.this, Login.class);
											it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
											Bundle bundle = new Bundle();
											bundle.putInt("value", 3);
											it.putExtras(bundle);
											SearchHelper.this.startActivity(it);
							           }
							       })
							       .setNegativeButton("否", new DialogInterface.OnClickListener() {
							           public void onClick(DialogInterface dialog, int id) {
							        	   Toast.makeText(SearchHelper.this,"请您先登录，再提交信息",Toast.LENGTH_SHORT).show(); 
							               dialog.cancel();
							           }
							       });
							builder.show();
						}else if (ISin == 1) {
							Intent intent = new Intent(SearchHelper.this,loser_release.class);
							Bundle bundle = new Bundle();
							bundle.putString("releasename", releasename);
							bundle.putString("contents", contents);
							intent.putExtras(bundle);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
							SearchHelper.this.startActivity(intent);
						}
						
					}
				});
				views.map.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(SearchHelper.this,ShowPointActivity3.class);
						Bundle bundle = new Bundle();
						bundle.putString("losename", loname);
						bundle.putString("content", contents);
						bundle.putString("Address", Address);
						intent.putExtras(bundle);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
						SearchHelper.this.startActivity(intent);
						
					}
					
				});
				
				return convertView;
			}
			class MyView{
				   public TextView Name;
				   public TextView Time;
				   public TextView Time2;
				   public TextView Content;
				   public ImageButton map;
				   public Button renlingButton;
			}
	    }
	    //开始连接webservice（按照名称搜索时）
		@SuppressWarnings("deprecation")
		@SuppressLint({ "HandlerLeak", "ShowToast" })
		private void doThread(){
			Log.e("demo", "开始连接webservice");
				ConnectivityManager  connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo(); 
		       if(networkInfo != null &&  networkInfo.isAvailable()){
		    	   progressDialog=new ProgressDialog(SearchHelper.this);
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
				 params.put("SearchName", Searchstr);
				 CallWebservice callWeb=new CallWebservice(handler);
				 callWeb.doStart(url, methodName, params);
			 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
		       }
		       else{    	 
		    	   Toast.makeText(SearchHelper.this, "加载失败，请检查网络", 2000).show();
		    	   }
	    }
			
}

