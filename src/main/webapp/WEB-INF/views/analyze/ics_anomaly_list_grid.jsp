<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta charset="UTF-8">
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">

<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
<%@ include file="/WEB-INF/views/include/css/reset_css.jsp"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<%@ include file="/WEB-INF/views/include/css/style_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/policy2_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/white_ics_css.jsp"%>
<style>.switch{width:38px;height:21px;}.slider:before{height:17px;width:17px;}</style>

<link rel="stylesheet" href="/css/DevExtreme/dx.generic.custom-swatch.css" />
<link rel="stylesheet" href="/css/DevExtreme/dx.light_Custom.css" />
<%@ include file="/WEB-INF/views/include/css/table_css.jsp"%>

<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/rsa/jsbn.js"></script>
<script type="text/javascript" src="/js/rsa/rsa.js"></script>
<script type="text/javascript" src="/js/rsa/prng4.js"></script>
<script type="text/javascript" src="/js/rsa/rng.js"></script>

<!-- <script type="text/javascript" src="/js/html5shiv.js"></script> -->
<script type="text/javascript" src="/js/main.js"></script>

<script type="text/javascript" src="<c:url value="/js/polyfill.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/DevExtreme/dx.all.js"/>"></script>

<script type="text/javascript">
$(document).ready(function() {
	$.ajaxSetup({ cache: false });//ajax 캐시 사용안함
	
	var stat_ps_inout = "${stat_ps_inout}"; 
	console.log(stat_ps_inout);
	var icsdataGrid = $('#icsDataContainer').dxDataGrid({
		remoteOperations: {
            paging: true,
            filtering: true,
            sorting: true
        },
		dataSource: new DevExpress.data.CustomStore({
			key: 'ps_id',
            load: function (loadOptions) {
                var params = {
                        sdateTime: $("#startDateBox").dxDateBox('instance').option('value'),
                        edateTime: $("#endDateBox").dxDateBox('instance').option('value'),
                        skip: loadOptions.skip || 0,
                        take: loadOptions.take || 20,
                        sort: loadOptions.sort && loadOptions.sort.length > 0 ? JSON.stringify(loadOptions.sort) : null,
                        filter: loadOptions.filter && loadOptions.filter.length > 0 ? JSON.stringify(loadOptions.filter) : null, 
                };
                return $.getJSON("/ics_anomaly_list_data.do", params)
                .then(function (response) {
        			return {
                        data: response.data,
                        totalCount: response.totalCount
                    };
                }).fail(function () {
                    return {
                        data: [],
                        totalCount: 0
                    };
                });           
            }
		}),
		paging: {
            pageSize: 20 // 한 페이지에 표시할 데이터 수
        },
        wordWrapEnabled: true,
		showBorders: true,
		showRowLines: true,
		showColumnLines: false,
		width: '100%',
		filterRow: {
			visible: false,
		},
		headerFilter: {
		    visible: true,
		    texts: {
				emptyValue: 'No data to display',
		    },
		},
		selection: {
			mode: 'single', 
// 			showCheckBoxesMode:'always',
// 			selectAllMode:'page',
			deferred: false, 
		},
		sorting: {
            mode: 'multiple' // 다중 정렬 허용
        },
		columns: [{
			dataField: 'wl_name',
			caption: '서비스',
			alignment: 'center',
// 			dataType: 'string',
			width: '10%',
			cellTemplate: function(container, cellInfo) {
				const valueDiv = $("<div>").text(cellInfo.value || "\u200B")// 빈 문자열일 경우 유니코드 공백 추가
				
				if (cellInfo.data.ps_detect == 1) {
					valueDiv.addClass("dotRisk1");
				} else if(cellInfo.data.ps_detect == 0){
					valueDiv.addClass("dotRisk3");
					container.parent().addClass("fail");
				}
				else if(cellInfo.data.ps_detect == 2){
					valueDiv.addClass("dotRisk4");
					container.parent().addClass("fail");
				}
				
				return valueDiv;
			},
			headerFilter: {
                search: {
					enabled: true,
					editorOptions: {
						onValueChanged: (e) => {
                            console.log(e);
                        } 
					},
				},
            },
		}, {
			dataField: 'ps_inout',
		    caption: '구분',
		    alignment: 'center',
		    width: '10%',
		    lookup: {
		        dataSource: [
		            { value: 0, display: 'Outbound' },
		            { value: 1, display: 'Inbound' }
		        ],
		        valueExpr: 'value',
		        displayExpr: 'display'
		    },
		    headerFilter: {
		    	dataSource: [
		            { value: 0, text: 'Outbound' },
		            { value: 1, text: 'Inbound' }
		        ],
		        valueExpr: 'value',
		        displayExpr: 'text'
			},
			filterValues: stat_ps_inout ? (stat_ps_inout === '0' ? [0] : [1]) : null,
		}, {
            caption: '접근제어',
            alignment: 'center',
            columns: [{
	            dataField: 'ps_detect',
	            caption: '탐지',
	            alignment: 'center',
	            width: '8%',
	            lookup: {
	                dataSource: [
	                    { value: 0, text: "차단" },
	                    { value: 1, text: "허용" },
	                    { value: 2, text: "악성코드" }
	                ],
	                valueExpr: 'value',
	                displayExpr: 'text'
	            },
	            headerFilter: {
	            	search: { enabled: true, },
	            	dataSource: [
	                    { value: 0, text: "차단" },
	                    { value: 1, text: "허용" },
	                    { value: 2, text: "악성코드" }
	                ],
	                valueExpr: 'value',
	                displayExpr: 'text'
	            },
			},{
				dataField: 'ps_detect_desc',
				caption: '탐지내용',
				alignment: 'center',
// 				dataType: 'string',
				width: '12%',
				headerFilter: {
	                search: {
						enabled: true,
					},
	            }, 
			}],
		}, {
			caption: '출발지',
			alignment: 'center',
			columns: [{
				dataField: 'ps_src_ip',
				caption: 'IP',
				alignment: 'center',
// 				dataType: 'string',
				width: '15%',
				headerFilter: {
	                search: {
						enabled: true,
					},
	            }, 
			},{
				dataField: 'ps_src_port',
				caption: 'Port',
				alignment: 'center',
// 				dataType: 'string',
				width: '10%',
				headerFilter: {
	                search: {
						enabled: true,
					},
	            }, 			
			}],
		}, {
			caption: '목적지',
			alignment: 'center',
			columns: [{
				dataField: 'ps_dst_ip',
				caption: 'IP',
				alignment: 'center',
// 				dataType: 'string',
				width: '15%',
				headerFilter: {
	                search: {
						enabled: true,
					},
	            }, 	
			},{
				dataField: 'ps_dst_port',
				caption: 'Port',
				alignment: 'center',
// 				dataType: 'string',
				width: '10%',
				headerFilter: {
	                search: {
						enabled: true,
					},
	            }, 	
			}],
		},{
			dataField: 'ps_strc_time',
			caption: '통신시간',
			alignment: 'center',
// 			dataType: 'date',
			format: 'yyyy-MM-dd HH:mm:ss',
			width: '10%',
			headerFilter: {
                search: {
					enabled: true,
				},
            }, 	
		}],
	}).dxDataGrid('instance');
	
	// 시작 날짜 DateBox
	const startDateBox = $("#startDateBox").dxDateBox({
		type: "datetime",
		name: "sdateTime", 
		displayFormat: "yyyy-MM-dd HH:mm",
		dateSerializationFormat: 'yyyy-MM-dd HH:mm',
// 		value: new Date(new Date().getTime() - 60 * 60 * 1000), // 기본값: 현재 시간 - 1시간
		value: "${sdateTime}", 
		onContentReady: function(e) {
			e.element.find('input').attr('readonly', true);
		},
	}).dxDateBox("instance");

	// 종료 날짜 DateBox
	const endDateBox = $("#endDateBox").dxDateBox({
		type: "datetime",
		name: "edateTime", 
		displayFormat: "yyyy-MM-dd HH:mm",
		dateSerializationFormat: 'yyyy-MM-dd HH:mm',
// 		value: new Date(), // 기본값: 현재 시간 
		value: "${edateTime}", 
		onContentReady: function(e) {
			e.element.find('input').attr('readonly', true);
		},
	}).dxDateBox("instance");
    
	var dateTab = ${dateTab};
	
	const tabOptions = [
		{ text: "1시간", value: 1 },
		{ text: "6시간", value: 6 },
		{ text: "12시간", value: 12 },
	];
    
	$("#tabContainer").dxButtonGroup({
		items: tabOptions,
		selectionMode: "single",
		selectedItemKeys: [dateTab], // 초기 선택값: 1시간
		elementAttr: {
			class: "dateTabDiv",
			name: "dateTab", 
		},
		onItemClick: function(e) {
			setDateRange(e.itemData.value);
			$("#tabContainer .dx-button").removeClass("tabSelect");
			
			$(e.itemElement).addClass("tabSelect");
		},
		onContentReady: function(){
			$("#tabContainer .dx-button").removeClass("tabSelect");
		    
		    $("#tabContainer .dx-button").each(function(index, button) {
		        if (tabOptions[index].value === dateTab) {
		            $(button).addClass("tabSelect");
		        }
		    });
		},
	});
	
	const searchBtn = document.createElement("div");
	searchBtn.id = "searchBtn";
	searchBtn.className = "searchBtn";
	
	const searchIcon = document.createElement("i");
	searchIcon.className = "fa fa-search";
	searchIcon.style.margin = "0px 8px 0px 0px";
	
	searchBtn.appendChild(searchIcon);
	searchBtn.appendChild(document.createTextNode("검색"));
	searchBtn.onclick = function () {
		var selectedTabValue = $("#tabContainer").dxButtonGroup("instance").option("selectedItems")[0].value;  
		
		loadList(selectedTabValue);
	};
	const searchContainer = document.getElementById("search");
	searchContainer.appendChild(searchBtn);

	function setDateRange(hours) {
		const currentDate = new Date();
		const startDate = new Date(currentDate.getTime() - hours * 60 * 60 * 1000);
		
		startDateBox.option("value", startDate);
		endDateBox.option("value", currentDate);
	}
      
	
	function loadList(selectedTabValue){
		const startDate = startDateBox.option("value");
		const endDate = endDateBox.option("value");
		
		if (new Date(startDate) > new Date(endDate)) {
			alert("시작 시간이 종료 시간보다 늦을 수 없습니다.");
			return;
		}
		
		var form = document.getElementById("frmPs");
		
		var dateTabInput = document.createElement('input');
		dateTabInput.setAttribute("type", "hidden");
		dateTabInput.setAttribute("name", "dateTab");
		dateTabInput.setAttribute("value", selectedTabValue);
		form.appendChild(dateTabInput);
		
		form.action="ics_anomaly_list_grid.do";
		form.submit();
	}
});
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	
	<section id="content">
		<!-- left menu start -->
		<%@ include file="../include/left.jsp"%>
		<!-- left menu end -->
		<div id="right-content">
			<form id="frmPs" name="frmPs">
				<div class="policyTopDiv">
