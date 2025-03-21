package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcLinkPolicy implements Serializable {
	int npc_no;
	String npc_name;
	
	int npcs_seq;
	String npcs_name;
	String npcs_header;
	String npcs_type;
	int npcs_use_yn;
	
	int nli_seq;
	int nli_ip_type;
	String nli_ip;
	String nli_ip_nm;
	int nli_gcode;
	int nli_div;
	int nli_use_yn;
	String org_ip_nm;
	
	int nls_seq;
	int nls_port;
	String nls_service_nm;
	int nls_gcode;
	int nls_div;
	int nls_use_yn;
	String org_service_nm;
	String rcv_service_nm;
	
	int nla_seq;
	String nla_name;
	int nla_div;
	int nla_use_yn;
	int nla_ip_gcode;
	int nla_svc_gcode;
	
	int nlp_seq;
	String nlp_name;
	String nlp_opc_server;
	String nlp_db_name;
	int nlp_rcv_port;
	String nlp_account;
	String nlp_passwd;
	int nlp_div;
	int nlp_use_yn;
	int nlp_control_port;
	String nlp_nw_ip;
	int nlp_nw_port;
	
	String nlp_tag_filename;
	int nlp_tag_startindex;
	int nlp_tag_readcount;
	String nlp_allow_ip;
	
	String nos_obj_nm;
	
	int nlp_pro_id;
	int nlp_rxpro_id;
	String ncp_name;
	String rx_ncp_name;
	int ncp_seq;
	String ncp_file_name;
	
	int npr_seq;
	String npr_source_ip;
	int nlp_trans_port;
	String npr_destination_ip;
	int npr_destination_port;
	
	String[] source_ip;
	String[] destination_ip;
	int[] destination_port;
	
	int ntc_seq;
	String ntc_name;
	int nsc_seq;
	String nsc_name;
	int ndc_seq;
	String ndc_name;
	
	String npe_value;
	String npe_name;
	String npe_description;
	int npp_no;
	String npp_name;
	String npp_description;
	String nlp_tx_nic;
	String nlp_rx_nic;
	String nlp_src_ip;
	String nlp_src_port;
	String nlp_dst_ip;
	String nlp_dst_port;
	int nlp_oneway_port;
		
	public int getNpr_seq() {
		return npr_seq;
	}
	public void setNpr_seq(int npr_seq) {
		this.npr_seq = npr_seq;
	}
	public String[] getSource_ip() {
		return source_ip;
	}
	public void setSource_ip(String[] source_ip) {
		this.source_ip = source_ip;
	}
	public String[] getDestination_ip() {
		return destination_ip;
	}
	public void setDestination_ip(String[] destination_ip) {
		this.destination_ip = destination_ip;
	}
	public int[] getDestination_port() {
		return destination_port;
	}
	public void setDestination_port(int[] destination_port) {
		this.destination_port = destination_port;
	}
	public int getNtc_seq() {
		return ntc_seq;
	}
	public void setNtc_seq(int ntc_seq) {
		this.ntc_seq = ntc_seq;
	}
	public String getNtc_name() {
		return ntc_name;
	}
	public void setNtc_name(String ntc_name) {
		this.ntc_name = ntc_name;
	}
	public int getNsc_seq() {
		return nsc_seq;
	}
	public void setNsc_seq(int nsc_seq) {
		this.nsc_seq = nsc_seq;
	}
	public String getNsc_name() {
		return nsc_name;
	}
	public void setNsc_name(String nsc_name) {
		this.nsc_name = nsc_name;
	}
	public int getNdc_seq() {
		return ndc_seq;
	}
	public void setNdc_seq(int ndc_seq) {
		this.ndc_seq = ndc_seq;
	}
	public String getNdc_name() {
		return ndc_name;
	}
	public void setNdc_name(String ndc_name) {
		this.ndc_name = ndc_name;
	}
	public String getNpr_source_ip() {
		return npr_source_ip;
	}
	public void setNpr_source_ip(String npr_source_ip) {
		this.npr_source_ip = npr_source_ip;
	}
	public String getNpr_destination_ip() {
		return npr_destination_ip;
	}
	public void setNpr_destination_ip(String npr_destination_ip) {
		this.npr_destination_ip = npr_destination_ip;
	}
	public int getNpr_destination_port() {
		return npr_destination_port;
	}
	public void setNpr_destination_port(int npr_destination_port) {
		this.npr_destination_port = npr_destination_port;
	}
	public int getNlp_trans_port() {
		return nlp_trans_port;
	}
	public void setNlp_trans_port(int nlp_trans_port) {
		this.nlp_trans_port = nlp_trans_port;
	}
	public int getNpcs_use_yn() {
		return npcs_use_yn;
	}
	public void setNpcs_use_yn(int npcs_use_yn) {
		this.npcs_use_yn = npcs_use_yn;
	}
	public String getNlp_allow_ip() {
		return nlp_allow_ip;
	}
	public void setNlp_allow_ip(String nlp_allow_ip) {
		this.nlp_allow_ip = nlp_allow_ip;
	}
	public int getNcp_seq() {
		return ncp_seq;
	}
	public void setNcp_seq(int ncp_seq) {
		this.ncp_seq = ncp_seq;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public String getRx_ncp_name() {
		return rx_ncp_name;
	}
	public void setRx_ncp_name(String rx_ncp_name) {
		this.rx_ncp_name = rx_ncp_name;
	}
	public int getNlp_pro_id() {
		return nlp_pro_id;
	}
	public void setNlp_pro_id(int nlp_pro_id) {
		this.nlp_pro_id = nlp_pro_id;
	}
	public int getNlp_rxpro_id() {
		return nlp_rxpro_id;
	}
	public void setNlp_rxpro_id(int nlp_rxpro_id) {
		this.nlp_rxpro_id = nlp_rxpro_id;
	}
	public String getRcv_service_nm() {
		return rcv_service_nm;
	}
	public void setRcv_service_nm(String rcv_service_nm) {
		this.rcv_service_nm = rcv_service_nm;
	}
	public String getNlp_tag_filename() {
		return nlp_tag_filename;
	}
	public void setNlp_tag_filename(String nlp_tag_filename) {
		this.nlp_tag_filename = nlp_tag_filename;
	}
	public int getNlp_tag_startindex() {
		return nlp_tag_startindex;
	}
	public void setNlp_tag_startindex(int nlp_tag_startindex) {
		this.nlp_tag_startindex = nlp_tag_startindex;
	}
	public int getNlp_tag_readcount() {
		return nlp_tag_readcount;
	}
	public void setNlp_tag_readcount(int nlp_tag_readcount) {
		this.nlp_tag_readcount = nlp_tag_readcount;
	}
	public String getNos_obj_nm() {
		return nos_obj_nm;
	}
	public void setNos_obj_nm(String nos_obj_nm) {
		this.nos_obj_nm = nos_obj_nm;
	}
	public String getOrg_service_nm() {
		return org_service_nm;
	}
	public void setOrg_service_nm(String org_service_nm) {
		this.org_service_nm = org_service_nm;
	}
	public String getOrg_ip_nm() {
		return org_ip_nm;
	}
	public void setOrg_ip_nm(String org_ip_nm) {
		this.org_ip_nm = org_ip_nm;
	}
	public int getNla_ip_gcode() {
		return nla_ip_gcode;
	}
	public void setNla_ip_gcode(int nla_ip_gcode) {
		this.nla_ip_gcode = nla_ip_gcode;
	}
	public int getNla_svc_gcode() {
		return nla_svc_gcode;
	}
	public void setNla_svc_gcode(int nla_svc_gcode) {
		this.nla_svc_gcode = nla_svc_gcode;
	}
	public int getNlp_rcv_port() {
		return nlp_rcv_port;
	}
	public void setNlp_rcv_port(int nlp_rcv_port) {
		this.nlp_rcv_port = nlp_rcv_port;
	}
	public int getNlp_control_port() {
		return nlp_control_port;
	}
	public void setNlp_control_port(int nlp_control_port) {
		this.nlp_control_port = nlp_control_port;
	}
	public String getNlp_nw_ip() {
		return nlp_nw_ip;
	}
	public void setNlp_nw_ip(String nlp_nw_ip) {
		this.nlp_nw_ip = nlp_nw_ip;
	}
	public int getNlp_nw_port() {
		return nlp_nw_port;
	}
	public void setNlp_nw_port(int nlp_nw_port) {
		this.nlp_nw_port = nlp_nw_port;
	}
	public int getNpc_no() {
		return npc_no;
	}
	public void setNpc_no(int npc_no) {
		this.npc_no = npc_no;
	}
	public String getNpc_name() {
		return npc_name;
	}
	public void setNpc_name(String npc_name) {
		this.npc_name = npc_name;
	}
	public int getNpcs_seq() {
		return npcs_seq;
	}
	public void setNpcs_seq(int npcs_seq) {
		this.npcs_seq = npcs_seq;
	}
	public String getNpcs_name() {
		return npcs_name;
	}
	public void setNpcs_name(String npcs_name) {
		this.npcs_name = npcs_name;
	}
	public String getNpcs_header() {
		return npcs_header;
	}
	public void setNpcs_header(String npcs_header) {
		this.npcs_header = npcs_header;
	}
	public String getNpcs_type() {
		return npcs_type;
	}
	public void setNpcs_type(String npcs_type) {
		this.npcs_type = npcs_type;
	}
	public int getNli_seq() {
		return nli_seq;
	}
	public void setNli_seq(int nli_seq) {
		this.nli_seq = nli_seq;
	}
	public int getNli_ip_type() {
		return nli_ip_type;
	}
	public void setNli_ip_type(int nli_ip_type) {
		this.nli_ip_type = nli_ip_type;
	}
	public String getNli_ip() {
		return nli_ip;
	}
	public void setNli_ip(String nli_ip) {
		this.nli_ip = nli_ip;
	}
	public String getNli_ip_nm() {
		return nli_ip_nm;
	}
	public void setNli_ip_nm(String nli_ip_nm) {
		this.nli_ip_nm = nli_ip_nm;
	}
	public int getNli_gcode() {
		return nli_gcode;
	}
	public void setNli_gcode(int nli_gcode) {
		this.nli_gcode = nli_gcode;
	}
	public int getNli_div() {
		return nli_div;
	}
	public void setNli_div(int nli_div) {
		this.nli_div = nli_div;
	}
	public int getNli_use_yn() {
		return nli_use_yn;
	}
	public void setNli_use_yn(int nli_use_yn) {
		this.nli_use_yn = nli_use_yn;
	}
	public int getNls_seq() {
		return nls_seq;
	}
	public void setNls_seq(int nls_seq) {
		this.nls_seq = nls_seq;
	}
	public int getNls_port() {
		return nls_port;
	}
	public void setNls_port(int nls_port) {
		this.nls_port = nls_port;
	}
	public String getNls_service_nm() {
		return nls_service_nm;
	}
	public void setNls_service_nm(String nls_service_nm) {
		this.nls_service_nm = nls_service_nm;
	}
	public int getNls_gcode() {
		return nls_gcode;
	}
	public void setNls_gcode(int nls_gcode) {
		this.nls_gcode = nls_gcode;
	}
	public int getNls_div() {
		return nls_div;
	}
	public void setNls_div(int nls_div) {
		this.nls_div = nls_div;
	}
	public int getNls_use_yn() {
		return nls_use_yn;
	}
	public void setNls_use_yn(int nls_use_yn) {
		this.nls_use_yn = nls_use_yn;
	}
	public int getNla_seq() {
		return nla_seq;
	}
	public void setNla_seq(int nla_seq) {
		this.nla_seq = nla_seq;
	}
	public String getNla_name() {
		return nla_name;
	}
	public void setNla_name(String nla_name) {
		this.nla_name = nla_name;
	}
	public int getNla_div() {
		return nla_div;
	}
	public void setNla_div(int nla_div) {
		this.nla_div = nla_div;
	}
	public int getNla_use_yn() {
		return nla_use_yn;
	}
	public void setNla_use_yn(int nla_use_yn) {
		this.nla_use_yn = nla_use_yn;
	}
	public int getNlp_seq() {
		return nlp_seq;
	}
	public void setNlp_seq(int nlp_seq) {
		this.nlp_seq = nlp_seq;
	}
	public String getNlp_name() {
		return nlp_name;
	}
	public void setNlp_name(String nlp_name) {
		this.nlp_name = nlp_name;
	}
	public String getNlp_opc_server() {
		return nlp_opc_server;
	}
	public void setNlp_opc_server(String nlp_opc_server) {
		this.nlp_opc_server = nlp_opc_server;
	}
	public String getNlp_db_name() {
		return nlp_db_name;
	}
	public void setNlp_db_name(String nlp_db_name) {
		this.nlp_db_name = nlp_db_name;
	}
	public String getNlp_account() {
		return nlp_account;
	}
	public void setNlp_account(String nlp_account) {
		this.nlp_account = nlp_account;
	}
	public String getNlp_passwd() {
		return nlp_passwd;
	}
	public void setNlp_passwd(String nlp_passwd) {
		this.nlp_passwd = nlp_passwd;
	}
	public int getNlp_div() {
		return nlp_div;
	}
	public void setNlp_div(int nlp_div) {
		this.nlp_div = nlp_div;
	}
	public int getNlp_use_yn() {
		return nlp_use_yn;
	}
	public void setNlp_use_yn(int nlp_use_yn) {
		this.nlp_use_yn = nlp_use_yn;
	}
	public String getNcp_file_name() {
		return ncp_file_name;
	}
	public void setNcp_file_name(String ncp_file_name) {
		this.ncp_file_name = ncp_file_name;
	}
	public String getNpe_value() {
		return npe_value;
	}
	public void setNpe_value(String npe_value) {
		this.npe_value = npe_value;
	}
	public String getNpe_name() {
		return npe_name;
	}
	public void setNpe_name(String npe_name) {
		this.npe_name = npe_name;
	}
	public int getNpp_no() {
		return npp_no;
	}
	public void setNpp_no(int npp_no) {
		this.npp_no = npp_no;
	}
	public String getNpp_name() {
		return npp_name;
	}
	public void setNpp_name(String npp_name) {
		this.npp_name = npp_name;
	}
	public String getNlp_tx_nic() {
		return nlp_tx_nic;
	}
	public void setNlp_tx_nic(String nlp_tx_nic) {
		this.nlp_tx_nic = nlp_tx_nic;
	}
	public String getNlp_rx_nic() {
		return nlp_rx_nic;
	}
	public void setNlp_rx_nic(String nlp_rx_nic) {
		this.nlp_rx_nic = nlp_rx_nic;
	}
	public String getNlp_src_ip() {
		return nlp_src_ip;
	}
	public void setNlp_src_ip(String nlp_src_ip) {
		this.nlp_src_ip = nlp_src_ip;
	}
	public String getNlp_src_port() {
		return nlp_src_port;
	}
	public void setNlp_src_port(String nlp_src_port) {
		this.nlp_src_port = nlp_src_port;
	}
	public String getNlp_dst_ip() {
		return nlp_dst_ip;
	}
	public void setNlp_dst_ip(String nlp_dst_ip) {
		this.nlp_dst_ip = nlp_dst_ip;
	}
	public String getNlp_dst_port() {
		return nlp_dst_port;
	}
	public void setNlp_dst_port(String nlp_dst_port) {
		this.nlp_dst_port = nlp_dst_port;
	}
	public int getNlp_oneway_port() {
		return nlp_oneway_port;
	}
	public void setNlp_oneway_port(int nlp_oneway_port) {
		this.nlp_oneway_port = nlp_oneway_port;
	}
	public String getNpe_description() {
		return npe_description;
	}
	public void setNpe_description(String npe_description) {
		this.npe_description = npe_description;
	}
	public String getNpp_description() {
		return npp_description;
	}
	public void setNpp_description(String npp_description) {
		this.npp_description = npp_description;
	}
}