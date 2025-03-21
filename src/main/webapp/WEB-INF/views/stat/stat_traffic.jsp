<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
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
<%@ include file="/WEB-INF/views/include/css/traffic_css.jsp"%>

<script type="text/javascript" src="/js/jquery.min.js"></script>

</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	<section id="main-form">
		<div class="main-wrapper" style="width: 100%;">
			<form id="search" method="GET">
			<input type="hidden" name="tx_div" id="tx_div" value="${tx_div}" />
			<input type="hidden" name="rx_div" id="rx_div" value="${rx_div}" />
			<input type="hidden" name="datetab" id="datetab" value="${ncStatNw.datetab}" />
				<ul id="search" class="clearfix">
					<!-- <li style="padding:14px 0">
						<span>통신방향</span>
						<select name="stat_net" id="stat_net" onChange="location.href=this.value" <c:if test="${Config.getInstance().getModel() eq 'single'}">disabled=true</c:if>>
							<option value="stat_traffic.do" <c:if test="${tx_div != null && tx_div == 1}">selected</c:if>><spring:message code='config.inside'/> → <spring:message code='config.outside'/></option>
							<option value="stat_traffic.do?tx_div=3&rx_div=4" <c:if test="${tx_div != null && tx_div == 3}">selected</c:if>><spring:message code='config.outside'/> → <spring:message code='config.inside'/></option>
						</select>						
					</li> -->
					<li>
						<span><spring:message code='stat.searchperiod'/></span>
						<input type="text" id="stat_sdate" name="stat_sdate" class="input date" value="${ncStatNw.stat_sdate}" style="margin-top:-3px;" autocomplete="off" />
						<select name="stat_shour" id="stat_shour" style="margin-left:1px; width:69px;" onChange="end_term_set();init_datetab();">
						<c:forEach begin="0" end="23" var="i">
							<option value="${i}" <c:if test="${ncStatNw.stat_shour == i}">selected</c:if>>${i}시</option>
						</c:forEach>
						</select>
						<select name="stat_sminute" id="stat_sminute" style="margin-left:1px; width:69px;"onChange="end_term_set();init_datetab();">
						<c:forEach begin="0" end="59" var="i">
							<option value="${i}" <c:if test="${ncStatNw.stat_sminute == i}">selected</c:if>>${i}분</option>
						</c:forEach>
						</select>
						<img src="/img/search-bar2.png">
						<input type="text" id="stat_edate" name="stat_edate" class="input date" value="${ncStatNw.stat_edate}" style="margin-top:-3px;" autocomplete="off" />
						<select name="stat_ehour" id="stat_ehour" style="margin-left:1px; width:69px;" onChange="start_term_set();init_datetab();">
						<c:forEach begin="0" end="23" var="i">
							<option value="${i}" <c:if test="${ncStatNw.stat_ehour == i}">selected</c:if>>${i}시</option>
						</c:forEach>
						</select>
						<select name="stat_eminute" id="stat_eminute" style="margin-left:2px; width:69px;" onChange="start_term_set();init_datetab();">
						<c:forEach begin="0" end="59" var="i">
							<option value="${i}" <c:if test="${ncStatNw.stat_eminute == i}">selected</c:if>>${i}분</option>
						</c:forEach>
						</select>
					</li>
					<li>
						<ul class="tab clearfix">
							<li><div class="tablinks <c:if test="${ncStatNw.datetab eq '1'}">active</c:if>" onClick="javascript:setDate('1');"><a>1시간</a></div></li>
							<li><div class="tablinks <c:if test="${ncStatNw.datetab eq '8'}">active</c:if>" style="margin-left:-1px; padding:0 15px;" onClick="javascript:setDate('8');"><a>8시간</a></div></li>
							<li><div class="tablinks <c:if test="${ncStatNw.datetab eq '24'}">active</c:if>" style="margin-left:-1px; padding:0 18px;" onClick="javascript:setDate('24');"><a>24시간</a></div></li>
						</ul>
					</li>
					<li><a href="javascript:search();" id="submitbtn"><img src="/img/search.png"><spring:message code='btn.search'/></a></li>
				</ul>
			</form>
		</div>
	</section>
	<!-- left menu start -->
	<section id="content">
		<div class="left-content">
		    <div class="chart left_chart1">
                <span>송신 트래픽 현황</span>
		        <div id="in_rcv" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart left_chart2">
                <span>전송 트래픽 현황</span>
		        <div id="in_snd" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart left_chart6">
                <span>위협탐지</span>
		        <div id="in_detect" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart left_chart5">
                <span>서비스별 트래픽</span>
		        <div id="chartProtocol" class="chartdiv" style="width:350px;height:120px;background-color:#0E131A;"></div>
		    </div>
		</div>
		
		<div class="center-content">
			<div class="main-section">
				<div class="main-wrapper">
					<div class="tx">
						<div id="tx-sankey" class="sankeyDiv"></div>
					</div>

					<div class="main-arrow" id="main-arrow">
					    <div class="tx-machine">
                            <span class="mb">
                                <p id="txbyte"></p>
                                <p id="tx-byte-unit">MB</p>
                            </span>
					        <span class="service">
                                <p>Services</p>
                                <p id="txcnt"></p>
                            </span>
					    </div>
						<img class="arrow_in" src="/img/master_in.png">
						<img class="product_cds" src="/img/CDS_prod_img.gif">
