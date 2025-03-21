package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcLinkPolicy;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcLinkPolicyMapper {
	
	// IP 주소 조회 - 송/수신 구분 전체
	final String SELETE_IPADDR = "SELECT NLI_SEQ, NLI_IP_TYPE, NLI_IP, NLI_IP_NM, NLI_GCODE FROM NNC_LINK_IPADDR WHERE NLI_USE_YN=1 AND NLI_DIV=#{nli_div} ORDER BY NLI_SEQ DESC";
	
	// IP 주소 조회 - 해당 seq 하나
	final String SELETE_IPADDR_BYSEQ = "SELECT NLI_SEQ, NLI_IP_TYPE, NLI_IP, NLI_IP_NM, NLI_GCODE, NLI_DIV FROM NNC_LINK_IPADDR WHERE NLI_SEQ=#{nli_seq}";
		
	// IP 주소 추가
	final String IPADDR_INSERT = "INSERT INTO NNC_LINK_IPADDR(NLI_IP_TYPE, NLI_IP, NLI_IP_NM, NLI_GCODE, NLI_DIV, NLI_USE_YN) " +
			"VALUES(#{nli_ip_type}, #{nli_ip}, #{nli_ip_nm}, #{nli_gcode}, #{nli_div}, 1)"; 
		
	// IP 주소 수정
	final String IPADDR_UPDATE = "UPDATE NNC_LINK_IPADDR " +
			"SET NLI_IP_TYPE=#{nli_ip_type}, NLI_IP=#{nli_ip}, NLI_IP_NM=#{nli_ip_nm}, NLI_GCODE=#{nli_gcode}, NLI_DIV=#{nli_div} WHERE NLI_SEQ=#{nli_seq}";
		
	// IP 주소 삭제
	//final String IPADDR_DELETE = "UPDATE NNC_LINK_IPADDR SET NLI_USE_YN=0 WHERE NLI_SEQ=#{nli_seq}";
	final String IPADDR_DELETE = "DELETE FROM NNC_LINK_IPADDR WHERE NLI_SEQ=#{nli_seq}";
		
	// 서비스 조회
	final String SELETE_SERVICE = "SELECT NLS_SEQ, NLS_PORT, NLS_SERVICE_NM, NLS_GCODE FROM NNC_LINK_SERVICE WHERE NLS_USE_YN=1 AND NLS_DIV=#{nls_div} ORDER BY NLS_SEQ DESC";
	
	// 서비스 조회 - 해당 seq 하나
	final String SELETE_SERVICE_BYSEQ = "SELECT NLS_SEQ, NLS_PORT, NLS_SERVICE_NM, NLS_GCODE, NLS_DIV FROM NNC_LINK_SERVICE WHERE NLS_USE_YN=1 AND NLS_SEQ=#{nls_seq}";
		
	// 서비스 추가
	final String SERVICE_INSERT = "INSERT INTO NNC_LINK_SERVICE(NLS_PORT, NLS_SERVICE_NM, NLS_GCODE, NLS_DIV, NLS_USE_YN) " +
			"VALUES(#{nls_port}, #{nls_service_nm}, #{nls_gcode}, #{nls_div}, 1)"; 

	// 서비스 수정
	final String SERVICE_UPDATE = "UPDATE NNC_LINK_SERVICE " +
			"SET NLS_PORT=#{nls_port}, NLS_SERVICE_NM=#{nls_service_nm}, NLS_GCODE=#{nls_gcode}, NLS_DIV=#{nls_div} WHERE NLS_SEQ=#{nls_seq}";
	
	// 서비스 삭제
	//final String SERVICE_DELETE = "UPDATE NNC_LINK_SERVICE SET NLS_USE_YN=0 WHERE NLS_SEQ=#{nls_seq}";
	final String SERVICE_DELETE = "DELETE FROM NNC_LINK_SERVICE WHERE NLS_SEQ=#{nls_seq}";
	
	// 접속 허용 정책 조회 - 송/수신 구분 전체
	final String SELETE_POLICY_ALLOW = "SELECT NLA_SEQ, NLA_NAME, NLA_IP_GCODE, NLI.NLI_IP_NM, NLA_SVC_GCODE, NLS.NLS_SERVICE_NM, NLP_NAME " +
			"FROM NNC_LINK_ALLOW NLA " +
			"LEFT JOIN NNC_LINK_IPADDR NLI ON (NLA.NLA_IP_GCODE=NLI.NLI_GCODE AND NLA.NLA_DIV=NLI.NLI_DIV) " +
			"LEFT JOIN NNC_LINK_SERVICE NLS ON (NLA.NLA_SVC_GCODE=NLS.NLS_GCODE AND NLA.NLA_DIV=NLS.NLS_DIV) " +
			"LEFT JOIN NNC_LINK_POLICY NLP ON (NLA.NLP_SEQ=NLP.NLP_SEQ AND NLA.NLA_DIV=NLP.NLP_DIV) " +
			"WHERE NLA_USE_YN=1 " +
			"AND NLA_DIV=#{nla_div} " +
			"ORDER BY NLA_SEQ DESC";
		
	// 접속 허용 정책 조회 - 해당 seq 하나
	final String POLICY_ALLOW_BYSEQ = "SELECT NLA_SEQ, NLA_NAME, NLA_IP_GCODE, NLI.NLI_IP_NM, NLA_SVC_GCODE, NLS.NLS_SERVICE_NM, NLA_DIV, NLA.NLP_SEQ, NLP_NAME " +
			"FROM NNC_LINK_ALLOW NLA " +
			"LEFT JOIN NNC_LINK_IPADDR NLI ON (NLA.NLA_IP_GCODE=NLI.NLI_GCODE AND NLA.NLA_DIV=NLI.NLI_DIV) " +
			"LEFT JOIN NNC_LINK_SERVICE NLS ON (NLA.NLA_SVC_GCODE=NLS.NLS_GCODE AND NLA.NLA_DIV=NLS.NLS_DIV) " +
			"LEFT JOIN NNC_LINK_POLICY NLP ON (NLA.NLP_SEQ=NLP.NLP_SEQ AND NLA.NLA_DIV=NLP.NLP_DIV) " +
			"WHERE NLA_USE_YN=1 " +
			"AND NLA_SEQ=#{nla_seq}";
		
	// 접속 허용 정책 추가
	final String POLICY_ALLOW_INSERT = "INSERT INTO NNC_LINK_ALLOW(NLA_NAME, NLA_IP_GCODE, NLA_SVC_GCODE, NLP_SEQ, NLA_DIV, NLA_USE_YN) " +
			"VALUES(#{nla_name}, #{nla_ip_gcode}, #{nla_svc_gcode}, #{nlp_seq}, #{nla_div}, 1)";
	
	// 접속 허용 정책 수정
	final String POLICY_ALLOW_UPDATE = "UPDATE NNC_LINK_ALLOW SET NLA_NAME=#{nla_name}, NLA_IP_GCODE=#{nla_ip_gcode}, NLA_SVC_GCODE=#{nla_svc_gcode}, NLP_SEQ=#{nlp_seq} WHERE NLA_SEQ=#{nla_seq}";
	
	// 접속 허용 정책 삭제
	//final String POLICY_ALLOW_DELETE = "UPDATE NNC_LINK_ALLOW SET NLA_USE_YN=0 WHERE NLA_SEQ=#{nla_seq}";
	final String POLICY_ALLOW_DELETE = "DELETE FROM NNC_LINK_ALLOW WHERE NLA_SEQ=#{nla_seq}";
	
	// 콘텐츠 명 조회
	final String POLICY_CONTENTS_BYSEQ = "SELECT NPCS_TYPE, NPCS_NAME FROM NNC_POLICY_CONTENTS WHERE NPCS_SEQ=#{npcs_seq} AND NPCS_USE_YN=1";
	
	final String POLICY_CONTENTS_NAME = "SELECT NTC_NAME, NSC_NAME, NDC_NAME from NNC_TOP_CONTENTS NTC "+
			"LEFT JOIN NNC_SUB_CONTENTS NSC on (NSC.NTC_SEQ = NTC.NTC_SEQ) "+
			"LEFT JOIN NNC_DETAIL_CONTENTS NDC on (NDC.NTC_SEQ = NTC.NTC_SEQ AND NDC.NSC_SEQ = NSC.NSC_SEQ) "+
			"WHERE NDC_SEQ=#{ndc_seq} AND NDC.NTC_SEQ=#{ntc_seq} AND NDC.NSC_SEQ=#{nsc_seq}";
	
	// IP 주소 수정 - 송/수신 네트워크에서 수정했을 때
	final String IPADDR_UPDATE_SYSTEM = "UPDATE NNC_LINK_IPADDR SET NLI_IP=#{nli_ip} WHERE NLI_GCODE=#{nli_gcode} AND NLI_DIV=#{nli_div}";
		
	// IP 그룹 코드가 있는지 확인
	final String SELECT_IP_GCODE = "SELECT NLI_GCODE FROM NNC_LINK_IPADDR WHERE NLI_IP_NM=#{nli_ip_nm} AND NLI_DIV=#{nli_div} AND NLI_USE_YN=1 GROUP BY NLI_GCODE";
	final String NEXT_IP_GCODE = "SELECT IFNULL(MAX(NLI_GCODE)+1,1) NLI_GCODE FROM NNC_LINK_IPADDR WHERE NLI_DIV=#{nli_div}";
	
	// 서비스 그룹 코드가 있는지 확인
	final String SELECT_SVC_GCODE = "SELECT NLS_GCODE FROM NNC_LINK_SERVICE WHERE NLS_SERVICE_NM=#{nls_service_nm} AND NLS_DIV=#{nls_div} AND NLS_USE_YN=1 GROUP BY NLS_GCODE";
	final String NEXT_SVC_GCODE = "SELECT IFNULL(MAX(NLS_GCODE)+1,1) NLS_GCODE FROM NNC_LINK_SERVICE WHERE NLS_DIV=#{nls_div}";
		
	// 중복된 허용 정책이 있는지 확인
	/*final String POLICY_ALLOW_COUNT = "SELECT COUNT(*) CNT FROM NNC_LINK_ALLOW " +
			"WHERE (NLA_NAME=#{nla_name} OR (NLA_IP_GCODE=#{nla_ip_gcode} AND NLA_SVC_GCODE=#{nla_svc_gcode} AND NLP_SEQ=#{nlp_seq})) " +
			"AND NLA_DIV=#{nla_div} AND NLA_SEQ !=#{nla_seq} AND NLA_USE_YN=1";*/
	final String POLICY_ALLOW_COUNT = "SELECT COUNT(*) CNT FROM NNC_LINK_ALLOW WHERE NLA_NAME=#{nla_name} AND NLA_DIV=#{nla_div} AND NLA_SEQ !=#{nla_seq} AND NLA_USE_YN=1";
	final String POLICY_ALLOW_COUNT_POLICY = "SELECT COUNT(*) CNT FROM NNC_LINK_ALLOW WHERE NLP_SEQ=#{nlp_seq} AND NLA_IP_GCODE=#{nla_ip_gcode} AND NLA_DIV=#{nla_div} AND NLA_SEQ !=#{nla_seq} AND NLA_USE_YN=1";
	
	// IP가 허용 정책에서 사용 중인지 확인
	final String IPADDR_USE_COUNT = "SELECT SUM(CNT) CNT FROM ( " +
			"SELECT COUNT(*) CNT " +
			"FROM NNC_LINK_POLICY NLP, ( " +
			"	SELECT NLI_GCODE, NLI_DIV FROM NNC_LINK_IPADDR WHERE NLI_SEQ = #{nli_seq} " +
			") NLI " +
			"WHERE NLP.NLP_DIV=NLI.NLI_DIV " +
			"AND NLP_NW_IP=NLI.NLI_GCODE " +
			"UNION " +
		    "SELECT COUNT(*) CNT " +
		    "FROM NNC_LINK_ALLOW NLA, ( " +
			"	SELECT NLI_GCODE, NLI_DIV FROM NNC_LINK_IPADDR WHERE NLI_SEQ = #{nli_seq} " +
			") NLI " +
		    "WHERE NLA.NLA_DIV=NLI.NLI_DIV " +
		    "AND NLA_IP_GCODE=NLI.NLI_GCODE " +
		    "AND NLA_USE_YN=1) a";
	
	// 서비스가 허용 정책에서 사용 중인지 확인
	final String SERVICE_USE_COUNT_TX = "SELECT COUNT(*) CNT " +
			"FROM NNC_LINK_POLICY NLP, ( " +
			"	SELECT NLS_GCODE, NLS_DIV FROM NNC_LINK_SERVICE WHERE NLS_SEQ = #{nls_seq} " +
			") NLS " +
			"WHERE NLP.NLP_DIV=NLS.NLS_DIV " +
			"AND NLP_RCV_PORT=NLS.NLS_GCODE";
	final String SERVICE_USE_COUNT_RX = "SELECT COUNT(*) CNT " +
			"FROM NNC_LINK_POLICY NLP, ( " +
			"	SELECT NLS_GCODE, NLS_DIV FROM NNC_LINK_SERVICE WHERE NLS_SEQ = #{nls_seq} " +
			") NLS " +
			"WHERE (NLP_RCV_PORT=NLS.NLS_GCODE) OR (NLP_CONTROL_PORT=NLS.NLS_GCODE)";
		
	// 서비스 정책 조회 - 송/수신 구분 전체
	/*final String SELETE_POLICY_SERVICE = "SELECT NLP_SEQ, NLP_NAME, NLP_NW_IP, NLP_NW_PORT, NPCS_TYPE, NPCS_NAME, NLS.NLS_SERVICE_NM, NLS2.NLS_SERVICE_NM as RCV_SERVICE_NM, NCP_NAME, NLP_USE_YN " +
			"FROM NNC_LINK_POLICY NLP " +
			"LEFT JOIN NNC_LINK_SERVICE NLS ON (NLP.NLP_CONTROL_PORT=NLS.NLS_GCODE AND NLS.NLS_DIV=2 AND NLS.NLS_USE_YN=1) " +
			"LEFT JOIN NNC_LINK_SERVICE NLS2 ON (NLP.NLP_RCV_PORT=NLS2.NLS_GCODE AND NLS2.NLS_DIV=NLP.NLP_DIV AND NLS2.NLS_USE_YN=1) " +
			"LEFT JOIN NNC_POLICY_CONTENTS NPC ON (NLP.NPCS_SEQ=NPC.NPCS_SEQ) " +
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NCP.NCP_SEQ=NLP.NLP_PRO_ID) " +
			"WHERE NLP_DIV = #{nlp_div} " +
			"ORDER BY NLP_SEQ DESC";*/
	/*final String SELETE_POLICY_SERVICE = "SELECT NLP_SEQ, NLP_NAME, NLP_NW_IP, NLP_NW_PORT, NPCS_TYPE, NPCS_NAME, NLP_RCV_PORT, NLP_CONTROL_PORT, NCP_NAME, NLP_USE_YN, NLP_ALLOW_IP " +
			"FROM NNC_LINK_POLICY NLP " +
			"LEFT JOIN NNC_POLICY_CONTENTS NPC ON (NLP.NPCS_SEQ=NPC.NPCS_SEQ) " +
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NCP.NCP_SEQ=NLP.NLP_PRO_ID) " +
			"WHERE NLP_DIV = #{nlp_div} " +
			"ORDER BY NLP_SEQ DESC";*/
	final String SELETE_POLICY_SERVICE = "SELECT NLP.NLP_SEQ, NLP_NAME, NLP.NTC_SEQ, NTC_NAME, NSC_NAME, NDC_NAME, NLP_RCV_PORT, NLP_TRANS_PORT, NCP.NCP_NAME, " +
			"NLP_USE_YN, NCP.NCP_FILE_NAME, NCP2.NCP_NAME AS RX_NCP_NAME, " +
			"NLP_TX_NIC, NLP_ONEWAY_PORT, NLP_RX_NIC, NPE_NAME "+
			"FROM NNC_LINK_POLICY NLP "+
			"LEFT JOIN NNC_TOP_CONTENTS NTC ON (NLP.NTC_SEQ=NTC.NTC_SEQ) "+
			"LEFT JOIN NNC_SUB_CONTENTS NSC ON (NLP.NSC_SEQ=NSC.NSC_SEQ AND NTC.NTC_SEQ=NSC.NTC_SEQ) "+
			"LEFT JOIN NNC_DETAIL_CONTENTS NDC ON (NLP.NDC_SEQ=NDC.NDC_SEQ AND NTC.NTC_SEQ=NDC.NTC_SEQ AND NSC.NSC_SEQ=NDC.NSC_SEQ) "+
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLP.NLP_PRO_ID=NCP.NCP_SEQ) "+
			"LEFT JOIN NNC_CONF_PROGRAM NCP2 ON (NLP.NLP_RXPRO_ID=NCP2.NCP_SEQ) "+
			"LEFT JOIN NNC_POLICY_ETHERTYPE NPE ON (NLP.NPE_VALUE = NPE.NPE_VALUE) "+
			"WHERE NLP_DIV = #{nlp_div} " +
			"ORDER BY NLP_SEQ DESC";
	
	final String SELETE_POLICY_ROUTE = "SELECT NPR_SOURCE_IP, NPR_DESTINATION_IP, NPR_DESTINATION_PORT " +
			"FROM NNC_POLICY_ROUTE "+
			"WHERE NLP_SEQ = #{nlp_seq} ";
	
	// 서비스 정책 조회 - 해당 seq 하나
	final String POLICY_SERVICE_BYSEQ = "SELECT NLP_SEQ, NLP_NAME, NLP.NTC_SEQ, NLP.NSC_SEQ, NLP.NDC_SEQ, NLP_RCV_PORT, NLP_TRANS_PORT, " +
			"NLP_PRO_ID, NLP_RXPRO_ID, NLP_USE_YN, NLP_DIV, NTC_NAME, NDC_NAME, NCP.NCP_FILE_NAME, NCP.NCP_NAME, NCP2.NCP_NAME AS RX_NCP_NAME, "+
			"NLP_TX_NIC, NLP_ONEWAY_PORT, NLP_RX_NIC, NLP.NPE_VALUE, NLP.NPP_NO, NLP_SRC_IP, NLP_SRC_PORT, NLP_DST_IP, NLP_DST_PORT "+
			"FROM NNC_LINK_POLICY NLP "+
			"LEFT JOIN NNC_TOP_CONTENTS NTC ON (NLP.NTC_SEQ=NTC.NTC_SEQ) "+
			"LEFT JOIN NNC_SUB_CONTENTS NSC ON (NLP.NSC_SEQ=NSC.NSC_SEQ AND NTC.NTC_SEQ=NSC.NTC_SEQ) "+
			"LEFT JOIN NNC_DETAIL_CONTENTS NDC ON (NLP.NDC_SEQ=NDC.NDC_SEQ AND NTC.NTC_SEQ=NDC.NTC_SEQ AND NSC.NSC_SEQ=NDC.NSC_SEQ) "+
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLP.NLP_PRO_ID=NCP.NCP_SEQ) "+
			"LEFT JOIN NNC_CONF_PROGRAM NCP2 ON (NLP.NLP_RXPRO_ID=NCP2.NCP_SEQ) "+
			"LEFT JOIN NNC_POLICY_ETHERTYPE NPE ON (NLP.NPE_VALUE = NPE.NPE_VALUE) "+
			"LEFT JOIN NNC_POLICY_PROTOCOL NPP ON (NLP.NPP_NO = NPP.NPP_NO) "+
			"WHERE NLP_SEQ = #{nlp_seq}";
	
	// 서비스 정책 추가
	final String POLICY_SERVICE_INSERT = "INSERT INTO NNC_LINK_POLICY(NLP_NAME, NTC_SEQ, NSC_SEQ, NDC_SEQ, NLP_RCV_PORT, NLP_TRANS_PORT, NLP_USE_YN, NLP_DIV, NLP_PRO_ID, NLP_RXPRO_ID, "+
			"NPE_VALUE, NPP_NO, NLP_SRC_IP, NLP_SRC_PORT, NLP_DST_IP, NLP_DST_PORT, NLP_TX_NIC, NLP_ONEWAY_PORT, NLP_RX_NIC) " +
			"VALUES(#{nlp_name}, #{ntc_seq}, #{nsc_seq}, #{ndc_seq}, #{nlp_rcv_port}, #{nlp_trans_port}, #{nlp_use_yn}, #{nlp_div}, #{nlp_pro_id}, #{nlp_rxpro_id}, "+
			"#{npe_value}, #{npp_no}, #{nlp_src_ip}, #{nlp_src_port}, #{nlp_dst_ip}, #{nlp_dst_port}, #{nlp_tx_nic}, #{nlp_oneway_port}, #{nlp_rx_nic})";
	// 정책 경로 추가
	final String POLICY_ROUTE_INSERT = "INSERT INTO NNC_POLICY_ROUTE(NPR_SEQ, NPR_SOURCE_IP, NPR_DESTINATION_IP, NPR_DESTINATION_PORT, NLP_SEQ) " +
			"VALUES(#{npr_seq}, #{npr_source_ip}, #{npr_destination_ip}, #{npr_destination_port}, #{nlp_seq})";
	
	// 서비스 정책 수정
	final String POLICY_SERVICE_UPDATE = "UPDATE NNC_LINK_POLICY " +
			"SET NLP_NAME=#{nlp_name}, NTC_SEQ=#{ntc_seq}, NSC_SEQ=#{nsc_seq}, NDC_SEQ=#{ndc_seq}, " +
			"NLP_RCV_PORT=#{nlp_rcv_port}, NLP_TRANS_PORT=#{nlp_trans_port}, " +
			"NLP_USE_YN=#{nlp_use_yn}, NLP_PRO_ID=#{nlp_pro_id}, NLP_RXPRO_ID=#{nlp_rxpro_id}, "+
			"NPE_VALUE=#{npe_value}, NPP_NO=#{npp_no}, NLP_SRC_IP=#{nlp_src_ip}, NLP_SRC_PORT=#{nlp_src_port}, "+
			"NLP_DST_IP=#{nlp_dst_ip}, NLP_DST_PORT=#{nlp_dst_port}, NLP_TX_NIC=#{nlp_tx_nic}, NLP_RX_NIC=#{nlp_rx_nic}, NLP_ONEWAY_PORT=#{nlp_oneway_port} "+
			"WHERE NLP_SEQ=#{nlp_seq}";
	
	// 서비스 정책 삭제
	//final String POLICY_SERVICE_DELETE = "UPDATE NNC_LINK_POLICY SET NLP_USE_YN=0 WHERE NLP_SEQ=#{nlp_seq}";
	final String POLICY_SERVICE_DELETE = "DELETE FROM NNC_LINK_POLICY WHERE NLP_SEQ=#{nlp_seq}";
	
	final String POLICY_ROUTE_DELETE = "DELETE FROM NNC_POLICY_ROUTE WHERE NLP_SEQ=#{nlp_seq}";
	
	// 마지막 서비스 정책 번호 구하기
	final String LAST_SERVICE_SEQ = "SELECT MAX(NLP_SEQ) NLP_SEQ FROM NNC_LINK_POLICY";
	
	// 마지막 허용 정책 번호 구하기
	final String LAST_ALLOW_SEQ = "SELECT MAX(NLA_SEQ) NLA_SEQ FROM NNC_LINK_ALLOW";
	
	// 마지막 허용 서비스 번호 구하기
	final String LAST_OBJ_SERVICE_SEQ = "SELECT MAX(NLS_SEQ) NLS_SEQ FROM NNC_LINK_SERVICE";
	
	// 마지막 허용 IP 번호 구하기
	final String LAST_IPADDR_SEQ = "SELECT MAX(NLI_SEQ) NLI_SEQ FROM NNC_LINK_IPADDR";
	
	// 중복된 연계 정책이 있는지 확인
	final String POLICY_SERVICE_COUNT = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY WHERE NLP_NAME=#{nlp_name} AND NLP_SEQ!=#{nlp_seq} AND NLP_DIV=#{nlp_div}";
	final String POLICY_SERVICE_COUNT_TYPE = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY WHERE NPCS_SEQ=#{npcs_seq} AND NLP_SEQ!=#{nlp_seq} AND NLP_DIV=#{nlp_div}";
	final String POLICY_SERVICE_COUNT_CPORT = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY WHERE NLP_CONTROL_PORT=#{nlp_control_port} AND NLP_SEQ!=#{nlp_seq} AND NLP_DIV=1";
	final String POLICY_SERVICE_COUNT_RPORT = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY WHERE NLP_RCV_PORT=#{nlp_rcv_port} AND NLP_SEQ!=#{nlp_seq} AND NLP_DIV=2";
	final String POLICY_SERVICE_COUNT_PROG = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY WHERE NLP_PRO_ID=#{nlp_pro_id} AND NLP_SEQ!=#{nlp_seq} AND NLP_DIV=#{nlp_div}";
	
	final String IPADDR_OBJ_NM = "SELECT NLI_IP_NM FROM NNC_LINK_IPADDR WHERE NLI_GCODE=#{nli_gcode} AND NLI_DIV=#{nli_div}";
	final String SERVICE_OBJ_NM = "SELECT NLS_SERVICE_NM FROM NNC_LINK_SERVICE WHERE NLS_GCODE=#{nls_gcode} AND NLS_DIV=#{nls_div}";
	
	// 연계 정책이 허용 정책에서 사용 중인지 확인
	final String POLICY_USE_COUNT = "SELECT COUNT(*) CNT FROM NNC_LINK_ALLOW WHERE NLP_SEQ = #{nlp_seq} AND NLA_USE_YN=1";
	
	// 정책 사용 여부 변경
	final String POLICY_USE_CHANGE = "UPDATE NNC_LINK_POLICY SET NLP_USE_YN = #{nlp_use_yn} WHERE NLP_SEQ = #{nlp_seq}";
	
	@Select(SELETE_IPADDR)
    @Results(value = {
    		@Result(property="nli_seq", column="NLI_SEQ"),
    		@Result(property="nli_ip_type", column="NLI_IP_TYPE"),
	        @Result(property="nli_ip", column="NLI_IP"),
	        @Result(property="nli_ip_nm", column="NLI_IP_NM"),
	        @Result(property="nli_gcode", column="NLI_GCODE")
    })
    ArrayList<NcLinkPolicy> getIpaddr(@Param("nli_div") int nli_div);
	
	@Select(SELETE_IPADDR_BYSEQ)
    @Results(value = {
    		@Result(property="nli_seq", column="NLI_SEQ"),
    		@Result(property="nli_ip_type", column="NLI_IP_TYPE"),
	        @Result(property="nli_ip", column="NLI_IP"),
	        @Result(property="nli_ip_nm", column="NLI_IP_NM"),
	        @Result(property="nli_gcode", column="NLI_GCODE"),
	        @Result(property="nli_div", column="NLI_DIV")
    })
	NcLinkPolicy getIpaddrBySeq(@Param("nli_seq") int nli_seq);
	
	@Select(SELETE_SERVICE)
    @Results(value = {
    		@Result(property="nls_seq", column="NLS_SEQ"),
	        @Result(property="nls_port", column="NLS_PORT"),
	        @Result(property="nls_service_nm", column="NLS_SERVICE_NM"),
	        @Result(property="nls_gcode", column="NLS_GCODE")
    })
    ArrayList<NcLinkPolicy> getService(@Param("nls_div") int nls_div);
	
	@Select(SELETE_SERVICE_BYSEQ)
    @Results(value = {
    		@Result(property="nls_seq", column="NLS_SEQ"),
	        @Result(property="nls_port", column="NLS_PORT"),
	        @Result(property="nls_service_nm", column="NLS_SERVICE_NM"),
	        @Result(property="nls_gcode", column="NLS_GCODE"),
	        @Result(property="nls_div", column="NLS_DIV")
    })
	NcLinkPolicy getServiceBySeq(@Param("nls_seq") int nls_seq);
	
	@Insert(IPADDR_INSERT)
	int ipaddr_insert(NcLinkPolicy ncLinkPolicy);
	
	@Insert(SERVICE_INSERT)
	int service_insert(NcLinkPolicy ncLinkPolicy);
	
	@Insert(IPADDR_UPDATE)
	int ipaddr_update(NcLinkPolicy ncLinkPolicy);
	
	@Insert(IPADDR_UPDATE_SYSTEM)
	int ipaddr_update_system(NcLinkPolicy ncLinkPolicy);
	
	@Insert(SERVICE_UPDATE)
	int service_update(NcLinkPolicy ncLinkPolicy);
	
	@Insert(IPADDR_DELETE)
	int ipaddr_delete(@Param("nli_seq") int nli_seq);
	
	@Insert(SERVICE_DELETE)
	int service_delete(@Param("nls_seq") int nls_seq);
	
	@Select(SELETE_POLICY_ALLOW)
    @Results(value = {
    		@Result(property="nla_seq", column="NLA_SEQ"),
    		@Result(property="nla_name", column="NLA_NAME"),
	        @Result(property="nla_ip_gcode", column="NLA_IP_GCODE"),
	        @Result(property="nli_ip_nm", column="NLI_IP_NM"),
	        @Result(property="nla_svc_gcode", column="NLA_SVC_GCODE"),
	        @Result(property="nls_service_nm", column="NLS_SERVICE_NM"),
	        @Result(property="nlp_name", column="NLP_NAME")
    })
    ArrayList<NcLinkPolicy> getPolicyAllow(@Param("nla_div") int nla_div);
	
	@Select(POLICY_ALLOW_BYSEQ)
    @Results(value = {
    		@Result(property="nla_seq", column="NLA_SEQ"),
    		@Result(property="nla_name", column="NLA_NAME"),
    		@Result(property="nla_ip_gcode", column="NLA_IP_GCODE"),
	        @Result(property="nli_ip_nm", column="NLI_IP_NM"),
	        @Result(property="nla_svc_gcode", column="NLA_SVC_GCODE"),
	        @Result(property="nls_service_nm", column="NLS_SERVICE_NM"),
    		@Result(property="nla_div", column="NLA_DIV"),
    		@Result(property="nlp_seq", column="NLP_SEQ"),
    		@Result(property="nlp_name", column="NLP_NAME")
    })
	NcLinkPolicy getPolicyAllowBySeq(@Param("nla_seq") int nla_seq);
		
	@Insert(POLICY_ALLOW_INSERT)
	int policy_allow_insert(NcLinkPolicy ncLinkPolicy);
	
	@Insert(POLICY_ALLOW_UPDATE)
	int policy_allow_update(NcLinkPolicy ncLinkPolicy);
	
	@Insert(POLICY_ALLOW_DELETE)
	int policy_allow_delete(@Param("nla_seq") int nla_seq);
	
	@Select(SELECT_IP_GCODE)
	String getIpGcode(@Param("nli_ip_nm") String nli_ip_nm, @Param("nli_div") int nli_div);
	
	@Select(SELECT_SVC_GCODE)
	String getSvcGcode(@Param("nls_service_nm") String nls_service_nm, @Param("nls_div") int nls_div);
	
	@Select(NEXT_IP_GCODE)
	int nextIpGcode(@Param("nli_div") int nli_div);
	
	@Select(NEXT_SVC_GCODE)
	int nextSvcGcode(@Param("nls_div") int nls_div);
	
	@Select(POLICY_ALLOW_COUNT)
	int policy_allow_count(NcLinkPolicy ncLinkPolicy);
	@Select(POLICY_ALLOW_COUNT_POLICY)
	int policy_allow_count_policy(NcLinkPolicy ncLinkPolicy);
	
	@Select(IPADDR_USE_COUNT)
	int ipaddr_use_count(@Param("nli_seq") int nli_seq);
	
	@Select(SERVICE_USE_COUNT_TX)
	int service_use_count_tx(@Param("nls_seq") int nls_seq);
	@Select(SERVICE_USE_COUNT_RX)
	int service_use_count_rx(@Param("nls_seq") int nls_seq);

	@Select(SELETE_POLICY_SERVICE)
    @Results(value = {
    		@Result(property="nlp_seq", column="NLP_SEQ"),
	        @Result(property="nlp_name", column="NLP_NAME"),
	        @Result(property="ntc_name", column="NTC_NAME"),
	        @Result(property="nsc_name", column="NSC_NAME"),
	        @Result(property="ndc_name", column="NDC_NAME"),
    		@Result(property="nlp_rcv_port", column="NLP_RCV_PORT"),
    		@Result(property="nlp_trans_port", column="NLP_TRANS_PORT"),
    		@Result(property="ncp_name", column="NCP_NAME"),
    		@Result(property="nlp_use_yn", column="NLP_USE_YN"),
    		@Result(property="ncp_file_name", column="NCP_FILE_NAME")
    })
    ArrayList<NcLinkPolicy> getPolicyService(@Param("nlp_div") int nlp_div);
	
	@Select(SELETE_POLICY_ROUTE)
    @Results(value = {
    		@Result(property="npr_source_ip", column="NPR_SOURCE_IP"),
	        @Result(property="npr_destination_ip", column="NPR_DESTINATION_IP"),
	        @Result(property="npr_destination_port", column="NPR_DESTINATION_PORT")
    })
	ArrayList<NcLinkPolicy> getPolicyRoute(@Param("nlp_seq") int nlp_seq);
	
	@Select(POLICY_SERVICE_BYSEQ)
    @Results(value = {
    		@Result(property="nlp_seq", column="NLP_SEQ"),
	        @Result(property="nlp_name", column="NLP_NAME"),
	        @Result(property="ntc_seq", column="NTC_SEQ"),
	        @Result(property="nsc_seq", column="NSC_SEQ"),
	        @Result(property="ndc_seq", column="NDC_SEQ"),
	        @Result(property="nlp_rcv_port", column="NLP_RCV_PORT"),
    		@Result(property="nlp_trans_port", column="NLP_TRANS_PORT"),
    		@Result(property="nlp_pro_id", column="NLP_PRO_ID"),
    		@Result(property="nlp_use_yn", column="NLP_USE_YN"),
    		@Result(property="nlp_div", column="NLP_DIV"),
    		@Result(property="ntc_name", column="NTC_NAME"),
    		@Result(property="ndc_name", column="NDC_NAME"),
    		@Result(property="ncp_file_name", column="NCP_FILE_NAME")
    })
	NcLinkPolicy getPolicyServiceBySeq(@Param("nlp_seq") int nlp_seq);
	
	@Insert(POLICY_SERVICE_INSERT)
	int policy_service_insert(NcLinkPolicy ncLinkPolicy);
	
	@Insert(POLICY_ROUTE_INSERT)
	int policy_route_insert(NcLinkPolicy ncLinkPolicy);
	
	@Insert(POLICY_SERVICE_UPDATE)
	int policy_service_update(NcLinkPolicy ncLinkPolicy);
	
	@Insert(POLICY_SERVICE_DELETE)
	int policy_service_delete(@Param("nlp_seq") int nlp_seq);
	
	@Insert(POLICY_ROUTE_DELETE)
	int policy_route_delete(@Param("nlp_seq") int nlp_seq);
	
	@Select(LAST_SERVICE_SEQ)
	int last_service_seq();
	
	@Select(LAST_ALLOW_SEQ)
	int last_allow_seq();
	
	@Select(LAST_OBJ_SERVICE_SEQ)
	int last_obj_service_seq();
	
	@Select(LAST_IPADDR_SEQ)
	int last_ipaddr_seq();
	
	@Select(POLICY_SERVICE_COUNT)
	int policy_service_count(NcLinkPolicy ncLinkPolicy);
	@Select(POLICY_SERVICE_COUNT_TYPE)
	int policy_service_count_type(NcLinkPolicy ncLinkPolicy);
	@Select(POLICY_SERVICE_COUNT_CPORT)
	int policy_service_count_cport(NcLinkPolicy ncLinkPolicy);
	@Select(POLICY_SERVICE_COUNT_RPORT)
	int policy_service_count_rport(NcLinkPolicy ncLinkPolicy);
	@Select(POLICY_SERVICE_COUNT_PROG)
	int policy_service_count_prog(NcLinkPolicy ncLinkPolicy);
	
	@Select(IPADDR_OBJ_NM)
	String getIpObjNm(@Param("nli_gcode") int nli_gcode, @Param("nli_div") int nli_div);
	
	@Select(SERVICE_OBJ_NM)
	String getSvcObjNm(@Param("nls_gcode") int nls_gcode, @Param("nls_div") int nls_div);
	
	@Select(POLICY_CONTENTS_BYSEQ)
    @Results(value = {
    		@Result(property="npcs_type", column="NPCS_TYPE"),
	        @Result(property="npcs_name", column="NPCS_NAME")
    })
	NcLinkPolicy getContsPolicy(@Param("npcs_seq") int npcs_seq);
	
	@Select(POLICY_CONTENTS_NAME)
    @Results(value = {
    		@Result(property="ntc_name", column="NTC_NAME"),
	        @Result(property="nsc_name", column="NSC_NAME"),
	        @Result(property="ndc_name", column="NDC_NAME")
    })
	NcLinkPolicy getContsName(@Param("ntc_seq") int ntc_seq, @Param("nsc_seq") int nsc_seq, @Param("ndc_seq") int ndc_seq);
	
	@Select(POLICY_USE_COUNT)
	int policy_use_count(@Param("nlp_seq") int nlp_seq);
	
	@Update(POLICY_USE_CHANGE)
	int policy_use_change(@Param("nlp_use_yn") int nlp_use_yn, @Param("nlp_seq") int nlp_seq);
}