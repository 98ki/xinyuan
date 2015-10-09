package com.xlj.erp.movefield.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.xlj.erp.movefield.adapter.CustomerAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.Customer;
import com.xlj.erp.movefield.ui.activity.AddCustomerActivity;
import com.xlj.erp.movefield.ui.activity.CustomerSearchAdvancedActivity;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;
import com.xlj.erp.movefield.widget.SwipeRecyclerViewOnScrollListener;

/**
 * 客户
 * 
 * @author chaohui.yang
 *
 */
public class CustomerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
	private static String REQUEST_GETMYCUSTOMERLIST = "getMyCustomerList";
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private MultiStateView mMultiStateView;
	private RecyclerView mRecyclerView;
	private EditText mEtSearchKey;

	private String cusAge = "";
	private String cusInterest = "";
	private String cusStatus = "";
	private String cusName = "";
	private String cusPhone = "";
	private String cusCondition = "";

	/**
	 * 第一次加载
	 */
	private boolean mFirstLoading = true;

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;

	public static CustomerFragment newInstance() {
		CustomerFragment customerFragment = new CustomerFragment();
		return customerFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_customer;
	}

	@Override
	public void findViews() {
		mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);
		mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
		mEtSearchKey = (EditText) mRootView.findViewById(R.id.search_key);
	}

	@Override
	public void setListener() {
		mSwipeRefreshLayout.setOnRefreshListener(this);

		mRootView.findViewById(R.id.advanced_search).setOnClickListener(this);

		mEtSearchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
					}
					cusName = mEtSearchKey.getText().toString().trim();
					cusPhone = mEtSearchKey.getText().toString().trim();
					search();
					return true;
				}
				return false;
			}

		});
	}

	@Override
	public void businessLogic() {
		setupToolbar();
		setHasOptionsMenu(true);
		// mSwipeRefreshLayout.setEnabled(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mParentActivity);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(mParentActivity, DividerItemDecoration.VERTICAL_LIST));
		mRecyclerView.setOnScrollListener(new SwipeRecyclerViewOnScrollListener(mSwipeRefreshLayout, layoutManager));
		getMyCustomerList(false);

		registerReceiver();
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 如果在网络请求，取消网络请求；停止swipe
			List<Customer> list = new ArrayList<Customer>();
			CustomerAdapter adapter = new CustomerAdapter(mParentActivity, list);
			mRecyclerView.setAdapter(adapter);

			mFirstLoading = true;
			getMyCustomerList(false);
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
		setTitle("我的客户");
		setDisplayShowTitleEnabled(true);
	}

	@Override
	public void onRefresh() {
		getMyCustomerList(false);
	}

	/**
	 * 网络请求：客户列表
	 */
	private void getMyCustomerList(boolean showDialog) {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		requestHttp(String.format(Urls.getMyCustomerList(), String.valueOf(projectId), userid, username, cusAge, cusInterest, cusStatus, cusName, cusPhone, cusCondition), REQUEST_GETMYCUSTOMERLIST,
				this, showDialog);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);

		if (REQUEST_GETMYCUSTOMERLIST.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					if (mFirstLoading) {
						mFirstLoading = false;
						mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					} else {
						mSwipeRefreshLayout.setRefreshing(false);
					}
					return;
				}
				String data = jsonObject.getString("result");
				List<Customer> list = JSON.parseArray(data, Customer.class);
				if (list == null) {
					showToast(R.string.response_invalid);
					if (mFirstLoading) {
						mFirstLoading = false;
						mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					} else {
						mSwipeRefreshLayout.setRefreshing(false);
					}
					return;
				}
				if (mFirstLoading) {
					mFirstLoading = false;
					mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
				} else {
					mSwipeRefreshLayout.setRefreshing(false);
				}

				CustomerAdapter adapter = new CustomerAdapter(mParentActivity, list);
				mRecyclerView.setAdapter(adapter);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				if (mFirstLoading) {
					mFirstLoading = false;
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				} else {
					mSwipeRefreshLayout.setRefreshing(false);
				}
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		showToast(R.string.network_or_server_invalid);
		if (mFirstLoading) {
			mFirstLoading = false;
			mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
		} else {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	private void search() {
		mFirstLoading = true;
		List<Customer> list = new ArrayList<Customer>();
		CustomerAdapter adapter = new CustomerAdapter(mParentActivity, list);
		mRecyclerView.setAdapter(adapter);
		getMyCustomerList(true);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.advanced_search) {
			Intent intent = new Intent(mParentActivity, CustomerSearchAdvancedActivity.class);
			intent.putExtra("cusAge", cusAge);
			intent.putExtra("cusInterest", cusInterest);
			intent.putExtra("cusStatus", cusStatus);
			intent.putExtra("cusCondition", cusCondition);
			startActivityForResult(intent, 0);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				cusAge = data.getStringExtra("cusAge");
				cusInterest = data.getStringExtra("cusInterest");
				cusStatus = data.getStringExtra("cusStatus");
				cusCondition = data.getStringExtra("cusCondition");
				search();
			} else if (requestCode == 1) {
				search();
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_customer, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_customer) {
			Intent intent = new Intent(mParentActivity, AddCustomerActivity.class);
			startActivityForResult(intent, 1);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
