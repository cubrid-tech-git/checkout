package com.cubrid.util.replace;

public class ReplaceData {
	
	/**
	 * 
	 * @param String
	 * @return String
	 * 
	 * mm/dd/yyyy 형태의 데이터를 yyyy/mm/dd 로 바꾸는 메소드
	 */
	public static String replaceTimeFormat(String date) {
		String result = new String();
		
		if(!date.equals("")) {
			String[] data = new String[3];
			data = date.split("/");
			result = data[2].substring(2,4) + "/" +  data[0] + "/" + data[1];
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param String name
	 * @return String result
	 * 
	 * id를 받아서 이름을 리턴해주는 메소드
	 */
	public static String replaceName(String id) {
		String name = new String();
		
		if(id == null) name = "";
		else if(id.equals("bjchung")) name = "정병주";
		else if(id.equals("john")) name = "김상욱";
		else if(id.equals("jwnam")) name = "남재우";
		else if(id.equals("ssihil")) name = "손승일";
		else if(id.equals("hsjang")) name = "장현석";
		else if(id.equals("kiho")) name = "엄기호";
		else if(id.equals("hikwon")) name = "권호일";
		else if(id.equals("bagus")) name = "김주현";
		else if(id.equals("myjun")) name = "정만영";
		else if(id.equals("joahram")) name = "조아람";
		else if(id.equals("lym_1028")) name = "이용미";
		else if(id.equals("pdywow")) name = "박동윤";
		else if(id.equals("seunghun_kim")) name = "김승훈";
		else if(id.equals("changhwee")) name = "김창휘";
		else if(id.equals("hiclass")) name = "김성진";
		else name = "spy";
		
		return name;
	}

	/**
	 * 
	 * @param int time
	 * @return String result
	 * 
	 * Int 형식으로 들어온 총 시간을 00 days 00 hours 00 minutes 형태로 변환
	 */
	public static String replaceTimeSpent(int time) {
		String result = new String();
		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		
		weeks = time / (5 * 8 * 60 * 60);
		time = time - (weeks * 5 * 8 * 60 * 60);
		days = time / (8 * 60 * 60);
		time = time - (days * 8 * 60 * 60);
		hours = time / (60 * 60);
		time = time - (hours * 60 * 60);
		minutes = time / 60;
		
		if(weeks > 0) result += weeks + "w ";
		if(days > 0) result += days + "d ";
		if(hours > 0) result += hours + "h ";
		if(minutes > 0) result += minutes + "m";
		
		return result;
	}
	
	/**
	 * 
	 * @param String
	 * @return String
	 * 
	 * 윤년이 들어올 경우 다음 해는 윤년이 아니므로 28일로 변경하는 메소드
	 */
	public static String replaceEnddate(String endDate) {
		String[] data = new String[3];
		data = endDate.split("-");
		if(Integer.parseInt(data[2]) == 29) data[2] = "28";
		return data[0] + "-" + data[1] + "-" + data[2];
	}
	
	/**
	 * 
	 * @param String check_nm;
	 * @return	String;
	 * 
	 * 점검 조건을 매핑시켜주는 메소드
	 */
	public static String replaceCondition(String check_nm) {
		StringBuffer output = new StringBuffer();
		
		if(check_nm == null) return null;
		else {
			output.setLength(0);
			if(check_nm.equals("매월")) output.append("방문(매월)");
			else if(check_nm.equals("격월")) output.append("방문(격월)");
			else if(check_nm.equals("매분기")) output.append("방문(분기)");
			else if(check_nm.equals("매반기")) output.append("방문(반기)");
			else if(check_nm.equals("매월(방문,원격)")) output.append("방문(격월),원격(격월)");
			else if(check_nm.equals("매월(원격)")) output.append("원격(매월)");
			else if(check_nm.equals("매분기(방문,원격)")) output.append("방문(분기),원격(반기)");
			else return check_nm;
		}
		return output.toString();
	}
	
	/**
	 * 
	 * @param String input
	 * @return String output
	 * 
	 * V = 방문, R = 원격 으로 바꿔주는 메소드
	 */
	public static String replaceVandR(String input) {
		StringBuffer output = new StringBuffer();
		output.setLength(0);
		
		if(input == null) return null;
		
		if(input.equals("V")) output.append("방문");
		else if(input.equals("R")) output.append("원격");
		
		return output.toString();
	}
}
