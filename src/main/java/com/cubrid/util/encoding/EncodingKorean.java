package com.cubrid.util.encoding;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author HUN
 * 
 * jsp에서는 request.getParameter로 넘어오는 한글을 ISO-8859-1 로 인코딩 한다.
 * 따라서 해당 인코딩 결과를 EUC-KR로 바꾸기 위해 사용하는 인코딩 클래스 
 */
public class EncodingKorean {
	/**
	 * 
	 * @param String
	 * @return String
	 * 
	 * getParameter로 넘어오는 한글을 인코딩 하기 위한 메소드
	 */
	public static String kor(String input) {
		String result = null;
		/*
		if(input == null) result = null;
		else {
			try {
				result = new String(input.getBytes("ISO-8859-1"), "EUC-KR");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		result = input;
		
		return result;
	}
	
	/**
	 * @param String
	 * @return String
	 * 
	 * utf-8 을 EUC-KR로 변경
	 * 근데 이기종간 통신의 경우 잘 안됨... ㅅㅂ
	 */
	public static String utfToEuckr(String input) {
		String result = null;
		/*
		if(input == null) result = null;
		else {
			try {
				result = new String(input.getBytes("utf-8"), "EUC-KR");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		result = input;
		
		return result;
	}
	
	/**
	 * @param String
	 * @return String
	 * 
	 * utf-8 을 ms949로 변경
	 */
	public static String utfToMs(String input) {
		String result = null;
		
		/*
		if(input == null) return null;
		else {
			try {
				result = new String(input.getBytes("utf-8"), "ms949");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		result = input;
		
		return result;
	}
}
