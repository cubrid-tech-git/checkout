<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
#wrapper {
	text-align: center;
	width: 100%;
}

#boardTitle {
	color: white;
	background-color: #336699;
	font-weight: bold;
	text-align: center;
	font-size: small;
}
</style>
<script type="text/javascript" src="../../js/myScript.js"></script>
</head>
<body>
<%
	String id = request.getParameter("id");
	int seq = Integer.parseInt(request.getParameter("seq"));
	String name = request.getParameter("name");
	String date = request.getParameter("date");
	String originDate = request.getParameter("originDate");
	String check_nm = request.getParameter("check_nm");
	System.out.println(id + "\t" + name + "\t" + seq + "\t" + date + "\t" + originDate + "\t" + check_nm);
	request.setAttribute("con_id", id);
	request.setAttribute("con_year", seq);
	request.setAttribute("job_nm", name);
	request.setAttribute("job_date", date);
	request.setAttribute("originDate", originDate);
	request.setAttribute("check_nm", check_nm);
%>

<div id="wrapper">
	<table id="">
		<tr>
			<td id="boardTitle" width="100px">수정사유</td>
			<td>
				<textarea rows="10" cols="22" id="job_reason"></textarea>
			</td>
		</tr>
	</table>
	<input type="button" value="등록" onclick="checkupCompleteWithReason('../../controller/checkCompleteWithReason.jsp')">
	<input type="button" value="취소" onclick="eventReset()">
</div>
	<input type="hidden" id="con_id" value="${con_id}">
	<input type="hidden" id="con_year" value="${con_year}">
	<input type="hidden" id="inspector" value="${job_nm}">
	<input type="hidden" id="date" value="${job_date}">
	<input type="hidden" id="originDate" value="${originDate}">
	<input type="hidden" id="check_nm" value="${check_nm}">
</body>
</html>