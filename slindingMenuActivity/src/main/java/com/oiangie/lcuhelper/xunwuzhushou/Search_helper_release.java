package com.oiangie.lcuhelper.xunwuzhushou;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;







import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.example.fragments.MainActivity;
import com.lcu.helper.main.Login;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Search_helper_release extends Activity {
	
	private EditText rel_name_et,rel_cont_et,rel_add_et;
	private ImageView backimbtn;
	private Button gobtn;
	private String address;
	private SharedPreferences sp;
	
	//时间选择
	private EditText showDate = null;
	private Button pickDate = null;
	private EditText showTime = null;
	private Button pickTime = null;
	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_TIMEPICK = 2;
	private static final int TIME_DIALOG_ID = 3;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private String releasename = "";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.search_helper_release);
	    
	    
	    rel_cont_et = (EditText) findViewById(R.id.rel_cont_et);
	    rel_name_et = (EditText) findViewById(R.id.rel_name_et);
	    //rel_pho_et = (EditText) findViewById(R.id.rel_pho_et);
	    rel_add_et = (EditText) findViewById(R.id.rel_add_et);
	    backimbtn=(ImageView)findViewById(R.id.serel_back);
	    backimbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//返回键
				Search_helper_release.this.finish();
				
			}
		});
	    
	    
	 /**
	  * 实现时间选择   
	  */
	    initializeViews();

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		setDateTime();
		setTimeOfDay();
		
		gobtn = (Button) findViewById(R.id.goser1);
		rel_add_et.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("onclick", "进入了");
				LocationApplication app = (LocationApplication)getApplicationContext();
				releasename = app.getusername();//取值
				View view = getLayoutInflater().inflate(R.layout.map_point_choose_dialog, null);
				final Dialog dialog = new Dialog(Search_helper_release.this, R.style.transparentFrameWindowStyle);
				dialog.setContentView(view, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				Button	mylocation = (Button) dialog.findViewById(R.id.map_point_but1);
				Button	choose = (Button) dialog.findViewById(R.id.map_point_but2);
				Button	cancle = (Button) dialog.findViewById(R.id.map_point_but3);
				Log.e("onclick", "寻找到控件");
				mylocation.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {	
					  rel_add_et.setText("我的位置");
					  dialog.dismiss();
					}						
				});
				choose.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent it=new Intent();
					    it.setClass(Search_helper_release.this, ShowPoint_search.class);
						it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						Bundle bundle = new Bundle();
						bundle.putInt("where", 1);
						ArrayList<String> data=new ArrayList<String>() ;
						data.add(rel_name_et.getText().toString());
						data.add(rel_cont_et.getText().toString());								
						//data.add(rel_pho_et.getText().toString());
						bundle.putStringArrayList("data", data);
						Log.e("地图选点", "已经传送好date数据");
						it.putExtras(bundle);
						Search_helper_release.this.startActivity(it);
						dialog.dismiss();
					}						
				});
				cancle.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
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
			}			 
		 });
		  gobtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			String str=rel_add_et.getText().toString();
			if(str.equals("我的位置")){
				sp = getSharedPreferences("example", Context.MODE_PRIVATE);
				String longitudeS =sp.getString("longitude", "0");
				String latitudeS =sp.getString("latitude", "0");
				Double longitude = Double.valueOf(longitudeS);
				Double latitude = Double.valueOf(latitudeS);
				String x=Double.toString(latitude);
				String y=Double.toString(longitude);
				address=x+","+y;
				if (address.equals("0.0,0.0")) {
					 Toast.makeText(getApplicationContext(), "无法定位，请检查网络", Toast.LENGTH_SHORT).show();
				}
			}
			
			
			String name = rel_name_et.getText().toString();
			String cont = rel_cont_et.getText().toString();
			//String pho = rel_pho_et.getText().toString();
			
			if(name.length()==0||cont.length()==0) 
	        { 
				Toast.makeText(Search_helper_release.this,"请完整输入所有信息",Toast.LENGTH_SHORT).show(); 
				return; 
	        }
			
			LocationApplication app = (LocationApplication) getApplicationContext();
			int ISin = app.getISin();
			if (ISin == 0) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Search_helper_release.this);
				builder.setMessage("您没有登录，是否登录？")
				       .setCancelable(false)
				       .setPositiveButton("是", new DialogInterface.OnClickListener() {
				           @Override
						public void onClick(DialogInterface dialog, int id) {
				        	   Intent it=new Intent();
								it.setClass(Search_helper_release.this, Login.class);
								it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
								Bundle bundle = new Bundle();
								bundle.putInt("value", 1);
								it.putExtras(bundle);
								Search_helper_release.this.startActivity(it);
				           }
				       })
				       .setNegativeButton("否", new DialogInterface.OnClickListener() {
				           @Override
						public void onClick(DialogInterface dialog, int id) {
				        	   Toast.makeText(Search_helper_release.this,"请您先登录，再提交信息",Toast.LENGTH_SHORT).show(); 
				               dialog.cancel();
				               Search_helper_release.this.finish();
				           }
				       });
				builder.show();
			}else if (ISin == 1) {
				//时间
				String date = showDate.getText().toString();
				String time = showTime.getText().toString();
				String SearchTime = date + "," + time;
				CallWebservice callWeb=new CallWebservice(myhandler);
				String url="Study.asmx";
				HashMap<String, Object> params = new HashMap<String, Object>();
				//遗失物品名称
				params.put("SearchName",name);
				//遗失物品时间
				params.put("SearchTime",SearchTime);
				//遗失物品内容
				params.put("SearchContent",cont);
				//联系方式
				//params.put("SearchPhone",pho);
				//遗失位置坐标（经纬度）
				params.put("SearchAddress",address);//address为null
				
				params.put("ReleaseName",releasename);
				String methodName="ReleaseSearch";
				callWeb.doStart(url, methodName, params);	
				
			}
			}
		});
	}
	/**
	 * 初始化控件和UI视图
	 */
	private void initializeViews() {
		showDate = (EditText) findViewById(R.id.showdate);
		pickDate = (Button) findViewById(R.id.pickdate);
		showTime = (EditText) findViewById(R.id.showtime);
		pickTime = (Button) findViewById(R.id.picktime);
		
		  
		pickDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickDate.equals(v)) {
					msg.what = Search_helper_release.SHOW_DATAPICK;
				}
				Search_helper_release.this.dateandtimeHandler.sendMessage(msg);
			}
		});

		pickTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickTime.equals(v)) {
					msg.what = Search_helper_release.SHOW_TIMEPICK;
				}
				Search_helper_release.this.dateandtimeHandler.sendMessage(msg);
			}
		});
	}
	/**
	 * 设置日期
	 */
	private void setDateTime() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDateDisplay();
	}
	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		showDate.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}
	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay();
		}
	};
	/**
	 * 设置时间
	 */
	private void setTimeOfDay() {

		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTimeDisplay();
	}
	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay() {
		showTime.setText(new StringBuilder().append(mHour).append(":")
				.append((mMinute < 10) ? "0" + mMinute : mMinute));
	}
	/**
	 * 时间控件事件
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			updateTimeDisplay();
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					true);
		}

		return null;
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		case TIME_DIALOG_ID:
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
			break;
		}
	}
	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Search_helper_release.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			case Search_helper_release.SHOW_TIMEPICK:
				showDialog(TIME_DIALOG_ID);
				break;
			}
		}

	};
	
	
	/*
	 *开始线程，显示发布是否成功
	 */
		@SuppressLint("ShowToast")
		private Handler myhandler=new Handler(){
			@Override
			public void handleMessage(Message m)
	        {
				Boolean isFalse = m.getData().getBoolean("isFalse");
				if(isFalse){
					Toast.makeText(Search_helper_release.this, "发布成功", 2000).show();
					Intent intent=new Intent(Search_helper_release.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					Search_helper_release.this.finish();
		
				}else{
					Toast.makeText(Search_helper_release.this, "发布失败", 2000).show();
				}
	        };
	      };
	      
	      
			@Override
			protected void onNewIntent(Intent intent){
				super.onNewIntent(intent);
				setIntent(intent);
				Log.e("demo", "start onnewintent~~~"); 
		    Bundle bundle=getIntent().getExtras();
		    try {
		    	address=bundle.getString("address");
		    	
			    if(!address.equals("")){
					ArrayList<String> data=getIntent().getExtras().getStringArrayList("data");
					rel_name_et.setText(data.get(0).toString());
					rel_cont_et.setText(data.get(1).toString());
					rel_add_et.setText("地图上的点");
				}
		    } catch (Exception e) {
			   // TODO: handle exception
		    	Log.e("demo", "暂时没有address值");
		    } 
				
			}
			

}

