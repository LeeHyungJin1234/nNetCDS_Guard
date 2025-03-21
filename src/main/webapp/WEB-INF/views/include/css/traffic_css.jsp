<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style>
html{width: 100%; height:100%; background-color: #0e131a;}
header {border-color:#000;/*background-color:rgba(156,199,247,0.03);background-image: none;*/}
header > nav #nav #menu01 .icon {background-position: -23px 0;}

#main-form {position:relative;border-bottom:1px solid rgba(255,255,255,0.2); background-color: #0e131a;z-index: 2;width:1920px;min-width:100%;}
#main-form .main-wrapper {display: flex; justify-content: flex-start;}

input {width:101px;height:30px;border-radius:4px;border:1px solid #242a32;background-color: #242a32;font-size:13px;font-weight:400;font-family:"Nanum Barun Gothic";color:rgba(255,255,255,0.7);}

input.date{background:#242a32 url('../../img/top-sort2.png')no-repeat 90% 50%;}

input::placeholder {color:rgba(255,255,255,0.3);}
:-ms-input-placeholder {color:rgba(255,255,255,0.3);}
::-webkit-input-placeholder {color:rgba(255,255,255,0.3);}

#main-form .main-wrapper form #search {/* width:1310px; */height:52px;/* margin:0 auto; */background-color:#0e131a; display: flex; align-items: center;}
#main-form .main-wrapper form #search > li {float:left;}
#main-form .main-wrapper form #search > li > span{font-family:"Nanum Barun Gothic";margin:0 7px 0 20px;vertical-align: middle;font-size:13px;color:rgba(255,255,255,0.4);font-weight:400;}
#main-form .main-wrapper form #search > li > img{margin:0 8px;vertical-align: middle;}
#main-form .main-wrapper form #search > li .tab {margin: 0px 20px;}
#main-form .main-wrapper form #search > li .tab li {float:left;}
#main-form .main-wrapper form #search > li .tab div {height:32px;outline: none;cursor: pointer;padding: 0 16px;transition: 0.3s;border: 1px solid #242a32;display:table;}
#main-form .main-wrapper form #search > li .tab div:hover{background-color:rgba(0,0,0,.05);transition: 0.3s ease-in-out;}
#main-form .main-wrapper form #search > li .tab li:nth-child(1) div{border-radius:4px 0 0 4px;}
#main-form .main-wrapper form #search > li .tab li:nth-child(3) div{border-radius:0 4px 4px 0;padding: 0 16px;}
#main-form .main-wrapper form #search > li .tab div a {font-size: 14px;color:rgba(255,255,255,0.3);vertical-align: middle;display:table-cell;}
#main-form .main-wrapper form #search > li .tab div.active {background-color:#cc1b3c; background-image: linear-gradient(#d92648, #cc1b3c, #b91130); border:1px solid #cc1b3c; z-index:9;}
#main-form .main-wrapper form #search > li .tab div.active a {color:white; }
#main-form .main-wrapper form #search > li:last-child{border-left: 1px solid; border-left-color: rgba(255,255,255,0.2);}
#main-form .main-wrapper form #search > li select:disabled {background: #242a32;}

#main-form .main-wrapper select {width:69px;height:32px;background:#242a32 url('../../img/top-sort2.png')no-repeat 90% 50%;border-radius:4px;border:1px solid #242a32;color:#010c1e;font-size:13px;font-weight:400;font-family:"Nanum Barun Gothic";padding-left:10px; -webkit-appearance: none;-moz-appearance: none;appearance: none;color:rgba(255,255,255,0.7);}
#main-form .main-wrapper select:focus {border:1px solid #cc1b3c;}

#submitbtn{
	padding: 4px 19px 4px 17px;
}

#content {
	position: relative; 
	width: 100%; 
	min-width: 1900px;
	height: unset; 
	display: flex;
	max-width: 1920px;
}

#content .left-content {
    /* position: absolute;
    top: 30px;
    width: 355px; */
    flex: 1;
    /* min-height: 843px; */
    height: 614px;
    background-color: #0e131a;
    border-right: 1px solid rgba(255, 255, 255, 0.2);
}
#content .left-content .chart {width:355px;height:139px;border-bottom: 1px solid rgba(255,255,255,0.1);}
#content .left-content .chart:nth-child(6) {border-bottom:0;}

#content .left-content .chart > span {position:relative;top:14px;left:20px;color:#fff;font-size:14px;}

#content .left-content .chart > .chartdiv { margin-top: 15px;}
#content .left-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(7) {transform: translate(0, -28px);}
#content .left-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(15) {transform: translate(0, -28px);}
#content .left-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(12) > g:nth-child(1) {transform: translate(28px, 81px);}
#content .left-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(15) > g:nth-child(3){display:none;}
#content .left-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(18) {display:none;}
#content .left-content .chart > .chartdiv text {fill:rgba(255,255,255,0.4) !important;}

#content .center-content {/* width:1145px; */ flex: 4; background-color: #0e131a; height:614px;}
#content .center-content .main-section {
    position:relative;
    /* left:26px;
    top:55px;
    right:365px; */
    /* width:1145px; */
    height:598px;
    margin-top:0px;
}

