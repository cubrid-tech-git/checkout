<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.service.CheckUpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	System.out.println("** delete site controller **");
	System.out.println("con_id : " + con_id + "\tcon_year : " + con_year);
	
	CheckUpService service = new CheckUpServiceImpl();
	int result = service.deleteSite(con_id, con_year);
	
	if(result > 0) {
		out.println("삭제 성공");
		System.out.println(con_id + " is deleted");
	} else {
		out.println("삭제 실패");
		System.out.println(con_id + " is not deleted");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>delete site controller</title>
</head>
<body>
	<script>
		opener.location.reload();
		window.close();
	</script>
</body>
</html>