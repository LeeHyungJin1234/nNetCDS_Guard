package nnsp.mappers;

import nnsp.vo.NcAudit;

import org.apache.ibatis.annotations.Insert;

public interface NcAuditMapper {
	// 관리자 로그 기록
	final String MNG_LOG_INSERT = "INSERT INTO NNC_LOG_MNG(NSU_ID, NLM_CREATE_DATE, NAI_IP, NLM_PAGE, NLM_PARAM, NLM_RESULT, NLM_RISK_LEVEL) " +
			"VALUES(#{nsu_id}, #{nlm_create_date}, #{nai_ip}, #{nlm_page}, #{nlm_param}, #{nlm_result}, #{nlm_risk_level})"; 
	
	// 정책관리 로그 기록
	final String POLICY_LOG_INSERT = "INSERT INTO NNC_LOG_POLICY(NSU_ID, NLP_CREATE_DATE, NAI_IP, NLP_PAGE, NLP_PARAM, NLP_RESULT, NLP_RISK_LEVEL) " +
			"VALUES(#{nsu_id}, #{nlp_create_date}, #{nai_ip}, #{nlp_page}, #{nlp_param}, #{nlp_result}, #{nlp_risk_level})"; 
	
	final String POLICY_LOG_INSERT2 = "INSERT INTO NNC_LOG_POLICY(NSU_ID, NLP_CREATE_DATE, NAI_IP, NLP_PAGE, NLP_PARAM, NLP_RESULT, NLP_RISK_LEVEL) " +
			"VALUES(#{nsu_id}, #{nlp_create_date}, #{nai_ip}, #{nlp_page}, #{nlp_param}, #{nlp_result}, #{nlp_risk_level})"; 
	
	// 무결성 로그 기록
	final String INTEGRITY_LOG_INSERT =
		"INSERT INTO NNC_LOG_INTEGRITY (NSU_ID, NLI_CREATE_DATE, NLI_HOST_NAME, " +
			"NLI_PROG_NAME, NLI_MESSAGE, NLI_RESULT, NLI_RISK_LEVEL, NLI_DIV) " +
		"VALUES(#{nsu_id}, now(), #{nli_host_name}, #{nli_prog_name}, " +
			"#{nli_message}, #{nli_result}, #{nli_risk_level}, 1)";
	
	@Insert(MNG_LOG_INSERT)
	int mng_log_insert(NcAudit ncAudit);
	
	@Insert(POLICY_LOG_INSERT)
	int policy_log_insert(NcAudit ncAudit);
	@Insert(POLICY_LOG_INSERT2)
	int policy_log_insert2(NcAudit ncAudit);
	
	@Insert(INTEGRITY_LOG_INSERT)
	int integrity_log_insert(NcAudit ncAudit);
}
