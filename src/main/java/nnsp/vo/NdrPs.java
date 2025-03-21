package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NdrPs implements Serializable {
	String[] colorMac = {"#ffffff", "#009642", "#afc611", "#afc611", "#f4af0a", "#e74404", "#b20811"};
	String[] colorIp = {"#ffffff", "#009642",  "#f4af0a", "#e74404", "#b20811"};
	public String[] colorCode = {"#67B7DC","#6794DC","#6770DC","#8067DC","#A367DC","#C767DC","#DC67CE","#DC67B7","#DC6794","#DC6780"};
	public String[] getColorCode() {
		return colorCode;
	}
	
	// 패킷 상태 정보
	long ps_id;
	String ps_strc_time;
	String ps_strc_stime;
	String ps_strc_sdate;
	String ps_strc_shour;
	String ps_strc_smin;
	String ps_strc_ssec;
	String ps_strc_etime;
	String ps_strc_edate;
	String ps_strc_ehour;
	String ps_strc_emin;
	String ps_strc_esec;
	int ps_payload_size = -1; // 입력값 0을 구분 하기위해 -1로 셋팅
	String ps_type;
	String ps_proto_id;
	String ps_proto_sub_type;
	String ps_proto_sub_dec;
	String ps_proto_ics;
	String ps_proto_ics_str;
	String ps_proto_ics_type;
	String ps_proto_ics_cmd_type;
	String ps_proto_ics_cmd_type_str;
	String ps_mac;
	String ps_src_mac;
	String ps_dst_mac;
	String ps_mac_vendor;
	String ps_src_mac_vendor;
	String ps_dst_mac_vendor;
	String ps_ether_type;
	String ps_ether_type_dec;
	String ps_dsap;
	String ps_ssap;
	String ps_organization;
	String ps_pid;
	String ps_ip;
	String ps_src_ip;
	String ps_dst_ip;
	long ps_src_ip_int;
	long ps_dst_ip_int;
	String ps_port;
	String ps_src_port;
	String ps_dst_port;
	String ps_tcp_flag;
	String ps_tcp_flag_dec;
	String ps_ics_header1;
	String ps_ics_header2;
	String ps_ics_header3;
	String ps_ics_header4;
	String ps_ics_header5;
	String ps_ics_header6;
	String ps_ics_header7;
	String ps_ics_header8;
	String ps_ics_header9;
	String ps_ics_header10;
	String ps_ics_header11;
	String ps_ics_header12;
	String ps_ics_header13;
	String ps_ics_header14;
	String ps_white_rule;
	String ps_white_result; // 입력값 0을 구분 하기위해 -1로 셋팅
	String ps_flow_result; // 입력값 0을 구분 하기위해 -1로 셋팅
	String ps_flow_value; // 입력값 0을 구분 하기위해 -1로 셋팅
	int ps_endpoint;
	String ps_regdttm;
	long ps_packets;
	String ps_packets_str;
	Double ps_bytes;
	String ps_bytes_str;
	int tblName;
	int tblDate;

	String ps_src_ics_vendor;
	String ps_dst_ics_vendor;
	String ps_src_ics_layer;
	String ps_dst_ics_layer;
	String ps_src_gi_name;
	String ps_dst_gi_name;
	int ps_src_gi_id;
	int ps_dst_gi_id;
	
	String ps_nic_id;
	
	int ps_inout;
	String strPsInOut;
	int ps_detect;
	String strPsDetect;
	String ps_detect_desc;
	int wl_id;
	String wl_name;
	
	public int getTblDate() {
		return tblDate;
	}
	public void setTblDate(int tblDate) {
		this.tblDate = tblDate;
	}
	public int getPs_inout() {
		return ps_inout;
	}
	public void setPs_inout(int ps_inout) {
		this.ps_inout = ps_inout;
		if(ps_inout == 0)
			this.strPsInOut = "Outbound";
		else if(ps_inout == 1)
			this.strPsInOut = "Inbound";
	}
	public String getStrPsInOut() {
		return strPsInOut;
	}
	public int getPs_detect() {
		return ps_detect;
	}
	public void setPs_detect(int ps_detect) {
		this.ps_detect = ps_detect;
		if(ps_detect == 0)
			this.strPsDetect = "차단";
		else if(ps_detect == 1)
			this.strPsDetect = "허용";
		else if(ps_detect == 2)
			this.strPsDetect = "악성코드 탐지";
	}
	public String getStrPsDetect() {
		return strPsDetect;
	}
	public String getPs_detect_desc() {
		return ps_detect_desc;
	}
	public void setPs_detect_desc(String ps_detect_desc) {
		this.ps_detect_desc = ps_detect_desc;
	}
	public long getPs_id() {
		return ps_id;
	}
	public void setPs_id(long ps_id) {
		this.ps_id = ps_id;
	}
	public String getPs_strc_time() {		
		return ps_strc_time;
	}
	public String getPs_strc_time_hms() {
		String rtnTxt = ps_strc_time; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(11);
			if(rtnTxt.length()>9 && rtnTxt.length()<12){
				int roopNum = 12-rtnTxt.length();
				for(int i=0; i<roopNum; i++){
					rtnTxt = rtnTxt+"0";
				}
			}
			
		}
		return rtnTxt;
	}
	public void setPs_strc_time(String ps_strc_time) {
		this.ps_strc_time = ps_strc_time;
	}
	public String getPs_strc_stime() {
		return ps_strc_stime;
	}
	public String getPs_strc_stime_hms() {
		String rtnTxt = ps_strc_stime; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(11);
			if(rtnTxt.length()>9 && rtnTxt.length()<12){
				int roopNum = 12-rtnTxt.length();
				for(int i=0; i<roopNum; i++){
					rtnTxt = rtnTxt+"0";
				}
			}
			
		}
		return rtnTxt;
	}
	public void setPs_strc_stime(String ps_strc_stime) {
		this.ps_strc_stime = ps_strc_stime;
	}
	public String getPs_strc_sdate() {
		return ps_strc_sdate;
	}
	public String getPs_strc_sdate_hms() {
		String rtnTxt = ps_strc_sdate; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(11);
			if(rtnTxt.length()>9 && rtnTxt.length()<12){
				int roopNum = 12-rtnTxt.length();
				for(int i=0; i<roopNum; i++){
					rtnTxt = rtnTxt+"0";
				}
			}
			
		}
		return rtnTxt;
	}
	public void setPs_strc_sdate(String ps_strc_sdate) {
		this.ps_strc_sdate = ps_strc_sdate;
	}
	public String getPs_strc_shour() {
		return ps_strc_shour;
	}
	public void setPs_strc_shour(String ps_strc_shour) {
		this.ps_strc_shour = ps_strc_shour;
	}
	public String getPs_strc_smin() {
		return ps_strc_smin;
	}
	public void setPs_strc_smin(String ps_strc_smin) {
		this.ps_strc_smin = ps_strc_smin;
	}
	public String getPs_strc_ssec() {
		return ps_strc_ssec;
	}
	public void setPs_strc_ssec(String ps_strc_ssec) {
		this.ps_strc_ssec = ps_strc_ssec;
	}
	public String getPs_strc_etime() {
		return ps_strc_etime;
	}
	public String getPs_strc_etime_hms() {
		String rtnTxt = ps_strc_etime; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(11);
			if(rtnTxt.length()>9 && rtnTxt.length()<12){
				int roopNum = 12-rtnTxt.length();
				for(int i=0; i<roopNum; i++){
					rtnTxt = rtnTxt+"0";
				}
			}
			
		}
		return rtnTxt;
	}
	public void setPs_strc_etime(String ps_strc_etime) {
		this.ps_strc_etime = ps_strc_etime;
	}
	public String getPs_strc_edate() {
		return ps_strc_edate;
	}
	public String getPs_strc_edate_hms() {
		String rtnTxt = ps_strc_edate; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(11);
			if(rtnTxt.length()>9 && rtnTxt.length()<12){
				int roopNum = 12-rtnTxt.length();
				for(int i=0; i<roopNum; i++){
					rtnTxt = rtnTxt+"0";
				}
			}
			
		}
		return rtnTxt;
	}
	public void setPs_strc_edate(String ps_strc_edate) {
		this.ps_strc_edate = ps_strc_edate;
	}
	public String getPs_strc_ehour() {
		return ps_strc_ehour;
	}
	public void setPs_strc_ehour(String ps_strc_ehour) {
		this.ps_strc_ehour = ps_strc_ehour;
	}
	public String getPs_strc_emin() {
		return ps_strc_emin;
	}
	public void setPs_strc_emin(String ps_strc_emin) {
		this.ps_strc_emin = ps_strc_emin;
	}
	public String getPs_strc_esec() {
		return ps_strc_esec;
	}
	public void setPs_strc_esec(String ps_strc_esec) {
		this.ps_strc_esec = ps_strc_esec;
	}
	public int getPs_payload_size() {
		return ps_payload_size;
	}
	public void setPs_payload_size(int ps_payload_size) {
		this.ps_payload_size = ps_payload_size;
	}
	public String getPs_type() {
		return ps_type;
	}
	public void setPs_type(String ps_type) {
		this.ps_type = ps_type;
	}
	public String getPs_proto_id() {
		return ps_proto_id;
	}
	public void setPs_proto_id(String ps_proto_id) {
		this.ps_proto_id = ps_proto_id;
	}
	public String getPs_proto_sub_type() {
		return ps_proto_sub_type;
	}
	public void setPs_proto_sub_type(String ps_proto_sub_type) {
		this.ps_proto_sub_type = ps_proto_sub_type;
	}
	public String getPs_proto_sub_dec() {
		return ps_proto_sub_dec;
	}
	public void setPs_proto_sub_dec(String ps_proto_sub_dec) {
		this.ps_proto_sub_dec = ps_proto_sub_dec;
	}
	public String getPs_proto_ics() {
		return ps_proto_ics;
	}
	public void setPs_proto_ics(String ps_proto_ics) {
		this.ps_proto_ics = ps_proto_ics;
		String ps_proto_ics_str="IT";
		if(ps_proto_ics!=null && ps_proto_ics.equals("1")) {
			ps_proto_ics_str="OT";
		}
		this.ps_proto_ics_str = ps_proto_ics_str;
	}
	public String getPs_proto_ics_str() {
		return ps_proto_ics_str;
	}
	public String getPs_proto_ics_type() {
		return ps_proto_ics_type;
	}
	public void setPs_proto_ics_type(String ps_proto_ics_type) {
		this.ps_proto_ics_type = ps_proto_ics_type;
	}
	public String getPs_proto_ics_cmd_type() {
		return ps_proto_ics_cmd_type;
	}
	public void setPs_proto_ics_cmd_type(String ps_proto_ics_cmd_type) {
		this.ps_proto_ics_cmd_type = ps_proto_ics_cmd_type;
		String ps_proto_ics_cmd_type_str="N/A";
		if(ps_proto_ics_cmd_type!=null && ps_proto_ics_cmd_type.equals("1")) {
			ps_proto_ics_cmd_type_str="Read";
		}
		else if(ps_proto_ics_cmd_type!=null && ps_proto_ics_cmd_type.equals("2")) {
			ps_proto_ics_cmd_type_str="Write";
		}
		this.ps_proto_ics_cmd_type_str = ps_proto_ics_cmd_type_str;
	}
	public String getPs_proto_ics_cmd_type_str() {
		return ps_proto_ics_cmd_type_str;
	}
	public String getPs_mac() {
		return ps_mac;
	}
	public void setPs_mac(String ps_mac) {
		this.ps_mac = ps_mac;
	}
	public String getPs_src_mac() {
		return ps_src_mac;
	}
	public void setPs_src_mac(String ps_src_mac) {
		this.ps_src_mac = ps_src_mac;
	}
	public String getPs_dst_mac() {
		return ps_dst_mac;
	}
	public void setPs_dst_mac(String ps_dst_mac) {
		this.ps_dst_mac = ps_dst_mac;
	}
	public String getPs_mac_vendor() {
		return ps_mac_vendor;
	}
	public void setPs_mac_vendor(String ps_mac_vendor) {
		this.ps_mac_vendor = ps_mac_vendor;
	}
	public String getPs_src_mac_vendor() {
		return ps_src_mac_vendor;
	}
	public void setPs_src_mac_vendor(String ps_src_mac_vendor) {
		this.ps_src_mac_vendor = ps_src_mac_vendor;
	}
	public String getPs_dst_mac_vendor() {
		return ps_dst_mac_vendor;
	}
	public void setPs_dst_mac_vendor(String ps_dst_mac_vendor) {
		this.ps_dst_mac_vendor = ps_dst_mac_vendor;
	}
	public int getPs_dst_mac_interval() {
		int interval = 6;
		if(this.ps_src_mac != null && this.ps_dst_mac != null){
			String[] arr_src_mac = this.ps_src_mac.split(":");
			String[] arr_dst_mac = this.ps_dst_mac.split(":");
			for(int i=0; i<5; i++){
				if(arr_src_mac[i].equals(arr_dst_mac[i])){
					interval = 5-i;
				}
				else{
					break;
				}
			}
			
		}
		return interval;
	}
	public String getPs_dst_mac_interval_color() {
		int interval = 6;
		if(this.ps_src_mac != null && this.ps_dst_mac != null){
			String[] arr_src_mac = this.ps_src_mac.split(":");
			String[] arr_dst_mac = this.ps_dst_mac.split(":");
			for(int i=0; i<5; i++){
				if(arr_src_mac.length >= i && arr_dst_mac.length >= i && arr_src_mac[i].equals(arr_dst_mac[i])){
					interval = 5-i;
				}
				else{
					break;
				}
			}
			
		}
		return colorMac[interval];
	}
	public String getPs_ether_type() {
		return ps_ether_type;
	}
	public void setPs_ether_type(String ps_ether_type) {
		this.ps_ether_type = ps_ether_type;
	}
	public String getPs_ether_type_dec() {
		return ps_ether_type_dec;
	}
	public void setPs_ether_type_dec(String ps_ether_type_dec) {
		this.ps_ether_type_dec = ps_ether_type_dec;
	}
	public String getPs_dsap() {
		return ps_dsap;
	}
	public void setPs_dsap(String ps_dsap) {
		this.ps_dsap = ps_dsap;
	}
	public String getPs_ssap() {
		return ps_ssap;
	}
	public void setPs_ssap(String ps_ssap) {
		this.ps_ssap = ps_ssap;
	}
	public String getPs_organization() {
		return ps_organization;
	}
	public void setPs_organization(String ps_organization) {
		this.ps_organization = ps_organization;
	}
	public String getPs_pid() {
		return ps_pid;
	}
	public void setPs_pid(String ps_pid) {
		this.ps_pid = ps_pid;
	}
	public String getPs_ip() {
		return ps_ip;
	}
	public void setPs_ip(String ps_ip) {
		this.ps_ip = ps_ip;
	}
	public String getPs_src_ip() {
		return ps_src_ip;
	}
	public void setPs_src_ip(String ps_src_ip) {
		this.ps_src_ip = ps_src_ip;
	}
	public String getPs_dst_ip() {
		return ps_dst_ip;
	}
	public void setPs_dst_ip(String ps_dst_ip) {
		this.ps_dst_ip = ps_dst_ip;
	}
	public long getPs_src_ip_int() {
		return ps_src_ip_int;
	}
	public void setPs_src_ip_int(long ps_src_ip_int) {
		this.ps_src_ip_int = ps_src_ip_int;
	}
	public long getPs_dst_ip_int() {
		return ps_dst_ip_int;
	}
	public void setPs_dst_ip_int(long ps_dst_ip_int) {
		this.ps_dst_ip_int = ps_dst_ip_int;
	}
	public String getPs_port() {
		return ps_port;
	}
	public void setPs_port(String ps_port) {
		this.ps_port = ps_port;
	}
	public String getPs_src_port() {
		return ps_src_port;
	}
	public void setPs_src_port(String ps_src_port) {
		this.ps_src_port = ps_src_port;
	}
	public String getPs_dst_port() {
		return ps_dst_port;
	}
	public void setPs_dst_port(String ps_dst_port) {
		this.ps_dst_port = ps_dst_port;
	}
	public String getPs_tcp_flag() {
		return ps_tcp_flag;
	}
	public void setPs_tcp_flag(String ps_tcp_flag) {
		this.ps_tcp_flag = ps_tcp_flag;
	}
	public String getPs_tcp_flag_dec() {
		return ps_tcp_flag_dec;
	}
	public void setPs_tcp_flag_dec(String ps_tcp_flag_dec) {
		this.ps_tcp_flag_dec = ps_tcp_flag_dec;
	}
	public String getPs_ics_header1() {
		return ps_ics_header1;
	}
	public void setPs_ics_header1(String ps_ics_header1) {
		this.ps_ics_header1 = ps_ics_header1;
	}
	public String getPs_ics_header2() {
		return ps_ics_header2;
	}
	public void setPs_ics_header2(String ps_ics_header2) {
		this.ps_ics_header2 = ps_ics_header2;
	}
	public String getPs_ics_header3() {
		return ps_ics_header3;
	}
	public void setPs_ics_header3(String ps_ics_header3) {
		this.ps_ics_header3 = ps_ics_header3;
	}
	public String getPs_ics_header4() {
		return ps_ics_header4;
	}
	public void setPs_ics_header4(String ps_ics_header4) {
		this.ps_ics_header4 = ps_ics_header4;
	}
	public String getPs_ics_header5() {
		return ps_ics_header5;
	}
	public void setPs_ics_header5(String ps_ics_header5) {
		this.ps_ics_header5 = ps_ics_header5;
	}
	public String getPs_ics_header6() {
		return ps_ics_header6;
	}
	public void setPs_ics_header6(String ps_ics_header6) {
		this.ps_ics_header6 = ps_ics_header6;
	}
	public String getPs_ics_header7() {
		return ps_ics_header7;
	}
	public void setPs_ics_header7(String ps_ics_header7) {
		this.ps_ics_header7 = ps_ics_header7;
	}
	public String getPs_ics_header8() {
		return ps_ics_header8;
	}
	public void setPs_ics_header8(String ps_ics_header8) {
		this.ps_ics_header8 = ps_ics_header8;
	}
	public String getPs_ics_header9() {
		return ps_ics_header9;
	}
	public void setPs_ics_header9(String ps_ics_header9) {
		this.ps_ics_header9 = ps_ics_header9;
	}
	public String getPs_ics_header10() {
		return ps_ics_header10;
	}
	public void setPs_ics_header10(String ps_ics_header10) {
		this.ps_ics_header10 = ps_ics_header10;
	}
	public String getPs_ics_header11() {
		return ps_ics_header11;
	}
	public void setPs_ics_header11(String ps_ics_header11) {
		this.ps_ics_header11 = ps_ics_header11;
	}
	public String getPs_ics_header12() {
		return ps_ics_header12;
	}
	public void setPs_ics_header12(String ps_ics_header12) {
		this.ps_ics_header12 = ps_ics_header12;
	}
	public String getPs_ics_header13() {
		return ps_ics_header13;
	}
	public void setPs_ics_header13(String ps_ics_header13) {
		this.ps_ics_header13 = ps_ics_header13;
	}
	public String getPs_ics_header14() {
		return ps_ics_header14;
	}
	public void setPs_ics_header14(String ps_ics_header14) {
		this.ps_ics_header14 = ps_ics_header14;
	}
	public String getPs_white_rule() {
		return ps_white_rule;
	}
	public void setPs_white_rule(String ps_white_rule) {
		this.ps_white_rule = ps_white_rule;
	}
	public String getPs_white_result() {
		return ps_white_result;
	}
	public void setPs_white_result(String ps_white_result) {
		this.ps_white_result = ps_white_result;
	}
	public String getPs_flow_result() {
		return ps_flow_result;
	}
	public void setPs_flow_result(String ps_flow_result) {
		this.ps_flow_result = ps_flow_result;
	}
	public String getPs_flow_value() {
		return ps_flow_value;
	}
	public void setPs_flow_value(String ps_flow_value) {
		this.ps_flow_value = ps_flow_value;
	}
	public int getPs_endpoint() {
		return ps_endpoint;
	}
	public void setPs_endpoint(int ps_endpoint) {
		this.ps_endpoint = ps_endpoint;
	}
	public String getPs_regdttm() {
		String rtnTxt = ps_regdttm; 
		if(rtnTxt != null){
			rtnTxt = rtnTxt.substring(0, 19);
		}
		return rtnTxt;
	}
	public void setPs_regdttm(String ps_regdttm) {
		this.ps_regdttm = ps_regdttm;
	}
	public long getPs_packets() {
		return ps_packets;
	}
	public String getPs_packets_str() {
		return ps_packets_str;
	}
	public void setPs_packets(long ps_packets) {
		this.ps_packets = ps_packets;
		this.ps_packets_str = nnsp.util.StringUtil.toNumFormat(ps_packets);
	}
	public Double getPs_bytes() {
		return ps_bytes;
	}
	public String getPs_bytes_str() {
		return ps_bytes_str;
	}
	public void setPs_bytes(Double ps_bytes) {
		this.ps_bytes = ps_bytes;
		this.ps_bytes_str = nnsp.util.StringUtil.byteCalculation(ps_bytes);
	}
	
	public String getPs_src_ics_vendor() {
		return ps_src_ics_vendor;
	}
	public void setPs_src_ics_vendor(String ps_src_ics_vendor) {
		this.ps_src_ics_vendor = ps_src_ics_vendor;
	}
	public String getPs_dst_ics_vendor() {
		return ps_dst_ics_vendor;
	}
	public void setPs_dst_ics_vendor(String ps_dst_ics_vendor) {
		this.ps_dst_ics_vendor = ps_dst_ics_vendor;
	}
	public String getPs_src_ics_layer() {
		return ps_src_ics_layer;
	}
	public void setPs_src_ics_layer(String ps_src_ics_layer) {
		this.ps_src_ics_layer = ps_src_ics_layer;
	}
	public String getPs_dst_ics_layer() {
		return ps_dst_ics_layer;
	}
	public void setPs_dst_ics_layer(String ps_dst_ics_layer) {
		this.ps_dst_ics_layer = ps_dst_ics_layer;
	}
	public String getPs_src_gi_name() {
		return ps_src_gi_name;
	}
	public void setPs_src_gi_name(String ps_src_gi_name) {
		this.ps_src_gi_name = ps_src_gi_name;
	}
	public String getPs_dst_gi_name() {
		return ps_dst_gi_name;
	}
	public void setPs_dst_gi_name(String ps_dst_gi_name) {
		this.ps_dst_gi_name = ps_dst_gi_name;
	}
	public int getPs_src_gi_id() {
		return ps_src_gi_id;
	}
	public void setPs_src_gi_id(int ps_src_gi_id) {
		this.ps_src_gi_id = ps_src_gi_id;
	}
	public int getPs_dst_gi_id() {
		return ps_dst_gi_id;
	}
	public void setPs_dst_gi_id(int ps_dst_gi_id) {
		this.ps_dst_gi_id = ps_dst_gi_id;
	}
	public String getPs_nic_id() {
		return ps_nic_id;
	}
	public void setPs_nic_id(String ps_nic_id) {
		this.ps_nic_id = ps_nic_id;
	}
	public int getTblName() {
		return tblName;
	}
	public void setTblName(int tblName) {
		this.tblName = tblName;
	}

	//conversation
	Double bytes;
	long packets;
	String strBytes;
	String strPackets;
	
	public Double getBytes() {
		return bytes;
	}
	public String getStrBytes() {
		return strBytes;
	}
	public void setBytes(Double bytes) {
		this.bytes = bytes;
		this.strBytes = nnsp.util.StringUtil.byteCalculation(bytes);
	}
	public long getPackets() {
		return packets;
	}
	public String getStrPackets() {
		return strPackets;
	}
	public void setPackets(long packets) {
		this.packets = packets;
		this.strPackets = nnsp.util.StringUtil.toNumFormat(packets);
	}

	Double totBytes;
	long totPackets;
	String strTotBytes;
	String strTotPackets;
	
	public Double getTotBytes() {
		return totBytes;
	}
	public String getStrTotBytes() {
		return strTotBytes;
	}
	public void setTotBytes(Double totBytes) {
		this.totBytes = totBytes;
		this.strTotBytes = nnsp.util.StringUtil.byteCalculation(totBytes);
	}
	public long getTotPackets() {
		return totPackets;
	}
	public String getStrTotPackets() {
		return strTotPackets;
	}
	public void setTotPackets(long totPackets) {
		this.totPackets = totPackets;
		this.strTotPackets = nnsp.util.StringUtil.toNumFormat(totPackets);
	}

	Double srcBytes;
	long srcPackets;
	String strSrcBytes;
	String strSrcPackets;
	
	public Double getSrcBytes() {
		return srcBytes;
	}
	public String getStrSrcBytes() {
		return strSrcBytes;
	}
	public void setSrcBytes(Double srcBytes) {
		this.srcBytes = srcBytes;
		this.strSrcBytes = nnsp.util.StringUtil.byteCalculation(srcBytes);
	}
	public long getSrcPackets() {
		return srcPackets;
	}
	public String getStrSrcPackets() {
		return strSrcPackets;
	}
	public void setSrcPackets(long srcPackets) {
		this.srcPackets = srcPackets;
		this.strSrcPackets = nnsp.util.StringUtil.toNumFormat(srcPackets);
	}

	Double dstBytes;
	long dstPackets;
	String strDstBytes;
	String strDstPackets;

	public Double getDstBytes() {
		return dstBytes;
	}
	public String getStrDstBytes() {
		return strDstBytes;
	}
	public void setDstBytes(Double dstBytes) {
		this.dstBytes = dstBytes;
		this.strDstBytes = nnsp.util.StringUtil.byteCalculation(dstBytes);
	}
	public long getDstPackets() {
		return dstPackets;
	}
	public String getStrDstPackets() {
		return strDstPackets;
	}
	public void setDstPackets(long dstPackets) {
		this.dstPackets = dstPackets;
		this.strDstPackets = nnsp.util.StringUtil.toNumFormat(dstPackets);
	}
	
	
	//PS 개수
	int cnt;
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	

	//Row Number
	int rowNumber;
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
//  paging을 위함
	int page;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	//Risk level
	int riskLevel=0;
	public int getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}
	

	boolean ps_src;
	public boolean getPs_src() {
		return chkReserved(this.ps_src_ip, this.ps_src_mac);
	}
	boolean ps_dst;
	public boolean getPs_dst() {
		return chkReserved(this.ps_dst_ip, this.ps_dst_mac);
	}
	
	private boolean chkReserved(String ip, String mac){
		boolean rtnResult = false;
		
		int ipBIndex=-1;
		int ipRIndex=-1;
		int ipLength=0;
		long ipInt=0;
		if(ip!=null&&!ip.equals("")) {
			ipBIndex = ip.lastIndexOf(".255");
			ipLength = ip.length();//  .255를 제외했을때의 문자열 길이
			ipInt=nnsp.util.StringUtil.ipToLong(ip);
			ipRIndex = ip.lastIndexOf(".0");
		}
		if(mac!=null) {
			mac=mac.toLowerCase();
		}
		else {
			mac="";
		}
		
		if(mac.equals("00:00:00:00:00:00") || mac.equals("02:00:00:00:00:00")) {
			rtnResult=true;
		}
		else if(mac.equals("ff:ff:ff:ff:ff:ff") || (ipLength-4>0&&ipLength-4==ipBIndex) || ipInt==4294967295L) {
			rtnResult=true;
		}
		else if(ipInt>=3758096384L && ipInt<=4026531839L) {
			rtnResult=true;
		}
		else if((ipInt>=4026531840L && ipInt<=4294967295L)||(ipLength-4>0&&ipLength-2==ipRIndex)||(ip!=null&&ip.indexOf("0.")==0)) {
			rtnResult=true;
		}
		
		return rtnResult;
	}
	
// 검색조건 추가
	String searchPs_inout;
	public String getSearchPs_inout() {
		return searchPs_inout;
	}
	public void setSearchPs_inout(String searchPs_inout) {
		this.searchPs_inout = searchPs_inout;
	}
	public int getWl_id() {
		return wl_id;
	}
	public void setWl_id(int wl_id) {
		this.wl_id = wl_id;
	}
	public String getWl_name() {
		return wl_name;
	}
	public void setWl_name(String wl_name) {
		this.wl_name = wl_name;
	}
	
}
