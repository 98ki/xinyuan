package com.xlj.erp.movefield.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.SaleAnalysis;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * 销售分析
 * 
 * @author chaohui.yang
 *
 */
public class SaleAnalysisFragment extends BaseFragment {
	private SwitchProjectBroadcastReceiver mSwitchProjectBroadcastReceiver;
	
	private static final String REQUEST_SALEANALYSIS = "saleAnalysis";
	/**
	 * 请求的数据对象
	 */
	private SaleAnalysis saleAnalysis;
	/**
	 * 意向客户数
	 */
	private TextView tv_peoplenum_sale_analyse;
	/**
	 * 选择日期--日周月
	 */
	private TextView tv_selecttime;
	private ImageView img_selecttime;
	/**
	 * 各个意向人数分布
	 */
	private TextView tv_bimai_num;
	private TextView tv_gaoyixiang_num;
	private TextView tv_yibanyixiang_num;
	private TextView tv_wuyixiang_num;
	/**
	 * 本日周月登记数
	 */
	private TextView tv_denngji_label;
	private TextView tv_denngji_num;
	/**
	 * 本日周月接待数
	 */
	private TextView tv_jiedai_label;
	private TextView tv_jiedai_num;
	/**
	 * 本日周月回访数
	 */
	private TextView tv_huifang_label;
	private TextView tv_huifang_num;
	/**
	 * 本日周月认购数
	 */
	private TextView tv_rengou_label;
	private TextView tv_rengou_num;
	/**
	 * 本日周月签约数
	 */
	private TextView tv_qianyue_label;
	private TextView tv_qianyue_num;
	/**
	 * 本日周月签约金额
	 */
	private TextView tv_qianyueamount_label;
	private TextView tv_qianyueamount_amount;
	
	private MultiStateView multiStateView;
	
	private PopupWindow popupWindow;
	
	private View popView;
	
	private RelativeLayout relative_day;
	private RelativeLayout relative_week;
	private RelativeLayout relative_month;
	
	private ImageView img_day;
	private ImageView img_week;
	private ImageView img_month;
	private ImageView[] imgViews = {img_day,img_week,img_month};
	
	private int popSelectIndex = 0;
	
	

	public static SaleAnalysisFragment newInstance() {
		SaleAnalysisFragment saleAnalysisFragment = new SaleAnalysisFragment();
		return saleAnalysisFragment;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_sale_analysis;
	}

