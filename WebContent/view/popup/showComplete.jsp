<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@page import="com.cubrid.util.replace.MakeModifyReason"%>
<%@page import="com.cubrid.checkup.vo.MainConVo"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.checkup.vo.JobOpVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>점검 내용 확인</title>
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
		int curYear =Integer.parseInt(request.getParameter("year"));
			String con_id = request.getParameter("con_id");
			String con_year = request.getParameter("con_year");
			String date = request.getParameter("date");
			String newDate = null;
			//newDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
			request.setAttribute("con_id", con_id);
			request.setAttribute("con_year", con_year);
			request.setAttribute("curYear", curYear);
			request.setAttribute("date", date);
			
			System.out.println("show Complete page\ncurYear : " + curYear + "\tcon_id : " + con_id + "\tcon_year : " + con_year + "\tdate : " + date);
			
			JobOpVo inputJobOpVo = new JobOpVo();
			inputJobOpVo.setCon_id(con_id);
			inputJobOpVo.setCon_year(con_year);
			inputJobOpVo.setJob_date(date);
			
			CheckUpServiceImpl service = new CheckUpServiceImpl();
			Map<String, Object> map = service.selectCompleteShow(inputJobOpVo);
			MainConSubVo mainConSubVo = (MainConSubVo)map.get("mainConSubVo");
			JobOpVo resultJobOpVo = (JobOpVo)map.get("jobOpVo");
			request.setAttribute("mainConSubVo", mainConSubVo);
			request.setAttribute("resultJobOpVo", resultJobOpVo);
			System.out.println("Is working?");
			System.out.println(resultJobOpVo.getJob_reason());
			String job_reason = MakeModifyReason.getWithoutRollbackData(resultJobOpVo.getJob_reason());
			request.setAttribute("job_reason", job_reason);
	%>
</body>
	<div id="wrapper">
	<div>
		<img src="../../img/check_complete.jpg">
	</div>
	<table id="tb1">
		<tr>
			<td id="boardTitle">고객사</td>
			<td id="dataField">${mainConSubVo.cust_nm}</td>
		</tr>
		<tr>
			<td id="boardTitle">프로젝트</td>
			<td id="dataField">${mainConSubVo.proc_nm}</td>
		</tr>
		<tr>
			<td id="boardTitle">담당자</td>
			<td id="dataField">${mainConSubVo.main_oper_nm}</td>
		</tr>
		<tr>
			<td id="boardTitle">점검자</td>
			
			<td id="dataField">${resultJobOpVo.job_nm}</td>
		</tr>
		<tr>
			<td id="boardTitle">점검날짜</td>
			<td id="dataField">${resultJobOpVo.job_date}</td>
		</tr>
		<tr>
			<td id="boardTitle">등록날짜</td>
			<td id="dataField">${resultJobOpVo.upd_date}</td>
		</tr>
		<tr>
			<td id="boardTitle">점검방식</td>
			<td id="dataField">
				<c:choose>
					<c:when test="${resultJobOpVo.job_visit_remote == 'V'}">
						방문
					</c:when>
					<c:otherwise>
						원격
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
		<!-- modify_yn = 'Y' 일 경우, textArea 뿌려주기 -->
			<td id="boardTitle">수정사유</td>
			<td id="dataField">
				<textarea rows="3" cols="" disabled="disabled">${job_reason}</textarea>
			</td>
		</tr>
	</table>
	<input type="button" value="수정" onclick="openReWrite('${con_id}', '${con_year}', '${resultJobOpVo.job_date}', '${resultJobOpVo.job_nm}', '${resultJobOpVo.job_reason}', '${resultJobOpVo.job_visit_remote}')">
	<input type="button" value="닫기" onclick="window.close();">
	</div>
</html>