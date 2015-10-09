package com.xlj.erp.movefield.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences封装，key全部在这里定义，防止冲突
 * 
 * @author chaohui.yang
 *
 */
public class PrefsUtils {
	/**
	 * SharedPreferences文件名
	 */
	private static String PREFS_FILE = "settings";

	// ---------以下定义key----------------
	/**
	 * 记住用户名密码
	 */
	public static String KEY_LOGIN_REMEMBER = "login_remember";
	/**
	 * 登录用户名
	 */
	public static String KEY_LOGIN_USERNAME = "login_username";
	/**
	 * 登录密码
	 */
	public static String KEY_LOGIN_PASSWORD = "login_password";
	/**
	 * 服务器
	 */
	public static String KEY_SERVER_HOST = "server_host";
	/**
	 * 自动登录
	 */
	public static String KEY_AUTO_LOGIN = "auto_login";

	/**
	 * 上次选择的project id
	 */
	public static String KEY_LAST_CHOOSE_PROJECT_ID = "last_choose_project_id";

	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}

	public static boolean getBoolean(Context context, String key, boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	public static void putInt(Context context, String key, int value) {
		Editor editor = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value).commit();
	}

	public static void putBoolean(Context context, String key, boolean value) {
		Editor editor = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value).commit();
	}

	public static void putString(Context context, String key, String value) {
		Editor editor = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).edit();
		editor.putString(key, value).commit();
	}

	/**
	 * 清理缓存
	 */
	public static void clear(Context context) {
		Editor editor = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}
}
