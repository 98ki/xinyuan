package com.xlj.erp.movefield.ui.activity;

import android.os.Bundle;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

public class AboutActivity extends BaseToolbarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("关于");
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_about;
	}

	@Override
	public void findViews() {

	}

	@Override
	public void setListener() {

	}

}
