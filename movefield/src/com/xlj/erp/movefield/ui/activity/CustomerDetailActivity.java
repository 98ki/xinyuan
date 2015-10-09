package com.xlj.erp.movefield.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.ui.fragment.CustomerBasicInfoFragment;
import com.xlj.erp.movefield.ui.fragment.FollowRecordFragment;
import com.xlj.erp.movefield.ui.fragment.SaleRecordFragment;
import com.xlj.erp.movefield.widget.SegmentControl;

/**
 * 客户详情界面
 * 
 * @author chaohui.yang
 *
 */
public class CustomerDetailActivity extends BaseToolbarActivity implements SegmentControl.OnSegmentControlClickListener {
	private final static String TAG_CUSTOMERBASICINFOFRAGMENT = "CustomerBasicInfoFragment";
	private final static String TAG_FOLLOWRECORDFRAGMENT = "FollowRecordFragment";
	private final static String TAG_SALERECORDFRAGMENT = "SaleRecordFragment";
	private BaseFragment mCustomerBasicInfoFragment;
	private BaseFragment mFollowRecordFragment;
	private BaseFragment mSaleRecordFragment;

	private String mCustomerid = "";
	private String mCustomername = "";
	private SegmentControl mSegmentControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCustomerid = getIntent().getStringExtra("customerid");
		mCustomername = getIntent().getStringExtra("customername");

		setTitle(mCustomername);

		setTabCheck(0);
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_customer_detail;
	}

	@Override
	public void findViews() {
		mSegmentControl = (SegmentControl) findViewById(R.id.segment_control);
	}

	@Override
	public void setListener() {
		mSegmentControl.setOnSegmentControlClickListener(this);
	}

	@Override
	public void onSegmentControlClick(int index) {
		switch (index) {
		case 0:
			setTabCheck(0);
			break;
		case 1:
			setTabCheck(1);
			break;
		case 2:
			setTabCheck(2);
			break;
		}
	}

	private void setTabCheck(int index) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		hideFragment(fragmentTransaction);
		switch (index) {
		case 0:
			mCustomerBasicInfoFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_CUSTOMERBASICINFOFRAGMENT);
			if (mCustomerBasicInfoFragment == null) {
				mCustomerBasicInfoFragment = CustomerBasicInfoFragment.newInstance(mCustomerid);
				fragmentTransaction.add(R.id.customer_detail_content_frame, mCustomerBasicInfoFragment, TAG_CUSTOMERBASICINFOFRAGMENT);
			} else {
				fragmentTransaction.show(mCustomerBasicInfoFragment);
			}
			break;
		case 1:
			mFollowRecordFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_FOLLOWRECORDFRAGMENT);
			if (mFollowRecordFragment == null) {
				mFollowRecordFragment = FollowRecordFragment.newInstance(mCustomerid);
				fragmentTransaction.add(R.id.customer_detail_content_frame, mFollowRecordFragment, TAG_FOLLOWRECORDFRAGMENT);
			} else {
				mFollowRecordFragment.setupToolbar();
				fragmentTransaction.show(mFollowRecordFragment);
			}
			break;
		case 2:
			mSaleRecordFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_SALERECORDFRAGMENT);
			if (mSaleRecordFragment == null) {
				mSaleRecordFragment = SaleRecordFragment.newInstance(mCustomerid);
				fragmentTransaction.add(R.id.customer_detail_content_frame, mSaleRecordFragment, TAG_SALERECORDFRAGMENT);
			} else {
				mSaleRecordFragment.setupToolbar();
				fragmentTransaction.show(mSaleRecordFragment);
			}
			break;
		}
		fragmentTransaction.commitAllowingStateLoss();
	}

	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (mCustomerBasicInfoFragment != null) {
			fragmentTransaction.hide(mCustomerBasicInfoFragment);
		}
		if (mFollowRecordFragment != null) {
			fragmentTransaction.hide(mFollowRecordFragment);
		}
		if (mSaleRecordFragment != null) {
			fragmentTransaction.hide(mSaleRecordFragment);
		}
	}
}
