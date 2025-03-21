<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='common.title'/></title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/css/nprogress.css">
<link rel="stylesheet" type="text/css" href="/css/skins/green.css">
<link rel="stylesheet" type="text/css" href="/css/custom.min.css">
<link rel="stylesheet" type="text/css" href="/css/contents.css">
<link rel="stylesheet" type="text/css" href="/css/dashboard.css"><!-- 대시보드용 css -->
<link rel="stylesheet" type="text/css" href="/css/statistics.css"><!-- 막대 바 관련 css -->
<script type="text/javascript" src="/js/justgage/raphael-2.1.4.min.js"></script>
<script type="text/javascript" src="/js/justgage/justgage.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>

<!-- Resources -->
<script src="/amcharts/amcharts.js"></script>
<script src="/amcharts/serial.js"></script>
<!-- <script src="/amcharts/plugins/export/export.min.js"></script> -->
<script src="/amcharts/themes/light.js"></script>
<!-- 차트 스크립트 -->
<script type="text/javascript">
//검색유무
var bSerach = true;

//차트 변수
var chartTxRecv = null;
var chartTxSend = null;
var chartTxRepe = null;
var chartTxLoss = null;
var chartRxRecv = null;
var chartRxSend = null;
var chartRxRepe = null;
var chartRxLoss = null;

//차트 컬러
var colors= ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7","#828C38","#C5DE00","#874340","#DBB0A0","#0D477D","#8EBBDD","#A87000","#D8C679","#515151"];

