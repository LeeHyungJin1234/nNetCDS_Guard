<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
#content{
	display: flex;
	flex-direction: row;
	min-width: 1920px;
	max-width: 1920px;
}

#left-menu{
	width: 269px;
	padding-top: 31px;
}
#left-menu > .left-wrapper {
	width: 200px;
	margin-left: 20px;
}
#left-menu > .left-wrapper > ul >li{
/* 	width: 100% */
}
#left-menu .left-wrapper > ul .left-menu02 img.left-icon {
    margin: 6px 11px !important;
}


#right-content {
	padding: 20px 30px 48px 20px;
	margin-left: 0;
}
.btn_div{
	margin: 0 0 20px 0;
}
.btn_div .policyBtn:first-child{
	margin-right: 10px;
}
.policyBtn{
	font-family: 'NanumGothic';
    display: inline-block;
    padding: 10px 20px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 700;
    background-color: #cc1b3c;
    background-image: linear-gradient(#d92648, #cc1b3c, #b91130);
    color: #fff;
    cursor: pointer;
    border: none;
    border-radius: 4px;
/*     text-shadow: 0px 2px 3px rgba(0, 0, 0, .1); */
}
.policyBtn>img{
	margin: 2px 5px 0 0;
}
.deleteBtn{
	color: #333;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ddd;
}
.policyBtn>img.deleteIcon{
	margin: 3px 5px 0 0;
}

.popup{
	display: flex;
	align-items: center;
	justify-content: center;
	position: absolute;
}
.popup .pop-wrapper {
	height: unset;
	left: unset;
    top: 30%;
    margin-left: unset;
    margin-top: unset;
}
#groupFormPopup .pop-wrapper{
	width: 410px;
}
#whiteFormPopup .pop-wrapper{
	width: 550px;
	top: 20%;
}
#regstWhiteListInGroup .pop-wrapper{
	width: 410px;
}
.popup .pop-wrapper .header_bar{
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 0 15px;
	width: unset;
}
.popup .pop-wrapper .header_bar h1{
	margin: 0;
}
.popup .pop-wrapper .header_bar img{
	width: 15px;
	height: 15px;
	filter: brightness(1);
	cursor: pointer;
}
.popup .pop-wrapper .header_bar img:hover{
	filter: brightness(0.5);
}
.popup .pop-wrapper .registForm{
	padding: 0px !important;
}
.popup .pop-wrapper .registForm > ul{
	padding: 20px 20px 10px 20px !important;
}
.popup .pop-wrapper .registForm li{
	display: flex;
	margin-bottom: 10px;
}
.popup .pop-wrapper .registForm li .li_label{
	flex: 1;
	display: flex;
	align-items: center;
	justify-content: left;
	margin-right: 15px;
}
.popup .pop-wrapper .registForm li .li_input,
.popup .pop-wrapper .registForm li .li_select{
	flex: 4;
}
.popup .pop-wrapper .registForm li .li_input_flex{
	display: flex;
	align-items: center;
}
.popup .pop-wrapper .registForm li .li_input input,
.popup .pop-wrapper .registForm li .li_select select{
	padding: 0;
	text-indent: 10px;
}
.popup .pop-wrapper #groupForm li .li_input input{
	width: 296px;
}
.popup .pop-wrapper #whiteForm li .li_input input{
	width: 394px;
}
.popup .pop-wrapper #whiteForm li .li_input select#wl_src_host_type,
.popup .pop-wrapper #whiteForm li .li_input select#wl_dst_host_type,
.popup .pop-wrapper #whiteForm li .li_input select#wl_src_port_type,
.popup .pop-wrapper #whiteForm li .li_input select#wl_dst_port_type{
	width: 90px;
	height: 34px;
	margin-right: 4px;
}
.popup .pop-wrapper #whiteForm li .li_input input#wl_src_ip,
.popup .pop-wrapper #whiteForm li .li_input input#wl_dst_ip,
.popup .pop-wrapper #whiteForm li .li_input input#wl_src_port,
.popup .pop-wrapper #whiteForm li .li_input input#wl_dst_port{
	width: 300px;
}
.popup .pop-wrapper .registForm > div{
	padding: 20px 20px 0px 20px !important;
	background-color: #f3f4f7;
}
.popup .pop-wrapper .registForm > div > .subFormLabel{
	padding-left:20px;
	margin-bottom:5px;
	background:url("../images/menu03/icon_tab_info_g.png") no-repeat left center;	
	font-size: 15px;
    color: #383838;
    font-weight: 600;
}
.popup .pop-wrapper .registForm > div > .subForm{
	padding: 20px !important;
	border: 1px solid #d6d7d8 !important;
	background-color: #fff;
}
.popup .pop-wrapper .registForm > div > .subForm > li{
	display: flex;
}
.popup .pop-wrapper .registForm > div > .subForm > li > .li_label{
	margin-right: 15px;
}
.popup .pop-wrapper .registForm > div > .subForm > li > .li_input,
.popup .pop-wrapper .registForm > div > .subForm > li > .li_select{
	flex-grow: 1;
}
.popup .pop-wrapper .registForm > div >.btnDiv{
	border-top: 0;
	padding-top: 0px;
	padding-bottom: 20px;
	margin-top: 0px;
}


