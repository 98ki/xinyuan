package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.BuildingSearchConditionKey;

public class BuildingSearchConditionAdapter extends BaseAdapter {

	private Context mContext;
	private List<BuildingSearchConditionKey> mConditionList;

	public BuildingSearchConditionAdapter(Context context, List<BuildingSearchConditionKey> conditionList) {
		mContext = context;
		mConditionList = conditionList;
	}

	@Override
	public int getCount() {
		return mConditionList.size();
	}

	@Override
	public Object getItem(int position) {
		return mConditionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_building_search_condition, parent, false);
			holder = new ViewHolder();
			holder.conditionCheck = (CheckBox) convertView.findViewById(R.id.condition_check);
			holder.condition = (TextView) convertView.findViewById(R.id.condition);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BuildingSearchConditionKey conditionKey = mConditionList.get(position);
		holder.condition.setText(conditionKey.getCondition());
		holder.conditionCheck.setChecked(conditionKey.isChecked());
		return convertView;
	}

	private static class ViewHolder {
		CheckBox conditionCheck;
		TextView condition;
	}

}
