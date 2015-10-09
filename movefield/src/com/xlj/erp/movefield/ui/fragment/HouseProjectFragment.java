package com.xlj.erp.movefield.ui.fragment;

import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.CarouselPic;
import com.xlj.erp.movefield.entity.DesignSketchUrl;
import com.xlj.erp.movefield.ui.activity.AddCustomerActivity;
import com.xlj.erp.movefield.ui.activity.DesignSketchActivity;
import com.xlj.erp.movefield.ui.activity.HouseSearchActivity;
import com.xlj.erp.movefield.ui.activity.ProjectFileActivity;
import com.xlj.erp.movefield.ui.activity.ProjectInfoActivity;
import com.xlj.erp.movefield.ui.activity.PromoteRoomActivity;
import com.xlj.erp.movefield.ui.activity.WebViewActivity;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.view.BannerView;

/**
 * 项目楼盘
 * 
 * @author chaohui.yang
 *
 */
public class HouseProjectFragment extends BaseFragment {
	private static String REQUEST_GET_CAROUSEL_PIC = "getCarouselPic";
	private static String REQUEST_GET_DESIGN_SKETCH = "getDesignSketch";
	private BannerView mHouseProjectBanner;

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;

	public static HouseProjectFragment newInstance() {
		HouseProjectFragment fragment = new HouseProjectFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private List<String> getTips(String[] picTips) {
		return Arrays.asList(picTips);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_house_project;
	}

	@Override
	public void findViews() {
		mHouseProjectBanner = (BannerView) mRootView.findViewById(R.id.binnerview);
	}

	@Override
	public void setListener() {
		mRootView.findViewById(R.id.module_building_info).setOnClickListener(this);
		mRootView.findViewById(R.id.module_promote_room).setOnClickListener(this);
		mRootView.findViewById(R.id.module_design_sketch).setOnClickListener(this);
		mRootView.findViewById(R.id.module_register_customer).setOnClickListener(this);
		mRootView.findViewById(R.id.module_mortgage_calc).setOnClickListener(this);
		mRootView.findViewById(R.id.module_saleskits).setOnClickListener(this);
		mRootView.findViewById(R.id.module_house_search).setOnClickListener(this);
	}

	@Override
	public void businessLogic() {
		setupToolbar();
		getCarouselPic();
		registerReceiver();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.module_building_info) {
			Intent intent = new Intent(mParentActivity, ProjectInfoActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.module_promote_room) {
			Intent intent = new Intent(mParentActivity, PromoteRoomActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.module_design_sketch) {
			getDesignSketch();
		} else if (v.getId() == R.id.module_register_customer) {
			Intent intent = new Intent(mParentActivity, AddCustomerActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.module_mortgage_calc) {
			Intent intent = new Intent(mParentActivity, WebViewActivity.class);
			intent.putExtra(Contants.WEB_VIEW_ACTIVITY_TITLE, getString(R.string.title_mortgage_calc));
			intent.putExtra(Contants.WEB_VIEW_ACTIVITY_URL, String.format(Urls.getMortgageCalc(), ""));
			startActivity(intent);
		} else if (v.getId() == R.id.module_saleskits) {
			Intent intent = new Intent(mParentActivity, ProjectFileActivity.class);
			startActivity(intent);

		} else if (v.getId() == R.id.module_house_search) {
			Intent intent = new Intent(mParentActivity, HouseSearchActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_DESIGN_SKETCH.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.login_failure);
					return;
				}
				String data = jsonObject.getString("result");
				DesignSketchUrl designSketchUrl = JSON.parseObject(data, DesignSketchUrl.class);
				if (designSketchUrl == null) {
					showToast(R.string.response_invalid);
					return;
				}
				Intent intent = new Intent(mParentActivity, DesignSketchActivity.class);
				intent.putExtra(Contants.DESIGN_SKETCH_URL, designSketchUrl);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		} else if (REQUEST_GET_CAROUSEL_PIC.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess == Contants.REQUEST_SUCCESS) {
					JSONObject data = jsonObject.getJSONObject("result");
					String carouselpicurlist = data.getString("carouselpicurlist");
					List<CarouselPic> list = JSON.parseArray(carouselpicurlist, CarouselPic.class);
					mHouseProjectBanner.setPicUrls(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		if (REQUEST_GET_DESIGN_SKETCH.equals(request.getRequestTag())) {
			showToast(R.string.network_or_server_invalid);
		}
	}

	private void getCarouselPic() {
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		requestHttp(String.format(Urls.getCarouselPic(), projectId), REQUEST_GET_CAROUSEL_PIC, this);
	}

	private void getDesignSketch() {
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		requestHttp(String.format(Urls.getDesignSketch(), projectId), REQUEST_GET_DESIGN_SKETCH, this, true);
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			getCarouselPic();
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
		Toolbar toolbar = getToolbar();
		View view = toolbar.findViewById(R.id.segment_control);
		if (view != null) {
			toolbar.removeView(view);
		}
		setTitle("楼盘");
		setDisplayShowTitleEnabled(true);
	}
}