// 차트 초기화
function initChart() {
	$.ajax({
		type : "POST",  
		url : "/initChart.do",
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
			+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
			+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&ssecond="+document.getElementById("stat_ssecond").value+"&esecond="+document.getElementById("stat_esecond").value
			+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data.code == "nodata"){
				alert("조회된 내용이 없습니다.")
			}else if(data.code == "OK"){
				//데이터 정보 테이블
				document.getElementById("tx_recv_table").innerHTML=data.txRecvTbCont;
				document.getElementById("tx_send_table").innerHTML=data.txSendTbCont;
				document.getElementById("rx_recv_table").innerHTML=data.rxRecvTbCont;
				document.getElementById("rx_send_table").innerHTML=data.rxSendTbCont;

				//차트 초기 생성
				chartTxRecv = createLineChart("chartTx01", data.txRecvGraphs, data.txRecvCtData, data.maxTxRSumCnt);
				chartTxSend = createLineChart("chartTx02", data.txSendGraphs, data.txSendCtData, data.maxTxSSumCnt);
				chartRxRecv = createLineChart("chartRx01", data.rxRecvGraphs, data.rxRecvCtData, data.maxRxRSumCnt);
				chartRxSend = createLineChart("chartRx02", data.rxSendGraphs, data.rxSendCtData, data.maxRxSSumCnt);
				
				//그래프 온 오프 버튼 이벤트
				Array.prototype.forEach.call(
					document.querySelectorAll('.toggle-graphTxRecv'),
					function (button) {
						button.addEventListener('click', function(e) {
							var graphTR = chartTxRecv.graphs[e.currentTarget.dataset.graphIndex];
							
							if (graphTR.hidden) {
								chartTxRecv.showGraph(graphTR);
								
								document.getElementById("txRecvLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor=colors[e.currentTarget.dataset.graphIndex%10];
								$(this)[0].style.color="#2A3F54";
							}
							else {
								chartTxRecv.hideGraph(graphTR);
								
								document.getElementById("txRecvLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor="#c9c9ca";
								$(this)[0].style.color="#c9c9ca";
							}
						});
					}
				);
				Array.prototype.forEach.call(
					document.querySelectorAll('.toggle-graphTxSend'),
					function (button) {
						button.addEventListener('click', function(e) {
							var graphTS = chartTxSend.graphs[e.currentTarget.dataset.graphIndex];
							
							if (graphTS.hidden) {
								chartTxSend.showGraph(graphTS);
								
								document.getElementById("txSendLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor=colors[e.currentTarget.dataset.graphIndex%10];
								$(this)[0].style.color="#2A3F54";
							}
							else {
								chartTxSend.hideGraph(graphTS);
								
								document.getElementById("txSendLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor="#c9c9ca";
								$(this)[0].style.color="#c9c9ca";
							}
						});
					}
				);
				Array.prototype.forEach.call(
					document.querySelectorAll('.toggle-graphRxRecv'),
					function (button) {
						button.addEventListener('click', function(e) {
							var graphRR = chartRxRecv.graphs[e.currentTarget.dataset.graphIndex];
							
							if (graphRR.hidden) {
								chartRxRecv.showGraph(graphRR);
								
								document.getElementById("rxRecvLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor=colors[e.currentTarget.dataset.graphIndex%10];
								$(this)[0].style.color="#2A3F54";
							}
							else {
								chartRxRecv.hideGraph(graphRR);
								
								document.getElementById("rxRecvLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor="#c9c9ca";
								$(this)[0].style.color="#c9c9ca";
							}
						});
					}
				);
				Array.prototype.forEach.call(
					document.querySelectorAll('.toggle-graphRxSend'),
					function (button) {
						button.addEventListener('click', function(e) {
							var graphRS = chartRxSend.graphs[e.currentTarget.dataset.graphIndex];
							
							if (graphRS.hidden) {
								chartRxSend.showGraph(graphRS);
								
								document.getElementById("rxSendLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor=colors[e.currentTarget.dataset.graphIndex%10];
								$(this)[0].style.color="#2A3F54";
							}
							else {
								chartRxSend.hideGraph(graphRS);
								
								document.getElementById("rxSendLine"+e.currentTarget.dataset.graphIndex).style.backgroundColor="#c9c9ca";
								$(this)[0].style.color="#c9c9ca";
							}
						});
					}
				);
			}else{
				alert(data.code);
			}
		},
		error: function(request,status,error){
			alert('불러오기 중 에러가 발생하였습니다.\n'+error);
		}
	});
}
// 이벤트 로그
function initDashLog() {
	$.ajax({
		type : "POST",  
		url : "/initDashLog.do",   
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data.code == "nodata"){
				alert("조회된 내용이 없습니다.")
			}else if(data.code == "OK"){
				document.getElementById("txLog").innerHTML=data.txEventLogTxt;
				document.getElementById("rxLog").innerHTML=data.rxEventLogTxt;
			}else{
				alert(data.code);
			}
		},
		error: function(request,status,error){
			alert('불러오기 중 에러가 발생하였습니다.\n'+error);
		}
	});
}
// 차트 스타일 정의
function createLineChart(charId, graphsData, chartData, chartScrollbarNo){
	return AmCharts.makeChart(charId, {
	    "type": "serial",
	    "theme": "light",
	    "marginRight": 0,
	    "autoMarginOffset": 0,
	    "marginTop": 7,
	    "dataProvider": chartData,
	    "valueAxes": [{
	        "axisAlpha": 0.2,
	        "dashLength": 1,
	        "position": "left",
	        "minimum":0
	    }],
	    "mouseWheelZoomEnabled": true,
	    "graphs": graphsData,
	    "chartScrollbar": {
	        "autoGridCount": true,
	        "oppositeAxis":false,
	        "offset": 20,
	        "scrollbarHeight": 40,
	        "graph": graphsData[chartScrollbarNo].id,
	        "dragIconHeight": 40,
	        "dragIconWidth": 20,
	        "dragIcon": "dragIconRectSmall"
	    },
	    "chartCursor": {
	       "categoryBalloonDateFormat": "YYYY-MM-DD JJ:NN:SS"
	    },
	    "categoryField": "date",
	    "categoryAxis": {
	    	"dateFormats": [{"period":"fff","format":"JJ:NN:SS"},{"period":"ss","format":"JJ:NN:SS"},{"period":"mm","format":"JJ:NN"},{"period":"hh","format":"JJ:NN"},{"period":"DD","format":"MM-DD"},{"period":"WW","format":"MM-DD"},{"period":"MM","format":"MM"},{"period":"YYYY","format":"YYYY"}],
	    	"minPeriod": "ss",
	        "parseDates": true,
	        "axisColor": "#DADADA",
	        "dashLength": 1,
	        "minorGridEnabled": true
	    },
	    "export": {
	        "enabled": false
	    }
	});
}
</script>
<!-- 검색바 관련 -->
<script type="text/javascript">
function search() {
	var form = document.getElementById("search");
	form.action="stat_sndrcv.do";
	form.submit();
}
function end_term_set(){
	var sdate = document.getElementById("stat_sdate").value;
	var rev_sdate = sdate.replace(/-/g, "/");
	var shour = document.getElementById("stat_shour").value;
	var sminute = document.getElementById("stat_sminute").value;
	
	var edate = document.getElementById("stat_edate").value;
	var rev_edate = edate.replace(/-/g, "/");
	var ehour = document.getElementById("stat_ehour").value;
	var eminute = document.getElementById("stat_eminute").value;
	
	var start = new Date(rev_sdate+" "+shour+":"+sminute);
	var end = new Date(rev_edate+" "+ehour+":"+eminute);
	
	var interval = (end.getTime() - start.getTime()) / 60000; //분차이
	if(interval<60){
		var after = new Date(Date.parse(start) + 1000 * 60 * 60); //60분 후
		
		var after_month = after.getMonth()+1;
		if(after_month<10){
			after_month = "0"+after_month;
		}
		var after_date = after.getDate();
		if(after_date<10){
			after_date = "0"+after_date;
		}
		document.getElementById("stat_edate").value = after.getFullYear()+"-"+after_month+"-"+after_date;
		document.getElementById("stat_ehour").value = after.getHours();
		document.getElementById("stat_eminute").value = after.getMinutes();
	}else if(interval>1440){
		var after = new Date(Date.parse(start) + 1000 * 60 * 1440); //하루(1440분) 후
		
		var after_month = after.getMonth()+1;
		if(after_month<10){
			after_month = "0"+after_month;
		}
		var after_date = after.getDate();
		if(after_date<10){
			after_date = "0"+after_date;
		}
		document.getElementById("stat_edate").value = after.getFullYear()+"-"+after_month+"-"+after_date;
		document.getElementById("stat_ehour").value = after.getHours();
		document.getElementById("stat_eminute").value = after.getMinutes();
	}
}
function start_term_set(){	
	var sdate = document.getElementById("stat_sdate").value;
	var rev_sdate = sdate.replace(/-/g, "/");
	var shour = document.getElementById("stat_shour").value;
	var sminute = document.getElementById("stat_sminute").value;
	
	var edate = document.getElementById("stat_edate").value;
	var rev_edate = edate.replace(/-/g, "/");
	var ehour = document.getElementById("stat_ehour").value;
	var eminute = document.getElementById("stat_eminute").value;
	
	var start = new Date(rev_sdate+" "+shour+":"+sminute);
	var end = new Date(rev_edate+" "+ehour+":"+eminute);
	
	if(start>end){
		alert("검색 종료일이 시작일 보다 작습니다. 시작일로부터 한시간 후로 변경됩니다.");
		
		var after = new Date(Date.parse(start) + 1000 * 60 * 60); //60분 후
		
		var after_month = after.getMonth()+1;
		if(after_month<10){
			after_month = "0"+after_month;
		}
		var after_date = after.getDate();
		if(after_date<10){
			after_date = "0"+after_date;
		}
		
		document.getElementById("stat_edate").value = after.getFullYear()+"-"+after_month+"-"+after_date;
		document.getElementById("stat_ehour").value = after.getHours();
		document.getElementById("stat_eminute").value = after.getMinutes();
		return;
	}
	
	var interval = (end.getTime() - start.getTime()) / 60000; //분차이
	if(interval<60){
		var before = new Date(Date.parse(end) - 1000 * 60 * 60); //60분 전
		
		var before_month = before.getMonth()+1;
		if(before_month<10){
			before_month = "0"+before_month;
		}
		var before_date = before.getDate();
		if(before_date<10){
			before_date = "0"+before_date;
		}
		document.getElementById("stat_sdate").value = before.getFullYear()+"-"+before_month+"-"+before_date;
		document.getElementById("stat_shour").value = before.getHours();
		document.getElementById("stat_sminute").value = before.getMinutes();
	}else if(interval>1440){
		var before = new Date(Date.parse(end) - 1000 * 60 * 1440); //하루(1440분) 전
		
		var before_month = before.getMonth()+1;
		if(before_month<10){
			before_month = "0"+before_month;
		}
		var before_date = before.getDate();
		if(before_date<10){
			before_date = "0"+before_date;
		}
		document.getElementById("stat_sdate").value = before.getFullYear()+"-"+before_month+"-"+before_date;
		document.getElementById("stat_shour").value = before.getHours();
		document.getElementById("stat_sminute").value = before.getMinutes();
	}
}
</script>
</head>
<body class="nav-sm">
<input type="hidden" id="menu_id" value="${menu_id}">
<div class="container body">

	<div class="main_container">
		<!-- top content -->
		<%@ include file="../include/top.jsp"%>
		<!-- /top content -->

        <!-- page content -->
		<div class="right_col" role="main">
			<div class="search_zone">
				<form id="search" method="post" class="form-horizontal form-label-left">
					<div class="form-group">
						<div class="col-md-2 col-sm-12 col-xs-12 form-inline form-group">
							<label style="margin-right:10px;">통신방향</label>
							<select name="stat_net" id="stat_net" class="form-control" style="width:60%" onChange="location.href=this.value">
								<option value="stat_sndrcv.do" <c:if test="${tx_div != null && tx_div == 1}">selected</c:if>>내부 → 외부</option>
								<option value="stat_sndrcv.do?tx_div=3&rx_div=4" <c:if test="${tx_div != null && tx_div == 3}">selected</c:if>>외부 → 내부</option>
							</select>
						</div>
						<div class="col-md-7 col-sm-12 col-xs-12 form-inline form-group">
							<label style="margin-right:10px;">검색기간</label>
							<input type="text" id="stat_sdate" name="stat_sdate" class="form-control" value="${ncStatNw.stat_sdate}" />&nbsp;&nbsp; 
							<select name="stat_shour" id="stat_shour" class="form-control" onChange="end_term_set();">
							<c:forEach begin="0" end="23" var="i">
								<option value="${i}" <c:if test="${ncStatNw.stat_shour == i}">selected</c:if>>${i}</option>
							</c:forEach>
							</select> 시&nbsp;&nbsp;
							<select name="stat_sminute" id="stat_sminute" class="form-control" onChange="end_term_set();">
							<c:forEach begin="0" end="59" var="i">
								<option value="${i}" <c:if test="${ncStatNw.stat_sminute == i}">selected</c:if>>${i}</option>
							</c:forEach>
							</select> 분&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;
							<input type="text" id="stat_edate" name="stat_edate" class="form-control" value="${ncStatNw.stat_edate}" />&nbsp;&nbsp;
							<select name="stat_ehour" id="stat_ehour" class="form-control" onChange="start_term_set();">
							<c:forEach begin="0" end="23" var="i">
								<option value="${i}" <c:if test="${ncStatNw.stat_ehour == i}">selected</c:if>>${i}</option>
							</c:forEach>
							</select> 시&nbsp;&nbsp;
							<select name="stat_eminute" id="stat_eminute" class="form-control" onChange="start_term_set();">
							<c:forEach begin="0" end="59" var="i">
								<option value="${i}" <c:if test="${ncStatNw.stat_eminute == i}">selected</c:if>>${i}</option>
							</c:forEach>
							</select> 분
							<input type="hidden" name="stat_ssecond" id="stat_ssecond" value="00"/>
							<input type="hidden" name="stat_esecond" id="stat_esecond" value="59"/>
							<input type="hidden" name="tx_div" id="tx_div" value="${tx_div}" />
							<input type="hidden" name="rx_div" id="rx_div" value="${rx_div}" />
							<button type="button" class="btn btn-primary" onClick="javascript:search();" style="margin-left:20px"><i class="fa fa-search"></i> 검색</button>
							<button type="button" class="btn btn-success" onClick="location.href='/stat_sndrcv.do<c:if test="${tx_div != null && tx_div == 3}">?tx_div=3&rx_div=4</c:if>'"><i class="fa fa-refresh"></i> 초기화</button>
						</div>
						<label class="control-label col-md-3 col-sm-12 col-xs-12 form-group" style="color:#ff5555;font-weight:bold;text-align:left;">* 검색 기간은 최소 한 시간에서 최대 하루까지 설정 가능합니다.</label>
					</div>
				</form>
			</div>
		
			<div class="contents" style="padding:0px 10px 0px 0px;">
				<div class="col-md-6 col-sm-12 col-xs-12 padding0">
					<div class="col-md-12 col-sm-12 col-xs-12 padding_r0">
						<div class="box_title x_content">
							<div class="box_icon glyphicon glyphicon-log-out"></div>
							<div class="m_title">Tx ${txTxt}부망 데이터 수집 및 일방향 데이터 송신</div>
							<div class="s_title">${txTxt}부망에서 일방향 전송을 위해 수집한 데이터 양과 전송통제 서버에서 ${rxTxt}부망으로 일방향 송신한 데이터 양을 나타냅니다.</div>
						</div>						
					</div>
					<div class="col-md-6 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont x_content">
							<div class="m_title"><i class="fa fa-download"></i> ${txTxt}부망 데이터 수집</div>
							<div id="chartTx01"></div>
						</div>
						<div class="box_cont box_service x_content">
							<div class="m_content">
								<table class='data_table'>
									<tr>
										<td class="tdTitle">서비스</td>
										<td class="tdNumber">수집량</td>
										<td class="tdBlank">&nbsp;</td>
									</tr>
								</table>
								<div id='tx_recv_table' class="serviceList"></div>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont x_content">
							<div class="m_title"><i class="fa fa-sign-out"></i> 일방향 데이터 송신</div>
							<div id="chartTx02"></div>
						</div>
						<div class="box_cont box_service x_content">
							<div class="m_content">
								<table class='data_table'>
									<tr>
										<td class="tdTitle">서비스</td>
										<td class="tdNumber">송신량</td>
										<td class="tdBlank">&nbsp;</td>
									</tr>
								</table>
								<div id='tx_send_table' class="serviceList"></div>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont box_log x_content">
							<div class="m_title" style="border:0px;"><i class="fa fa-list-ul"></i> Tx ${txTxt}부망 이벤트 로그</div>
							<div class="m_content">
								<table class='logHead'>
									<colgroup>
										<col width='150' />
										<col width='80' />
										<col width='200' />
										<col width='' />
									</colgroup>
									<thead>
										<tr>
											<th>로그 생성일</th>
											<th>위험도</th>
											<th class="tLeft">서비스명</th>
											<th class="tLeft">메시지</th>
										</tr>
									</thead>
								</table>
								<div class='logBody' id="txLog"></div>
							</div>
							
						</div>
					</div>
				</div>
				
				<div class="col-md-6 col-sm-12 col-xs-12 padding0">
					<div class="col-md-12 col-sm-12 col-xs-12 padding_r0">
						<div class="box_title x_content">
							<div class="box_icon glyphicon glyphicon-log-in"></div>
							<div class="m_title">Rx 일방향 데이터 수신  및 ${rxTxt}부망 데이터 송신</div>
							<div class="s_title">일방향 전송으로 수신한 데이터 양과 ${rxTxt}부 전송통제 서버에서 ${rxTxt}부망으로 전송한 데이터 양을 나타냅니다.</div>
						</div>						
					</div>
					
					<div class="col-md-6 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont x_content">
							<div class="m_title"><i class="fa fa-sign-in"></i> 일방향 데이터 수신</div>
							<div id="chartRx01"></div>
						</div>
						<div class="box_cont box_service x_content">
							<div class="m_content">
								<table class='data_table'>
									<tr>
										<td class="tdTitle">서비스</td>
										<td class="tdNumber">수신량</td>
										<td class="tdBlank">&nbsp;</td>
									</tr>
								</table>
								<div id='rx_recv_table' class="serviceList"></div>
							</div>
						</div>
					</div>				
					<div class="col-md-6 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont x_content">
							<div class="m_title"><i class="fa fa-upload"></i> ${rxTxt}부망 데이터 송신</div>
							<div id="chartRx02"></div>
						</div>
						<div class="box_cont box_service x_content">
							<div class="m_content">
								<table class='data_table'>
									<tr>
										<td class="tdTitle">서비스</td>
										<td class="tdNumber">송신량</td>
										<td class="tdBlank">&nbsp;</td>
									</tr>
								</table>
								<div id='rx_send_table' class="serviceList"></div>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding_r0">
						<div class="box_cont box_log x_content">
							<div class="m_title" style="border:0px;"><i class="fa fa-list-ul"></i> Rx ${rxTxt}부망 이벤트 로그</div>
							<div class="m_content">
								<table class='logHead'>
									<colgroup>
										<col width='150' />
										<col width='80' />
										<col width='200' />
										<col width='' />
									</colgroup>
									<thead>
										<tr>
											<th>로그 생성일</th>
											<th>위험도</th>
											<th class="tLeft">서비스명</th>
											<th class="tLeft">메시지</th>
										</tr>
									</thead>
								</table>
								<div class='logBody' id="rxLog"></div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
        <!-- /page content -->

        <!-- footer content -->
       	<%@ include file="../include/footer.jsp"%>
		<!-- /footer content -->
	</div>
</div>
</body>

<!-- 달력 스크립트 -->
<script src="/js/moment/moment.min.js"></script>
<script src="/js/datepicker/daterangepicker.js"></script>
<script>
var setTimeDash = null; 

$(document).ready(function() {
	$("#sidebar-menu").find("li.active ul").hide();
	$("#sidebar-menu").find("li.active").addClass("active-sm").removeClass("active");
	$("#left_logo").attr("src", "/images/left_slogo.png");
	$("#left_logo").attr("width", "40");
	
	//달력 설정
	$('#stat_sdate').daterangepicker({
		singleDatePicker: true,
		calender_style: "picker_4"
	}, function(start, end, label) {
		end_term_set();
		console.log(start.toISOString(), end.toISOString(), label);
	});
	
	$('#stat_edate').daterangepicker({
		singleDatePicker: true,
		calender_style: "picker_4"
	}, function(start, end, label) {
		start_term_set();
		console.log(start.toISOString(), end.toISOString(), label);
	});
	
	//검색바 설정 및 타이머 설정여부
	if("${ncStatNw.stat_sdate}"==""){
		date_set();
		bSerach = false;
	}

	//차트초기화
	initChart();
	
	//로그초기화
	initDashLog();
	
	if(!bSerach){
		setTimeDash = setTimeout("setDashboard();", 60000); // 60초 간격
	}
});


//시간 설정
function date_set(){
	var loadDt = new Date();
	var start = new Date(Date.parse(loadDt) - 1000 * 60 * 481); //481분(8시간+1분) 전
	var end = new Date(Date.parse(loadDt) - 1000 * 60); //1분 전;
	
	var start_month = start.getMonth()+1;
	if(start_month<10){
		start_month = "0"+start_month;
	}
	var start_date = start.getDate();
	if(start_date<10){
		start_date = "0"+start_date;
	}
	var end_month = end.getMonth()+1;
	if(end_month<10){
		end_month = "0"+end_month;
	}
	var end_date = end.getDate();
	if(end_date<10){
		end_date = "0"+end_date;
	}
	document.getElementById("stat_sdate").value = start.getFullYear()+"-"+start_month+"-"+start_date;
	document.getElementById("stat_shour").value = start.getHours();
	document.getElementById("stat_sminute").value = start.getMinutes();
	document.getElementById("stat_ssecond").value = start.getSeconds();
	
	document.getElementById("stat_edate").value = end.getFullYear()+"-"+end_month+"-"+end_date;
	document.getElementById("stat_ehour").value = end.getHours();
	document.getElementById("stat_eminute").value = end.getMinutes();
	document.getElementById("stat_esecond").value = end.getSeconds();
}

function setDashboard(){//60초마다 대시보드 설정 
	date_set();
	
	//데이터 테이블 불러오기
	$.ajax({
		type : "POST",  
		url : "/updateChart.do",
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
			+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
			+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&ssecond="+document.getElementById("stat_ssecond").value+"&esecond="+document.getElementById("stat_esecond").value
			+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data.code == "nodata"){
				alert("조회된 내용이 없습니다.")
			}else if(data.code == "OK"){
	
				//데이터 정보 테이블
				var txRecvByteSum = document.querySelectorAll(".toggle-graphTxRecv");
				for(var i=0; i < data.nsrByteSumTx.length; i++){
					txRecvByteSum[i].nextSibling.innerHTML = data.nsrByteSumTx[i];
				}
				var txSendByteSum = document.querySelectorAll(".toggle-graphTxSend");
				for(var i=0; i < data.nssByteSumTx.length; i++){
					txSendByteSum[i].nextSibling.innerHTML = data.nssByteSumTx[i];
				}
				
				var rxRecvByteSum = document.querySelectorAll(".toggle-graphRxRecv");
				for(var i=0; i < data.nsrByteSumRx.length; i++){
					rxRecvByteSum[i].nextSibling.innerHTML = data.nsrByteSumRx[i];
				}
				var rxSendByteSum = document.querySelectorAll(".toggle-graphRxSend");
				for(var i=0; i < data.nssByteSumRx.length; i++){
					rxSendByteSum[i].nextSibling.innerHTML = data.nssByteSumRx[i];
				}

				//차트 변수 갱신
				if(chartTxRecv != null){
					chartTxRecv.dataProvider = data.txRecvCtData;
					chartTxRecv.validateData();
				}
				if(chartTxSend != null){
					chartTxSend.dataProvider = data.txSendCtData;
					chartTxSend.validateData();
				}
				
				if(chartRxRecv != null){
					chartRxRecv.dataProvider = data.rxRecvCtData;
					chartRxRecv.validateData();
				}
				if(chartRxSend != null){
					chartRxSend.dataProvider = data.rxSendCtData;
					chartRxSend.validateData();
				}
			}else{
				alert(data.code);
			}
		},
		error: function(request,status,error){
			alert('불러오기 중 에러가 발생하였습니다.\n'+error);
		}
	});
	
	//로그초기화
	initDashLog();
	
	clearTimeout(setTimeDash);
	setTimeDash = setTimeout("setDashboard();", 60000); // 60초 간격
}
</script>
</html>