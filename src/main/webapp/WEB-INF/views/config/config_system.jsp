<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNet-CSG Manager</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript">
    function writeCheck(div) {
    	if (confirm("등록하시겠습니까?") == true) {
	    	var check = inputCheck(div);
			if( check ){
		    	var formData = $("form[id=writeform"+div+"]").serialize();
	
				$.ajax({        
					type : "POST",  
					url : "system_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("등록되었습니다.");
							location.replace('/config_network.do');
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('Error while request..');
					}
				});
			}
    	}
    }
    
    function inputCheck(div){
    	var form = document.getElementById("writeform"+div);  
    	var check = /^(\d+)[.](\d+)[.](\d+)[.](\d+)$/i;
    	if( !form.ncs_int_ip.value ) {
			alert("내부IP를 입력하세요.");
			form.ncs_int_ip.focus();
			return false;
		}
    	
    	if (!check.test(form.ncs_int_ip.value)) {
			alert("내부 IP가 IP주소 형식이 아닙니다.");
			return false;
		}
    	
    	if( !form.ncs_ext_ip.value ) {
			alert("외부IP를 입력하세요.");
			form.ncs_ext_ip.focus();
			return false;
		}
    	
    	if (!check.test(form.ncs_ext_ip.value)) {
			alert("외부 IP가 IP주소 형식이 아닙니다.");
			return false;
		}
    	
    	if(div == 1){
			if( !form.ncs_mac.value ) {
				alert("MAC을 입력하세요.");
				form.ncs_mac.focus();
				return false;
			}
    	}
    	
		if( !form.ncs_dns.value ) {
			alert("DNS를 입력하세요.");
			form.ncs_dns.focus();
			return false;
		}
		return true;
    }
    
    function update_system(div) {
    	if (confirm("수정하시겠습니까?") == true) {
	    	var check = inputCheck(div);
			if( check ){
		    	var formData = $("form[id=writeform"+div+"]").serialize();
	
				$.ajax({        
					type : "POST",  
					url : "system_update.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("수정되었습니다.");
							location.replace('/config_network.do');
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('Error while request..');
					}
				});
			}
    	}
	}
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	
	<div id="main">
		<h1 id="exampleTitle">
			<strong>${title}</strong>
		</h1>
		<div id="exampleWrap" class="bbsList">
		
			<sf:form id="writeform1" modelAttribute="systemToRegit" method="POST" action="/system_regit.do">  
			
				<span style="font-weight: bold;font-size:13px;">* 송신 네트워크</span>
				<table border="0" cellspacing="0" cellpadding="0" class="boardList" style="margin-bottom:50px">
					<colgroup>
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
					</colgroup>
					<thead>
						<tr>
							<th>내부IP</th>
							<th>외부IP</th>
							<th>MAC</th>
							<th>DNS</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
				<c:choose>
					<c:when test="${ !empty sendList }">
						<c:forEach var="send" items="${sendList}" varStatus="status">
							<sf:hidden path="ncs_seq" value="${send.ncs_seq}" />
							<sf:hidden path="ncs_div" value="1" /> <!-- 송수신 구분 -->
							<tr>
								<td><sf:input path="ncs_int_ip" size="30" value="${send.ncs_int_ip}" /></td>
								<td><sf:input path="ncs_ext_ip" size="30" value="${send.ncs_ext_ip}" /></td>
								<td><sf:input path="ncs_mac" size="30" value="${send.ncs_mac}" /></td>
								<td><sf:input path="ncs_dns" size="30" value="${send.ncs_dns}" /></td>
								<td><a href="javascript:update_system(1);" class="btn_st04"><span>수정</span></a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<sf:hidden path="ncs_div" value="1" /> <!-- 송수신 구분 -->
						<tr>
							<td><sf:input path="ncs_int_ip" size="30" /></td>
							<td><sf:input path="ncs_ext_ip" size="30" /></td>
							<td><sf:input path="ncs_mac" size="30" /></td>
							<td><sf:input path="ncs_dns" size="30" /></td>
							<td><a href="javascript:writeCheck(1);" class="btn_st04"><span>등록</span></a></td>
						</tr>
					</c:otherwise>
				</c:choose>
					</tbody>
	    		</table>
	    		
			</sf:form>
			<sf:form id="writeform2" modelAttribute="systemToRegit" method="POST" action="/system_regit.do">  
			
	    		<span style="font-weight: bold;font-size:13px;">* 수신 네트워크</span>
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
					</colgroup>
					<thead>
						<tr>
							<th>내부IP</th>
							<th>외부IP</th>
							<th>DNS</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
				<c:choose>
					<c:when test="${ !empty receiveList }">
						<c:forEach var="receive" items="${receiveList}" varStatus="status">
							<sf:hidden path="ncs_seq" value="${receive.ncs_seq}" />
							<sf:hidden path="ncs_div" value="2" /> <!-- 송수신 구분 -->
							<tr>
								<td><sf:input path="ncs_int_ip" size="30" value="${receive.ncs_int_ip}" /></td>
								<td><sf:input path="ncs_ext_ip" size="30" value="${receive.ncs_ext_ip}" /></td>
								<td><sf:input path="ncs_dns" size="30" value="${receive.ncs_dns}" /></td>
								<td><a href="javascript:update_system(2);" class="btn_st04"><span>수정</span></a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<sf:hidden path="ncs_div" value="2" /> <!-- 송수신 구분 -->
						<tr>
							<td><sf:input path="ncs_int_ip" size="30" /></td>
							<td><sf:input path="ncs_ext_ip" size="30" /></td>
							<td><sf:input path="ncs_dns" size="30" /></td>
							<td><a href="javascript:writeCheck(2);" class="btn_st04"><span>등록</span></a></td>
						</tr>
					</c:otherwise>
				</c:choose>
					</tbody>
	    		</table>
	    	
	    	</sf:form>
			
		</div>
		<%@ include file="../include/footer.jsp"%>
	</div>
</body>
</html>