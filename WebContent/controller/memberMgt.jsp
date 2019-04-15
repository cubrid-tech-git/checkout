<%@page import="java.util.ArrayList"%>
<%@page import="com.cubrid.checkup.vo.CubMemberVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.service.CheckUpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	CheckUpService service = new CheckUpServiceImpl();
	List<CubMemberVo> memberList = service.selectMember("A");
	List<CubMemberVo> updateList = new ArrayList<CubMemberVo>();
	
	// 바뀐 값들을 받아와서
	for(CubMemberVo vo : memberList) {
		if(request.getParameter(vo.getJira_id() + "_del") != null) 
			vo.setShow_yn(request.getParameter(vo.getJira_id() + "_del"));
		else 
			vo.setShow_yn(request.getParameter(vo.getJira_id() + "_show"));
		
		updateList.add(vo);
	}
	
	// update 실시
	int result = service.updateCubMember(updateList);
	request.setAttribute("result", result);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Member Management</title>
</head>
<body>
	<script>
		alert("${result} DATA UPDATED");
		<%-- location.href = "<%=request.getContextPath()%>/view/admin/member.jsp"; --%>
		window.close();
	</script>
</body>
</html>