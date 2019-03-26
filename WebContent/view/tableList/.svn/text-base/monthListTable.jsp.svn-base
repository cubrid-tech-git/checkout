<%@page import="com.cubrid.util.count.CheckupCount"%>
<%@page import="com.cubrid.checkup.vo.CustRegSvcVo"%>
<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.cubrid.checkup.service.CheckUpServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Month List</title>
<link rel="stylesheet" type="text/css" href="../../css/tableList.css">
<script type="text/javascript" src="../../js/myScript.js"></script>
<!-- 마우스 휠을 이용해서 수평 스크롤 이동하는 jquery -->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="../../js/jquery.mousewheel.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('html, body, *').mousewheel(function(e, delta) {
			this.scrollLeft -= (delta * 40);
			e.preventDefault();
		});
	});
</script>
</head>
<body>
<%
	String curYear = request.getParameter("curYear");
	request.setAttribute("curYear", curYear);
	
	String memberName = request.getParameter("memberName");
	
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	Map<String, Object> map = service.selectResult(Integer.parseInt(curYear), memberName);
	
	@SuppressWarnings("unchecked")
	List<CustRegSvcVo> custRegSvcList = (List<CustRegSvcVo>)map.get("custRegSvcList");
	request.setAttribute("custRegSvcList", custRegSvcList);
	
	CheckupCount sumCount = (CheckupCount)map.get("count");
	request.setAttribute("sumCount", sumCount);
