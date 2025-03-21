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
<%@ include file="/WEB-INF/views/include/css/reset_css.jsp"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<%@ include file="/WEB-INF/views/include/css/style_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/log_css.jsp"%>

<link rel="stylesheet" href="/css/DevExtreme/dx.generic.custom-swatch.css" />
<link rel="stylesheet" href="/css/DevExtreme/dx.light_Custom.css" />
<%@ include file="/WEB-INF/views/include/css/log_manager_css.jsp"%>

<script type="text/javascript" src="<c:url value="/js/polyfill.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/DevExtreme/exceljs.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/DevExtreme/FileSaver.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/DevExtreme/dx.all.js"/>"></script>
<script type="text/javascript">
	function log_search() {   
		var form = document.getElementById("search");
		var temp = calDateRange(form.nler_create_sdate.value, form.nler_create_edate.value);
		if(temp<0){
			alert("<spring:message code='log.dategreater'/>");
			return;
		}
		form.action="error_grid_search.do";
		form.submit();
	}
	
	function download_csv(){
		var form = document.getElementById("search");
		if(form.count.value>20000){
			alert("<spring:message code='log.downloaddesc'/>");
			return;
		}
		form.action = "NcLogCsv.do";
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
					<form id="search" method="get">
						<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}"/>
						<input type="hidden" name="log_type" value="error" />
						<input type="hidden" name="count" value="${pginfo.totalArticle}" />
						<input type="hidden" name="datetab" id="datetab" value="${ncEvtData.datetab}" />
						<input type="hidden" name="nler_risk_level" id="nler_risk_level" />
						<ul id="search" class="clearfix">
							<!-- <li style="padding:14px 0"><span>통신방향</span>
								<select name="nler_div" id="nler_div" onChange="log_search()" <c:if test="${Config.getInstance().getModel() eq 'single'}">disabled=true</c:if>>
									<option value="1" <c:if test="${ndEvtData.nler_div == '1'}" > selected </c:if>><spring:message code='config.inside'/> → <spring:message code='config.outside'/></option>
									<option value="3" <c:if test="${ndEvtData.nler_div == '3'}" > selected </c:if>><spring:message code='config.outside'/> → <spring:message code='config.inside'/></option>
								</select>						
							</li> -->
							<li><span><spring:message code='stat.searchperiod'/></span><input type="text" id="from" name="nler_create_sdate" class="input date" value="${ncEvtData.nler_create_sdate}"><img src="/img/search-bar.png"><input type="text" class="input date" id="to" name="nler_create_edate" value="${ncEvtData.nler_create_edate}"></li>
							<%-- <li><span><spring:message code='policy.svcname'/></span><input type="text" name="ncp_name" value="${ncEvtData.ncp_name}" style="width:140px;"></li> --%>
							<li>
								<ul class="tab clearfix">
									<li><div class="tablinks <c:if test="${ncEvtData.datetab eq '1'}">active</c:if>" onClick="javascript:setDate('1');"><a>1일</a></div></li>
									<li><div class="tablinks <c:if test="${ncEvtData.datetab eq '7'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('7');"><a>7일</a></div></li>
									<li><div class="tablinks <c:if test="${ncEvtData.datetab eq '15'}">active</c:if>" style="margin-left:-1px;" onClick="javascript:setDate('15');"><a>15일</a></div></li>
								</ul>
							</li>
							<li><a href="javascript:log_search();" id="submitbtn"><img src="/img/search.png"><spring:message code='btn.search'/></a></li>
							<li class="csv_btn"><div id="csv_btn"></div></li>
						</ul>
					</form>
				</div>

				<div class="content">
					<div class="demo-container">
				     	<div id="gridContainer"></div>
				   	</div>
				</div>

			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
<script>
	var jsonlogdata = ${jsondata};
	
	const dataGrid = $('#gridContainer').dxDataGrid({
	    dataSource: jsonlogdata,
	    keyExpr: 'nler_seq',
	    columnsAutoWidth: true,
	    showBorders: true,
	    filterRow: {
	      visible: false,
	    },
	    headerFilter: {
	      visible: true,
	    },
	    columns: [{
	      dataField: 'nli_index',
	      caption: '순번',
	      dataType: 'String',
	      alignment: 'center', 
	      width: 74,
	      allowHeaderFiltering: false,
	    }, {
	      dataField: 'nler_create_date',
	      caption: '로그 생성일',
	      alignment: 'center',
	      dataType: 'date',
	      format: 'yyyy-MM-dd HH:mm:ss',
	      width: 283,
	      allowHeaderFiltering: false,
	    }, {
	      dataField: 'nsu_id',
	      caption: '주체',
	      alignment: 'center',
	      dataType: 'date',
	      format: 'yyyy-MM-dd HH:mm:ss',
	      width: 147,
	      allowHeaderFiltering: false,
	    }, {
	      dataField: 'nler_result',
	      caption: '작업 결과',
	      alignment: 'center',
	      dataType: 'date',
	      format: 'yyyy-MM-dd HH:mm:ss',
	      width: 127,
	      allowHeaderFiltering: false,
	    }, {
		  dataField: 'nler_risk_level',
		  caption: '위험도',
		  alignment: 'center',
		  dataType: 'String',
		  width: 127,
		  cellTemplate(container, options) {
	          container.parent().addClass((options.data.nla_risk_level == '위험') ? 'inc' : 'dec');
	          container.html(options.text);
	        }
		 }, {
	      dataField: 'nler_prog_name',
	      caption: '작업구분',
	      alignment: 'center',
	      dataType: 'String',
	      width: 342,
	    }, {
		  dataField: 'nler_message',
		  caption: '세부 내용',
		  alignment: 'center',
		  dataType: 'String',
		  width: 500,
		}],
	  }).dxDataGrid('instance');
	
	$('#csv_btn').dxButton({
		icon: '/img/icon_csv_r.png',
		text: 'csv 다운로드',
		onClick() {
			var form = document.getElementById("search");
			form.action = "NcLogCsv.do";
			form.requestSubmit();
		},
	});
</script>
</html>