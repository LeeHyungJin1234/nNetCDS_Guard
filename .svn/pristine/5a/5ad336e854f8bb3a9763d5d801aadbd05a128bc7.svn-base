package nnsp.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import nnsp.util.MessageUtil;

@SuppressWarnings("serial")
public class NcLog implements Serializable {
	LOG_TYPE logType;

	private transient Map<String, String> RESULT_TYPE = Stream.of(new String[][] {
		{ null, "" },
		{ "", "" },
		{ "잠김", MessageUtil.getMessage("query.locked") },
		{ "성공", MessageUtil.getMessage("query.success") },
		{ "실패", MessageUtil.getMessage("query.failure") },
		{ "S", "S" },
		{ "F", "F" }
	}).collect(Collectors.collectingAndThen(
		Collectors.toMap(data -> data[0], data -> data[1]),
		Collections::<String, String> unmodifiableMap));

	private transient Map<String, String> RISK_LEVEL = Stream.of(new String[][] {
		{ null, "" },
		{ "", "" },
		{ "정보", MessageUtil.getMessage("query.info") },
		{ "위험", MessageUtil.getMessage("query.risk") },
		{ "경고", MessageUtil.getMessage("query.warn") },
		{ "I", "I" },
		{ "W", "W" },
		{ "R", "R" }
	}).collect(Collectors.collectingAndThen(
		Collectors.toMap(data -> data[0], data -> data[1]),
		Collections::<String, String> unmodifiableMap));

	public static String getResultSF(int result) {
		return result > 0 ? "S" : "F";
	}

	public static String getResultsf(int result) {
		return result > 0 ? "success" : "fail";
	}

	public static String getResultTD(int result) {
		return result > 0 ? "true" : "DB Error";
	}

	public LOG_TYPE getLogType() {
		return logType;
	}
	
	int npl_no;
	String npl_name;
	
	long nlm_index;
	int nlm_seq;
	int nsu_seq;
	String nsu_id;
	String nlm_create_date;
	String nai_ip;
	String nlm_page;
	String nlm_param;
	String nlm_result;
	String nlm_risk_level;
	String nlm_reg_date;
	String nlm_create_sdate;
	String nlm_create_edate;
	String nlm_reg_sdate;
	String nlm_reg_edate;
	
	String log_type;
	
	long nlp_index;
	int nlp_seq;
	String nlp_create_date;
	String nlp_page;
	String nlp_param;
	String nlp_result;
	String nlp_risk_level;
	String nlp_reg_date;
	String nlp_create_sdate;
	String nlp_create_edate;
	
	long nle_index;
	int nle_seq;
	String nle_create_date;
	String nle_host_name;
	String nle_risk_level;
	String nle_prog_name;
	String nle_npl_name;
	String nle_message;
	String nle_create_sdate;
	String nle_create_edate;
	String ncp_name; // 프로그램 용도
	String ncp_div;
	int nle_div;
	String nle_div_str;
	String nle_result;
	
	long nla_index;
	int nla_seq;
	String nla_create_date;
	String nla_npl_name;
	String nla_host_name;
	String nla_risk_level;
	String nla_src_ip;
	String nla_dst_ip;
	String nla_src_port;
	String nla_dst_port;
	String nla_protocol;
	String nla_access_type;
	String nla_create_sdate;
	String nla_create_edate;
	String nla_div2;
	int nla_div;
	String nla_div_str;
	String nla_dec;
	String nla_agent;
	String nla_service;
	String nla_file_name;
	String nla_file_size;
	String nla_send_status;
	String nla_virus;
	
	long npa_seq;
	int cnt;
	int scnt;
	int nla_duplicate;
	
	int sip;
	int ssvc;
	int spa;
	int sps;
	int rip;
	int rsvc;
	int rpa;
	int rps;
	
	int acc;
	int accip;
	int login;
	int snw;
	int rnw;
	int log;
	int mail;
	int integ;
	
	int fwdig;
	int fwdpg;
	int rvsig;
	int rvspg;
	int prodt;
	int conts;
	
