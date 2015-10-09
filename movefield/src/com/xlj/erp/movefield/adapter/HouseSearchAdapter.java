package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.BuildingInfo;
import com.xlj.erp.movefield.ui.activity.BuildingSearchActivity;

public class HouseSearchAdapter extends RecyclerView.Adapter<HouseSearchAdapter.ViewHolder> {
	private Context mContext;
	private List<BuildingInfo> mBuildingInfos;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		View mItemView;
		TextView mBuildingName;
		TextView mSaleCount;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mBuildingName = (TextView) itemView.findViewById(R.id.building_name);
			mSaleCount = (TextView) itemView.findViewById(R.id.sale_count);
		}
	}

	public HouseSearchAdapter(Context context,List<BuildingInfo> buildingInfos) {
		mContext = context;
		mBuildingInfos = buildingInfos;
	}

	@Override
	public int getItemCount() {
		return mBuildingInfos.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		BuildingInfo buildingInfo = mBuildingInfos.get(i);
		viewHolder.mBuildingName.setText(buildingInfo.getVbuildname());
		viewHolder.mSaleCount.setText(buildingInfo.getShellCount()+"套可售");
		final String buildingId = buildingInfo.getBuildid();
		viewHolder.mItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, BuildingSearchActivity.class);
				intent.putExtra("buildingId", buildingId);
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_house_search, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
}
