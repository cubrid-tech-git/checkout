<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.service.JiraToRegchedbServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Get data from Jira to REGCHEDB</title>
</head>
<body>
	<%
		JiraToRegchedbServiceImpl service = new JiraToRegchedbServiceImpl();
			List<MainConSubVo> list = service.getUpdatedData();
			
			System.out.println("\n*** recored list ***");
			for(MainConSubVo vo : list) {
		System.out.println(vo.getCon_id() + "\t" + vo.getCon_year() + "\t" + vo.getCon_from_date() + "\t" + vo.getCon_to_date() + "\t" + vo.getCust_nm() + "\t" + vo.getProc_nm() + "\t" + vo.getCheck_nm() + "\t" + vo.getMain_oper_nm() + "\t" + vo.getSub_oper_nm() + "\t" + vo.getCon_desc() + "\t" + vo.getClose_yn() + "\t" + vo.getReg_date() + "\t" + vo.getUpd_date());
			}
			System.out.println();
			
			Map<String, Integer> map = service.getCustomFromJira(list);
			int success = map.get("success");
			int fail = map.get("fail");
			System.out.println("success : " + success + "\tfail : " + fail);
			
			out.println("<script>");
			out.println("location.href='../main.jsp'");
			out.println("</script>");
			
			service.insertOrUpdateFromJiraMember();
	%>
</body>
</html>