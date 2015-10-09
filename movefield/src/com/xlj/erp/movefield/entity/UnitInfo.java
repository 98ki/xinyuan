package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 每个单元对应的房屋信息
 * 
 * @author chaohui.yang
 *
 */
public class UnitInfo implements Serializable {
	private String nunitnum;
	private List<HouseInfo> houseLists;

	public String getNunitnum() {
		return nunitnum;
	}

	public void setNunitnum(String nunitnum) {
		this.nunitnum = nunitnum;
	}

	public List<HouseInfo> getHouseLists() {
		return houseLists;
	}

	public void setHouseLists(List<HouseInfo> houseLists) {
		this.houseLists = houseLists;
	}

}
