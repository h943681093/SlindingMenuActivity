package com.oiangie.lcuhelper.xunwuzhushou;

import java.util.Calendar;
import java.util.HashMap;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.example.fragments.MainActivity;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class loser_release extends Activity {
private  EditText rel_pho_et,rel_cont_et;
private  Button gButton;
private String losername = "";


//答辩时使用
private Button tostr;


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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.loser_release);
		
		
		
		
		//答辩时使用
		tostr = (Button) findViewById(R.id.tostr);
		tostr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				rel_pho_et.setText("18866579371");
				rel_cont_et.setText("黑色的adidas钱包 里面有我的尾号为660的农行银行卡 还有我的身份证我叫霍一心 ");
			}
		});
		
		
		rel_pho_et = (EditText) findViewById(R.id.rel_pho_et);
		rel_cont_et = (EditText) findViewById(R.id.rel_cont_et);
		gButton = (Button) findViewById(R.id.gbutton);
		Intent intent = getIntent();
		final String releasename = intent.getExtras().getString("releasename");
		final String contents = intent.getExtras().getString("contents");
		
		gButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if(rel_pho_et.length()==0||rel_cont_et.length()==0) 
		        { 
					Toast.makeText(loser_release.this,"请完整输入所有信息",Toast.LENGTH_SHORT).show(); 
					return; 
		        }
				
				//丢失的时间
				String date = showDate.getText().toString();
				String time = showTime.getText().toString();
				String LoseTime = date + "," + time;
				
				//丢失者电话
				String phone = rel_pho_et.getText().toString();
				
				//丢失者描述
				String content = rel_cont_et.getText().toString();
				
				//丢失者用户名 
				LocationApplication app = (LocationApplication)getApplicationContext();
				losername = app.getusername();//取值
				
				
				
				String NoticeTitle = losername+"向您提交认领申请";
				
				String NoticeContent = losername+"在"+LoseTime+"丢失类似物品。"+"具体细节为："+content+"如相符请尽快联系我，他的联系方式为："+phone;
				
				
				CallWebservice callWeb=new CallWebservice(myhandler);
				String url="Study.asmx";
				HashMap<String, Object> params = new HashMap<String, Object>();
				//读取者用户名
				params.put("UserID",releasename);
				Log.i("发布招领者用户名",releasename);
				//通知标题
				params.put("NoticeName",NoticeTitle);
				Log.i("标题",NoticeTitle);
				//通知内容
				params.put("NoticeContent",NoticeContent);
				Log.i("内容",NoticeContent);
				
				String methodName="PutNotice";
				
				callWeb.doStart(url, methodName, params);	
				
				
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
					msg.what = loser_release.SHOW_DATAPICK;
				}
				loser_release.this.dateandtimeHandler.sendMessage(msg);
			}
		});

		pickTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickTime.equals(v)) {
					msg.what = loser_release.SHOW_TIMEPICK;
				}
				loser_release.this.dateandtimeHandler.sendMessage(msg);
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
			case loser_release.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			case loser_release.SHOW_TIMEPICK:
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
					Toast.makeText(loser_release.this, "你的消息我们已经加急送往目的地喽", 2000).show();
					Intent intent=new Intent(loser_release.this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					loser_release.this.finish();
		
				}else{
					Toast.makeText(loser_release.this, "发布失败", 2000).show();
				}
	        };
	      };
			
}
