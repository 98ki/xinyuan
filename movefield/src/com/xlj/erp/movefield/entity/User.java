package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * 
 * @author chaohui.yang
 *
 */
public class User implements Serializable {
	private String token;
	/**
	 * userid
	 */
	private String userid;
	/**
	 * 用户姓名
	 */
	private String realname;
	/**
	 * 用户ID
	 */
	private String username;
	/**
	 * 所在项目信息
	 */
	private List<UserProject> tempProperties;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserProject> getTempProperties() {
		return tempProperties;
	}

	public void setTempProperties(List<UserProject> tempProperties) {
		this.tempProperties = tempProperties;
	}

}
