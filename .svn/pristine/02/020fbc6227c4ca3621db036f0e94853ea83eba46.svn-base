<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title><spring:message code='common.title'/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome.min.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/nprogress.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/custom.min.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/dialog.css"/>">
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
    $("header").remove();

    $(document).ready(function () {
    	if(typeof timeInterval !== 'undefined'){
    		clearInterval(timeInterval);  
        }
		if(typeof alarmInterval !== 'undefined'){
			clearInterval(alarmInterval);  
		}
      setTimeout(function () {
        location.replace("/login.do");
      }, <%= session.getMaxInactiveInterval() * 1000 - 5000 %>);

		document.getElementById("nce_pw").addEventListener("keydown", pwKeyDownEventHandler);
    });

    function modify() {
      var form = document.getElementById("form");

      if (form.nce_host.value == "") {
        alert("<spring:message code='config.emailhostinput'/>");
        form.nce_host.focus();
        return false;
      }
      if (form.nce_host_type.value == 1) {
        var reg_host = /^[a-zA-Z0-9.]{1,64}$/; //1~64자 영대소문자, 숫자, 특수문자 . 사용가능
        var reg_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        if (!reg_host.test(form.nce_host.value)) {
          alert("<spring:message code='config.emailhost64'/>");
          return false;
        }
        
        if(!reg_email.test(form.nce_host.value)){
        	alert("<spring:message code='user.emailinvalid'/>");
            return false;
        }
        
      } else {
        var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
        if (!check.test(form.nce_host.value)) {
          alert("<spring:message code='config.notipformat'/>");
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
      if (form.nce_port.value < 1 || form.nce_port.value > 65535) {
        alert("<spring:message code='config.emailportrange'/>");
        return false;
      }

      if (form.nce_id.value == "") {
        alert("<spring:message code='config.emailidinput'/>");
        form.nce_id.focus();
        return false;
      }
      var reg_id = /^[a-zA-Z0-9]{1,64}$/; //1~64자 영대소문자, 숫자 사용가능
      if (!reg_id.test(form.nce_id.value)) {
        alert("<spring:message code='config.emailid64'/>");
        return;
      }

      // 보안기능확인서 - 보안 문제로 패스워드 정보는 받아와서 *문자로도 표시하지 않는다.
      // 공백이면 변경하지 않음, 입력시에만 변경
      if (form.nce_pw.value == "") {
        alert("<spring:message code='config.emailpwinput'/>");
        form.nce_pw.focus();
        return false;
      }
      if (getByte(form.nce_pw.value) > 127) {
        alert("<spring:message code='config.emailpw128'/>");
        return false;
      }

      if (form.nce_from_email.value == "") {
        alert("<spring:message code='config.fromemailinput'/>");
        form.nce_from_email.focus();
        return false;
      }
      if (getByte(form.nce_from_email.value) > 192) {
        alert("<spring:message code='config.fromemail192'/>");
        return false;
      }
      var regExp = /^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/gm;
      if (!regExp.test(form.nce_from_email.value)) {
        alert("<spring:message code='user.emailinvalid'/>");
        form.nce_from_email.focus();
        return false;
      }
      var reg_email2 = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		if (!reg_email2.test(form.nce_from_email.value)){
			alert("<spring:message code='user.emailinvalid'/>");
			return;
		}

      if(confirm("<spring:message code='alert.register'/>") == true ){
      sendModify();
      }
    }

    function sendModify() {

		//RSA 암호화 생성
		var rsa = new RSAKey();
		rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
		document.submit_form.nce_pw.value = rsa.encrypt(document.form.nce_pw.value);

		document.submit_form.nce_host_type.value = document.form.nce_host_type.value;
		document.submit_form.nce_host.value = document.form.nce_host.value;
		document.submit_form.nce_port.value = document.form.nce_port.value;
		document.submit_form.nce_id.value = document.form.nce_id.value;
		document.submit_form.nce_from_email.value = document.form.nce_from_email.value;

		var formData = $("form[id=submit_form]").serializeArray();

      $.ajax({
        type: "POST",
        url: "modify_email.do",
        data: formData,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
          if (data == "true") {
            alert("<spring:message code='alert.mail.success'/>");
            document.location.href = '/logout.do';
          } else if (data == "not_ip") {
            alert("<spring:message code='config.notipformat'/>");
          } else if (data == "not_domain") {
            alert("<spring:message code='config.emailhost64'/>");
          } else if (data == "port_wrong") {
            alert("<spring:message code='config.emailportrange'/>");
          } else if (data == "email_wrong") {
            alert("<spring:message code='user.emailinvalid'/>");
          } else if (data == "email_idwrong") {
            alert("<spring:message code='config.emailid64'/>");
          } else if (data == "email_pwinput") {
            alert("<spring:message code='config.emailpwinput'/>");
          } else if (data == "email_pwwrong") {
            alert("<spring:message code='config.emailpw128'/>");
          } else {
        	  alert("<spring:message code='alert.initialization.error'/>");
        	  document.location.href='/login.do';
          }
        },
        error: function () {
        	alert("<spring:message code='alert.initialization.error'/>");
        	document.location.href='/login.do';
        }
      });
    }
  </script>
</head>
<style>
  body, html {
    background: #f7f7f7;
    height: 100%;
  }

  /*#prod {*/
  /*  position: relative;*/
  /*  text-align: center;*/
  /*  height: 100%;*/
  /*}*/

  #prodHeader {
    height: 178px;
    line-height: 178px;
    background-image: url('/images/top_bg.png');
    background-repeat: no-repeat;
    background-size: 100% 100%;
    text-align: center;
  }

  #prodFooter {
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    background: #fff;
    text-align: right;
    height: 50px;
    line-height: 50px;
    padding-right: 20px;
  }

  .form-select {
    height: 34px;
    font-size: 14px;
    float: left;
    vertical-align: middle;
    margin-right: 10px;
    padding-left: 10px;
    width: 102px;
    line-height: 1.42857143;
    color: #555;
    border: 1px solid #ccc;
    padding-left: 10px;
    background: #fff url(../../img/sort.png) no-repeat 94% 50%;
  }
  .item{display:inline-block;width:100%;}
  .item > label{text-align:right;} 
  
	#footer {margin:0 auto;border-top:1px solid;border-top-color:rgba(224,227,231,0.4);width:1920px;min-width: 100%;}
	#footer .footer-wrapper {margin:0 auto;width:1920px;}
	#footer .footer-wrapper span {float: right; padding: 14px 28px 14px 0;}
