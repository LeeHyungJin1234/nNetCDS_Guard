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
	//접속 ip 상태 변경
	function change_use_yn(nai_ip, use_yn, nai_div){
		$.ajax({
			type : "POST",
			url : "edit_access_ip.do",
			data : "nai_ip="+nai_ip+"&use_yn="+use_yn+"&nai_div="+nai_div,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data == "true"){
					location.replace('/access_ip_set.do');
				}else if(data == "under"){
					alert("<spring:message code='accessip.mustone'/>");
					location.replace('/access_ip_set.do');
				}else{
					alert(data);
				}
			},
			error: function(){
				alert('사용 상태 변경 중 에러가 발생하였습니다.');
			}
		});
	}
	
	// 접속 ip 등록
	function regit_access_ip() {
		var form = document.getElementById("insert_access_ip");
    	var check = inputCheck(form);
    	if( check ){
    		if(confirm("<spring:message code='alert.register'/>")){
		    	var formData = $("form[id=insert_access_ip]").serialize();
				$.ajax({        
					type : "POST",  
					url : "/insert_access_ip.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/access_ip_set.do');
						}else if(data=="dupli"){
							alert("<spring:message code='accessip.sameip'/>");
						}else if(data == "over"){
							alert("<spring:message code='accessip.notinputip'/>");
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
		var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
		if (form.nai_ip.value == "") {
			alert("<spring:message code='accessip.ipinput'/>");
			form.nai_ip.focus(); 
			return false;
		}
		if (!check.test(form.nai_ip.value)) {
			alert("<spring:message code='accessip.notformat'/>");
			return false;
		}
		if (form.nai_name.value == "") {
			alert("<spring:message code='accessip.nameinput'/>");
			form.nai_name.focus(); 
			return false;
		}
		if(getByte(form.nai_name.value)>64){
			alert("<spring:message code='accessip.nameexceed'/>");
			return false;
		}
		return true;
    }
	
	// 수정화면 호출
	function goModify(nai_ip) {
		$.ajax({
			type : "POST",
			url : "/ipinfo_editVw.do?nai_ip="+nai_ip,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				$('#modcontent').html(data);
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	
	// 접속 ip 수정
	function update_access_ip() {
		var form = document.getElementById("update_access_ip");
    	var check = inputCheck(form);
    	if( check ){
    		if(confirm("<spring:message code='alert.edit'/>") == true){
		    	var formData = $("form[id=update_access_ip]").serialize();
				$.ajax({        
					type : "POST",  
					url : "/update_ip_info.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/access_ip_set.do');
						}else if(data=="dupli"){
							alert("<spring:message code='accessip.sameip'/>");
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
	
	function delete_access_ip(nai_ip){
		if(confirm("<spring:message code='alert.delete'/>")){
	    	$.ajax({        
				type : "POST",  
				url : "delete_access_ip.do",   
				data : "nai_ip="+nai_ip, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						location.replace('/access_ip_set.do');
					}else if(data == "under"){
						alert("<spring:message code='accessip.mustone'/>");
						location.replace('/access_ip_set.do');
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
					<a href="#" id="red-top-btn" style="margin-right:14px;"><img src="/img/btnicon1.png" style="margin:6px 8px 0 0;">접속 장비 등록</a>
					<span style="position:relative;">*</span><span> <spring:message code='accessip.upto2input'/></span>
				</div>

				<div class="content">
					<table>
					<colgroup>
						<col width="160px" />
						<col width="293px" />
						<col width="194px" />
						<col width="49px" />
						<col width="42px" />
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
								<th style="padding:10px 0"><span><spring:message code='accessip.connip'/></span></th>
								<th style="padding:10px 0"><span><spring:message code='accessip.connname'/></span></th>
								<th style="padding:10px 0 10px 48px;text-align:left;"><span><spring:message code='policy.policyuseyn'/></span></th>
								<th style="padding:10px 0"><span><spring:message code='btn.modify'/></span></th>
								<th style="padding:10px 28px 10px 0"><span><spring:message code='btn.delete'/></span></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="ncuser" items="${getAccessIp}">
							<tr class="text-medium text-main-color">
								<td align="center" style="line-height:70px;"><span>${ncuser.nai_ip}</span></td>
								<td align="center" style="line-height:70px;"><span>${ncuser.nai_name}</span></td>
								<td style="padding-left:52px">
									<c:if test="${sessionScope.clientIp ne ncuser.nai_ip}">
										<c:choose>
											<c:when test="${ncuser.nai_use_yn == 1}">
												<label class="switch" style="margin-top: 23px;">
													<input type="checkbox" class="checked" checked onchange="change_use_yn('${ncuser.nai_ip}', '${ncuser.nai_use_yn}', '${ncuser.nai_div}');"><span class="slider"></span>
												</label>
											</c:when>
											<c:otherwise>
												<label class="switch" style="margin-top: 23px;">
													<input type="checkbox" class="unchecked" onchange="change_use_yn('${ncuser.nai_ip}', '${ncuser.nai_use_yn}', '${ncuser.nai_div}');"><span class="slider"></span>
												</label>
											</c:otherwise>
										</c:choose>
									</c:if>
								</td>
								<td align="center" style="margin-top:16px; height:0;">
									<c:if test="${sessionScope.clientIp ne ncuser.nai_ip}">
										<a href="javascript:goModify('${ncuser.nai_ip}');" class="modify-btn"><img src="/img/modify.png"></a>
									</c:if>
								</td>
								<td align="center" style="margin-top:16px; height:0; padding-right:21px;">
									<c:if test="${sessionScope.clientIp ne ncuser.nai_ip}">
										<a href="javascript:delete_access_ip('${ncuser.nai_ip}');" class="delete-btn"><img src="/img/delete.png"></a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td>
							</tr>
						</tfoot>
					</table>
				</div>    
			</div>
		</div>
	</section>
	<!-- left menu end -->
	
	<div id="wripopup" class="ippopup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font"><spring:message code='accessip.regtitle'/></h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="insert_access_ip" modelAttribute="getAccessIp">
			<ul>
				<li>
					<label><spring:message code='accessip.connip'/></label>
					<input type="text" name="nai_ip" id="nai_ip" maxlength="15" />
				</li>
				<li>
					<label><spring:message code='accessip.connname'/></label>
					<input type="text" name="nai_name" id="nai_name" />
				</li>
			</ul>
			</sf:form>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
				<a href="javascript:regit_access_ip();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;"><spring:message code='btn.registration2'/></a>
			</div>
		</div>
	</div>
	
	<div id="modpopup" class="ippopup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font"><spring:message code='accessip.modtitle'/></h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="update_access_ip" modelAttribute="getAccessIp">
				<div id="modcontent"></div>
			</sf:form>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
				<a href="javascript:update_access_ip();" id="white-bottom-btn2" style="margin-right:12px;"><img src="/img/modify.png" style="margin:6px 20px 0 2px;"><spring:message code='btn.modify2'/></a>
			</div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
	<script>
		// 팝업창 띄우기
		$("#right-content .btn-section a").click(function(){
			const equipCnt = ${fn:length(getAccessIp)};

			if (equipCnt >= 2) {
				alert("<spring:message code='accessip.upto2input'/>");
				return ;
			}
			
			document.getElementById("insert_access_ip").reset();
			$("#wripopup").fadeIn();
		})
		$(".modify-btn").click(function(){
			$("#modpopup").fadeIn();
		})
		$(".ippopup .pop-wrapper .header_bar p, .cancelbtn").click(function(){
			$(".ippopup").fadeOut();
		})
		
		if($("#right-content .right-wrapper .content table tbody tr").length>0){
            $("#right-content").removeClass("footh")
        } else if($("#right-content .right-wrapper .content table tbody tr").length<1){
            $("#right-content").addClass("footh")
        }
	</script>
</body>
</html>