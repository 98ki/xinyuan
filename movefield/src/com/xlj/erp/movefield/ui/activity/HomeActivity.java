package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.CommonDataManager;
import com.xlj.erp.movefield.entity.KeyValue;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.ui.fragment.BacklogFragment;
import com.xlj.erp.movefield.ui.fragment.CustomerFragment;
import com.xlj.erp.movefield.ui.fragment.FieldMonitorFragment;
import com.xlj.erp.movefield.ui.fragment.HouseProjectFragment;
import com.xlj.erp.movefield.ui.fragment.MenuFragment;
import com.xlj.erp.movefield.ui.fragment.SaleAnalysisFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.view.HomeTabView;

public class HomeActivity extends BaseToolbarActivity {
	private static String REQUEST_GETINTERESTDGREE = "getInterestDgree";
	private static String REQUEST_GETVAGERANGE = "getVagerange";
	private static String REQUEST_GETINTERESTHOUSE = "getInterestHouse";
	private static String REQUEST_GETMTZL = "getMTZL";
	private static String REQUEST_GETCUSTOMERRESOURCE = "getCustomerResource";
	private static String REQUEST_GETCUSTSTATUS = "getCustStatus";
	private static String REQUEST_GETGJFS = "getGJFS";

	private static final String TAG_HOUSE_PROJECT_FRAGMENT = "HouseProjectFragment";
	private static final String TAG_BACKLOG_FRAGMENT = "BacklogFragment";
	private static final String TAG_CUSTOMER_FRAGMENT = "CustomerFragment";
	private static final String TAG_SALE_ANALYSIS_FRAGMENT = "SaleAnalysisFragment";
	private static final String TAG_FIELD_MONITOR_FRAGMENT = "FieldMonitorFragment";
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private HomeTabView mHomeTabHouseProject;
	private HomeTabView mHomeTabBacklog;
	private HomeTabView mHomeTabCustomer;
	private HomeTabView mHomeTabSaleAnalysis;
	private HomeTabView mHomeTabFieldMonitor;

	private BaseFragment mHouseProjectFragment;
	private BaseFragment mBacklogFragment;
	private BaseFragment mCustomerFragment;
	private BaseFragment mSaleAnalysisFragment;
	private BaseFragment mFieldMonitorFragment;

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;

	private int mCurrentTabIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		// if (Build.VERSION.SDK_INT >= 19) {
		// ViewGroup drawerRoot = (ViewGroup)
		// findViewById(R.id.home_drawer_layout_root);
		// drawerRoot.setPadding(drawerRoot.getPaddingLeft(),
		// SystemBarUtils.getStatusBarHeight(this),
		// drawerRoot.getPaddingRight(), drawerRoot.getBottom());
		// }
		// if (Build.VERSION.SDK_INT == 19) {
		// ViewGroup rootMain = (ViewGroup) findViewById(R.id.home_layout_root);
		// rootMain.setPadding(rootMain.getPaddingLeft(),
		// rootMain.getPaddingTop(), rootMain.getPaddingRight(),
		// rootMain.getBottom());
		// }

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.draw_open, R.string.draw_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// MenuFragment
		MenuFragment menuFragment = MenuFragment.newInstance();
		getSupportFragmentManager().beginTransaction().add(R.id.home_menu_frame, menuFragment, "MenuFragment").commit();

		getInterestDgree();
		getVagerange();
		getInterestHouse();
		getMTZL();
		getCustomerResource();
		getCustStatus();
		getGJFS();

		// 权限
		switchAuthority();
		// 默认打开HouseProjectFragment
		setTabCheck(0);

