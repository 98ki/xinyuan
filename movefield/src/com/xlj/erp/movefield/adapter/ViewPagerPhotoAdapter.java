package com.xlj.erp.movefield.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xlj.erp.movefield.entity.DesignSketchUrl;
import com.xlj.erp.movefield.ui.fragment.ViewPagerPhotoFragment;

/**
 * 效果图adapter，4个tab
 * @author chaohui.yang
 *
 */
public class ViewPagerPhotoAdapter extends FragmentPagerAdapter {
	private DesignSketchUrl mDesignSketchUrl;

	public ViewPagerPhotoAdapter(FragmentManager fm, DesignSketchUrl designSketchUrl) {
		super(fm);
		mDesignSketchUrl = designSketchUrl;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = ViewPagerPhotoFragment.newInstance(mDesignSketchUrl.getDesignpicurllist());
			break;
		case 1:
			fragment = ViewPagerPhotoFragment.newInstance(mDesignSketchUrl.getRealisticpicurllist());
			break;
		case 2:
			fragment = ViewPagerPhotoFragment.newInstance(mDesignSketchUrl.getTemplatepicurllist());
			break;
		case 3:
			fragment = ViewPagerPhotoFragment.newInstance(mDesignSketchUrl.getTrafficpicurlist());
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
