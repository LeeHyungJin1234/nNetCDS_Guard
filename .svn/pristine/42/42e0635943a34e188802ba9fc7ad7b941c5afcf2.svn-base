package nnsp.controllers;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nnsp.vo.NcOpcTag;
import nnsp.vo.NcPolicyLink;
import nnsp.security.RSAUtil;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcMenuService;
import nnsp.services.NcPolicyLinkService;
import nnsp.services.NcSecurity;

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
public class NcPolicyLinkController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcPolicyLinkController.class);
	
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcPolicyLinkService ncPolicyLinkService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcSecurity ncSecurity;
	/**
	 * 정방향 정책 설정 화면 test
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/policy_link.do", method = RequestMethod.GET)
    public String forward_policy1(NcPolicyLink policyToRegit,Model model, HttpSession session){
		//Top메뉴만
		
		
		//등록 폼 프로그램
		ArrayList<NcPolicyLink> programTx = ncPolicyLinkService.getProgramTxName();
		ArrayList<NcPolicyLink> programRx = ncPolicyLinkService.getProgramRxName();
		
		//리스트
		ArrayList<ArrayList<NcPolicyLink>> rList = new ArrayList<ArrayList<NcPolicyLink>>();
		ArrayList<ArrayList<NcPolicyLink>> rList1 = new ArrayList<ArrayList<NcPolicyLink>>();
		
		//정책
		ArrayList<NcPolicyLink> policyList = ncPolicyLinkService.getPolicyService();
		
		for(int i = 0; i < policyList.size(); i++) {
			rList.add(i, ncPolicyLinkService.getPolicyRoute(policyList.get(i).getNpl_seq()));
			rList1.add(i, ncPolicyLinkService.getPolicyRoute2(policyList.get(i).getNpl_seq()));
		}
		model.addAttribute("programRx", programRx);
		model.addAttribute("programTx", programTx);
		model.addAttribute("policyList", policyList);
		model.addAttribute("rList", rList);
		model.addAttribute("rList1", rList1);
		
		return "policy_link/policy_link";
	}
	@RequestMapping(value = "/insert_policy_link.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_text(NcPolicyLink policyInsert,HttpSession session, Model model,HttpServletRequest request) {
		String[] src_nplr_range = request.getParameterValues("src_nplr_range");
		policyInsert.setSrc_ip(request.getParameterValues("src_nplr_ip"));
		policyInsert.setSrc_end_ip(request.getParameterValues("src_nplr_end_ip"));
		policyInsert.setDst_ip(request.getParameterValues("dst_nplr_ip"));
		String[] Dst_port = request.getParameterValues("nplr_dst_port");
	    
		int[] int_nplr_range = new int[src_nplr_range.length]; 
		int[] int_dst_port= new int[Dst_port.length];

		for(int i=0;i<Dst_port.length;i++){
			int_dst_port[i]=Integer.parseInt(Dst_port[i]);
		}
		for(int i=0;i<src_nplr_range.length;i++){
			int_nplr_range[i]=Integer.parseInt(src_nplr_range[i]);
		}
		
		
		
		
		int result = ncPolicyLinkService.insert_policylink(policyInsert);
		if (result == 1) {
				int npl_seq = ncPolicyLinkService.last_policy_seq();
				policyInsert.setNpl_seq(npl_seq);
				for(int i=0; i< policyInsert.getSrc_ip().length; i++) {
					policyInsert.setNplr_seq(i+1);
					policyInsert.setNplr_ip(policyInsert.getSrc_ip()[i]);
					policyInsert.setNplr_end_ip(policyInsert.getSrc_end_ip()[i]);
					policyInsert.setNplr_dst_port(int_dst_port[i]);
					policyInsert.setNplr_dst_ip(policyInsert.getDst_ip()[i]);
					policyInsert.setNplr_range(int_nplr_range[i]);
					
					ncPolicyLinkService.pl_route_insert(policyInsert);
					
					ncPolicyLinkService.pl_route_insert2(policyInsert);
			}
		}
		if (result == 1) {
			//ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		}else {
			return "DB Error";
		}
	}
	/**
	 * 서비스 변경
	 * @param nts_seq
	 * @return
	 */
	@RequestMapping(value = "/change_type.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_type(@RequestParam("ntc_seq") int ntc_seq) {
		ArrayList<NcPolicyLink> contsList = ncPolicyLinkService.getDetailConts(ntc_seq);
		
		String strReturn = "<select  class='pop_select' name='npl_tx_nts_seq' id='npl_tx_nts_seq' onchange='wri_set_tx_proto(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNts_seq()+"'>"+contsList.get(i).getNts_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 서비스 변경
	 * @param nts_seq
	 * @return
	 */
	@RequestMapping(value = "/change_type2.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_type2(@RequestParam("ntc_seq") int ntc_seq) {
		ArrayList<NcPolicyLink> contsList = ncPolicyLinkService.getDetailConts(ntc_seq);
		
		String strReturn = "<select  class='pop_select' name='npl_tx_nts_seq' id='mod_npl_tx_nts_seq' onchange='mod_set_tx_proto(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNts_seq()+"'>"+contsList.get(i).getNts_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 서비스 변경
	 * @param nts_seq
	 * @return
	 */
	@RequestMapping(value = "/change_type3.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_type3(@RequestParam("ntc_seq") int ntc_seq) {
		ArrayList<NcPolicyLink> contsList = ncPolicyLinkService.getDetailConts(ntc_seq);
		
		String strReturn = "<select  class='pop_select' name='npl_rx_nts_seq' id='npl_rx_nts_seq' onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect; wri_set_rx_proto(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNts_seq()+"'>"+contsList.get(i).getNts_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 서비스 변경
	 * @param nts_seq
	 * @return
	 */
	@RequestMapping(value = "/change_type4.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String change_type4(@RequestParam("ntc_seq") int ntc_seq) {
		ArrayList<NcPolicyLink> contsList = ncPolicyLinkService.getDetailConts(ntc_seq);
		
		String strReturn = "<select  class='pop_select' name='npl_rx_nts_seq' id='mod_npl_rx_nts_seq' onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect; mod_set_rx_proto(this.value);'>";
		for(int i=0; i<contsList.size(); i++){
			strReturn += "<option value='"+contsList.get(i).getNts_seq()+"'>"+contsList.get(i).getNts_name()+"</option>";
		}
		strReturn += "</select>";
		
		return strReturn;
	}
	
	/**
	 * 전송 통제 정책 삭제
	 * @param npl_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete_policy_link.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete_policy_link(@RequestParam("npl_seq") int npl_seq, HttpSession session) {
		int result = ncPolicyLinkService.delete_policy_link(npl_seq);
		if (result == 1) {
			ncPolicyLinkService.delete_policy_route(npl_seq); // 경로 삭제
			return "true";
		} else {
			return "DB Error";
		}
	}
	
	
	/**
	 * 전송 통제 정책 삭제
	 * @param npl_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/go_modify.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> go_modify_policy_link(@RequestParam("npl_seq") int npl_seq, HttpSession session) {
		NcPolicyLink policyListOne = ncPolicyLinkService.getPolicyOne(npl_seq);
		//리스트
		NcPolicyLink rList = ncPolicyLinkService.getsrcRoute(npl_seq);
		NcPolicyLink rList1 = ncPolicyLinkService.getdstRoute(npl_seq);
		
		ArrayList<NcOpcTag> opcTagList = ncPolicyLinkService.getopcTagList(policyListOne.getNpl_no());
		
		
		ArrayList<NcPolicyLink> policyList = ncPolicyLinkService.getPolicyService();
		ArrayList<Integer> al = new ArrayList<>();
		int check = 0;
		for(int j = 1; j <= 10; j++) {
			for(int i = 0; i < policyList.size(); i++) {
				if(policyList.get(i).getNpl_no() == j && check == 0) {
					
					check =1;
				}
			}
			if(check == 0) {
				al.add(j);
			}
			check = 0;
		}
		al.add(policyListOne.getNpl_no());
		Collections.sort(al);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("policyListOne", policyListOne);
		map.put("rList", rList);
		map.put("rList1", rList1);
		map.put("al", al);
		map.put("oList", opcTagList);
		
		return map;
	}
	/**
	 * 정방향 정책 설정 화면
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@RequestMapping(value = "/policy_link2.do", method = RequestMethod.GET)
    public String forward_policy(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException{
		// 메뉴 만들기 시작
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		// 메뉴 만들기 끝
		// 메뉴 만들기 끝
		
		ArrayList<NcPolicyLink> policyList = ncPolicyLinkService.getPolicyService(); //정책 리스트
		
		ArrayList<NcPolicyLink> cType = ncPolicyLinkService.getctName();
		ArrayList<NcPolicyLink> sType = ncPolicyLinkService.getstName(cType.get(0).getNtc_seq());
		ArrayList<NcPolicyLink> sType2 = ncPolicyLinkService.getstName3();
		ArrayList<ArrayList<NcPolicyLink>> srcList = new ArrayList<ArrayList<NcPolicyLink>>();
		ArrayList<ArrayList<NcPolicyLink>> dstList = new ArrayList<ArrayList<NcPolicyLink>>();
		for(int i = 0; i < policyList.size(); i++) {
			srcList.add(i, ncPolicyLinkService.getPolicyRoute(policyList.get(i).getNpl_seq()));
			dstList.add(i, ncPolicyLinkService.getPolicyRoute2(policyList.get(i).getNpl_seq()));
		}
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		ncSecurity.setPrivateKey(privateKey);
		
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
 
		model.addAttribute("RSAModulus", publicKeyModulus);
		model.addAttribute("RSAExponent", publicKeyExponent);
		model.addAttribute("sType", sType);
		model.addAttribute("cType", cType);
		model.addAttribute("policyList", policyList);
		model.addAttribute("srcList", srcList);
		model.addAttribute("dstList", dstList);
		model.addAttribute("sType2", sType2);
		
		return "policy_link/policy_link2";
    }
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/insert_policy_link2.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_policy_link2 (NcPolicyLink policyInsert,HttpSession session, Model model,HttpServletRequest request) {
		
		NcPolicyLink cType = ncPolicyLinkService.getctName2(policyInsert.getNpl_tx_nts_seq());
		NcPolicyLink sType = ncPolicyLinkService.getstName2(policyInsert.getNpl_rx_nts_seq());
		
		String audit_page = "전송 통제 정책 등록";
		String audit_param = "정책명 = "+policyInsert.getNpl_name()+",";
		
		String server_name = policyInsert.getNplr_server_name();
		server_name = server_name.replaceAll(",", "");
		policyInsert.setNplr_server_name(server_name);
		
		String server_name2 = policyInsert.getNplr_server_name2();
		server_name2 = server_name2.replaceAll(",", "");
		policyInsert.setNplr_server_name2(server_name2);
		
		String tx_id = policyInsert.getNpl_tx_id();
		tx_id = tx_id.replaceAll(",", "");
		policyInsert.setNpl_tx_id(tx_id);
		
		String rx_id = policyInsert.getNpl_rx_id();
		rx_id = rx_id.replaceAll(",", "");
		policyInsert.setNpl_rx_id(rx_id);
		
		
		String tx_pw = policyInsert.getNpl_tx_pw();
		tx_pw = tx_pw.replaceAll(",", "");
		policyInsert.setNpl_tx_pw(tx_pw);
		
		String rx_pw = policyInsert.getNpl_rx_pw();
		rx_pw = rx_pw.replaceAll(",", "");
		policyInsert.setNpl_rx_pw(rx_pw);
		
		if(policyInsert.getNplr_port() == "") {
			policyInsert.setNplr_port("0");
		}
		if(policyInsert.getNplr_port2() == "") {
			policyInsert.setNplr_port2("0");
		}
		
		
		audit_param += 
				"내부 프로토콜명 =" + cType.getNts_name() + ",내부 Ip =" + policyInsert.getNplr_ip() + "내부 포트 =" + policyInsert.getNplr_port() +
				",외부 프로토콜명 =" + sType.getNts_name() + ",외부 Ip =" + policyInsert.getNplr_ip2() + "외부 포트 =" + policyInsert.getNplr_port2() +
				"사용 여부 = " + (policyInsert.getNpl_use_yn()==1?"사용":"미사용");
		
		
		int result = ncPolicyLinkService.insert_policylink2(policyInsert);
		if (result == 1) {
			// 새로 입력한 정책 및 key값 조회
			int npl_seq = ncPolicyLinkService.last_policy_seq();
			NcPolicyLink ncpolicy = ncPolicyLinkService.getPolicySalt(npl_seq);
			policyInsert.setNpl_seq(npl_seq);
			
			// 내부 외부 접속 정보 저장
			int result1 = ncPolicyLinkService.insert_srcIp(policyInsert);
			int result2 = ncPolicyLinkService.insert_dstIp(policyInsert);
			
			//tx_passwd aria256암호화 시작
			/*if(ndpolicy.getNpl_tx_ntc_seq() != 7) {
				String salt = ndpolicy.getNpl_regdttm();
				int result7 = ncPolicyLinkService.tx_password_update(npl_seq, tx_pw, salt,1);
				int result8 = ncPolicyLinkService.rx_password_update(npl_seq, rx_pw, salt,2);
			}*/
			
			
			// OPC 태그 정보 입력
			if(policyInsert.getNpl_tx_nts_seq()==6) {
				String[] not_tx_tag = request.getParameterValues("w_not_tx_tag");
				String[] not_rx_tag = request.getParameterValues("w_not_rx_tag");
				String[] not_type = request.getParameterValues("w_not_type");
				for(int i=0; i<not_tx_tag.length; i++) {
					int iNot_type = Integer.parseInt(not_type[i]);
					ncPolicyLinkService.insertOpcTag(policyInsert.getNpl_no(), not_tx_tag[i], not_rx_tag[i], iNot_type);
				}
			}
			
		
		}
		if (result == 1) {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I");
			return "true";
		}else {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I");
			return "DB Error";
		}
	}
	
	
	
	/**
	 * 정책 사용 여부 변경
	 * @param nlp_use_yn
	 * @param nlp_seq
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/policy_link_use_change.do", method = RequestMethod.POST)
	@ResponseBody
    public String policy_use_change(@RequestParam("npl_use_yn") int npl_use_yn, @RequestParam("npl_seq") int npl_seq, HttpSession session, Model model){		
		int result = ncPolicyLinkService.policy_use_change(npl_use_yn, npl_seq);
		String audit_page = "전송 통제 정책 수정";
		String audit_param = "";
		
		if(npl_use_yn==0){
			audit_param = "정책 사용 여부를 미사용으로 변경";
		}else{
			audit_param = "정책 사용 여부를 사용으로 변경";
		}
		
		if (result == 1) {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I");
			return "true";
		} else {
			return "DB Error";
		}
    }
	/**
	 * 전송 통제 정책 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/policylink_delete.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_delall(HttpServletRequest req, HttpSession session) {
		String nlp_seq[] = req.getParameterValues("delck");
		
		int result = 0;
		String audit_page = "전송 통제 정책 삭제";
		String audit_param = ""; 
		for(int i=0; i<nlp_seq.length; i++){
			NcPolicyLink policy =  ncPolicyLinkService.getPolicyServiceBySeq(Integer.parseInt(nlp_seq[i]));
			//NdPolicyLink cType = ncPolicyLinkService.getctName3(Integer.parseInt(nlp_seq[i]));
			//NdPolicyLink sType = ncPolicyLinkService.getstName3(Integer.parseInt(nlp_seq[i]));
			
			//NdPolicyLink r1 =  ncPolicyLinkService.getPolicyr1(Integer.parseInt(nlp_seq[i]));
			//NdPolicyLink r2 =  ncPolicyLinkService.getPolicyr2(Integer.parseInt(nlp_seq[i]));
			
			result = ncPolicyLinkService.policy_service_delete(Integer.parseInt(nlp_seq[i]));
			
			audit_param += "정책명 =" + policy.getNpl_name() + ",";
			
			if (result == 1) {
				ncPolicyLinkService.policy_route_delete(Integer.parseInt(nlp_seq[i])); // 경로 삭제
				ncPolicyLinkService.deleteOpcTag(policy.getNpl_no()); // OPC TAG 삭제
			}
		}
		
		if (result == 1) {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I");
			return "true";
		}else{
			return "DB Error";
		}
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "/update_policy_link.do", produces="text/plain;charset=UTF-8",  method = RequestMethod.POST)
	@ResponseBody
	public String update_policy_link (NcPolicyLink policyInsert,HttpSession session, Model model,HttpServletRequest request) {
		NcPolicyLink cType = ncPolicyLinkService.getctName2(policyInsert.getNpl_tx_nts_seq());
		NcPolicyLink sType = ncPolicyLinkService.getstName2(policyInsert.getNpl_rx_nts_seq());
		
		String audit_page = "전송 통제 정책 수정";
		String audit_param = "정책명 = "+policyInsert.getNpl_name() +",";
		
		String server_name = policyInsert.getNplr_server_name();
		server_name = server_name.replaceAll(",", "");
		policyInsert.setNplr_server_name(server_name);
		
		String server_name2 = policyInsert.getNplr_server_name2();
		server_name2 = server_name2.replaceAll(",", "");
		policyInsert.setNplr_server_name2(server_name2);
		
		String tx_id = policyInsert.getNpl_tx_id();
		tx_id = tx_id.replaceAll(",", "");
		policyInsert.setNpl_tx_id(tx_id);
		
		String rx_id = policyInsert.getNpl_rx_id();
		rx_id = rx_id.replaceAll(",", "");
		policyInsert.setNpl_rx_id(rx_id);
		
		String tx_pw = policyInsert.getNpl_tx_pw();
		tx_pw = tx_pw.replaceAll(",", "");
		policyInsert.setNpl_tx_pw(tx_pw);
		
		String rx_pw = policyInsert.getNpl_rx_pw();
		rx_pw = rx_pw.replaceAll(",", "");
		policyInsert.setNpl_rx_pw(rx_pw);
		
		if(policyInsert.getNplr_port() == "") {
			policyInsert.setNplr_port("0");
		}
		if(policyInsert.getNplr_port2() == "") {
			policyInsert.setNplr_port2("0");
		}

		audit_param += 
			"내부 프로토콜명 =" + cType.getNts_name() + ",내부 Ip =" + policyInsert.getNplr_ip() + "내부 포트 =" + policyInsert.getNplr_port() +
			",외부 프로토콜명 =" + sType.getNts_name() + ",외부 Ip =" + policyInsert.getNplr_ip2() + "외부 포트 =" + policyInsert.getNplr_port2() +
			"사용 여부 = " + (policyInsert.getNpl_use_yn()==1?"사용":"미사용");
		
		int npl_seq = policyInsert.getNpl_seq();
		
		int result = ncPolicyLinkService.update_policylink(policyInsert);
		if (result == 1) {
		
		//salt값 조회
		//policyInsert.setNpl_seq(npl_seq);
		NcPolicyLink ncpolicy = ncPolicyLinkService.getPolicySalt(npl_seq);
		ncPolicyLinkService.deleteOpcTag(policyInsert.getNpl_no());
		
		int result1 = ncPolicyLinkService.update_srcIp(policyInsert);
		int result2 = ncPolicyLinkService.update_dstIp(policyInsert);
		//tx_passwd aria256암호화 시작
		/*if(ndpolicy.getNpl_tx_ntc_seq() != 7) {
				
		}*/
		if(policyInsert.getNpl_tx_nts_seq()==6) {
			String[] not_tx_tag = request.getParameterValues("m_not_tx_tag");
			String[] not_rx_tag = request.getParameterValues("m_not_rx_tag");
			String[] not_type = request.getParameterValues("m_not_type");
			for(int i=0; i<not_tx_tag.length; i++) {
				int iNot_type = Integer.parseInt(not_type[i]);
				ncPolicyLinkService.insertOpcTag(policyInsert.getNpl_no(), not_tx_tag[i], not_rx_tag[i], iNot_type);
			}
		}
		//int result3 = ncPolicyLinkService.update_fileSend(policyInsert);
		//int result4 = ncPolicyLinkService.update_fileExcept(policyInsert);
		//int result5 = ncPolicyLinkService.update_fileTx(policyInsert);
		//int result6 = ncPolicyLinkService.update_fileRx(policyInsert);
		
		}
		if (result == 1) {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I");
			return "true";
		}else {
			ncAuditService.policy_log_insert2((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I");
			return "DB Error";
		}
	}
	/**
	 * 회원정보 수정에서 패스워드 변경
	 * @param req
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/pwchange_policyedit.do", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String pwchange_useredit(NcPolicyLink policyInsert,HttpServletRequest req, Model model, HttpSession session) {
		
		NcPolicyLink ncpolicy = ncPolicyLinkService.getPolicySalt(policyInsert.getNpl_seq());
		
		//암호화 추가1
		PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            return "rsa_error1";
        }
    	NcPolicyLink rList = ncPolicyLinkService.getsrcRoute(policyInsert.getNpl_seq());
    	String new_pw="", orig_pw=""; 
    	try {
    		new_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd"));
			orig_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("orig_passwd"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "rsa_error2";
		}
    	//추가1 끝
    	
		// 현재 비밀번호가 맞는지 확인
		String encodePassword = orig_pw; //passwordEncoder.encodePassword(orig_pw, ncpolicy.getNpl_regdttm());
		if(!encodePassword.equals(rList.getNplr_pw())) {
			return "not_match_passwd";
			
		}

		//현재시간을 암호키로 사용하기 위하여 현재시간을 불러옴
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        
		int result = ncPolicyLinkService.tx_password_update(policyInsert.getNpl_seq(), new_pw, today,1);
		if (result == 1) {	
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"패스워드 변경",null,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"패스워드 변경",null,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/pwchange_policyedit2.do", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String pwchange_useredit2(NcPolicyLink policyInsert,HttpServletRequest req, Model model, HttpSession session) {
		NcPolicyLink ncpolicy = ncPolicyLinkService.getPolicySalt(policyInsert.getNpl_seq());
		
		//암호화 추가1
		PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            return "rsa_error1";
        }
    	NcPolicyLink rList = ncPolicyLinkService.getdstRoute(policyInsert.getNpl_seq());
    	String new_pw="", orig_pw=""; 
    	try {
    		new_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd"));
			orig_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("orig_passwd"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "rsa_error2";
		}
    	//추가1 끝
    	
		// 현재 비밀번호가 맞는지 확인
		String encodePassword = orig_pw; // passwordEncoder.encodePassword(orig_pw, ncpolicy.getNpl_regdttm());
		if(!encodePassword.equals(rList.getNplr_pw())) {
			return "not_match_passwd";
			
		}

		// 문자, 숫자, 특수문자의 조합인지 확인
		//현재시간을 암호키로 사용하기 위하여 현재시간을 불러옴
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        
		int result = ncPolicyLinkService.rx_password_update(policyInsert.getNpl_seq(), new_pw, today,2);
		if (result == 1) {	
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"패스워드 변경",null,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"패스워드 변경",null,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
}