package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

public class TmpBuildingSearchConditionKey implements Serializable {
	private int key;
	private List<String> values;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
