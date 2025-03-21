package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcPolicy;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface NcPolicyMapper {
	
	// IP 주소 조회 - 송/수신 구분 전체
	final String SELETE_IPADDR = "SELECT NOI_SEQ, NOI_IP_TYPE, NOI_IP, NOI_OBJ_NM, NOI_GCODE FROM NNC_OBJ_IPADDR WHERE NOI_USE_YN=1 AND NOI_DIV=#{noi_div} ORDER BY NOI_SEQ DESC";
	
	// IP 주소 조회 - 해당 seq 하나
	final String SELETE_IPADDR_BYSEQ = "SELECT NOI_SEQ, NOI_IP_TYPE, NOI_IP, NOI_OBJ_NM, NOI_GCODE, NOI_DIV FROM NNC_OBJ_IPADDR WHERE NOI_SEQ=#{noi_seq}";
		
	// IP 주소 추가
	final String IPADDR_INSERT = "INSERT INTO NNC_OBJ_IPADDR(NOI_IP_TYPE, NOI_IP, NOI_OBJ_NM, NOI_GCODE, NOI_DIV, NOI_USE_YN) " +
			"VALUES(#{noi_ip_type}, #{noi_ip}, #{noi_obj_nm}, #{noi_gcode}, #{noi_div}, 1)"; 
		
	// IP 주소 수정
	final String IPADDR_UPDATE = "UPDATE NNC_OBJ_IPADDR " +
			"SET NOI_IP_TYPE=#{noi_ip_type}, NOI_IP=#{noi_ip}, NOI_OBJ_NM=#{noi_obj_nm}, NOI_GCODE=#{noi_gcode}, NOI_DIV=#{noi_div} WHERE NOI_SEQ=#{noi_seq}";
		
	// IP 주소 삭제
	final String IPADDR_DELETE = "UPDATE NNC_OBJ_IPADDR SET NOI_USE_YN=0 WHERE NOI_SEQ=#{noi_seq}";
		
	// 서비스 조회
	final String SELETE_SERVICE = "SELECT NOS_SEQ, NOS_PORT, NOS_SERVICE_NM, NOS_OBJ_NM, NOS_GCODE FROM NNC_OBJ_SERVICE WHERE NOS_USE_YN=1 AND NOS_DIV=#{nos_div} ORDER BY NOS_SEQ DESC";
	
	// 서비스 조회 - 해당 seq 하나
	final String SELETE_SERVICE_BYSEQ = "SELECT NOS_SEQ, NOS_PORT, NOS_SERVICE_NM, NOS_OBJ_NM, NOS_GCODE, NOS_DIV FROM NNC_OBJ_SERVICE WHERE NOS_USE_YN=1 AND NOS_SEQ=#{nos_seq}";
		
	// 서비스 추가
	final String SERVICE_INSERT = "INSERT INTO NNC_OBJ_SERVICE(NOS_PORT, NOS_SERVICE_NM, NOS_OBJ_NM, NOS_GCODE, NOS_DIV, NOS_USE_YN) " +
			"VALUES(#{nos_port}, #{nos_service_nm}, #{nos_obj_nm}, #{nos_gcode}, #{nos_div}, 1)"; 

	// 서비스 수정
	final String SERVICE_UPDATE = "UPDATE NNC_OBJ_SERVICE " +
			"SET NOS_PORT=#{nos_port}, NOS_SERVICE_NM=#{nos_service_nm}, NOS_OBJ_NM=#{nos_obj_nm}, NOS_GCODE=#{nos_gcode}, NOS_DIV=#{nos_div} WHERE NOS_SEQ=#{nos_seq}";
	
	// 서비스 삭제
	final String SERVICE_DELETE = "UPDATE NNC_OBJ_SERVICE SET NOS_USE_YN=0 WHERE NOS_SEQ=#{nos_seq}"; 
	
	// 접속 허용 정책 조회 - 송/수신 구분 전체
	final String SELETE_POLICY_ALLOW = "SELECT NPA_SEQ, NPA_SIP_GCODE, NOI.NOI_OBJ_NM SIP_OBJNM, NPA_DIP_GCODE, NOI2.NOI_OBJ_NM DIP_OBJNM, NPA_SSVC_GCODE, " +
			"IFNULL(NOS.NOS_OBJ_NM,'모든 서비스') SSVC_OBJNM, NPA_DSVC_GCODE, NOS2.NOS_OBJ_NM DSVC_OBJNM, getServiceName(NPC_NO) NPC_NAME, NPC_NO, NOI.NOI_IP_TYPE, NOI.NOI_IP SIP, NOI2.NOI_IP DIP," +
			"NOS.NOS_PORT SPORT, NOS2.NOS_PORT DPORT " +
			"FROM NNC_POLICY_ALLOW NPA " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPA.NPA_SIP_GCODE=NOI.NOI_GCODE AND NPA.NPA_DIV=NOI.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPA.NPA_DIP_GCODE=NOI2.NOI_GCODE AND NPA.NPA_DIV=NOI2.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS ON (NPA.NPA_SSVC_GCODE=NOS.NOS_GCODE AND NPA.NPA_DIV=NOS.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS2 ON (NPA.NPA_DSVC_GCODE=NOS2.NOS_GCODE AND NPA.NPA_DIV=NOS2.NOS_DIV) " +
			"WHERE NPA_USE_YN=1 " +
			"AND NPA_DIV=#{npa_div} " +
			"ORDER BY NPA_SEQ DESC";
		
	// 접속 허용 정책 조회 - 해당 seq 하나
	final String POLICY_ALLOW_BYSEQ = "SELECT NPA_SEQ, NPA_SIP_GCODE, NOI.NOI_OBJ_NM SIP_OBJNM, NPA_DIP_GCODE, NOI2.NOI_OBJ_NM DIP_OBJNM, NPA_SSVC_GCODE, " +
			"IFNULL(NOS.NOS_OBJ_NM,'모든 서비스') SSVC_OBJNM, NPA_DSVC_GCODE, NOS2.NOS_OBJ_NM DSVC_OBJNM, getServiceName(NPC_NO) NPC_NAME, NPC_NO, NOI.NOI_IP_TYPE, NOI.NOI_IP SIP, NOI2.NOI_IP DIP," +
			"NOS.NOS_PORT SPORT, NOS2.NOS_PORT DPORT, NPA_DIV " +
			"FROM NNC_POLICY_ALLOW NPA " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPA.NPA_SIP_GCODE=NOI.NOI_GCODE AND NPA.NPA_DIV=NOI.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPA.NPA_DIP_GCODE=NOI2.NOI_GCODE AND NPA.NPA_DIV=NOI2.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS ON (NPA.NPA_SSVC_GCODE=NOS.NOS_GCODE AND NPA.NPA_DIV=NOS.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS2 ON (NPA.NPA_DSVC_GCODE=NOS2.NOS_GCODE AND NPA.NPA_DIV=NOS2.NOS_DIV) " +
			"WHERE NPA_USE_YN=1 " +
			"AND NPA_SEQ=#{npa_seq}";
		
	// 접속 허용 정책 추가
	final String POLICY_ALLOW_INSERT = "INSERT INTO NNC_POLICY_ALLOW(NPA_SIP_GCODE, NPA_DIP_GCODE, NPA_SSVC_GCODE, NPA_DSVC_GCODE, NPC_NO, NPA_DIV, NPA_USE_YN) " +
			"VALUES(#{npa_sip_gcode}, #{npa_dip_gcode}, #{npa_ssvc_gcode}, #{npa_dsvc_gcode}, #{npc_no}, #{npa_div}, 1)";
	
	// 접속 허용 정책 수정
	final String POLICY_ALLOW_UPDATE = "UPDATE NNC_POLICY_ALLOW SET NPA_SIP_GCODE=#{npa_sip_gcode}, NPA_DIP_GCODE=#{npa_dip_gcode}, NPA_SSVC_GCODE=#{npa_ssvc_gcode}, " +
			"NPA_DSVC_GCODE=#{npa_dsvc_gcode}, NPC_NO=#{npc_no} WHERE NPA_SEQ=#{npa_seq}";
	
	// 접속 허용 정책 삭제
	final String POLICY_ALLOW_DELETE = "UPDATE NNC_POLICY_ALLOW SET NPA_USE_YN=0 WHERE NPA_SEQ=#{npa_seq}"; 
	
	// 프로토콜 조회
	final String SELETE_PROTOCOL = "SELECT NPC_NO, NPC_NAME FROM NNC_PROT_CODE";
	
	// 프로토콜에 따른 콘텐츠 타입 조회
	final String SELETE_CONTS_TYPE = "SELECT NPCS_TYPE, NPC_NO FROM NNC_POLICY_CONTENTS WHERE NPCS_USE_YN=1 GROUP BY NPCS_TYPE ORDER BY NPCS_SEQ";
	
	// 상위 콘텐츠 조회
	final String SELETE_TOP_CONTS = "SELECT NTC_SEQ, NTC_NAME FROM NNC_TOP_CONTENTS ORDER BY NTC_SEQ";
	
	// 서브 콘텐츠 조회
	final String SELETE_SUB_CONTS = "SELECT NSC_SEQ, NSC_NAME FROM NNC_SUB_CONTENTS WHERE NTC_SEQ=#{ntc_seq}";
	
	// 상세 콘텐츠 조회
	final String SELETE_DETAIL_CONTS = "SELECT NDC_SEQ, NDC_NAME FROM NNC_DETAIL_CONTENTS WHERE NTC_SEQ=#{ntc_seq} AND NSC_SEQ=#{nsc_seq} ORDER BY NDC_SEQ";
		
	// 콘텐츠 명 조회
	final String SELETE_CONTENTS = "SELECT NPCS_SEQ, NPCS_NAME FROM NNC_POLICY_CONTENTS WHERE NPC_NO=#{npc_no} AND NPCS_TYPE=#{npcs_type} AND NPCS_USE_YN=1";
	
	// IP 주소 수정 - 송/수신 네트워크에서 수정했을 때
	final String IPADDR_UPDATE_SYSTEM = "UPDATE NNC_OBJ_IPADDR SET NOI_IP=#{noi_ip} WHERE NOI_GCODE=#{noi_gcode} AND NOI_DIV=#{noi_div}";
		
	// IP 그룹 코드가 있는지 확인
	final String SELECT_IP_GCODE = "SELECT NOI_GCODE FROM NNC_OBJ_IPADDR WHERE NOI_OBJ_NM=#{noi_obj_nm} AND NOI_DIV=#{noi_div} AND NOI_USE_YN=1 GROUP BY NOI_GCODE";
	final String NEXT_IP_GCODE = "SELECT IFNULL(MAX(NOI_GCODE)+1,3) NOI_GCODE FROM NNC_OBJ_IPADDR WHERE NOI_DIV=#{noi_div}"; // 1과 2는 송/수신 장치에서 사용
	
	// 서비스 그룹 코드가 있는지 확인
	final String SELECT_SVC_GCODE = "SELECT NOS_GCODE FROM NNC_OBJ_SERVICE WHERE NOS_OBJ_NM=#{nos_obj_nm} AND NOS_DIV=#{nos_div} AND NOS_USE_YN=1 GROUP BY NOS_GCODE";
	final String NEXT_SVC_GCODE = "SELECT IFNULL(MAX(NOS_GCODE)+1,1) NOS_GCODE FROM NNC_OBJ_SERVICE WHERE NOS_DIV=#{nos_div}";
		
	// 중복된 허용 정책이 있는지 확인
	final String POLICY_ALLOW_COUNT = "SELECT COUNT(*) CNT FROM NNC_POLICY_ALLOW WHERE NPA_SIP_GCODE=#{npa_sip_gcode} AND NPA_DIP_GCODE=#{npa_dip_gcode} " +
			"AND NPA_SSVC_GCODE=#{npa_ssvc_gcode} AND NPA_DSVC_GCODE=#{npa_dsvc_gcode} AND NPC_NO=#{npc_no} AND NPA_DIV=#{npa_div} AND NPA_USE_YN=1";
	
	// IP가 허용 정책에서 사용 중인지 확인
	final String IPADDR_USE_COUNT = "SELECT SUM(CNT) CNT FROM ( " +
			"SELECT COUNT(*) CNT " +
			"FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP, ( " +
			"	SELECT NOI_GCODE, NOI_DIV FROM NNC_OBJ_IPADDR WHERE NOI_SEQ = #{noi_seq} " +
			") NOI " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ " +
			"AND NCP.NCP_DIV=NOI.NOI_DIV " +
			"AND (NPS_TX_INTIP=NOI.NOI_GCODE OR NPS_RX_INTIP=NOI.NOI_GCODE OR NPS_CONTS_IP=NOI.NOI_GCODE) " +
			"AND NPS_USE_YN=1 " +
		    "UNION " +
		    "SELECT COUNT(*) CNT " +
		    "FROM NNC_POLICY_ALLOW NPA, ( " +
			"	SELECT NOI_GCODE, NOI_DIV FROM NNC_OBJ_IPADDR WHERE NOI_SEQ = #{noi_seq} " +
			") NOI " +
		    "WHERE NPA.NPA_DIV=NOI.NOI_DIV " +
		    "AND (NPA_SIP_GCODE=NOI.NOI_GCODE OR NPA_DIP_GCODE=NOI.NOI_GCODE) " +
		    "AND NPA_USE_YN=1) a";
	
	// 서비스가 허용 정책에서 사용 중인지 확인
	final String SERVICE_USE_COUNT = "SELECT SUM(CNT) CNT FROM ( " +
			"SELECT COUNT(*) CNT " +
			"FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP, ( " +
			"	SELECT NOS_GCODE, NOS_DIV FROM NNC_OBJ_SERVICE WHERE NOS_SEQ = #{nos_seq} " +
			") NOS " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ " +
			"AND NCP.NCP_DIV=NOS.NOS_DIV " +
			"AND (NPS_TX_INTPORT=NOS.NOS_GCODE OR NPS_RX_INTPORT=NOS.NOS_GCODE OR NPS_CONTS_PORT=NOS.NOS_GCODE) " +
			"AND NPS_USE_YN=1 " +
		    "UNION " +
		    "SELECT COUNT(*) CNT " +
		    "FROM NNC_POLICY_ALLOW NPA, ( " +
			"	SELECT NOS_GCODE, NOS_DIV FROM NNC_OBJ_SERVICE WHERE NOS_SEQ = #{nos_seq} " +
			") NOS " +
		    "WHERE NPA.NPA_DIV=NOS.NOS_DIV " +
		    "AND (NPA_SSVC_GCODE=NOS.NOS_GCODE OR NPA_DSVC_GCODE=NOS.NOS_GCODE) " +
		    "AND NPA_USE_YN=1) a";
	
	// 서비스가 연계정책에서 사용 중인지 확인
	final String SERVICE_LINK_COUNT = "SELECT COUNT(*) CNT FROM NNC_LINK_POLICY NLP, ( " +
			"	SELECT NOS_GCODE, NOS_DIV FROM NNC_OBJ_SERVICE WHERE NOS_SEQ = #{nos_seq} " +
			") NOS " +
			"WHERE NLP.NLP_DIV=NOS.NOS_DIV " +
			"AND NLP_CONTROL_PORT=NOS_GCODE " +
			"AND NLP_USE_YN=1";
		
	// 서비스 정책 조회 - 송/수신 구분 전체
	final String SELETE_POLICY_SERVICE = "SELECT NPS_SEQ, NCP_NAME, NPS_CONF_FILE, NOI.NOI_OBJ_NM AS TX_INTIP_OBJNM, NOI2.NOI_OBJ_NM AS RX_INTIP_OBJNM, NOI3.NOI_OBJ_NM AS CONTS_IP_OBJNM, " +
			"NOS.NOS_OBJ_NM AS TX_INTPORT_OBJNM, NOS2.NOS_OBJ_NM AS RX_INTPORT_OBJNM, NOS3.NOS_OBJ_NM AS CONTS_PORT_OBJNM, getServiceName(NPS.NPC_NO) NPC_NAME, NPS.NPC_NO, " +
			"(CASE NPS_ELC_FLAG WHEN '0' THEN '사용안함' ELSE '사용함' END) AS ELC_FLAG_TEXT, NPS_RESEND, NPCS_HEADER, NPCS_NAME, NPCS_TYPE " +
			"FROM NNC_POLICY_SERVICE NPS " +
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NCP.NCP_SEQ=NPS.NCP_SEQ) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPS.NPS_TX_INTIP=NOI.NOI_GCODE AND NCP.NCP_DIV=NOI.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPS.NPS_RX_INTIP=NOI2.NOI_GCODE AND NCP.NCP_DIV=NOI2.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI3 ON (NPS.NPS_CONTS_IP=NOI3.NOI_GCODE AND NCP.NCP_DIV=NOI3.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS ON (NPS.NPS_TX_INTPORT=NOS.NOS_GCODE AND NCP.NCP_DIV=NOS.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS2 ON (NPS.NPS_RX_INTPORT=NOS2.NOS_GCODE AND NCP.NCP_DIV=NOS2.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS3 ON (NPS.NPS_CONTS_PORT=NOS3.NOS_GCODE AND NCP.NCP_DIV=NOS3.NOS_DIV) " +
			"LEFT JOIN NNC_POLICY_CONTENTS NPC ON (NPS.NPCS_SEQ=NPC.NPCS_SEQ) " +
			"WHERE NPS_USE_YN = 1 " +
			"AND NCP_DIV = #{ncp_div} " +
			"ORDER BY NPS_SEQ DESC";
	
	// 서비스 정책 조회 - 해당 seq 하나
	final String POLICY_SERVICE_BYSEQ = "SELECT NPS_SEQ, NCP_NAME, NPS_CONF_FILE, NOI.NOI_OBJ_NM AS TX_INTIP_OBJNM, NOI2.NOI_OBJ_NM AS RX_INTIP_OBJNM, NOI3.NOI_OBJ_NM AS CONTS_IP_OBJNM, " +
			"NOS.NOS_OBJ_NM AS TX_INTPORT_OBJNM, NOS2.NOS_OBJ_NM AS RX_INTPORT_OBJNM, NOS3.NOS_OBJ_NM AS CONTS_PORT_OBJNM, getServiceName(NPS.NPC_NO) NPC_NAME, NPS.NPC_NO, " +
			"(CASE NPS_ELC_FLAG WHEN '0' THEN '사용안함' ELSE '사용함' END) AS ELC_FLAG_TEXT, NCP_DIV, " +
			"NPS.NCP_SEQ, NOI.NOI_IP AS TX_INTIP, NOI2.NOI_IP AS RX_INTIP, NOI3.NOI_IP AS CONTS_IP, NOS.NOS_PORT AS TX_INTPORT, NOS2.NOS_PORT AS RX_INTPORT, NOS3.NOS_PORT AS CONTS_PORT, NCP_FILE_NAME, NPS_ELC_FLAG, "+
			"NPS_TX_INTIP, NPS_RX_INTIP, NPS_CONTS_IP, NPS_TX_INTPORT, NPS_RX_INTPORT, NPS_CONTS_PORT, NPS_RESEND, NPCS_HEADER, NPCS_TYPE, NPS.NPCS_SEQ "+
			"FROM NNC_POLICY_SERVICE NPS " +
			"LEFT JOIN NNC_CONF_PROGRAM NCP ON (NCP.NCP_SEQ=NPS.NCP_SEQ) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPS.NPS_TX_INTIP=NOI.NOI_GCODE AND NCP.NCP_DIV=NOI.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPS.NPS_RX_INTIP=NOI2.NOI_GCODE AND NCP.NCP_DIV=NOI2.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_IPADDR NOI3 ON (NPS.NPS_CONTS_IP=NOI3.NOI_GCODE AND NCP.NCP_DIV=NOI3.NOI_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS ON (NPS.NPS_TX_INTPORT=NOS.NOS_GCODE AND NCP.NCP_DIV=NOS.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS2 ON (NPS.NPS_RX_INTPORT=NOS2.NOS_GCODE AND NCP.NCP_DIV=NOS2.NOS_DIV) " +
			"LEFT JOIN NNC_OBJ_SERVICE NOS3 ON (NPS.NPS_CONTS_PORT=NOS3.NOS_GCODE AND NCP.NCP_DIV=NOS3.NOS_DIV) " +
			"LEFT JOIN NNC_POLICY_CONTENTS NPC ON (NPS.NPCS_SEQ=NPC.NPCS_SEQ) " +
			"WHERE NPS_SEQ = #{nps_seq}";
	
	// 서비스 정책 추가
	final String POLICY_SERVICE_INSERT = "INSERT INTO NNC_POLICY_SERVICE(NPC_NO, NCP_SEQ, NPS_TX_INTIP, NPS_TX_INTPORT, NPS_RX_INTIP, NPS_RX_INTPORT, NPS_CONTS_IP, NPS_CONTS_PORT, NPS_ELC_FLAG, NPS_CONF_FILE, NPS_RESEND, NPS_USE_YN, NPCS_SEQ) " +
			"VALUES(#{npc_no}, #{ncp_seq}, #{nps_tx_intip}, #{nps_tx_intport}, #{nps_rx_intip}, #{nps_rx_intport}, #{nps_conts_ip}, #{nps_conts_port}, #{nps_elc_flag}, #{nps_conf_file}, #{nps_resend}, 1, #{npcs_seq})";
	
	// 서비스 정책 수정
	final String POLICY_SERVICE_UPDATE = "UPDATE NNC_POLICY_SERVICE " +
			"SET NCP_SEQ=#{ncp_seq}, NPS_TX_INTIP=#{nps_tx_intip}, NPS_TX_INTPORT=#{nps_tx_intport}, NPS_RX_INTIP=#{nps_rx_intip}, NPS_RX_INTPORT=#{nps_rx_intport}, " +
			"NPS_CONTS_IP=#{nps_conts_ip}, NPS_CONTS_PORT=#{nps_conts_port}, NPS_ELC_FLAG=#{nps_elc_flag}, NPC_NO=#{npc_no}, NPS_CONF_FILE=#{nps_conf_file}, NPS_RESEND=#{nps_resend}, NPCS_SEQ=#{npcs_seq} " +
			"WHERE NPS_SEQ=#{nps_seq}";
	
	// 서비스 정책 삭제
	final String POLICY_SERVICE_DELETE = "UPDATE NNC_POLICY_SERVICE SET NPS_USE_YN=0 WHERE NPS_SEQ=#{nps_seq}"; 
	
	// 마지막 서비스 정책 번호 구하기
	final String LAST_SERVICE_SEQ = "SELECT MAX(NPS_SEQ) NPS_SEQ FROM NNC_POLICY_SERVICE";
	
	// 마지막 허용 정책 번호 구하기
	final String LAST_ALLOW_SEQ = "SELECT MAX(NPA_SEQ) NPA_SEQ FROM NNC_POLICY_ALLOW";
	
	// 마지막 허용 서비스 번호 구하기
	final String LAST_OBJ_SERVICE_SEQ = "SELECT MAX(NOS_SEQ) NOS_SEQ FROM NNC_OBJ_SERVICE";
	
	// 마지막 허용 IP 번호 구하기
	final String LAST_IPADDR_SEQ = "SELECT MAX(NOI_SEQ) NOI_SEQ FROM NNC_OBJ_IPADDR";
	
	// 중복된 송신 서비스 정책이 있는지 확인
	final String SEND_SERVICE_COUNT = "SELECT COUNT(*) CNT FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ AND NCP_DIV=1 AND NPC_NO=#{npc_no} AND NPS_TX_INTPORT=#{nps_tx_intport} AND NPS_SEQ!=#{nps_seq} AND NPS_USE_YN=1";
	
	// 중복된 수신 서비스 정책이 있는지 확인
	final String RECEIVE_SERVICE_COUNT = "SELECT COUNT(*) CNT FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ AND NCP_DIV=2 AND NPC_NO=#{npc_no} AND NPS_RX_INTPORT=#{nps_rx_intport} AND NPS_SEQ!=#{nps_seq} AND NPS_USE_YN=1";
	
	// 중복된 서비스 정책이 있는지 확인 - 프로그램 명으로
	final String POLICY_PROGRAM_COUNT = "SELECT COUNT(*) CNT FROM NNC_POLICY_SERVICE WHERE NCP_SEQ=#{ncp_seq} AND NPS_SEQ!=#{nps_seq} AND NPS_USE_YN=1";
		
	// IP 주소를 사용 중인 서비스 정책 번호
	final String USE_IP_POLICY = "SELECT NPS_SEQ FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ AND NCP_DIV=#{noi_div} AND (NPS_TX_INTIP=#{noi_gcode} OR NPS_RX_INTIP=#{noi_gcode} OR NPS_CONTS_IP=#{noi_gcode}) AND NPS_USE_YN=1";
	
	// 서비스를 사용 중인 서비스 정책 번호
	final String USE_SERVICE_POLICY = "SELECT NPS_SEQ FROM NNC_POLICY_SERVICE NPS, NNC_CONF_PROGRAM NCP " +
			"WHERE NPS.NCP_SEQ=NCP.NCP_SEQ AND NCP_DIV=#{nos_div} AND (NPS_TX_INTPORT=#{nos_gcode} OR NPS_RX_INTPORT=#{nos_gcode} OR NPS_CONTS_PORT=#{nos_gcode}) AND NPS_USE_YN=1";
	
	// flist 파일 생성 시 필요 정보 조회
	final String CREATE_FLIST = "SELECT NPS_USE_YN, NCP_PATH, NPS.NPS_CONF_FILE " +
			"FROM NNC_CONF_PROGRAM NCP, NNC_POLICY_SERVICE NPS, (SELECT MAX(NPS_SEQ) NPS_SEQ, NPS_CONF_FILE FROM NNC_POLICY_SERVICE GROUP BY NPS_CONF_FILE) sub " +
			"WHERE NPS.NPS_SEQ=sub.NPS_SEQ " +
			"AND NCP.NCP_SEQ=NPS.NCP_SEQ " +
			"AND NCP_DIV = #{ncp_div}";
	
	final String IPADDR_OBJ_NM = "SELECT NOI_OBJ_NM FROM NNC_OBJ_IPADDR WHERE NOI_GCODE=#{noi_gcode} AND NOI_DIV=#{noi_div}";
	final String SERVICE_OBJ_NM = "SELECT NOS_OBJ_NM FROM NNC_OBJ_SERVICE WHERE NOS_GCODE=#{nos_gcode} AND NOS_DIV=#{nos_div}";
	final String PROTOCOL_NM = "SELECT NPC_NAME FROM NNC_PROT_CODE WHERE NPC_NO=#{npc_no}";
	
	// 동탄
	final String SELETE_ETHER_TYPE = "SELECT NPE_VALUE, NPE_NAME, NPE_DESCRIPTION FROM NNC_POLICY_ETHERTYPE ORDER BY NPE_ORDER";
	final String SELETE_IP_PROTOCOL = "SELECT NPP_NO, NPP_NAME, NPP_DESCRIPTION FROM NNC_POLICY_PROTOCOL ORDER BY NPP_ORDER";
		
	@Select(SELETE_IPADDR)
    @Results(value = {
    		@Result(property="noi_seq", column="NOI_SEQ"),
    		@Result(property="noi_ip_type", column="NOI_IP_TYPE"),
	        @Result(property="noi_ip", column="NOI_IP"),
	        @Result(property="noi_obj_nm", column="NOI_OBJ_NM"),
	        @Result(property="noi_gcode", column="NOI_GCODE")
    })
    ArrayList<NcPolicy> getIpaddr(@Param("noi_div") int noi_div);
	
	@Select(SELETE_IPADDR_BYSEQ)
    @Results(value = {
    		@Result(property="noi_seq", column="NOI_SEQ"),
    		@Result(property="noi_ip_type", column="NOI_IP_TYPE"),
	        @Result(property="noi_ip", column="NOI_IP"),
	        @Result(property="noi_obj_nm", column="NOI_OBJ_NM"),
	        @Result(property="noi_gcode", column="NOI_GCODE"),
	        @Result(property="noi_div", column="NOI_DIV")
    })
    NcPolicy getIpaddrBySeq(@Param("noi_seq") int noi_seq);
	
	@Select(SELETE_SERVICE)
    @Results(value = {
    		@Result(property="nos_seq", column="NOS_SEQ"),
	        @Result(property="nos_port", column="NOS_PORT"),
	        @Result(property="nos_service_nm", column="NOS_SERVICE_NM"),
	        @Result(property="nos_obj_nm", column="NOS_OBJ_NM"),
	        @Result(property="nos_gcode", column="NOS_GCODE")
    })
    ArrayList<NcPolicy> getService(@Param("nos_div") int nos_div);
	
	@Select(SELETE_SERVICE_BYSEQ)
    @Results(value = {
    		@Result(property="nos_seq", column="NOS_SEQ"),
	        @Result(property="nos_port", column="NOS_PORT"),
	        @Result(property="nos_service_nm", column="NOS_SERVICE_NM"),
	        @Result(property="nos_obj_nm", column="NOS_OBJ_NM"),
	        @Result(property="nos_gcode", column="NOS_GCODE"),
	        @Result(property="nos_div", column="NOS_DIV")
    })
    NcPolicy getServiceBySeq(@Param("nos_seq") int nos_seq);
	
	@Insert(IPADDR_INSERT)
	int ipaddr_insert(NcPolicy ncPolicy);
	
	@Insert(SERVICE_INSERT)
	int service_insert(NcPolicy ncPolicy);
	
	@Insert(IPADDR_UPDATE)
	int ipaddr_update(NcPolicy ncPolicy);
	
	@Insert(IPADDR_UPDATE_SYSTEM)
	int ipaddr_update_system(NcPolicy ncPolicy);
	
	@Insert(SERVICE_UPDATE)
	int service_update(NcPolicy ncPolicy);
	
	@Insert(IPADDR_DELETE)
	int ipaddr_delete(@Param("noi_seq") int noi_seq);
	
	@Insert(SERVICE_DELETE)
	int service_delete(@Param("nos_seq") int nos_seq);
	
	@Select(SELETE_POLICY_ALLOW)
    @Results(value = {
    		@Result(property="npa_seq", column="NPA_SEQ"),
	        @Result(property="npa_sip_gcode", column="NPA_SIP_GCODE"),
	        @Result(property="npa_dip_gcode", column="NPA_DIP_GCODE"),
	        @Result(property="npa_ssvc_gcode", column="NPA_SSVC_GCODE"),
    		@Result(property="npa_dsvc_gcode", column="NPA_DSVC_GCODE"),
    		@Result(property="sip_objnm", column="SIP_OBJNM"),
	        @Result(property="dip_objnm", column="DIP_OBJNM"),
	        @Result(property="ssvc_objnm", column="SSVC_OBJNM"),
    		@Result(property="dsvc_objnm", column="DSVC_OBJNM"),
    		@Result(property="sip", column="SIP"),
	        @Result(property="dip", column="DIP"),
	        @Result(property="sport", column="SPORT"),
    		@Result(property="dport", column="DPORT"),
    		@Result(property="npc_name", column="NPC_NAME"),
    		@Result(property="npc_no", column="NPC_NO"),
    		@Result(property="noi_ip_type", column="NOI_IP_TYPE"),
    		@Result(property="noi_ip", column="NOI_IP")
    })
    ArrayList<NcPolicy> getPolicyAllow(@Param("npa_div") int npa_div);
	
	@Select(POLICY_ALLOW_BYSEQ)
    @Results(value = {
    		@Result(property="npa_seq", column="NPA_SEQ"),
	        @Result(property="npa_sip_gcode", column="NPA_SIP_GCODE"),
	        @Result(property="npa_dip_gcode", column="NPA_DIP_GCODE"),
	        @Result(property="npa_ssvc_gcode", column="NPA_SSVC_GCODE"),
    		@Result(property="npa_dsvc_gcode", column="NPA_DSVC_GCODE"),
    		@Result(property="sip_objnm", column="SIP_OBJNM"),
	        @Result(property="dip_objnm", column="DIP_OBJNM"),
	        @Result(property="ssvc_objnm", column="SSVC_OBJNM"),
    		@Result(property="dsvc_objnm", column="DSVC_OBJNM"),
    		@Result(property="sip", column="SIP"),
	        @Result(property="dip", column="DIP"),
	        @Result(property="sport", column="SPORT"),
    		@Result(property="dport", column="DPORT"),
    		@Result(property="npc_name", column="NPC_NAME"),
    		@Result(property="npc_no", column="NPC_NO"),
    		@Result(property="noi_ip_type", column="NOI_IP_TYPE"),
    		@Result(property="noi_ip", column="NOI_IP"),
    		@Result(property="npa_div", column="NPA_DIV")
    })
    NcPolicy getPolicyAllowBySeq(@Param("npa_seq") int npa_seq);
		
	@Insert(POLICY_ALLOW_INSERT)
	int policy_allow_insert(NcPolicy ncPolicy);
	
	@Insert(POLICY_ALLOW_UPDATE)
	int policy_allow_update(NcPolicy ncPolicy);
	
	@Insert(POLICY_ALLOW_DELETE)
	int policy_allow_delete(@Param("npa_seq") int npa_seq);
	
	@Select(SELETE_PROTOCOL)
    @Results(value = {
    		@Result(property="npc_no", column="NPC_NO"),
	        @Result(property="npc_name", column="NPC_NAME")
    })
    ArrayList<NcPolicy> getProtocol();
	
	@Select(SELETE_CONTS_TYPE)
    @Results(value = {
    		@Result(property="npcs_type", column="NPCS_TYPE"),
    		@Result(property="npc_no", column="NPC_NO")
    })
    ArrayList<NcPolicy> getContsType();
	
	@Select(SELETE_TOP_CONTS)
    @Results(value = {
    		@Result(property="ntc_seq", column="NTC_SEQ"),
    		@Result(property="ntc_name", column="NTC_NAME")
    })
    ArrayList<NcPolicy> getTopConts();
	
	@Select(SELETE_SUB_CONTS)
    @Results(value = {
    		@Result(property="nsc_seq", column="NSC_SEQ"),
    		@Result(property="nsc_name", column="NSC_NAME")
    })
    ArrayList<NcPolicy> getSubConts(@Param("ntc_seq") int ntc_seq);
	
	@Select(SELETE_DETAIL_CONTS)
    @Results(value = {
    		@Result(property="ndc_seq", column="NDC_SEQ"),
    		@Result(property="ndc_name", column="NDC_NAME")
    })
    ArrayList<NcPolicy> getDetailConts(@Param("ntc_seq") int ntc_seq, @Param("nsc_seq") int nsc_seq);
	
	@Select(SELETE_CONTENTS)
    @Results(value = {
    		@Result(property="npcs_seq", column="NPCS_SEQ"),
    		@Result(property="npcs_name", column="NPCS_NAME")
    })
    ArrayList<NcPolicy> getContents(@Param("npc_no") int npc_no, @Param("npcs_type") String npcs_type);
	
	@Select(SELECT_IP_GCODE)
	String getIpGcode(@Param("noi_obj_nm") String noi_obj_nm, @Param("noi_div") int noi_div);
	
	@Select(SELECT_SVC_GCODE)
	String getSvcGcode(@Param("nos_obj_nm") String nos_obj_nm, @Param("nos_div") int nos_div);
	
	@Select(NEXT_IP_GCODE)
	int nextIpGcode(@Param("noi_div") int noi_div);
	
	@Select(NEXT_SVC_GCODE)
	int nextSvcGcode(@Param("nos_div") int nos_div);
	
	@Select(POLICY_ALLOW_COUNT)
	int policy_allow_count(NcPolicy ncPolicy);
	
	@Select(IPADDR_USE_COUNT)
	int ipaddr_use_count(@Param("noi_seq") int noi_seq);
	
	@Select(SERVICE_USE_COUNT)
	int service_use_count(@Param("nos_seq") int nos_seq);
	
	@Select(SERVICE_LINK_COUNT)
	int service_link_count(@Param("nos_seq") int nos_seq);

	@Select(SELETE_POLICY_SERVICE)
    @Results(value = {
    		@Result(property="nps_seq", column="NPS_SEQ"),
	        @Result(property="ncp_name", column="NCP_NAME"),
	        @Result(property="nps_conf_file", column="NPS_CONF_FILE"),
	        @Result(property="tx_intip_objnm", column="TX_INTIP_OBJNM"),
    		@Result(property="rx_intip_objnm", column="RX_INTIP_OBJNM"),
    		@Result(property="conts_ip_objnm", column="CONTS_IP_OBJNM"),
	        @Result(property="tx_intport_objnm", column="TX_INTPORT_OBJNM"),
	        @Result(property="rx_intport_objnm", column="RX_INTPORT_OBJNM"),
    		@Result(property="conts_port_objnm", column="CONTS_PORT_OBJNM"),
    		@Result(property="npc_no", column="NPC_NO"),
    		@Result(property="elc_flag_text", column="ELC_FLAG_TEXT"),
    		@Result(property="nps_resend", column="NPS_RESEND"),
    		@Result(property="npcs_header", column="NPCS_HEADER"),
    		@Result(property="npcs_name", column="NPCS_NAME"),
    		@Result(property="npcs_type", column="NPCS_TYPE")
    })
    ArrayList<NcPolicy> getPolicyService(@Param("ncp_div") int ncp_div);
	
	@Select(POLICY_SERVICE_BYSEQ)
    @Results(value = {
    		@Result(property="nps_seq", column="NPS_SEQ"),
	        @Result(property="ncp_name", column="NCP_NAME"),
	        @Result(property="nps_conf_file", column="NPS_CONF_FILE"),
	        @Result(property="tx_intip_objnm", column="TX_INTIP_OBJNM"),
    		@Result(property="rx_intip_objnm", column="RX_INTIP_OBJNM"),
    		@Result(property="conts_ip_objnm", column="CONTS_IP_OBJNM"),
	        @Result(property="tx_intport_objnm", column="TX_INTPORT_OBJNM"),
	        @Result(property="rx_intport_objnm", column="RX_INTPORT_OBJNM"),
    		@Result(property="conts_port_objnm", column="CONTS_PORT_OBJNM"),
    		@Result(property="npc_no", column="NPC_NO"),
    		@Result(property="elc_flag_text", column="ELC_FLAG_TEXT"),
    		@Result(property="ncp_div", column="NCP_DIV"),
    		@Result(property="ncp_seq", column="NCP_SEQ"),
    		@Result(property="tx_intip", column="TX_INTIP"),
    		@Result(property="rx_intip", column="RX_INTIP"),
    		@Result(property="conts_ip", column="CONTS_IP"),
    		@Result(property="tx_intport", column="TX_INTPORT"),
    		@Result(property="rx_intport", column="RX_INTPORT"),
    		@Result(property="conts_port", column="CONTS_PORT"),
    		@Result(property="ncp_file_name", column="NCP_file_NAME"),
    		@Result(property="nps_elc_flag", column="NPS_ELC_FLAG"),
    		@Result(property="nps_tx_intip", column="NPS_TX_INTIP"),
    		@Result(property="nps_rx_intip", column="NPS_RX_INTIP"),
    		@Result(property="nps_conts_ip", column="NPS_CONTS_IP"),
    		@Result(property="nps_tx_intport", column="NPS_TX_INTPORT"),
    		@Result(property="nps_rx_intport", column="NPS_RX_INTPORT"),
    		@Result(property="nps_conts_port", column="NPS_CONTS_PORT"),
    		@Result(property="nps_resend", column="NPS_RESEND"),
    		@Result(property="npcs_header", column="NPCS_HEADER"),
    		@Result(property="npcs_type", column="NPCS_TYPE"),
    		@Result(property="npcs_seq", column="NPCS_SEQ")
    })
    NcPolicy getPolicyServiceBySeq(@Param("nps_seq") int nps_seq);
	
	@Insert(POLICY_SERVICE_INSERT)
	int policy_service_insert(NcPolicy ncPolicy);
	
	@Insert(POLICY_SERVICE_UPDATE)
	int policy_service_update(NcPolicy ncPolicy);
	
	@Insert(POLICY_SERVICE_DELETE)
	int policy_service_delete(@Param("nps_seq") int nps_seq);
	
	@Select(LAST_SERVICE_SEQ)
	int last_service_seq();
	
	@Select(LAST_ALLOW_SEQ)
	int last_allow_seq();
	
	@Select(LAST_OBJ_SERVICE_SEQ)
	int last_obj_service_seq();
	
	@Select(LAST_IPADDR_SEQ)
	int last_ipaddr_seq();
	
	@Select(SEND_SERVICE_COUNT)
	int send_service_count(NcPolicy ncPolicy);
	
	@Select(RECEIVE_SERVICE_COUNT)
	int receive_service_count(NcPolicy ncPolicy);
	
	@Select(POLICY_PROGRAM_COUNT)
	int policy_program_count(NcPolicy ncPolicy);
	
	@Select(USE_IP_POLICY)
	@Results(value = {
			@Result(property="nps_seq", column="NPS_SEQ")
    })
    ArrayList<NcPolicy> useIpPolicy(NcPolicy ncPolicy);
	
	@Select(USE_SERVICE_POLICY)
	@Results(value = {
			@Result(property="nps_seq", column="NPS_SEQ")
    })
    ArrayList<NcPolicy> useServicePolicy(NcPolicy ncPolicy);
	
	@Select(CREATE_FLIST)
    @Results(value = {
    		@Result(property="nps_use_yn", column="NPS_USE_YN"),
    		@Result(property="ncp_path", column="NCP_PATH"),
	        @Result(property="nps_conf_file", column="NPS_CONF_FILE")
    })
    ArrayList<NcPolicy> create_flist(@Param("ncp_div") int ncp_div);
	
	@Select(IPADDR_OBJ_NM)
	String getIpObjNm(@Param("noi_gcode") int noi_gcode, @Param("noi_div") int noi_div);
	
	@Select(SERVICE_OBJ_NM)
	String getSvcObjNm(@Param("nos_gcode") int nos_gcode, @Param("nos_div") int nos_div);
	
	@Select(PROTOCOL_NM)
	String getProtNm(@Param("npc_no") int npc_no);
	
	@Select(SELETE_ETHER_TYPE)
    @Results(value = {
    		@Result(property="npe_value", column="NPE_VALUE"),
    		@Result(property="npe_name", column="NPE_NAME"),
    		@Result(property="npe_description", column="NPE_DESCRIPTION")
    })
    ArrayList<NcPolicy> getEtherType();
	
	@Select(SELETE_IP_PROTOCOL)
    @Results(value = {
    		@Result(property="npp_no", column="NPP_NO"),
    		@Result(property="npp_name", column="NPP_NAME"),
    		@Result(property="npp_description", column="NPP_DESCRIPTION")
    })
    ArrayList<NcPolicy> getIpProtocol();
}