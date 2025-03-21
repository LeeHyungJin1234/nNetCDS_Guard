package nnsp.controllers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import nnsp.common.PageInfo;
import nnsp.mappers.NcConfigMapper;
import nnsp.security.AES256Util;
import nnsp.security.Constants;
import nnsp.security.NcSecurityUtil;
import nnsp.security.RSAUtil;
import nnsp.services.NcAlarmService;
import nnsp.services.NcAuditService;
import nnsp.services.NcLinkPolicyService;
import nnsp.services.NcMenuService;
import nnsp.services.NcPolicyService;
import nnsp.services.NcSecurity;
import nnsp.services.NcTransferService;
import nnsp.services.NcUserService;
import nnsp.util.InetAddressUtils;
import nnsp.util.MacaddrUtil;
import nnsp.util.MessageUtil;
import nnsp.util.StringUtil;
import nnsp.services.NcConfigService;
import nnsp.vo.NcConfig;
import nnsp.vo.NcLinkPolicy;
import nnsp.vo.NcLog;
import nnsp.vo.NcSysLoad;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import inet.ipaddr.MACAddressString;

@Controller
public class NcConfigController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcConfigController.class);

	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcPolicyService ncPolicyService;
	@Autowired
	NcLinkPolicyService ncLinkPolicyService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcTransferService ncTransferService;
	@Autowired
	NcAlarmService ncAlarmService;
	@Autowired
	NcUserService ncUserService;
	@Autowired
	NcSecurity ncSecurity;
	
	/**
	 * 로그 관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_log.do", method = RequestMethod.GET)
	public String config_log(Model model) {
		model.addAttribute("config_log_data", ncConfigService.getConfigLog());
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		return "config/config_log";
	}

	/**
	 * 로그 수정
	 * @param ncl_cycle
	 * @param ncl_server_ip
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modify_log.do", method = RequestMethod.POST)
	@ResponseBody
	public String modify_log(@RequestParam("ncl_cycle") int ncl_cycle, HttpSession session) {
		
		if(ncl_cycle < 70 || ncl_cycle > 95) {
			return "디스크 임계값은 70~95 사이의 숫자만 입력하세요.";
		}
		
		int result = ncConfigService.config_log_update(ncl_cycle);
		String audit_param = "디스크 임계값 설정 = "+ncl_cycle+"%";
		
		if (result == 1) {
			//ndTransferService.trans_conf_log(ncl_cycle, ncl_server_ip); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"로그 관리 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"로그 관리 수정",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 로그인 설정 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_login.do", method = RequestMethod.GET)
	public String config_login(Model model) {
		model.addAttribute("config_login_data", ncConfigService.getConfigLogIn());
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
				
		return "config/config_login";
	}
	
	
	/**
	 * 로그인 설정
	 * @param ncli_lock_failcnt
	 * @param ncli_lock_date
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modify_login.do", method = RequestMethod.POST)
	@ResponseBody
	public String modify_login(@RequestParam("ncli_lock_failcnt") int ncli_lock_failcnt, @RequestParam("ncli_lock_date") int ncli_lock_date, HttpSession session) {
		
		 if(ncli_lock_failcnt < 2 || ncli_lock_failcnt > 5 ) {
			 return "계정잠김 로그인 실패 횟수는 2~5 사이의 숫자만 입력하세요.";
		 }
		 
		 if(ncli_lock_date < 5 || ncli_lock_date > 30) {
			 return "계정 잠김 시간은 5~30 사이의 숫자만 입력하세요.";
		 }
			
		int result = ncConfigService.config_login_update(ncli_lock_failcnt, ncli_lock_date);
		
		String audit_param = "계정잠김 로그인 실패 횟수 = "+ncli_lock_failcnt+"회,계정 잠김 시간 = "+ncli_lock_date+"분";
		if (result == 1) {
			//ndTransferService.trans_conf_login(ncli_lock_failcnt, ncli_lock_date); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"로그인 설정 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"로그인 설정 수정",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}	
	}
	
	/**
	 * 메일 설정 페이지
	 * 
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@RequestMapping(value = "/config_email.do")
	public String config_email(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
		NcConfig confEmail = ncConfigService.getConfigeEmail();
		String audit_page = MessageUtil.getMessage("menu.mailset");

		//String strDek = ndConfigService.getEmailDek();//   dek 값 가져오기
	    //String strDecEmailPW = nnsp.security.ARIAUtil.ariaDecrypt(confEmail.getNce_pw(), strDek,audit_page);
	    //confEmail.setNce_pw(strDecEmailPW);
		confEmail.setNce_pw("");
		
		String regex_ip = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	    boolean isIP = Pattern.matches(regex_ip, confEmail.getNce_host());
	    confEmail.setNce_host_type(isIP ? 2/* IP */ : 1/* 도메인명*/);
	    
	    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair keyPair = generator.genKeyPair();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		//session.setAttribute("_RSA_WEB_Key_", privateKey); // RSA 개인키를 세션에 저장
		ncSecurity.setPrivateKey(privateKey);
		
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
 
		model.addAttribute("RSAModulus", publicKeyModulus);
		model.addAttribute("RSAExponent", publicKeyExponent);
	    
		model.addAttribute("config_email_data", confEmail);
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
				
		return "config/config_email";
	}
	
	/**
	 * 메일 설정
	 * @param emailToUpdate
	 * @param session
	 * @return
	 * @throws Exception 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@RequestMapping(value = "/modify_email.do")
	@ResponseBody
	public String modify_email(NcConfig emailToUpdate, HttpSession session) throws Exception {
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
	    NcUser ncUser = ncUserService.getUserbySeq(nsuser.getNsu_seq());
	    if (nsuser.getNsu_seq() < 1 || ncUser.getNsu_id() == null || ncUser.getNsu_id().equals("")) {
	      return "Manager Error";
	    }
	    
	    String audit_page = MessageUtil.getMessage("log.modifymailsettings");
	    
	    PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            throw new AuthenticationServiceException("NOT_FIND_PRIVATEKEY");
        }
    	
    	String tPw = RSAUtil.decryptRsa(privateKey, emailToUpdate.getNce_pw());
    	
	    String newPw = NcSecurityUtil.getRotatedString(tPw, Constants.ROTATE);
	    String oldPw = ncConfigService.getConfigePw();
	    
	    if(oldPw==null || oldPw.isEmpty()) {
	    	audit_page = MessageUtil.getMessage("menu.firstmailset");
	    	emailToUpdate.setNce_use_yn(1);//   처음 등록때는 메일서 사용상태로 설정
	    }
	    else {
	        NcConfig confEmail = ncConfigService.getConfigeEmail();
	        emailToUpdate.setNce_use_yn(confEmail.getNce_use_yn());
	    }
	    
	    emailToUpdate.setNce_pw(newPw);
	    String strPwMessage = "";
	    if (!emailToUpdate.getNce_pw().isEmpty() && !emailToUpdate.getNce_pw().equals(emailToUpdate.getOrg_nce_pw())) {
	    	String strDek = ncConfigService.getEmailDek(); // 메일 dek 확인

	        if (strDek == null || strDek.isEmpty() || strDek.equals("")) {
	    		return "KEY Error";
	        }
			String strEecEmailPW = nnsp.security.ARIAUtil.ariaEncrypt(emailToUpdate.getNce_pw(), strDek, audit_page);
	        emailToUpdate.setNce_pw(strEecEmailPW);
	      try {
	        strDek.close();
	      } catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	      if (!InetAddressUtils.isEmailPw(emailToUpdate.getNce_pw())) {
	        	return "email_pwwrong";
	        }
	        else {
	        	if(oldPw!=null && !oldPw.isEmpty()) {
	        		strPwMessage = MessageUtil.getMessage("log.user.editpw")+", ";
	        	}
	        	else {
	        		strPwMessage = MessageUtil.getMessage("log.user.regitpw")+", ";
	        	}
	        }
	    } else {// 비멀번호 입력값이 없으면 기존 비밀번호로 설정
	    	if(oldPw!=null && !oldPw.isEmpty()) {
	    	      NcConfig email = ncConfigService.getConfigeEmail();
	    	      emailToUpdate.setNce_pw(email.getNce_pw());
	        }
	    	else {
	    		return "email_pwinput"; 
	    	}
	    }

	    if (emailToUpdate.getNce_host_type() == 2) {
	      if (!InetAddressUtils.isIPv4Address(emailToUpdate.getNce_host())) {
	        return "not_ip";
	      }
	    } else {
	      if (!InetAddressUtils.isDomain(emailToUpdate.getNce_host())) {
	        return "not_domain";
	      }
	    }

	    if (emailToUpdate.getNce_port() < 1
	      || emailToUpdate.getNce_port() > 65535) {
	      return "port_wrong";
	    }
	    // if(!InetAddressUtils.isEmailAddr(emailToUpdate.getNce_from_email()))
	    // 	return "email_wrong";
	    // if(!InetAddressUtils.isEmailId(emailToUpdate.getNce_id()))
	    // 	return "email_idwrong";

	    //  특수문자 html 코드 변환 방지
	    emailToUpdate.setNce_from_email(
	      HtmlUtils.htmlUnescape(emailToUpdate.getNce_from_email())
	    );

	    int result = ncConfigService.config_email_update(emailToUpdate);

	    String audit_param = MessageUtil.getMessage("log.mailserveraddress") + " = "
	      + emailToUpdate.getNce_host() + ", "
	      + MessageUtil.getMessage("log.mailserverport") + " = " +
	      emailToUpdate.getNce_port() + ", "
	      + MessageUtil.getMessage("log.mailserverid") + " = "
	      + emailToUpdate.getNce_id() + ", "
	      + strPwMessage
	      + MessageUtil.getMessage("log.mailaddressoutgoing") + " = "
	      + emailToUpdate.getNce_from_email();
	    String audit_result = NcLog.getResultSF(result);

	    ncAuditService.policy_log_insert(
	      (String) session.getAttribute("loginUserId"),
	      (String) session.getAttribute("clientIp"), audit_page, audit_param,
	      audit_result, "I"); // 감사로그 저장

	    if((oldPw==null || oldPw.isEmpty()) && result>0) {//  메일 초기 설정이 정상적으로 완료 됬다면 
	    	ncConfigService.update_complete_date();  // 초기설정 완료 날짜 기록
	    }
	    else {
	    	try {
	    		oldPw.close();
	    	} catch (Exception e) {
	    		throw new RuntimeException(e);
	    	}
	    }
	    try {
	    	tPw.close();
	    	newPw.close();
	    } catch (Exception e) {
	    	throw new RuntimeException(e);
	    }
	    
	    return NcLog.getResultTD(result);
	}
	
	@PostMapping(value = "/mail_use_yn.do")
	@ResponseBody
	public String mail_use_yn(@RequestParam("nce_use_yn") int nce_use_yn, HttpSession session) {
		//  관리자가 로그인된 상태가 아니면 실행 금지
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
		NcUser ncUser = ncUserService.getUserbySeq(nsuser.getNsu_seq());
		if (nsuser.getNsu_seq() < 1 || ncUser.getNsu_id() == null || ncUser.getNsu_id().equals("")) {
		  return "Manager Error";
		}
		
		int result = ncConfigService.mail_use_yn(nce_use_yn);
		
		String audit_page = MessageUtil.getMessage("config.emailchangeuse");
		String audit_param = MessageUtil.getMessage("config.emailchangeuseno");
		if(nce_use_yn==1) {
			audit_param = MessageUtil.getMessage("config.emailchangeuseyes");;
		}
		else if(nce_use_yn==0) {
			audit_param = MessageUtil.getMessage("config.emailchangeuseno");;
		}
		else {
			return "DB Error";
		}
		
		String audit_result = NcLog.getResultSF(result);
		
		ncAuditService.policy_log_insert(
		  (String) session.getAttribute("loginUserId"),
		  (String) session.getAttribute("clientIp"), audit_page, audit_param,
		  audit_result, "I"); // 감사로그 저장
	
		return NcLog.getResultTD(result);
	}
	
	/**
	 * 송/수신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_network.do", method = RequestMethod.GET)
    public String config_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		//ArrayList<NdConfig> sendList = ncConfigService.getConfigSystem(1); // 1:송신 2:수신
		//model.addAttribute("sendList", sendList);
		
		//ArrayList<NdConfig> receiveList = ncConfigService.getConfigSystem(2); // 1:송신 2:수신
		//model.addAttribute("receiveList", receiveList);
		
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_system";
    }
	
	/**
	 * 송신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_snd_network.do", method = RequestMethod.GET)
    public String config_snd_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		NcConfig sendList = ncConfigService.getConfigSystem(1); // 1:송신 2:수신
		
		String str = sendList.getNcs_antivirus();

	    String[] st = str.split("((?=\\())");

	    sendList.setNcs_antivirus1(st[0]);
	    sendList.setNcs_antivirus2(st[1]);
	    
		model.addAttribute("sendList", sendList);
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_snd_system";
    }
	
	/**
	 * 수신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_rcv_network.do", method = RequestMethod.GET)
    public String config_rcv_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		NcConfig receiveList = ncConfigService.getConfigSystem(2); // 1:송신 2:수신
		model.addAttribute("receiveList", receiveList);
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_rcv_system";
    }
	
	/**
	 * 시스템설정 송/수신 네트워크 설정 등록
	 * @param systemToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/system_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String system_regit(NcConfig systemToRegit, Model model, HttpSession session){
		int result = ncConfigService.config_system_insert(systemToRegit);	
		
		String audit_param = "";
		String audit_page = "";
		if(systemToRegit.getNcs_div()==2){
			audit_page = "외부 수신 시스템 네트워크 설정 등록";
			audit_param = "Master MAC = "+systemToRegit.getNcs_master_mac()+",Slave MAC = "+systemToRegit.getNcs_slave_mac();
		}else if(systemToRegit.getNcs_div()==4){
			audit_page = "내부 수신 시스템 네트워크 설정 등록";
			audit_param = "Master MAC = "+systemToRegit.getNcs_master_mac()+",Slave MAC = "+systemToRegit.getNcs_slave_mac();
		}
		
		String audit_result = NcLog.getResultTD(result);
		
		ncAuditService.policy_log_insert(
			      (String) session.getAttribute("loginUserId"),
			      (String) session.getAttribute("clientIp"), audit_page, audit_param,
			      audit_result, "I"); // 감사로그 저장
		
		return NcLog.getResultTD(result);
    }
	
	/**
	 * 시스템설정 송/수신 네트워크 설정 수정
	 * @param systemToUpdate
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/system_update.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	// TODO: message converters 설정?
	@ResponseBody
	public String system_update(@Valid NcConfig systemToUpdate, BindingResult br, Model model, HttpSession session) {
		if (br.hasErrors()) {
			String msgKey = br.getAllErrors().get(0).getDefaultMessage();
			return MessageUtil.getMessage(msgKey);
		}

		// validate mac address
		MACAddressString ncs_master_mac = new MACAddressString(systemToUpdate.getNcs_master_mac());
		MACAddressString ncs_slave_mac = new MACAddressString(systemToUpdate.getNcs_slave_mac());

		if (!ncs_master_mac.isValid()) {
			return MessageUtil.getMessage("config.mastermacincorrect");
		} else if (!ncs_slave_mac.isValid()) {
			return MessageUtil.getMessage("config.slavemacincorrect");
		}

		int result = ncConfigService.config_system_update(systemToUpdate);

		String audit_page = systemToUpdate.getNcs_div() == 1 ? MessageUtil.getMessage("log.modifyintsystem") : MessageUtil.getMessage("log.modifyextsystem");
		String audit_param = "Master NIC = "
			+ systemToUpdate.getNcs_master_nic() + ", Master MAC = "
			+ systemToUpdate.getNcs_master_mac() + ", Slave NIC = "
			+ systemToUpdate.getNcs_slave_nic() + ", Slave MAC = "
			+ systemToUpdate.getNcs_slave_mac();

		String audit_result = NcLog.getResultSF(result);

		ncAuditService.policy_log_insert(
			(String) session.getAttribute("loginUserId"),
			(String) session.getAttribute("clientIp"), audit_page,
			audit_param, audit_result, "I"); // 감사로그 저장

		return NcLog.getResultTD(result);
	}
	
	/**
	 * 내부 연계서버 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_snd_link.do", method = RequestMethod.GET)
    public String config_snd_link(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcConfig> sendList = ncConfigService.getConfigLink(1); // 1:송신 2:수신
		model.addAttribute("sendList", sendList);
		model.addAttribute("version", ncConfigService.getSysVersion(3));
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_snd_link";
    }
	
	/**
	 * 외부 연계서버 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_rcv_link.do", method = RequestMethod.GET)
    public String config_rcv_link(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcConfig> receiveList = ncConfigService.getConfigLink(2); // 1:송신 2:수신
		model.addAttribute("receiveList", receiveList);
		model.addAttribute("version", ncConfigService.getSysVersion(4));
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_rcv_link";
    }
	
	/**
	 * 시스템설정 송/수신 네트워크 설정 등록
	 * @param systemToRegit
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/link_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String link_regit(NcConfig systemToRegit, Model model, HttpSession session){
		int result = ncConfigService.config_link_insert(systemToRegit);	
		
		String audit_param = "";
		String audit_page = "";
		if(systemToRegit.getNclk_div()==1){
			audit_page = "내부 연계서버 네트워크 설정 등록";
			audit_param = "수신 IP = "+systemToRegit.getNclk_rcv_ip()+",수신 Netmask = "+systemToRegit.getNclk_rcv_nm()+",송신 IP = "+systemToRegit.getNclk_snd_ip()+",송신 Netmask = "+systemToRegit.getNclk_snd_nm()+",송신 Gateway = "+systemToRegit.getNclk_snd_gw();
		}else{
			audit_page = "외부 연계서버 네트워크 설정 등록";
			audit_param = "수신 IP = "+systemToRegit.getNclk_rcv_ip()+",수신 Netmask = "+systemToRegit.getNclk_rcv_nm()+",송신 IP = "+systemToRegit.getNclk_snd_ip()+",송신 Netmask = "+systemToRegit.getNclk_snd_nm()+",송신 Gateway = "+systemToRegit.getNclk_snd_gw();
		}
		
		if (result == 1) {			
			// 보안 정책 IP주소 관리에 동시처리 시작
			NcLinkPolicy ncPolicy = new NcLinkPolicy();
			
			if(systemToRegit.getNclk_div()==1){
				ncPolicy.setNli_ip_nm("내부 연계 수신");
			}else{
				ncPolicy.setNli_ip_nm("외부 연계 수신");
			}
			ncPolicy.setNli_gcode(1);
			ncPolicy.setNli_ip(systemToRegit.getNclk_rcv_ip());
			ncPolicy.setNli_div(systemToRegit.getNclk_div());
			ncPolicy.setNli_ip_type(1);
			ncLinkPolicyService.ipaddr_insert(ncPolicy);
			if(systemToRegit.getNclk_div()==2) ncTransferService.trans_link_ip("I", ncPolicy, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			
			if(systemToRegit.getNclk_div()==1){
				ncPolicy.setNli_ip_nm("내부 연계 송신");
			}else{
				ncPolicy.setNli_ip_nm("외부 연계 송신");
			}
			ncPolicy.setNli_gcode(2);
			ncPolicy.setNli_ip(systemToRegit.getNclk_snd_ip());
			ncLinkPolicyService.ipaddr_insert(ncPolicy);
			if(systemToRegit.getNclk_div()==2) ncTransferService.trans_link_ip("I", ncPolicy, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			// 보안 정책 IP주소 관리에 동시처리 끝
				
			//ncConfigService.create_configFile(systemToRegit); // 환경설정 파일 만들기
			if(systemToRegit.getNclk_div()==2) ncTransferService.trans_conf_link("I", systemToRegit); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 시스템설정 송/수신 네트워크 설정 수정
	 * @param systemToUpdate
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/link_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String link_update(NcConfig systemToUpdate, Model model, HttpSession session){
		int result = ncConfigService.config_link_update(systemToUpdate);
		
		String audit_param = "";
		String audit_page = "";
		if(systemToUpdate.getNclk_div()==1){
			audit_page = "내부 연계서버 네트워크 설정 수정";
			audit_param = "수신 IP = "+systemToUpdate.getNclk_rcv_ip()+",수신 Netmask = "+systemToUpdate.getNclk_rcv_nm()+",송신 IP = "+systemToUpdate.getNclk_snd_ip()+",송신 Netmask = "+systemToUpdate.getNclk_snd_nm()+",송신 Gateway = "+systemToUpdate.getNclk_snd_gw();
		}else{
			audit_page = "외부 연계서버 네트워크 설정 수정";
			audit_param = "수신 IP = "+systemToUpdate.getNclk_rcv_ip()+",수신 Netmask = "+systemToUpdate.getNclk_rcv_nm()+",송신 IP = "+systemToUpdate.getNclk_snd_ip()+",송신 Netmask = "+systemToUpdate.getNclk_snd_nm()+",송신 Gateway = "+systemToUpdate.getNclk_snd_gw();
		}
		
		if (result == 1) {
			// 보안 정책 IP주소 관리에 동시처리 시작
			NcLinkPolicy ncPolicy = new NcLinkPolicy();
			
			ncPolicy.setNli_gcode(1);
			ncPolicy.setNli_ip(systemToUpdate.getNclk_rcv_ip());
			ncPolicy.setNli_div(systemToUpdate.getNclk_div());
			ncPolicy.setNli_ip_type(1);
			ncLinkPolicyService.ipaddr_update_system(ncPolicy);
			if(systemToUpdate.getNclk_div()==2) ncTransferService.trans_link_ip("SYS", ncPolicy, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			
			ncPolicy.setNli_gcode(2);
			ncPolicy.setNli_ip(systemToUpdate.getNclk_snd_ip());
			ncLinkPolicyService.ipaddr_update_system(ncPolicy);
			if(systemToUpdate.getNclk_div()==2) ncTransferService.trans_link_ip("SYS", ncPolicy, 0); // 수신 DB에서 실행할 쿼리파일 만들기
			// 보안 정책 IP주소 관리에 동시처리 끝			
			
			//ncConfigService.create_configFile(systemToUpdate); // 환경설정 파일 만들기
			if(systemToUpdate.getNclk_div()==2) ncTransferService.trans_conf_link("U", systemToUpdate); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장	
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/*********** 아래는 송/수신 네트워크를 여러개 등록 할 수 있는 소스이다. 현재는 송/수신 네트워크를 하나만 사용하므로 미사용 이고 추후 쓰도록 하자. ***********/
	
	/**
	 * 시스템설정 송신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/send_network.do", method = RequestMethod.GET)
    public String send_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		//ArrayList<NdConfig> systemList = ncConfigService.getConfigSystem(1); // 1:송신 2:수신
		//model.addAttribute("systemList", systemList);
		model.addAttribute("systemToRegit", new NcConfig());
		
		return "config/config_send_system";
    }
	
	/**
	 * 시스템설정 송/수신 네트워크 설정 삭제
	 * @param ncs_seq
	 * @return
	 */
	@RequestMapping(value = "/system_delete.do", method = RequestMethod.POST)
	@ResponseBody
    public String system_delete(@RequestParam("ncs_seq") int ncs_seq){
		int result = ncConfigService.config_system_delete(ncs_seq);
		if (result == 1) {
			return "true";
		} else {
			return "DB Error";
		}
    }
	
	/**
	 * 시스템설정 수신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receive_network.do", method = RequestMethod.GET)
    public String receive_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		//ArrayList<NdConfig> systemList = ncConfigService.getConfigSystem(2); // 1:송신 2:수신
		//model.addAttribute("systemList", systemList);
		model.addAttribute("systemToRegit", new NcConfig());
		
		return "config/config_receive_system";
    }
	
	/*********** 아래는 송/수신 네트워크를 여러개 등록 할 수 있는 소스이다. 현재는 송/수신 네트워크를 하나만 사용하므로 미사용 이고 추후 쓰도록 하자. ***********/

	/*********** 아래는 서비스 데몬 설정 화면도 추후 사용 ***********/
	
	/**
	 * 서비스 데몬 설정 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_deamon.do", method = RequestMethod.GET)
	public String config_deamon(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		model.addAttribute("deamon_data1", ncConfigService.getConfigProgram(1));
		model.addAttribute("deamon_data2", ncConfigService.getConfigProgram(2));
		
		return "config/config_deamon";
	}
	
	/**
	 * 등록된 서비스 데몬 수정
	 * 
	 * @param ncd_seq
	 * @param ncd_name
	 * @param ncd_div
	 * @param ncd_status
	 * @param ncd_path
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modify_deamon.do", method = RequestMethod.POST)
	@ResponseBody
	public String modify_deamon(@RequestParam("ncd_seq") int ncd_seq, @RequestParam("ncd_name") String ncd_name, @RequestParam("ncd_div") int ncd_div, @RequestParam("ncd_status") int ncd_status, 
		@RequestParam("ncd_path") String ncd_path, HttpSession session){
		
		int result = ncConfigService.config_deamon_update(ncd_seq, ncd_name, ncd_div, ncd_status, ncd_path);
		String audit_param = "nce_seq"+ncd_seq+",ncd_name="+ncd_name+",ncd_div="+ncd_div+",ncd_status="+ncd_status+",ncd_path="+ncd_path;
		
		if (result == 1) {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"서비스 데몬 설정 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"서비스 데몬 설정 수정",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 신규 서비스 데몬 추가
	 * 
	 * @param ncd_name
	 * @param ncd_div
	 * @param ncd_status
	 * @param ncd_path
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/insert_deamon.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_deamon(@RequestParam("ncd_name") String ncd_name, @RequestParam("ncd_div") int ncd_div, @RequestParam("ncd_status") int ncd_status, @RequestParam("ncd_path") String ncd_path, HttpSession session){
		int result = ncConfigService.config_deamon_insert(ncd_name, ncd_div, ncd_status, ncd_path);
		
		String audit_param ="ncd_name="+ncd_name+",ncd_div="+ncd_div+",ncd_status="+ncd_status+",ncd_path="+ncd_path;
		if (result == 1) {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"신규 서비스 데몬 추가",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"신규 서비스 데몬 추가",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 서비스 데몬 사용여부 변경
	 * 
	 * @param ncd_seq
	 * @param ncd_use_yn
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit_deamon_useyn.do", method = RequestMethod.POST)
	@ResponseBody
	public String edit_deamon_useyn(@RequestParam("ncd_seq") int ncd_seq, @RequestParam("ncd_use_yn") int ncd_use_yn, HttpSession session){
		int result=0;
		
		// edit_deamon_useyn(ncd_seq, ncd_use_yn, ncd_status)
		// 사용으로 변경 시 자동 실행 동작, 미사용으로 변경 시 자동 실행 중지
		if(ncd_use_yn == 0)
			result = ncConfigService.edit_deamon_useyn(ncd_seq, 1, 1);			
		else
			result = ncConfigService.edit_deamon_useyn(ncd_seq, 0, 2);
		
		String audit_param ="ncd_seq="+ncd_seq+",ncd_use_yn="+ncd_use_yn;
		if (result == 1) {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"서비스 데몬 사용여부 변경",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"서비스 데몬 사용여부 변경",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 데몬 명 중복 검사
	 * 
	 * @param ncp_file_name
	 * @param ncp_div
	 * @param ncp_seq
	 * @return
	 */
	@RequestMapping(value = "/check_deamon.do", method = RequestMethod.POST)
	@ResponseBody
	public String check_deamon(@RequestParam("ncp_file_name") String ncp_file_name, @RequestParam("ncp_div") int ncp_div, @RequestParam("ncp_seq") int ncp_seq){
		ArrayList<NcConfig> deamon_list = ncConfigService.getConfigProgram(ncp_div);
		int check=0;		
		
		// ncp_seq = 0 : 신규 데몬 추가, ncp_seq !=0 기존 데몬 수정
		if(ncp_seq != 0){
			for (int i = 0; i < deamon_list.size(); i++) {
				if (deamon_list.get(i).getNcp_file_name().equals(ncp_file_name) && deamon_list.get(i).getNcp_seq() != ncp_seq)
					check++;
			}
		}else{
			for (int i = 0; i < deamon_list.size(); i++) {
				if (deamon_list.get(i).getNcp_file_name().equals(ncp_file_name))
					check++;
			}			
		}
		
		if (check > 0)
			return "exist";
		else
			return "non_exist";
	}
	
	/*********** 아래는 서비스 데몬 설정 화면도 추후 사용 ***********/
	
	/**
	 * 메인 서비스 프로그램 시작/중지
	 * @param ncp_status
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modify_deamon_status.do", method = RequestMethod.POST)
	@ResponseBody
	public String modify_deamon_status(@RequestParam("ncp_status") int ncp_status, HttpSession session){
		String audit_page = "서비스 프로그램 시작";
		if(ncp_status==1) audit_page="서비스 프로그램 중지";
		
		int result = ncConfigService.service_status_update(ncp_status);
		if (result>0) { // Mybatis는 update된 rows 수를 리턴
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,null,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,null,"F","I"); // 감사로그 저장
			return "DB Error";
		}	
	}
	
	/**
	 * 프로그램 자체 보호(무결성)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/self_protect.do", method = RequestMethod.GET)
	public String self_protect(Model model,@RequestParam(value="page",required=false) String page,
		    @RequestParam(value = "page2", required = false) String page2) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		int cpage=1; 
		int total = ncConfigService.getProtectTotalCnt();
		if( page != null && page.length() > 0) {
			try{
				cpage = Integer.parseInt(page);
			}catch(NumberFormatException e){
				
			}
		}
		PageInfo<NcConfig> pginfo = new PageInfo<NcConfig>(total, cpage);
		pginfo.setPerArticle(10);//  페이지당 줄 수 10개로 셋팅
	    pginfo.setPageInfo(total, cpage);
	    ArrayList<NcConfig> file_size = ncConfigService.getfile_size(
	      pginfo.getOffset(), pginfo.getPerArticle());

	    for (int i = 0; i < file_size.size(); i++) {
	      String size = StringUtil.byteCalculation(
	        (double) file_size.get(i).getNcp_file_size());
	      file_size.get(i).setNcp_file_size2(size);
	    }
	    pginfo.setResult(file_size);
	    
	    int s_cpage = 1;
	    int s_total = ncConfigService.getServiceTotalCnt();
	    if (page2 != null && page2.length() > 0) {
	      try {
	    	  s_cpage = Integer.parseInt(page2);
	      } catch (NumberFormatException e) {
	    	  s_cpage = 1;
	      }
	    }
	    PageInfo<NcConfig> s_pginfo = new PageInfo<NcConfig>();
	    s_pginfo.setPerArticle(10);//  페이지당 줄 수 10개로 셋팅
	    s_pginfo.setPageInfo(s_total, s_cpage);
	    ArrayList<NcConfig> service_size = ncConfigService.getService_size(
	    	s_pginfo.getOffset(), s_pginfo.getPerArticle());

	    for (int i = 0; i < service_size.size(); i++) {
	      String size = StringUtil.byteCalculation(
	        (double) service_size.get(i).getNcp_file_size());
	      service_size.get(i).setNcp_file_size2(size);
	    }
	    s_pginfo.setResult(service_size);
	    
	    model.addAttribute("pginfo", pginfo);
	    model.addAttribute("base_url", "self_protect.do?page2="+s_cpage+"&page=");
	    model.addAttribute("pginfo2", s_pginfo);
	    model.addAttribute("base_url2", "self_protect.do?page="+cpage+"&page2=");
	    model.addAttribute("sendList", ncConfigService.getConfigProgram(1));
	    model.addAttribute("receiveList", ncConfigService.getConfigProgram(2));
	    model.addAttribute("cycle",ncConfigService.getInspectCycle(1)); // 무결성 점검 주기
	    model.addAttribute("protection_datetime",ncConfigService.protection_last_datetime());
		return "program/program_protect";
	}
	
	/**
	   * 자체 시험 - 마지막 실행 시간
	   *
	   * @param session
	   * @return retVal
	   */
	  @GetMapping(value = "/prot_time.do")
	  @ResponseBody
	  public String getProtectionLastDateTime(HttpSession session) {
	    return ncConfigService.protection_last_datetime();
	  }
	
	/**
	 * 프로그램 자체 보호(무결성)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rvs_self_protect.do", method = RequestMethod.GET)
	public String rvs_self_protect(Model model) {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		model.addAttribute("sendList", ncConfigService.getConfigProgram(3));
		model.addAttribute("receiveList", ncConfigService.getConfigProgram(4));
		model.addAttribute("cycle", ncConfigService.getInspectCycle(4)); // 무결성 점검 주기
		
		return "program/rvs_program_protect";
	}
	
	/**
	 * 무결성 점검 주기 수정
	 * @param ncConfig
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/cycle_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String cycle_update(NcConfig ncConfig, Model model, HttpSession session){
		int result = ncConfigService.inspect_cycle_update(ncConfig);
		String audit_page = "무결성 점검 주기 수정";
		
		String audit_param = "무결성 점검 주기 = "+ncConfig.getNci_cycle()+"시간마다";
		if (result==1) {
			//ndTransferService.trans_conf_inspect(ncConfig.getNci_cycle()); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 검사 플래그 값 수정
	 * @param ncp_div
	 * @param ncp_inspect_flag
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspect_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String inspect_update(@RequestParam("ncp_div") int ncp_div, @RequestParam("ncp_inspect_flag") int ncp_inspect_flag, Model model, HttpSession session){
		int cnt=ncConfigService.inspect_ing_count();
		if(cnt>0){
			return "ing";
		}
		
		String audit_page = "";
		if(ncp_div==0) {
			audit_page += "송/수신 전체 프로그램 ";
		}else if(ncp_div==1) {
			audit_page += "송신 프로그램만 ";
		}else{
			audit_page += "수신 프로그램만 ";
		}
		if(ncp_inspect_flag==1) {
			audit_page += "무결성 검사 실행";
		}else{
			audit_page += "무결성값 업데이트 실행";
		}
		
		int result = ncConfigService.inspect_flag_update(ncp_div, ncp_inspect_flag);
		if (result>0) {
			//if(ncp_div!=1) ndTransferService.trans_inspect_flag(ncp_div, ncp_inspect_flag); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,null,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,null,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 무결성 검사 실행
	 * @param nci_div
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/inspect_start.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String inspect_start(@RequestParam("nci_div") int nci_div, Model model, HttpSession session){
		// 검사 중인지 확인
		int daemon = ncConfigService.inspect_ing_deamon(nci_div);
		if (daemon != 0) {
	      return NcConfigMapper.INTEGRITY_DAEMON_STATUS.values()[daemon].getMessage();
	    }
		
		int result = ncConfigService.inspect_start(1, nci_div, (String) session.getAttribute("loginUserId"));
		
		String audit_page = "무결성 검사 실행";
		String audit_result = NcLog.getResultSF(result);
		// 무결성 검사 데몬 시작
		ncAuditService.integrity_log_insert(
	      (String) session.getAttribute("loginUserId"),
	      (String) session.getAttribute("clientIp"), audit_page, null, audit_result,
	      "I"); // 감사로그 저장
		
		return NcLog.getResultsf(result);
	}
	
	/**
	 * 무결성 값 업데이트 실행
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/update_hash.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String update_hash(@RequestParam("nci_div") int nci_div, Model model, HttpSession session){
		// 검사 중인지 확인
		int daemon = ncConfigService.inspect_ing_deamon(nci_div);
		if (daemon != 0) {
	      return NcConfigMapper.INTEGRITY_DAEMON_STATUS.values()[daemon].getMessage();
	    }
		
		// 무결성 업데이트 데몬 시작
		int result = ncConfigService.inspect_start(2, nci_div);
		
		String audit_page = "무결성값 업데이트 실행";
		String audit_result = NcLog.getResultSF(result);
		
		ncAuditService.policy_log_insert(
	      (String) session.getAttribute("loginUserId"),
	      (String) session.getAttribute("clientIp"), audit_page, null, audit_result,
	      "I"); // 감사로그 저장

	    if (result > 0) {
	      result = ncAlarmService.alarm_delete("I"); // 무결성 알람 삭제
	    }

	    return NcLog.getResultsf(result);
	}
	
	/**
	 * 송신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sys_snd_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String sys_snd_restart(Model model, HttpSession session){
		//int result = ncConfigService.nds_restart();
		int result = ncConfigService.system_restart_update(1); // 내부 데몬 재시작
		if(result>0){
			ncAlarmService.alarm_delete("I"); // 무결성 알람 삭제
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 송신 시스템 재시작",null,"S","I"); // 감사로그 저장
			return "success";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 송신 시스템 재시작",null,"F","I"); // 감사로그 저장
			return "fail";
		}
	}
	
	/**
	 * 수신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sys_rcv_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String sys_rcv_restart(Model model, HttpSession session){
		//int result = ncConfigService.trans_ndr_restart();
		int result = ncConfigService.system_restart_update(2, (String) session.getAttribute("loginUserId")); // 외부 데몬 재시작
		
		String audit_page = MessageUtil.getMessage("config.extsystemrestart");
		String audit_result = NcLog.getResultSF(result);
		
		ncAuditService.policy_log_insert(
		  (String) session.getAttribute("loginUserId"),
	      (String) session.getAttribute("clientIp"), audit_page, null, audit_result,
	      "I"); // 감사로그 저장
		
		return NcLog.getResultsf(result);
	}
	
	/**
	 * 외부 송신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sys_extsnd_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String sys_extsnd_restart(Model model, HttpSession session){
		int result = ncConfigService.system_restart_update(3); // 외부 송신 데몬 재시작
		if(result>0){
			//ncalarmService.alarm_delete("I"); // 무결성 알람 삭제
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"외부 송신 시스템 재시작",null,"S","I"); // 감사로그 저장
			return "success";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"외부 송신 시스템 재시작",null,"F","I"); // 감사로그 저장
			return "fail";
		}
	}
	
	/**
	 * 내부 수신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sys_intrcv_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String sys_intrcv_restart(Model model, HttpSession session){
		int result = ncConfigService.system_restart_update(4); // 내부 수신 데몬 재시작
		if(result>0){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 수신 시스템 재시작",null,"S","I"); // 감사로그 저장
			return "success";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 수신 시스템 재시작",null,"F","I"); // 감사로그 저장
			return "fail";
		}
	}
	
	/**
	 * 연계 송신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/link_snd_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String link_snd_restart(Model model, HttpSession session){
		int result = ncConfigService.link_restart_update(); // 연계 서버 재시작
		if(result==1){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 연계 서버 재시작",null,"S","I"); // 감사로그 저장
			return "success";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내부 연계 서버 재시작",null,"F","I"); // 감사로그 저장
			return "fail";
		}
	}
	
	/**
	 * 연계 수신 데몬 재시작
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/link_rcv_restart.do", method = RequestMethod.POST)
	@ResponseBody
	public String link_rcv_restart(Model model, HttpSession session){
		int result = ncTransferService.trans_link_restart();
		if(result==0){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"외부 연계 서버 재시작",null,"S","I"); // 감사로그 저장
			return "success";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"외부 연계 서버 재시작",null,"F","I"); // 감사로그 저장
			return "fail";
		}
	}
	
	/**
	 * 프로그램 관리
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_program.do")
    public String config_program(NcConfig ncConfig, Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		ArrayList<NcConfig> programList = ncConfigService.program_list(1, ncConfig.getNcp_div());
		model.addAttribute("programList", programList);
		
		model.addAttribute("ncpDiv", ncConfig.getNcp_div());
		model.addAttribute("programToRegit", new NcConfig());
		return "config/config_program";
    }
	
	/**
	 * 프로그램 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/program_regitVw.do", method = RequestMethod.GET)
	public String program_regitVw(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(400013));
		model.addAttribute("title", ncMenuService.getNowTitle(400013));
		model.addAttribute("menu_id", 400013);
		// 메뉴 만들기 끝
		
		model.addAttribute("programToRegit", new NcConfig());
		return "config/config_program_regit";			
	}
	
	/**
	 * 프로그램 등록
	 * @param ncConfig
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/program_regit.do", method = RequestMethod.POST)
	@ResponseBody
	public String program_regit(NcConfig ncConfig, Model model, HttpSession session){
		int cnt = ncConfigService.program_count(ncConfig);
		if(cnt>0){
			return "dupli";
		}
		
		ncConfig.setNcp_file_name(ncConfig.getNcp_file_name().replaceAll("\\p{Z}", "")); // 공백 제거
		ncConfig.setNcp_path(ncConfig.getNcp_path().replaceAll("\\p{Z}", "")); // 공백 제거
		
		int result = ncConfigService.program_insert(ncConfig);
		String audit_page = "프로그램 등록";
		
		String audit_param = "프로그램명 = "+ncConfig.getNcp_name()+",프로그램 파일명 = "+ncConfig.getNcp_file_name()+",파일 경로 = "+ncConfig.getNcp_path()+",내/외부 구분 = "+(ncConfig.getNcp_div()==1?"내부":"외부");
		if(result==1){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 프로그램 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/program_editVw_bak.do", method = RequestMethod.GET)
	public String program_editVw_bak(@RequestParam("ncp_seq") int ncp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(400013));
		model.addAttribute("title", ncMenuService.getNowTitle(400013));
		model.addAttribute("menu_id", 400013);
		// 메뉴 만들기 끝
		
		NcConfig ncConfig = ncConfigService.serviceProgramBySeq(ncp_seq);
		model.addAttribute("programToRegit", ncConfig);
		return "config/config_program_edit";			
	}
	
	/**
	 * 프로그램 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/program_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String policy_editVw(@RequestParam("ncp_seq") int ncp_seq, Model model) {
		NcConfig ncConfig = ncConfigService.serviceProgramBySeq(ncp_seq);
		
		String result = "<input type='hidden' name='ncp_seq' value='"+ncConfig.getNcp_seq()+"' /><ul>"+
				"<li>"+
					"<label>프로그램 명</label>"+
					"<input name='ncp_name' value='"+ncConfig.getNcp_name()+"' />"+
				"</li>"+
				"<li>"+
					"<label>프로그램 파일명</label>"+
					"<input name='ncp_file_name' value='"+ncConfig.getNcp_file_name()+"' />"+
				"</li>"+
				"<li>"+
					"<label>파일 경로</label>"+
					"<input name='ncp_path' value='"+ncConfig.getNcp_path()+"' maxlength='100' />"+
				"</li>"+
				"<li>"+
					"<label>내/외부 구분</label>";
		if(ncConfig.getNcp_div()==1 || ncConfig.getNcp_div()==2){
			result += "<select name='ncp_div'>"+
						"<option value='1'"+(ncConfig.getNcp_div()==1?" selected":"")+">내부</option>"+
						"<option value='2'"+(ncConfig.getNcp_div()==2?" selected":"")+">외부</option>"+
					"</select>";
		}else{
			result += "<select name='ncp_div'>"+
					"<option value='4'"+(ncConfig.getNcp_div()==4?" selected":"")+">내부</option>"+
					"<option value='3'"+(ncConfig.getNcp_div()==3?" selected":"")+">외부</option>"+
				"</select>";
		}
		result +="</li>"+
			"</ul>";
		return result;
	}
	
	/**
	 * 프로그램 수정
	 * @param ncConfig
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/program_edit.do", method = RequestMethod.POST)
	@ResponseBody
	public String program_edit(NcConfig ncConfig, Model model, HttpSession session){
		int cnt = ncConfigService.program_count(ncConfig);
		if(cnt>0){
			return "dupli";
		}
		
		ncConfig.setNcp_file_name(ncConfig.getNcp_file_name().replaceAll("\\p{Z}", "")); // 공백 제거
		ncConfig.setNcp_path(ncConfig.getNcp_path().replaceAll("\\p{Z}", "")); // 공백 제거
		
		int result = ncConfigService.program_update(ncConfig);
		String audit_page = "프로그램 수정";
		
		String audit_param = "프로그램명 = "+ncConfig.getNcp_name()+",프로그램 파일명 = "+ncConfig.getNcp_file_name()+",파일 경로 = "+ncConfig.getNcp_path()+",내/외부 구분 = "+(ncConfig.getNcp_div()==1?"내부":"외부");
		if(result==1){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 프로그램 삭제
	 * @param ncp_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/program_delete.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String program_delete(@RequestParam("ncp_seq") int ncp_seq, HttpSession session) {
		int cnt = ncConfigService.program_use_count(ncp_seq);
		if(cnt>0){ // 사용중인 프로그램 삭제 불가
			return "used";
		}
		
		NcConfig ncConfig = ncConfigService.serviceProgramBySeq(ncp_seq);
		
		String audit_page = "프로그램 삭제";
		
		String audit_param = "프로그램명 = "+ncConfig.getNcp_name()+",프로그램 파일명 = "+ncConfig.getNcp_file_name()+",파일 경로 = "+ncConfig.getNcp_path()+",내/외부 구분 = "+(ncConfig.getNcp_div()==1?"내부":"외부");
		
		int result = ncConfigService.program_delete(ncp_seq);
		if(result==1){
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		}else{
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 프로그램 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/program_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String program_delall(HttpServletRequest req, HttpSession session) {
		String ncp_seq[] = req.getParameterValues("delck");
		
		int cnt = 0;
		for(int i=0; i<ncp_seq.length; i++){
			cnt = ncConfigService.program_use_count(Integer.parseInt(ncp_seq[i]));
			if(cnt>0){
				NcConfig ncConfig = ncConfigService.serviceProgramBySeq(Integer.parseInt(ncp_seq[i]));
				return "used|프로그램명="+ncConfig.getNcp_name()+", 프로그램 파일명 = "+ncConfig.getNcp_file_name();
			}
		}
		
		int result = 0;
		String audit_param = "";
		String audit_page = "프로그램 삭제";
		for(int i=0; i<ncp_seq.length; i++){
			NcConfig ncConfig = ncConfigService.serviceProgramBySeq(Integer.parseInt(ncp_seq[i]));
			
			audit_param = "프로그램명 = "+ncConfig.getNcp_name()+",프로그램 파일명 = "+ncConfig.getNcp_file_name()+",파일 경로 = "+ncConfig.getNcp_path()+",내/외부 구분 = "+(ncConfig.getNcp_div()==1?"내부":"외부");
			
			
			result = ncConfigService.program_delete(Integer.parseInt(ncp_seq[i]));
			if (result == 1) {
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
	 * 원격 터미널 플래그 변경 
	 * @param ncs_remote_flag
	 * @param ncs_div
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sys_remote_update.do", method = RequestMethod.POST)
	@ResponseBody
	public String sys_remote_update(@RequestParam("ncs_remote_flag") int ncs_remote_flag, 
			@RequestParam("ncs_div") int ncs_div, Model model, HttpSession session){
		int result = ncConfigService.system_remote_update(ncs_remote_flag, ncs_div); // 내부 데몬 재시작
		if(result>0){
			return "success";
		}else{
			return "fail";
		}
	}
	
	/**
	 * 역방향 프로그램 관리
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_rvs_program.do")
    public String config_rvs_program(NcConfig ncConfig, Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		ArrayList<NcConfig> programList = ncConfigService.program_rvs_list(1, ncConfig.getNcp_div());
		model.addAttribute("programList", programList);
		
		model.addAttribute("ncpDiv", ncConfig.getNcp_div());
		model.addAttribute("programToRegit", new NcConfig());
		return "config/config_rvs_program";
    }
	
	/**
	 * 프로그램 등록 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/program_rvs_regitVw.do", method = RequestMethod.GET)
	public String program_rvs_regitVw(Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(400021));
		model.addAttribute("title", ncMenuService.getNowTitle(400021));
		model.addAttribute("menu_id", 400021);
		// 메뉴 만들기 끝
		
		model.addAttribute("programToRegit", new NcConfig());
		return "config/config_rvs_program_regit";			
	}
	
	/**
	 * 프로그램 수정 화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/program_rvs_editVw.do", method = RequestMethod.GET)
	public String program_rvs_editVw(@RequestParam("ncp_seq") int ncp_seq, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(400021));
		model.addAttribute("title", ncMenuService.getNowTitle(400021));
		model.addAttribute("menu_id", 400021);
		// 메뉴 만들기 끝
		
		NcConfig ncConfig = ncConfigService.serviceProgramBySeq(ncp_seq);
		model.addAttribute("programToRegit", ncConfig);
		return "config/config_rvs_program_edit";			
	}
	
	/**
	 * 송신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_extsnd_network.do", method = RequestMethod.GET)
    public String config_extsnd_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		NcConfig sendList = ncConfigService.getConfigSystem(3); // 3:송신 4:수신
		model.addAttribute("sendList", sendList);
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_extsnd_system";
    }
	
	/**
	 * 수신 네트워크 설정 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_intrcv_network.do", method = RequestMethod.GET)
    public String config_intrcv_network(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		
		NcConfig receiveList = ncConfigService.getConfigSystem(4); // 1:송신 2:수신
		model.addAttribute("receiveList", receiveList);
		model.addAttribute("systemToRegit", new NcConfig());
		return "config/config_intrcv_system";
    }
	
	/**
	 * 콘텐츠 관리 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/config_contents.do", method = RequestMethod.GET)
    public String config_contents(Model model){
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu());
		model.addAttribute("title", ncMenuService.getNowTitle());
		// 메뉴 만들기 끝
		
		ArrayList<NcLinkPolicy> contsList = ncConfigService.getContents();
		model.addAttribute("contsList", contsList);
		model.addAttribute("contsToRegit", new NcLinkPolicy());
		model.addAttribute("protList", ncConfigService.getProtocol());
		return "config/config_contents";
    }
	
	/**
	 * 콘텐츠 등록
	 * @param contsToRegit
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/contents_regit.do", method = RequestMethod.POST)
	@ResponseBody
    public String contents_regit(NcLinkPolicy contsToRegit, HttpSession session, Model model){
		int cnt = ncConfigService.contents_count(contsToRegit);
		if(cnt>0){ // 동일한 콘텐츠 등록 불가
			return "dupli";
		}
		
		String audit_param = "프로포콜 명 = "+contsToRegit.getNpcs_type()+",콘텐츠 명 = "+contsToRegit.getNpcs_name()+",콘텐츠 헤더 = "+contsToRegit.getNpcs_header();
		
		int result = ncConfigService.contents_insert(contsToRegit);
		if (result == 1) {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 등록",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 등록",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 콘텐츠 수정화면 호출
	 * @param npcs_seq
	 * @return
	 */
	@RequestMapping(value = "/contents_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String contents_editVw(@RequestParam("npcs_seq") int npcs_seq) {
		NcLinkPolicy conts = ncConfigService.getContentsBySeq(npcs_seq);
			
		String strReturn = "<div class='form-group'>";
		strReturn += "<label class='control-label col-md-3'>프로토콜 명</label>";
		strReturn += "<div class='form-inline col-md-7'><input type='hidden' id='npcs_seq' name='npcs_seq' value='"+conts.getNpcs_seq()+"' /><select name='npcs_type_sel' class='form-control' onChange='selectValue(this.value);'>";
		strReturn += "<option value=''>직접입력</option>";
		
		String temp="";
		ArrayList<NcLinkPolicy> protList = ncConfigService.getProtocol();
		for(int i=0; i < protList.size(); i++){
			temp="";
			if(conts.getNpcs_type().equals(protList.get(i).getNpcs_type())) temp = "selected";
			strReturn += "<option value='"+protList.get(i).getNpcs_type()+"' "+temp+">"+protList.get(i).getNpcs_type()+"</option>";
		}
		strReturn += "</select>&nbsp;&nbsp;<input name='npcs_type' id='npcs_type' class='form-control' value='"+conts.getNpcs_type()+"' readonly /></div></div>";
		strReturn += "<div class='form-group'>";
		strReturn += "	<label class='control-label col-md-3'>콘텐츠 명</label>";
		strReturn += "	<div class='col-md-7'>";
		strReturn += "		<input name='npcs_name' class='form-control col-md-7 col-xs-12' value='"+conts.getNpcs_name()+"' />";
		strReturn += "	</div>";
		strReturn += "</div>";
		strReturn += "<div class='form-group'>";
		strReturn += "	<label class='control-label col-md-3'>콘텐츠 헤더</label>";
		strReturn += "	<div class='col-md-7'>";
		strReturn += "		<input name='npcs_header' class='form-control col-md-7 col-xs-12' value='"+(conts.getNpcs_header()==null?"":conts.getNpcs_header())+"' />";
		strReturn += "	</div>";
		strReturn += "</div>";
		strReturn += "<div class='ln_solid'></div>";
		strReturn += "<div class='col-md-6 col-md-offset-3'>";
		strReturn += "<button type='button' class='btn btn-primary' onClick=\"javascript:location.replace('config_contents.do');\"><i class='fa fa-close'></i> "+MessageUtil.getMessage("btn.cancel")+"</button>";
		strReturn += "<button type='button' class='btn btn-success' onClick='javascript:update_contents();'><i class='fa fa-pencil'></i> "+MessageUtil.getMessage("btn.modify")+"</button></div></div>";
		
		return strReturn;
	}
	
	/**
	 * 콘텐츠 수정
	 * @param contsToRegit
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/contents_update.do", method = RequestMethod.POST)
	@ResponseBody
    public String contents_update(NcLinkPolicy contsToRegit, HttpSession session, Model model){
		int cnt = ncConfigService.contents_count(contsToRegit);
		if(cnt>0){ // 동일한 콘텐츠 등록 불가
			return "dupli";
		}
		
		String audit_param = "프로포콜 명 = "+contsToRegit.getNpcs_type()+",콘텐츠 명 = "+contsToRegit.getNpcs_name()+",콘텐츠 헤더 = "+contsToRegit.getNpcs_header();
		
		int result = ncConfigService.contents_update(contsToRegit);
		if (result == 1) {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 수정",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
    }
	
	/**
	 * 콘텐츠 삭제
	 * @param npcs_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/contents_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String contents_delete(@RequestParam("npcs_seq") int npcs_seq, HttpSession session) {
		int cnt = ncConfigService.contents_use_count(npcs_seq);
		if(cnt>0){ // 사용중인 콘텐트 삭제 불가
			return "used";
		}
		
		NcLinkPolicy conts = ncConfigService.getContentsBySeq(npcs_seq);
		String audit_param = "프로포콜 명 = "+conts.getNpcs_type()+",콘텐츠 명 = "+conts.getNpcs_name()+",콘텐츠 헤더 = "+conts.getNpcs_header();
		
		int result = ncConfigService.contents_delete(npcs_seq);
		if (result == 1) {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 삭제",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 삭제",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 콘텐츠 일괄 삭제
	 * @param req
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/contents_delall.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String contents_delall(HttpServletRequest req, HttpSession session) {
		String npcs_seq[] = req.getParameterValues("sel[]");

		int cnt = 0;
		for(int i=0; i<npcs_seq.length; i++){
			cnt = ncConfigService.contents_use_count(Integer.parseInt(npcs_seq[i]));
			if(cnt>0){
				NcLinkPolicy conts = ncConfigService.getContentsBySeq(Integer.parseInt(npcs_seq[i]));
				return "used|프로토콜 명="+conts.getNpcs_type()+", 콘텐츠 명="+conts.getNpcs_name();
			}
		}
		
		int result = 0;
		String audit_param = "";
		for(int i=0; i<npcs_seq.length; i++){
			NcLinkPolicy conts = ncConfigService.getContentsBySeq(Integer.parseInt(npcs_seq[i]));
			
			audit_param = "프로포콜 명 = "+conts.getNpcs_type()+",콘텐츠 명 = "+conts.getNpcs_name()+",콘텐츠 헤더 = "+conts.getNpcs_header();
			
			result = ncConfigService.contents_delete(Integer.parseInt(npcs_seq[i]));
			if (result == 1) {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 삭제",audit_param,"S","I"); // 감사로그 저장
			} else {
				ncAuditService.policy_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"콘텐츠 삭제",audit_param,"F","I"); // 감사로그 저장
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