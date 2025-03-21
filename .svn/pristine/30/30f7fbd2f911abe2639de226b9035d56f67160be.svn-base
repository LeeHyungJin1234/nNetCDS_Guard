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
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
	function modify() {
		var check = inputCheck();
		if( check ){
			if (confirm("<spring:message code='alert.edit'/>") == true) {
				var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "modify_product.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/config_product.do');
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
	
	function insert() {
		var check = inputCheck();
		if( check ){
			if (confirm("<spring:message code='alert.register'/>") == true) {
				var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "insert_product.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/config_product.do');
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
		var tel_check = /^[0-9]+$/; 
		var regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
		
		if (form.ncpd_name.value==null || form.ncpd_name.value=="") {
			alert("<spring:message code='config.deviceinput'/>");
			return;
		}
		
		if (form.ncpd_model.value==null || form.ncpd_model.value=="") {
			alert("<spring:message code='config.modelinput'/>");
			return;
		}

		if (form.ncpd_sn.value==null || form.ncpd_sn.value=="") {
			alert("<spring:message code='config.serialinput'/>");
			return;
		}
		
		if (form.ncpd_tel1.value==null || form.ncpd_tel1.value=="") {
			alert("<spring:message code='config.techinput'/>");
			return;
		}

		if (form.ncpd_tel2.value==null || form.ncpd_tel2.value=="") {
			alert("<spring:message code='config.techinput'/>");
			return;
		}
		
		if (form.ncpd_tel3.value==null || form.ncpd_tel3.value=="") {
			alert("<spring:message code='config.techinput'/>");
			return;
		}
		if(!tel_check.test(form.ncpd_tel2.value)){
			alert("잘못된 전화 번호 입니다.숫자만 입력하세요.");
			return;
		}
		
		if(!tel_check.test(form.ncpd_tel3.value)){
			alert("잘못된 전화 번호 입니다.숫자만 입력하세요.");
			return;
		}
		
		form.ncpd_tel.value = form.ncpd_tel1.value+"-"+form.ncpd_tel2.value+"-"+form.ncpd_tel3.value;

		if (form.ncpd_date.value==null || form.ncpd_date.value=="") {
			alert("<spring:message code='config.installdateinput'/>");
			return;
		}
		
		if(!regex.test(form.ncpd_date.value)){
			alert("검색기간 입력 형식이 맞지 않습니다. \"yyyy-mm-dd\" 형식에 맞게 입력해 주십시오.");
			
			let date = new Date();
			
			form.ncpd_date.value =
				date.getFullYear() + "-" +
				String((date.getMonth() + 1)).padStart(2, '0') + "-" +
				date.getDate();
			
			return;
		}
		
		return true;
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

				<!-- contents start -->
				<div class="content-form" style="width:590px;">
					<div class="form-wrapper">
						<sf:form id="writeform" modelAttribute="config_product_data">
							<ul id="product_ul" lang="${pageContext.response.locale}">
								<li id="li-prod-name" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.prodname'/>
									</span>
									<input type="text" name="ncpd_name"
												 value="${config_product_data.ncpd_name}"
												 style="width:340px;" disabled="disabled">
								</li>
								<li id="li-prod-version" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.prodver'/>
									</span>
									<input type="text" name="ncpd_version"
												 value="${config_product_data.ncpd_version}"
												 style="width:340px;" disabled="disabled">
								</li>
								<li id="li-hw-model-name" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.hwmodel'/>
									</span>
									<input type="text" id="ncpd_hw_model" name="ncpd_hw_model"
												 value="${config_product_data.ncpd_hw_model}"
												 style="width:340px;margin-left:0px;border-top-left-radius:0;border-bottom-left-radius:0;border-radius:4px; position:relative;"
												 disabled="disabled">
								</li>
								<li id="li-prod-model-name" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.prodmodel'/>
									</span>
									<input type="text" id="ncpd_model" name="ncpd_model"
												 value="${config_product_data.ncpd_model}"
												 style="width:340px;margin-left:0px;border-top-left-radius:0;border-bottom-left-radius:0;border-radius:4px; position:relative;"
												 disabled="disabled">
								</li>
								<li id="li-prod-sn" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.serialnm'/>
									</span>
									<input type="text" name="ncpd_sn" value="${config_product_data.ncpd_sn}"
												 style="width:340px;"
												 disabled="disabled">
								</li>
								<li id="li-prod-ts" lang="${pageContext.response.locale}" style="padding-right:12px;">
									<span class="text-main-color2">
										<spring:message code='config.techsupport'/>
									</span>
									<select id="ncpd_tel1" name="ncpd_tel1" style="width:67px;">
										<option value="02" <c:if test="${ncpd_tel1 eq '02'}">selected</c:if>>02</option>
										<option value="031" <c:if test="${ncpd_tel1 eq '031'}">selected</c:if>>031</option>
										<option value="032" <c:if test="${ncpd_tel1 eq '032'}">selected</c:if>>032</option>
										<option value="033" <c:if test="${ncpd_tel1 eq '033'}">selected</c:if>>033</option>
										<option value="041" <c:if test="${ncpd_tel1 eq '041'}">selected</c:if>>041</option>
										<option value="042" <c:if test="${ncpd_tel1 eq '042'}">selected</c:if>>042</option>
										<option value="043" <c:if test="${ncpd_tel1 eq '043'}">selected</c:if>>043</option>
										<option value="044" <c:if test="${ncpd_tel1 eq '044'}">selected</c:if>>044</option>
										<option value="051" <c:if test="${ncpd_tel1 eq '051'}">selected</c:if>>051</option>
										<option value="052" <c:if test="${ncpd_tel1 eq '052'}">selected</c:if>>052</option>
										<option value="053" <c:if test="${ncpd_tel1 eq '053'}">selected</c:if>>053</option>
										<option value="054" <c:if test="${ncpd_tel1 eq '054'}">selected</c:if>>054</option>
										<option value="055" <c:if test="${ncpd_tel1 eq '055'}">selected</c:if>>055</option>
										<option value="061" <c:if test="${ncpd_tel1 eq '061'}">selected</c:if>>061</option>
										<option value="062" <c:if test="${ncpd_tel1 eq '062'}">selected</c:if>>062</option>
										<option value="063" <c:if test="${ncpd_tel1 eq '063'}">selected</c:if>>063</option>
										<option value="064" <c:if test="${ncpd_tel1 eq '064'}">selected</c:if>>064</option>
										<option value="070" <c:if test="${ncpd_tel1 eq '070'}">selected</c:if>>070</option>
									</select>
									<img src="/img/search-bar.png" style="margin:0 10px;vertical-align:middle;"><input type="text" id="ncpd_tel2" name="ncpd_tel2" maxlength="4" value="${ncpd_tel2}" style="width:87px;"/>
									<img src="/img/search-bar.png" style="margin:0 10px;vertical-align:middle;"><input type="text" id="ncpd_tel3" name="ncpd_tel3" maxlength="4" value="${ncpd_tel3}" style="width:87px;"/>
									<input type="hidden" name="ncpd_tel" /></li>
								<li id="li-prod-installed" lang="${pageContext.response.locale}" style="padding-right:85px;">
									<span class="text-main-color2">
										<spring:message code='config.installdate'/>
									</span>
									<input type="text" id="to" name="ncpd_date" value="${config_product_data.ncpd_date}" class="input date" style="width:253px;">
								</li>
								<li id="li-prod-desc" lang="${pageContext.response.locale}">
									<span class="text-main-color2">
										<spring:message code='config.desc'/>
									</span>
									<textarea id="ncpd_desc" name="ncpd_desc" style="width: 338px; height: 77px; padding-top: 10px; resize: vertical;">${config_product_data.ncpd_desc}</textarea>
								</li>
							</ul>
						</sf:form>
						<div class="btn-section">
							<a href="javascript:location.replace('/config_product.do');" id="gray-bottom-btn" style="margin-right:12px;">
								<img src="/img/cancel.png" style="margin:5px 20px 0 0;">
								<spring:message code='btn.cancel2'/>
							</a>
							<a href="javascript:modify();" id="white-bottom-btn" style="margin-right:12px;">
								<img src="/img/modify.png" style="margin:3px 20px 0 0;">
								<spring:message code='btn.modify2'/>
							</a>
						</div>
					</div>
				</div>
				<!-- contents end -->
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>