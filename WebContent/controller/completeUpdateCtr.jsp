<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="com.cubrid.util.encoding.EncodingKorean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�Ϸ�� ���� ��Ʈ�ѷ�</title>
</head>
<body>
<%
	System.out.println(" ** completeUpdateCtr.jsp ** ");
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String originDate = request.getParameter("originDate");
	String job_date = request.getParameter("date");
	String job_nm = EncodingKorean.kor(request.getParameter("job_nm"));
	String job_reason = EncodingKorean.kor(request.getParameter("job_reason"));
	String check_nm = request.getParameter("check_nm");
	System.out.println("id : " + con_id + ", year : " + con_year + ", job_date : " + job_date + ", originDate : " + originDate + ", job_nm : " + job_nm + ", check_nm : " + check_nm + ", job_reason : " + job_reason);
	JobOpVo vo = new JobOpVo();
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	vo.setJob_date(job_date);
	vo.setUpd_date(originDate);
	vo.setJob_nm(job_nm);
	vo.setJob_reason(job_reason);
	vo.setJob_visit_remote(check_nm);
	
	// �����Ϸ��� �޿� �Ϸ���� ���� ������ �ִ��� Ȯ��
	List<JobOpVo> list = (List<JobOpVo>)service.selectMonth(vo);
	String compareDate = job_date.substring(0, 7);
	for(JobOpVo checkMonthVo : list) {
		String dbDate = checkMonthVo.getJob_date().substring(0, 7);
		System.out.println("not complete date : " + dbDate + "\tchangeDate : " + compareDate);
		if(dbDate.equals(compareDate)) {
			out.println("<script>");
			out.println("alert('�����Ϸ��� ��¥�� �Ϸ���� ���� ������ �����Ǿ��ֽ��ϴ�.');");
			out.println("window.history.back();");
			out.println("</script>");
			return;
		}
	}
	
	String home = request.getContextPath();
	int result = service.insertCompleteCheck(vo);
	
	if(result == -3) {
		out.println("<script>");
		out.println("alert('�Ϸ� ��¥�� �ߺ��Ǿ� ������ �Ұ��մϴ�.');");
		out.println("window.close();");
		out.println("</script>");
	}else if(result == 0) {
		out.println("<script>");
		out.println("alert('���� ����');");
		out.println("window.close();");
		out.println("</script>");
	} else {
		out.println("<script>");
		//out.println("alert('���� ����');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
	}
%>

</body>
</html>