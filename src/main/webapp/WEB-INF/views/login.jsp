<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='common.title'/></title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
<%@ include file="/WEB-INF/views/include/css/nprogress_css.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/custom.min.css">
<%@ include file="/WEB-INF/views/include/css/login_css.jsp"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

<!-- start csrf -->
<%@ include file="/WEB-INF/views/include/csrf.jsp"%>
<!-- csrf end -->

<script type="text/javascript">
function validate() {
	var frm = document.loginForm;
	var id_check = /^[a-zA-Z0-9]*$/;
	
	if( frm.username.value == "" ) {
    	alert("<spring:message code='login.idinput'/>");
        frm.username.focus(); 
        return false;
    }
	if(!id_check.test(frm.username.value)){
		alert("<spring:message code='login.idcheck'/>");
		return false;
	}
    if( frm.password.value =="" ) {
    	alert("<spring:message code='login.pwinput'/>");
        frm.password.focus();
        return false;
    }
    
    //RSA 암호화 생성
	var rsa = new RSAKey();
	rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
	
	var subfrm = document.loginSubmit;
	subfrm.username.value = rsa.encrypt(frm.username.value);
	subfrm.password.value = rsa.encrypt(frm.password.value);
    subfrm.action = "<c:url value='login_check.do'/>";

    frm.password.value=null;
    frm.password.value=null;
    frm.password.value=null;
    frm.password=null;
    frm.password=null;
    frm.password=null;
    
   	subfrm.submit();
}
/*
* Enter(Return) Key control
*/
function keyPress(){
	var ieKey = window.event.keyCode;
	if (ieKey == 13 && window.event.srcElement.type=="text") {
		if(window.event.srcElement.name == "username") {
			document.loginForm.password.focus();
		}
	} else if (ieKey == 13 && window.event.srcElement.type == "password") {
		if(window.event.srcElement.name == "password") {
			void(validate()); //validateForm
		}
	}
}
</script>
<script type="text/javascript">
	function pwmodify(){
		var id_check = /^[a-zA-Z0-9]*$/;
 		var id_flag;
 		var form = document.getElementById("passwdform");
 		
 		if(form.orig_passwd.value.length < 1){
			alert("<spring:message code='login.curpwinput'/>");
   			form.orig_passwd.focus();
   			return;
  		}
 		if(form.new_id.value.length < 1){
   			alert("<spring:message code='login.newidinput'/>");
   			form.new_id.focus();
   			return;
   		}else{
			id_flag=id_verification(form.new_id.value);
			if(id_flag==1){
				alert("<spring:message code='login.sameid'/>");
				return;
			}
		}
 		if(!id_check.test(form.new_id.value)){
			alert("<spring:message code='login.newidcheck'/>");
			return;
		}
   		if(form.new_passwd.value.length < 1){
			alert("<spring:message code='login.newpwinput'/>");
   			form.new_passwd.focus();
   			return;
  		}
  		if(form.new_passwd_confirm.value.length < 1){
   			alert("<spring:message code='login.newpwverifinput'/>");
   			form.new_passwd_confirm.focus();
   			return;
   		}
  		if(form.orig_passwd.value == form.new_passwd.value){
   			alert("<spring:message code='login.newcursame'/>");
  	 		return;
  		}
  		if(form.new_passwd.value.length < 9){
   			alert("<spring:message code='login.pwleast9'/>");
  	 		form.new_passwd.focus();
   			return;
  		}
  		if(form.new_passwd.value.length > 127){
   			alert("<spring:message code='login.pwmost127'/>");
  	 		form.new_passwd.focus();
   			return;
  		}
  		var check = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$/;
  		if (!check.test(form.new_passwd.value)){
			alert("<spring:message code='login.pwcombi'/>");
			return;
		}
  		if(form.new_passwd.value != form.new_passwd_confirm.value){
   			alert("<spring:message code='login.pwnotmatch'/>");
  	 		return;
  		}
  		
		
  		if(id_flag == 0){
  			
			if (confirm("<spring:message code='alert.change'/>")) {
				var form = document.getElementById("passwdform");
				//RSA 암호화 생성
				var rsa = new RSAKey();
				rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
				
				document.passwdSubmit.orig_passwd.value = rsa.encrypt(form.orig_passwd.value);
				document.passwdSubmit.new_id.value = rsa.encrypt(form.new_id.value);
				document.passwdSubmit.new_passwd.value = rsa.encrypt(form.new_passwd.value);
				document.passwdSubmit.new_passwd_confirm.value = rsa.encrypt(form.new_passwd_confirm.value);
				
				var formData = $("form[id=passwdSubmit]").serialize();
				$.ajax({        
					type : "POST",  
					url : "passwd_change.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='login.loginagain'/>");
							location.replace('/logout.do');
						}else if(data=="not_enough_length"){
							alert("<spring:message code='login.least9more127'/>");
						}else if(data=="not_origpw_length"){
							alert("<spring:message code='login.curpwinput'/>");
						}else if(data=="not_id_length"){
							alert("<spring:message code='login.newidinput'/>");
						}else if(data=="not_newpw_length"){
							alert("<spring:message code='login.newpwinput'/>");
						}else if(data=="not pw_confirm"){
							alert("<spring:message code='login.newpwverifinput'/>");
						}else if(data=="not_same_pw"){
							alert("<spring:message code='login.newcursame'/>");
						}else if(data=="not_pw9"){
							alert("<spring:message code='login.pwleast9'/>");
						}else if(data=="not_pw127"){
							alert("<spring:message code='login.pwmost127'/>");
						}else if(data=="not_match_passwd"){
							alert("<spring:message code='login.curpwnotmatch'/>");
						}else if(data=="not_rule"){
							alert("<spring:message code='login.pwcombi'/>");
						}else if(data=="not_enough_length"){
							alert("<spring:message code='login.least9more127'/>");
						}else if(data=="not_same"){
							alert("<spring:message code='login.consecutive4pwd'/>");
						}else if(data=="not_continuity"){
							alert("<spring:message code='login.consecutive4pwd'/>");
						}else if(data=="not_qwer"){
							alert("<spring:message code='login.consecutive3pwd'/>");
						}else{
							alert(data);
						}
					},
					error: function(){
						alert("<spring:message code='login.changeerror'/>");
					}
				});
				
			}
  		}
	}

	function id_verification(id){
		var flag=0;
		$.ajax({
			type : "POST",
			url : "id_check.do",
			async : false,
			data : "nsu_id="+id,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(data){
				if(data=="present"){
					//Present same ID's
					flag=1;
				}
			},
			error : function(){
				alert("<spring:message code='login.errorID'/>");			
			}
		});
		return flag;		
	}
	
	$(function () {
		setTimeout(function () {
			location.replace("/login.do");
		}, <%= session.getMaxInactiveInterval() * 1000 - 5000 %>);
	    
		document.getElementById("password").addEventListener("keydown", pwKeyDownEventHandler);
	});
