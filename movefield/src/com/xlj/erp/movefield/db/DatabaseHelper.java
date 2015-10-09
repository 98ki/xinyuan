package com.xlj.erp.movefield.db;

import java.util.HashMap;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xlj.erp.movefield.MApplication;

/**
 * 支持多数据库，每个数据库有唯一的连接Helper。整个程序运行中，SQLiteDatabase对象不关闭 。切换用户根据projectid清除数据
 * 
 * @author chaohui.yang
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	/**
	 * 数据库版本号
	 */
	private static final int DB_VERSION = 1;
	/**
	 * 多数据库，<数据库名称,数据库连接Helper>
	 */
	private static HashMap<String, DatabaseHelper> map = new HashMap<String, DatabaseHelper>();

	private DatabaseHelper(String name) {
		super(MApplication.mApplication, name, null, DB_VERSION);
	}

	public static synchronized DatabaseHelper getInstance(String name) {
		DatabaseHelper helper = map.get(name);
		if (helper == null) {
			helper = new DatabaseHelper(name);
			map.put(name, helper);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tb_customer = "CREATE TABLE IF NOT EXISTS tb_customer(customerid TEXT PRIMARY KEY,customername TEXT,customersex TEXT,interestdegree TEXT,phone TEXT,status TEXT,vagerange TEXT,deleted INTEGER)";
		String tb_updatetime = "CREATE TABLE IF NOT EXISTS tb_updatetime(tag TEXT PRIMARY KEY,updatetime TEXT)";
		db.execSQL(tb_customer);
		db.execSQL(tb_updatetime);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i < newVersion; i++) {
			switch (i) {
			// 数据库升级时，不同的版本在这里修改表结构、增删数据等
			}
		}
	}

}
