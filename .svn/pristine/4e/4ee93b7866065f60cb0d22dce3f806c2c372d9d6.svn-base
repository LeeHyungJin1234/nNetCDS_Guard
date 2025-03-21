<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="whiteFormPopup" class="popup" style="display:none;">
	<div class="bg"></div>
	<div class="pop-wrapper">
		<div class="header_bar">
			<h1>접근제어 정책 등록</h1>
			<img src="/img/close.png" onclick="closeWhiteForm()">
		</div>
		<form id="whiteForm" class="registForm">
			<input type="hidden" name="wl_proto_sub_dec" id="wl_proto_sub_dec" />
			<ul>
				<li>
					<div class="li_label"><label for="ngl_group_name">그룹명</label></div>
					<div class="li_select">
						<select class="pop_select" name="ngl_id" id="ngl_id" onchange="selectFirst(this.options[this.selectedIndex].value)">
							<c:forEach var="group" items="${groupList}">
								<option value="${group.ngl_id}">${group.ngl_group_name}</option>
							</c:forEach>
							<option value="0">직접 입력</option>
                     	</select>
                     	<div id="ngl_group_div"></div>
                    	</div>
				</li>
				<li>
					<div class="li_label"><label for="ps_inout">IN/ OUT</label></div>
					<div class="li_select">
						<select class="pop_select" name="ps_inout" id="ps_inout">
	                       	<option value="0">Outbound</option>
	                       	<option value="1">Inbound</option>
                     	</select>
                   	</div>
				</li>
				<li>
					<div class="li_label"><label for="wl_src_ip">출발지 Host</label></div>
					<div class="li_input li_input_flex">
						<select class="pop_select" name="wl_src_host_type" id="wl_src_host_type">
							<c:forEach var="host_type" items="${hostTypeList}">
								<option value="${host_type.nht_id}">${host_type.nht_name}</option>
							</c:forEach>
                     	</select>
						<input class="frmTxt" type="text" name="wl_src_ip" id="wl_src_ip" value="" />
						<div id="srcIpSeperator" style="display: none;">~</div>
						<input class="frmTxt" type="text" name="wl_src_eip" id="wl_src_eip" value="" style="display: none;"/>
					</div>
				</li>
				<li>
					<div class="li_label"><label for="wl_src_port">출발지 Port</label></div>
					<div class="li_input li_input_flex">
						<select class="pop_select" name="wl_src_port_type" id="wl_src_port_type">
	                       	<option value="0">단일 Port</option>
	                       	<option value="1">Any</option>
                     	</select>
						<input class="frmTxt" type="text" name="wl_src_port" id="wl_src_port" value="" />
					</div>
				</li>
				<li>
					<div class="li_label"><label for="wl_dst_ip">도착지 Host</label></div>
					<div class="li_input li_input_flex">
						<select class="pop_select" name="wl_dst_host_type" id="wl_dst_host_type">
	                       	<c:forEach var="host_type" items="${hostTypeList}">
								<option value="${host_type.nht_id}">${host_type.nht_name}</option>
							</c:forEach>
                     	</select>
						<input class="frmTxt" type="text" name="wl_dst_ip" id="wl_dst_ip" value="" />
						<div id="dstIpSeperator" style="display: none;">~</div>
						<input class="frmTxt" type="text" name="wl_dst_eip" id="wl_dst_eip" value="" style="display: none;"/>
					</div>
				</li>
				<li>
					<div class="li_label"><label for="wl_dst_port">도착지 Port</label></div>
					<div class="li_input li_input_flex">
						<select class="pop_select" name="wl_dst_port_type" id="wl_dst_port_type">
	                       	<option value="0">단일 Port</option>
	                       	<option value="1">Any</option>
                     	</select>
						<input class="frmTxt" type="text" name="wl_dst_port" id="wl_dst_port" value="" />
					</div>
				</li>
			</ul>
			<div class="btnDiv">
				<div onclick="closeWhiteForm()" class="cancelBtn"><img src="/img/cancel.png"><spring:message code='btn.cancel2'/></div>
				<div onclick="registerWhiteList()" class="registBtn"><img src="/img/btnicon1.png"><spring:message code='btn.registration2'/></div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	function openWhiteForm() {
		$("#whiteFormPopup").fadeIn();
		
		$("#ngl_id option:eq(0)").prop("selected", true);
		selectFirst($("#ngl_id").val());
		
		$("#wl_proto_sub_dec").val("");
		
		$("#ps_inout option:eq(0)").prop("selected", true);
		if($("#ps_inout").val() === "0"){
			$("#wl_dst_port_type option:eq(0)").prop("selected",true);
			$("#wl_dst_port_type").attr("readonly", true).addClass("readonly-select");
			$("#wl_src_port_type").attr("readonly", false).removeClass("readonly-select");
		}
		else{
			$("#wl_src_port_type option:eq(0)").prop("selected",true);
        	$("#wl_src_port_type").attr("readonly", true).addClass("readonly-select");
			$("#wl_dst_port_type").attr("readonly", false).removeClass("readonly-select");
		}
		
		$("#wl_src_host_type option:eq(0)").prop("selected", true);
		$("#wl_src_ip").css("width", "300px");
		$("#srcIpSeperator").css("display", "none");
		$("#wl_src_eip").css("display", "none");
		$("#wl_src_ip").val("");
		
		$("#wl_src_port").val("");
		$("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
		$("#wl_src_port_type option:eq(0)").prop("selected", true);
		
		$("#wl_dst_host_type option:eq(0)").prop("selected", true);
		$("#wl_dst_ip").css("width", "300px");
		$("#dstIpSeperator").css("display", "none");
		$("#wl_dst_eip").css("display", "none");
		$("#wl_dst_ip").val("");
		
		$("#wl_dst_port").val("");
		$("#wl_dst_port").attr("readonly", false).removeClass("readonly-disabled");
		$("#wl_dst_port_type option:eq(0)").prop("selected", true);
	}
	
	const pathname = window.location.pathname;
	$("#ps_inout").change(function(){
		var selectedValue = $(this).val(); 
		
		if (selectedValue === "0") { 
			$("#wl_dst_port_type option:eq(0)").prop("selected",true);
			$("#wl_dst_port_type").attr("readonly", true).addClass("readonly-select");
			$("#wl_src_port_type").attr("readonly", false).removeClass("readonly-select");
            $("#wl_dst_port").attr("readonly", false).removeClass("readonly-disabled");
            if(pathname.includes("white_ics")){
            	$("#wl_dst_port").val("");	
            }
        } else if (selectedValue === "1") { 
        	$("#wl_src_port_type option:eq(0)").prop("selected",true);
        	$("#wl_src_port_type").attr("readonly", true).addClass("readonly-select");
			$("#wl_dst_port_type").attr("readonly", false).removeClass("readonly-select");
            $("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
            if(pathname.includes("white_ics")){
            	$("#wl_src_port").val("");	
            }
        } 
	});
	
	$("#wl_src_host_type").change(function(){
		if(pathname.includes("white_ics")){
			$("#wl_src_port").val("");
        }
		
		var selectedValue = $(this).val();
		if(selectedValue === "2"){
			$("#wl_src_ip").css("width", "140px");
			$("#srcIpSeperator").css("display", "inline");
			$("#srcIpSeperator").css("margin", "0 4.5px");
			$("#wl_src_eip").css("display", "inline");
			$("#wl_src_eip").css("width", "140px");
		}
		else {
			$("#wl_src_ip").css("width", "300px");
			$("#srcIpSeperator").css("display", "none");
			$("#wl_src_eip").css("display", "none");
		}
	});
	$("#wl_dst_host_type").change(function(){
		if(pathname.includes("white_ics")){
			$("#wl_dst_port").val("");
        }
		
		var selectedValue = $(this).val();
		if(selectedValue === "2"){
			$("#wl_dst_ip").css("width", "140px");
			$("#dstIpSeperator").css("display", "inline");
			$("#dstIpSeperator").css("margin", "0 4.5px");
			$("#wl_dst_eip").css("display", "inline");
			$("#wl_dst_eip").css("width", "140px");
		}
		else {
			$("#wl_dst_ip").css("width", "300px");
			$("#dstIpSeperator").css("display", "none");
			$("#wl_dst_eip").css("display", "none");
		}
	});
	
	$("#wl_src_port_type").change(function(){
		if(pathname.includes("white_ics")){
			$("#wl_src_port").val("");
        }
		
		if($("#wl_src_port_type option:eq(1)").is(":selected")){
			$("#wl_src_port").attr("readonly", true).addClass("readonly-disabled");
		}
		else{
			$("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
		}
	});
	$("#wl_dst_port_type").change(function(){
		if(pathname.includes("white_ics")){
			$("#wl_dst_port").val("");
        }
		
		if($("#wl_dst_port_type option:eq(1)").is(":selected")){
			$("#wl_dst_port").attr("readonly", true).addClass("readonly-disabled");
		}
		else{
			$("#wl_dst_port").attr("readonly", false).removeClass("readonly-disabled");
		}
	});
	
	function closeWhiteForm() {
		$("#whiteFormPopup").fadeOut();
	}
	
	function registerWhiteList(){
		var form = document.getElementById("whiteForm"); 
		
		if(form.ngl_id.value == 0){
			if(form.ngl_group_name.value == ""){
				alert("그룹명을 입력하세요.");
				form.ngl_group_name.focus();
				return false;
			}
		}
		
		// 출발지 검증
	    if (!validateHost("wl_src_host_type", "wl_src_ip", "wl_src_eip")) return false;

	    // 출발지 포트 검증
	    if (!validatePort("wl_src_port_type", "wl_src_port")) return false;

	    // 도착지 검증
	    if (!validateHost("wl_dst_host_type", "wl_dst_ip", "wl_dst_eip")) return false;

	    // 도착지 포트 검증
	    if (!validatePort("wl_dst_port_type", "wl_dst_port")) return false;

		if (confirm("접근제어 정책을 등록 하시겠습니까?") == true) {
			$.ajax({
				url : "regst_white_list.do",
				data :  $("#whiteForm").serialize(), 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					if(data == "success"){
						alert("정책을 등록하였습니다.");
						 location.href="/white_ics.do";
					}
					else if(data == "same_group"){
						alert("이미 등록된 그룹 정보 입니다.")
					}
					else if(data == "same_white_list"){
						alert("이미 등록된 정책 입니다.")
					}
					else{
						alert(data);
					}
				},
				error: function(){
					alert('등록 작업 중 에러가 발생하였습니다.');
				}
			});
		}
	}
	
	function ipToNumber(ip) {
	    return ip.split('.').reduce((acc, octet) => (acc << 8) + parseInt(octet, 10), 0);
	}
	
	function validateHost(hostTypeId, ipFieldId, endIpFieldId) {
	    const hostType = document.getElementById(hostTypeId).value;
	    const ipField = document.getElementById(ipFieldId);
	    const endIpField = document.getElementById(endIpFieldId);

		//const domain_check = /^[a-zA-Z0-9.]{1,64}$/;

		if (hostType === "2") { // IP 대역
	        if (!validateIp(ipField) || !validateIp(endIpField)) return false;
			
	        const startIpNumber = ipToNumber(ipField.value);
	        const endIpNumber = ipToNumber(endIpField.value);
			
	        if (startIpNumber > endIpNumber) {
	            alert("시작 IP가 끝 IP보다 클 수 없습니다.");
	            endIpField.focus();
	            return false;
	        }
	    } 
		else { // 단일 IP
	        if (!validateIp(ipField)) return false;
	    } 
	    return true;
	}

	// IP 검증 함수
	function validateIp(field) {
		const ip_check = /^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$/;
		
	    if (field.value.trim() === "") {
	        alert("IP를 입력하세요.");
	        field.focus();
	        return false;
	    }
	    if (!ip_check.test(field.value)) {
	        alert("입력한 값이 IP 주소 형식이 아닙니다.");
	        field.focus();
	        return false;
	    }
	    return true;
	}

	// 포트 검증 함수
	function validatePort(portTypeId, portFieldId) {
	    const portType = document.getElementById(portTypeId).value;
	    const portField = document.getElementById(portFieldId);
	    
		const portRegex = /^[0-9]{1,5}$/;

	    if (portType === "1") {
	        // 'Any' 선택 시 처리
	        portField.value = "Any";
	    } else {
	        if (portField.value.trim() === "") {
	            alert("포트를 입력하세요.");
	            portField.focus();
	            return false;
	        }
	        if (!portRegex.test(portField.value) || Number(portField.value) < 0 || Number(portField.value) > 65535) {
	            alert("포트는 0부터 65535까지의 숫자만 입력 가능합니다.");
	            portField.focus();
	            return false;
	        }
	    }
	    return true;
	}
	
	function selectFirst(name){
		if(name == '0') {
			document.getElementById('ngl_group_div').innerHTML=
				'<div class="li_label"><label for="ngl_group_name">그룹명</label></div>' + 
				'<div class="li_input"><input class="frmTxt" type="text" name="ngl_group_name" id="ngl_group_name" value="" /></div>' + 
				'<div class="li_label"><label for="ngl_group_desc">설명</label></div>' + 
				'<div class="li_input"><input class="frmTxt" type="text" name="ngl_group_desc" id="ngl_group_desc" value="" /></div>';
		}
		else {
			document.getElementById('ngl_group_div').innerHTML='';
		}
	}
	
	function deleteWhiteList(){
		var selectedRowsData = $('#groupListGrid').dxDataGrid('instance').getSelectedRowsData();
		if (selectedRowsData.length === 0) {
	        alert("선택된 정책이 없습니다.");
	        return;
	    }
		else{
			var selectedRowWlId = [];
			for(let i=0;i<selectedRowsData.length;i++){
				selectedRowWlId.push(selectedRowsData[i].wl_id);
			}
			
			if(selectedRowWlId.length > 0){
				if(confirm("접근제어 정책을 삭제 하시겠습니까?")) {
					$.ajax({
						url : "delete_checked_white_list.do",
						data : { selectedRowWlId : selectedRowWlId },
						dataType : 'json',
						traditional: true,	//ajax로 배열 넘기기 옵션
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success: function(data) {
							if(data.delWhiteListCnt > 0){
								alert(data.delWhiteListCnt+"개의 정책이 삭제되었습니다.");
									location.href="/white_ics.do";
							}
						},
						error: function(){
							alert('삭제 작업 중 에러가 발생하였습니다.');
						}
					});
				}
			}
		}
	}
</script>