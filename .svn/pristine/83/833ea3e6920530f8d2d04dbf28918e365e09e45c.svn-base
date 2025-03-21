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
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/dygraph.js"></script>
<script type="text/javascript">
function search() {
	var form = document.getElementById("search");
	form.action="stat_traffic.do";
	form.submit();
}
function end_term_set(){
	/*var sdate = document.getElementById("stat_sdate").value;
	var rev_sdate = sdate.replace(/-/g, "/");
	var shour = document.getElementById("stat_shour").value;
	var sminute = document.getElementById("stat_sminute").value;
	
	var loadDt = new Date(rev_sdate+" "+shour+":"+sminute);
	var after = new Date(Date.parse(loadDt) + 1000 * 60 * 59); //59분 후
	
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
	document.getElementById("stat_eminute").value = after.getMinutes();*/
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
	/*var edate = document.getElementById("stat_edate").value;
	var rev_edate = edate.replace(/-/g, "/");
	var ehour = document.getElementById("stat_ehour").value;
	var eminute = document.getElementById("stat_eminute").value;
	
	var loadDt = new Date(rev_edate+" "+ehour+":"+eminute);
	var before = new Date(Date.parse(loadDt) - 1000 * 60 * 59); //59분 전
	
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
	document.getElementById("stat_sminute").value = before.getMinutes();*/
	
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
				<label class="m_title"><i class='fa fa-bar-chart-o'></i>${title}</label>
				<label class="s_title"><spring:message code='stat.trafficdesc'/></label>
			</div>
			<div class="clearfix"></div>

			<script>
				$(document).ready(function() {
					if("${ncStatNw.stat_sdate}"==""){
						date_set();
						setInterval("date_set();", 60000); // 1분 간격
					}else{
						// 내부망 데이터 수집
						getCollect_tb();
						drawCollect();
						// 데이터 전송 손실
						getLoss_tb();
						drawLoss();
						// 일방향 데이터 송신
						getSend_tb();
						drawSend();
						// 데이터 재전송
						getRepeat_tb();
						drawRepeat();
					}
				});
				
				function date_set(){
					var loadDt = new Date();
					var start = new Date(Date.parse(loadDt) - 1000 * 60 * 481); //481분(8시간+1분) 전
					var end = new Date(Date.parse(loadDt) - 1000 * 60); //1분 전;
					
					var start_month = start.getMonth()+1;
					if(start_month<10){
						start_month = "0"+start_month;
					}
					var end_month = end.getMonth()+1;
					if(end_month<10){
						end_month = "0"+end_month;
					}
					
					document.getElementById("stat_sdate").value = start.getFullYear()+"-"+start_month+"-"+start.getDate();
					document.getElementById("stat_shour").value = start.getHours();
					document.getElementById("stat_sminute").value = start.getMinutes();
					
					document.getElementById("stat_edate").value = end.getFullYear()+"-"+end_month+"-"+end.getDate();
					document.getElementById("stat_ehour").value = end.getHours();
					document.getElementById("stat_eminute").value = end.getMinutes();
					
					// 내부망 데이터 수집
					getCollect_tb();
					drawCollect();
					// 데이터 전송 손실
					getLoss_tb();
					drawLoss();
					// 일방향 데이터 송신
					getSend_tb();
					drawSend();
					// 데이터 재전송
					getRepeat_tb();
					drawRepeat();
				}
			</script>
			<div class="contents">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<form id="search" method="post" class="form-horizontal form-label-left">
								<div class="form-group">
									<label class="control-label col-md-2 col-sm-12 col-xs-12 form-group"><spring:message code='stat.searchperiod'/></label>
									<div class="col-md-8 col-sm-12 col-xs-12 form-inline form-group">
										<input type="text" id="stat_sdate" name="stat_sdate" class="form-control" value="${ncStatNw.stat_sdate}" />&nbsp;&nbsp; 
										<select name="stat_shour" id="stat_shour" class="form-control" onChange="end_term_set();">
										<c:forEach begin="0" end="23" var="i">
											<option value="${i}" <c:if test="${ncStatNw.stat_shour == i}">selected</c:if>>${i}</option>
										</c:forEach>
										</select> <spring:message code='stat.hour'/>&nbsp;&nbsp;
										<select name="stat_sminute" id="stat_sminute" class="form-control" onChange="end_term_set();">
										<c:forEach begin="0" end="59" var="i">
											<option value="${i}" <c:if test="${ncStatNw.stat_sminute == i}">selected</c:if>>${i}</option>
										</c:forEach>
										</select> <spring:message code='stat.minute'/>&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;
										<input type="text" id="stat_edate" name="stat_edate" class="form-control" value="${ncStatNw.stat_edate}" />&nbsp;&nbsp;
										<select name="stat_ehour" id="stat_ehour" class="form-control" onChange="start_term_set();">
										<c:forEach begin="0" end="23" var="i">
											<option value="${i}" <c:if test="${ncStatNw.stat_ehour == i}">selected</c:if>>${i}</option>
										</c:forEach>
										</select> <spring:message code='stat.hour'/>&nbsp;&nbsp;
										<select name="stat_eminute" id="stat_eminute" class="form-control" onChange="start_term_set();">
										<c:forEach begin="0" end="59" var="i">
											<option value="${i}" <c:if test="${ncStatNw.stat_eminute == i}">selected</c:if>>${i}</option>
										</c:forEach>
										</select> <spring:message code='stat.minute'/>
									</div>
									<button type="button" class="btn btn-primary" onClick="javascript:search();"><spring:message code='btn.search'/></button>
									<button type="button" class="btn btn-success" onClick="location.href='/stat_traffic.do'"><spring:message code='btn.reset'/></button>
									<div class="col-md-2 col-sm-12 col-xs-12"></div>
									<label class="control-label col-md-8 col-sm-12 col-xs-12 form-group" style="color:red;font-weight:bold;text-align:left">* <spring:message code='stat.searchdesc'/></label>
								</div>
							</form>
						</div>
					</div>
					
					<div class="col-md-6 col-sm-6 col-xs-12">
	              		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back01">
									<div class="m_title"><spring:message code='stat.intcollection'/></div>
									<div class="s_title"><spring:message code='stat.collectdesc'/></div>
								</div>						
							</div>
	                		<div class="col-md-4 col-sm-4 col-xs-12 padding_r0">
								<div class="box_cont x_content">
									<div style="text-align:right;font-size:12px;">(<spring:message code='stat.tranunit'/> : byte)</div>
									<table style='width:100%;height:25px;table-layout:fixed;'>
										<colgroup>
											<col width='8%' /><col width='' /><col width='35%' />
										</colgroup>
										<thead>
											<tr>
												<td></td>
												<td><spring:message code='stat.service'/></td>
												<td><spring:message code='stat.collection'/></td>
											</tr>
										</thead>
									</table>
									<div id='collect_table'></div>				                  
								</div>
	                		</div>
	                		<div class="col-md-8 col-sm-8 col-xs-12 padding_l5">
	                  			<div class="box_cont x_content">
	                    			<div id="collect_chart" class="demo-placeholder" style="width:100%;height:270px;padding-top:7px;"></div>
	                  			</div>
	                		</div>
	                		<div class="clearfix"></div>
	              		</div>
	            	</div>
		          	<script type="text/javascript">
			          	function drawCollect(){
			          		/*$.ajaxSettings.traditional = true; // 배열 전송
							$.ajax({
								type : "GET",
								url : "receive_ct.do",
								data : "dtArray="+getDtAry()+"&sdate=${ndStatNw.stat_sdate}&edate=${ndStatNw.stat_edate}&shour=${ndStatNw.stat_shour}&ehour=${ndStatNw.stat_ehour}&sminute=${ndStatNw.stat_sminute}&eminute=${ndStatNw.stat_eminute}",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data1) {
									$("#collect_chart").length && $.plot($("#collect_chart"), data1, options);  // 내부망 데이터 수집 
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});*/
							$.ajax({
								type : "GET",
								url : "receive_ct.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded;charset=UTF-8",
								success: function(data1) {
									g = new Dygraph(
							        	document.getElementById("collect_chart"),
							           	data1,
										{
											maxNumberWidth : 20,
											colors: ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7"],
											axes: {
								            	y: {
													pixelsPerLabel : 20
								            	}
											},
											strokeWidth: 2,
											includeZero : true,
											//showLabelsOnHighlight : false,
											//highlightSeriesOpts: { highlightCircleSize: 0 },
											showRangeSelector: true
										}
									);
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});
						}
						function getCollect_tb(){
							$.ajax({
								type : "POST",
								url : "receive_tb.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data) {
									if(data != null)  {
										document.getElementById('collect_table').innerHTML = data;
									}
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});
						}
					</script>
	
					<div class="col-md-6 col-sm-6 col-xs-12">
	              		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back02">
									<div class="m_title"><spring:message code='stat.transmission'/></div>
									<div class="s_title"><spring:message code='stat.transdesc'/></div>
								</div>						
							</div>
	                		<div class="col-md-8 col-sm-8 col-xs-12 padding_r0">
	                  			<div class="box_cont x_content">
	                    			<div id="send_chart" class="demo-placeholder" style="width:100%;height:270px;padding-top:7px;"></div>
	                  			</div>
	                		</div>
	                		<div class="col-md-4 col-sm-4 col-xs-12 padding_l5">
								<div class="box_cont x_content">
									<div style="text-align:right;font-size:12px;">(<spring:message code='stat.tranunit'/> : byte)</div>
									<table style='width:100%;height:25px;table-layout:fixed;'>
										<colgroup>
											<col width='8%' /><col width='' /><col width='35%' />
										</colgroup>
										<thead>
											<tr>
												<td></td>
												<td><spring:message code='stat.service'/></td>
												<td><spring:message code='stat.tranamount'/></td>
											</tr>
										</thead>
									</table>
									<div id='send_table'></div>				                  
								</div>
	                		</div>
	                		<div class="clearfix"></div>
	              		</div>
	            	</div>
		          	<script type="text/javascript">
						function drawSend(){
					        /*$.ajaxSettings.traditional = true; // 배열 전송
							$.ajax({
								type : "GET",
								url : "send_ct.do",
								data : "dtArray="+getDtAry()+"&sdate=${ndStatNw.stat_sdate}&edate=${ndStatNw.stat_edate}&shour=${ndStatNw.stat_shour}&ehour=${ndStatNw.stat_ehour}&sminute=${ndStatNw.stat_sminute}&eminute=${ndStatNw.stat_eminute}",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data1) {
									$("#send_chart").length && $.plot($("#send_chart"), data1, options);  // 내부망 데이터 수집 
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});*/
							$.ajax({
								type : "GET",
								url : "send_ct.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded;charset=UTF-8",
								success: function(data1) {
									g = new Dygraph(
							        	document.getElementById("send_chart"),
							           	data1,
										{
											maxNumberWidth : 20,
											colors: ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7"],
											axes: {
								            	y: {
													pixelsPerLabel : 20
								            	}
											},
											strokeWidth: 2,
											includeZero : true,
											//showLabelsOnHighlight : false,
											//highlightSeriesOpts: { highlightCircleSize: 0 },
											showRangeSelector: true
										}
									);
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});
				        }
		
						function getSend_tb(){
							$.ajax({
								type : "POST",
								url : "send_tb.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data) {
									if(data != null)  {
										document.getElementById('send_table').innerHTML = data;
									}
								}
							});
						}
					</script>
				</div>
	          	<br />

				<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-12">
	              		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back03">
									<div class="m_title"><spring:message code='stat.tranloss'/></div>
									<div class="s_title"><spring:message code='stat.tranlossdesc'/></div>
								</div>						
							</div>
							<div class="col-md-4 col-sm-4 col-xs-12 padding_r0">
								<div class="box_cont x_content">
									<div style="text-align:right;font-size:12px;">(<spring:message code='stat.tranunit'/> : <spring:message code='stat.quantity'/>)</div>
									<table style='width:100%;height:25px;table-layout:fixed;'>
										<colgroup>
											<col width='8%' /><col width='' /><col width='35%' />
										</colgroup>
										<thead>
											<tr>
												<td></td>
												<td><spring:message code='stat.service'/></td>
												<td><spring:message code='stat.lossamount'/></td>
											</tr>
										</thead>
									</table>
									<div id='loss_table'></div>				                  
								</div>
	                		</div>
	                		<div class="col-md-8 col-sm-8 col-xs-12 padding_l5">
	                  			<div class="box_cont x_content">
	                    			<div id="loss_chart" class="demo-placeholder" style="width:100%;height:270px;padding-top:7px;"></div>
	                  			</div>
	                		</div>
	                		<div class="clearfix"></div>
	              		</div>
	            	</div>
		          	<script type="text/javascript">
				       	function drawLoss() {
							/*$.ajaxSettings.traditional = true; // 배열 전송
							$.ajax({
								type : "GET",
								url : "repe_loss_ct.do",
								data : "dtArray="+getDtAry()+"&sdate=${ndStatNw.stat_sdate}&edate=${ndStatNw.stat_edate}&shour=${ndStatNw.stat_shour}&ehour=${ndStatNw.stat_ehour}&sminute=${ndStatNw.stat_sminute}&eminute=${ndStatNw.stat_eminute}",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data1) {
									$("#loss_chart").length && $.plot($("#loss_chart"), data1, options);  // 내부망 데이터 수집 
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});*/
				       		$.ajax({
								type : "GET",
								url : "repe_loss_ct.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded;charset=UTF-8",
								success: function(data1) {
									g = new Dygraph(
							        	document.getElementById("loss_chart"),
							           	data1,
										{
											maxNumberWidth : 20,
											colors: ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7"],
											axes: {
								            	y: {
													pixelsPerLabel : 20
								            	}
											},
											strokeWidth: 2,
											includeZero : true,
											//showLabelsOnHighlight : false,
											//highlightSeriesOpts: { highlightCircleSize: 0 },
											showRangeSelector: true
										}
									);
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});
						}
				        
				        function getLoss_tb(){
							$.ajax({        
								type : "POST",  
								url : "repe_loss_tb.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
								success: function(data) { 
									if(data != null)  {
										document.getElementById('loss_table').innerHTML = data;
									}    
								}  
							});
						 }
				    </script>
				    
					<div class="col-md-6 col-sm-6 col-xs-12">
	              		<div class="row">
	              			<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box_title x_content box_back04">
									<div class="m_title"><spring:message code='stat.retransmit'/></div>
									<div class="s_title"><spring:message code='stat.retransdesc'/></div>
								</div>						
							</div>
							<div class="col-md-8 col-sm-8 col-xs-12 padding_r0">
	                  			<div class="box_cont x_content">
	                    			<div id="repeat_chart" class="demo-placeholder" style="width:100%;height:270px;padding-top:7px;"></div>
	                  			</div>
	                		</div>
							<div class="col-md-4 col-sm-4 col-xs-12 padding_l5">
								<div class="box_cont x_content">
									<div style="text-align:right;font-size:12px;">(<spring:message code='stat.tranunit'/> : byte)</div>
									<table style='width:100%;height:25px;table-layout:fixed;'>
										<colgroup>
											<col width='8%' /><col width='' /><col width='35%' />
										</colgroup>
										<thead>
											<tr>
												<td></td>
												<td><spring:message code='stat.service'/></td>
												<td><spring:message code='stat.retransmission'/></td>
											</tr>
										</thead>
									</table>
									<div id='repeat_table'></div>				                  
								</div>
	                		</div>
	                		<div class="clearfix"></div>
	              		</div>
	            	</div>
		          	<script type="text/javascript">
				       	function drawRepeat() {
							/*$.ajaxSettings.traditional = true; // 배열 전송
							$.ajax({
								type : "GET",
								url : "repe_byte_ct.do",
								data : "dtArray="+getDtAry()+"&sdate=${ndStatNw.stat_sdate}&edate=${ndStatNw.stat_edate}&shour=${ndStatNw.stat_shour}&ehour=${ndStatNw.stat_ehour}&sminute=${ndStatNw.stat_sminute}&eminute=${ndStatNw.stat_eminute}",
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",
								success: function(data1) {
									$("#repeat_chart").length && $.plot($("#repeat_chart"), data1, options);  // 내부망 데이터 수집 
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});*/
				       		$.ajax({
								type : "GET",
								url : "repe_byte_ct.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded;charset=UTF-8",
								success: function(data1) {
									g = new Dygraph(
							        	document.getElementById("repeat_chart"),
							           	data1,
										{
											maxNumberWidth : 20,
											colors: ["#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7"],
											axes: {
								            	y: {
													pixelsPerLabel : 20
								            	}
											},
											strokeWidth: 2,
											includeZero : true,
											//showLabelsOnHighlight : false,
											//highlightSeriesOpts: { highlightCircleSize: 0 },
											showRangeSelector: true
										}
									);
								},
								error:function(request,status,error){
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							});
						}
				        
				        function getRepeat_tb(){
							$.ajax({        
								type : "POST",  
								url : "repe_byte_tb.do",
								data : "sdate="+document.getElementById("stat_sdate").value+"&edate="+document.getElementById("stat_edate").value+"&shour="+document.getElementById("stat_shour").value+"&ehour="+document.getElementById("stat_ehour").value+"&sminute="+document.getElementById("stat_sminute").value+"&eminute="+document.getElementById("stat_eminute").value,
								contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
								success: function(data) { 
									if(data != null)  {
										document.getElementById('repeat_table').innerHTML = data;
									}    
								}
							});
						 }
				    </script>
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
function gd(year, month, day, hour, minute) {
	return new Date(Date.UTC(year, month - 1, day, hour, minute)).getTime();
}
  
function getDtAry(){
	var d = new Date(); //현재 날짜 및 시간
	var dtArray = new Array();

	////alert(11);
	//alert(new Date(Date.UTC(2016, 7, 18, 16, 20)).getTime());
	//alert(new Date(Date.UTC(2016, 8-1, 18, 16, 20)).getTime());
	//alert(new Date(Date.UTC(2016, 7, 18, 4, 50)).getTime());
	//alert(2);
	for(i=60; i>0; i--){
		//alert(new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes())).getTime());
		//alert((new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes())).getTime())-(1000*60*parseInt(i)));
		dtArray.push((new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes())).getTime())-(1000*60*parseInt(i)));
	}
	return dtArray;
	//return new Date(Date.UTC(year, month - 1, day, hour, minute)).getTime();
}

var data = [{"data":[[gd(2016, 7, 18, 16, 20), "11"],[gd(2016, 7, 18, 16, 22), "1"]]}];

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
<!-- 달력 스크립트 -->
<script src="/js/moment/moment.min.js"></script>
<script src="/js/datepicker/daterangepicker.js"></script>
<script>
$(document).ready(function() {
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
});
</script>
</body>
</html>