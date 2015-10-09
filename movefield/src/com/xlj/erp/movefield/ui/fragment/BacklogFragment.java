package com.xlj.erp.movefield.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.widget.SegmentControl;
import com.xlj.erp.movefield.widget.SegmentControl.OnSegmentControlClickListener;

/**
 * 待办
 * 
 * @author chaohui.yang
 *
 */
public class BacklogFragment extends BaseFragment implements OnSegmentControlClickListener {

	private SegmentControl mSegmentControl;
	private final String TAG_TODO_FRAGMENT = "TodoTaskFragment";
	private final String TAG_OVERDUEFRAGMENT = "OverdueTaskFragment";
	private static int PARAM_TYPE_TODO = 0;
	private static int PARAM_TYPE_OVERDUE = 1;
	private int mCurrentTabIndex;

	BaseFragment mTodoTaskFragment;
	BaseFragment mOverdueTaskFragment;

	public static BacklogFragment newInstance() {
		BacklogFragment backlogFragment = new BacklogFragment();
		return backlogFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_backlog;
	}

	@Override
	public void findViews() {

	}

	@Override
	public void setListener() {

	}

	@Override
	public void businessLogic() {
		setupToolbar();
		setTabCheck(0);
	}

	@Override
	public void setupToolbar() {
		Toolbar toolbar = getToolbar();
		LayoutInflater.from(mParentActivity).inflate(R.layout.layout_backlog_fragment_segmentcontrol, toolbar);
		mSegmentControl = (SegmentControl) toolbar.findViewById(R.id.segment_control);
		mSegmentControl.setOnSegmentControlClickListener(this);
		mSegmentControl.setCurrentIndex(mCurrentTabIndex);
		setDisplayShowTitleEnabled(false);
	}

	@Override
	public void onSegmentControlClick(int index) {
		FragmentManager fragmentManager = getChildFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		switch (index) {
		case 0:
			if (mCurrentTabIndex != 0) {
				setTabCheck(0);
			}
			break;
		case 1:
			if (mCurrentTabIndex != 1) {
				setTabCheck(1);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 隐藏所有Fragment
	 *
	 * @param fragmentTransaction
	 */
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (mTodoTaskFragment != null) {
			fragmentTransaction.hide(mTodoTaskFragment);
		}
		if (mOverdueTaskFragment != null) {
			fragmentTransaction.hide(mOverdueTaskFragment);
		}

	}

	private void setTabCheck(int index) {
		FragmentManager fragmentManager = getChildFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		hideFragment(fragmentTransaction);
		switch (index) {
		case 0:
			mTodoTaskFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_TODO_FRAGMENT);
			if (mTodoTaskFragment == null) {
				mTodoTaskFragment = BacklogChildFragment.newInstance(PARAM_TYPE_TODO);
				fragmentTransaction.add(R.id.backlog_fragment_layout, mTodoTaskFragment, TAG_TODO_FRAGMENT);
			} else {
				mTodoTaskFragment.setupToolbar();
				fragmentTransaction.show(mTodoTaskFragment);
			}
			mCurrentTabIndex = 0;
			break;
		case 1:
			mOverdueTaskFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_OVERDUEFRAGMENT);
			if (mOverdueTaskFragment == null) {
				mOverdueTaskFragment = BacklogChildFragment.newInstance(PARAM_TYPE_OVERDUE);
				fragmentTransaction.add(R.id.backlog_fragment_layout, mOverdueTaskFragment, TAG_OVERDUEFRAGMENT);
			} else {
				mOverdueTaskFragment.setupToolbar();
				fragmentTransaction.show(mOverdueTaskFragment);
			}
			mCurrentTabIndex = 1;
			break;

		}
		fragmentTransaction.commit();
	}
}
