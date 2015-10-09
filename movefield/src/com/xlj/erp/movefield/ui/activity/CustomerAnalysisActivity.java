package com.xlj.erp.movefield.ui.activity;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.xclcharts.chart.PieData;
import org.xclcharts.chart.SplineData;
import org.xclcharts.renderer.XEnum;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.chart.RoseChart01View;
import com.xlj.erp.movefield.chart.SplineChart01View;
import com.xlj.erp.movefield.entity.CustomerAnalysisKnowWay;
import com.xlj.erp.movefield.entity.CustomerAnalysisKnowWay.AnalysisData;
import com.xlj.erp.movefield.entity.CustomerAnalysisNewAndVisit;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.ActionSheetDialog;
import com.xlj.erp.movefield.widget.ActionSheetDialog.OnSheetItemClickListener;
import com.xlj.erp.movefield.widget.ActionSheetDialog.SheetItemColor;
import com.xlj.erp.movefield.widget.SegmentControl.OnSegmentControlClickListener;
import com.xlj.erp.movefield.widget.SegmentControl;

public class CustomerAnalysisActivity extends BaseToolbarActivity implements OnSegmentControlClickListener{

	/**
	 * 认知途径等
	 */
	private static final String REQUEST_MONITORCUSTOMERKNOWWAYANALYSISACTIVITY = "monitorCustomerKnowWayAnalysis";
	/**
	 * 新客户等
	 */
	private static final String REQUEST_MONITORCUSTOMERNEWANALYSISACTIVITY = "monitorCustomerNewAnalysis";
	/**
	 * 玫瑰图色值
	 */
	private static final int[] COLORS = {0xff2BC8C8,0xffB6A3DE,0xff8C9AAF,0xff59B2EF,0xffffB980,0xffD87B7F,0xff000000,0xff000000,0xff000000,0xff000000,0xff000000};
	/**
	 * 玫瑰图上部文字
	 */
	private static final String[] ROSE_LABELS = {"认知途径分析","居住区域分析","意向户型分析","意向总价分析","客户来源分析"};
	/**
	 * 线性图上部文字
	 */
	private static final String[] LINES_LABELS = {"新客户登记","回访情况","接访情况"};

	/**
	 * 认知途径等数据
	 */
	private CustomerAnalysisKnowWay customerAnalysisKnowWay;
	/**
	 * 新客户等数据
	 */
	private CustomerAnalysisNewAndVisit customerAnalysisNewAndVisit;
	/**
	 * 认知途径等label
	 */
	private TextView tv_analysis_label;
	/**
	 * 认知途径等箭头
	 */
	private ImageView img_analysis_label;
	/**
	 * 认知来源图
	 */
	private RoseChart01View knowWay_roseChart;
	/**
	 * 居住区域
	 */
	private RoseChart01View liveArea_roseChart;
	/**
	 * 意向户型
	 */
	private RoseChart01View likeType_roseChart;
	/**
	 * 意向总价
	 */
	private RoseChart01View likePrice_roseChart;
	/**
	 * 来源
	 */
	private RoseChart01View source_roseChart;
	/**
	 * 认知途径等图数组
	 */
	private RoseChart01View[] roseCharts;
	/**
	 * 玫瑰图index
	 */
	private int roseChartIndex = 0;
	/**
	 * 新客户等label
	 */
	private TextView tv_newcustomer_vivit_label;
	/**
	 * 新客户等箭头
	 */
	private ImageView img_newcustomer_vivit_label;
	
	/**
	 * 新客户登记布局
	 */
	private LinearLayout linear_new_customer;
	/**
	 * 选择框
	 */
	private SegmentControl segment_control_newCustomer;
	/**
	 * 周线形图
	 */
	private SplineChart01View week_newcustomer_splineChart;
	/**
	 * 月线形图
	 */
	private SplineChart01View month_newcustomer_splineChart;
	/**
	 * 年线形图
	 */
	private SplineChart01View year_newcustomer_splineChart;
	/**
	 * 新客户登记线形图数组
	 */
	private SplineChart01View[] newcustomerSplineCharts;
	/**
	 * 新客户登记线性图显示索引
	 */
	private int newcustomerSplineChartIndex = 0;
	