	long nli_index;
	int nli_seq;
	String nli_create_date;
	String nli_host_name;
	String nli_risk_level;
	String nli_prog_name;
	String nli_message;
	String nli_create_sdate;
	String nli_create_edate;
	int nli_div;
	String nli_div_str;
	String nli_result;
	
	int nler_seq;
	String nler_create_date;
	String nler_host_name;
	String nler_risk_level;
	String nler_prog_name;
	String nler_message;
	String nler_create_sdate;
	String nler_create_edate;
	int nler_div;
	String nler_div_str;
	String nler_result;
	
	int nlpt_seq;
	String nlpt_create_date;
	String nlpt_host_name;
	String nlpt_risk_level;
	String nlpt_prog_name;
	String nlpt_message;
	String nlpt_create_sdate;
	String nlpt_create_edate;
	int nlpt_div;
	String nlpt_div_str;
	String nlpt_result;
	
	int ints;
	int intr;
	int exts;
	int extr;
	
	int internal;
	int external;
	
	String nla_message;
	
	String datetab;
	
	public void setLogType(LOG_TYPE logType) {
		this.logType = logType;
	}

	public enum LOG_TYPE {
		MANAGER,
		POLICY,
		EVENT,
		ACCESS,
		PROTECTION,
		INTEGRITY,
		ERROR
	}
	
