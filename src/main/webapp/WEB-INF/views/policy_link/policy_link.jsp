<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html xmlns="https://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta charset="UTF-8">
<title><spring:message code='common.title'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="nNetCDS">
<!-- font -->
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
<%@ include file="/WEB-INF/views/include/css/reset_css.jsp"%>
<link rel="stylesheet" href="/css/jquery-ui.css">
<%@ include file="/WEB-INF/views/include/css/style_css.jsp"%>
<%@ include file="/WEB-INF/views/include/css/policy_css.jsp"%>
<script type="text/javascript" src="/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<style type="text/css">
	.disabledTxt{
		color:#cdcdcd;
	}
	.ip_list li::before{
		font-weight:bold;
		content:'·';
		margin:0px 5px 0px 0px;
	}
	.delBtn{color:#000;cursor:pointer;cursor:hand;}
</style>
<script type="text/javascript">
	function changeRange(check){
		if(check){
			$('#input_src_nplr_end_ip').attr("disabled", false);
			$('#rngTxt').removeClass("disabledTxt");
		}
		else{
			$('#input_src_nplr_end_ip').attr("disabled", true);
			$('#rngTxt').addClass("disabledTxt");
		}
		
	}
	function addIp(nplr_div){
		if(nplr_div==1){
			if($("input[name=src_nplr_range]").length>9){
				alert("출발지 정보는 최대 10개까지 입력 가능합니다.");
				return false;
			}
			
			var nplr_range = 0;
			var nplr_ip = $('#input_src_nplr_ip').val();
			var nplr_end_ip = $('#input_src_nplr_end_ip').val();
			if(nplr_ip=="" || !checkIpForm(nplr_ip)){  //  IP 주소형식 체크
				alert("입력한 값이 IP 주소 형식이 아닙니다.");
				return false;
			}
			if($('#input_src_nplr_range').attr("checked")){  //  범위입력 유무 체크
				nplr_range = 1;
			}
			var nplr_ip_number=ip2long(nplr_ip);
			var nplr_end_ip_number=ip2long(nplr_end_ip);
			if(nplr_range==1 && nplr_ip_number >= nplr_end_ip_number){
				alert("범위 입력 시 시작 IP 주소는 끝 IP 주소 보다 작아야 합니다.");
				return false;
			}
			
			//  중복체크
			var chkIp=0;
			$("input[name=src_nplr_range]").each(function(idx){
				var tmp_nplr_range=$("input[name=src_nplr_range]:eq("+idx+")").val();
				var tmp_nplr_ip=$("input[name=src_nplr_ip]:eq("+idx+")").val();
				var tmp_nplr_end_ip=$("input[name=src_nplr_end_ip]:eq("+idx+")").val();
				if(nplr_ip == tmp_nplr_ip || nplr_ip == tmp_nplr_end_ip){
					chkIp=1;
					return false;
				}
				
				if(nplr_range==1 && (nplr_end_ip == tmp_nplr_ip || nplr_end_ip == tmp_nplr_end_ip)){
					chkIp=1;
					return false;
				}
				if(tmp_nplr_range==1){
					var tmp_nplr_ip_number=ip2long(tmp_nplr_ip);
					var tmp_nplr_end_ip_number=ip2long(tmp_nplr_end_ip);
					if(nplr_ip_number >= tmp_nplr_ip_number && nplr_ip_number <= tmp_nplr_end_ip_number){
						chkIp=2;
						return false;
					}
					if(nplr_range==1){
						if(nplr_end_ip_number >= tmp_nplr_ip_number && nplr_end_ip_number <= tmp_nplr_end_ip_number){
							chkIp=2;
							return false;
						}
						else if(nplr_ip_number <= tmp_nplr_ip_number && nplr_end_ip_number >= tmp_nplr_end_ip_number){
							chkIp=2;
							return false;
						}
					}
				}
				else{
					if(nplr_range==1){
						var tmp_nplr_ip_number=ip2long(tmp_nplr_ip);
						if(tmp_nplr_ip_number >= nplr_ip_number && tmp_nplr_ip_number <= nplr_end_ip_number){
							chkIp=2;
							return false;
						}
					}
				}
			});
			if(chkIp==1){
				alert("중복된 IP 주소가 입력 되었습니다.");
				return false;
			}
			else if(chkIp==2){
				alert("범위 입력된 IP 주소의 범위가 겹칩니다.");
				return false;				
			}
			
			// 출발지 IP 추가
			var srcIpInfo=nplr_ip;
			if(nplr_range==1){
				srcIpInfo = srcIpInfo + " ~ " + nplr_end_ip;
			}
			srcIpInfo = srcIpInfo +"<input type='hidden' name='src_nplr_range' value='"+nplr_range+"' />";
			srcIpInfo = srcIpInfo +"<input type='hidden' name='src_nplr_ip' value='"+nplr_ip+"' />";
			srcIpInfo = srcIpInfo +"<input type='hidden' name='src_nplr_end_ip' value='"+nplr_end_ip+"' />";
			$('#src_ip_list').append("<li>"+srcIpInfo+"<a onclick='delIp($(this));' class='delBtn'>X</a></li>");
			
			emptySrcInput(); // 입력 성공후 출발지 입력칸을 초기화
		}
		else if(nplr_div==2){
			if($("input[name=dst_nplr_ip]").length>9){
				alert("출발지 정보는 최대 10개까지 입력 가능합니다.");
				return false;
			}
			
			var nplr_ip = $('#input_dst_nplr_ip').val();
			var nplr_dst_port = $('#input_nplr_dst_port').val();
			if(nplr_ip=="" || !checkIpForm(nplr_ip)){  //  IP 주소형식 체크
				alert("입력한 값이 IP 주소 형식이 아닙니다.");
				return false;
			}
			if(nplr_dst_port=="" || isNaN(nplr_dst_port)){  //  Port 숫자 체크
				alert("잘못된 Port번호가 입력되었습니다.");
				return false;
			}
			//  중복체크
			var chkIp=0;
			$("input[name=dst_nplr_ip]").each(function(idx){
				var tmp_nplr_ip=$("input[name=dst_nplr_ip]:eq("+idx+")").val();
				var tmp_nplr_dst_port=$("input[name=nplr_dst_port]:eq("+idx+")").val();
				if(nplr_ip == tmp_nplr_ip && nplr_dst_port == tmp_nplr_dst_port){
					chkIp=1;
					return false;
				}
			});
			if(chkIp==1){
				alert("중복된 IP 주소와 Port가 입력 되었습니다.");
				return false;
			}
			
			// 목적지 IP Port 추가
			var dstIpInfo=nplr_ip+" : "+nplr_dst_port;
			dstIpInfo = dstIpInfo +"<input type='hidden' name='dst_nplr_ip' value='"+nplr_ip+"' />";
			dstIpInfo = dstIpInfo +"<input type='hidden' name='nplr_dst_port' value='"+nplr_dst_port+"' />";
			$('#dst_ip_list').append("<li>"+dstIpInfo+"<a onclick='delIp($(this));' class='delBtn'>X</a></li>");
			
			emptyDstInput();  // 입력 성공후 도착지 입력칸을 초기화
		}
	}
	function delIp(delBtn){
		delBtn.parent().remove();
	}
	function emptySrcInput(){ //   입력칸 초기화
		$('#input_src_nplr_range').attr("checked", false);
		$('#input_src_nplr_end_ip').attr("disabled", true);
		$('#rngTxt').addClass("disabledTxt");
		$('#input_src_nplr_ip').val("");
		$('#input_src_nplr_end_ip').val("");
	}
	function emptyDstInput(){ //   입력칸 초기화
		$('#input_dst_nplr_ip').val("");
		$('#input_nplr_dst_port').val("");
	}
	function emptyInput(){ // 전체 초기화
		emptySrcInput();
		emptyDstInput();
		$('#src_ip_list').empty();
		$('#dst_ip_list').empty();
	}
	
	// 등록
    function writeCheck() {
    	var form = document.getElementById("writeform");
    	var srcIpCnt = $("input[name=src_nplr_ip]").length;
    	var dstIpCnt = $("input[name=dst_nplr_ip]").length;
    	if(srcIpCnt==0 || dstIpCnt==0){
    		alert("출발지 또는 목적지 IP, Port 정보가 입력되지 않았습니다.");
    		return false;
    	}
    	if(confirm("<spring:message code='alert.register'/>")){
    		var formData = $("form[id=writeform]").serialize();
			$.ajax({        
				type : "POST",  
				url : "policy_link_regit.do",   
				data : formData, 
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",      
				success: function(data) {
					alert(data);
				},
				error: function(){
					alert('등록 작업 중 에러가 발생하였습니다.');
				}
			});
    	}
    }
</script>
</head>
<body>
	<!-- start header -->
	<%@ include file="../include/top.jsp"%>
	<!-- header end -->
	
	<!-- left menu start -->
	<section id="content">
		<%@ include file="../include/left.jsp"%>
		
		<div id="right-content">
			<div class="right-wrapper">
				<form name="writeform" id="writeform" method="post">
					<div class="content">
						<table class="scroll" style="width:100%;">
							<colgroup>
								<col width="38%" />
								<col width="6%" />
								<col width="44%" />
								<col width="12%" />
							</colgroup>
							<thead>
								<tr class="text-small text-main-color2">
									<th colspan="2" class="group1">출발지</th>
									<th rowspan="2" class="group1">도착지 IP : PORT</th>
									<th rowspan="2" class="group1"></th>
								</tr>
								<tr class="text-small text-main-color2">
									<th>IP 주소</th>
									<th class="group1">PORT</th>
								</tr>
							</thead>
							<tbody>
								<tr class="text-medium text-main-color">
									<td class="list-group1">
										<label><input type="checkbox" id="input_src_nplr_range" name="input_src_nplr_range" value="1" style="width:20px;" onchange="changeRange(this.checked);">범위 입력</label>
										<input type="text" id="input_src_nplr_ip" name="input_src_nplr_ip" value="" maxlength="15">
										<span id="rngTxt" class="disabledTxt">~</span>
										<input type="text" id="input_src_nplr_end_ip" name="input_src_nplr_end_ip" value="" maxlength="15" disabled>
										<a href="javascript:addIp(1);" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;">추가</a>
										<ul id="src_ip_list" class="ip_list"></ul>
									</td>
									<td class="list-group1"></td>
									<td class="list-group1">
										<input type="text" id="input_dst_nplr_ip" name="input_src_nplr_ip" value="" maxlength="15">
										:
										<input type="text" id="input_nplr_dst_port" name="input_nplr_dst_port" value="" maxlength="5">
										<a href="javascript:addIp(2);" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;">추가</a>
										<ul id="dst_ip_list" class="ip_list"></ul>
									</td>
									<td class="list-group1">
										<a href="javascript:emptyInput();" id="gray-bottom-btn" style="margin-right:12px;" class="cancelbtn"><img src="/img/cancel.png" style="margin:5px 20px 0 0;">취소하기</a>
										<a href="javascript:writeCheck();" id="red-bottom-btn2" style="margin-right:12px;"><img src="/img/btnicon1.png" style="margin:6px 20px 0 2px;">등록하기</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</form>
			</div>
		</div>
	</section>
	<!-- left menu end -->

	
	<%@ include file="../include/footer.jsp"%>

</body>
</html>