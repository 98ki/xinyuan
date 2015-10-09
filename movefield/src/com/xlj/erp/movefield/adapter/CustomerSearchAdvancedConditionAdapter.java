package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xlj.erp.movefield.R;

public class CustomerSearchAdvancedConditionAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mConditions;
	private List<Boolean> mCheckedList;

	public CustomerSearchAdvancedConditionAdapter(Context context, List<String> conditions, List<Boolean> checkedList) {
		mContext = context;
		mConditions = conditions;
		mCheckedList = checkedList;
	}

	@Override
	public int getCount() {
		return mConditions.size();
	}

	@Override
	public Object getItem(int position) {
		return mConditions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_customer_search_advanced_condition, null);
			holder = new ViewHolder();
			holder.tvCondition = (TextView) convertView.findViewById(R.id.condition);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvCondition.setText(mConditions.get(position));
		if (mCheckedList.get(position)) {
			holder.tvCondition.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_customer_search_advanced_condition_checked));
		} else {
			holder.tvCondition.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_customer_search_advanced_condition_normal));
		}
		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mCheckedList.get(position)) {
					for (int i = 0; i < mCheckedList.size(); i++) {
						mCheckedList.set(i, false);
					}
				}
				mCheckedList.set(position, !mCheckedList.get(position));
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		TextView tvCondition;
	}

	public String getChecked() {
		String result = "";
		for (int i = 0; i < mCheckedList.size(); i++) {
			if (mCheckedList.get(i)) {
				result = mConditions.get(i);
				break;
			}
		}
		return result;
	}

}
