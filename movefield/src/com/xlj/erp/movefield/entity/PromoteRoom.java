package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 主推户型实体
 * 
 * @author chaohui.yang
 *
 */
public class PromoteRoom implements Serializable {
	private String id;
	private String area;
	private String houseName;
	private String houseNum;
	private String housePicture;
	private String houseType;
	private String miniHousePic;
	private String projectId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getHousePicture() {
		return housePicture;
	}

	public void setHousePicture(String housePicture) {
		this.housePicture = housePicture;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getMiniHousePic() {
		return miniHousePic;
	}

	public void setMiniHousePic(String miniHousePic) {
		this.miniHousePic = miniHousePic;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
