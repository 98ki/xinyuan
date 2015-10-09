package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.FollowRecord;

public class FollowRecordAdapter extends RecyclerView.Adapter<FollowRecordAdapter.ViewHolder> {
	private Context mContext;
	private List<FollowRecord> mFollowRecords;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		public TextView mFollowcontent;
		public TextView mFollowtime;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mFollowcontent = (TextView) itemView.findViewById(R.id.followcontent);
			mFollowtime = (TextView) itemView.findViewById(R.id.followtime);
		}
	}

	public FollowRecordAdapter(Context context, List<FollowRecord> followRecords) {
		mContext = context;
		mFollowRecords = followRecords;
	}

	@Override
	public int getItemCount() {
		return mFollowRecords.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		FollowRecord followRecord = mFollowRecords.get(i);
		viewHolder.mFollowcontent.setText(followRecord.getFollowcontent());
		viewHolder.mFollowtime.setText(followRecord.getFollowtime());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_follow_record, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
}
