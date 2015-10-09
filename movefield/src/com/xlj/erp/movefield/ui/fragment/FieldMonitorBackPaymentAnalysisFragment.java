package com.xlj.erp.movefield.ui.fragment;

import java.text.DecimalFormat;
import java.util.LinkedList;

import org.xclcharts.chart.PieData;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.chart.RoseChart01View;
import com.xlj.erp.movefield.entity.MonitorBackPaymentAnalysis;
import com.xlj.erp.movefield.entity.MonitorBackPaymentAnalysis.SalesConstitute;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
/**
 * 案场监控-回款分析
 */
public class FieldMonitorBackPaymentAnalysisFragment extends BaseFragment {

	private static final String REQUEST_MONITORBACKPAYMENTANALYSISACTIVITY = "monitorBackPaymentAnalysis";
	/**
	 * 右上角当（日月年）销售分析
	 */
	private TextView tv_time_label;
	/**
	 * 选择时间图标
	 */
	private ImageView img_selecttime;
	/**
	 * 选择时间文字
	 */
	private TextView tv_selecttime;
	/**
	 * 完成率
	 */
	private TextView tv_rate;
	/**
	 * 回款金额
	 */
	private TextView tv_returnMoney;
	/**
	 * 回款任务
	 */
	private TextView tv_returnTaskMoney;
	/**
	 * 定金金额
	 */
	private TextView tv_depositMoney;
	/**
	 * 楼款金额
	 */
	private TextView tv_buildMoney;
	/**
	 * 商业按揭
	 */
	private TextView tv_businessMortgage;
	/**
	 * 公积金
	 */
	private TextView tv_accumulation;
	
	/**
	 * 接口返回数据
	 */
	private MonitorBackPaymentAnalysis monitorBackPaymentAnalysis;
	/**
	 * 数据格式化
	 */
	private DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	
	private PopupWindow popupWindow;
	
	private View popView;
	
	private RelativeLayout relative_day;
	private RelativeLayout relative_week;
	private RelativeLayout relative_month;
	private RelativeLayout relative_quarter;
	private RelativeLayout relative_year;
	
	private ImageView img_day;
	private ImageView img_week;
	private ImageView img_month;
	private ImageView img_quarter;
	private ImageView img_year;
	private ImageView[] imgViews;
	
	private RoseChart01View day_roseChart;
	private RoseChart01View week_roseChart;
	private RoseChart01View month_roseChart;
	private RoseChart01View quarter_roseChart;
	private RoseChart01View year_roseChart;
	private RoseChart01View[] roseCharts;
	
	private static final int[] COLORS = {0xff2BC8C8,0xffB6A3DE,0xff8C9AAF,0xff59B2EF,0xffffB980,0xffD87B7F,0xff000000,0xff000000,0xff000000,0xff000000,0xff000000};
	
	private int popSelectIndex = 0;
	
	public static FieldMonitorBackPaymentAnalysisFragment newInstance() {
		FieldMonitorBackPaymentAnalysisFragment fieldMonitorBackPaymentAnalysisFragment = new FieldMonitorBackPaymentAnalysisFragment();
		return fieldMonitorBackPaymentAnalysisFragment;
	}
	
	@Override
	public int getLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_monitor_backpayment_analysis;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		tv_time_label = (TextView) mRootView.findViewById(R.id.tv_time_label);
		img_selecttime = (ImageView) mRootView.findViewById(R.id.img_selecttime);
		tv_selecttime = (TextView) mRootView.findViewById(R.id.tv_selecttime);
		
		tv_rate = (TextView) mRootView.findViewById(R.id.tv_rate);
		
		tv_returnMoney = (TextView) mRootView.findViewById(R.id.tv_returnMoney);
		tv_returnTaskMoney = (TextView) mRootView.findViewById(R.id.tv_returnTaskMoney);
		
		tv_depositMoney = (TextView) mRootView.findViewById(R.id.tv_depositMoney);
		tv_buildMoney = (TextView) mRootView.findViewById(R.id.tv_buildMoney);
		
		tv_businessMortgage = (TextView) mRootView.findViewById(R.id.tv_businessMortgage);
		tv_accumulation = (TextView) mRootView.findViewById(R.id.tv_accumulation);
		
