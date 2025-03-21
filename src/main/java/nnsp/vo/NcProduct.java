package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcProduct implements Serializable {
	String ncpd_name;
	String ncpd_version;
	String ncpd_hw_model;
	String ncpd_model;
	String ncpd_sn;
	String ncpd_tel;
	String ncpd_date;
	String ncpd_desc;
	String ncpd_reg;
	int ncpd_div;
	String ncpd_key;
	boolean ncpd_setup;
	
	public String getNcpd_name() {
		return ncpd_name;
	}
	public void setNcpd_name(String ncpd_name) {
		this.ncpd_name = ncpd_name;
	}
	public String getNcpd_version() {
		return ncpd_version;
	}
	public void setNcpd_version(String ncpd_version) {
		this.ncpd_version = ncpd_version;
	}
	public String getNcpd_hw_model() {
		return ncpd_hw_model;
	}
	public void setNcpd_hw_model(String ncpd_hw_model) {
		this.ncpd_hw_model = ncpd_hw_model;
	}
	public String getNcpd_model() {
		return ncpd_model;
	}
	public void setNcpd_model(String ncpd_model) {
		this.ncpd_model = ncpd_model;
	}
	public String getNcpd_sn() {
		return ncpd_sn;
	}
	public void setNcpd_sn(String ncpd_sn) {
		this.ncpd_sn = ncpd_sn;
	}

	public String getNcpd_tel() {
		return ncpd_tel;
	}
	public void setNcpd_tel(String ncpd_tel) {
		this.ncpd_tel = ncpd_tel;
	}
	public String getNcpd_date() {
		return ncpd_date;
	}
	public void setNcpd_date(String ncpd_date) {
		this.ncpd_date = ncpd_date;
	}
	public String getNcpd_desc() {
		return ncpd_desc;
	}
	public void setNcpd_desc(String ncpd_desc) {
		this.ncpd_desc = ncpd_desc;
	}
	public String getNcpd_reg() {
		return ncpd_reg;
	}
	public void setNcpd_reg(String ncpd_reg) {
		this.ncpd_reg = ncpd_reg;
	}
	public int getNcpd_div() {
		return ncpd_div;
	}
	public void setNcpd_div(int ncpd_div) {
		this.ncpd_div = ncpd_div;
	}
	public String getNcpd_key() {
		return ncpd_key;
	}
	public void setNcpd_key(String ncpd_key) {
		this.ncpd_key = ncpd_key;
	}
	public boolean isNcpd_setup() {
		return ncpd_setup;
	}
	public void setNcpd_setup(boolean ncpd_setup) {
		this.ncpd_setup = ncpd_setup;
	}
}