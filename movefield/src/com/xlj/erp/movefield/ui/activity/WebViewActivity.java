package com.xlj.erp.movefield.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

/**
 * 使用场景简单，仅用于本项目
 * 
 * @author chaohui.yang
 * 
 */
public class WebViewActivity extends BaseToolbarActivity {
	private static final String TAG = "WebViewActivity";
	private WebView mWebView;
	// private ProgressBar mProgressBar;
	private LinearLayout mProgressbarLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String title = getIntent().getStringExtra(Contants.WEB_VIEW_ACTIVITY_TITLE);
		String url = getIntent().getStringExtra(Contants.WEB_VIEW_ACTIVITY_URL);
		setTitle(title);
		initWebView();
		loadUrl(url);
	}

	private void initWebView() {
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.requestFocus();

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSaveFormData(true);
		webSettings.setSupportZoom(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
	}

	private void loadUrl(String url) {
		mWebView.setWebChromeClient(new MWebChromeClient());
		mWebView.setWebViewClient(new MWebViewClient());
		mWebView.loadUrl(url);
	}

	private class MWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				hideProgress();
			}
			// else {
			// showProgress();
			// mProgressBar.setProgress(newProgress);
			// }
			super.onProgressChanged(view, newProgress);
		}
	}

	private class MWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.v(TAG, "shouldOverrideUrlLoading");
			showProgress();
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				view.loadUrl("http://" + url);
			} else {
				view.loadUrl(url);
			}
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Log.v(TAG, "onReceivedError");
			hideProgress();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			showProgress();
			Log.v(TAG, "onPageStarted");
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.v(TAG, "onPageFinished");
			hideProgress();
			super.onPageFinished(view, url);
		}
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWebView.destroy();
	}

	private void showProgress() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// mProgressBar.setVisibility(View.VISIBLE);
				mProgressbarLayout.setVisibility(View.VISIBLE);
			}
		});

	}

	private void hideProgress() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// mProgressBar.setVisibility(View.INVISIBLE);
				mProgressbarLayout.setVisibility(View.INVISIBLE);
			}
		});

	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_webview;
	}

	@Override
	public void findViews() {
		mWebView = (WebView) findViewById(R.id.webview);
		// mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		mProgressbarLayout = (LinearLayout) findViewById(R.id.progressbar_layout);
	}

	@Override
	public void setListener() {

	}
}