</script>
</head>
<body>
	<c:choose>
		<c:when test='${param.message == "login_error"}'>
 			<script>alert("<spring:message code='login.loginfail'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "notaccess_ip"}'>
			<script>alert("<spring:message code='login.notallowip'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "unused_account"}'>
			<script>alert("<spring:message code='login.unusedaccount'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "id_lock"}'>
			<script>alert("<spring:message code='login.lockid'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "login_duplicate"}'>
			<script>alert("<spring:message code='login.samepemision'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "lock_attempt"}'>
			<script>alert("<spring:message code='log.loginfaillockedaccount'/>"); location.href='/login.do';</script>
		</c:when>
		<c:when test='${param.message == "notfind_key"}'>
			<!-- script>alert("로그인 정보 암호화 전송에 실패하였습니다. 다시 시도해주세요.");</script-->
			<script>alert("<spring:message code='login.notfind_key'/>"); location.href='/login.do';</script>
		</c:when>
	</c:choose>
	
	<div class="login_cont">
		<div class="login_left">
			<div class="login_bg">
				<div class="login_bg_logo">
					<div class="login_bg_logo_cont">
						<div class="logo_cont">
							<img src="/img/logo_nNetCDS_GuardV2_h.png" alt="nNetCDS Guard v2.0" />
<!-- 							<img src="/images/logo_nNetTrustV2.0_rh.png" alt="nNetTrust v2.0" /> -->
<!-- 							<img src="/images/logo_nNetDiodev3.0_h.png" alt="nNetDiode v3.0" /> -->
<!-- 							<img src="/images/logo_nNetTG_h.png" alt="nNetTG v1.0" /> -->
<!-- 								<img src="/img/logo_nNetTAG_h.png" alt="nNetTAG v1.0" /> -->
							<div class="logo_txt">
								<c:choose>
									<c:when test="${pageContext.response.locale.language eq 'ko' && pageContext.response.locale.country eq 'KR'}">제품버전: V2.0.1<br/></c:when>
									<c:when test="${pageContext.response.locale.language eq 'ko'}">제품버전: V2.0.1<br/></c:when>
									<c:otherwise>Product Version: V2.0.1<br/></c:otherwise>
								</c:choose>
								Copyright NNSP Co. Ltd. All rights reserved.
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="login_right">
			<div class="login_frm">
				<div class="login_frm_cont">
					<div class="login_frm_logo">
						<img src="/img/logo_nNetCDS_GuardV2_v.png" alt="nNetCDS Guard v2.0" />
<!-- 						<img src="/images/logo_nNetCSG_login.png" alt="nNetCDS v2.0" /> -->
<!-- 						<img src="/img/logo_nNetTAG_login.png" alt="nNetTAG v1.0" /> -->
					</div>
					<section>
					<sf:form name="loginForm">
						<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
						<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
						<div class="login_frm_id"><input type="text" class="frm_login" name="username" id="login" onkeydown="javascript:if(event.keyCode == 13){keyPress(); return false;}" autocomplete="off" maxlength="64" placeholder="User ID" /></div>
						<div class="login_frm_pw"><input type="password" class="frm_login" name="password" id="password" onkeydown="javascript:if(event.keyCode == 13){keyPress(); return false;}" maxlength="127" autocomplete="off" placeholder="Password" /></div>
						<div class="login_frm_submit" onclick="javascript:validate();"><i class="fa fa-lock"></i>  Login</div>
					</sf:form>
					<sf:form method="post" name="loginSubmit" onsubmit="return false;">
						<input type="hidden" name="username"/>
						<input type="hidden" name="password"/>
					</sf:form>
					</section>
				</div>
				<div class="login_com_logo"><img src="/images/logo.png" alt="NNSP" /></div>
			</div>
		</div>
	</div>
  	 	
</body>
</html>