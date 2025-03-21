<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='common.title'/></title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/css/nprogress.css">
<link rel="stylesheet" type="text/css" href="/css/skins/green.css">
<link rel="stylesheet" type="text/css" href="/css/custom.min.css">
<link rel="stylesheet" type="text/css" href="/css/contents.css">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
	// Modified text check
	function inputCheck() {    	
		var form = document.getElementById("writeform");
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
		if(form.nsu_email.value==""){
			alert("<spring:message code='user.emailinput'/>");
			form.nsu_email.focus();
			return false;
		}
		if(form.email_server.value==""){
			alert("<spring:message code='user.emailinput'/>");
			form.email_server.focus();
			return false;
		}
		if(getByte(form.nsu_email.value+form.email_server.value)>191){
			alert("<spring:message code='user.emaillong'/>");
			return;
		}
		var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
		if (!regExp.test(form.nsu_email.value+'@'+form.email_server.value)){
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
		    	var formData = $("form[id=writeform]").serialize();
				$.ajax({
					type : "POST",
					url : "user_edit.do",
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/user_editVw.do');
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

	// Distibute email information
	function selectValue(value_id) {	 
		 document.getElementById('email_server').value = value_id;
	}
	
	// 비밀번호 변경
	function pwmodify(){
		var check = pwinputCheck();
		if( check ){
			if (confirm("<spring:message code='alert.change'/>")) {
				var formData = $("form[id=passwdform]").serialize();
				
				$.ajax({        
					type : "POST",  
					url : "pwchange_useredit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed2'/>");
							location.replace('/user_editVw.do');
						}else if(data=="not_match_passwd"){
							alert("<spring:message code='login.curpwnotmatch'/>");
						}else if(data=="not_rule"){
							alert("<spring:message code='login.pwcombi'/>");
						}else if(data=="not_enough_length"){
							alert("<spring:message code='login.least9more127'/>");
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('변경 작업 중 에러가 발생하였습니다.');
					}
				});
			}
		}
	}
	
	function pwinputCheck(){
 		var form = document.getElementById("passwdform");
 		
 		if(form.orig_passwd.value.length < 1){
			alert('<spring:message code="login.curpwinput"/>');
   			form.orig_passwd.focus();
   			return false;
  		}
 		if(form.new_passwd.value.length < 1){
			alert('<spring:message code="login.newpwinput"/>');
   			form.new_passwd.focus();
   			return false;
  		}
  		if(form.new_passwd_confirm.value.length < 1){
   			alert('<spring:message code="login.newpwverifinput"/>');
   			form.new_passwd_confirm.focus();
   			return false;
   		}
  		if(form.orig_passwd.value == form.new_passwd.value){
   			alert('<spring:message code="login.newcursame"/>');
  	 		return false;
  		}
  		if(form.new_passwd.value.length < 9){
   			alert('<spring:message code="login.pwleast9"/>');
  	 		form.new_passwd.focus();
   			return false;
  		}
  		if(form.new_passwd.value.length > 127){
   			alert('<spring:message code="login.pwmost127"/>');
  	 		form.new_passwd.focus();
   			return false;
  		}
  		var check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{9,127}$/;
  		if (!check.test(form.new_passwd.value)){
			alert('<spring:message code="login.pwcombi"/>');
			return false;
		}
  		if(form.new_passwd.value != form.new_passwd_confirm.value){
   			alert('<spring:message code="login.pwnotmatch"/>');
  	 		return false;
  		}
  		
  		return true;
 	}
</script>
</head>
<body class="nav-md">
<div class="container body">
<input type="hidden" id="menu_id" value="${menu_id}">
	<div class="main_container">
		<!-- top content -->
		<%@ include file="../include/top.jsp"%>
		<!-- /top content -->

        <!-- page content -->
		<div class="right_col" role="main">
			<div class="contents">
				<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-12">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content">
									<div class="m_title2"><spring:message code="user.admininfoedit"/></div>
								</div>						
							</div>					
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont x_content" style="height:100%">
			
								<sf:form id="writeform" modelAttribute="ncUserData" class="form-horizontal form-label-left">  
								
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.name"/> <span class="required">*</span></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:input type="text" title="이름" class="form-control col-md-7 col-xs-12" path="nsu_name" />
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.passwd"/> <span class="required">*</span></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<button type="button" class="btn btn-danger" data-toggle="modal" data-target=".bs-example-modal-lg"><spring:message code="user.changepw"/></button>
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.department"/></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:input type="text" title="부서" class="form-control col-md-7 col-xs-12" path="nsu_division" />
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.rank"/></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:input type="text" title="직급" class="form-control col-md-7 col-xs-12" path="nsu_position" />
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.wireline"/></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:input type="text" title="유선전화" class="form-control col-md-7 col-xs-12" path="nsu_tel" placeholder="ex) 02-123-4567" />
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.cellphone"/></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:input type="text" title="휴대전화" class="form-control col-md-7 col-xs-12" path="nsu_mobile" placeholder="ex) 010-1234-5678" />
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.emailaddr"/> <span class="required">*</span></label>
									<div class="form-inline col-md-10 col-sm-10 col-xs-12">
										<input type="text" name="nsu_email" id="nsu_email" title="이메일주소" class="form-control" value="${email_id}" /> @ <input class="form-control" id="email_server" name="email_server" type="text" value="${email_serv}" />
										<select id="email_sel" name="email_sel" class="form-control" onChange="selectValue(this.value)">
											<option value=""><spring:message code="user.directsel"/></option>											
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
											<option value="nate.com">nate.com</option>											
											<option value="netian.com">netian.com</option>											
											<option value="paran.com">paran.com</option>											
											<option value="yahoo.com">yahoo.com</option>											
											<option value="yahoo.co.kr">yahoo.co.kr</option>
										</select>
									</div>
								</div>
								<div class="item form-group">
									<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code="user.remarks"/></label>
									<div class="col-md-10 col-sm-10 col-xs-12">
										<sf:textarea path="nsu_desc" class="form-control col-md-7 col-xs-12" />
									</div>
								</div>
								<div class="ln_solid"></div>
								<div class="form-group">
									<div class="col-md-10 col-md-offset-2">
										<button type="button" class="btn btn-primary" onClick="javascript:location.replace('/user_editVw.do');"><i class="fa fa-close"></i> <spring:message code='btn.cancel'/></button>
										<button type="button" class="btn btn-success" onClick="javascript:modify();"><i class="fa fa-pencil"></i> <spring:message code='btn.modify'/></button>
									</div>
								</div>
									
								</sf:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /page content -->
		
		<!-- 비밀번호 변경 -->
		<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<sf:form id="passwdform" class="form-horizontal form-label-left">  
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
						<h4 class="modal-title" id="myModalLabel"><spring:message code='user.changepw'/></h4>
					</div>
					<div class="modal-body">
						<div class="item form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='login.currentpw'/></label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="password" name="orig_passwd" class="form-control col-md-7 col-xs-12" maxlength="127">
							</div>
						</div>
						<div class="item form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='user.newpw'/></label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="password" name="new_passwd" class="form-control col-md-7 col-xs-12" maxlength="127">
							</div>
						</div>
						<div class="item form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='login.newpwconfi'/></label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="password" name="new_passwd_confirm" class="form-control col-md-7 col-xs-12" maxlength="127">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"></i> <spring:message code='btn.cancel'/></button>
						<button type="button" class="btn btn-primary" onClick="javascript:pwmodify();"><i class="fa fa-pencil"></i> <spring:message code='btn.change'/></button>
					</div>
				</div>
				</sf:form>
			</div>
		</div>
		<!-- /비밀번호 변경 -->
                  
		<!-- footer content -->
		<%@ include file="../include/footer.jsp"%>
		<!-- /footer content -->
	</div>
</div>
</body>
</html>