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
<script type="text/javascript" src="/js/jquery.min.js"></script>
<style>
	.table>tbody>tr>td,.table>thead>tr>th {line-height:0.92857143}
</style>
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
				<label class="s_title">장치 동작 현황 및 실시간 데이터 전송 현황을 보여줍니다.</label>
			</div>
			
			<div class="contents">
				<div class="row">
					<div class="col-md-4 col-sm-4 col-xs-12">
	              		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back01">
									<div class="m_title">nNetCDS 동작상태</div>
									<div class="s_title">실시간 장치간 동작 상태를 나타냅니다.</div>
								</div>						
							</div>
	                		<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_cont x_content" style="height:100%">
									<div class="txrx_cont">
										<div class="server01"><img src="/images/server.png" width="100%" alt="server"/></div>
										<div class="tx_server" id="active_state1"></div>
										<div class="tx"><img src="/images/tx_rx.jpg" width="100%" alt="tx"/></div>
										<div id="active_state2"></div>
										<div class="rx"><img src="/images/tx_rx.jpg" width="100%" alt="rx"/></div>
										<div class="rx_server">
											<div class="rx_server_line black_line"></div>
											<div class="rx_server_line black_line"></div>
											<div class="rx_server_line black_line"></div>
										</div>
										<div class="server02"><img src="/images/server.png" width="100%" alt="server"/></div>
										<div>
											<div class="txrx_notice"><div class="bg_b"></div>정상동작</div>
											<div class="txrx_notice"><div class="bg_r"></div>연결오류</div>
											<div class="txrx_notice"><div class="bg_gray"></div>사용안함</div>
											<div class="txrx_notice"><div class="bg_black"></div>외부망</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-8 col-sm-8 col-xs-12">
	              		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back01">
									<div class="m_title">데이터 전송 및 실시간 이벤트 로그</div>
									<div class="s_title">실시간 데이터 전송 현황 및 이벤트 로그를 나타냅니다.</div>
								</div>						
							</div>
							<div class="col-md-6 col-sm-6 col-xs-12 padding_r0">
								<div class="box_cont x_content" style="height:270px">
                    				<div id="collect_chart" class="demo-placeholder" style="width:100%;height:250px;"></div>
                    			</div>
                  			</div>
                  			<div class="col-md-6 col-sm-6 col-xs-12 padding_l5">
								<div class="box_cont x_content" style="height:270px">
                    				<div id="send_chart" class="demo-placeholder" style="width:100%;height:250px;"></div>
                    			</div>
                  			</div>
							<script type="text/javascript">
								$(document).ready(function() {
									drawCollect();
									drawSend();
									getReal_event();
									getActive_state();
									
									setInterval("drawCollect();", 1000); // 10초 간격
									setInterval("drawSend();", 1000); // 10초 간격
									setInterval("getReal_event();", 1000); // 10초 간격
									setInterval("getActive_state();", 1000); // 10초 간격
								});
								
								function drawCollect(){
					          		$.ajaxSettings.traditional = true; // 배열 전송
									$.ajax({
										type : "GET",
										url : "receive_ct.do",
										data : "dtArray="+getDtAry(),
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										success: function(data1) {
											$("#collect_chart").length && $.plot($("#collect_chart"), data1, options);  // 내부망 데이터 수집 
										},
										error:function(request,status,error){
											alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
										}
									});
								}
					          	function drawSend(){
							        $.ajaxSettings.traditional = true; // 배열 전송
									$.ajax({
										type : "GET",
										url : "send_ct.do",
										data : "dtArray="+getDtAry(),
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",
										success: function(data1) {
											$("#send_chart").length && $.plot($("#send_chart"), data1, options);  // 내부망 데이터 수집 
										},
										error:function(request,status,error){
											alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
										}
									});
						        }
					          	
								function getReal_event(){
									$.ajax({        
										type : "POST",  
										url : "real_event.do", 
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
										success: function(data) { 
											if(data != null)  {
												document.getElementById('event_table').innerHTML = data;
											}    
										}  
									});
								}
								function getActive_state(){
									$.ajax({        
										type : "POST",  
										url : "active_state.do", 
										contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
										success: function(data) { 
											if(data != null)  {
												var data = data.split("|");
												document.getElementById('active_state1').innerHTML = data[0];
												document.getElementById('active_state2').innerHTML = data[1];
											}    
										}  
									});
								}
							</script>
							<div class="col-md-12 col-sm-12 col-xs-12">
                				<div class="box_cont3 x_content" style="height:100%;">
									<table class="table table-striped jambo_table bulk_action" style="margin:0px;">
									<colgroup>
										<col width="15%" />
										<col width="10%" />
										<col width="7%" />
										<col width="16%" />
										<col width="" />
									</colgroup>
									<thead>
										<tr class="headings">
											<th>로그 생성일</th>
											<th>호스트명</th>
											<th>위험도</th>
											<th>서비스명</th>
											<th>메시지</th>
										</tr>
									</thead>
									</table>
									<div id='event_table' style="overflow-y:scroll; height:390px;"></div>
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
<!-- 차트 관련 -->
<script src="/js/flot/jquery.flot.js"></script>
<script src="/js/flot/jquery.flot.time.js"></script>
<script src="/js/flot/jquery.flot.spline.min.js"></script>
<script src="/js/flot/jquery.flot.tooltip.min.js"></script>
<script src="/js/flot/jquery.flot.resize.js"></script>
<script>
function getDtAry(){
	var d = new Date(); //현재 날짜 및 시간
	var dtArray = new Array();

	for(i=60; i>0; i--){
		dtArray.push((new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes())).getTime())-(1000*60*parseInt(i)));
	}
	return dtArray;
}

var options = {
	series: {
		lines: {
			show: false,
			fill: true
		},
		splines: {
			show: true,
            tension: 0.4,
            lineWidth: 2,
            fill: 0
		},
		points: {
			radius: 0,
			show: true
		},
		shadowSize: 2
	},
	grid: {
		verticalLines: true,
		hoverable: true,
		clickable: true,
		tickColor: "#d5d5d5",
		borderWidth: {
			top: 0,
			right: 0,
			bottom: 1,
			left: 1
		},
		borderColor: {
			bottom: "#7F8790",
			left: "#7F8790"
		},
		color: '#fff'
	},
	colors: ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151"],
	xaxis: {
		tickColor: "rgba(51, 51, 51, 0.06)",
		mode: "time",
		tickSize: [5, "minute"],
		axisLabel: "Date",
		axisLabelUseCanvas: true,
		axisLabelFontSizePixels: 12,
		axisLabelFontFamily: 'Verdana, Arial',
		axisLabelPadding: 10
	},
	yaxis: {
		ticks: 8,
		tickColor: "rgba(51, 51, 51, 0.06)",
		min:0
	},
	tooltip: {
		show: true,
		content: "%x<br>%y"
	}
};
</script>
<!--// 차트 관련 -->
</body>
</html>