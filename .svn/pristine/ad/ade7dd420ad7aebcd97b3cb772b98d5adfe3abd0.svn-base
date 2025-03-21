<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav data-pagination>
	<div>
		
		<c:choose>
			<c:when test="${pginfo.currentPage > 1}">
				<a href="${base_url}1" style="margin-right:-4px;border-left-width:1px;" class="page-box"><img src="/img/trans.png" style="background:url(/img/page-arrow1.png) no-repeat; width:10px;height:10px; vertical-align: middle;"></a>
				<a href="${base_url}${pginfo.currentPage -1}" class="page-box" style="border-left-width:0;"><img src="/img/trans.png" style="background:url(/img/page-arrow2.png) no-repeat 1px top; width:10px;height:10px; vertical-align: middle;"></a>
			</c:when>
			<c:otherwise>
				<a style="margin-right:-4px;border-left-width:1px;cursor:not-allowed" class="page-box"><img src="/img/trans.png" style="background:url(/img/page-arrow1.png) no-repeat; width:10px;height:10px; vertical-align: middle;"></a>
				<a class="page-box" style="border-left-width:0;cursor:not-allowed"><img src="/img/trans.png" style="background:url(/img/page-arrow2.png) no-repeat 1px top; width:10px;height:10px; vertical-align: middle;"></a>
			</c:otherwise>
		</c:choose>
		
		<ul>
		<c:forEach items="${pginfo.pagesArray}" var='pgno' >
			<c:choose>
				<c:when test='${pgno == pginfo.currentPage}'>
					<li class="active"><a>${pgno}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${base_url}${pgno}">${pgno}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</ul>
	
		<c:choose>
			<c:when test="${pginfo.currentPage < pginfo.totalPage}">
				<a href="${base_url}${pginfo.currentPage + 1}" style="margin-right:-4px;border-left-width:1px;" class="page-box"><img src="/img/trans.png" style="background:url(/img/page-arrow3.png) no-repeat 3px top; width:10px;height:10px; vertical-align: middle;"></a>
				<a href="${base_url}${pginfo.totalPage}" class="page-box" style="border-left-width:0;"><img src="/img/trans.png" style="background:url(/img/page-arrow4.png) no-repeat; width:10px;height:10px; vertical-align: middle;"></a>
			</c:when>
			<c:otherwise>
		    	<a style="margin-right:-4px;border-left-width:1px;cursor:not-allowed" class="page-box"><img src="/img/trans.png" style="background:url(/img/page-arrow3.png) no-repeat 3px top; width:10px;height:10px; vertical-align: middle;"></a>
				<a class="page-box" style="border-left-width:0;cursor:not-allowed"><img src="/img/trans.png" style="background:url(/img/page-arrow4.png) no-repeat; width:10px;height:10px; vertical-align: middle;"></a>
		  	</c:otherwise>
		</c:choose>
		
	</div>
</nav>