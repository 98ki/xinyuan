package com.xlj.erp.movefield.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.CustomerBasicInfo;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

public class CustomerBasicInfoFragment extends BaseFragment {
	private static String REQUEST_GETONECUSTOMERBYID = "getOneCustomerById";
	private MultiStateView mMultiStateView;

	private TextView mCustomerName;
	private ImageView mCustomerSex;
	private TextView mVagerange;

	private TextView mPhone;
	private TextView mInterestdegree;
	private TextView mFollowtimes;
	private TextView mVisittimes;
	private TextView mLastcontactdate;
	private TextView mStatus;
	private TextView mCustomerSource;
	private TextView mMedia;
	private TextView mInteresthousetype;
	private TextView mResidentialdistrict;
	private TextView mHouseuse;

	private String mCustomerid;

	public static CustomerBasicInfoFragment newInstance(String customerid) {
		CustomerBasicInfoFragment customerBasicInfoFragment = new CustomerBasicInfoFragment();
		Bundle args = new Bundle();
		args.putSerializable("customerid", customerid);
		customerBasicInfoFragment.setArguments(args);
		return customerBasicInfoFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_customer_basic_info;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		mCustomerName = (TextView) mRootView.findViewById(R.id.customer_name);
		mCustomerSex = (ImageView) mRootView.findViewById(R.id.customersex);
		mVagerange = (TextView) mRootView.findViewById(R.id.vagerange);
		mPhone = (TextView) mRootView.findViewById(R.id.phone);
		mInterestdegree = (TextView) mRootView.findViewById(R.id.interestdegree);
		mFollowtimes = (TextView) mRootView.findViewById(R.id.followtimes);
		mVisittimes = (TextView) mRootView.findViewById(R.id.visittimes);
		mLastcontactdate = (TextView) mRootView.findViewById(R.id.lastcontactdate);
		mStatus = (TextView) mRootView.findViewById(R.id.status);
		mCustomerSource = (TextView) mRootView.findViewById(R.id.customersource);
		mMedia = (TextView) mRootView.findViewById(R.id.media);
		mInteresthousetype = (TextView) mRootView.findViewById(R.id.interesthousetype);
		mResidentialdistrict = (TextView) mRootView.findViewById(R.id.residentialdistrict);
		mHouseuse = (TextView) mRootView.findViewById(R.id.houseuse);
	}

	@Override
	public void setListener() {
	}

	@Override
	public void businessLogic() {
		mCustomerid = getArguments().getString("customerid");
		getOneCustomerById();
	}

	@Override
	public void setupToolbar() {
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETONECUSTOMERBYID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				CustomerBasicInfo customerBasicInfo = JSON.parseObject(data, CustomerBasicInfo.class);
				if (customerBasicInfo == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);

					return;
				}
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
				fillData(customerBasicInfo);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		showToast(R.string.network_or_server_invalid);
		mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
	}

	private void getOneCustomerById() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		requestHttp(String.format(Urls.getOneCustomerById(), String.valueOf(projectId), userid, username, mCustomerid), REQUEST_GETONECUSTOMERBYID, this);
	}

	private void fillData(CustomerBasicInfo customerBasicInfo) {
		mCustomerName.setText(customerBasicInfo.getCustomername());
		if ("男".equals(customerBasicInfo.getCustomersex())) {
			mCustomerSex.setImageDrawable(getResources().getDrawable(R.drawable.ic_backlog_female));
		} else {
			mCustomerSex.setImageDrawable(getResources().getDrawable(R.drawable.ic_backlog_male));
		}
		mVagerange.setText(customerBasicInfo.getVagerange());
		mPhone.setText(customerBasicInfo.getPhone());
		mInterestdegree.setText(customerBasicInfo.getInterestdegree());
		mFollowtimes.setText(customerBasicInfo.getFollowtimes());
		mVisittimes.setText(customerBasicInfo.getVisittimes());
		mLastcontactdate.setText(customerBasicInfo.getLastcontactdate());
		mStatus.setText(customerBasicInfo.getStatus());
		mCustomerSource.setText(customerBasicInfo.getCustomerSource());
		mMedia.setText(customerBasicInfo.getMedia());
		mInteresthousetype.setText(customerBasicInfo.getInteresthousetype());
		mResidentialdistrict.setText(customerBasicInfo.getResidentialdistrict());
		mHouseuse.setText(customerBasicInfo.getHouseuse());
	}
}
