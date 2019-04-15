<%@page import="com.cubrid.checkup.vo.MainConSubVo"%>
<%@page import="com.cubrid.util.count.CheckupCount"%>
<%@page import="com.cubrid.checkup.vo.ResultSumVo"%>
<%@page import="com.cubrid.checkup.vo.CustRegSvcVo"%>
<%@page import="com.cubrid.checkup.vo.MainConVo"%>
<%@page import="java.util.List"%>
<%@page import="com.cubrid.util.date.MyDate"%>
<%@page import="com.cubrid.checkup.dao.*"%>
<%@page import="com.cubrid.checkup.service.*"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String curYear = request.getParameter("curYear");
	String memberName = request.getParameter("memberName");
	
	CheckUpServiceImpl service = new CheckUpServiceImpl();
	
	if(curYear == null) curYear = Integer.toString(MyDate.currentYear());
	request.setAttribute("curYear", curYear);
	
	Map<String, Object> map = service.selectResult(Integer.parseInt(curYear), memberName);
	
	@SuppressWarnings("unchecked")
	List<MainConSubVo> mainConSubList = (List<MainConSubVo>)map.get("mainConSubList");
	request.setAttribute("mainConSubList", mainConSubList);
	
	@SuppressWarnings("unchecked")
	List<CustRegSvcVo> custRegSvcList = (List<CustRegSvcVo>)map.get("custRegSvcList");
	request.setAttribute("custRegSvcList", custRegSvcList);
	
	CheckupCount sumCount = (CheckupCount)map.get("count");
	request.setAttribute("sumCount", sumCount);
	
	int count = service.recordCount(Integer.parseInt(curYear), memberName);
	request.setAttribute("count", count);
	int iframeHeight = 36 * (count + 10);
	request.setAttribute("iframeHeight", iframeHeight);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Info List</title>
<style type="text/css">
html {
	overflow-x: hidden;
}
#padding {
	width: 1000px;
	height: 30px;
	background-color: white;
	position: fixed; top: -5px;
	z-index: 3;
}
#scrollbar {
	overflow-y: hidden;
	overflow-x: scroll;
	background-color: white;
	width: 445px;
	height: 10px;
	position: fixed; left: 548px;
	z-index: 5;
}
#contentIframe {
	overflow: hidden;
	overflow-x: visible; 
	width: 445px;
	position: absolute; left: 548px; top: 15px;
	z-index: 2;
}
#titleIframe {
	overflow-x: hidden;
	overflow-y: hiddin;
	width: 445px;
	height: 120px; 
	position: fixed; left: 548px; top: 15px;
	z-index: 4;
}
#leftTitle {
	position: fixed; top: 15px;
	z-index: 4;
}
#leftContent {
	position: absolute; top: 15px;
}
</style>
<link rel="stylesheet" type="text/css" href="../../css/tableList.css">
<script type="text/javascript" src="../../js/myScript.js"></script>
<!-- 마우스 휠을 이용해서 수평 스크롤 이동하는 jquery -->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="../../js/jquery.mousewheel.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".wheel").mousewheel(function(e, delta) {
			this.scrollLeft -= (delta * 40);
			e.preventDefault();
		});
		
		$(".monthInfo").click(function() {
			var month = this.title;
			window.open("../popup/adminPage.jsp?curYear=${curYear}&month=" + month, "_blank", "width=400, height=380");
		});
	});
