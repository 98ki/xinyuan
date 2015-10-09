package com.xlj.erp.movefield.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xlj.erp.movefield.entity.Customer;

public class CustomerDao {
	private SQLiteDatabase mDb;

	public CustomerDao(String projectId) {
		mDb = DatabaseHelper.getInstance(projectId).getReadableDatabase();
	}

	/**
	 * 获取上次更新时间
	 * 
	 * @return
	 */
	public String getLastUpdateTime() {
		String result = "";
		String sql = "SELECT updatetime FROM tb_updatetime WHERE tag='updatetime'";
		Cursor cursor = mDb.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			result = cursor.getString(0);
		}
		cursor.close();
		return result;
	}

	/**
	 * 插入客户
	 * 
	 * @param customers
	 */
	public void insertCustomers(List<Customer> customers) {
		for (Customer customer : customers) {
			if (existCustomer(customer.getCustomerid())) {
				String sql = "UPDATE tb_customer SET customername=?,customersex=?,interestdegree=?,phone=?,status=?,vagerange=?,deleted=? WHERE customerid=?";
				mDb.execSQL(sql, new Object[] { customer.getCustomername(), customer.getCustomersex(), customer.getInterestdegree(), customer.getPhone(), customer.getStatus(),
						customer.getVagerange(), customer.getDeleted(), customer.getCustomerid() });
			} else {
				String sql = "INSERT INTO tb_customer(customerid,customername,customersex,interestdegree,phone,status,vagerange,deleted) VALUES(?,?,?,?,?,?,?,?)";
				mDb.execSQL(sql,
						new Object[] { customer.getCustomerid(), customer.getCustomername(), customer.getCustomersex(), customer.getInterestdegree(), customer.getPhone(), customer.getStatus(),
								customer.getVagerange(), customer.getDeleted() });
			}
		}
	}

	/**
	 * 是否已存在该客户
	 * 
	 * @param customerid
	 * @return
	 */
	public boolean existCustomer(String customerid) {
		boolean result = false;
		String sql = "SELECT customerid FROM tb_customer WHERE customerid=?";
		Cursor cursor = mDb.rawQuery(sql, new String[] { customerid });
		if (cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		return result;
	}

	/**
	 * 查询全部的客户
	 * 
	 * @return
	 */
	public List<Customer> findAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT customerid,customername,customersex,interestdegree,phone,status,vagerange FROM tb_customer WHERE deleted!='0'";
		Cursor cursor = mDb.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String customerid = cursor.getString(0);
			String customername = cursor.getString(1);
			String customersex = cursor.getString(2);
			String interestdegree = cursor.getString(3);
			String phone = cursor.getString(4);
			String status = cursor.getString(5);
			String vagerange = cursor.getString(6);
			Customer customer = new Customer(customerid, customername, customersex, interestdegree, phone, status, vagerange);
			customers.add(customer);
		}
		cursor.close();
		return customers;
	}

	/**
	 * 姓名、电话号码模糊查询
	 * 
	 * @param key
	 * @return
	 */
	public List<Customer> findAllCustomersByNamePhone(String key) {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT customerid,customername,customersex,interestdegree,phone,status,vagerange FROM tb_customer WHERE deleted!='0' AND (customername LIKE '%" + key + "%' OR phone LIKE '%"
				+ key + "%')";
		Cursor cursor = mDb.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String customerid = cursor.getString(0);
			String customername = cursor.getString(1);
			String customersex = cursor.getString(2);
			String interestdegree = cursor.getString(3);
			String phone = cursor.getString(4);
			String status = cursor.getString(5);
			String vagerange = cursor.getString(6);
			Customer customer = new Customer(customerid, customername, customersex, interestdegree, phone, status, vagerange);
			customers.add(customer);
		}
		cursor.close();
		return customers;
	}

}