#content .center-content .main-section .main-wrapper {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	justify-content: center;
}

#content .center-content .main-section .main-wrapper .tx {
	position:absolute;
	left:10px;
	width: 50%;
	height:100%;
}

#content .center-content .main-section .main-wrapper .main-arrow .tx-machine{
    position: absolute;
    top:169px;
    right:634px;
    width:249px;
    height:249px;
    /* background: url("../../img/main-circle1_v2.gif") left top no-repeat; */
    /* background: url("../../img/main-CSG_circle1_v2.gif") left top no-repeat; */
    background: url("../../img/main-CSG_circle1_3.gif") left top no-repeat;
    background-size: 249px 249px;
    z-index: 2;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

#content .center-content .main-section .main-wrapper .main-arrow .tx-machine span {text-align: center; font-family:'Nunito Sans' , Gulim, Dotum;font-weight:500;}
#content .center-content .main-section .main-wrapper .main-arrow .tx-machine .mb p:nth-child(1) {color:white;font-size:28px;}
#content .center-content .main-section .main-wrapper .main-arrow .tx-machine .mb p:nth-child(2) {margin: 15px 0px 6px 0px; color:rgba(255,255,255,0.4);font-size:13px;}

#content .center-content .main-section .main-wrapper .main-arrow .tx-machine .service p:nth-child(1) {margin: 6px 0px 15px 0px; color:rgba(255,255,255,0.4);font-size:13px;}
#content .center-content .main-section .main-wrapper .main-arrow .tx-machine .service p:nth-child(2) {color:white;font-size:28px;}

#content .center-content .main-section .main-wrapper .tx .sankeyDiv {
    width:82%;height:100%;float: left;z-index: -1;
}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv span {
    display: grid;
    grid-template-areas:
        'img service-name'
        'img byte-and-unit';
    grid-template-columns: fit-content(28px) minmax(30px, 140px);
    grid-template-rows: min-content;
}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv img {
    width: 28px;height: 28px;
    padding-right: 8px;
    grid-area: img;
}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .service-name {
    overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
    display: inline-block;
    font-family: "NanumSquare";
    font-size: 13px;
    color: #dddddd;
    position: relative;
    top: -5px;
    grid-area: service-name;
    transform: skew(-0.1deg);
}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .bytes {
    font-family: "Nunito Sans Light";font-size: 11px;color: #5a93f3;
    position: relative;
    top: -14px;
    grid-area: byte-and-unit;
}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .bytes .byte-unit {
    font-family: "Nunito Sans Light";font-size: 10px;color: #366aad;
    position: relative;
    left: 4px;
}

/*#content .center-content .main-section .main-wrapper .tx .sankeyDiv .normal {}*/
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .inactive .service-name {color: #868686 !important;}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .inactive .bytes {color: #686868 !important;}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .inactive .bytes .byte-unit {color: #686868 !important;}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .failure .service-name {color: #fa7b6a !important;}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .failure .bytes {color: #f96464 !important;}
#content .center-content .main-section .main-wrapper .tx .sankeyDiv .failure .bytes .byte-unit {color: #8e3734 !important;}

#content .center-content .main-section .main-wrapper .rx {
	position:absolute;
	right:10px;
	width: 50%;
	height: 100%;
}
#content .center-content .main-section .main-wrapper .main-arrow .rx-machine{
    position: absolute;
    top:169px;
    left:634px;
    width:249px;
    height:249px;
    /* background: url("../../img/main-circle2_v2.gif") left top no-repeat; */
    /* background: url("../../img/main-CSG_circle2_v2.gif") left top no-repeat; */
    background: url("../../img/main-CSG_circle2_3.gif") left top no-repeat;
    background-size: 249px 249px;
    z-index: 2;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

#content .center-content .main-section .main-wrapper .main-arrow .rx-machine span {text-align: center; font-family:'Nunito Sans' , Gulim, Dotum;font-weight:500;}
#content .center-content .main-section .main-wrapper .main-arrow .rx-machine .mb p:nth-child(1) {color:white;font-size:28px;}
#content .center-content .main-section .main-wrapper .main-arrow .rx-machine .mb p:nth-child(2) {margin: 15px 0px 6px 0px; color:rgba(255,255,255,0.4);font-size:13px;}

