package com.xlj.erp.movefield.ui.fragment;

import java.util.LinkedList;

import org.xclcharts.chart.PieData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.chart.PieChart02View;
import com.xlj.erp.movefield.entity.CaseFieldMonitor;
import com.xlj.erp.movefield.ui.activity.CustomerAnalysisActivity;
import com.xlj.erp.movefield.ui.activity.SaleAnalysisAndBackPaymentActivity;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * 案场监控
 * 
 * @author chaohui.yang
 *
 */
public class FieldMonitorFragment extends BaseFragment {

	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;
	
	private static final String REQUEST_FIELDMONITOR = "fieldMonitor";
	/**
	 * 销售业绩布局
	 */
	private LinearLayout linear_saleachieve;
	/**
	 * 客户意向布局
	 */
	private LinearLayout linear_customerinterset;
	/**
	 * 客户跟进布局
	 */
	private LinearLayout linear_customerfollow;
	/**
	 * 销售业绩饼图
	 */
	private PieChart02View cake_statistic_chart;
	/**
	 * 销售目标
	 */
	private TextView tv_targetAmount;
	/**
	 * 完成销售金额
	 */
	private TextView tv_compelete_amount;
	/**
	 * 预定套数
	 */
	private TextView tv_subscribecount;
	/**
	 * 预定金额
	 */
	private TextView tv_subscribeamount;
	/**
	 * 签约套数
	 */
	private TextView tv_signcount;
	/**
	 * 签约金额
	 */
	private TextView tv_signamount;
	/**
	 * 意向人数
	 */
	private TextView tv_interestcount;
	
	private TextView tv_interest_no;
	
	private TextView tv_interest_normal;
	
	private TextView tv_interest_high;
	
	private TextView tv_interest_must;
	/**
	 * 必买人数
	 */
	private TextView tv_boughtcount;
	/**
	 * 一般意向人数
	 */
	private TextView tv_normalinterestcount;
	/**
	 * 高意向人数
	 */
	private TextView tv_hightinterestcount;
	/**
	 * 无意向人数
	 */
	private TextView tv_nointerestcount;
	/**
	 * 客户跟进
	 */
	private TextView tv_followcount;
	/**
	 * 客户回访
	 */
	private TextView tv_visitcount;
	/**
	 * 新客户登记
	 */
	private TextView tv_registercustomercount;
	/**
	 * 客户管理
	 */
	private TextView tv_customerMgr;
	
	private MultiStateView multiStateView;

	public static FieldMonitorFragment newInstance() {
		FieldMonitorFragment fieldMonitorFragment = new FieldMonitorFragment();
		return fieldMonitorFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_field_monitor;
	}