</script>
</head>
<body>
	<div id="padding"></div>
	<div id="leftTitle">
		<table id="tb1" border="1" cellspacing="0" width="550px">
			<tr id="boardTitle" height="25px">
				<td colspan="2">계약기간</td>
				<td width="210px" rowspan="3">고 객<br>[사업명]</td>
				<td width="65px" rowspan="3">점검조건</td>
				<td width="50px" rowspan="3">담당자</td>
				<td colspan="2">합계</td>
			</tr>
			<tr id="boardTitle">
				<td width="55px" rowspan="2">from</td>
				<td width="55px" rowspan="2">to</td>
				<td width="50px">계획</td>
				<td width="50px">실적</td>
			</tr>
			<tr id="boardTitle">
				<td><font color="yello" style="font-size: x-small;">${sumCount.totalVisit}, ${sumCount.totalRemote}</font></td>
				<td><font color="yello" style="font-size: x-small;">${sumCount.totalRecentSum}/${sumCount.totalResultSum}</font></td>
			</tr>
		</table>
	</div>
	<div id="leftContent">
		<form action="">
			<table id="tb1" border="1" cellspacing="0" width="550px">
				<tr id="boardTitle" height="25px">
				<td colspan="2">계약기간</td>
				<td width="210px" rowspan="3">고 객<br>[사업명]</td>
				<td width="65px" rowspan="3">점검조건</td>
				<td width="50px" rowspan="3">담당자</td>
				<td colspan="2">합계</td>
				</tr>
				<tr id="boardTitle">
					<td width="55px" rowspan="2">from</td>
					<td width="55px" rowspan="2">to</td>
					<td width="50px">계획</td>
					<td width="50px">실적</td>
				</tr>
				<tr id="boardTitle">
					<td><font color="yello" style="font-size: x-small;">${sumCount.totalVisit}, ${sumCount.totalRemote}</font></td>
					<td><font color="yello" style="font-size: x-small;">${sumCount.totalRecentSum}/${sumCount.totalResultSum}</font></td>
				</tr>
				<c:set var="index" value="1"/>
				<c:forEach var="vo" items="${mainConSubList}">
				<tr id="hr${index}" title="${vo.con_id}" >
					<td id="hr${index}" class="dataField" width="55px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})">${vo.con_from_date}</td>
					<td id="hr${index}" class="dataField" width="55px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})">${vo.con_to_date}</td>
					<td id="hr${index}" class="dataField" width="210px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})"><a href="http://dev.cubrid.com:8888/browse/${vo.con_id}" target="_blank">${vo.cust_nm}</a><br>[${vo.proc_nm}]</td>
					<td id="hr${index}" class="dataField" width="65px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})">${vo.check_nm}</td>
					<td id="hr${index}" class="dataField" width="50px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})">${vo.main_oper_nm}</td>
					<td id="hr${index}" class="dataField" width="50px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})" style="font-weight: bold;">${vo.visitPlanCount},${vo.remotePlanCount}</td>
					<td id="hr${index}" class="dataField" width="50px" onclick="changeSelect(this.id, ${count}, ${vo.con_year})"style="font-weight: bold;">${vo.monthResultCount}/${vo.monthTotalResultCount}</td>
					<c:set var="index" value="${index + 1}"/>
				</tr>
			</c:forEach>
			</table>
		</form>
	</div>
	
	<div id="scrollbar" class="wheel" onscroll="javascript:document.all.titleIframe.scrollLeft = document.all.scrollbar.scrollLeft;document.all.contentframe.scrollLeft = document.all.scrollbar.scrollLeft">
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
		</table>
	</div>
	<div id="titleIframe" class="wheel" onscroll="javascript:document.all.contentIframe.scrollLeft = document.all.titleIframe.scrollLeft;document.all.scrollbar.scrollLeft = document.all.titleIframe.scrollLeft">
		<table id="tb1" border="1" cellspacing="0" width="1800px">
			<tr id="boardTitle">
				<td class="monthInfo" title="1" colspan="3">1월</td>
				<td class="monthInfo" title="2" colspan="3">2월</td>
				<td class="monthInfo" title="3" colspan="3">3월</td>
				<td class="monthInfo" title="4" colspan="3">4월</td>
				<td class="monthInfo" title="5" colspan="3">5월</td>
				<td class="monthInfo" title="6" colspan="3">6월</td>
				<td class="monthInfo" title="7" colspan="3">7월</td>
				<td class="monthInfo" title="8" colspan="3">8월</td>
				<td class="monthInfo" title="9" colspan="3">9월</td>
				<td class="monthInfo" title="10" colspan="3">10월</td>
				<td class="monthInfo" title="11" colspan="3">11월</td>
				<td class="monthInfo" title="12" colspan="3">12월</td>
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
		</table>
	</div>
	
	<div id="contentIframe" class="wheel" height="${iframeHeight}" onscroll="javascript:document.all.scrollbar.scrollLeft = document.all.contentIframe.scrollLeft;document.all.titleIframe.scrollLeft = document.all.contentIframe.scrollLeft">
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
			<c:set var="index1" value="1"/>
			<c:forEach var="vo" items="${custRegSvcList}">
				<tr id="hrm${index1}" title="hr${index1}">
					<c:choose>
						<c:when test="${vo.job_yn_1 == 'C' }">
							<td class="dataField" width="40px" width="40px"><del>${vo.visit_cnt_1}</del></td>
							<td class="dataField" width="40px" width="40px"><del>${vo.remote_cnt_1}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px" width="40px">${vo.visit_cnt_1}</td>
							<td class="dataField" width="40px" width="40px">${vo.remote_cnt_1}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_1 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_1 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_1}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_1}&visit_cnt=${vo.visit_cnt_1}&remote_cnt=${vo.remote_cnt_1}&visit_cnt=${vo.visit_cnt_1}&remote_cnt=${vo.remote_cnt_1}&visit_cnt=${vo.visit_cnt_1}&remote_cnt=${vo.remote_cnt_1}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_2}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_2}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_2}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_2}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_2 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_2 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_2}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_2}&visit_cnt=${vo.visit_cnt_2}&remote_cnt=${vo.remote_cnt_2}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_3}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_3}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_3}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_3}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_3 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_3 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_3}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_3}&visit_cnt=${vo.visit_cnt_3}&remote_cnt=${vo.remote_cnt_3}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_4}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_4}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_4}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_4}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_4 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_4 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_4}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_4}&visit_cnt=${vo.visit_cnt_4}&remote_cnt=${vo.remote_cnt_4}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_5}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_5}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_5}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_5}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_5 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_5 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_5}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_5}&visit_cnt=${vo.visit_cnt_5}&remote_cnt=${vo.remote_cnt_5}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_6}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_6}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_6}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_6}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_6 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_6 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_6}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_6}&visit_cnt=${vo.visit_cnt_6}&remote_cnt=${vo.remote_cnt_6}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_7}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_7}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_7}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_7}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_7 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_7 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_7}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_7}&visit_cnt=${vo.visit_cnt_7}&remote_cnt=${vo.remote_cnt_7}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_8}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_8}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_8}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_8}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_8 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_8 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_8}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_8}&visit_cnt=${vo.visit_cnt_8}&remote_cnt=${vo.remote_cnt_8}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_9}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_9}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_9}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_9}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_9 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_9 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_9}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_9}&visit_cnt=${vo.visit_cnt_9}&remote_cnt=${vo.remote_cnt_9}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_10}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_10}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_10}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_10}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_10 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_10 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_10}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_10}&visit_cnt=${vo.visit_cnt_10}&remote_cnt=${vo.remote_cnt_10}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_11}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_11}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_11}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_11}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_11 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_11 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_11}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_11}&visit_cnt=${vo.visit_cnt_11}&remote_cnt=${vo.remote_cnt_11}', 'width=270, height=250')">
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
							<td class="dataField" width="40px"><del>${vo.visit_cnt_12}</del></td>
							<td class="dataField" width="40px"><del>${vo.remote_cnt_12}</del></td>
						</c:when>
						<c:otherwise>
							<td class="dataField" width="40px">${vo.visit_cnt_12}</td>
							<td class="dataField" width="40px">${vo.remote_cnt_12}</td>
						</c:otherwise>
					</c:choose>
					<td class="dataField" width="70px">
						<c:choose>
							<c:when test="${vo.job_nm_12 == ''}">
								-
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${vo.job_yn_12 == 'N'}">
										<input type="button" value="입력" onclick="openPop('../popup/updateComplete.jsp?name=${vo.job_nm_12}&con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_12}&visit_cnt=${vo.visit_cnt_12}&remote_cnt=${vo.remote_cnt_12}', 'width=270, height=250')">
									</c:when>
									<c:when test="${vo.job_yn_12 == 'Y'}">
										<a href="#" onclick="openPop('../popup/showComplete.jsp?con_id=${vo.con_id}&con_year=${vo.con_year}&year=${curYear}&date=${vo.job_date_12}', 'width=300, height=410')">${vo.job_nm_12}</a>
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<c:set var="index1" value="${index1 + 1}"/>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<input type="hidden" id="checkValue" value="">
	<input type="hidden" id="checkValue1" value="">
</body>
</html>