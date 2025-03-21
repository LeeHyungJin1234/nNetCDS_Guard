<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title><spring:message code='common.title'/></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="nNetCDS">

	<!-- Stylesheets -->
	<link rel="stylesheet" href="/css/DevExtreme/dx.generic.custom-swatch.css" />
	<link rel="stylesheet" href="/css/DevExtreme/dx.light_Custom.css" />
	<%@ include file="/WEB-INF/views/include/css/table_css.jsp"%>
	
	<!-- Scripts -->
	<script type="text/javascript" src="<c:url value="/js/polyfill.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/DevExtreme/dx.all.js"/>"></script>
	
	<script>
		$(document).ready(function() {
			$.ajaxSetup({ cache: false });
			var wl_name = "${wl_name}";
			
			var groupListDataGrid = $('#groupListGrid').dxDataGrid({
				dataSource: new DevExpress.data.CustomStore({
					load: function(){
						return $.getJSON("/getWhiteList.do").then(response => ({
                            data: response.data || [],
                            totalCount: response.totalCount || 0
                        })).fail(() => ({
                            data: [],
                            totalCount: 0
                        }));  
					}
				}),
				keyExpr: 'wl_name',
			    showBorders: true,
			    showRowLines: true,
				showColumnLines: false,
			    width: '100%',
			    height: 700,
				sorting: {
					mode: 'multiple',
				},
			    filterRow: {
					visible: false,
			    },
			    headerFilter: {
					visible: true,
			    },
				grouping: {
					autoExpandAll: true,
					expandMode: "rowClick"
				},
				groupPanel: {
					visible: false,
				},
				selection: {
					mode: 'multiple', 
					showCheckBoxesMode:'always',
					selectAllMode:'page',
					deferred: false, 
				},
			    scrolling: {
			        mode: 'virtual',
			    },
				noDataText: "No data",
				stateStoring: {
				    enabled: false,
				},
			    columns: [
					{
						dataField: 'wl_id',
						visible: 'false',
					},
				    {
						dataField: 'wl_name',
						caption: '정책명',
						dataType: 'String',
						alignment: 'center', 
						width: '20%',
// 						allowHeaderFiltering: false,
						headerFilter: {
							search: {
								enabled: true,
							},
						},
					}, 
					{
						dataField: 'wl_npl_id',
						visible: 'false',
					},
					{
						dataField: 'wl_npl_name',
						caption: '프로토콜',
						alignment: 'center',
						dataType: 'String',
						width: '10%',
						headerFilter: {
							search: {
								enabled: true,
							},
						},
					}, 
					{
						dataField: 'wl_nplv_name',
						caption: '등급',
						alignment: 'center',
						dataType: 'String',
						width: '8%',
					}, 
					{
						caption: '출발지',
						alignment: 'center',
						columns: [
							{
								dataField: 'wl_src_ip',
								caption: 'HOST',
								alignment: 'center',
								dataType: 'string',
								width: '10%',
								headerFilter: {
									search: {
										enabled: true,
									},
								},
							}, 
							{
								dataField: 'wl_src_port',
								caption: 'PORT',
								alignment: 'center',
								dataType: 'string',
								width: '9%',
								headerFilter: {
									search: {
										enabled: true,
									},
								}, 
							}
						],
		  			}, 
		  			{
						caption: '목적지',
						alignment: 'center',
						columns: [
							{
								dataField: 'wl_dst_ip',
								caption: 'HOST',
								alignment: 'center',
								dataType: 'string',
								width: '10%',
								headerFilter: {
									search: {
										enabled: true,
									},
								}, 
							}, 
							{
								dataField: 'wl_dst_port',
								caption: 'PORT',
								alignment: 'center',
								dataType: 'string',
								width: '9%',
								headerFilter: {
									search: {
										enabled: true,
									},
								}, 
							}
						],
		  			}, 
		  			{
						dataField: 'wl_regdttm',
						caption: '등록일',
						alignment: 'center',
						dataType: 'date',
						format: 'yyyy-MM-dd HH:mm:ss',
						width: '10%',
						headerFilter: {
							search: {
								enabled: true,
							},
						},
					}, 
					{
		                dataField: 'wl_use_yn',
		                caption: '사용 여부',
		                alignment: 'center',
		                width: '9%',
		                allowSorting: false,
		                lookup: {
		                    dataSource: [
		                        { value: 1, text: "ON" },
		                        { value: 0, text: "OFF" }
		                    ],
		                    valueExpr: "value",
		                    displayExpr: "text"
		                },
		                headerFilter: {
		                    dataSource: [
		                        { text: "ON", value: 1 },
		                        { text: "OFF", value: 0 }
		                    ]
		                },
		                cellTemplate: function(container, options) {
		                	const isVisible = options.data && options.data.wl_name;
		                    if (isVisible) {
			                    const isChecked = options.data.wl_use_yn === 1;
			                    $("<div>")
			                        .dxSwitch({
			                            value: isChecked,
			                            onValueChanged: function(e) {
			                                const newValue = e.value ? 1 : 0;
			                                $.ajax({
			                                    url: '/updateWlUseYn.do',
			                                    data: {
			                                        wl_name: options.data.wl_name,
			                                        wl_id: options.data.wl_id,
			                                        wl_use_yn: newValue
			                                    },
			                                    success: function(data) {
													if(data > 0){
														DevExpress.ui.notify({
										                    message: "상태가 변경되었습니다.",
										                    type: "success",
										                    displayTime: 500,
										                    width: 230,
										                    position: {
										                        my: "center",
										                        at: "center",
										                        of: "#serviceListGrid"
										                    }
										                });
														
														$('#groupListGrid').dxDataGrid('instance').refresh();
													} else{
														DevExpress.ui.notify({
										                    message: "상태 변경 중 오류가 발생했습니다.",
										                    type: "error",
										                    displayTime: 500,
										                    width: 230,
										                    position: {
										                        my: "center",
										                        at: "center",
										                        of: "#groupListGrid"
										                    }
										                });
													}
			                                    },
			                                    error: function() {
			                                    	DevExpress.ui.notify({
									                    message: "상태 변경 중 오류가 발생했습니다.",
									                    type: "error",
									                    displayTime: 500,
									                    width: 230,
									                    position: {
									                        my: "center",
									                        at: "center",
									                        of: "#groupListGrid"
									                    }
									                });
			                                    }
			                                });
			                            },
			                        })
			                        .appendTo(container);
		                    } else{
		                    	$("<div>").appendTo(container);
		                    }
		                }
					},
					{
						type: 'buttons',
						caption: '수정',
						width: '5%',
						buttons: [{
							hint: "수정",
							icon: "edit",
							visible: function(e) {
				                return e.row && e.row.data && e.row.data.wl_name; 
				            },
							onClick: function(e){
								if(e.row.data.wl_use_yn == 1){
									alert("사용중인 정책은 수정할 수 없습니다.");
								}
								else{
									editRow(e.row.data);
								}
							}
						}],
					},
	  			],
			}).dxDataGrid('instance');
		});
		
