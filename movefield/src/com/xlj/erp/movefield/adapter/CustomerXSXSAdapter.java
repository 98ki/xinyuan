package com.xlj.erp.movefield.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.CustomerXSXS;

public class CustomerXSXSAdapter extends RecyclerView.Adapter<CustomerXSXSAdapter.ViewHolder> {
	private Context mContext;
	private List<CustomerXSXS> mCustomers;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		private TextView mName;
		private TextView mTel;
		private TextView mState;
		public TextView mTime;
		public CheckBox mChecked;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mChecked = (CheckBox) itemView.findViewById(R.id.checked);
			mName = (TextView) itemView.findViewById(R.id.name);
			mTel = (TextView) itemView.findViewById(R.id.tel);
			mState = (TextView) itemView.findViewById(R.id.state);
			mTime = (TextView) itemView.findViewById(R.id.time);
		}
	}

	public CustomerXSXSAdapter(Context context, List<CustomerXSXS> cutomers) {
		mContext = context;
		mCustomers = cutomers;
	}

	@Override
	public int getItemCount() {
		return mCustomers.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final CustomerXSXS customer = mCustomers.get(i);
		viewHolder.mName.setText(customer.getVcusname());
		viewHolder.mTel.setText(customer.getVtel());
		viewHolder.mState.setText(customer.getVcusstate());
		viewHolder.mTime.setText(customer.getGjTime());
		viewHolder.mChecked.setChecked(customer.isChecked());
		viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				customer.setChecked(!customer.isChecked());
				notifyDataSetChanged();
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_xsxs, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	public List<String> getCheckedId() {
		List<String> result = new ArrayList<String>();
		for (CustomerXSXS c : mCustomers) {
			if (c.isChecked()) {
				result.add(c.getId());
			}
		}
		return result;
	}
}
