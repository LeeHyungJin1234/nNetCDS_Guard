package nnsp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nnsp.vo.NcLog;
import nnsp.xmlmappers.NcLogXmlMapper;

import org.springframework.stereotype.Component;
import nnsp.mappers.NcLogMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class NcLogService {

	@Autowired
	private NcLogMapper ncLogMapper;
	
	@Autowired
	private NcLogXmlMapper ncLogXmlMapper; // 동적 SQL은 XMLMAPPER로 한다.
	
	// 관리자 로그 전체 갯수
	public int getMngLogTotalCnt(NcLog ncMngLog) {
		return this.ncLogMapper.getMngLogTotalCnt(ncMngLog);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getMngLogList(int nStart, int list_num, NcLog ncMngLog) {
		return this.ncLogMapper.getMngLogList(nStart, list_num, ncMngLog.getNlm_create_sdate(), ncMngLog.getNlm_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getMngSearchCnt(NcLog ncMngLog) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncMngLog.getNsu_id() != null && !"".equals(ncMngLog.getNsu_id())) map.put("nsu_id", "%"+ncMngLog.getNsu_id()+"%");
		if(ncMngLog.getNlm_create_sdate() != null && !"".equals(ncMngLog.getNlm_create_sdate())) map.put("nlm_create_sdate", ncMngLog.getNlm_create_sdate());
		if(ncMngLog.getNlm_create_edate() != null && !"".equals(ncMngLog.getNlm_create_edate())) map.put("nlm_create_edate", ncMngLog.getNlm_create_edate());
		if(ncMngLog.getNai_ip() != null && !"".equals(ncMngLog.getNai_ip())) map.put("nai_ip", "%"+ncMngLog.getNai_ip()+"%");
		if(ncMngLog.getNlm_page() != null && !"".equals(ncMngLog.getNlm_page())) map.put("nlm_page", "%"+ncMngLog.getNlm_page()+"%");
		if(ncMngLog.getNlm_param() != null && !"".equals(ncMngLog.getNlm_param())) map.put("nlm_param", "%"+ncMngLog.getNlm_param()+"%");
		if(ncMngLog.getNlm_result() != null && !"".equals(ncMngLog.getNlm_result())) map.put("nlm_result", ncMngLog.getNlm_result());
		if(ncMngLog.getNlm_risk_level() != null && !"".equals(ncMngLog.getNlm_risk_level())) map.put("nlm_risk_level", ncMngLog.getNlm_risk_level());
		
		return this.ncLogXmlMapper.getMngSearchCnt(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getMngSearchList(int nStart, int list_num, NcLog ncMngLog) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncMngLog.getNsu_id() != null && !"".equals(ncMngLog.getNsu_id())) map.put("nsu_id", "%"+ncMngLog.getNsu_id()+"%");
		if(ncMngLog.getNlm_create_sdate() != null && !"".equals(ncMngLog.getNlm_create_sdate())) map.put("nlm_create_sdate", ncMngLog.getNlm_create_sdate());
		if(ncMngLog.getNlm_create_edate() != null && !"".equals(ncMngLog.getNlm_create_edate())) map.put("nlm_create_edate", ncMngLog.getNlm_create_edate());
		if(ncMngLog.getNai_ip() != null && !"".equals(ncMngLog.getNai_ip())) map.put("nai_ip", "%"+ncMngLog.getNai_ip()+"%");
		if(ncMngLog.getNlm_page() != null && !"".equals(ncMngLog.getNlm_page())) map.put("nlm_page", "%"+ncMngLog.getNlm_page()+"%");
		if(ncMngLog.getNlm_param() != null && !"".equals(ncMngLog.getNlm_param())) map.put("nlm_param", "%"+ncMngLog.getNlm_param()+"%");
		if(ncMngLog.getNlm_result() != null && !"".equals(ncMngLog.getNlm_result())) map.put("nlm_result", ncMngLog.getNlm_result());
		if(ncMngLog.getNlm_risk_level() != null && !"".equals(ncMngLog.getNlm_risk_level())) map.put("nlm_risk_level", ncMngLog.getNlm_risk_level());
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
	
		return this.ncLogXmlMapper.getMngSearchList(map);
	}
	
	public int getMngLogTotalCnt_grid(NcLog ncMngLog) {
		return this.ncLogMapper.getMngLogTotalCnt_grid(ncMngLog);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getMngLogList_grid(int nStart, int list_num, NcLog ncMngLog) {
		return this.ncLogMapper.getMngLogList_grid(nStart, list_num, ncMngLog.getNlm_create_sdate(), ncMngLog.getNlm_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getMngSearchCnt_grid(NcLog ncMngLog) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncMngLog.getNlm_create_sdate() != null && !"".equals(ncMngLog.getNlm_create_sdate())) map.put("nlm_create_sdate", ncMngLog.getNlm_create_sdate());
		if(ncMngLog.getNlm_create_edate() != null && !"".equals(ncMngLog.getNlm_create_edate())) map.put("nlm_create_edate", ncMngLog.getNlm_create_edate());
		
		return this.ncLogXmlMapper.getMngSearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getMngSearchList_grid(int nStart, int list_num, NcLog ncMngLog) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncMngLog.getNlm_create_sdate() != null && !"".equals(ncMngLog.getNlm_create_sdate())) map.put("nlm_create_sdate", ncMngLog.getNlm_create_sdate());
		if(ncMngLog.getNlm_create_edate() != null && !"".equals(ncMngLog.getNlm_create_edate())) map.put("nlm_create_edate", ncMngLog.getNlm_create_edate());
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
	
		return this.ncLogXmlMapper.getMngSearchList_grid(map);
	}
	
	// 관리자 로그 엑셀 다운로드
	public ArrayList<NcLog> getMngCsv(NcLog ncMngLog) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncMngLog.getNsu_id() != null && !"".equals(ncMngLog.getNsu_id()))
			map.put("nsu_id", "%"+ncMngLog.getNsu_id()+"%");
		if(ncMngLog.getNlm_create_sdate() != null && !"".equals(ncMngLog.getNlm_create_sdate()))
			map.put("nlm_create_sdate", ncMngLog.getNlm_create_sdate());
		if(ncMngLog.getNlm_create_edate() != null && !"".equals(ncMngLog.getNlm_create_edate()))
			map.put("nlm_create_edate", ncMngLog.getNlm_create_edate());
		if(ncMngLog.getNai_ip() != null && !"".equals(ncMngLog.getNai_ip()))
			map.put("nai_ip", "%"+ncMngLog.getNai_ip()+"%");
		if(ncMngLog.getNlm_page() != null && !"".equals(ncMngLog.getNlm_page()))
			map.put("nlm_page", "%"+ncMngLog.getNlm_page()+"%");
		if(ncMngLog.getNlm_param() != null && !"".equals(ncMngLog.getNlm_param()))
			map.put("nlm_param", "%"+ncMngLog.getNlm_param()+"%");
		if(ncMngLog.getNlm_result() != null && !"".equals(ncMngLog.getNlm_result()))
			map.put("nlm_result", ncMngLog.getNlm_result());
		if(ncMngLog.getNlm_risk_level() != null && !"".equals(ncMngLog.getNlm_risk_level()))
			map.put("nlm_risk_level", ncMngLog.getNlm_risk_level());
		
		return this.ncLogXmlMapper.getMngCsv(map);
	}
	
	// 정책관리 로그 전체 갯수
	public int getPolicyTotalCnt(NcLog ncLogPolicy) {
		return this.ncLogMapper.getPolicyTotalCnt(ncLogPolicy);
    }
	
	// 정책관리 로그 목록
	public ArrayList<NcLog> getPolicyList(int nStart, int list_num, NcLog ncLogPolicy) {
		return this.ncLogMapper.getPolicyList(nStart, list_num, ncLogPolicy.getNlp_create_sdate(), ncLogPolicy.getNlp_create_edate());
	}
	
	// 정책관리 로그 검색 갯수
	public int getPolicySearchCnt(NcLog ncLogPolicy) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogPolicy.getNsu_id() != null && !"".equals(ncLogPolicy.getNsu_id())) map.put("nsu_id", "%"+ncLogPolicy.getNsu_id()+"%");
		if(ncLogPolicy.getNlp_create_sdate() != null && !"".equals(ncLogPolicy.getNlp_create_sdate())) map.put("nlp_create_sdate", ncLogPolicy.getNlp_create_sdate());
		if(ncLogPolicy.getNlp_create_edate() != null && !"".equals(ncLogPolicy.getNlp_create_edate())) map.put("nlp_create_edate", ncLogPolicy.getNlp_create_edate());
		if(ncLogPolicy.getNai_ip() != null && !"".equals(ncLogPolicy.getNai_ip())) map.put("nai_ip", "%"+ncLogPolicy.getNai_ip()+"%");
		if(ncLogPolicy.getNlp_page() != null && !"".equals(ncLogPolicy.getNlp_page())) map.put("nlp_page", "%"+ncLogPolicy.getNlp_page()+"%");
		if(ncLogPolicy.getNlp_param() != null && !"".equals(ncLogPolicy.getNlp_param())) map.put("nlp_param", "%"+ncLogPolicy.getNlp_param()+"%");
		if(ncLogPolicy.getNlp_result() != null && !"".equals(ncLogPolicy.getNlp_result())) map.put("nlp_result", ncLogPolicy.getNlp_result());
		if(ncLogPolicy.getNlp_risk_level() != null && !"".equals(ncLogPolicy.getNlp_risk_level())) map.put("nlp_risk_level", ncLogPolicy.getNlp_risk_level());
		
		return this.ncLogXmlMapper.getPolicySearchCnt(map);
    }

	// 정책관리 로그 검색 목록
	public ArrayList<NcLog> getPolicySearchList(int nStart, int list_num, NcLog ncLogPolicy) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogPolicy.getNsu_id() != null && !"".equals(ncLogPolicy.getNsu_id())) map.put("nsu_id", "%"+ncLogPolicy.getNsu_id()+"%");
		if(ncLogPolicy.getNlp_create_sdate() != null && !"".equals(ncLogPolicy.getNlp_create_sdate())) map.put("nlp_create_sdate", ncLogPolicy.getNlp_create_sdate());
		if(ncLogPolicy.getNlp_create_edate() != null && !"".equals(ncLogPolicy.getNlp_create_edate())) map.put("nlp_create_edate", ncLogPolicy.getNlp_create_edate());
		if(ncLogPolicy.getNai_ip() != null && !"".equals(ncLogPolicy.getNai_ip())) map.put("nai_ip", "%"+ncLogPolicy.getNai_ip()+"%");
		if(ncLogPolicy.getNlp_page() != null && !"".equals(ncLogPolicy.getNlp_page())) map.put("nlp_page", "%"+ncLogPolicy.getNlp_page()+"%");
		if(ncLogPolicy.getNlp_param() != null && !"".equals(ncLogPolicy.getNlp_param())) map.put("nlp_param", "%"+ncLogPolicy.getNlp_param()+"%");
		if(ncLogPolicy.getNlp_result() != null && !"".equals(ncLogPolicy.getNlp_result())) map.put("nlp_result", ncLogPolicy.getNlp_result());
		if(ncLogPolicy.getNlp_risk_level() != null && !"".equals(ncLogPolicy.getNlp_risk_level())) map.put("nlp_risk_level", ncLogPolicy.getNlp_risk_level());
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
	
		return this.ncLogXmlMapper.getPolicySearchList(map);
	}
	
	public int getPolicyLogTotalCnt_grid(NcLog ncPolicyData) {
		return this.ncLogMapper.getPolicyLogTotalCnt_grid(ncPolicyData);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getPolicyLogList_grid(int nStart, int list_num, NcLog ncPolicyData) {
		return this.ncLogMapper.getPolicyLogList_grid(nStart, list_num, ncPolicyData.getNlp_create_sdate(), ncPolicyData.getNlp_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getPolicySearchCnt_grid(NcLog ncPolicyData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncPolicyData.getNlp_create_sdate() != null && !"".equals(ncPolicyData.getNlp_create_sdate())) map.put("nlp_create_sdate", ncPolicyData.getNlp_create_sdate());
		if(ncPolicyData.getNlp_create_edate() != null && !"".equals(ncPolicyData.getNlp_create_edate())) map.put("nlp_create_edate", ncPolicyData.getNlp_create_edate());
		
		return this.ncLogXmlMapper.getPolicySearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getPolicySearchList_grid(int nStart, int list_num, NcLog ncPolicyData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncPolicyData.getNlp_create_sdate() != null && !"".equals(ncPolicyData.getNlp_create_sdate())) map.put("nlp_create_sdate", ncPolicyData.getNlp_create_sdate());
		if(ncPolicyData.getNlp_create_edate() != null && !"".equals(ncPolicyData.getNlp_create_edate())) map.put("nlp_create_edate", ncPolicyData.getNlp_create_edate());
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
	
		return this.ncLogXmlMapper.getPolicySearchList_grid(map);
	}
	
	// 정책관리 로그 엑셀 다운로드
	public ArrayList<NcLog> getPolicyCsv(NcLog ncLogPolicy) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogPolicy.getNsu_id() != null && !"".equals(ncLogPolicy.getNsu_id()))
			map.put("nsu_id", "%"+ncLogPolicy.getNsu_id()+"%");
		if(ncLogPolicy.getNlp_create_sdate() != null && !"".equals(ncLogPolicy.getNlp_create_sdate()))
			map.put("nlp_create_sdate", ncLogPolicy.getNlp_create_sdate());
		if(ncLogPolicy.getNlp_create_edate() != null && !"".equals(ncLogPolicy.getNlp_create_edate()))
			map.put("nlp_create_edate", ncLogPolicy.getNlp_create_edate());
		if(ncLogPolicy.getNai_ip() != null && !"".equals(ncLogPolicy.getNai_ip()))
			map.put("nai_ip", "%"+ncLogPolicy.getNai_ip()+"%");
		if(ncLogPolicy.getNlp_page() != null && !"".equals(ncLogPolicy.getNlp_page()))
			map.put("nlp_page", "%"+ncLogPolicy.getNlp_page()+"%");
		if(ncLogPolicy.getNlp_param() != null && !"".equals(ncLogPolicy.getNlp_param()))
			map.put("nlp_param", "%"+ncLogPolicy.getNlp_param()+"%");
		if(ncLogPolicy.getNlp_result() != null && !"".equals(ncLogPolicy.getNlp_result()))
			map.put("nlp_result", ncLogPolicy.getNlp_result());
		if(ncLogPolicy.getNlp_risk_level() != null && !"".equals(ncLogPolicy.getNlp_risk_level()))
			map.put("nlp_risk_level", ncLogPolicy.getNlp_risk_level());
		
		return this.ncLogXmlMapper.getPolicyCsv(map);
	}
	
	// 이벤트 로그 전체 갯수
	public int getEventTotalCnt(NcLog ncLogEvent) {
		return this.ncLogMapper.getEventTotalCnt(ncLogEvent);
    }
	
	// 이벤트 로그 목록
	public ArrayList<NcLog> getEventList(int nStart, int list_num, NcLog ncLogEvent) {
		return this.ncLogMapper.getEventList(nStart, list_num, ncLogEvent.getNle_create_sdate(), ncLogEvent.getNle_create_edate());
	}
	
	// 이벤트 로그 검색 갯수
	public int getEventSearchCnt(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogEvent.getNle_create_sdate() != null && !"".equals(ncLogEvent.getNle_create_sdate())) map.put("nle_create_sdate", ncLogEvent.getNle_create_sdate());
		if(ncLogEvent.getNle_create_edate() != null && !"".equals(ncLogEvent.getNle_create_edate())) map.put("nle_create_edate", ncLogEvent.getNle_create_edate());
		if(ncLogEvent.getNle_host_name() != null && !"".equals(ncLogEvent.getNle_host_name())) map.put("nle_host_name", "%"+ncLogEvent.getNle_host_name()+"%");
		if(ncLogEvent.getNle_risk_level() != null && !"".equals(ncLogEvent.getNle_risk_level())) map.put("nle_risk_level", ncLogEvent.getNle_risk_level());
		if(ncLogEvent.getNpl_name() != null && !"".equals(ncLogEvent.getNpl_name())) map.put("npl_name", "%"+ncLogEvent.getNpl_name()+"%");
		if(ncLogEvent.getNle_message() != null && !"".equals(ncLogEvent.getNle_message())) map.put("nle_message", "%"+ncLogEvent.getNle_message()+"%");
		
		map.put("nle_div", ncLogEvent.getNle_div());
		return this.ncLogXmlMapper.getEventSearchCnt(map);
    }

	// 이벤트 로그 검색 목록
	public ArrayList<NcLog> getEventSearchList(int nStart, int list_num, NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNle_create_sdate() != null && !"".equals(ncLogEvent.getNle_create_sdate())) map.put("nle_create_sdate", ncLogEvent.getNle_create_sdate());
		if(ncLogEvent.getNle_create_edate() != null && !"".equals(ncLogEvent.getNle_create_edate())) map.put("nle_create_edate", ncLogEvent.getNle_create_edate());
		if(ncLogEvent.getNle_host_name() != null && !"".equals(ncLogEvent.getNle_host_name())) map.put("nle_host_name", "%"+ncLogEvent.getNle_host_name()+"%");
		if(ncLogEvent.getNle_risk_level() != null && !"".equals(ncLogEvent.getNle_risk_level())) map.put("nle_risk_level", ncLogEvent.getNle_risk_level());
		if(ncLogEvent.getNpl_name() != null && !"".equals(ncLogEvent.getNpl_name())) map.put("npl_name", "%"+ncLogEvent.getNpl_name()+"%");
		if(ncLogEvent.getNle_message() != null && !"".equals(ncLogEvent.getNle_message())) map.put("nle_message", "%"+ncLogEvent.getNle_message()+"%");
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
		map.put("nle_div", ncLogEvent.getNle_div());
		return this.ncLogXmlMapper.getEventSearchList(map);
	}
	
	public int getEventLogTotalCnt_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getEventLogTotalCnt_grid(ncEvtData);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getEventLogList_grid(int nStart, int list_num, NcLog ncEvtData) {
		return this.ncLogMapper.getEventLogList_grid(nStart, list_num, ncEvtData.getNle_create_sdate(), ncEvtData.getNle_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getEventSearchCnt_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncEvtData.getNle_create_sdate() != null && !"".equals(ncEvtData.getNle_create_sdate())) map.put("nle_create_sdate", ncEvtData.getNle_create_sdate());
		if(ncEvtData.getNle_create_edate() != null && !"".equals(ncEvtData.getNle_create_edate())) map.put("nle_create_edate", ncEvtData.getNle_create_edate());
		
		return this.ncLogXmlMapper.getEventSearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getEventSearchList_grid(int nStart, int list_num, NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncEvtData.getNle_create_sdate() != null && !"".equals(ncEvtData.getNle_create_sdate())) map.put("nle_create_sdate", ncEvtData.getNle_create_sdate());
		if(ncEvtData.getNle_create_edate() != null && !"".equals(ncEvtData.getNle_create_edate())) map.put("nle_create_edate", ncEvtData.getNle_create_edate());
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
	
		return this.ncLogXmlMapper.getEventSearchList_grid(map);
	}
	
	// 이벤트 로그 엑셀 다운로드
	public ArrayList<NcLog> getEventCsv(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNle_create_sdate() != null && !"".equals(ncLogEvent.getNle_create_sdate()))
			map.put("nle_create_sdate", ncLogEvent.getNle_create_sdate());
		if(ncLogEvent.getNle_create_edate() != null && !"".equals(ncLogEvent.getNle_create_edate()))
			map.put("nle_create_edate", ncLogEvent.getNle_create_edate());
		if(ncLogEvent.getNle_host_name() != null && !"".equals(ncLogEvent.getNle_host_name()))
			map.put("nle_host_name", "%"+ncLogEvent.getNle_host_name()+"%");
		if(ncLogEvent.getNle_risk_level() != null && !"".equals(ncLogEvent.getNle_risk_level()))
			map.put("nle_risk_level", ncLogEvent.getNle_risk_level());
		if(ncLogEvent.getNpl_name() != null && !"".equals(ncLogEvent.getNpl_name()))
			map.put("npl_name", "%"+ncLogEvent.getNpl_name()+"%");
		if(ncLogEvent.getNle_message() != null && !"".equals(ncLogEvent.getNle_message()))
			map.put("nle_message", "%"+ncLogEvent.getNle_message()+"%");
		
		map.put("nle_div", ncLogEvent.getNle_div());
		
		return this.ncLogXmlMapper.getEventCsv(map);
	}
	
	public int getAccessLogTotalCnt_grid(NcLog ncAcsData, int tblDate) {
		return this.ncLogMapper.getAccessLogTotalCnt_grid(ncAcsData.getNla_create_sdate(), ncAcsData.getNla_create_edate(), tblDate);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getAccessLogList_grid(NcLog ncAcsData, int tblDate) {
		return this.ncLogMapper.getAccessLogList_grid(ncAcsData.getNla_create_sdate(), ncAcsData.getNla_create_edate(), tblDate);
	}
	
	// 관리자 로그 검색 갯수
	public int getAccessSearchCnt_grid(NcLog ncAcsData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncAcsData.getNla_create_sdate() != null && !"".equals(ncAcsData.getNla_create_sdate())) map.put("nla_create_sdate", ncAcsData.getNla_create_sdate() + " 00:00:00");
		if(ncAcsData.getNla_create_edate() != null && !"".equals(ncAcsData.getNla_create_edate())) map.put("nla_create_edate", ncAcsData.getNla_create_edate() + " 23:59:59");
		
		return this.ncLogXmlMapper.getAccessSearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getAccessSearchList_grid(NcLog ncAcsData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncAcsData.getNla_create_sdate() != null && !"".equals(ncAcsData.getNla_create_sdate())) map.put("nla_create_sdate", ncAcsData.getNla_create_sdate() + " 00:00:00");
		if(ncAcsData.getNla_create_edate() != null && !"".equals(ncAcsData.getNla_create_edate())) map.put("nla_create_edate", ncAcsData.getNla_create_edate() + " 23:59:59");
	
	return this.ncLogXmlMapper.getAccessSearchList_grid(map);
}
	
	// 접근통제 로그 전체 갯수
	public int getAccessTotalCnt(NcLog ncLogAccess) {
		return this.ncLogMapper.getAccessTotalCnt(ncLogAccess.getNla_create_sdate()+" 00:00:00", ncLogAccess.getNla_create_edate()+" 23:59:59");
    }
	
	// 접근통제 로그 목록
	public ArrayList<NcLog> getAccessList(int nStart, int list_num, NcLog ncLogAccess) {
		return this.ncLogMapper.getAccessList(nStart, list_num, ncLogAccess.getNla_create_sdate()+" 00:00:00", ncLogAccess.getNla_create_edate()+" 23:59:59");
	}
	
	// 접근통제 로그 검색 갯수
	public int getAccessSearchCnt(NcLog ncLogAccess) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogAccess.getNla_create_sdate() != null && !"".equals(ncLogAccess.getNla_create_sdate())) map.put("nla_create_sdate", ncLogAccess.getNla_create_sdate()+" 00:00:00");
		if(ncLogAccess.getNla_create_edate() != null && !"".equals(ncLogAccess.getNla_create_edate())) map.put("nla_create_edate", ncLogAccess.getNla_create_edate()+" 23:59:59");
		if(ncLogAccess.getNla_host_name() != null && !"".equals(ncLogAccess.getNla_host_name())) map.put("nla_host_name", "%"+ncLogAccess.getNla_host_name()+"%");
		if(ncLogAccess.getNla_risk_level() != null && !"".equals(ncLogAccess.getNla_risk_level())) map.put("nla_risk_level", ncLogAccess.getNla_risk_level());
		if(ncLogAccess.getNla_access_type() != null && !"".equals(ncLogAccess.getNla_access_type())) map.put("nla_access_type", ncLogAccess.getNla_access_type());
		if(ncLogAccess.getNla_div2() != null && !"".equals(ncLogAccess.getNla_div2())) map.put("nla_div2", ncLogAccess.getNla_div2());
		if(ncLogAccess.getNla_src_ip() != null && !"".equals(ncLogAccess.getNla_src_ip())) map.put("nla_src_ip", "%"+ncLogAccess.getNla_src_ip()+"%");
		if(ncLogAccess.getNla_src_port() != null && !"".equals(ncLogAccess.getNla_src_port())) map.put("nla_src_port", "%"+ncLogAccess.getNla_src_port()+"%");
		if(ncLogAccess.getNla_dst_ip() != null && !"".equals(ncLogAccess.getNla_dst_ip())) map.put("nla_dst_ip", "%"+ncLogAccess.getNla_dst_ip()+"%");
		if(ncLogAccess.getNla_dst_port() != null && !"".equals(ncLogAccess.getNla_dst_port())) map.put("nla_dst_port", "%"+ncLogAccess.getNla_dst_port()+"%");
		if(ncLogAccess.getNla_protocol() != null && !"".equals(ncLogAccess.getNla_protocol())) map.put("nla_protocol", "%"+ncLogAccess.getNla_protocol()+"%");
		map.put("nla_div", ncLogAccess.getNla_div());
		return this.ncLogXmlMapper.getAccessSearchCnt(map);
    }

	// 접근통제 로그 검색 목록
	public ArrayList<NcLog> getAccessSearchList(int nStart, int list_num, NcLog ncLogAccess) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogAccess.getNla_create_sdate() != null && !"".equals(ncLogAccess.getNla_create_sdate())) map.put("nla_create_sdate", ncLogAccess.getNla_create_sdate()+" 00:00:00");
		if(ncLogAccess.getNla_create_edate() != null && !"".equals(ncLogAccess.getNla_create_edate())) map.put("nla_create_edate", ncLogAccess.getNla_create_edate()+" 23:59:59");
		if(ncLogAccess.getNla_host_name() != null && !"".equals(ncLogAccess.getNla_host_name())) map.put("nla_host_name", "%"+ncLogAccess.getNla_host_name()+"%");
		if(ncLogAccess.getNla_risk_level() != null && !"".equals(ncLogAccess.getNla_risk_level())) map.put("nla_risk_level", ncLogAccess.getNla_risk_level());
		if(ncLogAccess.getNla_access_type() != null && !"".equals(ncLogAccess.getNla_access_type())) map.put("nla_access_type", ncLogAccess.getNla_access_type());
		if(ncLogAccess.getNla_div2() != null && !"".equals(ncLogAccess.getNla_div2())) map.put("nla_div2", ncLogAccess.getNla_div2());
		if(ncLogAccess.getNla_src_ip() != null && !"".equals(ncLogAccess.getNla_src_ip())) map.put("nla_src_ip", "%"+ncLogAccess.getNla_src_ip()+"%");
		if(ncLogAccess.getNla_src_port() != null && !"".equals(ncLogAccess.getNla_src_port())) map.put("nla_src_port", "%"+ncLogAccess.getNla_src_port()+"%");
		if(ncLogAccess.getNla_dst_ip() != null && !"".equals(ncLogAccess.getNla_dst_ip())) map.put("nla_dst_ip", "%"+ncLogAccess.getNla_dst_ip()+"%");
		if(ncLogAccess.getNla_dst_port() != null && !"".equals(ncLogAccess.getNla_dst_port())) map.put("nla_dst_port", "%"+ncLogAccess.getNla_dst_port()+"%");
		if(ncLogAccess.getNla_protocol() != null && !"".equals(ncLogAccess.getNla_protocol())) map.put("nla_protocol", "%"+ncLogAccess.getNla_protocol()+"%");
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
		map.put("nla_div", ncLogAccess.getNla_div());
		return this.ncLogXmlMapper.getAccessSearchList(map);
	}
	
	// 메인 실시간 로그
	public ArrayList<NcLog> getMngLogMain() {
		return this.ncLogMapper.getMngLogMain();
	}
	
	// 실시간 이벤트 로그
	public ArrayList<NcLog> getRealEvent() {
		return this.ncLogMapper.getRealEvent();
	}
	
	// 실시간 접속 통제 로그
	public ArrayList<NcLog> getRealAccess() {
		return this.ncLogMapper.getRealAccess();
	}
	
	// 실시간 관리자 로그
	public ArrayList<NcLog> getRealMng() {
		return this.ncLogMapper.getRealMng();
	}
	
	// 무결성 로그 전체 갯수
	public int getIntegrityTotalCnt(NcLog ncLogEvent) {
		return this.ncLogMapper.getIntegrityTotalCnt(ncLogEvent);
    }
	
	// 무결성 로그 목록
	public ArrayList<NcLog> getIntegrityList(int nStart, int list_num, NcLog ncLogEvent) {
		return this.ncLogMapper.getIntegrityList(nStart, list_num, ncLogEvent.getNli_create_sdate(), ncLogEvent.getNli_create_edate());
	}
	
	// 무결성 로그 검색 갯수
	public int getIntegritySearchCnt(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogEvent.getNli_create_sdate() != null && !"".equals(ncLogEvent.getNli_create_sdate())) map.put("nli_create_sdate", ncLogEvent.getNli_create_sdate());
		if(ncLogEvent.getNli_create_edate() != null && !"".equals(ncLogEvent.getNli_create_edate())) map.put("nli_create_edate", ncLogEvent.getNli_create_edate());
		if(ncLogEvent.getNli_host_name() != null && !"".equals(ncLogEvent.getNli_host_name())) map.put("nli_host_name", "%"+ncLogEvent.getNli_host_name()+"%");
		if(ncLogEvent.getNli_risk_level() != null && !"".equals(ncLogEvent.getNli_risk_level())) map.put("nli_risk_level", ncLogEvent.getNli_risk_level());
		if(ncLogEvent.getNcp_name() != null && !"".equals(ncLogEvent.getNcp_name())) map.put("ncp_name", "%"+ncLogEvent.getNcp_name()+"%");
		if(ncLogEvent.getNli_message() != null && !"".equals(ncLogEvent.getNli_message())) map.put("nli_message", "%"+ncLogEvent.getNli_message()+"%");
		
		map.put("nli_div", ncLogEvent.getNli_div());
		return this.ncLogXmlMapper.getIntegritySearchCnt(map);
    }

	// 무결성 로그 검색 목록
	public ArrayList<NcLog> getIntegritySearchList(int nStart, int list_num, NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNli_create_sdate() != null && !"".equals(ncLogEvent.getNli_create_sdate())) map.put("nli_create_sdate", ncLogEvent.getNli_create_sdate());
		if(ncLogEvent.getNli_create_edate() != null && !"".equals(ncLogEvent.getNli_create_edate())) map.put("nli_create_edate", ncLogEvent.getNli_create_edate());
		if(ncLogEvent.getNli_host_name() != null && !"".equals(ncLogEvent.getNli_host_name())) map.put("nli_host_name", "%"+ncLogEvent.getNli_host_name()+"%");
		if(ncLogEvent.getNli_risk_level() != null && !"".equals(ncLogEvent.getNli_risk_level())) map.put("nli_risk_level", ncLogEvent.getNli_risk_level());
		if(ncLogEvent.getNpl_name() != null && !"".equals(ncLogEvent.getNpl_name())) map.put("npl_name", "%"+ncLogEvent.getNpl_name()+"%");
		if(ncLogEvent.getNli_message() != null && !"".equals(ncLogEvent.getNli_message())) map.put("nli_message", "%"+ncLogEvent.getNli_message()+"%");
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
		map.put("nli_div", ncLogEvent.getNli_div());
		return this.ncLogXmlMapper.getIntegritySearchList(map);
	}
	
	// 무결성 로그 엑셀 다운로드
	public ArrayList<NcLog> getProtectionCsv(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNlpt_create_sdate() != null && !"".equals(ncLogEvent.getNlpt_create_sdate()))
			map.put("nlpt_create_sdate", ncLogEvent.getNlpt_create_sdate());
		if(ncLogEvent.getNlpt_create_edate() != null && !"".equals(ncLogEvent.getNlpt_create_edate()))
			map.put("nlpt_create_edate", ncLogEvent.getNlpt_create_edate());
		if(ncLogEvent.getNlpt_div_str() != null && !"".equals(ncLogEvent.getNlpt_div_str()))
			map.put("nlpt_div_str", "%"+ncLogEvent.getNlpt_div_str()+"%");
		if(ncLogEvent.getNsu_id() != null && !"".equals(ncLogEvent.getNsu_id()))
			map.put("nsu_id", ncLogEvent.getNsu_id());
		if(ncLogEvent.getNlpt_result() != null && !"".equals(ncLogEvent.getNlpt_result())) 
			map.put("nlpt_result", "%"+ncLogEvent.getNlpt_result()+"%");
		if(ncLogEvent.getNlpt_risk_level() != null && !"".equals(ncLogEvent.getNlpt_risk_level()))
			map.put("nlpt_risk_level", "%"+ncLogEvent.getNlpt_risk_level()+"%");
		if(ncLogEvent.getNlpt_prog_name() != null && !"".equals(ncLogEvent.getNlpt_prog_name()))
			map.put("nlpt_prog_name", "%"+ncLogEvent.getNlpt_prog_name()+"%");
		if(ncLogEvent.getNlpt_message() != null && !"".equals(ncLogEvent.getNlpt_message()))
			map.put("nlpt_message", "%"+ncLogEvent.getNlpt_message()+"%");
		
			
		map.put("nlpt_div", ncLogEvent.getNlpt_div());
		
		return this.ncLogXmlMapper.getProtectionCsv(map);
	}
	
	public int getProtectionTotalCnt_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getProtectionTotalCnt_grid(ncEvtData);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getProtectionList_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getProtectionList_grid(ncEvtData.getNlpt_create_sdate(), ncEvtData.getNlpt_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getProtectionSearchCnt_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncEvtData.getNli_create_sdate() != null && !"".equals(ncEvtData.getNli_create_sdate())) map.put("nli_create_sdate", ncEvtData.getNli_create_sdate());
		if(ncEvtData.getNli_create_edate() != null && !"".equals(ncEvtData.getNli_create_edate())) map.put("nli_create_edate", ncEvtData.getNli_create_edate());
		
		return this.ncLogXmlMapper.getIntegritySearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getProtectionSearchList_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncEvtData.getNli_create_sdate() != null && !"".equals(ncEvtData.getNli_create_sdate())) map.put("nli_create_sdate", ncEvtData.getNli_create_sdate());
		if(ncEvtData.getNli_create_edate() != null && !"".equals(ncEvtData.getNli_create_edate())) map.put("nli_create_edate", ncEvtData.getNli_create_edate());
	
		return this.ncLogXmlMapper.getIntegritySearchList_grid(map);
	}
	
	// 무결성 로그 엑셀 다운로드
	public ArrayList<NcLog> getIntegrityCsv(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNli_create_sdate() != null && !"".equals(ncLogEvent.getNli_create_sdate()))
			map.put("nli_create_sdate", ncLogEvent.getNli_create_sdate());
		if(ncLogEvent.getNli_create_edate() != null && !"".equals(ncLogEvent.getNli_create_edate()))
			map.put("nli_create_edate", ncLogEvent.getNli_create_edate());
		if(ncLogEvent.getNli_host_name() != null && !"".equals(ncLogEvent.getNli_host_name()))
			map.put("nli_host_name", "%"+ncLogEvent.getNli_host_name()+"%");
		if(ncLogEvent.getNli_risk_level() != null && !"".equals(ncLogEvent.getNli_risk_level()))
			map.put("nli_risk_level", ncLogEvent.getNli_risk_level());
		if(ncLogEvent.getNpl_name() != null && !"".equals(ncLogEvent.getNpl_name())) 
			map.put("npl_name", "%"+ncLogEvent.getNpl_name()+"%");
		if(ncLogEvent.getNli_message() != null && !"".equals(ncLogEvent.getNli_message()))
			map.put("nli_message", "%"+ncLogEvent.getNli_message()+"%");
			
		map.put("nli_div", ncLogEvent.getNli_div());
		
		return this.ncLogXmlMapper.getIntegrityCsv(map);
	}
	
	public int getIntegrityTotalCnt_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getIntegrityTotalCnt_grid(ncEvtData);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getIntegrityList_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getIntegrityList_grid(ncEvtData.getNli_create_sdate(), ncEvtData.getNli_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getIntegritySearchCnt_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncEvtData.getNli_create_sdate() != null && !"".equals(ncEvtData.getNli_create_sdate())) map.put("nli_create_sdate", ncEvtData.getNli_create_sdate());
		if(ncEvtData.getNli_create_edate() != null && !"".equals(ncEvtData.getNli_create_edate())) map.put("nli_create_edate", ncEvtData.getNli_create_edate());
		
		return this.ncLogXmlMapper.getIntegritySearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getIntegritySearchList_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncEvtData.getNli_create_sdate() != null && !"".equals(ncEvtData.getNli_create_sdate())) map.put("nli_create_sdate", ncEvtData.getNli_create_sdate());
		if(ncEvtData.getNli_create_edate() != null && !"".equals(ncEvtData.getNli_create_edate())) map.put("nli_create_edate", ncEvtData.getNli_create_edate());
	
		return this.ncLogXmlMapper.getIntegritySearchList_grid(map);
	}
		
	// 에러 로그 전체 갯수
	public int getErrorTotalCnt(NcLog ncLogEvent) {
		return this.ncLogMapper.getErrorTotalCnt(ncLogEvent);
    }
	
	// 에러 로그 목록
	public ArrayList<NcLog> getErrorList(int nStart, int list_num, NcLog ncLogEvent) {
		return this.ncLogMapper.getErrorList(nStart, list_num, ncLogEvent.getNler_create_sdate(), ncLogEvent.getNler_create_edate());
	}
	
	// 에러 로그 검색 갯수
	public int getErrorSearchCnt(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogEvent.getNler_create_sdate() != null && !"".equals(ncLogEvent.getNler_create_sdate())) map.put("nler_create_sdate", ncLogEvent.getNler_create_sdate());
		if(ncLogEvent.getNler_create_edate() != null && !"".equals(ncLogEvent.getNler_create_edate())) map.put("nler_create_edate", ncLogEvent.getNler_create_edate());
		if(ncLogEvent.getNler_host_name() != null && !"".equals(ncLogEvent.getNler_host_name())) map.put("nler_host_name", "%"+ncLogEvent.getNler_host_name()+"%");
		if(ncLogEvent.getNler_risk_level() != null && !"".equals(ncLogEvent.getNler_risk_level())) map.put("nler_risk_level", ncLogEvent.getNler_risk_level());
		if(ncLogEvent.getNcp_name() != null && !"".equals(ncLogEvent.getNcp_name())) map.put("ncp_name", "%"+ncLogEvent.getNcp_name()+"%");
		if(ncLogEvent.getNler_message() != null && !"".equals(ncLogEvent.getNler_message())) map.put("nler_message", "%"+ncLogEvent.getNler_message()+"%");
		
		map.put("nler_div", ncLogEvent.getNler_div());
		return this.ncLogXmlMapper.getErrorSearchCnt(map);
    }

	// 에러 로그 검색 목록
	public ArrayList<NcLog> getErrorSearchList(int nStart, int list_num, NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncLogEvent.getNler_create_sdate() != null && !"".equals(ncLogEvent.getNler_create_sdate())) map.put("nler_create_sdate", ncLogEvent.getNler_create_sdate());
		if(ncLogEvent.getNler_create_edate() != null && !"".equals(ncLogEvent.getNler_create_edate())) map.put("nler_create_edate", ncLogEvent.getNler_create_edate());
		if(ncLogEvent.getNler_host_name() != null && !"".equals(ncLogEvent.getNler_host_name())) map.put("nler_host_name", "%"+ncLogEvent.getNler_host_name()+"%");
		if(ncLogEvent.getNler_risk_level() != null && !"".equals(ncLogEvent.getNler_risk_level())) map.put("nler_risk_level", ncLogEvent.getNler_risk_level());
		if(ncLogEvent.getNcp_name() != null && !"".equals(ncLogEvent.getNcp_name())) map.put("ncp_name", "%"+ncLogEvent.getNcp_name()+"%");
		if(ncLogEvent.getNler_message() != null && !"".equals(ncLogEvent.getNler_message())) map.put("nler_message", "%"+ncLogEvent.getNler_message()+"%");
		
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
		map.put("nler_div", ncLogEvent.getNler_div());
		return this.ncLogXmlMapper.getErrorSearchList(map);
	}
	
	// 에러 로그 엑셀 다운로드
	public ArrayList<NcLog> getErrorCsv(NcLog ncLogEvent) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(ncLogEvent.getNler_create_sdate() != null && !"".equals(ncLogEvent.getNler_create_sdate()))
			map.put("nler_create_sdate", ncLogEvent.getNler_create_sdate());
		if(ncLogEvent.getNler_create_edate() != null && !"".equals(ncLogEvent.getNler_create_edate()))
			map.put("nler_create_edate", ncLogEvent.getNler_create_edate());
		if(ncLogEvent.getNler_host_name() != null && !"".equals(ncLogEvent.getNler_host_name()))
			map.put("nler_host_name", "%"+ncLogEvent.getNler_host_name()+"%");
		if(ncLogEvent.getNler_risk_level() != null && !"".equals(ncLogEvent.getNler_risk_level()))
			map.put("nler_risk_level", ncLogEvent.getNler_risk_level());
		if(ncLogEvent.getNcp_name() != null && !"".equals(ncLogEvent.getNcp_name()))
			map.put("ncp_name", "%"+ncLogEvent.getNcp_name()+"%");
		if(ncLogEvent.getNler_message() != null && !"".equals(ncLogEvent.getNler_message()))
			map.put("nler_message", "%"+ncLogEvent.getNler_message()+"%");
		
		return this.ncLogXmlMapper.getErrorCsv(map);
	}
	
	public int getErrorTotalCnt_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getErrorTotalCnt_grid(ncEvtData);
    }
	
	// 관리자 로그 목록
	public ArrayList<NcLog> getErrorList_grid(NcLog ncEvtData) {
		return this.ncLogMapper.getErrorList_grid(ncEvtData.getNler_create_sdate(), ncEvtData.getNler_create_edate());
	}
	
	// 관리자 로그 검색 갯수
	public int getErrorSearchCnt_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncEvtData.getNler_create_sdate() != null && !"".equals(ncEvtData.getNler_create_sdate())) map.put("nler_create_sdate", ncEvtData.getNler_create_sdate());
		if(ncEvtData.getNler_create_edate() != null && !"".equals(ncEvtData.getNler_create_edate())) map.put("nler_create_edate", ncEvtData.getNler_create_edate());
		
		return this.ncLogXmlMapper.getErrorSearchCnt_grid(map);
    }

	// 관리자 로그 검색 목록
	public ArrayList<NcLog> getErrorSearchList_grid(NcLog ncEvtData) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		if(ncEvtData.getNler_create_sdate() != null && !"".equals(ncEvtData.getNler_create_sdate())) map.put("nler_create_sdate", ncEvtData.getNler_create_sdate());
		if(ncEvtData.getNler_create_edate() != null && !"".equals(ncEvtData.getNler_create_edate())) map.put("nler_create_edate", ncEvtData.getNler_create_edate());
	
		return this.ncLogXmlMapper.getErrorSearchList_grid(map);
	}

	// 접근통제 로그 목록
	public ArrayList<NcLog> getAccessList2(int nStart, int list_num, NcLog ncLogAccess, int tblDate) {
		return this.ncLogMapper.getAccessList2(nStart, list_num, ncLogAccess.getNla_create_sdate()+" 00:00:00", ncLogAccess.getNla_create_edate()+" 23:59:59",tblDate);
	}

	public int getAccessSearchCnt2(NcLog ncLogAccess, int tblDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(ncLogAccess.getNla_create_sdate() != null && !"".equals(ncLogAccess.getNla_create_sdate())) map.put("nla_create_sdate", ncLogAccess.getNla_create_sdate());
		if(ncLogAccess.getNla_create_edate() != null && !"".equals(ncLogAccess.getNla_create_edate())) map.put("nla_create_edate", ncLogAccess.getNla_create_edate());
		if(ncLogAccess.getNla_host_name() != null && !"".equals(ncLogAccess.getNla_host_name())) map.put("nla_host_name", "%"+ncLogAccess.getNla_host_name()+"%");
		if(ncLogAccess.getNla_risk_level() != null && !"".equals(ncLogAccess.getNla_risk_level())) map.put("nla_risk_level", ncLogAccess.getNla_risk_level());
		if(ncLogAccess.getNla_access_type() != null && !"".equals(ncLogAccess.getNla_access_type())) map.put("nla_access_type", ncLogAccess.getNla_access_type());
		if(ncLogAccess.getNla_div2() != null && !"".equals(ncLogAccess.getNla_div2())) map.put("nla_div2", ncLogAccess.getNla_div2());
		if(ncLogAccess.getNla_src_ip() != null && !"".equals(ncLogAccess.getNla_src_ip())) map.put("nla_src_ip", "%"+ncLogAccess.getNla_src_ip()+"%");
		if(ncLogAccess.getNla_src_port() != null && !"".equals(ncLogAccess.getNla_src_port())) map.put("nla_src_port", "%"+ncLogAccess.getNla_src_port()+"%");
		if(ncLogAccess.getNla_dst_ip() != null && !"".equals(ncLogAccess.getNla_dst_ip())) map.put("nla_dst_ip", "%"+ncLogAccess.getNla_dst_ip()+"%");
		if(ncLogAccess.getNla_dst_port() != null && !"".equals(ncLogAccess.getNla_dst_port())) map.put("nla_dst_port", "%"+ncLogAccess.getNla_dst_port()+"%");
		if(ncLogAccess.getNla_protocol() != null && !"".equals(ncLogAccess.getNla_protocol())) map.put("nla_protocol", "%"+ncLogAccess.getNla_protocol()+"%");
		
		map.put("table_name", tblDate);
		map.put("nla_div", ncLogAccess.getNla_div());
		return this.ncLogXmlMapper.getAccessSearchCnt2(map);
    }

	public List<NcLog> getAccessSearchList2(int nStart, int list_num, NcLog ncLogAccess, int tblDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(ncLogAccess.getNla_create_sdate() != null && !"".equals(ncLogAccess.getNla_create_sdate())) map.put("nla_create_sdate", ncLogAccess.getNla_create_sdate()+" 00:00:00");
		if(ncLogAccess.getNla_create_edate() != null && !"".equals(ncLogAccess.getNla_create_edate())) map.put("nla_create_edate", ncLogAccess.getNla_create_edate()+" 23:59:59");
		if(ncLogAccess.getNla_host_name() != null && !"".equals(ncLogAccess.getNla_host_name())) map.put("nla_host_name", "%"+ncLogAccess.getNla_host_name()+"%");
		if(ncLogAccess.getNla_risk_level() != null && !"".equals(ncLogAccess.getNla_risk_level())) map.put("nla_risk_level", ncLogAccess.getNla_risk_level());
		if(ncLogAccess.getNla_access_type() != null && !"".equals(ncLogAccess.getNla_access_type())) map.put("nla_access_type", ncLogAccess.getNla_access_type());
		if(ncLogAccess.getNla_div2() != null && !"".equals(ncLogAccess.getNla_div2())) map.put("nla_div2", ncLogAccess.getNla_div2());
		if(ncLogAccess.getNla_src_ip() != null && !"".equals(ncLogAccess.getNla_src_ip())) map.put("nla_src_ip", "%"+ncLogAccess.getNla_src_ip()+"%");
		if(ncLogAccess.getNla_src_port() != null && !"".equals(ncLogAccess.getNla_src_port())) map.put("nla_src_port", "%"+ncLogAccess.getNla_src_port()+"%");
		if(ncLogAccess.getNla_dst_ip() != null && !"".equals(ncLogAccess.getNla_dst_ip())) map.put("nla_dst_ip", "%"+ncLogAccess.getNla_dst_ip()+"%");
		if(ncLogAccess.getNla_dst_port() != null && !"".equals(ncLogAccess.getNla_dst_port())) map.put("nla_dst_port", "%"+ncLogAccess.getNla_dst_port()+"%");
		if(ncLogAccess.getNla_protocol() != null && !"".equals(ncLogAccess.getNla_protocol())) map.put("nla_protocol", "%"+ncLogAccess.getNla_protocol()+"%");
		map.put("table_name", tblDate);
		map.put("page", nStart);
		map.put("rowsPerPage", list_num);
		map.put("nla_div", ncLogAccess.getNla_div());
		return this.ncLogXmlMapper.getAccessSearchList2(map);
	}

	public ArrayList<NcLog> getAccessCsv2(NcLog ncLogAccess) {
		HashMap<String, Object> map = new HashMap<>();
		if(ncLogAccess.getNla_create_sdate() != null && !"".equals(ncLogAccess.getNla_create_sdate()))
			map.put("nla_create_sdate", ncLogAccess.getNla_create_sdate()+" 00:00:00");
		if(ncLogAccess.getNla_create_edate() != null && !"".equals(ncLogAccess.getNla_create_edate()))
			map.put("nla_create_edate", ncLogAccess.getNla_create_edate()+" 23:59:59");
		if(ncLogAccess.getNla_host_name() != null && !"".equals(ncLogAccess.getNla_host_name()))
			map.put("nla_host_name", "%"+ncLogAccess.getNla_host_name()+"%");
		if(ncLogAccess.getNla_risk_level() != null && !"".equals(ncLogAccess.getNla_risk_level()))
			map.put("nla_risk_level", ncLogAccess.getNla_risk_level());
		if(ncLogAccess.getNla_access_type() != null && !"".equals(ncLogAccess.getNla_access_type()))
			map.put("nla_access_type", ncLogAccess.getNla_access_type());
		if(ncLogAccess.getNla_div2() != null && !"".equals(ncLogAccess.getNla_div2()))
			map.put("nla_div2", ncLogAccess.getNla_div2());
		if(ncLogAccess.getNla_src_ip() != null && !"".equals(ncLogAccess.getNla_src_ip()))
			map.put("nla_src_ip", "%"+ncLogAccess.getNla_src_ip()+"%");
		if(ncLogAccess.getNla_src_port() != null && !"".equals(ncLogAccess.getNla_src_port()))
			map.put("nla_src_port", "%"+ncLogAccess.getNla_src_port()+"%");
		if(ncLogAccess.getNla_dst_ip() != null && !"".equals(ncLogAccess.getNla_dst_ip()))
			map.put("nla_dst_ip", "%"+ncLogAccess.getNla_dst_ip()+"%");
		if(ncLogAccess.getNla_dst_port() != null && !"".equals(ncLogAccess.getNla_dst_port()))
			map.put("nla_dst_port", "%"+ncLogAccess.getNla_dst_port()+"%");
		if(ncLogAccess.getNla_protocol() != null && !"".equals(ncLogAccess.getNla_protocol()))
			map.put("nla_protocol", "%"+ncLogAccess.getNla_protocol()+"%");
		
		map.put("nla_div", ncLogAccess.getNla_div());
		
		return this.ncLogXmlMapper.getAccessCsv2(map);
	}
	
}