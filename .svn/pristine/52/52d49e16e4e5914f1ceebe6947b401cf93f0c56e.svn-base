package nnsp.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import nnsp.mappers.NcAuditMapper;
import nnsp.vo.NcAudit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcAuditService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcAuditService.class);
	@Autowired
	private NcAuditMapper ncAuditMapper;
	
	// 관리자 로그 기록
	public int mng_log_insert(String nsu_id, String nai_ip, String nlm_page, String nlm_param, String nlm_result, String nlm_risk_level) {
		NcAudit ncAudit = new NcAudit();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ncAudit.setNsu_id(nsu_id);
		ncAudit.setNlm_create_date(sdf.format(new Date(System.currentTimeMillis())));
		ncAudit.setNai_ip(nai_ip);
		ncAudit.setNlm_page(nlm_page);
		ncAudit.setNlm_param(nlm_param);
		ncAudit.setNlm_result(nlm_result);
		ncAudit.setNlm_risk_level(nlm_risk_level);
		return ncAuditMapper.mng_log_insert(ncAudit);
    }
	
	// 정책관리 로그 기록
	public int policy_log_insert(String nsu_id, String nai_ip, String nlp_page, String nlp_param, String nlp_result, String nlp_risk_level) {
		NcAudit ncAudit = new NcAudit();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ncAudit.setNsu_id(nsu_id);
		ncAudit.setNlp_create_date(sdf.format(new Date(System.currentTimeMillis())));
		ncAudit.setNai_ip(nai_ip);
		ncAudit.setNlp_page(nlp_page);
		ncAudit.setNlp_param(nlp_param);
		ncAudit.setNlp_result(nlp_result);
		ncAudit.setNlp_risk_level(nlp_risk_level);
		return ncAuditMapper.policy_log_insert(ncAudit);
    }

	public int policy_log_insert2(String nsu_id, String nai_ip, String nlp_page, String nlp_param,String nlp_result, String nlp_risk_level) {
		NcAudit ncAudit = new NcAudit();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ncAudit.setNsu_id(nsu_id);
		ncAudit.setNlp_create_date(sdf.format(new Date(System.currentTimeMillis())));
		ncAudit.setNai_ip(nai_ip);
		ncAudit.setNlp_page(nlp_page);
		ncAudit.setNlp_param(nlp_param);
		ncAudit.setNlp_result(nlp_result);
		ncAudit.setNlp_risk_level(nlp_risk_level);
		return ncAuditMapper.policy_log_insert2(ncAudit);
		
	}
	
	public int integrity_log_insert(String nsu_id, String nli_host_name, String nli_prog_name, String nli_message, String nli_result, String nli_risk_level) {
		NcAudit ncAudit = new NcAudit();
					
		ncAudit.setNsu_id(nsu_id);
		ncAudit.setNli_host_name(nli_host_name);
		ncAudit.setNli_prog_name(nli_prog_name);
		ncAudit.setNli_message(nli_message);
		ncAudit.setNli_result(nli_result);
		ncAudit.setNli_risk_level(nli_risk_level);
		return ncAuditMapper.integrity_log_insert(ncAudit);
    }
}