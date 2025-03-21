<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <!DOCTYPE html PUBLIC "-W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>nNet-CSG Manager</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="/js/FusionCharts.js"></script>
<script type="text/javascript">
	var rsystem_tb = null;
	var rservice_tb = null;
	var rsystem = null;
	var rservice = null;
	
	var traffic = null;
	var traffic1 = null;
	
	var system_tb = null;
	var service_tb = null;
	var system = null;
	var service = null;

	window.onload = function() {
		getSystem_tb();
		getService_tb();
		drawSystem();
		drawService();

		getReciSystem_tb();
		getReciService_tb();
		drawReciSystem();
		drawReciService();

	 	rsystem_tb= setInterval("getReciSystem_tb();", 60000); // 1분 간격
		rservice_tb = setInterval("getReciService_tb();", 60000); // 1분 간격
		rsystem = setInterval("drawReciSystem();", 60000); // 1분 간격
		rservice = setInterval("drawReciService();", 60000); // 1분 간격

		getNetwork_traffic();
		traffic = setInterval("getNetwork_traffic();", 60000); // 1분 간격
		getNetwork_traffic1();
		traffic1 = setInterval("getNetwork_traffic1();", 60000); // 1분 간격
		
		system_tb = setInterval("getSystem_tb();", 60000); // 1분 간격
		service_tb = setInterval("getService_tb();", 60000); // 1분 간격
		system = setInterval("drawSystem();", 60000); // 1분 간격
		service = setInterval("drawService();", 60000); // 1분 간격
	}
	
	// 시스템별 트래픽 상세
    function system_detail(obj, group_id){
    	var btn = $(obj);
    	var img = btn.children("img");
    	
    	if(img.attr("src").indexOf("close.png") > 0){
			$.ajax({        
				type : "POST",  
				data : "group_id="+group_id,
				url : "system_detail.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data != null)  {
						$('#system_'+group_id).after(data);
					}    
				}  
			});
			img.attr("src", "/images/tree_menu/open.png");
    	}else{
    		var temp = document.getElementsByName('sys_dtTR_'+group_id);
			for (var j = 0; j < temp.length; j++){
				temp[j].style.display = 'none';
			}
			img.attr("src", "/images/tree_menu/close.png");
    	}
	}
	
	// 서비스별 트래픽 상세
	function service_detail(obj, port, protocol, view_type){
    	var btn = $(obj);
    	var img = btn.children("img");
    	
    	if(img.attr("src").indexOf("close.png") > 0){
			$.ajax({        
				type : "POST",  
				data : "port="+port+"&protocol="+protocol+"&view_type="+view_type,
				url : "service_detail.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) { 
					if(data != null)  {
						$('#service_'+port+'_'+protocol+'_'+view_type).after(data);
					}
				}  
			});
			img.attr("src", "/images/tree_menu/open.png");
    	}else{
    		var temp = document.getElementsByName('srv_dtTR_'+port+'_'+protocol+'_'+view_type);
			for (var j = 0; j < temp.length; j++){
				temp[j].style.display = 'none';
			}
			img.attr("src", "/images/tree_menu/close.png");
    	}
	 }
</script>
</head>

