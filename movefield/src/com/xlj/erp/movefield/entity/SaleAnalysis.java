package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class SaleAnalysis implements Serializable {

	public int bought;
	
	public int hightinterest;
	
	public int interestcount;
	
	public int nointerest;
	
	public int normalinterest;
	
	public SaleStatistical saleStatistical;
	
	public static class SaleStatistical  implements Serializable{
		
		public Weeksalestatus  weeksalestatus;
		public Monthsalestatus  monthsalestatus;
		public Daysalestatus  daysalestatus;
	}
	
	public static class Weeksalestatus implements Serializable{
		public String receptioninfo;
		public String registerinfo;
		public String signamount;
		public String signinfo;
		public String subscribeinfo;
		public String visitinfo;
	}
	public static class Monthsalestatus implements Serializable{
		public String receptioninfo;
		public String registerinfo;
		public String signamount;
		public String signinfo;
		public String subscribeinfo;
		public String visitinfo;
	}
	public static class Daysalestatus implements Serializable{
		public String receptioninfo;
		public String registerinfo;
		public String signamount;
		public String signinfo;
		public String subscribeinfo;
		public String visitinfo;
	}
	
	
}
