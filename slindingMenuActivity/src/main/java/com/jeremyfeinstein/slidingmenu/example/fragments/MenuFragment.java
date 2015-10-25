package com.jeremyfeinstein.slidingmenu.example.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.liaodajianjie.liaodajianjie;
import com.lcu.helper.main.Login;
import com.lcu.helper.main.SchoolMap;
import com.lcu.helper.main.User;
import com.lcu.helper.ruxuedaohang.ruxuedaohang;
import com.lcu.helper.xinshenggonglue.xueshenggonglue;
import com.lcu.xueshengzuzhi.xueshnegzuzhi;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.ImageDownloader;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;
import com.oiangie.lcuhelper.JavaTool.OnImageDownload;
import com.oiangie.lcuhelper.xunwuzhushou.SearchHelper;

public class MenuFragment extends Fragment {
	private ImageView login,oneImageView,twoImageView,threeImageView,fourImageView,fiveImageView,sixImageView;
	private String Username;
	private String Userurl = CommUtil.imageurl + "1.jpg";
	private ImageDownloader mDownloader;
	private TextView user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.layout_menu, null);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		user = (TextView) getActivity().findViewById(R.id.mtv1);
		
		login = (ImageView) getActivity().findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				login.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						LocationApplication app = (LocationApplication)getActivity().getApplicationContext();
						int ISin = app.getISin();
						if (ISin==1) {
							//进入个人中心界面
							startActivity(new Intent(getActivity(),User.class));
							
						} else {
							//进入登陆界面
							//传入来源值 value =0 
							Intent it=new Intent();
							it.setClass(getActivity(), Login.class);
							it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
							Bundle bundle = new Bundle();
							bundle.putInt("value", 0);
							it.putExtras(bundle);
							getActivity().startActivity(it);
						}
					}
				});
				
			}
		});
		oneImageView = (ImageView) getActivity().findViewById(R.id.one);
		oneImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(),SchoolMap.class));
			}
		});
		twoImageView = (ImageView) getActivity().findViewById(R.id.two);
		twoImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(), ruxuedaohang.class));
				
			}
		});
		threeImageView = (ImageView) getActivity().findViewById(R.id.three);
		threeImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(),liaodajianjie.class));
				
			}
		});
		fourImageView = (ImageView) getActivity().findViewById(R.id.four);
		fourImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(),xueshnegzuzhi.class));
				
			}
		});
		fiveImageView = (ImageView) getActivity().findViewById(R.id.five);
		fiveImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(),xueshenggonglue.class));
				
			}
		});
		
		sixImageView = (ImageView) getActivity().findViewById(R.id.six);
		sixImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(getActivity(),SearchHelper.class));
				
			}
		});
		
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.d(this.getTag(), "onStart");
		// Fragment对象在ui可见时调用。
		LocationApplication app = (LocationApplication)getActivity().getApplicationContext();
		Username = app.getusername();
		
		
		
		user.setText(Username);
		
		Userurl = app.getuserurl();
		
		Log.e("url", "url"+Userurl);
		
		
		login.setTag(Userurl);
		if (mDownloader == null) {mDownloader = new ImageDownloader();}
				
		login.setImageResource(R.drawable.admin);
				
		mDownloader.imageDownload(Userurl, login, "/lcu",getActivity(), new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
					
				ImageView imageView =(ImageView)login.findViewWithTag(c_url);
						
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});	

		super.onStart();
	}
}
