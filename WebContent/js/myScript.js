function openPop(url, option) {
	var popUrl = url;
	var popOption = option;
	window.open(popUrl, "", popOption);
}

function selectMemberName(th) {
	var curYear = document.getElementById("curYear").value;
	
	if(curYear == "") 
		top.location.href="../../main.jsp?memberName=" + th;
	else
		top.location.href="../../main.jsp?curYear=" + curYear + "&memberName=" + th;
		
}
	
function selectYear(th) {
	var memberName = document.getElementById("memberName").value;
	
	if(memberName == "")
		top.location.href="../../main.jsp?curYear=" + th;
	else
		top.location.href="../../main.jsp?curYear=" + th + "&memberName=" + memberName;
	
}

function insertCheckAndSubmit(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var from = document.getElementById("from").value;
	var to = document.getElementById("to").value;
	var condition = document.getElementById("condition").value;
	var inspector = document.getElementById("inspector").value;
	var inspector1 = document.getElementById("inspector1").value;
	var date = document.getElementById("date").value;
	if(condition == "") {
		alert("점검조건을 입력하세요.");
		return;
	}
	if(inspector == "") {
		alert("(정)담당자를 입력하세요.");
		return;
	}
	if(inspector1 == "") {
		alert("(부)담당자를 입력하세요.");
		return;
	}
	if(date == "") {
		alert("점검일자를 입력하세요.");
		return;
	}
	
	location.href = url + "?con_id=" + con_id + "&con_year=" + con_year + "&condition=" + condition + "&inspector=" + inspector + "&inspector1=" + inspector1 + "&date=" + date;
}

// 전체 일정 수정
function updateCheckAll(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var name = document.getElementById("inspector").value;
	var check_nm = document.getElementById("check_nm").value;
	var subName = document.getElementById("inspector1").value;
	var date = document.getElementById("date").value;
	var job_reason = document.getElementById("job_reason").value;
	
	if(name == "") {
		alert("담당자를 입력하세요.");
		return;
	}
	if(date == "") {
		alert("점검일자를 입력하세요.");
		return;
	}
	if(job_reason == "") {
		alert("수정 사유를 입력하세요");
		return;
	}
	
	url += "?con_id=" + con_id + "&con_year=" + con_year + "&name=" + name + "&check_nm=" + check_nm + "&subName= " + subName + "&date=" + date + "&job_reason=" + job_reason;
	location.href = url;
}


// 선택 날짜만 수정
function updateCheckMonth(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var job_nm = document.getElementById("inspector").value;
	var job_date = document.getElementById("date").value;
	var check_nm = document.getElementById("check_nm").value;
	var job_reason = document.getElementById("job_reason").value;
	var originDate = document.getElementById("originDate").value;
	
	if(check_nm == "") {
		alert("점검 조건을 입력하세요.");
		return;
	}
	if(job_nm == "") {
		alert("담당자를 입력하세요.");
		return;
	}
	if(originDate == "") {
		alert("기존 점검일자를 입력하세요.");
		return;
	}
	if(job_date == "") {
		alert("점검일자를 입력하세요.");
		return;
	}
	if(job_reason == "") {
		alert("수정사유를 입력하세요.");
		return;
	}
	var originYear = originDate.substr(0, 4);
	var originMonth = originDate.substr(5, 2);
	var originDay = originDate.substr(8);
	var year = job_date.substr(0, 4);
	var month = job_date.substr(5, 2);
	var day = job_date.substr(8);
	var check = confirm(originYear + "년 " + originMonth + "월 " + originDay + "일의 점검일을\n" + year + "년 " + month + "월 " + day + "일로 바꾸시겠습니까?");
	if(check == true) 
		location.href = url + "?con_id=" + con_id + "&con_year=" + con_year + "&job_nm=" + job_nm + "&job_date=" + job_date + "&job_reason=" + job_reason + "&check_nm=" + check_nm + "&originDate=" + originDate;
	else
		return;
}

function eventSubmit(url) {
	alert(url);
}

function eventReset() {
	close();
}

function changeSelect(id, count, seq) {
	var targetId = document.getElementById(id);
	var item = targetId.title;
	var secondId = id.substr(0,2) + "m" + id.substr(2);
	var monthTargetId = document.getElementById(secondId);
	
	document.getElementById("checkValue").setAttribute("value", item);
	document.getElementById("checkValue1").setAttribute("value", seq);
	
	for(var i = 1; i <= count; i++) {
		document.getElementById(id.substr(0,2) + i).setAttribute("bgcolor", "white");
		document.getElementById(secondId.substr(0,3) + i).setAttribute("bgcolor", "white");
	}
	targetId.setAttribute("bgcolor", "#FFFFCC");
	monthTargetId.setAttribute("bgcolor", "#FFFFCC");
}

