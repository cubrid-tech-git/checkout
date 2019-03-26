<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="com.cubrid.util.encoding.EncodingKorean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>점검 완료 등록 controller</title>
</head>
<body>
<%
	String id = request.getParameter("id");
	String year = request.getParameter("seq");
	String name = EncodingKorean.kor(request.getParameter("name"));
	String date = request.getParameter("date");
	String originDate = request.getParameter("originDate");
	String check_nm = request.getParameter("check_nm");
	String job_reason = EncodingKorean.kor(request.getParameter("job_reason"));
	System.out.println(id + "\t" + name + "\t" + year + "\t" + date + "\t" + originDate + "\t" + check_nm + "\t" + job_reason);
	JobOpVo vo = new JobOpVo();
	vo.setJob_reason(job_reason);
	vo.setCon_id(id);
	vo.setCon_year(year);
	vo.setJob_date(date);
	vo.setJob_nm(name);
	vo.setUpd_date(originDate);
	vo.setJob_visit_remote(check_nm);
	// service 객체 만들어서 insert 수행
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	int result = service.insertCompleteCheck(vo);
	String home = request.getContextPath();
	
	if(result == 0) {
		out.println("<script>");
		out.println("alert('입력 실패');");
		out.println("window.close();");
		out.println("</script>");
	} else {
		out.println("<script>");
		//out.println("alert('입력 성공');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	}
%>
</body>
</html>