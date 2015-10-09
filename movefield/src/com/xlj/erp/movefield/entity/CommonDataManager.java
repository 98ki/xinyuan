package com.xlj.erp.movefield.entity;

import java.io.File;
import java.util.List;

import com.xlj.erp.movefield.MApplication;
import com.xlj.erp.movefield.utils.FileUtils;

public class CommonDataManager {
	private static final String FILE_INTERESTDGREE = "InterestDgree";
	private static final String FILE_VAGERANGE = "Vagerange";
	private static final String FILE_INTERESTHOUSE = "InterestHouse";
	private static final String FILE_MTZL = "MTZL";
	private static final String FILE_CUSTOMERRESOURCE = "CustomerResource";
	private static final String FILE_CUSTSTATUS = "CustStatus";
	private static final String FILE_GJFS = "GJFS";

	private static List<String> mInterestDgrees;
	private static List<String> mVagerange;
	private static List<KeyValue> mInterestHouse;
	private static List<KeyValue> mMTZL;
	private static List<String> mCustomerResource;
	private static List<String> mCustStatus;
	private static List<KeyValue> mGJFS;

	/**
	 * 客户意向(字符串列表)
	 */
	public static List<String> getInterestDgree() {
		if (mInterestDgrees == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTDGREE;
			mInterestDgrees = (List<String>) FileUtils.restoreObject(filePath);
		}
		return mInterestDgrees;
	}

	public static void setInterestDgree(List<String> interestDgrees) {
		mInterestDgrees = interestDgrees;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTDGREE;
		FileUtils.saveObject(mInterestDgrees, filePath);
	}

	/**
	 * 年龄段(字符串列表)
	 */
	public static List<String> getVagerange() {
		if (mVagerange == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_VAGERANGE;
			mVagerange = (List<String>) FileUtils.restoreObject(filePath);
		}
		return mVagerange;
	}

	public static void setVagerange(List<String> vagerange) {
		mVagerange = vagerange;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_VAGERANGE;
		FileUtils.saveObject(mVagerange, filePath);
	}

	/**
	 * 意向房源(key-value)
	 */
	public static List<KeyValue> getInterestHouse() {
		if (mInterestHouse == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTHOUSE;
			mInterestHouse = (List<KeyValue>) FileUtils.restoreObject(filePath);
		}
		return mInterestHouse;
	}

	public static void setInterestHouse(List<KeyValue> interestHouse) {
		mInterestHouse = interestHouse;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTHOUSE;
		FileUtils.saveObject(mInterestHouse, filePath);
	}

	/**
	 * 认知媒体(key-value)
	 */
	public static List<KeyValue> getMTZL() {
		if (mMTZL == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_MTZL;
			mMTZL = (List<KeyValue>) FileUtils.restoreObject(filePath);
		}
		return mMTZL;
	}

	public static void setMTZL(List<KeyValue> MTZL) {
		mMTZL = MTZL;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_MTZL;
		FileUtils.saveObject(mMTZL, filePath);
	}

	/**
	 * 客户来源(字符串列表)
	 */
	public static List<String> getCustomerResource() {
		if (mCustomerResource == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTOMERRESOURCE;
			mCustomerResource = (List<String>) FileUtils.restoreObject(filePath);
		}
		return mCustomerResource;
	}

	public static void setCustomerResource(List<String> customerResource) {
		mCustomerResource = customerResource;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTOMERRESOURCE;
		FileUtils.saveObject(mCustomerResource, filePath);
	}

	/**
	 * 客户状态(字符串列表)
	 */
	public static List<String> getCustStatus() {
		if (mCustStatus == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTSTATUS;
			mCustStatus = (List<String>) FileUtils.restoreObject(filePath);
		}
		return mCustStatus;
	}

	public static void setCustStatus(List<String> custStatus) {
		mCustStatus = custStatus;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTSTATUS;
		FileUtils.saveObject(mCustStatus, filePath);
	}

	/**
	 * 跟进方式(key-value)
	 */
	public static List<KeyValue> getGJFS() {
		if (mGJFS == null) {
			String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_GJFS;
			mGJFS = (List<KeyValue>) FileUtils.restoreObject(filePath);
		}
		return mGJFS;
	}

	/**
	 * 跟进方式(key-value)
	 */
	public static void setGJFS(List<KeyValue> GJFS) {
		mGJFS = GJFS;
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_GJFS;
		FileUtils.saveObject(mGJFS, filePath);
	}

	/**
	 * 清理缓存
	 */
	public static void clear() {
		String filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTDGREE;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_VAGERANGE;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_INTERESTHOUSE;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_MTZL;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTOMERRESOURCE;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_CUSTSTATUS;
		FileUtils.deleteFile(filePath);
		filePath = MApplication.mApplication.getFilesDir().getAbsolutePath() + File.separator + FILE_GJFS;
		FileUtils.deleteFile(filePath);
	}
}
