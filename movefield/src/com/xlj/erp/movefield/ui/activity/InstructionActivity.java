package com.xlj.erp.movefield.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

public class InstructionActivity extends BaseToolbarActivity {
	private TextView instructions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("配套说明");
		
		String content = getIntent().getStringExtra("instructions");
		instructions.setText(content);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_instruction;
	}

	@Override
	public void findViews() {
		instructions = (TextView) findViewById(R.id.instructions);
	}

	@Override
	public void setListener() {

	}
}
