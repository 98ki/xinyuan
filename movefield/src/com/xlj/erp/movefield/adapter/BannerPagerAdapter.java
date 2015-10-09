package com.xlj.erp.movefield.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class BannerPagerAdapter extends PagerAdapter {
	private View[] mViews;

	public BannerPagerAdapter(View[] views) {
		mViews = views;
	}

	@Override
	public int getCount() {
		return mViews.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mViews[position]);

	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mViews[position], 0);
        return mViews[position];
	}

}
