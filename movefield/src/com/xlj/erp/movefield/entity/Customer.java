package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 客户实体
 * 
 * @author chaohui.yang
 *
 */
public class Customer implements Serializable {
	private String customerid;
	private String customername;
	private String customersex;
	private String interestdegree;
	private String phone;
	private String status;
	private String vagerange;
	private String deleted;

	public Customer() {
	}

	public Customer(String customerid, String customername, String customersex, String interestdegree, String phone, String status, String vagerange) {
		this.customerid = customerid;
		this.customername = customername;
		this.customersex = customersex;
		this.interestdegree = interestdegree;
		this.phone = phone;
		this.status = status;
		this.vagerange = vagerange;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVagerange() {
		return vagerange;
	}

	public void setVagerange(String vagerange) {
		this.vagerange = vagerange;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}
