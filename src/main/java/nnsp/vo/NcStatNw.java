package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcStatNw implements Serializable {
	int nsr_seq;
	String nsr_date;
	String nsr_pg_fname;
	long nsr_byte;
	
	int nss_seq;
	String nss_date;
	String nss_pg_fname;
	long nss_byte;
	
	int nsrp_seq;
	String nsrp_date;
	String nsrp_pg_fname;
	int nsrp_byte;
	int nsrp_loss_yn;
	
	String ncp_name;
	
	String stat_date;
	int stat_sum;
	
	int nas_in_line1;
	int nas_in_line2;
	int nas_in_line3;
	int nas_tr_line1;
	int nas_tr_line2;
	
	int nsm_cpu;
	int nsm_ram;
	int nsm_hdd;
	
	int ncs_master_queue;
	int ncs_slave_queue;
	int ncs_master_out;
	int ncs_slave_out;
	
	String stat_sdate;
	String stat_edate;
	String stat_shour;
	String stat_ehour;
	String stat_sminute;
	String stat_eminute;
	
	String datetab;
	
	long ps_packets;
	String ps_packets_str;
	String ps_proto_sub_dec;
	String ps_strc_time;
	long total_packets;
	int ps_detect;
	String strPsDetect;
	String wl_name;
	
	
	public String getDatetab() {
		return datetab;
	}
	public void setDatetab(String datetab) {
		this.datetab = datetab;
	}
	public String getStat_shour() {
		return stat_shour;
	}
	public void setStat_shour(String stat_shour) {
		this.stat_shour = stat_shour;
	}
	public String getStat_ehour() {
		return stat_ehour;
	}
	public void setStat_ehour(String stat_ehour) {
		this.stat_ehour = stat_ehour;
	}
	public String getStat_sminute() {
		return stat_sminute;
	}
	public void setStat_sminute(String stat_sminute) {
		this.stat_sminute = stat_sminute;
	}
	public String getStat_eminute() {
		return stat_eminute;
	}
	public void setStat_eminute(String stat_eminute) {
		this.stat_eminute = stat_eminute;
	}
	public String getStat_sdate() {
		return stat_sdate;
	}
	public void setStat_sdate(String stat_sdate) {
		this.stat_sdate = stat_sdate;
	}
	public String getStat_edate() {
		return stat_edate;
	}
	public void setStat_edate(String stat_edate) {
		this.stat_edate = stat_edate;
	}
	public int getNcs_master_out() {
		return ncs_master_out;
	}
	public void setNcs_master_out(int ncs_master_out) {
		this.ncs_master_out = ncs_master_out;
	}
	public int getNcs_slave_out() {
		return ncs_slave_out;
	}
	public void setNcs_slave_out(int ncs_slave_out) {
		this.ncs_slave_out = ncs_slave_out;
	}
	public int getNcs_master_queue() {
		return ncs_master_queue;
	}
	public void setNcs_master_queue(int ncs_master_queue) {
		this.ncs_master_queue = ncs_master_queue;
	}
	public int getNcs_slave_queue() {
		return ncs_slave_queue;
	}
	public void setNcs_slave_queue(int ncs_slave_queue) {
		this.ncs_slave_queue = ncs_slave_queue;
	}
	public int getNsm_cpu() {
		return nsm_cpu;
	}
	public void setNsm_cpu(int nsm_cpu) {
		this.nsm_cpu = nsm_cpu;
	}
	public int getNsm_ram() {
		return nsm_ram;
	}
	public void setNsm_ram(int nsm_ram) {
		this.nsm_ram = nsm_ram;
	}
	public int getNsm_hdd() {
		return nsm_hdd;
	}
	public void setNsm_hdd(int nsm_hdd) {
		this.nsm_hdd = nsm_hdd;
	}
	public int getNas_in_line1() {
		return nas_in_line1;
	}
	public void setNas_in_line1(int nas_in_line1) {
		this.nas_in_line1 = nas_in_line1;
	}
	public int getNas_in_line2() {
		return nas_in_line2;
	}
	public void setNas_in_line2(int nas_in_line2) {
		this.nas_in_line2 = nas_in_line2;
	}
	public int getNas_in_line3() {
		return nas_in_line3;
	}
	public void setNas_in_line3(int nas_in_line3) {
		this.nas_in_line3 = nas_in_line3;
	}
	public int getNas_tr_line1() {
		return nas_tr_line1;
	}
	public void setNas_tr_line1(int nas_tr_line1) {
		this.nas_tr_line1 = nas_tr_line1;
	}
	public int getNas_tr_line2() {
		return nas_tr_line2;
	}
	public void setNas_tr_line2(int nas_tr_line2) {
		this.nas_tr_line2 = nas_tr_line2;
	}
	public String getStat_date() {
		return stat_date;
	}
	public void setStat_date(String stat_date) {
		this.stat_date = stat_date;
	}
	public int getStat_sum() {
		return stat_sum;
	}
	public void setStat_sum(int stat_sum) {
		this.stat_sum = stat_sum;
	}
	public String getNsr_pg_fname() {
		return nsr_pg_fname;
	}
	public void setNsr_pg_fname(String nsr_pg_fname) {
		this.nsr_pg_fname = nsr_pg_fname;
	}
	public String getNss_pg_fname() {
		return nss_pg_fname;
	}
	public void setNss_pg_fname(String nss_pg_fname) {
		this.nss_pg_fname = nss_pg_fname;
	}
	public String getNsrp_pg_fname() {
		return nsrp_pg_fname;
	}
	public void setNsrp_pg_fname(String nsrp_pg_fname) {
		this.nsrp_pg_fname = nsrp_pg_fname;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public int getNsr_seq() {
		return nsr_seq;
	}
	public void setNsr_seq(int nsr_seq) {
		this.nsr_seq = nsr_seq;
	}
	public String getNsr_date() {
		return nsr_date;
	}
	public void setNsr_date(String nsr_date) {
		this.nsr_date = nsr_date;
	}
	public long getNsr_byte() {
		return nsr_byte;
	}
	public void setNsr_byte(long nsr_byte) {
		this.nsr_byte = nsr_byte;
	}
	public int getNss_seq() {
		return nss_seq;
	}
	public void setNss_seq(int nss_seq) {
		this.nss_seq = nss_seq;
	}
	public String getNss_date() {
		return nss_date;
	}
	public void setNss_date(String nss_date) {
		this.nss_date = nss_date;
	}
	public long getNss_byte() {
		return nss_byte;
	}
	public void setNss_byte(long nss_byte) {
		this.nss_byte = nss_byte;
	}
	public int getNsrp_seq() {
		return nsrp_seq;
	}
	public void setNsrp_seq(int nsrp_seq) {
		this.nsrp_seq = nsrp_seq;
	}
	public String getNsrp_date() {
		return nsrp_date;
	}
	public void setNsrp_date(String nsrp_date) {
		this.nsrp_date = nsrp_date;
	}
	public int getNsrp_byte() {
		return nsrp_byte;
	}
	public void setNsrp_byte(int nsrp_byte) {
		this.nsrp_byte = nsrp_byte;
	}
	public int getNsrp_loss_yn() {
		return nsrp_loss_yn;
	}
	public void setNsrp_loss_yn(int nsrp_loss_yn) {
		this.nsrp_loss_yn = nsrp_loss_yn;
	}
	
	public long getPs_packets() {
		return ps_packets;
	}
	public void setPs_packets(long ps_packets) {
		this.ps_packets = ps_packets;
		this.ps_packets_str = nnsp.util.StringUtil.toNumFormat(ps_packets);
	}
	public String getPs_packets_str() {
		return ps_packets_str;
	}
	public String getPs_proto_sub_dec() {
		return ps_proto_sub_dec;
	}
	public void setPs_proto_sub_dec(String ps_proto_sub_dec) {
		this.ps_proto_sub_dec = ps_proto_sub_dec;
	}
	public String getPs_strc_time() {
		return ps_strc_time;
	}
	public void setPs_strc_time(String ps_strc_time) {
		this.ps_strc_time = ps_strc_time;
	}
	public long getTotal_packets() {
		return total_packets;
	}
	public void setTotal_packets(long total_packets) {
		this.total_packets = total_packets;
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
			this.strPsDetect = "악성코드";
	}
	public String getStrPsDetect() {
		return strPsDetect;
	}
	public String getWl_name() {
		return wl_name;
	}
	public void setWl_name(String wl_name) {
		this.wl_name = wl_name;
	}
}