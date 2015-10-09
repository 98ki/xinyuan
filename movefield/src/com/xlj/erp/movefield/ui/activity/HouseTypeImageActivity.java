package com.xlj.erp.movefield.ui.activity;

import uk.co.senab.photoview.PhotoView;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

/**
 * 户型图
 * 
 * @author chaohui.yang
 *
 */
public class HouseTypeImageActivity extends BaseToolbarActivity {
	private PhotoView mPhotoView;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_house_type_image);
		String url = getIntent().getStringExtra(Contants.PIC_URL);
		ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.imageloader_default_color));
		options = new DisplayImageOptions.Builder().showImageOnLoading(colorDrawable).showImageForEmptyUri(colorDrawable).showImageOnFail(colorDrawable).cacheInMemory(true)
				.cacheOnDisk(true).build();
		ImageLoader.getInstance().displayImage(url, mPhotoView, options);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_house_type_image;
	}

	@Override
	public void findViews() {
		mPhotoView = (PhotoView) findViewById(R.id.photo_view);
	}

	@Override
	public void setListener() {

	}

}
