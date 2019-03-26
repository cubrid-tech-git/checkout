package com.cubrid.util.replace;

/**
 * 
 * @author HUN
 * 
 * job_reason �κп� ���� ������ append �ϴ� Ŭ����
 *
 */
public class MakeModifyReason {

	public static String make(String before, String after) {
		StringBuffer str = new StringBuffer(); 
		str.setLength(0);
		if(before.equals("")) str.append(after);
		else str.append(before + "\n" + after);
		
		return str.toString();
	}
	
	public static String getDateAndName(String input) {
		StringBuffer str = new StringBuffer();
		
		if(input == null) return null;
		String[] tmp = input.split("\\*\\*");
		
		if(tmp != null && tmp[0].equals("")) {
			str.setLength(0);
			str.append(tmp[1]);	// split �� ������ �� ��°�� �ѹ� �����Ͱ� �ִ�
		} else return null;
		
		return str.toString();
	}
	
	public static String getWithoutRollbackData(String input) {
		StringBuffer str = new StringBuffer();
		String[] tmp = null;
		
		if(input != null) tmp = input.split("\\*\\*");
		else return null;
		
		str.setLength(0);
		// �迭�� 0, 1 ���� YYYY-MM-DD:000 ������ �����Ͱ� ��������Ƿ� �н�
		for(int i = 2; i < tmp.length; i++) {
			str.append(tmp[i]);
		}
		
		return str.toString();
	}
}
