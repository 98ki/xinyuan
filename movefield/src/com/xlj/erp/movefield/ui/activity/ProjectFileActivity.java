package com.xlj.erp.movefield.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.xlj.erp.movefield.Contants;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.Urls;
import com.xlj.erp.movefield.adapter.ProjectFileAdapter;
import com.xlj.erp.movefield.base.volley.VolleyRequest;
import com.xlj.erp.movefield.entity.ProjectFile;
import com.xlj.erp.movefield.ui.base.BaseToolbarActivity;
import com.xlj.erp.movefield.utils.UserManager;
import com.xlj.erp.movefield.widget.MultiStateView;

/**
 * Created by zpy on 7/18/15.
 */
public class ProjectFileActivity extends BaseToolbarActivity {
	private static String REQUEST_GET_PROJECT_FILE_ID = "getSalesDocByProjectId";
	private ProjectFileAdapter mProjectFileAdapter;
	private RecyclerView mRecyclerView;
	private MultiStateView mMultiStateView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("销售资料");

		getProjectFile();
	}

	@Override
	public int getLayoutResId() {
		return R.layout.activity_project_file;
	}

	@Override
	public void findViews() {
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mMultiStateView = (MultiStateView) findViewById(R.id.multiStateView);
	}

	@Override
	public void setListener() {
		mMultiStateView.getView(MultiStateView.ViewState.ERROR).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMultiStateView.setViewState(MultiStateView.ViewState.LOADING);
				getProjectFile();
			}
		});
	}

	@Override
	public void onPost(VolleyRequest request, String result) {
		super.onPost(request, result);
		if (REQUEST_GET_PROJECT_FILE_ID.equals(request.getRequestTag())) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				int isSuccess = jsonObject.getInteger("isSuccess");
				if (isSuccess != Contants.REQUEST_SUCCESS) {
					mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
					return;
				}
				String data = jsonObject.getString("result");
				List<ProjectFile> files = JSON.parseArray(data, ProjectFile.class);
				if (files == null) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				if (files.size() == 0) {
					mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
					return;
				}
				// TODO 测试代码
				// for (int i = 0; i < files.size(); i++) {
				// ProjectFile file = files.get(i);
				// if (i == 0) {
				// file.setDocUrl("http://p.gdown.baidu.com/2e4828f7555394ea5ab3a163e66d205e74455e315a89e2010b5b111350db207fdafe82582040347892e8054d2db80918c58f29b5df2eeb11ce26a71c4ba0fd4d3069b7d46bb7352f27590cde42d7b5b1148ffb7c0ae0d17672e5670c6c0d59bc69f47b8c69be6e49cd1e45abfbb3b0fdf07af921f006d6284d97df81faae68f4d6314207ac0d4280");
				// }
				// if (i == 1) {
				// file.setDocUrl("http://apps.wandoujia.com/apps/com.nbahero.wdj/download");
				// }
				// if (i == 2) {
				// file.setDocUrl("http://129.9.9.9/aaaa.jpg");
				// }
				// if (i == 3) {
				// file.setDocUrl("http://p.gdown.baidu.com/41dc13b59af6f739f5bec776f11faad67cd397ba69abdf65e1fd8bfb6665b5339305dac692389f13c5eb1ba077323f644e1ea0a9dcb2e2c7be76f232c6c5a17dfddde48a6b7595e19b95e0bccf59bf1cce8c93fe5e9b8b8b5ae861d576d3c02d13809f8c37646b6bcd1accd0b90e97662004241dc352ccb78054ed571d83426b0efde24b993fa03d3390494bfc42d58bd3e04a3ba21a28763f5f1419747821ee3acaf8f0a482574c3eca9bb81333ed37d1a12153937d4f47");
				// }
				// if (i == 4) {
				// file.setDocUrl("http://p.gdown.baidu.com/2e4828f7555394ea5ab3a163e66d205e74455e315a89e2010b5b111350db207fdafe82582040347892e8054d2db80918c58f29b5df2eeb11ce26a71c4ba0fd4d3069b7d46bb7352f27590cde42d7b5b1148ffb7c0ae0d17672e5670c6c0d59bc69f47b8c69be6e49cd1e45abfbb3b0fdf07af921f006d6284d97df81faae68f4d6314207ac0d4280");
				// }
				// }

				mProjectFileAdapter = new ProjectFileAdapter(files);
				mRecyclerView.setAdapter(mProjectFileAdapter);

				RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
				mRecyclerView.setLayoutManager(layoutManager);
				mRecyclerView.setHasFixedSize(true);
				mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(R.string.response_json_invalid);
				mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
				return;
			}
		}
	}

	@Override
	public void onError(VolleyRequest request, VolleyError error) {
		super.onError(request, error);
		if (REQUEST_GET_PROJECT_FILE_ID.equals(request.getRequestTag())) {
			mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
		}
	}

	private void getProjectFile() {
		int projectId = UserManager.getCurrentProject(this).getProjectId();
		String username = UserManager.getUser().getUsername();
		requestHttp(String.format(Urls.getProjectFileByProjectId(), String.valueOf(projectId)), REQUEST_GET_PROJECT_FILE_ID, this);

	}

}