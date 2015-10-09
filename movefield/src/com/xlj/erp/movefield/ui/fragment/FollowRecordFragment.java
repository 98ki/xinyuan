package com.xlj.erp.movefield.ui.fragment;

import java.util.List;

import android.app.Activity;
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
import com.xlj.erp.movefield.adapter.FollowRecordAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.FollowRecord;
import com.xlj.erp.movefield.ui.activity.AddFollowRecordActivity;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;

public class FollowRecordFragment extends BaseFragment {
	private static String REQUEST_GETFOLLOWRECORD = "getFollowRecord";
	private MultiStateView mMultiStateView;
	private RecyclerView mRecyclerView;

	private String mCustomerid;

	public static FollowRecordFragment newInstance(String customerid) {
		FollowRecordFragment followRecordFragment = new FollowRecordFragment();
		Bundle args = new Bundle();
		args.putSerializable("customerid", customerid);
		followRecordFragment.setArguments(args);
		return followRecordFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_follow_record;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
	}

	@Override
	public void setListener() {
		mRootView.findViewById(R.id.add_follow_record).setOnClickListener(this);
	}

	@Override
	public void businessLogic() {
		mCustomerid = getArguments().getString("customerid");
		LinearLayoutManager layoutManager = new LinearLayoutManager(mParentActivity);
		mRecyclerView.setLayoutManager(layoutManager);
		// mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(mParentActivity, DividerItemDecoration.VERTICAL_LIST));
		getFollowRecord();
	}

	@Override
	public void setupToolbar() {

	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETFOLLOWRECORD.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<FollowRecord> followRecords = JSON.parseArray(data, FollowRecord.class);
				if (followRecords == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (followRecords.size() == 0) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
				FollowRecordAdapter adapter = new FollowRecordAdapter(mParentActivity, followRecords);
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

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.add_follow_record) {
			Intent intent = new Intent(mParentActivity, AddFollowRecordActivity.class);
			intent.putExtra("customerid", mCustomerid);
			startActivityForResult(intent, 0);
		}
	}

	private void getFollowRecord() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		String url = String.format(Urls.getFollowRecord(), String.valueOf(projectId), userid, username, mCustomerid);
		requestHttp(url, REQUEST_GETFOLLOWRECORD, this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			getFollowRecord();
		}
	}

}
