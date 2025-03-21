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
<%@ include file="/WEB-INF/views/include/css/policy_css.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
</head>
<body>
<div id="descpopup" class="descpopup">
	<div class="bg"></div>
	<c:choose>
	<c:when test="${'1' eq type}">
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font">Ethernet Type</h1>
				<p><a href="javascript:self.close();"><img src="/img/close.png"></a></p>
			</div>
			<table>
				<thead>
				<tr class="text-small text-main-color2">
					<th style="vertical-align:middle;width:222px;border-right:1px solid #e0e3e7;">Type</th>
					<th style="vertical-align:middle;">설명</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="ether" items="${etherType}" varStatus="status">
				<tr>
					<td style="padding-left:12px;width:210px;border-right:1px solid #e0e3e7;">${ether.npe_name}</td>
					<td style="padding-left:12px;word-break:break-all">${ether.npe_description}</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1 class="main-font">프로토콜 타입</h1>
				<p><a href="javascript:self.close();"><img src="/img/close.png"></a></p>
			</div>
			<table>
				<thead>
				<tr class="text-small text-main-color2">
					<th style="vertical-align:middle;width:222px;border-right:1px solid #e0e3e7;">Type</th>
					<th style="vertical-align:middle;">설명</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="ipproto" items="${ipProtocol}" varStatus="status">
				<tr>
					<td style="padding-left:12px;width:210px;border-right:1px solid #e0e3e7;">${ipproto.npp_name}</td>
					<td style="padding-left:12px;word-break:break-all">${ipproto.npp_description}</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</c:otherwise>
	</c:choose>
</div>
</body>
</html>