<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title><spring:message code='common.title'/></title>
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome.min.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/nprogress.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/custom.min.css"/>">
  <script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/js/dialog.js"/>"></script>
  
	<!-- start csrf -->
	<%@ include file="/WEB-INF/views/include/csrf.jsp"%>
	<!-- csrf end -->
	
  <script type="text/javascript">
  $("header").remove();
  $(document).ready(function () {
  	if(typeof timeInterval !== 'undefined'){
		clearInterval(timeInterval);  
    }
	if(typeof alarmInterval !== 'undefined'){
		clearInterval(alarmInterval);  
	}
      setTimeout(function () {
        location.replace("/login.do");
      }, <%= session.getMaxInactiveInterval() * 1000 - 5000 %>);

    });
    function save() {
   	
      $.ajax({
        type: "POST",
        url: "prod_info.do",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: $("#form").serialize(),
        success: function (data) {
        	if (data == "true") {
       	    	document.frmTmp.ncpd_key.value=null;
       	  		document.frmTmp.ncpd_key=null;
       	    	document.frmTmp=null;
        		alert("<spring:message code='alert.setting.success'/>");
            document.location.href='/logout.do';
            }
        	else if (data === "not_enough_length") {
                alert("<spring:message code='alert.serial.number.rule'/>");
            }
        	else{
        		alert("<spring:message code='alert.initialization.error'/>");
        		document.location.href='/login.do';
        	}

        },
        error: function (data) {
        	alert("<spring:message code='alert.initialization.error'/>");
        	document.location.href='/login.do';
        }
      });

    }
  </script>
</head>
<style>
  body, html {
    background: #f7f7f7;
    height: 100%;
  }

  /*#prod {*/
  /*  position: relative;*/
  /*  text-align: center;*/
  /*  height: 100%;*/
  /*}*/

  #prodHeader {
    height: 178px;
    line-height: 178px;
    background-image: url('/images/top_bg.png');
    background-repeat: no-repeat;
    background-size: 100% 100%;
    text-align:center;
  }

  #prodFooter {
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    background: #fff;
    text-align: right;
    height: 50px;
    line-height: 50px;
    padding-right: 20px;
  }
  
  .item{display:inline-block;width:100%;}
  .item > label{text-align:right;} 
  
  	#footer {margin:0 auto;border-top:1px solid;border-top-color:rgba(224,227,231,0.4);width:1920px;min-width: 100%;}
	#footer .footer-wrapper {margin:0 auto;width:1920px;}
	#footer .footer-wrapper span {float: right; padding: 14px 28px 14px 0;}
</style>
<body>
<div id="prod">
  <div id="prodHeader"><img src="<c:url value="/img/logo_nNetCDS_GuardV2_v.png"/>" alt="nNetCDS Guard V2.0"/></div>


    <div class="col-md-12 col-sm-12 col-xs-12" style="padding:25px 20px 10px 20px">
      <div class="x_panel">
        <div class="x_title">
          <h2><spring:message code='product.setting'/></h2>
          <div class="clearfix"></div>
        </div>

        <div class="x_content form-horizontal form-label-left">
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='product.pskey'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <form name='frmTmp' id='frmTmp'>
              <input type="text" class="form-control col-md-7 col-xs-12" name="ncpd_key" value ="${currProdInfo.ncpd_key}" readonly/>
              </form>
            </div>
          </div>
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='product.hardware.name'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="ncpd_hw_model" value ="${currProdInfo.ncpd_hw_model}" readonly/>
            </div>
          </div>
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='product.name'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="ncpd_model" value ="${currProdInfo.ncpd_model}" readonly/>
            </div>
          </div>
          <form id='form' name='form' method="post">
          <div class="item form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-12">
              <spring:message code='product.serial.number'/>
            </label>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <input type="text" class="form-control col-md-7 col-xs-12"
                     name="ncpd_sn" />
            </div>
          </div>
          </form>
          <div class="ln_solid"></div>
          <div class="form-group">
        	<div class="col-md-3"></div>
			<div class="col-md-6">
              <button type="button" class="btn btn-danger" onClick="javascript:save();">
                <i class="fa fa-pencil"></i>
                <spring:message code='btn.registration'/>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

  <%@ include file="include/footer.jsp"%>
</div>
</body>
</html>