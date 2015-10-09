package com.xlj.erp.movefield.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.ProjectInfo;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * 楼盘信息
 * 
 * @author chaohui.yang
 *
 */
public class ProjectInfoActivity extends BaseToolbarActivity {
	private static String REQUEST_GET_BUILDING_INFO = "getBuildingInfo";

	private MultiStateView mMultiStateView;
	private TextView mBuildingInfoProjectName;
	private TextView mBuildingInfoAvgPrice;
	private TextView mBuildingInfoOpenTime;
	private TextView mBuildingInfoType;
	private TextView mBuildingInfoCheckinTime;

	private TextView pName;
	private TextView location;
	private TextView buildingtype;
	private TextView zhanArea;
	private TextView jianArea;
	private TextView greening;
	private TextView plotRatio;
	private TextView parkingSpace;

	private TextView startTime;
	private TextView shoppingDoor;
	private TextView averagePrice;
	private TextView mainDoor;
	private TextView policy;
	private TextView license;
	private TextView address;

	private TextView property;
	private TextView cost;
	private TextView instructions;

	private ProjectInfo mProjectInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.title_project_info);

		getBuildingInfo();
		mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				getBuildingInfo();
			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_building_info;
	}

	@Override
	public void findViews() {
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
		mBuildingInfoProjectName = (TextView) findViewById(R.id.building_info_project_name);
		mBuildingInfoAvgPrice = (TextView) findViewById(R.id.building_info_avg_price);
		mBuildingInfoOpenTime = (TextView) findViewById(R.id.building_info_open_time);
		mBuildingInfoType = (TextView) findViewById(R.id.building_info_type);
		mBuildingInfoCheckinTime = (TextView) findViewById(R.id.building_info_checkin_time);

		pName = (TextView) findViewById(R.id.pName);
		location = (TextView) findViewById(R.id.location);
		buildingtype = (TextView) findViewById(R.id.buildingtype);
		zhanArea = (TextView) findViewById(R.id.zhanArea);
		jianArea = (TextView) findViewById(R.id.jianArea);
		greening = (TextView) findViewById(R.id.greening);
		plotRatio = (TextView) findViewById(R.id.plotRatio);
		parkingSpace = (TextView) findViewById(R.id.parkingSpace);

		startTime = (TextView) findViewById(R.id.startTime);
		shoppingDoor = (TextView) findViewById(R.id.shoppingDoor);
		averagePrice = (TextView) findViewById(R.id.averagePrice);
		mainDoor = (TextView) findViewById(R.id.mainDoor);
		policy = (TextView) findViewById(R.id.policy);
		license = (TextView) findViewById(R.id.license);
		address = (TextView) findViewById(R.id.address);

		property = (TextView) findViewById(R.id.property);
		cost = (TextView) findViewById(R.id.cost);
		instructions = (TextView) findViewById(R.id.instructions);
	}

	@Override
	public void setListener() {
		findViewById(R.id.instructions_layout).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.instructions_layout) {
			Intent intent = new Intent(this, InstructionActivity.class);
			intent.putExtra("instructions", mProjectInfo.getInstructions());
			startActivity(intent);
		}
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_BUILDING_INFO.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				mProjectInfo = JSON.parseObject(data, ProjectInfo.class);
				if (mProjectInfo == null) {
					showToast(R.string.response_invalid);
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				fillViewData(mProjectInfo);
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
	}

	/**
	 * 网络请求:获取楼盘信息
	 */
	private void getBuildingInfo() {
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getBuildingInfo(), String.valueOf(projectId), username), REQUEST_GET_BUILDING_INFO, this);
	}

	private void fillViewData(ProjectInfo projectInfo) {
		mBuildingInfoProjectName.setText((projectInfo.getpName()));
		mBuildingInfoAvgPrice.setText(projectInfo.getAveragePrice());
		mBuildingInfoOpenTime.setText(projectInfo.getStartTime());
		mBuildingInfoType.setText(projectInfo.getpType());
		mBuildingInfoCheckinTime.setText(projectInfo.getCheckintime());

		pName.setText(projectInfo.getpName());
		location.setText(projectInfo.getLocation());
		buildingtype.setText(projectInfo.getBuildingtype());
		zhanArea.setText(projectInfo.getZhanArea());
		jianArea.setText(projectInfo.getJianArea());
		greening.setText(projectInfo.getGreening());
		plotRatio.setText(projectInfo.getPlotRatio());
		parkingSpace.setText(projectInfo.getParkingSpace());

		startTime.setText(projectInfo.getStartTime());
		shoppingDoor.setText(projectInfo.getShoppingDoor());
		if (TextUtils.isEmpty(projectInfo.getAveragePrice())) {
			averagePrice.setText(projectInfo.getAveragePrice() + "元/平方米");
		}
		mainDoor.setText(projectInfo.getMainDoor());
		policy.setText(projectInfo.getPolicy());
		license.setText(projectInfo.getLicense());
		address.setText(projectInfo.getAddress());

		property.setText(projectInfo.getProperty());
		cost.setText(projectInfo.getCost());
		instructions.setText(projectInfo.getInstructions());
	}

}
