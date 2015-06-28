function loadReplyNumber() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("replyNumber").innerHTML = xmlhttp.responseText + "人";
		} //if..readyState and status
	} //function()
	
	xmlhttp.open("GET", "missingRecord.do?action=getReplyNumber&recordId=" + document.getElementById("recordId").value, true);
	xmlhttp.send();
} //function loadReplyNumber()

var s = 6;
var status
function countDown(statusStr) {
	s -= 1;
	var startResult = "<font color='deeppink'><h4>";
	var betweenResult = "秒後即將為您開啟";
	var endResult = "頁面</h4></font>";
	status = statusStr;
	
	if (s == 0 && status == "登入") {
		location.replace("../member/login.jsp");
	} else if (s == 0 && status == "信箱驗證") {
		location.replace("../member/emailvalidate_failure.jsp");
	} else if (s == 0 && status == "手機驗證") {
		location.replace("../member/phoneValidate_step1.jsp");
	}else {
		document.getElementById("showCountDown").innerHTML = startResult + s + betweenResult + status + endResult;
	}
	
	setTimeout("countDown(status)", 1000);
}

function showResult(jsonStr) {
	var result = JSON.parse(jsonStr);
	var str;
	
	if (result.errorMsgs != null) {		
		str = "<font color='deeppink'>溫馨提醒:<ul>";
		if (result.errorMsgs.memId != null) {
			str += "<li>" + result.errorMsgs.memId + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
			countDown("登入");
		}
		if (result.errorMsgs.email != null) {
			str += "<li>" + result.errorMsgs.email + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
			countDown("信箱驗證");
		}
		if (result.errorMsgs.phone != null) {
			str += "<li>" + result.errorMsgs.phone + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
			countDown("手機驗證");
		}
		if (result.errorMsgs.deny != null) {
			str += "<li>" + result.errorMsgs.deny + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
		}			
		if (result.errorMsgs.illegalReply != null) {
			str += "<li>" + result.errorMsgs.illegalReply + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
		}			
		if (result.errorMsgs.duplicateReply != null) {
			str += "<li>" + result.errorMsgs.duplicateReply + "</li>";
			document.getElementById("comments").disabled = true;
			document.getElementById("insertButton").disabled = true;
		}			
		if (result.errorMsgs.comments != null)
			str += "<li>" + result.errorMsgs.comments + "</li>";
		str += "</ul></font>"; 
	} else {
		document.getElementById("comments").disabled = true;
		document.getElementById("insertButton").disabled = true;
		str = "<font color='deeppink'>溫馨提醒:<ul>";
		str += "<li>" + result.success + "</li>";
		str += "</ul></font>";
	}
	
	document.getElementById("showResult").innerHTML = str;
} //function showResult(jsonStr)

function insertReply() {
	var xhr;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			showResult(xhr.responseText);
		} // if..readyState and status
	} //function()
	
	var url = "../missingReply/missingReply.do";
	var queryString = "action=insertReply&memId=" + document.getElementById("memId").value + 
								"&recordId=" + document.getElementById("recordId").value + 
								"&comments=" + document.getElementById("comments").value;
	xhr.open("Post", url, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(queryString);
} //function insertReply()