<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>점검 완료 되돌리기</title>
</head>
<body>
<%
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String originReason = request.getParameter("originReason");
	String newReason = request.getParameter("newReason");
	
	System.out.println("id : " + con_id + "\ncon_year : " + con_year + "\noriginReason : " + originReason + "\nnewReason : " + newReason);
	
	JobOpVo vo = new JobOpVo();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	vo.setJob_reason(originReason);
	// newReason을 저장할대가 없으니깐 modify_yn에다가...
	vo.setModify_yn(newReason);
	int result = new CheckUpServiceImpl().completeRollback(vo);
	
	String home = request.getContextPath();
	
	if(result == 0) {
		out.println("<script>");
		out.println("alert('수정 실패');");
		out.println("window.close();");
		out.println("</script>");
	} else {
		out.println("<script>");
		//out.println("alert('수정 성공');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	}
%>
	
</body>
</html>