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
    function svc_restart(){
    	if (confirm("<spring:message code='config.wantrestart'/>") == true) {
	    	$.ajax({        
				type : "POST",  
				url : "sys_extsnd_restart.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "success"){
						alert("<spring:message code='config.restartexec'/>");
						location.replace('/config_extsnd_network.do');
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
					data : "ncs_remote_flag="+flag+"&ncs_div=3",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remoteexec'/>");
							location.replace('/config_extsnd_network.do');
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
					data : "ncs_remote_flag="+flag+"&ncs_div=3",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remotesvcstop'/>");
							location.replace('/config_extsnd_network.do');
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
				<div class="content-form" style="width:346px;">
					<div class="form-wrapper">
						<form>
							<ul>
								<li><span class="text-main-color2" style="margin-left:-2px;">Master IP</span><input type="text" value="${sendList.ncs_master_ip}" style="width:182px;" disabled></li>
								<li><span class="text-main-color2" style="margin-left:9px;">Slave IP</span><input type="text" value="${sendList.ncs_slave_ip}" style="width:182px;" disabled></li>
								<li><span class="text-main-color2" style="margin-left:6px;">버전정보</span><span style="margin-left:10px;">${sendList.ncs_version}</span></li>
							</ul>
						</form>
						<div class="btn-section">
							<a href="javascript:svc_restart();" id="red-bottom-btn" style="margin-right:12px;"><img src="/img/rebooting.png" style="margin:4px 8px 0 0;">재시작 실행</a>
							<c:if test="${sessionScope.loginUserId eq 'tech'}">
								<c:choose>
								<c:when test="${sendList.ncs_remote_flag==1}">
									<a href="javascript:remote_update('0');" id="white-bottom-btn">원격 종료</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:remote_update('1');" id="white-bottom-btn">원격 실행</a>
								</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</div>
				</div>
				<div class="orange"><span style="position:relative;">* </span><span><spring:message code='config.nwsetdesc'/></span></div>
				<!-- contents end -->
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>