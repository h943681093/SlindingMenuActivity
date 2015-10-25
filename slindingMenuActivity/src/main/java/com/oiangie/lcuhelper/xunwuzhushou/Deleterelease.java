package com.oiangie.lcuhelper.xunwuzhushou;

import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.User;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Deleterelease extends Activity {
private ImageView sureinf_back;
private TextView noticetitle,noticecontent;
private Button falseinf,trueinf;
private boolean isGO =false ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sureinf);
		
		
		final String SID =  getIntent().getExtras().getString("ID");
		final int ID=Integer.parseInt(SID);
		final String NoticeName =  getIntent().getExtras().getString("NoticeName");
		final String NoticeContent =  getIntent().getExtras().getString("NoticeContent");
		
		noticetitle = (TextView) findViewById(R.id.noticetitle);
		noticetitle.setText(NoticeContent);
		noticecontent = (TextView) findViewById(R.id.noticecontent);
		noticecontent.setText(NoticeName);
		sureinf_back = (ImageView) findViewById(R.id.sureinf_back);
		sureinf_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存�?
				Deleterelease.this.finish();
			}
		});
		
		//忽略消息
		falseinf = (Button) findViewById(R.id.falseinf);
		falseinf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存�?
				CallWebservice callWeb=new CallWebservice(myhandler);
				
				String url="Study.asmx";
				HashMap<String, Object> params = new HashMap<String, Object>();
				//ID�?			
				Log.i("被删除的", ""+ID);
				params.put("ID",ID);
				String methodName="DelCNotice";
				callWeb.doStart(url, methodName, params);
				Toast.makeText(Deleterelease.this, "好人有好�?感谢小伙伴热心的参与", 2000).show();
			}
		});
		
		//删除消息
		trueinf = (Button) findViewById(R.id.trueinf);
		trueinf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存�?
				CallWebservice callWeb=new CallWebservice(myhandler);
				String url="Study.asmx";
				HashMap<String, Object> params = new HashMap<String, Object>();
				//ID�?
				params.put("ID",ID);
				String methodName="DelCNotice";
				//callWeb.doStart(url, methodName, params);
			     if (!isGO) {  
			            isGO = true;  
			            Toast.makeText(getApplicationContext(), "再按�?��将会删除您发布的失物招领，请慎重哦！", Toast.LENGTH_LONG).show();  
			            mHandler.sendEmptyMessageDelayed(0, 2000);  
			        } else {  
			       
			            callWeb.doStart(url, methodName, params);
						Toast.makeText(Deleterelease.this, "好人有好�?感谢小伙伴热心的参与",Toast.LENGTH_SHORT).show();
			        }  
				//总表删除操作
//				CallWebservice callWeb2=new CallWebservice(myhandler);
//				String url2="Study.asmx";
//				HashMap<String, Object> params2 = new HashMap<String, Object>();
//				//ID�?
//				params.put("ID",ID);
//				String methodName2="DelCNotice";
//				callWeb2.doStart(url2, methodName2, params2);
			
			}
		});
	}
	
	/*
	 *�?��线程，显示发布是否成�?
	 */
		@SuppressLint("ShowToast")
		private  Handler myhandler=new Handler(){
			@Override
			public void handleMessage(Message m)
	        {
				Boolean isFalse = m.getData().getBoolean("isFalse");
				if(isFalse){
					Toast.makeText(Deleterelease.this, "处理成功", 2000).show();
					
					Deleterelease.this.finish();
					startActivity(new Intent(Deleterelease.this,User.class));
					//Intent intent=new Intent(DormRepair.this,MainActivity.class);
					//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
					//startActivity(intent);
					//DormRepair.this.finish();
		
				}else{
					Toast.makeText(Deleterelease.this, "处理失败了，检查一下网络吧", 2000).show();
					
				}
	        };
	      };
	      
	      
	      Handler mHandler = new Handler() {  
			  
	          @Override  
	          public void handleMessage(Message msg) {  
	              // TODO Auto-generated method stub  
	              super.handleMessage(msg);  
	              isGO = false;  
	          }  
	    
	      };  
	
	

}
