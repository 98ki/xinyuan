package com.xlj.erp.movefield.ui.activity;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.CommonDataManager;
import com.xlj.erp.movefield.entity.KeyValue;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.DateTimeUtils;
import com.xlj.erp.movefield.utils.UserManager;

/**
 * 添加客户界面
 * 
 * @author chaohui.yang
 *
 */
public class AddCustomerActivity extends BaseToolbarActivity {
	private static String REQUEST_ADDONECUSTOMER = "addOneCustomer";

	private String customername;
	private String customersex;
	private String phone;
	private String comment;
	private String interestdegreeid;
	private String interesthousetypeid;
	private String agebracket;
	private String mediaid;
	private String customersourceid;
	private String nextfollowdate;

	private List<String> mInterestDgreeList;
	private List<KeyValue> mInterestInterestHouseList;
	private List<String> mAgeRangeList;
	private List<KeyValue> mMediaList;
	private List<String> mCustomerResourceList;

	private String[] mInterestDgreeArray;
	private String[] mInterestInterestHouseArray;
	private String[] mAgeRangeArray;
	private String[] mMediaArray;
	private String[] mCustomerResourceArray;

	private EditText mEtName;
	private EditText mEtPhone;
	private EditText mEtComment;
	private TextView mTvInteresthouse;
	private TextView mTvInterestdegree;
	private TextView mTvAgeRange;
	private TextView mTvMedia;
	private TextView mTvCustomersource;
	private TextView mTvNextfollowdate;
	private RadioGroup mRgSex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("快速添加");
		mInterestDgreeList = CommonDataManager.getInterestDgree();
		mInterestInterestHouseList = CommonDataManager.getInterestHouse();
		mAgeRangeList = CommonDataManager.getVagerange();
		mMediaList = CommonDataManager.getMTZL();
		mCustomerResourceList = CommonDataManager.getCustomerResource();

		mInterestDgreeArray = new String[mInterestDgreeList.size()];
		for (int i = 0; i < mInterestDgreeList.size(); i++) {
			mInterestDgreeArray[i] = mInterestDgreeList.get(i);
		}

		mInterestInterestHouseArray = new String[mInterestInterestHouseList.size()];
		for (int i = 0; i < mInterestInterestHouseList.size(); i++) {
			mInterestInterestHouseArray[i] = mInterestInterestHouseList.get(i).getValue();
		}

		mAgeRangeArray = new String[mAgeRangeList.size()];
		for (int i = 0; i < mAgeRangeList.size(); i++) {
			mAgeRangeArray[i] = mAgeRangeList.get(i);
		}

		mMediaArray = new String[mMediaList.size()];
		for (int i = 0; i < mMediaList.size(); i++) {
			mMediaArray[i] = mMediaList.get(i).getValue();
		}

		mCustomerResourceArray = new String[mCustomerResourceList.size()];
		for (int i = 0; i < mCustomerResourceList.size(); i++) {
			mCustomerResourceArray[i] = mCustomerResourceList.get(i);
		}
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_add_customer;
	}

	@Override
	public void findViews() {
		mEtName = (EditText) findViewById(R.id.name);
		mEtPhone = (EditText) findViewById(R.id.phone);
		mEtComment = (EditText) findViewById(R.id.comment);
		mTvInteresthouse = (TextView) findViewById(R.id.tv_interesthouse);
		mTvInterestdegree = (TextView) findViewById(R.id.tv_interestdegree);
		mTvAgeRange = (TextView) findViewById(R.id.tv_agerange);
		mTvMedia = (TextView) findViewById(R.id.tv_media);
		mTvCustomersource = (TextView) findViewById(R.id.tv_customersource);
		mTvNextfollowdate = (TextView) findViewById(R.id.tv_nextfollowdate);
		mRgSex = (RadioGroup) findViewById(R.id.rg_sex);
	}

	@Override
	public void setListener() {
		findViewById(R.id.interesthouse).setOnClickListener(this);
		findViewById(R.id.interestdegree).setOnClickListener(this);
		findViewById(R.id.agerange).setOnClickListener(this);
		findViewById(R.id.media).setOnClickListener(this);
		findViewById(R.id.customersource).setOnClickListener(this);
		findViewById(R.id.nextfollowdate).setOnClickListener(this);
		mRgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_male) {
					customersex = "男";
				} else if (checkedId == R.id.rb_female) {
					customersex = "女";
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.interesthouse) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(mInterestInterestHouseArray, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					interesthousetypeid = mInterestInterestHouseList.get(which).getKey();
					mTvInteresthouse.setText(mInterestInterestHouseList.get(which).getValue());
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.interestdegree) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(mInterestDgreeArray, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					interestdegreeid = mInterestDgreeList.get(which);
					mTvInterestdegree.setText(mInterestDgreeList.get(which));
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.agerange) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(mAgeRangeArray, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					agebracket = mAgeRangeList.get(which);
					mTvAgeRange.setText(mAgeRangeList.get(which));
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.media) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(mMediaArray, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					mediaid = mMediaList.get(which).getKey();
					mTvMedia.setText(mMediaList.get(which).getValue());
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.customersource) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(mCustomerResourceArray, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					customersourceid = mCustomerResourceList.get(which);
					mTvCustomersource.setText(mCustomerResourceList.get(which));
				}
			});
			builder.create().show();
		} else if (v.getId() == R.id.nextfollowdate) {
			Calendar calendar = Calendar.getInstance();
			if (!TextUtils.isEmpty(nextfollowdate)) {
				calendar = DateTimeUtils.StringToCalendar(nextfollowdate);
			}
			DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, year);
					c.set(Calendar.MONTH, monthOfYear);
					c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					nextfollowdate = DateTimeUtils.CalendarToString(c);
					mTvNextfollowdate.setText(nextfollowdate);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.show();
		}
	}

	private void verify() {
		customername = mEtName.getText().toString().trim();
		if (TextUtils.isEmpty(customername)) {
			showToast("请输入姓名");
			return;
		}

		phone = mEtPhone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			showToast("请输入手机");
			return;
		}

		if (TextUtils.isEmpty(customersex)) {
			showToast("请选择性别");
			return;
		}

		if (TextUtils.isEmpty(interesthousetypeid)) {
			showToast("请选择意向房源");
			return;
		}

		if (TextUtils.isEmpty(interestdegreeid)) {
			showToast("请选择意向情况");
			return;
		}

		if (TextUtils.isEmpty(agebracket)) {
			showToast("请选择年龄段");
			return;
		}

		if (TextUtils.isEmpty(mediaid)) {
			showToast("请选择认知媒体");
			return;
		}
		if (TextUtils.isEmpty(customersourceid)) {
			showToast("请选择客户来源");
			return;
		}

		if (TextUtils.isEmpty(nextfollowdate)) {
			showToast("请选择下次跟进日期");
			return;
		}
		comment = mEtComment.getText().toString().trim();
		addOneCustomer();
	}

	private void addOneCustomer() {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.addOneCustomer(), String.valueOf(projectId), userid, username, customername, customersex, interestdegreeid, phone, interesthousetypeid, agebracket, mediaid,
				customersourceid, nextfollowdate, comment);
		url = url.replace(" ", "%20");
		requestHttp(url, REQUEST_ADDONECUSTOMER, this, true);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_ADDONECUSTOMER.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					showToast(R.string.response_invalid);
					return;
				}
				showToast("保存成功");
				setResult(Activity.RESULT_OK);
				finish();
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		showToast(R.string.network_or_server_invalid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_customer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_save) {
			verify();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
