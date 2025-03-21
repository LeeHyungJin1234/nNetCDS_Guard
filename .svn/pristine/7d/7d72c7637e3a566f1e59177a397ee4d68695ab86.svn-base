<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ include file="/WEB-INF/views/include/css/manager_css.jsp"%>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script src="<c:url value="/js/deline.js"/>"></script>
<!--  amCharts 4 -->
<script src="<c:url value="/amcharts4/core.js"/>"></script>
<script src="<c:url value="/amcharts4/charts.js"/>"></script>
<script src="<c:url value="/amcharts4/themes/animated.js"/>"></script>
<!--  amchart -->
<script src="/amcharts/amcharts.js"></script>
<script src="/amcharts/serial.js"></script>
<script src="/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/amcharts/plugins/export/export.css" type="text/css" media="all" />
<script src="/amcharts/themes/black.js"></script>
<script type="text/javascript">
// window.onload = function() {
// 	// 접근 현황
// 	getLogin_tb();
// 	drawLogin();
// 	// 운영환경 변경 현황
// 	getConfig_tb();
// 	drawConfig();
// 	// 정책 변경 현황
// 	getPolicy_tb();
// 	drawPolicy();
// }
	$(document).ready(function () {
		am4core.options.commercialLicense = true;
		
		// 자체시험/모니터링/
		getProt_log();
		drawSystemLoadChart();
		// 접근 현황
		getLogin_tb();
		drawLogin();
		// 운영환경 변경 현황
		getConfig_tb();
		drawConfig();
		// 정책 변경 현황
		getPolicy_tb();
		drawPolicy();
	});

	function search() {
		var form = document.getElementById("search");
		var temp = calDateRange(form.nlm_create_sdate.value, form.nlm_create_edate.value);
		if(temp<0){
			alert("<spring:message code='stat.searchdategrat'/>");
			return;
		}
		if(temp==null || temp>30){
			alert("<spring:message code='stat.searchmonth'/>");
			return;
		}
		form.action="stat_manager.do";
		form.submit();
	}
	
	function setDate(day){
		var today = new Date();
		var from = new Date(Date.parse(today) - 1000 * 60 * 60 * 24 * day); //day일수 만큼 전;
		
		var today_month = today.getMonth()+1;
		if(today_month<10){
			today_month = "0"+today_month;
		}
		var today_date = today.getDate();
		if(today_date<10){
			today_date = "0"+today_date;
		}
		document.getElementById("to").value = today.getFullYear()+"-"+today_month+"-"+today_date;

		var from_month = from.getMonth()+1;
		if(from_month<10){
			from_month = "0"+from_month;
		}
		var from_date = from.getDate();
		if(from_date<10){
			from_date = "0"+from_date;
		}
		document.getElementById("from").value = from.getFullYear()+"-"+from_month+"-"+from_date;
		
		document.getElementById("datetab").value = day;
	}
	// 날짜탭 초기화
	function init_datetab(){
		document.getElementById("datetab").value = "";
		var tablink = document.getElementsByClassName('tablinks');
		for (var i=0; i<tablink.length; i++) {
			tablink[i].classList.remove('active');
		}
	}
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	<section id="main-form">
		<div class="main-wrapper">
			<form id="search" method="get">
			<input type="hidden" name="datetab" id="datetab" value="${ncMngData.datetab}" />
				<ul id="search" class="clearfix">
					<li>
						<span style="vertical-align: unset;"><spring:message code='stat.adminid'/></span>
						<select name="nsu_id">
							<option value="" selected><spring:message code='stat.total'/></option>
							<c:forEach var="user" items="${userList}" varStatus="status">
							<c:if test="${user.nsu_id!='tech'}">
								<option value="${user.nsu_id}" <c:if test="${ncMngData.nsu_id == user.nsu_id}">selected</c:if>>${user.nsu_id}</option>
							</c:if>
							</c:forEach>
						</select>							
					</li>
					<li>
						<span><spring:message code='stat.searchperiod'/></span>
						<input type="text" id="from" name="nlm_create_sdate" class="input date" value="${ncMngData.nlm_create_sdate}">
						<img src="/img/search-bar2.png">
						<input type="text" class="input date" id="to" name="nlm_create_edate" value="${ncMngData.nlm_create_edate}">
					</li>
					<li>
						<ul class="tab clearfix">
							<li><div class="tablinks <c:if test="${ncMngData.datetab eq '7'}">active</c:if>" onClick="javascript:setDate('7');"><a>7일</a></div></li>
							<li><div class="tablinks <c:if test="${ncMngData.datetab eq '14'}">active</c:if>" style="margin-left:-1px; padding:0 15px;" onClick="javascript:setDate('14');"><a>14일</a></div></li>
							<li><div class="tablinks <c:if test="${ncMngData.datetab eq '30'}">active</c:if>" style="margin-left:-1px; padding:0 18px;" onClick="javascript:setDate('30');"><a>1달</a></div></li>
						</ul>
					</li>
					<li><a href="javascript:search();" id="submitbtn"><img src="/img/search.png"><spring:message code='btn.search'/></a></li>
				</ul>
			</form>
		</div>
	</section>
	<!-- left menu start -->
	<section id="content">
        <div class="main-wrapper">
			<div class="content-box content4">
				<div class="title">
					<span><spring:message code='stat.sysstat'/></span>
					<span><spring:message code='stat.sysdesc'/></span>
				</div>
				<div class="chart">
					<div id="prot_charts" class="chartdiv" style="width:450px;height:440px;background-color:#191f28; ">
						<div id="chart_area_cpu" class="flex-sub-container">
							<span class="flex-title"><spring:message code='stat.cpu'/></span>
							<div id="chart_cpu" class="flex-item"></div>
						</div>
						<div id="chart_area_ram" class="flex-sub-container">
							<span class="flex-title"><spring:message code='stat.memory'/></span>
							<div id="chart_ram" class="flex-item"></div>
						</div>
						<div id="chart_area_disk" class="flex-sub-container">
							<span class="flex-title"><spring:message code='stat.disk'/></span>
							<div id="chart_disk" class="flex-item"></div>
						</div>
					</div>
				</div>
				<div class="board">
					<table>
						<thead>
							<tr>
								<th class="group1"><spring:message code="stat.prothistory"/></th>
								<th class="group2">
									<a href="javascript:view_all_logs();" id="view-all-logs"><spring:message code="stat.protlog"/></a>
								</th>
							</tr>
						</thead>
					</table>
					<div id='prot_table'>
						<div id="prot_history" class="tabcontent">
							<ul>
								<li><span></span><span></span></li>
								<li><span></span><span></span></li>
								<li><span></span><span></span></li>
								<li><span></span><span></span></li>
								<li><span></span><span></span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
            <div class="content-box content1">
                <div class="title">
                    <span><spring:message code='stat.accessstat'/></span>
                    <span><spring:message code='stat.accessdesc'/></span>
                </div>
                <div class="chart">
                    <div id="login_chart" class="chartdiv" style="width:450px;height:420px;background-color:#191f28; "></div>
                </div>
                <div class="board">
                    <table>
                        <thead>
                            <tr>
                                <th class="group1">계정</th>
                                <th class="group2"><spring:message code='stat.accesscount'/></th>
                                <th class="group3"><div class="board-circle content1-board-circle3"></div><spring:message code='stat.accesssuccess'/></th>
                                <th class="group4"><div class="board-circle content1-board-circle1"></div><spring:message code='stat.accessfail'/></th>
                            </tr>
                        </thead>
					</table>
					<div id='login_table'></div>
                </div>
            </div>
            <div class="content-box content2">
                <div class="title">
                    <span><spring:message code='stat.operatstat'/></span>
                    <span><spring:message code='stat.operatdesc'/></span>
                </div>
                <div class="chart">
                    <div id="config_chart" class="chartdiv" style="width:450px;height:420px;background-color:#191f28;"></div>
                </div>
                <div class="board">
                    <div class="tab clearfix">
                        <button class="tablinks1 active" onclick="openTab(event, 'tab01')"><div class="board-circle content2-board-circle1"></div><spring:message code='stat.manager'/></button>
                        <button class="tablinks1" onclick="openTab(event, 'tab02')"><div class="board-circle content2-board-circle2"></div>정방향</button>