#content .center-content .main-section .main-wrapper .main-arrow .rx-machine .service p:nth-child(1) {margin: 6px 0px 15px 0px; color:rgba(255,255,255,0.4);font-size:13px;}
#content .center-content .main-section .main-wrapper .main-arrow .rx-machine .service p:nth-child(2) {color:white;font-size:28px;}

#content .center-content .main-section .main-wrapper .rx .sankeyDiv {
    width:82%;height:100%;float: right;z-index: -1;
}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv span {
    display: grid;
    grid-template-areas:
        'service-name img'
        'byte-and-unit img';
    grid-template-columns: minmax(30px, 140px) fit-content(28px);
    grid-template-rows: min-content;
}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .service-name {
    overflow: hidden;text-overflow: ellipsis;white-space: nowrap;
    display: inline-block;
    font-family: "NanumSquare";
    font-size: 13px;
    color: #dddddd;
    text-align: right;
    position: relative;
    top: -5px;
    grid-area: service-name;
    transform: skew(-0.1deg);
}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .bytes {
    font-family: "Nunito Sans Light";
    font-size: 11px;
/*     color: #5a93f3; */
	color: #0eaba7;
    text-align: right;
    position: relative;
    top: -14px;
}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .bytes .byte-unit {
    display: inline-block;
    /*width: 15px;*/
    font-family: "Nunito Sans Light";
    font-size: 10px;
/*     color: #366aad; */
	color: #038688;
    position: relative;
    right: 0px;
    margin-left: 4px;
}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv img {
    width: 28px;height: 28px;
    padding-left: 8px;
    grid-area: img;
}

/*#content .center-content .main-section .main-wrapper .rx .sankeyDiv .normal {}*/
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .inactive .service-name {color: #868686 !important;}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .inactive .bytes {color: #686868 !important;}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .inactive .bytes .byte-unit {color: #686868 !important;}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .failure .service-name {color: #fc625e !important;}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .failure .bytes {color: #dd4747 !important;}
#content .center-content .main-section .main-wrapper .rx .sankeyDiv .failure .bytes .byte-unit {color: #8e3734 !important;}


.main-arrow {
	/* display: flex;
	flex-direction: column;
	width: 4%; */
	height: 100%;
	/* justify-content: center;
	align-items: center; */
	z-index: 1;
	/* position:absolute;
	width:42px;
	height:104px;
	left:50.2%;
	top:46%;
	margin-left:-24px;
	margin-top:-41px; */
}
.main-arrow .arrow_in{
	width: 38px;
	height: 36px;
	position: absolute;
	top: 330px;
	left: 50%;
	margin-left: -19px;
	z-index: 99;
}
.main-arrow .product_cds{
	width: 252px;
	height: 48px;
	position: absolute;
	top: 267px;
	left: 50%;
	margin-left: -126px;
	z-index: 99;
}
.main-arrow .arrow_out{
	width: 38px;
	height: 36px;
	position: absolute;
	top: 220px;
	left: 50%;
	margin-left: -19px;
	z-index: 99;
}

/* .main-arrow > img:nth-child(2) {margin-top:20px;}
.main-arrow > img { width: 42px;height: 40px;} */

#content .right-content {/* position:absolute;left:1545px;top:30px;width:355px; */ flex: 1; /* min-height:843px; */height:614px;background-color:#0e131a;border-left:1px solid rgba(255,255,255,0.2);}

#content .right-content .chart {width:355px;height:139px;border-bottom: 1px solid rgba(255,255,255,0.1);}
#content .right-content .chart:nth-child(6) {border-bottom:0;}

#content .right-content .chart > span {position:relative;top:14px;left:20px;color:#fff;font-size:14px;}

#content .right-content .chart > .chartdiv { margin-top: 15px;}
#content .right-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(7) {transform: translate(0, -28px);}
#content .right-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(15) {transform: translate(0, -28px);}
#content .right-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(12) > g:nth-child(1) {transform: translate(28px, 81px);}
#content .right-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(15) > g:nth-child(3){display:none;}
#content .right-content .chart > .chartdiv .amcharts-chart-div > svg > g:nth-child(18) {display:none;}
#content .right-content .chart > .chartdiv text {fill:rgba(255,255,255,0.4) !important;}
      
#foot-section  {display:flex;position:relative;/* margin-top:32px; *//* width:1145px; */background-color: #0e131a;height:164px;border-top:1px solid rgba(255,255,255,0.2); padding: 0px; max-width: 1920px; margin: 0 auto;}
#foot-section  .text-section {flex:1; position:relative; /* width:1550px; */top:0; padding: 0 20px;}
#foot-section > .text-section:nth-child(1) {border-right: 1px solid rgba(255,255,255,0.1);}

#foot-section  .text-section .foot-wrapper {width: 100%; table-layout:fixed}
#foot-section  .text-section .foot-wrapper thead {font-size:13px;color:rgba(255,255,255,0.3);}
#foot-section  .text-section .foot-wrapper thead .group1 {width:160px;}
#foot-section  .text-section .foot-wrapper thead .group2 {width:50px;}
#foot-section  .text-section .foot-wrapper thead .group3 {width:100px;}
#foot-section  .text-section .foot-wrapper tbody {font-size:14px;color:#fff;}
#foot-section  .text-section .foot-wrapper tbody .list-group1 {width:160px;}
#foot-section  .text-section .foot-wrapper tbody .list-group2 {width:50px;}
#foot-section  .text-section .foot-wrapper tbody .list-group3 {width:100px;}
#foot-section  .text-section .foot-wrapper tbody tr {height:28px;}
#foot-section  .text-section .foot-wrapper tbody tr.danger {color:#cc1b3c !important;}
#foot-section  .text-section .foot-wrapper td { /*max-width: 0px;*/ overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}

#foot-section  .text-section .realtime_log{
	overflow-y: scroll;
	height: 139px;
}      
  
