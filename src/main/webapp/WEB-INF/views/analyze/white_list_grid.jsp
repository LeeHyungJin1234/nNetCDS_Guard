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
<meta charset="UTF-8">
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">
<!-- font -->
<link rel="stylesheet" href="/css/DevExtreme/dx.generic.custom-swatch.css" />
<link rel="stylesheet" href="/css/DevExtreme/dx.light_Custom.css" />
<%@ include file="/WEB-INF/views/include/css/table_css.jsp"%>

<script type="text/javascript" src="<c:url value="/js/polyfill.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/DevExtreme/dx.all.js"/>"></script>
<script type="text/javascript">
	jQuery.curCSS = function(element, prop, val){
		return jQuery(element).css(prop, val);
	};
	
</script>
</head>
<body>
	<div class="demo-container">
     	<div id="whiteListGrid"></div>
   	</div>
</body>

<script type="text/javascript">
	var jsonlogdata = ${jsondata};
	
	var dataGrid = $('#whiteListGrid').dxDataGrid({
	    dataSource: jsonlogdata,
	    multiline : true,
        RowAutoHeight:true,
	    columnsAutoWidth: true,
	    showBorders: true,
	    width: '100%',
	    filterRow: {
	      visible: false,
	    },
	    headerFilter: {
	      visible: true,
	    },
	    columns: [{
	      dataField: 'wl_id',
	      caption: 'ID',
	      dataType: 'String',
	      alignment: 'center', 
	      width: '5%',
	      allowHeaderFiltering: false,
	    }, {
	      dataField: 'wl_proto_sub_dec',
	      caption: '서비스',
	      alignment: 'center',
	      dataType: 'String',
	      width: '10%',
	    }, {
		  dataField: 'strPsInOut',
		  caption: '구분',
		  alignment: 'center',
		  dataType: 'String',
		  width: '5%',
		 }, {
			 caption: '출발지',
		     alignment: 'center',
			 columns: [{
				 dataField: 'wl_src_ip',
			     caption: 'IP',
			     alignment: 'center',
			     dataType: 'string',
			     width: '20%',
			     headerFilter: {
			       search: {
			         enabled: true,
			      },
				}, 
			 },{
				 dataField: 'wl_src_port',
			     caption: 'Port',
			     alignment: 'center',
			     dataType: 'string',
			     width: '15%',
			     headerFilter: {
			       search: {
			         enabled: true,
			      },
				}, 
			 }],
		  },
		  , {
			 caption: '목적지',
		     alignment: 'center',
			 columns: [{
				 dataField: 'wl_dst_ip',
			     caption: 'IP',
			     alignment: 'center',
			     dataType: 'string',
			     width: '20%',
			     headerFilter: {
			       search: {
			         enabled: true,
			      },
				}, 
			 },{
				 dataField: 'wl_dst_port',
			     caption: 'Port',
			     alignment: 'center',
			     dataType: 'string',
			     width: '15%',
			     headerFilter: {
			       search: {
			         enabled: true,
			      },
				}, 
			 }],
		  },{
		  dataField: 'wl_regdttm',
		  caption: '등록일',
		  alignment: 'center',
		  dataType: 'date',
	      format: 'yyyy-MM-dd HH:mm:ss',
		  width: '10%',
		  headerFilter: {
		    search: {
	          enabled: true,
	        },
		  },
		}],
	  }).dxDataGrid('instance');
</script>
</html>