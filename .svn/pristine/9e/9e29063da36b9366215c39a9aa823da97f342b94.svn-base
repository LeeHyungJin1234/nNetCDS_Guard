<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" >
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
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/setting.css" />
<link rel="stylesheet" href="/css/style.css" />
<%@ include file="/WEB-INF/views/include/css/setting_css.jsp"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

<script type="text/javascript">
//리스트에서 사용 여부 수정
function change_checkbox(el) {
  var useyn = 0
  if (el.checked) {
    useyn = 1
  }
  $.ajax({
    type: "POST",
    url: "dooray_user_useyn.do",
    data: "usr_idx=" + el.value + "&usr_status=" + useyn,
    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    success: function (data) {
      if (data == "true") {
        location.replace('/dooray_user_list.do');
      } else {
        alert(data);
      }
    },
    error: function () {
      alert("<spring:message code='alert.errorupdate'/>");
    }
  });
}
//사용자 삭제
function delete_user(usr_idx) {
	if(confirm("<spring:message code='alert.delete'/>")){
		$.ajax({
			type : "POST",
			url : "dooray_user_delete.do",
			data : "usr_idx=" + usr_idx,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data == "true"){
					alert("<spring:message code='alert.deleted'/>");
					location.replace('/dooray_user_list.do');
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
//정보 수정
function goModify(user_id) {
	$("#modifySubmit input[name=usr_account]").val(user_id);
	$("#modifyform input[name=usr_srcip]").val($("#tr_"+user_id+" > td:eq(2)").text());
	$("#modifyform input[name=usr_url]").val($("#tr_"+user_id+" > td:eq(3)").text());
	$("#modifyform input[name=usr_dstip]").val($("#tr_"+user_id+" > td:eq(4)").text());
	$("#modpopup").fadeIn();	
}
function pw_verification(id, pw, pw_confirm){
	var flag = 99;
	
	 //RSA 암호화 생성
	var rsa = new RSAKey();
	rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);
		
    if(getByte(pw) > 127){
    	flag = 2; 
    	return flag;
    }else{    		
    	pw = rsa.encrypt(pw);
	}
	if( getByte(pw_confirm) > 127){
    	flag = 2; 
    	return flag;
	}else{
		pw_confirm = rsa.encrypt(pw_confirm);
    }
	 $.ajax({
        type: "POST",
        url: "dooray_pw_check.do",
        async: false,
        data: {
        	new_id: id,
        	usr_pwd: pw,
            usr_pw_confirm: pw_confirm
        },
        datatype: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        success: function (data) {
			if (data == "not_rule") {
			  // Not enough password rule
			  flag = 1;
			} else if (data == "not_enough_length") {
			  // Not enough password length
			  flag = 2;
			} else if (data == "not_same_confirm") {
			  flag = 3;
			} else if (data == "not_rule") {
			  flag = 4;
			} else if (data ==="not_continuity") {
			  flag = 5;
			} else if (data == "not_qwer") {
			  flag = 6;
			} else if (data == "not_id_pw") {
			  flag = 7;
			} else if (data == "rsa_error") {
			  flag = 8;
			} else{
				flag = 0;
			}
        },
        error: function () {
        	alert("<spring:message code='user.errorpw'/>");
    	},
    	complete : function () {
    		pw=null;
    		pw=null;
    		pw=null;
    		pw_confirm=null;
    		pw_confirm=null;
    		pw_confirm=null;
    	}
    });

	return flag;	
}
//Registered text check
function modify() {
	let pw_flag=99;
	let usr_account = $("#modifySubmit input[name=usr_account]").val(); 
	let form = document.getElementById("modifyform"); 

	if (form.orig_passwd.value == "") {
    	alert("<spring:message code='login.curpwinput'/>");
        return;
    } else if (form.new_passwd.value != "" || form.new_passwd_confirm.value != "") {
        pw_flag = pw_verification(usr_account, form.new_passwd.value, form.new_passwd_confirm.value);
        if (pw_flag == 1) {
          alert("<spring:message code='login.pwcombi'/>");
          return;
        } else if (pw_flag == 2) {
          alert("<spring:message code='user.pwleast9more127'/>");
          return;
        } else if (pw_flag == 3 || form.new_passwd_confirm.value == "") {
          alert("<spring:message code='login.pwnotmatch'/>");
            return;
        } else if (pw_flag == 4) {
          alert("<spring:message code='login.pwcombi'/>");
          return;
        } else if (pw_flag == 5) {
          alert("<spring:message code='login.pwsamecont'/>");	
          return;
        } else if (pw_flag == 6) {
          alert("<spring:message code='login.consecutive3pwd'/>");
          return;
        } else if (pw_flag == 7) {
            alert("<spring:message code='user.idpw'/>");
              return;
        } else if (pw_flag == 8) {
             alert("RSA Error");
             return;
        }
    }
	if(pw_flag == 99 || pw_flag == 0 ){
		if (confirm("<spring:message code='alert.edit'/>") == true) {
		    //RSA 암호화 생성
			var rsa = new RSAKey();
			rsa.setPublic(document.getElementById("RSAModulus").value, document.getElementById("RSAExponent").value);

			var subfrm = document.getElementById("modifySubmit");
			subfrm.usr_account.value = rsa.encrypt(usr_account);
			subfrm.orig_passwd.value = rsa.encrypt(form.orig_passwd.value);
			
			subfrm.new_passwd.value = rsa.encrypt(form.new_passwd.value);
			subfrm.new_passwd_confirm.value = rsa.encrypt(form.new_passwd_confirm.value);
			
			subfrm.usr_srcip.value = form.usr_srcip.value;
			subfrm.usr_url.value = form.usr_url.value;
			subfrm.usr_dstip.value = form.usr_dstip.value;

			form.orig_passwd.value="";
			form.orig_passwd.value="";
			form.orig_passwd.value="";
			form.new_passwd.value="";
			form.new_passwd.value="";
			form.new_passwd.value="";
			form.new_passwd_confirm.value="";
			form.new_passwd_confirm.value="";
			form.new_passwd_confirm.value="";
			
			var formData = $("form[id=modifySubmit]").serialize();
			$.ajax({
				type : "POST",  
				url : "dooray_update_user.do",
				data :  formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.changed'/>");
						location.replace('/dooray_user_list.do');
			        } else if (data === "present") {
					  alert("<spring:message code='login.sameid'/>");
					} else if (data === "not_allow") {
					  alert("<spring:message code='user.notuseid'/>");
					} else if (data === "not_enough_length") {
					  alert("<spring:message code='user.pwleast9more127'/>");
					} else if (data === "not_pw_confirm") {
					  alert("<spring:message code='user.pwinputconfirm'/>");
					} else if (data === "not_id_length") {
					  alert("<spring:message code='user.idlengthfrom6to64'/>");
					} else if (data === "not_name_length64") {
					  alert("<spring:message code='user.nameexceed'/>");
					} else if (data == "not_rule") {
					  alert("<spring:message code='user.pwin4'/>");
					} else if (data === "not_continuity") {
					  alert("<spring:message code='login.pwsamecont'/>");
					} else if (data === "not_qwer") {
					  alert("<spring:message code='login.consecutive3pwd'/>");
					} else if (data === "not_id_pw") {
					  alert("<spring:message code='user.idpw'/>");
					} else if(data=="not_match_passwd"){
						alert("<spring:message code='login.curpwnotmatch'/>");
					} else if (data == "not_same_pw") {
			              alert("<spring:message code='login.newcursame'/>");
					} else if (data == "same_with_prev_pw") {
			            alert("<spring:message code='login.pwnoprev'/>");
					} else {
					  alert(data);
					}
				},
				error: function(){
					alert('등록 작업 중 에러가 발생하였습니다.');
				},
		    	complete : function () {
		    		subfrm.usr_account.value="";
					subfrm.orig_passwd.value="";
					subfrm.orig_passwd.value="";
					subfrm.orig_passwd.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd.value="";
					subfrm.new_passwd_confirm.value="";
					subfrm.new_passwd_confirm.value="";
					subfrm.new_passwd_confirm.value="";
					subfrm.usr_srcip.value="";
					subfrm.usr_url.value="";
					subfrm.usr_dstip.value="";
					$(".popup").fadeOut();
		    	}
			});
		}
	}
}

$(function(){
	$(".popup .pop-wrapper .header_bar p,.cancelbtn").click(function(){
		$(".popup").fadeOut();
	})
});
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	
	<section id="content">
		<!-- left menu start -->
		<%@ include file="../include/left.jsp"%>
		<!-- left menu end -->
		<div id="right-content">
			<div class="right-wrapper">
				<%@ include file="../include/tabmenu.jsp"%>
					
<!-- 				<div class="btn-section"> -->
<!-- 					<a href="#" id="red-top-btn"> -->
<!-- 						<img src="/img/btnicon1.png" style="margin:6px 8px 0 0;"> -->
<%-- 							<spring:message code='clientUsr.account'/>						 --%>
<%--          					<spring:message code='btn.registration'/> --%>
<!-- 					</a> -->
<!-- 				</div> -->
				
				<div class="content">
					<table>
					<colgroup>
						<col width="74px"/>
						<col width="148px"/>
						<col width="127px"/>
						<col width="236px"/>
						<col width="127px"/>
						<col width="192px"/>
						<col width="100px"/>
						<col width="50px"/>
						<col width="50px"/>
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
					            <th><span><spring:message code='log.turn'/></span></th>
					            <th><span>ID</span></th>
					            <th><span><spring:message code='log.sourceip'/></span></th>
					            <th><span>URL</span></th>
					            <th><span><spring:message code='log.destnationip'/></span></th>
					            <th><span><spring:message code='user.registdate'/></span></th>
					            <th><span><spring:message code='common.useyn'/></span></th>
								<th><span><spring:message code='btn.modify'/></span></th>
					            <th><span><spring:message code='user.delete'/></span></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="ncclient" items="${getClientUser}" varStatus="status">
							<tr class="text-medium text-main-color" id="tr_${ncclient.usr_account}">
								<td align="center" style="padding:25px 0;">${status.count}</td>
								<td align="center" style="padding:25px 0;">${ncclient.usr_account}</td>
								<td align="center" style="padding:25px 0;">${ncclient.usr_srcip}</td>
								<td align="center" style="padding:25px 0;">${ncclient.usr_url}</td>
								<td align="center" style="padding:25px 0;">${ncclient.usr_dstip}</td>
								<td align="center" style="padding:25px 0;">${ncclient.usr_regdate}</td>
								<td align="center" style="padding:25px 0;">
									<c:choose>
										<c:when test="${ncclient.usr_status eq 0}">
											<c:set var="check_state" value="unchecked"/>
										</c:when>
										<c:otherwise>
											<c:set var="check_state" value="checked"/>
										</c:otherwise>
									</c:choose>
									<label class="switch">
									  <input type="checkbox" class="${check_state}" value="${ncclient.usr_idx}" onchange="change_checkbox(this)" ${check_state}>
								      <span class="slider"></span>
								    </label>
								</td>
								<td align="center">
									<a href="javascript:goModify('${ncclient.usr_account}');" class="modify-btn"><img src="/img/modify.png"></a>
								</td>
								<td align="center">
									<a href="javascript:delete_user('${ncclient.usr_idx}');" class="delete-btn">
								      <img src="/img/delete.png"></a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr>
            					<td colspan="4"></td>
							</tr>
						</tfoot>
					</table>
				</div>    
			</div>
		</div>
	</section>
	
	<div id="modpopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
            	<h1>내 정보 수정</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form method="post" id="modifySubmit" name="modifySubmit" onsubmit="return false;">
				<input type="hidden" id="RSAModulus" value="${RSAModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
				<input type="hidden" id="RSAExponent" value="${RSAExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
				<input type="hidden" name="usr_account"/>
				<input type="hidden" name="orig_passwd"/>
				<input type="hidden" name="new_passwd"/>
				<input type="hidden" name="new_passwd_confirm"/>
				<input type="hidden" name="usr_srcip"/>
				<input type="hidden" name="usr_url"/>
				<input type="hidden" name="usr_dstip"/>
			</sf:form>
			<sf:form id="modifyform" name="modifyform" method="post">
				<div id="modcontent">
					<div class='section01'>
						<h2><spring:message code='user.requiredinput'/></h2><p class='border_title'></p>
						<ul>
							<li>
								<label><spring:message code='login.currentpw'/></label>
								<input type="password" name="orig_passwd" maxlength="127" style="width:390px;">
							</li>
							<li>
								<label><spring:message code='user.newpw'/></label>
								<spring:message code="pw.inputchange" var="inputchange"/>
								<input type="password" name="new_passwd" maxlength="127"  style="width:390px;font-family: caption;" placeholder="${inputchange}">
							</li>
							<li>
								<label><spring:message code='login.newpwconfi'/></label>
								<input type="password" name="new_passwd_confirm" maxlength="127"  style="width:390px;font-family: caption;" placeholder="${inputchange}">
							</li>
						</ul>
					</div>
					<div class='section01'>
						<h2><spring:message code='user.optionalinput'/></h2><p class='border_title'></p>
						<ul>
							<li>
								<label><spring:message code='log.sourceip'/></label>
								<input type="text" name="usr_srcip" style="width:390px;">
							</li>
							<li>
								<label>URL</label>
								<input type="text" name="usr_url" style="width:390px;" >
							</li>
							<li>
								<label><spring:message code='log.destnationip'/></label>
								<input type="text" name="usr_dstip" style="width:390px;">
							</li>
						</ul>
					</div>
				</div>
				
				<p class="border_bottom"></p>
				<div class="bottom-section">
					<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
					<a href="javascript:modify();" id="white-bottom-btn2" style="margin-right:12px;" class="white-bottom-btn2"><img src="/img/modify.png" style="margin:3px 20px 0 0;"><spring:message code='btn.modify2'/></a>
				</div>
			</sf:form>
		</div>
	</div>

</body>
</html>