package nnsp.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nnsp.vo.NcConfig;
import nnsp.vo.NcPolicy;
import nnsp.vo.NcLinkPolicy;

import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcLinkPolicyService;
import nnsp.services.NcMenuService;
import nnsp.services.NcPolicyService;
import nnsp.services.NcTransferService;
import nnsp.util.MessageUtil;

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
public class NcLinkPolicyController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcLinkPolicyController.class);
	
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcPolicyService ncPolicyService;
	@Autowired
	NcLinkPolicyService ncLinkPolicyService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcTransferService ncTransferService;
	
	/**
	 * 연계 내부 IP 주소 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_ipaddr.do", method = RequestMethod.GET)
    public String lnk_snd_ipaddr(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> ipAddrList = ncLinkPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		model.addAttribute("ipAddrToRegit", new NcLinkPolicy());
		return "link_policy/send_ipaddr";
    }
	
	/**
	 * 연계 보안정책 IP 주소 등록
	 * @param ipAddrToRegit
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/lnk_ipaddr_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String lnk_ipaddr_regit(NcLinkPolicy ipAddrToRegit, HttpSession session, Model model){
		String gcode = ncLinkPolicyService.getIpGcode(ipAddrToRegit.getNli_ip_nm(), ipAddrToRegit.getNli_div());
		if(gcode!=null){ // 동일한 오브젝트 등록 불가
			return "dupli";
		}
		
		ipAddrToRegit.setNli_gcode(ncLinkPolicyService.nextIpGcode(ipAddrToRegit.getNli_div()));
		int result = ncLinkPolicyService.ipaddr_insert(ipAddrToRegit);
		
		String ip_type="";
		if(ipAddrToRegit.getNli_ip_type()==1){
			ip_type="단일";
		}else if(ipAddrToRegit.getNli_ip_type()==3){
			ip_type="다중";
		}else if(ipAddrToRegit.getNli_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}

		String audit_page = "";
		String audit_param = "";
		if(ipAddrToRegit.getNli_div()==1){
			audit_page = "내부 접속 허용 IP 등록";
			audit_param = "내부망 IP명 = "+ipAddrToRegit.getNli_ip_nm()+",내부망 IP주소 = "+ipAddrToRegit.getNli_ip();
		}else{
			audit_page = "외부 접속 허용 IP 등록";
			audit_param = "외부망 IP명 = "+ipAddrToRegit.getNli_ip_nm()+",외부망 IP주소 = "+ipAddrToRegit.getNli_ip();
		}
		
		if (result == 1) {
			//if(ipAddrToRegit.getNli_div()==2) ncTransferService.trans_link_ip("I", ipAddrToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 연계 보안정책 IP 주소 수정화면 호출
	 * @param nli_seq
	 * @return
	 */
	@RequestMapping(value = "/lnk_ipaddr_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_ipaddr_editVw(@RequestParam("nli_seq") int nli_seq) {
		NcLinkPolicy policy = ncLinkPolicyService.getIpaddrBySeq(nli_seq);
			
		String strReturn = "";
		if(policy.getNli_div()==1){
			strReturn = "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.intipname")+"</label>";
		}else{
			strReturn = "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.extipname")+"</label>";
		}
		strReturn += "<input type='hidden' id='org_ip_nm' name='org_ip_nm' value='"+policy.getNli_ip_nm()+"' /><input type='hidden' id='nli_seq' name='nli_seq' value='"+policy.getNli_seq()+"' />"+
			"<input type='hidden' id='nli_gcode' name='nli_gcode' value='"+policy.getNli_gcode()+"' />"+
			"<div class='col-md-7'><input type='text' id='nli_ip_nm' name='nli_ip_nm' class='form-control col-md-7 col-xs-12' value='"+policy.getNli_ip_nm()+"' /></div></div>";
		if(policy.getNli_div()==1){
			strReturn += "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.intipaddr")+"</label>";
		}else{
			strReturn += "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.extipaddr")+"</label>";
		}
		
		/*strReturn += "<th><select id='nli_ip_type' name='nli_ip_type' style='width:150px;' onChange='change_type(this.value);'>"+
		"<option value='1' "+ (policy.getNli_ip_type()==1?"selected":"") +">단일</option>"+
		"<option value='3' "+ (policy.getNli_ip_type()==3?"selected":"") +">다중</option>"+
		"<option value='5' "+ (policy.getNli_ip_type()==5?"selected":"") +">범위</option>"+
		"<option value='7' "+ (policy.getNli_ip_type()==7?"selected":"") +">CIDR</option>"+
		"</select></th>" +*/
		strReturn += "<div class='col-md-7'>";
			
		if(policy.getNli_ip_type()==1){
			strReturn += "<input type='text' id='ip_1' name='ip_1' class='form-control col-md-7 col-xs-12' value='"+policy.getNli_ip()+"' maxlength='15' />";
		}else if(policy.getNli_ip_type()==3){
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:95%;' value='"+policy.getNli_ip()+"' />";
		}else if(policy.getNli_ip_type()==5){
			String temp[] = policy.getNli_ip().split("-");
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:45%;' value='"+temp[0]+"' /> - <input type='text' id='ip_2' name='ip_2' style='width:45%;' value='"+temp[1]+"' />";
		}else if(policy.getNli_ip_type()==7){
			String temp[] = policy.getNli_ip().split("/");
			strReturn += "<input type='text' id='ip_1' name='ip_1' style='width:80%;' value='"+temp[0]+"' /> / <input type='text' id='ip_2' name='ip_2' style='width:15%;' value='"+temp[1]+"' />";
		}
		
		strReturn += "</div></div><div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'> ";
		
		if(policy.getNli_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_snd_ipaddr.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_rcv_ipaddr.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_ipaddr();'>"+MessageUtil.getMessage("btn.save")+"</button></div></div>";
		
		return strReturn;
	}
	
	/**
	 * 연계 보안정책 IP 주소 수정
	 * @param ipAddrToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/lnk_ipaddr_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String lnk_ipaddr_update(NcLinkPolicy ipAddrToRegit, Model model, HttpSession session){
		if(!ipAddrToRegit.getNli_ip_nm().equals(ipAddrToRegit.getOrg_ip_nm())){
			String gcode = ncLinkPolicyService.getIpGcode(ipAddrToRegit.getNli_ip_nm(), ipAddrToRegit.getNli_div());
			if(gcode!=null){
				return "dupli";
			}
		}
		
		int result = ncLinkPolicyService.ipaddr_update(ipAddrToRegit);
		
		String ip_type="";
		if(ipAddrToRegit.getNli_ip_type()==1){
			ip_type="단일";
		}else if(ipAddrToRegit.getNli_ip_type()==3){
			ip_type="다중";
		}else if(ipAddrToRegit.getNli_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}

		String audit_page = "";
		String audit_param = "";
		if(ipAddrToRegit.getNli_div()==1){
			audit_page = "내부 접속 허용 IP 수정";
			audit_param = "내부망 IP명 = "+ipAddrToRegit.getNli_ip_nm()+",내부망 IP주소 = "+ipAddrToRegit.getNli_ip();
		}else{
			audit_page = "외부 접속 허용 IP 수정";
			audit_param = "외부망 IP명 = "+ipAddrToRegit.getNli_ip_nm()+",외부망 IP주소 = "+ipAddrToRegit.getNli_ip();
		}
		
		if (result == 1) {
			//ncPolicyService.create_aclRuleFile(ipAddrToRegit.getNoi_div()); // 정책 파일 만들기
			/*ArrayList<NdPolicy> policyList = ncPolicyService.useIpPolicy(ipAddrToRegit); // IP 주소를 사용 중인 서비스 정책 번호 조회
			for(int i=0; i < policyList.size(); i++){
				ncPolicyService.create_configFile(policyList.get(i).getNps_seq()); // 환경 설정 파일 만들기
			}*/
			//if(ipAddrToRegit.getNli_div()==2) ncTransferService.trans_link_ip("U", ipAddrToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 연계 보안정책 IP 주소 삭제
	 * @param nli_seq
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/lnk_ipaddr_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_ipaddr_delete(@RequestParam("nli_seq") int nli_seq, HttpSession session) {
		int cnt = ncLinkPolicyService.ipaddr_use_count(nli_seq);
		if(cnt>0){
			return "used";
		}
		
		NcLinkPolicy policy = ncLinkPolicyService.getIpaddrBySeq(nli_seq);
		
		String ip_type="";
		if(policy.getNli_ip_type()==1){
			ip_type="단일";
		}else if(policy.getNli_ip_type()==3){
			ip_type="다중";
		}else if(policy.getNli_ip_type()==5){
			ip_type="범위";
		}else{
			ip_type="CIDR";
		}
		
		String audit_page = "";
		String audit_param = "";
		if(policy.getNli_div()==1){
			audit_page = "내부 접속 허용 IP 삭제";
			audit_param = "내부망 IP명 = "+policy.getNli_ip_nm()+",내부망 IP주소 = "+policy.getNli_ip();
		}else{
			audit_page = "외부 접속 허용 IP 삭제";
			audit_param = "외부망 IP명 = "+policy.getNli_ip_nm()+",외부망 IP주소 = "+policy.getNli_ip();
		}
		
		int result = ncLinkPolicyService.ipaddr_delete(nli_seq);
		if (result == 1) {
			//if(policy.getNli_div()==2) ncTransferService.trans_link_ip("D", policy, nli_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 연계 보안정책 IP 주소 일괄삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/lnk_ipaddr_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_ipaddr_delall(HttpServletRequest req, HttpSession session) {
		String nli_seq[] = req.getParameterValues("sel[]");
		
		for(int i=0; i<nli_seq.length; i++){
			int cnt = ncLinkPolicyService.ipaddr_use_count(Integer.parseInt(nli_seq[i]));
			if(cnt>0){
				NcLinkPolicy policy = ncLinkPolicyService.getIpaddrBySeq(Integer.parseInt(nli_seq[i]));
				return "used|내부망 IP명="+policy.getNli_ip_nm()+", IP="+policy.getNli_ip();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		int nli_div = 0;
		for(int i=0; i<nli_seq.length; i++){
			NcLinkPolicy policy = ncLinkPolicyService.getIpaddrBySeq(Integer.parseInt(nli_seq[i]));
			
			String ip_type="";
			if(policy.getNli_ip_type()==1){
				ip_type="단일";
			}else if(policy.getNli_ip_type()==3){
				ip_type="다중";
			}else if(policy.getNli_ip_type()==5){
				ip_type="범위";
			}else{
				ip_type="CIDR";
			}
			
			nli_div = policy.getNli_div();
			
			if(policy.getNli_div()==1){
				audit_page = "내부 접속 허용 IP 삭제";
				audit_param = "내부망 IP명 = "+policy.getNli_ip_nm()+",내부망 IP주소 = "+policy.getNli_ip();
			}else{
				audit_page = "외부 접속 허용 IP 삭제";
				audit_param = "외부망 IP명 = "+policy.getNli_ip_nm()+",외부망 IP주소 = "+policy.getNli_ip();
			}

			result = ncLinkPolicyService.ipaddr_delete(Integer.parseInt(nli_seq[i]));
			if (result == 1) {
				//if(policy.getNli_div()==2) ncTransferService.trans_link_ip("D", policy, Integer.parseInt(nli_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
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
	 * 연계 보안정책 송신 서비스 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_service.do", method = RequestMethod.GET)
    public String lnk_snd_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1); // 1:송신 2:수신
		model.addAttribute("serviceList", serviceList);
		
		model.addAttribute("serviceToRegit", new NcLinkPolicy());
		return "link_policy/send_service";
    }
	
	/**
	 * 연계 보안정책 서비스 등록
	 * @param serviceToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_service_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String lnk_service_regit(NcLinkPolicy serviceToRegit, Model model, HttpSession session){
		String gcode = ncLinkPolicyService.getSvcGcode(serviceToRegit.getNls_service_nm(), serviceToRegit.getNls_div());
		if(gcode!=null){ // 동일한 오브젝트명 등록 안됨
			return "dupli";
		}
		
		serviceToRegit.setNls_gcode(ncLinkPolicyService.nextSvcGcode(serviceToRegit.getNls_div()));
		
		String audit_page = "";
		if(serviceToRegit.getNls_div()==1){
			audit_page = "내부 접속 허용 서비스 등록";
		}else{
			audit_page = "외부 접속 허용 서비스 등록";
		}
		
		String audit_param = "서비스명 = "+serviceToRegit.getNls_service_nm()+",포트번호 = "+serviceToRegit.getNls_port();
		
		int result = ncLinkPolicyService.service_insert(serviceToRegit);
		if (result == 1) {
			//if(serviceToRegit.getNls_div()==2) ncTransferService.trans_link_service("I", serviceToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
		
	/**
	 * 연계 보안정책 서비스 수정화면 호출
	 * @param nls_seq
	 * @return
	 */
	@RequestMapping(value = "/lnk_service_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_service_editVw(@RequestParam("nls_seq") int nls_seq) {
		NcLinkPolicy policy = ncLinkPolicyService.getServiceBySeq(nls_seq);
				
		String strReturn = "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.svcname")+"</label>"+
		"<input type='hidden' id='org_service_nm' name='org_service_nm' value='"+policy.getNls_service_nm()+"' /><input type='hidden' id='nls_seq' name='nls_seq' value='"+policy.getNls_seq()+"' />"+
		"<input type='hidden' id='nls_gcode' name='nls_gcode' value='"+policy.getNls_gcode()+"' />"+
		"<div class='col-md-7'><input type='text' id='nls_service_nm' name='nls_service_nm' class='form-control col-md-7 col-xs-12' value='"+policy.getNls_service_nm()+"' /></div></div>"+
		"<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.port")+"</label>"+
		"<div class='col-md-7'><input type='text' id='nls_port' name='nls_port' class='form-control col-md-7 col-xs-12' value='"+policy.getNls_port()+"' /></div></div>"+
		"<div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'>";
		
		if(policy.getNls_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_snd_service.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_rcv_service.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_service();'>"+MessageUtil.getMessage("btn.save")+"</button></div>";
		
		return strReturn;
	}
	
	/**
	 * 연계 보안정책 서비스 수정
	 * @param serviceToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_service_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String lnk_service_update(NcLinkPolicy serviceToRegit, Model model, HttpSession session){
		if(!serviceToRegit.getNls_service_nm().equals(serviceToRegit.getOrg_service_nm())){
			String gcode = ncLinkPolicyService.getSvcGcode(serviceToRegit.getNls_service_nm(), serviceToRegit.getNls_div());
			if(gcode!=null){
				return "dupli";
			}
		}
		
		String audit_page = "";
		if(serviceToRegit.getNls_div()==1){
			audit_page = "내부 접속 허용 서비스 수정";
		}else{
			audit_page = "외부 접속 허용 서비스 수정";
		}
		
		String audit_param = "서비스명 = "+serviceToRegit.getNls_service_nm()+",포트번호 = "+serviceToRegit.getNls_port();
		
		int result = ncLinkPolicyService.service_update(serviceToRegit);
		if (result == 1) {
			/*ncPolicyService.create_aclRuleFile(serviceToRegit.getNos_div()); // 정책 파일 만들기
			ArrayList<NdPolicy> policyList = ncPolicyService.useServicePolicy(serviceToRegit); // 서비스를 사용 중인 서비스 정책 번호
			for(int i=0; i < policyList.size(); i++){
				ncPolicyService.create_configFile(policyList.get(i).getNps_seq()); // 환경 설정 파일 만들기
			}*/
			//if(serviceToRegit.getNls_div()==2) ncTransferService.trans_link_service("U", serviceToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 연계 보안정책 서비스 삭제
	 * @param nls_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_service_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_service_delete(@RequestParam("nls_seq") int nls_seq, HttpSession session) {
		NcLinkPolicy policy = ncLinkPolicyService.getServiceBySeq(nls_seq);
		int cnt = 0;
		if(policy.getNls_div()==1){
			cnt = ncLinkPolicyService.service_use_count_tx(nls_seq);
		}else{
			cnt = ncLinkPolicyService.service_use_count_rx(nls_seq);
		}
		if(cnt>0){
			return "used";
		}
		
		String audit_page = "";
		if(policy.getNls_div()==1){
			audit_page = "내부 접속 허용 서비스 삭제";
		}else{
			audit_page = "외부 접속 허용 서비스 삭제";
		}
		
		String audit_param = "서비스명 = "+policy.getNls_service_nm()+",포트번호 = "+policy.getNls_port();
		
		int result = ncLinkPolicyService.service_delete(nls_seq);
		if (result == 1) {
			//if(policy.getNls_div()==2) ncTransferService.trans_link_service("D", policy, nls_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 연계 보안정책 서비스 일괄삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_service_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_service_delall(HttpServletRequest req, HttpSession session) {
		String nls_seq[] = req.getParameterValues("sel[]");
		
		int nls_div = 0;
		NcLinkPolicy tmp = ncLinkPolicyService.getServiceBySeq(Integer.parseInt(nls_seq[0]));
		nls_div = tmp.getNls_div();
		
		for(int i=0; i<nls_seq.length; i++){
			int cnt = 0;
			if(nls_div==1){
				cnt = ncLinkPolicyService.service_use_count_tx(Integer.parseInt(nls_seq[i]));
			}else{
				cnt = ncLinkPolicyService.service_use_count_rx(Integer.parseInt(nls_seq[i]));
			}
			if(cnt>0){
				NcLinkPolicy policy = ncLinkPolicyService.getServiceBySeq(Integer.parseInt(nls_seq[i]));
				return "used|서비스명="+policy.getNls_service_nm()+", 포트번호="+policy.getNls_port();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		for(int i=0; i<nls_seq.length; i++){
			NcLinkPolicy policy = ncLinkPolicyService.getServiceBySeq(Integer.parseInt(nls_seq[i]));
			
			if(policy.getNls_div()==1){
				audit_page = "내부 접속 허용 서비스 삭제";
			}else{
				audit_page = "외부 접속 허용 서비스 삭제";
			}
			
			audit_param = "서비스명 = "+policy.getNls_service_nm()+",포트번호 = "+policy.getNls_port();
			
			result = ncLinkPolicyService.service_delete(Integer.parseInt(nls_seq[i]));
			if (result == 1) {
				//if(policy.getNls_div()==2) ncTransferService.trans_link_service("D", policy, Integer.parseInt(nls_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
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
	 * 연계 보안정책 송신 접속허용정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_allow.do", method = RequestMethod.GET)
    public String lnk_snd_allow(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> ipAddrList = ncLinkPolicyService.getIpaddr(1); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyAllow(1);
		model.addAttribute("policyList", policyList);
		
		ArrayList<NcLinkPolicy> linkList = ncLinkPolicyService.getPolicyService(1);
		model.addAttribute("linkList", linkList);
		
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/send_policy_allow";
    }
	
	/**
	 * 연계 보안정책 접속허용정책 등록
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_allow_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_allow_regit(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 동일하면 안됨
		 * 2. 전송 통제 정책명이 동일하면 안됨
		 * 3. 내부망IP와 서비스가 동일하면 안됨
		 */
		int cnt = ncLinkPolicyService.policy_allow_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		cnt = ncLinkPolicyService.policy_allow_count_policy(policyToRegit);
		if(cnt>0){
			return "dupli_policy";
		}
		
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(policyToRegit.getNlp_seq());
		
		String audit_page = "";
		String audit_param = "";		
		if(policyToRegit.getNla_div()==1){
			audit_page = "내부 접속 허용 정책 등록";
			audit_param = "정책명 = "+policyToRegit.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",내부망 IP명 = "+ncLinkPolicyService.getIpObjNm(policyToRegit.getNla_ip_gcode(),policyToRegit.getNla_div());
		}else{
			audit_page = "외부 접속 허용 정책 등록";
			audit_param = "정책명 = "+policyToRegit.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",외부망 IP명 = "+ncLinkPolicyService.getIpObjNm(policyToRegit.getNla_ip_gcode(),policyToRegit.getNla_div());
		}
		
		int result = ncLinkPolicyService.policy_allow_insert(policyToRegit);
		if (result == 1) {
			//ncPolicyService.create_aclRuleFile(policyToRegit.getNpa_div()); // 정책 파일 만들기
			//if(policyToRegit.getNla_div()==2) ncTransferService.trans_link_allow("I", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 연계 보안정책 접속허용정책 수정화면 호출
	 * @param nla_seq
	 * @return
	 */
	@RequestMapping(value = "/lnk_allow_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_allow_editVw(@RequestParam("nla_seq") int nla_seq) {
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyAllowBySeq(nla_seq);
		String temp="";
		
		String strReturn = "<div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.policyname")+"</label><div class='col-md-7'>" +
				"<input type='hidden' id='nla_seq' name='nla_seq' value='"+policy.getNla_seq()+"' /><input type='text' name='nla_name' class='form-control col-md-7 col-xs-12' value='"+policy.getNla_name()+"'></div>" +
				"</div><div class='form-group'><label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.ctrlpolicynm")+"</label><div class='col-md-7'><select name='nlp_seq' class='form-control'>";

		ArrayList<NcLinkPolicy> linkList = ncLinkPolicyService.getPolicyService(policy.getNla_div()); // 1:송신 2:수신
		for(int i=0; i < linkList.size(); i++){
			temp="";
			if(policy.getNlp_seq()==linkList.get(i).getNlp_seq()) temp = "selected";
			strReturn += "<option value='"+linkList.get(i).getNlp_seq()+"' "+temp+">"+linkList.get(i).getNlp_name()+"</option>";
		}
		
		strReturn += "</select></div></div><div class='form-group'>";
		
		if(policy.getNla_div()==1){
			strReturn += "<label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.intipname")+"</label>";
		}else{
			strReturn += "<label class='control-label col-md-3'>"+MessageUtil.getMessage("policy.extipname")+"</label>";
		}

		strReturn += "<div class='col-md-7'><select name='nla_ip_gcode' class='form-control'>";
		ArrayList<NcLinkPolicy> ipAddrList = ncLinkPolicyService.getIpaddr(policy.getNla_div()); // 1:송신 2:수신
		for(int i=0; i < ipAddrList.size(); i++){
			temp="";
			if(policy.getNla_ip_gcode()==ipAddrList.get(i).getNli_gcode()) temp = "selected";
			strReturn += "<option value='"+ipAddrList.get(i).getNli_gcode()+"' "+temp+">"+ipAddrList.get(i).getNli_ip_nm()+"</option>";
		}
		
		strReturn += "</select></div></div><div class='ln_solid'></div><div class='col-md-6 col-md-offset-3'>";
		
		if(policy.getNla_div()==1){
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_snd_allow.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}else{
			strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('lnk_rcv_allow.do');\">"+MessageUtil.getMessage("btn.cancel")+"</button>";
		}
		
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_policy();'>"+MessageUtil.getMessage("btn.save")+"</button></div>";
		
		return strReturn;
	}
	
	/**
	 * 연계 보안정책 접속허용정책 수정
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_allow_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_allow_update(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 동일하면 안됨
		 * 2. 전송 통제 정책명이 동일하면 안됨
		 * 3. 내부망IP와 서비스가 동일하면 안됨
		 */
		int cnt = ncLinkPolicyService.policy_allow_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		cnt = ncLinkPolicyService.policy_allow_count_policy(policyToRegit);
		if(cnt>0){
			return "dupli_policy";
		}
		
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyAllowBySeq(policyToRegit.getNla_seq());
		
		String audit_page = "";
		String audit_param = "";
		if(policyToRegit.getNla_div()==1){
			audit_page = "내부 접속 허용 정책 수정";
			audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",내부망 IP명 = "+policy.getNli_ip_nm();
		}else{
			audit_page = "외부 접속 허용 정책 수정";
			audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",외부망 IP명 = "+policy.getNli_ip_nm();
		}

		int result = ncLinkPolicyService.policy_allow_update(policyToRegit);
		if (result == 1) {
			//ncPolicyService.create_aclRuleFile(policyToRegit.getNpa_div()); // 정책 파일 만들기
			//if(policyToRegit.getNla_seq()==2) ncTransferService.trans_link_allow("U", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 보안정책 접속허용정책 삭제
	 * @param nla_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_allow_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_allow_delete(@RequestParam("nla_seq") int nla_seq, HttpSession session) {
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyAllowBySeq(nla_seq);
	
		String audit_page = "";
		String audit_param = "";
		if(policy.getNla_div()==1){
			audit_page = "내부 접속 허용 정책 삭제";
			audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",내부망 IP명 = "+policy.getNli_ip_nm();
		}else{
			audit_page = "외부 접속 허용 정책 삭제";
			audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",외부망 IP명 = "+policy.getNli_ip_nm();
		}
		
		int result = ncLinkPolicyService.policy_allow_delete(nla_seq);
		if (result == 1) {
			//ncPolicyService.create_aclRuleFile(policy.getNpa_div()); // 정책 파일 만들기
			//if(policy.getNla_div()==2) ncTransferService.trans_link_allow("D", policy, nla_seq); // 수신 DB에서 실행할 쿼리파일 만들기
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
	@RequestMapping(value = "/lnk_rcv_ipaddr.do", method = RequestMethod.GET)
    public String lnk_rcv_ipaddr(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> ipAddrList = ncLinkPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		model.addAttribute("ipAddrToRegit", new NcLinkPolicy());
		return "link_policy/receive_ipaddr";
    }
	
	/**
	 * 보안정책 수신 서비스 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_rcv_service.do", method = RequestMethod.GET)
    public String lnk_rcv_service(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2); // 1:송신 2:수신
		model.addAttribute("serviceList", serviceList);
		
		model.addAttribute("serviceToRegit", new NcLinkPolicy());
		return "link_policy/receive_service";
    }
	
	/**
	 * 보안정책 수신 접속허용정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_rcv_allow.do", method = RequestMethod.GET)
    public String lnk_rcv_allow(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> ipAddrList = ncLinkPolicyService.getIpaddr(2); // 1:송신 2:수신
		model.addAttribute("ipAddrList", ipAddrList);
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyAllow(2);
		model.addAttribute("policyList", policyList);
		
		ArrayList<NcLinkPolicy> linkList = ncLinkPolicyService.getPolicyService(2);
		model.addAttribute("linkList", linkList);
		
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/receive_policy_allow";
    }
	
	/**
	 * 접속 허용 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/lnk_allow_delall.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_allow_delall(HttpServletRequest req, HttpSession session) {
		String nla_seq[] = req.getParameterValues("sel[]");
		
		int result = 0;
		//int nlp_div = 0;
		String audit_page = "";
		String audit_param = "";
		int nla_div = 0;
		for(int i=0; i<nla_seq.length; i++){
			NcLinkPolicy policy = ncLinkPolicyService.getPolicyAllowBySeq(Integer.parseInt(nla_seq[i]));
			
		//	nlp_div = policy.getNla_div();
			
			nla_div = policy.getNla_div();
			if(policy.getNla_div()==1){
				audit_page = "내부 접속 허용 정책 삭제";
				audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",내부망 IP명 = "+policy.getNli_ip_nm();
			}else{
				audit_page = "외부 접속 허용 정책 삭제";
				audit_param = "정책명 = "+policy.getNla_name()+",전송통제 정책명 = "+policy.getNlp_name()+",외부망 IP명 = "+policy.getNli_ip_nm();
			}
			
			result = ncLinkPolicyService.policy_allow_delete(Integer.parseInt(nla_seq[i]));
			if (result == 1) {
				//if(policy.getNla_div()==2) ncTransferService.trans_link_allow("D", policy, Integer.parseInt(nla_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
				break; // 실패 시 더이상 진행없이 for 문 빠져 나오기
			}
		}
		
		if (result == 1) {
			//ncPolicyService.create_aclRuleFile(nap_div); // 정책 파일 만들기
			return "true";
		}else{
			return "DB Error";
		}
	}
	
	/**
	 * 내부 연계 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_policy.do", method = RequestMethod.GET)
    public String lnk_snd_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(1);
		model.addAttribute("policyList", policyList);
		
		return "link_policy/send_policy_service";
    }
	
	/**
	 * 내부 연계 정책 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_policy_regit.do", method = RequestMethod.GET)
	public String lnk_snd_policy_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500003));
		model.addAttribute("title", ncMenuService.getNowTitle(500003));
		model.addAttribute("menu_id", 500003);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(protList.get(0).getNpc_no(), protList.get(0).getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,1));
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/send_policy_service_regit";			
	}
	
	/**
	 * 내부 연계 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_snd_policy_editVw.do", method = RequestMethod.GET)
	public String lnk_snd_policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500003));
		model.addAttribute("title", ncMenuService.getNowTitle(500003));
		model.addAttribute("menu_id", 500003);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(ncPolicy.getNpc_no(), ncPolicy.getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,1));
		model.addAttribute("policyToRegit", ncPolicy);
		return "link_policy/send_policy_service_edit";
	}
	
	/**
	 * 콘텐츠 타입 변경
	 * @param npc_no
	 * @param npcs_type
	 * @return
	 */
	@RequestMapping(value = "/lnk_change_prot.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_change_prot(@RequestParam("npc_no") int npc_no, @RequestParam("npcs_type") String npcs_type) {
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(npc_no, npcs_type);
		
		String strReturn = "<select name='npcs_seq' id='npcs_seq' class='form-control col-md-7 col-xs-12' onChange='change_conts(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNpcs_seq()+"'>"+contsList.get(i).getNpcs_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 상위 콘텐츠 변경
	 * @param ntc_seq
	 * @return
	 */
	@RequestMapping(value = "/change_conts_top.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_conts_top(@RequestParam("ntc_seq") int ntc_seq) {
		ArrayList<NcPolicy> contsList = ncPolicyService.getDetailConts(ntc_seq, 1);
		
		String strReturn = "<select name='ndc_seq' id='ndc_seq'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNdc_seq()+"'>"+contsList.get(i).getNdc_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 연계 정책 등록
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_policy_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_policy_regit(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 같으면 안됨
		 * 2. 콘텐츠 타입 하나당 정책 한개
		 * 3. RX 전송 포트당 정책 한개
		 */
		int cnt = ncLinkPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		/*cnt = ncLinkPolicyService.policy_service_count_type(policyToRegit);
		if(cnt>0){
			return "dupli_type";
		}*/
		cnt = ncLinkPolicyService.policy_service_count_port(policyToRegit);
		if(cnt>0){
			return "dupli_port";
		}
		
		NcLinkPolicy conts = ncLinkPolicyService.getContsPolicy(policyToRegit.getNpcs_seq());
		
		NcConfig program = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_pro_id());
		
		String audit_param = "";
		String audit_page = "";
		if(policyToRegit.getNlp_div()==1 || policyToRegit.getNlp_div()==3){
			if(policyToRegit.getNlp_div()==1) {
				audit_page = "내부 송신 전송 통제 정책 등록";
			}else{
				audit_page = "외부 송신 전송 통제 정책 등록";
			}
			audit_param = "정책명 = "+policyToRegit.getNlp_name()+",프로토콜 타입 = "+conts.getNpcs_type()+",콘텐츠 타입 = "+conts.getNpcs_name();
			/*if(policyToRegit.getNpcs_seq()==4||policyToRegit.getNpcs_seq()==5){ // ftp
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2);
			}else if(policyToRegit.getNpcs_seq()==10||policyToRegit.getNpcs_seq()==11){ // opc
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",OPC 서버명 = "+policyToRegit.getNlp_opc_server()+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2)+
						",태그 파일명="+policyToRegit.getNlp_tag_filename()+",태그 시작 번호="+policyToRegit.getNlp_tag_startindex()+",태그 갯수="+policyToRegit.getNlp_tag_readcount();
			}else if(policyToRegit.getNpcs_seq()==6||policyToRegit.getNpcs_seq()==7||policyToRegit.getNpcs_seq()==8||policyToRegit.getNpcs_seq()==9){ // db
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",데이터베이스명 = "+policyToRegit.getNlp_db_name()+",계정 = "+policyToRegit.getNlp_account()+
						",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2);
			}else{ // udp*/
				audit_param += ",접근 허용 IP = "+policyToRegit.getNlp_allow_ip().replace("|", " ");
					
				if(policyToRegit.getNlp_div()==1) {
					audit_param += ",내부망 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				}else{
					audit_param += ",외부망 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				}
				audit_param += ",일방향 전송 포트 = "+policyToRegit.getNlp_control_port();
			//}
			//policyToRegit.setNps_conf_file(Config.getInstance().getSndPath()+programList.getNcp_file_name()+"_conf");
		}else{
			if(policyToRegit.getNlp_div()==2) {
				audit_page = "외부 수신 전송 통제 정책 등록";
			}else{
				audit_page = "내부 수신 전송 통제 정책 등록";
			}
			audit_param = "정책명 = "+policyToRegit.getNlp_name()+",프로토콜 타입 = "+conts.getNpcs_type()+",콘텐츠 타입 = "+conts.getNpcs_name();
			/*if(policyToRegit.getNpcs_seq()==4||policyToRegit.getNpcs_seq()==5){ // ftp
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();
			}else if(policyToRegit.getNpcs_seq()==10||policyToRegit.getNpcs_seq()==11){ // opc
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",OPC 서버명 = "+policyToRegit.getNlp_opc_server()+",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();
			}else if(policyToRegit.getNpcs_seq()==6||policyToRegit.getNpcs_seq()==7||policyToRegit.getNpcs_seq()==8||policyToRegit.getNpcs_seq()==9){ // db
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",데이터베이스명 = "+policyToRegit.getNlp_db_name()+",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();;
			}else{ // udp*/
				audit_param += ",접근 허용 IP = "+policyToRegit.getNlp_allow_ip().replace("|", " ")+
						",일방향 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				if(policyToRegit.getNlp_div()==2) {
					audit_param += ",외부망 전송 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 송신 포트 = "+policyToRegit.getNlp_nw_port();
				}else{
					audit_param += ",내부망 전송 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 송신 포트 = "+policyToRegit.getNlp_nw_port();
				}
			//}
			//policyToRegit.setNps_conf_file(Config.getInstance().getSndPath()+programList.getNcp_file_name()+"_conf");
		}
		audit_param += ",프로그램명 = "+program.getNcp_name()+",정책 사용 여부 = "+(policyToRegit.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_insert(policyToRegit);
		if (result == 1) {
			/*int service_seq = ncPolicyService.last_service_seq();
			ncPolicyService.create_flist(policyToRegit.getNcp_div()); // flist 만들기
			try {
				Thread.sleep(1000); // 1초
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ncPolicyService.create_configFile(service_seq); // 환경설정 파일 만들기*/
			//if(policyToRegit.getNlp_div()==2) ncTransferService.trans_link_policy("I", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 연계 정책 수정
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_policy_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_policy_update(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 같으면 안됨
		 * 2. 콘텐츠 타입 하나당 정책 한개
		 * 3. RX 전송 포트당 정책 한개
		 */
		int cnt = ncLinkPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		/*cnt = ncLinkPolicyService.policy_service_count_type(policyToRegit);
		if(cnt>0){
			return "dupli_type";
		}*/
		cnt = ncLinkPolicyService.policy_service_count_port(policyToRegit);
		if(cnt>0){
			return "dupli_port";
		}
		
		NcLinkPolicy conts = ncLinkPolicyService.getContsPolicy(policyToRegit.getNpcs_seq());
		
		NcConfig program = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_pro_id());
		
		String audit_param = "";
		String audit_page = "";
		if(policyToRegit.getNlp_div()==1 || policyToRegit.getNlp_div()==3){
			if(policyToRegit.getNlp_div()==1) {
				audit_page = "내부 송신 전송 통제 정책 수정";
			}else{
				audit_page = "외부 송신 전송 통제 정책 수정";
			}
			audit_param = "정책명 = "+policyToRegit.getNlp_name()+",프로토콜 타입 = "+conts.getNpcs_type()+",콘텐츠 타입 = "+conts.getNpcs_name();
			/*if(policyToRegit.getNpcs_seq()==4||policyToRegit.getNpcs_seq()==5){ // ftp
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2);
			}else if(policyToRegit.getNpcs_seq()==10||policyToRegit.getNpcs_seq()==11){ // opc
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",OPC 서버명 = "+policyToRegit.getNlp_opc_server()+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2)+
						",태그 파일명="+policyToRegit.getNlp_tag_filename()+",태그 시작 번호="+policyToRegit.getNlp_tag_startindex()+",태그 갯수="+policyToRegit.getNlp_tag_readcount();
			}else if(policyToRegit.getNpcs_seq()==6||policyToRegit.getNpcs_seq()==7||policyToRegit.getNpcs_seq()==8||policyToRegit.getNpcs_seq()==9){ // db
				audit_param += ",내부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",데이터베이스명 = "+policyToRegit.getNlp_db_name()+",계정 = "+policyToRegit.getNlp_account()+
						",패스워드 = "+policyToRegit.getNlp_passwd()+",RX 송신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_control_port(),2);
			}else{ // udp*/
				audit_param += ",접근 허용 IP = "+policyToRegit.getNlp_allow_ip().replace("|", " ");
				
				if(policyToRegit.getNlp_div()==1) {
					audit_param += ",내부망 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				}else{
					audit_param += ",외부망 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				}
				audit_param += ",일방향 전송 포트 = "+policyToRegit.getNlp_control_port();
			//}
			//policyToRegit.setNps_conf_file(Config.getInstance().getSndPath()+policy.getNcp_file_name()+"_conf");
		}else{
			if(policyToRegit.getNlp_div()==2) {
				audit_page = "외부 수신 전송 통제 정책 수정";
			}else{
				audit_page = "내부 수신 전송 통제 정책 수정";
			}
			audit_param = "정책명 = "+policyToRegit.getNlp_name()+",프로토콜 타입 = "+conts.getNpcs_type()+",콘텐츠 타입 = "+conts.getNpcs_name();
			/*if(policyToRegit.getNpcs_seq()==4||policyToRegit.getNpcs_seq()==5){ // ftp
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();
			}else if(policyToRegit.getNpcs_seq()==10||policyToRegit.getNpcs_seq()==11){ // opc
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",OPC 서버명 = "+policyToRegit.getNlp_opc_server()+",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();
			}else if(policyToRegit.getNpcs_seq()==6||policyToRegit.getNpcs_seq()==7||policyToRegit.getNpcs_seq()==8||policyToRegit.getNpcs_seq()==9){ // db
				audit_param += ",외부망 접속 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 접속 포트 = "+policyToRegit.getNlp_nw_port()+
						",TX 수신 포트 = "+ncLinkPolicyService.getSvcObjNm(policyToRegit.getNlp_rcv_port(),policyToRegit.getNlp_div())+
						",데이터베이스명 = "+policyToRegit.getNlp_db_name()+",계정 = "+policyToRegit.getNlp_account()+",패스워드 = "+policyToRegit.getNlp_passwd();;
			}else{ // udp*/
				audit_param += ",접근 허용 IP = "+policyToRegit.getNlp_allow_ip().replace("|", " ")+ 
						",일방향 수신 포트 = "+policyToRegit.getNlp_rcv_port();
				if(policyToRegit.getNlp_div()==2) {
					audit_param += ",외부망 전송 IP = "+policyToRegit.getNlp_nw_ip()+",외부망 송신 포트 = "+policyToRegit.getNlp_nw_port();
				}else{
					audit_param += ",내부망 전송 IP = "+policyToRegit.getNlp_nw_ip()+",내부망 송신 포트 = "+policyToRegit.getNlp_nw_port();
				}
			//}
			//policyToRegit.setNps_conf_file(Config.getInstance().getRcvPath()+policy.getNcp_file_name()+"_conf");
		}
			
		audit_param += ",프로그램명 = "+program.getNcp_name()+",정책 사용 여부 = "+(policyToRegit.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_update(policyToRegit);
		if (result == 1) {
			//ncPolicyService.create_configFile(policyToRegit.getNps_seq()); // 환경설정 파일 만들기
			//if(policyToRegit.getNlp_div()==2) ncTransferService.trans_link_policy("U", policyToRegit, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 연계 정책 삭제
	 * @param nps_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_policy_delete.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_policy_delete(@RequestParam("nlp_seq") int nlp_seq, HttpSession session) {
		int cnt = ncLinkPolicyService.policy_use_count(nlp_seq);
		if(cnt>0){
			return "used";
		}
		
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
	
		NcConfig program = ncConfigService.serviceProgramBySeq(policy.getNlp_pro_id());
		
		String audit_param = "";
		String audit_page = "";
		if(policy.getNlp_div()==1 || policy.getNlp_div()==3){
			if(policy.getNlp_div()==1) {
				audit_page = "내부 송신 전송 통제 정책 삭제";
			}else{
				audit_page = "외부 송신 전송 통제 정책 삭제";
			}
			audit_param = "정책명 = "+policy.getNlp_name()+",프로토콜 타입 = "+policy.getNpcs_type()+",콘텐츠 타입 = "+policy.getNpcs_name()+",접근 허용 IP = "+policy.getNlp_allow_ip().replace("|", " ");
			if(policy.getNlp_div()==1) {
				audit_param += ",내부망 수신 포트 = "+policy.getNlp_rcv_port();
			}else{
				audit_param += ",외부망 수신 포트 = "+policy.getNlp_rcv_port();
			}
			audit_param += ",일방향 전송 포트 = "+policy.getNlp_control_port();
		}else{
			if(policy.getNlp_div()==2) {
				audit_page = "외부 수신 전송 통제 정책 삭제";
			}else{
				audit_page = "내부 수신 전송 통제 정책 삭제";
			}
			audit_param = "정책명 = "+policy.getNlp_name()+",프로토콜 타입 = "+policy.getNpcs_type()+",콘텐츠 타입 = "+policy.getNpcs_name()+
					",접근 허용 IP = "+policy.getNlp_allow_ip().replace("|", " ")+",일방향 수신 포트 = "+policy.getNlp_rcv_port();
			if(policy.getNlp_div()==2) {
				audit_param += ",외부망 전송 IP = "+policy.getNlp_nw_ip()+",외부망 송신 포트 = "+policy.getNlp_nw_port();
			}else{
				audit_param += ",내부망 전송 IP = "+policy.getNlp_nw_ip()+",내부망 송신 포트 = "+policy.getNlp_nw_port();
			}
		}
		audit_param += ",프로그램명 = "+program.getNcp_name()+",정책 사용 여부 = "+(policy.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_delete(nlp_seq);
		if (result == 1) {
			//ncPolicyService.create_flist(policy.getNcp_div()); // flist 만들기
			//if(policy.getNlp_div()==2) ncTransferService.trans_link_policy("D", policy, nlp_seq); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 외부 연계 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_rcv_policy.do", method = RequestMethod.GET)
    public String lnk_rcv_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
				
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(2);
		model.addAttribute("policyList", policyList);
				
		return "link_policy/receive_policy_service";
    }
	
	/**
	 * 외부 연계 정책 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_rcv_policy_regit.do", method = RequestMethod.GET)
	public String lnk_rcv_policy_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500004));
		model.addAttribute("title", ncMenuService.getNowTitle(500004));
		model.addAttribute("menu_id", 500004);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(protList.get(0).getNpc_no(), protList.get(0).getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,2));
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/receive_policy_service_regit";			
	}
	
	/**
	 * 외부 연계 정책 수정 화면 호출
	 * @param nlp_seq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lnk_rcv_policy_editVw.do", method = RequestMethod.GET)
	public String lnk_rcv_policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500004));
		model.addAttribute("title", ncMenuService.getNowTitle(500004));
		model.addAttribute("menu_id", 500004);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(ncPolicy.getNpc_no(), ncPolicy.getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,2));
		model.addAttribute("policyToRegit", ncPolicy);
		return "link_policy/receive_policy_service_edit";
	}
	
	/**
	 * 연계 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/lnk_policy_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String lnk_policy_delall(HttpServletRequest req, HttpSession session) {
		String nlp_seq[] = req.getParameterValues("sel[]");
		
		for(int i=0; i<nlp_seq.length; i++){
			int cnt = ncLinkPolicyService.policy_use_count(Integer.parseInt(nlp_seq[i]));
			if(cnt>0){
				NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(Integer.parseInt(nlp_seq[i]));
				return "used|정책명="+policy.getNlp_name();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		for(int i=0; i<nlp_seq.length; i++){
			NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(Integer.parseInt(nlp_seq[i]));
			
			NcConfig program = ncConfigService.serviceProgramBySeq(policy.getNlp_pro_id());
			
			if(policy.getNlp_div()==1 || policy.getNlp_div()==3){
				if(policy.getNlp_div()==1) {
					audit_page = "내부 송신 전송 통제 정책 삭제";
				}else{
					audit_page = "외부 송신 전송 통제 정책 삭제";
				}
				audit_param = "정책명 = "+policy.getNlp_name()+",프로토콜 타입 = "+policy.getNpcs_type()+",콘텐츠 타입 = "+policy.getNpcs_name()+",접근 허용 IP = "+policy.getNlp_allow_ip().replace("|", " ");
				if(policy.getNlp_div()==1) {
					audit_param += ",내부망 수신 포트 = "+policy.getNlp_rcv_port();
				}else{
					audit_param += ",외부망 수신 포트 = "+policy.getNlp_rcv_port();
				}
				audit_param += ",일방향 전송 포트 = "+policy.getNlp_control_port();
			}else{
				if(policy.getNlp_div()==1) {
					audit_page = "외부 수신 전송 통제 정책 삭제";
				}else{
					audit_page = "내부 수신 전송 통제 정책 삭제";
				}
				audit_param = "정책명 = "+policy.getNlp_name()+",프로토콜 타입 = "+policy.getNpcs_type()+",콘텐츠 타입 = "+policy.getNpcs_name()+
						",접근 허용 IP = "+policy.getNlp_allow_ip().replace("|", " ")+",일방향 수신 포트 = "+policy.getNlp_rcv_port();
				if(policy.getNlp_div()==2) {
					audit_param += ",외부망 전송 IP = "+policy.getNlp_nw_ip()+",외부망 송신 포트 = "+policy.getNlp_nw_port();
				}else{
					audit_param += ",내부망 전송 IP = "+policy.getNlp_nw_ip()+",내부망 송신 포트 = "+policy.getNlp_nw_port();
				}
			}
			audit_param += ",프로그램명 = "+program.getNcp_name()+",정책 사용 여부 = "+(policy.getNlp_use_yn()==1?"사용":"미사용");
			
			result = ncLinkPolicyService.policy_service_delete(Integer.parseInt(nlp_seq[i]));
			if (result == 1) {
				//ncPolicyService.create_flist(policy.getNcp_div()); // flist 만들기
				//if(policy.getNlp_div()==2) ncTransferService.trans_link_policy("D", policy, Integer.parseInt(nlp_seq[i])); // 수신 DB에서 실행할 쿼리파일 만들기
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
	 * 외부 송신 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ext_snd_policy.do", method = RequestMethod.GET)
    public String ext_snd_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(3);
		model.addAttribute("policyList", policyList);
		
		return "link_policy/external_send_policy";
    }
	
	/**
	 * 외부 송신 정책 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ext_snd_policy_regit.do", method = RequestMethod.GET)
	public String ext_snd_policy_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500006));
		model.addAttribute("title", ncMenuService.getNowTitle(500006));
		model.addAttribute("menu_id", 500006);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(protList.get(0).getNpc_no(), protList.get(0).getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,3));
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/external_send_policy_regit";			
	}
	
	/**
	 * 내부 연계 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ext_snd_policy_editVw.do", method = RequestMethod.GET)
	public String ext_snd_policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500006));
		model.addAttribute("title", ncMenuService.getNowTitle(500006));
		model.addAttribute("menu_id", 500006);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(1);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(ncPolicy.getNpc_no(), ncPolicy.getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,3));
		model.addAttribute("policyToRegit", ncPolicy);
		return "link_policy/external_send_policy_edit";
	}
	
	/**
	 * 내부 수신 정책 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/int_rcv_policy.do", method = RequestMethod.GET)
    public String int_rcv_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(4);
		model.addAttribute("policyList", policyList);
		
		return "link_policy/internal_receive_policy";
    }
	
	/**
	 * 내부 수신 정책 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/int_rcv_policy_regit.do", method = RequestMethod.GET)
	public String int_rcv_policy_regit(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500007));
		model.addAttribute("title", ncMenuService.getNowTitle(500007));
		model.addAttribute("menu_id", 500007);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(protList.get(0).getNpc_no(), protList.get(0).getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,4));
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		return "link_policy/internal_receive_policy_regit";			
	}
	
	/**
	 * 내부 수신 정책 수정 화면 호출
	 * @param nlp_seq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/int_rcv_policy_editVw.do", method = RequestMethod.GET)
	public String int_rcv_policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500007));
		model.addAttribute("title", ncMenuService.getNowTitle(500007));
		model.addAttribute("menu_id", 500007);
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> serviceList = ncLinkPolicyService.getService(2);
		model.addAttribute("serviceList", serviceList);
		
		ArrayList<NcLinkPolicy> contrList = ncLinkPolicyService.getService(2);
		model.addAttribute("contrList", contrList);
		
		ArrayList<NcPolicy> protList = ncPolicyService.getContsType(); // 프로토콜에 따른 콘텐츠 타입
		model.addAttribute("protList", protList);
		
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(ncPolicy.getNpc_no(), ncPolicy.getNpcs_type());
		model.addAttribute("contsList", contsList);
		
		model.addAttribute("programList", ncConfigService.program_list(1,4));
		model.addAttribute("policyToRegit", ncPolicy);
		return "link_policy/internal_receive_policy_edit";
	}
	
	/**
	 * 콘텐츠 타입 변경
	 * @param npc_no
	 * @param npcs_type
	 * @return
	 */
	@RequestMapping(value = "/policy_use_change.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	public String policy_use_change(@RequestParam("npc_no") int npc_no, @RequestParam("npcs_type") String npcs_type) {
		ArrayList<NcPolicy> contsList = ncPolicyService.getContents(npc_no, npcs_type);
		
		String strReturn = "<select name='npcs_seq' id='npcs_seq' class='form-control col-md-7 col-xs-12' onChange='change_conts(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNpcs_seq()+"'>"+contsList.get(i).getNpcs_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 정책 사용 여부 변경
	 * @param nlp_use_yn
	 * @param nlp_seq
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/policy_use_change.do", method = RequestMethod.POST)
	@ResponseBody
    public String policy_use_change(@RequestParam("nlp_use_yn") int nlp_use_yn, @RequestParam("nlp_seq") int nlp_seq, HttpSession session, Model model){		
		String audit_page = "";
		String audit_param = "";
		
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		if(policy.getNlp_div()==1) audit_page = "내부 송신 전송 통제 정책 수정";
		if(policy.getNlp_div()==2) audit_page = "외부 수신 전송 통제 정책 수정";
		if(policy.getNlp_div()==3) audit_page = "외부 송신 전송 통제 정책 수정";
		if(policy.getNlp_div()==4) audit_page = "내부 수신 전송 통제 정책 수정";
			
		if(nlp_use_yn==0){
			audit_param = "정책 사용 여부를 미사용으로 변경";
		}else{
			audit_param = "정책 사용 여부를 사용으로 변경";
		}
		
		int result = ncLinkPolicyService.policy_use_change(nlp_use_yn, nlp_seq);
		if (result == 1) {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 정방향 정책 설정 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forward_policy.do", method = RequestMethod.GET)
    public String forward_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		ArrayList<ArrayList<NcLinkPolicy>> routeList = new ArrayList<ArrayList<NcLinkPolicy>>();
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(1);
		for(int i=0; i<policyList.size(); i++){
			routeList.add(i, ncLinkPolicyService.getPolicyRoute(policyList.get(i).getNlp_seq()));
		}
		
		model.addAttribute("policyList", policyList);
		model.addAttribute("routeList", routeList);
		
		ArrayList<NcPolicy> topConts = ncPolicyService.getTopConts(); // 상위 콘텐츠
		model.addAttribute("topConts", topConts);
		
		ArrayList<NcPolicy> detailConts = ncPolicyService.getDetailConts(topConts.get(0).getNtc_seq(), 1); // 상세 콘텐츠
		model.addAttribute("detailConts", detailConts);
		
		ArrayList<NcPolicy> etherType = ncPolicyService.getEtherType();
		model.addAttribute("etherType", etherType);
		
		ArrayList<NcPolicy> ipProtocol = ncPolicyService.getIpProtocol();
		model.addAttribute("ipProtocol", ipProtocol);
		
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		model.addAttribute("programList", ncConfigService.program_list(1,1));
		model.addAttribute("rxprogramList", ncConfigService.program_list(1,2));
		return "link_policy/forward_policy";
    }
	
	
	
	/**
	 * 정/역방향 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/policy_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		String all="", packet="style='display:none'";
		if(ncPolicy.getNtc_seq()==6) {
			all="style='display:none'";
			packet="";
		}
		
		String result = "<input type='hidden' name='nlp_seq' value='"+nlp_seq+"' /><div class='left-content'>"+
				"<ul>"+
					"<li>"+
						"<label>정책명</label>"+
						"<input type='text' name='nlp_name' value='"+ncPolicy.getNlp_name()+"' />"+
					"</li>"+
					"<li>"+
						"<label>서비스 타입</label>"+
						"<select name='ntc_seq' onChange='change_top(this.value);'>";
		
						ArrayList<NcPolicy> topConts = ncPolicyService.getTopConts(); // 상위 콘텐츠
						for(int i=0; i<topConts.size(); i++){
							result +="<option value='"+topConts.get(i).getNtc_seq()+"'"+(ncPolicy.getNtc_seq()==topConts.get(i).getNtc_seq()?" selected":"")+">"+topConts.get(i).getNtc_name()+"</option>";
						}
						
						result +="</select>"+
					"</li>";
			result+="<li class='ether' "+packet+">"+
						"<label>Ethernet Type</label><img src=\"/img/info.png\" style=\"margin-left:2px;vertical-align:middle\" onClick=\"window.open('/desc_popup.do?type=1', 'ether', 'width=750px,height=650px,scrollbars=no,resizable=no,toolbars=no,menubar=no,location=no');\">"+
						"<select name='npe_value'>";
					
						ArrayList<NcPolicy> etherType = ncPolicyService.getEtherType();
						for(int i=0; i<etherType.size(); i++){
							result +="<option value='"+etherType.get(i).getNpe_value()+"'"+(ncPolicy.getNpe_value()==etherType.get(i).getNpe_value()?" selected":"")+">"+etherType.get(i).getNpe_name()+"</option>";
						}

						result +="</select>"+
					"</li>"+
					"<li class='ip_proto' "+packet+">"+
						"<label>프로토콜 타입</label><img src=\"/img/info.png\" style=\"margin-left:2px;vertical-align:middle\" onClick=\"window.open('/desc_popup.do?type=2', 'ether', 'width=750px,height=650px,scrollbars=no,resizable=no,toolbars=no,menubar=no,location=no');\">"+
						"<select name='npp_no'>";
						
						ArrayList<NcPolicy> ipProtocol = ncPolicyService.getIpProtocol();
						for(int i=0; i<ipProtocol.size(); i++){
							result +="<option value='"+ipProtocol.get(i).getNpp_no()+"'"+(ncPolicy.getNpp_no()==ipProtocol.get(i).getNpp_no()?" selected":"")+">"+ipProtocol.get(i).getNpp_name()+"</option>";
						}
						
						result +="</select>"+
					"</li>"+
					"<li class='src_ip' "+packet+">"+
						"<label>출발지 IP</label>"+
						"<select name='sip' style='width:95px' onChange=\"change_disable('nlp_src_ip', this.value);\">"+
							"<option value='any'>모든</option>"+
							"<option value='sel' "+(!"any".equals(ncPolicy.getNlp_src_ip())?"selected":"")+">특정</option>"+
						"</select>"+
						"<input name='nlp_src_ip' class='nlp_src_ip' style='width:183px' value='"+(!"any".equals(ncPolicy.getNlp_src_ip())?ncPolicy.getNlp_src_ip():"")+"' "+("any".equals(ncPolicy.getNlp_src_ip())?"disabled='true'":"")+"/>"+
					"</li>"+
					"<li class='src_port' "+packet+">"+
						"<label>출발지 포트</label>"+
						"<select name='sport' style='width:95px' onChange=\"change_disable('nlp_src_port', this.value);\">"+
							"<option value='any'>모든</option>"+
							"<option value='sel' "+(!"any".equals(ncPolicy.getNlp_src_port())?"selected":"")+">특정</option>"+
						"</select>"+
						"<input name='nlp_src_port' class='nlp_src_port' style='width:183px' value='"+(!"any".equals(ncPolicy.getNlp_src_port())?ncPolicy.getNlp_src_port():"")+"' "+("any".equals(ncPolicy.getNlp_src_port())?"disabled='true'":"")+"/>"+
					"</li>"+
					"<li class='dst_ip' "+packet+">"+
						"<label>도착지 IP</label>"+
						"<select name='dip' style='width:95px' onChange=\"change_disable('nlp_dst_ip', this.value);\">"+
							"<option value='any'>모든</option>"+
							"<option value='sel' "+(!"any".equals(ncPolicy.getNlp_dst_ip())?"selected":"")+">특정</option>"+
						"</select>"+
						"<input name='nlp_dst_ip' class='nlp_dst_ip' style='width:183px' value='"+(!"any".equals(ncPolicy.getNlp_dst_ip())?ncPolicy.getNlp_dst_ip():"")+"' "+("any".equals(ncPolicy.getNlp_dst_ip())?"disabled='true'":"")+"/>"+
					"</li>"+
					"<li class='dst_port' "+packet+">"+
						"<label>도착지 포트</label>"+
						"<select name='dport' style='width:95px' onChange=\"change_disable('nlp_dst_port', this.value);\">"+
							"<option value='any'>모든</option>"+
							"<option value='sel' "+(!"any".equals(ncPolicy.getNlp_dst_port())?"selected":"")+">특정</option>"+
						"</select>"+
						"<input name='nlp_dst_port' class='nlp_dst_port' style='width:183px' value='"+(!"any".equals(ncPolicy.getNlp_dst_port())?ncPolicy.getNlp_src_port():"")+"' "+("any".equals(ncPolicy.getNlp_dst_port())?"disabled='true'":"")+"/>"+
					"</li>";
			result+="<li class='svs' "+all+">"+
						"<label>서비스명</label>"+
						"<select name='ndc_seq' id='conts_area2'>";
							
						ArrayList<NcPolicy> detailConts = ncPolicyService.getDetailConts(ncPolicy.getNtc_seq(), 1);
						for(int i=0; i<detailConts.size(); i++){
							result +="<option value='"+detailConts.get(i).getNdc_seq()+"'"+(ncPolicy.getNdc_seq()==detailConts.get(i).getNdc_seq()?" selected":"")+">"+detailConts.get(i).getNdc_name()+"</option>";
						}
								
						result +="</select>"+
					"</li>";

				if(ncPolicy.getNlp_div()==1){
					result+="<li>"+
						"<label>내부 프로그램명</label>"+
						"<select name='nlp_pro_id'>";
						ArrayList<NcConfig> program = new ArrayList<NcConfig>();
						program = ncConfigService.program_list(1,1);
						for(int i=0; i<program.size(); i++){
							result +="<option value='"+program.get(i).getNcp_seq()+"'"+(ncPolicy.getNlp_pro_id()==program.get(i).getNcp_seq()?" selected":"")+">"+program.get(i).getNcp_name()+"</option>";
						}
						result +="</select>"+
					"</li>"+
					"<li>"+
						"<label>외부 프로그램명</label>"+
						"<select name='nlp_rxpro_id'>";
						ArrayList<NcConfig> program1 = new ArrayList<NcConfig>();
						program1 = ncConfigService.program_rvs_list(1,2);
						for(int i=0; i<program1.size(); i++){
							result +="<option value='"+program1.get(i).getNcp_seq()+"'"+(ncPolicy.getNlp_rxpro_id()==program1.get(i).getNcp_seq()?" selected":"")+">"+program1.get(i).getNcp_name()+"</option>";
						}
						result +="</select>"+
					"</li>";	
				}else{
					result+="<li>"+
						"<label>외부 프로그램명</label>"+
						"<select name='nlp_pro_id'>";
						ArrayList<NcConfig> program = new ArrayList<NcConfig>();
						program = ncConfigService.program_list(1,3);
						for(int i=0; i<program.size(); i++){
							result +="<option value='"+program.get(i).getNcp_seq()+"'"+(ncPolicy.getNlp_pro_id()==program.get(i).getNcp_seq()?" selected":"")+">"+program.get(i).getNcp_name()+"</option>";
						}
						result +="</select>"+
					"</li>"+
					"<li>"+
						"<label>내부 프로그램명</label>"+
						"<select name='nlp_rxpro_id'>";
						ArrayList<NcConfig> program1 = new ArrayList<NcConfig>();
						program1 = ncConfigService.program_rvs_list(1,4);
						for(int i=0; i<program1.size(); i++){
							result +="<option value='"+program1.get(i).getNcp_seq()+"'"+(ncPolicy.getNlp_pro_id()==program1.get(i).getNcp_seq()?" selected":"")+">"+program1.get(i).getNcp_name()+"</option>";
						}
						result +="</select>"+
					"</li>";
				}
				result+="<li class='pop-switch'>"+
						"<p>사용여부</p>"+
						"<label class='switch'>"+
							"<input name='use_yn' type='checkbox' "+(ncPolicy.getNlp_use_yn()==1?"checked":"")+"><span class='slider'></span>"+
						"</label>"+
					"</li>"+
				"</ul>"+
			"</div>";
				

			result+="<div class='right-content packets' "+packet+">"+
					"<div class='path-wrapper1'>"+
						"<h2>경로</h2>"+
						"<ul class='box box1_1'>"+
						"<li>"+
							"<label style='font-size:13px;'>내부 NIC 번호</label><br>"+
							"<input name='nlp_tx_nic' value='"+ncPolicy.getNlp_tx_nic()+"' style=\"width:208px\" />"+
						"</li>"+
						"<li>"+
							"<label style='font-size:13px;'>Oneway 포트</label><br/>"+
							"<select name='nlp_oneway_port'>"+
								"<option value='1' "+(ncPolicy.getNlp_oneway_port()==1?"selected":"")+">Master</option>"+
								"<option value='2' "+(ncPolicy.getNlp_oneway_port()==2?"selected":"")+">Slave</option>"+
							"</select>"+
						"</li>"+
						"<li style='border-right: none;'>"+
							"<label style='font-size:13px;'>외부 NIC 번호</label><br/>"+
							"<input name='nlp_rx_nic' value='"+ncPolicy.getNlp_rx_nic()+"' style=\"width:208px\" />"+
						"</li>"+
					"</ul>"+
				"</div>"+
			"</div>";

			result+="<div class='right-content all' "+all+">";
				
			ArrayList<NcLinkPolicy> routeList = ncLinkPolicyService.getPolicyRoute(ncPolicy.getNlp_seq());
			if(routeList.size()==0) {
				result +="<div class='path-wrapper1'>"+
					"<h2>경로1</h2>"+
					"<ul class='box box1_1'>"+
						"<li>"+
							"<label style='font-size:13px;'>출발지 IP</label><br>"+
							"<input name='source_ip' />"+
						"</li>"+
						"<li>"+
							"<label style='font-size:13px;'>출발지 수신 포트</label><br/>"+
							"<input name='nlp_rcv_port' value='"+ncPolicy.getNlp_rcv_port()+"'/>"+
						"</li>"+
						"<li>"+
							"<label style='font-size:13px;'>일방향 전송 포트</label><br/>"+
							"<input name='nlp_trans_port' value='"+ncPolicy.getNlp_trans_port()+"'/>"+
						"</li>"+
						"<li>"+
							"<label style='font-size:13px;'>도착지 IP:포트</label><br/>"+
							"<input name='destination_ip' class='input_1' /> : <input name='destination_port' class='input_2' />"+
						"</li>"+
					"</ul>"+
				"</div>";
			}
			for(int i=0; i<routeList.size(); i++){
				if(i==0){
					result +="<div class='path-wrapper1'>"+
						"<h2>경로1</h2>"+
						"<ul class='box box1_1'>"+
							"<li>"+
								"<label style='font-size:13px;'>출발지 IP</label><br>"+
								"<input name='source_ip' value='"+routeList.get(i).getNpr_source_ip()+"' />"+
							"</li>"+
							"<li>"+
								"<label style='font-size:13px;'>출발지 수신 포트</label><br/>"+
								"<input name='nlp_rcv_port' value='"+ncPolicy.getNlp_rcv_port()+"'/>"+
							"</li>"+
							"<li>"+
								"<label style='font-size:13px;'>일방향 전송 포트</label><br/>"+
								"<input name='nlp_trans_port' value='"+ncPolicy.getNlp_trans_port()+"'/>"+
							"</li>"+
							"<li>"+
								"<label style='font-size:13px;'>도착지 IP:포트</label><br/>"+
								"<input name='destination_ip' class='input_1' value='"+routeList.get(i).getNpr_destination_ip()+"' /> : <input name='destination_port' class='input_2' value='"+routeList.get(i).getNpr_destination_port()+"' />"+
							"</li>"+
						"</ul>"+
					"</div>";
				}else{
					result +="<div id='mrowcnt"+(i+1)+"' name='mrouteinput'><div class='path-wrapper2'>"+
							"<h2><a href='javascript:remove_div1("+(i+1)+")'><img src='/img/box2_icon.png'>경로"+(i+1)+"</a></h2>"+
							"<ul class='box box2_1'>"+
								"<li>"+
									"<label style='font-size:13px;'>출발지 IP</label><br>"+
									"<input name='source_ip' value='"+routeList.get(i).getNpr_source_ip()+"' />"+
								"</li>"+
								"<li><p>* 출발지 수신 포트와 일방향 전송 포트는 동일</p></li>"+
								"<li>"+
									"<label style='font-size:13px;'>도착지 IP:포트</label><br/>"+
									"<input name='destination_ip' class='input_1' value='"+routeList.get(i).getNpr_destination_ip()+"' /> : <input name='destination_port' class='input_2' value='"+routeList.get(i).getNpr_destination_port()+"' />"+
								"</li>"+
							"</ul>"+
						"</div></div>";
				}
			}
			
			result +="<div id='route_field2'></div>"+
				"<div class='path-plus'>"+
					"<h2><a href='javascript:add_div1();'><img src='/img/box_plus_icon.png' alt=''>경로 추가</a></h2>"+
				"</div>"+
			"</div>";
		
		return result;
	}
	
	/**
	 * 설명 팝업
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/desc_popup.do", method = RequestMethod.GET)
    public String desc_popup(@RequestParam("type") int type, Model model){
		if(type==1) {
			ArrayList<NcPolicy> etherType = ncPolicyService.getEtherType();
			model.addAttribute("etherType", etherType);
		}else {
			ArrayList<NcPolicy> ipProtocol = ncPolicyService.getIpProtocol();
			model.addAttribute("ipProtocol", ipProtocol);
		}
		model.addAttribute("type", type);
		return "link_policy/description_popup";
    }
	
	/**
	 * 전송 통제 정책 등록
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_regit(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 같으면 안됨
		 * 2. 같은 프로그램이 있으면 안됨
		 */
		int cnt = ncLinkPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		cnt = ncLinkPolicyService.policy_service_count_prog(policyToRegit);
		if(cnt>0){
			return "dupli_prog";
		}
		
		NcLinkPolicy conts = ncLinkPolicyService.getContsName(policyToRegit.getNtc_seq(), policyToRegit.getNsc_seq(), policyToRegit.getNdc_seq());
		
		String audit_page = "";
		if(policyToRegit.getNlp_div()==1){
			audit_page = "정방향 전송 통제 정책 등록";
		}else{			
			audit_page = "역방향 전송 통제 정책 등록";
		}
		
		String sip="";
		String dip="";
		String dport="";
		
		for(int i=0; i<policyToRegit.getSource_ip().length; i++){
			sip += policyToRegit.getSource_ip()[i]+" ";
			dip += policyToRegit.getDestination_ip()[i]+" ";
			dport += policyToRegit.getDestination_port()[i]+" ";
		}
		
		NcConfig program = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_pro_id());
		NcConfig program1 = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_rxpro_id());
		
		String audit_param = "정책명 = "+policyToRegit.getNlp_name();
		if(policyToRegit.getNtc_seq() == 6) {
			audit_param += ",프로토콜 = Packets";
		} else {
			audit_param += ",프로토콜 = "+conts.getNtc_name()+",서비스 = "+conts.getNdc_name();
		}
		if(policyToRegit.getNlp_div()==1){
			audit_param += ",내부 프로그램명 = "+program.getNcp_name()+",외부 프로그램명 = "+program1.getNcp_name();
		}else{
			audit_param += ",외부 프로그램명 = "+program.getNcp_name()+",내부 프로그램명 = "+program1.getNcp_name();
		}
		audit_param += ",출발지 IP = "+sip+",출발지 수신 포트 = "+policyToRegit.getNlp_rcv_port();
		audit_param += ",일방향 전송 포트 = "+policyToRegit.getNlp_trans_port()+",도착지 IP = "+dip+",도착지 포트 = "+dport;
		audit_param += ",사용 여부 = "+(policyToRegit.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_insert(policyToRegit);
		if (result == 1) {
			if(policyToRegit.getNtc_seq() != 6) { // 패킷이 아닌것만
				int nlp_seq = ncLinkPolicyService.last_service_seq();
				policyToRegit.setNlp_seq(nlp_seq);
				for(int i=0; i<policyToRegit.getSource_ip().length; i++){
					policyToRegit.setNpr_seq(i+1);
					policyToRegit.setNpr_source_ip(policyToRegit.getSource_ip()[i]);
					policyToRegit.setNpr_destination_ip(policyToRegit.getDestination_ip()[i]);
					policyToRegit.setNpr_destination_port(policyToRegit.getDestination_port()[i]);
					ncLinkPolicyService.policy_route_insert(policyToRegit);
				}
			}
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 전송 통제 정책 수정
	 * @param policyToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String policy_update(NcLinkPolicy policyToRegit, Model model, HttpSession session){
		/*
		 * 중복 체크 룰
		 * 1. 정책명이 같으면 안됨
		 * 2. 같은 프로그램이 있으면 안됨
		 */
		int cnt = ncLinkPolicyService.policy_service_count(policyToRegit);
		if(cnt>0){
			return "dupli_name";
		}
		cnt = ncLinkPolicyService.policy_service_count_prog(policyToRegit);
		if(cnt>0){
			return "dupli_prog";
		}
		
		NcLinkPolicy conts = ncLinkPolicyService.getContsName(policyToRegit.getNtc_seq(), policyToRegit.getNsc_seq(), policyToRegit.getNdc_seq());
		
		String audit_page = "";
		if(policyToRegit.getNlp_div()==1){
			audit_page = "정방향 전송 통제 정책 수정";
		}else{			
			audit_page = "역방향 전송 통제 정책 수정";
		}

		String sip="";
		String dip="";
		String dport="";
		
		for(int i=0; i<policyToRegit.getSource_ip().length; i++){
			sip += policyToRegit.getSource_ip()[i]+" ";
			dip += policyToRegit.getDestination_ip()[i]+" ";
			dport += policyToRegit.getDestination_port()[i]+" ";
		}
		
		NcConfig program = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_pro_id());
		NcConfig program1 = ncConfigService.serviceProgramBySeq(policyToRegit.getNlp_rxpro_id());
		
		String audit_param = "정책명 = "+policyToRegit.getNlp_name();
		if(policyToRegit.getNtc_seq() == 6) {
			audit_param += ",프로토콜 = Packets";
		} else {
			audit_param += ",프로토콜 = "+conts.getNtc_name()+",서비스 = "+conts.getNdc_name();
		}
		if(policyToRegit.getNlp_div()==1){
			audit_param += ",내부 프로그램명 = "+program.getNcp_name()+",외부 프로그램명 = "+program1.getNcp_name();
		}else{
			audit_param += ",외부 프로그램명 = "+program.getNcp_name()+",내부 프로그램명 = "+program1.getNcp_name();
		}
		audit_param += ",출발지 IP = "+sip+",출발지 수신 포트 = "+policyToRegit.getNlp_rcv_port();
		audit_param += ",일방향 전송 포트 = "+policyToRegit.getNlp_trans_port()+",도착지 IP = "+dip+",도착지 포트 = "+dport;
		audit_param += ",사용 여부 = "+(policyToRegit.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_update(policyToRegit);
		if (result == 1) {
			if(policyToRegit.getNtc_seq() != 6) { // 패킷이 아닌것만
				ncLinkPolicyService.policy_route_delete(policyToRegit.getNlp_seq()); // 경로 삭제 후 재입력
				for(int i=0; i<policyToRegit.getSource_ip().length; i++){
					policyToRegit.setNpr_seq(i+1);
					policyToRegit.setNpr_source_ip(policyToRegit.getSource_ip()[i]);
					policyToRegit.setNpr_destination_ip(policyToRegit.getDestination_ip()[i]);
					policyToRegit.setNpr_destination_port(policyToRegit.getDestination_port()[i]);
					ncLinkPolicyService.policy_route_insert(policyToRegit);
				}
			}
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 전송 통제 정책 삭제
	 * @param nps_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_delete.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_delete(@RequestParam("nlp_seq") int nlp_seq, HttpSession session) {
		NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
	
		//NdConfig program = ncConfigService.serviceProgramBySeq(policy.getNlp_pro_id());
		
		NcLinkPolicy conts = ncLinkPolicyService.getContsName(policy.getNtc_seq(), policy.getNsc_seq(), policy.getNdc_seq());
		
		String audit_page = "";
		if(policy.getNlp_div()==1){
			audit_page = "정방향 전송 통제 정책 삭제";
		}else{			
			audit_page = "역방향 전송 통제 정책 삭제";
		}
		
		String sip="";
		String dip="";
		String dport="";
		ArrayList<NcLinkPolicy> route = ncLinkPolicyService.getPolicyRoute(nlp_seq);
		for(int i=0; i<route.size(); i++){
			sip += route.get(i).getNpr_source_ip()+" ";
			dip += route.get(i).getNpr_destination_ip()+" ";
			dport += route.get(i).getNpr_destination_port()+" ";
		}
		
		String audit_param = "정책명 = "+policy.getNlp_name();
		if(policy.getNtc_seq() == 6) {
			audit_param += ",프로토콜 = Packets";
		} else {
			audit_param += ",프로토콜 = "+conts.getNtc_name()+",서비스 = "+conts.getNdc_name();
		}
		if(policy.getNlp_div()==1){
			audit_param += ",내부 프로그램명 = "+policy.getNcp_name()+",외부 프로그램명 = "+policy.getRx_ncp_name();
		}else{
			audit_param += ",외부 프로그램명 = "+policy.getNcp_name()+",내부 프로그램명 = "+policy.getRx_ncp_name();
		}
		audit_param += ",출발지 IP = "+sip+",출발지 수신 포트 = "+policy.getNlp_rcv_port();
		audit_param += ",일방향 전송 포트 = "+policy.getNlp_trans_port()+",도착지 IP = "+dip+",도착지 포트 = "+dport;
		audit_param += ",사용 여부 = "+(policy.getNlp_use_yn()==1?"사용":"미사용");
		
		int result = ncLinkPolicyService.policy_service_delete(nlp_seq);
		if (result == 1) {
			ncLinkPolicyService.policy_route_delete(nlp_seq); // 경로 삭제
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 역방향 정책 설정 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reverse_policy.do", method = RequestMethod.GET)
    public String reverse_policy(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		
		ArrayList<ArrayList<NcLinkPolicy>> routeList = new ArrayList<ArrayList<NcLinkPolicy>>();
		
		ArrayList<NcLinkPolicy> policyList = ncLinkPolicyService.getPolicyService(3);
		for(int i=0; i<policyList.size(); i++){
			routeList.add(i, ncLinkPolicyService.getPolicyRoute(policyList.get(i).getNlp_seq()));
		}
		
		model.addAttribute("policyList", policyList);
		model.addAttribute("routeList", routeList);
		
		ArrayList<NcPolicy> topConts = ncPolicyService.getTopConts(); // 상위 콘텐츠
		model.addAttribute("topConts", topConts);
		
		ArrayList<NcPolicy> detailConts = ncPolicyService.getDetailConts(topConts.get(0).getNtc_seq(), 1); // 상세 콘텐츠
		model.addAttribute("detailConts", detailConts);
		model.addAttribute("policyToRegit", new NcLinkPolicy());
		model.addAttribute("programList", ncConfigService.program_rvs_list(1,3));
		model.addAttribute("rxprogramList", ncConfigService.program_rvs_list(1,4));
		return "link_policy/reverse_policy";
    }
	
	/**
	 * 역방향 정책 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reverse_policy_editVw.do", method = RequestMethod.GET)
	public String reverse_policy_editVw(@RequestParam("nlp_seq") int nlp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(500002));
		model.addAttribute("title", ncMenuService.getNowTitle(500002));
		model.addAttribute("menu_id", 500002);
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicy> topConts = ncPolicyService.getTopConts(); // 상위 콘텐츠
		model.addAttribute("topConts", topConts);
		
		NcLinkPolicy ncPolicy = ncLinkPolicyService.getPolicyServiceBySeq(nlp_seq);
		
		ArrayList<NcPolicy> detailConts = ncPolicyService.getDetailConts(ncPolicy.getNtc_seq(), ncPolicy.getNsc_seq()); // 상세 콘텐츠
		model.addAttribute("detailConts", detailConts);
		
		ArrayList<NcLinkPolicy> routeList = ncLinkPolicyService.getPolicyRoute(ncPolicy.getNlp_seq());
		
		model.addAttribute("programList", ncConfigService.program_rvs_list(1,0));
		model.addAttribute("policyToRegit", ncPolicy);
		model.addAttribute("routeList", routeList);
		model.addAttribute("routeCount", routeList.size());
		return "link_policy/reverse_policy_edit";
	}
	
	/**
	 * 전송 통제 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policy_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_delall(HttpServletRequest req, HttpSession session) {
		String nlp_seq[] = req.getParameterValues("delck");
		
		int result = 0;
		String audit_param = "";
		String audit_page = "";
		for(int i=0; i<nlp_seq.length; i++){
			NcLinkPolicy policy = ncLinkPolicyService.getPolicyServiceBySeq(Integer.parseInt(nlp_seq[i]));
			
			//NdConfig program = ncConfigService.serviceProgramBySeq(policy.getNlp_pro_id());
			
			NcLinkPolicy conts = ncLinkPolicyService.getContsName(policy.getNtc_seq(), policy.getNsc_seq(), policy.getNdc_seq());
			
			if(policy.getNlp_div()==1){
				audit_page = "정방향 전송 통제 정책 삭제";
			}else{			
				audit_page = "역방향 전송 통제 정책 삭제";
			}
			
			String sip="";
			String dip="";
			String dport="";
			ArrayList<NcLinkPolicy> route = ncLinkPolicyService.getPolicyRoute(Integer.parseInt(nlp_seq[i]));
			for(int j=0; j<route.size(); j++){
				sip += route.get(i).getNpr_source_ip()+" ";
				dip += route.get(i).getNpr_destination_ip()+" ";
				dport += route.get(i).getNpr_destination_port()+" ";
			}
			
			audit_param = "정책명 = "+policy.getNlp_name()+",프로토콜 = "+conts.getNtc_name()+",서비스 = "+conts.getNdc_name();
			if(policy.getNlp_div()==1){
				audit_param += ",내부 프로그램명 = "+policy.getNcp_name()+",외부 프로그램명 = "+policy.getRx_ncp_name();
			}else{
				audit_param += ",외부 프로그램명 = "+policy.getNcp_name()+",내부 프로그램명 = "+policy.getRx_ncp_name();
			}
			audit_param += ",출발지 IP = "+sip+",출발지 수신 포트 = "+policy.getNlp_rcv_port();
			audit_param += ",일방향 전송 포트 = "+policy.getNlp_trans_port()+",도착지 IP = "+dip+",도착지 포트 = "+dport;
			audit_param += ",사용 여부 = "+(policy.getNlp_use_yn()==1?"사용":"미사용");
			
			result = ncLinkPolicyService.policy_service_delete(Integer.parseInt(nlp_seq[i]));
			if (result == 1) {
				ncLinkPolicyService.policy_route_delete(Integer.parseInt(nlp_seq[i])); // 경로 삭제
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