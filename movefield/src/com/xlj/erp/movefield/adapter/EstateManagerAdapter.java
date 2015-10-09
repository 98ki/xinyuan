package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.KeyValue;

/**
 * 项目切换界面RecyclerView的Adapter
 * 
 * @author chaohui.yang
 *
 */
public class EstateManagerAdapter extends RecyclerView.Adapter<EstateManagerAdapter.ViewHolder> {
	private List<KeyValue> mEstateManagerList;

	private KeyValue mCheckedManager;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		public CheckBox mChecked;
		public TextView mName;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mChecked = (CheckBox) itemView.findViewById(R.id.checked);
			mName = (TextView) itemView.findViewById(R.id.name);
		}
	}

	public EstateManagerAdapter(List<KeyValue> estateManagerList) {
		mEstateManagerList = estateManagerList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estate_manager, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final KeyValue manager = mEstateManagerList.get(i);
		viewHolder.mName.setText(manager.getValue());
		viewHolder.mChecked.setChecked(manager.isChecked());
		viewHolder.mItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!manager.isChecked()) {
					setAllUnChecked();
					manager.setChecked(true);
					mCheckedManager = manager;
					notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mEstateManagerList.size();
	}

	private void setAllUnChecked() {
		for (KeyValue kv : mEstateManagerList) {
			kv.setChecked(false);
		}
	}

	public KeyValue getCheckedManager() {
		return mCheckedManager;
	}
}
