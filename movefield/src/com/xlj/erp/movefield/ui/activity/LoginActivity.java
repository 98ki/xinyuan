package com.xlj.erp.movefield.ui.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.dialog.LoadingApkDialog;
import com.xlj.erp.movefield.entity.User;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.PrefsUtils;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.utils.Utils;

/**
 * 登录界面
 * 
 * @author chaohui.yang
 *
 */
public class LoginActivity extends BaseToolbarActivity {
	private static String UPDATE_PACKAGE_NAME = "lastest.apk";
	private static int REQ_CODE_SETTINGS = 0;
	private static String REQUEST_GET_LOGO = "get_logo";
	private static String REQUEST_LOGIN = "login";
	private static String REQUEST_UPDATEAPPVERSION = "updateAppVersion";

	private DisplayImageOptions options;

	private ImageView mLoginLogo;
	private EditText mLoginUsername;
	private EditText mLoginPassword;
	private CheckBox mLoginRemember;
	private CheckBox mLoginAutoLogin;

	private String mUsername;
	private String mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.logo_default).showImageForEmptyUri(R.drawable.logo_default).showImageOnFail(R.drawable.logo_default)
				.cacheInMemory(true).cacheOnDisk(true).build();
		updateAppVersion();
		getLogo();
		restoreLocalUserInfo();
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_login;
	}

	@Override
	public void findViews() {
		mLoginLogo = (ImageView) findViewById(R.id.login_logo);
		mLoginUsername = (EditText) findViewById(R.id.login_username);
		mLoginPassword = (EditText) findViewById(R.id.login_password);
		mLoginRemember = (CheckBox) findViewById(R.id.login_remember);
		mLoginAutoLogin = (CheckBox) findViewById(R.id.login_auto_login);
	}

	@Override
	public void setListener() {
		findViewById(R.id.login_btn).setOnClickListener(this);
		findViewById(R.id.login_setting).setOnClickListener(this);

		mLoginRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					mLoginAutoLogin.setChecked(false);
				}
			}
		});

		mLoginAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mLoginRemember.setChecked(true);
				}
			}
		});
	}

	/**
	 * 登录网络请求
	 * 
	 * @param username
	 * @param password
	 */
	private void login(String username, String password) {
		requestHttp(String.format(Urls.getLoginUrl(), username, password), REQUEST_LOGIN, this, true);
	}

	private void getLogo() {
		requestHttp(Urls.getLogo(), REQUEST_GET_LOGO, this);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_LOGIN.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.login_failure);
					return;
				}
				String data = jsonObject.getString("result");
				User u = JSON.parseObject(data, User.class);
				if (u == null) {
					showToast(R.string.response_invalid);
					return;
				}
				if (u.getTempProperties() == null || u.getTempProperties().size() == 0) {
					showToast(R.string.login_user_no_project_tip);
					return;
				}
				UserManager.setUser(this, u);
				saveLocalUserInfo(mUsername, mPassword);
				Intent intent = new Intent(this, HomeActivity.class);
				startActivity(intent);
				finish();
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		} else if (REQUEST_GET_LOGO.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess == Contants.REQUEST_SUCCESS) {
					String logoUrl = jsonObject.getString("result");
					if (!TextUtils.isEmpty(logoUrl)) {
						ImageLoader.getInstance().displayImage(logoUrl, mLoginLogo, options);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (REQUEST_UPDATEAPPVERSION.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess == Contants.REQUEST_SUCCESS) {
					JSONObject resultJson = jsonObject.getJSONObject("result");
					int isneed = resultJson.getInteger("isneed");
					if (isneed == 1) {
						final String apkUrl = resultJson.getString("url");
						final int isForce = resultJson.getInteger("isforce");
						if (isForce == 1) {
							// 强制更新
							AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setMessage("发现新版本");
							builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									new DownloadApkTask().execute(apkUrl, String.valueOf(isForce));
								}
							});
							builder.setCancelable(false);
							builder.create().show();

						} else {
							// 非强制更新
							AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setMessage("发现新版本");
							builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									new DownloadApkTask().execute(apkUrl, String.valueOf(isForce));
								}
							});
							builder.setNegativeButton("取消", null);
							builder.create().show();
						}
					} else {
						// 没有新版本，处理界面逻辑
						if (PrefsUtils.getBoolean(mContext, PrefsUtils.KEY_AUTO_LOGIN, false)) {
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											verify();
										}
									});

								}
							}, 300);
						}
					}
				} else {
					// 退出对话框
					showExitDialog("获取版本失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 退出对话框
				showExitDialog("获取版本失败");
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		if (REQUEST_LOGIN.equals(request.getRequestTag())) {
			showToast(R.string.network_or_server_invalid);
		} else if (REQUEST_UPDATEAPPVERSION.equals(request.getRequestTag())) {
			showExitDialog("获取版本失败");
		}
	}

	private void restoreLocalUserInfo() {
		boolean remember = PrefsUtils.getBoolean(mContext, PrefsUtils.KEY_LOGIN_REMEMBER, false);
		if (remember) {
			String username = PrefsUtils.getString(mContext, PrefsUtils.KEY_LOGIN_USERNAME, "");
			String password = PrefsUtils.getString(mContext, PrefsUtils.KEY_LOGIN_PASSWORD, "");
			mLoginUsername.setText(username);
			mLoginPassword.setText(password);
			mLoginRemember.setChecked(true);
			mLoginAutoLogin.setChecked(PrefsUtils.getBoolean(mContext, PrefsUtils.KEY_AUTO_LOGIN, false));
		} else {
			mLoginUsername.setText("");
			mLoginPassword.setText("");
			mLoginRemember.setChecked(false);
		}
	}

	private void saveLocalUserInfo(String username, String password) {
		if (mLoginRemember.isChecked()) {
			PrefsUtils.putBoolean(mContext, PrefsUtils.KEY_LOGIN_REMEMBER, true);
			PrefsUtils.putString(mContext, PrefsUtils.KEY_LOGIN_USERNAME, mUsername);
			PrefsUtils.putString(mContext, PrefsUtils.KEY_LOGIN_PASSWORD, mPassword);
			PrefsUtils.putBoolean(mContext, PrefsUtils.KEY_AUTO_LOGIN, mLoginAutoLogin.isChecked());
		} else {
			PrefsUtils.putBoolean(mContext, PrefsUtils.KEY_LOGIN_REMEMBER, false);
			PrefsUtils.putString(mContext, PrefsUtils.KEY_LOGIN_USERNAME, "");
			PrefsUtils.putString(mContext, PrefsUtils.KEY_LOGIN_PASSWORD, "");
		}
	}

	/**
	 * 校验用户名、密码
	 */
	private void verify() {
		mUsername = mLoginUsername.getText().toString().trim();
		mPassword = mLoginPassword.getText().toString().trim();
		if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
			showToast(R.string.login_username_password_null_tip);
			return;
		}
		login(mUsername, mPassword);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.login_btn) {
			verify();
		} else if (v.getId() == R.id.login_setting) {
			Intent intent = new Intent(mContext, SettingsActivity.class);
			startActivityForResult(intent, REQ_CODE_SETTINGS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE_SETTINGS) {
			if (resultCode == Activity.RESULT_OK) {
				getLogo();
			}
		}
	}

	private class DownloadApkTask extends AsyncTask<String, Integer, Boolean> {
		private LoadingApkDialog loadingApkDialog;
		private int isForce;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingApkDialog = new LoadingApkDialog(mContext);
			loadingApkDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String downloaduri = params[0];
			isForce = Integer.parseInt(params[1]);
			boolean result = false;
			HttpUriRequest get = new HttpGet(downloaduri);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse response;
			try {
				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					long total = entity.getContentLength();
					if (is != null) {
						File file = new File(mContext.getCacheDir(), UPDATE_PACKAGE_NAME);
						if (!file.exists()) {
							file.createNewFile();
						}
						Runtime.getRuntime().exec("chmod 777 " + file.getAbsolutePath());
						fileOutputStream = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							publishProgress((int) (count * 100 / total));
						}
						fileOutputStream.flush();
						if (fileOutputStream != null) {
							fileOutputStream.close();
						}
						result = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			loadingApkDialog.dismiss();
			if (result) {
				finish();
				installNewPackage();
			} else {
				if (isForce == 1) {
					showExitDialog("更新版本失败");
				} else {
					showToast("更新版本失败");
				}
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			loadingApkDialog.setProgress(values[0]);
		}
	}

	/**
	 * 安装新包
	 */
	private void installNewPackage() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(mContext.getCacheDir(), UPDATE_PACKAGE_NAME)), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void updateAppVersion() {
		int versionCode = Utils.getVersionCode(this);
		requestHttp(String.format(Urls.updateAppVersion(), String.valueOf(versionCode)), REQUEST_UPDATEAPPVERSION, this, true);
	}

	private void showExitDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.create().show();
	}

}
