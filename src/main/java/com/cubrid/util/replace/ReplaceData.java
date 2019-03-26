package com.cubrid.util.replace;

public class ReplaceData {
	
	/**
	 * 
	 * @param String
	 * @return String
	 * 
	 * mm/dd/yyyy ������ �����͸� yyyy/mm/dd �� �ٲٴ� �޼ҵ�
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
	 * id�� �޾Ƽ� �̸��� �������ִ� �޼ҵ�
	 */
	public static String replaceName(String id) {
		String name = new String();
		
		if(id == null) name = "";
		else if(id.equals("bjchung")) name = "������";
		else if(id.equals("john")) name = "����";
		else if(id.equals("jwnam")) name = "�����";
		else if(id.equals("ssihil")) name = "�ս���";
		else if(id.equals("hsjang")) name = "������";
		else if(id.equals("kiho")) name = "����ȣ";
		else if(id.equals("hikwon")) name = "��ȣ��";
		else if(id.equals("bagus")) name = "������";
		else if(id.equals("myjun")) name = "������";
		else if(id.equals("joahram")) name = "���ƶ�";
		else if(id.equals("lym_1028")) name = "�̿��";
		else if(id.equals("pdywow")) name = "�ڵ���";
		else if(id.equals("seunghun_kim")) name = "�����";
		else if(id.equals("changhwee")) name = "��â��";
		else if(id.equals("hiclass")) name = "�輺��";
		else name = "spy";
		
		return name;
	}

	/**
	 * 
	 * @param int time
	 * @return String result
	 * 
	 * Int �������� ���� �� �ð��� 00 days 00 hours 00 minutes ���·� ��ȯ
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
	 * ������ ���� ��� ���� �ش� ������ �ƴϹǷ� 28�Ϸ� �����ϴ� �޼ҵ�
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
	 * ���� ������ ���ν����ִ� �޼ҵ�
	 */
	public static String replaceCondition(String check_nm) {
		StringBuffer output = new StringBuffer();
		
		if(check_nm == null) return null;
		else {
			output.setLength(0);
			if(check_nm.equals("�ſ�")) output.append("�湮(�ſ�)");
			else if(check_nm.equals("�ݿ�")) output.append("�湮(�ݿ�)");
			else if(check_nm.equals("�źб�")) output.append("�湮(�б�)");
			else if(check_nm.equals("�Źݱ�")) output.append("�湮(�ݱ�)");
			else if(check_nm.equals("�ſ�(�湮,����)")) output.append("�湮(�ݿ�),����(�ݿ�)");
			else if(check_nm.equals("�ſ�(����)")) output.append("����(�ſ�)");
			else if(check_nm.equals("�źб�(�湮,����)")) output.append("�湮(�б�),����(�ݱ�)");
			else return check_nm;
		}
		return output.toString();
	}
	
	/**
	 * 
	 * @param String input
	 * @return String output
	 * 
	 * V = �湮, R = ���� ���� �ٲ��ִ� �޼ҵ�
	 */
	public static String replaceVandR(String input) {
		StringBuffer output = new StringBuffer();
		output.setLength(0);
		
		if(input == null) return null;
		
		if(input.equals("V")) output.append("�湮");
		else if(input.equals("R")) output.append("����");
		
		return output.toString();
	}
}
