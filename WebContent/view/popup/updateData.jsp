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
<title>�������� ���� ����</title>
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
	
	// service ��ü �����Ͽ� vo ��ü ���Ϲޱ�
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
			alert("������ ��ϵ��� �ʾҽ��ϴ�.");
			window.close();
		</script>
	</c:if>
	<div id="wrapper">
		<img src="../../img/updateTitle.jpg">
		<br>
		<div>
			<input type="radio" name="type" id="type" value="ALL" onclick="selectRadio(this.value)" ${allChecked}> ��ü ���� &nbsp; 
			<input type="radio" name="type" id="type" value="MONTH" onclick="selectRadio(this.value)" ${monthChecked}> ���� ����
		</div>
		<form id="form1" action="" method="get">
			<table id="tb1">
				<tr>
					<td id="boardTitle">����</td>
					<td id="dataField">
						${mainConSubVo.cust_nm}
						<input type="hidden" id="customer" value="${mainConSubVo.cust_nm}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">�����</td>
					<td id="dataField">
						${mainConSubVo.proc_nm}
						<input type="hidden" id="project" value="${mainConSubVo.proc_nm}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">���Ⱓ</td>
					<td id="dataField">${mainConSubVo.con_from_date} ~ ${mainConSubVo.con_to_date}</td>
				</tr>
				<tr>
					<td id="boardTitle">��������</td>
					<td id="dataField">
						<c:choose>
							<c:when test="${type == 'ALL'}">
								<select id="condition" onchange="selectCondition(this.value)">
									<option value="${mainConSubVo.check_nm}">${mainConSubVo.check_nm}</option>
									<option value="�湮(�ſ�)">�湮(�ſ�)</option>
									<option value="�湮(�ݿ�)">�湮(�ݿ�)</option>
									<option value="�湮(�б�)">�湮(�б�)</option>
									<option value="�湮(�ݱ�)">�湮(�ݱ�)</option>
									<option value="����(�ſ�)">����(�ſ�)</option>
									<option value="����(�ݿ�)">����(�ݿ�)</option>
									<option value="����(�б�)">����(�б�)</option>
									<option value="����(�ݱ�)">����(�ݱ�)</option>
									<option value="�湮(�ſ�),����(�б�)">�湮(�ſ�),����(�б�)</option>
									<option value="�湮(�ſ�),����(�ݱ�)">�湮(�ſ�),����(�ݱ�)</option>
									<option value="�湮(�б�),����(�ſ�)">�湮(�б�),����(�ſ�)</option>
									<option value="�湮(�б�),����(�ݱ�)">�湮(�б�),����(�ݱ�)</option>
									<option value="�湮(�ݱ�),����(�ſ�)">�湮(�ݱ�),����(�ſ�)</option>
									<option value="�湮(�ݱ�),����(�б�)">�湮(�ݱ�),����(�б�)</option>
									<option value="�湮(�ݿ�),����(�ݿ�)">�湮(�ݿ�),����(�ݿ�)</option>
								</select>
							</c:when>
							<c:otherwise>
								<select id="condition" onchange="selectCondition(this.value)">
									<option value="">����</option>
									<option value="V">�湮</option>
									<option value="R">����</option>
								</select>
							</c:otherwise>
						</c:choose>
					
						
					</td>
				</tr>
				<tr>
					<td id="boardTitle">�����(��)</td>
					<td id="dataField">
						<select id="inspector">
							<option value="${mainConSubVo.main_oper_nm}">${mainConSubVo.main_oper_nm}</option>
							<!--
							<option value="��â��">��â��</option>
							<option value="������">������</option>
							<option value="�輺��">�輺��</option>
							<option value="�̿��">�̿��</option>
							<option value="�ڵ���">�ڵ���</option>
							<option value="�����">�����</option>
							-->
							<c:forEach var="member" items="${memberList}">
                                               			<option value="${member.cub_name}">${member.cub_name}</option>
                                        		</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">�����(��)</td>
					<td id="dataField">
						<select id="inspector1">
							<option value="${mainConSubVo.sub_oper_nm}">${mainConSubVo.sub_oper_nm}</option>
							<!--
							<option value="��â��">��â��</option>
							<option value="������">������</option>
							<option value="�輺��">�輺��</option>
							<option value="�̿��">�̿��</option>
							<option value="�ڵ���">�ڵ���</option>
							<option value="�����">�����</option>
							-->
							<c:forEach var="member" items="${memberList}">
                                                                <option value="${member.cub_name}">${member.cub_name}</option>                                       
                                                        </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td id="boardTitle">��������</td>
					<td id="dataField">
						<c:if test="${type == 'MONTH'}">
							<select id="originDate">
								<option value="">����</option>
								<c:forEach var="vo" items="${list}">
									<option value="${vo.job_date}">${vo.job_date}</option>	
								</c:forEach>
							</select> �� 
						</c:if>
						<input type="date" id="date" value="${currDate}">
					</td>
				</tr>
				<tr>
					<td id="boardTitle">���� ����</td>
					<td>
						<textarea id="job_reason" rows="5" cols="40"></textarea>
					</td>
				</tr>
			</table>
				<br>
				<!-- ������ �Է½� ������ ���� -->
				<input id="con_id" type="hidden" value="${con_id}">
				<input id="con_year" type="hidden" value="${con_year}">
				<input type="hidden" id="from" value="${mainConSubVo.con_from_date}">
				<input type="hidden" id="to" value="${mainConSubVo.con_to_date}">
				<%-- <input type="hidden" id="checkYear" value="${year}">
				<input type="hidden" id="checkMonth" value="">
				<input type="hidden" id="checkDay" value=""> --%>
				
				<c:choose>
					<c:when test="${type == 'ALL'}">
						<input id="" type="button" value="����" onclick="updateCheckAll('../../controller/allDataUpdate.jsp')">
						<input id="check_nm" type="hidden" value="${mainConSubVo.check_nm}">
					</c:when>
					<c:otherwise>
						<input id="submitButton" type="button" value="����" onclick="updateCheckMonth('../../controller/monthDataUpdate.jsp')">
						<input id="check_nm" type="hidden" value="">
					</c:otherwise>
				</c:choose>
				<input id="resetButton" type="button" value="���" onclick="eventReset()">
		</form>
	</div>
</body>
</html>
