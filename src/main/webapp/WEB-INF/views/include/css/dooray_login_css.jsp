<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
html, body {height:100%;}
.login_cont{display:flex; align-items:center; justify-content:center; width:100%;min-width:1000px;height:100%;color:#c9c9ca;}
.login_left{float:left;width:100%;min-width:1000px;height:100%;padding:0px; margin:0px -387px 0px 0px;}
.login_bg{height:100%;margin:0px 387px 0px 0px; background-image:url('/images/bg.jpg');background-repeat:no-repeat; background-size:100% 100%;text-align:center;}
.login_bg_logo{width:100%;height:100%;display:table;}
.login_bg_logo_cont{display:table-cell;text-align:center;vertical-align:middle;}
.logo_cont{margin-top:-110px;}
.logo_txt{text-align:center;margin:30px 0px 0px 0px;}
.login_right{float:right;padding:0px;margin:0px;width:387px;height:100%; background-color:#556576;display:table;}
.login_frm{position:relative;display:table-cell;text-align:center;vertical-align:middle;}
.login_frm_logo{display:flex; flex-direction: column; margin-bottom: 20px;}
.login_frm_id{width:319px; height:38px; margin:20px auto; background-image:url('/images/id.jpg');background-repeat:no-repeat; background-position:center;text-align:right;}
.login_frm_pw{width:319px; height:38px; margin:20px auto; background-image:url('/images/pw.jpg');background-repeat:no-repeat; background-position:center;text-align:right;}
.frm_login{border:0px;height:38px;width:258px;background:transparent none;padding-left:10px;}
.frm_login:focus{border:0px;}
.login_frm_submit{width:319px; height:32px;line-height:30px; margin:20px auto;cursor:pointer;background-color:#e60012;font-size:16px;color:#fff}
.login_com_logo{position:absolute;bottom:30px;right:35px;}
.usr_add{font-weight:bold;color:#c9c9ca;line-height:30px;}
.bs-example-modal-lg .modal-dialog {width: 553px;}
.bs-example-modal-lg .modal-dialog .modal-content .modal-header {display: flex; justify-content: space-between;background-color:#191f28;}
.bs-example-modal-lg .modal-dialog .modal-content .modal-body .item {display:flex;}
.bs-example-modal-lg .modal-dialog .modal-content .modal-body .item .control-label{text-align:right;}


.bs-example-modal-lg .modal-dialog .modal-content .modal-body .item  h2{width:10%;color:#c9c9ca;font-size: 12px; float: left; margin-right: 10px; line-height: 13px;}
.bs-example-modal-lg .modal-dialog .modal-content .modal-body .item  .border_title {width: 90%; height: 1px; background: #DBDBDB; float: right; margin-top: 16px;}
</style>