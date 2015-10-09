package com.xlj.erp.movefield.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.BuildingSearchConditionAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.BuildingSearchConditionKey;
import com.xlj.erp.movefield.entity.KeyValue;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.widget.LinearListView;
import com.xlj.erp.movefield.widget.LinearListView.OnItemClickListener;

public class BuildingSearchAdvancedActivity extends BaseToolbarActivity {
	private static String REQUEST_GETUNITSBYBUILDINGID = "getUnitsByBuildingId";
	private static String REQUEST_GETROOMTYPESBYUNIT = "getRoomTypesByUnit";
	private static String REQUEST_GETROOMAREABYUNITORRTYPE = "getRoomAreaByUnitOrRType";

	private final static int TYPE_UNIT_NAME = 0;
	private final static int TYPE_ROOM_TYPE = 1;
	private final static int TYPE_ROOM_AREA = 2;

	private View mSearchConditionDivider;
	private EditText mMinPrice;
	private EditText mMaxPrice;
	private TextView mStartFloor;
	private TextView mEndFloor;

	private TextView mTvSearchUnit;
	private TextView mTvSearchRoomtype;
	private TextView mTvSearchArea;

	// 查询条件
	private String mCheckedUnitName = "";
	private String mCheckedRoomType = "";
	private String mCheckedRoomArea = "";
	private String mCheckedStartFloor = "";
	private String mCheckedEndFloor = "";
	private String mCheckedMinPrice = "";
	private String mCheckedMaxPrice = "";

	private int mScreenWidth;
	private int mScreenHeight;

	private String mBuildingId;

	private List<KeyValue> mFloorList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("高级筛选");
		getScreenWidthHeight();
		Intent intent = getIntent();
		mBuildingId = intent.getStringExtra("buildingId");
		mCheckedUnitName = intent.getStringExtra("unitname");
		mCheckedRoomType = intent.getStringExtra("roomtype");
		mCheckedRoomArea = intent.getStringExtra("roomarea");
		mCheckedStartFloor = intent.getStringExtra("startfloor");
		mCheckedEndFloor = intent.getStringExtra("endfloor");
		mCheckedMinPrice = intent.getStringExtra("minprice");
		mCheckedMaxPrice = intent.getStringExtra("maxprice");
		mFloorList = (List<KeyValue>) intent.getSerializableExtra("floorList");

		if (TextUtils.isEmpty(mCheckedUnitName)) {
			mTvSearchUnit.setText("单元");
		} else {
			mTvSearchUnit.setText(mCheckedUnitName);
		}

		if (TextUtils.isEmpty(mCheckedRoomType)) {
			mTvSearchRoomtype.setText("户型");
		} else {
			mTvSearchRoomtype.setText(mCheckedRoomType);
		}

		if (TextUtils.isEmpty(mCheckedRoomArea)) {
			mTvSearchArea.setText("面积");
		} else {
			mTvSearchArea.setText(mCheckedRoomArea);
		}
		mMinPrice.setText(mCheckedMinPrice);
		mMaxPrice.setText(mCheckedMaxPrice);
		if (!TextUtils.isEmpty(mCheckedStartFloor)) {
			for (KeyValue kv : mFloorList) {
				if (kv.getKey().equals(mCheckedStartFloor)) {
					mStartFloor.setText(kv.getValue());
					break;
				}
			}
		}

