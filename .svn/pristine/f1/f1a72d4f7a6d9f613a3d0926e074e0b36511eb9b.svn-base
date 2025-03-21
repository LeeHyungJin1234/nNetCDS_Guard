package nnsp.services;

import java.io.*;
import java.util.ArrayList;

import nnsp.common.Config;
import nnsp.mappers.NcPolicyMapper;
import nnsp.vo.NcPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NcPolicyService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcPolicyService.class);
	@Autowired
	private NcPolicyMapper ncPolicyMapper;
	
	// ip 주소 조회
	public ArrayList<NcPolicy> getIpaddr(int noi_div) {
		return ncPolicyMapper.getIpaddr(noi_div);
    }
	
	// ip 주소 조회
	public NcPolicy getIpaddrBySeq(int noi_seq) {
		return ncPolicyMapper.getIpaddrBySeq(noi_seq);
    }
	
	// 서비스 조회
	public ArrayList<NcPolicy> getService(int nos_div) {
		return ncPolicyMapper.getService(nos_div);
    }
	
	public NcPolicy getServiceBySeq(int nos_div) {
		return ncPolicyMapper.getServiceBySeq(nos_div);
    }
	
	// ip 주소 등록
	public int ipaddr_insert(NcPolicy ncPolicy) {
		return ncPolicyMapper.ipaddr_insert(ncPolicy);
    }
	
	// 서비스 등록
	public int service_insert(NcPolicy ncPolicy) {
		return ncPolicyMapper.service_insert(ncPolicy);
    }
	
	// ip 주소 수정
	public int ipaddr_update(NcPolicy ncPolicy) {
		return ncPolicyMapper.ipaddr_update(ncPolicy);
    }
	
	// ip 주소 수정
	public int ipaddr_update_system(NcPolicy ncPolicy) {
		return ncPolicyMapper.ipaddr_update_system(ncPolicy);
    }
	
	// 서비스 수정
	public int service_update(NcPolicy ncPolicy) {
		return ncPolicyMapper.service_update(ncPolicy);
    }
	
	// ip 주소 삭제
	public int ipaddr_delete(int noi_seq) {
		return ncPolicyMapper.ipaddr_delete(noi_seq);
    }
	
	// 서비스 삭제
	public int service_delete(int nos_seq) {
		return ncPolicyMapper.service_delete(nos_seq);
    }
	
	// 접속 허용 정책 조회
	public ArrayList<NcPolicy> getPolicyAllow(int npa_div) {
		return ncPolicyMapper.getPolicyAllow(npa_div);
    }
	
	// 접속 허용 정책 조회
	public NcPolicy getPolicyAllowBySeq(int npa_seq) {
		return ncPolicyMapper.getPolicyAllowBySeq(npa_seq);
    }
	
	// 접속 허용 정책 등록
	public int policy_allow_insert(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_allow_insert(ncPolicy);
    }
	
	// 접속 허용 정책 수정
	public int policy_allow_update(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_allow_update(ncPolicy);
    }
	
	// 접속 허용 정책 삭제
	public int policy_allow_delete(int npa_seq) {
		return ncPolicyMapper.policy_allow_delete(npa_seq);
    }
	
	// 서비스 조회
	public ArrayList<NcPolicy> getProtocol() {
		return ncPolicyMapper.getProtocol();
    }
	
	// 프로토콜에 따른 콘텐츠 타입 조회
	public ArrayList<NcPolicy> getContsType() {
		return ncPolicyMapper.getContsType();
    }
	
	// 콘텐츠 명 조회
	public ArrayList<NcPolicy> getContents(int npc_no, String npcs_type) {
		return ncPolicyMapper.getContents(npc_no, npcs_type);
    }
	
	// 상위 콘텐츠 조회
	public ArrayList<NcPolicy> getTopConts() {
		return ncPolicyMapper.getTopConts();
    }
	
	// 서브 콘텐츠 조회
	public ArrayList<NcPolicy> getSubConts(int ntc_seq) {
		return ncPolicyMapper.getSubConts(ntc_seq);
    }
	
	// 상세 콘텐츠 조회
	public ArrayList<NcPolicy> getDetailConts(int ntc_seq, int nsc_seq) {
		return ncPolicyMapper.getDetailConts(ntc_seq, nsc_seq);
    }
		
	// IP 그룹 코드가 있는지 확인
	public String getIpGcode(String noi_obj_nm, int noi_div) {
		return ncPolicyMapper.getIpGcode(noi_obj_nm, noi_div);
    }
	
	// 서비스 그룹 코드가 있는지 확인
	public String getSvcGcode(String nos_obj_nm, int nos_div) {
		return ncPolicyMapper.getSvcGcode(nos_obj_nm, nos_div);
    }
	
	public int nextIpGcode(int noi_div) {
		return ncPolicyMapper.nextIpGcode(noi_div);
    }
	
	public int nextSvcGcode(int nos_div) {
		return ncPolicyMapper.nextSvcGcode(nos_div);
    }
	
	// 접속 허용 정책 중복 확인
	public int policy_allow_count(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_allow_count(ncPolicy);
    }
	
	// IP가 정책에서 사용 중인지 확인
	public int ipaddr_use_count(int noi_seq) {
		return ncPolicyMapper.ipaddr_use_count(noi_seq);
    }
	
	// 서비스가 정책에서 사용 중인지 확인
	public int service_use_count(int nos_seq) {
		return ncPolicyMapper.service_use_count(nos_seq);
    }
	
	// 서비스가 연계정책에서 사용 중인지 확인
	public int service_link_count(int nos_seq) {
		return ncPolicyMapper.service_link_count(nos_seq);
    }
		
	// ACL 룰 파일 만들기
	public void create_aclRuleFile(int npa_div) {
		String file_path = "";
		String line_str="";
		try {
			if(npa_div==1){
				file_path = Config.getInstance().getSndPath()+"nds_acl_rule_conf";
			}else{
				file_path = Config.getInstance().getTransPath()+"ndr_acl_rule_conf";
			}
			
			File f1 = new File(file_path);
			if(f1.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
				f1.delete();
			}
			f1.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			
			ArrayList<NcPolicy> policyList = ncPolicyMapper.getPolicyAllow(npa_div);
			for(int i=0; i < policyList.size(); i++){
				//line_str = policyList.get(i).getNpa_seq()+":"+policyList.get(i).getNoi_ip_type()+":"+policyList.get(i).getSip()+":"+(policyList.get(i).getSport()==0?"*":policyList.get(i).getSport())+":";
				line_str = policyList.get(i).getNpa_seq()+":"+policyList.get(i).getNoi_ip_type()+":"+policyList.get(i).getSip()+":";
				line_str += policyList.get(i).getDip()+":"+policyList.get(i).getDport()+":"+policyList.get(i).getNpc_no();
	      		out.write(line_str); 
	      		out.newLine(); // 줄 바꾸기
	      	}
	      	
	      	out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// 서비스 정책 조회
	public ArrayList<NcPolicy> getPolicyService(int ncp_div) {
		return ncPolicyMapper.getPolicyService(ncp_div);
    }
	
	// 서비스 정책 조회
	public NcPolicy getPolicyServiceBySeq(int nps_seq) {
		return ncPolicyMapper.getPolicyServiceBySeq(nps_seq);
    }
		
	// 서비스 정책 등록
	public int policy_service_insert(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_service_insert(ncPolicy);
    }
	
	// 서비스 정책 수정
	public int policy_service_update(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_service_update(ncPolicy);
    }
	
	// 서비스 정책 삭제
	public int policy_service_delete(int npa_seq) {
		return ncPolicyMapper.policy_service_delete(npa_seq);
    }
	
	// 마지막 정책 번호 구하기
	public int last_service_seq() {
		return ncPolicyMapper.last_service_seq();
	}	
	
	// 서비스 정책 중복 확인
	public int policy_service_count(NcPolicy ncPolicy) {
		if(ncPolicy.getNcp_div()==1){
			return ncPolicyMapper.send_service_count(ncPolicy);
		}else{
			return ncPolicyMapper.receive_service_count(ncPolicy);
		}
	}
	
	// 서비스 정책에 프로그램 중복 확인
	public int policy_program_count(NcPolicy ncPolicy) {
		return ncPolicyMapper.policy_program_count(ncPolicy);
	}
		
	// 환경설정 파일 만들기
	public void create_configFile(int nps_seq) {
		String line_str="";
		String file_path = "";
		try {
			NcPolicy policyList = ncPolicyMapper.getPolicyServiceBySeq(nps_seq);
			
			if(policyList.getNcp_div()==1){
				if(policyList.getNpc_no()==17){
					line_str="## one-way communication "+policyList.getNpc_name().toUpperCase()+" configuration file : transmission" +
							"\r\n\r\n# one-way transmission "+policyList.getNpc_name().toUpperCase()+" configuration" +
							"\r\n[one-way transmission "+policyList.getNpc_name().toUpperCase()+"]" +
							"\r\nIP_SEXT = " +policyList.getTx_intip()+
							"\r\nPORT_SEXT = " +policyList.getTx_intport()+
							"\r\nPROTO_SEXT = " +policyList.getNpc_name().toLowerCase()+
							"\r\n\r\n# One-way receiver local network" +
							"\r\n[one-way receive local]" +
							"\r\nIP_RINT = " +policyList.getRx_intip()+
							"\r\nPORT_RINT = " +policyList.getRx_intport()+
							"\r\n\r\n[Flag]" +
							"\r\nFLAG_ELC = "+policyList.getNps_elc_flag();
				}else{
					line_str="## one-way communication "+policyList.getNpc_name().toUpperCase()+" configuration file : transmission" +
							"\r\n\r\n# one-way transmission "+policyList.getNpc_name().toUpperCase()+" configuration" +
							"\r\n[one-way transmission "+policyList.getNpc_name().toUpperCase()+"]" +
							"\r\nIP_SEXT = " +policyList.getTx_intip()+
							"\r\nPORT_SEXT = " +policyList.getTx_intport()+
							"\r\nPROTO_SEXT = " +policyList.getNpc_name().toLowerCase()+
							"\r\n\r\n# One-way receiver local network" +
							"\r\n[one-way receive local]" +
							"\r\nIP_RINT = " +policyList.getRx_intip()+
							"\r\nPORT_RINT = " +policyList.getRx_intport()+
							"\r\n\r\n[Flag]" +
							"\r\nFLAG_ELC = "+policyList.getNps_elc_flag()+
							"\r\n\r\n[Contents Header]" +
							"\r\nCHEADER = "+policyList.getNpcs_header();
				}
				
				file_path = Config.getInstance().getSndPath()+policyList.getNcp_file_name()+"_conf";
			}else{
				line_str="## one-way communication "+policyList.getNpc_name().toUpperCase()+" configuration file : receive"+
						"\r\n\r\n# one-way receive "+policyList.getNpc_name().toUpperCase()+" configuration"+
						"\r\n[one-way receive "+policyList.getNpc_name().toUpperCase()+"]"+
						"\r\nIP_RDST = " +policyList.getConts_ip()+
						"\r\nPORT_RDST = " +policyList.getConts_port()+
						"\r\nPROTO_RDST = " +policyList.getNpc_name().toLowerCase()+
						"\r\n\r\n# One-way receiver local network" +
						"\r\n[one-way receive local]" +
						"\r\nIP_RINT = " +policyList.getRx_intip()+
						"\r\nPORT_RINT = "+policyList.getRx_intport();
				
				file_path = Config.getInstance().getTransPath()+policyList.getNcp_file_name()+"_conf";
			}
			
			File f1 = new File(file_path);
			if(f1.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
				f1.delete();
			}
			f1.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			out.write(line_str);
			out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// flist 파일 생성
	public void create_flist(int ncp_div) {
		String line_str="";
		String file_path = "";
		try {
			if(ncp_div==1){
				file_path = Config.getInstance().getSndPath()+"nds_flist";
			}else{
				file_path = Config.getInstance().getTransPath()+"ndr_flist";
			}
			
			File f1 = new File(file_path);
			if(f1.exists()){ // 파일이 존재 할 경우 지우고 새로 생성
				f1.delete();
			}
			f1.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(f1, true));
			
			if(ncp_div==1){
				out.write("e:/nnc-snd/system/bin/nds_cli:/nnc-snd/system/conf/nds_conf"); 
	      		out.newLine(); // 줄 바꾸기
	      		out.write("e:/nnc-snd/system/bin/nds_rxconf:/nnc-snd/system/conf/nds_rxconf_conf"); 
	      		out.newLine(); // 줄 바꾸기
			}else{
	      		out.write("e:/nnc-rcv/system/bin/ndr_cli:/nnc-rcv/system/conf/ndr_conf"); 
	      		out.newLine(); // 줄 바꾸기
	      		out.write("e:/nnc-rcv/system/bin/ndr_rxconf:/nnc-rcv/system/conf/ndr_rxconf_conf "); 
	      		out.newLine(); // 줄 바꾸기
			}
      		
			ArrayList<NcPolicy> policyList = ncPolicyMapper.create_flist(ncp_div);
			String flagStr="";
			for(int i=0; i < policyList.size(); i++){
				if(policyList.get(i).getNps_use_yn()==1) {
					flagStr="e";
				}else{
					flagStr="d";
				}
				line_str = flagStr+":"+policyList.get(i).getNcp_path()+":"+policyList.get(i).getNps_conf_file();
	      		out.write(line_str); 
	      		out.newLine(); // 줄 바꾸기
	      	}
	      	
	      	out.close();
		} catch (IOException e) {
			System.err.println(e); // 에러 메시지 출력
		}
	}
	
	// IP 주소를 사용 중인 서비스 정책 번호
	public ArrayList<NcPolicy> useIpPolicy(NcPolicy ncPolicy) {
		return ncPolicyMapper.useIpPolicy(ncPolicy);
    }
	
	// 서비스를 사용 중인 서비스 정책 번호
	public ArrayList<NcPolicy> useServicePolicy(NcPolicy ncPolicy) {
		return ncPolicyMapper.useServicePolicy(ncPolicy);
    }
	
	// 그룹 코드번호로 그룹명 찾기
	public String getIpObjNm(int noi_gcode, int noi_div) {
		return ncPolicyMapper.getIpObjNm(noi_gcode, noi_div);
    }
	// 그룹 코드번호로 그룹명 찾기
	public String getSvcObjNm(int nos_gcode, int nos_div) {
		return ncPolicyMapper.getSvcObjNm(nos_gcode, nos_div);
    }
	// 프로토콜명 찾기
	public String getProtNm(int npc_no) {
		return ncPolicyMapper.getProtNm(npc_no);
    }
	
	public ArrayList<NcPolicy> getEtherType() {
		return ncPolicyMapper.getEtherType();
    }
	public ArrayList<NcPolicy> getIpProtocol() {
		return ncPolicyMapper.getIpProtocol();
    }
}