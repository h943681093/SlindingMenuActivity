package com.lcu.xueshengzuzhi;

import java.util.ArrayList;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.example.R;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class xueshnegzuzhi extends Activity {
	private RadioGroup radioGroup;
	private ViewPager mPager;
	private List<View> listViews;
	private LocalActivityManager manager = null;
	private MyPagerAdapter mpAdapter = null;
	private int index;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xueshengzuzhi);
		
		back = (ImageView) findViewById(R.id.notice_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				xueshnegzuzhi.this.finish();
			}
		});
		mPager = (ViewPager)findViewById(R.id.vPager);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		InitViewPager();
		radioGroup = (RadioGroup)findViewById(R.id.rg_main_btns);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {

						case R.id.buyHomeTab:
							index = 0;
							listViews.set(0,getView("A", new Intent(xueshnegzuzhi.this,xszz_xueshenghui.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(0);
							break;

						case R.id.winAfficheTab:
							index = 1;
							listViews.set(1,getView("B", new Intent(xueshnegzuzhi.this,xszz_shetuan.class)));
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(1);
							break;
						default:
							break;
						}
					}
				});
	}
	private void InitViewPager() {
		Intent intent = null;
		listViews = new ArrayList<View>();
		mpAdapter = new MyPagerAdapter(listViews);
		intent = new Intent(xueshnegzuzhi.this, xszz_xueshenghui.class);
		listViews.add(getView("A", intent));
		intent = new Intent(xueshnegzuzhi.this, xszz_shetuan.class);
		listViews.add(getView("B", intent));
		mPager.setOffscreenPageLimit(0);
		mPager.setAdapter(mpAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			manager.dispatchResume();
			switch (arg0) {
			case 0:
				index = 0;
				radioGroup.check(R.id.buyHomeTab);
				listViews
						.set(0,
								getView("A", new Intent(xueshnegzuzhi.this,xszz_xueshenghui.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			case 1:
				index = 1;
				radioGroup.check(R.id.winAfficheTab);
				listViews.set(1,
						getView("B", new Intent(xueshnegzuzhi.this, xszz_shetuan.class)));
				mpAdapter.notifyDataSetChanged();
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	
	
}
