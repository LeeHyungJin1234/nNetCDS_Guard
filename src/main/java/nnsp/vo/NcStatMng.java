package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcStatMng implements Serializable {
	int nsm_seq;
	int nsu_seq;
	String nsm_create_date;
	String nai_ip;
	String nsm_page;
	String nsm_param;
	String nsm_result;
	String nsm_risk_level;
	int nsm_cnt;
	String nsm_reg_date;
	
	public int getNsm_seq() {
		return nsm_seq;
	}
	public void setNsm_seq(int nsm_seq) {
		this.nsm_seq = nsm_seq;
	}
	public int getNsu_seq() {
		return nsu_seq;
	}
	public void setNsu_seq(int nsu_seq) {
		this.nsu_seq = nsu_seq;
	}
	public String getNsm_create_date() {
		return nsm_create_date;
	}
	public void setNsm_create_date(String nsm_create_date) {
		this.nsm_create_date = nsm_create_date;
	}
	public String getNai_ip() {
		return nai_ip;
	}
	public void setNai_ip(String nai_ip) {
		this.nai_ip = nai_ip;
	}
	public String getNsm_page() {
		return nsm_page;
	}
	public void setNsm_page(String nsm_page) {
		this.nsm_page = nsm_page;
	}
	public String getNsm_param() {
		return nsm_param;
	}
	public void setNsm_param(String nsm_param) {
		this.nsm_param = nsm_param;
	}
	public String getNsm_result() {
		return nsm_result;
	}
	public void setNsm_result(String nsm_result) {
		this.nsm_result = nsm_result;
	}
	public String getNsm_risk_level() {
		return nsm_risk_level;
	}
	public void setNsm_risk_level(String nsm_risk_level) {
		this.nsm_risk_level = nsm_risk_level;
	}
	public int getNsm_cnt() {
		return nsm_cnt;
	}
	public void setNsm_cnt(int nsm_cnt) {
		this.nsm_cnt = nsm_cnt;
	}
	public String getNsm_reg_date() {
		return nsm_reg_date;
	}
	public void setNsm_reg_date(String nsm_reg_date) {
		this.nsm_reg_date = nsm_reg_date;
	}
}
