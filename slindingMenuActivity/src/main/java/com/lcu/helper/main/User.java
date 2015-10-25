package com.lcu.helper.main;

import com.baidu.demo.Conf;
import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.ImageDownloader;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
import com.oiangie.lcuhelper.JavaTool.OnImageDownload;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class User extends Activity {

	private Button exitButton;
	private ImageView backView;
	private FrontiaAuthorization mAuthorization;
	private String Username;
	private String Userurl = CommUtil.imageurl + "1.jpg";
	private TextView user;
	private ImageView userlogo,activity,search,course;
	private ImageDownloader mDownloader;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);
		
		
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		mAuthorization = Frontia.getAuthorization();
		
		//宿舍报修
		activity = (ImageView) findViewById(R.id.activity);
		activity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//startActivity(new Intent(User.this,Activity_release.class));
			}
		});
		//失物招领
		search = (ImageView) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//startActivity(new Intent(User.this,Search_helper_release.class));
			}
		});
		
		backView = (ImageView) findViewById(R.id.center_back);
		backView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				User.this.finish();
			}
		});
		
		
		exitButton = (Button) findViewById(R.id.logout);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				tuichuLogin();
				User.this.finish();
				
			}
		});
		
		
		course = (ImageView) findViewById(R.id.course_go);
		course.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//startActivity(new Intent(User.this,Course_release.class));
			}
		});
		
		
		LocationApplication app = (LocationApplication)getApplicationContext();
		Username = app.getusername();
		user= (TextView) findViewById(R.id.tvce2);
		user.setText(Username);
		
		Userurl = app.getuserurl();
		
		Log.e("url", "url"+Userurl);
		userlogo = (ImageView) findViewById(R.id.imageView1);
		
		userlogo.setTag(Userurl);
		if (mDownloader == null) {mDownloader = new ImageDownloader();}
				
		userlogo.setImageResource(R.drawable.admin);
				
		mDownloader.imageDownload(Userurl, userlogo, "/lcu",User.this, new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
					
				ImageView imageView =(ImageView)userlogo.findViewWithTag(c_url);
						
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});	

		super.onStart();
	}
	
	
	
	
	 public void tuichuLogin(){
	    	boolean result = mAuthorization.clearAllAuthorizationInfos();
			if (result) {
				Log.e("第三方登陆", "退出成功");
			} else {
				Log.e("第三方登陆", "退出失败");
			}
			LocationApplication app = (LocationApplication) getApplicationContext();
			app.setISin(0);
			app.setusername("请登录");
			app.setuserurl(CommUtil.imageurl + "1.jpg");
	    }
}
