package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcClientUser implements Serializable {
	int usr_idx;
	String usr_account;
	String usr_pwd;
	String usr_prev_pwd;
	String usr_salt;
	String usr_prev_salt;
	int  usr_status;
	String usr_regdate;
	String usr_lasttime;
	String usr_srcip;
	String usr_url;
	String usr_dstip;
	
	public int getUsr_idx() {
		return usr_idx;
	}
	public void setUsr_idx(int usr_idx) {
		this.usr_idx = usr_idx;
	}
	public String getUsr_account() {
		return usr_account;
	}
	public void setUsr_account(String usr_account) {
		this.usr_account = usr_account;
	}
	public String getUsr_pwd() {
		return usr_pwd;
	}
	public void setUsr_pwd(String usr_pwd) {
		this.usr_pwd = usr_pwd;
	}
	public String getUsr_prev_pwd() {
		return usr_prev_pwd;
	}
	public void setUsr_prev_pwd(String usr_prev_pwd) {
		this.usr_prev_pwd = usr_prev_pwd;
	}
	public String getUsr_salt() {
		return usr_salt;
	}
	public void setUsr_salt(String usr_salt) {
		this.usr_salt = usr_salt;
	}
	public String getUsr_prev_salt() {
		return usr_prev_salt;
	}
	public void setUsr_prev_salt(String usr_prev_salt) {
		this.usr_prev_salt = usr_prev_salt;
	}
	public int getUsr_status() {
		return usr_status;
	}
	public void setUsr_status(int usr_status) {
		this.usr_status = usr_status;
	}
	public String getUsr_regdate() {
		return usr_regdate;
	}
	public void setUsr_regdate(String usr_regdate) {
		this.usr_regdate = usr_regdate;
	}
	public String getUsr_lasttime() {
		return usr_lasttime;
	}
	public void setUsr_lasttime(String usr_lasttime) {
		this.usr_lasttime = usr_lasttime;
	}
	public String getUsr_srcip() {
		return usr_srcip;
	}
	public void setUsr_srcip(String usr_srcip) {
		this.usr_srcip = usr_srcip;
	}
	public String getUsr_url() {
		return usr_url;
	}
	public void setUsr_url(String usr_url) {
		this.usr_url = usr_url;
	}
	public String getUsr_dstip() {
		return usr_dstip;
	}
	public void setUsr_dstip(String usr_dstip) {
		this.usr_dstip = usr_dstip;
	}
}
