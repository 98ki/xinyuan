package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.SaleRecord;

public class SaleRecordAdapter extends RecyclerView.Adapter<SaleRecordAdapter.ViewHolder> {
	private Context mContext;
	private List<SaleRecord> mSaleRecords;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		public View mLine;
		public TextView mRecorddate;
		public TextView mRecordcontent;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mLine = itemView.findViewById(R.id.line);
			mRecorddate = (TextView) itemView.findViewById(R.id.recorddate);
			mRecordcontent = (TextView) itemView.findViewById(R.id.recordcontent);
		}
	}

	public SaleRecordAdapter(Context context, List<SaleRecord> saleRecords) {
		mContext = context;
		mSaleRecords = saleRecords;
	}

	@Override
	public int getItemCount() {
		return mSaleRecords.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		SaleRecord saleRecord = mSaleRecords.get(i);
		viewHolder.mRecorddate.setText(saleRecord.getRecorddate());
		viewHolder.mRecordcontent.setText(saleRecord.getRecordcontent());
		if (i == 0) {
			viewHolder.mLine.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_timer_shaft_header));
		} else {
			viewHolder.mLine.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_timer_shaft_normal));
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_record, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
}
