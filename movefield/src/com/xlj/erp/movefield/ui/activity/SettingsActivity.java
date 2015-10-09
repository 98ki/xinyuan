package com.xlj.erp.movefield.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.dialog.AboutDialog;
import com.xlj.erp.movefield.dialog.InfoLoadingDialog;
import com.xlj.erp.movefield.entity.CommonDataManager;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.PrefsUtils;
import com.xlj.erp.movefield.utils.UserManager;

/**
 * 设置界面
 * 
 * @author chaohui.yang
 *
 */
public class SettingsActivity extends BaseToolbarActivity {
	private EditText mServerHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_settings);
		String serverHost = Urls.getServerHost(mContext);
		mServerHost.setText(serverHost);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_settings;
	}

	@Override
	public void findViews() {
		mServerHost = (EditText) findViewById(R.id.settings_server_host);
	}

	@Override
	public void setListener() {
		findViewById(R.id.settings_clean_cache).setOnClickListener(this);
		findViewById(R.id.settings_about).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_ok) {
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.settings_clean_cache) {
			new ClearAsyncTask().execute();
		} else if (v.getId() == R.id.settings_about) {
			// Intent intent = new Intent(this, AboutActivity.class);
			// startActivity(intent);
			AboutDialog aboutDialog = new AboutDialog(this);
			aboutDialog.show();
		}
	}

	private void save() {
		String serverHost = mServerHost.getText().toString().trim();
		if (TextUtils.isEmpty(serverHost)) {
			showToast(R.string.settings_server_null_tip);
			return;
		}
		Urls.setServerHost(mContext, serverHost);
		showToast(R.string.settings_complete);
		setResult(Activity.RESULT_OK);
		finish();
	}

	private void clear() {
		UserManager.clear();
		CommonDataManager.clear();
		PrefsUtils.clear(this);
		ImageLoader.getInstance().clearDiskCache();
	}

	private class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
		private InfoLoadingDialog loadingDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingDialog = new InfoLoadingDialog(mContext, "正在清理缓存...");
			loadingDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			clear();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loadingDialog.dismiss();
			showToast("清理缓存完成");
		}

	}

}
