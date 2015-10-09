package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 主推户型列表
 * 
 * @author chaohui.yang
 *
 */
public class PromoteRoomList implements Serializable {
	private String houseType;
	private List<PromoteRoom> toPromoteHouseList;

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public List<PromoteRoom> getToPromoteHouseList() {
		return toPromoteHouseList;
	}

	public void setToPromoteHouseList(List<PromoteRoom> toPromoteHouseList) {
		this.toPromoteHouseList = toPromoteHouseList;
	}

}
