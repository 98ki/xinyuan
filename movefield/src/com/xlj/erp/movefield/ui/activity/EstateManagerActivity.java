package com.xlj.erp.movefield.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.EstateManagerAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.KeyValue;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * 分配置业经理界面
 * 
 * @author chaohui.yang
 *
 */
public class EstateManagerActivity extends BaseToolbarActivity {
	private static String REQUEST_GETESTATEMANAGER = "getEstateManager";
	private static String REQUEST_DISTRIBUTIONCUSTOMER = "distributionCustomer";

	private MultiStateView mMultiStateView;
	private RecyclerView mRecyclerView;
	private EditText mEtManagerName;

	private EstateManagerAdapter mEstateManagerAdapter;
	private String mCustomerIds;
	private String mFlag;

	private String managerName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("客户分配");

		Intent intent = getIntent();
		mCustomerIds = intent.getStringExtra("customerIds");
		mFlag = intent.getStringExtra("flag");

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

		getEstateManager();
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_estate_manager;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mEtManagerName = (EditText) findViewById(R.id.manager_name);
	}

	@Override
	public void setListener() {
		findViewById(R.id.assign).setOnClickListener(this);
		mEtManagerName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
					}
					managerName = mEtManagerName.getText().toString().trim();
					mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
					mEstateManagerAdapter = new EstateManagerAdapter(new ArrayList<KeyValue>());
					mRecyclerView.setAdapter(mEstateManagerAdapter);
					getEstateManager();
					return true;
				}
				return false;
			}

		});
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETESTATEMANAGER.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<KeyValue> managers = JSON.parseArray(data, KeyValue.class);
				if (managers == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (managers.size() == 0) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mEstateManagerAdapter = new EstateManagerAdapter(managers);
				mRecyclerView.setAdapter(mEstateManagerAdapter);
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		} else if (REQUEST_DISTRIBUTIONCUSTOMER.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				showToast("分配成功");
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		if (REQUEST_GETESTATEMANAGER.equals(request.getRequestTag())) {
			mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
		} else if (REQUEST_DISTRIBUTIONCUSTOMER.equals(request.getRequestTag())) {
			showToast(R.string.network_or_server_invalid);
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.assign) {
			if (mEstateManagerAdapter == null || mEstateManagerAdapter.getCheckedManager() == null) {
				showToast("您还没有置业经理");
				return;
			}
			String managerId = mEstateManagerAdapter.getCheckedManager().getKey();
			distributionCustomer(managerId);
		}
	}

	private void getEstateManager() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.getEstateManager(), String.valueOf(projectId), userid, username, managerName);
		requestHttp(url, REQUEST_GETESTATEMANAGER, this);
	}

	private void distributionCustomer(String managerId) {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.distributionCustomer(), String.valueOf(projectId), userid, username, managerName, mCustomerIds, managerId, mFlag);
		requestHttp(url, REQUEST_DISTRIBUTIONCUSTOMER, this, true);
	}

}