	public int getNpl_no() {
		return npl_no;
	}
	public void setNpl_no(int npl_no) {
		this.npl_no = npl_no;
	}
	public String getNpl_name() {
		return npl_name;
	}
	public void setNpl_name(String npl_name) {
		this.npl_name = npl_name;
	}
	public String getDatetab() {
		return datetab;
	}
	public void setDatetab(String datetab) {
		this.datetab = datetab;
	}
	public int getInternal() {
		return internal;
	}
	public void setInternal(int internal) {
		this.internal = internal;
	}
	public int getExternal() {
		return external;
	}
	public void setExternal(int external) {
		this.external = external;
	}
	public String getNla_div2() {
		return nla_div2;
	}
	public void setNla_div2(String nla_div2) {
		this.nla_div2 = nla_div2;
	}
	public int getNla_div() {
		return nla_div;
	}
	public void setNla_div(int nla_div) {
		this.nla_div = nla_div;
	}
	public int getNli_div() {
		return nli_div;
	}
	public void setNli_div(int nli_div) {
		this.nli_div = nli_div;
	}
	public int getNler_div() {
		return nler_div;
	}
	public void setNler_div(int nler_div) {
		this.nler_div = nler_div;
	}
	public int getNle_div() {
		return nle_div;
	}
	public void setNle_div(int nle_div) {
		this.nle_div = nle_div;
		this.nle_div_str = Integer.toString(nle_div);
		if(nle_div==1) {
			this.nle_div_str=MessageUtil.getMessage("log.div1");
		}
		else if(nle_div==2) {
			this.nle_div_str=MessageUtil.getMessage("log.div2");
		}
	}
	public int getFwdig() {
		return fwdig;
	}
	public void setFwdig(int fwdig) {
		this.fwdig = fwdig;
	}
	public int getFwdpg() {
		return fwdpg;
	}
	public void setFwdpg(int fwdpg) {
		this.fwdpg = fwdpg;
	}
	public int getRvsig() {
		return rvsig;
	}
	public void setRvsig(int rvsig) {
		this.rvsig = rvsig;
	}
	public int getRvspg() {
		return rvspg;
	}
	public void setRvspg(int rvspg) {
		this.rvspg = rvspg;
	}
	public int getProdt() {
		return prodt;
	}
	public void setProdt(int prodt) {
		this.prodt = prodt;
	}
	public int getConts() {
		return conts;
	}
	public void setConts(int conts) {
		this.conts = conts;
	}
	public int getInts() {
		return ints;
	}
	public void setInts(int ints) {
		this.ints = ints;
	}
	public int getIntr() {
		return intr;
	}
	public void setIntr(int intr) {
		this.intr = intr;
	}
	public int getExts() {
		return exts;
	}
	public void setExts(int exts) {
		this.exts = exts;
	}
	public int getExtr() {
		return extr;
	}
	public void setExtr(int extr) {
		this.extr = extr;
	}
	public String getNcp_div() {
		return ncp_div;
	}
	public void setNcp_div(String ncp_div) {
		this.ncp_div = ncp_div;
	}
	public int getNli_seq() {
		return nli_seq;
	}
	public void setNli_seq(int nli_seq) {
		this.nli_seq = nli_seq;
	}
	public String getNli_create_date() {
		return nli_create_date;
	}
	public void setNli_create_date(String nli_create_date) {
		this.nli_create_date = nli_create_date;
	}
	public String getNli_host_name() {
		return nli_host_name;
	}
	public void setNli_host_name(String nli_host_name) {
		this.nli_host_name = nli_host_name;
	}
	public String getNli_risk_level() {
		return nli_risk_level;
	}
	public void setNli_risk_level(String nli_risk_level) {
		this.nli_risk_level = RISK_LEVEL.get(nli_risk_level);
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
	public String getNli_create_sdate() {
		return nli_create_sdate;
	}
	public void setNli_create_sdate(String nli_create_sdate) {
		this.nli_create_sdate = nli_create_sdate;
	}
	public String getNli_create_edate() {
		return nli_create_edate;
	}
	public void setNli_create_edate(String nli_create_edate) {
		this.nli_create_edate = nli_create_edate;
	}
	public int getNler_seq() {
		return nler_seq;
	}
	public void setNler_seq(int nler_seq) {
		this.nler_seq = nler_seq;
	}
	public String getNler_create_date() {
		return nler_create_date;
	}
	public void setNler_create_date(String nler_create_date) {
		this.nler_create_date = nler_create_date;
	}
	public String getNler_host_name() {
		return nler_host_name;
	}
	public void setNler_host_name(String nler_host_name) {
		this.nler_host_name = nler_host_name;
	}
	public String getNler_risk_level() {
		return RISK_LEVEL.get(nler_risk_level);
	}
	public void setNler_risk_level(String nler_risk_level) {
		this.nler_risk_level = nler_risk_level;
	}
	public String getNler_prog_name() {
		return nler_prog_name;
	}
	public void setNler_prog_name(String nler_prog_name) {
		this.nler_prog_name = nler_prog_name;
	}
	public String getNler_message() {
		return nler_message;
	}
	public void setNler_message(String nler_message) {
		this.nler_message = nler_message;
	}
	public String getNler_create_sdate() {
		return nler_create_sdate;
	}
	public void setNler_create_sdate(String nler_create_sdate) {
		this.nler_create_sdate = nler_create_sdate;
	}
	public String getNler_create_edate() {
		return nler_create_edate;
	}
	public void setNler_create_edate(String nler_create_edate) {
		this.nler_create_edate = nler_create_edate;
	}
	public int getAcc() {
		return acc;
	}
	public void setAcc(int acc) {
		this.acc = acc;
	}
	public int getAccip() {
		return accip;
	}
	public void setAccip(int accip) {
		this.accip = accip;
	}
	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}
	public int getSnw() {
		return snw;
	}
	public void setSnw(int snw) {
		this.snw = snw;
	}
	public int getRnw() {
		return rnw;
	}
	public void setRnw(int rnw) {
		this.rnw = rnw;
	}
	public int getLog() {
		return log;
	}
	public void setLog(int log) {
		this.log = log;
	}
	public int getMail() {
		return mail;
	}
	public void setMail(int mail) {
		this.mail = mail;
	}
	public int getInteg() {
		return integ;
	}
	public void setInteg(int integ) {
		this.integ = integ;
	}
	public int getSip() {
		return sip;
	}
	public void setSip(int sip) {
		this.sip = sip;
	}
	public int getSsvc() {
		return ssvc;
	}
	public void setSsvc(int ssvc) {
		this.ssvc = ssvc;
	}
	public int getSpa() {
		return spa;
	}
	public void setSpa(int spa) {
		this.spa = spa;
	}
	public int getSps() {
		return sps;
	}
	public void setSps(int sps) {
		this.sps = sps;
	}
	public int getRip() {
		return rip;
	}
	public void setRip(int rip) {
		this.rip = rip;
	}
	public int getRsvc() {
		return rsvc;
	}
	public void setRsvc(int rsvc) {
		this.rsvc = rsvc;
	}
	public int getRpa() {
		return rpa;
	}
	public void setRpa(int rpa) {
		this.rpa = rpa;
	}
	public int getRps() {
		return rps;
	}
	public void setRps(int rps) {
		this.rps = rps;
	}
	public int getScnt() {
		return scnt;
	}
	public void setScnt(int scnt) {
		this.scnt = scnt;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getNlm_seq() {
		return nlm_seq;
	}
	public void setNlm_seq(int nlm_seq) {
		this.nlm_seq = nlm_seq;
	}
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
		this.nlm_result = RESULT_TYPE.get(nlm_result);
	}
	public String getNlm_risk_level() {
		return nlm_risk_level;
	}
	public void setNlm_risk_level(String nlm_risk_level) {
		this.nlm_risk_level = RISK_LEVEL.get(nlm_risk_level);
	}
	public String getNlm_reg_date() {
		return nlm_reg_date;
	}
	public void setNlm_reg_date(String nlm_reg_date) {
		this.nlm_reg_date = nlm_reg_date;
	}
	public String getNlm_create_sdate() {
		return nlm_create_sdate;
	}
	public void setNlm_create_sdate(String nlm_create_sdate) {
		this.nlm_create_sdate = nlm_create_sdate;
	}
	public String getNlm_create_edate() {
		return nlm_create_edate;
	}
	public void setNlm_create_edate(String nlm_create_edate) {
		this.nlm_create_edate = nlm_create_edate;
	}
	public String getNlm_reg_sdate() {
		return nlm_reg_sdate;
	}
	public void setNlm_reg_sdate(String nlm_reg_sdate) {
		this.nlm_reg_sdate = nlm_reg_sdate;
	}
	public String getNlm_reg_edate() {
		return nlm_reg_edate;
	}
	public void setNlm_reg_edate(String nlm_reg_edate) {
		this.nlm_reg_edate = nlm_reg_edate;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
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
		this.nlp_result = RESULT_TYPE.get(nlp_result);
	}
	public String getNlp_risk_level() {
		return nlp_risk_level;
	}
	public void setNlp_risk_level(String nlp_risk_level) {
		this.nlp_risk_level = RISK_LEVEL.get(nlp_risk_level);
	}
	public String getNlp_reg_date() {
		return nlp_reg_date;
	}
	public void setNlp_reg_date(String nlp_reg_date) {
		this.nlp_reg_date = nlp_reg_date;
	}
	public String getNlp_create_sdate() {
		return nlp_create_sdate;
	}
	public void setNlp_create_sdate(String nlp_create_sdate) {
		this.nlp_create_sdate = nlp_create_sdate;
	}
	public String getNlp_create_edate() {
		return nlp_create_edate;
	}
	public void setNlp_create_edate(String nlp_create_edate) {
		this.nlp_create_edate = nlp_create_edate;
	}
	public int getNle_seq() {
		return nle_seq;
	}
	public void setNle_seq(int nle_seq) {
		this.nle_seq = nle_seq;
	}
	public String getNle_create_date() {
		return nle_create_date;
	}
	public void setNle_create_date(String nle_create_date) {
		this.nle_create_date = nle_create_date;
	}
	public String getNle_host_name() {
		return nle_host_name;
	}
	public void setNle_host_name(String nle_host_name) {
		this.nle_host_name = nle_host_name;
	}
	public String getNle_risk_level() {
		return nle_risk_level;
	}
	public void setNle_risk_level(String nle_risk_level) {
		this.nle_risk_level = RISK_LEVEL.get(nle_risk_level);
	}
	public String getNle_prog_name() {
		return nle_prog_name;
	}
	public void setNle_prog_name(String nle_prog_name) {
		this.nle_prog_name = nle_prog_name;
	}
	public String getNle_message() {
		return nle_message;
	}
	public void setNle_message(String nle_message) {
		this.nle_message = nle_message;
	}
	public String getNle_create_sdate() {
		return nle_create_sdate;
	}
	public void setNle_create_sdate(String nle_create_sdate) {
		this.nle_create_sdate = nle_create_sdate;
	}
	public String getNle_create_edate() {
		return nle_create_edate;
	}
	public void setNle_create_edate(String nle_create_edate) {
		this.nle_create_edate = nle_create_edate;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public int getNla_seq() {
		return nla_seq;
	}
	public void setNla_seq(int nla_seq) {
		this.nla_seq = nla_seq;
	}
	public String getNla_create_date() {
		return nla_create_date;
	}
	public void setNla_create_date(String nla_create_date) {
		this.nla_create_date = nla_create_date;
	}
	public String getNla_host_name() {
		return nla_host_name;
	}
	public void setNla_host_name(String nla_host_name) {
		this.nla_host_name = nla_host_name;
	}
	public String getNla_risk_level() {
		return nla_risk_level;
	}
	public void setNla_risk_level(String nla_risk_level) {
		this.nla_risk_level = RISK_LEVEL.get(nla_risk_level);
	}
	public String getNla_src_ip() {
		return nla_src_ip;
	}
	public void setNla_src_ip(String nla_src_ip) {
		this.nla_src_ip = nla_src_ip;
	}
	public String getNla_dst_ip() {
		return nla_dst_ip;
	}
	public void setNla_dst_ip(String nla_dst_ip) {
		this.nla_dst_ip = nla_dst_ip;
	}
	public String getNla_src_port() {
		return nla_src_port;
	}
	public void setNla_src_port(String nla_src_port) {
		this.nla_src_port = nla_src_port;
	}
	public String getNla_dst_port() {
		return nla_dst_port;
	}
	public void setNla_dst_port(String nla_dst_port) {
		this.nla_dst_port = nla_dst_port;
	}
	public String getNla_protocol() {
		return nla_protocol;
	}
	public void setNla_protocol(String nla_protocol) {
		this.nla_protocol = nla_protocol;
	}
	public String getNla_access_type() {
		return nla_access_type;
	}
	public void setNla_access_type(String nla_access_type) {
		this.nla_access_type = nla_access_type;
	}
	public String getNla_create_sdate() {
		return nla_create_sdate;
	}
	public void setNla_create_sdate(String nla_create_sdate) {
		this.nla_create_sdate = nla_create_sdate;
	}
	public String getNla_create_edate() {
		return nla_create_edate;
	}
	public void setNla_create_edate(String nla_create_edate) {
		this.nla_create_edate = nla_create_edate;
	}
	public long getNlm_index() {
		return nlm_index;
	}

	public void setNlm_index(long nlm_index) {
		this.nlm_index = nlm_index;
	}

	public long getNlp_index() {
		return nlp_index;
	}

	public void setNlp_index(long nlp_index) {
		this.nlp_index = nlp_index;
	}

	public long getNle_index() {
		return nle_index;
	}

	public void setNle_index(long nle_index) {
		this.nle_index = nle_index;
	}

	public String getNle_npl_name() {
		return nle_npl_name;
	}

	public void setNle_npl_name(String nle_npl_name) {
		this.nle_npl_name = nle_npl_name;
	}

	public String getNle_div_str() {
		return nle_div_str;
	}

	public void setNle_div_str(String nle_div_str) {
		this.nle_div_str = nle_div_str;
	}

	public String getNle_result() {
		return nle_result;
	}

	public void setNle_result(String nle_result) {
		this.nle_result = nle_result;
	}

	public long getNla_index() {
		return nla_index;
	}

	public void setNla_index(long nla_index) {
		this.nla_index = nla_index;
	}

	public String getNla_npl_name() {
		return nla_npl_name;
	}

	public void setNla_npl_name(String nla_npl_name) {
		this.nla_npl_name = nla_npl_name;
	}

	public String getNla_div_str() {
		return nla_div_str;
	}

	public void setNla_div_str(String nla_div_str) {
		this.nla_div_str = nla_div_str;
	}

	public String getNla_dec() {
		return nla_dec;
	}

	public void setNla_dec(String nla_dec) {
		this.nla_dec = nla_dec;
	}

	public String getNla_agent() {
		return nla_agent;
	}

	public void setNla_agent(String nla_agent) {
		this.nla_agent = nla_agent;
	}

	public String getNla_service() {
		return nla_service;
	}

	public void setNla_service(String nla_service) {
		this.nla_service = nla_service;
	}

	public String getNla_file_name() {
		return nla_file_name;
	}

	public void setNla_file_name(String nla_file_name) {
		this.nla_file_name = nla_file_name;
	}

	public String getNla_file_size() {
		return nla_file_size;
	}

	public void setNla_file_size(String nla_file_size) {
		this.nla_file_size = nla_file_size;
	}

	public String getNla_send_status() {
		return nla_send_status;
	}

	public void setNla_send_status(String nla_send_status) {
		this.nla_send_status = nla_send_status;
	}

	public String getNla_virus() {
		return nla_virus;
	}

	public void setNla_virus(String nla_virus) {
		this.nla_virus = nla_virus;
	}

	public long getNpa_seq() {
		return npa_seq;
	}

	public void setNpa_seq(long npa_seq) {
		this.npa_seq = npa_seq;
	}

	public int getNla_duplicate() {
		return nla_duplicate;
	}

	public void setNla_duplicate(int nla_duplicate) {
		this.nla_duplicate = nla_duplicate;
	}

	public String getNli_div_str() {
		return nli_div_str;
	}

	public void setNli_div_str(String nli_div_str) {
		this.nli_div_str = nli_div_str;
	}

	public String getNli_result() {
		return nli_result;
	}

	public void setNli_result(String nli_result) {
		this.nli_result = nli_result;
	}

	public String getNler_div_str() {
		return nler_div_str;
	}

	public void setNler_div_str(String nler_div_str) {
		this.nler_div_str = nler_div_str;
	}

	public String getNler_result() {
		return nler_result;
	}

	public void setNler_result(String nler_result) {
		this.nler_result = nler_result;
	}

	public String getNla_message() {
		return nla_message;
	}

	public void setNla_message(String nla_message) {
		this.nla_message = nla_message;
	}
	
	public int getNlpt_seq() {
		return nlpt_seq;
	}

	public void setNlpt_seq(int nlpt_seq) {
		this.nlpt_seq = nlpt_seq;
	}

	public String getNlpt_create_date() {
		return nlpt_create_date;
	}

	public void setNlpt_create_date(String nlpt_create_date) {
		this.nlpt_create_date = nlpt_create_date;
	}

	public String getNlpt_host_name() {
		return nlpt_host_name;
	}

	public void setNlpt_host_name(String nlpt_host_name) {
		this.nlpt_host_name = nlpt_host_name;
	}

	public String getNlpt_risk_level() {
		return nlpt_risk_level;
	}

	public void setNlpt_risk_level(String nlpt_risk_level) {
		this.nlpt_risk_level = nlpt_risk_level;
	}

	public String getNlpt_prog_name() {
		return nlpt_prog_name;
	}

	public void setNlpt_prog_name(String nlpt_prog_name) {
		this.nlpt_prog_name = nlpt_prog_name;
	}

	public String getNlpt_message() {
		return nlpt_message;
	}

	public void setNlpt_message(String nlpt_message) {
		this.nlpt_message = nlpt_message;
	}

	public String getNlpt_create_sdate() {
		return nlpt_create_sdate;
	}

	public void setNlpt_create_sdate(String nlpt_create_sdate) {
		this.nlpt_create_sdate = nlpt_create_sdate;
	}

	public String getNlpt_create_edate() {
		return nlpt_create_edate;
	}

	public void setNlpt_create_edate(String nlpt_create_edate) {
		this.nlpt_create_edate = nlpt_create_edate;
	}

	public int getNlpt_div() {
		return nlpt_div;
	}

	public void setNlpt_div(int nlpt_div) {
		this.nlpt_div = nlpt_div;
	}

	public String getNlpt_div_str() {
		return nlpt_div_str;
	}

	public void setNlpt_div_str(String nlpt_div_str) {
		this.nlpt_div_str = nlpt_div_str;
	}

	public String getNlpt_result() {
		return nlpt_result;
	}

	public void setNlpt_result(String nlpt_result) {
		this.nlpt_result = nlpt_result;
	}

	public static String[] getCsvHeader(String mode) {
		// 'switch' expressions are not supported at language level '8'
		String[] result;
		switch(mode) {
			case "manager":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.subject"),
					MessageUtil.getMessage("log.connectip"),
					MessageUtil.getMessage("log.workcontent"),
					MessageUtil.getMessage("log.operresult"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			case "policy":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.subject"),
					MessageUtil.getMessage("log.connectip"),
					MessageUtil.getMessage("log.workcontent"),
					MessageUtil.getMessage("log.operresult"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			case "event":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.division"),
					MessageUtil.getMessage("log.policy.name"),
					MessageUtil.getMessage("log.operresult"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			case "access2":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.accesstype"),
//					MessageUtil.getMessage("log.sndrcvclassfi"),
					MessageUtil.getMessage("log.sourceip"),
					MessageUtil.getMessage("log.sourceport"),
					MessageUtil.getMessage("log.destnationip"),
					MessageUtil.getMessage("log.destnationport")
				};
				break;
			case "protection":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.division"),
					MessageUtil.getMessage("log.subject"),
					MessageUtil.getMessage("log.operresult"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.workclass"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			case "integrity":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.service.name"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			case "error":
				result = new String[] {
					MessageUtil.getMessage("log.turn"),
					MessageUtil.getMessage("log.createdate"),
					MessageUtil.getMessage("log.subject"),
					MessageUtil.getMessage("log.operresult"),
					MessageUtil.getMessage("log.risk"),
					MessageUtil.getMessage("log.workclass"),
					MessageUtil.getMessage("log.detail")
				};
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	public List<String> getCsvData(String mode) {
		List<String> result;
		switch(mode) {
			case "manager":
				result = mngData();
				break;
			case "policy":
				result = policyData();
				break;
			case "event":
				result = eventData();
				break;
			case "access2":
				result = accessData2();
				break;
			case "protection":
				result = protData();
				break;
			case "integrity":
				result = inteData();
				break;
			case "error":
				result = errorData();
				break;
			default:
				result = null;
				break;
		}
		return result;
	}

	public List<String> mngData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nlm_create_date);
		list.add(this.nsu_id);
		list.add(this.nai_ip);
		list.add(this.nlm_page);
		list.add(this.nlm_result);
		list.add(this.nlm_risk_level);
		list.add(this.nlm_param);
		return list;
	}
	public List<String> policyData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nlp_create_date);
		list.add(this.nsu_id);
		list.add(this.nai_ip);
		list.add(this.nlp_page);
		list.add(this.nlp_result);
		list.add(this.nlp_param);
		return list;
	}
	public List<String> eventData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nle_create_date);
		list.add(this.nle_div_str);
		if(this.nle_npl_name == "" || this.nle_npl_name == null) {
			list.add(this.nle_npl_name);
		}
		else if(this.npl_name == "" || this.npl_name == null) {
			list.add(this.nle_prog_name);
		}else {
			list.add(this.npl_name);
		}
		list.add(this.nle_result);
		list.add(this.nle_risk_level);
		list.add(this.nle_message);
		return list;
	}
	public List<String> accessData2() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nla_create_date);
		list.add(this.nla_risk_level);
		list.add(this.nla_access_type);
//		list.add(this.nla_div2);
		list.add(this.nla_src_ip);
		list.add(this.nla_src_port);
		list.add(this.nla_dst_ip);
		list.add(this.nla_dst_port);
		return list;
	}
	public List<String> protData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nlpt_create_date);
		list.add(this.nlpt_div_str);
		list.add(this.nsu_id);
		list.add(this.nlpt_result);
		list.add(this.nlpt_risk_level);
		list.add(this.nlpt_prog_name);
		list.add(this.nlpt_message);
		return list;
	}
	public List<String> inteData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nli_create_date);
		list.add(this.nli_risk_level);
		list.add(this.npl_name);
		list.add(this.nli_message);
		return list;
	}
	public List<String> errorData() {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(this.rnw));
		list.add(this.nler_create_date);
		list.add(this.nsu_id);
		list.add(this.nler_result);
		list.add(this.nler_risk_level);
		list.add(this.nler_prog_name);
		list.add(this.nler_message);
		return list;
	}
}