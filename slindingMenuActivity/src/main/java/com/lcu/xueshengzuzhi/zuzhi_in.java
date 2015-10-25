package com.lcu.xueshengzuzhi;

import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CommUtil;
import com.oiangie.lcuhelper.JavaTool.ImageDownloader;
import com.oiangie.lcuhelper.JavaTool.OnImageDownload;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class zuzhi_in extends Activity {
private TextView name,StIntdro,StDesc,StPhone;
private ImageView st_url1,st_url2;
private String Name,Intdro,Desc,Phone,Url,urld;
private String url;
private ImageDownloader mDownloader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		Log.i("demo", "1");
		this.setContentView(R.layout.zuzhi_in);
		Log.i("demo", "1");
		name = (TextView) findViewById(R.id.stnamein);
		StIntdro = (TextView) findViewById(R.id.StIntdro);
		StDesc= (TextView) findViewById(R.id.StDesc);
		StPhone= (TextView) findViewById(R.id.StPhone);
		st_url1 = (ImageView) findViewById(R.id.st_url1);
		st_url2 = (ImageView) findViewById(R.id.st_url2);
		Log.i("demo", "1");
		int way =  getIntent().getIntExtra("way", 0);
		
		if (way==1) {
			//����
			 Name =  getIntent().getExtras().getString("StName");
			 Intdro =  getIntent().getExtras().getString("StIntdro");
			 Desc =  getIntent().getExtras().getString("StDesc");
			 Phone =  getIntent().getExtras().getString("StPhone");
			 Url =  getIntent().getExtras().getString("StUrl");
		}else if (way==2) {
			//ѧ����
			 Name =  getIntent().getExtras().getString("SuName");
			 Intdro =  getIntent().getExtras().getString("SuIntdro");
			 Desc =  getIntent().getExtras().getString("SuDesc");
			 Phone =  getIntent().getExtras().getString("SuPhone");
			 Url =  getIntent().getExtras().getString("SuUrl");
		}
		urld = "b"+Url;
		Log.i("Url", Url);
		Log.i("urld", urld);
		name.setText(Name);
		StIntdro.setText(Intdro);
		StDesc.setText(Desc);
		StPhone.setText(Phone);
		
		//��ʼ����Сͼ
		if(!Url.contains(".")){//����ַ����Ƿ���"."
			url=CommUtil.imageurl+CommUtil.image;
		}
		else{
			url=CommUtil.imageurl+Url;
		}
		Log.e("url", url);
		//����ͼƬ
	
		st_url2.setTag(url);
		if (mDownloader == null) {mDownloader = new ImageDownloader();}
		
		st_url2.setImageResource(R.drawable.v5_0_1_photo_default_img);
		
		mDownloader.imageDownload(url, st_url2, "/lcu",zuzhi_in.this, new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
			
				ImageView imageView =(ImageView)st_url2.findViewWithTag(c_url);
				
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});	
		
		//��ʼ�����ͼ
		if(!urld.contains(".")){//����ַ����Ƿ���"."
			url=CommUtil.imageurl+CommUtil.image;
		}
		else{
			url=CommUtil.imageurl+urld;
		}
		Log.e("url", url);
		//����ͼƬ
	
		st_url1.setTag(url);
		if (mDownloader == null) {mDownloader = new ImageDownloader();}
		
		st_url1.setImageResource(R.drawable.v5_0_1_photo_default_img);
		
		mDownloader.imageDownload(url, st_url1, "/lcu",zuzhi_in.this, new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
			
				ImageView imageView =(ImageView)st_url1.findViewWithTag(c_url);
				
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});	
		
	}

}
