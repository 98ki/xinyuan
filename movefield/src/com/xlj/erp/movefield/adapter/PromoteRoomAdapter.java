package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.PromoteRoom;
import com.xlj.erp.movefield.ui.activity.HouseTypeImageActivity;

/**
 * 主推户型adapter
 * 
 * @author chaohui.yang
 *
 */
public class PromoteRoomAdapter extends RecyclerView.Adapter<PromoteRoomAdapter.ViewHolder> {
	private Context mContext;
	private List<PromoteRoom> mPromoteRooms;

	private DisplayImageOptions options;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		View mItemView;
		ImageView mThumb;
		TextView mHouseType;
		TextView mArea;
		TextView mHouseNum;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mThumb = (ImageView) itemView.findViewById(R.id.thumb);
			mHouseType = (TextView) itemView.findViewById(R.id.house_type);
			mArea = (TextView) itemView.findViewById(R.id.area);
			mHouseNum = (TextView) itemView.findViewById(R.id.house_num);
		}
	}

	public PromoteRoomAdapter(Context context, List<PromoteRoom> promoteRooms) {
		mContext = context;
		mPromoteRooms = promoteRooms;
		ColorDrawable colorDrawable = new ColorDrawable(context.getResources().getColor(R.color.imageloader_default_color));
		options = new DisplayImageOptions.Builder().showImageOnLoading(colorDrawable).showImageForEmptyUri(colorDrawable).showImageOnFail(colorDrawable).cacheInMemory(true)
				.cacheOnDisk(true).build();
	}

	@Override
	public int getItemCount() {
		return mPromoteRooms.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final PromoteRoom promoteRoom = mPromoteRooms.get(i);
		ImageLoader.getInstance().displayImage(promoteRoom.getMiniHousePic(), viewHolder.mThumb, options);
		viewHolder.mHouseType.setText(promoteRoom.getHouseType());
		viewHolder.mArea.setText(promoteRoom.getArea() + mContext.getString(R.string.square_meter));
		viewHolder.mHouseNum.setText(promoteRoom.getHouseNum());

		viewHolder.mItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, HouseTypeImageActivity.class);
				intent.putExtra(Contants.PIC_URL, promoteRoom.getHousePicture());
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_promote_room, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
}
