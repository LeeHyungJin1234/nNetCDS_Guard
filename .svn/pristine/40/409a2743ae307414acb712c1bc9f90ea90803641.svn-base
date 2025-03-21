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
<link rel="stylesheet" href="/css/reset.css" />
<link rel="stylesheet" href="/css/jquery-ui.css">
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/log.css" />
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	function log_search() {   
		var form = document.getElementById("search");
		var temp = calDateRange(form.nlp_create_sdate.value, form.nlp_create_edate.value);
		if(temp<0){
			alert("<spring:message code='log.dategreater'/>");
			return;
		}
		form.action="policy_search.do";
		form.submit();
	}
	
	function download_excel(){
		var form = document.getElementById("search");
		if(form.count.value>20000){
			alert("<spring:message code='log.downloaddesc'/>");
			return;
		}
		form.action = "NcLogExcel.do";
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
		var from_month = from.getMonth()+1;
		if(from_month<10){
			from_month = "0"+from_month;
		}
		var from_date = from.getDate();
		if(from_date<10){
			from_date = "0"+from_date;
		}
		
		document.getElementById("from").value = from.getFullYear()+"-"+from_month+"-"+from_date;
		document.getElementById("to").value = today.getFullYear()+"-"+today_month+"-"+today_date;
		document.getElementById("datetab").value = day;
	}
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
	
	<!-- left menu start -->
	<section id="content">
		<%@ include file="../include/left.jsp"%>
		
		<div id="right-content">
			<div class="right-wrapper">
				<div class="top-section log-section">
					<form id="search" method="post">
						<input type="hidden" name="log_type" value="policy" />
						<input type="hidden" name="count" value="${pginfo.totalArticle}" />
						<input type="hidden" name="datetab" id="datetab" value="${ncPolicyData.datetab}" />
						<ul id="search" class="clearfix">
							<li><span><spring:message code='stat.searchperiod'/></span><input type="text" id="from" name="nlp_create_sdate" class="input date" value="${ncPolicyData.nlp_create_sdate}"><img src="/img/search-bar.png"><input type="text" class="input date" id="to" name="nlp_create_edate" value="${ncPolicyData.nlp_create_edate}"></li>
							<li><span><spring:message code='stat.adminid'/></span><input type="text" name="nsu_id" style="width:140px;" value="${ncPolicyData.nsu_id}"></li>
							<li><span><spring:message code='log.connectip'/></span><input type="text" name="nai_ip" style="width:140px;" value="${ncPolicyData.nai_ip}"></li>
							<li>
								<ul class="tab clearfix">
									<li><div class="tablinks <c:if test="${ncPolicyData.datetab eq '1'}">active</c:if>" onClick="javascript:setDate('1');"><a>1일</a></div></li>
									<li><div class="tablinks <c:if test="${ncPolicyData.datetab eq '7'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('7');"><a>7일</a></div></li>
									<li><div class="tablinks <c:if test="${ncPolicyData.datetab eq '15'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('15');"><a>15일</a></div></li>
								</ul>
							</li>
							<li><a href="javascript:log_search();" id="submitbtn"><img src="/img/search.png"><spring:message code='btn.search'/></a></li>
							<li style="padding:11px 0;float:right;"><a href="javascript:download_excel();" id="excel-btn"><img src="/img/icon_csv_r.png"><spring:message code='log.downloadexcel'/></a></li>
						</ul>
					</form>
				</div>

				<div class="content">
					<table>
					<colgroup>
						<col width="74px" />
						<col width="201px" />
						<col width="261px" />
						<col width="221px" />
						<col width="151px" />
						<col width="692px" />
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
								<th><span><spring:message code='log.turn'/></span></th>
								<th><span><spring:message code='stat.adminid'/></span></th>
								<th><span><spring:message code='log.createdate'/></span></th>
								<th><span><spring:message code='log.connectip'/></span></th>
								<th><span><spring:message code='log.workcontent'/></span></th>
								<th><span><spring:message code='log.detail'/></span></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${pginfo.result != null && !empty pginfo.result}">
							    <c:forEach var="mlog" items="${pginfo.result}" varStatus="status">
									<tr class="text-medium text-main-color">
										<td align="center"><span><c:out value="${pginfo.totalArticle-((pginfo.currentPage-1)*pginfo.perArticle)-status.index}" /></span></td>
										<td align="center"><span>${mlog.nsu_id}</span></td>
										<td align="center"><span>${mlog.nlp_create_date}</span></td>
										<td align="center"><span>${mlog.nai_ip}</span></td>
										<td align="center"><span>${mlog.nlp_page}</span></td>
										<td align="left"><span>${fn:replace(mlog.nlp_param, ',', ',&nbsp;')}</span></td>
									</tr>
								</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="text-medium text-main-color">
										<td align="center" colspan="6"><spring:message code='log.nodata'/></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td><td></td>
							</tr>
						</tfoot>
					</table>
				</div>
				<!--  페이징 시작 -->
				<%@ include file="../include/paging.jsp"%>
				<!--  페이징 끝 -->
			</div>
		</div>
	</section>
	<%@ include file="../include/footer.jsp"%>
</body>
<script>
	if($("#right-content .right-wrapper .content table tbody tr").length>4){
	    $("#right-content").removeClass("footh")
	} else if($("#right-content .right-wrapper .content table tbody tr").length<5){
	    $("#right-content").addClass("footh")
	}
</script>
</html>