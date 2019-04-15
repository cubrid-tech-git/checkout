<%@page import="com.cubrid.checkup.vo.CubMemberVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.util.date.MyDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>점검 완료 등록</title>
<script type="text/javascript" src="../../js/myScript.js"></script>
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
</head>
<body>
	<%
		String currDate = MyDate.currentDate();
		request.setAttribute("currDate", currDate);
		String name = request.getParameter("name").toString();
		request.setAttribute("name", name);
		String con_id = request.getParameter("con_id");
		request.setAttribute("con_id", con_id);
		int con_year = Integer.parseInt(request.getParameter("con_year"));
		request.setAttribute("con_year", con_year);
		int year = Integer.parseInt(request.getParameter("year"));
		request.setAttribute("year", year);
		String originDate = request.getParameter("date");
		originDate = originDate.substring(0, 4) + "-" + originDate.substring(4, 6) + "-" + originDate.substring(6, 8);
		request.setAttribute("originDate", originDate);
		int visit_cnt = Integer.parseInt(request.getParameter("visit_cnt"));
		int remote_cnt = Integer.parseInt(request.getParameter("remote_cnt"));
		request.setAttribute("visit_cnt", visit_cnt);
		request.setAttribute("remote_cnt", remote_cnt);
		
		
		request.setAttribute("originDate", originDate);
		
		System.out.println("id : " + con_id + "\tseq : " + con_year + "\tname : " + name + "\tyear : " + year + "\toriginDate : " + originDate);

		CheckUpServiceImpl service = new CheckUpServiceImpl();
		List<CubMemberVo> memberList = service.selectMember("Y");
		request.setAttribute("memberList", memberList);
	%>
	<div id="wrapper">
	<img src="../../img/completeTitle.jpg">
	<br>
	<br>
	<form action="">
	<table id="">
		<tr>
			<td id="boardTitle" width="100px">담 당 자</td>
			<td align="left">${name}</td>
		</tr>
		<tr>
			<td id="boardTitle" width="100px">점검방식</td>
			<td align="left">
				<select id="check_nm">
					<c:choose>
						<c:when test="${visit_cnt == 1}">
							<option value="V">방문</option>
							<option value="R">원격</option>
						</c:when>
						<c:when test="${remote_cnt == 1}">
							<option value="R">원격</option>
							<option value="V">방문</option>
						</c:when>
					</c:choose>
				</select>
			</td>
		</tr>
		<tr>
			<td id="boardTitle">점 검 자</td>
			<td align="left">
				<!-- 
				<select id="inspector">
					<option value="${name}">${name}</option>
					<option value="김창휘">김창휘</option>
					<option value="정만영">정만영</option>
					<option value="김성진">김성진</option>
					<option value="이용미">이용미</option>
					<option value="박동윤">박동윤</option>
					<option value="김승훈">김승훈</option>
				</select>
				-->
				<select id="inspector">
				 	<option value="${name}">${name}</option>
				 	<c:forEach var="member" items="${memberList}">
				 		<option value="${member.cub_name}">${member.cub_name}</option>
				 	</c:forEach>
				 </select>
			</td>
		</tr>
		<tr>
			<td id="boardTitle">점검예정일</td>
			<td>
				${originDate} 
			</td>
		</tr>
		<tr>
			<td id="boardTitle">점검날짜</td>
			<td>
				<input type="date" id="date" value="${currDate}">
			</td>
		</tr>
	</table>
	<br>
		<input type="hidden" id="con_id" value="${con_id}">
		<input type="hidden" id="con_year" value="${con_year}">
		<input type="hidden" id="originDate" value="${originDate}">
		<input type="hidden" id="main_oper" value="${name}">
		<input type="button" value="등록" onclick="checkupComplete('../../controller/checkComplete.jsp')">
		<input type="button" value="삭제" onclick="deletePlan('../../controller/deletePlan.jsp')">
		<input type="button" value="취소" onclick="eventReset()">
	</form>
	</div>
</body>
</html>