		registerReceiver();
	}

	@Override
	public int getStatusBarTintResource() {
		return R.color.transparent;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_home;
	}

	@Override
	public void findViews() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
		mHomeTabHouseProject = (HomeTabView) findViewById(R.id.home_tab_house_project);
		mHomeTabBacklog = (HomeTabView) findViewById(R.id.home_tab_backlog);
		mHomeTabCustomer = (HomeTabView) findViewById(R.id.home_tab_customer);
		mHomeTabSaleAnalysis = (HomeTabView) findViewById(R.id.home_tab_sale_analysis);
		mHomeTabFieldMonitor = (HomeTabView) findViewById(R.id.home_tab_field_monitor);
	}

	@Override
	public void setListener() {
		mHomeTabHouseProject.setOnClickListener(this);
		mHomeTabBacklog.setOnClickListener(this);
		mHomeTabCustomer.setOnClickListener(this);
		mHomeTabSaleAnalysis.setOnClickListener(this);
		mHomeTabFieldMonitor.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.home_tab_house_project) {
			if (mCurrentTabIndex != 0) {
				setTabCheck(0);
			}
		} else if (v.getId() == R.id.home_tab_backlog) {
			if (mCurrentTabIndex != 1) {
				setTabCheck(1);
			}
		} else if (v.getId() == R.id.home_tab_customer) {
			if (mCurrentTabIndex != 2) {
				setTabCheck(2);
			}
		} else if (v.getId() == R.id.home_tab_sale_analysis) {
			if (mCurrentTabIndex != 3) {
				setTabCheck(3);
			}
		} else if (v.getId() == R.id.home_tab_field_monitor) {
			if (mCurrentTabIndex != 4) {
				setTabCheck(4);
			}
		}
	}

	/**
	 * 重置所有底部TAB为非选中
	 */
	private void resetTab() {
		mHomeTabHouseProject.setChecked(false);
		mHomeTabBacklog.setChecked(false);
		mHomeTabCustomer.setChecked(false);
		mHomeTabSaleAnalysis.setChecked(false);
		mHomeTabFieldMonitor.setChecked(false);
	}

	/**
	 * 隐藏所有Fragment
	 * 
	 * @param fragmentTransaction
	 */
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (mHouseProjectFragment != null) {
			fragmentTransaction.hide(mHouseProjectFragment);
		}
		if (mBacklogFragment != null) {
			fragmentTransaction.hide(mBacklogFragment);
		}
		if (mCustomerFragment != null) {
			fragmentTransaction.hide(mCustomerFragment);
		}
		if (mSaleAnalysisFragment != null) {
			fragmentTransaction.hide(mSaleAnalysisFragment);
		}
		if (mFieldMonitorFragment != null) {
			fragmentTransaction.hide(mFieldMonitorFragment);
		}
	}

	/**
	 * Fragment切换管理
	 * 
	 * @param index
	 */
	private void setTabCheck(int index) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		resetTab();
		hideFragment(fragmentTransaction);
		switch (index) {
		case 0:
			mHouseProjectFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_HOUSE_PROJECT_FRAGMENT);
			if (mHouseProjectFragment == null) {
				mHouseProjectFragment = HouseProjectFragment.newInstance();
				fragmentTransaction.add(R.id.home_content_frame, mHouseProjectFragment, TAG_HOUSE_PROJECT_FRAGMENT);
			} else {
				mHouseProjectFragment.setupToolbar();
				fragmentTransaction.show(mHouseProjectFragment);
			}
			mHomeTabHouseProject.setChecked(true);
			mCurrentTabIndex = 0;
			break;
		case 1:
			mBacklogFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_BACKLOG_FRAGMENT);
			if (mBacklogFragment == null) {
				mBacklogFragment = BacklogFragment.newInstance();
				fragmentTransaction.add(R.id.home_content_frame, mBacklogFragment, TAG_BACKLOG_FRAGMENT);
			} else {
				mBacklogFragment.setupToolbar();
				fragmentTransaction.show(mBacklogFragment);
			}
			mHomeTabBacklog.setChecked(true);
			mCurrentTabIndex = 1;
			break;
		case 2:
			mCustomerFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_CUSTOMER_FRAGMENT);
			if (mCustomerFragment == null) {
				mCustomerFragment = CustomerFragment.newInstance();
				fragmentTransaction.add(R.id.home_content_frame, mCustomerFragment, TAG_CUSTOMER_FRAGMENT);
			} else {
				mCustomerFragment.setupToolbar();
				fragmentTransaction.show(mCustomerFragment);
			}
			mHomeTabCustomer.setChecked(true);
			mCurrentTabIndex = 2;
			break;
		case 3:
			mSaleAnalysisFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_SALE_ANALYSIS_FRAGMENT);
			if (mSaleAnalysisFragment == null) {
				mSaleAnalysisFragment = SaleAnalysisFragment.newInstance();
				fragmentTransaction.add(R.id.home_content_frame, mSaleAnalysisFragment, TAG_SALE_ANALYSIS_FRAGMENT);
			} else {
				mSaleAnalysisFragment.setupToolbar();
				fragmentTransaction.show(mSaleAnalysisFragment);
			}
			mHomeTabSaleAnalysis.setChecked(true);
			mCurrentTabIndex = 3;
			break;
		case 4:
			mFieldMonitorFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_FIELD_MONITOR_FRAGMENT);
			if (mFieldMonitorFragment == null) {
				mFieldMonitorFragment = FieldMonitorFragment.newInstance();
				fragmentTransaction.add(R.id.home_content_frame, mFieldMonitorFragment, TAG_FIELD_MONITOR_FRAGMENT);
			} else {
				mFieldMonitorFragment.setupToolbar();
				fragmentTransaction.show(mFieldMonitorFragment);
			}
			mHomeTabFieldMonitor.setChecked(true);
			mCurrentTabIndex = 4;
			break;
		}
		fragmentTransaction.commitAllowingStateLoss();
	}

	/**
	 * 权限控制底部TAB
	 */
	private void switchAuthority() {
		if (UserManager.getCurrentProject(this).getAuthority() == Contants.AUTHORITY_MANAGER) {
			mHomeTabSaleAnalysis.setVisibility(View.VISIBLE);
			mHomeTabFieldMonitor.setVisibility(View.GONE);
		} else {
			mHomeTabSaleAnalysis.setVisibility(View.GONE);
			mHomeTabFieldMonitor.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			getInterestDgree();
			getVagerange();
			getInterestHouse();
			getMTZL();
			getCustomerResource();
			getCustStatus();
			getGJFS();

			switchAuthority();
			setTabCheck(0);
		}
	}

	private void registerReceiver() {
		mSwitchProjectBroadcastReceiver = new SwitchProjectBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Contants.ACTION_SWITCH_PROJECT);
		registerReceiver(mSwitchProjectBroadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		if (mSwitchProjectBroadcastReceiver != null) {
			unregisterReceiver(mSwitchProjectBroadcastReceiver);
		}
		super.onDestroy();
	}

	private void getInterestDgree() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getInterestDgree(), String.valueOf(projectId), userid, username), REQUEST_GETINTERESTDGREE, this);
	}

	private void getVagerange() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getVagerange(), String.valueOf(projectId), userid, username), REQUEST_GETVAGERANGE, this);
	}

	private void getInterestHouse() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getInterestHouse(), String.valueOf(projectId), userid, username), REQUEST_GETINTERESTHOUSE, this);
	}

	private void getMTZL() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getMTZL(), String.valueOf(projectId), userid, username), REQUEST_GETMTZL, this);
	}

	private void getCustomerResource() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getCustomerResource(), String.valueOf(projectId), userid, username), REQUEST_GETCUSTOMERRESOURCE, this);
	}

	private void getCustStatus() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getCustStatus(), String.valueOf(projectId), userid, username), REQUEST_GETCUSTSTATUS, this);
	}

	private void getGJFS() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getGJFS(), String.valueOf(projectId), userid, username), REQUEST_GETGJFS, this);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETINTERESTDGREE.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setInterestDgree(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETVAGERANGE.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setVagerange(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETINTERESTHOUSE.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<KeyValue> list = JSON.parseArray(data, KeyValue.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setInterestHouse(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETMTZL.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<KeyValue> list = JSON.parseArray(data, KeyValue.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setMTZL(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETCUSTOMERRESOURCE.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setCustomerResource(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETCUSTSTATUS.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setCustStatus(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (REQUEST_GETGJFS.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					return;
				}
				String data = jsonObject.getString("result");
				List<KeyValue> list = JSON.parseArray(data, KeyValue.class);
				if (list == null) {
					return;
				}
				CommonDataManager.setGJFS(list);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

}
