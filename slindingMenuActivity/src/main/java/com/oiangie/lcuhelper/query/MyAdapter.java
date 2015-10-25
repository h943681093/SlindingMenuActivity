package com.oiangie.lcuhelper.query;

import java.util.ArrayList;
import java.util.HashMap;







import com.jeremyfeinstein.slidingmenu.example.R;
import com.lcu.helper.main.SchoolMap;
import com.oiangie.lcuhelper.JavaTool.ImageDownloader;
import com.oiangie.lcuhelper.JavaTool.OnImageDownload;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private static ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	private Activity mActivity;
	private MyView views ;
	private ImageDownloader mDownloader;
	private ListView lv;
	private Context context;
	private int key;
	public MyAdapter(Activity mActivity, ArrayList<HashMap<String, Object>> data,
			ListView lv,Context context,int key){
		
		this.mInflater = LayoutInflater.from(context);
		this.context=context;
		MyAdapter.data = data;
		this.mActivity = mActivity;
		this.lv=lv;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int indext=position;//定义第三变量
		if (convertView == null){
		convertView = mInflater.inflate(R.layout.query_item, null);	
	    views = new MyView();
	    views.photo=(ImageView) convertView.findViewById(R.id.query_item_pic);
		views.infor=(TextView) convertView.findViewById(R.id.query_item_infor);
		views.name = (TextView) convertView.findViewById(R.id.query_item_name);		
		views.map = (ImageView) convertView.findViewById(R.id.query_item_location);
		views.near=(Button)convertView.findViewById(R.id.query_item_near);
		convertView.setTag(views);
		}
		else{
			views=(MyView)convertView.getTag();
		}
		views.name.setText(data.get(position).get("name").toString());
		views.infor.setText(data.get(position).get("infor").toString());
		views.near.setOnClickListener(new OnClickListener(){
		//点击附近按钮   这是第一种方法 在地图上选择一个点 返回地图上面的坐标来进行选择
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
			  	String str=data.get(indext).get("name").toString();
				Log.e("附近按钮点击事件",str);
				Intent intent=new Intent(context,Nearby.class);
				Bundle bundle=new Bundle();
				bundle.putString("namea", str);
				Log.e("bundle", "我进行传值了");
				bundle.putInt("come", 2);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
				mActivity.finish();
				}	
			
		});
	//点击地图按钮
		views.map.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=data.get(indext).get("name").toString();//得到某一个item对应的数据
				Intent it=new Intent();
				it.setClass(context, SchoolMap.class);
				it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				Bundle bundle = new Bundle();
				bundle.putInt("way",1);//显示的方法 1为单点显示
				bundle.putInt("Doshow",1);//用于判断是否显示点
				bundle.putString("name",name);
				it.putExtras(bundle);
				context.startActivity(it);
				Log.e("demo", "Adapter数据填装完成");
				Log.i("demo", "输入的内容"+name);
			}
		});
		final String url = data.get(position).get("url").toString();
		views.photo.setTag(url);
		if (mDownloader == null) {
			mDownloader = new ImageDownloader();
			}
		views.photo.setImageResource(R.drawable.v5_0_1_photo_default_img);
		mDownloader.imageDownload(url, views.photo, "/yanbin",mActivity, new OnImageDownload() {
		@Override
		public void onDownloadSucc(Bitmap bitmap,String c_url,ImageView mimageView) {
				ImageView imageView =(ImageView)lv.findViewWithTag(c_url);
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
					imageView.setTag("");
					} 
				}
		});		
				return convertView;
		}
		public ArrayList<String> getName(){
			ArrayList<String> names=new ArrayList<String>();
				for(int i=0;i<data.size();i++){
					String name=data.get(i).get("name").toString();
					names.add(name);
					}
				return names;
		}
		private final class MyView {
		
			TextView name;
			TextView infor;
			ImageView photo;
			ImageView map;
			Button near;
		}
}
