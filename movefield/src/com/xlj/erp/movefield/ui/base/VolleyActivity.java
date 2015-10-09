package com.xlj.erp.movefield.ui.base;

import java.util.Map;

import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.MApplication;
import com.xlj.erp.movefield.base.volley.IVolleyCallback;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.dialog.HttpLoadingDialog;
import com.xlj.erp.movefield.utils.ToastUtils;

/**
 * 网络请求封装
 * 
 * @author chaohui.yang
 *
 */
public class VolleyActivity extends TranslucentStatusBarActivity implements IVolleyCallback {
	private HttpLoadingDialog mHttpLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDialog();
	}

	/**
	 * GET请求，无loading框
	 * 
	 * @param url
	 * @param requestTag
	 * @param volleyCallback
	 */
	public void requestHttp(String url, String requestTag, IVolleyCallback volleyCallback) {
		requestHttp(url, requestTag, volleyCallback, false);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param requestTag
	 * @param showDialog
	 * @param volleyCallback
	 */
	public VolleyRequest requestHttp(String url, String requestTag, IVolleyCallback volleyCallback, boolean showDialog) {
		VolleyRequest request = new VolleyRequest(url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		}, this, showDialog);
		request.setShowDialog(showDialog);
		request.setRequestTag(requestTag);
		onReady(request);
		MApplication.getRequestQueue().add(request);
		return request;
	}

	/**
	 * POST请求
	 * 
	 * @param method
	 * @param url
	 * @param requestTag
	 * @param showDialog
	 * @param postParams
	 * @param volleyCallback
	 */
	public void requestHttp(int method, String url, String requestTag, final Map<String, String> postParams, IVolleyCallback volleyCallback, boolean showDialog) {
		VolleyRequest request = new VolleyRequest(method, url, postParams, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		}, this, showDialog);
		request.setShowDialog(showDialog);
		request.setRequestTag(requestTag);
		onReady(request);
		MApplication.getRequestQueue().add(request);
	}

	private void initDialog() {
		mHttpLoadingDialog = new HttpLoadingDialog(this);
	}

	private void showLoadingDialog() {
		if (mHttpLoadingDialog.isShowing()) {
			return;
		}
		mHttpLoadingDialog.show();
	}

	private void dismissLoadingDialog() {
		if (mHttpLoadingDialog.isShowing()) {
			mHttpLoadingDialog.dismiss();
		}
	}

	@Override
	public void onReady(VolleyRequest request) {
		if (request.isShowDialog()) {
			mHttpLoadingDialog.bindRequest(request);
			showLoadingDialog();
		}
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		if (request.isShowDialog()) {
			dismissLoadingDialog();
		}
		if (result == null) {
			showToast("请求数据有误");
			return;
		}
		// if (request == null) {
		// return;
		// }
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		dismissLoadingDialog();
	}

	/**
	 * 显示Toast
	 * 
	 * @param text
	 */
	public void showToast(CharSequence text) {
		ToastUtils.showToast(this, text);
	}

	/**
	 * 显示toast
	 * 
	 * @param resId
	 */
	public void showToast(int resId) {
		ToastUtils.showToast(this, resId);
	}
}
