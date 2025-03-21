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
	//시작 , 끝 날짜 임의 세팅
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

<chart bgcolor="ffffff" caption="" numberprefix="" xaxisname="" useroundedges="1" showvalues="0" legendborderalpha="0" showsum="1" showalternatehgridcolor="0" divlineisdashed="1" palettecolors="f8bd19,008ee4,33bdda,e44a00,6baa01,583e78" 
decimals="0" showborder="0" plotSpacePercent="50">
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
	<dataset seriesname="관리자">
	<% 	
		c1.setTime( d1 );
		c2.setTime( d2 );
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
	%>
			<c:set var="temp">0</c:set>
			<c:forEach var="mng" items="${mList}" varStatus="status">
				<c:set var="compare"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime())%></c:set>
	
				<c:if test="${mng.nlm_create_date == compare}">
					<set value="${mng.cnt}" />
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
	<dataset seriesname="전송 통제시스템">
	<% 	
		c1.setTime( d1 );
		c2.setTime( d2 );
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
	%>
			<c:set var="temp">0</c:set>
			<c:forEach var="sys" items="${sList}" varStatus="status">
				<c:set var="compare"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime())%></c:set>
				
				<c:if test="${sys.nlm_create_date == compare}">
					<set value="${sys.cnt}" />
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
	<dataset seriesname="기타">
	<% 	
		c1.setTime( d1 );
		c2.setTime( d2 );
		//시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력
		while( c1.compareTo( c2 ) !=1 ){
	%>
			<c:set var="temp">0</c:set>
			<c:forEach var="etc" items="${eList}" varStatus="status">
				<c:set var="compare"><%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(c1.getTime())%></c:set>
				
				<c:if test="${etc.nlm_create_date == compare}">
					<set value="${etc.cnt}" />
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