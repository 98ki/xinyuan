package com.xlj.erp.movefield.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.adapter.BannerPagerAdapter;
import com.xlj.erp.movefield.entity.CarouselPic;

public class BannerView extends RelativeLayout implements OnPageChangeListener {
	private Context mContext;
	private DisplayImageOptions mImageOptions;
	private List<CarouselPic> mPicUrls;

	private ImageView[] mImageViews;
	/**
	 * 小圆点
	 */
	private ImageView[] mTips;

	private ViewPager mViewPager;
	private TextView mDescription;
	private LinearLayout mIndicatorContainer;

	public BannerView(Context context) {
		this(context, null);
	}

	public BannerView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		RelativeLayout originalLayout = (RelativeLayout) inflate(context, R.layout.layout_view_pager_banner, this);

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mDescription = (TextView) findViewById(R.id.description);
		mIndicatorContainer = (LinearLayout) findViewById(R.id.indicator_container);
	}

	public void setPicUrls(List<CarouselPic> picUrls) {
		if (picUrls == null || picUrls.size() == 0) {
			return;
		}
		ColorDrawable colorDrawable = new ColorDrawable(mContext.getResources().getColor(R.color.imageloader_default_color));
		mImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(colorDrawable).showImageForEmptyUri(colorDrawable).showImageOnFail(colorDrawable).cacheInMemory(true)
				.cacheOnDisk(true).build();
		mPicUrls = picUrls;
		initIndicator();
		initImageView();
		initDescription();

		mViewPager.setAdapter(new BannerPagerAdapter(mImageViews));
		mViewPager.setOnPageChangeListener(this);
		stopAutoPlay();
		processAutoPlay();
	}

	/**
	 * 初始化小圆圈
	 */
	private void initIndicator() {
		mIndicatorContainer.removeAllViews();
		mTips = new ImageView[mPicUrls.size()];
		for (int i = 0; i < mTips.length; i++) {
			ImageView imageView = new ImageView(mContext);
			mTips[i] = imageView;
			if (i == 0) {
				mTips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				mTips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelOffset(R.dimen.banner_view_indicator), mContext.getResources()
					.getDimensionPixelOffset(R.dimen.banner_view_indicator));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			mIndicatorContainer.addView(imageView, layoutParams);
		}
	}

	private void initImageView() {
		mImageViews = new ImageView[mPicUrls.size()];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageViews[i] = imageView;
			ImageLoader.getInstance().displayImage(mPicUrls.get(i).getPicUrl(), imageView, mImageOptions);
		}
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < mTips.length; i++) {
			if (i == selectItems) {
				mTips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				mTips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		int realIndex = position;
		setImageBackground(realIndex);
		if (TextUtils.isEmpty(mPicUrls.get(realIndex).getDescription())) {
			mDescription.setText("");
		} else {
			mDescription.setText(mPicUrls.get(realIndex).getDescription());
		}
	}

	private void initDescription() {
		if (TextUtils.isEmpty(mPicUrls.get(0).getDescription())) {
			mDescription.setText("");
		} else {
			mDescription.setText(mPicUrls.get(0).getDescription());
		}
	}

	private Handler mPagerHandler = new Handler();

	private Runnable mAutoPlayTask = new Runnable() {
		@Override
		public void run() {
			int position = mViewPager.getCurrentItem() + 1;
			if (position >= mPicUrls.size()) {
				position = 0;
			}
			mViewPager.setCurrentItem(position);
			mPagerHandler.postDelayed(mAutoPlayTask, 5000);
		}
	};

	private void startAutoPlay() {
		mPagerHandler.postDelayed(mAutoPlayTask, 5000);
	}

	private void stopAutoPlay() {
		mPagerHandler.removeCallbacks(mAutoPlayTask);
	}

	private void processAutoPlay() {
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopAutoPlay();
					break;
				case MotionEvent.ACTION_UP:
					startAutoPlay();
					break;
				}
				return false;
			}
		});
		startAutoPlay();
	}

}
