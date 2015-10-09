package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * 楼栋信息
 * 
 * @author chaohui.yang
 *
 */
public class BuildingInfo implements Serializable {
	private String buildid;
	private String vbuildname;
	private int shellCount;

	public String getBuildid() {
		return buildid;
	}

	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}

	public String getVbuildname() {
		return vbuildname;
	}

	public void setVbuildname(String vbuildname) {
		this.vbuildname = vbuildname;
	}

	public int getShellCount() {
		return shellCount;
	}

	public void setShellCount(int shellCount) {
		this.shellCount = shellCount;
	}

}
