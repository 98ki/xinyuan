package com.xlj.erp.movefield.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.xlj.erp.movefield.base.volley.VolleyRequest;

/**
 * Dialog基类
 * 
 * @author chaohui.yang
 *
 */
public abstract class BaseDialog extends Dialog {
	protected View rootView;

	protected BaseDialog(Context context) {
		super(context);

		init(context);
	}

	protected void init(Context context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		window.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		LayoutInflater inflater = LayoutInflater.from(context);
		rootView = inflater.inflate(getLayoutResId(), null);
		setContentView(rootView);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}

	abstract public int getLayoutResId();
}
