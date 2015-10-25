package com.lcu.helper.ruxuedaohang;

import com.jeremyfeinstein.slidingmenu.example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ruxuedaohang extends Activity {
	private ImageView newsnotice_back2;
	private ImageView step_1,step_2,step_3,step_4,step_5,step_6;
	private TextView wstep_1,wstep_2,wstep_3,wstep_4,wstep_5,wstep_6;
	private TextView wstep2_1,wstep2_2,wstep2_3,wstep2_4,wstep2_5,wstep2_6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		newsnotice_back2 = (ImageView) findViewById(R.id.Activity_back);
		newsnotice_back2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Log.i("点击返回", "结束");
				ruxuedaohang.this.finish();
			}
		
		});
	  	
		step_1 = (ImageView) findViewById(R.id.step_1);
		step_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_1.class));
			}
		});
		
		step_2 = (ImageView) findViewById(R.id.step_2);
		step_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_2.class));

			}
		});
		
		step_3 = (ImageView) findViewById(R.id.step_3);
		step_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_3.class));

			}
		});
		
		step_4 = (ImageView) findViewById(R.id.step_4);
		step_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_4.class));

			}
		});
		
		step_5 = (ImageView) findViewById(R.id.step_5);
		step_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_5.class));

			}
		});
		
		step_6 = (ImageView) findViewById(R.id.step_6);
		step_6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_6.class));

			}
		});	
		
		
		
		wstep_1 = (TextView) findViewById(R.id.wstep_1);
		wstep_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_1.class));
			}
		});
		
		wstep_2 = (TextView) findViewById(R.id.wstep_2);
		wstep_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_2.class));

			}
		});
		
		wstep_3 = (TextView) findViewById(R.id.wstep_3);
		wstep_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_3.class));

			}
		});
		
		wstep_4 = (TextView) findViewById(R.id.wstep_4);
		wstep_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_4.class));

			}
		});
		
		wstep_5 = (TextView) findViewById(R.id.wstep_5);
		wstep_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_5.class));

			}
		});
		
		wstep_6 = (TextView) findViewById(R.id.wstep_6);
		wstep_6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_6.class));

			}
		});		
		
		
		wstep2_1 = (TextView) findViewById(R.id.wstep2_1);
		wstep2_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_1.class));
			}
		});
		
		wstep2_2 = (TextView) findViewById(R.id.wstep2_2);
		wstep2_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_2.class));

			}
		});
		
		wstep2_3 = (TextView) findViewById(R.id.wstep2_3);
		wstep2_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_3.class));

			}
		});
		
		wstep2_4 = (TextView) findViewById(R.id.wstep2_4);
		wstep2_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_4.class));

			}
		});
		
		wstep2_5 = (TextView) findViewById(R.id.wstep2_5);
		wstep2_5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_5.class));

			}
		});
		
		wstep2_6 = (TextView) findViewById(R.id.wstep2_6);
		wstep2_6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(ruxuedaohang.this,Step_6.class));

			}
		});		
		
		
	}
    
}
