package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 每个项目对应的楼栋（每个楼栋对应的单元，每个单元对应的房间）
 * 
 * @author chaohui.yang
 *
 */
public class BuildingSearchInfo implements Serializable {
	private List<UnitInfo> roomList;
	private String forsaleinfo;
	private String soldinfo;

	public List<UnitInfo> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<UnitInfo> roomList) {
		this.roomList = roomList;
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

}
