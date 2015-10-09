package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class SaleRecord implements Serializable {
	private String recordcontent;
	private String recorddate;
	private String status;

	public String getRecordcontent() {
		return recordcontent;
	}

	public void setRecordcontent(String recordcontent) {
		this.recordcontent = recordcontent;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
