package com.oiangie.lcuhelper.xunwuzhushou;


import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.example.fragments.MainActivity;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Search_query extends Activity{
	 
	private EditText et;
	private ImageView quetry,back;
	private Button b1,b2,b3,b4,b5,b6,b7,b8,search_quertBtn;
	private Integer[] ids=CommUtil.ids;//点图标图片
	public String stringSname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 		
		this.setContentView(R.layout.search_query);
		
	b1=(Button)findViewById(ids[0]);
	b2=(Button)findViewById(ids[1]);
	b3=(Button)findViewById(ids[2]);
	b4=(Button)findViewById(ids[3]);
	b5=(Button)findViewById(ids[4]);
	b6=(Button)findViewById(ids[5]);
	b7=(Button)findViewById(ids[6]);
	b8=(Button)findViewById(ids[7]);
	b1.setOnClickListener(l);
	b2.setOnClickListener(l);
	b3.setOnClickListener(l);
	b4.setOnClickListener(l);
	b5.setOnClickListener(l);
	b6.setOnClickListener(l);
	b7.setOnClickListener(l);
	b8.setOnClickListener(l);
	et=(EditText)findViewById(R.id.query_edittext);
	quetry=(ImageView) findViewById(R.id.query_query);	
	quetry.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str=et.getText().toString();
			setStr(str,false);
		}	
	});
	//返回键功能
	back=(ImageView) findViewById(R.id.query_back);	
	back.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent(Search_query.this,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//如果不传值就不要加flags
			Bundle bundle = new Bundle();
			bundle.putInt("way", 0);
			intent.putExtras(bundle);
			Search_query.this.startActivity(intent);
			Search_query.this.finish();
		}		
	});
	
	
	
	//高级搜索功能
	search_quertBtn = (Button) findViewById(R.id.search_query_gaoji);
	search_quertBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			String str=et.getText().toString();
			if (str.length() == 0) {
				Toast.makeText(Search_query.this, "请输入丢失物品的关键字", Toast.LENGTH_SHORT).show();
				return;
			}
			Log.e("搜索的词", str);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("searchstr", str);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(bundle);
            intent.setClass(Search_query.this, Gaoji_Search_query.class);
            startActivity(intent);
		}
	});
	}
	
	
	
	private OnClickListener l = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button button=(Button) v;
			String str=button.getText().toString();
			setStr(str,true);
		}
	};
	
	
	private void setStr(String str,Boolean isClass)
	{
		if (str.length() == 0) {
			Toast.makeText(this, "请输入丢失物品的关键字", Toast.LENGTH_SHORT).show();
			return;
		}
		LocationApplication app = (LocationApplication) getApplicationContext();
		app.setSearchHelperName(str);
		app.setSearchHelperWay(1);
		//Intent intent=new Intent(Search_query.this,MainActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
		//startActivity(intent);
		Search_query.this.finish();
	}

}