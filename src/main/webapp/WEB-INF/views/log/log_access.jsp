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
		var temp = calDateRange(form.nla_create_sdate.value, form.nla_create_edate.value);
		if(temp<0){
			alert("<spring:message code='log.dategreater'/>");
			return;
		}
		$('.wrap-loading').removeClass('display-none');
		form.action="access_search.do";
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
					<input type="hidden" name="log_type" value="access" />
					<input type="hidden" name="count" value="${pginfo.totalArticle}" />
					<input type="hidden" name="datetab" id="datetab" value="${ncMngData.datetab}" />
					<input type="hidden" name="nla_risk_level" id="nla_risk_level" />
						<ul id="search" class="clearfix">
							<li style="padding:14px 0"><span>통신방향</span>
								<select name="nla_div" id="nla_div" onChange="log_search()" <c:if test="${Config.getInstance().getModel() eq 'single'}">disabled=true</c:if>>
									<option value="1" <c:if test="${ncAcsData.nla_div == '1'}" > selected </c:if>><spring:message code='config.inside'/> → <spring:message code='config.outside'/></option>
									<option value="3" <c:if test="${ncAcsData.nla_div == '3'}" > selected </c:if>><spring:message code='config.outside'/> → <spring:message code='config.inside'/></option>
								</select>							
							</li>
							<li style="padding:14px 0"><span><spring:message code='stat.searchperiod'/></span><input type="text" id="from" name="nla_create_sdate" class="input date" value="${ncAcsData.nla_create_sdate}"><img src="/img/search-bar.png"><input type="text" class="input date" id="to" name="nla_create_edate" value="${ncAcsData.nla_create_edate}"></li>
							<li style="padding:14px 0"><span><spring:message code='log.accesstype'/></span>
								<select name="nla_access_type" style="width:80px;">
									<option value=""><spring:message code='stat.total'/></option>
									<option value="0" <c:if test="${ncAcsData.nla_access_type == '0'}"> selected </c:if>>Allow</option>
									<option value="1" <c:if test="${ncAcsData.nla_access_type == '1'}"> selected </c:if>>Deny</option>
								</select>						
							</li>
							<li style="padding:14px 0"><span><spring:message code='log.sndrcvclassfi'/></span>
								<select name="nla_div2" style="width:80px;">
									<option value=""><spring:message code='stat.total'/></option>
									<option value="S" <c:if test="${ncAcsData.nla_div2 == 'S'}" > selected </c:if>><spring:message code='log.send'/></option>
									<option value="R" <c:if test="${ncAcsData.nla_div2 == 'R'}" > selected </c:if>><spring:message code='log.reception'/></option>
								</select>						
							</li>
							<li style="padding:14px 0">
								<ul class="tab clearfix">
									<li><div class="tablinks <c:if test="${ncAcsData.datetab eq '1'}">active</c:if>" onClick="javascript:setDate('1');"><a>1일</a></div></li>
									<li><div class="tablinks <c:if test="${ncAcsData.datetab eq '7'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('7');"><a>7일</a></div></li>
									<li><div class="tablinks <c:if test="${ncAcsData.datetab eq '15'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('15');"><a>15일</a></div></li>
								</ul>
							</li>
							<li style="padding:14px 0;"><a href="javascript:log_search();" id="submitbtn"><img src="/img/search.png"><spring:message code='btn.search'/></a></li>
							<li style="padding:14px 0;float:right;"><a href="javascript:download_excel();" id="excel-btn"><img src="/img/csv.png"><spring:message code='log.downloadexcel'/></a></li>
						</ul>
					</form>
				</div>

				<div class="content">
					<table>
					<colgroup>
						<col width="66px" />
						<col width="270px" />
						<col width="140px" />
						<col width="140px" />
						<col width="140px" />
						<col width="250px" />
						<col width="140px" />
						<col width="250px" />
						<col width="141px" />
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
								<th><span><spring:message code='log.turn'/></span></th>
								<th><span><spring:message code='log.createdate'/></span></th>
								<th>
									<select onChange="document.getElementById('nla_risk_level').value=this.value;log_search();">
										<option value=""><spring:message code='log.risk'/></option>
										<option value="I" <c:if test="${ncAcsData.nla_risk_level eq 'I'}">selected</c:if>><spring:message code='log.info'/></option>
										<option value="W" <c:if test="${ncAcsData.nla_risk_level eq 'W'}">selected</c:if>><spring:message code='log.warning'/></option>
										<option value="R" <c:if test="${ncAcsData.nla_risk_level eq 'R'}">selected</c:if>><spring:message code='log.danger'/></option>
									</select>
								</th>
								<th><span><spring:message code='log.accesstype'/></span></th>
								<th><span><spring:message code='log.sndrcvclassfi'/></span></th>
								<th><span><spring:message code='log.sourceip'/></span></th>
								<th><span><spring:message code='log.sourceport'/></span></th>
								<th><span><spring:message code='log.destnationip'/></span></th>
								<th><span><spring:message code='log.destnationport'/></span></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
							<c:when test="${pginfo.result != null && !empty pginfo.result}">
						    <c:forEach var="mlog" items="${pginfo.result}" varStatus="status">
								<tr class="text-medium text-main-color <c:if test="${mlog.nla_risk_level eq '경고'}">fail</c:if>">
									<td align="center"><c:out value="${pginfo.totalArticle-((pginfo.currentPage-1)*pginfo.perArticle)-status.index}" /></td>											
									<td align="center">${mlog.nla_create_date}</td>
									<td align="center">${mlog.nla_risk_level}</td>								
									<td align="center">${mlog.nla_access_type}</td>
									<td align="center">${mlog.nla_div2}</td>
									<td align="center">${mlog.nla_src_ip}</td>
									<td align="center">${mlog.nla_src_port}</td>
									<td align="center">${mlog.nla_dst_ip}</td>
									<td align="center">${mlog.nla_dst_port}</td>
								</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="text-medium text-main-color">
									<td align="center" colspan="9"><spring:message code='log.nodata'/></td>
								</tr>
							</c:otherwise>
							</c:choose>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
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
	<!-- left menu end -->
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