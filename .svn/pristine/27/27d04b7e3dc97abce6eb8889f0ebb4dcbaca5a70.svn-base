<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${show == 'i'}">
	
		<chart caption="INGRESS BYTES" bgcolor="FFFFFF" showvalues="0" showpercentvalues="0" showborder="0" showplotborder="0" showlegend="0" legendborder="0"
			showlabels="1" showpercentageinlabel="1" enablesmartlabels="1" use3dlighting="0" showshadow="0" legendbgcolor="#CCCCCC" legendbgalpha="20" legendborderalpha="0" legendshadow="0" legendnumcolumns="3" 
			palettecolors="#0D477D,#8EBBDD,#A87000,#D8C679,#515151,#B7B7B7,#310091,#9673EA,#93008B,#F149E7,#828C38,#C5DE00,#874340,#DBB0A0,#0D477D,#8EBBDD,#A87000,#D8C679,#515151">
			<c:forEach var="system" items="${systemList}" varStatus="status">
				<set label="${system.noi_obj_nm}" value="${system.insize}" />
			</c:forEach>
		</chart>
		
	</c:when>
	<c:otherwise>
		
		<chart caption="EGRESS BYTES" bgcolor="FFFFFF" showvalues="0" showpercentvalues="0" showborder="0" howplotborder="0" showlegend="0" legendborder="0"
			showlabels="1" showpercentageinlabel="1" enablesmartlabels="1" use3dlighting="0" showshadow="0" legendbgcolor="#CCCCCC" legendbgalpha="20" legendborderalpha="0" legendshadow="0" legendnumcolumns="3" 
			palettecolors="#0D477D,#8EBBDD,#A87000,#D8C679,#515151,#B7B7B7,#310091,#9673EA,#93008B,#F149E7,#828C38,#C5DE00,#874340,#DBB0A0,#0D477D,#8EBBDD,#A87000,#D8C679,#515151">
			<c:forEach var="system" items="${systemList}" varStatus="status">
				<set label="${system.noi_obj_nm}" value="${system.outsize}" />
			</c:forEach>
		</chart>
		
	</c:otherwise>
</c:choose>