<!-- 						<img class="product_cds" src="/img/prod_Trust_img.gif"> -->
<!-- 						<img class="product_cds" src="/img/prod_Diode_img.gif"> -->
						<img class="arrow_out" src="/img/master_out.png">
					    <div class="rx-machine">
					        <span class="mb">
                                <p id="rxbyte"></p>
                                <p id="rx-byte-unit">MB</p>
                            </span>
					        <span class="service">
                                <p>Services</p>
                                <p id="rxcnt"></p>
                            </span>
					    </div>
					</div>

					<div class="rx">
						<div id="rx-sankey" class="sankeyDiv"></div>
					</div>
				</div>
			</div>
			<!-- 정책 이미지 클릭 popup -->
			<div id="main-pop" style="display:none;"></div>

			
		</div>

		<div class="right-content">
		    <div class="chart chart1">
                <span>수신 트래픽 현황</span>
		        <div id="out_rcv" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart chart2">
                <span>전송 트래픽 현황</span>
		        <div id="out_snd" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart chart6">
                <span>위협탐지</span>
		        <div id="out_detect" class="chartdiv" style="width:350px;height:102px;background-color:#0E131A;"></div>
		    </div>
		    <div class="chart chart5">
                <span>서비스별 트래픽</span>
		        <div id="chartProtocolOut" class="chartdiv" style="width:350px;height:120px;background-color:#0E131A;"></div>
		    </div>
		</div>
	</section>
	
	<section id="foot-section">
		<div class="text-section">
			<table class="foot-wrapper">
				<thead>
				    <tr>
				        <th align="left" class="group1">통신시간</th>
				        <th align="left" class="group3" rowspan="2"><spring:message code='policy.svcname'/></th>
				        <th align="left" class="group2">구분</th>
				        <th align="left" class="group2">탐지</th>
				        <th align="left" class="group1">탐지 내용</th>
				        <th align="left" class="group1">출발지 IP:PORT</th>
				        <th align="left" class="group1">도착지 IP:PORT</th>
						<th align="right" class="group4" rowspan="2">
							<a href="javascript:view_all_logs(0);" id="view-all-logs">전체 보기</a>
						</th>
				    </tr>
				    <tr>
				    </tr>
				</thead>
			</table>
			<div id='realtime_log' class="realtime_log"></div>
	    </div>
	    <div class="text-section">
			<table class="foot-wrapper">
				<thead>
				    <tr>
				        <th align="left" class="group1">통신시간</th>
				        <th align="left" class="group3" rowspan="2"><spring:message code='policy.svcname'/></th>
				        <th align="left" class="group2">구분</th>
				        <th align="left" class="group2">탐지</th>
				        <th align="left" class="group1">탐지 내용</th>
				        <th align="left" class="group1">출발지 IP:PORT</th>
				        <th align="left" class="group1">도착지 IP:PORT</th>
						<th align="right" class="group4" rowspan="2">
							<a href="javascript:view_all_logs(1);" id="view-all-logs">전체 보기</a>
						</th>
				    </tr>
				    <tr>
				    </tr>
				</thead>
			</table>
			<div id='realtime_log_out' class="realtime_log"></div>
	    </div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
	
<script>
	// 차트 스타일 정의
	function createLineChart(chartId, chartData, valueAxisMax){
		return AmCharts.makeChart(chartId, {
			"type": "serial",
			"categoryField": "category",
			"columnSpacing": 57,
			"angle": 11,
			"autoMarginOffset": 0,
			"marginBottom": 65,
			"minMarginLeft": -2,
			"plotAreaBorderColor": "#000",
			"plotAreaFillColors": "#0E131A",
			"startDuration": 1,
			"backgroundColor": "#0E131A",
			"borderColor": "#0E131A",
			"color": "#353B43",
			"fontFamily": "NanumGothic",
			"handDrawThickness": 8,
			"hideBalloonTime": 86,
			"processTimeout": 1,
			"theme": "dark",
			"categoryAxis": {
				"autoRotateAngle": 0,
				"gridPosition": "start",
				"tickPosition": "start",
				"axisAlpha": 1,
				"axisColor": "#0E131A",
				"fillColor": "#FFF",
				"firstDayOfWeek": 2.82,
				"fontSize": null,
				"gridAlpha": 1,
				"gridThickness": 0,
				"tickLength": 3
			},
			"chartScrollbar": {
				"enabled": true,
				"backgroundColor": "#1A1F26",
				"dragIconHeight": 15,
				"dragIconWidth": 15,
				"graph": "AmGraph-1",
                "graphFillAlpha": 0,
				"oppositeAxis": false,
				"scrollbarHeight": 15,
				"selectedBackgroundAlpha": 0,
				"selectedGraphFillAlpha": 0
			},
			"graphs": [
				{
					"bulletBorderThickness": 13,
					"colorField": "color",
					"fillAlphas": 1,
					"id": "AmGraph-1",
					"lineColorField": "color",
					"precision": 0,
					"title": "graph 1",
					"type": "column",
					"valueField": "column"
				}
			],
			"valueAxes": [
				{
					"id": "ValueAxis-1",
					"autoGridCount": false,
					"autoRotateAngle": 0,
					"axisAlpha": 0,
					"axisColor": "#0E131A",
					"axisThickness": 0,
					"fillColor": "#0E131A",
					"fontSize": 0,
					"gridAlpha": 0,
					"gridColor": "#0E131A",
					"gridThickness": 0,
					"tickLength": 0,
					"title": "Axis title",
					"maximum" : valueAxisMax
				}
			],
			"balloon": {
                "borderThickness": 1,
                "cornerRadius": 100,
                "fontSize": 10,
                "fixedPosition": false,
                "textAlign": " middle"
            },
			"dataProvider": chartData
		});
	}

	function getTraffic_ct(){
		$.ajax({
			type : "POST",  
			url : "/trafficChart.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
				+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
				+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
				+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				createLineChart("in_rcv", data.inRecvCtData, data.inMaxPackets);
				createLineChart("out_rcv", data.outRecvCtData, data.outMaxPackets);
				
				createLineChart("in_snd", data.inSendCtData, data.inMaxPackets);
				createLineChart("out_snd", data.outSendCtData, data.outMaxPackets);
				
// 				createLineChart("in_detect", data.inDetectData, data.inMaxPackets);
// 				createLineChart("out_detect", data.outDetectData, data.outMaxPackets);
			},
			error: function(request,status,error){
				alert('불러오기 중 에러가 발생하였습니다.\n'+error);
			}
		});
	}
