package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 客户分析--认知途径等
 */
public class CustomerAnalysisKnowWay implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<AnalysisData> knowWay;
	public List<AnalysisData> liveArea;
	public List<AnalysisData> likeType;
	public List<AnalysisData> likePrice;
	public List<AnalysisData> source;
	
	
	public static class AnalysisData implements Serializable{
		//类别
		public String key;
		//百分比
		public String value;
	}

}
