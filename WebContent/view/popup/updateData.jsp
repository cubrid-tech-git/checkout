<%@page import="com.cubrid.util.date.MyDate"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.checkup.vo.CubMemberVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>정기점검 일정 수정</title>
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
	String type = null;
	
	String currDate = MyDate.currentDate();
	request.setAttribute("currDate", currDate);
	
	if(request.getParameter("type") == null) type = "ALL";
	else type = request.getParameter("type");
	
	request.setAttribute("con_id", con_id);
	request.setAttribute("con_year", con_year);
	request.setAttribute("type", type);
	
	JobOpVo vo = new JobOpVo();
	vo.setCon_id(con_id);
	vo.setCon_year(con_year);
	
	// service 객체 생성하여 vo 객체 리턴받기
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	MainConSubVo mainConSubVo = service.selectInfoByCodeSeq(con_id, con_year);
	request.setAttribute("mainConSubVo", mainConSubVo);
	
	if(type.equals("ALL")) {
		String allChecked = "checked=\"checked\"";
		request.setAttribute("allChecked", allChecked);
	} else if(type.equals("MONTH")){
		String monthChecked = "checked=\"checked\"";
		request.setAttribute("monthChecked", monthChecked);
		List<JobOpVo> list = service.selectMonth(vo);
		request.setAttribute("list", list);
	}
	service = new CheckUpServiceImpl();
	List<CubMemberVo> memberList = service.selectMember("Y");
	request.setAttribute("memberList", memberList);
%>
	<c:if test="${mainConSubVo.upd_date == null }">
		<script type="text/javascript">
			alert("일정이 등록되지 않았습니다.");
			window.close();
		</script>
	</c:if>
	<div id="wrapper">
		<img src="../../img/updateTitle.jpg">
		<br>
		<div>
			<input type="radio" name="type" id="type" value="ALL" onclick="selectRadio(this.value)" ${allChecked}> 전체 수정 &nbsp; 
			<input type="radio" name="type" id="type" value="MONTH" onclick="selectRadio(this.value)" ${monthChecked}> 선택 수정
		</div>
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
							<c:when test="${type == 'ALL'}">
								<select id="condition" onchange="selectCondition(this.value)">
									<option value="${mainConSubVo.check_nm}">${mainConSubVo.check_nm}</option>
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
							</c:when>
							<c:otherwise>
								<select id="condition" onchange="selectCondition(this.value)">
									<option value="">선택</option>
									<option value="V">방문</option>
									<option value="R">원격</option>
								</select>
							</c:otherwise>
						</c:choose>
					
						
					</td>
				</tr>
				<tr>
					<td id="boardTitle">담당자(정)</td>
					<td id="dataField">
						<select id="inspector">
							<option value="${mainConSubVo.main_oper_nm}">${mainConSubVo.main_oper_nm}</option>
							<!--
							<option value="김창휘">김창휘</option>
							<option value="정만영">정만영</option>
							<option value="김성진">김성진</option>
							<option value="이용미">이용미</option>
							<option value="박동윤">박동윤</option>
							<option value="김승훈">김승훈</option>
							-->
							<c:forEach var="member" items="${memberList}">
                                               			<option value="${member.cub_name}">${member.cub_name}</option>
                                        		</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">담당자(부)</td>
					<td id="dataField">
						<select id="inspector1">
							<option value="${mainConSubVo.sub_oper_nm}">${mainConSubVo.sub_oper_nm}</option>
							<!--
							<option value="김창휘">김창휘</option>
							<option value="정만영">정만영</option>
							<option value="김성진">김성진</option>
							<option value="이용미">이용미</option>
							<option value="박동윤">박동윤</option>
							<option value="김승훈">김승훈</option>
							-->
							<c:forEach var="member" items="${memberList}">
                                                                <option value="${member.cub_name}">${member.cub_name}</option>                                       
                                                        </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">점검일자</td>
					<td id="dataField">
						<c:if test="${type == 'MONTH'}">
							<select id="originDate">
								<option value="">선택</option>
								<c:forEach var="vo" items="${list}">
									<option value="${vo.job_date}">${vo.job_date}</option>	
								</c:forEach>
							</select> → 
						</c:if>
						<input type="date" id="date" value="${currDate}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">수정 사유</td>
					<td>
						<textarea id="job_reason" rows="5" cols="40"></textarea>
					</td>
				</tr>
			</table>
				<br>
				<!-- 데이터 입력시 가져갈 값들 -->
				<input id="con_id" type="hidden" value="${con_id}">
				<input id="con_year" type="hidden" value="${con_year}">
				<input type="hidden" id="from" value="${mainConSubVo.con_from_date}">
				<input type="hidden" id="to" value="${mainConSubVo.con_to_date}">
				<%-- <input type="hidden" id="checkYear" value="${year}">
				<input type="hidden" id="checkMonth" value="">
				<input type="hidden" id="checkDay" value=""> --%>
				
				<c:choose>
					<c:when test="${type == 'ALL'}">
						<input id="" type="button" value="수정" onclick="updateCheckAll('../../controller/allDataUpdate.jsp')">
						<input id="check_nm" type="hidden" value="${mainConSubVo.check_nm}">
					</c:when>
					<c:otherwise>
						<input id="submitButton" type="button" value="수정" onclick="updateCheckMonth('../../controller/monthDataUpdate.jsp')">
						<input id="check_nm" type="hidden" value="">
					</c:otherwise>
				</c:choose>
				<input id="resetButton" type="button" value="취소" onclick="eventReset()">
		</form>
	</div>
</body>
</html>
