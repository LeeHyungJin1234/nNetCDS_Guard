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
<link rel="stylesheet" href="/css/reset.css" />
<link rel="stylesheet" href="/css/jquery-ui.css">
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/traffic.css" />
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script src="/amcharts/amcharts.js"></script>
<script src="/amcharts/serial.js"></script>
</head>
<body>
<div class="main-pop">
	<input type="hidden" id="nlp_seq" name="nlp_seq" value="${ncPolicy.nlp_seq}"/>
	<input type="hidden" id="ncp_file_name" name="ncp_file_name" value="${ncPolicy.ncp_file_name}"/>
	<input type="hidden" id="sdate" name="sdate" value="${sdate}"/>
	<input type="hidden" id="edate" name="edate" value="${edate}"/>
	<input type="hidden" id="shour" name="shour" value="${shour}"/>
	<input type="hidden" id="ehour" name="ehour" value="${ehour}"/>
	<input type="hidden" id="sminute" name="sminute" value="${sminute}"/>
	<input type="hidden" id="eminute" name="eminute" value="${eminute}"/>
	<div class="title"><span>${ncPolicy.nlp_name} &nbsp;<span style=font-size:13px;>(${ncPolicy.ntc_name} | ${ncPolicy.ndc_name})</span></span><img src="/img/main_pop_close.png" onClick="$('#main-pop').css('display','none');"></div>
	<div class="content">
		<div class="text">
			<div><span>출발지IP</span><span>${ncPolicy.npr_source_ip}</span></div>
			<div><span>도착지IP:PORT</span><span>${ncPolicy.npr_destination_ip}:${ncPolicy.npr_destination_port}</span></div>
		</div>
		<div class="graph">
			<div class="graph1">
				<span>일방향 데이터 수신</span>
				<div id="recv" class="chart"></div>
			</div>
			<div class="graph2">
     			<span>외부망 데이터 송신</span>
     			<div id="send" class="chart"></div>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		$.ajaxSetup({ cache: false });//ajax 캐시 사용안함
		getTraffic_ct();
	});
	// 차트 스타일 정의
	function createLineChart(charId, chartData){
		return AmCharts.makeChart(charId, {
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
					"title": "Axis title"
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
			url : "/trafficPopRx.do",
			data : "sdate="+document.getElementById("sdate").value+"&edate="+document.getElementById("edate").value
				+"&shour="+document.getElementById("shour").value+"&ehour="+document.getElementById("ehour").value
				+"&sminute="+document.getElementById("sminute").value+"&eminute="+document.getElementById("eminute").value
				+"&ncp_file_name="+document.getElementById("ncp_file_name").value,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				createLineChart("recv", data.recvCtData);
				createLineChart("send", data.sendCtData);
			},
			error: function(request,status,error){
				alert('불러오기 중 에러가 발생하였습니다.\n'+error);
			}
		});
	}
</script>
</body>
</html>