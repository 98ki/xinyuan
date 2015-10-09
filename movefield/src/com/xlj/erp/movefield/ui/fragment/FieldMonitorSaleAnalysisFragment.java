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
import com.xlj.erp.movefield.entity.MonitorSaleAnalysis;
import com.xlj.erp.movefield.entity.MonitorSaleAnalysis.SalesConstitute;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.utils.UserManager;
/**
 * 案场监控-销售分析
 */
public class FieldMonitorSaleAnalysisFragment extends BaseFragment {

	private static final String REQUEST_MONITORSALEANALYSISACTIVITY = "monitorSaleAnalysis";
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
	 * 签约金额
	 */
	private TextView tv_signamount;
	/**
	 * 签约任务
	 */
	private TextView tv_targetamount;
	/**
	 * 签约套数
	 */
	private TextView tv_signcount;
	/**
	 * 签约面积
	 */
	private TextView tv_signarea;
	/**
	 * 认购金额
	 */
	private TextView tv_subscribeamount;
	/**
	 * 认购面积
	 */
	private TextView tv_subscribearea;
	/**
	 * 认购套数
	 */
	private TextView tv_subscribecount;
	/**
	 * 接口返回数据
	 */
	private MonitorSaleAnalysis monitorSaleAnalysis;
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
	
	public static FieldMonitorSaleAnalysisFragment newInstance() {
		FieldMonitorSaleAnalysisFragment fieldMonitorSaleAnalysisFragment = new FieldMonitorSaleAnalysisFragment();
		return fieldMonitorSaleAnalysisFragment;
	}
	
	@Override
	public int getLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_monitor_sale_analysis;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		tv_time_label = (TextView) mRootView.findViewById(R.id.tv_time_label);
		img_selecttime = (ImageView) mRootView.findViewById(R.id.img_selecttime);
		tv_selecttime = (TextView) mRootView.findViewById(R.id.tv_selecttime);
		
		tv_rate = (TextView) mRootView.findViewById(R.id.tv_rate);
		
		tv_signamount = (TextView) mRootView.findViewById(R.id.tv_signamount);
		tv_targetamount = (TextView) mRootView.findViewById(R.id.tv_targetamount);
		
		tv_signcount = (TextView) mRootView.findViewById(R.id.tv_signcount);
		tv_signarea = (TextView) mRootView.findViewById(R.id.tv_signarea);
		
