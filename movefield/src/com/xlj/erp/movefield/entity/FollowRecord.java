package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class FollowRecord implements Serializable {
	private String followcontent;
	private String followtime;
	private String followtype;

	public String getFollowcontent() {
		return followcontent;
	}

	public void setFollowcontent(String followcontent) {
		this.followcontent = followcontent;
	}

	public String getFollowtime() {
		return followtime;
	}

	public void setFollowtime(String followtime) {
		this.followtime = followtime;
	}

	public String getFollowtype() {
		return followtype;
	}

	public void setFollowtype(String followtype) {
		this.followtype = followtype;
	}

}
