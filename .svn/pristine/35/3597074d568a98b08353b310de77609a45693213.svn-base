<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNetCDS 관리 시스템</title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/css/nprogress.css">
<link rel="stylesheet" type="text/css" href="/css/skins/green.css">
<link rel="stylesheet" type="text/css" href="/css/custom.min.css">
<link rel="stylesheet" type="text/css" href="/css/contents.css">
<link rel="stylesheet" type="text/css" href="/css/statistics.css">
<script type="text/javascript" src="/js/justgage/raphael-2.1.4.min.js"></script>
<script type="text/javascript" src="/js/justgage/justgage.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var cpu, ram, hdd, master_q1, slave_q1;
	
	// 현재 데이터 수집량
	getCollect_info();
	setInterval("getCollect_info();", 10000); // 10초 간격
	// 현재 데이터 송신량
	getSend_info();
	setInterval("getSend_info();", 10000); // 10초 간격
	// 실시간 이벤트 로그
	getReal_event();
	setInterval("getReal_event();", 10000); // 10초 간격
	// 실시간 접속 통제 로그
	getReal_access();
	setInterval("getReal_access();", 10000); // 10초 간격
	// 실시간 관리자 로그
	getReal_mng();
	setInterval("getReal_mng();", 10000); // 10초 간격
	// master, slave 전송량
	getSystem_traffic();
	setInterval("getSystem_traffic();", 10000); // 10초 간격
	// master, slave 큐 사이즈
	var temp1 = getQueue_size();
	var queue_value = temp1.split("|");
	setInterval(function() {
		var temp1 = getQueue_size();
		var queue_value = temp1.split("|");
		master_q1.refresh(queue_value[0]);
		slave_q1.refresh(queue_value[1]);
	}, 10000);
	master_q1 = new JustGage({
	    id: "master_q1",
	    value: queue_value[0],
	    min: 0,
	    max: 100,
	    symbol: "%",
	    label: "대기 큐",
	    donut: true,
	    pointer: true,
	    pointerOptions: {
        	toplength: 3,
         	bottomlength: -16,
         	bottomwidth: 6,
        	color: '#0000ff'
       	},
       	customSectors: [{
        	color: "#00ff00",
        	lo: 0,
        	hi: 50
       	}, {
        	color: "#ffff00",
        	lo: 50,
        	hi: 75
       	}, {
        	color: "#ff8800",
        	lo: 75,
        	hi: 90
       	}, {
        	color: "#ff0000",
        	lo: 90,
        	hi: 100
       	}],
        gaugeWidthScale: 0.5,
       	counter: true
  	});
	slave_q1 = new JustGage({
	    id: "slave_q1",
	    value: queue_value[1],
	    min: 0,
	    max: 100,
	    symbol: "%",
	    label: "대기 큐",
	    donut: true,
	    pointer: true,
	    pointerOptions: {
        	toplength: 3,
         	bottomlength: -16,
         	bottomwidth: 6,
        	color: '#0000ff'
       	},
       	customSectors: [{
        	color: "#00ff00",
        	lo: 0,
        	hi: 50
       	}, {
        	color: "#ffff00",
        	lo: 50,
        	hi: 75
       	}, {
        	color: "#ff8800",
        	lo: 75,
        	hi: 90
       	}, {
        	color: "#ff0000",
        	lo: 90,
        	hi: 100
       	}],
        gaugeWidthScale: 0.5,
       	counter: true
  	});
	// cpu, ram, hdd 상태 모니터링
	var temp = getSystem_monitor();
	var sys_value = temp.split("|");
	setInterval(function() {
		var temp = getSystem_monitor();
		var sys_value = temp.split("|");
		cpu.refresh(sys_value[0]);
		ram.refresh(sys_value[1]);
		hdd.refresh(sys_value[2]);
	}, 10000);
	cpu = new JustGage({
	    id: "cpu",
	    value: sys_value[0],
	    min: 0,
	    max: 100,
	    title: "CPU 상태",
	    symbol: "%",
	    label: "점유율",
    	pointer: true,
	    pointerOptions: {
        	toplength: 6,
         	bottomlength: 10,
         	bottomwidth: 2,
        	color: '#000'
       	},
       	customSectors: [{
        	color: "#00ff00",
        	lo: 0,
        	hi: 50
       	}, {
        	color: "#ffff00",
        	lo: 50,
        	hi: 75
       	}, {
        	color: "#ff8800",
        	lo: 75,
        	hi: 90
       	}, {
        	color: "#ff0000",
        	lo: 90,
        	hi: 100
       	}],
       	counter: true
	});
    ram = new JustGage({
	    id: "ram",
	    value: sys_value[1],
	    min: 0,
	    max: 100,
	    title: "RAM 상태",
	    symbol: "%",
	    label: "점유율",
    	pointer: true,
	    pointerOptions: {
        	toplength: 6,
         	bottomlength: 10,
         	bottomwidth: 2,
        	color: '#000'
       	},
       	customSectors: [{
        	color: "#00ff00",
        	lo: 0,
        	hi: 50
       	}, {
        	color: "#ffff00",
        	lo: 50,
        	hi: 75
       	}, {
        	color: "#ff8800",
        	lo: 75,
        	hi: 90
       	}, {
        	color: "#ff0000",
        	lo: 90,
        	hi: 100
       	}],
       	counter: true
	});
  	hdd = new JustGage({
	    id: "hdd",
	    value: sys_value[2],
	    min: 0,
	    max: 100,
	    title: "하드디스크 상태",
	    symbol: "%",
	    label: "점유율",
   		pointer: true,
	    pointerOptions: {
        	toplength: 6,
         	bottomlength: 10,
         	bottomwidth: 2,
        	color: '#000'
       	},
       	customSectors: [{
        	color: "#00ff00",
        	lo: 0,
        	hi: 50
       	}, {
        	color: "#ffff00",
        	lo: 50,
        	hi: 75
       	}, {
        	color: "#ff8800",
        	lo: 75,
        	hi: 90
       	}, {
        	color: "#ff0000",
        	lo: 90,
        	hi: 100
       	}],
       	counter: true
  	});
});
function getCollect_info(){
	$.ajax({
		type : "POST",
		url : "collect_info.do",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				document.getElementById('collect_area').innerHTML = data;
			}
		}
	});
}
function getSend_info(){
	$.ajax({
		type : "POST",
		url : "send_info.do",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				document.getElementById('send_area').innerHTML = data;
			}
		}
	});
}
function getReal_event(){
	$.ajax({        
		type : "POST",  
		url : "real_event.do", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				document.getElementById('event_log').innerHTML = data;
			}
		}
	});
}
function getReal_access(){
	$.ajax({        
		type : "POST",  
		url : "real_access.do", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				document.getElementById('access_log').innerHTML = data;
			}
		}
	});
}
function getReal_mng(){
	$.ajax({        
		type : "POST",  
		url : "real_mng.do", 
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				document.getElementById('mng_log').innerHTML = data;
			}
		}
	});
}
function getSystem_traffic(){
	$.ajax({
		type : "POST",
		url : "system_traffic.do",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				var response = data.split("|");
				$("#m_bar").children('div').children('span').css('width',response[0]+"%");
				$("#s_bar").children('div').children('span').css('width',response[1]+"%");
				$("#m_bar").children('div').children('#m_bar_val').html(response[2]);
				$("#s_bar").children('div').children('#s_bar_val').html(response[3]);
			}
		},
		error:function(request,status,error){
	    	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
function getQueue_size(){
	var result = "";
	$.ajax({
		type : "POST",
		url : "queue_size.do",
		async: false,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				result = data;
			}
		}
	});
	return result;
}
function getSystem_monitor(){
	var result = "";
	$.ajax({
		type : "POST",
		url : "system_monitor.do",
		async: false,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			if(data != null) {
				result = data;
			}
		}
	});
	return result;
}
</script>
</head>
<body class="nav-md">
<div class="container body">
	<div class="main_container">
		<!-- top content -->
		<%@ include file="../include/top.jsp"%>
		<!-- /top content -->

        <!-- page content -->        
		<div class="right_col" role="main">
			<div class="pg_title">
				<label class="m_title"><i class='fa fa-bar-chart-o'></i> ${title}</label>
				<label class="s_title">장치 현황 및 실시간 로그 정보를 보여줍니다.</label>
			</div>
			<div class="contents">
				<div class="col-md-2 col-sm-12 col-xs-12">
					<div class="row">
						<div class="box_title x_content">
							<h2 class="m_title"><i class='glyphicon glyphicon-log-in'></i>&nbsp;&nbsp;내부망 데이터 수집</h2>
						</div>
						<div class="side_box_cont x_content">
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12" style="text-align:right;font-size:13px;">(전송단위 : byte)</div>
							</div>
							<table style='width:100%;height:26px;table-layout:fixed;'>
								<colgroup>
									<col width='3%' /><col width='' /><col width='35%' />
								</colgroup>
								<thead>
									<tr>
										<td></td>
										<td>서비스</td>
										<td>현재 수집량</td>
									</tr>
								</thead>
							</table>
							<div id="collect_area" style="height:318px"></div>
						</div>
					</div>
				</div>
				<div class="col-md-8 col-sm-12 col-xs-12" style="padding-right:7px;">
					<div class="box_title x_content">
						<h2 class="m_title"><i class='glyphicon glyphicon-check'></i>&nbsp;&nbsp;전송 상태</h2>
					</div>
					<div class="box_cont3 x_content">
						<div class="row">
							<div class="col-md-2 col-sm-12 col-xs-12" style="padding:0px;">
								<div id="cpu"></div>
							</div>
							<div class="col-md-2 col-sm-12 col-xs-12" style="padding:0px;">
								<div id="ram"></div>
							</div>
							<div class="col-md-2 col-sm-12 col-xs-12" style="padding:0px;">
								<div id="hdd"></div>
								<!--  <div id="g_hdd" style="width:152px; height:152px;"></div>-->
							</div>
						</div>
						<div class="row">
							<div class="col-md-1 col-sm-12 col-xs-12"></div>
							<div class="bar_title col-md-2 col-sm-12 col-xs-12" style="margin-top:24px;">MASTER</div>
							<div class="col-md-6 col-sm-12 col-xs-12" style="margin-top:24px;">
								<div id="m_bar">
									<div class="progress-bar blue shine"> <span style="width: 40%"></span><div id="m_bar_val"></div></div>
								</div>
							</div>
							<div class="col-md-1 col-sm-12 col-xs-12">
								<div id="master_q1"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-sm-12 col-xs-12">
								<div class="tx"><img src="/images/tx_rx.jpg" width="100%" alt="tx"/></div>
							</div>
							<div class="col-md-6 col-sm-12 col-xs-12">
								<div class="rx"><img src="/images/tx_rx.jpg" width="100%" alt="rx"/></div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-1 col-sm-12 col-xs-12"></div>
							<div class="bar_title col-md-2 col-sm-12 col-xs-12" style="margin-top:34px;">SLAVE</div>
							<div class="col-md-6 col-sm-12 col-xs-12" style="margin-top:34px;">
								<div id="s_bar">
									<div class="progress-bar orange shine"> <span style="width: 40%"></span><div id="s_bar_val"></div></div>
								</div>
							</div>
							<div class="col-md-1 col-sm-12 col-xs-12">
								<div id="slave_q1"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-12 col-xs-12">
					<div class="row">
						<div class="box_title x_content">
							<h2 class="m_title"><i class='glyphicon glyphicon-log-out'></i>&nbsp;&nbsp;일방향 데이터 송신</h2>
						</div>
						<div class="side_box_cont x_content">
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12" style="text-align:right;font-size:13px;">(전송단위 : byte)</div>
							</div>
							<table style='width:100%;height:26px;table-layout:fixed;'>
								<colgroup>
									<col width='3%' /><col width='' /><col width='35%' />
								</colgroup>
								<thead>
									<tr>
										<td></td>
										<td>서비스</td>
										<td>현재 송신량</td>
									</tr>
								</thead>
							</table>
							<div id="send_area" style="height:318px"></div>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 col-xs-12 form-horizontal" style="padding-left:0px;">
					<div class="box_cont4 x_content">
						<div class="form-group no_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">장비이름(모델) :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>nNetCDS V2.0</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">시리얼 :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>WZN801A0M1AHTD15000003A0</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">CID :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>00000000001e1d23-0600-0200-0b414289</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">HA상태 :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>123456ABCDEFGH</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">SMC 연동 :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>Stand-alone</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">NS-Zone :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12"><label>Running</label></div>
						</div>
						<div class="form-group upper_line">
							<label class="control-label col-md-4 col-sm-12 col-xs-12">라이선스 :</label>
							<div class="checkbox col-md-8 col-sm-12 col-xs-12">
								<label><i class='fa fa-tree'></i></label>
								<label><i class='fa fa-trophy'></i></label>
								<label><i class='fa fa-truck'></i></label>
								<label><i class='fa fa-tty'></i></label>
								<label><i class='fa fa-umbrella'></i></label>
								<label><i class='fa fa-unlock'></i></label>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 col-sm-12 col-xs-12" style="padding:0px;">
					<div class="box_cont4 x_content">
						<div class="" role="tabpanel" data-example-id="togglable-tabs">
							<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
								<li role="presentation" class="active"><a href="#tab_content1" id="event-tab" role="tab" data-toggle="tab" aria-expanded="true">이벤트 로그</a></li>
								<li role="presentation"><a href="#tab_content2" role="tab" id="access-tab" data-toggle="tab" aria-expanded="false" onclick="javascript:getReal_access();">접속 통제 로그</a></li>
								<li role="presentation"><a href="#tab_content3" role="tab" id="manager-tab2" data-toggle="tab" aria-expanded="false" onclick="javascript:getReal_mng();">관리자 로그</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="event-tab">
									 <div class="log_box">
									 	<div style="overflow:auto;">
											<table class="log_head">
												<colgroup>
													<col width="20%" />
													<col width="10%" />
													<col width="20%" />
													<col width="50%" />
												</colgroup>
												<tr>
													<td>로그 생성일</td>
													<td>위험도</td>
													<td>서비스명</td>
													<td class="none">메시지</td>
												</tr>
											</table>
										</div>
										<div id='event_log' style="overflow-y:scroll;height:200px"></div>
									</div> 
								</div>
								<div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="access-tab">
									<div class="log_box">
										<table class="log_head">
											<colgroup>
												<col width="16%" />
												<col width="10%" />
												<col width="11%" />
												<col width="11%" />
												<col width="15%" />
												<col width="11%" />
												<col width="15%" />
												<col width="11%" />
											</colgroup>
											<tr>
												<td>로그 생성일</td>
												<td>위험도</td>
												<td>액세스 타입</td>
												<td>송/수신 구분</td>
												<td>출발지 IP</td>
												<td>출발지 PORT</td>
												<td>도착지 IP</td>
												<td class="none">도착지 PORT</td>
											</tr>
										</table>
										<div id='access_log' style="overflow-y:scroll;height:200px"></div>
									</div>
								</div>
								<div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="manager-tab2">
									<div class="log_box">
										<table class="log_head">
											<colgroup>
												<col width="12%" />
												<col width="15%" />
												<col width="12%" />
												<col width="16%" />
												<col width="9%" />
												<col width="9%" />
												<col width="" />
											</colgroup>
											<tr>
												<td>관리자 ID</td>
												<td>로그 생성일</td>
												<td>접속 IP</td>
												<td>작업 내용</td>
												<td>작업 결과</td>
												<td>위험도</td>
												<td class="none">세부내용</td>
											</tr>
										</table>
										<div id='mng_log' style="overflow-y:scroll;height:200px"></div>
									</div>
								</div>
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

