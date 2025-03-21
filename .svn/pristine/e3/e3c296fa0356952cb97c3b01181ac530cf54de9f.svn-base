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
    function writeCheck() {    	
    	var check = inputCheck();
		if( check ){
	    	var formData = $("form[id=writeform]").serialize();

			$.ajax({        
				type : "POST",  
				url : "system_regit.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("등록되었습니다.");
						location.replace('/send_network.do');
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
    
    function inputCheck(){
    	var form = document.getElementById("writeform");  
    	var check = /^(\d+)[.](\d+)[.](\d+)[.](\d+)$/ig;
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
    	alert(form.ncs_ext_ip.value);
    	if (!check.test(form.ncs_ext_ip.value)) {
			alert("외부 IP가 IP주소 형식이 아닙니다.");
			return false;
		}
    	
		if( !form.ncs_mac.value ) {
			alert("MAC을 입력하세요.");
			form.ncs_mac.focus();
			return false;
		}
		
		if( !form.ncs_dns.value ) {
			alert("DNS를 입력하세요.");
			form.ncs_dns.focus();
			return false;
		}
		return true;
    }
    
    function delete_system(ncs_seq) {
		if(confirm("삭제하시겠습니까?")){
	    	$.ajax({        
				type : "POST",  
				url : "system_delete.do",   
				data : "ncs_seq="+ncs_seq, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("삭제되었습니다.");
						location.replace('/send_network.do');
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
    
    function update_system(ncs_seq) {
		
	}
</script>
</head>
<body>
	<%@ include file="../include/top.jsp"%>
	
	<div id="main">
		<h1 id="exampleTitle">
			<strong>${title}</strong>
		</h1>
		<div id="exampleWrap">
		
			<div style="width:95%" class="bbsList">
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
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
				</table>
			</div>
			<div style="overflow-y:scroll; width:95%; height:500px" class="bbsList">
				<table border="0" cellspacing="0" cellpadding="0" class="boardList" >
					<colgroup>
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
						<col width="100" />
					</colgroup>
					<tbody>
					<c:forEach var="system" items="${systemList}" varStatus="status">
					<tr>
						<td>${system.ncs_int_ip}</td>
						<td>${system.ncs_ext_ip}</td>
						<td>${system.ncs_mac}</td>
						<td>${system.ncs_dns}</td>
						<td><a href="javascript:update_system('${system.ncs_seq}');" class="btn_st01"><span>수정</span></a>
						<a href="javascript:delete_system('${system.ncs_seq}');" class="btn_st01"><span>삭제</span></a></td>
					</tr>
					</c:forEach>
					</tbody>
	    		</table>
	    	</div>
	    	
	    	<sf:form id="writeform" modelAttribute="systemToRegit" method="POST" action="/system_regit.do">  
	    	<sf:hidden path="ncs_div" value="1" /> <!-- 송수신 구분 -->
	    	<div class="bbsList" style="margin-top:30px;width:95%">
				<table style="padding-top:30px;" class="boardList" >
					<colgroup>
						<col width="7%" />
						<col width="10%" />
						<col width="7%" />
						<col width="10%" />
						<col width="7%" />
						<col width="10%" />
						<col width="7%" />
						<col width="10%" />
						<col width="" />
					</colgroup>	
					<thead>	
					<tr>
						<th>내부IP</th><th><sf:input path="ncs_int_ip" size="30" /></th>
						<th>외부IP</th><th><sf:input path="ncs_ext_ip" size="30" /></th>
						<th>MAC</th><th><sf:input path="ncs_mac" size="30" /></th>
						<th>DNS</th><th><sf:input path="ncs_dns" size="30" /></th>
						<th><a href="javascript:writeCheck();" class="btn_st04"><span>등록</span></a></th>
					</tr>
					</thead>
				</table>
			</div>
			</sf:form>
			
		</div>
		<%@ include file="../include/footer.jsp"%>
	</div>
</body>
</html>