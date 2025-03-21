<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<c:set var="netflow_data" value="${data}" />
<%
 	String[][] netflow_data = (String[][]) pageContext.getAttribute("netflow_data"); 

 	StringBuffer sbuff = new StringBuffer();
	for(int i=0;i<netflow_data.length;i++){
		for(int j=0;j<netflow_data[i].length;j++){
			sbuff.append(netflow_data[i][j]);
			if(j<netflow_data[i].length-1) {
				sbuff.append(","); // 배열의 끝이 아니라면 문자사이에 ,(콤마) 구분자를 추가
		 	}
		}
		if(i<netflow_data.length-1){
			sbuff.append("|"); // 배열의 끝이 아니라면 문자사이에 |(파이프) 구분자를 추가
	 	}
	}
%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
body {
	font-family: Arial, Helvetica, Sans-Serif;
	margin: 0;
	padding: 0;
}
svg text {
    font-size: 12px;
}
rect {
    shape-rendering: crispEdges;
}
</style>
<body>
<script src="/js/d3.v3.min.js"></script>
<script src="/js/biPartite.js"></script>
<script>
	//네트워크 선차트에서 클릭 이벤트 
	function setAgent_network(param){
		//parent.document.location.href = "main_dash.do?"+param;
		alert(param);
	}
</script>
<script>
	var str="<%=sbuff.toString()%>"
	var temp = str.split("|");
	var chart_data = new Array();
	
	for(var i = 0; i < temp.length; i++) {
		chart_data[i] = temp[i].split(",");
	}
	
	var width = 1100, height = 710, margin ={b:0, t:40, l:160, r:50};
	
	var svg = d3.select("body")
		.append("svg").attr('width',width).attr('height',(height+margin.b+margin.t))
		.append("g").attr("transform","translate("+ margin.l+","+margin.t+")");
	
	var data = [ 
		{data:bP.partData(chart_data,2), id:'NetworkTraffic', header:["Source","Destination", "Network Traffic"]}
	];
	
	bP.draw(data, svg);
</script>
</body>
</html>