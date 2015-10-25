package com.jeremyfeinstein.slidingmenu.example.fragments;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.ImageDownloader;
import com.oiangie.lcuhelper.JavaTool.OnImageDownload;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class zhuye_in extends Activity {

	private ImageButton mapin;
	private ImageView newstIvin,newsnotice_back;
	private TextView newsactNamein,newsactAbstractin,newsactSourcein,newsactTimein;
	private ImageView news_biaoqain;
	private ImageDownloader mDownloader;
	private String url;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_itemin);
		
		newstIvin = (ImageView) findViewById(R.id.newstIvin);
		mapin = (ImageButton) findViewById(R.id.newsorgDepart_mapin);
		newsactNamein = (TextView) findViewById(R.id.newsactNamein);
		newsactAbstractin= (TextView) findViewById(R.id.newsactAbstractin);
		newsactSourcein= (TextView) findViewById(R.id.newsactSourcein);
		newsactTimein= (TextView) findViewById(R.id.newsactTimein);
		newsnotice_back = (ImageView) findViewById(R.id.newsnotice_back);
		
		final String NewsName =  getIntent().getExtras().getString("NewsName");
		final String NewsTime =  getIntent().getExtras().getString("NewsTime");
		final String NewsAbs =  getIntent().getExtras().getString("NewsAbs");
		final String NewsContent =  getIntent().getExtras().getString("NewsContent");
		final String NewsAddress =  getIntent().getExtras().getString("NewsAddress");
		final String NewsSource =  getIntent().getExtras().getString("NewsSource");
		final String NewsImages =  getIntent().getExtras().getString("NewsImages");
		final String NewsClass =  getIntent().getExtras().getString("NewsClass");
		
		newsactNamein.setText(NewsName);
		newsactAbstractin.setText(NewsContent);
		newsactSourcein.setText(NewsSource);
		newsactTimein.setText(NewsTime);
		
		news_biaoqain = (ImageView) findViewById(R.id.news_bqin);
		if (NewsClass.indexOf("1")!=-1) {
			//精华	
			news_biaoqain.setImageDrawable(getResources().getDrawable(R.drawable.news_jh_f));
		}else if (NewsClass.indexOf("2")!=-1) {
			//官方
			news_biaoqain.setImageDrawable(getResources().getDrawable(R.drawable.news_gf_f));
		}else if (NewsClass.indexOf("3")!=-1) {
			//新鲜事
			news_biaoqain.setImageDrawable(getResources().getDrawable(R.drawable.news_flesh_f));
		}else if (NewsClass.indexOf("4")!=-1) {
			//生活
			news_biaoqain.setImageDrawable(getResources().getDrawable(R.drawable.news_life_f));
		}
		
		
		
		if(!NewsImages.contains(".")){//检查字符中是否含有"."
			url=CommUtil.imageurl+CommUtil.image;
		}
		else{
			url=CommUtil.imageurl+NewsImages;
		}
		Log.e("url", url);
		//下载图片
		newstIvin.setTag(url);
		if (mDownloader == null) {mDownloader = new ImageDownloader();}
		
		newstIvin.setImageResource(R.drawable.v5_0_1_photo_default_img);
		
		mDownloader.imageDownload(url, newstIvin, "/lcu",zhuye_in.this, new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
			
				ImageView imageView =(ImageView)newstIvin.findViewWithTag(c_url);
				
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});	
		
		
		newstIvin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		
		newsnotice_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				zhuye_in.this.finish();
				
			}
		});
		
		
		mapin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(News_in.this,ShowPointActivity.class);
//				 Bundle bundle = new Bundle();
//					bundle.putString("losename",NewsName);
//					bundle.putString("content",NewsAbs);
//					bundle.putString("Address",NewsAddress);
//					bundle.putString("url", url);
//					intent.putExtras(bundle);
//					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// 两者必须结合，重新开始MainActivity
//					startActivity(intent);
			}
		});
		
		
	}
    
}


