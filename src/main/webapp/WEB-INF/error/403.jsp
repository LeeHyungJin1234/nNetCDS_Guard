<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link rel="stylesheet" href="/css/jquery-ui.css" />
<style>
	body,html{width:100%;height:100%;}
	#content {position:relative;width:100%;height:100%;}
	#content .error-wrapper {position:absolute;left:50%;top:45%;transform:translateX(-50%) translateY(-50%);width:690px;height:400px;text-align:center;}
	#content .error-wrapper h2{font-family: "nunito sans"; font-size:140px; color:#010c1e;margin-bottom:76px;}
	#content .error-wrapper p {font-family: 'NanumSquare';font-size:22px; color:#040911;padding-bottom:60px;border-bottom:1px solid #e0e3e7;}
	#content .error-wrapper .btn-section {margin-top:54px;}
	#white-bottom-btn{font-family: 'NanumSquare';display:inline-block;padding:7px 28px 7px 20px;margin-bottom:0;font-size:14px;font-weight:700;border:1px solid;color:#010c1e;border-color: #cdcecf; cursor:pointer;border-radius:4px;}
	#white-bottom-btn:hover{background-color:rgba(0,0,0,.05);transition: 0.3s ease-in-out; color:#191f28;}
</style>
</head>
<body>
	<section id="content">
		<div class="error-wrapper">
			<h2>403</h2>
			<p><spring:message code='error.403msg'/></p>
			<div class="btn-section">
				<a href="javascript:history.back();" id="white-bottom-btn" style="padding:13px 21px 12px 18px;"><img src="/img/previous.png" style="margin:0 16px 0 0;"><spring:message code='error.backpage'/></a>
			</div>
		</div>
	</section>
</body>
</html>