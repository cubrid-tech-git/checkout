<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpService"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	request.setAttribute("con_id", con_id);
	request.setAttribute("con_year", con_year);
	
	CheckUpService service = new CheckUpServiceImpl();
	MainConSubVo vo = service.selectInfoByCodeSeq(con_id, con_year);
	
	session.setAttribute("cust_nm", vo.getCust_nm());
	System.out.println("\n** delete site **");	
	System.out.println("site : " + vo.getCust_nm() + " - " + vo.getProc_nm());
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>사이트 지우기</title>
<script>
</script>
</head>
<body>
	<script>
		var check = confirm('다음 사이트를 삭제하시겠습니까? : ${cust_nm}');
		if(check) {
			location.href = "../../controller/deleteSiteController.jsp?con_id=${con_id}&con_year=${con_year}";
		} else {
			window.close();
		}
	</script>
</body>
</html>