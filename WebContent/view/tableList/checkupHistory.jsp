<%@page import="com.cubrid.util.date.MyDate"%>
<%@page import="com.cubrid.checkup.vo.LoggingVo"%>
<%@page import="com.cubrid.logging.CheckuploggingImpl"%>
<%@page import="com.cubrid.logging.CheckupLogging"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>정기점검 이력</title>
<style type="text/css">
.dataField {
	font-size: x-small;
	height: 20;
}
.searcher {
	position: absolute; top: 25px; right: 10px;
}
</style>
<link rel="stylesheet" type="text/css" href="../../css/tableList.css">
<script type="text/javascript" src="../../js/myScript.js"></script>
<script type="text/javascript">
	onload = function() {
		<%
			//몇 년도 이력인지 알기 위해 받는 년도
			String curYear = null;
			if(request.getParameter("curYear") == null) {
				curYear = Integer.toString(MyDate.currentYear());
			} else {
				curYear = request.getParameter("curYear");
			}
			request.setAttribute("curYear", curYear);
		%>
		var curYear = <%=curYear%>; 
		document.getElementById(curYear).setAttribute("selected", "selected");
	}
</script>
</head>
<body>
<%
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	List<String> yearList = service.selectYear();
	request.setAttribute("yearList", yearList);
	
	// 검색시 넘어올 데이터를 받는 변수
	String searchColumn = request.getParameter("searchColumn");
	String searchText = request.getParameter("searchText");
	String category = null;
	
	/*
		이제 여기에서 searchColumn이 무슨 값인지 확인을 해보자.
		어떤 컬럼인지 확인 후 searchText를 같이 넣어서 던질 준비를 하자.
	*/
	if(searchColumn != null) {
		if(searchColumn.equals("conid")) {
			category = "사이트ID";
			searchText = searchText.toUpperCase();
		} else if(searchColumn.equals("cust")) {
			category = "고객명";
		} else if(searchColumn.equals("proc")) {
			category = "사업명";
		}
	}
	request.setAttribute("category", category);
	request.setAttribute("searchText", searchText);
	
	// logging 객체를 만들고 selectAll(String year, String condition, String arg) 호출하면 끝~
	CheckupLogging logging = new CheckuploggingImpl();
	List<LoggingVo> loggingList = logging.selectAll(curYear, searchColumn, searchText);
	request.setAttribute("loggingList", loggingList);
	
/* 	for(LoggingVo vo : loggingList) {
		System.out.println(vo.getLog_seq() + "\t" + vo.getJob_id() + "\t" + vo.getCon_id() + "\t" + vo.getCon_year()
				 + "\t" + vo.getLog_date() + "\t" + vo.getLog_content() + "\t" + vo.getCust_nm() + "\t" + vo.getProc_nm());
	} */
%>
	<div class="container">
		<div class="printTitle">
			<h4>
				${curYear} 년도 정기점검 이력 &nbsp;
				<c:choose>
					<c:when test="${category != null}">
						[ 검색조건 - category : ${category} / 검색어 : ${searchText}]
					</c:when>
					<c:otherwise>
						[ 전체 ]
					</c:otherwise>
				</c:choose>
			</h4>
		</div>
		<div class="searcher">
			<select id="yearSelect">
				<c:forEach var="year" items="${yearList}">
					<option id="${year}" value="${year}">${year}</option>
				</c:forEach>
			</select>
			<select id="searchColumn">
				<option value="">전체</option>
				<option value="conid">사이트ID</option>
				<option value="cust">고객사</option>
				<option value="proc">사업명</option>
			</select> <input type="text" id="searchText"> <input type="button" value="검색" onclick="searchEvent()">
		</div>
	</div>
	<table id="tb1" border="1" cellspacing="0" width="100%">
		<tr id="boardTitle" height="25px">
			<td width="5%">등록년도</td>
			<td width="5%">구분</td>
			<td width="10%">사이트ID</td>
			<td width="10%">고객사</td>
			<td width="10%">사업명</td>
			<td width="10%">로그 생성일</td>
			<td width="50%">내용</td>
		</tr>
		<c:forEach var="logging" items="${loggingList}">
			<tr class="dataField">
				<td>${logging.con_year}</td>
				<td>${logging.job_id}</td>
				<td>${logging.con_id}</td>
				<td>${logging.cust_nm}</td>
				<td>${logging.proc_nm}</td>
				<td>${logging.log_date}</td>
				<td>${logging.log_content}</td>
			</tr>
		</c:forEach>
	</table>
	<%-- <input type="hidden" id="curYear" value="${curYear}"> --%>
</body>
</html>