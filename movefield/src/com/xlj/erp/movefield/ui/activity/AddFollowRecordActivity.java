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
 * 添加跟进记录
 * 
 * @author chaohui.yang
 *
 */
public class AddFollowRecordActivity extends BaseToolbarActivity {
	private static String REQUEST_ADDONEFOLLOWRECORD = "getFollowRecord";

	private List<KeyValue> mFollowTypeList;
	private String[] mFollowTypeArray;
	private EditText mEtFollowContent;
	private TextView mTvFollowType;
	private TextView mTvFollowTime;
	private TextView mTvNextFollowTime;

	private String mCheckedFollowTypeId;
	private String mFollowTime;
	private String mNextFollowTime;

	private String mCustomerid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCustomerid = getIntent().getStringExtra("customerid");
		setTitle("增加跟进记录");

		mFollowTypeList = CommonDataManager.getGJFS();
		if (mFollowTypeList != null) {
			mFollowTypeArray = new String[mFollowTypeList.size()];
			for (int i = 0; i < mFollowTypeList.size(); i++) {
				KeyValue kv = mFollowTypeList.get(i);
				mFollowTypeArray[i] = kv.getValue();
			}
		}
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_add_follow_record;
	}

	@Override
	public void findViews() {
		mEtFollowContent = (EditText) findViewById(R.id.follow_content);
		mTvFollowType = (TextView) findViewById(R.id.tv_follow_type);
		mTvFollowTime = (TextView) findViewById(R.id.tv_follow_time);
		mTvNextFollowTime = (TextView) findViewById(R.id.tv_next_follow_time);
	}

	@Override
	public void setListener() {
		findViewById(R.id.follow_type).setOnClickListener(this);
		findViewById(R.id.follow_time).setOnClickListener(this);
		findViewById(R.id.next_follow_time).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.follow_type) {
			if (mFollowTypeArray != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setItems(mFollowTypeArray, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCheckedFollowTypeId = mFollowTypeList.get(which).getKey();
						mTvFollowType.setText(mFollowTypeList.get(which).getValue());
					}
				});
				builder.create().show();
			} else {
				showToast("没有跟进方式");
			}
		} else if (v.getId() == R.id.follow_time) {
			Calendar calendar = Calendar.getInstance();
			if (!TextUtils.isEmpty(mFollowTime)) {
				calendar = DateTimeUtils.StringToCalendar(mFollowTime);
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
					mFollowTime = DateTimeUtils.CalendarToString(c);
					mTvFollowTime.setText(mFollowTime);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.show();
		} else if (v.getId() == R.id.next_follow_time) {
			Calendar calendar = Calendar.getInstance();
			if (!TextUtils.isEmpty(mNextFollowTime)) {
				calendar = DateTimeUtils.StringToCalendar(mNextFollowTime);
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
					mNextFollowTime = DateTimeUtils.CalendarToString(c);
					mTvNextFollowTime.setText(mNextFollowTime);
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.show();
		}
	}

	private void verify() {
		if (TextUtils.isEmpty(mCheckedFollowTypeId)) {
			showToast("请选择跟进方式");
			return;
		}

		if (TextUtils.isEmpty(mFollowTime)) {
			showToast("请选择跟进时间");
			return;
		}

		String followContent = mEtFollowContent.getText().toString().trim();
		if (TextUtils.isEmpty(followContent)) {
			showToast("请输入跟进内容");
			return;
		}

		if (TextUtils.isEmpty(mNextFollowTime)) {
			showToast("请选择下次跟进时间");
			return;
		}

		addOneFollowRecord(followContent);
	}

	private void addOneFollowRecord(String followContent) {
		String userid = UserManager.getUser().getUserid();
		String username = UserManager.getUser().getUsername();
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String url = String.format(Urls.addOneFollowRecord(), String.valueOf(projectId), userid, username, mCustomerid, mCheckedFollowTypeId, mFollowTime, followContent, mNextFollowTime);
		url = url.replace(" ", "%20");
		requestHttp(url, REQUEST_ADDONEFOLLOWRECORD, this, true);
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_ADDONEFOLLOWRECORD.equals(request.getRequestTag())) {
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
		getMenuInflater().inflate(R.menu.menu_add_follow_record, menu);
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
