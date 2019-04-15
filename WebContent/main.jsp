<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>정기점검 현황판</title>
<%
	String yearUrl = "view/title/selectYear.jsp";
	String tableUrl = "view/tableList/infoListTable.jsp";
	String memberName = request.getParameter("memberName");
	String curYear = request.getParameter("curYear");
	
	if(curYear != null && memberName != null) {
		yearUrl += "?curYear=" + curYear + "&memberName=" + memberName;
		tableUrl += "?curYear=" + curYear + "&memberName=" + memberName;
	} else if(curYear == null && memberName != null) {
		yearUrl += "?memberName=" + memberName;
		tableUrl += "?memberName=" + memberName;
	} else if(curYear != null && memberName == null) {
		yearUrl += "?curYear=" + curYear;
		tableUrl += "?curYear=" + curYear;
	}
	
	System.out.println("** Main Page **");
	System.out.println("memberName = " + memberName + "\n");
	request.setAttribute("yearUrl", yearUrl);
	request.setAttribute("tableUrl", tableUrl);
%>
<frameset cols="*, 1030, *" border="0">
	<frameset>
	</frameset>
	<frameset rows="90, *" border="0">
		<frameset cols="680,*">
			<frame src="view/title/titleImg.html" name="title" scrolling="no">
			<frame src="${yearUrl}" name="selectYear" scrolling="no">
		</frameset>
		<frameset>
			<frame marginwidth="0" src="${tableUrl}" name="infoListTable">
		</frameset>
	</frameset>
	<frameset>
	</frameset>
</frameset>
</html>