<%--                         <c:if test="${Config.getInstance().getModel() eq 'dual'}"> --%>
<!--                         	<button class="tablinks1" onclick="openTab(event, 'tab03')"><div class="board-circle content2-board-circle3"></div>역방향</button> -->
<%--                         </c:if> --%>
                        <button class="tablinks1" onclick="openTab(event, 'tab04')"><div class="board-circle content2-board-circle4"></div><spring:message code='stat.etc'/></button>
                        <span><spring:message code='stat.count'/></span>
                    </div>
                    <div id='config_table'></div>
                </div>
            </div>
            <div class="content-box content3">
                <div class="title">
                    <span><spring:message code='stat.policystat'/></span>
                    <span><spring:message code='stat.policystatdesc'/></span>
                </div>
                <div class="chart">
                    <div id="policy_chart" class="chartdiv" style="width:450px;height:420px;background-color:#191f28;"></div>
                </div>
                <div class="board">
                    <table>
                        <thead>
                            <tr>
                                <th class="group1"><spring:message code='stat.menu'/></th>
                                <th class="group2"><spring:message code='stat.count'/></th>
                            </tr>
                        </thead>
                    </table>
                    <div id='policy_table'></div>
                </div>
            </div>
        </div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
	
<script type="text/javascript">
	//실시간 로그
	function getProt_log() {
		var form = document.getElementById("search");
		
		$.ajax({
			type: "GET",
			url: "prot_log.do",
			data: "sdate=" + form.nlm_create_sdate.value + "&edate=" + form.nlm_create_edate.value + "&tx_div=" + 1,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function (data) {
				if (data != null) {
					// TODO: 스타일 적용
					const logs = JSON.parse(data);
					// console.error(logs);
					if (logs.length === 0)
						return ;
					// html 부분에 고정적으로 5개 생성, 데이터만 넣어준다.
					let i = 0;
					const lis = $("#prot_history")
						.children()
						.children()
						.each(function () {
							$(this).children()
								.text(logs[i].nlpt_create_date)
								.next()
								.text(logs[i].nlpt_message);
							i++;
						});
				}
			},
			error: function (request, status, error) {
				alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			}
		});
	}
	
	function drawLogin(){
        $.ajax({
			type : "GET",  
			url : "/login_ct.do?sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data1) {
				var chart1 = AmCharts.makeChart("login_chart", {
          			"theme": "black",
          		    "type": "serial",
          		    "dataDateFormat": "YYYY-MM-DD",
          		    "dataProvider": data1.data_value,
          		    "valueAxes": [{
          		        "id": "v1",
          		        "axisAlpha": 0.1,
          	       	 	"position": "left"
          		    }],
          		    "graphs": data1.graphs_value,
          		    "chartScrollbar": {
          		     	"autoGridCount": true,
	          	        "oppositeAxis":false,
	          	        "offset": 20,
	          	        "scrollbarHeight": 40,
	          	        "graph": "id1",
	          	        "dragIconHeight": 40,
	          	        "dragIconWidth": 20,
	          	        "dragIcon": "dragIconRectSmall"
          		    },
          			"chartCursor": {
          				"categoryBalloonDateFormat": "YYYY-MM-DD"
          		    },
          		    "categoryField": "date",
          			"categoryAxis": {
          		        "minPeriod": "DD",
          		        "parseDates": true,
          		      	"dateFormats": [{"period":"fff","format":"JJ:NN:SS"},{"period":"ss","format":"JJ:NN:SS"},{"period":"mm","format":"JJ:NN"},{"period":"hh","format":"JJ:NN"},
							{"period":"DD","format":"YYYY-MM-DD"},{"period":"WW","format":"MM-DD"},{"period":"MM","format":"MM"},{"period":"YYYY","format":"YYYY"}]
          		    }
          		});
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	function getLogin_tb(){
		$.ajax({        
			type : "GET",  
			url : "login_tb.do",
			data : "sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) { 
				if(data != null) {
					document.getElementById('login_table').innerHTML = data;
				}    
			}  
		});
	}

	function drawConfig(){
		var model = '<c:out value="${Config.getInstance().getModel()}"/>';
		var graphs, colors;
		if(model=='single'){
			colors = ["#c79d1b","#1b72cc","#a061d4"];
			graphs = [
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-1",
							"lineAlpha": 0.2,
							"title": "관리자",
							"type": "column",
							"valueField": "관리자"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-2",
							"lineAlpha": 0.2,
							"title": "정방향",
							"type": "column",
							"valueField": "정방향"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-4",
							"lineAlpha": 0.2,
							"title": "기타",
							"type": "column",
							"valueField": "기타"
						}
					];
		}else{
			colors = ["#c79d1b","#1b72cc","#cc1f1b","#a061d4"];
			graphs = [
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-1",
							"lineAlpha": 0.2,
							"title": "관리자",
							"type": "column",
							"valueField": "관리자"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-2",
							"lineAlpha": 0.2,
							"title": "정방향",
							"type": "column",
							"valueField": "정방향"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-3",
							"lineAlpha": 0.2,
							"title": "역방향",
							"type": "column",
							"valueField": "역방향"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-4",
							"lineAlpha": 0.2,
							"title": "기타",
							"type": "column",
							"valueField": "기타"
						}
					];
		}
			
		
        $.ajax({
			type : "GET",  
			url : "/config_ct.do?sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data1) {
				var chart = AmCharts.makeChart("config_chart", {
					"type": "serial",
				    "theme": "black",
					"categoryField": "date",
					"colors": colors,
					"startDuration": 1,
					"categoryAxis": {
						"position": "left",
				     	"boldPeriodBeginning":false,
				     	"parseDates": true,
				     	"equalSpacing":true,
				     	"dateFormats": [{"period":"fff","format":"JJ:NN:SS"},{"period":"ss","format":"JJ:NN:SS"},{"period":"mm","format":"JJ:NN"},{"period":"hh","format":"JJ:NN"},
				     	                {"period":"DD","format":"YYYY-MM-DD"},{"period":"WW","format":"MM-DD"},{"period":"MM","format":"MM"},{"period":"YYYY","format":"YYYY"}]
					},
					"graphs": graphs,
					"valueAxes": [
						{
							"id": "ValueAxis-1",
							"position": "left",
							"axisAlpha": 0
						}
					],
					"dataProvider": data1.data_value,
					"chartCursor": {
						"categoryBalloonDateFormat": "YYYY-MM-DD"
					},
		  		 	"chartScrollbar": {
			        	"autoGridCount": true,
			        	"oppositeAxis":false,
			        	"offset": 20,
			        	"scrollbarHeight": 40,
			        	"graph": "AmGraph-1",
			        	"dragIconHeight": 40,
			        	"dragIconWidth": 20,
			        	"dragIcon": "dragIconRectSmall"
			    	}
				});
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
    }
	function getConfig_tb(){
		$.ajax({        
			type : "GET",  
			url : "config_tb.do",
			data : "sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data) { 
				if(data != null) {
					document.getElementById('config_table').innerHTML = data;
				}
			}
		});
	}
	
	function drawPolicy(){
		var model = '<c:out value="${Config.getInstance().getModel()}"/>';
		var graphs;
		if(model=='single'){
			graphs = [
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-1",
							"lineAlpha": 0.2,
							"title": "정방향",
							"type": "column",
							"valueField": "정방향"
						}
					];
		}else{
			graphs = [
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-1",
							"lineAlpha": 0.2,
							"title": "정방향",
							"type": "column",
							"valueField": "정방향"
						},
						{
							"balloonText": "[[value]]",
							"fillAlphas": 0.8,
							"id": "AmGraph-2",
							"lineAlpha": 0.2,
							"title": "역방향",
							"type": "column",
							"valueField": "역방향"
						}
					];
		}
        $.ajax({
			type : "GET",  
			url : "/policy_ct.do?sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
			success: function(data1) {
				var chart = AmCharts.makeChart("policy_chart", {
					"type": "serial",
				    "theme": "black",
					"categoryField": "date",
					"colors": ["#1b72cc","#cc1f1b"],
					"startDuration": 1,
					"categoryAxis": {
						"position": "left",
				     	"boldPeriodBeginning":false,
				     	"parseDates": true,
				     	"equalSpacing":true,
				     	"dateFormats": [{"period":"fff","format":"JJ:NN:SS"},{"period":"ss","format":"JJ:NN:SS"},{"period":"mm","format":"JJ:NN"},{"period":"hh","format":"JJ:NN"},
				     	                {"period":"DD","format":"YYYY-MM-DD"},{"period":"WW","format":"MM-DD"},{"period":"MM","format":"MM"},{"period":"YYYY","format":"YYYY"}]
					},
					"graphs": graphs,
					"valueAxes": [
						{
							"id": "ValueAxis-1",
							"position": "left",
							"axisAlpha": 0
						}
					],
					"dataProvider": data1.data_value,
					"chartCursor": {
						"categoryBalloonDateFormat": "YYYY-MM-DD"
					},
		  		 	"chartScrollbar": {
			        	"autoGridCount": true,
			        	"oppositeAxis":false,
			        	"offset": 20,
			        	"scrollbarHeight": 40,
			        	"graph": "AmGraph-1",
			        	"dragIconHeight": 40,
			        	"dragIconWidth": 20,
			        	"dragIcon": "dragIconRectSmall"
			    	}
				});
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
    }
	function getPolicy_tb(){
		$.ajax({
			type : "GET",
			url : "policy_tb.do",
			data : "sdate=${ncMngData.nlm_create_sdate}&edate=${ncMngData.nlm_create_edate}&nsu_id=${ncMngData.nsu_id}",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				if(data != null) {
					document.getElementById('policy_table').innerHTML = data;
				}
			}
		});
	}
	// 운영환경 탭
	function openTab(evt, tabName) {
		var i, tabcontent, tablinks;
		tabcontent = document.getElementsByClassName("tabcontent");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("tablinks1");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace("active", "");
		}
		document.getElementById(tabName).style.display = "block";
		evt.currentTarget.className = "tablinks1 active";
	}
	
	// CPU, RAM, Disk 사용량 차트
	function drawSystemLoadChart() {
		$.ajax({
			type: "GET",
			url: "/sys_load.do",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function (data) {
				if (data !== null) {
					createCpuLoadChart(data.cpuLoad);
					createRamLoadChart(data.ramLoad);
					createDiskLoadChart(data.diskLoad);
				}
			},
			error: function () {
				alert("<spring:message code="alert.errorload"/>");
			}
		});
	}

	// CPU 사용량 차트 만들기
	function createCpuLoadChart(data) {
		createXXXLoadChart(makeChartData("chart_cpu", data));
	}
	
	// RAM 사용량 차트 만들기
	function createRamLoadChart(data) {
		createXXXLoadChart(makeChartData("chart_ram", data));
	}
	
	// Disk 사용량 차트 만들기
	function createDiskLoadChart(data) {
		createXXXLoadChart(makeChartData("chart_disk", data));
	}

	function makeChartData(category, percentage) {
		const color = ({
			0: am4core.color("#76C76C"),
			1: am4core.color("#DDB73B"),
			2: am4core.color("#CA444A")
			// 0: "#76C76C",
			// 1: "#DDB73B",
			// 2: "#CA444A"
		}) [((percentage >= 80) ? 2 : 0) || (percentage >= 60 ? 1 : 0)];
		
		const chartData = [{
			"category": category,
			"value": percentage,
			"color": color,
			"full": 100,
			"width": am4core.percent(0)
		}];
		return chartData;
	}

	function createXXXLoadChart(data) {
		// Themes begin
		am4core.useTheme(am4themes_animated);
		// Themes end
		
		// Create chart instance
		var chart = am4core.create(data[0].category, am4charts.RadarChart);
		
		// Add data
		chart.data = data;
		
		// Make chart not full circle
		chart.startAngle = -180;
		chart.endAngle = 0;
		chart.innerRadius = am4core.percent(80);
		
		// Set number format
		chart.numberFormatter.numberFormat = "#.#'%'";
		
		chart.paddingTop = 0;
		chart.paddingBottom = 0;
		chart.paddingLeft = 0;
		chart.paddingRight = 0;
		
		// Create axes
		var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
		categoryAxis.dataFields.category = "category";
		categoryAxis.renderer.grid.template.disabled = true;
		categoryAxis.renderer.labels.template.disabled = true;
		
		var valueAxis = chart.xAxes.push(new am4charts.ValueAxis());
		valueAxis.renderer.grid.template.strokeOpacity = 0;
		valueAxis.min = 0;
		valueAxis.max = 100;
		valueAxis.strictMinMax = true;
		valueAxis.renderer.grid.template.disabled = true;
		valueAxis.renderer.labels.template.disabled = true;
		
		// Create series
		var series1 = chart.series.push(new am4charts.RadarColumnSeries());
		series1.dataFields.valueX = "full";
		series1.dataFields.categoryY = "category";
		series1.clustered = false;
		// series1.columns.template.fill = new am4core.InterfaceColorSet().getFor("alternativeBackground");
		// series1.columns.template.fillOpacity = 0.08;
		series1.columns.template.fill = am4core.color("#3E434A");
		series1.columns.template.fillOpacity = 1;
		series1.columns.template.cornerRadiusTopLeft = 20;
		series1.columns.template.strokeWidth = 0;
		series1.columns.template.radarColumn.cornerRadius = 20;
		
		var series2 = chart.series.push(new am4charts.RadarColumnSeries());
		series2.dataFields.valueX = "value";
		series2.dataFields.categoryY = "category";
		series2.clustered = false;
		// series2.columns.template.fill = "color";
		// series2.columns.template.propertyFields.fill = "color";
		series2.columns.template.fill = data[0].color;
		series2.columns.template.fillOpacity = 1;
		series2.columns.template.strokeWidth = 0;
		// series2.columns.template.tooltipText = "{category}: [bold]{value}[/]";
		series2.columns.template.radarColumn.cornerRadius = 20;
		
		// series2.columns.template.adapter.add("fill", function(fill, target) {
		//   return chart.colors.getIndex(target.dataItem.index);
		// });
		
		// Add cursor
		// chart.cursor = new am4charts.RadarCursor();
		
		var label = chart.createChild(am4core.Label);
		label.text = Math.ceil(data[0].value) + "%";
		label.fontSize = 22;
		label.align = "center";
		label.fill = am4core.color("#FFFFFF");
		label.isMeasured = false;
		label.x = 75;
		label.y = 70;
		
		var label = chart.createChild(am4core.Label);
		label.text = "0%"
		label.fontSize = 10;
		label.align = "center";
		label.fill = am4core.color("#757575");
		label.isMeasured = false;
		label.x = 20;
		label.y = 105;
		
		var label = chart.createChild(am4core.Label);
		label.text = "100%"
		label.fontSize = 10;
		label.align = "center";
		label.fill = am4core.color("#757575");
		label.isMeasured = false;
		label.x = 153;
		label.y = 105;
	}

	function view_all_logs() {
		// TODO: 테이블로 변경? 카운트 후 로그 목록이 없을 경우 오류, 이동 X
		
		const from = document.getElementById("from");
		const to = document.getElementById("to");
		const temp = calDateRange(from.value, to.value);
		if (temp < 0) {
		  alert("<spring:message code='log.dategreater'/>");
		  return;
		}
		// $('.wrap-loading').removeClass('display-none');
		
		window.location.replace(deline`/protection_grid_search.do?nlpt_create_sdate=\${from.value}&nlpt_create_edate=\${to.value}`);
	}
</script>
</body>
</html>