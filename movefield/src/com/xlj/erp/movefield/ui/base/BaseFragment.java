package com.xlj.erp.movefield.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xlj.erp.movefield.utils.ToastUtils;

public abstract class BaseFragment extends VolleyFragment implements OnClickListener {
	public View mRootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(getLayoutResId(), container, false);
		findViews();
		setListener();
		businessLogic();
		return mRootView;
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
	 * 业务逻辑
	 */
	public abstract void businessLogic();

	@Override
	public void onClick(View v) {
	}

	/**
	 * 显示Toast
	 * 
	 * @param text
	 */
	public void showToast(CharSequence text) {
		ToastUtils.showToast(mParentActivity, text);
	}

	/**
	 * 显示toast
	 * 
	 * @param resId
	 */
	public void showToast(int resId) {
		ToastUtils.showToast(mParentActivity, resId);
	}

	/**
	 * 设置标题
	 */
	public void setTitle(CharSequence title) {
		((AppCompatActivity) mParentActivity).getSupportActionBar().setTitle(title);
	}

	/**
	 * 设置标题
	 */
	public void setTitle(int resId) {
		((AppCompatActivity) mParentActivity).setTitle(resId);
	}
	
	/**
	 * 是否显示title
	 * @param enabled
	 */
	public void setDisplayShowTitleEnabled(boolean enabled){
		((AppCompatActivity) mParentActivity).getSupportActionBar().setDisplayShowTitleEnabled(enabled);
	}
	
	/**
	 * toolbar
	 * @return
	 */
	public Toolbar getToolbar(){
		return ((BaseToolbarActivity)mParentActivity).mToolbar;
	}
	
	public abstract void setupToolbar();
}