		if (!TextUtils.isEmpty(mCheckedEndFloor)) {
			for (KeyValue kv : mFloorList) {
				if (kv.getKey().equals(mCheckedEndFloor)) {
					mEndFloor.setText(kv.getValue());
					break;
				}
			}
		}

	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_building_search_advanced;
	}

	@Override
	public void findViews() {
		mSearchConditionDivider = findViewById(R.id.search_condition_divider);
		mMinPrice = (EditText) findViewById(R.id.min_price);
		mMaxPrice = (EditText) findViewById(R.id.max_price);
		mStartFloor = (TextView) findViewById(R.id.start_floor);
		mEndFloor = (TextView) findViewById(R.id.end_floor);
		mTvSearchUnit = (TextView) findViewById(R.id.search_unit);
		mTvSearchRoomtype = (TextView) findViewById(R.id.search_roomtype);
		mTvSearchArea = (TextView) findViewById(R.id.search_area);
	}

	@Override
	public void setListener() {
		mTvSearchUnit.setOnClickListener(this);
		mTvSearchRoomtype.setOnClickListener(this);
		mTvSearchArea.setOnClickListener(this);
		findViewById(R.id.reset).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
		mStartFloor.setOnClickListener(this);
		mEndFloor.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.search_unit) {
			getUnitsByBuildingId();
		} else if (v.getId() == R.id.search_roomtype) {
			getRoomTypesByUnit();
		} else if (v.getId() == R.id.search_area) {
			getRoomAreaByUnitOrRType();
		} else if (v.getId() == R.id.reset) {
			mCheckedUnitName = "";
			mCheckedRoomType = "";
			mCheckedRoomArea = "";
			mCheckedStartFloor = "";
			mCheckedEndFloor = "";
			mCheckedMinPrice = "";
			mCheckedMaxPrice = "";

			mMinPrice.setText("");
			mMaxPrice.setText("");
			mStartFloor.setText("");
			mEndFloor.setText("");

			mTvSearchUnit.setText("单元");
			mTvSearchRoomtype.setText("户型");
			mTvSearchArea.setText("面积");
		} else if (v.getId() == R.id.search) {
			String minPrice = mMinPrice.getText().toString();
			String maxPrice = mMaxPrice.getText().toString();
			if (minPrice.startsWith(".")) {
				minPrice = "0" + minPrice;
			}
			if (minPrice.endsWith(".")) {
				minPrice = minPrice + "0";
			}
			if (maxPrice.startsWith(".")) {
				maxPrice = "0" + maxPrice;
			}
			if (maxPrice.endsWith(".")) {
				maxPrice = maxPrice + "0";
			}

			mCheckedMinPrice = minPrice;
			mCheckedMaxPrice = maxPrice;
			Intent intent = new Intent();
			intent.putExtra("unitname", mCheckedUnitName);
			intent.putExtra("roomtype", mCheckedRoomType);
			intent.putExtra("roomarea", mCheckedRoomArea);
			intent.putExtra("startfloor", mCheckedStartFloor);
			intent.putExtra("endfloor", mCheckedEndFloor);
			intent.putExtra("minprice", mCheckedMinPrice);
			intent.putExtra("maxprice", mCheckedMaxPrice);
			setResult(Activity.RESULT_OK, intent);
			finish();
		} else if (v.getId() == R.id.start_floor) {
			String[] items = new String[mFloorList.size()];
			for (int i = 0; i < mFloorList.size(); i++) {
				items[i] = mFloorList.get(i).getValue();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					mCheckedStartFloor = mFloorList.get(which).getKey();
					mStartFloor.setText(mFloorList.get(which).getValue());
					if (!TextUtils.isEmpty(mCheckedEndFloor)) {
						int start = Integer.parseInt(mCheckedStartFloor);
						int end = Integer.parseInt(mCheckedEndFloor);
						if (start > end) {
							mCheckedEndFloor = "";
							mEndFloor.setText("");
						}
					}
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.end_floor) {
			if (TextUtils.isEmpty(mCheckedStartFloor)) {
				showToast("请先选择开始楼层 ");
				return;
			}
			int start = Integer.parseInt(mCheckedStartFloor);

			final List<KeyValue> itemKvList = new ArrayList<KeyValue>();
			for (KeyValue kv : mFloorList) {
				int floor = Integer.parseInt(kv.getKey());
				if (floor >= start) {
					itemKvList.add(kv);
				}
			}
			String[] items = new String[itemKvList.size()];
			for (int i = 0; i < itemKvList.size(); i++) {
				items[i] = itemKvList.get(i).getValue();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					mCheckedEndFloor = itemKvList.get(which).getKey();
					mEndFloor.setText(itemKvList.get(which).getValue());
				}
			});
			builder.create().show();
		}
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETUNITSBYBUILDINGID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null || list.size() == 0) {
					showToast(R.string.response_invalid);
					return;
				}
				showSearchCondition(TYPE_UNIT_NAME, list);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		} else if (REQUEST_GETROOMTYPESBYUNIT.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null || list.size() == 0) {
					showToast(R.string.response_invalid);
					return;
				}
				showSearchCondition(TYPE_ROOM_TYPE, list);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		} else if (REQUEST_GETROOMAREABYUNITORRTYPE.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				String data = jsonObject.getString("result");
				List<String> list = JSON.parseArray(data, String.class);
				if (list == null || list.size() == 0) {
					showToast(R.string.response_invalid);
					return;
				}
				showSearchCondition(TYPE_ROOM_AREA, list);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		showToast(R.string.network_or_server_invalid);
	}

	private void showSearchCondition(final int type, List<String> list) {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_building_search_condition, null);
		LinearListView linearListView = (LinearListView) contentView.findViewById(R.id.linear_listview);
		ScrollView scrollView = (ScrollView) contentView.findViewById(R.id.scrollview);
		final List<BuildingSearchConditionKey> conditionList = new ArrayList<BuildingSearchConditionKey>();
		String checkedCondition = "";
		switch (type) {
		case TYPE_UNIT_NAME:
			checkedCondition = mCheckedUnitName;
			break;
		case TYPE_ROOM_TYPE:
			checkedCondition = mCheckedRoomType;
			break;
		case TYPE_ROOM_AREA:
			checkedCondition = mCheckedRoomArea;
			break;
		}
		for (String c : list) {
			BuildingSearchConditionKey conditionKey = new BuildingSearchConditionKey();
			conditionKey.setChecked(checkedCondition.equals(c));
			conditionKey.setCondition(c);
			conditionList.add(conditionKey);
		}
		BuildingSearchConditionAdapter adapter = new BuildingSearchConditionAdapter(this, conditionList);
		linearListView.setAdapter(adapter);

		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		linearListView.measure(w, h);
		int height = linearListView.getMeasuredHeight();

		final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
		if (height > mScreenHeight / 2) {
			scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mScreenHeight / 2));
		} else {
			scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		}
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x55000000));
		popupWindow.setContentView(contentView);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		contentView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		linearListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(LinearListView parent, View view, int position, long id) {
				popupWindow.dismiss();
				BuildingSearchConditionKey conditionKey = conditionList.get(position);
				String tmpCondition = conditionKey.getCondition();
				switch (type) {
				case TYPE_UNIT_NAME:
					mCheckedUnitName = tmpCondition;
					mTvSearchUnit.setText(mCheckedUnitName);
					mCheckedRoomType = "";
					mCheckedRoomArea = "";
					mTvSearchRoomtype.setText("户型");
					mTvSearchArea.setText("面积");
					break;
				case TYPE_ROOM_TYPE:
					mCheckedRoomType = tmpCondition;
					mTvSearchRoomtype.setText(mCheckedRoomType);
					mCheckedRoomArea = "";
					mTvSearchArea.setText("面积");
					break;
				case TYPE_ROOM_AREA:
					mCheckedRoomArea = tmpCondition;
					mTvSearchArea.setText(mCheckedRoomArea);
					break;
				}

			}
		});
		popupWindow.showAsDropDown(mSearchConditionDivider);
	}

	private void getScreenWidthHeight() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		mScreenWidth = mDisplayMetrics.widthPixels;
		mScreenHeight = mDisplayMetrics.heightPixels;
	}

	private void getUnitsByBuildingId() {
		requestHttp(String.format(Urls.getUnitsByBuildingId(), mBuildingId), REQUEST_GETUNITSBYBUILDINGID, this, true);
	}

	private void getRoomTypesByUnit() {
		requestHttp(String.format(Urls.getRoomTypesByUnit(), mBuildingId, mCheckedUnitName), REQUEST_GETROOMTYPESBYUNIT, this, true);
	}

	private void getRoomAreaByUnitOrRType() {
		requestHttp(String.format(Urls.getRoomAreaByUnitOrRType(), mBuildingId, mCheckedRoomArea, mCheckedUnitName), REQUEST_GETROOMAREABYUNITORRTYPE, this, true);
	}
}
