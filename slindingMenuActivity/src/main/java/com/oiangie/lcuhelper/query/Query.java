package com.oiangie.lcuhelper.query;



import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.SchoolMap;
import com.oiangie.lcuhelper.JavaTool.CommUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Query extends Activity{
	 
	private EditText et;
	private ImageView quetry,back;
	private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
	private Integer[] ids=CommUtil.ids;//点图标图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 		
		this.setContentView(R.layout.query);
		
	b1=(Button)findViewById(ids[0]);
	b2=(Button)findViewById(ids[1]);
	b3=(Button)findViewById(ids[2]);
	b4=(Button)findViewById(ids[3]);
	b5=(Button)findViewById(ids[4]);
	b6=(Button)findViewById(ids[5]);
	b7=(Button)findViewById(ids[6]);
	b8=(Button)findViewById(ids[7]);
	b9=(Button)findViewById(ids[8]);
	b10=(Button)findViewById(ids[9]);
	b11=(Button)findViewById(ids[10]);
	b12=(Button)findViewById(ids[11]);
	b1.setOnClickListener(l);
	b9.setOnClickListener(l);
	b2.setOnClickListener(l);
	b10.setOnClickListener(l);
	b3.setOnClickListener(l);
	b11.setOnClickListener(l);
	b4.setOnClickListener(l);
	b12.setOnClickListener(l);
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
			Intent intent =new Intent(Query.this,SchoolMap.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//如果不传值就不要加flags
			Bundle bundle = new Bundle();
			bundle.putInt("way", 0);
			intent.putExtras(bundle);
			Query.this.startActivity(intent);
			Query.this.finish();
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
			Toast.makeText(this, "请输入名称", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent=new Intent(Query.this,QueryResult.class);
		Bundle bundle=new Bundle();
		bundle.putString("title", str);
		bundle.putBoolean("isEdit", true);
		bundle.putBoolean("isClass", isClass);
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

}