		tv_subscribeamount = (TextView) mRootView.findViewById(R.id.tv_subscribeamount);
		tv_subscribearea = (TextView) mRootView.findViewById(R.id.tv_subscribearea);
		tv_subscribecount = (TextView) mRootView.findViewById(R.id.tv_subscribecount);
		
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
		requestHttp(String.format(Urls.getMonitorSaleAnalysis(), projectId,userId), REQUEST_MONITORSALEANALYSISACTIVITY, this);
	}
	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_MONITORSALEANALYSISACTIVITY.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				monitorSaleAnalysis = JSON.parseObject(data, MonitorSaleAnalysis.class);
				if (monitorSaleAnalysis == null) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置玫瑰图
				//day
				if(monitorSaleAnalysis.day.salesConstitute != null && monitorSaleAnalysis.day.salesConstitute.size() > 0){
					LinkedList<PieData> dayPieData = new LinkedList<PieData>();
					int dayColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorSaleAnalysis.day.salesConstitute){
						dayPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[dayColorIndex++]));
					}
					day_roseChart.chartRender(dayPieData);
				}
				//week
				if(monitorSaleAnalysis.week.salesConstitute != null && monitorSaleAnalysis.week.salesConstitute.size() > 0){
					LinkedList<PieData> weekPieData = new LinkedList<PieData>();
					int weekColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorSaleAnalysis.week.salesConstitute){
						weekPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[weekColorIndex++]));
					}
					week_roseChart.chartRender(weekPieData);
				}
				//month
				if(monitorSaleAnalysis.month.salesConstitute != null && monitorSaleAnalysis.month.salesConstitute.size() > 0){
					LinkedList<PieData> monthPieData = new LinkedList<PieData>();
					int monthColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorSaleAnalysis.month.salesConstitute){
						monthPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[monthColorIndex++]));
					}
					month_roseChart.chartRender(monthPieData);
				}
				//quarter
				if(monitorSaleAnalysis.quarter.salesConstitute != null && monitorSaleAnalysis.quarter.salesConstitute.size() > 0){
					LinkedList<PieData> quarterPieData = new LinkedList<PieData>();
					int quarterColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorSaleAnalysis.quarter.salesConstitute){
						quarterPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[quarterColorIndex++]));
					}
					quarter_roseChart.chartRender(quarterPieData);
				}
				//year
				if(monitorSaleAnalysis.year.salesConstitute != null && monitorSaleAnalysis.year.salesConstitute.size() > 0){
					LinkedList<PieData> yearPieData = new LinkedList<PieData>();
					int yearColorIndex = 0;
					for(SalesConstitute salesConstitute : monitorSaleAnalysis.year.salesConstitute){
						yearPieData.add(new PieData(salesConstitute.key,salesConstitute.key,Double.parseDouble(salesConstitute.value), COLORS[yearColorIndex++]));
					}
					year_roseChart.chartRender(yearPieData);
				}
				
				//设置界面数据
				tv_time_label.setText("当日销售分析");
				double rate = (double)Integer.parseInt(monitorSaleAnalysis.day.signamount)/Integer.parseInt(monitorSaleAnalysis.day.targetamount)*100;
				tv_rate.setText(decimalFormat.format(rate) + "%");
				tv_signamount.setText(monitorSaleAnalysis.day.signamount+"万元");
				tv_targetamount.setText(monitorSaleAnalysis.day.targetamount+"万元");
				tv_signcount.setText(monitorSaleAnalysis.day.signcount+"套");
				tv_signarea.setText(monitorSaleAnalysis.day.signarea+"M2");
				tv_subscribeamount.setText(monitorSaleAnalysis.day.subscribeamount+"万元");
				tv_subscribearea.setText(monitorSaleAnalysis.day.subscribearea+"M2");
				tv_subscribecount.setText(monitorSaleAnalysis.day.subscribecount+"套");
				
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
				if(monitorSaleAnalysis.day != null){
					tv_time_label.setText("当日销售分析");
					double rate = (double)Integer.parseInt(monitorSaleAnalysis.day.signamount)/Integer.parseInt(monitorSaleAnalysis.day.targetamount)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_signamount.setText(monitorSaleAnalysis.day.signamount+"万元");
					tv_targetamount.setText(monitorSaleAnalysis.day.targetamount+"万元");
					tv_signcount.setText(monitorSaleAnalysis.day.signcount+"套");
					tv_signarea.setText(monitorSaleAnalysis.day.signarea+"M2");
					tv_subscribeamount.setText(monitorSaleAnalysis.day.subscribeamount+"万元");
					tv_subscribearea.setText(monitorSaleAnalysis.day.subscribearea+"M2");
					tv_subscribecount.setText(monitorSaleAnalysis.day.subscribecount+"套");
				}else{
					showToast("当日数据为空");
				}
				break;
			case 1:
				//设置周数据
				if(monitorSaleAnalysis.week != null){
					tv_time_label.setText("当周销售分析");
					double rate = (double)Integer.parseInt(monitorSaleAnalysis.week.signamount)/Integer.parseInt(monitorSaleAnalysis.week.targetamount)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_signamount.setText(monitorSaleAnalysis.week.signamount+"万元");
					tv_targetamount.setText(monitorSaleAnalysis.week.targetamount+"万元");
					tv_signcount.setText(monitorSaleAnalysis.week.signcount+"套");
					tv_signarea.setText(monitorSaleAnalysis.week.signarea+"M2");
					tv_subscribeamount.setText(monitorSaleAnalysis.week.subscribeamount+"万元");
					tv_subscribearea.setText(monitorSaleAnalysis.week.subscribearea+"M2");
					tv_subscribecount.setText(monitorSaleAnalysis.week.subscribecount+"套");
				}else{
					showToast("当周数据为空");
				}
				break;
			case 2:
				//设置月数据
				if(monitorSaleAnalysis.month != null){
					tv_time_label.setText("当月销售分析");
					double rate = (double)Integer.parseInt(monitorSaleAnalysis.month.signamount)/Integer.parseInt(monitorSaleAnalysis.month.targetamount)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_signamount.setText(monitorSaleAnalysis.month.signamount+"万元");
					tv_targetamount.setText(monitorSaleAnalysis.month.targetamount+"万元");
					tv_signcount.setText(monitorSaleAnalysis.month.signcount+"套");
					tv_signarea.setText(monitorSaleAnalysis.month.signarea+"M2");
					tv_subscribeamount.setText(monitorSaleAnalysis.month.subscribeamount+"万元");
					tv_subscribearea.setText(monitorSaleAnalysis.month.subscribearea+"M2");
					tv_subscribecount.setText(monitorSaleAnalysis.month.subscribecount+"套");
				}else{
					showToast("当月数据为空");
				}
				break;
			case 3:
				//设置季度数据
				if(monitorSaleAnalysis.quarter != null){
					tv_time_label.setText("当季度销售分析");
					double rate = (double)Integer.parseInt(monitorSaleAnalysis.quarter.signamount)/Integer.parseInt(monitorSaleAnalysis.quarter.targetamount)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_signamount.setText(monitorSaleAnalysis.quarter.signamount+"万元");
					tv_targetamount.setText(monitorSaleAnalysis.quarter.targetamount+"万元");
					tv_signcount.setText(monitorSaleAnalysis.quarter.signcount+"套");
					tv_signarea.setText(monitorSaleAnalysis.quarter.signarea+"M2");
					tv_subscribeamount.setText(monitorSaleAnalysis.quarter.subscribeamount+"万元");
					tv_subscribearea.setText(monitorSaleAnalysis.quarter.subscribearea+"M2");
					tv_subscribecount.setText(monitorSaleAnalysis.quarter.subscribecount+"套");
				}else{
					showToast("当季度数据为空");
				}
				break;
			case 4:
				//设置年数据
				if(monitorSaleAnalysis.year != null){
					tv_time_label.setText("当年销售分析");
					double rate = (double)Integer.parseInt(monitorSaleAnalysis.year.signamount)/Integer.parseInt(monitorSaleAnalysis.year.targetamount)*100;
					tv_rate.setText(decimalFormat.format(rate) + "%");
					tv_signamount.setText(monitorSaleAnalysis.year.signamount+"万元");
					tv_targetamount.setText(monitorSaleAnalysis.year.targetamount+"万元");
					tv_signcount.setText(monitorSaleAnalysis.year.signcount+"套");
					tv_signarea.setText(monitorSaleAnalysis.year.signarea+"M2");
					tv_subscribeamount.setText(monitorSaleAnalysis.year.subscribeamount+"万元");
					tv_subscribearea.setText(monitorSaleAnalysis.year.subscribearea+"M2");
					tv_subscribecount.setText(monitorSaleAnalysis.year.subscribecount+"套");
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
