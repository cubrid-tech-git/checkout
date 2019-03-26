<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.util.encoding.EncodingKorean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>전체 일정 수정</title>
</head>
<body>
<%
	System.out.println("\n** allDataUpdate.jsp **");

	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	String check_nm = EncodingKorean.kor(request.getParameter("check_nm"));
	String name = EncodingKorean.kor(request.getParameter("name"));
	String subName = EncodingKorean.kor(request.getParameter("subName"));
	String date = request.getParameter("date");
	String job_reason = EncodingKorean.kor(request.getParameter("job_reason"));
	System.out.println(con_id + ", " + con_year + ", " + check_nm + ", " + name + ", " + subName + ", " + date + ", " + job_reason);
	
	MainConSubVo vo = new MainConSubVo();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	vo.setCheck_nm(check_nm);
	vo.setMain_oper_nm(name);
	vo.setSub_oper_nm(subName);
	vo.setUpd_date(date);	// 수정할 날짜를 저장
	vo.setCon_desc(job_reason);	// 변경 사유를 저장
	
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	String home = request.getContextPath();
	int result = service.updateCheckAll(vo);
	
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
	} else if(result == -2) {
		out.println("<script>");
		out.println("alert('다른달로 작업이 이전된 경우 수정이 불가능합니다.');");
		out.println("window.close();");
		out.println("</script>");
	}
%>
	All Data Update
</body>
</html>