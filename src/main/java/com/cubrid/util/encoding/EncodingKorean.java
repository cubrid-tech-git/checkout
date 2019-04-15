package com.cubrid.util.encoding;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author HUN
 * 
 * jsp������ request.getParameter�� �Ѿ���� �ѱ��� ISO-8859-1 �� ���ڵ� �Ѵ�.
 * ���� �ش� ���ڵ� ����� EUC-KR�� �ٲٱ� ���� ����ϴ� ���ڵ� Ŭ���� 
 */
public class EncodingKorean {
	/**
	 * 
	 * @param String
	 * @return String
	 * 
	 * getParameter�� �Ѿ���� �ѱ��� ���ڵ� �ϱ� ���� �޼ҵ�
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
	 * utf-8 �� EUC-KR�� ����
	 * �ٵ� �̱����� ����� ��� �� �ȵ�... ����
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
	 * utf-8 �� ms949�� ����
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
