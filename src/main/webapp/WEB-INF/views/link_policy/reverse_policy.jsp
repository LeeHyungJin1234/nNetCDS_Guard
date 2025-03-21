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
<%@ include file="/WEB-INF/views/include/css/policy_css.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	var rowcnt=1;
	var mrowcnt=1;

	// 수정화면 호출
	function goModify(nlp_seq, rcnt) {
		mrowcnt = rcnt;
		$.ajax({
			type : "POST",
			url : "/policy_editVw.do?nlp_seq="+nlp_seq,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				$('#modcontent').html(data);
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	// 리스트에서 삭제
	function delete_policy(nlp_seq) {
		if(confirm("<spring:message code='alert.delete'/>")){
	    	$.ajax({        
				type : "POST",  
				url : "policy_delete.do",   
				data : "nlp_seq="+nlp_seq, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					if(data == "true"){
						alert("<spring:message code='alert.deleted'/>");
						location.replace('/reverse_policy.do');
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
    // 선택삭제
    function delete_all() {
    	if(check_yn()){
			if(confirm("<spring:message code='policy.wantdelctrl'/>")){
				var formData = $("form[id=listform]").serialize();
				$.ajax({
					type : "POST",
					url : "policy_delall.do",
					data : formData,
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						if(data != null)  {
							var response = data.split("|");
							if(response[0] == "true"){
								alert("<spring:message code='alert.deleted'/>");
								location.replace('/reverse_policy.do');
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
    // 선택삭제 정합성 확인
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
    		alert("<spring:message code='policy.seldelpolicy'/>");
    		return false;
    	}else{
    		return true;
    	}
    }
    // 리스트에서 사용 여부 수정
    function change_checkbox(el){
		var useyn=0
		if(el.checked){
			useyn=1
		}
		$.ajax({
			type : "POST",
			url : "policy_use_change.do",
			data : "nlp_seq="+el.value+"&nlp_use_yn="+useyn,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data == "true"){
					location.replace('/reverse_policy.do');
				}else{
					alert(data);
				}
			},
			error: function(){
				alert('수정 작업 중 에러가 발생하였습니다.');
			}
		});
	}
</script>
<script type="text/javascript">
	// 등록
    function writeCheck() {
    	var form = document.getElementById("writeform");
    	var check = inputCheck(form);
		if( check ){
	    	if(confirm("<spring:message code='alert.register'/>")){
	    		
	    		if(form.use_yn.checked){
	    		    form.nlp_use_yn.value=1;
	    		} else {
	    		    form.nlp_use_yn.value=0;
	    		}

	    		var formData = $("form[id=writeform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "policy_regit.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.registered'/>");
							location.replace('/reverse_policy.do');
						}else if(data == "dupli_name"){
							alert("<spring:message code='policy.samepolicy'/>");
						}else if(data == "dupli_prog"){
							alert("동일한 프로그램이 등록되어 있습니다.");
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
    
	// 수정
    function update_policy() {
    	var form = document.getElementById("modifyform");
    	var check = inputCheck(form);
		if( check ){
			if(confirm("<spring:message code='alert.edit'/>")){
				
				if(form.use_yn.checked){
	    		    form.nlp_use_yn.value=1;
	    		} else {
	    		    form.nlp_use_yn.value=0;
	    		}
				
	    		var formData = $("form[id=modifyform]").serialize();
				$.ajax({        
					type : "POST",  
					url : "policy_update.do",   
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "true"){
							alert("<spring:message code='alert.changed'/>");
							location.replace('/reverse_policy.do');
						}else if(data == "dupli_name"){
							alert("<spring:message code='policy.samepolicy'/>");
						}else if(data == "dupli_prog"){
							alert("동일한 프로그램이 등록되어 있습니다.");
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
    // 등록,수정 정합성 체크
    function inputCheck(form){
    	var check = /^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$/i;
    	
    	if( !form.nlp_name.value ) {
			alert("<spring:message code='policy.policynminput'/>");
			form.nlp_name.focus();
			return false;
		}
    	
    	if(getByte(form.nlp_name.value)>64){
			alert("<spring:message code='policy.policynmexceed'/>");
			return false;
		}
    	
    	if( !form.nlp_pro_id.value ) {
			alert("외부 프로그램명을 선택하세요.");
			return false;
		}
    	
    	if( !form.nlp_rxpro_id.value ) {
			alert("내부 프로그램명을 선택하세요.");
			return false;
		}
    	
    	if (typeof(form.source_ip.length) == "undefined") {
    		if( !form.source_ip.value ) {
    			alert("출발지 IP를 입력하세요.");
    			form.source_ip.focus();
    			return false;
    		}
        	if(!check.test(form.source_ip.value)){
        		alert("출발지 IP가 IP 주소형식이 아닙니다.");
        		return false;
        	}
    	} else {
    	    for (var i=0; i<form.source_ip.length; i++) {
    	    	if( !form.source_ip[i].value ) {
        			alert("출발지 IP를 입력하세요.");
        			form.source_ip[i].focus();
        			return false;
        		}
            	if(!check.test(form.source_ip[i].value)){
            		alert("출발지 IP가 IP 주소형식이 아닙니다.");
            		return false;
            	}
    	    }
    	}
    	
    	if( !form.nlp_rcv_port.value ) {
			alert("<spring:message code='policy.selectrcvport'/>");
			return false;
		}
    	
    	var checkp = /^\d+$/;
		if (!checkp.test(form.nlp_rcv_port.value)) {
			alert("출발지 수신 포트 입력이 올바르지 않습니다.");
			return false;
		}
		
		if(form.nlp_rcv_port.value>65535) {
			alert("출발지 수신 포트 범위는 0 - 65535 입니다.");
			return false;
		}
		
		if( !form.nlp_trans_port.value ) {
			alert("일방향 전송 포트를 입력하세요.");
			form.nlp_trans_port.focus();
			return false;
		}

		if (!checkp.test(form.nlp_trans_port.value)) {
			alert("일방향 전송 포트 입력이 올바르지 않습니다.");
			return false;
		}
		
		if(form.nlp_trans_port.value>65535) {
			alert("일방향 전송 포트 범위는 0 - 65535 입니다.");
			return false;
		}
		
		if (typeof(form.destination_ip.length) == "undefined") {
    		if( !form.destination_ip.value ) {
    			alert("도착지 IP를 입력하세요.");
    			form.destination_ip.focus();
    			return false;
    		}
        	if(!check.test(form.destination_ip.value)){
        		alert("도착지 IP가 IP 주소형식이 아닙니다.");
        		return false;
        	}
        	if( !form.destination_port.value ) {
    			alert("도착지 포트를 입력하세요.");
    			form.destination_port.focus();
    			return false;
    		}
        	if (!checkp.test(form.destination_port.value)) {
    			alert("도착지 포트 입력이 올바르지 않습니다.");
    			return false;
    		}
    		if(form.destination_port.value>65535) {
    			alert("도착지 포트 범위는 0 - 65535 입니다.");
    			return false;
    		}
    	} else {
    	    for (var i=0; i<form.destination_ip.length; i++) {
    	    	if( !form.destination_ip[i].value ) {
        			alert("도착지 IP를 입력하세요.");
        			form.destination_ip[i].focus();
        			return false;
        		}
            	if(!check.test(form.destination_ip[i].value)){
            		alert("도착지 IP가 IP 주소형식이 아닙니다.");
            		return false;
            	}
            	if( !form.destination_port[i].value ) {
        			alert("도착지 포트를 입력하세요.");
        			form.destination_port[i].focus();
        			return false;
        		}
            	if (!checkp.test(form.destination_port[i].value)) {
        			alert("도착지 포트 입력이 올바르지 않습니다.");
        			return false;
        		}
        		if(form.destination_port[i].value>65535) {
        			alert("도착지 포트 범위는 0 - 65535 입니다.");
        			return false;
        		}
    	    }
    	}
		
		return true;
    }
    
 	// 초기화
    function init(){
    	var form = document.getElementById("writeform");
    	document.getElementsByName('source_ip')[0].value="";
    	form.nlp_rcv_port.value=0;
    	form.nlp_trans_port.value=0;
    	document.getElementsByName('destination_ip')[0].value="";
    	document.getElementsByName('destination_port')[0].value=0;
    	document.getElementById('route_field').innerHTML="";
    }
    // 프로토콜 변경
    function change_top(ntc_seq, idx){
    	//init();
    	$.ajax({
			type : "POST",
			url : "change_conts_top.do",
			data : "ntc_seq="+ntc_seq,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(idx==1){
					document.getElementById("conts_area1").innerHTML = data; // 등록
				}else{
					document.getElementById("conts_area2").innerHTML = data; // 수정
				}
			},
			error: function(){
				alert('서비스 호출 중 에러가 발생하였습니다.');
			}
		});
    }
    // 등록 팝업 경로 추가/삭제 시작
    function add_div(){
    	++rowcnt;
    	var div = document.createElement('div');
    	div.setAttribute('id', 'rowcnt'+rowcnt);
    	div.setAttribute('name', 'routeinput');
    	div.innerHTML = '<div class="path-wrapper2">'+
		'<h2><a href="javascript:remove_div('+rowcnt.toString()+');"><img src="/img/box2_icon.png">경로'+rowcnt.toString()+'</a></h2>'+
		'<ul class="box box2_1">'+
			'<li>'+
				'<label for="text" style="font-size:13px;">출발지 IP</label><br/>'+
				'<input type="text" name="source_ip">'+
			'</li>'+
			'<li><p>* 출발지 수신 포트와 일방향 전송 포트는 동일</p></li>'+
			'<li>'+
				'<label style="font-size:13px;">도착지 IP:포트</label><br/>'+
				'<input type="text" name="destination_ip" class="input_1">:<input type="text" name="destination_port" class="input_2">'+
			'</li>'+
		'</ul>'+
		'</div>';
    	document.getElementById('route_field1').appendChild(div);
    }
    function remove_div(idx){
    	var elem = document.getElementById("rowcnt"+idx);
    	elem.parentNode.removeChild(elem);
    	
    	//다시 부여...
    	for (var i=0; i<document.getElementsByName('routeinput').length; i++) {
    		document.getElementsByName('routeinput')[i].setAttribute('id', 'rowcnt'+(i+2));
    		document.getElementsByName("routeinput")[i].getElementsByTagName("h2")[0].innerHTML = '<a href="javascript:remove_div('+(i+2)+');"><img src="/img/box2_icon.png">경로'+(i+2)+'</a>';
    	}
    	
    	--rowcnt;
    }
 	// 등록 팝업 경로 추가/삭제 끝
 	// 수정 팝업 경로 추가/삭제 시작
	function add_div1(){
		++mrowcnt;
    	var div = document.createElement('div');
    	div.setAttribute('id', 'mrowcnt'+mrowcnt);
    	div.setAttribute('name', 'mrouteinput');
    	div.innerHTML = '<div class="path-wrapper2">'+
		'<h2><a href="javascript:remove_div1('+mrowcnt.toString()+');"><img src="/img/box2_icon.png">경로'+mrowcnt.toString()+'</a></h2>'+
		'<ul class="box box2_1">'+
			'<li>'+
				'<label for="text" style="font-size:13px;">출발지 IP</label><br/>'+
				'<input type="text" name="source_ip">'+
			'</li>'+
			'<li><p>* 출발지 수신 포트와 일방향 전송 포트는 동일</p></li>'+
			'<li>'+
				'<label style="font-size:13px;">도착지 IP:포트</label><br/>'+
				'<input type="text" name="destination_ip" class="input_1">:<input type="text" name="destination_port" class="input_2">'+
			'</li>'+
		'</ul>'+
		'</div>';
    	document.getElementById('route_field2').appendChild(div);
    }
	function remove_div1(idx){
    	var elem = document.getElementById("mrowcnt"+idx);
    	elem.parentNode.removeChild(elem);
    	
    	//다시 부여...
    	for (var i=0; i<document.getElementsByName('mrouteinput').length; i++) {
    		document.getElementsByName('mrouteinput')[i].setAttribute('id', 'mrowcnt'+(i+2));
    		document.getElementsByName("mrouteinput")[i].getElementsByTagName("h2")[0].innerHTML = '<a href="javascript:remove_div1('+(i+2)+');"><img src="/img/box2_icon.png">경로'+(i+2)+'</a>';
    	}
    	
    	--mrowcnt;
    }
	// 수정 팝업 경로 추가/삭제 끝
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
				<div class="top-section">
					<a href="javascript:delete_all();" id="white-top-btn" style="margin-right:6px;"><img src="/img/delete.png" style="margin:3px 8px 0 0;">선택 삭제</a>
					<a href="#" id="red-top-btn" class="foward_btn popup-btn"><img src="/img/btnicon1.png" style="margin:6px 8px 0 0;">정책 등록</a>
				</div>
				<sf:form name='listform' id='listform'>
				<div class="content">
					<table class="scroll">
						<thead>
							<tr class="text-small text-main-color2">
								<th class="group1"><input type="checkbox" id="delck"><label for="delck"><span></span></label></th>
								<th class="group2"><span>정책명(서비스)</span></th>
								<th class="group3"><span>출발지 IP</span></th>
								<th class="group4"><span>출발지 수신 포트</span></th>
								<th class="group5"><span>일방향 전송 포트</span></th>
								<th class="group6"><span>도착지 IP:포트</span></th>
								<th class="group10"><span>프로그램명</span></th>
								<th class="group7"><span>사용 여부</span></th>
								<th class="group8"><span>수정</span></th>
								<th class="group9"><span>삭제</span></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="policy" items="${policyList}" varStatus="status">
							<tr class="text-medium text-main-color">
								<td class="list-group1"><input type="checkbox" name="delck" value="${policy.nlp_seq}" id="delck${policy.nlp_seq}"><label for="delck${policy.nlp_seq}"><span></span></label></td>
								<td class="list-group2">
									<c:choose>
									<c:when test="${'Database' eq policy.ntc_name}">
										<img src="/img/icon_database.png" <c:if test="${policy.nlp_use_yn==0}">class="opacity2"</c:if>>
									</c:when>
									<c:when test="${'OPC' eq policy.ntc_name}">
										<img src="/img/icon_opc.png" <c:if test="${policy.nlp_use_yn==0}">class="opacity2"</c:if>>
									</c:when>
									<c:when test="${'Modbus/TCP' eq policy.ndc_name or 'Modbus/UDP' eq policy.ndc_name}">
										<img src="/img/icon_modbus.png" <c:if test="${policy.nlp_use_yn==0}">class="opacity2"</c:if>>
									</c:when>
									<c:otherwise>
										<img src="/img/icon_${fn:toLowerCase(policy.ndc_name)}.png" <c:if test="${policy.nlp_use_yn==0}">class="opacity2"</c:if>>
									</c:otherwise>
									</c:choose>
										<span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>${policy.nlp_name}(${policy.ndc_name})</span>
								</td>
								<td align="center" class="list-group3"><span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>
								<c:forEach var="route" items="${routeList}" begin="${status.index}" end="${status.index}">
								<c:set var="counter" value="${fn:length(route)} " />
									<c:forEach var="source" items="${route}">
										${source.npr_source_ip}<br/>
									</c:forEach>
								</c:forEach></span></td>
								<td align="center" class="list-group4"><span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>${policy.nlp_rcv_port}</span></td>
								<td align="center" class="list-group5"><span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>${policy.nlp_trans_port}</span></td>
								<td align="center" class="list-group6"><span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>
								<c:forEach var="route" items="${routeList}" begin="${status.index}" end="${status.index}">
									<c:forEach var="destination" items="${route}">
										${destination.npr_destination_ip}:${destination.npr_destination_port}<br/>
									</c:forEach>
								</c:forEach></span></td>
								<td align="center" class="list-group10"><span <c:if test="${policy.nlp_use_yn==0}">class="opacity4"</c:if>>${policy.ncp_name}(외부)<br>${policy.rx_ncp_name}(내부)</span></td>
								<td align="center" class="list-group7">
									<c:choose>
									<c:when test="${'0' eq policy.nlp_use_yn}">
										<label class="switch"><input type="checkbox" class="unchecked" value="${policy.nlp_seq}" onchange="change_checkbox(this)"><span class="slider"></span></label>
									</c:when>
									<c:otherwise>
										<label class="switch"><input type="checkbox" class="checked" value="${policy.nlp_seq}" onchange="change_checkbox(this)" checked><span class="slider"></span></label>
									</c:otherwise>
									</c:choose>
								</td>
								<td align="center" class="list-group8"><a href="javascript:goModify('${policy.nlp_seq}','${counter}');" class="modify-btn popup-btn"><img src="/img/modify.png"></a></td>
								<td align="center" class="list-group9"><a href="javascript:delete_policy('${policy.nlp_seq}');" class="delete-btn"><img src="/img/delete.png"></a></td>
							</tr>
						</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
							</tr>
						</tfoot>
					</table>
				</div>
				</sf:form>
			</div>
		</div>
	</section>
	<!-- left menu end -->

	<div id="wripopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1>전송통제정책 정보 등록</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="writeform" modelAttribute="policyToRegit" method="POST">
			<sf:hidden path="nlp_div" value="3" /> <!-- 정/역방향 구분 -->
			<sf:hidden path="nsc_seq" value="1" />
			<sf:hidden path="nlp_use_yn" />
			<div class="left-content">
				<ul>
					<li>
						<label>정책명</label>
						<sf:input path="nlp_name" />
					</li>
					<li>
						<label>프로토콜</label>
						<sf:select path="ntc_seq" onChange="change_top(this.value, 1);">
							<c:forEach var="top" items="${topConts}">
								<option value="${top.ntc_seq}">${top.ntc_name}</option>
							</c:forEach>
						</sf:select>
					</li>
					<li>
						<label>서비스</label>
						<sf:select path="ndc_seq" id="conts_area1">
							<c:forEach var="detail" items="${detailConts}">
								<option value="${detail.ndc_seq}">${detail.ndc_name}</option>
							</c:forEach>
						</sf:select>
					</li>
					<li>
						<label>외부 프로그램명</label>
						<sf:select path="nlp_pro_id">
							<c:forEach var="program" items="${programList}">
								<option value="${program.ncp_seq}">${program.ncp_name}</option>
							</c:forEach>
						</sf:select>
					</li>
					<li>
						<label>내부 프로그램명</label>
						<sf:select path="nlp_rxpro_id">
							<c:forEach var="program" items="${rxprogramList}">
								<option value="${program.ncp_seq}">${program.ncp_name}</option>
							</c:forEach>
						</sf:select>
					</li>
					<li class="pop-switch">
						<p>사용여부</p>
						<label class="switch">
							<input name="use_yn" type="checkbox" checked><span class="slider"></span>
						</label>
					</li>
				</ul>
			</div>
			<div class="right-content">
				<div class="path-wrapper1">
					<h2>경로1</h2>
					<ul class="box box1_1">
						<li>
							<label style="font-size:13px;">출발지 IP</label><br>
							<input name="source_ip" />
						</li>
						<li>
							<label style="font-size:13px;">출발지 수신 포트</label><br/>
							<sf:input path="nlp_rcv_port" />
						</li>
						<li>
							<label style="font-size:13px;">일방향 전송 포트</label><br/>
							<sf:input path="nlp_trans_port" />
						</li>
						<li>
							<label style="font-size:13px;">도착지 IP:포트</label><br/>
							<input name="destination_ip" class="input_1" /> : <input name="destination_port" class="input_2" />
						</li>
					</ul>
				</div>
				<div id="route_field1"></div>
				<div class="path-plus">
					<h2><a href="javascript:add_div();"><img src="/img/box_plus_icon.png" alt="">경로 추가</a></h2>
				</div>
			</div>
			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
				<a href="javascript:writeCheck();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;">등록하기</a>
			</div>
			</sf:form>
		</div>
	</div>
	
	<div id="modpopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1>전송통제정책 정보 수정</h1>
				<p><a href="#"><img src="/img/close.png"></a></p>
			</div>
			<sf:form id="modifyform" modelAttribute="policyToRegit" method="POST">
			<sf:hidden path="nlp_div" value="3" /> <!-- 정/역방향 구분 -->
			<sf:hidden path="nsc_seq" value="1" />
			<sf:hidden path="nlp_use_yn" />
			
			<div id="modcontent"></div>

			<div class="bottom-section">
				<a href="#" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
				<a href="javascript:update_policy();" id="white-bottom-btn2" style="margin-right:12px;" class="white-bottom-btn2"><img src="/img/modify.png" style="margin:3px 20px 0 0;">수정하기</a>
			</div>
			</sf:form>
		</div>
	</div>
	
	<%@ include file="../include/footer.jsp"%>
<script>
	// 팝업창 띄우기
	$(".modify-btn").click(function(){
		$("#modpopup").fadeIn();
	})
	$(".foward_btn").click(function(){
		$("#wripopup").fadeIn();
		$("#route_field1").empty(); // 경로 추가 초기화
		rowcnt=1; // 전역변수 초기화
	})
	$(".popup .pop-wrapper .header_bar p,.cancelbtn").click(function(){
		$(".popup").fadeOut();
	})
</script>
</body>
</html>