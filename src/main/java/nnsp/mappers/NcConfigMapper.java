package nnsp.mappers;

import java.util.ArrayList;
import java.util.List;

import nnsp.util.MessageUtil;
import nnsp.vo.NcConfig;
import nnsp.vo.NcLinkPolicy;
import nnsp.vo.NcProduct;
import nnsp.vo.NcSysLoad;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NcConfigMapper {
	enum INTEGRITY_DAEMON_STATUS {
		DUMMY(""),
		AUTOMATIC_INSPECT(MessageUtil.getMessage("query.autoinspect")),
		MANUAL_INSPECT(MessageUtil.getMessage("query.manualinspect")),
		UPDATE(MessageUtil.getMessage("query.update"))
		;

		private String message;

		INTEGRITY_DAEMON_STATUS(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
	
	final String UPDATE_CONFIG_LOG = "UPDATE NNC_CONF_LOG SET NCL_CYCLE=#{ncl_cycle}";
	
	final String SELECT_CONFIG_LOG = "SELECT NCL_CYCLE FROM NNC_CONF_LOG";
	
	// 초기 설정 완료 날짜 기록
	final String UPDATE_COMPLETE_DATE =
		"UPDATE NNC_CONF_LOG SET NCL_LASTLOG=date_format(NOW(),'%Y%m%d') WHERE 1=1 ";
	
	final String UPDATE_CONFIG_LOGIN = "UPDATE NNC_CONF_LOGIN SET NCLI_LOCK_FAILCNT=#{ncli_lock_failcnt}, NCLI_LOCK_DATE=#{ncli_lock_date}";
	
	final String SELECT_CONFIG_LOGIN = "SELECT NCLI_LOCK_FAILCNT, NCLI_LOCK_DATE FROM NNC_CONF_LOGIN";
	
	final String UPDATE_CONFIG_EMAIL = 
		" UPDATE NNC_CONF_EMAIL " + 
		" SET NCE_HOST_TYPE = #{nce_host_type}, " + 
		" NCE_HOST = #{nce_host}, " + 
		" NCE_PORT = #{nce_port}, " + 
		" NCE_ID = #{nce_id}, " + 
		" NCE_PW = #{nce_pw}, " + 
		" NCE_FROM_EMAIL = #{nce_from_email}";
	
	final String USE_YN_EMAIL =
			"UPDATE NNC_CONF_EMAIL SET NCE_USE_YN=#{nce_use_yn}";
	
	final String SELECT_CONFIG_EMAIL = 
			" SELECT NCE_HOST_TYPE, NCE_HOST, NCE_PORT, NCE_ID, NCE_PW, NCE_CYCLE, NCE_FROM_EMAIL, NCE_USE_YN " + 
			" FROM NNC_CONF_EMAIL ";
	
	final String SELECT_CONFIG_SYSTEM = 
			"SELECT NCS_DIV, NCS_MASTER_IP, " + 
			" NCS_SLAVE_IP, NCS_MASTER_MAC, NCS_SLAVE_MAC, NCS_REMOTE_FLAG, " + 
			" NCS_VERSION, NCS_MASTER_NIC, NCS_SLAVE_NIC, NCS_ANTIVIRUS " +
			" FROM NNC_CONF_SYSTEM " + 
			" WHERE NCS_DIV=#{ncs_div} " + 
			" AND NCS_USE_YN = 1 ";
	
	final String UPDATE_CONFIG_SYSTEM = "UPDATE NNC_CONF_SYSTEM " +
			"SET NCS_MASTER_MAC=#{ncs_master_mac}, NCS_SLAVE_MAC=#{ncs_slave_mac}, NCS_MAC_FLAG=1 " +
			"WHERE NCS_DIV=#{ncs_div}";
	
	final String INSERT_CONFIG_SYSTEM = 
			" INSERT INTO " + 
			" 	NNC_CONF_SYSTEM(NCS_MASTER_IP, NCS_SLAVE_IP, NCS_MASTER_MAC, " + 
			" 	NCS_SLAVE_MAC, NCS_DIV, NCS_USE_YN) " + 
			" VALUES(#{ncs_master_ip}, #{ncs_slave_ip}, #{ncs_master_mac}, " + 
			" 	#{ncs_slave_mac}, #{ncs_div}, 1) ";
	
	final String DELETE_CONFIG_SYSTEM = "UPDATE NNC_CONF_SYSTEM SET NCS_USE_YN=0 WHERE NCS_SEQ=#{ncs_seq}";
	
	// 마지막 시스템 번호 구하기
	final String LAST_SYSTEM_SEQ = "SELECT MAX(NCS_SEQ) NCS_SEQ FROM NNC_CONF_SYSTEM";
	
	final String SELECT_CONFIG_LINK = "SELECT NCLK_SEQ, NCLK_RCV_IP, NCLK_SND_IP, NCLK_RCV_NM, NCLK_SND_NM, NCLK_SND_GW FROM NNC_CONF_LINK WHERE NCLK_DIV=#{nclk_div} AND NCLK_USE_YN=1";
	
	final String UPDATE_CONFIG_LINK = "UPDATE NNC_CONF_LINK " +
			"SET NCLK_RCV_IP=#{nclk_rcv_ip}, NCLK_SND_IP=#{nclk_snd_ip}, NCLK_RCV_NM=#{nclk_rcv_nm}, NCLK_SND_NM=#{nclk_snd_nm}, NCLK_SND_GW=#{nclk_snd_gw} " +
			"WHERE NCLK_SEQ=#{nclk_seq}";
	
	final String INSERT_CONFIG_LINK = "INSERT INTO NNC_CONF_LINK(NCLK_RCV_IP, NCLK_SND_IP, NCLK_RCV_NM, NCLK_SND_NM, NCLK_SND_GW, NCLK_DIV, NCLK_USE_YN) " +
			"VALUES(#{nclk_rcv_ip}, #{nclk_snd_ip}, #{nclk_rcv_nm}, #{nclk_snd_nm}, #{nclk_snd_gw}, #{nclk_div}, 1)";
	
	// 마지막 연계 시스템 번호 구하기
	final String LAST_LINK_SEQ = "SELECT MAX(NCLK_SEQ) NCLK_SEQ FROM NNC_CONF_LINK";
	
	final String SELECT_CONFIG_PROGRAM = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG, NCP_HASH, NCP_FILE_SIZE, DATE_FORMAT(NCP_HASH_DATE,'%Y-%m-%d %H:%i:%s') NCP_HASH_DATE, NCP_INSPECT_FLAG " +
			"FROM NNC_CONF_PROGRAM WHERE NCP_DIV=#{ncp_div} AND NCP_USE_YN=1";
	
	final String SELECT_SERVICE_PROGRAM = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG FROM NNC_CONF_PROGRAM WHERE NCP_DIV=#{ncp_div} AND NCP_SERVICE_FLAG=1";
	
	final String UPDATE_CONFIG_PROGRAM = "UPDATE NNC_CONF_DEAMON SET NCD_NAME=#{ncd_name}, NCD_DIV=#{ncd_div}, NCD_STATUS=#{ncd_status}, NCD_PATH=#{ncd_path} WHERE NCD_SEQ=#{ncd_seq}";
	
	final String INSERT_CONFIG_PROGRAM = "INSERT INTO NNC_CONF_DEAMON(NCD_NAME, NCD_DIV, NCD_STATUS, NCD_PATH) VALUES (#{ncd_name},#{ncd_div},#{ncd_status},#{ncd_path})";
	
	final String EDIT_PROGRAM_USEYN = "UPDATE NNC_CONF_DEAMON SET NCD_USE_YN=#{ncd_use_yn}, NCD_STATUS=#{ncd_status} WHERE NCD_SEQ=#{ncd_seq}";
	
	final String SELECT_SERVICE_STATUS = "SELECT NCP_STATUS FROM NNC_CONF_PROGRAM WHERE NCP_USE_YN=1 GROUP BY NCP_STATUS";
	
	final String UPDATE_SERVICE_STATUS = "UPDATE NNC_CONF_PROGRAM SET NCP_STATUS=#{ncp_status} WHERE NCP_USE_YN=1";
	
	final String SERVICE_PROGRAM_BYSEQ = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG FROM NNC_CONF_PROGRAM WHERE NCP_SEQ=#{ncp_seq}";
	
	// 자체 시험 수동 검사 상태 체크
	final String PROTECTION_STATUS =
		"SELECT NSM_STATUS as deamon " +
		" FROM NNC_SYSTEM_MONITOR " +
		" WHERE 1=1 " +
		" AND NSM_DATETIME IN (SELECT MAX(NSM_DATETIME) FROM NNC_SYSTEM_MONITOR) " ;

	// 무결성 검사 상태 변경(실행)
	final String UPDATE_PROTECTION_STATUS =
		"UPDATE NNC_SYSTEM_MONITOR SET NSM_STATUS=1 " + 
		"WHERE NSM_DATETIME = (SELECT max(NSM_DATETIME) FROM NNC_SYSTEM_MONITOR) " +
		"ORDER BY NSM_DATETIME DESC LIMIT 1 ";

	// 자체 시험 수동 검사 마지막 실행 시간
	final String PROTECTION_LAST_DATETIME =
		"SELECT NSM_DATETIME " +
		" FROM NNC_SYSTEM_MONITOR " +
		" WHERE 1=1 " +
		" AND NSM_DATETIME IN (SELECT MAX(NSM_DATETIME) FROM NNC_SYSTEM_MONITOR) " ;
	// 자체 시험 결과중 CPU, RAM, Disk 사용량 정보
	final String PROTECTION_SYSTEM_LOAD =
		"SELECT NSM_CPU, NSM_RAM, NSM_DISK " +
		" FROM NNC_SYSTEM_MONITOR " +
		" WHERE 1=1 " +
		" AND NSM_DATETIME IN (SELECT MAX(NSM_DATETIME) FROM NNC_SYSTEM_MONITOR) " ;
	
	// 검사 중인 프로그램 확인
	final String INSPECT_ING_COUNT = "SELECT COUNT(*) CNT FROM NNC_CONF_PROGRAM WHERE NCP_INSPECT_FLAG=1 OR NCP_INSPECT_FLAG=3"; // 검사 중 혹인 업데이트 중인 프로그램이 있는지 확인
	
	// 실행 중인 무결성 관련 데몬 조회
	final String INSPECT_ING_DEAMON = 
			" SELECT IF(NCI_ACTIVE=1,1," +
			" 	(if(NCI_PASSIVE=1,2," +
			" 	(if(NCI_UPDATE=1,3,0))))) as deamon " +
			" FROM NNC_CONF_INSPECT " +
			" WHERE NCI_DIV=#{nci_div}";
	
	final String UPDATE_INSPECT_PASSIVE =
			" UPDATE NNC_CONF_INSPECT " +
			"   SET NCI_PASSIVE=1 " +
			" WHERE NCI_DIV=#{nci_div}";
	// 무결성 검사 실행
	final String UPDATE_INSPECT_PASSIVE_ID = 
			" UPDATE NNC_CONF_INSPECT " + 
			" SET NCI_PASSIVE=1, " + 
			" NSU_ID = #{nsu_id}" + 
			" WHERE NCI_DIV=#{nci_div}";
	// 무결성값 업데이트 실행
	final String UPDATE_INSPECT_UPDATE = 
			"UPDATE NNC_CONF_INSPECT SET NCI_UPDATE=1 WHERE NCI_DIV=#{nci_div}";
		
	// 검사 플래그 변경 - 전체
	final String UPDATE_INSPECT_FLAG_ALL = "UPDATE NNC_CONF_PROGRAM SET NCP_INSPECT_FLAG=#{ncp_inspect_flag} WHERE NCP_INSPECT_FLAG=0"; // NCP_INSPECT_FLAG가 2. 즉, 알람인 상태는 제외
	// 검사 플래그 변경 - 송신 또는 수신만
	final String UPDATE_INSPECT_FLAG = "UPDATE NNC_CONF_PROGRAM SET NCP_INSPECT_FLAG=#{ncp_inspect_flag} WHERE NCP_DIV=#{ncp_div} AND NCP_INSPECT_FLAG=0"; // NCP_INSPECT_FLAG가 2. 즉, 알람인 상태는 제외
	
	// 프로그램 무결성 주기 조회
	final String SELECT_INSPECT_CYCLE = "SELECT NCI_CYCLE FROM NNC_CONF_INSPECT WHERE NCI_DIV=#{nci_div}";
	
	final String UPDATE_INSPECT_CYCLE = "UPDATE NNC_CONF_INSPECT SET NCI_CYCLE=#{nci_cycle} WHERE NCI_DIV=#{nci_div}";
	
	// 송/수신 시스템 버전 정보
	final String SELECT_SYSTEM_VERSION = "SELECT NCS_VERSION FROM NNC_CONF_SYSTEM WHERE NCS_DIV=#{ncs_div}";
	
	// 연계 서버 재시작 플래그 변경
	final String UPDATE_LINK_RESTART = "UPDATE NNC_CONF_LINK SET NCLK_RESTART=1 WHERE NCLK_DIV=1 AND NCLK_USE_YN=1";
	
	// DB 정보
	final String SELECT_CONFIG_DB = "SELECT NCD_CHANGE_DATE FROM NNC_CONF_DB";
	
	// DB 초기 패스워드 변경 기록
	final String INSERT_CONFIG_DB = "INSERT INTO NNC_CONF_DB(NCD_CHANGE_DATE) VALUES(now())";
	
	// 시스템 재시작 플래그 변경
	final String UPDATE_SYSTEM_RESTART = 
			"UPDATE NNC_CONF_SYSTEM " + 
			"	SET NCS_RESTART=1 " + 
			"WHERE NCS_DIV=#{ncs_div} " + 
			"	AND NCS_USE_YN=1";
	final String UPDATE_SYSTEM_RESTART_ID =
			"UPDATE NNC_CONF_SYSTEM " +
			"   SET NCS_RESTART=1, " +
			"   NSU_ID=#{nsu_id} " +
			" WHERE NCS_DIV=#{ncs_div} " +
			"   AND NCS_USE_YN=1";
	
	// 프로그램 중복 체크
	final String PROGRAM_COUNT = "SELECT COUNT(*) FROM NNC_CONF_PROGRAM WHERE NCP_NAME=#{ncp_name} AND NCP_DIV=#{ncp_div} AND NCP_FILE_NAME=#{ncp_file_name} AND NCP_SEQ!=#{ncp_seq}";
	// 프로그램 등록
	final String INSERT_CONF_PROGRAM = "INSERT INTO NNC_CONF_PROGRAM(NCP_NAME, NCP_DIV, NCP_FILE_NAME, NCP_PATH, NCP_STATUS, NCP_SERVICE_FLAG, NCP_INSPECT_FLAG, NCP_USE_YN) " +
									"VALUES(#{ncp_name}, #{ncp_div}, #{ncp_file_name}, #{ncp_path}, 1, 1, 0, 1)";
	// 프로그램 수정
	final String UPDATE_CONF_PROGRAM = "UPDATE NNC_CONF_PROGRAM SET NCP_NAME=#{ncp_name}, NCP_DIV=#{ncp_div}, NCP_FILE_NAME=#{ncp_file_name}, NCP_PATH=#{ncp_path} WHERE NCP_SEQ=#{ncp_seq}";
	// 프로그램 삭제
	final String DELETE_CONF_PROGRAM = "DELETE FROM NNC_CONF_PROGRAM WHERE NCP_SEQ=#{ncp_seq}";
	// 프로그램 관리 목록
	final String CONF_PROGRAM_BYFLAG = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_PATH, NCP_NAME FROM NNC_CONF_PROGRAM WHERE NCP_SERVICE_FLAG=#{ncp_service_flag} AND (NCP_DIV=1 OR NCP_DIV=2) AND NCP_USE_YN=1 ORDER BY NCP_DIV ASC, NCP_SEQ DESC";
	// 프로그램 관리 목록
	final String CONF_PROGRAM_BYFLAGDIV = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_PATH, NCP_NAME FROM NNC_CONF_PROGRAM WHERE NCP_SERVICE_FLAG=#{ncp_service_flag} AND NCP_DIV=#{ncp_div} AND NCP_USE_YN=1 ORDER BY NCP_DIV ASC, NCP_SEQ DESC";	
		
	// 프로그램 관리 목록
	final String CONF_PROGRAM_RVS_BYFLAG = "SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_PATH, NCP_NAME FROM NNC_CONF_PROGRAM WHERE NCP_SERVICE_FLAG=#{ncp_service_flag} AND (NCP_DIV=3 OR NCP_DIV=4) AND NCP_USE_YN=1 ORDER BY NCP_DIV ASC, NCP_SEQ DESC";
		
	// 원격 터미널 플래그 변경
	final String UPDATE_SYSTEM_REMOTE = "UPDATE NNC_CONF_SYSTEM SET NCS_REMOTE_FLAG=#{ncs_remote_flag} WHERE NCS_DIV=#{ncs_div} AND NCS_USE_YN=1";
		
	// 콘텐츠 목록
	final String SELECT_CONTENTS ="SELECT NPCS_SEQ, NPCS_TYPE, NPCS_NAME, NPCS_HEADER FROM NNC_POLICY_CONTENTS WHERE NPCS_USE_YN=1 ORDER BY NPCS_TYPE, NPCS_NAME";
	
	// 콘텐츠 정보
	final String SELECT_CONTENTS_BYSEQ ="SELECT NPCS_SEQ, NPCS_TYPE, NPCS_NAME, NPCS_HEADER FROM NNC_POLICY_CONTENTS WHERE NPCS_SEQ=#{npcs_seq}";
	
	// 프로토콜 목록
	final String SELECT_PROTOCOL ="SELECT DISTINCT NPCS_TYPE FROM NNC_POLICY_CONTENTS WHERE NPCS_USE_YN=1 ORDER BY NPCS_TYPE";
	
	// 콘텐츠 등록
	final String INSERT_CONTENTS ="INSERT INTO NNC_POLICY_CONTENTS(NPC_NO, NPCS_TYPE, NPCS_NAME, NPCS_HEADER, NPCS_USE_YN) VALUES (#{npc_no}, #{npcs_type}, #{npcs_name}, #{npcs_header}, 1)";
	
	// 콘텐츠 수정
	final String UPDATE_CONTENTS ="UPDATE NNC_POLICY_CONTENTS SET NPCS_TYPE=#{npcs_type}, NPCS_NAME=#{npcs_name}, NPCS_HEADER=#{npcs_header} WHERE NPCS_SEQ=#{npcs_seq}";
	
	// 콘텐츠 삭제
	final String DELETE_CONTENTS ="DELETE FROM NNC_POLICY_CONTENTS WHERE NPCS_SEQ=#{npcs_seq}";
	
	// 중복 콘텐츠 체크
	final String CONTENTS_COUNT = "SELECT COUNT(*) FROM NNC_POLICY_CONTENTS WHERE NPCS_TYPE=#{npcs_type} AND NPCS_NAME=#{npcs_name} AND NPCS_USE_YN=1 AND NPCS_SEQ!=#{npcs_seq}";
	
	// 정책에서 사용중인지 콘텐츠 체크
	final String CONTENTS_USE_COUNT = "SELECT COUNT(*) FROM NNC_LINK_POLICY WHERE NPCS_SEQ=#{npcs_seq} AND NLP_USE_YN=1";
	
	// 정책에서 사용중인지 프로그램 체크
	final String PROGRAM_USE_COUNT = "SELECT COUNT(*) FROM NNC_LINK_POLICY WHERE NLP_PRO_ID=#{ncp_seq} AND NLP_USE_YN=1";
	
	final String PROTECT_CNT_ALL = 
			" SELECT count(*) " + 
			" FROM NNC_CONF_PROGRAM" + 
			" WHERE NCP_DIV= 1" + 
			" 	AND NCP_USE_YN = 1";
	
	final String SERVICE_CNT_ALL =
		"SELECT count(*) " +
		"  FROM NNC_CONF_SERVICE " +
		" WHERE NCP_DIV= 1 " +
		"   AND NCP_USE_YN = 1";
	
	final String PROTECT_PAGE = 
			" SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, " + 
			" 	NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG, NCP_HASH, NCP_FILE_SIZE, " + 
			" 	DATE_FORMAT(NCP_HASH_DATE,'%Y-%m-%d %H:%i:%s') NCP_HASH_DATE, " + 
			" 	NCP_INSPECT_FLAG, NPL_NAME, NCP.NPL_NO  " +
			" FROM NNC_CONF_PROGRAM NCP " + 
			" 	LEFT JOIN NNC_POLICY_LINK NPL " + 
			" 	ON (NCP.NPL_NO = NPL.NPL_NO) " + 
			" WHERE NCP_DIV=1 AND NCP_USE_YN=1 " +
			" ORDER BY NCP_SEQ DESC " +
			" LIMIT #{page}, #{rowsPerPage}";
	
	final String FILE_SIZE =
		" SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, " +
		" 	NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG, NCP_HASH, NCP_FILE_SIZE, " +
		" 	DATE_FORMAT(NCP_HASH_DATE,'%Y-%m-%d %H:%i:%s') NCP_HASH_DATE, " +
		" 	NCP_INSPECT_FLAG, NPL_NAME, NCP.NPL_NO  " +
		" FROM NNC_CONF_PROGRAM NCP " +
		" 	LEFT JOIN NNC_POLICY_LINK NPL " +
		" 	ON (NCP.NPL_NO = NPL.NPL_NO) " +
		" WHERE NCP_DIV=1 AND NCP_USE_YN=1 " +
		" ORDER BY NCP_SEQ DESC " +
		" LIMIT #{page}, #{rowsPerPage}";
	final String SERVICE_SIZE =
		" SELECT NCP_SEQ, NCP_DIV, NCP_FILE_NAME, NCP_STATUS, NCP_PATH, " +
		" 	NCP_USE_YN, NCP_NAME, NCP_SERVICE_FLAG, NCP_HASH, NCP_FILE_SIZE, " +
		" 	DATE_FORMAT(NCP_HASH_DATE,'%Y-%m-%d %H:%i:%s') NCP_HASH_DATE, " +
		" 	NCP_INSPECT_FLAG, NPL_NAME, NCP.NPL_NO  " +
		" FROM NNC_CONF_SERVICE NCP " +
		" 	LEFT JOIN NNC_POLICY_LINK NPL " +
		" 	ON (NCP.NPL_NO = NPL.NPL_NO) " +
		" WHERE NCP_DIV=1 AND NCP_USE_YN=1 " +
		" ORDER BY NCP_SEQ DESC " +
		" LIMIT #{page}, #{rowsPerPage}";
	
	//NNC_CONF_LOG-----------
	@Update(UPDATE_CONFIG_LOG)
	int config_log_update(@Param("ncl_cycle") int ncl_cycle);
	
	@Select(SELECT_CONFIG_LOG)	
	@Results( value={
		@Result(property="ncl_cycle", column="NCL_CYCLE"),
		
	})
	NcConfig getConfigLog();
	
	@Insert(UPDATE_COMPLETE_DATE)
	int update_complete_date();
	//-----------------------
	
	//NNC_CONF_LOGIN---------
	@Update(UPDATE_CONFIG_LOGIN)
	int config_login_update(@Param("ncli_lock_failcnt") int ncli_lock_failcnt, @Param("ncli_lock_date") int ncli_lock_date);
	
	@Select(SELECT_CONFIG_LOGIN)	
	@Results( value={
		@Result(property="ncli_lock_failcnt", column="NCLI_LOCK_FAILCNT"),
		@Result(property="ncli_lock_date", column="NCLI_LOCK_DATE")
	})
	NcConfig getConfigLogIn();
	//-----------------------
	
	//NNC_CONF_EMAIL---------
	@Update(UPDATE_CONFIG_EMAIL)
	int config_email_update(NcConfig ncConfig);
	
	@Update(USE_YN_EMAIL)
	int mail_use_yn(int nce_use_yn);
		
	@Select(SELECT_CONFIG_EMAIL)	
	@Results( value={
		@Result(property="nce_host", column="NCE_HOST"),
		@Result(property="nce_port", column="NCE_PORT"),
		@Result(property="nce_id", column="NCE_ID"),
		@Result(property="nce_pw", column="NCE_PW"),
		@Result(property="nce_cycle", column="NCE_CYCLE"),
		@Result(property="nce_from_email", column="NCE_FROM_EMAIL"),
		@Result(property="nce_use_yn", column="NCE_USE_YN")
	})
	NcConfig getConfigEmail();
	//-----------------------
	
	//NNC_CONF_SYSTEM---------
	@Select(SELECT_CONFIG_SYSTEM)
	@Results( value={
		@Result(property="ncs_div", column="NCS_DIV"),
		@Result(property="ncs_master_ip", column="NCS_MASTER_IP"),
		@Result(property="ncs_slave_ip", column="NCS_SLAVE_IP"),
		@Result(property="ncs_master_mac", column="NCS_MASTER_MAC"),
		@Result(property="ncs_slave_mac", column="NCS_SLAVE_MAC"),
		@Result(property="ncs_remote_flag", column="NCS_REMOTE_FLAG"),
		@Result(property="ncs_version", column="NCS_VERSION"),
		@Result(property="ncs_master_nic", column="NCS_MASTER_NIC"),
		@Result(property="ncs_slave_nic", column="NCS_SLAVE_NIC"),
		@Result(property="ncs_antivirus", column="NCS_ANTIVIRUS")
	})
	NcConfig getConfigSystem(int ncs_div);
	
	@Update(UPDATE_CONFIG_SYSTEM)
	int config_system_update(NcConfig ncConfig);
	
	@Update(INSERT_CONFIG_SYSTEM)
	int config_system_insert(NcConfig ncConfig);
	
	@Update(DELETE_CONFIG_SYSTEM)
	int config_system_delete(int ncs_seq);
	
	@Select(LAST_SYSTEM_SEQ)
	int last_system_seq();
	//-----------------------

	//NNC_CONF_LINK---------
	@Select(SELECT_CONFIG_LINK)
	@Results( value={
		@Result(property="nclk_seq", column="NCLK_SEQ"),
		@Result(property="nclk_rcv_ip", column="NCLK_RCV_IP"),
		@Result(property="nclk_snd_ip", column="NCLK_SND_IP"),
		@Result(property="nclk_rcv_nm", column="NCLK_RCV_NM"),
		@Result(property="nclk_snd_nm", column="NCLK_SND_NM"),
		@Result(property="nclk_snd_gw", column="NCLK_SND_GW")
	})
	ArrayList<NcConfig> getConfigLink(int nclk_div);
	
	@Update(UPDATE_CONFIG_LINK)
	int config_link_update(NcConfig ncConfig);
	
	@Update(INSERT_CONFIG_LINK)
	int config_link_insert(NcConfig ncConfig);
	
	@Select(LAST_LINK_SEQ)
	int last_link_seq();
	//-----------------------
		
	//NNC_CONF_PROGRAM----------------------
	@Select(SELECT_CONFIG_PROGRAM)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_status", column="NCP_STATUS"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_use_yn", column="NCP_USE_YN"),
		@Result(property="ncp_name", column="NCP_NAME"),
		@Result(property="ncp_service_flag", column="NCP_SERVICE_FLAG"),
		@Result(property="ncp_hash", column="NCP_HASH"),
		@Result(property="ncp_file_size", column="NCP_FILE_SIZE"),
		@Result(property="ncp_hash_date", column="NCP_HASH_DATE"),
		@Result(property="ncp_inspect_flag", column="NCP_INSPECT_FLAG")
	})
	ArrayList<NcConfig> getConfigProgram(int ncp_div);
	
	@Select(SELECT_SERVICE_PROGRAM)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_status", column="NCP_STATUS"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_use_yn", column="NCP_USE_YN"),
		@Result(property="ncp_name", column="NCP_NAME"),
		@Result(property="ncp_service_flag", column="NCP_SERVICE_FLAG")
	})
	ArrayList<NcConfig> getServiceProgram(int ncp_div);
	
	@Update(UPDATE_CONFIG_PROGRAM)
	int config_deamon_update(@Param("ncd_seq") int ncd_seq, @Param("ncd_name") String ncd_name, @Param("ncd_div") int ncd_div, 
			@Param("ncd_status") int ncd_status, @Param("ncd_path") String ncd_path);
	
	@Update(EDIT_PROGRAM_USEYN)
	int edit_deamon_useyn(@Param("ncd_seq") int ncd_seq, @Param("ncd_use_yn") int ncd_use_yn, @Param("ncd_status") int ncd_status);
	
	@Insert(INSERT_CONFIG_PROGRAM)
	int config_deamon_insert(@Param("ncd_name") String ncd_name, @Param("ncd_div") int ncd_div, @Param("ncd_status") int ncd_status, @Param("ncd_path") String ncd_path);
	
	@Select(SELECT_SERVICE_STATUS)
	@Results(value={
		@Result(property="ncp_status", column="NCP_STATUS")
	})
	NcConfig getServiceStatus();
	
	@Update(UPDATE_SERVICE_STATUS)
	int service_status_update(int ncp_status);
	
	@Select(SERVICE_PROGRAM_BYSEQ)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_status", column="NCP_STATUS"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_use_yn", column="NCP_USE_YN"),
		@Result(property="ncp_name", column="NCP_NAME"),
		@Result(property="ncp_service_flag", column="NCP_SERVICE_FLAG")
	})
	NcConfig serviceProgramBySeq(int ncp_seq);
	
	@Select(SELECT_INSPECT_CYCLE)	
	@Results( value={
		@Result(property="nci_cycle", column="NCLI_LOCK_FAILCNT")
	})
	NcConfig getInspectCycle(int nci_div);
	
	@Update(UPDATE_INSPECT_CYCLE)
	int inspect_cycle_update(NcConfig ncConfig);
	
	@Update(UPDATE_INSPECT_FLAG_ALL)
	int inspect_flag_allupdate(@Param("ncp_inspect_flag") int ncp_inspect_flag);
	
	@Update(UPDATE_INSPECT_FLAG)
	int inspect_flag_update(@Param("ncp_inspect_flag") int ncp_inspect_flag, @Param("ncp_div") int ncp_div);
	
	@Select(INSPECT_ING_COUNT)
	int inspect_ing_count();
	
	@Select(INSPECT_ING_DEAMON)
	int inspect_ing_deamon(@Param("nci_div") int nci_div);
	//-------------------------------------
	
	@Select(PROTECTION_STATUS)
	int protection_status();

	@Update(UPDATE_PROTECTION_STATUS)
	int start_self_test();

	@Select(PROTECTION_LAST_DATETIME)
	String protection_last_datetime();

	@Select(PROTECTION_SYSTEM_LOAD)
	@Results(value = {
		@Result(property = "cpuLoad", column = "NSM_CPU"),
		@Result(property = "ramLoad", column = "NSM_RAM"),
		@Result(property = "diskLoad", column = "NSM_DISK")
	})
	NcSysLoad protection_system_load();
	
	@Select(SELECT_SYSTEM_VERSION)	
	@Results( value={
		@Result(property="ncs_version", column="NCS_VERSION")
	})
	NcConfig getSysVersion(int ncs_div);

	@Update(UPDATE_LINK_RESTART)
	int link_restart_update();
	
	@Select(SELECT_CONFIG_DB)
	String getConfigDB();
	
	@Insert(INSERT_CONFIG_DB)
	int insert_config_db();
	
	@Update(UPDATE_INSPECT_PASSIVE)
	int start_inspect_passive(@Param("nci_div") int nci_div);
	@Update(UPDATE_INSPECT_PASSIVE_ID)
	int start_inspect_passive_id(@Param("nci_div") int nci_div, @Param("nsu_id") String nsu_id);
	
	@Update(UPDATE_INSPECT_UPDATE)
	int start_inspect_update(@Param("nci_div") int nci_div);
	
	@Update(UPDATE_SYSTEM_RESTART)
	int system_restart_update(int ncs_div);
	@Update(UPDATE_SYSTEM_RESTART_ID)
	int system_restart_update_id(@Param("ncs_div") int ncs_div, @Param("nsu_id") String nsu_id);
	
	@Select(PROGRAM_COUNT)
	int program_count(NcConfig ncConfig);
	
	@Insert(INSERT_CONF_PROGRAM)
	int program_insert(NcConfig ncConfig);
	@Update(UPDATE_CONF_PROGRAM)
	int program_update(NcConfig ncConfig);
	@Update(DELETE_CONF_PROGRAM)
	int program_delete(int ncp_seq);
	@Select(CONF_PROGRAM_BYFLAG)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_name", column="NCP_NAME")
	})
	ArrayList<NcConfig> program_list(int ncp_service_flag);
	@Select(CONF_PROGRAM_BYFLAGDIV)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_name", column="NCP_NAME")
	})
	ArrayList<NcConfig> program_listbydiv(@Param("ncp_service_flag") int ncp_service_flag, @Param("ncp_div") int ncp_div);
	
	@Select(CONF_PROGRAM_RVS_BYFLAG)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_name", column="NCP_NAME")
	})
	ArrayList<NcConfig> program_rvs_list(int ncp_service_flag);
	
	@Update(UPDATE_SYSTEM_REMOTE)
	int system_remote_update(@Param("ncs_remote_flag") int ncs_remote_flag, @Param("ncs_div") int ncs_div);
	
	@Select(SELECT_CONTENTS)
	@Results(value={
		@Result(property="npcs_seq", column="NPCS_SEQ"),
		@Result(property="npcs_type", column="NPCS_TYPE"),
		@Result(property="npcs_name", column="NPCS_NAME"),
		@Result(property="npcs_header", column="NPCS_HEADER")
	})
	ArrayList<NcLinkPolicy> getContents();
	
	@Select(SELECT_CONTENTS_BYSEQ)
	@Results(value={
		@Result(property="npcs_seq", column="NPCS_SEQ"),
		@Result(property="npcs_type", column="NPCS_TYPE"),
		@Result(property="npcs_name", column="NPCS_NAME"),
		@Result(property="npcs_header", column="NPCS_HEADER")
	})
	NcLinkPolicy getContentsBySeq(int npcs_seq);
	
	@Select(SELECT_PROTOCOL)
	@Results(value={
		@Result(property="npcs_type", column="NPCS_TYPE")
	})
	ArrayList<NcLinkPolicy> getProtocol();
	
	@Insert(INSERT_CONTENTS)
	int contents_insert(NcLinkPolicy ncLinkPolicy);
	
	@Update(UPDATE_CONTENTS)
	int contents_update(NcLinkPolicy ncLinkPolicy);
	
	@Update(DELETE_CONTENTS)
	int contents_delete(int npcs_seq);
	
	@Select(CONTENTS_COUNT)
	int contents_count(NcLinkPolicy ncLinkPolicy);
	
	@Select(CONTENTS_USE_COUNT)
	int contents_use_count(int npcs_seq);
	
	@Select(PROGRAM_USE_COUNT)
	int program_use_count(int ncp_seq);
	
	@Select(PROTECT_CNT_ALL)
	int getProtectTotalCnt();
	
	@Select(SERVICE_CNT_ALL)
	int getServiceTotalCnt();
	
	@Select(PROTECT_PAGE)
	@Results(value={
		@Result(property="ncp_seq", column="NCP_SEQ"),
		@Result(property="ncp_file_name", column="NCP_FILE_NAME"),
		@Result(property="ncp_div", column="NCP_DIV"),
		@Result(property="ncp_status", column="NCP_STATUS"),
		@Result(property="ncp_path", column="NCP_PATH"),
		@Result(property="ncp_use_yn", column="NCP_USE_YN"),
		@Result(property="ncp_name", column="NCP_NAME"),
		@Result(property="ncp_service_flag", column="NCP_SERVICE_FLAG"),
		@Result(property="ncp_hash", column="NCP_HASH"),
		@Result(property="ncp_file_size", column="NCP_FILE_SIZE"),
		@Result(property="ncp_hash_date", column="NCP_HASH_DATE"),
		@Result(property="ncp_inspect_flag", column="NCP_INSPECT_FLAG"),
		@Result(property="ncp_hash_date", column="NCP_HASH_DATE"),
		@Result(property="npl_no", column="NPL_NO"),
		@Result(property="npl_name", column="NPL_NAME")
	})
	List<NcConfig> getProtectList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage);
	@Select(FILE_SIZE)
	ArrayList<NcConfig> getfile_size(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage);
	@Select(SERVICE_SIZE)
	ArrayList<NcConfig> getService_size(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage);
	
	final String UPDATE_CONFIG_EMAIL_DEK =
	        "UPDATE NNC_CONF_EMAIL SET NCE_DEK=#{nce_dek} ";
    final String SELECT_CONFIG_EMAIL_DEK =
            "SELECT NCE_DEK FROM NNC_CONF_EMAIL LIMIT 1 ";
    final String SELECT_CONFIG_PW =
            "SELECT NCE_PW " +
                "  FROM NNC_CONF_EMAIL";
    
	@Select(SELECT_CONFIG_PW)
	String getConfigePw();
	
    @Update(UPDATE_CONFIG_EMAIL_DEK)
    int config_email_dek_update(String nce_dek);
    
    @Select(SELECT_CONFIG_EMAIL_DEK)
    String config_email_dek_select();
    
    @Select(SELECT_CONFIG_PW)
    String getConfigEMailPw();
    
    // 제품 초기 정보 설정
    final String SELECT_CONFIG_PROD =
            "SELECT NCPD_SETUP FROM NNC_CONF_PRODUCT";
    
    final String UPDATE_PRODUCT =
            "UPDATE NNC_CONF_PRODUCT SET NCPD_SN=#{ncpd_sn}, NCPD_SETUP=#{ncpd_setup}";

    final String SELETE_PRODUCT =
            "SELECT NCPD_NAME, NCPD_VERSION, NCPD_HW_MODEL, NCPD_MODEL, NCPD_SN, "
            + " NCPD_TEL, NCPD_DATE, NCPD_DESC, NCPD_REG, NCPD_DIV, NCPD_KEY, NCPD_SETUP "
            + " FROM NNC_CONF_PRODUCT";
    
    @Select(SELECT_CONFIG_PROD)
    boolean getConfigProd();

    @Update(UPDATE_PRODUCT)
    int ncProduct_update(NcProduct ncProduct);
    
    @Select(SELETE_PRODUCT)
    @Results(value = {
        @Result(property="ncpd_name", column="NCPD_NAME"),
        @Result(property="ncpd_version", column="NCPD_VERSION"),
        @Result(property="ncpd_hw_model", column="NCPD_HW_MODEL"),
        @Result(property="ncpd_model", column="NCPD_MODEL"),
        @Result(property="ncpd_sn", column="NCPD_SN"),
        @Result(property="ncpd_tel", column="NCPD_TEL"),
        @Result(property="ncpd_date", column="NCPD_DATE"),
        @Result(property="ncpd_desc", column="NCPD_DESC"),
        @Result(property="ncpd_reg", column="NCPD_REG"),
        @Result(property="ncpd_div", column="NCPD_DIV"),
        @Result(property="ncpd_key", column="NCPD_KEY"),
        @Result(property="ncpd_setup", column="NCPD_SETUP")
    })
    NcProduct getNcProduct();
}