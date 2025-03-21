<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="nnsp.common.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- start csrf -->
<%@ include file="../include/csrf.jsp"%>
<!-- csrf end -->
<style>
	#manager .dropdown-content ul li a:hover{color: #fff;}
	#manager .dropdown-content ul li a{color:rgba(255,255,255,0.6);}
</style>

<script type="text/javascript">
$(document).ready(function(){
	function preload(logo_src) {
		let logo = new Image();
		logo.src = logo_src;
	}

	preload(
	  "./img/logo_nNetCDS_GuardV2_top.png"
	)
	
// 	preload(
// 	  "./img/logo_nNetCDSv2.0CSG_top.png"
// 	)

// 	preload(
// 	  "./img/logo_nNetTrustV2.0_rtop.png"
// 	)

// 	preload(
// 	  "./img/logo_nNetDiodeV3.0_top.png"
// 	)

// 	preload(
// 	  "./img/logo_nNetTG_top.png"
// 	)

// 	preload(
// 	  "./img/logo_nNetTAG_top.png"
// 	)
	
	printTime();
	animate_layer_open();
	setInterval("animate_layer_open()", 60000); // 1분 후에 함수 호출
	
	$("#logo").on("click", function() {
        window.location.href = "stat_traffic.do";
    });
});
<%
	session.setAttribute("SessionCheckTime", System.currentTimeMillis());//  세션 체크 시간 현재시간으로 초기화
%>
var maxInactive = <%=session.getMaxInactiveInterval()%>;
var cnt=1;
function printTime() {
	$.ajax({
		type : "POST",
		url : "session_check.do",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data==="0"){
				document.location.href='/logout.do';
			}
			else if(data.length > 20){
				if(data.indexOf("<spring:message code='login.samepemision'/>") > -1){
					alert("<spring:message code='login.samepemision'/>");
					location.href='/login.do';
				}
				else{
					document.location.href='/main.do';
				}
			}
			else{
				rest.innerHTML = data;
				cnt++;
			}
		},
		error: function(){}
	});
	
	
// 	if(maxInactive<=cnt) {
		//document.location.href='/logout.do';
// 	}else{
// 		var min = parseInt((maxInactive-cnt) / 60);
// 		var sec = parseInt((maxInactive-cnt) % 60);
// 		if(min>0){
// 			rest.innerHTML = min+"<spring:message code='common.minute'/> "+sec+"<spring:message code='common.second'/>";
// 		}else{
// 			rest.innerHTML = sec+"<spring:message code='common.second'/>";
// 		}
// 		cnt++;
		
// 		$.ajax({
// 			type : "POST",
// 			url : "session_check.do",
// 			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
// 			success: function(data) {
// 				if(data == "false"){
// 					document.location.href='/login.do?message=login_duplicate';
// 				}
// 				rest.innerHTML = rest.innerHTML+data;
// 			},
// 			error: function(){}
// 		});
// 	}
}
var timeInterval = setInterval(printTime, 1000); // 1초 후에 함수 호출

// 시간 연장
function extend() {
	location.reload();
}
</script>
<script type="text/javascript">
<!--
document.onkeydown = function(e) {
	var evtK = (e) ? e.which : window.event.keyCode;
	var isCtrl = ((typeof isCtrl != 'undefined' && isCtrl) || ((e && evtK == 17) || (!e && event.ctrlKey))) ? true : false;
	
	if ((isCtrl && evtK == 82) || evtK == 116) {
		if (e) { evtK = 505; } else { event.keyCode = evtK = 505; }
	}
	if (evtK == 505) {
		location.reload();
		return false;
	}
}
//-->  
</script>
<div class="top-bg"></div>
<header>
	<!-- start nav -->
	<nav>
		<div class="nav-left">
			<!-- logo start -->
			<div id="logo" style="padding: 10px 34px; cursor: pointer;">
				<!-- start logo -->
				<img src="/img/logo_nNetCDS_GuardV2_top.png">
<!-- 				<img src="/img/logo_nNetCDSv2.0CSG_top.png"> -->
<!-- 				<img src="/img/logo_nNetTrustV2.0_rtop.png"> -->
<!-- 				<img src="/img/logo_nNetDiodeV3.0_top.png"> -->
<!-- 				<img src="/img/logo_nNetTG_top.png"> -->
<!-- 				<img src="/img/logo_nNetTAG_top.png"> -->
			</div>
			<!-- logo end -->
			<!-- nav start -->
			${top_menu}
			<!-- nav end -->
		</div>
		<!-- nav right start -->
		<div id="nav-right">
			<div id="time">
				<img src="/img/clock.png" class="clock"><span class="text-small text-top-color2 text-shadow-light head-font"><spring:message code='common.time'/> : </span><span class="text-small text-top-color2 text-shadow-light head-font"><span id="rest" class="rest"></span></span>
				<a href="javascript:extend();" id="header-btn" class="head-font"><spring:message code='common.extension'/></a>
				<img src="/img/header-line.png" class="header-line">
			</div>
			
			<div id="header-icon2">
				<div class="dropbtn">
					<img src="/img/bell.png"><div class="alert-num"><span id="alarm_cnt"></span></div>
				</div>
				<div class="dropdown-content head-font" style='cursor:default;'>
					<ul>
						<div id="alarm1"></div>
						<div id="alarm2"></div>
						<div id="alarm3"></div>
					</ul>
				</div>
			</div>
			<img src="/img/header-line.png" class="header-line">
			<div id="manager">
				<div class="manager-wrapper">
					<div class="dropbtn"><span class="text-small text-top-color2 text-shadow-light head-font">${sessionScope.loginUser} <spring:message code='common.sir'/></span><img style="margin:9px 0 0 12px;" src="/img/arrow-bottom.png"></div>
					<div class="dropdown-content">
						<ul>
							<li><a href="/user_list.do?edit_flag=y"><spring:message code='common.editmyinfo'/></a></li>
							<%-- <li><a href="javascript:window.open('/html/user_manual.html','_new','width=1024,height=768,toolbar=no,menubar=no,location=no,scrollbars=yes');void(0);" class="head-font"><spring:message code='common.help'/></a></li> --%>
							<li><a href="javascript:void(0)" onclick="$(window).unbind('unload');clearInterval(timeInterval);document.location.href='/logout.do';" class="head-font"><spring:message code='common.logout'/></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- nav right end -->
	</nav>
