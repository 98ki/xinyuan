package com.xlj.erp.movefield.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.BacklogChildAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.BacklogInfo;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * Created by zpy on 7/22/15.
 */
public class BacklogChildFragment extends BaseFragment {
	private ExpandableStickyListHeadersListView mListView;
	private MultiStateView mMultiStateView;
	private BacklogChildAdapter mBacklogAdapter;
	private SwipeRefreshLayout refreshLayout;

	String[] HEADER_NAME;

	WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<View, Integer>();

	private static String REQUEST_GET_BACKLOG_INFO = "getBusinessInfo";
	private int mRequestParam;

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;

	/**
	 *
	 * @param params
	 *            (PARAM_TYPE_TODO ,PARAM_TYPE_OVERDUE)
	 * @return
	 */
	public static BacklogChildFragment newInstance(int params) {
		BacklogChildFragment todoTaskFragment = new BacklogChildFragment();
		Bundle args = new Bundle();
		args.putInt("params", params);
		todoTaskFragment.setArguments(args);
		return todoTaskFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_backlog_child;
	}

	@Override
	public void findViews() {
		mListView = (ExpandableStickyListHeadersListView) mRootView.findViewById(R.id.fragment_to_task_list);
		mListView.setAnimExecutor(new AnimationExecutor());
		mMultiStateView = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		refreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_layout);

	}

	@Override
	public void setListener() {
		mListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
			@Override
			public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
				if (mListView.isHeaderCollapsed(headerId)) {
					mListView.expand(headerId);
				} else {
					mListView.collapse(headerId);
				}
			}
		});

		mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				businessLogic();
			}
		});
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						refreshLayout.setRefreshing(false);
						businessLogic();
						mBacklogAdapter.notifyDataSetChanged();
					}
				}, 1000);
			}
		});
	}

	@Override
	public void businessLogic() {
		mRequestParam = getArguments().getInt("params");
		getBusinessInfo();
		registerReceiver();
	}

	@Override
	public void setupToolbar() {

	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_BACKLOG_INFO.equals(request.getRequestTag())) {
			JSONObject jsonObject;
			try {
				jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				// 获取 Key 的集合（sticky header的展示内容）
				JSONObject data = jsonObject.getJSONObject("result");
				Set<String> keys = data.keySet();
				int size = keys.size();
				HEADER_NAME = keys.toArray(new String[size]);

				// 获取 sticky header list， 放在一起 记录第一个区的起始位置 mSectionIndices
				List<BacklogInfo> mList = new ArrayList<BacklogInfo>();
				int[] mSectionIndices = new int[size];
				mSectionIndices[0] = 0;
				int index = 0;
				boolean isNull = true;
				for (int i = 0; i < size; i++) {
					// 得到 List

					List<BacklogInfo> tempList = new ArrayList<BacklogInfo>();
					tempList = JSON.parseArray(data.getString(HEADER_NAME[i]), BacklogInfo.class);
					if (tempList == null) {
						continue;
					}
					isNull = false;
					// 设置 headerID 相同分类 id相同，从1开始记数
					for (int j = 0; j < tempList.size(); j++) {
						tempList.get(j).setHeaderId(i + 1);
						tempList.get(j).setHeader(HEADER_NAME[i]);
					}
					mList.addAll(tempList);

					// 得到每个 stickyHeader 的位置
					index += tempList.size();
					if (i < size - 1) {
						mSectionIndices[i + 1] = index;
					}

				}
				if (isNull) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				mBacklogAdapter = new BacklogChildAdapter(getActivity(), mList, HEADER_NAME, mSectionIndices, mRequestParam);
				mListView.setAdapter(mBacklogAdapter);
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
		if (REQUEST_GET_BACKLOG_INFO.equals(request.getRequestTag())) {
			showToast(R.string.network_or_server_invalid);
			mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
		}
	}

	// animation executor
	class AnimationExecutor implements ExpandableStickyListHeadersListView.IAnimationExecutor {

		@Override
		public void executeAnim(final View target, final int animType) {
			if (ExpandableStickyListHeadersListView.ANIMATION_EXPAND == animType && target.getVisibility() == View.VISIBLE) {
				return;
			}
			if (ExpandableStickyListHeadersListView.ANIMATION_COLLAPSE == animType && target.getVisibility() != View.VISIBLE) {
				return;
			}
			if (mOriginalViewHeightPool.get(target) == null) {
				mOriginalViewHeightPool.put(target, target.getHeight());
			}
			final int viewHeight = mOriginalViewHeightPool.get(target);
			float animStartY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? 0f : viewHeight;
			float animEndY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? viewHeight : 0f;
			final ViewGroup.LayoutParams lp = target.getLayoutParams();
			ValueAnimator animator = ValueAnimator.ofFloat(animStartY, animEndY);
			animator.setDuration(200);
			target.setVisibility(View.VISIBLE);
			animator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animator) {
				}

				@Override
				public void onAnimationEnd(Animator animator) {
					if (animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND) {
						target.setVisibility(View.VISIBLE);
					} else {
						target.setVisibility(View.GONE);
					}
					target.getLayoutParams().height = viewHeight;
				}

				@Override
				public void onAnimationCancel(Animator animator) {

				}

				@Override
				public void onAnimationRepeat(Animator animator) {

				}
			});
			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					lp.height = ((Float) valueAnimator.getAnimatedValue()).intValue();
					target.setLayoutParams(lp);
					target.requestLayout();
				}
			});
			animator.start();

		}
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			getBusinessInfo();
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

	private void getBusinessInfo() {
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		String userId = UserManager.getUser().getUserid();
		String userName = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getBusinessInfo(), String.valueOf(projectId), userId, userName, String.valueOf(mRequestParam)), REQUEST_GET_BACKLOG_INFO, this);
	}
}