function openUrl(url, option) {
	var param = top.infoListTable.document.getElementById("checkValue").value;
	var param1 = top.infoListTable.document.getElementById("checkValue1").value;
	if(param=="") {
		alert("선택된 데이터가 없습니다.");
		return;
	} else {
		url = url + "?con_id=" + param + "&con_year=" + param1;
		window.open(url, "", option);
	}
}
function checkupCompleteWithReason(url) {
	var id = document.getElementById("con_id").value;
	var seq = document.getElementById("con_year").value;
	var name = document.getElementById("inspector").value;
	var date = document.getElementById("date").value;
	var originDate = document.getElementById("originDate").value;
	var check_nm = document.getElementById("check_nm").value;
	var job_reason = document.getElementById("job_reason").value;
	
	if(job_reason == "") {
		alert("수정사유를 입력하세요.");
		return;
	}
	url += "?id=" + id + "&seq=" + seq + "&name=" + name + "&date=" + date + "&originDate=" + originDate + "&check_nm=" + check_nm + "&job_reason=" + job_reason;  
	location.href=url;
}

function checkupComplete(url) {
	var id = document.getElementById("con_id").value;
	var seq = document.getElementById("con_year").value;
	var name = document.getElementById("inspector").value;
	var date = document.getElementById("date").value;
	var originDate = document.getElementById("originDate").value;
	var check_nm = document.getElementById("check_nm").value;
	if(name == "") {
		alert("점검자를 입력하세요.");
		return;
	}
	if(date == "") {
		alert("점검 날짜를 입력하세요.");
		return;
	}
	if(check_nm == "") {
		alert("점검 조건을 입력하세요.");
		return;
	}
	if(originDate != date) {
		var check = confirm("점검 날짜가 다릅니다. 수정사유를 입력하시겠습니까?");
		if(check) {
			open("updateCompleteWithReason.jsp?id=" + id + "&seq=" + seq + "&name=" + name + "&date=" + date + "&originDate=" + originDate + "&check_nm=" + check_nm, "", "width=300, height=220")
			return;
		}
		return;
	}
	url += "?id=" + id + "&seq=" + seq + "&name=" + name + "&date=" + date + "&originDate=" + originDate + "&check_nm=" + check_nm;  
	location.href=url;
}

function deletePlan(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var job_date = document.getElementById("originDate").value;
	var check = confirm("해당 점검 일정을 삭제하겠습니까?");
	
	url += "?con_id=" + con_id + "&con_year=" + con_year + "&job_date=" + job_date;
	
	if(check)
		location.href = url;
	else return;
}

function makeCheckMonth(month) {
	document.getElementById("checkMonth").value = month;
	alert(document.getElementById("checkMonth").value);
}

function makeCheckDay(day) {
	document.getElementById("checkDay").value = day;
	alert(document.getElementById("checkDay").value);
}

function openReWrite(id, seq, date, name, job_reason, job_visit_remote) {
	location.href="completeUpdate.jsp?con_id=" + id + "&con_year=" + seq + "&date=" + date + "&name=" + name + "&job_reason=" + job_reason + "&job_visit_remote=" + job_visit_remote;
}

function checkOperName(val) {
	var main_oper = document.getElementById("main_oper").value;
	if(main_oper != val) {
		alert("담당와 점검자가 다릅니다.\n담당자 : " + main_oper + "\n점검자 : " + val);
		window.close();
	}
}

function completeUpdate(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var originDate = document.getElementById("originDate").value;
	var date = document.getElementById("date").value;
	var changedReason = document.getElementById("reason").value;
	var originReason = document.getElementById("originReason").value;
	var job_nm = document.getElementById("inspector").value;
	var job_reason = originReason + "\n" + changedReason;
	var check_nm = document.getElementById("check_nm").value;
	
	if(check_nm == "") {
		alert("점검조건을 입력하세요.");
		return;
	}
	
	location.href=url + "?con_id=" + con_id + "&con_year=" + con_year + "&originDate=" + originDate + "&date=" + date + "&job_nm=" + job_nm + "&job_reason=" + job_reason + "&check_nm=" + check_nm;
}

function rollbackDate(url) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	var originReason = document.getElementById("originReason").value;
	var newReason = document.getElementById("reason").value;
	location.href = url + "?con_id=" + con_id + "&con_year=" + con_year + "&originReason=" + originReason + "&newReason=" + newReason;
}

function selectCondition(val) {
	document.getElementById("check_nm").value = val;
}

function selectRadio(val) {
	var con_id = document.getElementById("con_id").value;
	var con_year = document.getElementById("con_year").value;
	location.href = "updateData.jsp?con_id=" + con_id + "&con_year=" + con_year + "&type=" + val;
}

function searchEvent() {
	var searchColumn = document.getElementById("searchColumn").value;
	var searchText = document.getElementById("searchText").value;
	var curYear = document.getElementById("yearSelect").value;
	
	if(searchColumn == "") {
		location.href="checkupHistory.jsp?curYear=" + curYear;
	} else if(searchColumn != "" && searchText != "") {
		location.href="checkupHistory.jsp?curYear=" + curYear + "&searchColumn=" + searchColumn + "&searchText=" + searchText;
	}
}

function getJira() {
	var check = confirm("JIRA DB에서 데이터를 업데이트 하시겠습니까?");
	if(check) {
		window.open("../../controller/getJira.jsp", "", "width=445, height=380");
	}
}