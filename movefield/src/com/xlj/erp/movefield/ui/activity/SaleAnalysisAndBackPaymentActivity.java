package com.xlj.erp.movefield.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.ui.base.BaseFragment;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.ui.fragment.FieldMonitorBackPaymentAnalysisFragment;
import com.xlj.erp.movefield.ui.fragment.FieldMonitorSaleAnalysisFragment;
import com.xlj.erp.movefield.widget.SegmentControl;
import com.xlj.erp.movefield.widget.SegmentControl.OnSegmentControlClickListener;
/**
 * 销售及回款分析
 */
public class SaleAnalysisAndBackPaymentActivity extends BaseToolbarActivity implements OnSegmentControlClickListener{

	private SegmentControl segment_control;
	
	private FrameLayout analysis_content_frame;
	
	private BaseFragment fieldMonitorSaleAnalysisFragment;
	
	private BaseFragment fieldMonitorBackPaymentAnalysisFragment;
	
	private final String TAG_MONITOR_SALEANALYSIS = "MonitorSaleAnalysisFragment";
	
	private final String TAG_MONITOR_BACKPAYMENTANALYSIS = "MonitorBackPaymentAnalysisFragment";
	
	private int mCurrentTabIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTitle("销售分析");
		segment_control.setCurrentIndex(mCurrentTabIndex);
		setTabCheck(mCurrentTabIndex);
	}

	@Override
	public int getLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.activity_saleanalysis_backpayment;
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub
		segment_control = (SegmentControl) this.findViewById(R.id.segment_control);
		analysis_content_frame = (FrameLayout) this.findViewById(R.id.analysis_content_frame);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		segment_control.setOnSegmentControlClickListener(this);
		
	}

	@Override
	public void onSegmentControlClick(int index) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		switch (index) {
		case 0:
			if (mCurrentTabIndex != 0) {
				setTabCheck(0);
				setTitle("销售分析");
			}
			break;
		case 1:
			if (mCurrentTabIndex != 1) {
				setTabCheck(1);
				setTitle("回款分析");
			}
			break;
		default:
			break;
		}
	}
	private void setTabCheck(int index) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		hideFragment(fragmentTransaction);
		switch (index) {
		case 0:
			fieldMonitorSaleAnalysisFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_MONITOR_SALEANALYSIS);
			if (fieldMonitorSaleAnalysisFragment == null) {
				fieldMonitorSaleAnalysisFragment = FieldMonitorSaleAnalysisFragment.newInstance();
				fragmentTransaction.add(R.id.analysis_content_frame, fieldMonitorSaleAnalysisFragment, TAG_MONITOR_SALEANALYSIS);
			} else {
				fieldMonitorSaleAnalysisFragment.setupToolbar();
				fragmentTransaction.show(fieldMonitorSaleAnalysisFragment);
			}
			mCurrentTabIndex = 0;
			break;
		case 1:
			fieldMonitorBackPaymentAnalysisFragment = (BaseFragment) fragmentManager.findFragmentByTag(TAG_MONITOR_BACKPAYMENTANALYSIS);
			if (fieldMonitorBackPaymentAnalysisFragment == null) {
				fieldMonitorBackPaymentAnalysisFragment = FieldMonitorBackPaymentAnalysisFragment.newInstance();
				fragmentTransaction.add(R.id.analysis_content_frame, fieldMonitorBackPaymentAnalysisFragment, TAG_MONITOR_BACKPAYMENTANALYSIS);
			} else {
				fieldMonitorBackPaymentAnalysisFragment.setupToolbar();
				fragmentTransaction.show(fieldMonitorBackPaymentAnalysisFragment);
			}
			mCurrentTabIndex = 1;
			break;

		}
		fragmentTransaction.commit();
	}
	
	/**
	 * 隐藏所有Fragment
	 *
	 * @param fragmentTransaction
	 */
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (fieldMonitorSaleAnalysisFragment != null) {
			fragmentTransaction.hide(fieldMonitorSaleAnalysisFragment);
		}
		if (fieldMonitorBackPaymentAnalysisFragment != null) {
			fragmentTransaction.hide(fieldMonitorBackPaymentAnalysisFragment);
		}

	}

}
