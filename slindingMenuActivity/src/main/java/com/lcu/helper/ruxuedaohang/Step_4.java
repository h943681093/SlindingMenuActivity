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

public class Step_4 extends Activity {
	private ImageView Activity_back;
	private Button fypoit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fy);
		
		Activity_back = (ImageView) findViewById(R.id.Activity_back);
		Activity_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Step_4.this.finish();
			}
		});
		
		fypoit = (Button) findViewById(R.id.fypoit);
		fypoit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				
				Intent intent=new Intent(Step_4.this,QueryResult.class);
				Bundle bundle=new Bundle();
				bundle.putString("title", "���Ѵ�");
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