#footer {position:relative; /* top:956px; */ z-index:99;border-top-color: rgba(224,227,231,0.2);}

.main-pop {position:absolute;left:0;top:0;width:464px;height:353px;background:rgba(25,31,40,0.9);border:1px solid #3e4248;z-index:9999;color:#fff;font-size:14px;}
.main-pop .title {width:464px; height:40px; border-bottom:1px solid rgba(255,255,255,0.1);display:table;}
.main-pop .title > span {padding-left:20px;vertical-align: middle;display:table-cell;}
.main-pop .title > img {padding:16px 10px 0 0;cursor:pointer;}

.main-pop .content .text {width:464px; height:66px;font-size:13px;}
.main-pop .content .text > div {position:relative;}
.main-pop .content .text > div:nth-child(1) {padding:8px 0 0 20px;}
.main-pop .content .text > div:nth-child(2) {padding-left:20px;}
.main-pop .content .text > div > span:nth-child(1) {color:rgba(255,255,255,0.5);}
.main-pop .content .text > div > span:nth-child(2) {position:absolute; left:120px;}
.main-pop .content .graph {width:464px;height:96px; padding-left:6px;}
.main-pop .content .graph > div {position:relative;width:222px;height:105px;float:left;margin-right:6px;font-size:13px;}
.main-pop .content .graph > div > span {position:relative;left:14px;top:5px;}
.main-pop .content .graph > div .chart {width:220px;height:100px;}
/*.main-pop .content .graph > div .chart > div {position:absolute;bottom:0;float:left;font-size:10px;color:rgba(255,255,255,0.3);margin-right:6px;}
.main-pop .content .graph > div .chart > div:nth-child(1) {left:12px;}
.main-pop .content .graph > div .chart > div:nth-child(2) {left:44px;}
.main-pop .content .graph > div .chart > div:nth-child(3) {left:78px;}
.main-pop .content .graph > div .chart > div:nth-child(4) {left:111px;}
.main-pop .content .graph > div .chart > div:nth-child(5) {left:146px;}
.main-pop .content .graph > div .chart > div:nth-child(6) {left:181px;}
.main-pop .content .graph > div .chart > div > div {position:relative;left:10px;width:8px;}
.main-pop .content .graph > div.graph1 .chart > div > div {background: #3f81d7;}
.main-pop .content .graph > div.graph2 .chart > div > div {background: #4ab199;}
.main-pop .content .graph > div.graph3 .chart > div > div {background: #ce76c8;}
.main-pop .content .graph > div.graph4 .chart > div > div {background: #8e43f9;}*/

.main-pop .content .graph > div .chart .amcharts-chart-div > svg > g:nth-child(7) {transform: translate(0, -28px);}
.main-pop .content .graph > div .chart .amcharts-chart-div > svg > g:nth-child(15) {transform: translate(0, -28px);}
.main-pop .content .graph > div .chart .amcharts-chart-div > svg > g:nth-child(15) > g:nth-child(3){display:none;}
.main-pop .content .graph > div .chart .amcharts-chart-div > svg > g:nth-child(18) {display:none;}
.main-pop .content .graph > div .chart text {fill:rgba(255,255,255,0.4) !important;}
</style>