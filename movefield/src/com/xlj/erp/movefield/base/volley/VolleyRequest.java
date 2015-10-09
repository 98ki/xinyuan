package com.xlj.erp.movefield.base.volley;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * 给StringRequest增加requestTag和showDialog字段
 * 
 * @author chaohui.yang
 *
 */
public class VolleyRequest extends StringRequest {
	/**
	 * 请求标志，用于区分是哪个网络请求
	 */
	private String requestTag;
	/**
	 * 显示请求对话框
	 */
	private boolean showDialog;
	/**
	 * 请求回调
	 */
	private IVolleyCallback volleyCallback;

	private Map<String, String> postParams;

	public VolleyRequest(String url, Listener<String> listener, ErrorListener errorListener, IVolleyCallback volleyCallback, boolean showDialog) {
		super(Method.GET, url, listener, errorListener);
		this.showDialog = showDialog;
		this.volleyCallback = volleyCallback;
	}

	public VolleyRequest(int method, String url, Map<String, String> postParams, Listener<String> listener, ErrorListener errorListener, IVolleyCallback volleyCallback, boolean showDialog) {
		super(method, url, listener, errorListener);
		this.postParams = postParams;
		this.showDialog = showDialog;
		this.volleyCallback = volleyCallback;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public String getRequestTag() {
		return requestTag;
	}

	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return this.postParams;
	}

	@Override
	protected void deliverResponse(String response) {
		super.deliverResponse(response);
		if (volleyCallback != null) {
			volleyCallback.onPost(this, response);
		}
	}

	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
		if (volleyCallback != null) {
			volleyCallback.onError(this, error);
		}
	}

}
