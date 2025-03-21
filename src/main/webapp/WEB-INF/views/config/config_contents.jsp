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
			if(confirm("<spring:message code='alert.register'/>")){
				var formData = $("form[id=writeform]").serialize();
				$.ajax({      
					type : "POST",  
					url : "contents_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/config_contents.do');
						}else if(data == "dupli"){
							alert("동일한 콘텐츠가 존재합니다.");
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
    	
    	if( !form.npcs_type.value ) {
			alert("프로토콜 명을 입력하세요.");
			form.npcs_type.focus();
			return false;
		}
    	
    	if( !form.npcs_name.value ) {
			alert("콘텐츠 명을 입력하세요.");
			form.npcs_name.focus();
			return false;
		}
    	
		return true;
    }
	
	function goModify(npcs_seq) {
		$.ajax({        
			type : "POST",  
			url : "contents_editVw.do",   
			data : "npcs_seq="+npcs_seq, 
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) { 
				$("#insert_area").html(data);
			},
			error: function(){
				alert('수정 내용 호출 중 에러가 발생하였습니다.');
			}
		});
	}
	
	function update_contents() {    	
    	var check = inputCheck();
    	if( check ){
			if(confirm("<spring:message code='alert.edit'/>")){
				var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",
					url : "contents_update.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/config_contents.do');
						}else if(data == "dupli"){
							alert("동일한 콘텐츠가 존재합니다.");
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
	
	function delete_contents(npcs_seq) {
		if(confirm("<spring:message code='alert.delete'/>")){
	    	$.ajax({        
				type : "POST",  
				url : "contents_delete.do",   
				data : "npcs_seq="+npcs_seq, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						location.replace('/config_contents.do');
					}else if(data == "used"){
						alert("사용 중인 콘텐츠 입니다.\n전송 통제 정책을 먼저 삭제 후 다시 시도 하세요.");
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
	
	function selectValue(value_id) {
		document.getElementById('npcs_type').value = value_id;
		if(value_id!="") {
			document.getElementById('npcs_type').readOnly  = true;
		}else{
			document.getElementById('npcs_type').readOnly  = false;
		}
	}
	
	function all_checkbox(){
    	var xx= document.listform.all_chk.checked;
		
    	if(document.listform.sel != undefined){
    		if(document.listform.sel.checked==null){
	    		for(i=0;i<document.listform.sel.length;i++){
	    			if(xx == true){
	    				document.listform.sel[i].checked=true;
	    			}else{
	    				document.listform.sel[i].checked=false;
	    			}
	    		}
	    	}else{
	    		if(xx == true){
	    			document.listform.sel.checked=true;
	    		}else{
	    			document.listform.sel.checked=false;
	    		}
	    	}
    	}
    }
    
    function delete_all() {
    	if(check_yn()){
			if(confirm("선택한 콘텐츠를 삭제하시겠습니까?")){
				var formData = $("form[id=listform]").serialize();
				$.ajax({
					type : "POST",
					url : "contents_delall.do",
					data : formData,
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data != null)  {
							var response = data.split("|");
							if(response[0] == "true"){
								alert("<spring:message code='alert.deleted'/>");
								location.replace('/config_contents.do');
							}else if(response[0] == "used"){
								alert(response[1]+"가 사용 중인 콘텐츠 입니다.\n전송 통제 정책을 먼저 삭제 후 다시 시도 하세요.");
							}else{
								alert(data);
							}
						}
					},
					error: function(){
						alert('삭제 작업 중 에러가 발생하였습니다.');
					}
				});
			}
    	}
	}
    
    function check_yn(){  
		var checkpoint=false;
		
		if(document.listform.sel != undefined){
			if(document.listform.sel.checked==null){
		    	for(i=0;i<document.listform.sel.length;i++){
		    		if(document.listform.sel[i].checked==true){
		    			checkpoint=true;
		    			break;
		    		}
		    	}
			}else{
				if(document.listform.sel.checked) checkpoint=true;
			}
		}
    	
    	if(checkpoint==false){
    		alert("삭제할 접속 허용 IP를 한개 이상 선택하세요.");
    		return false;
    	}else{
    		return true;
    	}
    }
</script>
</head>
<body class="nav-md">
<div class="container body">
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
									<div class="m_title2">프로토콜 관리</div>
								</div>						
							</div>					
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont3 x_content">
								
								<sf:form name ='listform' id='listform'>
									<div class="table-responsive">
										<table class="table jambo_table bulk_action" style="margin:0px">
										<colgroup>
											<col width="15%" />
											<col width="20%" />
											<col width="20%" />
											<col width="28%" />
											<col width="17%" />
										</colgroup>
										<thead>
										<tr class="headings">
											<th class="column-title">
												<input name='all_chk' id='all_chk' type='checkbox' onclick='javascript:all_checkbox();' /> <button type="button" class="btn btn-danger btn-xs" onClick="javascript:delete_all();"><i class="fa fa-trash-o"></i> <spring:message code='btn.delselect'/></button>
											</th>
											<th>프로토콜 명</th>
											<th>콘텐츠 명</th>
											<th>콘텐츠 헤더</th>
											<th></th>
										</tr>
										</thead>
										</table>
									</div>
									<div class="table-responsive" style="overflow-y:auto;height:500px;">
										<table class="table table-striped jambo_table bulk_action">
											<colgroup>
												<col width="15%" />
												<col width="20%" />
												<col width="20%" />
												<col width="28%" />
												<col width="17%" />
											</colgroup>
											<tbody>
											<c:forEach var="conts" items="${contsList}" varStatus="status">
											<tr>
												<td><input type="checkbox" name='sel[]' id='sel' value="${conts.npcs_seq}" /></td>
												<td>${conts.npcs_type}</td>
												<td>${conts.npcs_name}</td>
												<td>${conts.npcs_header}</td>
												<td>
													<button type="button" class="btn btn-warning btn-xs" onClick="javascript:goModify('${conts.npcs_seq}');"><i class="fa fa-pencil"></i> <spring:message code='btn.modify'/></button>
													<button type="button" class="btn btn-danger btn-xs" onClick="javascript:delete_contents('${conts.npcs_seq}');"><i class="fa fa-trash-o"></i> <spring:message code='btn.delete'/></button>
												</td>
											</tr>
											</c:forEach>
											</tbody>
							    		</table>
									</div>
								</sf:form>
								<div class="ln_solid"></div>
								
								<sf:form id="writeform" modelAttribute="contsToRegit" method="POST" action="/contents_regit.do" class="form-horizontal form-label-left">
								<sf:hidden path="npc_no" value="6" /> <!-- tcp/udp 구분 -->
								<div id="insert_area">
									<div class="form-group">
										<label class="control-label col-md-3">프로토콜 명</label>
				 						<div class="form-inline col-md-7">
				                        	<select name="npcs_type_sel" class="form-control" onChange="selectValue(this.value);">
												<option value="">직접입력</option>
												<c:forEach var="protocol" items="${protList}">
													<option value="${protocol.npcs_type}">${protocol.npcs_type}</option>
												</c:forEach>
											</select>&nbsp;&nbsp;<sf:input path="npcs_type" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">콘텐츠 명</label>
				 						<div class="col-md-7">
				                        	<sf:input path="npcs_name" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">콘텐츠 헤더</label>
				 						<div class="col-md-7">
				                        	<sf:input path="npcs_header" class="form-control col-md-7 col-xs-12" />
										</div>
									</div>
									<div class="ln_solid"></div>
									<div class="col-md-6 col-md-offset-3">
										<button type="button" class="btn btn-primary" onClick="javascript:document.getElementById('writeform').reset();document.getElementById('npcs_type').readOnly = false;"><i class="fa fa-refresh"></i> <spring:message code='btn.reset'/></button>
										<button type="button" class="btn btn-success" onClick="javascript:writeCheck();"><i class="fa fa-file-text-o"></i> <spring:message code='btn.registration'/></button>		
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