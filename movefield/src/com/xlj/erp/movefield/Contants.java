package com.xlj.erp.movefield;

/**
 * 常量
 * 
 * @author chaohui.yang
 *
 */
public class Contants {
	/**
	 * 网络请求成功isSuccess返回值
	 */
	public static final int REQUEST_SUCCESS = 1;
	/**
	 * 项目权限 0:销售团队内-置业顾问
	 */
	public static final int AUTHORITY_MANAGER = 0;
	/**
	 * 项目权限 1:销售团队外-案场经理
	 */
	public static final int AUTHORITY_SALESMAN = 1;

	/******************************************************
	 * 以下为Activity传参常量key
	 *******************************************************/
	public static final String CHECKED_PROJECT = "checked_project";
	
	public static final String PIC_URL = "pic_url";
	
	public static final String DESIGN_SKETCH_URL = "design_sketch_url";
	
	public static final String WEB_VIEW_ACTIVITY_TITLE = "title";
	
	public static final String WEB_VIEW_ACTIVITY_URL= "url";
	
	
	/******************************************************
	 * 以下为广播
	 *******************************************************/
	public static final String ACTION_SWITCH_PROJECT = "com.xlj.erp.movefield.SWITCH_PROJECT";
}
