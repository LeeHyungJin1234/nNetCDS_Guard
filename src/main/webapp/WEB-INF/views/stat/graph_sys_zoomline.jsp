<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String[] colors = new String []{"#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7","#828C38","#C5DE00","#874340","#DBB0A0","#0D477D","#8EBBDD","#A87000","#D8C679","#515151"};
%>
<c:choose>
    <c:when test="${show == 'i'}">

		<chart caption="INGRESS BYTES" bgcolor="FFFFFF" showborder="0">
			<categories>
				<c:forEach var="date" items="${dateList}" varStatus="status">
					<c:set var="datesplit" value="${fn:split(date.nsn_reg_date, ' ')}" scope="request" />
	    			<category label="${datesplit[1]}" />
	   			</c:forEach>
	  		</categories>

			<c:forEach var="series" items="${seriesList}" varStatus="status">
				<c:set var="count" value="${status.index}"/>
				
				<c:choose>
					<c:when test="${ null eq series.service_name}">
				  		<dataset seriesName="${series.port}" color="<%=colors[(Integer) pageContext.getAttribute("count")]%>">
				  	</c:when>
					<c:otherwise>
						<dataset seriesName="${series.service_name}#${series.port}" color="<%=colors[(Integer) pageContext.getAttribute("count")]%>">
					</c:otherwise>
				</c:choose>
				
				<c:forEach var="value" items="${seriesValue}">
				<c:if test="${value.key == status.index}">
					<c:set var="temp" value="${value.value}"/>
					<c:forEach var="obj" items="${temp}">
						<set value = "${obj.insize}" />
					</c:forEach>
				</c:if>
				</c:forEach>
				
				</dataset>
			</c:forEach>
		</chart>

	</c:when>
	<c:otherwise>
		
		<chart caption="EGRESS BYTES" bgcolor="FFFFFF" showborder="0">
			<categories>
				<c:forEach var="date" items="${dateList}" varStatus="status">
					<c:set var="datesplit" value="${fn:split(date.nsn_reg_date, ' ')}" scope="request" />
	    			<category label="${datesplit[1]}" />
	   			</c:forEach>
			</categories>

			<c:forEach var="series" items="${seriesList}" varStatus="status">
				<c:set var="count" value="${status.index}"/>
			  	
			  	<c:choose>
					<c:when test="${ null eq series.service_name}">
				  		<dataset seriesName="${series.port}" color="<%=colors[(Integer) pageContext.getAttribute("count")]%>">
				  	</c:when>
					<c:otherwise>
						<dataset seriesName="${series.service_name}#${series.port}" color="<%=colors[(Integer) pageContext.getAttribute("count")]%>">
					</c:otherwise>
				</c:choose>
				
				<c:forEach var="value" items="${seriesValue}">
				<c:if test="${value.key == status.index}">
					<c:set var="temp" value="${value.value}"/>
					<c:forEach var="obj" items="${temp}">
						<set value = "${obj.outsize}" />
					</c:forEach>
				</c:if>
				</c:forEach>
				
				</dataset>
			</c:forEach>
		</chart>
		
	</c:otherwise>
</c:choose>