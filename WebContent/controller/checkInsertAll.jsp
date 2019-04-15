<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>data insert controller</title>
</head>
<body>
<%
	System.out.println("data insert page");
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String check_nm = request.getParameter("condition");
	String main_oper_nm = request.getParameter("inspector");
	String sub_oper_nm = request.getParameter("inspector1");
	String date = request.getParameter("date");
	System.out.println("** parameter result **");
	System.out.println("con_id : " + con_id + ", con_year : " + con_year + ", check_nm : " + check_nm + ", main_oper_nm : " + main_oper_nm + ", date : " + date);
	
	// data insert 수행
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	MainConSubVo mainConSubVo = new MainConSubVo();
	mainConSubVo.setCon_id(con_id);
	mainConSubVo.setCon_year(con_year);
	mainConSubVo.setCheck_nm(check_nm);
	mainConSubVo.setMain_oper_nm(main_oper_nm);
	mainConSubVo.setSub_oper_nm(sub_oper_nm);
	
	String home = request.getContextPath();
	int result = service.insertCheckAll(mainConSubVo, date);
	
	if(result == 1) System.out.println(result);
	
	if(result == 0) {
		out.println("<script>");
		out.println("alert('입력 실패');");
		out.println("window.close();");
		out.println("</script>");
	} else  if(result == 1){
		out.println("<script>");
		//out.println("alert('입력 성공');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	} else if(result == -1) {
		out.println("<script>");
		out.println("alert('완료된 작업이 있을 경우 수정이 불가능합니다.');");
		out.println("window.close();");
		out.println("</script>");
	}
%>
</body>
</html>