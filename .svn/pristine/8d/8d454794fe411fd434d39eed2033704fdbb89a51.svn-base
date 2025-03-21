<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" lang="ko">
<head>
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">
<!-- font -->
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
<%@ include file="/WEB-INF/views/include/css/reset_css.jsp"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<%@ include file="/WEB-INF/views/include/css/style_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/policy2_css.jsp"%>
<style>.switch{width:38px;height:21px;}.slider:before{height:17px;width:17px;}</style>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>
<script type="text/javascript">
//  ics 통신 목록 펼치기 DNP3만
$(document).ready(function() {
	$.ajaxSetup({ cache: false });//ajax 캐시 사용안함
	//loadCont('npl_no1', 'DNP 3.0')
	//loadCont('npl_no${plist.npl_no}', '${plist.npl_tx_nts_name}');
	$("#ics_anomaly_list").load("/white_ics.do");
});
function loadCont(id, nts_name){
	var npl = document.getElementById(id);
	
	if($("#ics_anomaly_list").css("display")=="none"){
		npl.className ="active";
		$("#ics_anomaly_list").slideDown();
		$("#ics_anomaly_list").load("/white_ics.do");
	}
	else{
		npl.className ="";
		$("#ics_anomaly_list").slideUp();
	}
}
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	
	<section id="content">
		<!-- left menu start -->
		<%@ include file="../include/left.jsp"%>
		<!-- left menu end -->
		<div id="right-content">
			
			<!-- ics list -->
			<div id="ics_anomaly_list"></div>
			<!-- /ics list -->
			
		</div>
	</section>
		
	<%@ include file="../include/footer.jsp"%>

</body>
</html>