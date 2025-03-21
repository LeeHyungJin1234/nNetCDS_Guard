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
	function modify() {    	
		var form = document.getElementById("writeform"); 
		//var check_ip = /^(\d+)[.](\d+)[.](\d+)[.](\d+)$/ig;
		var check_lock = /^\d+$/;
		/*if (!check_ip.test(form.ncl_server_ip.value)) {
			alert("IP주소 형식이 아닙니다.");
			return;
		}*/
		if (!check_lock.test(form.ncl_cycle.value)) {
			alert("<spring:message code='config.disksetnot'/>");
			return;
		}
		
		if (form.ncl_cycle.value<70 || form.ncl_cycle.value>95) {
			alert("<spring:message code='config.disknuminput'/>");
			return;
		}
		
		if (confirm("<spring:message code='alert.edit'/>") == true) {
			var formData = $("form[id=writeform]").serialize();
			$.ajax({        
				type : "POST",  
				url : "modify_log.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.changed'/>");
						location.replace('/config_log.do');
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
				<div class="content-form" style="width:432px;">
					<div class="form-wrapper">
						<sf:form id="writeform" modelAttribute="config_log_data">
						<sf:input type="text" path="ncl_server_ip" style="display:none;" />
							<ul>
								<li><span><spring:message code='config.diskthreshold'/></span><sf:input type="text" path="ncl_cycle" placeholder="70~95 사이 숫자만 입력" style="width:225px;" /></li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:location.replace('/config_log.do');" id="gray-bottom-btn" style="margin-right:12px;"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
							<a href="javascript:modify();" id="white-bottom-btn"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
						</div>
					</div>
				</div>
				
				<div class="orange"><span style="position:relative;">* </span><span>남은 용량이 500MB 이하시에는 오래된 감시데이터가 삭제 후 기록 됩니다.</span></div>
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>