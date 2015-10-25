package com.oiangie.lcuhelper.JavaTool;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.oiangie.lcuhelper.JavaTool.CommUtil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

/**
 * 主Application
 */
public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public TextView logMsg;
	public String mLocationResult;
	public TextView trigger,exit;
	public Vibrator mVibrator;
	public String mLocationx,mLocationy;
	public String SearchHelperName;
	public String username = "请登录";
	public String userurl = CommUtil.imageurl+"1.jpg";
	public int SearchHelperWay;
	public int ISin = 0;
	public int onNewIntentway = 0;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	public int DoShowMappoint,ShowMappointWay;
	public String StringSearchName,locationAddress;
	public ArrayList<String> ArraySearchName;
	public HashMap<String, Object> showpointerwei;
	public int librarydo;
	//查询的方法变量
	@Override
	public void onCreate() {
		super.onCreate();
	    FrontiaApplication.initFrontiaApplication(getApplicationContext());
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
	}
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Double x = location.getLatitude();
			Double y = location.getLongitude();
			String xString = Double.toString(x);
			String yString = Double.toString(y);
			String str = xString+","+ yString;
			if (location.getLocType() == BDLocation.TypeGpsLocation){
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			}
			logMsg(str,xString,yString);
		}
	}
	/**
	 * 显示请求字符串
	 * @param str
	 */
	public void logMsg(String str,String x,String y) {
		try {
			if (mLocationResult != null)
				mLocationResult = str;
				mLocationx= x;
				Log.i("点数据测试", ""+x);
				mLocationy= y;
				Log.i("点数据测试", ""+y);
				//存储到文件共享里面
				sp = getSharedPreferences("location", Context.MODE_PRIVATE);
				editor = sp.edit();
				editor.putString("longitude",mLocationx);
				editor.putString("latitude",mLocationy);
				editor.commit();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  
	 * 是否登陆 	getISin
	 * 			getISin
	 * 
	 * @param str
	 */	
	
	public int getISin() {
		return ISin;
	}
	public void setISin(int ISin) {
		this.ISin = ISin;
		Log.e("全局变量", ""+ISin);
	}
	
	/**
	 *  
	 * 获取登陆名 	getusername
	 * 			setusername
	 * 
	 * @param str
	 */	
		
	public String getusername() {
		return username;
		}
	public void setusername(String username) {
		this.username = username;
		Log.e("全局变量username", username);
		}
	
	
	/**
	 *  
	 * 传递头像url	getuserurl
	 * 				setuserurl
	 * 
	 * @param str
	 */	

	public String getuserurl() {
		return userurl;
		}
	public void setuserurl(String userurl) {
		this.userurl = userurl;
		Log.e("全局变量userurl", userurl);
		}

	
	/**
	 * 设置查找丢失物品的全局变量  
	 * 要查找物品的名称 	getSearchHelperName
	 * 				setSearchHelperName
	 * 区分要查找物品方法
	 * 				getSearchHelperWay
	 * 				setSearchHelperWay
	 * @param str
	 */	

	public String getSearchHelperName() {
	return SearchHelperName;
	}


	public void setSearchHelperName(String SearchHelperName) {
	this.SearchHelperName = SearchHelperName;
	Log.e("全局变量", SearchHelperName);
	}
	

	public int getSearchHelperWay() {
	return SearchHelperWay;
	}


	public void setSearchHelperWay(int SearchHelperWay) {
	this.SearchHelperWay = SearchHelperWay;
	Log.e("全局变量", SearchHelperName);
	}
	
	/**
	 * 设置查找地点的全局变量  
	 * 
	 *  获得应用程序实例的方法
		LocationApplication app = (LocationApplication) getApplicationContext();
		
	 * 要查找物品的名称 	getQueryName
	 * 				setQueryName
	 * 区分要查找物品方法
	 * 				getQueryWay
	 * 				setQueryWay
	 * @param str
	 */	

	
	
	public int getShowMappointWay() {
	return ShowMappointWay;
	}
	public void setShowMappointWay(int ShowMappointWay) {
	this.ShowMappointWay = ShowMappointWay;
	Log.e("全局变量ShowMappointWay", ""+ShowMappointWay);
	}
	
	//way==1 存储得到的是StringSearchName
	public String getStringSearchName() {
	return StringSearchName;
	}
	public void setStringSearchName(String StringSearchName) {
	this.StringSearchName = StringSearchName;
	Log.e("全局变量StringSearchName", ""+StringSearchName);
	}
	
	
	//way==2储存得到的是Arraylist<String>
	
	public HashMap<String, Object> getshowpointerwei() {
		return showpointerwei;
		}
	public void setshowpointerwei(HashMap<String, Object> showpointerwei) {
		this.showpointerwei = showpointerwei;
		Log.e("全局变量showpointerwei", ""+showpointerwei);
		}
	
	/*
	 * 确定返回MainActivity后启动OnNewIntent方法 进行的是什么操作
	 * onNewIntentway初始值=0：什么也不做
	 * 如果onNewIntentway=1：通过数据集中点的名字显示单个点
	 * 如果onNewIntentway=2：通过数据集中点的名字形成的数组显示多个点
	 * 如果onNewIntentway=3：通过获得的坐标显示单个点
	 * 如果onNewIntentway=4：
	 */
	
	public int getonNewIntentway() {
		return onNewIntentway;
		}
	public void setonNewIntentway(int onNewIntentway) {
		this.onNewIntentway = onNewIntentway;
		Log.e("全局变量onNewIntentway", ""+onNewIntentway);
		}
	
	
	//寻物助手（通过坐标显示点用）
	public String getlocationAddress() {
		return locationAddress;
		}
	public void setlocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
		Log.e("全局变量locationAddress", locationAddress);
		}
	
	//二维码的显示
	public ArrayList<String> getArraySearchName() {
		return ArraySearchName;
		}
	public void setArraySearchName(ArrayList<String> ArraySearchName) {
		this.ArraySearchName = ArraySearchName;
		Log.e("全局变量ArraySearchName", ""+ArraySearchName);
		}
	
	//是否来自二维码
	public int getlibrarydo() {
		return librarydo;
		}
	public void setlibrarydo(int librarydo) {
		this.librarydo = librarydo;
		Log.e("全局变量librarydo", ""+librarydo);
		}
	
	
}

