<%@page contentType="text/xml" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="start" value="${sdate}" />
<c:set var="end" value="${edate}" />
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.ParseException" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%
	//시작, 끝 날짜 세팅
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	String s1 = (String) pageContext.getAttribute("start");
	String s2 = (String) pageContext.getAttribute("end");
	
	//Date타입으로 변경
	Date d1 = df.parse( s1 );
	Date d2 = df.parse( s2 );
	
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();
	
	//Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
	c1.setTime( d1 );
	c2.setTime( d2 );
%>
<chart caption="" numberprefix="" plotgradientcolor="" bgcolor="FFFFFF" showalternatehgridcolor="0" divlinecolor="CCCCCC" showvalues="0" showcanvasborder="0" canvasborderalpha="0" canvasbordercolor="CCCCCC" 
canvasborderthickness="1" captionpadding="30" linethickness="1" xAxisNamePadding="1" legendshadow="0" legendborderalpha="0" palettecolors="#f8bd19,#008ee4,#33bdda,#e44a00,#6baa01,#583e78" showborder="0" numVisiblePlot="8">
	<categories>
		<% 	//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
			while( c1.compareTo( c2 ) !=1 ){
		%>
	  			<category label="<%=new java.text.SimpleDateFormat("MM-dd").format(c1.getTime())%>" />
		<% 		
				c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
			}
		%>
	</categories>
	<dataset seriesname="접근성공">
	<% 	
		c1.setTime( d1 );
		c2.setTime( d2 );
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
	%>
			<c:set var="temp">0</c:set>
			<c:forEach var="success" items="${sList}" varStatus="status">
				<c:set var="compare"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime())%></c:set>
	
				<c:if test="${success.nlm_create_date == compare}">
					<set value="${success.cnt}" />
					<c:set var="temp">1</c:set>
				</c:if>
  			</c:forEach>
			
			<c:if test="${temp == 0}">
				<set value="0" />
			</c:if>
	<%
			c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
		}
	%>
	</dataset>
	<dataset seriesname="접근실패">
	<% 	
		c1.setTime( d1 );
		c2.setTime( d2 );
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
	%>
			<c:set var="temp">0</c:set>
			<c:forEach var="fail" items="${fList}" varStatus="status">
				<c:set var="compare"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime())%></c:set>
				
				<c:if test="${fail.nlm_create_date == compare}">
					<set value="${fail.cnt}" />
					<c:set var="temp">1</c:set>
				</c:if>
   				
  			</c:forEach>
			
			<c:if test="${temp == 0}">
				<set value="0" />
			</c:if>
	<%
			c1.add(Calendar.DATE, 1); //시작날짜 + 1 일
		}
	%>
	</dataset>
</chart>