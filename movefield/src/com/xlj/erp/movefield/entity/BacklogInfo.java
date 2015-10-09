package com.xlj.erp.movefield.entity;

/**
 * Created by zpy@98ki.com on 7/22/15.
 *
 */
public class BacklogInfo {
	/**
	 * 客户id
	 */
	private int customerid;
	/**
	 * 客户name
	 */
	private String customername;
	/**
	 * 客户sex
	 */
	private String customersex;
	/**
	 * 意向程度 interestdegree
	 */
	private String interestdegree;
	/**
	 * 跟进次数
	 */
	private int followtimes;
	/**
	 * 上次跟进
	 */
	private String lastfollowtime;
	/**
	 * overduefollowday
	 */
	private int overduefollowday;
	/**
	 * 催办认购预约日期
	 */
	private String subscribedate;
	/**
	 * 催办签约认购日期
	 */
	private String signdate;
	/**
	 * 催办签约逾期天数
	 */
	private int overduesignday;

	private String phone;

	/**
	 * 记录在在一个列表中的哪个分类中，从1开始计数
	 */
	private int headerId;

	private String header;

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomersex() {
		return customersex;
	}

	public void setCustomersex(String customersex) {
		this.customersex = customersex;
	}

	public String getInterestdegree() {
		return interestdegree;
	}

	public void setInterestdegree(String interestdegree) {
		this.interestdegree = interestdegree;
	}

	public int getFollowtimes() {
		return followtimes;
	}

	public void setFollowtimes(int followtimes) {
		this.followtimes = followtimes;
	}

	public String getLastfollowtime() {
		return lastfollowtime;
	}

	public void setLastfollowtime(String lastfollowtime) {
		this.lastfollowtime = lastfollowtime;
	}

	public int getOverduefollowday() {
		return overduefollowday;
	}

	public void setOverduefollowday(int overduefollowday) {
		this.overduefollowday = overduefollowday;
	}

	public String getSubscribedate() {
		return subscribedate;
	}

	public void setSubscribedate(String subscribedate) {
		this.subscribedate = subscribedate;
	}

	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	public int getOverduesignday() {
		return overduesignday;
	}

	public void setOverduesignday(int overduesignday) {
		this.overduesignday = overduesignday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getHeaderId() {
		return headerId;
	}

	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
