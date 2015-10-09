package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 案场监控-销售分析-日周月季度年
 * 数据实体
 */
public class MonitorSaleAnalysis implements Serializable {

	private static final long serialVersionUID = 1L;

	public SaleAnalysisDataByType day;
	
	public SaleAnalysisDataByType week;
	
	public SaleAnalysisDataByType month;
	
	public SaleAnalysisDataByType quarter;
	
	public SaleAnalysisDataByType year;
	
	
	public static class SaleAnalysisDataByType implements Serializable {
		//销售构成
		public List<SalesConstitute> salesConstitute;
		//签约金额
		public String signamount;
		//目标金额
		public String targetamount;
		//签约面积
		public String signarea;
		//签约套数
		public String signcount;
		//认购金额
		public String subscribeamount;
		//认购面积
		public String subscribearea;
		//认购套数
		public String subscribecount;
	}
	public static class SalesConstitute implements Serializable{
		//类别
		public String key;
		//百分比
		public String value;
	}
}
