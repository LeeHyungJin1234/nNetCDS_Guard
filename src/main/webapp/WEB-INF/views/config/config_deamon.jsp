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
<style type="text/css">
#insert_area td {
	background-color: rgba(20,53,80,0.038);
}
</style>

<script type="text/javascript">
// 테이블 하단 텍스트 출력 및 modify_deamon() 함수 호출
function update_deamon(seq, name, div, status, path){
	var form = document.getElementById("insert_deamon"+div);	
	form.ncd_name.value = name;
	form.ncd_status[status-1].checked = true;
	form.ncd_path.value = path;
	form.ncd_seq.value = seq;
	document.getElementById("register"+div).innerHTML = "<a href=\"javascript:modify_deamon('"+div+"','"+seq+"');\" style='padding-right: 10px' class='btn_st04'><span>변경</span></a><a href=\"javascript:location.replace('/config_deamon.do');\"  class='btn_st04'><span>취소</span>";
}

// 기존 서비스 데몬 수정
function modify_deamon(div, seq){
	var formData = $("form[id=insert_deamon"+div+"]").serialize();
	var check = check_deamon(div, seq);	// check 1:중복, 0:사용가능
	if(check==0){
		$.ajax({        
			type : "POST",  
			url : "modify_deamon.do",   
			data : formData, 
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) {
				if(data == "true"){
					alert("수정되었습니다.");
					location.replace('/config_deamon.do');
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

// 등록 및 수정 시 데몬 명 중복 확인
function check_deamon(div, seq){
	var form = document.getElementById("insert_deamon"+div);
	var flag;
	$.ajax({        
		type : "POST",  
		url : "check_deamon.do",   
		data : "ncd_name="+form.ncd_name.value+"&ncd_div="+div+"&ncd_seq="+seq, 
		datatype : "json",
		async : false,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data == "exist"){
				alert("동일한 데몬 명이 있습니다.");	
				flag=1;
			}else{
				flag=0;
			}				
		},
		error: function(){
			alert('Error while request..');
		}
	});
	return flag;
}

// 신규 서비스 데몬 추가
function insert_deamon(div){
	var formData = $("form[id=insert_deamon"+div+"]").serialize();
	var check = check_deamon(div, 0) // check 1:중복, 0:사용가능
	if(check == 0){
		$.ajax({        
			type : "POST",  
			url : "insert_deamon.do",   
			data : formData, 
			datatype : "json",		
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) {
				if(data == "true"){
					location.replace('/config_deamon.do');
					alert("등록되었습니다.");				
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

// 서비스 데몬 사용여부 변경
function edit_use_yn(ncd_seq, ncd_use_yn){
	$.ajax({        
		type : "POST",  
		url : "edit_deamon_useyn.do",   
		data : "ncd_seq="+ncd_seq+"&ncd_use_yn="+ncd_use_yn, 
		datatype : "json",		
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data == "true"){
				location.replace('/config_deamon.do');
				alert("변경되었습니다.");				
			}else{
				alert(data);				
			}				
		},
		error: function(){
			alert('Error while request..');
		}
	});
}
</script>

</head>
<body>
	<%@ include file="../include/top.jsp"%>
	<div id="main">
		<h1 id="exampleTitle">
			<strong>${title}</strong>
		</h1>
		
		<!-- 송신 측 서비스 데몬 설정 start -->
		<div id="exampleWrap">
			
			<div class="bbsList">
			<h3>* 송신 서비스 데몬 설정</h3>
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="12%" />
						<col width="15%" />
						<col width="16%" />
						<col width="16%" />
						<col width="14%" />
					</colgroup>
					<thead>
					<tr>
						<th>데몬 명</th>
						<th>동작 상태</th>
						<th>실행 경로</th>
						<th>수정</th>
						<th>사용상태 변경</th>
					</tr>
					</thead>
				</table>
			</div>
			<div class="bbsList" style="overflow: auto;">
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="12%" />
						<col width="15%" />
						<col width="16%" />
						<col width="16%" />
						<col width="14%" />
					</colgroup>
					<tbody>
					<c:forEach var="ncconfig" items="${deamon_data1}">
						<tr>							
							<td>${ncconfig.ncd_name}</td>
							<td><c:if test="${ncconfig.ncd_status == 1}"><span>동작</span></c:if>
								<c:if test="${ncconfig.ncd_status == 2}"><span>중지</span></c:if>
								<c:if test="${ncconfig.ncd_status == 3}"><span>재시작</span></c:if>
								</td>
							<td>${ncconfig.ncd_path}</td>
							<td><a class="btn_st01" href="javascript:update_deamon('${ncconfig.ncd_seq}', '${ncconfig.ncd_name}', '${ncconfig.ncd_div}', '${ncconfig.ncd_status}', '${ncconfig.ncd_path}');"><span>수정</span></a></td>
							<td>
								<a href="javascript:edit_use_yn('${ncconfig.ncd_seq}', '${ncconfig.ncd_use_yn}');" class="btn_st01">
								<c:if test="${ncconfig.ncd_use_yn == 1}"><span>미사용</span></c:if>
								<c:if test="${ncconfig.ncd_use_yn == 0}"><span>사용</span></c:if></a>	
							</td>						
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			
			<form id="insert_deamon1" method="POST">
			<div class="bbsList" style="margin-top:30px">
				<table style="padding-top:30px;" class="boardList">
					<colgroup>
						<col width="10%" />
						<col width="18%" />
						<col width="10%" />
						<col width="15%" />
						<col width="10%" />
						<col width="20%" />
						<col width="10%" />
					</colgroup>	
					<thead>	
					<tr id="insert_area">						
						<th>데몬 명:</th>
						<td>
						<input type="hidden" id="ncd_seq" name="ncd_seq"/> <!-- 각 데몬의 고유 관리 번호 -->
						<input  class="frmBox" type="text" id="ncd_name" name="ncd_name" style="width:80%; text-align: center;" placeholder="ex) send" /></td>
						<th>동작 상태:</th>
						<td>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="1" />&nbsp;동작<span style="padding-left: 10px"></span>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="2" />&nbsp;중지<span style="padding-left: 10px"></span>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="3" />&nbsp;재시작
						</td>
						<th>실행 경로:</th>
						<td ><input type="text" class="frmBox" id="ncd_path" name="ncd_path" style="width:80%;  text-align: center;" placeholder="ex) /usr/local/" />
						<input type="hidden" id="ncd_div" name="ncd_div" value="1"/> <!-- 송수신 구분 --></td>
						<td id="register1"><a href="javascript:insert_deamon('1');" style="padding-right: 10px" class="btn_st04"><span>등록</span></a><a href="javascript:location.replace('/config_deamon.do');"  class="btn_st04"><span>취소</span></a></td>						
					</tr>		
					</thead>		
				</table>
			</div>
			</form>
		</div>
		<!-- 송신 측 서비스 데몬 설정 end -->
		
		<!-- 수신 측 서비스 데몬 설정 start -->
		<div id="exampleWrap">
			
			<div class="bbsList">
				<h3>* 수신 서비스 데몬 설정</h3>
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="12%" />
						<col width="15%" />
						<col width="16%" />
						<col width="16%" />
						<col width="14%" />						
					</colgroup>
					<thead>
					<tr>
						<th>데몬 명</th>
						<th>동작 상태</th>
						<th>실행 경로</th>
						<th>수정</th>
						<th>사용상태 변경</th>
					</tr>
					</thead>
				</table>
			</div>
			<div class="bbsList" style="overflow: auto;">
				<table border="0" cellspacing="0" cellpadding="0" class="boardList">
					<colgroup>
						<col width="12%" />
						<col width="15%" />
						<col width="16%" />
						<col width="16%" />
						<col width="14%" />
					</colgroup>
					<tbody>
					<c:forEach var="ncconfig" items="${deamon_data2}">
						<tr>
							<td>${ncconfig.ncd_name}</td>
							<td><c:if test="${ndconfig.ncd_status == 1}"><span>동작</span></c:if>
								<c:if test="${ndconfig.ncd_status == 2}"><span>중지</span></c:if>
								<c:if test="${ndconfig.ncd_status == 3}"><span>재시작</span></c:if>
								</td>
							<td>${ncconfig.ncd_path}</td>
							<td><a class="btn_st01" href="javascript:update_deamon('${ncconfig.ncd_seq}', '${ncconfig.ncd_name}', '${ncconfig.ncd_div}', '${ncconfig.ncd_status}', '${ncconfig.ncd_path}');"><span>수정</span></a></td>
							<td>
								<a href="javascript:edit_use_yn('${ncconfig.ncd_seq}', '${ncconfig.ncd_use_yn}');" class="btn_st01">
								<c:if test="${ncconfig.ncd_use_yn == 1}"><span>미사용</span></c:if>
								<c:if test="${ncconfig.ncd_use_yn == 0}"><span>사용</span></c:if></a>	
							</td>						
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			
			<form id="insert_deamon2" method="POST">
			<div class="bbsList" style="margin-top:30px">
				<table style="padding-top:30px;" class="boardList">
					<colgroup>					
						<col width="10%" />
						<col width="18%" />
						<col width="10%" />
						<col width="15%" />
						<col width="10%" />
						<col width="20%" />
						<col width="10%" />
					</colgroup>	
					<thead>	
					<tr id="insert_area">					
						<th>데몬 명:</th>
						<td>
						<input type="hidden" id="ncd_seq" name="ncd_seq"/> <!-- 각 데몬의 고유 관리 번호 -->
						<input  class="frmBox" type="text" id="ncd_name" name="ncd_name" style="width:80%; text-align: center;" placeholder="ex) recv" /></td>
						<th>동작 상태:</th>
						<td>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="1" />&nbsp;동작<span style="padding-left: 10px"></span>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="2" />&nbsp;중지<span style="padding-left: 10px"></span>
						<input class="frmBox" type="radio" id="ncd_status" name="ncd_status" value="3" />&nbsp;재시작
						</td>
						<th>실행 경로:</th>
						<td ><input type="text" class="frmBox" id="ncd_path" name="ncd_path" style="width:80%;  text-align: center;" placeholder="ex) /usr/local/" />
						<input type="hidden" id="ncd_div" name="ncd_div" value="2"/> <!-- 송수신 구분 -->
						</td>
						<td id="register2"><a href="javascript:insert_deamon('2');" style="padding-right: 10px" class="btn_st04"><span>등록</span></a><a href="javascript:location.replace('/config_deamon.do');"  class="btn_st04"><span>취소</span></a></td>						
					</tr>		
					</thead>		
				</table>
			</div>
			</form>
		</div>
		<!-- 수신 측 서비스 데몬 설정 end -->
		<%@ include file="../include/footer.jsp"%>
	</div>
</body>
</html>