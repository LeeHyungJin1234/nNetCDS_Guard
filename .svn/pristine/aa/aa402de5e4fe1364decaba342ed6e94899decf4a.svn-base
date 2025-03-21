package nnsp.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nnsp.vo.NcConfig;
import nnsp.vo.NcPolicy;
import nnsp.common.Config;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcMenuService;
import nnsp.services.NcPolicyService;
import nnsp.services.NcTransferService;
import nnsp.services.NcWhService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NcPolicyController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcPolicyController.class);
	
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcPolicyService ncPolicyService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcTransferService ncTransferService;
	@Autowired
	NcWhService ncWhService;
	/**
	 * 보안정책 송신 IP 주소 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_ipaddr.do", method = RequestMethod.GET)
    public String send_ipaddr(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		model.addAttribute("ipAddrToRegit", new NcPolicy());
		return "policy/send_ipaddr";
    }
	
	/**
	 * 보안정책 IP 주소 등록
	 * @param ipAddrToRegit
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ipaddr_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String ipaddr_regit(NcPolicy ipAddrToRegit, HttpSession session, Model model){
		String gcode = ncPolicyService.getIpGcode(ipAddrToRegit.getNoi_obj_nm(), ipAddrToRegit.getNoi_div());
		if(gcode!=null){ // 동일한 오브젝트 등록 불가
			return "dupli";
		}
		
		ipAddrToRegit.setNoi_gcode(ncPolicyService.nextIpGcode(ipAddrToRegit.getNoi_div()));
		int result = ncPolicyService.ipaddr_insert(ipAddrToRegit);
		
		String ip_type="";
		if(ipAddrToRegit.getNoi_ip_type()==1){
			ip_type="단일";
		}else if(ipAddrToRegit.getNoi_ip_type()==3){
			ip_type="다중";
		}else if(ipAddrToRegit.getNoi_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}

		String audit_page = "";
		if(ipAddrToRegit.getNoi_div()==1){
			audit_page = "내부 접속 허용 IP 등록";
		}else{
			audit_page = "외부 접속 허용 IP 등록";
		}
		
		String audit_param = "접속허용IP명 = "+ipAddrToRegit.getNoi_obj_nm()+",IP타입 = "+ip_type+",IP주소 = "+ipAddrToRegit.getNoi_ip();
		
		if (result == 1) {
			if(ipAddrToRegit.getNoi_div()==2) ncTransferService.trans_obj_ip("I", ipAddrToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 보안정책 IP 주소 수정화면 호출
	 * @param noi_seq
	 * @return
	 */
	@RequestMapping(value = "/ipaddr_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String ipaddr_editVw(@RequestParam("noi_seq") int noi_seq) {
		NcPolicy policy = ncPolicyService.getIpaddrBySeq(noi_seq);
				
		String strReturn = "<div class='form-group'><label class='control-label col-md-3'>접속 허용 IP명</label>"+
		"<input type='hidden' id='org_obj_nm' name='org_obj_nm' value='"+policy.getNoi_obj_nm()+"' /><input type='hidden' id='noi_seq' name='noi_seq' value='"+policy.getNoi_seq()+"' />"+
		"<input type='hidden' id='noi_gcode' name='noi_gcode' value='"+policy.getNoi_gcode()+"' />"+
		"<div class='col-md-7'><input type='text' id='noi_obj_nm' name='noi_obj_nm' class='form-control col-md-7 col-xs-12' value='"+policy.getNoi_obj_nm()+"' /></div></div>"+
		"<div class='form-group'><label class='control-label col-md-3'>IP 주소</label>"+
		//"<th><select id='noi_ip_type' name='noi_ip_type' style='width:150px;' onChange='change_type(this.value);'>"+
		//"<option value='1' "+ (policy.getNoi_ip_type()==1?"selected":"") +">단일</option>"+
		//"<option value='3' "+ (policy.getNoi_ip_type()==3?"selected":"") +">다중</option>"+
		//"<option value='5' "+ (policy.getNoi_ip_type()==5?"selected":"") +">범위</option>"+
		//"<option value='7' "+ (policy.getNoi_ip_type()==7?"selected":"") +">CIDR</option>"+
		//"</select></th>" +
		"<div class='col-md-7'>";
			
		if(policy.getNoi_ip_type()==1){
			strReturn += "<input type='text' id='ip_1' name='ip_1' class='form-control col-md-7 col-xs-12' value='"+policy.getNoi_ip()+"' maxlength='15' />";
		}else if(policy.getNoi_ip_type()==3){
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:95%;' value='"+policy.getNoi_ip()+"' />";
		}else if(policy.getNoi_ip_type()==5){
			String temp[] = policy.getNoi_ip().split("-");
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:45%;' value='"+temp[0]+"' /> - <input type='text' id='ip_2' name='ip_2' style='width:45%;' value='"+temp[1]+"' />";
		}else if(policy.getNoi_ip_type()==7){
			String temp[] = policy.getNoi_ip().split("/");
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:80%;' value='"+temp[0]+"' /> / <input type='text' id='ip_2' name='ip_2' style='width:15%;' value='"+temp[1]+"' />";
		}
		
		strReturn += "</div></div><div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'>";
		
		if(policy.getNoi_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('send_ipaddr.do');\">취소</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('receive_ipaddr.do');\">취소</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_ipaddr();'>저장</button></div>";
		
		return strReturn;
	}
	
	/**
	 * 보안정책 IP 주소 수정
	 * @param ipAddrToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ipaddr_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String ipaddr_update(NcPolicy ipAddrToRegit, Model model, HttpSession session){
		if(!ipAddrToRegit.getNoi_obj_nm().equals(ipAddrToRegit.getOrg_obj_nm())){
			String gcode = ncPolicyService.getIpGcode(ipAddrToRegit.getNoi_obj_nm(), ipAddrToRegit.getNoi_div());
			if(gcode!=null){
				return "dupli";
			}
		}
		
		int result = ncPolicyService.ipaddr_update(ipAddrToRegit);
		
		String ip_type="";
		if(ipAddrToRegit.getNoi_ip_type()==1){
			ip_type="단일";
		}else if(ipAddrToRegit.getNoi_ip_type()==3){
			ip_type="다중";
		}else if(ipAddrToRegit.getNoi_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}

		String audit_page = "";
		if(ipAddrToRegit.getNoi_div()==1){
			audit_page = "내부 접속 허용 IP 수정";
		}else{
			audit_page = "외부 접속 허용 IP 수정";
		}
		
		String audit_param = "접속허용IP명 = "+ipAddrToRegit.getNoi_obj_nm()+",IP타입 = "+ip_type+",IP주소 = "+ipAddrToRegit.getNoi_ip();
		
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(ipAddrToRegit.getNoi_div()); // 정책 파일 만들기
			ArrayList<NcPolicy> policyList = ncPolicyService.useIpPolicy(ipAddrToRegit); // IP 주소를 사용 중인 서비스 정책 번호 조회
			for(int i=0; i < policyList.size(); i++){
				ncPolicyService.create_configFile(policyList.get(i).getNps_seq()); // 환경 설정 파일 만들기
			}
			if(ipAddrToRegit.getNoi_div()==2) ncTransferService.trans_obj_ip("U", ipAddrToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 보안정책 IP 주소 삭제
	 * @param noi_seq
	 * @return
	 */
	@RequestMapping(value = "/ipaddr_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String ipaddr_delete(@RequestParam("noi_seq") int noi_seq, HttpSession session) {
		int cnt = ncPolicyService.ipaddr_use_count(noi_seq);
		if(cnt>0){
			return "used";
		}
		
		NcPolicy policy = ncPolicyService.getIpaddrBySeq(noi_seq);
		
		String ip_type="";
		if(policy.getNoi_ip_type()==1){
			ip_type="단일";
		}else if(policy.getNoi_ip_type()==3){
			ip_type="다중";
		}else if(policy.getNoi_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}
		
		String audit_page = "";
		if(policy.getNoi_div()==1){
			audit_page = "내부 접속 허용 IP 삭제";
		}else{
			audit_page = "외부 접속 허용 IP 삭제";
		}
		
		String audit_param = "접속허용IP명 = "+policy.getNoi_obj_nm()+",IP타입 = "+ip_type+",IP주소 = "+policy.getNoi_ip();
		
		int result = ncPolicyService.ipaddr_delete(noi_seq);
		if (result == 1) {
			if(policy.getNoi_div()==2) ncTransferService.trans_obj_ip("D", policy, noi_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 IP 주소 일괄삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/ipaddr_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String ipaddr_delall(HttpServletRequest req, HttpSession session) {
		String noi_seq[] = req.getParameterValues("sel[]");
		
		for(int i=0; i<noi_seq.length; i++){
			int cnt = ncPolicyService.ipaddr_use_count(Integer.parseInt(noi_seq[i]));
			if(cnt>0){
				NcPolicy policy = ncPolicyService.getIpaddrBySeq(Integer.parseInt(noi_seq[i]));
				return "used|접속허용IP명="+policy.getNoi_obj_nm()+", IP="+policy.getNoi_ip();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		for(int i=0; i<noi_seq.length; i++){
			NcPolicy policy = ncPolicyService.getIpaddrBySeq(Integer.parseInt(noi_seq[i]));
			
			String ip_type="";
			if(policy.getNoi_ip_type()==1){
				ip_type="단일";
			}else if(policy.getNoi_ip_type()==3){
				ip_type="다중";
			}else if(policy.getNoi_ip_type()==5){
				ip_type="범위";
			}else{
				ip_type="CIDR";
			}
			
			if(policy.getNoi_div()==1){
				audit_page = "내부 접속 허용 IP 삭제";
			}else{
				audit_page = "외부 접속 허용 IP 삭제";
			}
			
			audit_param = "접속허용IP명 = "+policy.getNoi_obj_nm()+",IP타입 = "+ip_type+",IP주소 = "+policy.getNoi_ip();
			
			result = ncPolicyService.ipaddr_delete(Integer.parseInt(noi_seq[i]));
			if (result == 1) {
				if(policy.getNoi_div()==2) ncTransferService.trans_obj_ip("D", policy, Integer.parseInt(noi_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
				break; // 실패 시 더이상 진행없이 for 문 빠져 나오기
			}
		}
		
		if (result == 1) {
			return "true";
		}else{
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 송신 서비스 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_service.do", method = RequestMethod.GET)
    public String send_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(1); // 1:송신 2:수신
		model.addAttribute("serviceList", serviceList);
		
		model.addAttribute("serviceToRegit", new NcPolicy());
		return "policy/send_service";
    }
	
	/**
	 * 보안정책 서비스 등록
	 * @param serviceToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/service_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String service_regit(NcPolicy serviceToRegit, Model model, HttpSession session){
		String gcode = ncPolicyService.getSvcGcode(serviceToRegit.getNos_obj_nm(), serviceToRegit.getNos_div());
		if(gcode!=null){ // 동일한 오브젝트명 등록 안됨
			return "dupli";
		}
		
		serviceToRegit.setNos_gcode(ncPolicyService.nextSvcGcode(serviceToRegit.getNos_div()));
		
		String audit_page = "";
		if(serviceToRegit.getNos_div()==1){
			audit_page = "내부 접속 허용 서비스 등록";
		}else{
			audit_page = "외부 접속 허용 서비스 등록";
		}
		
		String audit_param = "서비스명 = "+serviceToRegit.getNos_obj_nm()+",포트번호 = "+serviceToRegit.getNos_port();
		
		int result = ncPolicyService.service_insert(serviceToRegit);
		if (result == 1) {
			if(serviceToRegit.getNos_div()==2) ncTransferService.trans_obj_service("I", serviceToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
		
	/**
	 * 보안정책 서비스 수정화면 호출
	 * @param nos_seq
	 * @return
	 */
	@RequestMapping(value = "/service_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String service_editVw(@RequestParam("nos_seq") int nos_seq) {
		NcPolicy policy = ncPolicyService.getServiceBySeq(nos_seq);
				
		String strReturn = "<div class='form-group'><label class='control-label col-md-3'>서비스명</label>"+
		"<input type='hidden' id='org_obj_nm' name='org_obj_nm' value='"+policy.getNos_obj_nm()+"' /><input type='hidden' id='nos_seq' name='nos_seq' value='"+policy.getNos_seq()+"' />"+
		"<input type='hidden' id='nos_gcode' name='nos_gcode' value='"+policy.getNos_gcode()+"' />"+
		"<div class='col-md-7'><input type='text' id='nos_obj_nm' name='nos_obj_nm' class='form-control col-md-7 col-xs-12' value='"+policy.getNos_obj_nm()+"' /></div></div>"+
		"<div class='form-group'><label class='control-label col-md-3'>포트번호</label>"+
		"<div class='col-md-7'><input type='text' id='nos_port' name='nos_port' class='form-control col-md-7 col-xs-12' value='"+policy.getNos_port()+"' /></div>"+
		"</div><div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'>";
		
		if(policy.getNos_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('send_service.do');\">취소</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('receive_service.do');\">취소</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_service();'>저장</button></div>";
		
		return strReturn;
	}
	
	/**
	 * 보안정책 서비스 수정
	 * @param serviceToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/service_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String service_update(NcPolicy serviceToRegit, Model model, HttpSession session){
		if(!serviceToRegit.getNos_obj_nm().equals(serviceToRegit.getOrg_obj_nm())){
			String gcode = ncPolicyService.getSvcGcode(serviceToRegit.getNos_obj_nm(), serviceToRegit.getNos_div());
			if(gcode!=null){
				return "dupli";
			}
		}
		
		String audit_page = "";
		if(serviceToRegit.getNos_div()==1){
			audit_page = "내부 접속 허용 서비스 수정";
		}else{
			audit_page = "외부 접속 허용 서비스 수정";
		}
		
		String audit_param = "서비스명 = "+serviceToRegit.getNos_obj_nm()+",포트번호 = "+serviceToRegit.getNos_port();
		
		int result = ncPolicyService.service_update(serviceToRegit);
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(serviceToRegit.getNos_div()); // 정책 파일 만들기
			ArrayList<NcPolicy> policyList = ncPolicyService.useServicePolicy(serviceToRegit); // 서비스를 사용 중인 서비스 정책 번호
			for(int i=0; i < policyList.size(); i++){
				ncPolicyService.create_configFile(policyList.get(i).getNps_seq()); // 환경 설정 파일 만들기
			}
			if(serviceToRegit.getNos_div()==2) ncTransferService.trans_obj_service("U", serviceToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 보안정책 서비스 삭제
	 * @param nos_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/service_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String service_delete(@RequestParam("nos_seq") int nos_seq, HttpSession session) {
		int cnt = ncPolicyService.service_use_count(nos_seq);
		if(cnt>0){
			return "used";
		}
		cnt = ncPolicyService.service_link_count(nos_seq);
		if(cnt>0){
			return "link_used";
		}
		
		NcPolicy policy = ncPolicyService.getServiceBySeq(nos_seq);
		
		String audit_page = "";
		if(policy.getNos_div()==1){
			audit_page = "내부 접속 허용 서비스 삭제";
		}else{
			audit_page = "외부 접속 허용 서비스 삭제";
		}
		
		String audit_param = "서비스명 = "+policy.getNos_obj_nm()+",포트번호 = "+policy.getNos_port();
		
		int result = ncPolicyService.service_delete(nos_seq);
		if (result == 1) {
			if(policy.getNos_div()==2) ncTransferService.trans_obj_service("D", policy, nos_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 서비스 일괄삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/service_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String service_delall(HttpServletRequest req, HttpSession session) {
		String nos_seq[] = req.getParameterValues("sel[]");
		
		for(int i=0; i<nos_seq.length; i++){
			int cnt = ncPolicyService.service_use_count(Integer.parseInt(nos_seq[i]));
			if(cnt>0){
				NcPolicy policy = ncPolicyService.getServiceBySeq(Integer.parseInt(nos_seq[i]));
				return "used|서비스명="+policy.getNos_obj_nm()+", 포트번호="+policy.getNos_port();
			}
			cnt = ncPolicyService.service_link_count(Integer.parseInt(nos_seq[i]));
			if(cnt>0){
				NcPolicy policy = ncPolicyService.getServiceBySeq(Integer.parseInt(nos_seq[i]));
				return "link_used|서비스명="+policy.getNos_obj_nm()+", 포트번호="+policy.getNos_port();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		for(int i=0; i<nos_seq.length; i++){
			NcPolicy policy = ncPolicyService.getServiceBySeq(Integer.parseInt(nos_seq[i]));
			
			if(policy.getNos_div()==1){
				audit_page = "내부 접속 허용 서비스 삭제";
			}else{
				audit_page = "외부 접속 허용 서비스 삭제";
			}
			
			audit_param = "서비스명 = "+policy.getNos_obj_nm()+",포트번호 = "+policy.getNos_port();
			
			result = ncPolicyService.service_delete(Integer.parseInt(nos_seq[i]));
			if (result == 1) {
				if(policy.getNos_div()==2) ncTransferService.trans_obj_service("D", policy, Integer.parseInt(nos_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
				break; // 실패 시 더이상 진행없이 for 문 빠져 나오기
			}
		}
		
		if (result == 1) {
			return "true";
		}else{
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 송신 접속허용정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_policy_allow.do", method = RequestMethod.GET)
    public String send_policy_allow(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> policyList = ncPolicyService.getPolicyAllow(1);
		model.addAttribute("policyList", policyList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
		model.addAttribute("protList", protList);
		
		model.addAttribute("policyToRegit", new NcPolicy());
		return "policy/send_policy_allow";
    }
	
	/**
	 * 보안정책 접속허용정책 등록
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_allow_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_allow_regit(NcPolicy policyToRegit, Model model, HttpSession session){
		int cnt = ncPolicyService.policy_allow_count(policyToRegit);
		if(cnt>0){
			return "dupli";
		}
		
		String audit_page = "";
		if(policyToRegit.getNpa_div()==1){
			audit_page = "내부 접속 허용 정책 등록";
		}else{
			audit_page = "외부 접속 허용 정책 등록";
		}
		
		String audit_param = "";
		if(policyToRegit.getNpa_div()==1){
			audit_param = "접속 허용 IP명 = "+ncPolicyService.getIpObjNm(policyToRegit.getNpa_sip_gcode(),policyToRegit.getNpa_div())+",내부 전송통제 수신 IP명 = "+ncPolicyService.getIpObjNm(policyToRegit.getNpa_dip_gcode(),policyToRegit.getNpa_div())+
				",서비스명 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNpa_dsvc_gcode(),policyToRegit.getNpa_div())+",프로토콜 = "+ncPolicyService.getProtNm(policyToRegit.getNpc_no());
		}else{
			audit_param = "외부 전송통제 송신 IP명 = "+ncPolicyService.getIpObjNm(policyToRegit.getNpa_sip_gcode(),policyToRegit.getNpa_div())+",접속 허용 IP명 = "+ncPolicyService.getIpObjNm(policyToRegit.getNpa_dip_gcode(),policyToRegit.getNpa_div())+
				",서비스명 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNpa_dsvc_gcode(),policyToRegit.getNpa_div())+",프로토콜 = "+ncPolicyService.getProtNm(policyToRegit.getNpc_no());
		}
		
		int result = ncPolicyService.policy_allow_insert(policyToRegit);
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(policyToRegit.getNpa_div()); // 정책 파일 만들기
			if(policyToRegit.getNpa_div()==2) ncTransferService.trans_policy_allow("I", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 접속허용정책 수정화면 호출
	 * @param npa_seq
	 * @return
	 */
	@RequestMapping(value = "/policy_allow_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_allow_editVw(@RequestParam("npa_seq") int npa_seq) {
		NcPolicy policy = ncPolicyService.getPolicyAllowBySeq(npa_seq);
		String temp="";
		
		String strReturn = "";
		
		if(policy.getNpa_div()==1){
			strReturn += "<div class='form-group'><label class='control-label col-md-3'>접속 허용 IP명</label>";
		}else{
			strReturn += "<div class='form-group'><label class='control-label col-md-3'>외부 전송통제 송신 IP명</label>";
		}
		
		strReturn += "<div class='col-md-7'><input type='hidden' id='npa_seq' name='npa_seq' value='"+policy.getNpa_seq()+"' /><select name='npa_sip_gcode' class='form-control col-md-7 col-xs-12'>";
	
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(policy.getNpa_div()); // 1:송신 2:수신
		for(int i=0; i < ipAddrList.size(); i++){
			temp="";
			if(policy.getNpa_sip_gcode()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
			strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
		}
		
		strReturn += "</select></div></div><div class='form-group'>";
		
		if(policy.getNpa_div()==1){
			strReturn += "<label class='control-label col-md-3'>내부 전송통제 수신 IP명</label>";
		}else{
			strReturn += "<label class='control-label col-md-3'>접속 허용 IP명</label>";
		}
		
		strReturn += "<div class='col-md-7'><select name='npa_dip_gcode' class='form-control col-md-7 col-xs-12'>";

		for(int i=0; i < ipAddrList.size(); i++){
			temp="";
			if(policy.getNpa_dip_gcode()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
			strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
		}
		
		strReturn += "</select></div></div>";
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(policy.getNpa_div()); // 1:송신 2:수신
		
		/*<th>출발지 서비스</th><th><select name='npa_ssvc_gcode' style='width:150px;'><option value='0' selected>모든 서비스</option>";
		
		for(int i=0; i < serviceList.size(); i++){
			temp="";
			if(policy.getNpa_ssvc_gcode()==serviceList.get(i).getNos_gcode()) temp = "selected";
			strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
		}
		
		strReturn += "</select></th>";*/
		
		strReturn += "<div class='form-group'><label class='control-label col-md-3'>서비스명</label><div class='col-md-7'><select name='npa_dsvc_gcode' class='form-control col-md-7 col-xs-12'>";

		for(int i=0; i < serviceList.size(); i++){
			temp="";
			if(policy.getNpa_dsvc_gcode()==serviceList.get(i).getNos_gcode()) temp = "selected";
			strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
		}
		
		strReturn += "</select></div></div><div class='form-group'><label class='control-label col-md-3'>프로토콜</label><div class='col-md-7'><select name='npc_no' class='form-control col-md-7 col-xs-12'>";

		ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
		for(int i=0; i < protList.size(); i++){
			temp="";
			if(policy.getNpc_no()==protList.get(i).getNpc_no()) temp = "selected";
			strReturn += "<option value='"+protList.get(i).getNpc_no()+"' "+temp+">"+protList.get(i).getNpc_name()+"</option>";
		}
		
		strReturn += "</select></div></div><div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'>";
		
		if(policy.getNpa_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('send_policy_allow.do');\">취소</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('receive_policy_allow.do');\">취소</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_policy();'>저장</button></div>";
		
		return strReturn;
	}
	
	/**
	 * 보안정책 접속허용정책 수정
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_allow_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_allow_update(NcPolicy policyToRegit, Model model, HttpSession session){
		int cnt = ncPolicyService.policy_allow_count(policyToRegit);
		if(cnt>0){
			return "dupli";
		}
		
		NcPolicy policy = ncPolicyService.getPolicyAllowBySeq(policyToRegit.getNpa_seq());
		
		String audit_page = "";
		if(policyToRegit.getNpa_div()==1){
			audit_page = "내부 접속 허용 정책 수정";
		}else{
			audit_page = "외부 접속 허용 정책 수정";
		}
		
		String audit_param = "";
		if(policy.getNpa_div()==1){
			audit_param = "접속 허용 IP명 = "+policy.getSip_objnm()+",내부 전송통제 수신 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
		}else{
			audit_param = "외부 전송통제 송신 IP명 = "+policy.getSip_objnm()+",접속 허용 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
		}
		
		int result = ncPolicyService.policy_allow_update(policyToRegit);
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(policyToRegit.getNpa_div()); // 정책 파일 만들기
			if(policyToRegit.getNpa_div()==2) ncTransferService.trans_policy_allow("U", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 접속허용정책 삭제
	 * @param npa_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_allow_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_allow_delete(@RequestParam("npa_seq") int npa_seq, HttpSession session) {
		NcPolicy policy = ncPolicyService.getPolicyAllowBySeq(npa_seq);
	
		String audit_page = "";
		if(policy.getNpa_div()==1){
			audit_page = "내부 접속 허용 정책 삭제";
		}else{
			audit_page = "외부 접속 허용 정책 삭제";
		}
		
		String audit_param = "";
		if(policy.getNpa_div()==1){
			audit_param = "접속 허용 IP명 = "+policy.getSip_objnm()+",내부 전송통제 수신 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
		}else{
			audit_param = "외부 전송통제 송신 IP명 = "+policy.getSip_objnm()+",접속 허용 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
		}
		
		int result = ncPolicyService.policy_allow_delete(npa_seq);
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(policy.getNpa_div()); // 정책 파일 만들기
			if(policy.getNpa_div()==2) ncTransferService.trans_policy_allow("D", policy, npa_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 수신 IP 주소 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_ipaddr.do", method = RequestMethod.GET)
    public String receive_ipaddr(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		model.addAttribute("ipAddrToRegit", new NcPolicy());
		return "policy/receive_ipaddr";
    }
	
	/**
	 * 보안정책 수신 서비스 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_service.do", method = RequestMethod.GET)
    public String receive_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(2); // 1:송신 2:수신
		model.addAttribute("serviceList", serviceList);
		
		model.addAttribute("serviceToRegit", new NcPolicy());
		return "policy/receive_service";
    }
	
	/**
	 * 보안정책 수신 접속허용정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_policy_allow.do", method = RequestMethod.GET)
    public String receive_policy_allow(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> policyList = ncPolicyService.getPolicyAllow(2);
		model.addAttribute("policyList", policyList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
		model.addAttribute("protList", protList);
		
		model.addAttribute("policyToRegit", new NcPolicy());
		return "policy/receive_policy_allow";
    }
	
	/**
	 * 접속 허용 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_allow_delall.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_allow_delall(HttpServletRequest req, HttpSession session) {
		String npa_seq[] = req.getParameterValues("sel[]");
		
		int result = 0;
		int nap_div = 0;
		String audit_page = "";
		String audit_param = "";
		for(int i=0; i<npa_seq.length; i++){
			NcPolicy policy = ncPolicyService.getPolicyAllowBySeq(Integer.parseInt(npa_seq[i]));
			
			nap_div = policy.getNpa_div();
			
			if(policy.getNpa_div()==1){
				audit_page = "내부 접속 허용 정책 삭제";
			}else{
				audit_page = "외부 접속 허용 정책 삭제";
			}
			
			if(policy.getNpa_div()==1){
				audit_param = "접속 허용 IP명 = "+policy.getSip_objnm()+",내부 전송통제 수신 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
			}else{
				audit_param = "외부 전송통제 송신 IP명 = "+policy.getSip_objnm()+",접속 허용 IP명 = "+policy.getDip_objnm()+",서비스명 = "+policy.getDsvc_objnm()+",프로토콜 = "+policy.getNpc_name();
			}
			
			result = ncPolicyService.policy_allow_delete(Integer.parseInt(npa_seq[i]));
			if (result == 1) {
				if(policy.getNpa_div()==2) ncTransferService.trans_policy_allow("D", policy, Integer.parseInt(npa_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
				break; // 실패 시 더이상 진행없이 for 문 빠져 나오기
			}
		}
		
		if (result == 1) {
			ncPolicyService.create_aclRuleFile(nap_div); // 정책 파일 만들기
			return "true";
		}else{
			return "DB Error";
		}
	}
	
	/**
	 * 송신 서비스 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_policy_service.do", method = RequestMethod.GET)
    public String send_policy_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> policyList = ncPolicyService.getPolicyService(1);
		model.addAttribute("policyList", policyList);
		
		return "policy/send_policy_service";
    }
	
	/**
	 * 송신 서비스 정책 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_policy_service_regit.do", method = RequestMethod.GET)
	public String send_policy_service_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(200009));
		model.addAttribute("title", ncMenuService.getNowTitle(200009));
		model.addAttribute("menu_id", 200009);
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(protList.get(0).getNpc_no(), protList.get(0).getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(1);
		model.addAttribute("programList", programList);
		
		model.addAttribute("policyToRegit", new NcPolicy());
		return "policy/send_policy_service_regit";			
	}
	
	/**
	 * 송신 서비스 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_policy_service_editVw.do", method = RequestMethod.GET)
	public String send_policy_service_editVw(@RequestParam("nps_seq") int nps_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(200009));
		model.addAttribute("title", ncMenuService.getNowTitle(200009));
		model.addAttribute("menu_id", 200009);
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		NcPolicy ncPolicy = ncPolicyService.getPolicyServiceBySeq(nps_seq);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(ncPolicy.getNpc_no(), ncPolicy.getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(1);
		model.addAttribute("programList", programList);
		
		model.addAttribute("policyToRegit", ncPolicy);
		return "policy/send_policy_service_edit";			
	}
	
	/**
	 * 콘텐츠 타입 변경
	 * @param npc_no
	 * @param npcs_type
	 * @return
	 */
	@RequestMapping(value = "/change_prot.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_prot(@RequestParam("npc_no") int npc_no, @RequestParam("npcs_type") String npcs_type) {
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(npc_no, npcs_type);
		
		String strReturn = "<select name='npcs_seq' id='npcs_seq' style='width:97%;'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNpcs_seq()+"'>"+contsList.get(i).getNpcs_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 서비스 정책 등록
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_service_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_service_regit(NcPolicy policyToRegit, Model model, HttpSession session){
		int cnt = ncPolicyService.policy_program_count(policyToRegit); // 동일 프로그램으로 여러 정책을 설정 할수 없음. 추후 프로그램에 여러 정책을 설정할 수 있도록 함
		if(cnt>0){
			return "dupli";
		}
		
		cnt = ncPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli";
		}
		
		NcConfig programList = ncConfigService.serviceProgramBySeq(policyToRegit.getNcp_seq());
		
		String audit_param = "";
		String audit_page = "";
		if(policyToRegit.getNcp_div()==1){
			audit_page = "내부 전송 통제 정책 등록";
			audit_param = "프로그램명 = "+programList.getNcp_name()+",내부 전송통제 수신IP = "+ncPolicyService.getIpObjNm(policyToRegit.getNps_tx_intip(),policyToRegit.getNcp_div())+",내부 전송통제 수신포트 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNps_tx_intport(),policyToRegit.getNcp_div())+
					",외부 전송통제 수신IP = "+ncPolicyService.getIpObjNm(policyToRegit.getNps_rx_intip(),policyToRegit.getNcp_div())+",내부 전송통제 수신포트 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNps_rx_intport(),policyToRegit.getNcp_div())+
					",ELC서버 상태체크 = "+(policyToRegit.getNps_elc_flag()==1?"사용함":"사용안함")+",프로토콜 = "+ncPolicyService.getProtNm(policyToRegit.getNpc_no());
			policyToRegit.setNps_conf_file(Config.getInstance().getSndPath()+programList.getNcp_file_name()+"_conf");
		}else{
			audit_page = "외부 전송 통제 정책 등록";
			audit_param = "프로그램명 = "+programList.getNcp_name()+",외부 전송통제 수신IP = "+ncPolicyService.getIpObjNm(policyToRegit.getNps_rx_intip(),policyToRegit.getNcp_div())+",외부 전송통제 수신포트 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNps_rx_intport(),policyToRegit.getNcp_div())+
					",외부 연계 수신IP = "+ncPolicyService.getIpObjNm(policyToRegit.getNps_conts_ip(),policyToRegit.getNcp_div())+",외부 연계 수신포트 = "+ncPolicyService.getSvcObjNm(policyToRegit.getNps_conts_port(),policyToRegit.getNcp_div())+
					",프로토콜 = "+ncPolicyService.getProtNm(policyToRegit.getNpc_no());
			policyToRegit.setNps_conf_file(Config.getInstance().getRcvPath()+programList.getNcp_file_name()+"_conf");
		}
		
		int result = ncPolicyService.policy_service_insert(policyToRegit);
		if (result == 1) {
			int service_seq = ncPolicyService.last_service_seq();
			ncPolicyService.create_flist(policyToRegit.getNcp_div()); // flist 만들기
			try {
				Thread.sleep(1000); // 1초
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ncPolicyService.create_configFile(service_seq); // 환경설정 파일 만들기
			if(policyToRegit.getNcp_div()==2) ncTransferService.trans_policy_service("I", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 서비스 정책 수정화면 호출
	 * @param nps_seq
	 * @return
	 */
	@RequestMapping(value = "/policy_service_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_service_editVw(@RequestParam("nps_seq") int nps_seq) {
		NcPolicy policy = ncPolicyService.getPolicyServiceBySeq(nps_seq);
		String temp="";
		
		String strReturn = "";
		
		if(policy.getNcp_div()==1){ // 송신
			
			strReturn += "<tr><th>프로토콜</th><th><select name='npc_no' style='width:97%;' onChange='change_prot(this.value, this.options[this.selectedIndex].text);'>";
		
			ArrayList<NcPolicy> protList = ncPolicyService.getContsType();
			for(int i=0; i < protList.size(); i++){
				temp="";
				if(policy.getNpcs_type().equals(protList.get(i).getNpcs_type())) temp = "selected";
				strReturn += "<option value='"+protList.get(i).getNpc_no()+"' "+temp+">"+protList.get(i).getNpcs_type()+"</option>";
			}
			
			strReturn += "</select></th><th>콘텐츠 타입</th><th id='conts_area'><select name='npcs_seq' style='width:97%;'>";
			
			ArrayList<NcPolicy> contsList = ncPolicyService.getContents(policy.getNpc_no(), policy.getNpcs_type());
			for(int i=0; i < contsList.size(); i++){
				temp="";
				if(policy.getNpcs_seq()==contsList.get(i).getNpcs_seq()) temp = "selected";
				strReturn += "<option value='"+contsList.get(i).getNpcs_seq()+"' "+temp+">"+contsList.get(i).getNpcs_name()+"</option>";
			}
			
			strReturn += "</select></th><th>프로그램명</th><th><input type='hidden' id='nps_seq' name='nps_seq' value='"+policy.getNps_seq()+"' /><select name='ncp_seq' style='width:97%;'>";
		
			ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(policy.getNcp_div());
			for(int i=0; i<programList.size(); i++){
				temp="";
				if(policy.getNcp_seq()==programList.get(i).getNcp_seq()) temp = "selected";
				strReturn += "<option value='"+programList.get(i).getNcp_seq()+"' "+temp+">"+programList.get(i).getNcp_name()+"</option>";
			}
			
			strReturn += "</select></th><th>송신 내부 IP</th><th><select name='nps_tx_intip' style='width:97%;'>";
			
			ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(policy.getNcp_div()); // 1:송신 2:수신
			for(int i=0; i<ipAddrList.size(); i++){
				temp="";
				if(policy.getNps_tx_intip()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
				strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th rowspan='2'><a href='javascript:update_policy();' class='btn_st04'><span>저장</span></a> <a href=\"javascript:location.replace('send_policy_service.do');\" class='btn_st04'><span>취소</span></a></th></tr>" +
					"<tr><th>송신 내부 서비스</th><th><select name='nps_tx_intport' style='width:97%;'>";
			
			ArrayList<NcPolicy> serviceList = ncPolicyService.getService(policy.getNcp_div()); // 1:송신 2:수신
			for(int i=0; i<serviceList.size(); i++){
				temp="";
				if(policy.getNps_tx_intport()==serviceList.get(i).getNos_gcode()) temp = "selected";
				strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>수신 내부 IP</th><th><select name='nps_rx_intip' style='width:97%;'>";
			
			for(int i=0; i<ipAddrList.size(); i++){
				temp="";
				if(policy.getNps_rx_intip()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
				strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>수신 내부 서비스</th><th><select name='nps_rx_intport' style='width:97%;'>";
			
			for(int i=0; i<serviceList.size(); i++){
				temp="";
				if(policy.getNps_rx_intport()==serviceList.get(i).getNos_gcode()) temp = "selected";
				strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>ELC 서버 상태</th><th><select name='nps_elc_flag' style='width:97%;'><option value='1' selected>체크</option><option value='0' "+(policy.getNps_elc_flag()==0?"selected":"")+">체크안함</option></select></th></tr>";
		
		}else{ // 수신
			
			strReturn += "<tr><th>프로그램명</th><th><input type='hidden' id='nps_seq' name='nps_seq' value='"+policy.getNps_seq()+"' /><select name='ncp_seq' style='width:97%;'>";
			
			ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(policy.getNcp_div());
			for(int i=0; i<programList.size(); i++){
				temp="";
				if(policy.getNcp_seq()==programList.get(i).getNcp_seq()) temp = "selected";
				strReturn += "<option value='"+programList.get(i).getNcp_seq()+"' "+temp+">"+programList.get(i).getNcp_name()+"</option>";
			}
			
			strReturn += "</select></th><th>수신 내부 IP</th><th><select name='nps_rx_intip' style='width:97%;'>";
			
			ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(policy.getNcp_div()); // 1:송신 2:수신
			for(int i=0; i<ipAddrList.size(); i++){
				temp="";
				if(policy.getNps_rx_intip()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
				strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+ ">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>수신 내부 서비스</th><th><select name='nps_rx_intport' style='width:97%;'>";
			
			ArrayList<NcPolicy> serviceList = ncPolicyService.getService(policy.getNcp_div()); // 1:송신 2:수신
			for(int i=0; i<serviceList.size(); i++){
				temp="";
				if(policy.getNps_rx_intport()==serviceList.get(i).getNos_gcode()) temp = "selected";
				strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th rowspan='2'><a href='javascript:update_policy();' class='btn_st04'><span>저장</span></a> "+
					"<a href=\"javascript:location.replace('receive_policy_service.do');\" class='btn_st04'><span>취소</span></a></th></tr>" +
					"<tr><th>콘텐츠 전송  IP</th><th><select name='nps_conts_ip' style='width:97%;'>";
			
			for(int i=0; i<ipAddrList.size(); i++){
				temp="";
				if(policy.getNps_conts_ip()==ipAddrList.get(i).getNoi_gcode()) temp = "selected";
				strReturn += "<option value='"+ipAddrList.get(i).getNoi_gcode()+"' "+temp+">"+ipAddrList.get(i).getNoi_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>콘텐츠 전송 서비스</th><th><select name='nps_conts_port' style='width:97%;'>";
			
			for(int i=0; i<serviceList.size(); i++){
				temp="";
				if(policy.getNps_conts_port()==serviceList.get(i).getNos_gcode()) temp = "selected";
				strReturn += "<option value='"+serviceList.get(i).getNos_gcode()+"' "+temp+">"+serviceList.get(i).getNos_obj_nm()+"</option>";
			}
			
			strReturn += "</select></th><th>프로토콜</th><th><select name='npc_no' style='width:97%;'>";
		
			ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
			for(int i=0; i < protList.size(); i++){
				if(protList.get(i).getNpc_no() != 0){
					temp="";
					if(policy.getNpc_no()==protList.get(i).getNpc_no()) temp = "selected";
					strReturn += "<option value='"+protList.get(i).getNpc_no()+"' "+temp+">"+protList.get(i).getNpc_name()+"</option>";
				}
			}
			
			strReturn += "</select></th></tr>";
		}
		
		return strReturn;
	}
	
	/**
	 * 서비스 정책 수정
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_service_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_service_update(NcPolicy policyToRegit, Model model, HttpSession session){
		int cnt = ncPolicyService.policy_program_count(policyToRegit); // 동일 프로그램으로 여러 정책을 설정 할수 없음. 추후 프로그램에 여러 정책을 설정할 수 있도록 함
		if(cnt>0){
			return "dupli";
		}
		
		cnt = ncPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli";
		}
		
		NcPolicy policy = ncPolicyService.getPolicyServiceBySeq(policyToRegit.getNps_seq());
		
		String audit_param = "";
		String audit_page = "";
		if(policyToRegit.getNcp_div()==1){
			audit_page = "내부 전송 통제 정책 수정";
			audit_param = "프로그램명 = "+policy.getNcp_name()+",내부 전송통제 수신IP = "+policy.getTx_intip_objnm()+",내부 전송통제 수신포트 = "+policy.getTx_intport_objnm()+",외부 전송통제 수신IP = "+policy.getRx_intip_objnm()+",외부 전송통제 수신IP = "+policy.getRx_intport_objnm()+
					",ELC서버 상태체크 = "+policy.getElc_flag_text()+",프로토콜 = "+policy.getNpc_name();
			policyToRegit.setNps_conf_file(Config.getInstance().getSndPath()+policy.getNcp_file_name()+"_conf");
		}else{
			audit_page = "외부 전송 통제 정책 수정";
			audit_param = "프로그램명 = "+policy.getNcp_name()+",외부 전송통제 수신IP = "+policy.getRx_intip_objnm()+",외부 전송통제 수신포트 = "+policy.getRx_intport_objnm()+",외부 연계 수신IP = "+policy.getConts_ip_objnm()+",외부 연계 수신포트 = "+policy.getConts_port_objnm()+
					",프로토콜 = "+policy.getNpc_name();
			policyToRegit.setNps_conf_file(Config.getInstance().getRcvPath()+policy.getNcp_file_name()+"_conf");
		}
			
		int result = ncPolicyService.policy_service_update(policyToRegit);
		if (result == 1) {
			ncPolicyService.create_configFile(policyToRegit.getNps_seq()); // 환경설정 파일 만들기
			if(policyToRegit.getNcp_div()==2) ncTransferService.trans_policy_service("U", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 서비스 정책 삭제
	 * @param nps_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_service_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_service_delete(@RequestParam("nps_seq") int nps_seq, HttpSession session) {
		NcPolicy policy = ncPolicyService.getPolicyServiceBySeq(nps_seq);
	
		String audit_param = "";
		String audit_page = "";
		if(policy.getNcp_div()==1){
			audit_page = "내부 전송 통제 정책 삭제";
			audit_param = "프로그램명 = "+policy.getNcp_name()+",내부 전송통제 수신IP = "+policy.getTx_intip_objnm()+",내부 전송통제 수신포트 = "+policy.getTx_intport_objnm()+",외부 전송통제 수신IP = "+policy.getRx_intip_objnm()+",외부 전송통제 수신IP = "+policy.getRx_intport_objnm()+
					",ELC서버 상태체크 = "+policy.getElc_flag_text()+",프로토콜 = "+policy.getNpc_name();
		}else{
			audit_page = "외부 전송 통제 정책 삭제";
			audit_param = "프로그램명 = "+policy.getNcp_name()+",외부 전송통제 수신IP = "+policy.getRx_intip_objnm()+",외부 전송통제 수신포트 = "+policy.getRx_intport_objnm()+",외부 연계 수신IP = "+policy.getConts_ip_objnm()+",외부 연계 수신포트 = "+policy.getConts_port_objnm()+
					",프로토콜 = "+policy.getNpc_name();
		}
		
		int result = ncPolicyService.policy_service_delete(nps_seq);
		if (result == 1) {
			ncPolicyService.create_flist(policy.getNcp_div()); // flist 만들기
			if(policy.getNcp_div()==2) ncTransferService.trans_policy_service("D", policy, nps_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 수신 서비스 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_policy_service.do", method = RequestMethod.GET)
    public String receive_policy_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> policyList = ncPolicyService.getPolicyService(2);
		model.addAttribute("policyList", policyList);
		
		return "policy/receive_policy_service";
    }
	
	/**
	 * 수신 서비스 정책 등록 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_policy_service_regit.do", method = RequestMethod.GET)
	public String receive_policy_service_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(200010));
		model.addAttribute("title", ncMenuService.getNowTitle(200010));
		model.addAttribute("menu_id", 200010);
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
		model.addAttribute("protList", protList);
		
		ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(2);
		model.addAttribute("programList", programList);
		
		model.addAttribute("policyToRegit", new NcPolicy());
		return "policy/receive_policy_service_regit";			
	}
	
	/**
	 * 수신 서비스 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_policy_service_editVw.do", method = RequestMethod.GET)
	public String receive_policy_service_editVw(@RequestParam("nps_seq") int nps_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(200010));
		model.addAttribute("title", ncMenuService.getNowTitle(200010));
		model.addAttribute("menu_id", 200010);
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> ipAddrList = ncPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcPolicy> serviceList = ncPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getProtocol();
		model.addAttribute("protList", protList);
		
		ArrayList<NcConfig> programList = ncConfigService.getServiceProgram(2);
		model.addAttribute("programList", programList);
		
		model.addAttribute("policyToRegit", ncPolicyService.getPolicyServiceBySeq(nps_seq));
		return "policy/receive_policy_service_edit";			
	}
	
	/**
	 * 서비스 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_service_delall.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_service_delall(HttpServletRequest req, HttpSession session) {
		String nps_seq[] = req.getParameterValues("sel[]");
		
		int result = 0;
		for(int i=0; i<nps_seq.length; i++){
			NcPolicy policy = ncPolicyService.getPolicyServiceBySeq(Integer.parseInt(nps_seq[i]));
			
			String audit_param = "";
			String audit_page = "";
			if(policy.getNcp_div()==1){
				audit_page = "내부 전송 통제 정책 삭제";
				audit_param = "프로그램명 = "+policy.getNcp_name()+",내부 전송통제 수신IP = "+policy.getTx_intip_objnm()+",내부 전송통제 수신포트 = "+policy.getTx_intport_objnm()+",수신 내부 IP = "+policy.getRx_intip_objnm()+",수신 내부 서비스 = "+policy.getRx_intport_objnm()+
						",ELC 서버 상태="+policy.getElc_flag_text()+",프로토콜="+policy.getNpc_name();
			}else{
				audit_page = "외부 전송 통제 정책 삭제";
				audit_param = "프로그램명 = "+policy.getNcp_name()+",외부 전송통제 수신IP = "+policy.getRx_intip_objnm()+",외부 전송통제 수신포트 = "+policy.getRx_intport_objnm()+",외부 연계 수신IP = "+policy.getConts_ip_objnm()+",외부 연계 수신포트 = "+policy.getConts_port_objnm()+
						",프로토콜 = "+policy.getNpc_name();
			}
			
			result = ncPolicyService.policy_service_delete(Integer.parseInt(nps_seq[i]));
			if (result == 1) {
				ncPolicyService.create_flist(policy.getNcp_div()); // flist 만들기
				if(policy.getNcp_div()==2) ncTransferService.trans_policy_service("D", policy, Integer.parseInt(nps_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
				break; // 실패 시 더이상 진행없이 for 문 빠져 나오기
			}
		}
		
		if (result == 1) {
			return "true";
		}else{
			return "DB Error";
		}
	}
}