package com.xlj.erp.movefield.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.xlj.erp.movefield.R;

/**
 * 网络请求Loading框
 * 
 * @author chaohui.yang
 *
 */
public class HttpLoadingDialog extends BaseHttpDialog {

	public HttpLoadingDialog(Context context) {
		super(context);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = context.getResources().getDimensionPixelSize(R.dimen.loading_dialog_width);
		lp.height = context.getResources().getDimensionPixelOffset(R.dimen.loading_dialog_height);
		getWindow().setAttributes(lp);
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				request.cancel();
			}
		});
	}

	@Override
	public int getLayoutResId() {
		return R.layout.dialog_http_loading;
	}

}
