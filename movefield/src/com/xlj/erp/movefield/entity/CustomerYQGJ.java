package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class CustomerYQGJ implements Serializable {
	/**
	 * 客户id
	 */
	private String id;
	/**
	 * 姓名
	 */
	private String vcusname;
	/**
	 * 联系电话
	 */
	private String vtel;
	/**
	 * 性别
	 */
	private String vsex;
	/**
	 * 客户状态
	 */
	private String vcusstate;
	/**
	 * 客户意向程度
	 */
	private String vcintention;
	/**
	 * 销售代表
	 */
	private String salesheader;
	/**
	 * 逾期跟进天数
	 */
	private String withoutday;
	/**
	 * 是否选中
	 */
	private boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVcusname() {
		return vcusname;
	}

	public void setVcusname(String vcusname) {
		this.vcusname = vcusname;
	}

	public String getVtel() {
		return vtel;
	}

	public void setVtel(String vtel) {
		this.vtel = vtel;
	}

	public String getVsex() {
		return vsex;
	}

	public void setVsex(String vsex) {
		this.vsex = vsex;
	}

	public String getVcusstate() {
		return vcusstate;
	}

	public void setVcusstate(String vcusstate) {
		this.vcusstate = vcusstate;
	}

	public String getVcintention() {
		return vcintention;
	}

	public void setVcintention(String vcintention) {
		this.vcintention = vcintention;
	}

	public String getSalesheader() {
		return salesheader;
	}

	public void setSalesheader(String salesheader) {
		this.salesheader = salesheader;
	}

	public String getWithoutday() {
		return withoutday;
	}

	public void setWithoutday(String withoutday) {
		this.withoutday = withoutday;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