	/**
	 * 回访情况布局
	 */
	private LinearLayout linear_return_visit;
	/**
	 * 选择框
	 */
	private SegmentControl segment_control_returnVisit;
	/**
	 * 周线形图
	 */
	private SplineChart01View week_returnVisit_splineChart;
	/**
	 * 月线形图
	 */
	private SplineChart01View month_returnVisit_splineChart;
	/**
	 * 年线形图
	 */
	private SplineChart01View year_returnVisit_splineChart;
	/**
	 * 回访线形图数组
	 */
	private SplineChart01View[] returnVisitSplineCharts;
	/**
	 * 回访线性图显示索引
	 */
	private int returnVisitSplineChartIndex = 0;
	
	/**
	 * 接访情况布局
	 */
	private LinearLayout linear_recept_visit;
	/**
	 * 选择框
	 */
	private SegmentControl segment_control_receptVisit;
	/**
	 * 周线形图
	 */
	private SplineChart01View week_receptVisit_splineChart;
	/**
	 * 月线形图
	 */
	private SplineChart01View month_receptVisit_splineChart;
	/**
	 * 年线形图
	 */
	private SplineChart01View year_receptVisit_splineChart;
	/**
	 * 接访线形图数组
	 */
	private SplineChart01View[] receptVisitSplineCharts;
	/**
	 * 接访线性图显示索引
	 */
	private int receptVisitSplineChartIndex = 0;
	/**
	 * 新客户等布局数组
	 */
	private LinearLayout[] linearNewReturnRecepts;
	/**
	 * 新客户登记、回访、接访显示索引
	 */
	private int linearNewReturnReceptIndex = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题
		setTitle("客户分析");
		//请求数据
		requestHttp();
		
