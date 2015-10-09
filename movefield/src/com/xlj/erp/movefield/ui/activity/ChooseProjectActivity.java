package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.adapter.ChooseProjectAdapter;
import com.xlj.erp.movefield.entity.UserProject;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.DividerItemDecoration;

/**
 * 项目切换
 * 
 * @author chaohui.yang
 *
 */
public class ChooseProjectActivity extends BaseToolbarActivity {
	private RecyclerView mProjectRecyclerView;
	private ChooseProjectAdapter mChooseProjectAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_choose_project);
		UserProject checkedProject = UserManager.getCurrentProject(this);
		List<UserProject> projectList = UserManager.getUser().getTempProperties();
		mChooseProjectAdapter = new ChooseProjectAdapter(checkedProject, projectList);
		mProjectRecyclerView.setAdapter(mChooseProjectAdapter);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mProjectRecyclerView.setLayoutManager(layoutManager);
		mProjectRecyclerView.setHasFixedSize(true);
		mProjectRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_choose_project;
	}

	@Override
	public void findViews() {
		mProjectRecyclerView = (RecyclerView) findViewById(R.id.choose_project_list);
	}

	@Override
	public void setListener() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_choose_project, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_sure) {
			if (mChooseProjectAdapter.getCheckedProject() == null) {
				showToast(R.string.choose_project_null_tip);
				return true;
			}
			// TODO 判断是否还是原来的project，效率
			UserManager.setCurrentProject(this, mChooseProjectAdapter.getCheckedProject());
			sentSwitchProjectBroadcast();
			// Intent data = new Intent();
			// data.putExtra(Contants.CHECKED_PROJECT,
			// mChooseProjectAdapter.getCheckedProject());
			// setResult(Activity.RESULT_OK, data);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 发送广播，通知主界面、6个Fragment
	 */
	private void sentSwitchProjectBroadcast() {
		Intent intent = new Intent(Contants.ACTION_SWITCH_PROJECT);
		sendBroadcast(intent);
	}

}
