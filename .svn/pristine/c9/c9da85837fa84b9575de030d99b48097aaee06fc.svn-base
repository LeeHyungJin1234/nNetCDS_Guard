<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.ParseException" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String[] colors = new String []{"#0D477D","#8EBBDD","#A87000","#D8C679","#515151","#B7B7B7","#310091","#9673EA","#93008B","#F149E7","#828C38","#C5DE00","#874340","#DBB0A0","#0D477D","#8EBBDD","#A87000","#D8C679","#515151"};
	
	Calendar c1 = Calendar.getInstance();
	c1.add(Calendar.HOUR, -1);
%>
<chart bgcolor="FFFFFF" showborder="0" showLegend='0' showalternatehgridcolor="0" showplotborder="0" showVDivLines="0" lineThickness="1" showShadow="0" formatNumberScale="0" pixelsPerPoint="1" 
btnSwitchtoZoomModeTitle="확대 모드로 전환" btnSwitchToPinModeTitle="고정 모드로 전환" btnZoomOutTitle="축소" btnResetChartTitle="처음으로" 
btnSwitchtoZoomModeTooltext="상세히 보기위한 데이터 영역 선택" btnSwitchToPinModeTooltext="데이터 영역을 선택하여 고정시키고 이동하며 다른 데이터들과 비교" btnZoomOutTooltext="이전화면으로 축소" btnResetChartTooltext="모든 데이터 보기" 
showAboutMenuItem="0" resetChartMenuItemLabel="처음으로" zoomModeMenuItemLabel="확대 모드로 전환" pinModeMenuItemLabel="고정 모드로 전환" zoomOutMenuItemLabel="축소">
	<categories>
   		<% 	
   			for(int i=0; i<60; i++){
		%>
	  			<category label="<%=new java.text.SimpleDateFormat("HH:mm").format(c1.getTime())%>" />
		<% 		
				c1.add(Calendar.MINUTE, 1); // 1분
			}
		%>
	</categories>
	
	<c:if test="${!empty seriesList}">
	<c:forEach var="series" items="${seriesList}" varStatus="status">
		<c:set var="count" value="${status.index}"/>
		
		<dataset seriesName="${series.ncp_name}" color="<%=colors[(Integer) pageContext.getAttribute("count")]%>">
			
			<c:forEach var="value" items="${seriesValue}">
				<c:if test="${value.key == status.index}">
					<c:set var="temp" value="${value.value}"/>
					
					<%
						c1.add(Calendar.HOUR, -1);
						int temp=0;
						for(int i=0; i<60; i++){
							temp=0;
					%>

					<c:set var="doneLoop" value="false" />
					<c:forEach var="obj" items="${temp}">
						<c:if test="${not doneLoop}">
						<c:set var="stat_date" value="${obj.stat_date}"/>
						
					<%
							if(pageContext.getAttribute("stat_date").equals(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime()))){
					%>
								<set value = "${obj.stat_sum}" />
								<c:set var="doneLoop" value="true" />
					<%
								temp=1;
							}
					%>
						</c:if>
					</c:forEach>
					
					<%
							if(temp==0){
					%>
								<set value = "0" />
					<%
							}
							c1.add(Calendar.MINUTE, 1); // 1분
						}
					%>	
					
				</c:if>
			</c:forEach>
		
		</dataset>

	</c:forEach>
	</c:if>
	
	<c:if test="${empty seriesList}">
		<dataset seriesName="">
   		<%
   			for(int i=0; i<60; i++){
		%>
	  			<set value = "0" />
		<%
			}
		%>
		</dataset>
	</c:if>
	
</chart>