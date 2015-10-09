package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class CustomerXSXS implements Serializable {
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
	 * 客户状态
	 */
	private String vcusstate;
	/**
	 * 时间
	 */
	private String gjTime;

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

	public String getVcusstate() {
		return vcusstate;
	}

	public void setVcusstate(String vcusstate) {
		this.vcusstate = vcusstate;
	}

	public String getGjTime() {
		return gjTime;
	}

	public void setGjTime(String gjTime) {
		this.gjTime = gjTime;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
