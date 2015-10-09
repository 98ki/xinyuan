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
import com.xlj.erp.movefield.entity.CustomerYQGJ;

public class CustomerYQGJAdapter extends RecyclerView.Adapter<CustomerYQGJAdapter.ViewHolder> {
	private Context mContext;
	private List<CustomerYQGJ> mCustomers;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		private TextView mName;
		private TextView mWithoutday;
		private TextView mSalesheader;
		private TextView mState;
		public TextView mIntention;
		public CheckBox mChecked;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mChecked = (CheckBox) itemView.findViewById(R.id.checked);
			mName = (TextView) itemView.findViewById(R.id.name);
			mWithoutday = (TextView) itemView.findViewById(R.id.withoutday);
			mSalesheader = (TextView) itemView.findViewById(R.id.salesheader);
			mState = (TextView) itemView.findViewById(R.id.state);
			mIntention = (TextView) itemView.findViewById(R.id.intention);
		}
	}

	public CustomerYQGJAdapter(Context context, List<CustomerYQGJ> cutomers) {
		mContext = context;
		mCustomers = cutomers;
	}

	@Override
	public int getItemCount() {
		return mCustomers.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final CustomerYQGJ customer = mCustomers.get(i);
		viewHolder.mName.setText(customer.getVcusname());
		viewHolder.mWithoutday.setText(customer.getWithoutday());
		viewHolder.mSalesheader.setText(customer.getSalesheader());
		viewHolder.mState.setText(customer.getVcusstate());
		viewHolder.mIntention.setText(customer.getVcintention());
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
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer_yqgj, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	public List<String> getCheckedId() {
		List<String> result = new ArrayList<String>();
		for (CustomerYQGJ c : mCustomers) {
			if (c.isChecked()) {
				result.add(c.getId());
			}
		}
		return result;
	}
}