// 		function expandGroupByName(ngl_group_name) {
// 		    var groupListDataGrid = $('#groupListGrid').dxDataGrid('instance');
		    
// 		    groupListDataGrid.on("contentReady", function() {
// 			    groupListDataGrid.getDataSource().store().load().done(function(data) {
// 			        let groupFound = false;
// 			        for (let i = 0; i < data.data.length; i++) {
// 			        	if (data.data[i].ngl_group_name === ngl_group_name) {
// 		                    groupListDataGrid.expandRow([data.data[i].ngl_group_name]).done(function() {
// 		                    }).fail(function() {
// 		                        alert("Failed to expand group:", ngl_group_name);
// 		                    });
// 		                    groupFound = true;
// 		                    break;
// 		                }
// 			        }
		
// 			        if (!groupFound) {
// 			            alert("No matching group found for ngl_group_name:", ngl_group_name);
// 			        }
// 			    }).fail(function(error) {
// 			        alert("Error loading data:", error);
// 			    });
		    	
// 		        groupListDataGrid.off("contentReady");
// 		    });
// 		}
	
// 		function deleteGroup(groupName){
// 			if(confirm("그룹을 삭제하시면 등록된 정책들도 같이 삭제됩니다. 삭제 하시겠습니까?")) {
// 				$.ajax({
// 					url : "delete_group.do?groupName="+ groupName,
// 					traditional: true,	//ajax로 배열 넘기기 옵션
// 					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
// 					success: function(data) {
// 						if(data.delGroupCnt > 0){
// 							alert(data.groupname+" 그룹과 "+ data.delWhiteListCnt+"개의 접근제어 정책이 삭제되었습니다.");						
// 							location.href="/white_ics.do";
// 						}
// 						else{
// 							alert(data);
// 						}
// 					},
// 					error: function(){
// 						alert('삭제 작업 중 에러가 발생하였습니다.');
// 					}
// 				});
// 			}
// 		}

