package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.Customer;
import com.xlj.erp.movefield.ui.activity.CustomerDetailActivity;
import com.xlj.erp.movefield.utils.Utils;

/**
 * 客户Adapter
 * 
 * @author chaohui.yang
 *
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
	private Context mContext;
	private List<Customer> mCustomers;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		public TextView mCustomerName;
		public TextView mPhone;
		public ImageView mSms;
		public ImageView mDial;
		public TextView mStatus;
		public TextView mInterestdegree;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mCustomerName = (TextView) itemView.findViewById(R.id.customer_name);
			mPhone = (TextView) itemView.findViewById(R.id.phone);
			mSms = (ImageView) itemView.findViewById(R.id.sms);
			mDial = (ImageView) itemView.findViewById(R.id.dial);
			mStatus = (TextView) itemView.findViewById(R.id.status);
			mInterestdegree = (TextView) itemView.findViewById(R.id.interestdegree);
		}
	}

	public CustomerAdapter(Context context, List<Customer> customers) {
		mContext = context;
		mCustomers = customers;
	}

	@Override
	public int getItemCount() {
		return mCustomers.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final Customer customer = mCustomers.get(i);
		viewHolder.mCustomerName.setText(customer.getCustomername());
		viewHolder.mPhone.setText(customer.getPhone());
		viewHolder.mStatus.setText(customer.getStatus());
		viewHolder.mInterestdegree.setText(customer.getInterestdegree());
		viewHolder.mItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CustomerDetailActivity.class);
				intent.putExtra("customerid", String.valueOf(customer.getCustomerid()));
				intent.putExtra("customername", customer.getCustomername());
				mContext.startActivity(intent);
			}
		});
		viewHolder.mSms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.sms(mContext, customer.getPhone());
			}
		});
		viewHolder.mDial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.callPhone(mContext, customer.getPhone());
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_customer, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
}