%>
	<table id="tb1" border="1" cellspacing="0" width="1800px">
		<tr id="boardTitle">
			<td colspan="3">1월</td>
			<td colspan="3">2월</td>
			<td colspan="3">3월</td>
			<td colspan="3">4월</td>
			<td colspan="3">5월</td>
			<td colspan="3">6월</td>
			<td colspan="3">7월</td>
			<td colspan="3">8월</td>
			<td colspan="3">9월</td>
			<td colspan="3">10월</td>
			<td colspan="3">11월</td>
			<td colspan="3">12월</td>
		</tr>
		<tr id="boardTitle">
			<c:forEach var="loop" begin="1" end="12">
				<td width="40px">방문</td>
				<td width="40px">원격</td>
				<td width="70px">실적</td>
			</c:forEach>
		</tr>
		<tr id="boardTitle">
			<td>${sumCount.scheduledVisit_1}</td>
			<td>${sumCount.scheduledRemote_1}</td>
			<td>${sumCount.monthRecent_1}/${sumCount.monthResult_1}</td>
			<td>${sumCount.scheduledVisit_2}</td>
			<td>${sumCount.scheduledRemote_2}</td>
			<td>${sumCount.monthRecent_2}/${sumCount.monthResult_2}</td>
			<td>${sumCount.scheduledVisit_3}</td>
			<td>${sumCount.scheduledRemote_3}</td>
			<td>${sumCount.monthRecent_3}/${sumCount.monthResult_3}</td>
			<td>${sumCount.scheduledVisit_4}</td>
			<td>${sumCount.scheduledRemote_4}</td>
			<td>${sumCount.monthRecent_4}/${sumCount.monthResult_4}</td>
			<td>${sumCount.scheduledVisit_5}</td>
			<td>${sumCount.scheduledRemote_5}</td>
			<td>${sumCount.monthRecent_5}/${sumCount.monthResult_5}</td>
			<td>${sumCount.scheduledVisit_6}</td>
			<td>${sumCount.scheduledRemote_6}</td>
			<td>${sumCount.monthRecent_6}/${sumCount.monthResult_6}</td>
			<td>${sumCount.scheduledVisit_7}</td>
			<td>${sumCount.scheduledRemote_7}</td>
			<td>${sumCount.monthRecent_7}/${sumCount.monthResult_7}</td>
			<td>${sumCount.scheduledVisit_8}</td>
			<td>${sumCount.scheduledRemote_8}</td>
			<td>${sumCount.monthRecent_8}/${sumCount.monthResult_8}</td>
			<td>${sumCount.scheduledVisit_9}</td>
			<td>${sumCount.scheduledRemote_9}</td>
			<td>${sumCount.monthRecent_9}/${sumCount.monthResult_9}</td>
			<td>${sumCount.scheduledVisit_10}</td>
			<td>${sumCount.scheduledRemote_10}</td>
			<td>${sumCount.monthRecent_10}/${sumCount.monthResult_10}</td>
			<td>${sumCount.scheduledVisit_11}</td>
			<td>${sumCount.scheduledRemote_11}</td>
			<td>${sumCount.monthRecent_11}/${sumCount.monthResult_11}</td>
			<td>${sumCount.scheduledVisit_12}</td>
			<td>${sumCount.scheduledRemote_12}</td>
			<td>${sumCount.monthRecent_12}/${sumCount.monthResult_12}</td>
		</tr>
		<c:set var="index" value="1"/>
		<c:forEach var="vo" items="${custRegSvcList}">
			<tr id="hr${index}">
				<c:choose>
					<c:when test="${vo.job_yn_1 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_1}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_1}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_1}</td>
						<td class="dataField">${vo.remote_cnt_1}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_1 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_1 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_1}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_1}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_1 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_1}', 'width=300, height=410')">${vo.job_nm_1}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_2 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_2}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_2}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_2}</td>
						<td class="dataField">${vo.remote_cnt_2}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_2 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_2 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_2}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_2}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_2 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_2}', 'width=300, height=410')">${vo.job_nm_2}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_3 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_3}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_3}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_3}</td>
						<td class="dataField">${vo.remote_cnt_3}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_3 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_3 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_3}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_3}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_3 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_3}', 'width=300, height=410')">${vo.job_nm_3}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_4 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_4}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_4}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_4}</td>
						<td class="dataField">${vo.remote_cnt_4}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_4 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_4 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_4}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_4}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_4 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_4}', 'width=300, height=410')">${vo.job_nm_4}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_5 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_5}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_5}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_5}</td>
						<td class="dataField">${vo.remote_cnt_5}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_5 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_5 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_5}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_5}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_5 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_5}', 'width=300, height=410')">${vo.job_nm_5}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_6 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_6}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_6}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_6}</td>
						<td class="dataField">${vo.remote_cnt_6}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_6 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_6 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_6}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_6}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_6 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_6}', 'width=300, height=410')">${vo.job_nm_6}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_7 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_7}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_7}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_7}</td>
						<td class="dataField">${vo.remote_cnt_7}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_7 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_7 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_7}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_7}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_7 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_7}', 'width=300, height=410')">${vo.job_nm_7}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_8 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_8}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_8}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_8}</td>
						<td class="dataField">${vo.remote_cnt_8}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_8 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_8 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_8}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_8}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_8 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_8}', 'width=300, height=410')">${vo.job_nm_8}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_9 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_9}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_9}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_9}</td>
						<td class="dataField">${vo.remote_cnt_9}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_9 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_9 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_9}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_9}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_9 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_9}', 'width=300, height=410')">${vo.job_nm_9}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_10 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_10}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_10}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_10}</td>
						<td class="dataField">${vo.remote_cnt_10}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_10 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_10 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_10}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_10}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_10 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_10}', 'width=300, height=410')">${vo.job_nm_10}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_11 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_11}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_11}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_11}</td>
						<td class="dataField">${vo.remote_cnt_11}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_11 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_11 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_11}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_11}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_11 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_11}', 'width=300, height=410')">${vo.job_nm_11}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${vo.job_yn_12 == 'C' }">
						<td class="dataField"><del>${vo.visit_cnt_12}</del></td>
						<td class="dataField"><del>${vo.remote_cnt_12}</del></td>
					</c:when>
					<c:otherwise>
						<td class="dataField">${vo.visit_cnt_12}</td>
						<td class="dataField">${vo.remote_cnt_12}</td>
					</c:otherwise>
				</c:choose>
				<td class="dataField">
					<c:choose>
						<c:when test="${vo.job_nm_12 == ''}">
							-
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${vo.job_yn_12 == 'N'}">
									<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_12}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_12}', 'width=270, height=220')">
								</c:when>
								<c:when test="${vo.job_yn_12 == 'Y'}">
									<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_12}', 'width=300, height=410')">${vo.job_nm_12}</a>
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
				<c:set var="index" value="${index + 1}"/>
			</tr>
		</c:forEach>
	</table>
</body>
</html>