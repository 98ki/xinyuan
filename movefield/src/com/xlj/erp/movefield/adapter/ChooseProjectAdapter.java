package com.xlj.erp.movefield.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.UserProject;

/**
 * 项目切换界面RecyclerView的Adapter
 * 
 * @author chaohui.yang
 *
 */
public class ChooseProjectAdapter extends RecyclerView.Adapter<ChooseProjectAdapter.ViewHolder> {
	private UserProject mCheckedProject;
	private List<UserProject> mUserProjectList;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mItemView;
		public CheckBox mProjectCheck;
		public TextView mProjectName;

		public ViewHolder(View itemView) {
			super(itemView);
			mItemView = itemView;
			mProjectCheck = (CheckBox) itemView.findViewById(R.id.project_check);
			mProjectName = (TextView) itemView.findViewById(R.id.project_name);
		}
	}

	public ChooseProjectAdapter(UserProject checkedProject, List<UserProject> userProjectList) {
		mCheckedProject = checkedProject;
		mUserProjectList = userProjectList;
		initProjectList();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_project, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		final UserProject project = mUserProjectList.get(i);
		viewHolder.mProjectName.setText(project.getName());
		viewHolder.mProjectCheck.setChecked(project.isChecked());
		viewHolder.mItemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!project.isChecked()) {
					setAllProjectUnChecked();
					project.setChecked(true);
					mCheckedProject = project;
					notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mUserProjectList.size();
	}

	private void initProjectList() {
		if (mUserProjectList == null) {
			return;
		}
		if (mCheckedProject == null) {
			for (UserProject p : mUserProjectList) {
				p.setChecked(false);
			}
			return;
		}
		for (UserProject p : mUserProjectList) {
			if (p.getProjectId() == mCheckedProject.getProjectId()) {
				p.setChecked(true);
			} else {
				p.setChecked(false);
			}
		}
	}

	private void setAllProjectUnChecked() {
		for (UserProject p : mUserProjectList) {
			p.setChecked(false);
		}
	}

	public UserProject getCheckedProject() {
		return mCheckedProject;
	}
}
