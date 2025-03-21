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
<style type="text/css">
	.formLabel{
		display:inline-block;
		width:120px;
		text-align:right;
	}
</style>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/deline.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		document.getElementById("email_pw").addEventListener("keydown", pwKeyDownEventHandler);
	});
	
	function modify() {
		var form = document.getElementById("writeform");
		
		if (form.nce_host.value == "") {
			alert("<spring:message code='config.emailhostinput'/>");
			form.nce_host.focus();
			return false;
		}
		if(form.nce_host_type.value==1){
		var reg_host = /^[a-zA-Z0-9.]{1,64}$/; //1~64자 영대소문자, 숫자, 특수문자 . 사용가능
			if (!reg_host.test(form.nce_host.value)){
				alert("<spring:message code='config.emailhost64'/>");
				return false;
			}
		}else{
			var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
			if (!check.test(form.nce_host.value)){
				alert("메일 서버 IP가 IP 주소 형식이 아닙니다.");
				return;
			}
		}
		
		var check_port = /^\d+$/;
		if (form.nce_port.value == "") {
			alert("<spring:message code='config.emailportinput'/>");
			form.nce_port.focus();
			return false;
		}
		if (!check_port.test(form.nce_port.value)) {
			alert("<spring:message code='config.emailportnot'/>");			
			return false;
		}
		if(form.nce_port.value>65535) {
			alert("<spring:message code='config.emailportrange'/>");
			return false;
		}
		
		if (form.nce_id.value == "") {
			alert("<spring:message code='config.emailidinput'/>");
			form.nce_id.focus();
			return false;
		}
		var reg_id = /^[a-zA-Z0-9]{1,64}$/; //1~64자 영대소문자, 숫자 사용가능
		if(!reg_id.test(form.nce_id.value)){
			alert("<spring:message code='config.emailid64'/>");
			return;
		}
		
// 		if (form.nce_pw.value == "") {
// 			alert("<spring:message code='config.emailpwinput'/>");
// 			form.nce_pw.focus();
// 			return false;
// 		}
// 		var reg_pw = /^[a-zA-Z0-9\!\@\#\$\%\^\*\+\=\-\?\_\-\~\&\`]{1,128}$/; //1~64자 영대소문자, 숫자, 특수문자 사용가능
		if (getByte(form.nce_pw.value) > 127){
			alert("<spring:message code='config.emailpw128'/>");
			return false;
		}
		
		if (form.nce_from_email.value == "") {
			alert("<spring:message code='config.fromemailinput'/>");
			form.nce_from_email.focus();
			return false;
		}
		if(getByte(form.nce_from_email.value)>192){
			alert("<spring:message code='config.fromemail192'/>");
			return false;
		}
		var regExp=/^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/gm;
		if (!regExp.test(form.nce_from_email.value)){
			alert("<spring:message code='user.emailinvalid'/>");
			form.nce_from_email.focus();
			return false;
		}
		
		if (confirm("<spring:message code='alert.edit'/>") == true) {
			var form = document.getElementById("writeform");
			
			//RSA 암호화 생성
			var rsa = new RSAKey();
			
			rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
			
			document.writeformSubmit.nce_use_yn.value = form.nce_use_yn.value == "on" ? 1 : 0;
			document.writeformSubmit.nce_host_type.value = form.nce_host_type.value;
			document.writeformSubmit.nce_host.value = form.nce_host.value;
			document.writeformSubmit.nce_port.value = form.nce_port.value;
			document.writeformSubmit.nce_id.value = form.nce_id.value;
			document.writeformSubmit.nce_pw.value = rsa.encrypt(form.nce_pw.value);
			document.writeformSubmit.nce_from_email.value = form.nce_from_email.value;
			
			form.nce_pw.value=null;
			
			var formData = $("form[id=writeformSubmit]").serialize();
			
			$.ajax({
				type : "POST",  
				url : "modify_email.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.changed'/>");
						location.replace('/config_email.do');
					}else if(data == "not_ip"){
						alert("메일 서버 IP가 IP 주소 형식이 아닙니다.");
					}else if(data == "not_domain"){
						alert("<spring:message code='config.emailhost64'/>");
					}else if(data == "port_wrong"){
						alert("<spring:message code='config.emailportrange'/>");
					}else if(data == "email_wrong"){
						alert("<spring:message code='user.emailinvalid'/>");
					}else if(data == "email_idwrong"){
						alert("<spring:message code='config.emailid64'/>");
					}else if(data == "email_pwwrong"){
						alert("<spring:message code='config.emailpw128'/>");
					}else if (data == "KEY Error") {
		               alert("<spring:message code='error.key'/>");
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
	
	function change_use_yn(use_yn){
		use_yn ^= 1;
		$.ajax({
			type : "POST",
			url : "mail_use_yn.do",
			data : "nce_use_yn=" + use_yn,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data == "true"){
					location.replace('/config_email.do');
				}else if(data == "under"){
					alert("<spring:message code='accessip.mustone'/>");
					location.replace('/config_email.do');
				}else{
					alert(data);
				}
			},
			error: function(){
				alert('<spring:message code='alert.errormodify'/>');
			}
		});
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
				<div class="content-form" style="width:520px;">
					<div class="form-wrapper">
						<sf:form method="post" id="writeformSubmit" name="writeformSubmit" onsubmit="return false;">
							<input type="hidden" name="nce_use_yn"/>
							<input type="hidden" name="nce_host_type"/>
							<input type="hidden" name="nce_host"/>
							<input type="hidden" name="nce_port"/>
							<input type="hidden" name="nce_id"/>
							<input type="hidden" name="nce_pw"/>
							<input type="hidden" name="nce_from_email"/>
						</sf:form>
						<sf:form id="writeform" modelAttribute="config_email_data">
							<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
							<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
							<ul>
								<li>
									<span class="text-main-color2 formLabel">
										<spring:message code='config.email_server'/>
										<spring:message code='common.useyn'/>
									</span>
									<div class="switch">
										<input type="checkbox" name="nce_use_yn" 
											onclick="change_use_yn(${config_email_data.nce_use_yn});"
											<c:choose>
												<c:when test="${config_email_data.nce_use_yn == 1}">class="checked" checked</c:when>
												<c:otherwise>class="unchecked"</c:otherwise>
											</c:choose>>
										<label class="slider" onclick="change_use_yn(${config_email_data.nce_use_yn});"></label>
									</div>
								</li>
								<li>
									<span class="text-main-color2 formLabel"><spring:message code='config.emailhost'/></span>
									<sf:select path="nce_host_type" onChange="document.getElementById('nce_host').value='';" style="position: relative;left: 0px;width:85px; padding-right: 0px;">
										<option value="1" <c:if test="${config_email_data.nce_host_type==1}">selected</c:if>>
											<spring:message code='config.domain'/>
										</option>
										<option value="2" <c:if test="${config_email_data.nce_host_type==2}">selected</c:if>>
											IP
										</option>
									</sf:select>
									<sf:input type="text" path="nce_host" style="width:152px;" />
								</li>
								<li id="li-mail-port" lang="${pageContext.response.locale}">
									<span class="text-main-color2 formLabel"><spring:message code='config.emailport'/></span>
									<sf:input type="text" path="nce_port" style="width:240px;" />
								</li>
								<li id="li-mail-id" lang="${pageContext.response.locale}">
									<span class="text-main-color2 formLabel">
										<spring:message code='config.emailid'/>
									</span>
									<sf:input type="text" path="nce_id" style="width:240px;" />
								</li>
								<li id="li-mail-pw" lang="${pageContext.response.locale}" style="width:500px;">
									<span class="text-main-color2 formLabel">
										<spring:message code='config.emailpw'/>
									</span>
									<sf:input id="email_pw" type="password" path="nce_pw" style="width:240px;font-family: caption;"/>
									<script type="text/javascript">
										if("${pageContext.response.locale}" == "en_US" ){
											$("#email_pw").attr("placeholder", "※ It will change as you enter it. ※");
										}
										else{
											$("#email_pw").attr("placeholder", "※ 입력하시면 변경됩니다. ※");
										}
									</script>
								</li>
								<li id="li-sender-email" lang="${pageContext.response.locale}"  style="width:500px;">
									<span class="text-main-color2 formLabel"><spring:message code='config.fromemail'/></span>
									<sf:input type="text" path="nce_from_email" style="width:240px;" />
								</li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:location.replace('/config_email.do');" id="gray-bottom-btn" style="margin-right:12px;"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
							<a href="javascript:modify();" id="white-bottom-btn"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
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