</style>
<body>
<div id="prod">
  <div id="prodHeader"><img src="<c:url value="/img/logo_nNetCDS_GuardV2_v.png"/>" alt="nNetCDS Guard V2.0"/></div>
  

  <div class="col-md-12 col-sm-12 col-xs-12"
       style="padding:25px 20px 10px 20px">
    <div class="x_panel">
      <div class="x_title">
        <h2>초기 메일 설정</h2>
        <div class="clearfix"></div>
      </div>

      <div class="x_content form-horizontal form-label-left">
      	
		<sf:form method="post" id="submit_form" name="submit_form" onsubmit="return false;">
			<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}"/>
			<input type="hidden" name="nce_host_type"/>
			<input type="hidden" name="nce_host"/>
			<input type="hidden" name="nce_port"/>
			<input type="hidden" name="nce_id"/>
			<input type="hidden" name="nce_pw"/>
			<input type="hidden" name="nce_from_email"/>
			
		</sf:form>
      
        <form id='form' name='form' method="post">
			<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}"/>
			<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
			<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='config.emailhost'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <select class="form-select" name="nce_host_type">
                <option value="1"><spring:message code='config.domain'/></option>
                <option value="2">IP</option>
              </select>
              <input type="text" class="form-control col-md-7 col-xs-12"
                     style="width: 780px;" id="nce_host" name="nce_host"/>
            </div>
          </div>
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='config.emailport'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="nce_port"/>
            </div>
          </div>
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='config.emailid'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="nce_id"/>
            </div>
          </div>

          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='config.emailpw'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="password" class="form-control col-md-7 col-xs-12"
                     id="nce_pw" name="nce_pw"/>
            </div>
          </div>

          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='config.fromemail'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="nce_from_email"/>
            </div>
          </div>

        </form>
        <div class="ln_solid"></div>
        <div class="form-group">
        	<div class="col-md-3"></div>
			<div class="col-md-6">
	            <button type="button" class="btn btn-danger" onClick="javascript:modify();" >
	              <i class="fa fa-pencil"></i>
	              등록
	            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
<%@ include file="include/footer.jsp"%>

<!--   <div id="prodFooter">nNetCDS V2.0 Guard ⓒ NNSP Corp.</div> -->
</div>
</body>
</html>