	@Override
	public void findViews() {
		tv_peoplenum_sale_analyse = (TextView) mRootView.findViewById(R.id.tv_peoplenum_sale_analyse);
		tv_selecttime = (TextView) mRootView.findViewById(R.id.tv_selecttime);
		img_selecttime = (ImageView) mRootView.findViewById(R.id.img_selecttime);
		
		tv_bimai_num = (TextView) mRootView.findViewById(R.id.tv_bimai_num);
		tv_gaoyixiang_num = (TextView) mRootView.findViewById(R.id.tv_gaoyixiang_num);
		tv_yibanyixiang_num = (TextView) mRootView.findViewById(R.id.tv_yibanyixiang_num);
		tv_wuyixiang_num = (TextView) mRootView.findViewById(R.id.tv_wuyixiang_num);
		
		tv_denngji_label = (TextView) mRootView.findViewById(R.id.tv_denngji_label);
		tv_denngji_num = (TextView) mRootView.findViewById(R.id.tv_denngji_num);
		
		tv_jiedai_label = (TextView) mRootView.findViewById(R.id.tv_jiedai_label);
		tv_jiedai_num = (TextView) mRootView.findViewById(R.id.tv_jiedai_num);
		
		tv_huifang_label = (TextView) mRootView.findViewById(R.id.tv_huifang_label);
		tv_huifang_num = (TextView) mRootView.findViewById(R.id.tv_huifang_num);
		
		tv_rengou_label = (TextView) mRootView.findViewById(R.id.tv_rengou_label);
		tv_rengou_num = (TextView) mRootView.findViewById(R.id.tv_rengou_num);
		
		tv_qianyue_label = (TextView) mRootView.findViewById(R.id.tv_qianyue_label);
		tv_qianyue_num = (TextView) mRootView.findViewById(R.id.tv_qianyue_num);
		
		tv_qianyueamount_label = (TextView) mRootView.findViewById(R.id.tv_qianyueamount_label);
		tv_qianyueamount_amount = (TextView) mRootView.findViewById(R.id.tv_qianyueamount_amountl);
		
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
		tv_selecttime.setOnClickListener(this);
		img_selecttime.setOnClickListener(this);
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
		requestHttp(String.format(Urls.getSaleAnalysis(), projectId,userId,username), REQUEST_SALEANALYSIS, this);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		// TODO Auto-generated method stub
		super.onPost(request, result);
		if (REQUEST_SALEANALYSIS.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast("获取数据失败!");
					multiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				saleAnalysis = JSON.parseObject(data, SaleAnalysis.class);
				if (saleAnalysis == null) {
					showToast(R.string.response_invalid);
					multiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置控件数据
				tv_peoplenum_sale_analyse.setText(String.valueOf(saleAnalysis.interestcount));
				
				tv_bimai_num.setText(String.valueOf(saleAnalysis.bought));
				tv_gaoyixiang_num.setText(String.valueOf(saleAnalysis.hightinterest));
				tv_yibanyixiang_num.setText(String.valueOf(saleAnalysis.normalinterest));
				tv_wuyixiang_num.setText(String.valueOf(saleAnalysis.nointerest));
				
				//默认显示本日信息
				tv_denngji_label.setText(R.string.daysalestatus_registerinfo);
				tv_denngji_num.setText(saleAnalysis.saleStatistical.daysalestatus.registerinfo);
				
				tv_jiedai_label.setText(R.string.daysalestatus_receptioninfo);
				tv_jiedai_num.setText(saleAnalysis.saleStatistical.daysalestatus.receptioninfo);
				
				tv_huifang_label.setText(R.string.daysalestatus_visitinfo);
				tv_huifang_num.setText(saleAnalysis.saleStatistical.daysalestatus.visitinfo);
				
				tv_rengou_label.setText(R.string.daysalestatus_subscribeinfo);
				tv_rengou_num.setText(saleAnalysis.saleStatistical.daysalestatus.subscribeinfo);
				
				tv_qianyue_label.setText(R.string.daysalestatus_signinfo);
				tv_qianyue_num.setText(saleAnalysis.saleStatistical.daysalestatus.signinfo);
				
				tv_qianyueamount_label.setText(R.string.daysalestatus_signamount);
				tv_qianyueamount_amount.setText(saleAnalysis.saleStatistical.daysalestatus.signamount);
				
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
		case R.id.img_selecttime:
		case R.id.tv_selecttime:
			showPopWindow();
			break;
		case R.id.relative_day:
			//先判断是否与上次选择的不同
			//不同时才转变
			if(popSelectIndex != 0){
				imgViews[popSelectIndex].setVisibility(View.GONE);
				popSelectIndex = 0;
				imgViews[popSelectIndex].setVisibility(View.VISIBLE);
				showStaticInfo(popSelectIndex);
			}
			/*popSelectIndex = 0;
			img_day.setVisibility(View.VISIBLE);
        	img_week.setVisibility(View.GONE);
        	img_month.setVisibility(View.GONE);
			showStaticInfo(popSelectIndex);*/
			popupWindow.dismiss();
			break;
		case R.id.relative_week:
			//先判断是否与上次选择的不同
			//不同时才转变
			if(popSelectIndex != 1){
				imgViews[popSelectIndex].setVisibility(View.GONE);
				popSelectIndex = 1;
				imgViews[popSelectIndex].setVisibility(View.VISIBLE);
				showStaticInfo(popSelectIndex);
			}
			/*popSelectIndex = 1;
			img_week.setVisibility(View.VISIBLE);
        	img_day.setVisibility(View.GONE);
        	img_month.setVisibility(View.GONE);
			showStaticInfo(popSelectIndex);*/
			popupWindow.dismiss();
			break;
		case R.id.relative_month:
			//先判断是否与上次选择的不同
			//不同时才转变
			if(popSelectIndex != 2){
				imgViews[popSelectIndex].setVisibility(View.GONE);
				popSelectIndex = 2;
				imgViews[popSelectIndex].setVisibility(View.VISIBLE);
				showStaticInfo(popSelectIndex);
			}
			/*popSelectIndex = 2;
			img_month.setVisibility(View.VISIBLE);
        	img_day.setVisibility(View.GONE);
        	img_week.setVisibility(View.GONE);
			showStaticInfo(popSelectIndex);*/
			popupWindow.dismiss();
			break;
		}
	}

	private void showPopWindow(){
		if (popupWindow == null) {  
            LayoutInflater layoutInflater = LayoutInflater.from(mParentActivity);  
            popView = layoutInflater.inflate(R.layout.pop_sale_analysis, null);  
            
            relative_day = (RelativeLayout) popView.findViewById(R.id.relative_day);
            relative_week = (RelativeLayout) popView.findViewById(R.id.relative_week);
            relative_month = (RelativeLayout) popView.findViewById(R.id.relative_month);
            
            relative_day.setOnClickListener(this);
            relative_week.setOnClickListener(this);
            relative_month.setOnClickListener(this);
            
            img_day = (ImageView) popView.findViewById(R.id.img_day);
            img_week = (ImageView) popView.findViewById(R.id.img_week);
            img_month = (ImageView) popView.findViewById(R.id.img_month);
            
            imgViews[0] = img_day;
            imgViews[1] = img_week;
            imgViews[2] = img_month;
            
            popupWindow = new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
        }  
		
		popView.setFocusable(true);
		popView.setFocusableInTouchMode(true);  
		popView.setOnKeyListener(new OnKeyListener() {  
		    @Override  
		    public boolean onKey(View v, int keyCode, KeyEvent event) {  
		        if (keyCode == KeyEvent.KEYCODE_BACK) {  
		        	if(popupWindow != null && popupWindow.isShowing()){
		        		popupWindow.dismiss();  
		        	}
		            return true;  
		        }  
		        return false;  
		    }  
		});
		popView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(popupWindow != null && popupWindow.isShowing()){
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		popupWindow.setFocusable(true);  
        popupWindow.setOutsideTouchable(true);  
		
        imgViews[popSelectIndex].setVisibility(View.VISIBLE);
		/*if(popSelectIndex == 0){
        	img_day.setVisibility(View.VISIBLE);
        	img_week.setVisibility(View.GONE);
        	img_month.setVisibility(View.GONE);
        }else if(popSelectIndex == 1){
        	img_week.setVisibility(View.VISIBLE);
        	img_day.setVisibility(View.GONE);
        	img_month.setVisibility(View.GONE);
        }else if(popSelectIndex == 2){
        	img_month.setVisibility(View.VISIBLE);
        	img_day.setVisibility(View.GONE);
        	img_week.setVisibility(View.GONE);
        }*/
		
        popupWindow.showAsDropDown(img_selecttime);
	}
	
	private void showStaticInfo(int popSelectIndex){
		switch(popSelectIndex){
			case 0:
				//设置本日信息
				tv_denngji_label.setText(R.string.daysalestatus_registerinfo);
				tv_denngji_num.setText(saleAnalysis.saleStatistical.daysalestatus.registerinfo);
				
				tv_jiedai_label.setText(R.string.daysalestatus_receptioninfo);
				tv_jiedai_num.setText(saleAnalysis.saleStatistical.daysalestatus.receptioninfo);
				
				tv_huifang_label.setText(R.string.daysalestatus_visitinfo);
				tv_huifang_num.setText(saleAnalysis.saleStatistical.daysalestatus.visitinfo);
				
				tv_rengou_label.setText(R.string.daysalestatus_subscribeinfo);
				tv_rengou_num.setText(saleAnalysis.saleStatistical.daysalestatus.subscribeinfo);
				
				tv_qianyue_label.setText(R.string.daysalestatus_signinfo);
				tv_qianyue_num.setText(saleAnalysis.saleStatistical.daysalestatus.signinfo);
				
				tv_qianyueamount_label.setText(R.string.daysalestatus_signamount);
				tv_qianyueamount_amount.setText(saleAnalysis.saleStatistical.daysalestatus.signamount);
			break;
			case 1:
				//设置本周信息
				tv_denngji_label.setText(R.string.weeksalestatus_registerinfo);
				tv_denngji_num.setText(saleAnalysis.saleStatistical.weeksalestatus.registerinfo);
				
				tv_jiedai_label.setText(R.string.weeksalestatus_receptioninfo);
				tv_jiedai_num.setText(saleAnalysis.saleStatistical.weeksalestatus.receptioninfo);
				
				tv_huifang_label.setText(R.string.weeksalestatus_visitinfo);
				tv_huifang_num.setText(saleAnalysis.saleStatistical.weeksalestatus.visitinfo);
				
				tv_rengou_label.setText(R.string.weeksalestatus_subscribeinfo);
				tv_rengou_num.setText(saleAnalysis.saleStatistical.weeksalestatus.subscribeinfo);
				
				tv_qianyue_label.setText(R.string.weeksalestatus_signinfo);
				tv_qianyue_num.setText(saleAnalysis.saleStatistical.weeksalestatus.signinfo);
				
				tv_qianyueamount_label.setText(R.string.weeksalestatus_signamount);
				tv_qianyueamount_amount.setText(saleAnalysis.saleStatistical.weeksalestatus.signamount);
			break;
			case 2:
				//设置本周信息
				tv_denngji_label.setText(R.string.monthsalestatus_registerinfo);
				tv_denngji_num.setText(saleAnalysis.saleStatistical.monthsalestatus.registerinfo);
				
				tv_jiedai_label.setText(R.string.monthsalestatus_receptioninfo);
				tv_jiedai_num.setText(saleAnalysis.saleStatistical.monthsalestatus.receptioninfo);
				
				tv_huifang_label.setText(R.string.monthsalestatus_visitinfo);
				tv_huifang_num.setText(saleAnalysis.saleStatistical.monthsalestatus.visitinfo);
				
				tv_rengou_label.setText(R.string.monthsalestatus_subscribeinfo);
				tv_rengou_num.setText(saleAnalysis.saleStatistical.monthsalestatus.subscribeinfo);
				
				tv_qianyue_label.setText(R.string.monthsalestatus_signinfo);
				tv_qianyue_num.setText(saleAnalysis.saleStatistical.monthsalestatus.signinfo);
				
				tv_qianyueamount_label.setText(R.string.monthsalestatus_signamount);
				tv_qianyueamount_amount.setText(saleAnalysis.saleStatistical.monthsalestatus.signamount);
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
		setTitle("销售分析");
		setDisplayShowTitleEnabled(true);
	}
}
