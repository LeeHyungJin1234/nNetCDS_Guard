package nnsp.vo;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NcPolicyLink implements Serializable {
	
	//NND_CONF_PROGRAM
	int ncp_seq;
	String ncp_name;
	int ncp_div;
	String ncp_file_name;
	String ncp_path;
	int ncp_status;
	int ncp_service_flag;
	String ncp_hash;
	int ncp_file_size;
	String ncp_hash_date;
	int inspect_flag;
	int ncp_use_yn;
	
	//NND_POLICY_LINK
	int npl_seq; //NND_POLICY_LINK_ROUTE
	
	String npl_name;
	int npl_tx_ntc_seq;
	int npl_tx_nts_seq;
	int npl_rx_ntc_seq;
	int npl_rx_nts_seq;
	String npl_tx_ntc_name;
	String npl_tx_nts_name;
	String npl_rx_ntc_name;
	String npl_rx_nts_name;
	int npl_use_yn;
	String npl_regdttm;
	int npl_no;
	int npl_stat;
	
	//NND_POLICY_LINK_ROUTE
	int nplr_seq;
	int nplr_div;
	int nplr_range;
	String nplr_port;
	String nplr_ip;
	String nplr_end_ip;
	String nplr_dst_ip;
	int nplr_dst_port;
	String[] src_ip;
	String[] src_end_ip;
	String[] dst_ip;
	int[] dst_port;
	String nplr_pw;
	String nplr_url;
	String nplr_id;
	String nplr_file_dir;
	int nplr_cycle;
	String nplr_regdttm;
	
	String nplr_ip2;
	String nplr_port2;
	String nplr_id2;
	String nplr_pw2;
	String nplr_file_dir2;
	int nplr_cycle2;

//  DBMS 필터링 정책   -----
	String nplr_query;
	String nplr_where;
	String nplr_idx_column;
	int nplr_interval_type;
	int nplr_interval_value;
	String nplr_rx_query;
	int nplr_cnt;
	
	
	//NND_TYPE_SERVICE
	String nts_name; //NND_CONTENTS_TYPE
	int nts_seq;
	//NND_TYPE_CONTENTS
	String ntc_name;
	int ntc_seq;
	String ncp_tx_name;
	String ncp_rx_name;
	
	String[] route_dst_port;

	ArrayList<NcPolicyLink> srcRouteList;
	ArrayList<NcPolicyLink> dstRouteList;
	
	
	//nnd_policy_file_send
	int npfs_seq;
	int npfs_type;
	String npfs_filter;
	String npfs_regdttm;
	
	//nnd_policy_file_except
	int npfe_seq;
	int npfe_use;
	int npfe_max_size;
	String npfe_filter;
	int npfe_option;
	String npfe_move_dir;
	String npfe_regdttm;
	
	//nnd_policy_file_tx
	int npft_seq;
	int npft_split;
	int npft_interval;
	int npft_encrypt;
	int npft_encode;
	int npft_buffer;
	int npft_open;
	int npft_backup;
	String npfe_file_dir;
	
	//nnd_policy_file_rx
	int npfr_seq;
	int npfr_que_size;
	int npfr_decode_size;
	int npfr_backup;
	String npfr_backup_dir;
	
	String npft_file_dir;
	
	String nplr_server_name;
	String nplr_server_name2;
	
	
	public String getNplr_server_name2() {
		return nplr_server_name2;
	}
	public void setNplr_server_name2(String nplr_server_name2) {
		this.nplr_server_name2 = nplr_server_name2;
	}
	public String getNplr_server_name() {
		return nplr_server_name;
	}
	public void setNplr_server_name(String nplr_server_name) {
		this.nplr_server_name = nplr_server_name;
	}
	public String getNpft_file_dir() {
		return npft_file_dir;
	}
	public void setNpft_file_dir(String npft_file_dir) {
		this.npft_file_dir = npft_file_dir;
	}
	public int getNpfs_seq() {
		return npfs_seq;
	}
	public void setNpfs_seq(int npfs_seq) {
		this.npfs_seq = npfs_seq;
	}
	public int getNpfs_type() {
		return npfs_type;
	}
	public void setNpfs_type(int npfs_type) {
		this.npfs_type = npfs_type;
	}
	public String getNpfs_filter() {
		return npfs_filter;
	}
	public void setNpfs_filter(String npfs_filter) {
		this.npfs_filter = npfs_filter;
	}
	public String getNpfs_regdttm() {
		return npfs_regdttm;
	}
	public void setNpfs_regdttm(String npfs_regdttm) {
		this.npfs_regdttm = npfs_regdttm;
	}
	public int getNpfe_seq() {
		return npfe_seq;
	}
	public void setNpfe_seq(int npfe_seq) {
		this.npfe_seq = npfe_seq;
	}
	public int getNpfe_use() {
		return npfe_use;
	}
	public void setNpfe_use(int npfe_use) {
		this.npfe_use = npfe_use;
	}
	public int getNpfe_max_size() {
		return npfe_max_size;
	}
	public void setNpfe_max_size(int npfe_max_size) {
		this.npfe_max_size = npfe_max_size;
	}
	public String getNpfe_filter() {
		return npfe_filter;
	}
	public void setNpfe_filter(String npfe_filter) {
		this.npfe_filter = npfe_filter;
	}
	public int getNpfe_option() {
		return npfe_option;
	}
	public void setNpfe_option(int npfe_option) {
		this.npfe_option = npfe_option;
	}
	public String getNpfe_move_dir() {
		return npfe_move_dir;
	}
	public void setNpfe_move_dir(String npfe_move_dir) {
		this.npfe_move_dir = npfe_move_dir;
	}
	public String getNpfe_regdttm() {
		return npfe_regdttm;
	}
	public void setNpfe_regdttm(String npfe_regdttm) {
		this.npfe_regdttm = npfe_regdttm;
	}
	public int getNpft_seq() {
		return npft_seq;
	}
	public void setNpft_seq(int npft_seq) {
		this.npft_seq = npft_seq;
	}
	public int getNpft_split() {
		return npft_split;
	}
	public void setNpft_split(int npft_split) {
		this.npft_split = npft_split;
	}
	public int getNpft_interval() {
		return npft_interval;
	}
	public void setNpft_interval(int npft_interval) {
		this.npft_interval = npft_interval;
	}
	public int getNpft_encrypt() {
		return npft_encrypt;
	}
	public void setNpft_encrypt(int npft_encrypt) {
		this.npft_encrypt = npft_encrypt;
	}
	public int getNpft_encode() {
		return npft_encode;
	}
	public void setNpft_encode(int npft_encode) {
		this.npft_encode = npft_encode;
	}
	public int getNpft_buffer() {
		return npft_buffer;
	}
	public void setNpft_buffer(int npft_buffer) {
		this.npft_buffer = npft_buffer;
	}
	public int getNpft_open() {
		return npft_open;
	}
	public void setNpft_open(int npft_open) {
		this.npft_open = npft_open;
	}
	public int getNpft_backup() {
		return npft_backup;
	}
	public void setNpft_backup(int npft_backup) {
		this.npft_backup = npft_backup;
	}
	public String getNpfe_file_dir() {
		return npfe_file_dir;
	}
	public void setNpfe_file_dir(String npfe_file_dir) {
		this.npfe_file_dir = npfe_file_dir;
	}
	public int getNpfr_seq() {
		return npfr_seq;
	}
	public void setNpfr_seq(int npfr_seq) {
		this.npfr_seq = npfr_seq;
	}
	public int getNpfr_que_size() {
		return npfr_que_size;
	}
	public void setNpfr_que_size(int npfr_que_size) {
		this.npfr_que_size = npfr_que_size;
	}
	public int getNpfr_decode_size() {
		return npfr_decode_size;
	}
	public void setNpfr_decode_size(int npfr_decode_size) {
		this.npfr_decode_size = npfr_decode_size;
	}
	public int getNpfr_backup() {
		return npfr_backup;
	}
	public void setNpfr_backup(int npfr_backup) {
		this.npfr_backup = npfr_backup;
	}
	public String getNpfr_backup_dir() {
		return npfr_backup_dir;
	}
	public void setNpfr_backup_dir(String npfr_backup_dir) {
		this.npfr_backup_dir = npfr_backup_dir;
	}
	public int getNts_seq() {
		return nts_seq;
	}
	public void setNts_seq(int nts_seq) {
		this.nts_seq = nts_seq;
	}
	public int getNtc_seq() {
		return ntc_seq;
	}
	public void setNtc_seq(int ntc_seq) {
		this.ntc_seq = ntc_seq;
	}
	public String getNplr_ip2() {
		return nplr_ip2;
	}
	public void setNplr_ip2(String nplr_ip2) {
		this.nplr_ip2 = nplr_ip2;
	}
	public String getNplr_port2() {
		return nplr_port2;
	}
	public void setNplr_port2(String nplr_port2) {
		this.nplr_port2 = nplr_port2;
	}
	public String getNplr_id2() {
		return nplr_id2;
	}
	public void setNplr_id2(String nplr_id2) {
		this.nplr_id2 = nplr_id2;
	}
	public String getNplr_pw2() {
		return nplr_pw2;
	}
	public void setNplr_pw2(String nplr_pw2) {
		this.nplr_pw2 = nplr_pw2;
	}
	public String getNplr_file_dir2() {
		return nplr_file_dir2;
	}
	public void setNplr_file_dir2(String nplr_file_dir2) {
		this.nplr_file_dir2 = nplr_file_dir2;
	}
	public int getNplr_cycle2() {
		return nplr_cycle2;
	}
	public void setNplr_cycle2(int nplr_cycle2) {
		this.nplr_cycle2 = nplr_cycle2;
	}
	
	//  DBMS 필터링 정책   -----
	public String getNplr_query() {
		return nplr_query;
	}
	public void setNplr_query(String nplr_query) {
		this.nplr_query = nplr_query;
	}
	public String getNplr_where() {
		return nplr_where;
	}
	public void setNplr_where(String nplr_where) {
		this.nplr_where = nplr_where;
	}
	public String getNplr_idx_column() {
		return nplr_idx_column;
	}
	public void setNplr_idx_column(String nplr_idx_column) {
		this.nplr_idx_column = nplr_idx_column;
	}
	public int getNplr_interval_type() {
		return nplr_interval_type;
	}
	public void setNplr_interval_type(int nplr_interval_type) {
		this.nplr_interval_type = nplr_interval_type;
	}
	public int getNplr_interval_value() {
		return nplr_interval_value;
	}
	public void setNplr_interval_value(int nplr_interval_value) {
		this.nplr_interval_value = nplr_interval_value;
	}
	public String getNplr_rx_query() {
		return nplr_rx_query;
	}
	public void setNplr_rx_query(String nplr_rx_query) {
		this.nplr_rx_query = nplr_rx_query;
	}
	public int getNplr_cnt() {
		return nplr_cnt;
	}
	public void setNplr_cnt(int nplr_cnt) {
		this.nplr_cnt = nplr_cnt;
	}
	
	
	public String getNpl_tx_ntc_name() {
		return npl_tx_ntc_name;
	}
	public void setNpl_tx_ntc_name(String npl_tx_ntc_name) {
		this.npl_tx_ntc_name = npl_tx_ntc_name;
	}
	public String getNpl_tx_nts_name() {
		return npl_tx_nts_name;
	}
	public void setNpl_tx_nts_name(String npl_tx_nts_name) {
		this.npl_tx_nts_name = npl_tx_nts_name;
	}
	public String getNpl_rx_ntc_name() {
		return npl_rx_ntc_name;
	}
	public void setNpl_rx_ntc_name(String npl_rx_ntc_name) {
		this.npl_rx_ntc_name = npl_rx_ntc_name;
	}
	public String getNpl_rx_nts_name() {
		return npl_rx_nts_name;
	}
	public void setNpl_rx_nts_name(String npl_rx_nts_name) {
		this.npl_rx_nts_name = npl_rx_nts_name;
	}
	public int getNcp_seq() {
		return ncp_seq;
	}
	public void setNcp_seq(int ncp_seq) {
		this.ncp_seq = ncp_seq;
	}
	public String getNcp_name() {
		return ncp_name;
	}
	public void setNcp_name(String ncp_name) {
		this.ncp_name = ncp_name;
	}
	public int getNcp_div() {
		return ncp_div;
	}
	public void setNcp_div(int ncp_div) {
		this.ncp_div = ncp_div;
	}
	public String getNcp_file_name() {
		return ncp_file_name;
	}
	public void setNcp_file_name(String ncp_file_name) {
		this.ncp_file_name = ncp_file_name;
	}
	public String getNcp_path() {
		return ncp_path;
	}
	public void setNcp_path(String ncp_path) {
		this.ncp_path = ncp_path;
	}
	public int getNcp_status() {
		return ncp_status;
	}
	public void setNcp_status(int ncp_status) {
		this.ncp_status = ncp_status;
	}
	public int getNcp_service_flag() {
		return ncp_service_flag;
	}
	public void setNcp_service_flag(int ncp_service_flag) {
		this.ncp_service_flag = ncp_service_flag;
	}
	public String getNcp_hash() {
		return ncp_hash;
	}
	public void setNcp_hash(String ncp_hash) {
		this.ncp_hash = ncp_hash;
	}
	public int getNcp_file_size() {
		return ncp_file_size;
	}
	public void setNcp_file_size(int ncp_file_size) {
		this.ncp_file_size = ncp_file_size;
	}
	public String getNcp_hash_date() {
		return ncp_hash_date;
	}
	public void setNcp_hash_date(String ncp_hash_date) {
		this.ncp_hash_date = ncp_hash_date;
	}
	public int getInspect_flag() {
		return inspect_flag;
	}
	public void setInspect_flag(int inspect_flag) {
		this.inspect_flag = inspect_flag;
	}
	public int getNcp_use_yn() {
		return ncp_use_yn;
	}
	public void setNcp_use_yn(int ncp_use_yn) {
		this.ncp_use_yn = ncp_use_yn;
	}
	public int getNpl_seq() {
		return npl_seq;
	}
	public void setNpl_seq(int npl_seq) {
		this.npl_seq = npl_seq;
	}
	public String getNpl_name() {
		return npl_name;
	}
	public void setNpl_name(String npl_name) {
		this.npl_name = npl_name;
	}
	public int getNpl_tx_ntc_seq() {
		return npl_tx_ntc_seq;
	}
	public void setNpl_tx_ntc_seq(int npl_tx_ntc_seq) {
		this.npl_tx_ntc_seq = npl_tx_ntc_seq;
	}
	public int getNpl_tx_nts_seq() {
		return npl_tx_nts_seq;
	}
	public void setNpl_tx_nts_seq(int npl_tx_nts_seq) {
		this.npl_tx_nts_seq = npl_tx_nts_seq;
	}
	public int getNpl_rx_ntc_seq() {
		return npl_rx_ntc_seq;
	}
	public void setNpl_rx_ntc_seq(int npl_rx_ntc_seq) {
		this.npl_rx_ntc_seq = npl_rx_ntc_seq;
	}
	public int getNpl_rx_nts_seq() {
		return npl_rx_nts_seq;
	}
	public void setNpl_rx_nts_seq(int npl_rx_nts_seq) {
		this.npl_rx_nts_seq = npl_rx_nts_seq;
	}
	public int getNpl_use_yn() {
		return npl_use_yn;
	}
	public void setNpl_use_yn(int npl_use_yn) {
		this.npl_use_yn = npl_use_yn;
	}
	public int getNpl_no() {
		return npl_no;
	}
	public void setNpl_no(int npl_no) {
		this.npl_no = npl_no;
	}
	public int getNpl_stat() {
		return npl_stat;
	}
	public void setNpl_stat(int npl_stat) {
		this.npl_stat = npl_stat;
	}
	public String getNpl_regdttm() {
		return npl_regdttm;
	}
	public void setNpl_regdttm(String npl_regdttm) {
		this.npl_regdttm = npl_regdttm;
	}
	public int getNplr_seq() {
		return nplr_seq;
	}
	public void setNplr_seq(int nplr_seq) {
		this.nplr_seq = nplr_seq;
	}
	public int getNplr_div() {
		return nplr_div;
	}
	public void setNplr_div(int nplr_div) {
		this.nplr_div = nplr_div;
	}
	public int getNplr_range() {
		return nplr_range;
	}
	public void setNplr_range(int nplr_range) {
		this.nplr_range = nplr_range;
	}
	public String getNplr_port() {
		return nplr_port;
	}
	public void setNplr_port(String nplr_port) {
		this.nplr_port = nplr_port;
	}
	public String getNplr_ip() {
		return nplr_ip;
	}
	public void setNplr_ip(String nplr_ip) {
		this.nplr_ip = nplr_ip;
	}
	public String getNplr_end_ip() {
		return nplr_end_ip;
	}
	public void setNplr_end_ip(String nplr_end_ip) {
		this.nplr_end_ip = nplr_end_ip;
	}
	public String getNplr_dst_ip() {
		return nplr_dst_ip;
	}
	public void setNplr_dst_ip(String nplr_dst_ip) {
		this.nplr_dst_ip = nplr_dst_ip;
	}
	public int getNplr_dst_port() {
		return nplr_dst_port;
	}
	public void setNplr_dst_port(int nplr_dst_port) {
		this.nplr_dst_port = nplr_dst_port;
	}
	public String[] getSrc_ip() {
		return src_ip;
	}
	public void setSrc_ip(String[] src_ip) {
		this.src_ip = src_ip;
	}
	public String[] getSrc_end_ip() {
		return src_end_ip;
	}
	public void setSrc_end_ip(String[] src_end_ip) {
		this.src_end_ip = src_end_ip;
	}
	public String[] getDst_ip() {
		return dst_ip;
	}
	public void setDst_ip(String[] dst_ip) {
		this.dst_ip = dst_ip;
	}
	public int[] getDst_port() {
		return dst_port;
	}
	public void setDst_port(int[] dst_port) {
		this.dst_port = dst_port;
	}
	public String getNplr_pw() {
		return nplr_pw;
	}
	public void setNplr_pw(String nplr_pw) {
		this.nplr_pw = nplr_pw;
	}
	public String getNplr_url() {
		return nplr_url;
	}
	public void setNplr_url(String nplr_url) {
		this.nplr_url = nplr_url;
	}
	public String getNplr_id() {
		return nplr_id;
	}
	public void setNplr_id(String nplr_id) {
		this.nplr_id = nplr_id;
	}
	public String getNplr_file_dir() {
		return nplr_file_dir;
	}
	public void setNplr_file_dir(String nplr_file_dir) {
		this.nplr_file_dir = nplr_file_dir;
	}
	public int getNplr_cycle() {
		return nplr_cycle;
	}
	public void setNplr_cycle(int nplr_cycle) {
		this.nplr_cycle = nplr_cycle;
	}
	public String getNplr_regdttm() {
		return nplr_regdttm;
	}
	public void setNplr_regdttm(String nplr_regdttm) {
		this.nplr_regdttm = nplr_regdttm;
	}
	public String getNts_name() {
		return nts_name;
	}
	public void setNts_name(String nts_name) {
		this.nts_name = nts_name;
	}
	public String getNtc_name() {
		return ntc_name;
	}
	public void setNtc_name(String ntc_name) {
		this.ntc_name = ntc_name;
	}
	public String getNcp_tx_name() {
		return ncp_tx_name;
	}
	public void setNcp_tx_name(String ncp_tx_name) {
		this.ncp_tx_name = ncp_tx_name;
	}
	public String getNcp_rx_name() {
		return ncp_rx_name;
	}
	public void setNcp_rx_name(String ncp_rx_name) {
		this.ncp_rx_name = ncp_rx_name;
	}
	public String[] getRoute_dst_port() {
		return route_dst_port;
	}
	public void setRoute_dst_port(String[] route_dst_port) {
		this.route_dst_port = route_dst_port;
	}
	public ArrayList<NcPolicyLink> getSrcRouteList() {
		return srcRouteList;
	}
	public void setSrcRouteList(ArrayList<NcPolicyLink> srcRouteList) {
		this.srcRouteList = srcRouteList;
	}
	public ArrayList<NcPolicyLink> getDstRouteList() {
		return dstRouteList;
	}
	public void setDstRouteList(ArrayList<NcPolicyLink> dstRouteList) {
		this.dstRouteList = dstRouteList;
	}
	
	
	//  임시 추가
	String npl_tx_id;
	String npl_tx_pw;
	String npl_rx_id;
	String npl_rx_pw;
	public String getNpl_tx_id() {
		return npl_tx_id;
	}
	public void setNpl_tx_id(String npl_tx_id) {
		this.npl_tx_id = npl_tx_id;
	}
	public String getNpl_tx_pw() {
		return npl_tx_pw;
	}
	public void setNpl_tx_pw(String npl_tx_pw) {
		this.npl_tx_pw = npl_tx_pw;
	}
	public String getNpl_rx_id() {
		return npl_rx_id;
	}
	public void setNpl_rx_id(String npl_rx_id) {
		this.npl_rx_id = npl_rx_id;
	}
	public String getNpl_rx_pw() {
		return npl_rx_pw;
	}
	public void setNpl_rx_pw(String npl_rx_pw) {
		this.npl_rx_pw = npl_rx_pw;
	}
	
}
