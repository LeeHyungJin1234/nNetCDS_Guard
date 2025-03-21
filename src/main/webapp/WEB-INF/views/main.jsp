<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNet-CDS Manager</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.12.1.min.js"></script>
</head>
<script type="text/javascript">
	function resizeContainers() {
		var headerHeight = $("#header").height(),
			navbarWrapper = $("#example-nav-bar").height(),
            htmlHeight = $('html').height();
		navHeight = htmlHeight - (headerHeight + navbarWrapper);
        $("#nav-wrapper").height(navHeight - 2);
        $("#main").height(navHeight + navbarWrapper);
    }

    $(document).ready(resizeContainers);
    $(window).resize(resizeContainers);
</script>
<script type="text/javascript">
	window.onload = function() {
		getRealtime_log();
		setInterval("getRealtime_log();", 60000); // 1분 간격
	}
	
	// 서비스 프로그램 시작/중지
	function setDeamon(status){
		$.ajax({        
			type : "POST",  
			url : "modify_deamon_status.do",   
			data : "ncp_status="+status,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) {  
				if (data == "true") {
					location.replace('/main.do');
				} else {
					alert(data);
				}
			},
			error: function(){
				alert("<spring:message code='alert.requesterror'/>");
			}
		});
	}
	
	function alertfunc(){
		alert("<spring:message code='common.prepareservice'/>");
	}
</script>
<body>
	<!-- top -->
	<div id="header">
	    <div id="logo-bar">
			<img src="/images/logo2.gif" style="width:138px" />
	    </div>
	    <div class="nav-buttons">
			<span style="font-size:13px;color:#ee5315;font-weight:bold;">${sessionScope.loginUser} </span><font style="font-size:13px;color:#E7E7E7"><spring:message code='common.sir'/> </font>
			<a href="javascript:void(0)" onclick="$(window).unbind('unload'); document.location.href='/logout.do';" class="btn"><spring:message code='common.logout'/></a>
			<a href="/user_editVw.do" class="btn"><spring:message code='common.mypage'/></a>
	        <a href="javascript:alertfunc();" class="btn btn-primary"></a>
	    </div>
	</div>
	<div id="example-nav-bar">
	    <a href="#" id="back-forward"><spring:message code='common.allmenu'/></a>
	</div>
	<div id="nav-wrapper">
		<div id="root-nav">${total_menu}</div>
	</div>
	<!-- top -->
	
	<div id="main">
		
		<sec:authorize access="hasAuthority('ROLE_ADMIN')">
			<h1 id="exampleTitle">
				<strong><spring:message code='common.svcprogram'/></strong>
				<c:choose>
					<c:when test="${svc_deamon.ncp_status == 1}">
						<a href="javascript:setDeamon(2);" class="btn_st06"><span style="font-size:13px"><spring:message code='common.stop'/></span></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:setDeamon(1);" class="btn_st09"><span style="font-size:13px"><spring:message code='common.start'/></span></a>
					</c:otherwise>
				</c:choose>
			</h1>
		</sec:authorize>
		
	    <h1 id="exampleTitle"><strong><spring:message code='common.rtlog'/></strong></h1>
	    <div id="exampleWrap">
	    	
	    	<div class="bbsList">
	    		<script type="text/javascript">
					function getRealtime_log(){
						$.ajax({        
							type : "POST",  
							url : "realtime_log.do", 
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
							success: function(data) { 
								if(data != null)  {
									document.getElementById('realtime_log_table').innerHTML = data;
								}    
							}  
						});
					 }
				</script>
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="10%" />
						<col width="12%" />
						<col width="10%" />
						<col width="10%" />
						<col width="14%" />
						<col width="10%" />
						<col width="10%" />
					</colgroup>
					<thead>
					<tr>
						<th><spring:message code='common.mngID'/></th>
						<th><spring:message code='common.logcreateday'/></th>
						<th><spring:message code='common.connectIP'/></th>
						<th><spring:message code='common.workcontent'/></th>
						<th><spring:message code='common.parameter'/></th>
						<th><spring:message code='common.workresult'/></th>
						<th><spring:message code='common.danger'/></th>
					</tr>
					</thead>
				</table>
				<div id='realtime_log_table' style="overflow-y:scroll; height:250px;"></div>
			</div>
			 
	    </div>
	
		<%@ include file="include/footer.jsp"%>
	
	</div>

</body>
</html>