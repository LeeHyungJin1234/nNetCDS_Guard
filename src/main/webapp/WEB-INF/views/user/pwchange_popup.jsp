<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNet-CSG Manager</title>
<link rel="stylesheet" type="text/css" href="/css/base.css" />
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<script type="text/javascript" src="/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript">
	function modify(){
		var check = inputCheck();
		if( check ){
			if (confirm("변경하시겠습니까?")) {
				var formData = $("form[name=form]").serialize();
				
				$.ajax({        
					type : "POST",  
					url : "pwchange_useredit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("변경되었습니다.");
							self.close();
						}else if(data=="not_match_passwd"){
							alert("현재 비밀번호가 일치하지 않습니다.");
						}else if(data=="not_rule"){
							alert("비밀번호는 영대소문자, 숫자, 특수문자 3개 조합으로 입력해주세요.");
						}else if(data=="not_enough_length"){
							alert("비밀번호는 최소 9자이상 127자 이하로 입력하셔야 합니다.");
						}else{
							alert(data);
						}
					},
					error: function(){
						alert('변경 작업 중 에러가 발생하였습니다.');
					}
				});
			}
		}
	}

 	function inputCheck(){
 		var form = document.form;
 		
 		if(form.orig_passwd.value.length < 1){
			alert('현재 비밀번호를 입력하세요.');
   			form.orig_passwd.focus();
   			return false;
  		}
 		if(form.new_passwd.value.length < 1){
			alert('새 비밀번호를 입력하세요.');
   			form.new_passwd.focus();
   			return false;
  		}
  		if(form.new_passwd_confirm.value.length < 1){
   			alert('새 비밀번호 확인을 입력하세요.');
   			form.new_passwd_confirm.focus();
   			return false;
   		}
  		if(form.orig_passwd.value == form.new_passwd.value){
   			alert('현재 비밀번호와 새 비밀번호가 동일합니다.');
  	 		return false;
  		}
  		if(form.new_passwd.value.length < 9){
   			alert('비밀번호는 최소 9자이상 입력하셔야 합니다.');
  	 		form.new_passwd.focus();
   			return false;
  		}
  		if(form.new_passwd.value.length > 127){
   			alert('비밀번호는 최대 127자이하로 입력하셔야 합니다.');
  	 		form.new_passwd.focus();
   			return false;
  		}
  		var check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$/;
  		if (!check.test(form.new_passwd.value)){
			alert("비밀번호는 영문, 숫자, 특수문자 3개 조합으로 입력해주세요.");
			return false;
		}
  		if(form.new_passwd.value != form.new_passwd_confirm.value){
   			alert('비밀번호 확인이 일치하지 않습니다.');
  	 		return false;
  		}
  		
  		return true;
 	}
</script>
</head>
<body>
<div id="pop">
	<div id="popHeader"><h1>| 비밀번호 변경</h1></div>
	
	<form id='form' name='form' method="post">
		<div id="popContainer">
			<table cellpadding="0" cellspacing="0" summary="" class="popTable">
				<caption></caption>
				<colgroup>
					<col width="150"/><col width="*"/>
				</colgroup>
				<tbody>
				<tr>
					<th>현재 비밀번호</th>
					<td><input type="password" name="orig_passwd" style="width:98%;" maxlength="127"></td>
				</tr>
				<tr>
					<th>새 비밀번호</th>
					<td><input type="password" name="new_passwd" style="width:98%;" maxlength="127"></td>
				</tr>
				<tr>
					<th>새 비밀번호 확인</th>
					<td><input type="password" name="new_passwd_confirm" style="width:98%;" maxlength="127"></td>
				</tr>
				</tbody>
			</table>
			<div class="rtBtn">
				<a href="javascript:modify();" class="btn_st04"><span>변경</span></a>
				<a href="javascript:self.close();" class="btn_st04"><span>취소</span></a>
			</div>
		</div>
	</form>
</div>
</body>
</html>