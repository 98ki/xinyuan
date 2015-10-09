package com.xlj.erp.movefield.ui.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.utils.SystemBarTintManager;

/**
 * Android4.4+支持状态栏透明
 * 
 * @author chaohui.yang
 *
 */
@SuppressLint("NewApi")
public class TranslucentStatusBarActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(getStatusBarTintResource() == 0 ? R.color.md_color_primary : getStatusBarTintResource());

			boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
			boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
			if (!(isMeiZu() || isHuaWei())) {
				if (!hasMenuKey && !hasBackKey) {
					tintManager.setNavigationBarTintEnabled(true);
					tintManager.setNavigationBarTintResource(R.color.md_color_primary);
				} else {
					tintManager.setNavigationBarTintEnabled(false);
				}
			}
		}
	}

	public int getStatusBarTintResource() {
		return 0;
	}

	public void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	public static boolean isMeiZu() {
		return "Meizu".equalsIgnoreCase(Build.MANUFACTURER);
	}

	public static boolean isHuaWei() {
		return "HUAWEI".equalsIgnoreCase(Build.MANUFACTURER);
	}
}
