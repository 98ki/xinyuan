package com.xlj.erp.movefield.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public class SwipeRecyclerViewOnScrollListener extends OnScrollListener {
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private LinearLayoutManager mLayoutManager;
	
	public SwipeRecyclerViewOnScrollListener(SwipeRefreshLayout swipeRefreshLayout,LinearLayoutManager layoutManager){
		mSwipeRefreshLayout = swipeRefreshLayout;
		mLayoutManager = layoutManager;
	}
	
	@Override
	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
	}
	
	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		System.out.println("dx:"+dx+",dy:"+dy);
		int firstVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition();
		if(firstVisibleItem == 0){
			mSwipeRefreshLayout.setEnabled(true);
		}else{
			mSwipeRefreshLayout.setEnabled(false);
		}
	}
}
