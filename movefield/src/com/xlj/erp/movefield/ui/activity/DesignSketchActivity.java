package com.xlj.erp.movefield.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.UnderlinePageIndicator;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.adapter.ViewPagerPhotoAdapter;
import com.xlj.erp.movefield.entity.DesignSketchUrl;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

/**
 * 效果图
 * 
 * @author chaohui.yang
 *
 */
public class DesignSketchActivity extends BaseToolbarActivity {
	private ViewPager mViewPager;
	private UnderlinePageIndicator mPagerIndicator;
	private TextView mDesignPic;
	private TextView mRealPic;
	private TextView mTemplatePic;
	private TextView mTrafficPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.title_design_sketch);
		DesignSketchUrl designSketchUrl = (DesignSketchUrl) getIntent().getSerializableExtra(Contants.DESIGN_SKETCH_URL);
		ViewPagerPhotoAdapter adapter = new ViewPagerPhotoAdapter(getSupportFragmentManager(), designSketchUrl);
		mViewPager.setAdapter(adapter);
		mPagerIndicator.setViewPager(mViewPager);

		mPagerIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setSelected(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_design_sketch;
	}

	@Override
	public void findViews() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mPagerIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		mDesignPic = (TextView) findViewById(R.id.design_pic);
		mRealPic = (TextView) findViewById(R.id.real_pic);
		mTemplatePic = (TextView) findViewById(R.id.template_pic);
		mTrafficPic = (TextView) findViewById(R.id.traffic_pic);
	}

	@Override
	public void setListener() {
		mDesignPic.setOnClickListener(this);
		mRealPic.setOnClickListener(this);
		mTemplatePic.setOnClickListener(this);
		mTrafficPic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.design_pic) {
			mPagerIndicator.setCurrentItem(0);
		} else if (v.getId() == R.id.real_pic) {
			mPagerIndicator.setCurrentItem(1);
		} else if (v.getId() == R.id.template_pic) {
			mPagerIndicator.setCurrentItem(2);
		} else if (v.getId() == R.id.traffic_pic) {
			mPagerIndicator.setCurrentItem(3);
		}
	}

	private void setSelected(int position) {
		mDesignPic.setTextColor(getResources().getColor(R.color.gray));
		mRealPic.setTextColor(getResources().getColor(R.color.gray));
		mTemplatePic.setTextColor(getResources().getColor(R.color.gray));
		mTrafficPic.setTextColor(getResources().getColor(R.color.gray));
		switch (position) {
		case 0:
			mDesignPic.setTextColor(getResources().getColor(R.color.md_color_primary));
			break;
		case 1:
			mRealPic.setTextColor(getResources().getColor(R.color.md_color_primary));
			break;
		case 2:
			mTemplatePic.setTextColor(getResources().getColor(R.color.md_color_primary));
			break;
		case 3:
			mTrafficPic.setTextColor(getResources().getColor(R.color.md_color_primary));
			break;
		}
	}

}