.readonly-disabled{
	background-color: #e9ecef;
	color: transparent;
	pointer-events: none;
	border: 1px solid #ced4da;
}
.readonly-select{
	pointer-events: none;
    background-color: #f5f5f5; /* 비활성화처럼 보이도록 색 변경 */
    color: #999; /* 텍스트 색 약하게 */
}
.popup .pop-wrapper #whiteForm li .li_select select{
	width: 396px;
	height: 34px;
}
.popup .pop-wrapper #groupWhiteListForm li .li_select select{
	width: 296px;
	height: 34px;
}
.popup .pop-wrapper .registForm .btnDiv{
	display: flex;
	justify-content: right;
	border-top: 1px solid #333;
	padding-top: 10px;
	margin-top: 30px;
}
.popup .pop-wrapper .registForm .btnDiv .cancelBtn{
	font-family: 'NanumGothic';
	display: inline-block;
	padding: 7px 28px 7px 20px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 700;
	cursor: pointer;
	border-radius: 4px;
	background-color: #9f9f9f;
	background-image: linear-gradient(#a9a9a9, #9d9d9d, #8f8f8f);
	color: #fff;
	margin-right: 10px;
}
.popup .pop-wrapper .registForm .btnDiv .cancelBtn img{
	margin: 5px 7px 0 0;
}
.popup .pop-wrapper .registForm .btnDiv .registBtn{
	font-family: 'NanumGothic';
    display: inline-block;
    padding: 7px 26px 7px 20px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 700;
    background-color: #cc1b3c;
    background-image: linear-gradient(#d92648, #cc1b3c, #b91130);
    color: #fff;
    cursor: pointer;
    border-radius: 4px;
}
.popup .pop-wrapper .registForm .btnDiv .registBtn img{
	margin: 5px 7px 0 0;
}

#whiteFormPopup .pop-wrapper .registForm ul{
	border: unset;
}

.witeBody{
	position: relative;
}
.whiteTab{  
	font-size:0; width:100%;border-right: 2px solid white;border-bottom:1px solid #d5d6d7;margin-bottom:20px; 
}
.whiteTab > li{display: inline-block; height:32px; font-size:14px; width:130px; }
.whiteTab > li a{
	padding:0px 0px 1px 30px;margin:0px;border-radius: 4px 4px 0px 0px;
	border-top:1px solid #d2d2d3;border-left:1px solid #d2d2d3;border-right:1px solid #d2d2d3;	
	position:relative;font-family: 'Nanum Gothic'; display:block; height:33px; color: #010c1e;  
	line-height:35px; text-decoration:none; font-size:14px;
	background-color:#eeeded;
}
.whiteTab > li a.active{
	padding:0px 0px 0px 30px;
	border-bottom: 3.5px solid white;
	border-top: 1px solid #d2d2d3; border-right: 1px solid #d2d2d3;border-left:1px solid #d2d2d3;border-right:1px solid #d2d2d3;
    color:#cc1b3c; font-weight:bold;
	background-color:#fff;
}

.linkNDR{
	position:absolute;top:0px;left:290px;width:195px;height:33px;border-radius:16px;
	line-height:33px;text-align:center;color:#fff;font-size:13px;
	background-color:#7b8894;
}







.dx-datagrid>.dx-datagrid-headers{
	border-bottom: 1px solid #333 !important;
}
.dx-datagrid .dx-datagrid-headers .dx-header-row > td{
	border-right: 1px solid #ddd !important;
}
.dx-datagrid .dx-datagrid-headers .dx-header-row td.dx-command-select,
.dx-datagrid .dx-datagrid-headers .dx-header-row td.dx-datagrid-group-space{
	cursor: default !important;
}
#groupListGrid .dx-datagrid .dx-datagrid-headers .dx-header-row:first-child > td:first-child {
	border-right: none !important;
}
#groupListGrid .dx-datagrid .dx-datagrid-headers .dx-row td.dx-pointer-events-none{
	border-left: 1px solid #ddd !important;
}
#groupListGrid .dx-datagrid .dx-group-cell{
	border-right: 1px solid #ddd;
}
/*.dx-datagrid .dx-datagrid-headers .dx-header-row:first-child > td:last-child {
	border-right: none !important;
}*/
.dx-datagrid>.dx-bordered-bottom-view{
	border-top: none !important;
}
#icsDataContainer .dx-datagrid-rowsview .dx-row {
	font-weight: 700;
}
.dx-datagrid>.dx-bordered-bottom-view .dx-datagrid-content .dx-datagrid-table .dx-row td{
	cursor: default !important;
}
.dx-datagrid-content .dx-datagrid-table .dx-row > td.dx-datagrid-group-space{
	vertical-align: middle !important;
	padding: unset !important;
}
.dx-datagrid-rowsview.dx-empty {
	height: 40px !important;
}
.dx-freespace-row{
	height: 0 !important;
}
.dx-pager .dx-pages .dx-selection {
    background-color: #cc1b3c !important;
}

