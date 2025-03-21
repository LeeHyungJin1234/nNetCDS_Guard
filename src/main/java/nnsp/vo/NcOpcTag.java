package nnsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NcOpcTag implements Serializable {
	long not_seq;
	int npl_no;
	String not_tx_tag;
	String not_rx_tag;
	int not_type;
	String not_regdttm;
	
	public long getNot_seq() {
		return not_seq;
	}
	public void setNot_seq(long not_seq) {
		this.not_seq = not_seq;
	}
	public int getNpl_no() {
		return npl_no;
	}
	public void setNpl_no(int npl_no) {
		this.npl_no = npl_no;
	}
	public String getNot_tx_tag() {
		return not_tx_tag;
	}
	public void setNot_tx_tag(String not_tx_tag) {
		this.not_tx_tag = not_tx_tag;
	}
	public String getNot_rx_tag() {
		return not_rx_tag;
	}
	public void setNot_rx_tag(String not_rx_tag) {
		this.not_rx_tag = not_rx_tag;
	}
	public int getNot_type() {
		return not_type;
	}
	public void setNot_type(int not_type) {
		this.not_type = not_type;
	}
	public String getNot_regdttm() {
		return not_regdttm;
	}
	public void setNot_regdttm(String not_regdttm) {
		this.not_regdttm = not_regdttm;
	}
}