// 		$("#wl_src_host_type").change(function(){
// 			var selectedValue = $(this).val();
// 			if(selectedValue === "2"){
// 				$("#wl_src_ip").css("width", "129px");
// 				$("#srcIpSeperator").css("display", "inline");
// 				$("#srcIpSeperator").css("margin", "0 4.5px");
// 				$("#wl_src_eip").css("display", "inline");
// 				$("#wl_src_eip").css("width", "129px");
// 			}
// 			else {
// 				$("#wl_src_ip").css("width", "279px");
// 				$("#srcIpSeperator").css("display", "none");
// 				$("#wl_src_eip").css("display", "none");
// 			}
// 		});
// 		$("#wl_dst_host_type").change(function(){
// 			var selectedValue = $(this).val();
// 			if(selectedValue === "2"){
// 				$("#wl_dst_ip").css("width", "129px");
// 				$("#dstIpSeperator").css("display", "inline");
// 				$("#dstIpSeperator").css("margin", "0 4.5px");
// 				$("#wl_dst_eip").css("display", "inline");
// 				$("#wl_dst_eip").css("width", "129px");
// 			}
// 			else {
// 				$("#wl_dst_ip").css("width", "279px");
// 				$("#dstIpSeperator").css("display", "none");
// 				$("#wl_dst_eip").css("display", "none");
// 			}
// 		});

		$("#wl_src_port_type").change(function(){
			$("#wl_src_port").val("");
			if($("#wl_src_port_type option:eq(1)").is(":selected")){
				$("#wl_src_port").attr("readonly", true).addClass("readonly-disabled");
			}
			else{
				$("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
			}
		});
		
		function editRow(data) {
			$("#formHeader").attr("id", "formHeaderEdit");
		    $("#formHeaderTitle").text("접근제어 정책 수정");
		    
		    $("#registBtn").attr("data-page", "edit");
		    $("#registBtn").html('<img src="/img/modify.png">수정하기');
		    $("#registBtn").off("click").on("click", function(){
				modifyWhiteList(data);
			});
		    
		    $("#wl_name").val(data.wl_name);
			$("#wl_npl_id").val(data.wl_npl_id);
			$("#wl_nplv_id").val(data.wl_nplv_id);
			
			$("#wl_src_ip").css("width", "230px");
			$("#wl_src_ip").val(data.wl_src_ip);
			$("#wl_src_port_type").val(data.wl_src_port_type);
			if ($("#wl_src_port_type").val() === "1") {
				$("#wl_src_port").attr("readonly", true).addClass("readonly-disabled");
			}
			else{
				$("#wl_src_port").attr("readonly", false).removeClass("readonly-disabled");
			}
			$("#wl_src_port").css("width", "136px");
			$("#wl_src_port").val(data.wl_src_port);
			
			$("#wl_dst_ip").css("width", "230px");
			$("#wl_dst_ip").val(data.wl_dst_ip);
			$("#wl_dst_port_type").val(data.wl_dst_port_type);
			$("#wl_dst_port").css("width", "136px");
			$("#wl_dst_port").val(data.wl_dst_port);
			
			$("#wl_dst_port_type").attr("readonly", true).addClass("readonly-select");

		    $("#whitePolicyPopup").fadeIn();
		}
		
		function modifyWhiteList(data){
			var form = document.getElementById("whiteForm"); 
			var wl_id = data.wl_id;
			
			if(form.wl_name.value == ""){
				alert("정책명을 입력하세요.");
				form.wl_name.focus();
				return false;
			}
			
			form.wl_npl_name.value = form.wl_npl_id.options[form.wl_npl_id.selectedIndex].text;
			
			form.wl_nplv_name.value = form.wl_nplv_id.options[form.wl_nplv_id.selectedIndex].text;
			
			if (!validateHost("wl_src_ip")) return false;
			if (!validatePort("wl_src_port_type", "wl_src_port")) return false;
			if (!validateHost("wl_dst_ip")) return false;
			if (!validatePort("wl_dst_port_type", "wl_dst_port")) return false;
			
			if (confirm("접근제어 정책을 수정 하시겠습니까?") == true) {
				$.ajax({
					url : "modifyWhiteList.do",
					data : $("#whiteForm").serialize() + "&wl_id=" + wl_id, 
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
					success: function(data) {
						if(data == "success"){
							alert("접근제어 정책을 수정하였습니다.");
							 location.href="/white_ics.do";
						}
						else if(data == "same_policy"){
							alert("이미 등록된 정책 입니다.")
						}
						else if(data == "update_fail"){
							alert("수정 작업 중 에러가 발생하였습니다.")
						}
						else{
							alert(data);
						}
					},
					error: function(){
						alert('수정 작업 중 에러가 발생하였습니다.');
					}
				});
			}
		}
		
	</script>
</head>
<body>
	<div class="groupListGrid">
     	<div id="groupListGrid"></div>
   	</div>
</body>
</html>