<body>
	<%@ include file="../include/top.jsp"%>
	
	<div id="main">
	
	    <h1 id="exampleTitle"><strong>${title}</strong></h1>
		<div id="exampleWrap" style="margin:0em 2.200em 5.000em 1.500em;">
			
			<table style="margin:0px;padding:0px;" border="0px" cellpadding="0" cellspacing="0"><tr>
			<td style="vertical-align:top">
			
				<table class="ResourceContainer"><tr>
	                <td class="ResourceColumn">
						<div class="ResourceWrapper" style="width:505px;">
							<div class="HeaderBar">
								<h1>시스템별 트래픽</h1>
							</div>
							<div id="system_ingress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="system_chart"></div></div>
							<div id="system_egress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="system_chart1"></div></div>
							<table cellspacing="0px" cellpadding="2px">
							<colgroup> 
								<col width="5%" />
								<col width="5%" />
								<col width="" />
								<col width="" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
							</colgroup> 
								<thead style="table-layout:fixed;">
								<tr>
									<td style="text-align:center;"></td>
									<td style="text-align:center;"></td>
									<td style="text-align:center;">SYSTEM</td>
									<td style="text-align:center;">OUTSYSTEM</td>
									<td style="text-align:center;">INGRESS<BR>BYTES</td>
									<td style="text-align:center;">EGRESS<BR>BYTES</td>
									<td style="text-align:center;">INGRESS<BR>PACKETS</td>
									<td style="text-align:center;">EGRESS<BR>PACKETS</td>
								</tr>
								</thead>
							</table>
							<div id='system_table'></div>
						</div>
					</td>
				</tr></table>
				<script type="text/javascript">
					function drawSystem(){
				        var chart1 = new FusionCharts("/charts/Pie3D.swf", "ChId2", "500", "250", "0", "0");
				        chart1.setDataURL(encodeURIComponent("/system_ct.do?view_type=SEND&show=i"));
				        chart1.render("system_chart");
				        
				        var chart2 = new FusionCharts("/charts/Pie3D.swf", "ChId2", "500", "250", "0", "0");
				        chart2.setDataURL(encodeURIComponent("/system_ct.do?view_type=SEND&show=e"));
				        chart2.render("system_chart1");
			        }
	
					function getSystem_tb(){
						$.ajax({        
							type : "POST",  
							url : "system_tb.do",   
							data : "view_type=SEND",
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
							success: function(data) { 
								if(data != null)  {
									document.getElementById('system_table').innerHTML = data;
								}    
							}  
						});
					 }
				</script>
			
				<table class="ResourceContainer"><tr>
	                <td class="ResourceColumn">
						<div class="ResourceWrapper" style="width:505px;">
							<div class="HeaderBar">
								<h1>서비스별 트래픽</h1>
							</div>
							<div id="service_ingress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="service_chart"></div></div>
							<div id="service_egress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="service_chart1"></div></div>
							<table cellspacing="0px" cellpadding="2px">
							<colgroup> 
								<col width="5%" />
								<col width="5%" />
								<col width="" />
								<col width="" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
							</colgroup> 
								<thead style="table-layout:fixed;">
								<tr>
									<td style="text-align:center;"></td>
									<td style="text-align:center;"></td>
									<td style="text-align:center;">SERVICE</td>
									<td style="text-align:center;">OUTSYSTEM</td>
									<td style="text-align:center;">INGRESS<BR>BYTES</td>
									<td style="text-align:center;">EGRESS<BR>BYTES</td>
									<td style="text-align:center;">INGRESS<BR>PACKETS</td>
									<td style="text-align:center;">EGRESS<BR>PACKETS</td>
								</tr>
								</thead>
							</table>
							<div id='service_table'></div>
						</div>
					</td>
				</tr></table>
				<script type="text/javascript">
			       	function drawService() {
						var chart3 = new FusionCharts("/charts/ZoomLine.swf", "ChId2", "500", "350", "0", "0");
						chart3.setDataURL(encodeURIComponent("/service_ct.do?view_type=SEND&show=i"));
						chart3.render("service_chart");
				        
				        var chart4 = new FusionCharts("/charts/ZoomLine.swf", "ChId2", "500", "350", "0", "0");
				        chart4.setDataURL(encodeURIComponent("/service_ct.do?view_type=SEND&show=e"));
				        chart4.render("service_chart1");
					}
			        
			        function getService_tb(){
						$.ajax({        
							type : "POST",  
							url : "service_tb.do",    
							data : "view_type=SEND",
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
							success: function(data) { 
								if(data != null)  {
									document.getElementById('service_table').innerHTML = data;
								}    
							}  
						});
					 }
			    </script>
			
			</td>
			<td style="vertical-align:top">
			
				<script type="text/javascript">
					// iframe resize
				 	function autoResize(i){
						var iframeHeight=
						(i).contentWindow.document.body.scrollHeight;
						(i).height=iframeHeight+20;
				 	}
					
				 	function getNetwork_traffic(){
				 		document.getElementById('network_traffic').innerHTML="<iframe name='itemGrp05' id='itemGrp06' src='/network_traffic.do?view_type=SEND' marginwidth='0' marginheight='0' scrolling='no' border='0' frameborder='0' width='518' onload='autoResize(this)'></iframe>";
					}
				 	
				 	function getNetwork_traffic1(){
				 		document.getElementById('network_traffic1').innerHTML="<iframe name='itemGrp05' id='itemGrp06' src='/network_traffic1.do?view_type=RECI' marginwidth='0' marginheight='0' scrolling='no' border='0' frameborder='0' width='518' onload='autoResize(this)'></iframe>";
					}
				</script>
				<table class="ResourceContainer">
		            <tr>
		                <td class="ResourceColumn">
							<div class="ResourceWrapper" style="width:505px;">
								<div id="network_traffic"></div>
							</div>
						</td>
					</tr>
				</table>
				<table class="ResourceContainer">
		            <tr>
		                <td class="ResourceColumn">
							<div class="ResourceWrapper" style="width:505px;">
								<div id="network_traffic1"></div>
							</div>
						</td>
					</tr>
				</table>
				
			</td>
			<td style="vertical-align:top">
				<table class="ResourceContainer"><tr>
	                <td class="ResourceColumn">
						<div class="ResourceWrapper" style="width:505px;">
							<div class="HeaderBar">
								<h1>시스템별 트래픽</h1>
							</div>
							<div id="rsystem_ingress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="rsystem_chart"></div></div>
							<div id="rsystem_egress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="rsystem_chart1"></div></div>
							<table cellspacing="0px" cellpadding="2px">
							<colgroup> 
								<col width="5%" />
								<col width="5%" />
								<col width="" />
								<col width="" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
							</colgroup> 
								<thead style="table-layout:fixed;">
								<tr>
									<td style="text-align:center;"></td>
									<td style="text-align:center;"></td>
									<td style="text-align:center;">SYSTEM</td>
									<td style="text-align:center;">OUTSYSTEM</td>
									<td style="text-align:center;">INGRESS<BR>BYTES</td>
									<td style="text-align:center;">EGRESS<BR>BYTES</td>
									<td style="text-align:center;">INGRESS<BR>PACKETS</td>
									<td style="text-align:center;">EGRESS<BR>PACKETS</td>
								</tr>
								</thead>
							</table>
							<div id='rsystem_table'></div>
						</div>
					</td>
				</tr></table>
				<script type="text/javascript">
					function drawReciSystem(){
				        var chart1 = new FusionCharts("/charts/Pie3D.swf", "ChId2", "500", "250", "0", "0");
				        chart1.setDataURL(encodeURIComponent("/system_ct.do?view_type=RECI&show=i"));
				        chart1.render("rsystem_chart");
				        
				        var chart2 = new FusionCharts("/charts/Pie3D.swf", "ChId2", "500", "250", "0", "0");
				        chart2.setDataURL(encodeURIComponent("/system_ct.do?view_type=RECI&show=e"));
				        chart2.render("rsystem_chart1");
			        }
	
					function getReciSystem_tb(){
						$.ajax({        
							type : "POST",  
							url : "system_tb.do",   
							data : "view_type=RECI",
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
							success: function(data) { 
								if(data != null)  {
									document.getElementById('rsystem_table').innerHTML = data;
								}    
							}  
						});
					 }
				</script>
			
				<table class="ResourceContainer"><tr>
	                <td class="ResourceColumn">
						<div class="ResourceWrapper" style="width:505px;">
							<div class="HeaderBar">
								<h1>서비스별 트래픽</h1>
							</div>
							<div id="rservice_ingress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="rservice_chart"></div></div>
							<div id="rservice_egress" style="border:solid 1px #BBBABA;margin-bottom:3px"><div id="rservice_chart1"></div></div>
							<table cellspacing="0px" cellpadding="2px">
							<colgroup> 
								<col width="5%" />
								<col width="5%" />
								<col width="" />
								<col width="" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
								<col width="15%" />
							</colgroup> 
								<thead style="table-layout:fixed;">
								<tr>
									<td style="text-align:center;"></td>
									<td style="text-align:center;"></td>
									<td style="text-align:center;">SERVICE</td>
									<td style="text-align:center;">OUTSYSTEM</td>
									<td style="text-align:center;">INGRESS<BR>BYTES</td>
									<td style="text-align:center;">EGRESS<BR>BYTES</td>
									<td style="text-align:center;">INGRESS<BR>PACKETS</td>
									<td style="text-align:center;">EGRESS<BR>PACKETS</td>
								</tr>
								</thead>
							</table>
							<div id='rservice_table'></div>
						</div>
					</td>
				</tr></table>
				<script type="text/javascript">
			       	function drawReciService() {
						var chart3 = new FusionCharts("/charts/ZoomLine.swf", "ChId2", "500", "350", "0", "0");
						chart3.setDataURL(encodeURIComponent("/service_ct.do?view_type=RECI&show=i"));
						chart3.render("rservice_chart");
				        
				        var chart4 = new FusionCharts("/charts/ZoomLine.swf", "ChId2", "500", "350", "0", "0");
				        chart4.setDataURL(encodeURIComponent("/service_ct.do?view_type=RECI&show=e"));
				        chart4.render("rservice_chart1");
					}
			        
			        function getReciService_tb(){
						$.ajax({        
							type : "POST",  
							url : "service_tb.do",    
							data : "view_type=RECI",
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
							success: function(data) { 
								if(data != null)  {
									document.getElementById('rservice_table').innerHTML = data;
								}    
							}  
						});
					 }
			    </script>
			
			</td>
			</tr></table>
						
	    </div>
	
		<%@ include file="../include/footer.jsp"%>
	
	</div>

</body>
</html>