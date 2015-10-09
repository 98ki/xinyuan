package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.dialog.BuildingHouseInfoDialog;
import com.xlj.erp.movefield.entity.HouseInfo;

public class BuildingSearchDetailAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {
	private Context mContext;
	private List<HouseInfo> mHouseInfoList;

	public BuildingSearchDetailAdapter(Context context, List<HouseInfo> houseInfoList) {
		mContext = context;
		mHouseInfoList = houseInfoList;
	}

	@Override
	public int getCount() {
		return mHouseInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return mHouseInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_building_house, parent, false);
			holder = new ViewHolder();
			holder.houseNumber = (TextView) convertView.findViewById(R.id.house_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final HouseInfo houseInfo = (HouseInfo) getItem(position);
		holder.houseNumber.setText(houseInfo.getVhousenumber());
		if (houseInfo.getVsalestatus() == 1) {
			holder.houseNumber.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_btn_building_house_grid));
		} else {
			holder.houseNumber.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_btn_building_house_grid_forsale));
		}
		holder.houseNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BuildingHouseInfoDialog dialog = new BuildingHouseInfoDialog(mContext, houseInfo);
				dialog.show();
			}
		});
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return mHouseInfoList.get(position).getHeaderId();
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_building_house_header, parent, false);
			holder = new HeaderViewHolder();
			holder.header = (TextView) convertView;
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		HouseInfo houseInfo = (HouseInfo) getItem(position);
		holder.header.setText(houseInfo.getRealUnitName());
		return convertView;
	}

	private static class ViewHolder {
		TextView houseNumber;
	}

	private static class HeaderViewHolder {
		TextView header;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

}