		day_roseChart = (RoseChart01View) mRootView.findViewById(R.id.day_roseChart);
		week_roseChart = (RoseChart01View) mRootView.findViewById(R.id.week_roseChart);
		month_roseChart = (RoseChart01View) mRootView.findViewById(R.id.month_roseChart);
		quarter_roseChart = (RoseChart01View) mRootView.findViewById(R.id.quarter_roseChart);
		year_roseChart = (RoseChart01View) mRootView.findViewById(R.id.year_roseChart);
		
		roseCharts = new RoseChart01View[]{day_roseChart,week_roseChart,month_roseChart,quarter_roseChart,year_roseChart};
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		img_selecttime.setOnClickListener(this);
		tv_selecttime.setOnClickListener(this);
	}

	@Override
	public void businessLogic() {
		// TODO Auto-generated method stub
		//setupToolbar();
		requestHttp();	
		//registerReceiver();
	}
	
	private void requestHttp(){
		int projectId = UserManager.getCurrentProject(mParentActivity).getProjectId();
		String userId = UserManager.getUser().getUserid();
		//String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getMonitorBackPaymentAnalysis(), projectId,userId), REQUEST_MONITORBACKPAYMENTANALYSISACTIVITY, this);
	}
	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_MONITORBACKPAYMENTANALYSISACTIVITY.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				monitorBackPaymentAnalysis = JSON.parseObject(data, MonitorBackPaymentAnalysis.class);
				if (monitorBackPaymentAnalysis == null) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置玫瑰图
				//day
				if(monitorBackPaymentAnalysis.day.salesConstitute != null && monitorBackPaymentAnalysis.day.salesConstitute.size() > 0){
					LinkedList<PieData> dayPieData = new LinkedList<PieData>();
					int dayColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorBackPaymentAnalysis.day.salesConstitute){
						dayPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[dayColorIndex++]));
					}
					day_roseChart.chartRender(dayPieData);
				}
				//week
				if(monitorBackPaymentAnalysis.week.salesConstitute != null && monitorBackPaymentAnalysis.week.salesConstitute.size() > 0){
					LinkedList<PieData> weekPieData = new LinkedList<PieData>();
					int weekColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorBackPaymentAnalysis.week.salesConstitute){
						weekPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[weekColorIndex++]));
					}
					week_roseChart.chartRender(weekPieData);
				}
				//month
				if(monitorBackPaymentAnalysis.month.salesConstitute != null && monitorBackPaymentAnalysis.month.salesConstitute.size() > 0){
					LinkedList<PieData> monthPieData = new LinkedList<PieData>();
					int monthColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorBackPaymentAnalysis.month.salesConstitute){
						monthPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[monthColorIndex++]));
					}
					month_roseChart.chartRender(monthPieData);
				}
				//quarter
				if(monitorBackPaymentAnalysis.quarter.salesConstitute != null && monitorBackPaymentAnalysis.quarter.salesConstitute.size() > 0){
					LinkedList<PieData> quarterPieData = new LinkedList<PieData>();
					int quarterColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorBackPaymentAnalysis.quarter.salesConstitute){
						quarterPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[quarterColorIndex++]));
					}
					quarter_roseChart.chartRender(quarterPieData);
				}
				//year
				if(monitorBackPaymentAnalysis.year.salesConstitute != null && monitorBackPaymentAnalysis.year.salesConstitute.size() > 0){
					LinkedList<PieData> yearPieData = new LinkedList<PieData>();
					int yearColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorBackPaymentAnalysis.year.salesConstitute){
						yearPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[yearColorIndex++]));
					}
					year_roseChart.chartRender(yearPieData);
				}
				
				//设置界面数据
				tv_time_label.setText("当日回款分析");
				double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.day.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.day.returnTaskMoney)*100;
				tv_rate.setText(decimalFormat.format(rate) + "%");
				tv_returnMoney.setText(monitorBackPaymentAnalysis.day.returnMoney+"万元");
				tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.day.returnTaskMoney+"万元");
				tv_depositMoney.setText(monitorBackPaymentAnalysis.day.depositMoney+"万元");
				tv_buildMoney.setText(monitorBackPaymentAnalysis.day.buildMoney+"万元");
				tv_businessMortgage.setText(monitorBackPaymentAnalysis.day.businessMortgage+"万元");
				tv_accumulation.setText(monitorBackPaymentAnalysis.day.accumulation+"万元");
				
				//mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void onClick(View v) {
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
					roseCharts[popSelectIndex].setVisibility(View.GONE);
					popSelectIndex = 0;
					imgViews[popSelectIndex].setVisibility(View.VISIBLE);
					roseCharts[popSelectIndex].setVisibility(View.VISIBLE);
					//showStaticInfo(popSelectIndex);
				}
				popupWindow.dismiss();
				break;
			case R.id.relative_week:
				//先判断是否与上次选择的不同
				//不同时才转变
				if(popSelectIndex != 1){
					imgViews[popSelectIndex].setVisibility(View.GONE);
					roseCharts[popSelectIndex].setVisibility(View.GONE);
					popSelectIndex = 1;
					imgViews[popSelectIndex].setVisibility(View.VISIBLE);
					roseCharts[popSelectIndex].setVisibility(View.VISIBLE);
					//showStaticInfo(popSelectIndex);
				}
				popupWindow.dismiss();
				break;
			case R.id.relative_month:
				//先判断是否与上次选择的不同
				//不同时才转变
				if(popSelectIndex != 2){
					imgViews[popSelectIndex].setVisibility(View.GONE);
					roseCharts[popSelectIndex].setVisibility(View.GONE);
					popSelectIndex = 2;
					imgViews[popSelectIndex].setVisibility(View.VISIBLE);
					roseCharts[popSelectIndex].setVisibility(View.VISIBLE);
					//showStaticInfo(popSelectIndex);
				}
				popupWindow.dismiss();
				break;
			case R.id.relative_quarter:
				//先判断是否与上次选择的不同
				//不同时才转变
				if(popSelectIndex != 3){
					imgViews[popSelectIndex].setVisibility(View.GONE);
					roseCharts[popSelectIndex].setVisibility(View.GONE);
					popSelectIndex = 3;
					imgViews[popSelectIndex].setVisibility(View.VISIBLE);
					roseCharts[popSelectIndex].setVisibility(View.VISIBLE);
					//showStaticInfo(popSelectIndex);
				}
				popupWindow.dismiss();
				break;
			case R.id.relative_year:
				//先判断是否与上次选择的不同
				//不同时才转变
				if(popSelectIndex != 4){
					imgViews[popSelectIndex].setVisibility(View.GONE);
					roseCharts[popSelectIndex].setVisibility(View.GONE);
					popSelectIndex = 4;
					imgViews[popSelectIndex].setVisibility(View.VISIBLE);
					roseCharts[popSelectIndex].setVisibility(View.VISIBLE);
					//showStaticInfo(popSelectIndex);
				}
				popupWindow.dismiss();
				break;
		}
	}
	/**
	 * 显示选择时间pop
	 */
	private void showPopWindow(){
		if (popupWindow == null) {  
            LayoutInflater layoutInflater = LayoutInflater.from(mParentActivity);  
            popView = layoutInflater.inflate(R.layout.pop_monitor_sale_analysis, null);  
            
            relative_day = (RelativeLayout) popView.findViewById(R.id.relative_day);
            relative_week = (RelativeLayout) popView.findViewById(R.id.relative_week);
            relative_month = (RelativeLayout) popView.findViewById(R.id.relative_month);
            relative_quarter = (RelativeLayout) popView.findViewById(R.id.relative_quarter);
            relative_year = (RelativeLayout) popView.findViewById(R.id.relative_year);
            
            relative_day.setOnClickListener(this);
            relative_week.setOnClickListener(this);
            relative_month.setOnClickListener(this);
            relative_quarter.setOnClickListener(this);
            relative_year.setOnClickListener(this);
            
            img_day = (ImageView) popView.findViewById(R.id.img_day);
            img_week = (ImageView) popView.findViewById(R.id.img_week);
            img_month = (ImageView) popView.findViewById(R.id.img_month);
            img_quarter = (ImageView) popView.findViewById(R.id.img_quarter);
            img_year = (ImageView) popView.findViewById(R.id.img_year);
            
            imgViews = new ImageView[]{img_day,img_week,img_month,img_quarter,img_year};
            
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
		
        popupWindow.showAsDropDown(img_selecttime);
	}
	/**
	 * 根据选的时间类型显示数据
	 * @param popSelectIndex
	 */
	private void showStaticInfo(int popSelectIndex){
		switch(popSelectIndex){
			case 0:
				//设置日数据
				if(monitorBackPaymentAnalysis.day != null){
					tv_time_label.setText("当日回款分析");
					double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.day.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.day.returnTaskMoney)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_returnMoney.setText(monitorBackPaymentAnalysis.day.returnMoney+"万元");
					tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.day.returnTaskMoney+"万元");
					tv_depositMoney.setText(monitorBackPaymentAnalysis.day.depositMoney+"万元");
					tv_buildMoney.setText(monitorBackPaymentAnalysis.day.buildMoney+"万元");
					tv_businessMortgage.setText(monitorBackPaymentAnalysis.day.businessMortgage+"万元");
					tv_accumulation.setText(monitorBackPaymentAnalysis.day.accumulation+"万元");
				}else{
					showToast("当日数据为空");
				}
				break;
			case 1:
				//设置周数据
				if(monitorBackPaymentAnalysis.week != null){
					tv_time_label.setText("当周回款分析");
					double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.week.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.week.returnTaskMoney)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_returnMoney.setText(monitorBackPaymentAnalysis.week.returnMoney+"万元");
					tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.week.returnTaskMoney+"万元");
					tv_depositMoney.setText(monitorBackPaymentAnalysis.week.depositMoney+"万元");
					tv_buildMoney.setText(monitorBackPaymentAnalysis.week.buildMoney+"万元");
					tv_businessMortgage.setText(monitorBackPaymentAnalysis.week.businessMortgage+"万元");
					tv_accumulation.setText(monitorBackPaymentAnalysis.week.accumulation+"万元");
				}else{
					showToast("当周数据为空");
				}
				break;
			case 2:
				//设置月数据
				if(monitorBackPaymentAnalysis.month != null){
					tv_time_label.setText("当月回款分析");
					double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.month.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.month.returnTaskMoney)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_returnMoney.setText(monitorBackPaymentAnalysis.month.returnMoney+"万元");
					tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.month.returnTaskMoney+"万元");
					tv_depositMoney.setText(monitorBackPaymentAnalysis.month.depositMoney+"万元");
					tv_buildMoney.setText(monitorBackPaymentAnalysis.month.buildMoney+"万元");
					tv_businessMortgage.setText(monitorBackPaymentAnalysis.month.businessMortgage+"万元");
					tv_accumulation.setText(monitorBackPaymentAnalysis.month.accumulation+"万元");
				}else{
					showToast("当月数据为空");
				}
				break;
			case 3:
				//设置季度数据
				if(monitorBackPaymentAnalysis.quarter != null){
					tv_time_label.setText("当季度回款分析");
					double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.quarter.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.quarter.returnTaskMoney)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_returnMoney.setText(monitorBackPaymentAnalysis.quarter.returnMoney+"万元");
					tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.quarter.returnTaskMoney+"万元");
					tv_depositMoney.setText(monitorBackPaymentAnalysis.quarter.depositMoney+"万元");
					tv_buildMoney.setText(monitorBackPaymentAnalysis.quarter.buildMoney+"万元");
					tv_businessMortgage.setText(monitorBackPaymentAnalysis.quarter.businessMortgage+"万元");
					tv_accumulation.setText(monitorBackPaymentAnalysis.quarter.accumulation+"万元");
				}else{
					showToast("当季度数据为空");
				}
				break;
			case 4:
				//设置年数据
				if(monitorBackPaymentAnalysis.year != null){
					tv_time_label.setText("当年回款分析");
					double rate = (double)Integer.parseInt(monitorBackPaymentAnalysis.year.returnMoney)/Integer.parseInt(monitorBackPaymentAnalysis.year.returnTaskMoney)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_returnMoney.setText(monitorBackPaymentAnalysis.year.returnMoney+"万元");
					tv_returnTaskMoney.setText(monitorBackPaymentAnalysis.year.returnTaskMoney+"万元");
					tv_depositMoney.setText(monitorBackPaymentAnalysis.year.depositMoney+"万元");
					tv_buildMoney.setText(monitorBackPaymentAnalysis.year.buildMoney+"万元");
					tv_businessMortgage.setText(monitorBackPaymentAnalysis.year.businessMortgage+"万元");
					tv_accumulation.setText(monitorBackPaymentAnalysis.year.accumulation+"万元");
				}else{
					showToast("当季度数据为空");
				}
				break;
		}
	}

	@Override
	public void setupToolbar() {
		// TODO Auto-generated method stub
	}
}
