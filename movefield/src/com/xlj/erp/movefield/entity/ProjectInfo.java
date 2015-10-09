package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 楼盘信息实体
 * 
 * @author chaohui.yang
 *
 */
public class ProjectInfo implements Serializable {
	/**
	 * 项目名称
	 */
	private String pName;

	private String location;
	/**
	 * 类型
	 */
	private String pType;
	/**
	 * 入住时间
	 */
	private String checkintime;

	private String zhanArea;

	private String jianArea;

	private String parkingSpace;
	/**
	 * 容积率
	 */
	private String plotRatio;
	/**
	 * 绿化率
	 */
	private String greening;
	/**
	 * 建筑类型
	 */
	private String buildingtype;
	/**
	 * 装修情况
	 */
	private String decoration;
	/**
	 * 产权
	 */
	private String propertyrighttime;

	private String startTime;
	private String shoppingDoor;
	private String averagePrice;
	private String mainDoor;
	private String policy;
	private String license;
	private String address;

	private String property;
	private String cost;
	private String instructions;

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getCheckintime() {
		return checkintime;
	}

	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}

	public String getZhanArea() {
		return zhanArea;
	}

	public void setZhanArea(String zhanArea) {
		this.zhanArea = zhanArea;
	}

	public String getJianArea() {
		return jianArea;
	}

	public void setJianArea(String jianArea) {
		this.jianArea = jianArea;
	}

	public String getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(String parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public String getPlotRatio() {
		return plotRatio;
	}

	public void setPlotRatio(String plotRatio) {
		this.plotRatio = plotRatio;
	}

	public String getGreening() {
		return greening;
	}

	public void setGreening(String greening) {
		this.greening = greening;
	}

	public String getBuildingtype() {
		return buildingtype;
	}

	public void setBuildingtype(String buildingtype) {
		this.buildingtype = buildingtype;
	}

	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public String getPropertyrighttime() {
		return propertyrighttime;
	}

	public void setPropertyrighttime(String propertyrighttime) {
		this.propertyrighttime = propertyrighttime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getShoppingDoor() {
		return shoppingDoor;
	}

	public void setShoppingDoor(String shoppingDoor) {
		this.shoppingDoor = shoppingDoor;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}

	public String getMainDoor() {
		return mainDoor;
	}

	public void setMainDoor(String mainDoor) {
		this.mainDoor = mainDoor;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
