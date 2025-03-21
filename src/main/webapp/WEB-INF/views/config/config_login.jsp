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
<%@ include file="/WEB-INF/views/include/css/config_login.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	function modify() {    	
		var form = document.getElementById("writeform"); 
		var check = /^\d+$/;
		
		if (!check.test(form.ncli_lock_failcnt.value)) {
			alert("<spring:message code='config.locknuminorrect'/>");
			return;
		}
		
		if (form.ncli_lock_failcnt.value<2 || form.ncli_lock_failcnt.value>5) {
			alert("<spring:message code='config.locknumrange'/>");
			return;
		}
		
		if (!check.test(form.ncli_lock_date.value)) {
			alert("<spring:message code='config.locktimeinorrect'/>");
			return;
		}
		
		if (form.ncli_lock_date.value<5 || form.ncli_lock_date.value>30) {
			alert("<spring:message code='config.locktimerange'/>");
			return;
		}
		
		if (confirm("<spring:message code='alert.edit'/>") == true) {
			var formData = $("form[id=writeform]").serialize();
			$.ajax({        
				type : "POST",  
				url : "modify_login.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.changed'/>");
						location.replace('/config_login.do');
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
				<div class="content-form" style="width:350px;">
					<div class="form-wrapper">
						<sf:form id="writeform" modelAttribute="config_login_data">
							<ul>
								<li><span class="li_span"><spring:message code='config.lockfailcount'/></span><sf:input type="text" path="ncli_lock_failcnt" placeholder="2~5 사이 숫자만 입력" style="width:225px;" /></li>
								<li ><span  class="li_span"><spring:message code='config.locktime'/></span><sf:input type="text" path="ncli_lock_date" placeholder="5~30 사이 숫자만 입력" style="width:225px;" /></li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:location.replace('/config_login.do');" id="gray-bottom-btn" style="margin-right:12px;"><img src="/img/cancel.png" style="margin:5px 10px 0 0;">취소하기</a>
							<a href="javascript:modify();" id="white-bottom-btn"><img src="/img/modify.png" style="margin:3px 10px 0 0;">수정하기</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>