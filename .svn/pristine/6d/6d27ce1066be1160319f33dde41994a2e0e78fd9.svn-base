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
<%@ include file="/WEB-INF/views/include/css/nprogress_css.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/skins/green.css">
<link rel="stylesheet" type="text/css" href="/css/custom.min.css">
<link rel="stylesheet" type="text/css" href="/css/contents.css">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
    function writeCheck() {
    	var check = inputCheck();
		if( check ){
	    	if(confirm("<spring:message code='alert.edit'/>")){
		    	var formData = $("form[id=writeform]").serialize();
				$.ajax({
					type : "POST",  
					url : "program_edit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/config_rvs_program.do');
						}else if(data == "dupli"){
							alert("동일한 프로그램이 존재합니다.");
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
    
    function inputCheck(){
    	var form = document.getElementById("writeform");
    	
    	if( !form.ncp_name.value ) {
    		alert("<spring:message code='config.prognminput'/>");
			form.ncp_name.focus();
			return false;
		}
    	
    	if( !form.ncp_file_name.value ) {
    		alert("<spring:message code='config.progfileinput'/>");
			form.ncp_file_name.focus();
			return false;
		}
    	
    	if( !form.ncp_path.value ) {
    		alert("<spring:message code='config.pathinput'/>");
			form.ncp_path.focus();
			return false;
		}
    	
    	if( !form.ncp_div.value ) {
    		alert("<spring:message code='config.seldistinction'/>");
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
									<div class="m_title2"><spring:message code='config.editproginfo'/></div>
								</div>						
							</div>					
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont x_content" style="height:100%">

			    				<sf:form id="writeform" modelAttribute="programToRegit" method="POST" action="/program_regit.do" class="form-horizontal form-label-left">
			    				<sf:hidden path="ncp_seq" />
			 						<div class="item form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='policy.programnm'/></label>
										<div class="col-md-9 col-sm-9 col-xs-12">
											<sf:input path="ncp_name" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='program.filename'/></label>
										<div class="col-md-9 col-sm-9 col-xs-12">
											<sf:input path="ncp_file_name" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='config.filepath'/></label>
										<div class="col-md-9 col-sm-9 col-xs-12" id="conts_area">
											<sf:input path="ncp_path" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="item form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code='config.inexclass'/></label>
										<div class="col-md-9 col-sm-9 col-xs-12">
											<sf:select path="ncp_div" class="form-control col-md-7 col-xs-12">
												<option value="4" <c:if test="${programToRegit.ncp_div == 4}">selected</c:if>><spring:message code='config.inside'/></option>
												<option value="3" <c:if test="${programToRegit.ncp_div == 3}">selected</c:if>><spring:message code='config.outside'/></option>
											</sf:select>
										</div>
									</div>
									<div class="ln_solid"></div>
									<div class="form-group">
										<div class="col-md-9 col-md-offset-3">
											<button type="button" class="btn btn-primary" onClick="javascript:history.back();"><i class="fa fa-close"></i> <spring:message code='btn.cancel'/></button>
											<button type="button" class="btn btn-success" onClick="javascript:writeCheck();"><i class="fa fa-pencil"></i> <spring:message code='btn.modify'/></button>
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