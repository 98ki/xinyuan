package com.xlj.erp.movefield.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xlj.erp.movefield.R;

public class ToastUtils {

	/**
	 * 显示Toast
	 * 
	 * @param text
	 */
	// public static void showToast(Context context, CharSequence text) {
	// Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	// }

	/**
	 * 显示toast
	 * 
	 * @param resId
	 */
	// public static void showToast(Context context, int resId) {
	// Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	// }

	/**
	 * 显示Toast
	 * 
	 * @param text
	 */
	public static void showToast(Context context, CharSequence text) {
		View viewToast = View.inflate(context, R.layout.layout_toast, null);
		TextView tvToastMsg = (TextView) viewToast.findViewById(R.id.tv_toast_msg);
		tvToastMsg.setText(text);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 200);
		toast.setView(viewToast);
		toast.show();
	}

	/**
	 * 显示toast
	 * 
	 * @param resId
	 */
	public static void showToast(Context context, int resId) {
		View viewToast = View.inflate(context, R.layout.layout_toast, null);
		TextView tvToastMsg = (TextView) viewToast.findViewById(R.id.tv_toast_msg);
		tvToastMsg.setText(resId);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 200);
		toast.setView(viewToast);
		toast.show();
	}
}
