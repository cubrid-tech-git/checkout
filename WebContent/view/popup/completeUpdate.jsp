<%@page import="com.cubrid.util.date.MyDate"%>
<%@page import="com.cubrid.util.replace.MakeModifyReason"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>완료 내용 수정</title>
<style type="text/css">
#submitButton {
	text-align: center;
}
#domain {
	width: 100px;
}
#dataField {
	text-align: left;
}
#boardTitle {
	color: white;
	background-color: #336699;
	font-weight: bold;
	text-align: center;
	width: 100px;
}
#wrapper {
	text-align: center;
	width: 100%;
}
td {
	height: 30px;
}
</style>
<script type="text/javascript" src="../../js/myScript.js"></script>
</head>
<body>
	<%
		String currDate = MyDate.currentDate();
		request.setAttribute("currDate", currDate);
		String con_id = request.getParameter("con_id");
		int con_year = Integer.parseInt(request.getParameter("con_year"));
		String date = request.getParameter("date");
		String name = request.getParameter("name");
		String originReason = request.getParameter("job_reason");
		String job_visit_remote = request.getParameter("job_visit_remote");
		
		request.setAttribute("con_id", con_id);
		request.setAttribute("con_year", con_year);
		request.setAttribute("originDate", date);
		request.setAttribute("name", name);
		request.setAttribute("originReason", originReason);
		request.setAttribute("job_visit_remote", job_visit_remote);
		
		System.out.println("\n ** completeUpdate.jsp **");
		System.out.println("con_id : " + con_id + "\tcon_year : " + con_year + "\toriginDate : " + date + "\tname : " + name + "\toriginReason : " + originReason + "\tjob_visit_remote : " + job_visit_remote);
	%>
	<div id="wrapper">
		<img src="../../img/check_rewrite.jpg">
		<table>
			<tr>
				<td id="boardTitle">점검자</td>
				<td id="dataField">
					<input id="inspector" type="text" value="${name}">
				</td>
			</tr>
			<tr>
				<td id="boardTitle">날짜</td>
				<td id="dataField">
					<input type="date" id="date" value="${originDate}">
				</td>
			</tr>
			<tr>
				<td id="boardTitle">점검방식</td>
				<td align="left">
					<select id="check_nm">
						<c:choose>
							<c:when test="${job_visit_remote == 'V'}">
								<option value="V">방문</option>
								<option value="R">원격</option>
							</c:when>
							<c:when test="${job_visit_remote == 'R'}">
								<option value="R">원격</option>
								<option value="V">방문</option>
							</c:when>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<td id="boardTitle">수정 이유</td>
				<td id="dataField">
					<textarea id="reason" rows="11" cols="20"></textarea>
				</td>
			</tr>
		</table>
		<br>
		<input type="hidden" id="con_id" value="${con_id}">
		<input type="hidden" id="con_year" value="${con_year}">
		<input type="hidden" id="originDate" value="${originDate}">
		<input type="hidden" id="originReason" value="${originReason}">
		<input type="button" value="입력" onclick="completeUpdate('../../controller/completeUpdateCtr.jsp')">
		<input type="button" value="등록취소" onclick="rollbackDate('../../controller/completeRollback.jsp')">
		<input type="button" value="취소" onclick="window.close()">
	</div>
</body>
</html>