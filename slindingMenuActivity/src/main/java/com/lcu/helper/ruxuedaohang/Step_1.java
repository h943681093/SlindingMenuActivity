package com.lcu.helper.ruxuedaohang;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.liaodajianjie.liaodajianjie;
import com.lcu.helper.xinshenggonglue.xueshenggonglue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Step_1 extends Activity {
private Button in_jianjie,in_gonglue;
private ImageView Activity_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.rx);

		Activity_back = (ImageView) findViewById(R.id.Activity_back);
		Activity_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Step_1.this.finish();
			}
		});
		
		in_jianjie = (Button) findViewById(R.id.in_jianjie);
		in_jianjie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(Step_1.this,liaodajianjie.class));
			}
		});
		in_gonglue = (Button) findViewById(R.id.in_gonglue);
		in_gonglue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(Step_1.this,xueshenggonglue.class));
			}
		});
	}

}
