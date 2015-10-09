package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.CustomerXSXSAdapter;
import com.xlj.erp.movefield.adapter.CustomerYQGJAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.CustomerXSXS;
import com.xlj.erp.movefield.entity.CustomerYQGJ;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;
import com.xlj.erp.movefield.widget.SegmentControl;

public class AssignCustomerActivity extends BaseToolbarActivity implements SegmentControl.OnSegmentControlClickListener {
	private static String REQUEST_GETYQGJCUSTOMER = "getYQGJCustomer";
	private static String REQUEST_GETXSXSCUSTOMER = "getXSXSCustomer";

	private SegmentControl mSegmentControl;
	private MultiStateView mMultiStateViewYqgj;
	private RecyclerView mRecyclerViewYqgj;
	private MultiStateView mMultiStateViewXsxs;
	private RecyclerView mRecyclerViewXsxs;
	private CustomerYQGJAdapter mCustomerYQGJSAdapter;
	private CustomerXSXSAdapter mCustomerXSXSAdapter;

	private int mCurrentSegmentIndext = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("客户分配");

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerViewYqgj.setLayoutManager(layoutManager);
		mRecyclerViewYqgj.setHasFixedSize(true);
		mRecyclerViewYqgj.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		RecyclerView.LayoutManager layoutManagerXsxs = new LinearLayoutManager(this);
		mRecyclerViewXsxs.setLayoutManager(layoutManagerXsxs);
		mRecyclerViewXsxs.setHasFixedSize(true);
		mRecyclerViewXsxs.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

		getYQGJCustomer();
		getXSXSCustomer();
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_assign_customer;
	}

	@Override
	public void findViews() {
		mSegmentControl = (SegmentControl) findViewById(R.id.segment_control);
		mMultiStateViewYqgj = (MultiStateView) findViewById(R.id.multiStateViewYqgj);
		mRecyclerViewYqgj = (RecyclerView) findViewById(R.id.recycler_view_yqgj);
		mMultiStateViewXsxs = (MultiStateView) findViewById(R.id.multiStateViewXsxs);
		mRecyclerViewXsxs = (RecyclerView) findViewById(R.id.recycler_view_xsxs);
	}

	@Override
	public void setListener() {
		mSegmentControl.setOnSegmentControlClickListener(this);
		findViewById(R.id.assign).setOnClickListener(this);
	}

	@Override
	public void onSegmentControlClick(int index) {
		switch (index) {
		case 0:
			mCurrentSegmentIndext = 0;
			mMultiStateViewYqgj.setVisibility(View.VISIBLE);
			mMultiStateViewXsxs.setVisibility(View.GONE);
			break;
		case 1:
			mCurrentSegmentIndext = 1;
			mMultiStateViewYqgj.setVisibility(View.GONE);
			mMultiStateViewXsxs.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.assign) {
			if (mCurrentSegmentIndext == 0) {
				if (mCustomerYQGJSAdapter == null || mCustomerYQGJSAdapter.getCheckedId().size() == 0) {
					showToast("您还没有选择客户");
					return;
				}
				List<String> yqgjIds = mCustomerYQGJSAdapter.getCheckedId();
				StringBuffer sb = new StringBuffer();
				for (String s : yqgjIds) {
					sb.append(s).append(",");
				}
				String idsStr = "";
				if (sb.length() > 0) {
					idsStr = sb.substring(0, sb.length() - 1);
				}
				Intent intent = new Intent(this, EstateManagerActivity.class);
				intent.putExtra("customerIds", idsStr);
				intent.putExtra("flag", "0");
				startActivity(intent);
			} else if (mCurrentSegmentIndext == 1) {
				if (mCustomerXSXSAdapter == null || mCustomerXSXSAdapter.getCheckedId().size() == 0) {
					showToast("您还没有选择客户");
					return;
				}

				List<String> xsxsIds = mCustomerXSXSAdapter.getCheckedId();
				StringBuffer sb = new StringBuffer();
				for (String s : xsxsIds) {
					sb.append(s).append(",");
				}
				String idsStr = "";
				if (sb.length() > 0) {
					idsStr = sb.substring(0, sb.length() - 1);
				}
				Intent intent = new Intent(this, EstateManagerActivity.class);
				intent.putExtra("customerIds", idsStr);
				intent.putExtra("flag", "1");
				startActivity(intent);
			}
		}
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);

		if (REQUEST_GETYQGJCUSTOMER.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<CustomerYQGJ> cutomers = JSON.parseArray(data, CustomerYQGJ.class);
				if (cutomers == null) {
					showToast(R.string.response_invalid);
					mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (cutomers.size() == 0) {
					mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mCustomerYQGJSAdapter = new CustomerYQGJAdapter(this, cutomers);
				mRecyclerViewYqgj.setAdapter(mCustomerYQGJSAdapter);
				mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		} else if (REQUEST_GETXSXSCUSTOMER.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<CustomerXSXS> cutomers = JSON.parseArray(data, CustomerXSXS.class);
				if (cutomers == null) {
					showToast(R.string.response_invalid);
					mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (cutomers.size() == 0) {
					mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mCustomerXSXSAdapter = new CustomerXSXSAdapter(this, cutomers);
				mRecyclerViewXsxs.setAdapter(mCustomerXSXSAdapter);
				mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		if (REQUEST_GETYQGJCUSTOMER.equals(request.getRequestTag())) {
			mMultiStateViewYqgj.setViewState(MultiStateView.ViewState.ERROR);
		} else if (REQUEST_GETXSXSCUSTOMER.equals(request.getRequestTag())) {
			mMultiStateViewXsxs.setViewState(MultiStateView.ViewState.ERROR);
		}
	}

	private void getYQGJCustomer() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.getYQGJCustomer(), String.valueOf(projectId), userid, username);
		requestHttp(url, REQUEST_GETYQGJCUSTOMER, this);
	}

	private void getXSXSCustomer() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.getXSXSCustomer(), String.valueOf(projectId), userid, username);
		requestHttp(url, REQUEST_GETXSXSCUSTOMER, this);
	}
}
