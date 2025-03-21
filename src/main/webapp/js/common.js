/**
 * 두 날짜의 차이를 일자로 구한다.(조회 종료일 - 조회 시작일)
 *
 * @param val1 - 조회 시작일(날짜 ex.2002-01-01)
 * @param val2 - 조회 종료일(날짜 ex.2002-01-01)
 * @return 기간에 해당하는 일자
 */

//ip 정규식 유효성 검사
function checkIpForm(ip_addr)
{
	var filter = /^([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\.([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3}$/;
	return filter.test(ip_addr);
}

//mac 정규식 유효성 검사
function checkMacForm(mac_addr)
{
	var filter = /^[0-9a-fA-F]{2}$/;
	var arrMac = mac_addr.split(":");
	if(arrMac.length < 3){
		return false;
	}
	
	for(var i=0; i<arrMac.length; i++){
		if(!filter.test(arrMac[i])){
			return false;
		}
	}
	
	return true;
}


//정수형 주소를 IP 주소 형식으로 변환
function long2ip(l){
	with(Math){
		var ip1 = floor(l/pow(256,3));
		var ip2 = floor((l%pow(256,3))/pow(256,2));
		var ip3 = floor(((l%pow(256,3))%pow(256,2))/pow(256,1));
		var ip4 = floor((((l%pow(256,3))%pow(256,2))%pow(256,1))/pow(256,0));
	}
	return ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
}

//IP 주소를  정수형 주소 형식으로 변환
function ip2long(ip){
	var arrIp = ip.split('.');
	var ipLong = 0;
	with(Math){
		ipLong = 
			(arrIp[0]*pow(256,3))+
			(arrIp[1]*pow(256,2))+
			(arrIp[2]*pow(256,1))+
			(arrIp[3]*pow(256,0));
	}
	return ipLong;
}

function calDateRange(val1, val2){
    var FORMAT = "-";
	
    // FORMAT을 포함한 길이 체크
    if (val1.length != 10 || val2.length != 10)
        return null;
	
    // FORMAT이 있는지 체크
    if (val1.indexOf(FORMAT) < 0 || val2.indexOf(FORMAT) < 0)
        return null;
	
    // 년도, 월, 일로 분리
    var start_dt = val1.split(FORMAT);
    var end_dt = val2.split(FORMAT);

	// 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
    // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
    start_dt[1] = (Number(start_dt[1]) - 1) + "";
    end_dt[1] = (Number(end_dt[1]) - 1) + "";

	var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
    var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);

	return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
}

// 입력한 문자의 byte를 계산
function getByte(contents){
	var str_character;
	var int_char_count;
	var int_contents_length;
	
	int_char_count = 0;
	int_contents_length = contents.length;
	
	for(k=0; k < int_contents_length; k++){
		str_character = contents.charAt(k);
		if(escape(str_character).length > 4){
			int_char_count += 2;
		}else{
			int_char_count++;
		}
	}
	
	return int_char_count;
}

// 문자열 xor 암복호화
function crypto(strdata){
	var cryptoKey = "656D2B6A209ACE857601D1042D8A1401413172549F906BB9B2675CF727A397968B255FAF2461C2376B120DAEC765ACEC53C35559B934FEB6CAFD7D759A7CE296";
    var keySize = cryptoKey.length;
    
    var keyCount = 0;
    var crypto = new Array();
	for(i=0; i < strdata.length; i++){
        crypto[i] = String.fromCharCode(strdata.charAt(i).charCodeAt(0) ^ cryptoKey[keyCount++].charCodeAt(0));
        if(keyCount == keySize) keyCount = 0;
    }
	
	return crypto.join('');
}


// 정책

//ip 정규식 유효성 검사
function checkIpForm(ip_addr)
{
   var filter = /^([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\.([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3}$/;
   return filter.test(ip_addr);
}

//mac 정규식 유효성 검사
function checkMacForm(mac_addr)
{
   var filter = /^[0-9a-fA-F]{2}$/;
   var arrMac = mac_addr.split(":");
   if(arrMac.length < 3){
      return false;
   }
   
   for(var i=0; i<arrMac.length; i++){
      if(!filter.test(arrMac[i])){
         return false;
      }
   }
   
   return true;
}


//정수형 주소를 IP 주소 형식으로 변환
function long2ip(l){
   with(Math){
      var ip1 = floor(l/pow(256,3));
      var ip2 = floor((l%pow(256,3))/pow(256,2));
      var ip3 = floor(((l%pow(256,3))%pow(256,2))/pow(256,1));
      var ip4 = floor((((l%pow(256,3))%pow(256,2))%pow(256,1))/pow(256,0));
   }
   return ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
}

//IP 주소를  정수형 주소 형식으로 변환
function ip2long(ip){
   var arrIp = ip.split('.');
   var ipLong = 0;
   with(Math){
      ipLong = 
         (arrIp[0]*pow(256,3))+
         (arrIp[1]*pow(256,2))+
         (arrIp[2]*pow(256,1))+
         (arrIp[3]*pow(256,0));
   }
   return ipLong;
}