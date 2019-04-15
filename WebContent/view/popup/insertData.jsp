<%@page import="com.cubrid.util.date.MyDate"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>정기점검 일정 등록</title>
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
	String con_id = request.getParameter("con_id");
	String con_year = request.getParameter("con_year");
	request.setAttribute("con_id", con_id);
	request.setAttribute("con_year", con_year);
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	MainConSubVo mainConSubVo = service.selectInfoByCodeSeq(con_id, con_year);
	request.setAttribute("mainConSubVo", mainConSubVo);
	
	String currDate = MyDate.currentDate();
	request.setAttribute("currDate", currDate);
	System.out.println(currDate);
	
	System.out.println("\n ** insertData.jsp **");
	System.out.println("con_id : " + mainConSubVo.getCon_id() + "\tcon_year : " + mainConSubVo.getCon_year() + "\tcust_nm : " + mainConSubVo.getCust_nm() + "\tproc_nm : " + mainConSubVo.getProc_nm() + "\tdate : " + mainConSubVo.getCon_from_date() + " ~ " + mainConSubVo.getCon_to_date() + "\tcheck_nm : " + mainConSubVo.getCheck_nm() + "\tmain : " + mainConSubVo.getMain_oper_nm() + "\tsub : " + mainConSubVo.getSub_oper_nm());
%>
	<c:if test="${mainConSubVo.upd_date != null }">
		<script type="text/javascript">
			alert("이미 등록되어 있습니다.");
			window.close();
		</script>
	</c:if>
	<div id="wrapper">
		<img src="../../img/insertTitle.jpg">
		<br>
		<br>
		<form id="form1" action="" method="get">
			<table id="tb1">
				<tr>
					<td id="boardTitle">고객사</td>
					<td id="dataField">
						${mainConSubVo.cust_nm}
						<input type="hidden" id="customer" value="${mainConSubVo.cust_nm}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">사업명</td>
					<td id="dataField">
						${mainConSubVo.proc_nm}
						<input type="hidden" id="project" value="${mainConSubVo.proc_nm}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">계약기간</td>
					<td id="dataField">${mainConSubVo.con_from_date} ~ ${mainConSubVo.con_to_date}</td>
				</tr>
				<tr>
					<td id="boardTitle">점검조건</td>
					<td id="dataField">
						<c:choose>
							<c:when test="${mainConSubVo.check_nm != null}">
								<select id="condition" disabled="disabled">
									<option value="${mainConSubVo.check_nm}">${mainConSubVo.check_nm}</option>
								</select>
							</c:when>
							<c:otherwise>
								<select id="condition">
									<option value="">선택</option>
									<option value="방문(매월)">방문(매월)</option>
									<option value="방문(격월)">방문(격월)</option>
									<option value="방문(분기)">방문(분기)</option>
									<option value="방문(반기)">방문(반기)</option>
									<option value="원격(매월)">원격(매월)</option>
									<option value="원격(격월)">원격(격월)</option>
									<option value="원격(분기)">원격(분기)</option>
									<option value="원격(반기)">원격(반기)</option>
									<option value="방문(매월),원격(분기)">방문(매월),원격(분기)</option>
									<option value="방문(매월),원격(반기)">방문(매월),원격(반기)</option>
									<option value="방문(분기),원격(매월)">방문(분기),원격(매월)</option>
									<option value="방문(분기),원격(반기)">방문(분기),원격(반기)</option>
									<option value="방문(반기),원격(매월)">방문(반기),원격(매월)</option>
									<option value="방문(반기),원격(분기)">방문(반기),원격(분기)</option>
									<option value="방문(격월),원격(격월)">방문(격월),원격(격월)</option>
								</select>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">(정)담당자</td>
					<td id="dataField">
						<c:choose>
							<c:when test="${mainConSubVo.main_oper_nm != null}">
								<select id="inspector" disabled="disabled">
									<option value="${mainConSubVo.main_oper_nm}">${mainConSubVo.main_oper_nm}</option>
								</select>
							</c:when>
							<c:otherwise>
								<select id="inspector">
									<option value="">선택</option>
									<option value="김창휘">김창휘</option>
									<option value="정만영">정만영</option>
									<option value="김성진">김성진</option>
									<option value="이용미">이용미</option>
									<option value="박동윤">박동윤</option>
									<option value="김승훈">김승훈</option>
								</select>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">(부)담당자</td>
					<td id="dataField">
						<c:choose>
							<c:when test="${mainConSubVo.main_oper_nm != null}">
								<select id="inspector1" disabled="disabled">
									<option value="${mainConSubVo.sub_oper_nm}">${mainConSubVo.sub_oper_nm}</option>
								</select>
							</c:when>
							<c:otherwise>
								<select id="inspector1">
									<option value="">선택</option>
									<option value="김창휘">김창휘</option>
									<option value="정만영">정만영</option>
									<option value="김성진">김성진</option>
									<option value="이용미">이용미</option>
									<option value="박동윤">박동윤</option>
									<option value="김승훈">김승훈</option>
								</select>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">점검일자</td>
					<td id="dataField">
						<input type="date" id="date" value="${currDate}">
					</td>
				</tr>
			</table>
				<br>
				<!-- 데이터 입력시 가져갈 값들 -->
				<input id="con_id" type="hidden" value="${con_id}">
				<input id="con_year" type="hidden" value="${mainConSubVo.con_year}">
				<input type="hidden" id="from" value="${mainConSubVo.con_from_date}">
				<input type="hidden" id="to" value="${mainConSubVo.con_to_date}">
				
				<input id="submitButton" type="button" value="등록" onclick="insertCheckAndSubmit('../../controller/checkInsertAll.jsp')">
				<input id="resetButton" type="button" value="취소" onclick="eventReset()">
		</form>
	</div>
</body>
</html>