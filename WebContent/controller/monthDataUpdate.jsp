<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.util.encoding.EncodingKorean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>���� ���� controller</title>
</head>
<body>
<%
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String job_date = request.getParameter("job_date");
	String job_nm = EncodingKorean.kor(request.getParameter("job_nm"));
	String job_visit_remote = EncodingKorean.kor(request.getParameter("check_nm"));
	String job_reason = EncodingKorean.kor(request.getParameter("job_reason"));
	String originDate = request.getParameter("originDate");
	
	System.out.println(con_id + "\t" + con_year + "\t" + job_date + "\t" + job_nm + "\t" + job_visit_remote + "\t" + job_reason + "\t" + originDate);
	
	JobOpVo vo = new JobOpVo();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	vo.setUpd_date(job_date);
	vo.setJob_date(originDate);
	vo.setJob_nm(job_nm);
	vo.setJob_visit_remote(job_visit_remote);
	vo.setJob_reason(job_reason);
	
	// service �����ؼ� DB�� �����ڱ�
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	int result = service.updateCheckMonth(vo);
	String home = request.getContextPath();
	
	if(result == 0) {
		out.println("<script>");
		out.println("alert('���� ����');");
		out.println("window.close();");
		out.println("</script>");
	} else  if(result == 1){
		out.println("<script>");
		//out.println("alert('���� ����');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	} else if(result == -1) {
		out.println("<script>");
		out.println("alert('�ش� �޿��� �̹� ���������� �ֽ��ϴ�.');");
		out.println("window.close();");
		out.println("</script>");
	}
%>
</body>
</html>