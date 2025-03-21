<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<%@ include file="/WEB-INF/views/include/css/setting_css.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	//자체 시험 마지막 검사 시간 업데이트
	function updateProtectionLastDateTime() {
	  $.ajax({
	    type: "GET",
	    url: "/prot_time.do",
	    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
	    success: function (data) {
	      $("#last-test-datetime").text(data);
	    },
	    error: function () {
	      alert("<spring:message code="program.protect.timeerror"/>")
	    }
	  })
	}  
	// 무결성 겁사
	function inspect(){
		if (confirm("<spring:message code='program.wantcheck'/>")) {
			$.ajax({
				type : "POST",
				url : "inspect_start.do",
				data : "nci_div=1",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(data) {
					if (data == "success") {
						alert("<spring:message code='program.checksuccess'/>");
						setTimeout(() => location.replace('/log_integrity_grid.do'), 2000);
					}else if (data == "fail") {
						alert("<spring:message code='program.checkfail'/>");
					}else{
						alert(data + "<spring:message code='program.ing'/>");
					}
				},
				beforeSend:function(){
// 			        $('.wrap-loading').removeClass('display-none');
			    },
			    complete:function(){
			        $('.wrap-loading').addClass('display-none');
			    },
				error : function() {
					alert('<spring:message code='program.checkerror'/>');
				}
			});
		}
	}
	
	// 무결성 값 업데이트
	function update_hash(){
		if (confirm("<spring:message code='program.wantupdate'/>")) {
			$.ajax({
				type : "POST",
				url : "update_hash.do",
				data : "nci_div=1",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(data) {
					if (data == "success") {
						alert("<spring:message code='program.updateexec'/>");
						location.replace('/self_protect.do');
					}else if (data == "fail") {
						alert("<spring:message code='program.updatefail'/>");
					}else{
						alert(data+"가 진행 중입니다.");
					}
				},
				beforeSend:function(){
			        $('.wrap-loading').removeClass('display-none');
			    },
			    complete:function(){
			        $('.wrap-loading').addClass('display-none');
			    },
				error : function() {
					alert('무결성값 업데이트 수행 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	
	// 무결성 점검 주기 업데이트
	function update_cycle() {
		if (confirm("<spring:message code='alert.edit'/>")) {
			var formData = $("form[id=writeform]").serialize();
			$.ajax({
				type : "POST",
				url : "cycle_update.do",
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(data) {
					if (data == "true") {
						alert("<spring:message code='alert.changed'/>");
						location.replace('/self_protect.do');
					}else{
						alert(data);
					}
				},
				error : function() {
					alert('수정 작업 중 에러가 발생하였습니다.');
				}
			});
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
				<%@ include file="../include/tabmenu.jsp"%>
				
				 <div class="title-integrity">
			        <spring:message code="program.protect.title"/>
			      </div>
			
			      <div id="btn-section-2nd" class="btn-section clearfix" >
					<span class="text-main-color2">
						<span id="last-test-text" class="sub-section-1st">
							<spring:message code='program.protect.desc'/>
						</span>
						<span id="last-test-datetime" class="sub-section-1st">
			              <img src="<c:url value="/img/icon_cal.png"/>" style="vertical-align: middle;"/>
			              ${protection_datetime}
			            </span>
					</span>
			      </div>
			      <div class="content" style="border-bottom: 1px solid #A7A9AC;">
			        <table class="scroll">
			          <colgroup>
			            <col width="63px"/>
			            <col width="335px"/>
			            <col width="813px"/>
			            <col width="140px"/>
			            <col width="318px"/>
			          </colgroup>
			          <thead>
			          <tr class="text-small text-main-color2">
			            <th style="padding:10px 0;">
			              <span><spring:message code='log.turn'/></span>
			            </th>
			            <th style="padding:10px 0;">
			              <span><spring:message code='stat.servicenm'/></span>
			            </th>
			            <th style="padding:10px 0;">
			              <span><spring:message code='program.hash'/></span>
			            </th>
			            <th style="padding:10px 0;">
			              <span><spring:message code='program.size'/>(KB)</span>
			            </th>
			            <th style="padding:10px 0;">
			              <span><spring:message code='program.date'/></span>
			            </th>
			          </tr>
			          </thead>
			          <tbody>
			          <c:choose>
			            <c:when test="${pginfo2.result != null && !empty pginfo2.result}">
			              <c:forEach var="service" items="${pginfo2.result}" varStatus="status">
			                <tr class="text-medium text-main-color">
			                  <td align="center">
			                  	<span>
									<c:out value="${pginfo2.totalArticle-((pginfo2.currentPage-1)*pginfo2.perArticle)-status.index}"/>
								</span>
			                  </td>
			                  <td align="center" style="padding:25px 0;">
			                    <span>${service.ncp_name}</span>
			                  </td>
			                  <td align="center" style="padding:25px 0;">
			                    <span>${service.ncp_hash}</span>
			                  </td>
			                  <td align="center" style="padding:25px 0;">
			                    <span id="ncp_file_size">${service.ncp_file_size2}</span>
			                  </td>
			                  <td align="center" style="padding:25px 0;">
			                      ${service.ncp_hash_date}
			                  </td>
			                </tr>
			              </c:forEach>
			            </c:when>
			            <c:otherwise>
			            	<tr class="text-medium text-main-color">
			            		<td align="center" colspan="5" style="padding:25px 0;">
			            			<spring:message code='log.nodata'/>
			            		</td>
			            	</tr>
			            </c:otherwise>
			          </c:choose>
			          </tbody>
			        </table>
			      	<%@ include file="../include/paging2.jsp" %>
			      </div>
			      
			
			      <div class="title-integrity">
			        <spring:message code="program.integcheck"/>
			      </div>
				
				<div id="btn-section-2nd" class="btn-section clearfix">
					<span class="text-main-color2" style="float:left; padding:6px 12px 0 19px;"><spring:message code='program.integcycle'/></span>
					<form id="writeform" method="POST" style="float:left; margin-right:20px">
					<input type="hidden" name="nci_div" value="1"/>
						<select name="nci_cycle" id="nci_cycle">
							<option value="1" <c:if test="${cycle.nci_cycle == 1}">selected</c:if>>1</option>
							<option value="2" <c:if test="${cycle.nci_cycle == 2}">selected</c:if>>2</option>
							<option value="4" <c:if test="${cycle.nci_cycle == 4}">selected</c:if>>4</option>
							<option value="12" <c:if test="${cycle.nci_cycle == 12}">selected</c:if>>12</option>
							<option value="24" <c:if test="${cycle.nci_cycle == 24}">selected</c:if>>24</option>
						</select>
					</form>
					<a href="javascript:update_cycle();" id="red-top-btn" style="margin-right:8px;"><img src="/img/modify.png" style="margin:3px 8px 0 0;">수정하기</a>
					<span style="float:right; padding-right:0px;">
						<a href="javascript:inspect();" id="red-top-btn" class="forward_btn" style="margin-right:0px;"><img src="/img/btncheck.png" style="margin:6px 8px 0 0;"><spring:message code='program.integcheck_m'/></a>
					</span>
				</div>

				<div class="content">
					<table class="scroll">
					<colgroup>
						<col width="63px" />
						<col width="220px" />
						<col width="126px" />
						<col width="200px" />
						<col width="700px" />
						<col width="140px" />
						<col width="218px" />
					</colgroup>
						<thead>
							<tr class="text-small text-main-color2">
								<th style="padding:10px 0;"><span><spring:message code='log.turn'/></span></th>
								<th style="padding:10px 0;"><span><spring:message code='policy.programnm'/></span></th>
								<th style="padding:10px 0;"><span>정책번호</span></th>
								<th style="padding:10px 0;"><span>정책명</span></th>
								<th style="padding:10px 0;"><span><spring:message code='program.hash'/></span></th>
								<th style="padding:10px 0;"><span><spring:message code='program.size'/>(byte)</span></th>
								<th style="padding:10px 0;"><span><spring:message code='program.date'/></span></th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
						<c:when test="${pginfo.result != null && !empty pginfo.result}">
						<c:forEach var="send" items="${pginfo.result}" varStatus="status">
							<tr class="text-medium text-main-color">
								<td align="center"><span><c:out value="${pginfo.totalArticle-((pginfo.currentPage-1)*pginfo.perArticle)-status.index}" /></span></td>
								<td align="center" style="padding:25px 0;"><span>${send.ncp_name}</span></td>
								<td align="center" style="padding:25px 0;"><span>${send.npl_no}</span></td>
								<td align="center" style="padding:25px 0;"><span>${send.npl_name}</span></td>
								<td align="center" style="padding:25px 0;"><span>${send.ncp_hash}</span></td>
								<td align="center" style="padding:25px 0;"><span><fmt:formatNumber value="${send.ncp_file_size}" pattern="###,###" /></span></td>
								<td align="center" style="padding:25px 0;">${send.ncp_hash_date}</td>
							</tr>
						</c:forEach>
						</c:when>
						</c:choose>
						</tbody>
						<tfoot>
							<tr>
								<td></td><td></td><td></td><td></td><td></td><td></td>
							</tr>
						</tfoot>
					</table>
				</div>
				<%@ include file="../include/paging.jsp"%>      
			</div>
		</div>
	</section>
	<!-- left menu end -->
	<%@ include file="../include/footer.jsp"%>
</body>
<script>
if($("#right-content .right-wrapper .content table tbody tr").length>0){
	$("#right-content").removeClass("footh")
} else if($("#right-content .right-wrapper .content table tbody tr").length<1){
	$("#right-content").addClass("footh")
}
</script>
</html>