.policyTopDiv{
	display:flex;
	justify-content: flex-end;
	align-items: center;
	margin-bottom: 20px;
}
.policyTopDiv .searchDiv{
	display: flex;
	justify-content: flex-start;
	align-items: center;
}
.policyTopDiv .searchDiv .dateSpan{
	margin-right: 10px;
}
.policyTopDiv .searchDiv .dateBox{
	width: 175px;
}
.policyTopDiv .searchDiv .endDateBox{
	margin-right: 20px;
}
.policyTopDiv .searchDiv .dateSeparator{
	margin: 0px 8px;
	font-size: 16px;
}
.policyTopDiv .searchDiv .vertical_line{
	border-left:1px solid #c2c3c4;
	height:39px;
	margin:0px 17px;
}
.policyTopDiv .searchDiv .searchBtn{
    font-family: 'NanumGothic';
    display: inline-block;
    padding: 5px 19px 5px 17px;
    font-size: 14px;
    font-weight: 700;
    background-color: #cc1b3c;
    background-image: linear-gradient(#d92648, #cc1b3c, #b91130);
    color: #fff;
    border-radius: 4px;
    cursor: pointer;
}
.dx-buttongroup-mode-contained .dx-button-mode-contained {
	border-inline-end-width: 1px !important;
}
.tabSelect{
	background-color: #cc1b3c;
    background-image: linear-gradient(#d92648, #cc1b3c, #b91130);
    border: 1px solid #cc1b3c;
}
.dx-button-text{
	font-size: 14px;
    color: #969798;
}
.tabSelect .dx-button-text{
	color: #fff;
}

.dx-texteditor-input {
    caret-color: transparent; /* 커서 숨기기 */
}

.groupDiv{
	display: flex;
	align-items: center;
	justify-content: space-between;
	cursor: pointer;
}
.delGroupBtn{
	cursor: pointer;
	color: #333;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ddd;
	border-radius: 4px;
	font-family: 'NanumGothic';
	font-size: 14px;
	font-weight: normal;
	text-shadow: none;
	padding: 5px 10px;
}


tr.fail{
	color:#666 !important;
	font-weight:normal !important; 
/* 	border:2px solid #cc1b3c !important; */
}
tr.dx-selection td{
	background-color: #dee8f8 !important;
}


#whitePolicyPopup .pop-wrapper{
	width: 800px;
	top: 25%;
}
#whitePolicyPopup .registForm{
	display: grid;
	grid-template-rows: 1fr 1fr 0.5fr;
	grid-template-columns: 1fr 1fr;
}
#whitePolicyPopup .registForm .grid_top,
#whitePolicyPopup .registForm .grid_bottom{
	grid-column: span 2;
}
#whitePolicyPopup .registForm .grid_middle_left{
	padding: 20px 10px 0px 20px !important;
}
#whitePolicyPopup .registForm .grid_middle_right{
	padding: 20px 20px 0px 10px !important;
}
#whitePolicyPopup .pop-wrapper .registForm li:last-child{
	margin-bottom: unset !important;
}
#whitePolicyPopup .pop-wrapper .registForm li .li_label{
	margin-right: unset !important;
}
#whitePolicyPopup .pop-wrapper .registForm > div > .subForm > li:last-child{
	margin-bottom: unset !important;
}
/* .popup .pop-wrapper #whitePolicyPopup li .li_select select{ */
/* 	width: 396px; */
/* 	height: 34px; */
/* } */
#whitePolicyPopup .pop-wrapper #whiteForm li .li_input,
#whitePolicyPopup .pop-wrapper #whiteForm li .li_select{
	flex: 2;
}
#whitePolicyPopup .pop-wrapper #whiteForm li .li_input input{
	width: 650px;
}
#whitePolicyPopup .pop-wrapper #whiteForm li .li_select select{
	width: 652px;
	height: 34px;
}
#whitePolicyPopup .pop-wrapper #whiteForm li .li_select select#wl_npl_id,
#whitePolicyPopup .pop-wrapper #whiteForm li .li_select select#wl_nplv_id{
	background: #fff url(../../img/sort.png) no-repeat 97% 50% !important;
}

#whitePolicyPopup .pop-wrapper #whiteForm li .li_input select#wl_src_port_type,
#whitePolicyPopup .pop-wrapper #whiteForm li .li_input select#wl_dst_port_type{
	width: 90px;
	height: 34px;
	margin-right: 4px;
}
#whitePolicyPopup .pop-wrapper .registForm ul{
	border: unset;
}
/* #whitePolicyPopup .pop-wrapper #whiteForm li .li_input input#wl_src_ip, */
/* #whitePolicyPopup .pop-wrapper #whiteForm li .li_input input#wl_dst_ip, */
/* #whitePolicyPopup .pop-wrapper #whiteForm li .li_input input#wl_src_port, */
/* #whitePolicyPopup .pop-wrapper #whiteForm li .li_input input#wl_dst_port{ */
/* 	width: 300px; */
/* } */
</style>