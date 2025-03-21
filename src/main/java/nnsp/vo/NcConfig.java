package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcConfig implements Serializable {
	// 로그 설정
	int ncl_cycle;
	String ncl_server_ip;
	
	// 로그인 설정
	int ncli_lock_failcnt;
	int ncli_lock_date;
	
	// 메일 설정
	String nce_host;
	int nce_host_type;
	int nce_port;
	String nce_id;
	String nce_pw;
	int nce_cycle;
	String nce_from_email;
	String org_nce_pw;
	int nce_use_yn;
	
	// 시스템 설정
	int ncs_seq;
	String ncs_int_ip;
	String ncs_ext_ip;
	String ncs_mng_ip;
	String ncs_int_nm;
	String ncs_ext_nm;
	String ncs_mng_nm;
	String ncs_int_gw;
	String ncs_ext_gw;
	String ncs_mac;
	String ncs_dns;
	int ncs_elc_delay;
	int ncs_div;
	String org_int_ip;
	String org_mac;
	String ncs_master_ip;
	String ncs_master_mac;
	String ncs_slave_ip;
	String ncs_slave_mac;
	int ncs_remote_flag;
	String ncs_version;
	int ncs_arrow;
	String ncs_master_nic;
	String ncs_slave_nic;
	
	// 연계 네트워크 설정
	int nclk_seq;
	String nclk_rcv_ip;
	String nclk_snd_ip;
	String nclk_rcv_nm;
	String nclk_snd_nm;
	String nclk_snd_gw;
	int nclk_div;
	
	// 서비스 프로그램 설정
	int ncp_seq;
	String ncp_file_name;
	int ncp_div;
	int ncp_status;
	String ncp_path;
	int ncp_use_yn;
	String ncp_name;
	int ncp_service_flag;
	String ncp_hash;
	String ncp_hash_date;
	int ncp_file_size;
	int ncp_inspect_flag;
	int npl_no;
	String npl_name;
	
	String ncp_file_size2;
	
	// 무결성 검사
	int nci_cycle;
	int nci_div;
	int nci_active;
	int nci_update;
	String ncp_page;
	String ncs_antivirus;
	String ncs_antivirus1;
	String ncs_antivirus2;
	
	public int getNci_active() {
		return nci_active;
	}
	public void setNci_active(int nci_active) {
		this.nci_active = nci_active;
	}
	public int getNci_update() {
		return nci_update;
	}
	public String getNcp_file_size2() {
		return ncp_file_size2;
	}
	public void setNcp_file_size2(String ncp_file_size2) {
		this.ncp_file_size2 = ncp_file_size2;
	}
	public void setNci_update(int nci_update) {
		this.nci_update = nci_update;
	}
	public String getNcs_antivirus() {
		return ncs_antivirus;
	}
	public void setNcs_antivirus(String ncs_antivirus) {
		this.ncs_antivirus = ncs_antivirus;
	}
	public String getNcs_antivirus1() {
		return ncs_antivirus1;
	}
	public void setNcs_antivirus1(String ncs_antivirus1) {
		this.ncs_antivirus1 = ncs_antivirus1;
	}
	public String getNcs_antivirus2() {
		return ncs_antivirus2;
	}
	public void setNcs_antivirus2(String ncs_antivirus2) {
		this.ncs_antivirus2 = ncs_antivirus2;
	}
	public String getNpl_name() {
		return npl_name;
	}
	public void setNpl_name(String npl_name) {
		this.npl_name = npl_name;
	}
	public int getNpl_no() {
		return npl_no;
	}
	public void setNpl_no(int npl_no) {
		this.npl_no = npl_no;
	}
	public void setNce_host_type(int nce_host_type) {
		this.nce_host_type = nce_host_type;
	}
	public void setNcp_page(String ncp_page) {
		this.ncp_page = ncp_page;
	}
	public int getNci_div() {
		return nci_div;
	}
	public void setNci_div(int nci_div) {
		this.nci_div = nci_div;
	}
	public int getNcs_remote_flag() {
		return ncs_remote_flag;
	}
	public void setNcs_remote_flag(int ncs_remote_flag) {
		this.ncs_remote_flag = ncs_remote_flag;
	}
	public String getNcs_master_ip() {
		return ncs_master_ip;
	}
	public void setNcs_master_ip(String ncs_master_ip) {
		this.ncs_master_ip = ncs_master_ip;
	}
	public String getNcs_master_mac() {
		return ncs_master_mac;
	}
	public void setNcs_master_mac(String ncs_master_mac) {
		this.ncs_master_mac = ncs_master_mac;
	}
	public String getNcs_slave_ip() {
		return ncs_slave_ip;
	}
	public void setNcs_slave_ip(String ncs_slave_ip) {
		this.ncs_slave_ip = ncs_slave_ip;
	}
	public String getNcs_slave_mac() {
		return ncs_slave_mac;
	}
	public void setNcs_slave_mac(String ncs_slave_mac) {
		this.ncs_slave_mac = ncs_slave_mac;
	}
	public String getNcs_version() {
		return ncs_version;
	}
	public void setNcs_version(String ncs_version) {
		this.ncs_version = ncs_version;
	}
	public int getNcs_arrow() {
		return ncs_arrow;
	}
	public void setNcs_arrow(int ncs_arrow) {
		this.ncs_arrow = ncs_arrow;
	}
	public String getNcs_master_nic() {
		return ncs_master_nic;
	}
	public void setNcs_master_nic(String ncs_master_nic) {
		this.ncs_master_nic = ncs_master_nic;
	}
	public String getNcs_slave_nic() {
		return ncs_slave_nic;
	}
	public void setNcs_slave_nic(String ncs_slave_nic) {
		this.ncs_slave_nic = ncs_slave_nic;
	}
	
	// NCLK - NND_CONF_LINK
	public int getNclk_seq() {
		return nclk_seq;
	}
	public void setNclk_seq(int nclk_seq) {
		this.nclk_seq = nclk_seq;
	}
	public String getNclk_rcv_ip() {
		return nclk_rcv_ip;
	}
	public void setNclk_rcv_ip(String nclk_rcv_ip) {
		this.nclk_rcv_ip = nclk_rcv_ip;
	}
	public String getNclk_snd_ip() {
		return nclk_snd_ip;
	}
	public void setNclk_snd_ip(String nclk_snd_ip) {
		this.nclk_snd_ip = nclk_snd_ip;
	}
	public String getNclk_rcv_nm() {
		return nclk_rcv_nm;
	}
	public void setNclk_rcv_nm(String nclk_rcv_nm) {
		this.nclk_rcv_nm = nclk_rcv_nm;
	}
	public String getNclk_snd_nm() {
		return nclk_snd_nm;
	}
	public void setNclk_snd_nm(String nclk_snd_nm) {
		this.nclk_snd_nm = nclk_snd_nm;
	}
	public String getNclk_snd_gw() {
		return nclk_snd_gw;
	}
	public void setNclk_snd_gw(String nclk_snd_gw) {
		this.nclk_snd_gw = nclk_snd_gw;
	}
	public int getNclk_div() {
		return nclk_div;
	}
	public void setNclk_div(int nclk_div) {
		this.nclk_div = nclk_div;
	}
	
	// NCP - NND_CONF_PROGRAM
	public String getNcp_hash() {
		return ncp_hash;
	}
	public void setNcp_hash(String ncp_hash) {
		this.ncp_hash = ncp_hash;
	}
	public String getNcp_hash_date() {
		return ncp_hash_date;
	}
	public void setNcp_hash_date(String ncp_hash_date) {
		this.ncp_hash_date = ncp_hash_date;
	}
	public int getNcp_file_size() {
		return ncp_file_size;
	}
	public void setNcp_file_size(int ncp_file_size) {
		this.ncp_file_size = ncp_file_size;
	}
	public int getNcp_inspect_flag() {
		return ncp_inspect_flag;
	}
	public void setNcp_inspect_flag(int ncp_inspect_flag) {
		this.ncp_inspect_flag = ncp_inspect_flag;
	}
	public int getNci_cycle() {
		return nci_cycle;
	}
	public void setNci_cycle(int nci_cycle) {
		this.nci_cycle = nci_cycle;
	}
	public int getNcp_seq() {
		return ncp_seq;
	}
	public void setNcp_seq(int ncp_seq) {
		this.ncp_seq = ncp_seq;
	}
	public String getNcp_file_name() {
		return ncp_file_name;
	}
	public void setNcp_file_name(String ncp_file_name) {
		this.ncp_file_name = ncp_file_name;
	}
	public int getNcp_div() {
		return ncp_div;
	}
	public void setNcp_div(int ncp_div) {
		this.ncp_div = ncp_div;
	}
	public int getNcp_status() {
		return ncp_status;
	}
	public void setNcp_status(int ncp_status) {
		this.ncp_status = ncp_status;
	}
	public String getNcp_path() {
		return ncp_path;
	}
	public void setNcp_path(String ncp_path) {
		this.ncp_path = ncp_path;
	}
	public int getNcp_use_yn() {
		return ncp_use_yn;
	}
	public void setNcp_use_yn(int ncp_use_yn) {
		this.ncp_use_yn = ncp_use_yn;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public int getNcp_service_flag() {
		return ncp_service_flag;
	}
	public void setNcp_service_flag(int ncp_service_flag) {
		this.ncp_service_flag = ncp_service_flag;
	}
	public String getNcs_int_ip() {
		return ncs_int_ip;
	}
	public String getOrg_int_ip() {
		return org_int_ip;
	}
	public void setOrg_int_ip(String org_int_ip) {
		this.org_int_ip = org_int_ip;
	}
	public String getOrg_mac() {
		return org_mac;
	}
	public void setOrg_mac(String org_mac) {
		this.org_mac = org_mac;
	}
	public String getNcs_mng_ip() {
		return ncs_mng_ip;
	}
	public void setNcs_mng_ip(String ncs_mng_ip) {
		this.ncs_mng_ip = ncs_mng_ip;
	}
	public String getNcs_int_nm() {
		return ncs_int_nm;
	}
	public void setNcs_int_nm(String ncs_int_nm) {
		this.ncs_int_nm = ncs_int_nm;
	}
	public String getNcs_ext_nm() {
		return ncs_ext_nm;
	}
	public void setNcs_ext_nm(String ncs_ext_nm) {
		this.ncs_ext_nm = ncs_ext_nm;
	}
	public String getNcs_mng_nm() {
		return ncs_mng_nm;
	}
	public void setNcs_mng_nm(String ncs_mng_nm) {
		this.ncs_mng_nm = ncs_mng_nm;
	}
	public String getNcs_int_gw() {
		return ncs_int_gw;
	}
	public void setNcs_int_gw(String ncs_int_gw) {
		this.ncs_int_gw = ncs_int_gw;
	}
	public String getNcs_ext_gw() {
		return ncs_ext_gw;
	}
	public void setNcs_ext_gw(String ncs_ext_gw) {
		this.ncs_ext_gw = ncs_ext_gw;
	}
	public int getNcs_elc_delay() {
		return ncs_elc_delay;
	}
	public void setNcs_elc_delay(int ncs_elc_delay) {
		this.ncs_elc_delay = ncs_elc_delay;
	}
	public void setNcs_int_ip(String ncs_int_ip) {
		this.ncs_int_ip = ncs_int_ip;
	}
	public String getNcs_ext_ip() {
		return ncs_ext_ip;
	}
	public void setNcs_ext_ip(String ncs_ext_ip) {
		this.ncs_ext_ip = ncs_ext_ip;
	}
	public int getNcs_seq() {
		return ncs_seq;
	}
	public void setNcs_seq(int ncs_seq) {
		this.ncs_seq = ncs_seq;
	}
	public String getNcs_mac() {
		return ncs_mac;
	}
	public void setNcs_mac(String ncs_mac) {
		this.ncs_mac = ncs_mac;
	}
	public String getNcs_dns() {
		return ncs_dns;
	}
	public void setNcs_dns(String ncs_dns) {
		this.ncs_dns = ncs_dns;
	}
	public int getNcs_div() {
		return ncs_div;
	}
	public void setNcs_div(int ncs_div) {
		this.ncs_div = ncs_div;
	}
	
	//NCL - NND_CONF_LOG
	public int getNcl_cycle() {
		return ncl_cycle;
	}
	public void setNcl_cycle(int ncl_cycle) {
		this.ncl_cycle = ncl_cycle;
	}
	public String getNcl_server_ip() {
		return ncl_server_ip;
	}
	public void setNcl_server_ip(String ncl_server_ip) {
		this.ncl_server_ip = ncl_server_ip;
	}
	//
	
	//NCLI - NND_CONFIG_LOGIN
	public int getNcli_lock_failcnt() {
		return ncli_lock_failcnt;
	}
	public void setNcli_lock_failcnt(int ncli_lock_failcnt) {
		this.ncli_lock_failcnt = ncli_lock_failcnt;
	}
	public int getNcli_lock_date() {
		return ncli_lock_date;
	}
	public void setNcli_lock_date(int ncli_lock_date) {
		this.ncli_lock_date = ncli_lock_date;
	}
	//
	
	// NCE - NND_CONF_EMAIL
	public String getNce_host() {
		return nce_host;
	}
	
	public void setNce_host(String nce_host) {
		this.nce_host = nce_host;
	}
	public int getNce_host_type() {
		return nce_host_type;
	}
	public int getNce_port() {
		return nce_port;
	}
	public void setNce_port(int nce_port) {
		this.nce_port = nce_port;
	}
	public String getNce_id() {
		return nce_id;
	}
	public void setNce_id(String nce_id) {
		this.nce_id = nce_id;
	}
	public String getNce_pw() {
		return nce_pw;
	}
	public void setNce_pw(String nce_pw) {
		this.nce_pw = nce_pw;
	}
	public int getNce_cycle() {
		return nce_cycle;
	}
	public void setNce_cycle(int nce_cycle) {
		this.nce_cycle = nce_cycle;
	}
	public String getNce_from_email() {
		return nce_from_email;
	}
	public void setNce_from_email(String nce_from_email) {
		this.nce_from_email = nce_from_email;
	}
	public String getOrg_nce_pw() {
		return org_nce_pw;
	}
	public void setOrg_nce_pw(String org_nce_pw) {
		this.org_nce_pw = org_nce_pw;
	}
	public String getNcp_page() {
		return ncp_page;
	}
	public int getNce_use_yn() {
		return nce_use_yn;
	}
	public void setNce_use_yn(int nce_use_yn) {
		this.nce_use_yn = nce_use_yn;
	}
}
