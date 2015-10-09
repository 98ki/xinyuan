package com.xlj.erp.movefield.entity;

import java.io.Serializable;

public class UserProject implements Serializable {
	/**
	 * 项目权限 0:销售团队内-置业顾问，1:销售团队外-案场经理。注意：每个项目的权限都不相同
	 */
	private int authority;
	/**
	 * 项目ID
	 */
	private int projectId;
	/**
	 * 项目名称
	 */
	private String name;
	/**
	 * 是否选中
	 */
	private boolean checked;

	public UserProject() {
	}

	public UserProject(int authority, int projectId, String name, boolean checked) {
		this.authority = authority;
		this.projectId = projectId;
		this.name = name;
		this.checked = checked;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
