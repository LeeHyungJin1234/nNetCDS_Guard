<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNetCDS 관리 시스템</title>
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
			if (confirm("등록하시겠습니까?") == true) {
		    	var formData = $("form[id=writeform]").serialize();
				$.ajax({
					type : "POST",  
					url : "link_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("등록되었습니다.");
							location.replace('/config_snd_link.do');
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
    	var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
    	var check1 = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
    	if( !form.nclk_rcv_ip.value ) {
			alert("수신 IP를 입력하세요.");
			form.nclk_rcv_ip.focus();
			return false;
		}
    	if (!check.test(form.nclk_rcv_ip.value)) {
			alert("수신 IP가 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( !form.nclk_rcv_nm.value ) {
			alert("수신 Netmask를 입력하세요.");
			form.nclk_rcv_nm.focus();
			return false;
		}
    	if (!check1.test(form.nclk_rcv_nm.value)) {
			alert("수신 Netmask가 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( !form.nclk_snd_ip.value ) {
			alert("송신 IP를 입력하세요.");
			form.nclk_snd_ip.focus();
			return false;
		}
    	if (!check.test(form.nclk_snd_ip.value)) {
			alert("송신 IP가 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( !form.nclk_snd_nm.value ) {
			alert("송신 Netmask를 입력하세요.");
			form.nclk_snd_nm.focus();
			return false;
		}
    	if (!check1.test(form.nclk_snd_nm.value)) {
			alert("송신 Netmask가 IP주소 형식이 아닙니다.");
			return false;
		}
    	if( !form.nclk_snd_gw.value ) {
			alert("송신 Gateway를 입력하세요.");
			form.nclk_snd_gw.focus();
			return false;
		}
    	if (!check.test(form.nclk_snd_gw.value)) {
			alert("송신 Gateway가 IP주소 형식이 아닙니다.");
			return false;
		}
    	
		return true;
    }
    
    function update_system() {
    	var check = inputCheck();
		if( check ){
			if (confirm("수정하시겠습니까?") == true) {
		    	var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "link_update.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("수정되었습니다.");
							location.replace('/config_snd_link.do');
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
    
    function svc_restart(){
    	if (confirm("재시작 하시겠습니까?") == true) {
	    	$.ajax({        
				type : "POST",  
				url : "link_snd_restart.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "success"){
						alert("재시작 명령이 정상적으로 실행 되었습니다.");
						location.replace('/config_snd_link.do');
					}else if (data == "fail") {
						alert("재시작 명령 실행에 실패하였습니다. 관리자에게 문의 바랍니다.");
					}
				},
				error: function(){
					alert('서비스 재시작 중 에러가 발생하였습니다.');
				}
			});
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
			<div class="pg_title">
				<label class="m_title"><i class='fa fa-edit'></i> ${title}</label>
				<label class="s_title">관리자 계정 정보를 관리합니다.</label>
			</div>

			<div class="clearfix"></div>
			
			<div class="contents">
				<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-12">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content">
									<div class="m_title2">네트워크 설정 <small style="color:red;font-weight:bold">* 연계 시스템 네트워크 설정이 내/외부 모두 등록되어야 정상 동작 됩니다.</small></div>
								</div>						
							</div>					
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont x_content" style="height:100%">
								<sf:form id="writeform" modelAttribute="systemToRegit" method="POST" action="/link_regit.do" class="form-horizontal form-label-left">  
					
									<c:choose>
									<c:when test="${ !empty sendList }">
										<c:forEach var="send" items="${sendList}" varStatus="status">
										<sf:hidden path="nclk_seq" value="${send.nclk_seq}" />
										<sf:hidden path="nclk_div" value="1" /> <!-- 송수신 구분 -->
										
										<div class="item form-group">
											<label class="control-label col-md-2 col-sm-2 col-xs-12 green">수신</label>
											<div class="col-md-7 col-sm-7 col-xs-12">
												<div class="ln_solid"></div>
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">IP</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_rcv_ip" class="form-control col-md-7 col-xs-12" value="${send.nclk_rcv_ip}" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Netmask</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_rcv_nm" class="form-control col-md-7 col-xs-12" value="${send.nclk_rcv_nm}" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-2 col-sm-2 col-xs-12 green">송신</label>
											<div class="col-md-7 col-sm-7 col-xs-12">
												<div class="ln_solid"></div>
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">IP</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_ip" class="form-control col-md-7 col-xs-12" value="${send.nclk_snd_ip}" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Netmask</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_nm" class="form-control col-md-7 col-xs-12" value="${send.nclk_snd_nm}" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Gateway</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_gw" class="form-control col-md-7 col-xs-12" value="${send.nclk_snd_gw}" maxlength="15" />
											</div>
										</div>
										
										<div class="ln_solid"></div>
										<div class="form-group">
											<div class="col-md-6 col-md-offset-3">
												<button type="button" class="btn btn-primary" onClick="javascript:location.replace('/config_snd_link.do');">취소</button>
												<button type="button" class="btn btn-success" onClick="javascript:update_system();">수정</button>
											</div>
										</div>
							
										</c:forEach>
									</c:when>
									<c:otherwise>
										<sf:hidden path="nclk_div" value="1" /> <!-- 송수신 구분 -->
							
										<div class="item form-group">
											<label class="control-label col-md-2 col-sm-2 col-xs-12 green">수신</label>
											<div class="col-md-7 col-sm-7 col-xs-12">
												<div class="ln_solid"></div>
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">IP</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_rcv_ip" class="form-control col-md-7 col-xs-12" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Netmask</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_rcv_nm" class="form-control col-md-7 col-xs-12" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-2 col-sm-2 col-xs-12 green">송신</label>
											<div class="col-md-7 col-sm-7 col-xs-12">
												<div class="ln_solid"></div>
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">IP</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_ip" class="form-control col-md-7 col-xs-12" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Netmask</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_nm" class="form-control col-md-7 col-xs-12" maxlength="15" />
											</div>
										</div>
										<div class="item form-group">
											<label class="control-label col-md-3 col-sm-3 col-xs-12">Gateway</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<sf:input path="nclk_snd_gw" class="form-control col-md-7 col-xs-12" maxlength="15" />
											</div>
										</div>
										
										<div class="ln_solid"></div>
										<div class="form-group">
											<div class="col-md-6 col-md-offset-3">
												<button type="button" class="btn btn-primary" onClick="javascript:location.replace('/config_snd_link.do');">취소</button>
												<button type="button" class="btn btn-success" onClick="javascript:writeCheck();">등록</button>
											</div>
										</div>
							
									</c:otherwise>
								</c:choose>
					
								</sf:form>
								</div>
							</div>
		
	   					</div>
	   				</div>
	   			
	   			
		   			<div class="col-md-6 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>버전 정보</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content"><h2>${version.ncv_info}</h2></div>
						</div>
					</div>
						
					<div class="col-md-6 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>서비스 재시작</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<button type="button" class="btn btn-primary" onClick="javascript:svc_restart();">재시작 실행</button>
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