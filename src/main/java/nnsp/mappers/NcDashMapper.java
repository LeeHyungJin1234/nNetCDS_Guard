package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcConfig;
import nnsp.vo.NcLog;
import nnsp.vo.NcStatNw;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface NcDashMapper {
	
	final String SEND_CT_DASH = "SELECT NSS_PG_FNAME, SUM(NSS_BYTE) NSS_BYTE, " 
		+"concat(DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60),'0') ) NSS_DATE "
		+"FROM NNC_STAT_SEND_IN NSS "
		+"WHERE NSS.NSS_PG_FNAME = #{nss_pg_fname} "
		+"AND NSS_DATE >= #{nss_sdate} "
		+"AND NSS_DATE <= #{nss_edate} "
		+"GROUP BY DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60) "
		+"ORDER BY NSS_DATE ASC";

	final String NSS_BYTE_SUM = "SELECT IFNULL(SUM(NSS_BYTE), 0) NSS_BYTE "
		+"FROM NNC_STAT_SEND_IN NSS "
		+"WHERE NSS.NSS_NPL_NO = #{nss_npl_no} "
		+"AND NSS_DATE >= #{nss_sdate} "
		+"AND NSS_DATE <= #{nss_edate}";
	
	final String RECEIVE_CT_DASH = "SELECT NSR_PG_FNAME, SUM(NSR_BYTE) NSR_BYTE, "
		+"concat(DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60),'0') ) NSR_DATE "
		+"FROM NNC_STAT_RECV_IN NSR "
		+"WHERE NSR.NSR_PG_FNAME = #{nsr_pg_fname} "
		+"AND NSR_DATE >= #{nsr_sdate} "
		+"AND NSR_DATE <= #{nsr_edate} "
		+"GROUP BY DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60) "
		+"ORDER BY NSR_DATE ASC";
	
	final String REPEAT_CT_DASH = "SELECT NSRP_PG_FNAME, SUM(NSRP_BYTE) NSRP_BYTE, SUM(NSRP_LOSS_YN) NSRP_LOSS_YN, "
		+"concat(DATE_FORMAT(NSRP_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSRP_DATE, '%s')/60),'0') ) NSRP_DATE "
		+"FROM NNC_STAT_REPE_IN NSRP "
		+"WHERE NSRP.NSRP_PG_FNAME = #{nsrp_pg_fname} "
		+"AND NSRP_DATE >= #{nsrp_sdate} "
		+"AND NSRP_DATE <= #{nsrp_edate} "
		+"GROUP BY DATE_FORMAT(NSRP_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSRP_DATE, '%s')/60) "
		+"ORDER BY NSRP_DATE ASC";

	final String NSR_BYTE_SUM = "SELECT IFNULL(SUM(NSR_BYTE), 0) NSR_BYTE "
		+"FROM NNC_STAT_RECV_IN NSR " 
		+"WHERE NSR.NSR_NPL_NO = #{nsr_npl_no} "
		+"AND NSR_DATE >= #{nsr_sdate} "
		+"AND NSR_DATE <= #{nsr_edate}";
	
	// 대시보드 이벤트 로그
	final String DASH_EVENT = "SELECT NLE.*, NCP_NAME from ( " +
		"SELECT NLE_CREATE_DATE, NLE_HOST_NAME, " +
		"(CASE NLE_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLE_RISK_LEVEL, NLE_PROG_NAME, NLE_MESSAGE " +
		"FROM NNC_LOG_EVENT NLE "+
		"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i') >= DATE_FORMAT(#{nle_create_sdate},'%Y-%m-%d %H:%i') "+
		"AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i') <= DATE_FORMAT(#{nle_create_edate},'%Y-%m-%d %H:%i') "+
		"ORDER BY NLE_CREATE_DATE DESC, NLE_SEQ DESC " +
		") NLE LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLE.NLE_PROG_NAME = NCP.NCP_FILE_NAME) "+
		"WHERE NCP.NCP_DIV = #{nle_div} "+
		"ORDER BY NLE_CREATE_DATE DESC";
	
	// 대시보드 이벤트 로그
	final String DASH_EVENT2 = "SELECT NLE.*, NCP_NAME, NCP_DIV from ( " +
		"SELECT NLE_CREATE_DATE, NLE_HOST_NAME, " +
		"(CASE NLE_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLE_RISK_LEVEL, NLE_PROG_NAME, NLE_MESSAGE " +
		"FROM NNC_LOG_EVENT NLE "+
		"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i') >= DATE_FORMAT(#{nle_create_sdate},'%Y-%m-%d %H:%i') "+
		"AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i') <= DATE_FORMAT(#{nle_create_edate},'%Y-%m-%d %H:%i') "+
		"ORDER BY NLE_CREATE_DATE DESC, NLE_SEQ DESC " +
		") NLE LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLE.NLE_PROG_NAME = NCP.NCP_FILE_NAME) "+
		"WHERE (NCP.NCP_DIV = #{tx_div} OR NCP.NCP_DIV = #{rx_div})"+
		"ORDER BY NLE_CREATE_DATE DESC";
	
//	final String SEND_TRAFFIC_DASH_IN = "SELECT SUM(NSS_BYTE) NSS_BYTE, " +
//		"concat(DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60),'0') ) NSS_DATE " +
//		"FROM NNC_STAT_SEND_IN " +
//		"WHERE NSS_DATE >= #{nss_sdate} " +
//		"AND NSS_DATE <= #{nss_edate} " + 
//		"GROUP BY DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60) " +
//		"ORDER BY NSS_DATE ASC";

	final String RECEIVE_TRAFFIC_DASH_IN = "CALL nnet_cds2.getChartData(#{nsr_sdate}, #{nsr_edate}, 0, 9)"; 
	final String SEND_TRAFFIC_DASH_IN = "CALL nnet_cds2.getChartData(#{nss_sdate}, #{nss_edate}, 0, 1)";
	
//	final String RECEIVE_TRAFFIC_DASH_IN = "SELECT SUM(NSR_BYTE) NSR_BYTE, " + 
//		"concat(DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60),'0') ) NSR_DATE " + 
//		"FROM NNC_STAT_RECV_IN " + 
//		"WHERE NSR_DATE >= #{nsr_sdate} " + 
//		"AND NSR_DATE <= #{nsr_edate} " + 
//		"GROUP BY DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60) "+ 
//		"ORDER BY NSR_DATE ASC";
	
	final String DETECT_DASH_IN = "CALL nnet_cds2.getChartData(#{nsr_sdate}, #{nsr_edate}, 0, 0)"; 
	
//	final String SEND_TRAFFIC_DASH_OUT = "SELECT SUM(NSS_BYTE) NSS_BYTE, " +
//		"concat(DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60),'0') ) NSS_DATE " +
//		"FROM NNC_STAT_SEND_OUT " +
//		"WHERE NSS_DATE >= #{nss_sdate} " +
//		"AND NSS_DATE <= #{nss_edate} " + 
//		"GROUP BY DATE_FORMAT(NSS_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSS_DATE, '%s')/60) " +
//		"ORDER BY NSS_DATE ASC";

	final String RECEIVE_TRAFFIC_DASH_OUT = "CALL nnet_cds2.getChartData(#{nsr_sdate}, #{nsr_edate}, 1, 9)"; 
	final String SEND_TRAFFIC_DASH_OUT = "CALL nnet_cds2.getChartData(#{nss_sdate}, #{nss_edate}, 1, 1)";
	
//	final String RECEIVE_TRAFFIC_DASH_OUT = "SELECT SUM(NSR_BYTE) NSR_BYTE, " + 
//		"concat(DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60),'0') ) NSR_DATE " + 
//		"FROM NNC_STAT_RECV_OUT " + 
//		"WHERE NSR_DATE >= #{nsr_sdate} " + 
//		"AND NSR_DATE <= #{nsr_edate} " + 
//		"GROUP BY DATE_FORMAT(NSR_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSR_DATE, '%s')/60) " + 
//		"ORDER BY NSR_DATE ASC";
	final String DETECT_DASH_OUT = "CALL nnet_cds2.getChartData(#{nsr_sdate}, #{nsr_edate}, 1, 0)"; 
	
	final String REPEATE_TRAFFIC_DASH = "SELECT SUM(NSRP_BYTE) NSRP_BYTE, SUM(NSRP_LOSS_YN) NSRP_LOSS_YN, "
		+"concat(DATE_FORMAT(NSRP_DATE, '%Y-%m-%d %H:%i:'), concat(FLOOR(DATE_FORMAT(NSRP_DATE, '%s')/60),'0') ) NSRP_DATE "
		+"FROM NNC_STAT_REPE_IN NSRP "
		+"WHERE NSRP_DATE >= #{nsrp_sdate} "
		+"AND NSRP_DATE <= #{nsrp_edate} "
		+"GROUP BY DATE_FORMAT(NSRP_DATE, '%Y-%m-%d %H:%i'), FLOOR(DATE_FORMAT(NSRP_DATE, '%s')/60) "
		+"ORDER BY NSRP_DATE ASC";
	
	final String TX_BYTE_INFO = "SELECT IFNULL(SUM(NSS_BYTE), 0) NSS_BYTE "
		+"FROM NNC_STAT_SEND_IN NSS LEFT JOIN NNC_POLICY_LINK NPL ON (NSS.NSS_NPL_NO = NPL.NPL_NO) "
		+"WHERE NPL.NPL_USE_YN=1 AND NPL.NPL_STAT=1 "
		+"AND NSS_DATE >= #{nss_sdate} "
		+"AND NSS_DATE <= #{nss_edate}";
	
	final String RX_BYTE_INFO = "SELECT IFNULL(SUM(NSR_BYTE), 0) NSR_BYTE "
		+"FROM NNC_STAT_RECV_IN NSR LEFT JOIN NNC_POLICY_LINK NPL ON (NSR.NSR_NPL_NO = NPL.NPL_NO) "
		+"WHERE NPL.NPL_USE_YN=1 AND NPL.NPL_STAT=1 "
		+"AND NSR_DATE >= #{nsr_sdate} "
		+"AND NSR_DATE <= #{nsr_edate}";
	
	final String TX_SVC_CNT = "SELECT COUNT(NPL_SEQ) CNT "
		+"FROM NNC_POLICY_LINK "
		+"WHERE NPL_USE_YN=1 AND NPL_STAT=1";
		/*
		"SELECT COUNT(NLP_SEQ) CNT "
		+"FROM NNC_LINK_POLICY "
		+"WHERE NLP_DIV = #{tx_div} AND NLP_USE_YN=1";
		*/
	
	final String ARROW_INFO = "SELECT NCS_ARROW FROM NNC_CONF_SYSTEM WHERE NCS_DIV=#{tx_div} AND NCS_USE_YN=1";
	
	@Select(SEND_CT_DASH)
	@Results(value = {
		@Result(property="nss_pg_fname", column="NSS_PG_FNAME"),
		@Result(property="nss_byte", column="NSS_BYTE"),
		@Result(property="nss_date", column="NSS_DATE")
    })
	ArrayList<NcStatNw> send_ct_dash(@Param("nss_pg_fname") String nss_pg_fname, @Param("nss_sdate") String nss_sdate, @Param("nss_edate") String nss_edate);
	
	@Select(NSS_BYTE_SUM)
	long nss_byte_sum(@Param("nss_npl_no") int nss_npl_no, @Param("nss_sdate") String nss_sdate, @Param("nss_edate") String nss_edate);
	
	@Select(RECEIVE_CT_DASH)
	@Results(value = {
		@Result(property="nsr_pg_fname", column="NSR_PG_FNAME"),
		@Result(property="nsr_byte", column="NSR_BYTE"),
		@Result(property="nsr_date", column="NSR_DATE")
    })
	ArrayList<NcStatNw> receive_ct_dash(@Param("nsr_pg_fname") String nsr_pg_fname, @Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);

	@Select(REPEAT_CT_DASH)
	@Results(value = {
		@Result(property="nsrp_pg_fname", column="NSRP_PG_FNAME"),
		@Result(property="nsrp_byte", column="NSRP_BYTE"),
		@Result(property="nsrp_date", column="NSRP_DATE")
    })
	ArrayList<NcStatNw> repeat_ct_dash(@Param("nsrp_pg_fname") String nsrp_pg_fname, @Param("nsrp_sdate") String nsrp_sdate, @Param("nsrp_edate") String nsrp_edate);

	@Select(NSR_BYTE_SUM)
	long nsr_byte_sum(@Param("nsr_npl_no") int nsr_npl_no, @Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	
	@Select(DASH_EVENT)
    @Results(value = {
		@Result(property="nle_create_date", column="NLE_CREATE_DATE"),
		@Result(property="nle_host_name", column="NLE_HOST_NAME"),
		@Result(property="nle_risk_level", column="NLE_RISK_LEVEL"),
		@Result(property="nle_message", column="NLE_MESSAGE"),
		@Result(property="ncp_name", column="NCP_NAME")
    })
    ArrayList<NcLog> getDashEventList(@Param("nle_create_sdate") String nle_create_sdate, @Param("nle_create_edate") String nle_create_edate, @Param("nle_div") String nle_div);
		
	@Select(DASH_EVENT2)
    @Results(value = {
		@Result(property="nle_create_date", column="NLE_CREATE_DATE"),
		@Result(property="nle_host_name", column="NLE_HOST_NAME"),
		@Result(property="nle_risk_level", column="NLE_RISK_LEVEL"),
		@Result(property="nle_message", column="NLE_MESSAGE"),
		@Result(property="ncp_name", column="NCP_NAME"),
		@Result(property="ncp_div", column="NCP_DIV")
    })
    ArrayList<NcLog> getDashEventList2(@Param("nle_create_sdate") String nle_create_sdate, @Param("nle_create_edate") String nle_create_edate, @Param("tx_div") String tx_div, @Param("rx_div") String rx_div);

	@Select(SEND_TRAFFIC_DASH_IN)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> send_ct_traffic_in(@Param("nss_sdate") String nss_sdate, @Param("nss_edate") String nss_edate);
	
	@Select(RECEIVE_TRAFFIC_DASH_IN)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> receive_ct_traffic_in(@Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	
	@Select(DETECT_DASH_IN)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> detect_dash_in(@Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	
	@Select(SEND_TRAFFIC_DASH_OUT)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> send_ct_traffic_out(@Param("nss_sdate") String nss_sdate, @Param("nss_edate") String nss_edate);
	
	@Select(RECEIVE_TRAFFIC_DASH_OUT)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> receive_ct_traffic_out(@Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	
	@Select(DETECT_DASH_OUT)
	@Results(value = {
		@Result(property="ps_strc_time", column="PS_STRC_TIME"),
		@Result(property="ps_packets", column="PS_PACKETS")
    })
	ArrayList<NcStatNw> detect_dash_out(@Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	
	@Select(REPEATE_TRAFFIC_DASH)
	@Results(value = {
		@Result(property="nsrp_byte", column="NSRP_BYTE"),
		@Result(property="nsrp_loss_yn", column="NSRP_LOSS_YN")
    })
	ArrayList<NcStatNw> repeate_ct_traffic(@Param("tx_div") int tx_div, @Param("nsrp_sdate") String nsrp_sdate, @Param("nsrp_edate") String nsrp_edate);
	
	@Select(TX_BYTE_INFO)
	long tx_byte_info(@Param("tx_div") int tx_div, @Param("nss_sdate") String nss_sdate, @Param("nss_edate") String nss_edate);
	@Select(RX_BYTE_INFO)
	long rx_byte_info(@Param("rx_div") int rx_div, @Param("nsr_sdate") String nsr_sdate, @Param("nsr_edate") String nsr_edate);
	@Select(ARROW_INFO)
	@Results(value = {
		@Result(property="ncs_arrow", column="NCS_ARROW")
    })
	NcConfig arrow_info(int tx_div);
	@Select(TX_SVC_CNT)
	int tx_svc_cnt(int tx_div);
	
}