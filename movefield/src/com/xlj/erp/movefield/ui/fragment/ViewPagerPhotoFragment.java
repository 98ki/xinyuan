package com.xlj.erp.movefield.ui.fragment;

import java.io.Serializable;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.CirclePageIndicator;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.widget.HackyViewPager;

public class ViewPagerPhotoFragment extends BaseFragment {
	private List<String> mPicUrls;
	private DisplayImageOptions options;

	private ViewPager mViewPager;
	private CirclePageIndicator mIndicator;

	public static ViewPagerPhotoFragment newInstance(List<String> picUrls) {
		ViewPagerPhotoFragment viewPagerPhotoFragment = new ViewPagerPhotoFragment();
		Bundle args = new Bundle();
		args.putSerializable("urls", (Serializable) picUrls);
		viewPagerPhotoFragment.setArguments(args);
		return viewPagerPhotoFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_view_pager_photo;
	}

	@Override
	public void findViews() {
		mViewPager = (HackyViewPager) mRootView.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) mRootView.findViewById(R.id.indicator);
	}

	@Override
	public void setListener() {

	}

	@Override
	public void businessLogic() {
		ColorDrawable colorDrawable = new ColorDrawable(mParentActivity.getResources().getColor(R.color.imageloader_default_color));
		options = new DisplayImageOptions.Builder().showImageOnLoading(colorDrawable).showImageForEmptyUri(colorDrawable).showImageOnFail(colorDrawable).cacheInMemory(true)
				.cacheOnDisk(true).build();
		mPicUrls = (List<String>) getArguments().getSerializable("urls");
		mViewPager.setAdapter(new SamplePagerAdapter());
		mIndicator.setViewPager(mViewPager);
	}

	private class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPicUrls.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			ImageLoader.getInstance().displayImage(mPicUrls.get(position), photoView, options);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	public void setupToolbar() {
		
	}

}
