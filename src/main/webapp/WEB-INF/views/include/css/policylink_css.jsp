<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
header > nav #nav #menu03 .icon {background-position: -22px 0;}

.main-content {
	padding-top:128px;
	padding-left:64px;
	width:1920px;
}
.tbl {border-top:1px solid #e0e3e7; border-left:1px solid #eceef1;border-right:1px solid #eceef1;  }
.tbl thead{
	background-color: #f7f8fa; border-bottom:1px solid #686b70;
}
.tbl > thead > tr >th {
	padding-top:10px;padding-bottom:10px;
}
.tbl > thead > tr > .hgroup1 {
	vertical-align:middle;width:260px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup2 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup3 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup4 {
	vertical-align:middle;width:425px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup5 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup6 {
	vertical-align:middle;width:325px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup7 {
	vertical-align:middle;width:335px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup8 {
	vertical-align:middle;width:65px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > thead > tr > .hgroup9 {
	vertical-align:middle; width:75px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}

.tbl > tbody > tr{
	border-bottom:1px solid #e0e3e7;height:auto;
	overflow:hidden;
	text-align:center;
	
}
.tbl > tbody > tr >td {
	padding-top:10px;padding-bottom:10px;
}

.tbl > tbody > tr > .bgroup1 {
	vertical-align:middle;width:260px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup2 {
	vertical-align:middle;width:100px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup3 {
	vertical-align:middle;width:100px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup4 {
	vertical-align:middle;width:425px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup5 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup6 {
	vertical-align:middle;width:325px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup7 {
	vertical-align:middle;width:335px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tbl > tbody > tr > .bgroup8 {
	vertical-align:middle;width:65px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;font-size:11px;
}
.tbl > tbody > tr > .bgroup9 {
	vertical-align:middle; width:75px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
#npl_name{
	height:38px;width:240px;
}
#nts_name{
	height:38px;width:80px;
}
#ntc_name{
	height:38px;width:80px;
}
#nplr_ip{
	height:38px;width:145px;
}
#nplr_end_ip{
	height:38px;width:145px;margin-right:10px;
}
#npl_src_port{
	height:38px;width:50px;
}
#nplr_dst_ip{
	height:38px;width:155px;
}
#nplr_dst_port{
	height:38px;width:50px;margin-right:10px;
}
#ncp_seq_tx{
	height:38px;width:230px;margin-left:10px;margin-bottom:5px;
}
#ncp_seq_rx{
	height:38px;width:230px;margin-left:10px;
}
.npl_use_yn{
	height:15px;width:15px;
}
#enroll{
	height:25px;width:55px;margin-bottom:10px;
}
#cancel{
	height:25px;width:55px;
}
#nplr_range{
	text-align:left;height:15px;width:15px; /* top:252px; position:absolute; */
}
.range{
	text-align:left;
}
/*리스트*/
.tblist {border-top:1px solid #e0e3e7; border-left:1px solid #eceef1;border-right:1px solid #eceef1;  }
.tblist thead{
	background-color: #f7f8fa; border-bottom:1px solid #686b70;
}
.tblist > thead > tr >th {
	padding-top:10px;padding-bottom:10px;
}
.tblist > thead > tr > .thgroup1 {
	vertical-align:middle;width:260px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup2 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup3 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup4 {
	vertical-align:middle;width:420px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup5 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup6 {
	vertical-align:middle;width:320px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup7 {
	vertical-align:middle;width:335px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup8 {
	vertical-align:middle;width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup9 {
	vertical-align:middle; width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > thead > tr > .thgroup10 {
	vertical-align:middle; width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}

.tblist > tbody > tr{
	border-bottom:1px solid #e0e3e7;height:auto;
	overflow:hidden;
	text-align:center;
	
}
.tblist > tbody > tr >td {
	 padding-top:10px;padding-bottom:10px;
}

.tblist > tbody > tr > .tbgroup1 {
	vertical-align:middle;width:260px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup2 {
	vertical-align:middle;width:100px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup3 {
	vertical-align:middle;width:100px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup4 {
	vertical-align:middle;width:420px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup5 {
	vertical-align:middle;width:90px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup6 {
	vertical-align:middle;width:320px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup7 {
	vertical-align:middle;width:335px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup8 {
	vertical-align:middle;width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;font-size:11px;
}
.tblist > tbody > tr > .tbgroup9 {
	vertical-align:middle; width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.tblist > tbody > tr > .tbgroup10 {
	vertical-align:middle; width:50px;border-right:1px solid #e0e3e7; border-bottom:1px solid #686b70;
}
.delBtn {
	color:black;
	margin-left:5px;
}
.list_delete{
	color:black;
}
</style>