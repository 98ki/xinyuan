package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class KeyValue implements Serializable {
	private String key;
	private String value;
	
	private boolean checked;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	

}
