package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 楼栋查询，筛选条件关键字
 * 
 * @author chaohui.yang
 *
 */
public class BuildingSearchConditionKey implements Serializable {
	private String condition;
	private boolean checked;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
