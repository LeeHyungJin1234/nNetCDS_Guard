<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta charset="UTF-8">
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">
<!-- font -->
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
<%@ include file="/WEB-INF/views/include/css/reset_css.jsp"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<%@ include file="/WEB-INF/views/include/css/style_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/setting_css.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
    function writeCheck() {
    	var check = inputCheck();
		if( check ){
			if (confirm("<spring:message code='alert.register'/>") == true) {
		    	var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "system_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/config_intrcv_network.do');
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('등록 작업 중 에러가 발생하였습니다.');
					}
				});
			}
    	}
    }
    
    function inputCheck(){
    	var form = document.getElementById("writeform");  
    	var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
    	var check1 = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;

    	/*if( !form.ncs_master_ip.value ) {
			alert("Master IP를 입력하세요.");
			form.ncs_master_ip.focus();
			return false;
		}
    	if (!check.test(form.ncs_master_ip.value)) {
			alert("Master IP가 유효한 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( form.ncs_master_ip.value=='127.0.0.1') {
			alert("Master IP에 루프백 주소(127.0.0.1)를 입력할 수 없습니다.");
			return false;
		}*/
    	if( !form.ncs_master_mac.value ) {
			alert("<spring:message code='config.mastermacinput'/>");
			form.ncs_master_mac.focus();
			return false;
		}
    	var check_mac = /^([0-9a-fA-F][0-9a-fA-F]-){5}([0-9a-fA-F][0-9a-fA-F])$/i;
    	if (!check_mac.test(form.ncs_master_mac.value)) {
			alert("<spring:message code='config.mastermacincorrect'/>");
			return false;
		}
    	/*if( !form.ncs_slave_ip.value ) {
			alert("Slave IP를 입력하세요.");
			form.ncs_slave_ip.focus();
			return false;
		}
    	if (!check.test(form.ncs_slave_ip.value)) {
			alert("Slave IP가 유효한 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( form.ncs_slave_ip.value=='127.0.0.1') {
			alert("Slave IP에 루프백 주소(127.0.0.1)를 입력할 수 없습니다.");
			return false;
		}*/
    	if( !form.ncs_slave_mac.value ) {
			alert("<spring:message code='config.slavemacinput'/>");
			form.ncs_slave_mac.focus();
			return false;
		}
    	if (!check_mac.test(form.ncs_slave_mac.value)) {
			alert("<spring:message code='config.slavemacincorrect'/>");
			return false;
		}
    	
		return true;
    }
    
    function update_system() {
    	var check = inputCheck();
		if( check ){
			if (confirm("<spring:message code='config.wantmacrestart'/>") == true) {
		    	var formData = $("form[id=writeform]").serialize();
	
				$.ajax({        
					type : "POST",  
					url : "system_update.do",
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/config_intrcv_network.do');
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('수정 작업 중 에러가 발생하였습니다.');
					}
				});
			}
    	}
	}
    
    function svc_restart(){
    	if (confirm("<spring:message code='config.wantrestart'/>") == true) {
	    	$.ajax({        
				type : "POST",  
				url : "sys_intrcv_restart.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "success"){
						alert("<spring:message code='config.restartexec'/>");
						location.replace('/config_intrcv_network.do');
					}else if (data == "fail") {
						alert("<spring:message code='config.restartfail'/>");
					}
				},
				error: function(){
					alert('서비스 재시작 중 에러가 발생하였습니다.');
				}
			});
		}	
    }
    
    function remote_update(flag){
    	if(flag==1){
	    	if (confirm("<spring:message code='config.wantstartremote'/>") == true) {
		    	$.ajax({        
					type : "POST",  
					url : "sys_remote_update.do",
					data : "ncs_remote_flag="+flag+"&ncs_div=4",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remoteexec'/>");
							location.replace('/config_intrcv_network.do');
						}else if (data == "fail") {
							alert("<spring:message code='config.remotestartfail'/>");
						}
					},
					error: function(){
						alert('원격 서비스 실행 중 에러가 발생하였습니다.');
					}
				});
			}
    	}else{
    		if (confirm("<spring:message code='config.wantstopremote'/>") == true) {
		    	$.ajax({        
					type : "POST",  
					url : "sys_remote_update.do",
					data : "ncs_remote_flag="+flag+"&ncs_div=4",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remotesvcstop'/>");
							location.replace('/config_intrcv_network.do');
						}else if (data == "fail") {
							alert("<spring:message code='config.remotestopfail'/>");
						}
					},
					error: function(){
						alert('원격 서비스 중지 중 에러가 발생하였습니다.');
					}
				});
			}
    	}
    }
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	
	<!-- left menu start -->
	<section id="content">
		<%@ include file="../include/left.jsp"%>

		<div id="right-content">
			<div class="right-wrapper">
				<%@ include file="../include/tabmenu.jsp"%>

				<!-- contents start -->
				<div class="content-form" style="width:371px;">
					<div class="form-wrapper">
						<sf:form id="writeform" modelAttribute="systemToRegit" method="POST" action="/system_regit.do">
							<ul>
								<li><span class="text-main-color2" style="margin-left:20px;">Master IP</span><input type="text" value="${receiveList.ncs_master_ip}" style="width:182px;" disabled></li>
								<li><span class="text-main-color2">Master MAC</span><sf:input path="ncs_master_mac" value="${receiveList.ncs_master_mac}" style="width:182px;" /></li>
								<li><span class="text-main-color2" style="margin-left:31px;">Slave IP</span><input type="text" value="${receiveList.ncs_slave_ip}" style="width:182px;" disabled></li>
								<li><span class="text-main-color2" style="margin-left:11px;">Slave MAC</span><sf:input path="ncs_slave_mac" value="${receiveList.ncs_slave_mac}" style="width:182px;" /></li>
								<li><span class="text-main-color2" style="margin-left:25px;">버전정보</span><span style="margin-left:10px;">${receiveList.ncs_version}</span></li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:svc_restart();" id="red-bottom-btn" style="margin-right:12px;"><img src="/img/rebooting.png" style="margin:4px 8px 0 0;">재시작 실행</a>
							<a href="javascript:update_system();" id="white-bottom-btn"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
							<c:if test="${sessionScope.loginUserId eq 'tech'}">
								<c:choose>
								<c:when test="${receiveList.ncs_remote_flag==1}">
									<a href="javascript:remote_update('0');" id="white-bottom-btn" style="margin-top:12px">원격 종료</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:remote_update('1');" id="white-bottom-btn" style="margin-top:12px">원격 실행</a>
								</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</div>
				</div>
				<div class="orange"><span style="position:relative;">* </span><span><spring:message code='config.nwsetdesc'/></span></div>
				<div class="orange" style="margin-top:0;"><span style="position:relative;">* </span><span><spring:message code='config.chagerestart'/></span></div>
				<!-- contents end -->
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>