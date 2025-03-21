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
<%@ include file="/WEB-INF/views/include/css/dooray_login_css.jsp"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

<!-- start csrf -->
<%@ include file="/WEB-INF/views/include/csrf.jsp"%>
<!-- csrf end -->

<script type="text/javascript">
function validate() {
	var frm = document.doorayLoginForm;
	var id_check = /^[a-zA-Z0-9]*$/;
	
	if( frm.usr_account.value == "" ) {
    	alert("<spring:message code='login.idinput'/>");
        frm.usr_account.focus(); 
        return false;
    }
	if(!id_check.test(frm.usr_account.value)){
		alert("<spring:message code='login.idcheck'/>");
		return false;
	}
    if( frm.usr_pwd.value =="" ) {
    	alert("<spring:message code='login.pwinput'/>");
        frm.usr_pwd.focus();
        return false;
    }
        
	//RSA 암호화 생성
	var rsa = new RSAKey();
	rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
	
	var subfrm = document.loginSubmit;
	subfrm.usr_account.value = rsa.encrypt(frm.usr_account.value);
	subfrm.usr_pwd.value = rsa.encrypt(frm.usr_pwd.value);
    subfrm.action = "<c:url value='/dooray_login_check.do'/>";

    frm.usr_pwd.value=null;
    frm.usr_pwd.value=null;
    frm.usr_pwd.value=null;
    frm.usr_pwd=null;
    frm.usr_pwd=null;
    frm.usr_pwd=null;
    
   	subfrm.submit();
}
/*
* Enter(Return) Key control
*/
function keyPress(){
	var ieKey = window.event.keyCode;
	if (ieKey == 13 && window.event.srcElement.type=="text") {
		if(window.event.srcElement.name == "usr_account") {
			document.doorayLoginForm.usr_pwd.focus();
		}
	} else if (ieKey == 13 && window.event.srcElement.type == "password") {
		if(window.event.srcElement.name == "usr_pwd") {
			void(validate()); //validateForm
		}
	}
}
window.onload = function(){
    var obj = document.getElementById("login"); 
    obj.focus(); obj.select();
}

function id_verification(id){
	var flag = 99;
	$.ajax({
		type : "POST",
		url : "dooray_id_check.do",
		async : false,
		data : {
			usr_account: id,
        },
        datatype: "json",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(data){
			if(data=="present"){
				//Present same ID's
				flag=1;
			}
			else if(data=="not_allow"){
				flag=2;
			}
			else{
				flag=0;
			}
		},
		error : function(){
			alert("<spring:message code='login.errorID'/>");		
		}
	});
	return flag;		
}

function pw_verification(id, pw, pw_confirm){
	var flag = 99;
	
	 //RSA 암호화 생성
	var rsa = new RSAKey();
	rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
		
    if(getByte(pw) > 127){
    	flag = 2; 
    	return flag;
    }else{    		
    	pw = rsa.encrypt(pw);
	}
	if( getByte(pw_confirm) > 127){
    	flag = 2; 
    	return flag;
	}else{
		pw_confirm = rsa.encrypt(pw_confirm);
    }
	 $.ajax({
        type: "POST",
        url: "dooray_pw_check.do",
        async: false,
        data: {
        	new_id: id,
        	usr_pwd: pw,
            usr_pw_confirm: pw_confirm
        },
        datatype: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
			if (data == "not_rule") {
			  // Not enough password rule
			  flag = 1;
			} else if (data == "not_enough_length") {
			  // Not enough password length
			  flag = 2;
			} else if (data == "not_same_confirm") {
			  flag = 3;
			} else if (data == "not_rule") {
			  flag = 4;
			} else if (data ==="not_continuity") {
			  flag = 5;
			} else if (data == "not_qwer") {
			  flag = 6;
			} else if (data == "not_id_pw") {
			  flag = 7;
			} else if (data == "rsa_error") {
			  flag = 8;
			} else{
				flag = 0;
			}
        },
        error: function () {
        	alert("<spring:message code='user.errorpw'/>");
    	},
    	complete : function () {
    		pw=null;
    		pw=null;
    		pw=null;
    		pw_confirm=null;
    		pw_confirm=null;
    		pw_confirm=null;
    	}
    });

	return flag;	
}

