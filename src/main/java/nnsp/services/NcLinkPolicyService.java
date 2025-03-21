package nnsp.services;

import java.util.ArrayList;

import nnsp.mappers.NcLinkPolicyMapper;
import nnsp.vo.NcLinkPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcLinkPolicyService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcLinkPolicyService.class);

	@Autowired
	private NcLinkPolicyMapper ncLinkPolicyMapper;
	
	// ip 주소 조회
	public ArrayList<NcLinkPolicy> getIpaddr(int nli_div) {
		return ncLinkPolicyMapper.getIpaddr(nli_div);
    }
	
	// ip 주소 조회
	public NcLinkPolicy getIpaddrBySeq(int nli_seq) {
		return ncLinkPolicyMapper.getIpaddrBySeq(nli_seq);
    }
	
	// 서비스 조회
	public ArrayList<NcLinkPolicy> getService(int nls_div) {
		return ncLinkPolicyMapper.getService(nls_div);
    }
	
	public NcLinkPolicy getServiceBySeq(int nls_div) {
		return ncLinkPolicyMapper.getServiceBySeq(nls_div);
    }
	
	// ip 주소 등록
	public int ipaddr_insert(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.ipaddr_insert(ncLinkPolicy);
    }
	
	// 서비스 등록
	public int service_insert(NcLinkPolicy ncPolicy) {
		return ncLinkPolicyMapper.service_insert(ncPolicy);
    }
	
	// ip 주소 수정
	public int ipaddr_update(NcLinkPolicy ncPolicy) {
		return ncLinkPolicyMapper.ipaddr_update(ncPolicy);
    }
	
	// ip 주소 수정
	public int ipaddr_update_system(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.ipaddr_update_system(ncLinkPolicy);
    }
	
	// 서비스 수정
	public int service_update(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.service_update(ncLinkPolicy);
    }
	
	// ip 주소 삭제
	public int ipaddr_delete(int nli_seq) {
		return ncLinkPolicyMapper.ipaddr_delete(nli_seq);
    }
	
	// 서비스 삭제
	public int service_delete(int nls_seq) {
		return ncLinkPolicyMapper.service_delete(nls_seq);
    }
	
	// 접속 허용 정책 조회
	public ArrayList<NcLinkPolicy> getPolicyAllow(int nla_div) {
		return ncLinkPolicyMapper.getPolicyAllow(nla_div);
    }
	
	// 접속 허용 정책 조회
	public NcLinkPolicy getPolicyAllowBySeq(int nla_seq) {
		return ncLinkPolicyMapper.getPolicyAllowBySeq(nla_seq);
    }
	
	// 접속 허용 정책 등록
	public int policy_allow_insert(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.policy_allow_insert(ncLinkPolicy);
    }
	
	// 접속 허용 정책 수정
	public int policy_allow_update(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.policy_allow_update(ncLinkPolicy);
    }
	
	// 접속 허용 정책 삭제
	public int policy_allow_delete(int nla_seq) {
		return ncLinkPolicyMapper.policy_allow_delete(nla_seq);
    }
	
	// IP 그룹 코드가 있는지 확인
	public String getIpGcode(String nli_ip_nm, int nli_div) {
		return ncLinkPolicyMapper.getIpGcode(nli_ip_nm, nli_div);
    }
	
	// 서비스 그룹 코드가 있는지 확인
	public String getSvcGcode(String nls_service_nm, int nls_div) {
		return ncLinkPolicyMapper.getSvcGcode(nls_service_nm, nls_div);
    }
	
	public int nextIpGcode(int nli_div) {
		return ncLinkPolicyMapper.nextIpGcode(nli_div);
    }
	
	public int nextSvcGcode(int nls_div) {
		return ncLinkPolicyMapper.nextSvcGcode(nls_div);
    }
	
	// 접속 허용 정책 중복 확인
	public int policy_allow_count(NcLinkPolicy ncLinkPolicy) { // 정책명
		return ncLinkPolicyMapper.policy_allow_count(ncLinkPolicy);
    }
	public int policy_allow_count_policy(NcLinkPolicy ncLinkPolicy) { // 전송 통제 정책명
		return ncLinkPolicyMapper.policy_allow_count_policy(ncLinkPolicy);
    }
	
	// IP가 정책에서 사용 중인지 확인
	public int ipaddr_use_count(int nli_seq) {
		return ncLinkPolicyMapper.ipaddr_use_count(nli_seq);
    }
	
	// 서비스가 정책에서 사용 중인지 확인
	public int service_use_count_tx(int nls_seq) {
		return ncLinkPolicyMapper.service_use_count_tx(nls_seq);
    }
	public int service_use_count_rx(int nls_seq) {
		return ncLinkPolicyMapper.service_use_count_rx(nls_seq);
    }
	
	// 서비스 정책 조회
	public ArrayList<NcLinkPolicy> getPolicyService(int nlp_div) {
		return ncLinkPolicyMapper.getPolicyService(nlp_div);
    }
	
	public ArrayList<NcLinkPolicy> getPolicyRoute(int nlp_div) {
		return ncLinkPolicyMapper.getPolicyRoute(nlp_div);
    }
	
	// 서비스 정책 조회
	public NcLinkPolicy getPolicyServiceBySeq(int nlp_seq) {
		return ncLinkPolicyMapper.getPolicyServiceBySeq(nlp_seq);
    }
		
	// 서비스 정책 등록
	public int policy_service_insert(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.policy_service_insert(ncLinkPolicy);
    }
	
	// 마지막 정책 번호 구하기
	public int last_service_seq() {
		return ncLinkPolicyMapper.last_service_seq();
	}
	
	// 정책 경로 등록
	public int policy_route_insert(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.policy_route_insert(ncLinkPolicy);
    }
	
	// 서비스 정책 수정
	public int policy_service_update(NcLinkPolicy ncLinkPolicy) {
		return ncLinkPolicyMapper.policy_service_update(ncLinkPolicy);
    }
	
	// 서비스 정책 삭제
	public int policy_service_delete(int nlp_seq) {
		return ncLinkPolicyMapper.policy_service_delete(nlp_seq);
    }
	
	// 서비스 경로 삭제
	public int policy_route_delete(int nlp_seq) {
		return ncLinkPolicyMapper.policy_route_delete(nlp_seq);
    }
	
	// 서비스 정책 중복 확인
	public int policy_service_count(NcLinkPolicy ncLinkPolicy) { // 정책명
		return ncLinkPolicyMapper.policy_service_count(ncLinkPolicy);
	}
	public int policy_service_count_type(NcLinkPolicy ncLinkPolicy) { // 콘텐츠 타입
		return ncLinkPolicyMapper.policy_service_count_type(ncLinkPolicy);
	}
	public int policy_service_count_port(NcLinkPolicy ncLinkPolicy) { // 포트
		if(ncLinkPolicy.getNlp_div()==1){
			return ncLinkPolicyMapper.policy_service_count_cport(ncLinkPolicy);
		}else{
			return ncLinkPolicyMapper.policy_service_count_rport(ncLinkPolicy);
		}
	}
	public int policy_service_count_prog(NcLinkPolicy ncLinkPolicy) { // 프로그램
		return ncLinkPolicyMapper.policy_service_count_prog(ncLinkPolicy);
	}
	
	// 그룹 코드번호로 그룹명 찾기
	public String getIpObjNm(int nli_gcode, int nli_div) {
		return ncLinkPolicyMapper.getIpObjNm(nli_gcode, nli_div);
    }
	// 그룹 코드번호로 그룹명 찾기
	public String getSvcObjNm(int nls_gcode, int nls_div) {
		return ncLinkPolicyMapper.getSvcObjNm(nls_gcode, nls_div);
    }
	
	// 콘텐츠 정책 조회
	public NcLinkPolicy getContsPolicy(int npcs_seq) {
		return ncLinkPolicyMapper.getContsPolicy(npcs_seq);
    }
	
	public NcLinkPolicy getContsName(int ntc_seq, int nsc_seq, int ndc_seq) {
		return ncLinkPolicyMapper.getContsName(ntc_seq, nsc_seq, ndc_seq);
    }

	// 연계 정책이 허용 정책에서 사용 중인지 확인
	public int policy_use_count(int nlp_seq) {
		return ncLinkPolicyMapper.policy_use_count(nlp_seq);
    }
	
	// 정책 사용 여부 변경
	public int policy_use_change(int nlp_use_yn, int nlp_seq) {
		return ncLinkPolicyMapper.policy_use_change(nlp_use_yn, nlp_seq);
    }
}