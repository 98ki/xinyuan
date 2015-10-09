package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 房间信息
 * 
 * @author chaohui.yang
 *
 */
public class HouseInfo implements Serializable {
	/**
	 * 房间id
	 */
	private int houseid;
	/**
	 * 房间名称,门牌号
	 */
	private String vhousenumber;
	/**
	 * 房间户型
	 */
	private String roomtypename;
	/**
	 * 房间单价
	 */
	private String houseavgprice;
	/**
	 * 房间面积
	 */
	private String njzarea;
	/**
	 * 房间总价
	 */
	private String housetotalprice;
	/**
	 * 房间已售待售情况（1：已售出；0：未出售）
	 */
	private int vsalestatus;
	/**
	 * 房间所在单元名称
	 */
	private String unitName;
	/**
	 * 待售信息
	 */
	private String forsaleinfo;
	/**
	 * 已售信息
	 */
	private String soldinfo;

	/**
	 * 用来分组
	 */
	private long headerId;
	/**
	 * 上级单元名称
	 */
	private String realUnitName;

	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	public String getVhousenumber() {
		return vhousenumber;
	}

	public void setVhousenumber(String vhousenumber) {
		this.vhousenumber = vhousenumber;
	}

	public String getRoomtypename() {
		return roomtypename;
	}

	public void setRoomtypename(String roomtypename) {
		this.roomtypename = roomtypename;
	}

	public String getHouseavgprice() {
		return houseavgprice;
	}

	public void setHouseavgprice(String houseavgprice) {
		this.houseavgprice = houseavgprice;
	}

	public String getNjzarea() {
		return njzarea;
	}

	public void setNjzarea(String njzarea) {
		this.njzarea = njzarea;
	}

	public String getHousetotalprice() {
		return housetotalprice;
	}

	public void setHousetotalprice(String housetotalprice) {
		this.housetotalprice = housetotalprice;
	}

	public int getVsalestatus() {
		return vsalestatus;
	}

	public void setVsalestatus(int vsalestatus) {
		this.vsalestatus = vsalestatus;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getForsaleinfo() {
		return forsaleinfo;
	}

	public void setForsaleinfo(String forsaleinfo) {
		this.forsaleinfo = forsaleinfo;
	}

	public String getSoldinfo() {
		return soldinfo;
	}

	public void setSoldinfo(String soldinfo) {
		this.soldinfo = soldinfo;
	}

	public long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(long headerId) {
		this.headerId = headerId;
	}

	public String getRealUnitName() {
		return realUnitName;
	}

	public void setRealUnitName(String realUnitName) {
		this.realUnitName = realUnitName;
	}

}
