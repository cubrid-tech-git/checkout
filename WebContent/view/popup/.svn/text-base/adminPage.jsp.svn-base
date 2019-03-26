<%@page import="com.cubrid.checkup.dao.CheckupDaoImpl"%>
<%@page import="com.cubrid.checkup.vo.MonthInfoVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String year = request.getParameter("curYear");
	String month = request.getParameter("month");
	List<MonthInfoVo> list = new CheckupDaoImpl().getCheckInfo(year, month);
	session.setAttribute("curYear", year);
	session.setAttribute("month", month);
	session.setAttribute("list", list);
	
	for(MonthInfoVo vo : list) {
		System.out.print("name : " + vo.getName());
		System.out.print("\tplan : " + vo.getPlan());
		System.out.print("\tresult : " + vo.getResult());
		System.out.println("\trate : " + vo.getAverage());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>월별 점검 현황</title>
<link rel="stylesheet" type="text/css" href="../../css/tableList.css">
</head>
<body>
	<div style="text-align: center;">
		<h2>${curYear}년  ${month}월 실적</h2>
		<table id="tb1" width="370px">
			<tr id="boardTitle">
				<td>점검자</td>
				<td>예정</td>
				<td>완료</td>
				<td>진행률</td>
			</tr>
			<c:forEach var="vo" items="${list}">
				<tr>
					<td>${vo.name}</td>
					<td>${vo.plan}</td>
					<td>${vo.result}</td>
					<td>${vo.average}</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<button onclick="self.close();">닫기</button>
	</div>
</body>
</html>