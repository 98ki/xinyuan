package com.xlj.erp.movefield.entity;

import java.io.Serializable;
/**
 * 案场监控首页数据结构
 */
public class CaseFieldMonitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 必买人数
	 */
	public String boughtcount;
	/**
	 * 完成金额  单位万
	 */
	public String completeamount;
	/**
	 * 跟进
	 */
	public String followcount;
	/**
	 * 高意向
	 */
	public String hightinterestcount;
	/**
	 * 客户意向人数
	 */
	public String interestcount;
	/**
	 * 新增客户
	 */
	public String newcustomercount;
	/**
	 * 无意向
	 */
	public String nointerestcount;
	/**
	 * 一般意向
	 */
	public String normalinterestcount;
	/**
	 * 逾期调整
	 */
	public String overduecount;
	/**
	 * 客户接待排名情况
	 */
	public String receptionranking;
	/**
	 * 新客户登记
	 */
	public String registercustomercount;
	/**
	 * 销售排名
	 */
	public String saleranking;
	/**
	 * 
	 */
	public String salesClues;
	/**
	 * 签约金额 单位万
	 */
	public String signamount;
	/**
	 * 签约面积
	 */
	public String signarea;
	/**
	 * 签约套数
	 */
	public String signcount;
	/**
	 * 认购金额 单位万
	 */
	public String subscribeamount;
	/**
	 * 认购面积
	 */
	public String subscribearea;
	/**
	 * 认购套数
	 */
	public String subscribecount;
	/**
	 * 目标金额  单位万
	 */
	public String targetamount;
	/**
	 * 回访
	 */
	public String visitcount;
	/**
	 * 客户管理
	 */
	public String customerMgr;
}
