package com.xlj.erp.movefield.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.xlj.erp.movefield.R;

/**
 * Loading框
 * 
 * @author chaohui.yang
 *
 */
public class InfoLoadingDialog extends BaseDialog {

	public InfoLoadingDialog(Context context, CharSequence info) {
		super(context);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = context.getResources().getDimensionPixelSize(R.dimen.loading_dialog_width);
		lp.height = context.getResources().getDimensionPixelOffset(R.dimen.loading_dialog_height);
		getWindow().setAttributes(lp);
		TextView tvInfo = (TextView) findViewById(R.id.info);
		tvInfo.setText(info);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.dialog_loading;
	}

}
