package com.xlj.erp.movefield.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.entity.HouseInfo;
import com.xlj.erp.movefield.ui.activity.PromoteRoomActivity;
import com.xlj.erp.movefield.ui.activity.WebViewActivity;

public class BuildingHouseInfoDialog extends BaseDialog {

	public BuildingHouseInfoDialog(final Context context, final HouseInfo houseInfo) {
		super(context);
		TextView houseNumber = (TextView) findViewById(R.id.house_number);
		TextView houseType = (TextView) findViewById(R.id.house_type);
		TextView houseAvgPrice = (TextView) findViewById(R.id.house_avg_price);
		TextView houseArea = (TextView) findViewById(R.id.house_area);
		TextView houseTotalPrice = (TextView) findViewById(R.id.house_total_price);

		houseNumber.setText(houseInfo.getUnitName() + houseInfo.getVhousenumber());
		houseType.setText(houseInfo.getRoomtypename());
		houseAvgPrice.setText(houseInfo.getHouseavgprice() + "元/平方米");
		houseArea.setText(houseInfo.getNjzarea() + "平方米");
		houseTotalPrice.setText(houseInfo.getHousetotalprice() + "元");
		findViewById(R.id.calc).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, WebViewActivity.class);
				intent.putExtra(Contants.WEB_VIEW_ACTIVITY_TITLE, context.getString(R.string.title_mortgage_calc));
				intent.putExtra(Contants.WEB_VIEW_ACTIVITY_URL, String.format(Urls.getMortgageCalc(), houseInfo.getHousetotalprice()));
				context.startActivity(intent);
				dismiss();
			}
		});
		findViewById(R.id.house_type_image).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PromoteRoomActivity.class);
				context.startActivity(intent);
				dismiss();
			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.dialog_building_house_info;
	}

}
