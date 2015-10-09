package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class CarouselPic implements Serializable {
	/**
	 * url
	 */
	private String picUrl;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序
	 */
	private String sortNum;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

}
