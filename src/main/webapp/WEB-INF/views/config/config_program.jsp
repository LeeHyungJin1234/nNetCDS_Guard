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
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	function goModify(ncp_seq) {
	    $.ajax({
			type : "POST",
			url : "/program_editVw.do?ncp_seq="+ncp_seq,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				$('#modcontent').html(data);
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
    
    function delete_program(ncp_seq) {
		if(confirm("<spring:message code='alert.delete'/>")){
	    	$.ajax({        
				type : "POST",  
				url : "program_delete.do",   
				data : "ncp_seq="+ncp_seq, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						location.replace('/config_program.do');
					}else if(data == "used"){
						alert("사용 중인 프로그램 입니다.\n전송 통제 정책을 먼저 삭제 후 다시 시도 하세요.");
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
    
    function delete_all() {
    	if(check_yn()){
			if(confirm("<spring:message code='config.wentdelprog'/>")){
				var formData = $("form[id=listform]").serialize();
				$.ajax({
					type : "POST",  
					url : "program_delall.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data != null)  {
							var response = data.split("|");
							if(response[0] == "true"){
								alert("<spring:message code='alert.deleted'/>");
								location.replace('/config_program.do');
							}else if(response[0] == "used"){
								alert(response[1]+"가 사용 중인 프로그램 입니다.\n전송 통제 정책을 먼저 삭제 후 다시 시도 하세요.");
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
		
		if(document.listform.delck != undefined){
			if(document.listform.delck.checked==null){
		    	for(i=0;i<document.listform.delck.length;i++){
		    		if(document.listform.delck[i].checked==true){
		    			checkpoint=true;
		    			break;
		    		}
		    	}
			}else{
				if(document.listform.delck.checked) checkpoint=true;
			}
		}
    	
    	if(checkpoint==false){
    		alert("<spring:message code='config.seldelprog'/>");
    		return false;
    	}else{
    		return true;
    	}
    }
    
    function selectDiv(){
    	document.listform.action ="/config_program.do";
    	document.listform.submit();
    }
</script>
<script type="text/javascript">
    function writeCheck() {
    	var form = document.getElementById("writeform");
    	var check = inputCheck(form);
		if( check ){
	    	if(confirm("<spring:message code='alert.register'/>")){
		    	var formData = $("form[id=writeform]").serialize();
		
				$.ajax({
					type : "POST",  
					url : "program_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/config_program.do');
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
    
    function inputCheck(form){
    	if( !form.ncp_name.value ) {
			alert("<spring:message code='config.prognminput'/>");
			form.ncp_name.focus();
			return false;
		}
    	
    	if(getByte(form.ncp_name.value)>64){
			alert("프로그램 명은 한글 32자, 영문 64자를 초과 입력할 수 없습니다.");
			return false;
		}
    	
    	if( !form.ncp_file_name.value ) {
			alert("<spring:message code='config.progfileinput'/>");
			form.ncp_file_name.focus();
			return false;
		}
    	
    	if(getByte(form.ncp_file_name.value)>64){
			alert("프로그램 파일명은 영문 64자를 초과 입력할 수 없습니다.");
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
    
    function modifyCheck() {
    	var form = document.getElementById("modifyform");
    	var check = inputCheck(form);
		if( check ){
	    	if(confirm("<spring:message code='alert.edit'/>")){
		    	var formData = $("form[id=modifyform]").serialize();
		
				$.ajax({
					type : "POST",  
					url : "program_edit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/config_program.do');
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
				
				<div class="btn-section">
					<a href="javascript:delete_all();" id="white-top-btn" style="margin-right:8px;"><img src="/img/delete.png" style="margin:3px 8px 0 0;">선택 삭제</a>
					<a href="#" id="red-top-btn" class="program_btn"><img src="/img/btnicon1.png" style="margin:6px 8px 0 0;">프로그램 등록</a>
				</div>

				<sf:form name='listform' id='listform'>
				<div class="content">
					<table class="scroll">
					<colgroup>
						<col width="" />
						<col width="180px" />
						<col width="240px" />
						<col width="360px" />
						<col width="140px" />
						<col width="30px" />
						<col width="60px" />
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
								<th style="border-right:1px solid #e0e3e7;padding:0px"><input type="checkbox" id="delck"><label for="delck"><span></span></label></th>
								<th style="padding:10px 0;"><span><spring:message code='policy.programnm'/></span></th>
								<th style="padding:10px 0;"><span><spring:message code='program.filename'/></span></th>
								<th style="padding:10px 0;"><span><spring:message code='config.filepath'/></span></th>
								<th style="padding:10px 0;">
									<select id="ncp_div" name="ncp_div" onChange="selectDiv(this.value)" style="width:40px;">
										<option value="0"><spring:message code='stat.total'/></option>
										<option value="1" <c:if test="${ncpDiv == 1}">selected</c:if>><spring:message code='config.inside'/></option>
										<option value="2" <c:if test="${ncpDiv == 2}">selected</c:if>><spring:message code='config.outside'/></option>
									</select>
								</th>
								<th style="padding:10px 0 0 4px;"><span><spring:message code='btn.modify'/></span></th>
								<th style="padding:10px 0;"><span><spring:message code='btn.delete'/></span></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="program" items="${programList}" varStatus="status">
							<tr class="text-medium text-main-color">
								<td style="padding:25px 12px;border-right:1px solid #e0e3e7;"><input type="checkbox" name="delck" value="${program.ncp_seq}" id="delck${program.ncp_seq}"><label for="delck${program.ncp_seq}"><span></span></label></td>
								<td align="center" style="padding:25px 0;"><span>${program.ncp_name}</span></td>
								<td align="center" style="padding:25px 0;"><span>${program.ncp_file_name}</span></td>
								<td align="center" style="padding:25px 0;"><span>${program.ncp_path}</span></td>
								<td align="center" style="padding:25px 0;"><span><c:choose><c:when test="${program.ncp_div==1}"><spring:message code='config.inside'/></c:when><c:otherwise><spring:message code='config.outside'/></c:otherwise></c:choose></span></td>
								<td align="center" style="padding:5px 0 0 2px;"><a href="javascript:goModify('${program.ncp_seq}');" class="modify-btn" style="margin-top:10px;"><img src="/img/modify.png"></a></td>
								<td align="center" style="padding:5px 0;"><a href="javascript:delete_program('${program.ncp_seq}');" class="delete-btn" style="margin-top:10px;"><img src="/img/delete.png"></a></td>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td><td></td><td></td>
							</tr>
						</tfoot>
					</table>
				</div>
				</sf:form>
			</div>
		</div>
	</section>
	<!-- left menu end -->

	<div id="wpopup" class="prgpopup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font">프로그램 정보 등록</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="writeform" modelAttribute="programToRegit" method="POST">
			<ul>
				<li>
					<label><spring:message code='policy.programnm'/></label>
					<sf:input path="ncp_name" />
				</li>
				<li>
					<label><spring:message code='program.filename'/></label>
					<sf:input path="ncp_file_name" />
				</li>
				<li>
					<label>파일 경로</label>
					<sf:input path="ncp_path" maxlength="100" />
				</li>
				<li>
					<label>내/외부 구분</label>
					<sf:select path="ncp_div">
						<option value="1"><spring:message code='config.inside'/></option>
						<option value="2"><spring:message code='config.outside'/></option>
					</sf:select>
				</li>
			</ul>
			</sf:form>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
				<a href="javascript:writeCheck();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;">등록하기</a>
			</div>
		</div>
	</div>
	
	<div id="mpopup" class="prgpopup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font">프로그램 정보 수정</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="modifyform" modelAttribute="programToRegit" method="POST">
			<div id="modcontent"></div>
			</sf:form>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
				<a href="javascript:modifyCheck();" id="white-bottom-btn2" style="margin-right:12px;" class="white-bottom-btn2"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
			</div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
	<script>
		$(".modify-btn").click(function(){
			$("#mpopup").fadeIn();
		})
		$(".program_btn").click(function(){
			$("#wpopup").fadeIn();
		})
		$(".prgpopup .pop-wrapper .header_bar p, .cancelbtn").click(function(){
			$(".prgpopup").fadeOut();
		})
		
		if($("#right-content .right-wrapper .content table tbody tr").length>0){
            $("#right-content").removeClass("footh")
        } else if($("#right-content .right-wrapper .content table tbody tr").length<1){
            $("#right-content").addClass("footh")
        }
	</script>
</body>
</html>