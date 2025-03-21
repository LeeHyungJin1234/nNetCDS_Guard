<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNet-CSG Manager</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="/js/FusionCharts.js"></script>
<script type="text/javascript">

</script>
</head>

<body>
	<%@ include file="../include/top.jsp"%>
	
	
	<div id="main">
	
	    <h1 id="exampleTitle"><strong>${title}</strong></h1>
	    <div id="exampleWrap" style="margin:0em 2.200em 5.000em 1.500em;">
	    
	    </div>
	
		<%@ include file="../include/footer.jsp"%>
	
	</div>

</body>
</html>