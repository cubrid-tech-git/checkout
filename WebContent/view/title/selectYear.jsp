<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.vo.CubMemberVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page import="com.cubrid.util.date.*" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	ArrayList<String> yearList = service.selectYear();
	request.setAttribute("yearList", yearList);
	List<CubMemberVo> memberList = service.selectMember("Y");
	request.setAttribute("memberList", memberList);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>정기점검 현황판</title>
<style type="text/css">
#yearDiv {
	position: absolute; top: 66px; right: 15px;
}

#button {
	text-align: right;
}
</style>
<script type="text/javascript" src="../../js/myScript.js"></script>
<script type="text/javascript">
	onload = function() {
		<%
			String curYear = null;
			if(request.getParameter("curYear") == null) {
				curYear = Integer.toString(MyDate.currentYear());
			} else {
				curYear = request.getParameter("curYear");
			}
			request.setAttribute("curYear", curYear);
			
			String memberName = null;
			if(request.getParameter("memberName") == null) {
				memberName = "default";
			} else {
				memberName = request.getParameter("memberName");
			}
			request.setAttribute("memberName", memberName);
			System.out.println(" ** selectYear ** ");
			System.out.println("curYear : " + curYear);
			System.out.println("memberName : " + memberName);
		%>
		var curYear = <%=curYear%>; 
		var memberName = "<%=memberName%>";
		document.getElementById(curYear).setAttribute("selected", "selected");
		document.getElementById(memberName).setAttribute("selected", "selected");
	}
</script>
</head>
<body>
	<div id="button">
		<input type="button" value="일정 등록" onclick="openUrl('../popup/insertData.jsp', 'width=445, height=380')">
		<!-- <input type="button" value=" Get JIRA" onclick="getJira()"><br> -->
		<input type="button" value="일정 수정" onclick="openUrl('../popup/updateData.jsp', 'width=445, height=470')"><br>
		<input type="button" value="항목 삭제" onclick="openUrl('../popup/deleteSite.jsp', 'width=445, height=380');">
		<input type="button" value="이력 보기" onclick="window.open('../tableList/checkupHistory.jsp?curYear=${curYear}', '_blank')">
	</div>
	<div id="yearDiv">
		선택년도 : 
		<select id="yearSelect" onchange="selectYear(this.value)">
			<c:forEach var="year" items="${yearList}">
				<option id="${year}" value="${year}">${year}</option>
			</c:forEach>
			<!-- <option id="2012" value="2012">2012</option>
			<option id="2013" value="2013">2013</option>
			<option id="2014" value="2014">2014</option>
			<option id="2015" value="2015">2015</option> -->
		</select>
		담당자 :
		<select id="memberSelect" onchange="selectMemberName(this.value)">
			<option id="default" value="default">전체</option>
			<c:forEach var="member" items="${memberList}">
				<option id="${member.jira_id}" value="${member.jira_id}">${member.cub_name}</option>
			</c:forEach>
		</select>
	</div>
	<input type="hidden" id="curYear" value="${curYear}"></input>
	<input type="hidden" id="memberName" value="${memberName}"></input>
</body>
</html>