</header>
<script>
	// 로그알람 삭제
	function del_log(div){
		if (confirm("<spring:message code='common.wantdelalarm'/>") == true) {
	    	$.ajax({
				type : "POST",
				url : "alarm_delete.do",
				data : "noa_type=L",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						animate_layer_open();
					}else{
						alert(data);
					}
				},
				error: function(){
					alert('삭제 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	// 무결성 알람 삭제
	function del_inte(div){
		if (confirm("<spring:message code='common.wantdelalarm'/>") == true) {
	    	$.ajax({
				type : "POST",
				url : "alarm_delete.do",
				data : "noa_type=I",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						animate_layer_open();
					}else{
						alert(data);
					}
				},
				error: function(){
					alert('삭제 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	//로그인 알람 삭제
	function del_login(div){
		if (confirm("<spring:message code='common.wantdelalarm'/>") == true) {
	    	$.ajax({
				type : "POST",
				url : "alarm_delete.do",
				data : "noa_type=R",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						animate_layer_open();
					}else{
						alert(data);
					}
				},
				error: function(){
					alert('삭제 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	// 알람창 열기
	function animate_layer_open(){
		$.ajax({
			type : "POST",
			url : "alarm.do",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				var response = data.split("|");
				
				$("#alarm_cnt").html(response[0]); // 알람 갯수
				
				$("#alarm1").html(""); // 초기화
				$("#alarm2").html(""); // 초기화
				$("#alarm3").html(""); // 초기화
				
				if(response[0] == "0"){ // 알람 내용이 없을 때
					$("#alarm1").html("<li><span><spring:message code='common.noalarm'/></span></li>");
				}else if(response[0] == "1"){ // 오픈할 알람창이 하나일 때
					var temp = "<li><span><span style='color:red;'>"+response[2]+"</span>";
					if(response[1]=='I'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(1);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[1]=='L'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(1);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(1);' style='cursor:pointer;top:15px;'></span>";
					}
					temp += "<br>"+response[3]+"</span></li>";
					$("#alarm1").html(temp);
				}else if(response[0] == "2"){ // 오픈할 알람창이 두개일 때
					var temp = "<li><span><span style='color:red;'>"+response[2]+"</span>";
					if(response[1]=='I'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(1);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[1]=='L'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(1);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(1);' style='cursor:pointer;top:15px;'></span>";
					}
					temp += "<br>"+response[3]+"</span></li>";
					$("#alarm1").html(temp);
					
					var temp1 = "<li><span><span style='color:red;'>"+response[5]+"</span>";
					if(response[4]=='I'){
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(2);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[4]=='L'){
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(2);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(2);' style='cursor:pointer;top:15px;'></span>";
					}
					temp1 += "<br>"+response[6]+"</span></li>";
					$("#alarm2").html(temp1);
					
				
				}else if(response[0] == "3"){ // 오픈할 알람창이 세개일 때
					var temp = "<li><span><span style='color:red;'>"+response[2]+"</span>";
					if(response[1]=='I'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(1);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[1]=='L'){
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(1);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(1);' style='cursor:pointer;top:15px;'></span>";
					}
					temp += "<br>"+response[3]+"</span></li>";
					$("#alarm1").html(temp);
					
					var temp1 = "<li><span><span style='color:red;'>"+response[5]+"</span>";
					if(response[4]=='I'){
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(2);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[4]=='L'){
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(2);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp1 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(2);' style='cursor:pointer;top:15px;'></span>";
					}
					temp1 += "<br>"+response[6]+"</span></li>";
					$("#alarm2").html(temp1);
					
					
					var temp2 = "<li><span><span style='color:red;'>"+response[8]+"</span>";
					if(response[7]=='I'){
						temp2 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_inte(3);' style='cursor:pointer;top:15px;'></span>";
					}else if(response[7]=='L'){
						temp2 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_log(3);' style='cursor:pointer;top:15px;'></span>";
					}else{
						temp2 += "<span class='fa fa-trash-o pull-right' onClick='javascript:del_login(3);' style='cursor:pointer;top:15px;'></span>";
					}
					temp2 += "<br>"+response[9]+"</span></li>";
					$("#alarm3").html(temp2);
				
				}
			},
			error: function(){}
		});
	}
</script>