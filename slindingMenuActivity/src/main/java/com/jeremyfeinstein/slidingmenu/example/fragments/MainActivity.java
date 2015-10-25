package com.jeremyfeinstein.slidingmenu.example.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.frontia.api.FrontiaAuthorization;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

public class MainActivity extends SlidingFragmentActivity {

	private Fragment mContent;
	private Button menuButton1;
	private Button menuButton2;
	private Button menuButton3;
	boolean isExit;
	private int doshow = 0;
	private FrontiaAuthorization mAuthorization;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_main);

		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// set the Above View Fragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new ContentFragment();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

		// set the Behind View Fragment
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeEnabled(false);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});

		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});

	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	
	//菜单键事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
			
			View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
			
			final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
			dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT)); 
			
			
			menuButton1 = (Button) dialog.findViewById(R.id.menubut1);
			menuButton1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Log.e("dialog菜单键", "menuButton1");
					LocationApplication app = (LocationApplication) getApplicationContext();
					app.setISin(0);
					app.setusername("请登录");
					//退出第三方账号
					boolean result = mAuthorization.clearAllAuthorizationInfos();
					if (result) {
						Toast.makeText(MainActivity.this, "退出已登陆账号",Toast.LENGTH_SHORT).show();
					} else {
						
					}
					dialog.dismiss();
				}
			});
			menuButton2 = (Button) dialog.findViewById(R.id.menubut2);
			menuButton2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Log.e("dialog菜单键", "menuButton2");
					tuichuLogin();
					System.exit(0);
					dialog.dismiss();
				}
			});
			menuButton3 = (Button) dialog.findViewById(R.id.menubut3);
			menuButton3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Log.e("dialog菜单键", "menuButton3");
					dialog.dismiss();
				}
			});
			Window window = dialog.getWindow();
			window.setWindowAnimations(R.style.main_menu_animstyle);
			WindowManager.LayoutParams wl = window.getAttributes();
			wl.x = 0;
			wl.y = getWindowManager().getDefaultDisplay().getHeight();
			wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
			wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

			dialog.onWindowAttributesChanged(wl);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		} else if(keyCode == KeyEvent.KEYCODE_BACK){
			exit();  
            return false; 
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	public void exit(){  
        if (!isExit) {  
            isExit = true;  
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();  
            mHandler.sendEmptyMessageDelayed(0, 2000);  
        } else {  
            Intent intent = new Intent(Intent.ACTION_MAIN);  
            intent.addCategory(Intent.CATEGORY_HOME);  
            startActivity(intent); 
            tuichuLogin();
            System.exit(0);  
        }  
    }  
	Handler mHandler = new Handler() {  
		  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub  
            super.handleMessage(msg);  
            isExit = false;  
        }  
  
    };  
	
	
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
