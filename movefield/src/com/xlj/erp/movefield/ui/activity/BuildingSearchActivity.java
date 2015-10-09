package com.xlj.erp.movefield.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.BuildingSearchConditionAdapter;
import com.xlj.erp.movefield.adapter.BuildingSearchDetailAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.BuildingSearchConditionKey;
import com.xlj.erp.movefield.entity.BuildingSearchInfo;
import com.xlj.erp.movefield.entity.HouseInfo;
import com.xlj.erp.movefield.entity.KeyValue;
import com.xlj.erp.movefield.entity.TmpBuildingSearchConditionKey;
import com.xlj.erp.movefield.entity.UnitInfo;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.LinearListView;
import com.xlj.erp.movefield.widget.LinearListView.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuildingSearchActivity extends BaseToolbarActivity {
	private static String REQUEST_GETHOUSEBYBUILDINGID = "getHouseByBuildingId";
	private static String REQUEST_GETUNITSBYBUILDINGID = "getUnitsByBuildingId";
	private static String REQUEST_GETROOMTYPESBYUNIT = "getRoomTypesByUnit";
	private static String REQUEST_GETROOMAREABYUNITORRTYPE = "getRoomAreaByUnitOrRType";
	private static String REQUEST_GETALLFLOORBYBUILDINGID = "getAllFloorByBuildingId";

	private final static int TYPE_UNIT_NAME = 0;
	private final static int TYPE_ROOM_TYPE = 1;
	private final static int TYPE_ROOM_AREA = 2;
	private final static int TYPE_ADVANCED_SEARCH = 3;

	private int mScreenWidth;
	private int mScreenHeight;

	private String mBuildingId;
	// 查询条件
	private String mCheckedUnitName = "";
	private String mCheckedRoomType = "";
	private String mCheckedRoomArea = "";
	private String mCheckedStartFloor = "";
	private String mCheckedEndFloor = "";
	private String mCheckedMinPrice = "";
	private String mCheckedMaxPrice = "";

	private StickyGridHeadersGridView mGridView;
	private TextView mForSaleInfo;
	private TextView mSoldInfo;
	private View mSearchConditionDivider;
	private TextView mTvSearchUnit;
	private TextView mTvSearchRoomtype;
	private TextView mTvSearchArea;

	private List<KeyValue> mFloorList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("楼栋查询");
		getScreenWidthHeight();
		mBuildingId = getIntent().getStringExtra("buildingId");
		getAllFloorByBuildingId();
		getHouseByBuildingId(null, mCheckedUnitName, mCheckedRoomType, mCheckedRoomArea, mCheckedStartFloor, mCheckedEndFloor, mCheckedMinPrice, mCheckedMaxPrice);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_building_search;
	}

	@Override
	public void findViews() {
		mGridView = (StickyGridHeadersGridView) findViewById(R.id.grid_view);
		mForSaleInfo = (TextView) findViewById(R.id.forsaleinfo);
		mSoldInfo = (TextView) findViewById(R.id.soldinfo);
		mSearchConditionDivider = findViewById(R.id.search_condition_divider);
		mTvSearchUnit = (TextView) findViewById(R.id.search_unit);
		mTvSearchRoomtype = (TextView) findViewById(R.id.search_roomtype);
		mTvSearchArea = (TextView) findViewById(R.id.search_area);
	}

	@Override
	public void setListener() {
		mTvSearchUnit.setOnClickListener(this);
		mTvSearchRoomtype.setOnClickListener(this);
		mTvSearchArea.setOnClickListener(this);
		findViewById(R.id.search_advanced).setOnClickListener(this);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GETHOUSEBYBUILDINGID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				String data = jsonObject.getString("result");
				BuildingSearchInfo buildingSearchInfo = JSON.parseObject(data, BuildingSearchInfo.class);
				if (buildingSearchInfo == null) {
					showToast(R.string.response_invalid);
					return;
				}
				processResponse(buildingSearchInfo);
				setSearchCondition(request);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		} else if (REQUEST_GETUNITSBYBUILDINGID.equals(request.getRequestTag())) {
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
		} else if (REQUEST_GETALLFLOORBYBUILDINGID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				String data = jsonObject.getString("result");
				mFloorList = JSON.parseArray(data, KeyValue.class);
				if (mFloorList == null || mFloorList.size() == 0) {
					showToast(R.string.response_invalid);
					return;
				}
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

	private void setSearchCondition(VolleyRequest request) {
		TmpBuildingSearchConditionKey tmpSearchCondition = (TmpBuildingSearchConditionKey) request.getTag();
		if (tmpSearchCondition == null) {
			return;
		}
		List<String> values = tmpSearchCondition.getValues();
		switch (tmpSearchCondition.getKey()) {
		case TYPE_UNIT_NAME:
			mCheckedUnitName = values.get(0);
			mTvSearchUnit.setText(mCheckedUnitName);
			// 其他置空
			mCheckedRoomType = "";
			mCheckedRoomArea = "";
			mTvSearchRoomtype.setText("户型");
			mTvSearchArea.setText("面积");
			break;
		case TYPE_ROOM_TYPE:
			mCheckedRoomType = values.get(0);
			mTvSearchRoomtype.setText(mCheckedRoomType);
			mCheckedRoomArea = "";
			mTvSearchArea.setText("面积");
			break;
		case TYPE_ROOM_AREA:
			mCheckedRoomArea = values.get(0);
			mTvSearchArea.setText(mCheckedRoomArea);
			break;
		case TYPE_ADVANCED_SEARCH:
			mCheckedUnitName = values.get(0);
			mCheckedRoomType = values.get(1);
			mCheckedRoomArea = values.get(2);
			mCheckedStartFloor = values.get(3);
			mCheckedEndFloor = values.get(4);
			mCheckedMinPrice = values.get(5);
			mCheckedMaxPrice = values.get(6);

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
			break;
		}
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
		} else if (v.getId() == R.id.search_advanced) {
			Intent intent = new Intent(this, BuildingSearchAdvancedActivity.class);
			intent.putExtra("buildingId", mBuildingId);
			intent.putExtra("unitname", mCheckedUnitName);
			intent.putExtra("roomtype", mCheckedRoomType);
			intent.putExtra("roomarea", mCheckedRoomArea);
			intent.putExtra("startfloor", mCheckedStartFloor);
			intent.putExtra("endfloor", mCheckedEndFloor);
			intent.putExtra("minprice", mCheckedMinPrice);
			intent.putExtra("maxprice", mCheckedMaxPrice);
			intent.putExtra("floorList", (Serializable) mFloorList);
			startActivityForResult(intent, 0);
		}
	}

	private void getHouseByBuildingId(Object tag, String unitName, String roomType, String roomArea, String startFloor, String endFloor, String minPrice, String maxPrice) {
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.getHouseByBuildingId(), username, String.valueOf(projectId), mBuildingId, unitName, roomType, roomArea, startFloor, endFloor, minPrice, maxPrice);
		VolleyRequest request = requestHttp(url, REQUEST_GETHOUSEBYBUILDINGID, this, true);
		request.setTag(tag);
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

	private void getAllFloorByBuildingId() {
		requestHttp(String.format(Urls.getAllFloorByBuildingId(), mBuildingId), REQUEST_GETALLFLOORBYBUILDINGID, this);
	}

	private void processResponse(BuildingSearchInfo buildingSearchInfo) {
		mForSaleInfo.setText(buildingSearchInfo.getForsaleinfo());
		mSoldInfo.setText(buildingSearchInfo.getSoldinfo());
		List<HouseInfo> allHouseInfos = formatData(buildingSearchInfo);
		BuildingSearchDetailAdapter adapter = new BuildingSearchDetailAdapter(this, allHouseInfos);
		mGridView.setAdapter(adapter);
	}

	private List<HouseInfo> formatData(BuildingSearchInfo buildingSearchInfo) {
		List<HouseInfo> allHouseInfos = new ArrayList<HouseInfo>();
		List<UnitInfo> roomList = buildingSearchInfo.getRoomList();
		int headerId = 0;
		for (UnitInfo u : roomList) {
			List<HouseInfo> houseInfos = u.getHouseLists();
			for (HouseInfo h : houseInfos) {
				h.setRealUnitName(u.getNunitnum());
				h.setHeaderId(headerId);
				allHouseInfos.add(h);
			}
			headerId++;
		}
		return allHouseInfos;
	}

	private void getScreenWidthHeight() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		mScreenWidth = mDisplayMetrics.widthPixels;
		mScreenHeight = mDisplayMetrics.heightPixels;
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
				// 网络请求
				// 请求完成保存查询参数，注意清空之后的参数
				String tmpCondition = conditionKey.getCondition();
				switch (type) {
				case TYPE_UNIT_NAME:
					TmpBuildingSearchConditionKey unitNameCodition = new TmpBuildingSearchConditionKey();
					unitNameCodition.setKey(TYPE_UNIT_NAME);
					List<String> unitNameValues = new ArrayList<String>();
					unitNameValues.add(tmpCondition);
					unitNameCodition.setValues(unitNameValues);
					getHouseByBuildingId(unitNameCodition, tmpCondition, "", "", mCheckedStartFloor, mCheckedEndFloor, mCheckedMinPrice, mCheckedMaxPrice);
					break;
				case TYPE_ROOM_TYPE:
					TmpBuildingSearchConditionKey roomTypeCodition = new TmpBuildingSearchConditionKey();
					roomTypeCodition.setKey(TYPE_ROOM_TYPE);
					List<String> roomTypeValues = new ArrayList<String>();
					roomTypeValues.add(tmpCondition);
					roomTypeCodition.setValues(roomTypeValues);
					getHouseByBuildingId(roomTypeCodition, mCheckedUnitName, tmpCondition, "", mCheckedStartFloor, mCheckedEndFloor, mCheckedMinPrice, mCheckedMaxPrice);
					break;
				case TYPE_ROOM_AREA:
					TmpBuildingSearchConditionKey roomAreaCodition = new TmpBuildingSearchConditionKey();
					roomAreaCodition.setKey(TYPE_ROOM_AREA);
					List<String> roomAreaValues = new ArrayList<String>();
					roomAreaValues.add(tmpCondition);
					roomAreaCodition.setValues(roomAreaValues);
					getHouseByBuildingId(roomAreaCodition, mCheckedUnitName, mCheckedRoomType, tmpCondition, mCheckedStartFloor, mCheckedEndFloor, mCheckedMinPrice, mCheckedMaxPrice);
					break;
				}

			}
		});
		popupWindow.showAsDropDown(mSearchConditionDivider);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			String unitName = data.getStringExtra("unitname");
			String roomType = data.getStringExtra("roomtype");
			String roomArea = data.getStringExtra("roomarea");
			String startFloor = data.getStringExtra("startfloor");
			String endFloor = data.getStringExtra("endfloor");
			String minPrice = data.getStringExtra("minprice");
			String maxPrice = data.getStringExtra("maxprice");
			TmpBuildingSearchConditionKey roomAreaCodition = new TmpBuildingSearchConditionKey();
			roomAreaCodition.setKey(TYPE_ADVANCED_SEARCH);
			List<String> roomAreaValues = new ArrayList<String>();
			roomAreaValues.add(unitName);
			roomAreaValues.add(roomType);
			roomAreaValues.add(roomArea);
			roomAreaValues.add(startFloor);
			roomAreaValues.add(endFloor);
			roomAreaValues.add(minPrice);
			roomAreaValues.add(maxPrice);
			roomAreaCodition.setValues(roomAreaValues);
			getHouseByBuildingId(roomAreaCodition, unitName, roomType, roomArea, startFloor, endFloor, minPrice, maxPrice);
		}
	}
}
