<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.checkup.vo.CubMemberVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>CUBRID MEMBER</title>
<link rel="stylesheet" type="text/css" href="../../css/tableList.css">
<script type="text/javascript" src="../../js/myScript.js"></script>
<style>
table#tb1 {
	border-collapse: collapse;
}
#tbl_div {
	text-align: center;
	width: 770px;
}
</style>
</head>
<body>
	<%
		CheckUpServiceImpl service = new CheckUpServiceImpl();
		List<CubMemberVo> memberList = service.selectMember("A");
		request.setAttribute("memberList", memberList);
		
		for(CubMemberVo vo : memberList) {
			System.out.println("name : " + vo.getCub_name() + "\tshow_yn : " + vo.getShow_yn());
		}
	%>
</body>
<div id="tbl_div">
	<form action="../../controller/memberMgt.jsp">
		<table id="tb1" border="1">
			<tr id="boardTitle">
				<td width="150px">ID</td>
				<td width="100px">이름</td>
				<td width="300px">e-mail</td>
				<td width="140px">점검 출력 설정</td>
				<td width="70px">삭제</td>
			</tr>
			<c:forEach var="member" items="${memberList}">
				<tr>
					<td id="${member.jira_id}">${member.jira_id}</td>
					<td>${member.cub_name}</td>
					<td>${member.email_addr}</td>
					<td id="${member.jira_id}_td">
						<c:choose>
							<c:when test="${member.show_yn == 'Y'}">
								<input type="radio" name="${member.jira_id}_show" value="Y" checked="checked">표시 &nbsp;
								<input type="radio" name="${member.jira_id}_show" value="N">숨김
							</c:when>
							<c:otherwise>
								<input type="radio" name="${member.jira_id}_show" value="Y">표시 &nbsp;
								<input type="radio" name="${member.jira_id}_show" value="N" checked="checked">숨김
							</c:otherwise>
						</c:choose>
					</td>
					<td id="${member.jira_id}_del">
						<input type="checkbox" name="${member.jira_id}_del" value="D">삭제
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<input type="submit" value="입력"> &nbsp;&nbsp;&nbsp;
		<input type="reset"	value="취소">
	</form>
</div>
</html>