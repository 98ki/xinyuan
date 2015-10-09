package com.xlj.erp.movefield.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.utils.Utils;

/**
 * 关于
 * @author chaohui.yang
 *
 */
public class AboutDialog extends BaseDialog {

	public AboutDialog(Context context) {
		super(context);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
		getWindow().setAttributes(lp);
		
		String versionName = Utils.getVersionName(context);
		TextView tvVersion = (TextView) findViewById(R.id.version);
		tvVersion.setText("版本 "+versionName);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.dialog_about;
	}

}
