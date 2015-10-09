package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.PromoteRoomAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.PromoteRoomList;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;
import com.xlj.erp.movefield.widget.MultiStateView;
import com.xlj.erp.movefield.widget.SegmentControl;

/**
 * 主推户型
 * 
 * @author chaohui.yang
 *
 */
public class PromoteRoomActivity extends BaseToolbarActivity implements SegmentControl.OnSegmentControlClickListener {
	private static String REQUEST_GET_PROMOTE_ROOM = "getPromoteRoom";
	private MultiStateView mMultiStateView;
	private SegmentControl mSegmentControl;
	private RecyclerView mRecyclerView;

	private List<PromoteRoomList> mPromoteRoomLists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_promote_room);
		getPromoteRoom();
		mSegmentControl.setOnSegmentControlClickListener(this);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				getPromoteRoom();
			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_promote_room;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mSegmentControl = (SegmentControl) findViewById(R.id.segment_control);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
	}

	@Override
	public void setListener() {

	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_PROMOTE_ROOM.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				mPromoteRoomLists = JSON.parseArray(data, PromoteRoomList.class);
				if (mPromoteRoomLists == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				setSegmentControlTexts();
				switchPromoteRoomData(0);
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
		mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
	}

	/**
	 * 网络请求：获取主推户型
	 */
	private void getPromoteRoom() {
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		requestHttp(String.format(Urls.getPromoteRoom(), String.valueOf(projectId)), REQUEST_GET_PROMOTE_ROOM, this);
	}

	@Override
	public void onSegmentControlClick(int index) {
		switchPromoteRoomData(index);
	}

	private void setSegmentControlTexts() {
		String[] textArr = getSegments();
		if (textArr != null) {
			mSegmentControl.setText(textArr);
		}
	}

	private String[] getSegments() {
		if (mPromoteRoomLists == null || mPromoteRoomLists.size() == 0) {
			return null;
		}
		String[] textArr = new String[mPromoteRoomLists.size()];
		for (int i = 0; i < mPromoteRoomLists.size(); i++) {
			textArr[i] = mPromoteRoomLists.get(i).getHouseType();
		}
		return textArr;
	}

	private void switchPromoteRoomData(int index) {
		PromoteRoomAdapter adapter = new PromoteRoomAdapter(this, mPromoteRoomLists.get(index).getToPromoteHouseList());
		mRecyclerView.setAdapter(adapter);
	}

}
