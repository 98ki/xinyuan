package com.xlj.erp.movefield.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.xlj.erp.movefield.R;

/**
 * Toolbar封装
 * 
 * @author chaohui.yang
 *
 */
public abstract class BaseToolbarActivity extends VolleyActivity implements OnClickListener {
	public Context mContext;
	public Toolbar mToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(getLayoutResId());
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		displayHomeAsUp(mToolbar);
		findViews();
		setListener();
	}

	public void displayHomeAsUp(int resId) {
		Toolbar toolbar = (Toolbar) findViewById(resId);
		displayHomeAsUp(toolbar);
	}

	public void displayHomeAsUp(Toolbar toolbar) {
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			toolbar.setNavigationOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	/**
	 * 返回当前界面的布局
	 * 
	 * @return
	 */
	public abstract int getLayoutResId();

	/**
	 * find view
	 */
	public abstract void findViews();

	/**
	 * 控件监听
	 */
	public abstract void setListener();

	/**
	 * 获取Toolbar
	 * 
	 * @return
	 */
	public Toolbar getToolbar() {
		return mToolbar;
	}

	/**
	 * 设置标题
	 */
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}

	/**
	 * 设置标题
	 */
	public void setTitle(int resId) {
		getSupportActionBar().setTitle(resId);
	}

	/**
	 * 设置导航图标
	 * 
	 * @param resId
	 */
	public void setNavigationIcon(int resId) {
		mToolbar.setNavigationIcon(resId);
	}

	@Override
	public void onClick(View v) {
	}
}
