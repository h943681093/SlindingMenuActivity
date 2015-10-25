package com.lcu.helper.main;

import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.example.fragments.MainActivity;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

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
import android.widget.EditText;
import android.widget.Toast;

public class Register_User extends Activity {

		private EditText xuehao,password,password2,name;
		private Button register_complete;
		private String user_name;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_userinfo);
		xuehao = (EditText) findViewById(R.id.xuehao);
		password = (EditText) findViewById(R.id.password);
		password2 = (EditText) findViewById(R.id.password2);
		name = (EditText) findViewById(R.id.name);
		register_complete = (Button) findViewById(R.id.register_complete);
		
		register_complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				doThread(); 
				Log.e("开始注册", "开始");
			}
		});
		
	}
		
	
	public void doThread() {
		String user_pwd = password.getText().toString().trim();
		String user_repwd = password2.getText().toString().trim();
		String student_id=xuehao.getText().toString().trim();
		user_name=name.getText().toString().trim();
		if(user_pwd.equals("")||student_id.equals("")||user_repwd.equals("")||user_name.equals("")) 
        { 
			Toast.makeText(this,"请完整输入所有信息",Toast.LENGTH_SHORT).show(); 
            return; 
        }

		if(!user_repwd.equals(user_pwd)){
			
			Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            return; 
		}
		 String url = "Service1.asmx";
		 String methodName="InUserRegistion";
		 HashMap<String, Object> params = new HashMap<String, Object>();
		 params.put("StudentID",student_id);
		 params.put("UserPassword",user_pwd);		
		 params.put("UserName",user_name);
		 CallWebservice callWeb=new CallWebservice(handler);
		 callWeb.doStart(url, methodName, params);
		 
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		 
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler()
    {
        @Override
		public void handleMessage(Message m)
        {
        	 boolean datas = m.getData().getBoolean("isFalse");      	 
        	 register(datas);        	
        };        
    };
    
    
    
    private void register( boolean datas){
    if(datas){
    	Toast.makeText(this,"注册成功，欢迎您",Toast.LENGTH_SHORT).show();
    	
    	startActivity(new Intent(Register_User.this,MainActivity.class));
    	LocationApplication app = (LocationApplication) getApplicationContext();
    	app.setISin(1);
    	app.setusername(user_name);
		}
    else{
    	Toast.makeText(this,"注册失败,此号已注册",Toast.LENGTH_SHORT).show();
    }
    }
		
		
}
