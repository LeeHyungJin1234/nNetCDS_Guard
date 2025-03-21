package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcAudit implements Serializable {
	int nlm_seq;
	String nsu_id;
	String nlm_create_date;
	String nai_ip;
	String nlm_page;
	String nlm_param;
	String nlm_result;
	String nlm_risk_level;
	
	int nlp_seq;
	String nlp_create_date;
	String nlp_page;
	String nlp_param;
	String nlp_result;
	String nlp_risk_level;
	
	public int getNlm_seq() {
		return nlm_seq;
	}
	public void setNlm_seq(int nlm_seq) {
		this.nlm_seq = nlm_seq;
	}
	public String getNsu_id() {
		return nsu_id;
	}
	public void setNsu_id(String nsu_id) {
		this.nsu_id = nsu_id;
	}
	public String getNlm_create_date() {
		return nlm_create_date;
	}
	public void setNlm_create_date(String nlm_create_date) {
		this.nlm_create_date = nlm_create_date;
	}
	public String getNai_ip() {
		return nai_ip;
	}
	public void setNai_ip(String nai_ip) {
		this.nai_ip = nai_ip;
	}
	public String getNlm_page() {
		return nlm_page;
	}
	public void setNlm_page(String nlm_page) {
		this.nlm_page = nlm_page;
	}
	public String getNlm_param() {
		return nlm_param;
	}
	public void setNlm_param(String nlm_param) {
		this.nlm_param = nlm_param;
	}
	public String getNlm_result() {
		return nlm_result;
	}
	public void setNlm_result(String nlm_result) {
		this.nlm_result = nlm_result;
	}
	public String getNlm_risk_level() {
		return nlm_risk_level;
	}
	public void setNlm_risk_level(String nlm_risk_level) {
		this.nlm_risk_level = nlm_risk_level;
	}
	public int getNlp_seq() {
		return nlp_seq;
	}
	public void setNlp_seq(int nlp_seq) {
		this.nlp_seq = nlp_seq;
	}
	public String getNlp_create_date() {
		return nlp_create_date;
	}
	public void setNlp_create_date(String nlp_create_date) {
		this.nlp_create_date = nlp_create_date;
	}
	public String getNlp_page() {
		return nlp_page;
	}
	public void setNlp_page(String nlp_page) {
		this.nlp_page = nlp_page;
	}
	public String getNlp_param() {
		return nlp_param;
	}
	public void setNlp_param(String nlp_param) {
		this.nlp_param = nlp_param;
	}
	public String getNlp_result() {
		return nlp_result;
	}
	public void setNlp_result(String nlp_result) {
		this.nlp_result = nlp_result;
	}
	public String getNlp_risk_level() {
		return nlp_risk_level;
	}
	public void setNlp_risk_level(String nlp_risk_level) {
		this.nlp_risk_level = nlp_risk_level;
	}
	
	
	String nli_host_name;
	String nli_prog_name;
	String nli_message;
	String nli_result;
	String nli_risk_level;

	public String getNli_host_name() {
		return nli_host_name;
	}
	public void setNli_host_name(String nli_host_name) {
		this.nli_host_name = nli_host_name;
	}
	public String getNli_prog_name() {
		return nli_prog_name;
	}
	public void setNli_prog_name(String nli_prog_name) {
		this.nli_prog_name = nli_prog_name;
	}
	public String getNli_message() {
		return nli_message;
	}
	public void setNli_message(String nli_message) {
		this.nli_message = nli_message;
	}
	public String getNli_result() {
		return nli_result;
	}
	public void setNli_result(String nli_result) {
		this.nli_result = nli_result;
	}
	public String getNli_risk_level() {
		return nli_risk_level;
	}
	public void setNli_risk_level(String nli_risk_level) {
		this.nli_risk_level = nli_risk_level;
	}
	
}
