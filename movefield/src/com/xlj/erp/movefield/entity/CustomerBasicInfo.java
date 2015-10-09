package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 客户详情实体
 * 
 * @author chaohui.yang
 *
 */
public class CustomerBasicInfo implements Serializable {
	private String customername;
	private String customersex;
	private String vagerange;
	private String phone;
	private String interestdegree;
	private String followtimes;
	private String visittimes;
	private String lastcontactdate;
	private String status;
	private String customerSource;
	private String media;
	private String interesthousetype;
	private String residentialdistrict;
	private String houseuse;

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

	public String getVagerange() {
		return vagerange;
	}

	public void setVagerange(String vagerange) {
		this.vagerange = vagerange;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInterestdegree() {
		return interestdegree;
	}

	public void setInterestdegree(String interestdegree) {
		this.interestdegree = interestdegree;
	}

	public String getFollowtimes() {
		return followtimes;
	}

	public void setFollowtimes(String followtimes) {
		this.followtimes = followtimes;
	}

	public String getVisittimes() {
		return visittimes;
	}

	public void setVisittimes(String visittimes) {
		this.visittimes = visittimes;
	}

	public String getLastcontactdate() {
		return lastcontactdate;
	}

	public void setLastcontactdate(String lastcontactdate) {
		this.lastcontactdate = lastcontactdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getInteresthousetype() {
		return interesthousetype;
	}

	public void setInteresthousetype(String interesthousetype) {
		this.interesthousetype = interesthousetype;
	}

	public String getResidentialdistrict() {
		return residentialdistrict;
	}

	public void setResidentialdistrict(String residentialdistrict) {
		this.residentialdistrict = residentialdistrict;
	}

	public String getHouseuse() {
		return houseuse;
	}

	public void setHouseuse(String houseuse) {
		this.houseuse = houseuse;
	}

}