		/*mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				requestHttp();
			}
		});*/
	}
	private void requestHttp(){
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String userId = UserManager.getUser().getUserid();
		//String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getCusttomerAnalysisKnowWay(), projectId,userId), REQUEST_MONITORCUSTOMERKNOWWAYANALYSISACTIVITY, this);
		requestHttp(String.format(Urls.getCusttomerAnalysisNew(), projectId,userId), REQUEST_MONITORCUSTOMERNEWANALYSISACTIVITY, this);
	}
	@Override
	public int getLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.activity_customer_analysis;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		tv_analysis_label = (TextView) this.findViewById(R.id.tv_analysis_label);
		img_analysis_label = (ImageView) this.findViewById(R.id.img_analysis_label);
		knowWay_roseChart = (RoseChart01View) this.findViewById(R.id.knowWay_roseChart);
		liveArea_roseChart = (RoseChart01View) this.findViewById(R.id.liveArea_roseChart);
		likeType_roseChart = (RoseChart01View) this.findViewById(R.id.likeType_roseChart);
		likePrice_roseChart = (RoseChart01View) this.findViewById(R.id.likePrice_roseChart);
		source_roseChart = (RoseChart01View) this.findViewById(R.id.source_roseChart);
		roseCharts = new RoseChart01View[]{knowWay_roseChart,liveArea_roseChart,likeType_roseChart,likePrice_roseChart,source_roseChart};
		
		tv_newcustomer_vivit_label = (TextView) this.findViewById(R.id.tv_newcustomer_vivit_label);
		img_newcustomer_vivit_label = (ImageView) this.findViewById(R.id.img_newcustomer_vivit_label);
		
		//新客户登记相关控件
		linear_new_customer = (LinearLayout) this.findViewById(R.id.linear_new_customer);
		segment_control_newCustomer = (SegmentControl) this.findViewById(R.id.segment_control_newCustomer);
		week_newcustomer_splineChart = (SplineChart01View) this.findViewById(R.id.week_newcustomer_splineChart);
		month_newcustomer_splineChart = (SplineChart01View) this.findViewById(R.id.month_newcustomer_splineChart);
		year_newcustomer_splineChart = (SplineChart01View) this.findViewById(R.id.year_newcustomer_splineChart);
		newcustomerSplineCharts = new SplineChart01View[]{week_newcustomer_splineChart,month_newcustomer_splineChart,year_newcustomer_splineChart};
		//回访情况相关控件
		linear_return_visit = (LinearLayout) this.findViewById(R.id.linear_return_visit);
		segment_control_returnVisit = (SegmentControl) this.findViewById(R.id.segment_control_returnVisit);
		week_returnVisit_splineChart = (SplineChart01View) this.findViewById(R.id.week_returnVisit_splineChart);
		month_returnVisit_splineChart = (SplineChart01View) this.findViewById(R.id.month_returnVisit_splineChart);
		year_returnVisit_splineChart = (SplineChart01View) this.findViewById(R.id.year_returnVisit_splineChart);
		returnVisitSplineCharts = new SplineChart01View[]{week_returnVisit_splineChart,month_returnVisit_splineChart,year_returnVisit_splineChart};
		//接访情况相关控件
		linear_recept_visit = (LinearLayout) this.findViewById(R.id.linear_recept_visit);
		segment_control_receptVisit = (SegmentControl) this.findViewById(R.id.segment_control_receptVisit);
		week_receptVisit_splineChart = (SplineChart01View) this.findViewById(R.id.week_receptVisit_splineChart);
		month_receptVisit_splineChart = (SplineChart01View) this.findViewById(R.id.month_receptVisit_splineChart);
		year_receptVisit_splineChart = (SplineChart01View) this.findViewById(R.id.year_receptVisit_splineChart);
		receptVisitSplineCharts = new SplineChart01View[]{week_receptVisit_splineChart,month_receptVisit_splineChart,year_receptVisit_splineChart};
		
		linearNewReturnRecepts = new LinearLayout[]{linear_new_customer,linear_return_visit,linear_recept_visit};
	}
	
	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_MONITORCUSTOMERKNOWWAYANALYSISACTIVITY.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				customerAnalysisKnowWay = JSON.parseObject(data, CustomerAnalysisKnowWay.class);
				if (customerAnalysisKnowWay == null) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置玫瑰图
				//认知途径
				if(customerAnalysisKnowWay.knowWay != null && customerAnalysisKnowWay.knowWay.size() > 0){
					LinkedList<PieData> knowWayPieData = new LinkedList<PieData>();
					int knowWayColorIndex = 0;
					for(AnalysisData analysisData : customerAnalysisKnowWay.knowWay){
						knowWayPieData.add(new PieData(analysisData.key,analysisData.key,Double.parseDouble(analysisData.value), COLORS[knowWayColorIndex++]));
					}
					knowWay_roseChart.chartRender(knowWayPieData);
				}
				//居住区域
				if(customerAnalysisKnowWay.liveArea != null && customerAnalysisKnowWay.liveArea.size() > 0){
					LinkedList<PieData> liveAreaPieData = new LinkedList<PieData>();
					int liveAreaColorIndex = 0;
					for(AnalysisData analysisData : customerAnalysisKnowWay.liveArea){
						liveAreaPieData.add(new PieData(analysisData.key,analysisData.key,Double.parseDouble(analysisData.value), COLORS[liveAreaColorIndex++]));
					}
					liveArea_roseChart.chartRender(liveAreaPieData);
				}
				//意向客户
				if(customerAnalysisKnowWay.likeType != null && customerAnalysisKnowWay.likeType.size() > 0){
					LinkedList<PieData> likeTypePieData = new LinkedList<PieData>();
					int likeTypeColorIndex = 0;
					for(AnalysisData analysisData : customerAnalysisKnowWay.likeType){
						likeTypePieData.add(new PieData(analysisData.key,analysisData.key,Double.parseDouble(analysisData.value), COLORS[likeTypeColorIndex++]));
					}
					likeType_roseChart.chartRender(likeTypePieData);
				}
				//意向总价
				if(customerAnalysisKnowWay.likePrice != null && customerAnalysisKnowWay.likePrice.size() > 0){
					LinkedList<PieData> likePricePieData = new LinkedList<PieData>();
					int likePriceColorIndex = 0;
					for(AnalysisData analysisData : customerAnalysisKnowWay.likePrice){
						likePricePieData.add(new PieData(analysisData.key,analysisData.key,Double.parseDouble(analysisData.value), COLORS[likePriceColorIndex++]));
					}
					likePrice_roseChart.chartRender(likePricePieData);
				}
				//客户来源
				if(customerAnalysisKnowWay.source != null && customerAnalysisKnowWay.source.size() > 0){
					LinkedList<PieData> sourcePieData = new LinkedList<PieData>();
					int sourceColorIndex = 0;
					for(AnalysisData analysisData : customerAnalysisKnowWay.source){
						sourcePieData.add(new PieData(analysisData.key,analysisData.key,Double.parseDouble(analysisData.value), COLORS[sourceColorIndex++]));
					}
					source_roseChart.chartRender(sourcePieData);
				}
				
				//mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}else if (REQUEST_MONITORCUSTOMERNEWANALYSISACTIVITY.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				customerAnalysisNewAndVisit = JSON.parseObject(data, CustomerAnalysisNewAndVisit.class);
				if (customerAnalysisNewAndVisit == null) {
					showToast(R.string.response_invalid);
					//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				//设置新客户登记的周数据
				LinkedList<String> labelsWeekNewcustomer = new LinkedList<String>();
				LinkedList<SplineData> chartDataWeekNewcustomer = new LinkedList<SplineData>();
				labelsWeekNewcustomer.add("");
				labelsWeekNewcustomer.add("周日");
				labelsWeekNewcustomer.add("周一");
				labelsWeekNewcustomer.add("周二");
				labelsWeekNewcustomer.add("周三");
				labelsWeekNewcustomer.add("周四");
				labelsWeekNewcustomer.add("周五");
				labelsWeekNewcustomer.add("周六");
				//线1的数据集
				LinkedHashMap<Double,Double> linePointWeekNewcustomer = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.week != null && customerAnalysisNewAndVisit.newRegister.week.size() > 0){
					for(int i=0,index=0;i<customerAnalysisNewAndVisit.newRegister.week.size();i++,index++){
						linePointWeekNewcustomer.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.week.get(i).value.newCustomer));
					}
				}
				SplineData dataSeriesWeekNewcustomer = new SplineData("周新客户登记",linePointWeekNewcustomer,
						COLORS[0] );
				//dataSeriesWeekNewcustomer.setDotStyle(XEnum.DotStyle.RECT);	
				
				chartDataWeekNewcustomer.add(dataSeriesWeekNewcustomer);
				week_newcustomer_splineChart.chartRender(labelsWeekNewcustomer,chartDataWeekNewcustomer,100,20);
				
				//设置新客户登记的月数据
				LinkedList<String> labelsMonthNewcustomer = new LinkedList<String>();
				LinkedList<SplineData> chartDataMonthNewcustomer = new LinkedList<SplineData>();
				labelsMonthNewcustomer.add("");
				labelsMonthNewcustomer.add("4日");
				labelsMonthNewcustomer.add("8日");
				labelsMonthNewcustomer.add("12日");
				labelsMonthNewcustomer.add("16日");
				labelsMonthNewcustomer.add("20日");
				labelsMonthNewcustomer.add("24日");
				labelsMonthNewcustomer.add("28日");
				//线1的数据集
				LinkedHashMap<Double,Double> linePointMonthNewcustomer = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.month != null && customerAnalysisNewAndVisit.newRegister.month.size() > 0){
					for(int i=3,index=0;i<customerAnalysisNewAndVisit.newRegister.month.size();i+=4,index++){
						linePointMonthNewcustomer.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.month.get(i).value.newCustomer));
					}
				}
				SplineData dataSeriesMonthNewcustomer = new SplineData("月新客户登记",linePointMonthNewcustomer,
						COLORS[1] );
				chartDataMonthNewcustomer.add(dataSeriesMonthNewcustomer);
				month_newcustomer_splineChart.chartRender(labelsMonthNewcustomer,chartDataMonthNewcustomer,100,20);
				
				//设置新客户登记的年数据
				LinkedList<String> labelsYearNewcustomer = new LinkedList<String>();
				LinkedList<SplineData> chartDataYearNewcustomer = new LinkedList<SplineData>();
				labelsYearNewcustomer.add("");
				labelsYearNewcustomer.add("1月");
				labelsYearNewcustomer.add("2月");
				labelsYearNewcustomer.add("4月");
				labelsYearNewcustomer.add("6月");
				labelsYearNewcustomer.add("8月");
				labelsYearNewcustomer.add("10月");
				labelsYearNewcustomer.add("12月");
				
				LinkedHashMap<Double,Double> linePointYearNewcustomer = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.year != null && customerAnalysisNewAndVisit.newRegister.year.size() > 0){
					//添加一月的数据
					linePointYearNewcustomer.put(20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.year.get(0).value.newCustomer));
					//添加偶数月的数据
					for(int i=1,index=1;i<customerAnalysisNewAndVisit.newRegister.year.size();i+=2,index++){
						linePointYearNewcustomer.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.year.get(i).value.newCustomer));
					}
				}
				SplineData dataSeriesYearNewcustomer = new SplineData("年新客户登记",linePointYearNewcustomer,
						COLORS[2] );
				chartDataYearNewcustomer.add(dataSeriesYearNewcustomer);
				year_newcustomer_splineChart.chartRender(labelsYearNewcustomer,chartDataYearNewcustomer,100,20);
				
				
				//设置回访的周数据
				LinkedList<String> labelsWeekReturnVisit = new LinkedList<String>();
				LinkedList<SplineData> chartDataWeekReturnVisit = new LinkedList<SplineData>();
				labelsWeekReturnVisit.add("");
				labelsWeekReturnVisit.add("周日");
				labelsWeekReturnVisit.add("周一");
				labelsWeekReturnVisit.add("周二");
				labelsWeekReturnVisit.add("周三");
				labelsWeekReturnVisit.add("周四");
				labelsWeekReturnVisit.add("周五");
				labelsWeekReturnVisit.add("周六");
				
				LinkedHashMap<Double,Double> linePointWeekReturnVisit = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.week != null && customerAnalysisNewAndVisit.newRegister.week.size() > 0){
					for(int i=0,index=0;i<customerAnalysisNewAndVisit.newRegister.week.size();i++,index++){
						linePointWeekReturnVisit.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.week.get(i).value.tReturnVisit));
					}
				}
				SplineData dataSeriesWeekReturnVisit = new SplineData("周回访",linePointWeekReturnVisit,
						COLORS[0] );
				//dataSeriesWeekReturnVisit.setDotStyle(XEnum.DotStyle.RECT);	
				
				chartDataWeekReturnVisit.add(dataSeriesWeekReturnVisit);
				week_returnVisit_splineChart.chartRender(labelsWeekReturnVisit,chartDataWeekReturnVisit,100,20);
				
				//设置回访的月数据
				LinkedList<String> labelsMonthReturnVisit = new LinkedList<String>();
				LinkedList<SplineData> chartDataMonthReturnVisit = new LinkedList<SplineData>();
				labelsMonthReturnVisit.add("");
				labelsMonthReturnVisit.add("4日");
				labelsMonthReturnVisit.add("8日");
				labelsMonthReturnVisit.add("12日");
				labelsMonthReturnVisit.add("16日");
				labelsMonthReturnVisit.add("20日");
				labelsMonthReturnVisit.add("24日");
				labelsMonthReturnVisit.add("28日");
				
				LinkedHashMap<Double,Double> linePointMonthReturnVisit = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.month != null && customerAnalysisNewAndVisit.newRegister.month.size() > 0){
					for(int i=3,index=0;i<customerAnalysisNewAndVisit.newRegister.month.size();i+=4,index++){
						linePointMonthReturnVisit.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.month.get(i).value.tReturnVisit));
					}
				}
				SplineData dataSeriesMonthReturnVisit = new SplineData("月回访",linePointMonthReturnVisit,
						COLORS[1] );
				chartDataMonthReturnVisit.add(dataSeriesMonthReturnVisit);
				month_returnVisit_splineChart.chartRender(labelsMonthReturnVisit,chartDataMonthReturnVisit,100,20);
				//设置回访的年数据
				LinkedList<String> labelsYearReturnVisit = new LinkedList<String>();
				LinkedList<SplineData> chartDataYearReturnVisit = new LinkedList<SplineData>();
				labelsYearReturnVisit.add("");
				labelsYearReturnVisit.add("1月");
				labelsYearReturnVisit.add("2月");
				labelsYearReturnVisit.add("4月");
				labelsYearReturnVisit.add("6月");
				labelsYearReturnVisit.add("8月");
				labelsYearReturnVisit.add("10月");
				labelsYearReturnVisit.add("12月");
				
				LinkedHashMap<Double,Double> linePointYearReturnVisit = new LinkedHashMap<Double,Double>();
				if(customerAnalysisNewAndVisit.newRegister!=null && customerAnalysisNewAndVisit.newRegister.year != null && customerAnalysisNewAndVisit.newRegister.year.size() > 0){
					//添加一月的数据
					linePointYearReturnVisit.put(20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.year.get(0).value.tReturnVisit));
					//添加偶数月的数据
					for(int i=1,index=1;i<customerAnalysisNewAndVisit.newRegister.year.size();i+=2,index++){
						linePointYearReturnVisit.put((index+1)*20d, Double.parseDouble(customerAnalysisNewAndVisit.newRegister.year.get(i).value.tReturnVisit));
					}
				}
				SplineData dataSeriesYearReturnVisit = new SplineData("年回访",linePointYearReturnVisit,
						COLORS[2] );
				chartDataYearReturnVisit.add(dataSeriesYearReturnVisit);
				year_returnVisit_splineChart.chartRender(labelsYearReturnVisit,chartDataYearReturnVisit,100,20);
				
				//设置接访的周数据
				
				//设置接访的月数据
				
				//设置接访的年数据
				
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				//mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		tv_analysis_label.setOnClickListener(this);
		img_analysis_label.setOnClickListener(this);
		tv_newcustomer_vivit_label.setOnClickListener(this);
		img_newcustomer_vivit_label.setOnClickListener(this);
		
		segment_control_newCustomer.setOnSegmentControlClickListener(this);
		segment_control_returnVisit.setOnSegmentControlClickListener(this);
		segment_control_receptVisit.setOnSegmentControlClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
			case R.id.tv_analysis_label:
			case R.id.img_analysis_label:
				//选择认知途径等弹框
				new ActionSheetDialog(this)
				.builder()
				.setCancelable(true)
				.setCanceledOnTouchOutside(true)
				.addSheetItem("认知途径", SheetItemColor.Black,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								//先判断是否与上次选择的不同
								//不同时才转变
								if(roseChartIndex != 0){
									roseCharts[roseChartIndex].setVisibility(View.GONE);
									roseChartIndex = 0;
									tv_analysis_label.setText(ROSE_LABELS[roseChartIndex]);
									roseCharts[roseChartIndex].setVisibility(View.VISIBLE);
								}
							}
						})
					.addSheetItem("居住区域", SheetItemColor.Black,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							if(roseChartIndex != 1){
								roseCharts[roseChartIndex].setVisibility(View.GONE);
								roseChartIndex = 1;
								tv_analysis_label.setText(ROSE_LABELS[roseChartIndex]);
								roseCharts[roseChartIndex].setVisibility(View.VISIBLE);
							}
						}
					})
					.addSheetItem("意向客户", SheetItemColor.Black,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							if(roseChartIndex != 2){
								roseCharts[roseChartIndex].setVisibility(View.GONE);
								roseChartIndex = 2;
								tv_analysis_label.setText(ROSE_LABELS[roseChartIndex]);
								roseCharts[roseChartIndex].setVisibility(View.VISIBLE);
							}
						}
					})
					.addSheetItem("意向总价", SheetItemColor.Black,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							if(roseChartIndex != 3){
								roseCharts[roseChartIndex].setVisibility(View.GONE);
								roseChartIndex = 3;
								tv_analysis_label.setText(ROSE_LABELS[roseChartIndex]);
								roseCharts[roseChartIndex].setVisibility(View.VISIBLE);
							}
						}
					})
					.addSheetItem("客户来源", SheetItemColor.Black,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							if(roseChartIndex != 4){
								roseCharts[roseChartIndex].setVisibility(View.GONE);
								roseChartIndex = 4;
								tv_analysis_label.setText(ROSE_LABELS[roseChartIndex]);
								roseCharts[roseChartIndex].setVisibility(View.VISIBLE);
							}
						}
					})
					.show();	
				break;
			
			case R.id.tv_newcustomer_vivit_label:
			case R.id.img_newcustomer_vivit_label:
				//选择新客户等等弹框
				new ActionSheetDialog(this)
				.builder()
				.setCancelable(true)
				.setCanceledOnTouchOutside(true)
				.addSheetItem("新客户登记", SheetItemColor.Black,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								if(linearNewReturnReceptIndex != 0){
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.GONE);
									linearNewReturnReceptIndex = 0;
									tv_newcustomer_vivit_label.setText(LINES_LABELS[linearNewReturnReceptIndex]);
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.VISIBLE);
								}
							}
						})
				.addSheetItem("回访情况", SheetItemColor.Black,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								if(linearNewReturnReceptIndex != 1){
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.GONE);
									linearNewReturnReceptIndex = 1;
									tv_newcustomer_vivit_label.setText(LINES_LABELS[linearNewReturnReceptIndex]);
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.VISIBLE);
								}
							}
						})
				.addSheetItem("接访情况", SheetItemColor.Black,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								if(linearNewReturnReceptIndex != 2){
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.GONE);
									linearNewReturnReceptIndex = 2;
									tv_newcustomer_vivit_label.setText(LINES_LABELS[linearNewReturnReceptIndex]);
									linearNewReturnRecepts[linearNewReturnReceptIndex].setVisibility(View.VISIBLE);
								}
							}
						})
				.show();
				break;
		}
	}
	@Override
	public void onSegmentControlClick(int index) {
		// TODO Auto-generated method stub
		switch(linearNewReturnReceptIndex){
			case 0:
				//新客户登记
				if(newcustomerSplineChartIndex != index){
					//隐藏现在的布局
					newcustomerSplineCharts[newcustomerSplineChartIndex].setVisibility(View.GONE);
					//设置新的索引
					newcustomerSplineChartIndex = index;
					//显示新的布局
					newcustomerSplineCharts[newcustomerSplineChartIndex].setVisibility(View.VISIBLE);
				}
			break;
			
			case 1:
				//回访
				if(returnVisitSplineChartIndex != index){
					//隐藏现在的布局
					returnVisitSplineCharts[returnVisitSplineChartIndex].setVisibility(View.GONE);
					//设置新的索引
					returnVisitSplineChartIndex = index;
					//显示新的布局
					returnVisitSplineCharts[returnVisitSplineChartIndex].setVisibility(View.VISIBLE);
				}
			break;
			
			case 2:
				//接访	
				if(receptVisitSplineChartIndex != index){
					//隐藏现在的布局
					receptVisitSplineCharts[receptVisitSplineChartIndex].setVisibility(View.GONE);
					//设置新的索引
					receptVisitSplineChartIndex = index;
					//显示新的布局
					receptVisitSplineCharts[receptVisitSplineChartIndex].setVisibility(View.VISIBLE);
				}
			break;
		}
	}

}
