package com.xlj.erp.movefield.adapter;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.BacklogInfo;
import com.xlj.erp.movefield.ui.activity.CustomerDetailActivity;
import com.xlj.erp.movefield.utils.Utils;

public class BacklogChildAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer, View.OnClickListener, AdapterView.OnItemClickListener {

	private Context mContext;
	private int[] mSectionIndices;
	private String[] mSectionString;
	private LayoutInflater mInflater;

	/**
	 * 待办提醒0，业务催办1
	 */
	private int mType;

	List<BacklogInfo> mList = new ArrayList<BacklogInfo>();

	public BacklogChildAdapter(Context context, List<BacklogInfo> list, String[] HEADER_NAME, int[] sectionIndices, int type) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mList = list;
		mSectionIndices = sectionIndices;
		mSectionString = HEADER_NAME;
		mType = type;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_backlog_list, parent, false);
			holder.nameView = (TextView) convertView.findViewById(R.id.item_todo_task_tv_name);
			holder.headerView = (ImageView) convertView.findViewById(R.id.item_todo_task_iv_header);
			holder.interestView = (TextView) convertView.findViewById(R.id.item_todo_task_tv_interestdegree);
			holder.timesView = (TextView) convertView.findViewById(R.id.item_todo_task_tv_followtimes);
			holder.updateView = (TextView) convertView.findViewById(R.id.item_todo_task_tv_lastfollowtime);

			holder.smsView = (ImageView) convertView.findViewById(R.id.item_todo_task_iv_sms);
			holder.phoneView = (ImageView) convertView.findViewById(R.id.item_todo_task_iv_phone);
			holder.divider = convertView.findViewById(R.id.divider);

			holder.smsView.setOnClickListener(this);
			holder.smsView.setTag(position);
			holder.phoneView.setOnClickListener(this);
			holder.phoneView.setTag(position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final BacklogInfo bean = mList.get(position);
		if (bean.getCustomersex().equals("男")) {
			holder.headerView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_backlog_male));
		} else {
			holder.headerView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_backlog_female));
		}

		holder.nameView.setText(bean.getCustomername());

		if (TextUtils.isEmpty(bean.getInterestdegree())) {
			holder.interestView.setText("");
		} else {
			String interestdegree = bean.getInterestdegree();
			holder.interestView.setText(interestdegree);
			if ("高意向".equals(interestdegree)) {
				holder.interestView.setTextColor(mContext.getResources().getColor(R.color.backlog_interest_degree_hight));
			} else if ("一般意向".equals(interestdegree)) {
				holder.interestView.setTextColor(mContext.getResources().getColor(R.color.backlog_interest_degree_general));
			} else if ("无意向".equals(interestdegree)) {
				holder.interestView.setTextColor(mContext.getResources().getColor(R.color.backlog_interest_degree_no));
			} else if ("必买".equals(interestdegree)) {
				holder.interestView.setTextColor(mContext.getResources().getColor(R.color.backlog_interest_degree_buy));
			}
			if (mType == 1 && "催办签约".equals(bean.getHeader())) {
				holder.interestView.setVisibility(View.GONE);
			} else {
				holder.interestView.setVisibility(View.VISIBLE);
			}
		}

		holder.timesView.setText(bean.getFollowtimes() + "");

		if (mType == 0) {
			holder.updateView.setText("上次跟进时间：" + bean.getLastfollowtime());
		} else if (mType == 1) {
			if ("催办认购".equals(bean.getHeader())) {
				holder.updateView.setText("预约日期：" + bean.getSubscribedate() + "  " + bean.getOverduefollowday() + "天");
			} else if ("逾期跟进".equals(bean.getHeader())) {
				holder.updateView.setText("预约日期：" + bean.getLastfollowtime() + "  " + bean.getOverduefollowday() + "天");
			} else if ("催办签约".equals(bean.getHeader())) {
				holder.updateView.setText("认购日期：" + bean.getSigndate() + "  " + bean.getOverduesignday() + "天");
			}
		}

		int nextPosition = position + 1;
		if (nextPosition < mList.size()) {
			if (mList.get(nextPosition).getHeaderId() == mList.get(position).getHeaderId()) {
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.divider.getLayoutParams();
				params.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.backlog_child_item_divider_margin_left);
				holder.divider.setLayoutParams(params);
			} else {
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.divider.getLayoutParams();
				params.leftMargin = 0;
				holder.divider.setLayoutParams(params);
			}
		}

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CustomerDetailActivity.class);
				intent.putExtra("customerid", String.valueOf(bean.getCustomerid()));
				intent.putExtra("customername", bean.getCustomername());
				mContext.startActivity(intent);
			}
		});

		return convertView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;

		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = mInflater.inflate(R.layout.item_backlog_list_header, parent, false);
			holder.typeNameView = (TextView) convertView.findViewById(R.id.header_sticky_list_header_tv);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		holder.typeNameView.setText(mSectionString[mList.get(position).getHeaderId() - 1]);

		return convertView;
	}

	/**
	 * Remember that these have to be static, postion=1 should always return the
	 * same Id that is.
	 */
	@Override
	public long getHeaderId(int position) {
		// return the first character of the country as ID because this is what
		// headers are based upon

		return mList.get(position).getHeaderId();
	}

	@Override
	public int getPositionForSection(int section) {
		if (mSectionIndices.length == 0) {
			return 0;
		}

		if (section >= mSectionIndices.length) {
			section = mSectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return mSectionIndices[section];
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < mSectionIndices.length; i++) {
			if (position < mSectionIndices[i]) {
				return i - 1;
			}
		}
		return mSectionIndices.length - 1;
	}

	@Override
	public Object[] getSections() {
		return mSectionString;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.item_todo_task_iv_phone) {
			String phone = mList.get((Integer) v.getTag()).getPhone();
			Utils.callPhone(mContext, phone);
		} else if (v.getId() == R.id.item_todo_task_iv_sms) {
			String phone = mList.get((Integer) v.getTag()).getPhone();
			Utils.sms(mContext, phone);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	class HeaderViewHolder {
		TextView typeNameView;
	}

	class ViewHolder {
		ImageView headerView;
		TextView nameView;
		TextView interestView;
		TextView timesView;
		TextView updateView;
		ImageView smsView;
		ImageView phoneView;
		View divider;

	}

}
