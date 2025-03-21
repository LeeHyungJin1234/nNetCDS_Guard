package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcUser implements Serializable {
	//User
	int nsu_seq;
	//필수입력 사항
	String nsu_id;
	String nsu_pw;
    String nsu_pw_confirm;
	String nsu_name;
    String nsu_email_id;
    String nsu_email_server;
    
    //선택입력 사항
	String nsu_division;
	String nsu_position;
	String nsu_tel;
	String nsu_mobile;
	String nsu_desc;
	
	
    String nsu_prev_pw;
	String nsu_email;
	String nsu_reg_date;
	int nsu_secu_level;
	int nsu_login_failcnt;
	int nsu_lock_yn;
	String nsu_lock_date;
	int nsu_use_yn;
	String nsu_last_login;
	
	// 해쉬 기밀성 참조 값 
    String nsu_prev_salt;
    String nsu_salt;
	String salt;
	
	// 접근 가능 IP
	String nai_ip;
	String nai_name;
	int nai_use_yn;
	int nai_div;
	
	
	public int getNsu_seq() {
		return nsu_seq;
	}
	public void setNsu_seq(int nsu_seq) {
		this.nsu_seq = nsu_seq;
	}
	public String getNsu_id() {
		return nsu_id;
	}
	public void setNsu_id(String nsu_id) {
		this.nsu_id = nsu_id;
	}
	public String getNsu_pw() {
		return nsu_pw;
	}
	public void setNsu_pw(String nsu_pw) {
		this.nsu_pw = nsu_pw;
	}
	public String getNsu_pw_confirm() {
		return nsu_pw_confirm;
	}
	public void setNsu_pw_confirm(String nsu_pw_confirm) {
		this.nsu_pw_confirm = nsu_pw_confirm;
	}
	public String getNsu_name() {
		return nsu_name;
	}
	public void setNsu_name(String nsu_name) {
		this.nsu_name = nsu_name;
	}
	public String getNsu_email_id() {
		return nsu_email_id;
	}
	public void setNsu_email_id(String nsu_email_id) {
		this.nsu_email_id = nsu_email_id;
	}
	public String getNsu_email_server() {
		return nsu_email_server;
	}
	public void setNsu_email_server(String nsu_email_server) {
		this.nsu_email_server = nsu_email_server;
	}
	public String getNsu_division() {
		return nsu_division;
	}
	public void setNsu_division(String nsu_division) {
		this.nsu_division = nsu_division;
	}
	public String getNsu_position() {
		return nsu_position;
	}
	public void setNsu_position(String nsu_position) {
		this.nsu_position = nsu_position;
	}
	public String getNsu_tel() {
		return nsu_tel;
	}
	public void setNsu_tel(String nsu_tel) {
		this.nsu_tel = nsu_tel;
	}
	public String getNsu_mobile() {
		return nsu_mobile;
	}
	public void setNsu_mobile(String nsu_mobile) {
		this.nsu_mobile = nsu_mobile;
	}
	public String getNsu_desc() {
		return nsu_desc;
	}
	public void setNsu_desc(String nsu_desc) {
		this.nsu_desc = nsu_desc;
	}
	public String getNsu_prev_pw() {
		return nsu_prev_pw;
	}
	public void setNsu_prev_pw(String nsu_prev_pw) {
		this.nsu_prev_pw = nsu_prev_pw;
	}
	public String getNsu_email() {
		return nsu_email;
	}
	public void setNsu_email(String nsu_email) {
		this.nsu_email = nsu_email;
	}
	public String getNsu_reg_date() {
		return nsu_reg_date;
	}
	public void setNsu_reg_date(String nsu_reg_date) {
		this.nsu_reg_date = nsu_reg_date;
	}
	public int getNsu_secu_level() {
		return nsu_secu_level;
	}
	public void setNsu_secu_level(int nsu_secu_level) {
		this.nsu_secu_level = nsu_secu_level;
	}
	public int getNsu_login_failcnt() {
		return nsu_login_failcnt;
	}
	public void setNsu_login_failcnt(int nsu_login_failcnt) {
		this.nsu_login_failcnt = nsu_login_failcnt;
	}
	public int getNsu_lock_yn() {
		return nsu_lock_yn;
	}
	public void setNsu_lock_yn(int nsu_lock_yn) {
		this.nsu_lock_yn = nsu_lock_yn;
	}
	public String getNsu_lock_date() {
		return nsu_lock_date;
	}
	public void setNsu_lock_date(String nsu_lock_date) {
		this.nsu_lock_date = nsu_lock_date;
	}
	public int getNsu_use_yn() {
		return nsu_use_yn;
	}
	public void setNsu_use_yn(int nsu_use_yn) {
		this.nsu_use_yn = nsu_use_yn;
	}
	public String getNsu_last_login() {
		return nsu_last_login;
	}
	public void setNsu_last_login(String nsu_last_login) {
		this.nsu_last_login = nsu_last_login;
	}
	public String getNsu_prev_salt() {
		return nsu_prev_salt;
	}
	public void setNsu_prev_salt(String nsu_prev_salt) {
		this.nsu_prev_salt = nsu_prev_salt;
	}
	public String getNsu_salt() {
		return nsu_salt;
	}
	public void setNsu_salt(String nsu_salt) {
		this.nsu_salt = nsu_salt;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getNai_ip() {
		return nai_ip;
	}
	public void setNai_ip(String nai_ip) {
		this.nai_ip = nai_ip;
	}
	public String getNai_name() {
		return nai_name;
	}
	public void setNai_name(String nai_name) {
		this.nai_name = nai_name;
	}
	public int getNai_use_yn() {
		return nai_use_yn;
	}
	public void setNai_use_yn(int nai_use_yn) {
		this.nai_use_yn = nai_use_yn;
	}
	public int getNai_div() {
		return nai_div;
	}
	public void setNai_div(int nai_div) {
		this.nai_div = nai_div;
	}
}