// Registered text check
function register_check() {    	
	var id_flag;
	var pw_flag;
	var seculevel_flag;
	var id_check = /^[a-zA-Z0-9]*$/;
	var form = document.getElementById("passwdform"); 
	
	if(form.new_id.value==""){
		alert("<spring:message code='user.idinput'/>");
		form.new_id.focus();
		return;
	}else{
		id_flag=id_verification(form.new_id.value);
		if(id_flag==1){
			alert("<spring:message code='login.sameid'/>");
			return;
		}else if(id_flag>0){
			alert("<spring:message code='user.notuseid'/>");
			return;
		}
	}
	if(!id_check.test(form.new_id.value)){
		alert("<spring:message code='user.idnumalpha'/>");
		return;
	}
	if(getByte(form.new_id.value)>64){
		alert("<spring:message code='user.idmin6max64'/>");
		return;
	}
	if (form.new_passwd.value == "" || form.new_passwd_confirm.value == "") {
    	alert("<spring:message code='login.pwinput'/>");
        return;
      } else {
        pw_flag = pw_verification(form.new_id.value, form.new_passwd.value, form.new_passwd_confirm.value);
        if (pw_flag == 1) {
          alert("<spring:message code='login.pwcombi'/>");
          return;
        } else if (pw_flag == 2) {
          alert("<spring:message code='user.pwleast9more127'/>");
          return;
        } else if (pw_flag == 3) {
          alert("<spring:message code='login.pwnotmatch'/>");
            return;
        } else if (pw_flag == 4) {
          alert("<spring:message code='login.pwcombi'/>");
          return;
        } else if (pw_flag == 5) {
          alert("<spring:message code='login.pwsamecont'/>");	
          return;
        } else if (pw_flag == 6) {
          alert("<spring:message code='login.consecutive3pwd'/>");
          return;
        } else if (pw_flag == 7) {
            alert("<spring:message code='user.idpw'/>");
              return;
        } else if (pw_flag == 8) {
             alert("RSA Error");
             return;
        }
      }
	
	if(id_flag == 0 && pw_flag == 0 ){
		if (confirm("<spring:message code='alert.register'/>") == true) {
		    //RSA 암호화 생성
			var rsa = new RSAKey();
			rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);

			var subpfrm = document.passwdSubmit;
			subpfrm.new_id.value = rsa.encrypt(form.new_id.value);
			subpfrm.new_passwd.value = rsa.encrypt(form.new_passwd.value);
			subpfrm.new_passwd_confirm.value = rsa.encrypt(form.new_passwd_confirm.value);
			subpfrm.usr_srcip.value = form.usr_srcip.value;
			subpfrm.usr_url.value = form.usr_url.value;
			subpfrm.usr_dstip.value = form.usr_dstip.value;

			form.new_passwd.value="";
			form.new_passwd.value="";
			form.new_passwd.value="";
			form.new_passwd_confirm.value="";
			form.new_passwd_confirm.value="";
			form.new_passwd_confirm.value="";
			
			var formData = $("form[id=passwdSubmit]").serialize();
			$.ajax({
				type : "POST",  
				url : "dooray_insert_user.do",
				data :  formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.registered'/>\n<spring:message code='clientUsr.instaftermsg'/>");
						location.replace('/dooray_login.do');
			        } else if (data === "present") {
					  alert("<spring:message code='login.sameid'/>");
					} else if (data === "not_allow") {
					  alert("<spring:message code='user.notuseid'/>");
					} else if (data === "not_enough_length") {
					  alert("<spring:message code='user.pwleast9more127'/>");
					} else if (data === "not_pw_confirm") {
					  alert("<spring:message code='user.pwinputconfirm'/>");
					} else if (data === "not_id_length") {
					  alert("<spring:message code='user.idlengthfrom6to64'/>");
					} else if (data === "not_name_length64") {
					  alert("<spring:message code='user.nameexceed'/>");
					} else if (data == "not_rule") {
					  alert("<spring:message code='user.pwin4'/>");
					} else if (data === "not_continuity") {
					  alert("<spring:message code='login.pwsamecont'/>");
					} else if (data === "not_qwer") {
					  alert("<spring:message code='login.consecutive3pwd'/>");
					} else if (data === "not_id_pw") {
					  alert("<spring:message code='user.idpw'/>");
					} else {
					  alert(data);
					}
				},
				error: function(){
					alert('등록 작업 중 에러가 발생하였습니다.');
				},
		    	complete : function () {
					subfrm.new_id.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd_confirm.value="";
					subfrm.new_passwd_confirm.value="";
					subfrm.new_passwd_confirm.value="";
					subpfrm.usr_srcip.value="";
					subpfrm.usr_url.value="";
					subpfrm.usr_dstip.value="";
		    	}
			});
		}
	}
}
</script>
</head>
<body>
	<c:choose>
		<c:when test='${param.message == "login_error"}'>
			<script>alert("<spring:message code='login.loginfail'/>");</script>
		</c:when>
		<c:when test='${param.message == "notfind_key"}'>
			<!-- script>alert("로그인 정보 암호화 전송에 실패하였습니다. 다시 시도해주세요.");</script-->
			<script>alert("<spring:message code='login.loginfail'/>");</script>
		</c:when>
	</c:choose>
	<div class="login_cont">
			<div class="login_frm">
				<div class="login_frm_cont">
					<div class="login_frm_logo"><img src="/images/logo_nNetCDSV2.0_v.png" /><img src="/images/logo_nNetCSG_login2.png" alt="nNetCDS v2.0" /></div>
					<section>
						<sf:form name="doorayLoginForm">
							<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
							<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
							<div class="login_frm_id"><input type="text" class="frm_login" name="usr_account" id="login" onkeydown="javascript:if(event.keyCode == 13){keyPress(); return false;}" autocomplete="off" maxlength="64" placeholder="User ID" /></div>
							<div class="login_frm_pw"><input type="password" class="frm_login" name="usr_pwd" id="password" onkeydown="javascript:if(event.keyCode == 13){keyPress(); return false;}" maxlength="127" autocomplete="off" placeholder="Password" /></div>
							<div class="login_frm_submit" onclick="javascript:validate();"><i class="fa fa-lock"></i>  Login</div>
						</sf:form>
						<sf:form method="post" name="loginSubmit" onsubmit="return false;">
							<input type="hidden" name="usr_account"/>
							<input type="hidden" name="usr_pwd"/>
						</sf:form>
					</section>
					<div class="usr_add"><a href="javascript:$('.bs-example-modal-lg').modal('show');">계정 등록</a></div>
					<div class="logo_txt">Copyright(c) NNSP Co. Ltd. All rights reserced.</div>
				</div>
			</div>
	</div>
	
	
  	<!-- 계정 및 비밀번호 등록 -->
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<sf:form method="post" id="passwdSubmit" name="passwdSubmit" onsubmit="return false;">
				<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}"/>
				<input type="hidden" name="new_id"/>
				<input type="hidden" name="new_passwd"/>
				<input type="hidden" name="new_passwd_confirm"/>
				<input type="hidden" name="usr_srcip"/>
				<input type="hidden" name="usr_url"/>
				<input type="hidden" name="usr_dstip"/>
			</sf:form>
			<sf:form id="passwdform" class="form-horizontal form-label-left">  
			<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}"/>
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="myModalLabel" style="color:#fff"><spring:message code='clientUsr.account'/> <spring:message code='btn.registration'/></h5>
					<button type="button" class="btn btn-default" onclick="javascript:$('.bs-example-modal-lg').modal('hide');"><span aria-hidden="true"><img src="/img/close.png"></span></button>
				</div>
				<div class="modal-body">
					<div class="item form-group">
						<h2><spring:message code='user.requiredinput'/></h2><p class="border_title"></p>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12">ID</label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" name="new_id" class="form-control col-md-7 col-xs-12" maxlength="64" autocomplete="off">
						</div>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='user.passwd'/></label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="password" name="new_passwd" class="form-control col-md-7 col-xs-12" maxlength="127" autocomplete="off">
						</div>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='user.passwdconfirm'/></label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="password" name="new_passwd_confirm" class="form-control col-md-7 col-xs-12" maxlength="127" autocomplete="off">
						</div>
					</div>
					<div class="item form-group">
						<h2><spring:message code='user.optionalinput'/></h2><p class="border_title"></p>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='log.sourceip'/></label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" name="usr_srcip" class="form-control col-md-7 col-xs-12" autocomplete="off">
						</div>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12">URL</label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" name="usr_url" class="form-control col-md-7 col-xs-12" autocomplete="off">
						</div>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='log.destnationip'/></label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" name="usr_dstip" class="form-control col-md-7 col-xs-12" autocomplete="off">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="javascript:$('.bs-example-modal-lg').modal('hide');"><i class="fa fa-close"></i> <spring:message code='btn.cancel'/></button>
					<button type="button" class="btn btn-danger" onClick="javascript:register_check();"><i class="fa fa-pencil"></i> <spring:message code='btn.registration2'/></button>
				</div>
			</div>
			</sf:form>
		</div>
	</div>
	<!-- /계정 및 비밀번호 등록 -->	
</body>
</html>