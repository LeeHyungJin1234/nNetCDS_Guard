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
<script type="text/javascript" src="/js/jquery.min.js"></script>
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
							location.replace('/config_rcv_network.do');
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
    
    function inputCheck() {
      const form = document.getElementById("system_info");
      const maxLength = 256;
      
      if (form.ncs_master_nic.value.length > maxLength ||
          form.ncs_slave_nic.value.length > maxLength) {
        alert("<spring:message code='config.nic256'/>");
        return false;
      }
      if (!form.ncs_master_nic.value) {
        alert("<spring:message code='config.masternicinput'/>");
        form.ncs_master_nic.focus();
        return false;
      }
      if (!form.ncs_slave_nic.value) {
        alert("<spring:message code='config.slavenicinput'/>");
        form.ncs_slvae_nic.focus();
        return false;
      }
      return true;
    }
    
    function update_system_info() {
      const check = inputCheck();

      if (check === false)
        return ;

      if (confirm("<spring:message code='config.wantupdatesysinfo'/>") == true) {
        $.ajax({
          type : "POST",
          url : "system_update.do",
          data :  $("#system_info").serialize(),
          contentType : "application/x-www-form-urlencoded; charset=UTF-8",
          success: function(data) {
            if(data == "true"){
              alert("<spring:message code='alert.registered'/>");
              location.replace('/config_rcv_network.do');
            }
          },
          error: function(){
            alert('<spring:message code='alert.errorupdate'/>');
          }
        });
      }
    }

    
    function svc_restart(){
    	if (confirm("<spring:message code='config.wantrestart'/>") == true) {
	    	$.ajax({        
				type : "POST",  
				url : "sys_rcv_restart.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "success"){
						alert("<spring:message code='config.restartexec'/>");
						location.replace('/config_rcv_network.do');
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
					data : "ncs_remote_flag="+flag+"&ncs_div=2",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remoteexec'/>");
							location.replace('/config_rcv_network.do');
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
					data : "ncs_remote_flag="+flag+"&ncs_div=2",
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "success"){
							alert("<spring:message code='config.remotesvcstop'/>");
							location.replace('/config_rcv_network.do');
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
				<div class="content-form" style="width:456px;">
					<div class="form-wrapper">
						<sf:form id="system_info" modelAttribute="systemToRegit">
							<sf:hidden path="ncs_div" value="${receiveList.ncs_div}"/> <!-- 송수신 구분 -->
							<ul>
								<li>
									<span class="text-main-color2"> Master NIC</span>
									<sf:input type="text" path="ncs_master_nic" value="${receiveList.ncs_master_nic}" style="width:360px;" disabled="true"/>
								</li>
								<li>
									<span class="text-main-color2"> Slave NIC</span>
									<sf:input type="text" path="ncs_slave_nic" value="${receiveList.ncs_slave_nic}" style="width:360px;" disabled="true"/>
								</li>
								<li style="width:400px;">
									<span class="text-main-color2" style="margin-right: 12px !important;">
										<spring:message code='config.componentinfo'/>
									</span>
									<span style="margin-left:10px;">
										${receiveList.ncs_version}
									</span>
								</li>
								<li>
									<span class="text-main-color2" style="margin-right: 12px !important;">
										<spring:message code='config.firmwareinfo'/>
									</span>
									<span style="margin-left:10px;margin-right: 0px !important;">
										${receiveList.ncs_version.replace(" ", "_")}.adi
									</span>
								</li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:svc_restart();" id="red-bottom-btn" style="margin-right:12px;">
								<img src="/img/rebooting.png" style="margin:4px 8px 0 0;">
								<spring:message code='config.restartsvc'/>
							</a>
							<c:if test="${sessionScope.loginUserId eq 'tech'}">
								<c:choose>
									<c:when test="${receiveList.ncs_remote_flag==1}">
										<a href="javascript:remote_update('0');" id="white-bottom-btn" style="margin-top:12px">
											<spring:message code='config.remoteshutdown'/>
										</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:remote_update('1');" id="white-bottom-btn" style="margin-top:12px">
											<spring:message code='config.remoteexecution'/>
										</a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
					</div>
				</div>
				<!-- contents end -->
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>