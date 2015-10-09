package com.xlj.erp.movefield.ui.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.SaleRecordAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.SaleRecord;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * 销售记录
 * 
 * @author chaohui.yang
 *
 */
public class SaleRecordFragment extends BaseFragment {
	private static String REQUEST_GETSALESRECORD = "getSalesRecord";
	private MultiStateView mMultiStateView;
	private RecyclerView mRecyclerView;

	private String mCustomerid;

	public static SaleRecordFragment newInstance(String customerid) {
		SaleRecordFragment saleRecordFragment = new SaleRecordFragment();
		Bundle args = new Bundle();
		args.putSerializable("customerid", customerid);
		saleRecordFragment.setArguments(args);
		return saleRecordFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_sale_record;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
	}

	@Override
	public void setListener() {
	}

	@Override
	public void businessLogic() {
		mCustomerid = getArguments().getString("customerid");
		LinearLayoutManager layoutManager = new LinearLayoutManager(mParentActivity);
		mRecyclerView.setLayoutManager(layoutManager);
		// mRecyclerView.setHasFixedSize(true);
		//mRecyclerView.addItemDecoration(new DividerItemDecoration(mParentActivity, DividerItemDecoration.VERTICAL_LIST));
		getSalesRecord();
	}

	@Override
	public void setupToolbar() {
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETSALESRECORD.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<SaleRecord> saleRecords = JSON.parseArray(data, SaleRecord.class);
				if (saleRecords == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (saleRecords.size() == 0) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
				SaleRecordAdapter adapter = new SaleRecordAdapter(mParentActivity, saleRecords);
				mRecyclerView.setAdapter(adapter);
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

	private void getSalesRecord() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		requestHttp(String.format(Urls.getSalesRecord(), String.valueOf(projectId), userid, username, mCustomerid), REQUEST_GETSALESRECORD, this);
	}
}
