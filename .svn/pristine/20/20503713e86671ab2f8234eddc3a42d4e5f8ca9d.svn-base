<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta charset="UTF-8">
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">
<!-- start csrf -->
<%@ include file="../include/csrf.jsp"%>
<!-- csrf end -->
<!-- font -->
<%@ include file="/WEB-INF/views/include/css/table_css.jsp"%>
<!-- <script type="text/javascript" src="/js/jquery.min.js"></script> -->
<!-- <script type="text/javascript" src="/js/jquery-ui.min.js"></script> -->
<script type="text/javascript">
	function loadWhiteList(page){
		$("#whiteContent").load("/white_list.do", "page="+page);
	}
	function log_search() {
		var form = document.getElementById("search");
		var temp = 0;
		if(form.wl_sregdttm.value!=null&&form.wl_sregdttm.value!="" && form.wl_eregdttm.value!=null&&form.wl_eregdttm.value!=""){
			temp = calDateRange(form.mps_strc_stime.value, form.mps_strc_etime.value);
		}
		else if((form.wl_sregdttm.value!=null&&form.wl_sregdttm.value!="" && (form.wl_eregdttm.value==null||form.wl_eregdttm.value==""))
				(form.wl_eregdttm.value!=null&&form.wl_eregdttm.value!="" && (form.wl_sregdttm.value==null||form.wl_sregdttm.value==""))){
			alert("등록일 검색을 위해서는 시작일과 종료일 모두 입력 돠어야 합니다.")
			return;
		}
		if(temp<0){
			alert("<spring:message code='log.dategreater'/>");
			return;
		}
		form.action="white_search.do";
		form.submit();
	}
	
	// white 팝업 띄우기
	function white_popup(event, wl_id){ // 마우스 이벤트 추가
		var sWidth = window.innerWidth;
		var sHeight = window.innerHeight;
	
		var oWidth = $('#whitePopup').width();
		var oHeight = $('#whitePopup').height();
		
		var divTop = event.clientY;
		var divLeft = event.clientX;
		
		// 레이어가 화면 크기를 벗어나면 위치를 바꾸어 배치한다.
		if( divLeft + oWidth > sWidth ) divLeft -= oWidth;
		if( divTop + oHeight > sHeight ) divTop -= oHeight;
	
		// 레이어 위치를 바꾸었더니 상단기준점(0,0) 밖으로 벗어난다면 상단기준점(0,0)에 배치하자.
		if( divLeft < 0 ) divLeft = 0;
		if( divTop < 0 ) divTop = 0;
		
		$("#whitePopup").empty(); // 로딩되기 전까지 이전 잔상이 남음..
		$('#whitePopup').load('/white_popup.do', { 'wl_id':wl_id});
		$('#whitePopup').css({
			 "top": divTop
		     ,"left": divLeft
		     ,"position": "fixed"
		}).show();
	}
	$(document).ready(function() {
    	//draggable() 함수 	jquery-ul.js 스크립트 파일 안에 선언된 함수
		$("#whitePopup").draggable({containment:'.right-wrapper', scroll:false});
    });
	
	jQuery.curCSS = function(element, prop, val){
		return jQuery(element).css(prop, val);
	};
	
	// 리스트에서 사용 여부 수정
    function change_white(el){
		var useyn=0
		if(el.checked){
			useyn=1
		}
		$.ajax({
			type : "POST",
			url : "white_use_change.do",
			data : "wl_id="+el.value+"&wl_use_yn="+useyn,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data > 0){
			    	loadWhiteList(${page});
				}
			}
			
		});
	}

    function openWhiteForm(wl_id) {
    	$("#whiteFomrPopup").fadeIn();
    	
    	if(wl_id>0){  //  수정
    		$("#whiteFomrPopup > .pop-wrapper > .header_bar > h1").text("White List 정보 수정");
    		document.getElementById("whiteForm").wl_id.value=wl_id;
    	}
    	else{  //  등록
    		$("#whiteFomrPopup > .pop-wrapper > .header_bar > h1").text("White List 정보 등록");
    		document.getElementById("whiteForm").wl_id.value="0";
    		document.getElementById("whiteForm").wl_src_ip.value="";
    		document.getElementById("whiteForm").wl_src_port.value="";
    		document.getElementById("whiteForm").wl_dst_ip.value="";
    		document.getElementById("whiteForm").wl_dst_port.value="";
    		document.getElementById("whiteForm").wl_func1.value="";
    		document.getElementById("whiteForm").wl_func2.value="";
    		document.getElementById("whiteForm").wl_func3.value="";
    		document.getElementById("whiteForm").wl_func4.value="";
    		document.getElementById("whiteForm").wl_use_yn.checked=false;
    	}
    	
    }
    function closeWhiteForm() {
    	$("#whiteFomrPopup").fadeOut();
    }
    function register_check(){

		var form = document.getElementById("whiteForm"); 
		
		if(form.wl_proto_sub_dec.value==""){
			alert("Protocol을 입력하세요");
			form.wl_proto_sub_dec.focus();
			return false;
		}
		
		if(form.wl_src_ip.value==""){
			alert("출발지 IP를 입력하세요");
			form.wl_src_ip.focus();
			return false;
		}else{
			if(!checkIpForm(wl_src_ip.value)){
				alert("입력한 값이 IP 주소 형식이 아닙니다.");
				form.wl_src_ip.focus();
				return false;
			}
		}
		if(form.wl_src_port.value==""){
			alert("출발지 Port를 입력하세요");
			form.wl_src_port.focus();
			return false;
		}else{
			if(isNaN(form.wl_src_port.value) || wl_src_port.value < 0 || wl_src_port.value > 65535){
				alert("출발지 Port 범위는 0 - 65535 입니다.");
				form.wl_src_port.focus();
				return false;
			}
		}
		
		if(form.wl_dst_ip.value==""){
			alert("도착지 IP를 입력하세요");
			form.wl_dst_ip.focus();
			return false;
		}else{
			if(!checkIpForm(wl_dst_ip.value)){
				alert("입력한 값이 IP 주소 형식이 아닙니다.");
				form.wl_dst_ip.focus();
				return false;
			}
		}
		if(form.wl_dst_port.value==""){
			alert("도착지 Port를 입력하세요");
			form.wl_dst_port.focus();
			return false;
		}else{
			if(isNaN(form.wl_dst_port.value) || wl_dst_port.value < 0 || wl_dst_port.value > 65535){
				alert("도착지 Port 범위는 0 - 65535 입니다.");
				form.wl_dst_port.focus();
				return false;
			}
		}

		if(form.wl_func1.value==""){
			alert("Function Code를 입력하세요");
			form.wl_func1.focus();
			return false;
		}
		if(form.wl_func2.value==""){
			alert("Object를 입력하세요");
			form.wl_func2.focus();
			return false;
		}
		if(form.wl_func3.value==""){
			alert("Variation를 입력하세요");
			form.wl_func3.focus();
			return false;
		}
		if(form.wl_func4.value==""){
			alert("Qualifier를 입력하세요");
			form.wl_func4.focus();
			return false;
		}
		
		if (confirm("White List 정보를 등록 하시겠습니까?") == true) {
			$.ajax({
				type : "POST",  
				url : "insert_white.do",
				data :  $("#whiteForm").serialize(), 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == true){
						alert("White List 정보를 등록하였습니다.");
						loadWhiteList(${page});
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
</script>
</head>
<body>
	<div class="listContent">
		<!-- 
		<a href="javascript:deleteWhite();" id="white-top-btn" style="margin-bottom:10px;"><img src="/img/delete.png" style="margin:0px 8px 0 0;">선택 삭제</a>
		-->
<!-- 		<a href="javascript:openWhiteForm(0);" id="red-top-btn" class="popup-btn" style="margin-bottom:10px;"><img src="/img/btnicon1.png" style="margin:6px 8px 0 0;">White List 정보 등록</a> -->
		<form id="frmWhite" name="frmWhite">
			<table class="tbl">
				<colgroup>
					<col width="5%" />
					<col width="10%" />
					<col width="5%" />
					
					<col width="20%" />
					<col width="15%" />
					
					<col width="20%" />
					<col width="15%" />
					
					<col width="10%" />
				</colgroup>
				<thead>
					<tr>
						<!-- 
						<th rowspan="2">
		                	<input type="checkbox" id="all_chk" name="all_chk" onclick="all_checkbox()"><label for="all_chk"><span></span></label>
		                </th>
						<th rowspan="2"><spring:message code='log.turn'/></th>
		                 -->
						<th rowspan="2">ID</th>
						<th rowspan="2">서비스</th>
						<th rowspan="2">구분</th>
						<th colspan="2" class="bLine">출발지</th>
						<th colspan="2" class="bLine">목적지</th>
						<th rowspan="2">등록일</th>
					</tr>
					<tr>
						<th>IP</th>
						<th>Port</th>
						<th>IP</th>
						<th>Port</th>
					</tr>
				</thead>
			    <tbody>
			    <c:choose>
				<c:when test="${pginfo.result != null && !empty pginfo.result}">
			    <c:forEach var="wnwhlist" items="${pginfo.result}" varStatus="status">
					<tr class="trHover">
						<td>${wnwhlist.wl_id}</td>
						<td>${wnwhlist.wl_proto_sub_dec}</td>
						<td>${wnwhlist.strPsInOut}</td>
						<td>${wnwhlist.wl_src_ip}</td>
						<td>${wnwhlist.wl_src_port}</td>
						<td>${wnwhlist.wl_dst_ip}</td>
						<td>${wnwhlist.wl_dst_port}</td>
						<td>${(wnwhlist.wl_regdttm!=null)?fn:substring(wnwhlist.wl_regdttm,0,19):""}</td>
					</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="8"><spring:message code='log.nodata'/></td>
					</tr>
				</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</form>
	</div>
	<!--  페이징 시작 -->
	<%@ include file="../include/paging_script.jsp"%>
	<!--  페이징 끝 -->
	
	<!-- popup start -->
	<div id="whitePopup" style="display:none;z-index:999;"></div>
	<!-- popup end -->
	
	<!-- white Form -->
	<div id="whiteFomrPopup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1>White List 정보 등록</h1>
				<p><a href="javascript:closeWhiteForm();"><img src="/img/close.png"></a></p>
			</div>
			<form id="whiteForm">
				<input type="hidden" name="wl_id" id="wl_id" value="0" />
				<ul>
					<li>
						<div><label for="wl_src_ip">Protocol</label></div>
						<div><input class="frmTxt" type="text" name="wl_proto_sub_dec" id="wl_proto_sub_dec" value="" /></div>
					</li>
					<li>
						<div><label for="wl_src_ip">출발지 IP</label></div>
						<div><input class="frmTxt" type="text" name="wl_src_ip" id="wl_src_ip" value="" /></div>
					</li>
					<li>
						<div><label for="wl_src_port">출발지 Port</label></div>
						<div><input class="frmTxt" type="text" name="wl_src_port" id="wl_src_port" value="" /></div>
					</li>
					<li>
						<div><label for="wl_dst_ip">도착지 IP</label></div>
						<div><input class="frmTxt" type="text" name="wl_dst_ip" id="wl_dst_ip" value="" /></div>
					</li>
					<li>
						<div><label for="wl_dst_port">도착지 Port</label></div>
						<div><input class="frmTxt" type="text" name="wl_dst_port" id="wl_dst_port" value="" /></div>
					</li>
					<li>
						<div><label for="wl_func1">Function Code</label></div>
						<div><input class="frmTxt" type="text" name="wl_func1" id="wl_func1" value="" /></div>
					</li>
					<li>
						<div><label for="wl_func2">Object</label></div>
						<div><input class="frmTxt" type="text" name="wl_func2" id="wl_func2" value="" /></div>
					</li>
					<li>
						<div><label for="wl_func3">Variation</label></div>
						<div><input class="frmTxt" type="text" name="wl_func3" id="wl_func3" value="" /></div>
					</li>
					<li>
						<div><label for="wl_func4">Qualifier</label></div>
						<div><input class="frmTxt" type="text" name="wl_func4" id="wl_func4" value="" /></div>
					</li>
					<li>
						<div><label for="wl_use_yn">사용여부</label></div>
						<div><label class="switch"><input type="checkbox" class="unchecked" name="wl_use_yn" id="wl_use_yn" value="1" /><span class="slider"></span></label></div>
					</li>
				</ul>
				<div class="btnDiv">
					<a href="javascript:closeWhiteForm();" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;"><spring:message code='btn.cancel2'/></a>
					<a href="javascript:register_check();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;"><spring:message code='btn.registration2'/></a>
				</div>
			</form>
		</div>
	</div>
	
	
</body>

<script type="text/javascript">
	// 체크박스 관련
	function all_checkbox(){
		var xx= document.frmWhite.all_chk.checked;
		if(typeof(document.frmWhite.elements['wl_id[]'].length) != 'undefined'){// 다중
	   		for(i=0;i<document.frmWhite.elements['wl_id[]'].length;i++){
	   			if(xx == true){
	   				document.frmWhite.elements['wl_id[]'][i].checked=true;
	   			}else{
	   				document.frmWhite.elements['wl_id[]'][i].checked=false;
	   			}
	   		}
	   	}else{// 단일
	   		if(xx == true){
	   			document.frmWhite.elements['wl_id[]'].checked=true;
	   		}else{
	   			document.frmWhite.elements['wl_id[]'].checked=false;
	   		}
		}
	}
	function white_check_yn(){
		var checkpoint=false;
		if(typeof(document.frmWhite.elements['wl_id[]'].length) != 'undefined'){// 다중
	    	for(i=0;i<document.frmWhite.elements['wl_id[]'].length;i++){
	    		if(document.frmWhite.elements['wl_id[]'][i].checked==true){
	    			checkpoint=true;
	    			break;
	    		}
	    	}
		}
		else{// 단일
			if(document.frmWhite.elements['wl_id[]'].checked) checkpoint=true;
		}
		
		if(checkpoint==false){
			alert("삭제할 White List 정보를 하나 이상을 선택하세요.");
			return false;
		}else{
			return true;
		}
	}

	function deleteWhite(){
		if(white_check_yn()){
			if(confirm("체크된 White List 정보를 삭제 하시겠습니까?")){
				var formData = $("form[id=frmWhite]").serialize();
				$.ajax({        
					type : "POST",  
					url : "deleteWhite.do",
					data : formData, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						alert(data.delCnt+"개의 White List 정보가 삭제 되었습니다.");
					},
					error: function(){
						alert('삭제 작업 중 에러가 발생하였습니다.');
					},
				    complete : function() {
				    	loadWhiteList(${page});
				    }
				});
			}
		}
		
	}
</script>
</html>