	@Override
	public void findViews() {
		linear_saleachieve = (LinearLayout) mRootView.findViewById(R.id.linear_saleachieve);
		linear_customerinterset = (LinearLayout) mRootView.findViewById(R.id.linear_customerinterset);
		linear_customerfollow = (LinearLayout) mRootView.findViewById(R.id.linear_customerfollow);
		cake_statistic_chart = (PieChart02View) mRootView.findViewById(R.id.cake_statistic_chart);
		tv_targetAmount = (TextView) mRootView.findViewById(R.id.tv_targetAmount);
		tv_compelete_amount = (TextView) mRootView.findViewById(R.id.tv_compelete_amount);
		
		tv_interest_no = (TextView) mRootView.findViewById(R.id.tv_interest_no);
		tv_interest_normal = (TextView) mRootView.findViewById(R.id.tv_interest_normal);
		tv_interest_high = (TextView) mRootView.findViewById(R.id.tv_interest_high);
		tv_interest_must = (TextView) mRootView.findViewById(R.id.tv_interest_must);
		
		tv_subscribecount = (TextView) mRootView.findViewById(R.id.tv_subscribecount);
		tv_subscribeamount = (TextView) mRootView.findViewById(R.id.tv_subscribeamount);
		tv_signcount = (TextView) mRootView.findViewById(R.id.tv_signcount);
		tv_signamount = (TextView) mRootView.findViewById(R.id.tv_signamount);
		tv_interestcount = (TextView) mRootView.findViewById(R.id.tv_interestcount);
		tv_boughtcount = (TextView) mRootView.findViewById(R.id.tv_boughtcount);
		tv_normalinterestcount = (TextView) mRootView.findViewById(R.id.tv_normalinterestcount);
		tv_hightinterestcount = (TextView) mRootView.findViewById(R.id.tv_hightinterestcount);
		tv_nointerestcount = (TextView) mRootView.findViewById(R.id.tv_nointerestcount);
		tv_followcount = (TextView) mRootView.findViewById(R.id.tv_followcount);
		tv_visitcount = (TextView) mRootView.findViewById(R.id.tv_visitcount);
		tv_registercustomercount = (TextView) mRootView.findViewById(R.id.tv_registercustomercount);
		tv_customerMgr = (TextView) mRootView.findViewById(R.id.tv_customerMgr);
		
		multiStateView  = (MultiStateView) mRootView.findViewById(R.id.multiStateView);
		multiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				multiStateView.setViewState(MultiStateView.ViewState.LOADING);
				requestHttp();
			}
		});
	}

	@Override
	public void setListener() {
		linear_customerfollow.setOnClickListener(this);
		linear_customerinterset.setOnClickListener(this);
		linear_saleachieve.setOnClickListener(this);
	}

	@Override
	public void businessLogic() {
		setupToolbar();
		requestHttp();
		registerReceiver();
	}
	private void requestHttp(){
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		String userId = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getFieldMonitor(), projectId,userId,username), REQUEST_FIELDMONITOR, this);
	}
	@Override
	public void onPost(VolleyRequest request, String result) {
		// TODO Auto-generated method stub
		super.onPost(request, result);
		if (REQUEST_FIELDMONITOR.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast("获取数据失败!");
					multiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				CaseFieldMonitor caseFieldMonitor = JSON.parseObject(data, CaseFieldMonitor.class);
				if (caseFieldMonitor == null) {
					showToast(R.string.response_invalid);
					multiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置控件数据
				//完成百分比图
				//已完成百分比
				float done = Integer.parseInt(caseFieldMonitor.completeamount)*100 / Integer.parseInt(caseFieldMonitor.targetamount);
				//未完成百分比
				float notDone = 100-done;
				
				LinkedList<PieData> pieData = new LinkedList<PieData>();
				pieData.add(new PieData("已完成",done+"%",done,0xffe76f35,true ));
				pieData.add(new PieData("未完成","",notDone,0xfff7bf97));
				cake_statistic_chart.chartRender(pieData);
				
				//目标金额
				tv_targetAmount.setText(caseFieldMonitor.targetamount+"万");
				//完成金额
				tv_compelete_amount.setText(caseFieldMonitor.completeamount);
				//认购
				tv_subscribecount.setText(caseFieldMonitor.subscribecount+"套");
				tv_subscribeamount.setText(caseFieldMonitor.subscribeamount+"万元");
				//签约
				tv_signcount.setText(caseFieldMonitor.signcount+"套");
				tv_signamount.setText(caseFieldMonitor.signamount+"万元");
				
				tv_interest_no.setText(caseFieldMonitor.interestcount);
				tv_interest_normal.setText(caseFieldMonitor.normalinterestcount);
				tv_interest_high.setText(caseFieldMonitor.hightinterestcount);
				tv_interest_must.setText(caseFieldMonitor.boughtcount);
				
				//客户意向
				tv_interestcount.setText(caseFieldMonitor.interestcount+"人");
				tv_boughtcount.setText(caseFieldMonitor.boughtcount);
				tv_normalinterestcount.setText(caseFieldMonitor.normalinterestcount);
				tv_hightinterestcount.setText(caseFieldMonitor.hightinterestcount);
				tv_nointerestcount.setText(caseFieldMonitor.nointerestcount);
				//客户跟进
				tv_followcount.setText(caseFieldMonitor.followcount);
				tv_visitcount.setText(caseFieldMonitor.visitcount);
				tv_registercustomercount.setText(caseFieldMonitor.registercustomercount);
				tv_customerMgr.setText(caseFieldMonitor.customerMgr);
				
				multiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				multiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		} 
	}
	
	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		// TODO Auto-generated method stub
		super.onError(request, error);
		multiStateView.setViewState(MultiStateView.ViewState.ERROR);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.linear_saleachieve:
			Intent intent = new Intent(getActivity(),SaleAnalysisAndBackPaymentActivity.class);
			startActivity(intent);
			break;
		case R.id.linear_customerinterset:
			intent = new Intent(getActivity(),CustomerAnalysisActivity.class);
			startActivity(intent);
			break;
		case R.id.linear_customerfollow:
			showToast("客户跟进");
			break;
		}
	}

	/**
	 * 接收切换项目的广播
	 */
	private class SwitchProjectBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
		}
	}

	private void registerReceiver() {
		mSwitchProjectBroadcastReceiver = new SwitchProjectBroadcastReceiver();
		IntentFilter filter = new IntentFilter(Contants.ACTION_SWITCH_PROJECT);
		mParentActivity.registerReceiver(mSwitchProjectBroadcastReceiver, filter);
	}

	@Override
	public void onDestroyView() {
		if (mSwitchProjectBroadcastReceiver != null) {
			mParentActivity.unregisterReceiver(mSwitchProjectBroadcastReceiver);
		}
		super.onDestroyView();
	}

	@Override
	public void setupToolbar() {
		Toolbar toolbar = getToolbar();
		View view = toolbar.findViewById(R.id.segment_control);
		if(view!=null){
			toolbar.removeView(view);
		}
		setTitle("案场监控");
		setDisplayShowTitleEnabled(true);
	}

}
