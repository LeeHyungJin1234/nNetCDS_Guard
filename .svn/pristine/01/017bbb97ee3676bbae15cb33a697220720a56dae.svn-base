<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
	// Registered text check
	function register_check() {    	
		var id_flag;
		var pw_flag;
		var seculevel_flag;
		var id_check = /^[a-zA-Z0-9]*$/;
		var form = document.getElementById("writeform"); 
		
		if(form.nsu_id.value==""){
			alert("<spring:message code='user.idinput'/>");
			form.nsu_id.focus();
			return;
		}else{
			id_flag=id_verification();
			if(id_flag==1){
				alert("<spring:message code='login.sameid'/>");
				return;
			}else if(id_flag==2){
				alert("<spring:message code='user.notuseid'/>");
				return;
			}
		}
		if(!id_check.test(form.nsu_id.value)){
			alert("<spring:message code='user.idnumalpha'/>");
			return;
		}
		if(getByte(form.nsu_id.value)>64){
			alert("<spring:message code='user.id64'/>");
			return;
		}
		if (form.nsu_pw.value == "") {
			alert("<spring:message code='login.pwinput'/>");
			form.nsu_pw.focus();
			return;		
		}else{
			pw_flag=pw_verification();
			if(pw_flag==1){
				alert("<spring:message code='user.pwin3'/>");
				return;
			}else if(pw_flag==2){
				alert("<spring:message code='user.pwleast9more127'/>");
				return;				
			}
		}
		if (form.nsu_name.value == "") {
			alert("<spring:message code='user.nameinput'/>");
			form.nsu_name.focus();
			return;
		}
		if(getByte(form.nsu_name.value)>64){
			alert("<spring:message code='user.nameexceed'/>");
			return;
		}
		
		if (form.nsu_division.value != "") {
			if(getByte(form.nsu_division.value)>64){
				alert("<spring:message code='user.departexceed'/>");
				return;
			}
		}
		if (form.nsu_position.value != "") {
			if(getByte(form.nsu_position.value)>64){
				alert("<spring:message code='user.rankexceed'/>");
				return;
			}
		}
		if (form.nsu_tel.value != "") {
			var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
			if (!regTel.test(form.nsu_tel.value)){
				alert("<spring:message code='user.wiredinvalid'/>");
				return;
			}
		}
		if (form.nsu_mobile.value != "") {
			var regMob = /^\d{3}-\d{3,4}-\d{4}$/;
			if (!regMob.test(form.nsu_mobile.value)){
				alert("<spring:message code='user.mobileinvalid'/>");
				return;
			}
		}
		if (form.email_id.value == "") {
			alert("<spring:message code='user.emailinput'/>");
			form.email_id.focus();
			return;
		}
		if (form.email_server.value == "") {
			alert("<spring:message code='user.emailinput'/>");
			form.email_server.focus();
			return;
		}
		if(getByte(form.email_id.value+form.email_server.value)>191){
			alert("<spring:message code='user.emaillong'/>");
			return;
		}
		var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
		if (!regExp.test(form.email_id.value+'@'+form.email_server.value)){
			alert("<spring:message code='user.emailinvalid'/>");
			return;
		}
		/*if (!form.nsu_secu_level[0].checked && !form.nsu_secu_level[1].checked) {
			alert("보안등급을 선택하세요.");
			return;
		}
		seculevel_flag = seculevel_verification();
		if(seculevel_flag == 1){	
			alert("더 이상 관리자 등급으로 등록할 수 없습니다.");
			return;
		}
		if(seculevel_flag == 2){
			alert("더 이상 모니터링 등급으로 등록할 수 없습니다.");
			return;
		}*/
		
		if (form.nsu_desc.value != "") {
			if(getByte(form.nsu_desc.value)>255){
				alert("<spring:message code='user.remarksexceed'/>");
				return;
			}
		}
		
		if(id_flag == 0 && pw_flag == 0){
			if (confirm("<spring:message code='alert.register'/>") == true) {
				$.ajax({
					type : "POST",  
					url : "insert_user.do",   
					data :  $("#writeform").serialize(), 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/user_list.do');
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

	// Distibute email information
	function selectValue(value_id) {
		document.getElementById('email_server').value = value_id;
	}	
	
	function id_verification(){
		var flag=0;
		$.ajax({
			type : "POST",
			url : "id_check.do",
			async : false,
			data : $("#writeform").serialize(),
			datatype : "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(data){
				if(data=="present"){
					//Present same ID's
					flag=1;
				}else if(data=="not_allow"){
					flag=2;
				}
			},
			error : function(){				
				alert('아이디 적합성 체크 작업중 오류');			
			}
		});
		
		return flag;		
	}
	
	function pw_verification(){
		var flag=0;
		$.ajax({
			type : "POST",
			url : "pw_check.do",
			async : false,
			data : $("#writeform").serialize(),
			datatype : "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(data){
				if(data=="not_rule"){
					// Not enough password rule
					flag=1;
				}else if(data=="not_enough_length"){
					// Not enough password length
					flag=2;
				}
			},
			error : function(){
				alert('패스워드 적합성 체크 작업중 오류');			
			}
		});
		
		return flag;
	}
	
	function seculevel_verification(){
		var flag=0;
		$.ajax({
			type : "POST",
			url : "regit_seculevel_check.do",
			async : false,
			data : $("#writeform").serialize(),
			datatype : "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(data){
				if(data=="admin_over"){
					// 관리자 계정 등록 불가
					flag=1;
				}else if(data=="monitor_over"){
					// 모니터 계정 등록 불가
					flag=2;
				}
			},
			error : function(){
				alert('Error while request..');			
			}
		});
		
		return flag;
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
									<div class="m_title2"><spring:message code='user.admininforegit'/></div>
								</div>						
							</div>					
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont x_content" style="height:100%">
			
								<sf:form id="writeform" modelAttribute="ncuserdata" class="form-horizontal form-label-left">
								<sf:hidden path="nsu_secu_level" value="1" />
				
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='stat.adminid'/> <span class="required">*</span></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="ID" class="form-control col-md-7 col-xs-12" path="nsu_id" maxlength="64" />
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.passwd'/> <span class="required">*</span></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="password" title="비밀번호" class="form-control col-md-7 col-xs-12" path="nsu_pw" maxlength="127"/>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.name'/> <span class="required">*</span></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="이름" class="form-control col-md-7 col-xs-12" path="nsu_name" />
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.department'/></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="부서" class="form-control col-md-7 col-xs-12" path="nsu_division"/>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.rank'/></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="직급" class="form-control col-md-7 col-xs-12" path="nsu_position"/>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.wireline'/></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="유선전화" class="form-control col-md-7 col-xs-12" path="nsu_tel" placeholder="ex) 02-123-4567"/>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.cellphone'/></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:input type="text" title="휴대전화" class="form-control col-md-7 col-xs-12" path="nsu_mobile" placeholder="ex) 010-1234-5678"/>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.emailaddr'/> <span class="required">*</span></label>
										<div class="form-inline col-md-10 col-sm-10 col-xs-12">
											<input type="text" name="email_id" id="email_id" title="이메일주소" class="form-control" /> @ <input class="form-control" id="email_server" name="email_server" type="text" />
											<select id="email_sel" name="email_sel" class="form-control" onChange="selectValue(this.value)">
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
												<option value="nate.com">nate.com</option>											
												<option value="netian.com">netian.com</option>											
												<option value="paran.com">paran.com</option>											
												<option value="yahoo.com">yahoo.com</option>											
												<option value="yahoo.co.kr">yahoo.co.kr</option>
											</select>
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-2 col-sm-2 col-xs-12"><spring:message code='user.remarks'/></label>
										<div class="col-md-10 col-sm-10 col-xs-12">
											<sf:textarea path="nsu_desc" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="ln_solid"></div>
									<div class="form-group">
										<div class="col-md-10 col-md-offset-2">
											<button type="button" class="btn btn-primary" onClick="javascript:history.back();"><i class="fa fa-close"></i> <spring:message code='btn.cancel'/></button>
											<button type="button" class="btn btn-success" onClick="javascript:register_check();"><i class="fa fa-file-text-o"></i> <spring:message code='btn.registration'/></button>
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
		
		<!-- footer content -->
		<%@ include file="../include/footer.jsp"%>
		<!-- /footer content -->
	</div>
</div>
</body>
</html>