</script>

<!-- amcharts Resources -->
<script src="/amcharts/amcharts.js"></script>
<script src="/amcharts/serial.js"></script>
<script src="/amcharts4/core.js"></script>
<script src="/amcharts4/charts.js"></script>
<script src="/amcharts4/themes/animated.js"></script>
<script type="text/javascript">
// paddingTop, nodePadding
const PADDINGS = {
    1 : () => 110,
    2 : () => 103,
    3 : () => 96,
    4 : () => 89,
    5 : () => 82,
    6 : () => 75,
    7 : () => 68,
    8 : () => 61,
    9 : () => 54,
    10: () => 47,
};
// locationY
const GAPS = {
    1 : () => -2.4,
    2 : () => -2.4,
    3 : () => -2.4,
    4 : () => -2.36,
    5 : () => -2.3525,
    6 : () => -2.3,
    7 : () => -2.26,
    8 : () => -2.23,
    9 : () => -2.225,
	10: () => -2.2,
};
function search() {
	var form = document.getElementById("search");
	form.action="stat_traffic.do";
	form.submit();
}
function view_all_logs(ps_inout) {
	const tb = document.getElementById("log_event"+ps_inout);
	const rowsCnt = tb.rows.length;
	const columnsCnt = tb.rows[0].cells.length;

	if (columnsCnt === 1) {
		alert("검색 결과가 없습니다.");
		return ;
	}

	const form = document.getElementById("search");
	const temp = calDateRange(form.stat_sdate.value, form.stat_edate.value);
	if(temp<0){
		alert("<spring:message code='log.dategreater'/>");
		return;
	}
	$('.wrap-loading').removeClass('display-none');

	// Create a form dynamically
	let dummyForm = document.createElement("form");
	dummyForm.setAttribute("method", "get");
	dummyForm.setAttribute("action", "ics_anomaly_list_grid.do")

	<%--dummyForm.innerHTML = `<input type="text" id="form" name="nle_create_sdate" value=${form.stat_sdate.value}>--%>
	<%--	<input type="text" id="to" name="nle_create_edate" value=${form.stat_edate.value}>`;--%>
	
	var stat_ps_inout = document.createElement("input");
	stat_ps_inout.setAttribute("type", "hidden");
	stat_ps_inout.setAttribute("name", "stat_ps_inout");
	stat_ps_inout.setAttribute("value", ps_inout);
	
	// Create an input element for nle_create_edate
	var sdateTime = document.createElement("input");
	var sdateTimeValue = form.stat_sdate.value+" "+form.stat_shour.value+":"+form.stat_sminute.value;
	sdateTime.setAttribute("type", "text");
	sdateTime.setAttribute("id", "to");
	sdateTime.setAttribute("name", "sdateTime");
	sdateTime.setAttribute("value", sdateTimeValue);
	
	// Create an input element for nle_create_edate
	var edateTime = document.createElement("input");
	var edateTimeValue = form.stat_edate.value+" "+form.stat_ehour.value+":"+form.stat_eminute.value;
	edateTime.setAttribute("type", "text");
	edateTime.setAttribute("id", "to");
	edateTime.setAttribute("name", "edateTime");
	edateTime.setAttribute("value", edateTimeValue);

	dummyForm.append(stat_ps_inout);
	dummyForm.append(sdateTime);
	dummyForm.append(edateTime);

	document.body.appendChild(dummyForm);

	dummyForm.submit();
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
		alert("<spring:message code='stat.searchafterhour'/>");

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
// 날짜 빠른검색 탬버튼
function setDate(time){
	var loadDt = new Date();
	var today = new Date(Date.parse(loadDt) - 1000 * 60); //1분 전;
	var from = new Date(Date.parse(loadDt) - 1000 * 60 * (60 * time + 1)); //day일수 만큼 전;

	var today_month = today.getMonth()+1;
	if(today_month<10){
		today_month = "0"+today_month;
	}
	var today_date = today.getDate();
	if(today_date<10){
		today_date = "0"+today_date;
	}
	var from_month = from.getMonth()+1;
	if(from_month<10){
		from_month = "0"+from_month;
	}
	var from_date = from.getDate();
	if(from_date<10){
		from_date = "0"+from_date;
	}
	document.getElementById("stat_sdate").value = from.getFullYear()+"-"+from_month+"-"+from_date;
	document.getElementById("stat_shour").value = from.getHours();
	document.getElementById("stat_sminute").value = from.getMinutes();

	document.getElementById("stat_edate").value = today.getFullYear()+"-"+today_month+"-"+today_date;
	document.getElementById("stat_ehour").value = today.getHours();
	document.getElementById("stat_eminute").value = today.getMinutes();
	document.getElementById("datetab").value = time;
}
function init_datetab(){
	document.getElementById("datetab").value = "";
	var tablink = document.getElementsByClassName('tablinks');
	for (var i=0; i<tablink.length; i++) {
		tablink[i].classList.remove('active');
	}
}
</script>
<script>
	$.ajaxSetup ({
	    // Disable caching of AJAX responses */
	    cache: false
	});
	$(document).ready(function() {
		if("${ncStatNw.stat_sdate}"==""){
			date_set();
			//setInterval("date_set();", 120000); // 2분 간격
		}else{
			getRealtime_log(); // 실시간 로그
			getTraffic_ct(); // 오른쪽 차트
			getCenter_info(); // tx, rx 총 전송량, 서비스 갯수, m/s 여부
			getTx_proc();
			getRx_proc();
			getChartDataAjax();
			getThreatChartDataAjax();
		}
	});

	// 시간 설정
	function date_set(){
		var loadDt = new Date();
		var start = new Date(Date.parse(loadDt) - 1000 * 60 * 61); //61분(1시간+1분) 전
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

		document.getElementById("stat_edate").value = end.getFullYear()+"-"+end_month+"-"+end_date;
		document.getElementById("stat_ehour").value = end.getHours();
		document.getElementById("stat_eminute").value = end.getMinutes();

		getRealtime_log(); // 실시간 로그
		getTraffic_ct(); // 오른쪽 전체 차트
		getCenter_info(); // tx, rx 총 전송량, 서비스 갯수, m/s 여부
		getTx_proc();
		getRx_proc();
		getChartDataAjax();
		getThreatChartDataAjax();
	}

	// 실시간 로그
	function getRealtime_log(){
		$.ajax({
			type : "POST",
			url : "realtime_log.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value
			+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&ps_inout=0",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null)  {
					document.getElementById('realtime_log').innerHTML = data;
				}
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
		$.ajax({
			type : "POST",
			url : "realtime_log.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value
			+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&ps_inout=1",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null)  {
					document.getElementById('realtime_log_out').innerHTML = data;
				}
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	// 가운데 정보
	function getCenter_info(){
		$.ajax({
			type : "POST",
			url : "center_info.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value
			+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null)  {
					//document.getElementById('main-arrow').innerHTML = data.mainarrow;
					document.getElementById('txbyte').innerHTML = data.txbyte;
					document.getElementById('tx-byte-unit').innerHTML = data.tx_byte_unit;
					document.getElementById('txcnt').innerHTML = data.txcnt;
					document.getElementById('rxbyte').innerHTML = data.rxbyte;
					document.getElementById('rx-byte-unit').innerHTML = data.rx_byte_unit;
					document.getElementById('rxcnt').innerHTML = data.rxcnt;
				}
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	// tx 서비스 정보
	function getTx_proc(){
		$.ajax({
			type : "POST",
			url : "tx_proc.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value
			+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null)  {
					<!-- tx Chart code -->
                    am4core.options.commercialLicense = true;

					// Themes begin
					am4core.useTheme(am4themes_animated);
					// Themes end

					var chart = am4core.create("tx-sankey", am4charts.SankeyDiagram);
					chart.hiddenState.properties.opacity = 0;   // this creates initial fade-in

					const jsonData = JSON.parse(data);
					const rowCnt = jsonData.length - 1;         // -1 : gradation end color node

					chart.data = jsonData;

					// console.log("tx-chart");
					// console.log(chart);

					// 차트 패딩 값 조절
					chart.paddingLeft = -1;	    // node box 숨기기, 임시방편
					// chart.paddingRight = 0;
					chart.paddingTop = PADDINGS[rowCnt]();
					// chart.paddingBottom = 0;

					// 노드 간 간격 조절
					chart.nodePadding = PADDINGS[rowCnt]();

					chart.dataFields.fromName = "from";
					chart.dataFields.toName = "to";
					chart.dataFields.value = "value";
					chart.dataFields.color = "color";

					// Get link template
					var linkTemplate = chart.links.template;
					linkTemplate.colorMode = "gradient";
					//linkTemplate.fillOpacity = 1.0;
					linkTemplate.strokeWidth = 10;
					linkTemplate.strokeOpacity = 0.85;
					linkTemplate.tension = 0.6;
					linkTemplate.controlPointDistance = 0.3;

					linkTemplate.tooltipText = "";

					var nodeTemplate = chart.nodes.template;
					nodeTemplate.draggable = false;
					nodeTemplate.clickable = false;
					nodeTemplate.width = 1;
					// nodeTemplate.nameLabel.disabled = true;
					nodeTemplate.inert = true;
					nodeTemplate.togglable = false;
					nodeTemplate.cursorOverStyle = am4core.MouseCursorStyle.pointer;
					 
					// 넓이 조절, 참고 : nodeTemplate.nameLabel.label.html의 div width
					nodeTemplate.nameLabel.width = 180;
					// nodeTemplate.nameLabel.strokeWidth=1;
					nodeTemplate.nameLabel.label.html = "<div style='word-break:break-all;width:180px;height:28px;'><b>{name}</b></div>";
					nodeTemplate.nameLabel.label.propertyFields.fill = "color";
					nodeTemplate.nameLabel.locationX = -4;      // 위치 조절, X
                    // 위치 조절, Y: nodePadding값 변화에 영향을 받는다.
					nodeTemplate.nameLabel.locationY = GAPS[rowCnt]();
					nodeTemplate.nameLabel.label.truncate = false;
					nodeTemplate.nameLabel.label.wrap = true;

					// add labels which will animate
					var bullet = chart.links.template.bullets.push(new am4charts.LabelBullet());
					bullet.label.text = ">>>";
					bullet.label.fill = am4core.color("#fcfcfc");
					bullet.label.isMeasured = false;
					bullet.isMeasured = false;

					// create animations
					chart.events.on("inited", function () {
						for (var i = 0; i < chart.links.length; i++) {
							var link = chart.links.getIndex(i);
							var bullet = link.bullets.getIndex(0);

							if (link.dataItem.toNode) {
								bullet.label.fontSize = 9;
								firstHalfAnimation(bullet);
							}
							else {
								link.bullets.removeValue(bullet);
							}
						}
					})

					function firstHalfAnimation(bullet) {
						var duration = 6000 * Math.random() + 3000;
						var animation = bullet.animate([{ property: "locationX", from: 0.2, to: 0.5 }, { property: "opacity", from: 0.9, to: 1 }], duration)
						animation.events.on("animationended", function (event) {
							secondHalfAnimation(event.target.object, duration);
						})
					}

					function secondHalfAnimation(bullet, duration) {
						var animation = bullet.animate([{ property: "locationX", from: 0.5, to: 0.8 }, { property: "opacity", from: 1, to: 0.9 }], duration)
						animation.events.on("animationended", function (event) {
							setTimeout(function () {
								firstHalfAnimation(event.target.object)
							}, Math.random() * 5000);
						})
					}

					chart.events.on("ready", function(event) {
						// console.log("tx-dataItems");
						// console.log(chart.dataItems);

						let dataItems = chart.dataItems;

						/*// hide node box
						chart.dataItems.values.forEach(element => {
							// console.log(element);
						});*/

						// Z node nameLabel 숨김
						if (dataItems.length === 1 &&
							dataItems.first.hasOwnProperty("toNode") == false) {
							dataItems.first.fromNode.nameLabel.disabled = true;
						}

						// 사용안함, 장애 상태일 경우 애니메이션 숨기기.
						const inactive = "inactive";
						const failure = "failure";
						for (var i = 0; i < dataItems.length; i++) {
							let item = dataItems.getIndex(i);
							if (item.toNode) {
								let str = item.properties.fromName;

								if (str.includes(inactive) || str.includes(failure)) {
									let bullet = item.link.bullets.getIndex(0);
									bullet.hide();
								}
							}
						}
					});
					
					// 노드(nameLabel) 클릭시 효과(숨김, 다시표시) 끄기
		            chart.nodes.template.events.off("hit");
		            // 노드(nameLabel) 클릭시 팝업 창 띄우기
		            chart.nodes.template.events.on("hit", function (event) {
		              let node = event.target;
		               const wl_name = chart.data[node.dataItem.index].wl_name;
		               sendGroupList(wl_name);
	            	});
				}
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	// rx 서비스 정보
	function getRx_proc(){
		$.ajax({
			type : "POST",
			url : "rx_proc.do",
			data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value
			+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
			+"&tx_div="+document.getElementById("tx_div").value+"&rx_div="+document.getElementById("rx_div").value,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null)  {
					<!-- rx Chart code -->
					am4core.options.commercialLicense = true;

					// Themes begin
					am4core.useTheme(am4themes_animated);
					// Themes end

					var chart = am4core.create("rx-sankey", am4charts.SankeyDiagram);
					chart.hiddenState.properties.opacity = 0;   // this creates initial fade-in

					const jsonData = JSON.parse(data);
					const rowCnt = jsonData.length - 1;         // -1 : gradation end color node

					chart.data = jsonData;

					// console.log("rx-chart");
					// console.log(chart);

					// 차트 패딩 값 조절
					// chart.paddingLeft = 0;
					chart.paddingRight = -1;    // node box 숨기기, 임시방편
					chart.paddingTop = PADDINGS[rowCnt]();
					// chart.paddingBottom = 0;

					// 노드 간 간격 조절
					chart.nodePadding = PADDINGS[rowCnt]();

					chart.dataFields.fromName = "from";
					chart.dataFields.toName = "to";
					chart.dataFields.value = "value";
					chart.dataFields.color = "color";

					// Get link template
					var linkTemplate = chart.links.template;
					linkTemplate.colorMode = "gradient";
					//linkTemplate.fillOpacity = 1.0;
					linkTemplate.strokeWidth = 10;
					linkTemplate.strokeOpacity = 0.85;
					linkTemplate.tension = 0.6;
					linkTemplate.controlPointDistance = 0.3;

					linkTemplate.tooltipText = "";

					var nodeTemplate = chart.nodes.template;
					nodeTemplate.draggable = false;
					nodeTemplate.clickable = false;
					nodeTemplate.width = 1;
					// nodeTemplate.nameLabel.disabled = true;
					nodeTemplate.inert = true;
					nodeTemplate.togglable = false;
					nodeTemplate.cursorOverStyle = am4core.MouseCursorStyle.pointer;

					// 넓이 조절, 참고 : nodeTemplate.nameLabel.label.html의 div width
					nodeTemplate.nameLabel.width=180;
					// nodeTemplate.nameLabel.strokeWidth=1;
					nodeTemplate.nameLabel.label.html = "<div style='word-break:break-all;width:180px;height:28px;'><b>{name}</b></div>";
					nodeTemplate.nameLabel.label.propertyFields.fill = "color";
					nodeTemplate.nameLabel.locationX=-181;		// 위치 조절, X
                    // 위치 조절, Y: nodePadding값 변화에 영향을 받는다.
					nodeTemplate.nameLabel.locationY= GAPS[rowCnt]();
					nodeTemplate.nameLabel.label.truncate = false;
					nodeTemplate.nameLabel.label.wrap = true;

					// add labels which will animate
					var bullet = chart.links.template.bullets.push(new am4charts.LabelBullet());
					bullet.label.text = ">>>";
					bullet.label.fill = am4core.color("#fcfcfc");
					bullet.label.isMeasured = false;
					bullet.isMeasured = false;

					// create animations
					chart.events.on("inited", function () {
						for (var i = 0; i < chart.links.length; i++) {
							var link = chart.links.getIndex(i);
							var bullet = link.bullets.getIndex(0);

							if (link.dataItem.toNode) {
								bullet.label.fontSize = 9;
								firstHalfAnimation(bullet);
							}
							else {
								link.bullets.removeValue(bullet);
							}
						}
					})

					function firstHalfAnimation(bullet) {
						var duration = 6000 * Math.random() + 3000;
						var animation = bullet.animate([{ property: "locationX", from: 0.2, to: 0.5 }, { property: "opacity", from: 1, to: 1 }], duration)
						animation.events.on("animationended", function (event) {
							secondHalfAnimation(event.target.object, duration);
						})
					}

					function secondHalfAnimation(bullet, duration) {
						var animation = bullet.animate([{ property: "locationX", from: 0.5, to: 0.8 }, { property: "opacity", from: 1, to: 1 }], duration)
						animation.events.on("animationended", function (event) {
							setTimeout(function () {
								firstHalfAnimation(event.target.object)
							}, Math.random() * 5000);
						})
					}

					chart.events.on("ready", function(event) {
						// console.log("rx-dataItems");
						// console.log(chart.dataItems);

						let dataItems = chart.dataItems;

						/*!// hide node box
						chart.dataItems.values.forEach(element => {
							// console.log(element);
						});*/

						// Z node nameLabel 숨김
						if (dataItems.length === 1 &&
								dataItems.first.hasOwnProperty("toNode") == false) {
							dataItems.first.fromNode.nameLabel.disabled = true;
						}

						// 사용안함, 장애 상태일 경우 애니메이션 숨기기.
						const inactive = "inactive";
						const failure = "failure";
						for (var i = 0; i < dataItems.length; i++) {
							let item = dataItems.getIndex(i);
							if (item.toNode) {
								let str = item.properties.toName;

								if (str.includes(inactive) || str.includes(failure)) {
									let bullet = item.link.bullets.getIndex(0);
									bullet.hide();
								}
							}
						}
					});
					
					// 노드(nameLabel) 클릭시 효과(숨김, 다시표시) 끄기
		            chart.nodes.template.events.off("hit");
		            // 노드(nameLabel) 클릭시 팝업 창 띄우기
		            chart.nodes.template.events.on("hit", function (event) {
		               let node = event.target;
		               const wl_name = chart.data[node.dataItem.index].wl_name;
		               sendGroupList(wl_name);
	            	});
				}
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	// tx 서비스 클릭 팝업
	function show_txpopup(e, nlp_seq){
		var divTop = e.clientY - 170; //상단 좌표 위치 안맞을시 e.pageY
		var divLeft = e.clientX - 50; //좌측 좌표 위치 안맞을시 e.pageX
		$("#main-pop").empty(); // 로딩되기 전까지 이전 잔상이 남음..
		$('#main-pop').load('/service_txpopup.do?nlp_seq='+nlp_seq+'&sdate='+document.getElementById("stat_sdate").value+'&edate='+document.getElementById("stat_edate").value
				+'&shour='+document.getElementById("stat_shour").value+'&ehour='+document.getElementById("stat_ehour").value
				+'&sminute='+document.getElementById("stat_sminute").value+'&eminute='+document.getElementById("stat_eminute").value);
		$('#main-pop').css({
		     "top": divTop
		     ,"left": divLeft
		     ,"position": "absolute"
		}).show();
	}
	// rx 서비스 클릭 팝업
	function show_rxpopup(e, nlp_seq){
		var divTop = e.clientY - 170; //상단 좌표 위치 안맞을시 e.pageY
		var divLeft = e.clientX - 50; //좌측 좌표 위치 안맞을시 e.pageX
		$("#main-pop").empty(); // 로딩되기 전까지 이전 잔상이 남음..
		$('#main-pop').load('/service_rxpopup.do?nlp_seq='+nlp_seq+'&sdate='+document.getElementById("stat_sdate").value+'&edate='+document.getElementById("stat_edate").value
				+'&shour='+document.getElementById("stat_shour").value+'&ehour='+document.getElementById("stat_ehour").value
				+'&sminute='+document.getElementById("stat_sminute").value+'&eminute='+document.getElementById("stat_eminute").value);
		$('#main-pop').css({
		     "top": divTop
		     ,"left": divLeft
		     ,"position": "absolute"
		}).show();
	}
</script>

<script type="text/javascript">
var chartProtocol = null;
var chartProtocolOut = null;
var in_detect = null;
var out_detect = null;
am4core.ready(function() {
	// mark del ---		
	am4core.options.commercialLicense = true;
	// --- mark del
	
	// -- 프로토콜별 통계 라인차트
	
	// Create chart instance
	chartProtocol = am4core.create("chartProtocol", am4charts.XYChart);
	chartProtocol.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm";
	chartProtocol.cursor = new am4charts.XYCursor();
	chartProtocol.cursor.behavior = "zoomX";
	chartProtocol.leftAxesContainer.layout = "vertical";
	chartProtocol.paddingLeft = 10;
	chartProtocol.paddingRight = 10;
	chartProtocol.paddingBottom = 0;
	chartProtocol.paddingTop = 0;
	
	chartProtocol.data = null;//${getChartDataJSON};

	// Configure number formatter
	chartProtocol.numberFormatter.numberFormat = "#.a";
		
	// Create axes
	var dateAxis = chartProtocol.xAxes.push(new am4charts.DateAxis());
	//dateAxis.dateFormatter.dateFormat = "yyyy-MM-dd HH:mm";
	dateAxis.dateFormats.setKey("day", "yy-MM-dd");
	dateAxis.dateFormats.setKey("hour", "yy-MM-dd HH:mm");
	dateAxis.renderer.minGridDistance = 50;
	
	// this makes the data to be grouped
	dateAxis.groupData = true;
	dateAxis.height=30;
	dateAxis.fontSize = 11;
	dateAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");

	
	var valueAxis = chartProtocol.yAxes.push(new am4charts.ValueAxis());
	valueAxis.margin(0,0,0,0);
	valueAxis.renderer.inside = true;
	valueAxis.renderer.labels.template.fillOpacity = 0;
	valueAxis.renderer.grid.template.strokeOpacity = 0;
	//valueAxis.height = 120;
	valueAxis.fontSize = 11;
	valueAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");
	valueAxis.tooltip.disabled = true;
			
	chartProtocol.legend = new am4charts.Legend();
	chartProtocol.legend.align = "center";
	chartProtocol.legend.scrollable = true;
	chartProtocol.legend.fontSize = 11;
	chartProtocol.legend.labels.template.fill = am4core.color("#6f6f6f");
	chartProtocol.legend.markers.template.width=10;
	chartProtocol.legend.markers.template.height=10;
	chartProtocol.legend.itemContainers.template.paddingTop = 0;
	chartProtocol.legend.itemContainers.template.paddingBottom = 0;
	
	var activeLegendLebel = chartProtocol.legend.labels.template.states.getKey("active")
	
	
	
	
	// Create chart instance
	chartProtocolOut = am4core.create("chartProtocolOut", am4charts.XYChart);
	chartProtocolOut.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm";
	chartProtocolOut.cursor = new am4charts.XYCursor();
	chartProtocolOut.cursor.behavior = "zoomX";
	chartProtocolOut.leftAxesContainer.layout = "vertical";
	chartProtocolOut.paddingLeft = 10;
	chartProtocolOut.paddingRight = 10;
	chartProtocolOut.paddingBottom = 0;
	chartProtocolOut.paddingTop = 0;
	
	chartProtocolOut.data = null;

	// Configure number formatter
	chartProtocolOut.numberFormatter.numberFormat = "#.a";
		
	// Create axes
	var dateAxisOut = chartProtocolOut.xAxes.push(new am4charts.DateAxis());
	dateAxisOut.dateFormats.setKey("day", "yy-MM-dd");
	dateAxisOut.dateFormats.setKey("hour", "yy-MM-dd HH:mm");
	dateAxisOut.renderer.minGridDistance = 50;
	
	// this makes the data to be grouped
	dateAxisOut.groupData = true;
	dateAxisOut.height=30;
	dateAxisOut.fontSize = 11;
	dateAxisOut.renderer.labels.template.fill = am4core.color("#6f6f6f");

	
	var valueAxisOut = chartProtocolOut.yAxes.push(new am4charts.ValueAxis());
	valueAxisOut.margin(0,0,0,0);
	valueAxisOut.renderer.inside = true;
	valueAxisOut.renderer.labels.template.fillOpacity = 0;
	valueAxisOut.renderer.grid.template.strokeOpacity = 0;
	valueAxisOut.fontSize = 11;
	valueAxisOut.renderer.labels.template.fill = am4core.color("#6f6f6f");
	valueAxisOut.tooltip.disabled = true;
			
	chartProtocolOut.legend = new am4charts.Legend();
	chartProtocolOut.legend.align = "center";
	chartProtocolOut.legend.scrollable = true;
	chartProtocolOut.legend.fontSize = 11;
	chartProtocolOut.legend.labels.template.fill = am4core.color("#6f6f6f");
	chartProtocolOut.legend.markers.template.width=10;
	chartProtocolOut.legend.markers.template.height=10;
	chartProtocolOut.legend.itemContainers.template.paddingTop = 0;
	chartProtocolOut.legend.itemContainers.template.paddingBottom = 0;
	
	var activeLegendLebelOut = chartProtocolOut.legend.labels.template.states.getKey("active")
	

	
	
	
	
	// Create chart instance
	in_detect = am4core.create("in_detect", am4charts.XYChart);
	in_detect.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm";
	in_detect.cursor = new am4charts.XYCursor();
	in_detect.cursor.behavior = "zoomX";
	in_detect.leftAxesContainer.layout = "vertical";
	in_detect.paddingLeft = 10;
	in_detect.paddingRight = 10;
	in_detect.paddingBottom = 0;
	in_detect.paddingTop = 0;
	
	in_detect.data = null;//${getChartDataJSON};

	// Configure number formatter
	in_detect.numberFormatter.numberFormat = "#.a";
		
	// Create axes
	var dateAxis = in_detect.xAxes.push(new am4charts.DateAxis());
	//dateAxis.dateFormatter.dateFormat = "yyyy-MM-dd HH:mm";
	dateAxis.dateFormats.setKey("day", "yy-MM-dd");
	dateAxis.dateFormats.setKey("hour", "yy-MM-dd HH:mm");
	dateAxis.renderer.minGridDistance = 50;
	
	// this makes the data to be grouped
	dateAxis.groupData = true;
	dateAxis.height=30;
	dateAxis.fontSize = 11;
	dateAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");

	
	var valueAxis = in_detect.yAxes.push(new am4charts.ValueAxis());
	valueAxis.margin(0,0,0,0);
	valueAxis.renderer.inside = true;
	valueAxis.renderer.labels.template.fillOpacity = 0;
	valueAxis.renderer.grid.template.strokeOpacity = 0;
	//valueAxis.height = 120;
	valueAxis.fontSize = 11;
	valueAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");
	valueAxis.tooltip.disabled = true;
			
	in_detect.legend = new am4charts.Legend();
	in_detect.legend.align = "center";
	in_detect.legend.scrollable = true;
	in_detect.legend.fontSize = 11;
	in_detect.legend.labels.template.fill = am4core.color("#6f6f6f");
	in_detect.legend.markers.template.width=10;
	in_detect.legend.markers.template.height=10;
	in_detect.legend.markers.template.strokeWidth=0;
	in_detect.legend.itemContainers.template.paddingTop = 0;
	in_detect.legend.itemContainers.template.paddingBottom = 0;
	
	var activeLegendLebel = in_detect.legend.labels.template.states.getKey("active")
	
	// Create chart instance
	out_detect = am4core.create("out_detect", am4charts.XYChart);
	out_detect.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm";
	out_detect.cursor = new am4charts.XYCursor();
	out_detect.cursor.behavior = "zoomX";
	out_detect.leftAxesContainer.layout = "vertical";
	out_detect.paddingLeft = 10;
	out_detect.paddingRight = 10;
	out_detect.paddingBottom = 0;
	out_detect.paddingTop = 0;
	
	out_detect.data = null;//${getChartDataJSON};

	// Configure number formatter
	out_detect.numberFormatter.numberFormat = "#.a";
		
	// Create axes
	var dateAxis = out_detect.xAxes.push(new am4charts.DateAxis());
	//dateAxis.dateFormatter.dateFormat = "yyyy-MM-dd HH:mm";
	dateAxis.dateFormats.setKey("day", "yy-MM-dd");
	dateAxis.dateFormats.setKey("hour", "yy-MM-dd HH:mm");
	dateAxis.renderer.minGridDistance = 50;
	
	// this makes the data to be grouped
	dateAxis.groupData = true;
	dateAxis.height=30;
	dateAxis.fontSize = 11;
	dateAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");

	
	var valueAxis = out_detect.yAxes.push(new am4charts.ValueAxis());
	valueAxis.margin(0,0,0,0);
	valueAxis.renderer.inside = true;
	valueAxis.renderer.labels.template.fillOpacity = 0;
	valueAxis.renderer.grid.template.strokeOpacity = 0;
	//valueAxis.height = 120;
	valueAxis.fontSize = 11;
	valueAxis.renderer.labels.template.fill = am4core.color("#6f6f6f");
	valueAxis.tooltip.disabled = true;
			
	out_detect.legend = new am4charts.Legend();
	out_detect.legend.align = "center";
	out_detect.legend.scrollable = true;
	out_detect.legend.fontSize = 11;
	out_detect.legend.labels.template.fill = am4core.color("#6f6f6f");
	out_detect.legend.markers.template.width=10;
	out_detect.legend.markers.template.height=10;
	out_detect.legend.markers.template.strokeWidth=0;
	out_detect.legend.itemContainers.template.paddingTop = 0;
	out_detect.legend.itemContainers.template.paddingBottom = 0;
	
	var activeLegendLebel = out_detect.legend.labels.template.states.getKey("active")
}); // end am4core.ready()

//Create series
function createSeriesProtocol(name, color, chartP) {
	let series = chartP.series.push(new am4charts.ColumnSeries());
	series.autoDispose = true;
	series.fillOpacity = 1;
	series.fill = am4core.color(color);
	series.strokeWidth=0;
	series.strokeOpacity=0;
	series.dataFields.dateX = "dateTime";
	series.dataFields.valueY = name;
	series.tooltipText = name+": {valueY.value.formatNumber('#,###.')}";
	series.name = name;
	series.stacked = true;//  컬럼에서는 위로 쌓이게 라인에서는 이어 지지 않게
	
//  클릭 이벤트
// 	series.columns.template.events.on("hit", function(ev) {
// 		var form = document.getElementById("frmDate");
// 		form.pi_id.value=name;
// 		menuSubmit("total_protocols.do");
// 	});
	
	// Set mouse style on hover
// 	series.columns.template.cursorOverStyle = am4core.MouseCursorStyle.pointer;
  
	return series;
}

function getThreatChartDataAjax(){ // 네트워크 수집 현황
	$.ajax({
		type : "POST",  
		url : "getThreatChartDataJSON.do",
		async: true,  //  ajax 동기식 실행
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
		+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
		+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
		+"&inout=0", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data!=null && data.chartData !=null && data.chartData.length > 0){
				let arrColor = ["#E67030","#CC1F1B"];
				
				if(data.protoList != null){
					for(var i=0; i<data.protoList.length; i++){
						let color = "#ababab";
						if(i<11){
							color = arrColor[i];
						}
						createSeriesProtocol(data.protoList[i], color, in_detect);
						
					}
				}
				in_detect.data=data.chartData;
				
			}
		},
		error:function(request,status,error){   
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	$.ajax({
		type : "POST",  
		url : "getThreatChartDataJSON.do",
		async: true,  //  ajax 동기식 실행
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
		+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
		+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
		+"&inout=1", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data!=null && data.chartData !=null && data.chartData.length > 0){
				let arrColor = ["#E67030","#CC1F1B"];
				
				if(data.protoList != null){
					for(var i=0; i<data.protoList.length; i++){
						let color = "#ababab";
						if(i<11){
							color = arrColor[i];
						}
						createSeriesProtocol(data.protoList[i], color, out_detect);
						
					}
				}
				out_detect.data=data.chartData;
				
			}
		},
		error:function(request,status,error){   
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function getChartDataAjax(){ // 네트워크 수집 현황
	$.ajax({
		type : "POST",  
		url : "getChartDataJSON.do",
		async: true,  //  ajax 동기식 실행
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
		+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
		+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
		+"&inout=0", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data!=null && data.chartData !=null && data.chartData.length > 0){
				let arrColor = ["#67B7DC","#6794DC","#6770DC","#8067DC","#A367DC","#C767DC","#DC67CE","#DC67B7","#DC6794","#DC6780"];
				
				if(data.whiteList != null){
					for(var i=0; i<data.whiteList.length; i++){
						let color = "#ababab";
						if(i<11){
							color = arrColor[i];
						}
						createSeriesProtocol(data.whiteList[i], color, chartProtocol);
						
					}
				}
				chartProtocol.data=data.chartData;
				
			}
		},
		error:function(request,status,error){   
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	$.ajax({
		type : "POST",  
		url : "getChartDataJSON.do",
		async: true,  //  ajax 동기식 실행
		data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value
		+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value
		+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value
		+"&inout=1", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
		success: function(data) {
			if(data!=null && data.chartData !=null && data.chartData.length > 0){
				let arrColor = ["#67B7DC","#6794DC","#6770DC","#8067DC","#A367DC","#C767DC","#DC67CE","#DC67B7","#DC6794","#DC6780"];
				
				if(data.whiteList != null){
					for(var i=0; i<data.whiteList.length; i++){
						let color = "#ababab";
						if(i<11){
							color = arrColor[i];
						}
						createSeriesProtocol(data.whiteList[i], color, chartProtocolOut);
						
					}
				}
				chartProtocolOut.data=data.chartData;
				
			}
		},
		error:function(request,status,error){   
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function sendGroupList(wl_name) {
    $.ajax({
        url: "/group_list_grid.do",
        type: "GET",
        data: { wl_name: wl_name }, // wl_name을 요청 파라미터로 전송
        success: function(response) {
        	window.location.href = "/white_ics.do?wl_name=" + wl_name;
        },
        error: function(xhr, status, error) {
            console.error("Error sending Policy Name:", error);
        }
    });
}
</script>
</body>
</html>