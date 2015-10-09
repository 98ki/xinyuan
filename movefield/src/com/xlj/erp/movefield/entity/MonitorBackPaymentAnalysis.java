package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 案场监控-回款分析-日周月季度年
 * 数据实体
 */
public class MonitorBackPaymentAnalysis implements Serializable {

	private static final long serialVersionUID = 1L;

	public BackPaymentAnalysisDataByType day;
	
	public BackPaymentAnalysisDataByType week;
	
	public BackPaymentAnalysisDataByType month;
	
	public BackPaymentAnalysisDataByType quarter;
	
	public BackPaymentAnalysisDataByType year;
	
	
	public static class BackPaymentAnalysisDataByType implements Serializable {
		//销售构成
		public List<SalesConstitute> salesConstitute;
		//公积金
		public String accumulation;
		//楼款金额
		public String buildMoney;
		//商业按揭
		public String businessMortgage;
		//定金金额
		public String depositMoney;
		//回款金额
		public String returnMoney;
		//回款任务
		public String returnTaskMoney;
	}
	public static class SalesConstitute implements Serializable{
		//类别
		public String key;
		//百分比
		public String value;
	}
}
