package com.xlj.erp.movefield.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
	/**
	 * yyyy-MM-dd HH:mm:ss 转换成Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar StringToCalendar(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = sdf.parse(date);
			calendar.setTime(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * Calendar转换成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param c
	 * @return
	 */
	public static String CalendarToString(Calendar c) {
		Date d = new Date(c.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d);
		return date;
	}
}
