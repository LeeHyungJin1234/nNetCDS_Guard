package nnsp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nnsp.services.NcAlarmService;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcExecService;
import nnsp.services.NcLogService;
import nnsp.services.NcSecurity;
import nnsp.services.NcUserService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcAlarm;
import nnsp.vo.NcConfig;
import nnsp.vo.NcLog;
import nnsp.vo.NcProduct;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import nnsp.common.Config;
import nnsp.security.Constants;
import nnsp.security.NcSecurityUtil;
import nnsp.security.RSAUtil;

@Controller
public class HomeController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Config config;
	
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcUserService ncUserService;
	@Autowired
	NcConfigService ncConfigService;
	@Autowired
	NcLogService ncLogService;
	@Autowired
	NcExecService ncExecService;
	@Autowired
	NcAlarmService ncAlarmService;
	@Autowired
	NcSecurity ncSecurity;
	
	/**
	 * 메인 화면 호출
	 * @param session
	 * @param locale
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@SuppressWarnings({ "unused", "resource" })
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main(HttpSession session, Locale locale, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
		NcUser ncuser = ((NcUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser(); // 현재 로그인 사용자 정보 가져오기
		
        // 최근 로그인 시간이 없으면 초기 패스워드 변경하는 팝업 띄우기
        if (ncuser.getNsu_seq() == 1 && ("".equals(ncuser.getNsu_last_login()) || ncuser.getNsu_last_login() == null)) {
            model.addAttribute("step", "change_passwd");//  초기설정 단계
            model.addAttribute("change_passwd", "Y");
            
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
            
            return "/change_login";
        } else if (!ncConfigService.getConfigProd()) {
            //  초기 장비 설정
        	NcProduct currProdInfo = ncConfigService.getNcProduct();
            model.addAttribute("step", "init_prod");//  초기설정 단계
            model.addAttribute("currProdInfo", currProdInfo);//  초기설정 단계
            return "/init_prod";
        } else if (null == ncConfigService.getConfigDB() ||
            ncConfigService.getConfigDB().isEmpty()) {
        	
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

            model.addAttribute("step", "dbpw_change");//  초기설정 단계
            return "/dbpw_change";
        } else if (null == ncConfigService.getConfigEMailPw() ||
            ncConfigService.getConfigEMailPw().isEmpty()) {
        	
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
        	
            // EMAIL 등록
            String id = (String) session.getAttribute("loginUserId");
            model.addAttribute("step", "insert_email");//  초기설정 단계
            return "/insert_email";
        }
		return "redirect:/stat_traffic.do";
	}
	
	/**
	 * dooray 로그인 화면 호출
	 * @param req
	 * @param locale
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@RequestMapping(value = "/dooray_login.do")
	public String dooray_login(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException  {
		
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
		
		return "dooray_login";
	}
	
	/**
	 * 로그인 화면 호출
	 * @param req
	 * @param locale
	 * @param model
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest req, HttpServletResponse res, Locale locale, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
		HttpSession session = req.getSession();
		session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", config.getLocale()); // 언어 선택
		
		String loginUserId = (String) session.getAttribute("loginUserId");
		
		if(loginUserId!=null && !"".equals(loginUserId) && "login_error".equals(req.getParameter("message"))){
			NcUser ncUser = ncUserService.getUserbyId(loginUserId);
			
			HttpServletRequest req1 = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	        String client_ip = req1.getHeader("X-FORWARDED-FOR"); // client ip 구하기
	        if (client_ip == null || client_ip.length() == 0) client_ip = req1.getHeader("Proxy-Client-IP");
	        if (client_ip == null || client_ip.length() == 0) client_ip = req1.getHeader("WL-Proxy-Client-IP");  // 웹로직
	        if (client_ip == null || client_ip.length() == 0) client_ip = req1.getRemoteAddr();
			
	        
			if(ncUser!=null){
				NcConfig ndConfig = ncConfigService.getConfigLogIn();
				
				if(ncUser.getNsu_login_failcnt() >= (ndConfig.getNcli_lock_failcnt()-1)){
					ncAuditService.mng_log_insert(ncUser.getNsu_id(),client_ip,"로그인","연속 로그인 실패로 계정잠김","L","W"); // 감사로그 저장
					ncUserService.lock_status_update("1", ndConfig.getNcli_lock_date(), (ncUser.getNsu_login_failcnt()+1), ncUser.getNsu_seq()); // 설정된 시간 동안 계정 잠금
					
					String Detail = ncUser.getNsu_id() +  " 계정 잠김" ;
					ncAlarmService.login_alarm_insert("R" , Detail );
				}else{
					ncAuditService.mng_log_insert(ncUser.getNsu_id(),client_ip,"로그인",null,"F","W"); // 감사로그 저장
					ncUserService.lock_status_update("0", 0, (ncUser.getNsu_login_failcnt()+1), ncUser.getNsu_seq()); // 실패횟수 더하기
					
				}
			}else{
				ncAuditService.mng_log_insert(loginUserId,client_ip,"로그인","등록되지 않은 아이디","F","W"); // 없는 ID로 로그인 했을 때 감사기록 저장
			}
		}
		
		NcSecurityUtil.logout(req, res);// 로그아웃 처리
		
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
		
		return "login";
	}
	
	/**
	 * 중복 로그인 처리 - 기존 로그인 세션 끊기
	 */
	@RequestMapping(value = "login_duplicate.do", method = RequestMethod.GET)
	public void login_duplicate(HttpServletResponse response) {
		try {
			response.sendRedirect("/login.do?message=login_duplicate");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 세션 끊어짐 체크
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping(value = "/session_check.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String session_check(HttpServletRequest req,
		HttpServletResponse res, HttpSession session) {
		TimeUnit tu = TimeUnit.MILLISECONDS;
		if(!Objects.nonNull(session.getAttribute(Constants.SESSION_CHECK_TIME))) {
			session.setAttribute(Constants.SESSION_CHECK_TIME, System.currentTimeMillis());
		}
		long sessionCheckTime = (long) session.getAttribute(Constants.SESSION_CHECK_TIME);//    세션체크시간
		long sessionMaxInactiveInterval =  tu.convert(session.getMaxInactiveInterval(), TimeUnit.SECONDS);//   세션허용 시간
		long sessionCurrentTimeMillis = System.currentTimeMillis();//   현재시간
		int iPrintTime = Long.valueOf(((sessionCheckTime+sessionMaxInactiveInterval)-sessionCurrentTimeMillis)/1000).intValue();
		
		if(iPrintTime<1) {
			return "0";
		}

		int min = iPrintTime/60;
		int sec = iPrintTime%60;
		
		return ((min>0)?min+"분":"")+" "+sec+"초";
	}

	/**
	 * 파라메터로 받은 SQL문 실행
	 * @param param
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sqlexec.do")
	public String sqlexec(@RequestParam("param") String param, Model model) {
		String res = "";
		try{
			if("SELECT".equals(param.substring(0, 6).toUpperCase())){
				ArrayList<HashMap<String, Object>> result = ncExecService.select_exec(param);
				for(int i=0; i<result.size(); i++){
					Iterator iterator = result.get(i).entrySet().iterator();
					while (iterator.hasNext()) {
						Entry entry = (Entry)iterator.next();
						res += entry.getValue();
						if(iterator.hasNext()) res += "|";
					}
					res += "<br>";
				}
			}else if("INSERT".equals(param.substring(0, 6).toUpperCase())){
				res = Integer.toString(ncExecService.insert_exec(param));
			}else{
				res = Integer.toString(ncExecService.update_exec(param));
			}
			
			model.addAttribute("result", res);
		} catch (Exception e) {
			model.addAttribute("result", "DB Error");
		}
		
		return "sql_result";
	}
	
	/**
	 * 알람 출력 내용 만들기
	 * @return
	 */
	@RequestMapping(value = "/alarm.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String alarm() {
		String result = "";
		ArrayList<NcAlarm> ncAlarm = ncAlarmService.getAlarmCnt();
		result = Integer.toString(ncAlarm.size());
		for(int i=0; i<ncAlarm.size(); i++){
			if("I".equals(ncAlarm.get(i).getNoa_type())){ // 무결성 알람
				result += "|I|무결성 오류|아래 프로그램의 무결성이 결여되어 전체 프로그램이 종료되었습니다.<br><br>";
				ArrayList<NcAlarm> detail = ncAlarmService.getInteDetail();
				for(int j=0; j<detail.size(); j++){
					result += detail.get(j).getNoa_detail()+"<br>";
				}
				result += "</td></tr></table>";
			
			}else if("R".equals(ncAlarm.get(i).getNoa_type())){
				result +="|R| 계정 잠김|";
				ArrayList<NcAlarm> detail = ncAlarmService.getLoginDetail();
				for(int j=0; j<detail.size(); j++){
					result +="<div><span>" + detail.get(j).getNoa_detail() +   " (" +detail.get(j).getNoa_reg_date()+ ")</span></div>";
				}
				
			}else{ // 디스크 용량 알람
				result += "|L|디스크 용량 초과|";
				ArrayList<NcAlarm> detail = ncAlarmService.getLogDetail();
				for(int j=0; j<detail.size(); j++){
					if("REACH".equals(detail.get(j).getNoa_detail())){
						result += "<div><span>디스크 용량이 한계치에 도달 하였습니다. ("+detail.get(j).getNoa_reg_date()+")</span></div>";
					}else{
						result += "<div><span>디스크 용량이 한계치를 초과하여 오래된 로그가 삭제되었습니다. ("+detail.get(j).getNoa_reg_date()+"</span></div>";
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 알람 삭제
	 * @param noa_type
	 * @return
	 */
	@RequestMapping(value = "/alarm_delete.do", method = RequestMethod.POST)
	@ResponseBody
    public String alarm_delete(@RequestParam("noa_type") String noa_type){
		int result = ncAlarmService.alarm_delete(noa_type);
		if (result>0) {
			return "true";
		} else {
			return "DB Error";
		}
    }
	
	/**
	 * 초기 db pw 변경
	 * @param req
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/dbpasswd_change.do", method = RequestMethod.POST)
	@ResponseBody
	public String dbpasswd_change(HttpServletRequest req, HttpSession session) throws Exception {
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
		//  최초 DB 설정 전일 때만 실행
		if (null != ncConfigService.getConfigDB() && !ncConfigService.getConfigDB().isEmpty()) {
			return "Error";
		}
		
		String audit_page = MessageUtil.getMessage("log.user.changedbpw");

		PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            throw new AuthenticationServiceException("NOT_FIND_PRIVATEKEY");
        }
    	
		String tPw = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd"));
		String tPw2 = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd_confirm"));

		String pw = NcSecurityUtil.getRotatedString(tPw, Constants.ROTATE);
		String pw2 = NcSecurityUtil.getRotatedString(tPw2, Constants.ROTATE);
		
		if(pw.length() < 1) {
			return "not_newpw_length";
		}
		// 최소 9자 이상 최대 127자 이하인지 체크
		if (pw.length() < 9 ||pw.length() > 127) {
			return "not_enough_length";
		}
		if(pw2.length() < 1) {
			return "not_newpw_length2";
		}
		if(pw.equals(pw2) == false) {
			return "not_same_pw";
		}


		// 문자, 숫자, 특수문자의 조합인지 확인
		// 일부 특수문자만 지원
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
		// 모든 특수문자 지원
		// https://en.wikipedia.org/wiki/List_of_Special_Characters_for_Passwords
		// Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}|\\s]).{9,127}$");
		Matcher m = p.matcher(pw);
		if (!m.find()) {
			return "not_rule";
		}

        Pattern p0 = Pattern.compile("^[a-zA-Z0-9!@#$%^&*?_~]*$");
        m = p0.matcher(pw);
        if (!m.find()) {
            return "not_rule";
        }
		
		// 3자리 이상 연속 문자, 숫자
		Pattern p3 = Pattern.compile("(\\p{Print})\\1\\1+");
		m = p3.matcher(pw);
		if(m.find()) { 
			return "not_continuity";
		}
				
		String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm"
									+ "|ewq|rew|tre|ytr|uyt|iuy|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
		String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
		for (int j=0; j<arrKeyboardThreeChar.length; j++) {
			if(pw.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
				return "not_qwer";
		    }
		}
		
		 String listKeyboardThreeChar2 = "123|234|345|456|567|678|789|890|321|432"
			 + "|543|654|765|876|987|098";
	        String[] arrKeyboardThreeChar2 = listKeyboardThreeChar2.split("\\|");
	        for (int j=0; j<arrKeyboardThreeChar2.length; j++) {
	            if(pw.toLowerCase().matches(".*" + arrKeyboardThreeChar2[j] + ".*")) {
	                return "not_qwer";
	            }
	        }

		// 5. config.properties 변경
		ncConfigService.setConfigProperties("nntCfg.common.firstSetup", "1");
		// 1. jdbc.properties 변경
		int result = ncConfigService.setJdbcProperties(pw);
		if (result==0) {
			// 2. 평문으로된 DB 정보 파일 저장
			//ntConfigService.setDbConftxt(pw);
			// 3. db 초기 패스워드 변경 기록
			result = ncConfigService.insert_config_db();
			//여기까지
			
			//ntConfigService.init_db_start();

			String audit_param = MessageUtil.getMessage("dbpw.initpw");
			String audit_result = NcLog.getResultSF(result);

			ncAuditService.mng_log_insert(
				(String)session.getAttribute("loginUserId"),
				req.getRemoteAddr(),
				audit_page, audit_param, audit_result,"I"); // 감사로그 저장

			try {
				if(tPw!=null) {tPw.close();}
				if(tPw2!=null) {tPw2.close();}

				if(pw!=null) {pw.close();}
				if(pw2!=null) {pw2.close();}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			req.getSession().invalidate();//  어차피 종료되므로 세션 제거
			return NcLog.getResultTD(result);
		} else {
			return "flase";
		}
    }
	
	/**
	 * logout
	 * @param session
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest req, HttpServletResponse res, HttpSession session, Locale locale, Model model) {
		NcSecurityUtil.logout(req, res);// 로그아웃 처리
		return "redirect:/login.do";
	}
}