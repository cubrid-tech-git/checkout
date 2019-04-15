<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>삭제 컨트롤러</title>
</head>
<body>
<%
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String job_date = request.getParameter("job_date");
	System.out.println("\n** deletePlan.jsp **");
	System.out.println("con_id : " + con_id + "\tcon_year : " + con_year + "\tjob_date : " + job_date);

	JobOpVo vo = new JobOpVo();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	vo.setJob_date(job_date);
	
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	int result = service.deletePlan(vo);

	if(result == 0) {
		out.println("<script>");
		out.println("alert('삭제 실패');");
		out.println("window.close();");
		out.println("</script>");
	} else  if(result == 1){
		out.println("<script>");
		//out.println("alert('삭제 성공');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	}
%>

</body>
</html>