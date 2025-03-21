<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" >
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
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

<script type="text/javascript">	
	$(document).ready(function () {
		document.getElementById("nsu_pw").addEventListener("keydown", pwKeyDownEventHandler);
		document.getElementById("nsu_pw_confirm").addEventListener("keydown", pwKeyDownEventHandler);
		document.getElementById("orig_passwd").addEventListener("keydown", pwKeyDownEventHandler);
		document.getElementById("new_passwd").addEventListener("keydown", pwKeyDownEventHandler);
		document.getElementById("new_passwd_confirm").addEventListener("keydown", pwKeyDownEventHandler);
	});
	
	// 관리자 삭제
	function delete_access_user(user_seq) {
		if(confirm("<spring:message code='alert.delete'/>")){
			$.ajax({
				type : "POST",
				url : "user_delete.do",
				data : "user_seq=" + user_seq,
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						location.replace('/user_list.do');
					}else{
						alert(data);
					}
				},
				error: function(){
					alert('삭제 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	
	// 내정보 수정
	function goModify(user_id) {
		$.ajax({
			type : "POST",
			url : "/user_editVw.do?user_id="+user_id,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				$('#modcontent').html(data);
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
</script>
<script type="text/javascript">
	// Registered text check
	function register_check() {
		const form = document.getElementById("writeform");
		
		/////필수 입력
		//계정
		if (!validateId("nsu_id")) {
			return;
		}
		//PW
		if(!validatePassword("nsu_pw", "nsu_pw_confirm")){
			return;
		}
		//이름
		if (form.nsu_name.value == "") {
			alert("<spring:message code='user.nameinput'/>");
			form.nsu_name.focus();
			return;
		}
		if (getByte(form.nsu_name.value)>64) {
			alert("<spring:message code='user.nameexceed'/>");
			form.nsu_name.focus();
			return;
		}
		//이메일
		if (form.nsu_email_id.value == "") {
			alert("<spring:message code='user.emailinput'/>");
			form.nsu_email_id.focus();
			return;
		}
		if (form.nsu_email_server.value == "") {
			alert("<spring:message code='user.emaildomaininput'/>");
			form.nsu_email_server.focus();
			return;
		}
	
		////선택입력
		//부서
		if (form.nsu_division.value != "") {
			if(getByte(form.nsu_division.value)>64){
				alert("<spring:message code='user.departexceed'/>");
				return;
			}
		}
		//직급
		if (form.nsu_position.value != "") {
			if(getByte(form.nsu_position.value)>64){
				alert("<spring:message code='user.rankexceed'/>");
				return;
			}
		}
		//유선전화
		if (form.nsu_tel.value != "") {
			var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
			if (!regTel.test(form.nsu_tel.value)){
				alert("<spring:message code='user.wiredinvalid'/>");
				return;
			}
		}
		//휴대전화
		if (form.nsu_mobile.value != "") {
			var regMob = /^\d{3}-\d{3,4}-\d{4}$/;
			if (!regMob.test(form.nsu_mobile.value)){
				alert("<spring:message code='user.mobileinvalid'/>");
				return;
			}
		}
		//설명
		if (form.nsu_desc.value != "") {
			if(getByte(form.nsu_desc.value)>255){
				alert("<spring:message code='user.remarksexceed'/>");
				return;
			}
		}
		
		if (confirm("<spring:message code='alert.register'/>") == true) {
		    //RSA 암호화 생성
			var rsa = new RSAKey();
			rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
			
			document.insertUserSubmit.nsu_id.value = form.nsu_id.value;
			document.insertUserSubmit.nsu_pw.value = rsa.encrypt(form.nsu_pw.value);
			document.insertUserSubmit.nsu_pw_confirm.value = rsa.encrypt(form.nsu_pw_confirm.value);
			document.insertUserSubmit.nsu_name.value = form.nsu_name.value;
			document.insertUserSubmit.nsu_email_id.value = form.nsu_email_id.value;
			document.insertUserSubmit.nsu_email_server.value = form.nsu_email_server.value;
			document.insertUserSubmit.nsu_division.value = form.nsu_division.value;
			document.insertUserSubmit.nsu_position.value = form.nsu_position.value;
			document.insertUserSubmit.nsu_tel.value = form.nsu_tel.value;
			document.insertUserSubmit.nsu_mobile.value = form.nsu_mobile.value;
			document.insertUserSubmit.nsu_desc.value = form.nsu_desc.value;
			
// 			form.nsu_pw.value=null;
// 			form.nsu_pw_confirm.value=null;
			
			var formData = $("form[id=insertUserSubmit]").serialize();
			
			$.ajax({
				type : "POST",  
				url : "insert_user.do",
				data :formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.registered'/>");
						location.replace('/user_list.do');
			        } 
					else {
						handleRegisterError(data, form);
					}
				},
				error: function(){
					alert('등록 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	
	function handleRegisterError(data, form) {
		if(data.includes('pw')){
			form.nsu_pw.value = "";
		    form.nsu_pw_confirm.value = "";
		}
		
	    switch (data) {
			//ID 검증
		    case "id_duplicate":
	            alert("<spring:message code='user.sameid'/>");
	            form.nsu_id.focus();
	            break;
	           
	        //PW 검증
		    case "pw_not_enough_length":
	            alert("<spring:message code='user.pwleast9more127'/>");
	            form.nsu_pw.focus();
	            break;
		    case "pw_not_rule":
	            alert("<spring:message code='user.pwin4'/>");
	            form.nsu_pw.focus();
	            break;
	        case "pw_not_id":
	            alert("<spring:message code='user.idpw'/>");
	            form.nsu_pw.focus();
	            break;
	        case "pw_not_continuity":
	            alert("<spring:message code='user.pwsamecont'/>");
	            form.nsu_pw.focus();
	            break;
	        case "pw_not_qwer":
	            alert("<spring:message code='user.consecutive3pwd'/>");
	            form.nsu_pw.focus();
	            break;
	        case "pw_not_same_confirm":
	            alert("<spring:message code='user.pwconfirmnotpw'/>");
	            form.nsu_pw_confirm.focus();
	            break;
	         
	        //NAME 검증
	        case "name_not_length":
	            alert("<spring:message code='user.nameexceed'/>");
	            form.nsu_name.focus();
	            break;
	            
	        //EMAIL 검증
	        case "email_too_long":
	            alert("<spring:message code='user.emaillong'/>");
	            form.nsu_email_id.focus();
	            break;
	        case "email_not_email":
	            alert("<spring:message code='user.emailinvalid'/>");
	            form.nsu_email_id.focus();
	            break;
	        
	        default:
	            alert(data);  // 기타 오류 메시지 출력
	            break;
	    }
	}
	
	function validateId(nsu_id){
		const id = document.getElementById(nsu_id).value;
		const idField = document.getElementById(nsu_id);
		
		if (id == "") {
			alert("<spring:message code='user.idinput'/>");
			idField.focus();
			return false;
		}
		if(getByte(id) < 6 || getByte(id) > 64){
			alert("<spring:message code='user.idmin6max64'/>");
			idField.focus();
            return false;
		}
		const idRegex = /^(?=.*[a-zA-Z\d])[a-zA-Z\d]{6,64}$/;
		if(!idRegex.test(id)){
			alert("<spring:message code='user.idnumalpha'/>");
			idField.focus();
            return false;
		}
		if(id.toLowerCase() === "administrator"){
			alert("<spring:message code='user.notuseid'/>");
			idField.focus();
            return false;
		}
		
		return true;
	}
	
	function validatePassword(nsu_pw, nsu_pw_confirm){
		const pw = document.getElementById(nsu_pw).value;
		const pwField = document.getElementById(nsu_pw);
		const pwConfirm = document.getElementById(nsu_pw_confirm).value;
		const pwConfirmField = document.getElementById(nsu_pw_confirm);
		
		if (pw == "") {
			alert("<spring:message code='user.pwinput'/>");
			pwField.focus();
			return false;
		}
		if(pw.length < 9 || pw.length > 127){
			alert("<spring:message code='user.pwleast9more127'/>");
			pwField.focus();
            return false;
		}
		if (pwConfirm == "") {
			alert("<spring:message code='user.pwinputconfirm'/>");
			pwConfirmField.focus();
			return;
		}
		if(pwConfirm.length < 9 || pwConfirm.length > 127){
			alert("<spring:message code='user.pwleast9more127'/>");
			pwConfirmField.focus();
            return false;
		}
		
		return true;
	}
	
	// Distibute email information
	function selectValue(form, value_id) {
		form.nsu_email_server.value = value_id;
	}	
	
	// Modified text check
	function inputCheck() {    	
		var form = document.getElementById("modifyform");
		if(form.nsu_name.value==""){
			alert("<spring:message code='user.nameinput'/>");
			form.nsu_name.focus();
			return false;
		}
		if(getByte(form.nsu_name.value)>64){
			alert("<spring:message code='user.nameexceed'/>");
			return false;
		}
		if (form.nsu_division.value != "") {
			if(getByte(form.nsu_division.value)>64){
				alert("<spring:message code='user.departexceed'/>");
				return false;
			}
		}
		if (form.nsu_position.value != "") {
			if(getByte(form.nsu_position.value)>64){
				alert("<spring:message code='user.rankexceed'/>");
				return false;
			}
		}
		if (form.nsu_tel.value != "") {
			var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
			if (!regTel.test(form.nsu_tel.value)){
				alert("<spring:message code='user.wiredinvalid'/>");
				return false;
			}
		}
		if (form.nsu_mobile.value != "") {
			var regMob = /^\d{3}-\d{3,4}-\d{4}$/;
			if (!regMob.test(form.nsu_mobile.value)){
				alert("<spring:message code='user.mobileinvalid'/>");
				return false;
			}
		}
		if(form.nsu_email_id.value==""){
			alert("<spring:message code='user.emailinput'/>");
			form.nsu_email_id.focus();
			return false;
		}
		if(form.nsu_email_server.value==""){
			alert("<spring:message code='user.emailinput'/>");
			form.nsu_email_server.focus();
			return false;
		}
		if(getByte(form.nsu_email_id.value+form.nsu_email_server.value)>191){
			alert("<spring:message code='user.emaillong'/>");
			return;
		}
		var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
		if (!regExp.test(form.nsu_email_id.value+'@'+form.nsu_email_server.value)){
			alert("<spring:message code='user.emailinvalid'/>");
			return;
		}
		if (form.nsu_desc.value != "") {
			if(getByte(form.nsu_desc.value)>255){
				alert("<spring:message code='user.remarksexceed'/>");
				return;
			}
		}
		return true;		
    }
	function modify() {
    	var check = inputCheck();
		if( check ){
			if (confirm("<spring:message code='alert.edit'/>") == true) {
		    	var formData = $("form[id=modifyform]").serialize();
				$.ajax({
					type : "POST",
					url : "user_edit.do",
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/user_list.do');
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
	// 비밀번호 변경
	function pwmodify(){
		const form = document.getElementById("passwdform");
		
		if(!validatePasswordMod("orig_passwd", "new_passwd", "new_passwd_confirm")){
			return;
		}
		
		if (confirm("<spring:message code='alert.change'/>")) {
			var rsa = new RSAKey();
			rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
			
			document.modifyPwSubmit.orig_passwd.value = rsa.encrypt(document.passwdform.orig_passwd.value);
			document.modifyPwSubmit.new_passwd.value = rsa.encrypt(document.passwdform.new_passwd.value);
			document.modifyPwSubmit.new_passwd_confirm.value = rsa.encrypt(document.passwdform.new_passwd_confirm.value);
			
			form.orig_passwd.value=null;
			form.new_passwd.value=null;
			form.new_passwd_confirm.value=null;
			
			var formData = $("form[id=modifyPwSubmit]").serialize();
			
			$.ajax({        
				type : "POST",  
				url : "pwchange_useredit.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.changed2'/>");
						location.replace('/logout.do');
					} else{
						handleModifyPwError(data, form);
					}
				},
				error: function(){
					alert('변경 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	
	function validatePasswordMod(orig_passwd, new_passwd, new_passwd_confirm){
		const origPw = document.getElementById(orig_passwd).value;
		const origPwField = document.getElementById(orig_passwd);
		const newPw = document.getElementById(new_passwd).value;
		const newPwField = document.getElementById(new_passwd);
		const newPwConfirm = document.getElementById(new_passwd_confirm).value;
		const newPwConfirmField = document.getElementById(new_passwd_confirm);
		
		if (origPw == "") {
			alert('<spring:message code="login.curpwinput"/>');
			origPwField.focus();
			return false;
		}
		if(origPw.length < 9 || origPw.length > 127){
			alert("<spring:message code='user.pwleast9more127'/>");
			origPwField.focus();
            return false;
		}
		
		if (newPw == "") {
			alert('<spring:message code="login.newpwinput"/>');
			newPwField.focus();
			return;
		}
		if(newPw.length < 9 || newPw.length > 127){
			alert("<spring:message code='user.pwleast9more127'/>");
			newPwField.focus();
            return false;
		}
		
		if (newPwConfirm == "") {
			alert('<spring:message code="login.newpwverifinput"/>');
			newPwConfirmField.focus();
			return;
		}
		if(newPwConfirm.length < 9 || newPwConfirm.length > 127){
			alert("<spring:message code='user.pwleast9more127'/>");
			newPwConfirmField.focus();
            return false;
		}
		
		return true;
	}
	
	function handleModifyPwError(data, form) {
// 		if(data.includes('pw')){
// 			form.orig_passwd.value = "";
// 		    form.new_passwd.value = "";
// 		    form.new_passwd_confirm.value = "";
// 		}
		
	    switch (data) {
		    case "pw_not_match":
	            alert("<spring:message code='login.curpwnotmatch'/>");
	            form.orig_passwd.focus();
	            break;
		    case "pw_not_enough_length":
	            alert("<spring:message code='user.pwleast9more127'/>");
	            form.orig_passwd.focus();
	            break;
		    case "pw_not_rule":
	            alert("<spring:message code='user.pwin4'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_not_id":
	            alert("<spring:message code='user.idpw'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_not_continuity":
	            alert("<spring:message code='user.pwsamecont'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_not_qwer":
	            alert("<spring:message code='user.consecutive3pwd'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_same_with_prev":
	            alert("<spring:message code='login.pwnoprev'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_not_same_new":
	            alert("<spring:message code='login.newcursame'/>");
	            form.orig_passwd.focus();
	            break;
	        case "pw_not_same_confirm":
	            alert("<spring:message code='user.pwconfirmnotpw'/>");
	            form.orig_passwd.focus();
	            break;
	        default:
	            alert(data);  // 기타 오류 메시지 출력
	            break;
	    }
	}
	
	// 리스트에서 사용 여부 수정
    function change_checkbox(el) {
      var useyn = 0
      
      
      if (el.checked) {
        useyn = 1
      }
      $.ajax({
        type: "POST",
        url: "user_useyn.do",
        data: "nsu_seq=" + el.value + "&nsu_use_yn=" + useyn,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
          if (data == "true") {
            location.replace('/user_list.do');
          } else {
            alert(data);
          }
        },
        error: function () {
          alert("<spring:message code='alert.errorupdate'/>");
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
					
				<c:if test="${fn:length(getSystemUser)<10}">
					<div class="btn-section">
						<a href="#" id="red-top-btn">
							<img src="/img/btnicon1.png" style="margin:6px 8px 0 0;">
          					<spring:message code='btn.adminregist'/>
						</a>
				        <span style="position:relative;">*</span>
				        <span> <spring:message code='user.upto2'/></span>
					</div>
				</c:if>
				<div class="content">
					<table>
					<colgroup>
						<col width="74px"/>
						<col width="148px"/>
						<col width="127px"/>
						<col width="192px"/>
						<col width="69px"/>
						<col width="125px"/>
						<col width="138px"/>
						<col width="171px"/>
						<col width="186px"/>
						<col width="236px"/>
						<col width="67px"/>
						<col width="67px"/>
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
					            <th><span><spring:message code='log.turn'/></span></th>
					            <th><span><spring:message code='stat.adminid'/></span></th>
					            <th><span><spring:message code='user.name'/></span></th>
					            <th><span><spring:message code='user.email'/></span></th>
					            <th><span><spring:message code='user.department'/></span></th>
					            <th><span><spring:message code='user.rank'/></span></th>
					            <th><span><spring:message code='user.cellphone'/></span></th>
					            <th><span><spring:message code='user.wireline'/></span></th>
					            <th><span><spring:message code='user.registdate'/></span></th>
					            <th><span><spring:message code='config.desc'/></span></th>
					            <th><span><spring:message code='common.useyn'/></span></th>
					            <th><span><spring:message code='user.delete'/></span></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="ncuser" items="${getSystemUser}" varStatus="status">
							<tr class="text-medium text-main-color">
				              <td align="center" style="padding:25px 0;">${status.count}</td>
				              <td align="center" style="padding:25px 0;">${ncuser.nsu_id}</td>
				              <td align="center" style="padding:25px 0;">${ncuser.nsu_name}</td>
				              <td align="center" style="padding:25px 0;">${ncuser.nsu_email}</td>
				              <td align="center" style="padding:25px 0;">
				                <span>${ncuser.nsu_division}
				                  <c:if test="${'' eq ncuser.nsu_division}">-</c:if>
				                </span>
				              </td>
				              <td align="center" style="padding:25px 0;">
				                <span>${ncuser.nsu_position}
				                  <c:if test="${'' eq ncuser.nsu_position}">-</c:if>
				                </span>
				              </td>
				              <td align="center" style="padding:25px 0;">
				                <span>${ncuser.nsu_mobile}
				                  <c:if test="${'' eq ncuser.nsu_mobile}">-</c:if>
				                </span>
				              </td>
				              <td align="center" style="padding:25px 0;">
				                <span>${ncuser.nsu_tel}
				                  <c:if test="${'' eq ncuser.nsu_tel}">-</c:if>
				                </span>
				              </td>
				              <td align="center" style="padding:25px 0;">
				                  ${ncuser.nsu_reg_date}
				              </td>
				              <td align="center" style="padding:25px 0;">
				                <span>${ncuser.nsu_desc}</span>
				              </td>
				              <c:if test="${sessionScope.loginUserId ne ncuser.nsu_id}">
				                <td align="center" style="padding:25px 0;">
				                  <c:if test="${sessionScope.loginUserId ne ncuser.nsu_id}">
				                    <c:choose>
				                      <c:when test="${ncuser.nsu_use_yn eq 0}">
				                        <c:set var="check_state" value="unchecked"/>
				                      </c:when>
				                      <c:otherwise>
				                        <c:set var="check_state" value="checked"/>
				                      </c:otherwise>
				                    </c:choose>
				                    <label class="switch">
				                      <input type="checkbox" class="${check_state}"
				                             value="${ncuser.nsu_seq}"
				                             onchange="change_checkbox(this)" ${check_state}>
				                      <span class="slider"></span>
				                    </label>
				                  </c:if>
				                </td>
				              </c:if>
				              <td align="center" style="padding:16px 0;">
				                <c:if test="${sessionScope.loginUserId ne ncuser.nsu_id}">
				                  <a href="javascript:delete_access_user('${ncuser.nsu_seq}');" class="delete-btn">
				                    <img src="/img/delete.png"></a>
				                </c:if>
				              </td>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr>
            					<td colspan="11"></td>
							</tr>
						</tfoot>
					</table>
				</div>    
			</div>
		</div>
	</section>
	<!-- left menu end -->

	<div id="wripopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
            	<h1>관리자 등록</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			
			<sf:form method="post" id="insertUserSubmit" name="insertUserSubmit" onsubmit="return false;">
				<input type="hidden" name="nsu_secu_level" value="1"/>
				<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
				<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
				<input type="hidden" name="nsu_id"/>
				<input type="hidden" name="nsu_pw"/>
				<input type="hidden" name="nsu_pw_confirm"/>
				<input type="hidden" name="nsu_name"/>
				<input type="hidden" name="nsu_email_id"/>
				<input type="hidden" name="nsu_email_server"/>
				<input type="hidden" name="nsu_division"/>
				<input type="hidden" name="nsu_position"/>
				<input type="hidden" name="nsu_tel"/>
				<input type="hidden" name="nsu_mobile"/>
				<input type="hidden" name="nsu_desc"/>
			</sf:form>
			
			<sf:form id="writeform" modelAttribute="ncuserdata">
				<div class="section01">
					<h2><spring:message code='user.requiredinput'/></h2><p class="border_title"></p>
					<ul>
						<li>
							<label><spring:message code='stat.adminid'/></label>
							<sf:input type="text" id="nsu_id" name="nsu_id" path="nsu_id"/>
						</li>
						<li>
							<label><spring:message code='user.passwd'/></label>
							<sf:input type="password" id="nsu_pw" name="nsu_pw" path="nsu_pw" />
							<p><spring:message code='user.pwcombi'/></p>
						</li>
						<li style="margin-right:240px;">
							<label><spring:message code='user.passwdconfirm'/></label>
							<sf:input type="password" id="nsu_pw_confirm" name="nsu_pw_confirm" path="nsu_pw_confirm"/>
						</li>
						<li style="margin-right:240px;">
							<label><spring:message code='user.name'/></label>
							<sf:input type="text" id="" path="nsu_name" />
						</li>
						<li >
							<label><spring:message code='user.emailaddr'/></label>
							<input type="text" id="nsu_email_id" name="nsu_email_id" class="input_1">@<input type="text" id="nsu_email_server" name="nsu_email_server" class="input_2">
							<select id="email_sel" name="email_sel" onChange="selectValue(this.form, this.value)" style="margin-top:5px;">
								<option value=""><spring:message code='user.directsel'/></option>											
								<option value="naver.com">naver.com</option>
								<option value="dreamwiz.com">dreamwiz.com</option>											
								<option value="empal.com">empal.com</option>											
								<option value="freechal.com">freechal.com</option>	
								<option value="daum.net">daum.net</option>										
								<option value="gmail.com">gmail.com</option>											
								<option value="hanmail.net">hanmail.net</option>											
								<option value="hanmir.com">hanmir.com</option>		
								<option value="hotmail.com">hotmail.com</option>											
								<option value="nate.com">nate.com</option>											
								<option value="lycos.co.kr">lycos.co.kr</option>										
								<option value="netian.com">netian.com</option>											
								<option value="paran.com">paran.com</option>											
								<option value="yahoo.com">yahoo.com</option>											
								<option value="yahoo.co.kr">yahoo.co.kr</option>
							</select>
						</li>
					</ul>
				</div>
				<div class="section02" style="position:relative;">
					<h2><spring:message code='user.optionalinput'/></h2><p class="border_title"></p>
					<ul>
						<li class="clearfix">
							<label style="padding-top:8px"><spring:message code='user.department'/></label>
							<sf:input type="text" style="margin-left:12px" id="nsu_division" name="nsu_division" path="nsu_division"/>
						</li>
						<li style="position:absolute; right:44px; top:58px;">
							<label style="padding-top:8px"><spring:message code='user.rank'/></label>
							<sf:input type="text" style="margin-left:10px" id="nsu_position" name="nsu_position" path="nsu_position"/>
						</li>
						<li>
							<label><spring:message code='user.wireline'/></label>
							<sf:input type="text" id="nsu_tel" name="nsu_tel" path="nsu_tel" placeholder="ex) 02-123-4567"/>
						</li>
						<li>
							<label><spring:message code='user.cellphone'/></label>
							<sf:input type="text" id="nsu_mobile" name="nsu_mobile" path="nsu_mobile" placeholder="ex) 010-1234-5678"/>
						</li>
						<li>
							<span class="text-main-color2"><spring:message code='config.explanation'/></span>
							<sf:textarea id="nsu_desc" name="nsu_desc" path="nsu_desc" style="width:405px;height:64px; padding-top:10px;"/>
						</li>
					</ul>
				</div>
				<p class="border_bottom"></p>
				<div class="bottom-section">
					<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
					<a href="javascript:register_check();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;"><spring:message code='btn.registration2'/></a>
				</div>
			</sf:form>
		</div>
	</div>
	<div id="modpopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
            	<h1>내 정보 수정</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="modifyform" modelAttribute="ncuserdata">
				<sf:hidden path="nsu_secu_level" value="1" />
				<div id="modcontent"></div>
				<p class="border_bottom"></p>
				<div class="bottom-section">
					<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
					<a href="javascript:modify();" id="white-bottom-btn2" style="margin-right:12px;" class="white-bottom-btn2"><img src="/img/modify.png" style="margin:3px 20px 0 0;"><spring:message code='btn.modify2'/></a>
				</div>
			</sf:form>
		</div>
	</div>
	<div id="pwpopup" class="pwpopup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font">비밀번호 변경</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form method="post" id="modifyPwSubmit" name="modifyPwSubmit" onsubmit="return false;">
					<input type="hidden" name="nsu_secu_level" value="1"/>
					<input type="hidden" name="orig_passwd"/>
					<input type="hidden" name="new_passwd"/>
					<input type="hidden" name="new_passwd_confirm"/>
				</sf:form>
			<form id="passwdform" name="passwdform">
			<ul>
				<li>
					<label>현재 비밀번호</label>
					<input type="password" id="orig_passwd" name="orig_passwd" maxlength="127">
				</li>
				<li>
					<label>새 비밀번호</label>
					<input type="password" id="new_passwd" name="new_passwd" maxlength="127">
				</li>
				<li>
					<label>새 비밀번호 확인</label>
					<input type="password" id="new_passwd_confirm" name="new_passwd_confirm" maxlength="127">
				</li>
			</ul>
			</form>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="pwcancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
				<a href="javascript:pwmodify();" id="white-bottom-btn2" style="margin-right:12px;" class="white-bottom-btn2"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
			</div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
	<c:if test="${edit_flag=='y'}">
		<script>
			$(function() {
				$("#modpopup").fadeIn();
				goModify('${sessionScope.loginUserId}');
			});
		</script>
	</c:if>
	<script>
		// 팝업창 띄우기
		$("#right-content .btn-section a").click(function(){
			document.getElementById("writeform").reset();
			const userCnt = ${fn:length(getSystemUser)};

		    if (userCnt >= 2) {
		    	alert("<spring:message code='user.upto2'/>");
		      return;
		    }
		    $("#writeform").trigger('reset');
			$("#wripopup").fadeIn();
		})
		$(".popup .pop-wrapper .header_bar p,.cancelbtn").click(function(){
			$(".popup").fadeOut();
		})
		$(".pwpopup .pop-wrapper .header_bar p,.pwcancelbtn").click(function(){
			$(".pwpopup").fadeOut();
		})
		
		if($("#right-content .right-wrapper .content table tbody tr").length>0){
            $("#right-content").removeClass("footh")
        } else if($("#right-content .right-wrapper .content table tbody tr").length<1){
            $("#right-content").addClass("footh")
        }
	</script>
</body>
</html>