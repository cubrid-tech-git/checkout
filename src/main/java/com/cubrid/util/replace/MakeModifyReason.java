package com.cubrid.util.replace;

/**
 * 
 * @author HUN
 * 
 * job_reason 부분에 수정 내용을 append 하는 클래스
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
			str.append(tmp[1]);	// split 한 내용의 두 번째에 롤백 데이터가 있다
		} else return null;
		
		return str.toString();
	}
	
	public static String getWithoutRollbackData(String input) {
		StringBuffer str = new StringBuffer();
		String[] tmp = null;
		
		if(input != null) tmp = input.split("\\*\\*");
		else return null;
		
		str.setLength(0);
		// 배열의 0, 1 에는 YYYY-MM-DD:000 형식의 데이터가 들어있으므로 패스
		for(int i = 2; i < tmp.length; i++) {
			str.append(tmp[i]);
		}
		
		return str.toString();
	}
}
