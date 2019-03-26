package com.cubrid.util.date;

import java.util.Calendar;
import java.util.Locale;

public class MyDate {
	private static Calendar cal;
	
	/**
	 * 
	 * @return String
	 * 
	 * 현재 시간을 구하는 메소드
	 */
	public static String currentDate() {
		String date = new String();
		cal = Calendar.getInstance(Locale.KOREA);
		String month = new String();
		String day = new String();
		
		if((cal.get(Calendar.MONTH) + 1) < 10) month = "0" + (cal.get(Calendar.MONTH) + 1);
		else month = "" + (cal.get(Calendar.MONTH) + 1);
		
		if(cal.get(Calendar.DATE) < 10) day = "0" + cal.get(Calendar.DATE);
		else day = "" + cal.get(Calendar.DATE);
		
		date = cal.get(Calendar.YEAR) + "-" + month + "-" + day;
		
		return date;
	}
	
	/**
	 * 
	 * @return String
	 * 
	 * 현재로부터 1년 뒤 날짜를 구하는 메소드
	 */
	public static String nextYearDate() {
		String date = new String();
		cal = Calendar.getInstance(Locale.KOREA);
		
		date = (cal.get(Calendar.YEAR) + 1) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
		
		return date;
	}
	
	/**
	 * 
	 * @return String
	 * 
	 * 현재 달을 구하는 메소드
	 */
	public static int currentMonth() {
		cal = Calendar.getInstance(Locale.KOREA);
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 
	 * @return String
	 * 
	 * 지난 달을 구하는 메소드
	 */
	public static int lastMonth() {
		cal = Calendar.getInstance(Locale.KOREA);
		int month = cal.get(Calendar.MONTH) - 1;
		if(month < 0) month = 12;
		return month;
	}
	
	/**
	 * 
	 * @return String
	 * 
	 * 다음 달을 구하는 메소드
	 */
	public static int nextMonth() {
		cal = Calendar.getInstance(Locale.KOREA);
		int month = cal.get(Calendar.MONTH) + 2;
		if(month > 12) month = 1;
		return month;
	}
	
	/**
	 * 
	 * @return int
	 * 
	 * 현재 년도를 구하는 메소드
	 */
	public static int currentYear() {
		cal = Calendar.getInstance(Locale.KOREA);
		int year = cal.get(Calendar.YEAR);
		return year;
	}
}
