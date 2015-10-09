package com.xlj.erp.movefield.utils;

import java.io.File;

import android.content.Context;

import com.xlj.erp.movefield.MApplication;
import com.xlj.erp.movefield.entity.User;
import com.xlj.erp.movefield.entity.UserProject;

/**
 * 登录用户信息管理
 * 
 * @author chaohui.yang
 *
 */
public class UserManager {
	private static final String USER_FILE = "user.u";
	private static User user;
	private static UserProject currentProject;

	public static User getUser() {
		// 考虑内存不足，app被回收，变量为null的情况
		if (user == null) {
			user = restoreUser();
		}
		return user;
	}

	public static void setUser(Context context, User u) {
		user = u;
		saveUser(context, user);
	}

	private static void saveUser(Context context, User user) {
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + USER_FILE;
		FileUtils.saveObject(user, filePath);

		// 设置上次选择的项目。如果用户切换了，则设置第一个项目。如果用户没有切换，找对应的项目，如果没有找到则设置第一个项目；如果找到设置上次选择的项目
		String lastUsername = PrefsUtils.getString(context, PrefsUtils.KEY_LOGIN_USERNAME, "");
		if (lastUsername.equals(user.getUsername())) {
			UserProject lastChooseProject = null;
			int lastProjectId = PrefsUtils.getInt(context, PrefsUtils.KEY_LAST_CHOOSE_PROJECT_ID, 0);
			for (UserProject p : user.getTempProperties()) {
				if (p.getProjectId() == lastProjectId) {
					lastChooseProject = p;
					break;
				}
			}
			if (lastChooseProject != null) {
				setCurrentProject(context, lastChooseProject);
			} else {
				setCurrentProject(context, user.getTempProperties().get(0));
			}
		} else {
			setCurrentProject(context, user.getTempProperties().get(0));
		}
	}

	private static User restoreUser() {
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + USER_FILE;
		return (User) FileUtils.restoreObject(filePath);
	}

	public static void setCurrentProject(Context context, UserProject userProject) {
		currentProject = userProject;
		PrefsUtils.putInt(context, PrefsUtils.KEY_LAST_CHOOSE_PROJECT_ID, userProject.getProjectId());
	}

	public static UserProject getCurrentProject(Context context) {
		// 考虑内存不足，app被回收后，变量为null的情况
		if (currentProject == null) {
			int lastProjectId = PrefsUtils.getInt(context, PrefsUtils.KEY_LAST_CHOOSE_PROJECT_ID, 0);
			for (UserProject p : getUser().getTempProperties()) {
				if (p.getProjectId() == lastProjectId) {
					currentProject = p;
					break;
				}
			}
			if (currentProject == null) {
				currentProject = getUser().getTempProperties().get(0);
			}
		}
		return currentProject;
	}
	
	public static void clear(){
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + USER_FILE;
		FileUtils.deleteFile(filePath);
	}
}
