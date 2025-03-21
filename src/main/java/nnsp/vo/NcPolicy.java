package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcPolicy implements Serializable {
	int noi_seq;
	String noi_ip;
	String noi_obj_nm;
	int noi_gcode;
	int noi_div;
	int noi_use_yn;
	int noi_ip_type;
	String org_obj_nm;
	
	int nos_seq;
	int nos_port;
	String nos_service_nm;
	String nos_obj_nm;
	int nos_gcode;
	int nos_div;
	int nos_use_yn;
	
	int npa_seq;
	int npa_sip_gcode;
	int npa_dip_gcode;
	int npa_ssvc_gcode;
	int npa_dsvc_gcode;
	int npa_div;
	int npa_use_yn;
	
	String sip_objnm;
	String dip_objnm;
	String ssvc_objnm;
	String dsvc_objnm;
	
	String sip;
	String dip;
	int sport;
	int dport;
	
	int npc_no;
	String npc_name;
	
	int nps_seq;
	int ncp_seq;
	int nps_tx_intip;
	int nps_tx_intport;
	int nps_rx_intip;
	int nps_rx_intport;
	int nps_conts_ip;
	int nps_conts_port;
	int nps_elc_flag;
	int nps_use_yn;
	String nps_conf_file;
	int nps_resend;
	
	int ncp_div;
	String ncp_name;
	String ncp_file_name;
	String ncp_path;
	
	String tx_intip_objnm;
	String tx_intport_objnm;
	String rx_intip_objnm;
	String rx_intport_objnm;
	String conts_ip_objnm;
	String conts_port_objnm;
	
	String tx_intip;
	int tx_intport;
	String rx_intip;
	int rx_intport;
	String conts_ip;
	int conts_port;
	String elc_flag_text;
	
	int npcs_seq;
	String npcs_name;
	String npcs_header;
	String npcs_type;
	
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
	int nlp_tx_nic;
	int nlp_rx_nic;
		
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
	public String getNpcs_type() {
		return npcs_type;
	}
	public void setNpcs_type(String npcs_type) {
		this.npcs_type = npcs_type;
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
	public int getNps_resend() {
		return nps_resend;
	}
	public void setNps_resend(int nps_resend) {
		this.nps_resend = nps_resend;
	}
	public String getNcp_path() {
		return ncp_path;
	}
	public void setNcp_path(String ncp_path) {
		this.ncp_path = ncp_path;
	}
	public String getNcp_file_name() {
		return ncp_file_name;
	}
	public void setNcp_file_name(String ncp_file_name) {
		this.ncp_file_name = ncp_file_name;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public String getNps_conf_file() {
		return nps_conf_file;
	}
	public void setNps_conf_file(String nps_conf_file) {
		this.nps_conf_file = nps_conf_file;
	}
	public String getElc_flag_text() {
		return elc_flag_text;
	}
	public void setElc_flag_text(String elc_flag_text) {
		this.elc_flag_text = elc_flag_text;
	}
	public String getTx_intip_objnm() {
		return tx_intip_objnm;
	}
	public void setTx_intip_objnm(String tx_intip_objnm) {
		this.tx_intip_objnm = tx_intip_objnm;
	}
	public String getTx_intport_objnm() {
		return tx_intport_objnm;
	}
	public void setTx_intport_objnm(String tx_intport_objnm) {
		this.tx_intport_objnm = tx_intport_objnm;
	}
	public String getRx_intip_objnm() {
		return rx_intip_objnm;
	}
	public void setRx_intip_objnm(String rx_intip_objnm) {
		this.rx_intip_objnm = rx_intip_objnm;
	}
	public String getRx_intport_objnm() {
		return rx_intport_objnm;
	}
	public void setRx_intport_objnm(String rx_intport_objnm) {
		this.rx_intport_objnm = rx_intport_objnm;
	}
	public String getConts_ip_objnm() {
		return conts_ip_objnm;
	}
	public void setConts_ip_objnm(String conts_ip_objnm) {
		this.conts_ip_objnm = conts_ip_objnm;
	}
	public String getConts_port_objnm() {
		return conts_port_objnm;
	}
	public void setConts_port_objnm(String conts_port_objnm) {
		this.conts_port_objnm = conts_port_objnm;
	}
	public String getTx_intip() {
		return tx_intip;
	}
	public void setTx_intip(String tx_intip) {
		this.tx_intip = tx_intip;
	}
	public int getTx_intport() {
		return tx_intport;
	}
	public void setTx_intport(int tx_intport) {
		this.tx_intport = tx_intport;
	}
	public String getRx_intip() {
		return rx_intip;
	}
	public void setRx_intip(String rx_intip) {
		this.rx_intip = rx_intip;
	}
	public int getRx_intport() {
		return rx_intport;
	}
	public void setRx_intport(int rx_intport) {
		this.rx_intport = rx_intport;
	}
	public String getConts_ip() {
		return conts_ip;
	}
	public void setConts_ip(String conts_ip) {
		this.conts_ip = conts_ip;
	}
	public int getConts_port() {
		return conts_port;
	}
	public void setConts_port(int conts_port) {
		this.conts_port = conts_port;
	}
	public int getNcp_div() {
		return ncp_div;
	}
	public void setNcp_div(int ncp_div) {
		this.ncp_div = ncp_div;
	}
	public int getNps_seq() {
		return nps_seq;
	}
	public void setNps_seq(int nps_seq) {
		this.nps_seq = nps_seq;
	}
	public int getNcp_seq() {
		return ncp_seq;
	}
	public void setNcp_seq(int ncp_seq) {
		this.ncp_seq = ncp_seq;
	}
	public int getNps_tx_intip() {
		return nps_tx_intip;
	}
	public void setNps_tx_intip(int nps_tx_intip) {
		this.nps_tx_intip = nps_tx_intip;
	}
	public int getNps_tx_intport() {
		return nps_tx_intport;
	}
	public void setNps_tx_intport(int nps_tx_intport) {
		this.nps_tx_intport = nps_tx_intport;
	}
	public int getNps_rx_intip() {
		return nps_rx_intip;
	}
	public void setNps_rx_intip(int nps_rx_intip) {
		this.nps_rx_intip = nps_rx_intip;
	}
	public int getNps_rx_intport() {
		return nps_rx_intport;
	}
	public void setNps_rx_intport(int nps_rx_intport) {
		this.nps_rx_intport = nps_rx_intport;
	}
	public int getNps_conts_ip() {
		return nps_conts_ip;
	}
	public void setNps_conts_ip(int nps_conts_ip) {
		this.nps_conts_ip = nps_conts_ip;
	}
	public int getNps_conts_port() {
		return nps_conts_port;
	}
	public void setNps_conts_port(int nps_conts_port) {
		this.nps_conts_port = nps_conts_port;
	}
	public int getNps_elc_flag() {
		return nps_elc_flag;
	}
	public void setNps_elc_flag(int nps_elc_flag) {
		this.nps_elc_flag = nps_elc_flag;
	}
	public int getNps_use_yn() {
		return nps_use_yn;
	}
	public void setNps_use_yn(int nps_use_yn) {
		this.nps_use_yn = nps_use_yn;
	}
	public String getOrg_obj_nm() {
		return org_obj_nm;
	}
	public void setOrg_obj_nm(String org_obj_nm) {
		this.org_obj_nm = org_obj_nm;
	}
	public String getSip_objnm() {
		return sip_objnm;
	}
	public void setSip_objnm(String sip_objnm) {
		this.sip_objnm = sip_objnm;
	}
	public String getDip_objnm() {
		return dip_objnm;
	}
	public void setDip_objnm(String dip_objnm) {
		this.dip_objnm = dip_objnm;
	}
	public String getSsvc_objnm() {
		return ssvc_objnm;
	}
	public void setSsvc_objnm(String ssvc_objnm) {
		this.ssvc_objnm = ssvc_objnm;
	}
	public String getDsvc_objnm() {
		return dsvc_objnm;
	}
	public void setDsvc_objnm(String dsvc_objnm) {
		this.dsvc_objnm = dsvc_objnm;
	}
	public String getSip() {
		return sip;
	}
	public void setSip(String sip) {
		this.sip = sip;
	}
	public String getDip() {
		return dip;
	}
	public void setDip(String dip) {
		this.dip = dip;
	}
	public int getSport() {
		return sport;
	}
	public void setSport(int sport) {
		this.sport = sport;
	}
	public int getDport() {
		return dport;
	}
	public void setDport(int dport) {
		this.dport = dport;
	}
	public int getNoi_seq() {
		return noi_seq;
	}
	public void setNoi_seq(int noi_seq) {
		this.noi_seq = noi_seq;
	}
	public String getNoi_ip() {
		return noi_ip;
	}
	public void setNoi_ip(String noi_ip) {
		this.noi_ip = noi_ip;
	}
	public String getNoi_obj_nm() {
		return noi_obj_nm;
	}
	public void setNoi_obj_nm(String noi_obj_nm) {
		this.noi_obj_nm = noi_obj_nm;
	}
	public int getNoi_gcode() {
		return noi_gcode;
	}
	public void setNoi_gcode(int noi_gcode) {
		this.noi_gcode = noi_gcode;
	}
	public int getNoi_div() {
		return noi_div;
	}
	public void setNoi_div(int noi_div) {
		this.noi_div = noi_div;
	}
	public int getNoi_use_yn() {
		return noi_use_yn;
	}
	public void setNoi_use_yn(int noi_use_yn) {
		this.noi_use_yn = noi_use_yn;
	}
	public int getNoi_ip_type() {
		return noi_ip_type;
	}
	public void setNoi_ip_type(int noi_ip_type) {
		this.noi_ip_type = noi_ip_type;
	}
	public int getNos_seq() {
		return nos_seq;
	}
	public void setNos_seq(int nos_seq) {
		this.nos_seq = nos_seq;
	}
	public int getNos_port() {
		return nos_port;
	}
	public void setNos_port(int nos_port) {
		this.nos_port = nos_port;
	}
	public String getNos_service_nm() {
		return nos_service_nm;
	}
	public void setNos_service_nm(String nos_service_nm) {
		this.nos_service_nm = nos_service_nm;
	}
	public String getNos_obj_nm() {
		return nos_obj_nm;
	}
	public void setNos_obj_nm(String nos_obj_nm) {
		this.nos_obj_nm = nos_obj_nm;
	}
	public int getNos_gcode() {
		return nos_gcode;
	}
	public void setNos_gcode(int nos_gcode) {
		this.nos_gcode = nos_gcode;
	}
	public int getNos_div() {
		return nos_div;
	}
	public void setNos_div(int nos_div) {
		this.nos_div = nos_div;
	}
	public int getNos_use_yn() {
		return nos_use_yn;
	}
	public void setNos_use_yn(int nos_use_yn) {
		this.nos_use_yn = nos_use_yn;
	}
	public int getNpa_seq() {
		return npa_seq;
	}
	public void setNpa_seq(int npa_seq) {
		this.npa_seq = npa_seq;
	}
	public int getNpa_sip_gcode() {
		return npa_sip_gcode;
	}
	public void setNpa_sip_gcode(int npa_sip_gcode) {
		this.npa_sip_gcode = npa_sip_gcode;
	}
	public int getNpa_dip_gcode() {
		return npa_dip_gcode;
	}
	public void setNpa_dip_gcode(int npa_dip_gcode) {
		this.npa_dip_gcode = npa_dip_gcode;
	}
	public int getNpa_ssvc_gcode() {
		return npa_ssvc_gcode;
	}
	public void setNpa_ssvc_gcode(int npa_ssvc_gcode) {
		this.npa_ssvc_gcode = npa_ssvc_gcode;
	}
	public int getNpa_dsvc_gcode() {
		return npa_dsvc_gcode;
	}
	public void setNpa_dsvc_gcode(int npa_dsvc_gcode) {
		this.npa_dsvc_gcode = npa_dsvc_gcode;
	}
	public int getNpa_div() {
		return npa_div;
	}
	public void setNpa_div(int npa_div) {
		this.npa_div = npa_div;
	}
	public int getNpa_use_yn() {
		return npa_use_yn;
	}
	public void setNpa_use_yn(int npa_use_yn) {
		this.npa_use_yn = npa_use_yn;
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
	public int getNlp_tx_nic() {
		return nlp_tx_nic;
	}
	public void setNlp_tx_nic(int nlp_tx_nic) {
		this.nlp_tx_nic = nlp_tx_nic;
	}
	public int getNlp_rx_nic() {
		return nlp_rx_nic;
	}
	public void setNlp_rx_nic(int nlp_rx_nic) {
		this.nlp_rx_nic = nlp_rx_nic;
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
