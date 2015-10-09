package com.xlj.erp.movefield.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.activity.ChooseProjectActivity;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.PrefsUtils;
import com.xlj.erp.movefield.utils.UserManager;

/**
 * 主界面侧边栏
 * 
 * @author chaohui.yang
 *
 */
public class MenuFragment extends BaseFragment {
	private TextView mMenuAuthority;
	private TextView mMenuUsername;

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;

	public static MenuFragment newInstance() {
		MenuFragment fragment = new MenuFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_menu;
	}

	@Override
	public void findViews() {
		mMenuAuthority = (TextView) mRootView.findViewById(R.id.menu_authority);
		mMenuUsername = (TextView) mRootView.findViewById(R.id.menu_username);
	}

	@Override
	public void setListener() {
		mRootView.findViewById(R.id.menu_root_view).setOnClickListener(this);
		mRootView.findViewById(R.id.menu_switch_project).setOnClickListener(this);
		mRootView.findViewById(R.id.menu_my_message).setOnClickListener(this);
		mRootView.findViewById(R.id.menu_logout).setOnClickListener(this);
	}

	@Override
	public void businessLogic() {
		String realname = UserManager.getUser().getRealname();
		if (!TextUtils.isEmpty(realname)) {
			mMenuUsername.setText(realname);
		}
		setAuthorityText();
		registerReceiver();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.menu_root_view) {
			//DO NOTHING
		} else if (v.getId() == R.id.menu_switch_project) {
			Intent intent = new Intent(mParentActivity, ChooseProjectActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.menu_my_message) {
			// TODO 跳转
		} else if (v.getId() == R.id.menu_logout) {
			PrefsUtils.putBoolean(mParentActivity, PrefsUtils.KEY_AUTO_LOGIN, false);
			mParentActivity.finish();
		}
	}

	private void setAuthorityText() {
		int authority = UserManager.getCurrentProject(mParentActivity).getAuthority();
		mMenuAuthority.setText(authority == Contants.AUTHORITY_MANAGER ? getString(R.string.menu_authority_manager) : getString(R.string.menu_authority_salesman));
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			setAuthorityText();
		}
	}

	private void registerReceiver() {
		mSwitchProjectBroadcastReceiver = new SwitchProjectBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Contants.ACTION_SWITCH_PROJECT);
		mParentActivity.registerReceiver(mSwitchProjectBroadcastReceiver, filter);
	}

	@Override
	public void onDestroyView() {
		if (mSwitchProjectBroadcastReceiver != null) {
			mParentActivity.unregisterReceiver(mSwitchProjectBroadcastReceiver);
		}
		super.onDestroyView();
	}

	@Override
	public void setupToolbar() {

	}
}
