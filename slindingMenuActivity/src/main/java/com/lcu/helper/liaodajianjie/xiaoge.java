package com.lcu.helper.liaodajianjie;

import com.jeremyfeinstein.slidingmenu.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class xiaoge extends Activity {
	private ImageView Activity_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xg);
		Activity_back = (ImageView) findViewById(R.id.Activity_back);
		Activity_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				xiaoge.this.finish();
			}
		});
	}
	
}
