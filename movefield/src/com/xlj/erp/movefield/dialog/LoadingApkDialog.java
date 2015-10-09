package com.xlj.erp.movefield.dialog;

import com.xlj.erp.movefield.R;

import android.content.Context;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class LoadingApkDialog extends BaseDialog {
	private ProgressBar mProgressBar;

	public LoadingApkDialog(Context context) {
		super(context);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
		getWindow().setAttributes(lp);
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		setCancelable(false);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.dialog_loading_apk;
	}
	
	public void setProgress(int progress){
		mProgressBar.setProgress(progress);
	}

}
