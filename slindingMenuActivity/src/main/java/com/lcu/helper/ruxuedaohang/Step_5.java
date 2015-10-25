package com.lcu.helper.ruxuedaohang;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.query.QueryResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Step_5 extends Activity {
	private ImageView Activity_back;
	private Button brpoit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.br);
		
		Activity_back = (ImageView) findViewById(R.id.Activity_back);
		Activity_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Step_5.this.finish();
			}
		});
		
		brpoit = (Button) findViewById(R.id.brpoit);
		brpoit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				Intent intent=new Intent(Step_5.this,QueryResult.class);
				Bundle bundle=new Bundle();
				bundle.putString("title", "领取被褥");
				bundle.putBoolean("isEdit", true);
				bundle.putBoolean("isClass", true);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
						| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				
			}
		});
		
		
	}
}
