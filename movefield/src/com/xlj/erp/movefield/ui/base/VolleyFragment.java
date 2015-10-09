package com.xlj.erp.movefield.ui.base;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.MApplication;
import com.xlj.erp.movefield.base.volley.IVolleyCallback;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.dialog.HttpLoadingDialog;

public class VolleyFragment extends Fragment implements IVolleyCallback {
	public Activity mParentActivity;
	private HttpLoadingDialog mHttpLoadingDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentActivity = getActivity();
		initDialog();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
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
	public void requestHttp(String url, String requestTag, IVolleyCallback volleyCallback, boolean showDialog) {
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
		mHttpLoadingDialog = new HttpLoadingDialog(mParentActivity);
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
		// if (request == null) {
		// return;
		// }
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		dismissLoadingDialog();
	}

}
