package com.lcu.helper.liaodajianjie;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.query.QueryResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class liaodajianjie extends Activity {
private Button xiaohui,xiaoge,xiaoxun,jiaofeng,xuexiaojieshao,xianrenlingdao,lishidiyi,liaodajingshen;
private ImageView jianjie_back;
//private EditText search_search;
//private ImageButton search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.f);
		
		
//		search_search = (EditText) findViewById(R.id.search_search);
//		search = (ImageButton) findViewById(R.id.search);
//		search.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO 自动生成的方法存根
//				
//				String str = search_search.getText().toString();
//				
//				if (str.length() == 0) {
//					Toast.makeText(liaodajianjie.this, "请输入要查找的关键字", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				Intent intent=new Intent(liaodajianjie.this,QueryResult.class);
//				Bundle bundle=new Bundle();
//				bundle.putString("title", str);
//				bundle.putBoolean("isEdit", true);
//				bundle.putBoolean("isClass", true);
//				intent.putExtras(bundle);
//				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
//						| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(intent);
//				
//				
//				
//			}
//		});
		
		
		
		
		
		
		jianjie_back = (ImageView) findViewById(R.id.jj_back1);
		jianjie_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				liaodajianjie.this.finish();
			}
		});
		
		xiaohui = (Button) findViewById(R.id.bl1);
		xiaohui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,xiaohui.class));
			}
		});
		xiaoge = (Button) findViewById(R.id.bl2);
		xiaoge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,xiaoge.class));
			}
		});
		xiaoxun = (Button) findViewById(R.id.bl3);
		xiaoxun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,xiaoxun.class));
			}
		});
		jiaofeng = (Button) findViewById(R.id.bl4);
		jiaofeng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,jiaofeng.class));
			}
		});
		xuexiaojieshao = (Button) findViewById(R.id.tbl1);
		xuexiaojieshao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,xuexiaojieshao.class));
			}
		});
		xianrenlingdao = (Button) findViewById(R.id.tbl3);
		xianrenlingdao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,xianrenlingdao.class));
			}
		});
		lishidiyi = (Button) findViewById(R.id.tbl4);
		lishidiyi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,lishidiyi.class));
			}
		});
		liaodajingshen = (Button) findViewById(R.id.tbl2);
		liaodajingshen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(liaodajianjie.this,liaodajingshen.class));
			}
		});
		
		
	}

	
}