<!-- google charts start -->
<script type="text/javascript" src="/js/google_charts/loader.js"></script>
<script type="text/javascript">/*
	google.charts.load('current', {'packages':['gauge']});
	google.charts.setOnLoadCallback(drawChart);
   
	function drawChart() {
		   
		
		var data = google.visualization.arrayToDataTable([
		   ['Label', 'Value'],
		   ['HDD', 0]
		 ]);
		
		var options = {
		   redFrom: 90, redTo: 100,
		   yellowFrom:80, yellowTo: 90,
		   greenFrom:60, greenTo: 80,
		   greenColor: "#ffff00",
		   majorTicks:["0",20,40,60,80,100],
		   minorTicks: 4
		};
		
		var chart = new google.visualization.Gauge(document.getElementById('g_hdd'));

		var temp = getSystem_monitor();
		var sys_value = temp.split("|");
		
		data.setValue(0, 1, sys_value[2]);
		chart.draw(data, options);
		
		setInterval(function() {
			 var temp = getSystem_monitor();
		 	 var sys_value = temp.split("|");
		     data.setValue(0, 1, sys_value[2]);
		     chart.draw(data, options);
		}, 10000);
	}*/
</script>
<!-- google charts end -->

<!-- 전체화면 시 일부 사이즈 변경 start -->
<script type="text/javascript">
var box_height_f = 350;
var box_height = 225;
var line_padding_f = 14;
var line_padding = 10;
document.addEventListener("fullscreenchange", function () {
    if(document.fullscreen){
    	$(".log_box").css('height', box_height_f+'px');
    	$(".log_box .log_body").css('height', (box_height_f-25)+'px');

    	$(".upper_line").css('padding', line_padding_f+'px 0px '+line_padding_f+'px 0px');
    	$(".no_line").css('padding', (line_padding_f+2)+'px 0px '+line_padding_f+'px 0px');
    }else{
    	$(".log_box").css('height', box_height+'px');
    	$(".log_box .log_body").css('height', (box_height-25)+'px');

    	$(".upper_line").css('padding', '0px 0px '+line_padding+'px 0px');
    	$(".no_line").css('padding', '1px 0px '+line_padding+'px 0px');
    }
}, false);
document.addEventListener("mozfullscreenchange", function () {
	if(document.mozFullScreen){
    	$(".log_box").css('height', box_height_f+'px');
    	$(".log_box .log_body").css('height', (box_height_f-25)+'px');

    	$(".upper_line").css('padding', line_padding_f+'px 0px '+line_padding_f+'px 0px');
    	$(".no_line").css('padding', (line_padding_f+2)+'px 0px '+line_padding_f+'px 0px');
    }else{
    	$(".log_box").css('height', box_height+'px');
    	$(".log_box .log_body").css('height', (box_height-25)+'px');

    	$(".upper_line").css('padding', '0px 0px '+line_padding+'px 0px');
    	$(".no_line").css('padding', '1px 0px '+line_padding+'px 0px');
    }
}, false);
document.addEventListener("webkitfullscreenchange", function () {
	if(document.webkitIsFullScreen){
    	$(".log_box").css('height', box_height_f+'px');
    	$(".log_box .log_body").css('height', (box_height_f-25)+'px');

    	$(".upper_line").css('padding', line_padding_f+'px 0px '+line_padding_f+'px 0px');
    	$(".no_line").css('padding', (line_padding_f+2)+'px 0px '+line_padding_f+'px 0px');
    }else{
    	$(".log_box").css('height', box_height+'px');
    	$(".log_box .log_body").css('height', (box_height-25)+'px');

    	$(".upper_line").css('padding', '0px 0px '+line_padding+'px 0px');
    	$(".no_line").css('padding', '1px 0px '+line_padding+'px 0px');
    }
}, false);
document.addEventListener("MSFullscreenChange", function () {
	if(document.msFullscreenElement){
    	$(".log_box").css('height', box_height_f+'px');
    	$(".log_box .log_body").css('height', (box_height_f-25)+'px');

    	$(".upper_line").css('padding', line_padding_f+'px 0px '+line_padding_f+'px 0px');
    	$(".no_line").css('padding', (line_padding_f+2)+'px 0px '+line_padding_f+'px 0px');
    }else{
    	$(".log_box").css('height', box_height+'px');
    	$(".log_box .log_body").css('height', (box_height-25)+'px');

    	$(".upper_line").css('padding', '0px 0px '+line_padding+'px 0px');
    	$(".no_line").css('padding', '1px 0px '+line_padding+'px 0px');
    }
}, false);
</script>
<!-- 전체화면 시 일부 사이즈 변경 end -->
</html>