<!-- 					<div onclick="openRegstForm()" class="policyBtn"><i class="fa fa-upload" style="margin:0px 8px 0 0;"></i> 접근제어 정책 등록</div> -->
					<div id="search" class="searchDiv">
						<span class="dateSpan">조회 날짜 </span>
						<div id="startDateBox" class="dateBox"></div>
						<div class="dateSeparator">~</div>
						<div id="endDateBox" class="dateBox endDateBox"></div>
						<div id="tabContainer" class="tabContainer"></div>
						<div class="vertical_line"></div>
					</div>
				</div>
				
<!-- 				<div class="date_div"> -->
<!-- 					<label for="date">조회 날짜 </label> -->
<%-- 					<input type="text" id="date" name="date" class="input date" value="${date}" onchange="loadList();"> --%>
<!-- 					<div class="vertical_line"></div> -->
<!-- 					 -->
<!-- 				</div> -->
<%-- 				<input type="hidden" id="mps_strc_stime" name="mps_strc_stime" value="${date} 00:00:00" /> --%>
<%-- 				<input type="hidden" id="mps_strc_etime" name="mps_strc_etime" value="${date} 23:59:59" /> --%>
			</form>
			<div id="icsDataContainer"></div>
		</div>
	</section>
	
	<%@ include file="../include/footer.jsp"%>
	
	<div id="regstWhiteListInGroup" class="popup" style="display:none;">
		<div class="bg"></div>
		<div class="pop-wrapper">
			<div class="header_bar">
				<h1>접근제어 정책 등록</h1>
				<img src="/img/close.png" onclick="closeRegstForm()">
			</div>
			<form id="groupWhiteListForm" class="registForm">
				<ul>
					<li>
						<div class="li_label"><label for="ngl_group_name">그룹명</label></div>
						<div class="li_select">
							<select class="pop_select" name="ngl_id" id="ngl_id">
								<c:forEach var="group" items="${groupList}">
									<option value="${group.ngl_id}">${group.ngl_group_name}</option>
								</c:forEach>
	                     	</select>
                     	</div>
					</li>
				</ul>
				<div class="btnDiv">
					<div onclick="closeRegstForm()" class="cancelBtn"><img src="/img/cancel.png"><spring:message code='btn.cancel2'/></div>
					<div onclick="registerForm()" class="registBtn"><img src="/img/btnicon1.png"><spring:message code='btn.registration2'/></div>
				</div>
			</form>
		</div>
	</div>
	
	<%@ include file="whiteFormPopup.jsp"%>
