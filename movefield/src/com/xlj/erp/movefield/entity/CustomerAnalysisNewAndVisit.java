package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 客户分析--新客户登记等
 */
public class CustomerAnalysisNewAndVisit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public NewRegister newRegister;
	public ReturnVisit returnVisit;
	public ReceptVisit receptVisit;
	
	public static class NewRegister implements Serializable{
		public List<AnalysisDataNewAndVisit> day;
		public List<AnalysisDataNewAndVisit> week;
		public List<AnalysisDataNewAndVisit> month;
		public List<AnalysisDataNewAndVisit> quater;
		public List<AnalysisDataNewAndVisit> year;
	}
	public static class ReturnVisit implements Serializable{
		public List<AnalysisDataNewAndVisit> day;
		public List<AnalysisDataNewAndVisit> week;
		public List<AnalysisDataNewAndVisit> month;
		public List<AnalysisDataNewAndVisit> quater;
		public List<AnalysisDataNewAndVisit> year;
	}
	public static class ReceptVisit implements Serializable{
		public List<AnalysisDataNewAndVisit> day;
		public List<AnalysisDataNewAndVisit> week;
		public List<AnalysisDataNewAndVisit> month;
		public List<AnalysisDataNewAndVisit> quater;
		public List<AnalysisDataNewAndVisit> year;
	}
	
	
	public static class AnalysisDataNewAndVisit implements Serializable{
		//类别
		public String key;
		//百分比
		public Value value;
	}
	
	public static class Value implements Serializable{
		public String newCustomer;
		public String tReturnVisit;
		public String todayFollow;
	}

}
