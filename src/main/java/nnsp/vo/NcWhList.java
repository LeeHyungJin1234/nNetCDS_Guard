package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcWhList implements Serializable {
	//Protocol List
	int npl_id;
	String npl_name;
	String npl_desc;
	
	public int getNpl_id() {
		return npl_id;
	}
	public void setNpl_id(int npl_id) {
		this.npl_id = npl_id;
	}
	public String getNpl_name() {
		return npl_name;
	}
	public void setNpl_name(String npl_name) {
		this.npl_name = npl_name;
	}
	public String getNpl_desc() {
		return npl_desc;
	}
	public void setNpl_desc(String npl_desc) {
		this.npl_desc = npl_desc;
	}
	
	//Policy Level
	int nplv_id;
	String nplv_name;
	
	public int getNplv_id() {
		return nplv_id;
	}
	public void setNplv_id(int nplv_id) {
		this.nplv_id = nplv_id;
	}
	public String getNplv_name() {
		return nplv_name;
	}
	public void setNplv_name(String nplv_name) {
		this.nplv_name = nplv_name;
	}

	//GroupList
	int ngl_id;
	String ngl_group_name;
	String ngl_group_desc;
	int ngl_group_stat;
	
	public int getNgl_id() {
		return ngl_id;
	}
	public void setNgl_id(int ngl_id) {
		this.ngl_id = ngl_id;
	}
	public String getNgl_group_name() {
		return ngl_group_name;
	}
	public void setNgl_group_name(String ngl_group_name) {
		this.ngl_group_name = ngl_group_name;
	}
	public String getNgl_group_desc() {
		return ngl_group_desc;
	}
	public void setNgl_group_desc(String ngl_group_desc) {
		this.ngl_group_desc = ngl_group_desc;
	}
	public int getNgl_group_stat() {
		return ngl_group_stat;
	}
	public void setNgl_group_stat(int ngl_group_stat) {
		this.ngl_group_stat = ngl_group_stat;
	}
	
	
	//host_type
	int nht_seq;
	int nht_id;
	String nht_name;
	
	public int getNht_seq() {
		return nht_seq;
	}
	public void setNht_seq(int nht_seq) {
		this.nht_seq = nht_seq;
	}
	public int getNht_id() {
		return nht_id;
	}
	public void setNht_id(int nht_id) {
		this.nht_id = nht_id;
	}
	public String getNht_name() {
		return nht_name;
	}
	public void setNht_name(String nht_name) {
		this.nht_name = nht_name;
	}
	
	

	// white List
	long wl_id;
	String wl_name;
	String wl_npl_id;
	String wl_npl_name;
	String wl_nplv_id;
	String wl_nplv_name;
	String wl_src_host_type;
	String wl_dst_host_type;
	String wl_src_port_type;
	String wl_dst_port_type;
	String wl_src_ip;
	String wl_src_eip;
	String wl_dst_ip;
	String wl_dst_eip;
	String wl_src_port;
	String wl_dst_port;
	String wl_regdttm;
	int wl_use_yn;
	
	public long getWl_id() {
		return wl_id;
	}
	public void setWl_id(long wl_id) {
		this.wl_id = wl_id;
	}
	public String getWl_name() {
		return wl_name;
	}
	public void setWl_name(String wl_name) {
		this.wl_name = wl_name;
	}
	public String getWl_npl_id() {
		return wl_npl_id;
	}
	public void setWl_npl_id(String wl_npl_id) {
		this.wl_npl_id = wl_npl_id;
	}
	public String getWl_npl_name() {
		return wl_npl_name;
	}
	public void setWl_npl_name(String wl_npl_name) {
		this.wl_npl_name = wl_npl_name;
	}
	public String getWl_nplv_id() {
		return wl_nplv_id;
	}
	public void setWl_nplv_id(String wl_nplv_id) {
		this.wl_nplv_id = wl_nplv_id;
	}
	public String getWl_nplv_name() {
		return wl_nplv_name;
	}
	public void setWl_nplv_name(String wl_nplv_name) {
		this.wl_nplv_name = wl_nplv_name;
	}
	public String getWl_src_host_type() {
		return wl_src_host_type;
	}
	public void setWl_src_host_type(String wl_src_host_type) {
		this.wl_src_host_type = wl_src_host_type;
	}
	public String getWl_dst_host_type() {
		return wl_dst_host_type;
	}
	public void setWl_dst_host_type(String wl_dst_host_type) {
		this.wl_dst_host_type = wl_dst_host_type;
	}
	public String getWl_src_port_type() {
		return wl_src_port_type;
	}
	public void setWl_src_port_type(String wl_src_port_type) {
		this.wl_src_port_type = wl_src_port_type;
	}
	public String getWl_dst_port_type() {
		return wl_dst_port_type;
	}
	public void setWl_dst_port_type(String wl_dst_port_type) {
		this.wl_dst_port_type = wl_dst_port_type;
	}
	public String getWl_src_ip() {
		return wl_src_ip;
	}
	public void setWl_src_ip(String wl_src_ip) {
		this.wl_src_ip = wl_src_ip;
	}
	public String getWl_src_eip() {
		return wl_src_eip;
	}
	public void setWl_src_eip(String wl_src_eip) {
		this.wl_src_eip = wl_src_eip;
	}
	public String getWl_dst_ip() {
		return wl_dst_ip;
	}
	public void setWl_dst_ip(String wl_dst_ip) {
		this.wl_dst_ip = wl_dst_ip;
	}
	public String getWl_dst_eip() {
		return wl_dst_eip;
	}
	public void setWl_dst_eip(String wl_dst_eip) {
		this.wl_dst_eip = wl_dst_eip;
	}
	public String getWl_src_port() {
		return wl_src_port;
	}
	public void setWl_src_port(String wl_src_port) {
		this.wl_src_port = wl_src_port;
	}
	public String getWl_dst_port() {
		return wl_dst_port;
	}
	public void setWl_dst_port(String wl_dst_port) {
		this.wl_dst_port = wl_dst_port;
	}
	public String getWl_regdttm() {
		return wl_regdttm;
	}
	public void setWl_regdttm(String wl_regdttm) {
		this.wl_regdttm = wl_regdttm;
	}
	public int getWl_use_yn() {
		return wl_use_yn;
	}
	public void setWl_use_yn(int wl_use_yn) {
		this.wl_use_yn = wl_use_yn;
	}
	
	//Row Number
	int rowNumber;
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