</body>

<script type="text/javascript">
	var selectedRowPsId = [];
	
	function openRegstForm(){
		var selectedRowsData = $('#icsDataContainer').dxDataGrid('instance').getSelectedRowsData();
		if (selectedRowsData.length == 0) {
			alert("선택된 행이 없습니다.");
			return;
		}
		
		else if (selectedRowsData.length > 0) {
			if(selectedRowsData[0].ps_detect == 1){
				alert("선택된 행은 이미 등록된 정책입니다.");
				return;
			}
			if(selectedRowsData[0].ps_detect == 2){
				alert("선택된 행은 악성코드가 탐지된 상태입니다.");
				return;
			}
			
			document.getElementById("whiteForm").reset();
			
			$("#ngl_id option:eq(0)").prop("selected", true);
			selectFirst($("#ngl_id").val());
			
			$("#whiteFormPopup").fadeIn();
			
	        var selectedRow = selectedRowsData[0];

	        $("#wl_proto_sub_dec").val(selectedRow.ps_proto_sub_dec);
	        if(selectedRow.strPsInOut == "Outbound"){
	        	$("#ps_inout option:eq(0)").prop("selected", true);
	        	$("#wl_dst_port_type").attr("readonly", true).addClass("readonly-select");
				$("#wl_src_port_type").attr("readonly", false).removeClass("readonly-select");
	        }
	        else{
	        	$("#ps_inout option:eq(1)").prop("selected", true);
	        	$("#wl_src_port_type").attr("readonly", true).addClass("readonly-select");
				$("#wl_dst_port_type").attr("readonly", false).removeClass("readonly-select");
	        }
	        
	        $("#wl_src_ip").val(selectedRow.ps_src_ip);
	        	$("#wl_src_port").val(selectedRow.ps_src_port);
	        
	        $("#wl_dst_ip").val(selectedRow.ps_dst_ip);
	       
	        	$("#wl_dst_port").val(selectedRow.ps_dst_port);
		}
	}
	
	function closeRegstForm() {
		$("#regstWhiteListInGroup").fadeOut();
		$('#groupWhiteListForm')[0].reset();
		$("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
		$("#chk_src_port").prop("checked", false);
		$("#chk_src_port").attr("disabled", false);
		$("#wl_dst_port").attr("readonly", false).removeClass("readonly-disabled");
		$("#chk_dst_port").prop("checked", false);
		$("#chk_dst_port").attr("disabled", false);
	}
	
	function registerForm(){
		selectedRowPsId = [];
		
		var selectedRowsData = $('#icsDataContainer').dxDataGrid('instance').getSelectedRowsData();
		
		if (selectedRowsData.length === 0) {
	        alert("선택된 정책이 없습니다.");
	        return;
	    }
		else{
			for(let i=0;i<selectedRowsData.length;i++){
				selectedRowPsId.push(selectedRowsData[i].ps_id);
			}
			if(selectedRowPsId.length > 0){
				if(confirm("접근제어 정책을 등록 하시겠습니까?")) {
					var ngl_id = $("#ngl_id option:selected").val();
					var date = $("#date").val();
					$.ajax({
						url : "regst_checked_white_list.do",
						type: "POST", 
						data : { selectedRowPsId : selectedRowPsId, ngl_id : ngl_id, date : date },
						dataType : 'json',
						traditional: true,	//ajax로 배열 넘기기 옵션
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success: function(data) {
							if(data.regCnt > 0){
								alert(data.regCnt+"개의 정책이 등록되었습니다.");
 								location.href="/ics_anomaly_list_grid.do?date=${date}";
							}
							else if(data.duplicateCnt > 0){
								alert(data.regCnt+"개의 정책이 이미 등록되어 있습니다.");
							}
							else {
								alert("등록에 실패하였습니다.");
							}
						},
						error: function(){
							alert('등록 작업 중 에러가 발생하였습니다.');
						}
					});
				}
			}
		}
	}

	
</script>
</html>
