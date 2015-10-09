package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.HouseSearchAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.BuildingInfo;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;

public class HouseSearchActivity extends BaseToolbarActivity {
	private static String REQUEST_GET_BULIDINGS_BY_PROJECT_ID = "getBulidingsByProjectId";
	private TextView mProjectName;
	private MultiStateView mMultiStateView;
	private RecyclerView mRecyclerView;
	private HouseSearchAdapter mHouseSearchAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.title_house_search);

		String projectName = UserManager.getCurrentProject(this).getName();
		if (!TextUtils.isEmpty(projectName)) {
			mProjectName.setText(projectName);
		}

		getBulidingsByProjectId();
		mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				getBulidingsByProjectId();
			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_house_search;
	}

	@Override
	public void findViews() {
		mProjectName = (TextView) findViewById(R.id.project_name);
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
	}

	@Override
	public void setListener() {

	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_BULIDINGS_BY_PROJECT_ID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<BuildingInfo> buildingInfos = JSON.parseArray(data, BuildingInfo.class);
				if (buildingInfos == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				if (buildingInfos.size() == 0) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mHouseSearchAdapter = new HouseSearchAdapter(this, buildingInfos);
				mRecyclerView.setAdapter(mHouseSearchAdapter);

				RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
				mRecyclerView.setLayoutManager(layoutManager);
				mRecyclerView.setHasFixedSize(true);
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
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
		if (REQUEST_GET_BULIDINGS_BY_PROJECT_ID.equals(request.getRequestTag())) {
			mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
		}
	}

	private void getBulidingsByProjectId() {
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getBulidingsByProjectId(), String.valueOf(projectId), username), REQUEST_GET_BULIDINGS_BY_PROJECT_ID, this);
	}

}
