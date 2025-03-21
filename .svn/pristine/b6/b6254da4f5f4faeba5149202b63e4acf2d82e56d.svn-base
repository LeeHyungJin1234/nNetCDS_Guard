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
$(document).ready(function() {
	$.ajaxSetup({ cache: false });//ajax 캐시 사용안함
});

function loadList(page){
	$("#whiteContent").load("/ics_anomaly_list.do", 
			"date="+document.getElementById("date").value+"&page="+page);
}

//체크박스 관련
function all_checkbox(){
	var xx= document.frmPs.all_chk.checked;
	if(typeof(document.frmPs.elements['ps_id[]'].length) != 'undefined'){// 다중
   		for(i=0;i<document.frmPs.elements['ps_id[]'].length;i++){
   			if(xx == true){
   				document.frmPs.elements['ps_id[]'][i].checked=true;
   			}else{
   				document.frmPs.elements['ps_id[]'][i].checked=false;
   			}
   		}
   	}else{// 단일
   		if(xx == true){
   			document.frmPs.elements['ps_id[]'].checked=true;
   		}else{
   			document.frmPs.elements['ps_id[]'].checked=false;
   		}
	}
}
function check_yn(){
	var checkpoint=false;
	if(typeof(document.frmPs.elements['ps_id[]'].length) != 'undefined'){// 다중
    	for(i=0;i<document.frmPs.elements['ps_id[]'].length;i++){
    		if(document.frmPs.elements['ps_id[]'][i].checked==true){
    			checkpoint=true;
    			break;
    		}
    	}
	}
	else{// 단일
		if(document.frmPs.elements['ps_id[]'].checked) checkpoint=true;
	}
	
	if(checkpoint==false){
		alert("White List 등록할 ICS 패킷 정보를 하나 이상을 선택하세요.");
		return false;
	}else{
		return true;
	}
}

function regWhitePs(){
	if(check_yn()){
		if(confirm("체크된 ICS 패킷 정보를 White List에 등록 하시겠습니까?")){
			var formData = $("form[id=frmPs]").serialize();
			$.ajax({        
				type : "POST",  
				url : "regWhitePs.do",
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					alert(data.regCnt+"개의 ICS 패킷 정보가 White List에 등록 되었습니다.");
				},
				error: function(){
					alert('White List 등록 작업 중 에러가 발생하였습니다.');
				},
			    complete : function() {    
			    	loadList(${page});
			    }
			});
		}
	}
}

function delWhitePs(ps_id){
	if(confirm("해당 패킷 정보를 White List에서 삭제 하시겠습니까?")){
		
		$.ajax({        
			type : "POST",  
			url : "delWhitePs.do",
			data : "ps_id=" + ps_id+"&date="+document.getElementById("date").value, 
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) {
				alert(data.regCnt+"개의 ICS 패킷 정보가 White List에서 삭제 되었습니다.");
			},
			error: function(){
				alert('White List 삭제 작업 중 에러가 발생하였습니다.');
			},
		    complete : function() {    
		    	loadList(${page});
		    }
		});
	}
	
}

function setDate(day){
	var today = new Date();
	var from = new Date(Date.parse(today) - 1000 * 60 * 60 * 24 * day); //day일수 만큼 전;
	
	var from_month = from.getMonth()+1;
	if(from_month<10){
		from_month = "0"+from_month;
	}
	var from_date = from.getDate();
	if(from_date<10){
		from_date = "0"+from_date;
	}
	
	document.getElementById("from").value = from.getFullYear()+"-"+from_month+"-"+from_date;
}
</script>
</head>
<body>
	<!-- endpoint list -->
	<div class="listContent">
		<div>
			<div style="margin-bottom:10px;">
				조회 날짜 <input type="text" id="date" name="nlm_create_sdate" class="input date" value="${date}" onchange="loadList(${page});">
				<span style="border-left:1px solid #c2c3c4;height:22px;margin:0px 17px;"></span>
				<a href="javascript:regWhitePs();" id="white-top-btn"><i class="fa fa-upload" style="margin:0px 8px 0 0;"></i> White List 등록</a>
			</div>
		</div>
		<form id="frmPs" name="frmPs">
			<input type="hidden" id="mps_strc_stime" name="mps_strc_stime" value="${date} 00:00:00" />
			<input type="hidden" id="mps_strc_etime" name="mps_strc_etime" value="${date} 23:59:59" />
			<table id="tblEndpoint" class="tbl" >
				<colgroup>
					<col width="10%" />
					<col width="5%" />
					
					<col width="5%" />
					<col width="20%" />
					
					<col width="15%" />
					<col width="10%" />
					
					<col width="15%" />
					<col width="10%" />
					
					<col width="10%" />
				</colgroup>						
               	<thead>
               		<tr>
                 		<th rowspan="2">
                 			<input type="checkbox" id="all_chk" class="chk" name="all_chk" onclick="all_checkbox()">
                 			<label for="all_chk"><span></span> 서비스</label>
                 		</th>
                 		<th rowspan="2">구분</th>
                 		<th colspan="2" class="bLine">접근제어</th>
                 		<th colspan="2" class="bLine">출발지</th>
                 		<th colspan="2" class="bLine">도착지</th>
                 		<th rowspan="2">통신 시간</th>
                 	</tr>
               		<tr>
               			<th>탐지</th>
               			<th>탐지 내용</th>
                 		<th>IP 주소</th>
                 		<th>PORT</th>
                 		<th>IP 주소</th>
                 		<th>PORT</th>
                 	</tr>
                </thead>
				<tbody>
				<c:choose>
					<c:when test="${pginfo.result != null && !empty pginfo.result}">
						<c:forEach var="ndrPs" items="${pginfo.result}" varStatus="status"> 
							<tr id="con${status.index}" class="trHover <c:if test="${ndrPs.ps_detect eq '0'}">fail</c:if>">
								<td class="<c:choose><c:when test="${ndrPs.ps_detect==1 }">dotRisk1</c:when><c:otherwise>dotRisk3</c:otherwise></c:choose>">
									<input type="hidden" id="rowNumber_${ndrPs.rowNumber}" name="rowNumber_${ndrPs.rowNumber}" value="${ndrPs.rowNumber}" />
									<c:if test="${ndrPs.ps_detect!=1}"><input type="checkbox" id="ps_id_${ndrPs.ps_id}" name="ps_id[]" value='${ndrPs.ps_id}'></c:if>
									<label for="ps_id_${ndrPs.ps_id}"><span></span>${ndrPs.ps_proto_sub_dec}</label>
									<!--  <c:if test="${ndrPs.ps_white_result==1}"><a href="javascript:delWhitePs(${ndrPs.ps_id});"><img src="/img/delete.png" title="white list 제거" alt="white list 제거"/></a></c:if> -->
								</td>
								<td>${ndrPs.strPsInOut}</td>
								<td>${ndrPs.strPsDetect}</td>
								<td>${ndrPs.ps_detect_desc}</td>
								<td>${ndrPs.ps_src_ip}</td>
								<td>${ndrPs.ps_src_port}</td>
								<td>${ndrPs.ps_dst_ip}</td>
								<td>${ndrPs.ps_dst_port}</td>
								<td>${ndrPs.ps_strc_time}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="9">통신 정보가 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</form>
	<%@ include file="../include/paging_script.jsp"%>
	</div>
	<!-- /endpoint list -->
	
<!-- 	<script type="text/javascript" src="/js/jquery-ui.min.js"></script> -->
	<script type="text/javascript" src="/js/html5shiv.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>
</body>

</html>
