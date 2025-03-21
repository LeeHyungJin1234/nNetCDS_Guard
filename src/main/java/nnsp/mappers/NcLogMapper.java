package nnsp.mappers;

import java.util.ArrayList;

import nnsp.vo.NcLog;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface NcLogMapper {
	
	final String MNGLOG_CNT_ALL = "SELECT count(*) FROM NNC_LOG_MNG " +
			"WHERE DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') >= #{nlm_create_sdate} AND DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') <= #{nlm_create_edate}";
	
	final String MNGLOG_PAGE = "SELECT NSU_ID, DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLM_CREATE_DATE, NAI_IP, NLM_PAGE, NLM_PARAM, " +
			"(CASE NLM_RESULT WHEN 'L' THEN '잠김' WHEN 'S' THEN '성공' ELSE '실패' END) AS NLM_RESULT, " +
			"(CASE NLM_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLM_RISK_LEVEL " +
			"FROM NNC_LOG_MNG " +
			"WHERE DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') >= #{nlm_create_sdate} " +
			"AND DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') <= #{nlm_create_edate} " +
			"ORDER BY NLM_SEQ DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	final String MNGLOG_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_MNG " +
			"WHERE DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') >= #{nlm_create_sdate} AND DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') <= #{nlm_create_edate}";
	
	final String MNGLOG_PAGE_GRID =
			" SELECT ROW_NUMBER() OVER(ORDER BY NLM_CREATE_DATE ASC, NLM_SEQ ASC) AS NLM_INDEX, " + 
			" 	NSU_ID, DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLM_CREATE_DATE, NAI_IP, NLM_PAGE, NLM_PARAM, " +
			" 	(CASE NLM_RESULT WHEN 'L' THEN '잠김' WHEN 'S' THEN '성공' ELSE '실패' END) AS NLM_RESULT, " +
			" 	(CASE NLM_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLM_RISK_LEVEL " +
			" FROM NNC_LOG_MNG " +
			" WHERE DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') >= #{nlm_create_sdate} " +
			" 	AND DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d') <= #{nlm_create_edate} " +
			" ORDER BY NLM_INDEX DESC  ";
	
	final String POLICY_CNT_ALL = "SELECT count(*) FROM NNC_LOG_POLICY " +
			"WHERE DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') >= #{nlp_create_sdate} AND DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') <= #{nlp_create_edate}";
	
	final String POLICY_PAGE = "SELECT NSU_ID, DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLP_CREATE_DATE, NAI_IP, NLP_PAGE, NLP_PARAM, " +
			"(CASE NLP_RESULT WHEN 'S' THEN '성공' ELSE '실패' END) AS NLP_RESULT, " +
			"(CASE NLP_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLP_RISK_LEVEL " +
			"FROM NNC_LOG_POLICY " +
			"WHERE DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') >= #{nlp_create_sdate} " +
			"AND DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') <= #{nlp_create_edate} "+
			"ORDER BY NLP_SEQ DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	final String POLICYLOG_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_POLICY " +
			"WHERE DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') >= #{nlp_create_sdate} AND DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') <= #{nlp_create_edate}";
	
	final String POLICYLOG_PAGE_GRID = 
			" SELECT ROW_NUMBER() OVER(ORDER BY NLP_CREATE_DATE ASC, NLP_SEQ ASC) AS NLP_INDEX, " + 
			" NSU_ID, DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLP_CREATE_DATE, NAI_IP, NLP_PAGE, NLP_PARAM, " +
			"(CASE NLP_RESULT WHEN 'S' THEN '성공' ELSE '실패' END) AS NLP_RESULT, " +
			"(CASE NLP_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLP_RISK_LEVEL " +
			"FROM NNC_LOG_POLICY " +
			"WHERE DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') >= #{nlp_create_sdate} " +
			"AND DATE_FORMAT(NLP_CREATE_DATE,'%Y-%m-%d') <= #{nlp_create_edate} "+
			"ORDER BY NLP_SEQ DESC ";
	
	final String EVENT_CNT_ALL = "SELECT count(*) FROM NNC_LOG_EVENT "+
			"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') >= #{nle_create_sdate} AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') <= #{nle_create_edate} "+
			"AND (NLE_DIV=1 OR NLE_DIV=2)";
	
	final String EVENT_PAGE = "SELECT DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLE_CREATE_DATE, NLE_HOST_NAME, " +
			"(CASE NLE_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLE_RISK_LEVEL, NLE_PROG_NAME, NLE_MESSAGE, NPL_NAME " +
			"FROM NNC_LOG_EVENT NLE LEFT JOIN NNC_POLICY_LINK NPL ON (NLE.NLE_PROG_NAME = NPL.NPL_NO) " +
			"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') >= #{nle_create_sdate} " +
			"AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') <= #{nle_create_edate} "+
			"AND (NLE_DIV=1 OR NLE_DIV=2) "+
			"ORDER BY NLE_SEQ DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	final String EVENTLOG_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_EVENT "+
			"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') >= #{nle_create_sdate} AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') <= #{nle_create_edate} ";
	
	final String EVENTLOG_PAGE_GRID = 
			" SELECT ROW_NUMBER() OVER(ORDER BY NLE_CREATE_DATE ASC, NLE_SEQ ASC) AS NLE_INDEX, " + 
			" DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLE_CREATE_DATE, NLE_HOST_NAME, " +
			"(CASE NLE_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLE_RISK_LEVEL, NLE_PROG_NAME, NLE_MESSAGE, NPL_NAME " +
			"FROM NNC_LOG_EVENT NLE LEFT JOIN NNC_POLICY_LINK NPL ON (NLE.NLE_PROG_NAME = NPL.NPL_NO) " +
			"WHERE DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') >= #{nle_create_sdate} " +
			"AND DATE_FORMAT(NLE_CREATE_DATE,'%Y-%m-%d') <= #{nle_create_edate} "+
			"ORDER BY NLE_SEQ DESC ";
	
	final String ACCESS_CNT_ALL = "SELECT count(*) FROM NNC_LOG_ACCESS " +
			"WHERE NLA_CREATE_DATE >= #{nla_create_sdate} AND NLA_CREATE_DATE <= #{nla_create_edate} "+
			"AND (NLA_DIV=1 OR NLA_DIV=2)";

	final String ACCESS_PAGE = "SELECT DATE_FORMAT(NLA_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLA_CREATE_DATE, NLA_HOST_NAME, " +
			"(CASE NLA_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLA_RISK_LEVEL, " +
			//"IFNULL(NOI.NOI_OBJ_NM,NLA_SRC_IP) NLA_SRC_IP, IFNULL(NOI2.NOI_OBJ_NM,NLA_DST_IP) NLA_DST_IP, " +
			"NLA_SRC_IP, NLA_DST_IP, NLA_SRC_PORT, NLA_DST_PORT, " +
			"(CASE NLA_ACCESS_TYPE WHEN 0 THEN 'Allow' ELSE 'Deny' END) AS NLA_ACCESS_TYPE, " +
			"(CASE NLA_DIV WHEN 1 THEN '송신' WHEN 3 THEN '송신' ELSE '수신' END) AS NLA_DIV2 "+
			//"FROM NNC_LOG_ACCESS NLA LEFT JOIN NNC_POLICY_ALLOW NPA ON (NLA.NPA_SEQ=NPA.NPA_SEQ) " +
			//"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPA.NPA_SIP_GCODE=NOI.NOI_GCODE AND NPA.NPA_DIV=NOI.NOI_DIV) " +
			//"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPA.NPA_DIP_GCODE=NOI2.NOI_GCODE AND NPA.NPA_DIV=NOI2.NOI_DIV) " +
			"FROM NNC_LOG_ACCESS NLA " +
			"WHERE NLA_CREATE_DATE >= #{nla_create_sdate} " +
			"AND NLA_CREATE_DATE <= #{nla_create_edate} "+
			"AND (NLA_DIV=1 OR NLA_DIV=2) "+
			"ORDER BY NLA_CREATE_DATE DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	final String ACCESS_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_ACCESS_#{tblDate} " +
			"WHERE NLA_CREATE_DATE >= #{nla_create_sdate} AND NLA_CREATE_DATE <= #{nla_create_edate} ";
	
	final String ACCESS_PAGE_GRID =
			" SELECT ROW_NUMBER() OVER(ORDER BY NLA_CREATE_DATE ASC, NLA_SEQ ASC) AS NLA_INDEX, " + 
			" DATE_FORMAT(NLA_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLA_CREATE_DATE, NLA_HOST_NAME, " +
			"(CASE NLA_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLA_RISK_LEVEL, " +
			"NLA_SRC_IP, NLA_DST_IP, NLA_SRC_PORT, NLA_DST_PORT, " +
			"(CASE NLA_ACCESS_TYPE WHEN 0 THEN 'Allow' ELSE 'Deny' END) AS NLA_ACCESS_TYPE, " +
			"(CASE NLA_DIV WHEN 1 THEN '송신' WHEN 3 THEN '송신' ELSE '수신' END) AS NLA_DIV2 "+
			"FROM NNC_LOG_ACCESS_#{tblDate} NLA " +
			"WHERE NLA_CREATE_DATE >= #{nla_create_sdate} " +
			"AND NLA_CREATE_DATE <= #{nla_create_edate} "+
			"ORDER BY NLA_SEQ DESC ";
	
	// 메인 실시간 로그..
	final String MNGLOG_MAIN = "SELECT NSU_ID, DATE_FORMAT(NLM_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLM_CREATE_DATE, NAI_IP, NLM_PAGE, NLM_PARAM, " +
			"(CASE NLM_RESULT WHEN 'L' THEN '잠김' WHEN 'S' THEN '성공' ELSE '실패' END) AS NLM_RESULT, " +
			"(CASE NLM_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLM_RISK_LEVEL " +
			"FROM NNC_LOG_MNG " +
			"ORDER BY NLM_SEQ DESC " +
			"LIMIT 20";
	
	// 실시간 이벤트 로그
	final String REAL_EVENT = "select NLE.*, NCP_NAME from ( " +
			"SELECT NLE_CREATE_DATE, NLE_HOST_NAME, " +
			"(CASE NLE_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLE_RISK_LEVEL, NLE_PROG_NAME, NLE_MESSAGE " +
			"FROM NNC_LOG_EVENT NLE "+
			"ORDER BY NLE_SEQ DESC LIMIT 20 " +
			") NLE LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLE.NLE_PROG_NAME = NCP.NCP_FILE_NAME)";
	
	// 실시간 접근 통제 로그
	final String REAL_ACCESS = "SELECT NLA_CREATE_DATE, NLA_HOST_NAME, " +
			"(CASE NLA_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLA_RISK_LEVEL, " +
			"NLA_SRC_IP, NLA_DST_IP, NLA_SRC_PORT, NLA_DST_PORT, " +
			"getServiceName(NLA_PROTOCOL) NLA_PROTOCOL, (CASE NLA_ACCESS_TYPE WHEN 0 THEN 'Allow' ELSE 'Deny' END) AS NLA_ACCESS_TYPE, " +
			"(CASE NLA_DIV WHEN 2 THEN '수신' ELSE '송신' END) AS NLA_DIV "+
			"FROM NNC_LOG_ACCESS NLA " +
			"ORDER BY NLA_CREATE_DATE DESC LIMIT 20";
	
	// 실시간 관리자 로그
	final String REAL_MNG = "SELECT NSU_ID, NLM_CREATE_DATE, NAI_IP, NLM_PAGE, NLM_PARAM, " +
			"(CASE NLM_RESULT WHEN 'L' THEN '잠김' WHEN 'S' THEN '성공' ELSE '실패' END) AS NLM_RESULT, " +
			"(CASE NLM_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLM_RISK_LEVEL " +
			"FROM NNC_LOG_MNG " +
			"ORDER BY NLM_SEQ DESC LIMIT 20";
	
	// 자체보호 로그
	final String PROTECTION_GRID_CNT_ALL = " SELECT count(*) FROM NNC_LOG_PROTECTION " + 
			" WHERE DATE_FORMAT(NLPT_CREATE_DATE,'%Y-%m-%d') >= #{nlpt_create_sdate} " + 
			" AND DATE_FORMAT(NLPT_CREATE_DATE,'%Y-%m-%d') <= #{nlpt_create_edate} ";
	
	final String PROTECTION_PAGE_GRID = 
			" SELECT ROW_NUMBER() OVER(ORDER BY NLPT_CREATE_DATE ASC, NLPT_SEQ ASC) AS NLPT_INDEX, NLPT_DIV, NSU_ID, " + 
			" DATE_FORMAT(NLPT_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLPT_CREATE_DATE, NLPT_HOST_NAME, "  +
			"(CASE NLPT_RESULT WHEN 'F' THEN '실패' ELSE '성공' END) AS NLPT_RESULT, " +
			"(CASE NLPT_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLPT_RISK_LEVEL, "+
			"NLPT_PROG_NAME, NLPT_MESSAGE, NPL_NAME " +
			"FROM NNC_LOG_PROTECTION NLPT "+
			"LEFT JOIN NNC_POLICY_LINK NPL ON (NLPT.NLPT_PROG_NAME = NPL.NPL_NO) " +
			"WHERE DATE_FORMAT(NLPT_CREATE_DATE,'%Y-%m-%d') >= #{nlpt_create_sdate} " +
			"AND DATE_FORMAT(NLPT_CREATE_DATE,'%Y-%m-%d') <= #{nlpt_create_edate} "+
			"ORDER BY NLPT_SEQ DESC ";
	
	// 무결성 로그
	final String INTEGRITY_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_INTEGRITY " 
			+ "WHERE DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') >= #{nli_create_sdate} " 
			+ "AND DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') <= #{nli_create_edate} ";
	
	final String INTEGRITY_PAGE_GRID = 
			" SELECT ROW_NUMBER() OVER(ORDER BY NLI_CREATE_DATE ASC, NLI_SEQ ASC) AS NLI_INDEX, " + 
			" DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLI_CREATE_DATE, NLI_HOST_NAME, NLI_DIV, NSU_ID, "  +
			"(CASE NLI_RESULT WHEN 'F' THEN '실패' ELSE '성공' END) AS NLI_RESULT, " +
			"(CASE NLI_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLI_RISK_LEVEL, NLI_PROG_NAME, NLI_MESSAGE, NPL_NAME " +
			"FROM NNC_LOG_INTEGRITY NLI LEFT JOIN NNC_POLICY_LINK NPL ON (NLI.NLI_PROG_NAME = NPL.NPL_NO) " +
			"WHERE DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') >= #{nli_create_sdate} " +
			"AND DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') <= #{nli_create_edate} "+
			"ORDER BY NLI_SEQ DESC ";

	// 무결성 로그
	final String INTEGRITY_CNT_ALL = "SELECT count(*) FROM NNC_LOG_INTEGRITY "+
			"WHERE DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') >= #{nli_create_sdate} AND DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') <= #{nli_create_edate} "+
			"AND (NLI_DIV=1 OR NLI_DIV=2)";
	
	final String INTEGRITY_PAGE = "SELECT DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLI_CREATE_DATE, NLI_HOST_NAME, "  +
			"(CASE NLI_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLI_RISK_LEVEL, NLI_PROG_NAME, NLI_MESSAGE, NPL_NAME " +
			"FROM NNC_LOG_INTEGRITY NLI LEFT JOIN NNC_POLICY_LINK NPL ON (NLI.NLI_PROG_NAME = NPL.NPL_NO) " +
			"WHERE DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') >= #{nli_create_sdate} " +
			"AND DATE_FORMAT(NLI_CREATE_DATE,'%Y-%m-%d') <= #{nli_create_edate} "+
			"AND (NLI_DIV=1 OR NLI_DIV=2) "+
			"ORDER BY NLI_SEQ DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	// 에러 로그
	final String ERROR_CNT_ALL = "SELECT count(*) FROM NNC_LOG_ERROR "+
			"WHERE DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') >= #{nler_create_sdate} AND DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') <= #{nler_create_edate} "+
			"AND (NLER_DIV=1 OR NLER_DIV=2)";
	
	final String ERROR_PAGE = "SELECT DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLER_CREATE_DATE, NLER_HOST_NAME, " +
			"(CASE NLER_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLER_RISK_LEVEL, NLER_PROG_NAME, NLER_MESSAGE, NCP_NAME " +
			"FROM NNC_LOG_ERROR NLER LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLER.NLER_PROG_NAME = NCP.NCP_FILE_NAME) " +
			"WHERE DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') >= #{nler_create_sdate} " +
			"AND DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') <= #{nler_create_edate} "+
			"AND (NLER_DIV=1 OR NLER_DIV=2) "+
			"ORDER BY NLER_SEQ DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	// 에러 로그
	final String ERROR_GRID_CNT_ALL = "SELECT count(*) FROM NNC_LOG_ERROR "
			+ "WHERE DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') >= #{nler_create_sdate} "
			+ "AND DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') <= #{nler_create_edate} ";
	
	final String ERROR_PAGE_GRID = 
			" SELECT ROW_NUMBER() OVER(ORDER BY NLER_CREATE_DATE ASC, NLER_SEQ ASC) AS NLER_INDEX, " + 
			" DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLER_CREATE_DATE, NLER_HOST_NAME, NLER_DIV, NSU_ID, " +
			"(CASE NLER_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLER_RISK_LEVEL, NLER_PROG_NAME, NLER_MESSAGE, NCP_NAME " +
			"FROM NNC_LOG_ERROR NLER LEFT JOIN NNC_CONF_PROGRAM NCP ON (NLER.NLER_PROG_NAME = NCP.NCP_FILE_NAME) " +
			"WHERE DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') >= #{nler_create_sdate} " +
			"AND DATE_FORMAT(NLER_CREATE_DATE,'%Y-%m-%d') <= #{nler_create_edate} "+
			"ORDER BY NLER_SEQ DESC ";

	final String ACCESS_PAGE2 = "SELECT DATE_FORMAT(NLA_CREATE_DATE,'%Y-%m-%d %H:%i:%s') NLA_CREATE_DATE, NLA_HOST_NAME, " +
			"(CASE NLA_RISK_LEVEL WHEN 'I' THEN '정보' WHEN 'R' THEN '위험' ELSE '경고' END) AS NLA_RISK_LEVEL, " +
			//"IFNULL(NOI.NOI_OBJ_NM,NLA_SRC_IP) NLA_SRC_IP, IFNULL(NOI2.NOI_OBJ_NM,NLA_DST_IP) NLA_DST_IP, " +
			"NLA_SRC_IP, NLA_DST_IP, NLA_SRC_PORT, NLA_DST_PORT, " +
			"(CASE NLA_ACCESS_TYPE WHEN 0 THEN 'Allow' ELSE 'Deny' END) AS NLA_ACCESS_TYPE, " +
			"(CASE NLA_DIV WHEN 1 THEN '송신' WHEN 3 THEN '송신' ELSE '수신' END) AS NLA_DIV2 "+
			//"FROM NNC_LOG_ACCESS NLA LEFT JOIN NNC_POLICY_ALLOW NPA ON (NLA.NPA_SEQ=NPA.NPA_SEQ) " +
			//"LEFT JOIN NNC_OBJ_IPADDR NOI ON (NPA.NPA_SIP_GCODE=NOI.NOI_GCODE AND NPA.NPA_DIV=NOI.NOI_DIV) " +
			//"LEFT JOIN NNC_OBJ_IPADDR NOI2 ON (NPA.NPA_DIP_GCODE=NOI2.NOI_GCODE AND NPA.NPA_DIV=NOI2.NOI_DIV) " +
			"FROM NNC_LOG_ACCESS_#{tblDate} NLA " +
			"WHERE NLA_CREATE_DATE >= #{nla_create_sdate} " +
			"AND NLA_CREATE_DATE <= #{nla_create_edate} "+
			"AND (NLA_DIV=1 OR NLA_DIV=2) "+
			"ORDER BY NLA_CREATE_DATE DESC " +
			"LIMIT #{page}, #{rowsPerPage}";
	
	@Select(MNGLOG_CNT_ALL)
    int getMngLogTotalCnt(NcLog ncMngLog);
	
	@Select(MNGLOG_PAGE)
    @Results(value = {
    		@Result(property="nsu_id", column="NSU_ID"),
    		@Result(property="nlm_create_date", column="NLM_CREATE_DATE"),
    		@Result(property="nai_ip", column="NAI_IP"),
    		@Result(property="nlm_page", column="NLM_PAGE"),
    		@Result(property="nlm_param", column="NLM_PARAM"),
    		@Result(property="nlm_result", column="NLM_RESULT"),
    		@Result(property="nlm_risk_level", column="NLM_RISK_LEVEL")
    })
    ArrayList<NcLog> getMngLogList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nlm_create_sdate") String nlm_create_sdate, @Param("nlm_create_edate") String nlm_create_edate);
	
	@Select(MNGLOG_GRID_CNT_ALL)
    int getMngLogTotalCnt_grid(NcLog ncMngLog);
	
	@Select(MNGLOG_PAGE_GRID)
	ArrayList<NcLog> getMngLogList_grid(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nlm_create_sdate") String nlm_create_sdate, @Param("nlm_create_edate") String nlm_create_edate);
	
	@Select(POLICY_CNT_ALL)
    int getPolicyTotalCnt(NcLog ncLogPolicy);
	
	@Select(POLICY_PAGE)
    @Results(value = {
    		@Result(property="nsu_id", column="NSU_ID"),
    		@Result(property="nlp_create_date", column="NLP_CREATE_DATE"),
    		@Result(property="nai_ip", column="NAI_IP"),
    		@Result(property="nlp_page", column="NLP_PAGE"),
    		@Result(property="nlp_param", column="NLP_PARAM"),
    		@Result(property="nlp_result", column="NLP_RESULT"),
    		@Result(property="nlp_risk_level", column="NLP_RISK_LEVEL")
    })
    ArrayList<NcLog> getPolicyList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nlp_create_sdate") String nlp_create_sdate, @Param("nlp_create_edate") String nlp_create_edate);
	
	@Select(POLICYLOG_GRID_CNT_ALL)
    int getPolicyLogTotalCnt_grid(NcLog ncPolicyData);
	
	@Select(POLICYLOG_PAGE_GRID)
	ArrayList<NcLog> getPolicyLogList_grid(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nlp_create_sdate") String nlp_create_sdate, @Param("nlp_create_edate") String nlp_create_edate);
	
	@Select(EVENT_CNT_ALL)
    int getEventTotalCnt(NcLog ncLogEvent);
	
	@Select(EVENT_PAGE)
    @Results(value = {
    		@Result(property="nle_create_date", column="NLE_CREATE_DATE"),
    		@Result(property="nle_host_name", column="NLE_HOST_NAME"),
    		@Result(property="nle_risk_level", column="NLE_RISK_LEVEL"),
    		@Result(property="nle_prog_name", column="NLE_PROG_NAME"),
    		@Result(property="nle_message", column="NLE_MESSAGE"),
    		@Result(property="ncp_name", column="NCP_NAME"),
    		@Result(property="npl_no", column="NPL_NO"),
    		@Result(property="npl_name", column="NPL_NAME")
    })
    ArrayList<NcLog> getEventList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nle_create_sdate") String nle_create_sdate, @Param("nle_create_edate") String nle_create_edate);

	@Select(EVENTLOG_GRID_CNT_ALL)
    int getEventLogTotalCnt_grid(NcLog ncLogEvent);
	
	@Select(EVENTLOG_PAGE_GRID)
	ArrayList<NcLog> getEventLogList_grid(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nle_create_sdate") String nle_create_sdate, @Param("nle_create_edate") String nle_create_edate);
	
	@Select(ACCESS_CNT_ALL)
    int getAccessTotalCnt(@Param("nla_create_sdate") String nla_create_sdate, @Param("nla_create_edate") String nla_create_edate);
	
	@Select(ACCESS_PAGE)
    @Results(value = {
    		@Result(property="nla_create_date", column="NLA_CREATE_DATE"),
    		@Result(property="nla_host_name", column="NLA_HOST_NAME"),
    		@Result(property="nla_risk_level", column="NLA_RISK_LEVEL"),
    		@Result(property="nla_src_ip", column="NLA_SRC_IP"),
    		@Result(property="nla_dst_ip", column="NLA_DST_IP"),
    		@Result(property="nla_src_port", column="NLA_SRC_PORT"),
    		@Result(property="nla_dst_port", column="NLA_DST_PORT"),
    		@Result(property="nla_protocol", column="NLA_PROTOCOL"),
    		@Result(property="nla_access_type", column="NLA_ACCESS_TYPE"),
    })
    ArrayList<NcLog> getAccessList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nla_create_sdate") String nla_create_sdate, @Param("nla_create_edate") String nla_create_edate);
	
	@Select(ACCESS_GRID_CNT_ALL)
    int getAccessLogTotalCnt_grid(@Param("nla_create_sdate") String nla_create_sdate, @Param("nla_create_edate") String nla_create_edate, @Param("tblDate") int tblDate);	
	@Select(ACCESS_PAGE_GRID)
    ArrayList<NcLog> getAccessLogList_grid(@Param("nla_create_sdate") String nla_create_sdate, @Param("nla_create_edate") String nla_create_edate, @Param("tblDate") int tblDate);
	
	@Select(MNGLOG_MAIN)
    @Results(value = {
    		@Result(property="nsu_id", column="NSU_ID"),
    		@Result(property="nlm_create_date", column="NLM_CREATE_DATE"),
    		@Result(property="nai_ip", column="NAI_IP"),
    		@Result(property="nlm_page", column="NLM_PAGE"),
    		@Result(property="nlm_param", column="NLM_PARAM"),
    		@Result(property="nlm_result", column="NLM_RESULT"),
    		@Result(property="nlm_risk_level", column="NLM_RISK_LEVEL")
    })
    ArrayList<NcLog> getMngLogMain();
	
	@Select(REAL_EVENT)
    @Results(value = {
    		@Result(property="nle_create_date", column="NLE_CREATE_DATE"),
    		@Result(property="nle_host_name", column="NLE_HOST_NAME"),
    		@Result(property="nle_risk_level", column="NLE_RISK_LEVEL"),
    		@Result(property="nle_prog_name", column="NLE_PROG_NAME"),
    		@Result(property="nle_message", column="NLE_MESSAGE"),
    		@Result(property="ncp_name", column="NCP_NAME")
    })
    ArrayList<NcLog> getRealEvent();
	
	@Select(REAL_ACCESS)
    @Results(value = {
    		@Result(property="nla_create_date", column="NLA_CREATE_DATE"),
    		@Result(property="nla_host_name", column="NLA_HOST_NAME"),
    		@Result(property="nla_risk_level", column="NLA_RISK_LEVEL"),
    		@Result(property="nla_src_ip", column="NLA_SRC_IP"),
    		@Result(property="nla_dst_ip", column="NLA_DST_IP"),
    		@Result(property="nla_src_port", column="NLA_SRC_PORT"),
    		@Result(property="nla_dst_port", column="NLA_DST_PORT"),
    		@Result(property="nla_protocol", column="NLA_PROTOCOL"),
    		@Result(property="nla_access_type", column="NLA_ACCESS_TYPE"),
    		@Result(property="nla_div", column="NLA_DIV")
    })
    ArrayList<NcLog> getRealAccess();
	
	@Select(REAL_MNG)
    @Results(value = {
    		@Result(property="nsu_id", column="NSU_ID"),
    		@Result(property="nlm_create_date", column="NLM_CREATE_DATE"),
    		@Result(property="nai_ip", column="NAI_IP"),
    		@Result(property="nlm_page", column="NLM_PAGE"),
    		@Result(property="nlm_param", column="NLM_PARAM"),
    		@Result(property="nlm_result", column="NLM_RESULT"),
    		@Result(property="nlm_risk_level", column="NLM_RISK_LEVEL")
    })
    ArrayList<NcLog> getRealMng();
	
	@Select(INTEGRITY_CNT_ALL)
    int getIntegrityTotalCnt(NcLog ncLogEvent);
	
	@Select(INTEGRITY_PAGE)
    @Results(value = {
    		@Result(property="nli_create_date", column="NLI_CREATE_DATE"),
    		@Result(property="nli_host_name", column="NLI_HOST_NAME"),
    		@Result(property="nli_risk_level", column="NLI_RISK_LEVEL"),
    		@Result(property="nli_prog_name", column="NLI_PROG_NAME"),
    		@Result(property="nli_message", column="NLI_MESSAGE"),
    		@Result(property="ncp_name", column="NCP_NAME"),
    		@Result(property="npl_no", column="NPL_NO"),
    		@Result(property="npl_name", column="NPL_NAME")
    })
    ArrayList<NcLog> getIntegrityList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nli_create_sdate") String nli_create_sdate, @Param("nli_create_edate") String nli_create_edate);
	
	@Select(PROTECTION_GRID_CNT_ALL)
    int getProtectionTotalCnt_grid(NcLog ncLogEvent);
	
	@Select(PROTECTION_PAGE_GRID)
    ArrayList<NcLog> getProtectionList_grid(@Param("nlpt_create_sdate") String nlpt_create_sdate, @Param("nlpt_create_edate") String nlpt_create_edate);
	
	@Select(INTEGRITY_GRID_CNT_ALL)
    int getIntegrityTotalCnt_grid(NcLog ncLogEvent);
	
	@Select(INTEGRITY_PAGE_GRID)
    ArrayList<NcLog> getIntegrityList_grid(@Param("nli_create_sdate") String nli_create_sdate, @Param("nli_create_edate") String nli_create_edate);
	
	@Select(ERROR_CNT_ALL)
    int getErrorTotalCnt(NcLog ncLogEvent);
	
	@Select(ERROR_PAGE)
    @Results(value = {
    		@Result(property="nler_create_date", column="NLER_CREATE_DATE"),
    		@Result(property="nler_host_name", column="NLER_HOST_NAME"),
    		@Result(property="nler_risk_level", column="NLER_RISK_LEVEL"),
    		@Result(property="nler_prog_name", column="NLER_PROG_NAME"),
    		@Result(property="nler_message", column="NLER_MESSAGE"),
    		@Result(property="ncp_name", column="NCP_NAME")
    })
    ArrayList<NcLog> getErrorList(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nler_create_sdate") String nler_create_sdate, @Param("nler_create_edate") String nler_create_edate);

	@Select(ERROR_GRID_CNT_ALL)
    int getErrorTotalCnt_grid(NcLog ncLogEvent);
	
	@Select(ERROR_PAGE_GRID)
    ArrayList<NcLog> getErrorList_grid(@Param("nler_create_sdate") String nler_create_sdate, @Param("nler_create_edate") String nler_create_edate);
	
	
	@Select(ACCESS_PAGE2)
    @Results(value = {
    		@Result(property="nla_create_date", column="NLA_CREATE_DATE"),
    		@Result(property="nla_host_name", column="NLA_HOST_NAME"),
    		@Result(property="nla_risk_level", column="NLA_RISK_LEVEL"),
    		@Result(property="nla_src_ip", column="NLA_SRC_IP"),
    		@Result(property="nla_dst_ip", column="NLA_DST_IP"),
    		@Result(property="nla_src_port", column="NLA_SRC_PORT"),
    		@Result(property="nla_dst_port", column="NLA_DST_PORT"),
    		@Result(property="nla_protocol", column="NLA_PROTOCOL"),
    		@Result(property="nla_access_type", column="NLA_ACCESS_TYPE"),
    })
    ArrayList<NcLog> getAccessList2(@Param("page") int page, @Param("rowsPerPage") int rowsPerPage, @Param("nla_create_sdate") String nla_create_sdate, @Param("nla_create_edate") String nla_create_edate,@Param("tblDate") int tblDate);
	
}