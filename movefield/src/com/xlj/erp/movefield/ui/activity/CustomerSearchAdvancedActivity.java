package com.xlj.erp.movefield.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.adapter.CustomerSearchAdvancedConditionAdapter;
import com.xlj.erp.movefield.entity.CommonDataManager;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;

/**
 * 客户，高级搜索
 * 
 * @author chaohui.yang
 *
 */
public class CustomerSearchAdvancedActivity extends BaseToolbarActivity {
	private EditText mSearchKey;
	private GridView mGridStatus;
	private GridView mGridInterestdegree;
	private GridView mGridVagerange;
	private Button mReset;
	private Button mSearch;

	private CustomerSearchAdvancedConditionAdapter mStatusAdapter;
	private CustomerSearchAdvancedConditionAdapter mInterestdegreeAdapter;
	private CustomerSearchAdvancedConditionAdapter mVagerangeAdapter;

	private String cusAge;
	private String cusInterest;
	private String cusStatus;
	private String cusCondition;
	private List<String> mStatusList;
	private List<String> mInterestList;
	private List<String> mAgeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("高级搜索");
		Intent intent = getIntent();
		cusAge = intent.getStringExtra("cusAge");
		cusInterest = intent.getStringExtra("cusInterest");
		cusStatus = intent.getStringExtra("cusStatus");
		cusCondition = intent.getStringExtra("cusCondition");
		
		mSearchKey.setText(cusCondition);

		mStatusList = CommonDataManager.getCustStatus();
		List<Boolean> statusCheckedList = new ArrayList<Boolean>();
		if (!TextUtils.isEmpty(cusStatus)) {
			for (String s : mStatusList) {
				if (cusStatus.equals(s)) {
					statusCheckedList.add(true);
				} else {
					statusCheckedList.add(false);
				}
			}
		} else {
			for (String s : mStatusList) {
				statusCheckedList.add(false);
			}
		}

		mInterestList = CommonDataManager.getInterestDgree();
		List<Boolean> interestCheckedList = new ArrayList<Boolean>();
		if (!TextUtils.isEmpty(cusInterest)) {
			for (String s : mInterestList) {
				if (cusInterest.equals(s)) {
					interestCheckedList.add(true);
				} else {
					interestCheckedList.add(false);
				}
			}
		} else {
			for (String s : mInterestList) {
				interestCheckedList.add(false);
			}
		}

		mAgeList = CommonDataManager.getVagerange();
		List<Boolean> ageCheckedList = new ArrayList<Boolean>();
		if (!TextUtils.isEmpty(cusAge)) {
			for (String s : mAgeList) {
				if (cusAge.equals(s)) {
					ageCheckedList.add(true);
				} else {
					ageCheckedList.add(false);
				}
			}
		} else {
			for (String s : mAgeList) {
				ageCheckedList.add(false);
			}
		}

		mStatusAdapter = new CustomerSearchAdvancedConditionAdapter(this, mStatusList, statusCheckedList);
		mInterestdegreeAdapter = new CustomerSearchAdvancedConditionAdapter(this, mInterestList, interestCheckedList);
		mVagerangeAdapter = new CustomerSearchAdvancedConditionAdapter(this, mAgeList, ageCheckedList);

		mGridStatus.setAdapter(mStatusAdapter);
		mGridInterestdegree.setAdapter(mInterestdegreeAdapter);
		mGridVagerange.setAdapter(mVagerangeAdapter);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_customer_search_advanced;
	}

	@Override
	public void findViews() {
		mSearchKey = (EditText) findViewById(R.id.search_key);
		mGridStatus = (GridView) findViewById(R.id.grid_status);
		mGridInterestdegree = (GridView) findViewById(R.id.grid_interestdegree);
		mGridVagerange = (GridView) findViewById(R.id.grid_vagerange);
		mReset = (Button) findViewById(R.id.reset);
		mSearch = (Button) findViewById(R.id.search);
	}

	@Override
	public void setListener() {
		mReset.setOnClickListener(this);
		mSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.reset) {
			mSearchKey.setText("");
			List<Boolean> statusCheckedList = new ArrayList<Boolean>();
			for (String s : mStatusList) {
				statusCheckedList.add(false);
			}

			List<Boolean> interestCheckedList = new ArrayList<Boolean>();
			for (String s : mInterestList) {
				interestCheckedList.add(false);
			}

			List<Boolean> ageCheckedList = new ArrayList<Boolean>();
			for (String s : mAgeList) {
				ageCheckedList.add(false);
			}
			mStatusAdapter = new CustomerSearchAdvancedConditionAdapter(this, mStatusList, statusCheckedList);
			mInterestdegreeAdapter = new CustomerSearchAdvancedConditionAdapter(this, mInterestList, interestCheckedList);
			mVagerangeAdapter = new CustomerSearchAdvancedConditionAdapter(this, mAgeList, ageCheckedList);

			mGridStatus.setAdapter(mStatusAdapter);
			mGridInterestdegree.setAdapter(mInterestdegreeAdapter);
			mGridVagerange.setAdapter(mVagerangeAdapter);
		} else if (v.getId() == R.id.search) {
			String searchKey = mSearchKey.getText().toString().trim();
			
			Intent data = new Intent();
			data.putExtra("cusAge", mVagerangeAdapter.getChecked());
			data.putExtra("cusInterest", mInterestdegreeAdapter.getChecked());
			data.putExtra("cusStatus", mStatusAdapter.getChecked());
			data.putExtra("cusCondition", searchKey);
			setResult(Activity.RESULT_OK, data);
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_customer_search_advanced, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_cancel) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
