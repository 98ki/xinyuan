package com.xlj.erp.movefield.base.volley;

import com.android.volley.VolleyError;

/**
 * Volley网络请求回调
 * 
 * @author chaohui.yang
 * 
 */
public interface IVolleyCallback {
	/**
	 * 添加到队列时回调
	 * 
	 * @param request
	 */
	public void onReady(VolleyRequest request);

	/**
	 * 请求结束返回时回调
	 * 
	 * @param request
	 * @param result
	 */
	public void onPost(VolleyRequest request, String result);

	/**
	 * 取消时回调（暂时不需要）
	 * 
	 * @param request
	 */
	// public void onCancel(VolleyRequest request);

	/**
	 * 请求出错时回调，包括请求超时、服务器错误
	 * 
	 * @param request
	 * @param error
	 */
	public void onError(VolleyRequest request, VolleyError error);
}
