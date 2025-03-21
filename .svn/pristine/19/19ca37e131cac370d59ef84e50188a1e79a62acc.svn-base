package nnsp.controllers;

import static nnsp.security.Constants.RAND_SALT_LENGTH;
import static nnsp.security.Constants.SALT_LENGTH;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nnsp.security.NcSecurityUtil;
import nnsp.security.RSAUtil;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcDoorayUserService;
import nnsp.services.NcMenuService;
import nnsp.services.NcSecurity;
import nnsp.services.NcTransferService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcClientUser;
import nnsp.vo.NcLog;

import org.apache.commons.codec.binary.Hex;
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
public class NcDoorayUserController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcDoorayUserController.class);
		
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcDoorayUserService ncDoorayUserService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcTransferService ncTransferService;
	@Autowired
	NcSecurity ncSecurity;
	@Autowired
	NcConfigService ncConfigService;
	

	@RequestMapping(value = "/dooray_login_check.do")
	public String dooray_login_check(NcClientUser ncClientUser) {
		PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
    		return "redirect:/dooray_login.do?message=notfind_key";
        }
    	String username="", password="";
		try {
			username = RSAUtil.decryptRsa(privateKey, ncClientUser.getUsr_account());
			password = RSAUtil.decryptRsa(privateKey, ncClientUser.getUsr_pwd());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
    		return "redirect:/dooray_login.do?message=notfind_key";
		}
		NcClientUser ncuCheck = ncDoorayUserService.getClientUserbyId(username);
		
		if(ncuCheck!=null && ncuCheck.getUsr_account()!=null && ncuCheck.getUsr_status()==1) {
			// 패스워드 확인
	        String encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(password, ncuCheck.getUsr_salt());
	        if (ncuCheck.getUsr_pwd().equals(encodedPassword)) {
	        	ncDoorayUserService.updateUsrLastTime(ncuCheck.getUsr_idx());
	        	return "redirect:https://saastest.dooray.com/";
	        }
		}
		
		return "redirect:/dooray_login.do?message=login_error";
	}
	
	@RequestMapping(value = "/dooray_id_check.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String dooray_id_check(@RequestParam("usr_account") String usr_account) {
		NcClientUser ncuCheck = ncDoorayUserService.getClientUserbyId(usr_account);
		if(ncuCheck!=null && ncuCheck.getUsr_account()!=null) { // 아이디 중복 체크
			return "present";
		}else{
			if("root".equals(usr_account.toLowerCase())||"admin".equals(usr_account.toLowerCase())){ // root, admin 계정은 안됨
				return "not_allow";
			}
			return "not_present";
		}
	}
	
	/**
	 * 계정 등록 시 Password 유효성 검사
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dooray_pw_check.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String dooray_pw_check(HttpServletRequest req) {
		String new_pw = null, new_pw_confirm = null;

		PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            return "rsa_error";
        }
        try {
			new_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("usr_pwd"));
	        new_pw_confirm = RSAUtil.decryptRsa(privateKey, req.getParameter("usr_pw_confirm"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

            return "rsa_error";
		}
    	 
   	// 최소 9자 이상 최대 127자 이하인지 체크
       if (new_pw.length() < 9 || new_pw.length() > 127) {
           return "not_enough_length";
       }

       if (new_pw.length() < 1) {
           return "not_newpw_length";
       }

       if (new_pw_confirm.length() < 1) {
           return "not_pw_confirm";
       }
       
       if (!new_pw.equals(new_pw_confirm)) {
			return "not_same_confirm";
		}


       // 문자, 숫자, 특수문자의 조합인지 확인
       // 일부 특수문자만 지원
       Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
       // 모든 특수문자 지원
       // https://en.wikipedia.org/wiki/List_of_Special_Characters_for_Passwords
       // Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}|\\s]).{9,127}$");
       Matcher m = p.matcher(new_pw);
       
       if (!m.find()) {
           return "not_rule";
       }

       Pattern p0 = Pattern.compile("^[a-zA-Z0-9!@#$%^&*?_~]*$");
       m = p0.matcher(new_pw);
       if (!m.find()) {
           return "not_rule";
       }

       // 3자리 이상 연속 문자, 숫자
       Pattern p3 = Pattern.compile("(\\p{Print})\\1\\1+");
       m = p3.matcher(new_pw);
       if (m.find()) {
           return "not_continuity";
       }

       String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
       String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
       for (int j = 0; j < arrKeyboardThreeChar.length; j++) {
           if (new_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
               return "not_qwer";
           }
       }
       String listKeyboardThreeChar2 = "123|234|345|456|567|678|789|890|321"
               + "|432|543|654|765|876|987|098";
       String[] arrKeyboardThreeChar2 = listKeyboardThreeChar2.split("\\|");
       for (int j = 0; j < arrKeyboardThreeChar2.length; j++) {
           if (new_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar2[j] + ".*")) {
               return "not_qwer";
           }
       }
       if (new_pw.indexOf(req.getParameter("new_id")) != -1) {
           return "not_id_pw";
       }
       
       
       try {
           new_pw.close();
           new_pw_confirm.close();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       return "true";
	}
	
	/**
	 * 관리자 계정 추가
	 * @param ndUserToInsert
	 * @param email_id
	 * @param email_server
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dooray_insert_user.do", method = RequestMethod.POST)
	@ResponseBody
	public String dooray_insert_user(@RequestParam("new_id") String new_id, @RequestParam("new_passwd") String new_passwd,
			@RequestParam("new_passwd_confirm") String new_passwd_confirm,
			@RequestParam("usr_srcip") String usr_srcip,
			@RequestParam("usr_url") String usr_url,
			@RequestParam("usr_dstip") String usr_dstip, HttpSession session) {

		String usr_account = "";
		String usr_pwd = "";
		String usr_pwd_confirm = "";
		
		PrivateKey privateKey = ncSecurity.getPrivateKey();
		if (privateKey == null) {
            return "rsa_error";
        }

    	try {
    		usr_account = RSAUtil.decryptRsa(privateKey, new_id);
    		usr_pwd = RSAUtil.decryptRsa(privateKey, new_passwd);
    		usr_pwd_confirm = RSAUtil.decryptRsa(privateKey, new_passwd_confirm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "rsa_error";
		}
    	
    	NcClientUser ncuCheck = ncDoorayUserService.getClientUserbyId(usr_account);
    	if(ncuCheck!=null && ncuCheck.getUsr_account()!=null) { // 아이디 중복 체크
			return "present";
		}else{
			if("root".equals(usr_account.toLowerCase())||"admin".equals(usr_account.toLowerCase())){ // root, admin 계정은 안됨
				return "not_allow";
			}
		}

        if (usr_pwd.length() < 1) {
            return "not_newpw_length";
        }

        if (usr_pwd_confirm.length() < 1) {
            return "not_pw_confirm";
        }
        
		Matcher m ;
		// 최소 9자 이상 127자 이하인지 체크
		if (usr_pwd.length() < 9 || usr_pwd.length() > 127) {
			return "not_enough_length";
		}
		
		if(usr_account.length() > 64) {
			return "not_pw_length64";
		}
		
		if(usr_account == "") {
			return "not_id_empty";
		}
		
		if(usr_pwd == "") {
			return "not_pw_empty";
		}
		
		
		// 문자, 숫자, 특수문자의 조합인지 확인
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
		 m = p.matcher(usr_pwd);
		if (!m.find()) {
			return "not_rule";
		}
		// 아이디 영 소문자
		Pattern p1 = Pattern.compile("^[a-zA-Z0-9]*$");
		m = p1.matcher(usr_account);
		if (!m.find()) {
			return "id_rule";
		}
				
		//같은 문자 숫자 체크
		Pattern p2 = Pattern.compile("(\\w)\\1\\1\\1"); 
		m = p2.matcher(usr_pwd);
		if(m.find()) { 
			return "not_same"; 
		}
		
		//연속 문자, 숫자
		int o = 0;
		int d = 0;
		int z = 0;
		int n =o;
		int limit = 4;
		
		for(int i=0; i< usr_pwd.length(); i++) {
			char tempVal = usr_pwd.charAt(i);
			if(i>0 && (z = o - tempVal) >-2&&(n=z==d? n+1:0)>limit-3) {
			
				return "not_continuity";
			
			}
			d=z;
			o=tempVal;
			}
		
		String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
        String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
        for (int j=0; j<arrKeyboardThreeChar.length; j++) {
            if(usr_pwd.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
                return "not_qwer";
            }
        }
		
        NcClientUser ncClientToInsert = new NcClientUser();
        ncClientToInsert.setUsr_account(usr_account);
        ncClientToInsert.setUsr_srcip(usr_srcip);
        ncClientToInsert.setUsr_url(usr_url);
        ncClientToInsert.setUsr_dstip(usr_dstip);
        
		int result = ncDoorayUserService.insert_client_user(ncClientToInsert);	

		//Password encrypt logic----------------------------------
		NcClientUser ncClientUser = ncDoorayUserService.getClientUserbyId(usr_account);
  		int iUsrIdx = ncClientUser.getUsr_idx();

  		SecureRandom sr = NcSecurityUtil.getSecureRandom();

  		sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
  		byte[] salt = new byte[SALT_LENGTH];
  		sr.nextBytes(salt);
  		ncClientUser.setUsr_salt(Hex.encodeHexString(salt));

  		String encodePassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(usr_pwd, Hex.encodeHexString(salt));
  		result += ncDoorayUserService.password_update(
  			iUsrIdx,
  			encodePassword,
  			Hex.encodeHexString(salt));
      	//--------------------------------------------------------
  		
  		String audit_page = MessageUtil.getMessage("clientUsr.account")+" "+MessageUtil.getMessage("btn.registration");
  		String audit_result = NcLog.getResultSF(result == 2 ? 1 : 0);
  		
  		String audit_param =
  				MessageUtil.getMessage("clientUsr.account") + " = " +
  					ncClientUser.getUsr_account();
  		ncAuditService.mng_log_insert(
  				(String)session.getAttribute("loginUserId"),
  				(String)session.getAttribute("clientIp"),
  				audit_page, audit_param, audit_result, "I"); // 감사로그 저장
  		
  		try {
  			usr_pwd.close();
  			usr_pwd_confirm.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return NcLog.getResultTD(result);
	}
	
	/**
	 * 사용자 관리 정보 출력
	 * 
	 * @param model
	 * @return
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/dooray_user_list.do", method = RequestMethod.GET)
	public String user_list(@RequestParam(value="edit_flag", required=false) String edit_flag, Model model) throws InvalidKeySpecException, NoSuchAlgorithmException {
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝

		ArrayList<NcClientUser> getClientUser = ncDoorayUserService.getClientUser();
		model.addAttribute("getClientUser", getClientUser);
		
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
		
		return "dooray/dooray_user_list";
	}
	
	/**
	 * 사용자 계정 사용여부 변경
	 * @param usr_idx
	 * @param usr_status
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dooray_user_useyn.do", method = RequestMethod.POST)
	@ResponseBody
	public String user_useyn(@RequestParam("usr_idx") int usr_idx, @RequestParam("usr_status") int usr_status, HttpSession session) {
		NcClientUser ncClientUser = ncDoorayUserService.getClientUserbyIdx(usr_idx);

		String content = "";
		int result = ncDoorayUserService.updateUsrStatus(usr_idx, usr_status);

		if(usr_status == 1) {
			content = MessageUtil.getMessage("log.user.changeuseyny");
		}else {
			content = MessageUtil.getMessage("log.user.changeuseynn");
		}

		String audit_page = MessageUtil.getMessage("clientUsr.changeuseyn");
		String audit_param = "ID = " + ncClientUser.getUsr_account()+", " + content;
		String audit_result = result == 1 ? "S" : "F";

		ncAuditService.mng_log_insert(
			(String)session.getAttribute("loginUserId"),
			(String)session.getAttribute("clientIp"),
			audit_page, audit_param, audit_result, "I"); // 감사로그 저장

		return result == 1 ? "true" : "DB Error";
	}
	
	/**
	 * 사용자 계정 정보 삭제
	 * @param user_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dooray_user_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String dooray_user_delete(@RequestParam("usr_idx") int usr_idx, HttpSession session) {
		NcClientUser ncClientUser = ncDoorayUserService.getClientUserbyIdx(usr_idx);
		
		String audit_param = "ID = "+ncClientUser.getUsr_account();
		
		int result = ncDoorayUserService.deleteUserIdx(usr_idx);
		if (result == 1) {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),
					MessageUtil.getMessage("clientUsr.account")+" "+MessageUtil.getMessage("btn.delete"),audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),
					MessageUtil.getMessage("clientUsr.account")+" "+MessageUtil.getMessage("btn.delete"),audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 사용자 정보 수정
	 * @param ndUserToUpdate
	 * @param email_server
	 * @param email_id
	 * @param session
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/dooray_update_user.do", method = RequestMethod.POST)
	@ResponseBody
	public String dooray_update_user(HttpSession session, @RequestParam("usr_account") String usr_account, 
			@RequestParam("orig_passwd") String orig_passwd, @RequestParam("new_passwd") String new_passwd, 
			@RequestParam("new_passwd_confirm") String new_passwd_confirm, @RequestParam("usr_srcip") String usr_srcip, 
			@RequestParam("usr_url") String usr_url, @RequestParam("usr_dstip") String usr_dstip ) {

		String usr_orig_account = "";
		String usr_orig_pwd = "";
		String usr_pwd = "";
		String usr_pwd_confirm = "";
		
		PrivateKey privateKey = ncSecurity.getPrivateKey();
		if (privateKey == null) {
            return "rsa_error";
        }

    	try {
    		usr_orig_account = RSAUtil.decryptRsa(privateKey, usr_account);
    		usr_orig_pwd = RSAUtil.decryptRsa(privateKey, orig_passwd);
    		if(new_passwd!=null) {
    			usr_pwd = RSAUtil.decryptRsa(privateKey, new_passwd);
    		}
    		if(new_passwd_confirm!=null) {
    			usr_pwd_confirm = RSAUtil.decryptRsa(privateKey, new_passwd_confirm);
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "rsa_error";
		}
    	
    	if (usr_orig_pwd.length() < 1) {
            return "not_origpw_length";
        }
    	NcClientUser ncClientUser = ncDoorayUserService.getClientUserbyId(usr_orig_account);
    	
    	// 패스워드 확인
        String encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(usr_orig_pwd, ncClientUser.getUsr_salt());
        
        if(!encodedPassword.equals(ncClientUser.getUsr_pwd())) {
        	usr_orig_pwd.close();//  현재 비밀번호 저장 변수 초기화
    		usr_pwd.close();//  새 비밀번호 저장 변수 초기화
    		usr_pwd_confirm.close();//  새 비밀번호 확인 저장 변수 초기화
        	return "not_match_passwd";
        }
        
        String audit_page = MessageUtil.getMessage("clientUsr.editUserInfo");
        String audit_param = "ID = "+ncClientUser.getUsr_account()+","+MessageUtil.getMessage("log.sourceip")+" = "+usr_srcip+",URL = "+usr_url+
        		","+MessageUtil.getMessage("log.destnationip")+" = "+usr_dstip;
        
        if(usr_pwd!=null && !usr_pwd.equals("") ) {
        	// 최소 9자 이상 최대 127자 이하인지 체크
            if (usr_pwd.length() < 9 || usr_pwd.length() > 127) {
                return "not_enough_length";
            }
            if (usr_pwd_confirm.length() < 1) {
                return "not pw_confirm";
            }
            if (usr_orig_pwd.equals(usr_pwd)) {
                return "not_same_pw";
            }

            // 문자, 숫자, 특수문자의 조합인지 확인
            // 일부 특수문자만 지원
            Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
            // 모든 특수문자 지원
            // https://en.wikipedia.org/wiki/List_of_Special_Characters_for_Passwords
            // Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}|\\s]).{9,127}$");
            Matcher m = p.matcher(usr_pwd);
            if (!m.find()) {
                return "not_rule";
            }

            Pattern p0 = Pattern.compile("^[a-zA-Z0-9!@#$%^&*?_~]*$");
            m = p0.matcher(usr_pwd);
            if (!m.find()) {
                return "not_rule";
            }

            // 3자리 이상 연속 문자, 숫자
            Pattern p3 = Pattern.compile("(\\p{Print})\\1\\1+");
            m = p3.matcher(usr_pwd);
            if (m.find()) {
                return "not_continuity";
            }

            String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
            String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
            for (int j = 0; j < arrKeyboardThreeChar.length; j++) {
                if (usr_pwd.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
                    return "not_qwer";
                }
            }
            String listKeyboardThreeChar2 = "123|234|345|456|567|678|789|890|321"
                    + "|432|543|654|765|876|987|098";
            String[] arrKeyboardThreeChar2 = listKeyboardThreeChar2.split("\\|");
            for (int j = 0; j < arrKeyboardThreeChar2.length; j++) {
                if (usr_pwd.toLowerCase().matches(".*" + arrKeyboardThreeChar2[j] + ".*")) {
                    return "not_qwer";
                }
            }

            if (usr_pwd.indexOf(ncClientUser.getUsr_account()) != -1) {
                return "not_id_pw";
            }

            SecureRandom sr = NcSecurityUtil.getSecureRandom();

    		// 직전 사용 패스워드 재사용 금지
            if(ncClientUser.getUsr_prev_salt() != null && !ncClientUser.getUsr_prev_salt().equals("")) {
            	encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(usr_pwd, ncClientUser.getUsr_prev_salt());
    			if (encodedPassword.equals(ncClientUser.getUsr_prev_pwd())) {
    				return "same_with_prev_pw";
    			}
            }
            
    		sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
    		byte[] salt = new byte[SALT_LENGTH];
    		sr.nextBytes(salt);

    		encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(usr_pwd, Hex.encodeHexString(salt));
    		int result = ncDoorayUserService.password_update(
    			ncClientUser.getUsr_idx(),
    			encodedPassword,
    			Hex.encodeHexString(salt)
    		);
    		audit_param = MessageUtil.getMessage("user.changepw")+","+audit_param;
    		if(result<1) {

    			ncAuditService.mng_log_insert(
    				(String)session.getAttribute("loginUserId"),
    				(String)session.getAttribute("clientIp"),
    				audit_page, audit_param, "F","I"); // 감사로그 저장
    			
    	        usr_orig_pwd.close();//  현재 비밀번호 저장 변수 초기화
    	    	usr_pwd.close();//  새 비밀번호 저장 변수 초기화
    	    	usr_pwd_confirm.close();//  새 비밀번호 확인 저장 변수 초기화
    		}
        }
        int result = ncDoorayUserService.update_client_user(ncClientUser.getUsr_idx(), usr_srcip, usr_url, usr_dstip);

		String audit_result = NcLog.getResultSF(result);
        ncAuditService.mng_log_insert(
    			(String)session.getAttribute("loginUserId"),
    			(String)session.getAttribute("clientIp"),
    			audit_page, audit_param, audit_result,"I"); // 감사로그 저장
        if(usr_orig_pwd!=null) {
        	usr_orig_pwd.close();//  현재 비밀번호 저장 변수 초기화
        }
        
        if (result == 1) {
        	ncDoorayUserService.updateUsrLastTime(ncClientUser.getUsr_idx()); // 마지막 로그인 일시 변경
			return "true";
		}
		else {
			return "DB Error";
		}
		
		
		
    	/*
		int result = ncUserService.update(ndUserToUpdate);
		if (result == 1) {
			//ncTransferService.trans_system_user("U", ndUserToUpdate); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내 정보 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내 정보 수정 ",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
		*/
	}
}