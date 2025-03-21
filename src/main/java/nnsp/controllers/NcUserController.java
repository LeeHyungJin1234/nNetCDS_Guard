package nnsp.controllers;

import java.nio.charset.StandardCharsets;
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
import java.util.Objects;
import java.util.regex.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nnsp.security.Constants;
import nnsp.security.NcSecurityUtil;
import nnsp.security.RSAUtil;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcMenuService;
import nnsp.services.NcSecurity;
import nnsp.services.NcTransferService;
import nnsp.services.NcUserService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcLog;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static nnsp.security.Constants.RAND_SALT_LENGTH;
import static nnsp.security.Constants.SALT_LENGTH;

@Controller
public class NcUserController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcUserController.class);
		
	@Autowired
	NcAuditService ncAuditService;
	@Autowired
	NcUserService ncUserService;
	@Autowired
	NcMenuService ncMenuService;
	@Autowired
	NcTransferService ncTransferService;
	@Autowired
	NcSecurity ncSecurity;
	@Autowired
	NcConfigService ncConfigService;
	

	/**
	 * 최초 로그인 시 변경 팝업 호출
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/firstlogin_popup.do", method = RequestMethod.GET)
	public String firstlogin_popup(HttpSession session, Model model) {
		return "user/firstlogin_popup";
	}
	
	/**
	 * 패스워드 변경 팝업 호출
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pwchange_popup.do", method = RequestMethod.GET)
	public String pwchange_popup(HttpSession session, Model model) {
		return "user/pwchange_popup";
	}

	/**
	 * 최초 로그인 시 패스워드 변경
	 * @param req
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/passwd_change.do", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String passwd_change(HttpServletRequest req, Model model, HttpSession session) {
		//암호화 추가1
		PrivateKey privateKey = ncSecurity.getPrivateKey();
		if (privateKey == null) {
            return "rsa_error";
        }
		
		String new_pw="", orig_pw="", new_id=""; 
		String new_pw_confirm;
		
    	try {
    		String new_passwd = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd"));
			String orig_passwd = RSAUtil.decryptRsa(privateKey, req.getParameter("orig_passwd"));
			new_id = RSAUtil.decryptRsa(privateKey, req.getParameter("new_id"));
			String new_passwd_confirm = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd_confirm"));
			

	        // 클라이언트(브라우저) 메모리 평문 노출 방지 위해 변형된 값을 복구
	        new_pw = NcSecurityUtil.getRotatedString(new_passwd, Constants.ROTATE);
	        orig_pw = NcSecurityUtil.getRotatedString(orig_passwd, Constants.ROTATE);
	        new_pw_confirm = NcSecurityUtil.getRotatedString(new_passwd_confirm, Constants.ROTATE);

	        new_passwd.close();
	        orig_passwd.close();
	        new_passwd_confirm.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "rsa_error";
		}
    	//암호화 추가1 끝
    	
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
		NcUser ncUserData = ncUserService.getUserbyId(nsuser.getNsu_id());
		
		// 현재 비밀번호가 맞는지 확인
		String encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(orig_pw, ncUserData.getNsu_salt());
		if (!encodedPassword.equals(ncUserData.getNsu_pw())) {
			return "not_match_passwd";
		}

		// 최소 9자 이상 최대 127자 이하인지 체크
		if (new_pw.length() < 9 ||new_pw.length() > 127) {
			return "not_enough_length";
		}
		
		if(orig_pw.length() < 1) {
			return "not_origpw_length";
		}
		
		if(new_id.length() < 1) {
			return "not_id_length";
		}
		
		if(new_pw.length() < 1) {
			return "not_newpw_length";
		}
		
		if(new_pw_confirm.length() < 1) {
			return "not pw_confirm";
		}
		if(orig_pw == new_pw) {
			return "not_same_pw";
		}
		if(new_pw.length() < 9) {
			return "not_pw9";
		}
		
		// 문자, 숫자, 특수문자의 조합인지 확인
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
		Matcher m = p.matcher(new_pw);
		if (!m.find()) {
			return "not_rule";
		}
		
		// 아이디 영 소문자
		Pattern p1 = Pattern.compile("^[a-zA-Z0-9]*$");
		m = p1.matcher(new_id);
		if (!m.find()) {
			return "id_rule";
		}
		//같은 문자 숫자 체크
		
		Pattern p2 = Pattern.compile("(\\w)\\1\\1\\1"); 
		m = p2.matcher(new_pw);
		if(m.find()) { 
			return "not_same"; 
			}
		
		
		 
		
		//연속 문자, 숫자
		int o = 0;
		int d = 0;
		int z = 0;
		int n =o;
		int limit = 4;
		
		for(int i=0; i< new_pw.length(); i++) {
			char tempVal = new_pw.charAt(i);
			if(i>0 && (z = o - tempVal) >-2&&(n=z==d? n+1:0)>limit-3) {
				return "not_continuity";
			}
			d=z;
			o=tempVal;
		}
		
		String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
        String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
        for (int j=0; j<arrKeyboardThreeChar.length; j++) {
            if(new_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
                return "not_qwer";
            }
        }
	
	

        //   관리자 비밀번호 이자 KEK 값
        SecureRandom sr = NcSecurityUtil.getSecureRandom();

		sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
		byte[] salt = new byte[SALT_LENGTH];
		sr.nextBytes(salt);
		encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(new_pw,
    			Hex.encodeHexString(salt));

		if (!ncConfigService.createKey(encodedPassword)) {
            return "KEY Error";
        }
		
		
		int result = ncUserService.firstlogin_update(
	            nsuser.getNsu_seq(),
	            encodedPassword,
	            Hex.encodeHexString(salt),
	            new_id);
		
		String audit_param = MessageUtil.getMessage("login.newid") + " = " + new_id;
        String audit_result = result == 1 ? "S" : "F";
        String audit_page = MessageUtil.getMessage("log.login.initialidpw");
        ncAuditService.mng_log_insert(
                (String) session.getAttribute("loginUserId"),
                (String) session.getAttribute("clientIp"),
                audit_page, audit_param, audit_result, "I"); // 감사로그 저장
        
        try {
            orig_pw.close();
            new_pw.close();
            new_pw_confirm.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (result == 1) {
            ncUserService.last_login_update(nsuser.getNsu_seq()); // 마지막 로그인 일시 변경

            return "true";
        } else {
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
	@RequestMapping(value = "/pwchange_useredit.do", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String pwchange_useredit(HttpServletRequest req, HttpServletResponse res, Model model, HttpSession session) {
		//암호화 추가1
		try {
			PrivateKey privateKey = ncSecurity.getPrivateKey();
	    	if (privateKey == null) {
	    		return "privateKey_error";
	        }
	    	
	    	String tmp_orig_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("orig_passwd"));
            String tmp_new_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd"));
            String tmp_new_pw_confirm = RSAUtil.decryptRsa(privateKey, req.getParameter("new_passwd_confirm"));
            
            String orig_pw = NcSecurityUtil.getRotatedString(tmp_orig_pw, Constants.ROTATE);
            String new_pw = NcSecurityUtil.getRotatedString(tmp_new_pw, Constants.ROTATE);
            String new_pw_confirm = NcSecurityUtil.getRotatedString(tmp_new_pw_confirm, Constants.ROTATE);
            
            NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
    		NcUser ncUserData = ncUserService.getUserbyId(nsuser.getNsu_id());
    		
    		// 현재 비밀번호가 맞는지 확인
            String encodePassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(orig_pw, ncUserData.getNsu_salt());
    		if(!encodePassword.equals(ncUserData.getNsu_pw())) {
    			return "pw_not_match";
    		}
    		
    		// 최소 9자 이상 최대 127자 이하인지 체크
            if (new_pw.length() < 9 || new_pw.length() > 127) {
                return "pw_not_enough_length";
            }
            
            if (orig_pw.equals(new_pw)) {
                return "pw_not_same_new";
            }
            
            Pattern pw_p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
            Matcher pw_m = pw_p.matcher(new_pw);
            if (!pw_m.find()) {
                return "pw_not_rule";
            }
            
         // 비밀번호 연속 문자열 검사
            Pattern pw_p_conti = Pattern.compile("(\\p{Print})\\1\\1+");
            Matcher ps_m_conti = pw_p_conti.matcher(new_pw);
            if (ps_m_conti.find()) {
                return "pw_not_continuity";
            }
            
            String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh"
				+ "|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy"
				+ "|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
			String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
			for (int j = 0; j < arrKeyboardThreeChar.length; j++) {
				if (new_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
					return "pw_not_qwer";
				}
			}
			String listKeyboardThreeChar2 = "123|234|345|456|567|678|789|890|321"
				+ "|432|543|654|765|876|987|098";
			String[] arrKeyboardThreeChar2 = listKeyboardThreeChar2.split("\\|");
			for (int j = 0; j < arrKeyboardThreeChar2.length; j++) {
				if (new_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar2[j] + ".*")) {
					return "pw_not_qwer";
				}
			}
			
			// 비밀번호가 ID와 동일한지 확인
            if (new_pw.indexOf(ncUserData.getNsu_id()) != -1) {
                return "pw_not_id";
            }
            
            // 비밀번호 확인란과 비밀번호 일치 여부 검사
            if (!new_pw.equals(new_pw_confirm)) {
                return "pw_not_same_confirm";
            }
            
    		// 직전 사용 패스워드 재사용 금지
            if(ncUserData.getNsu_prev_salt() != null && !ncUserData.getNsu_prev_salt().equals("")) {
    		encodePassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(new_pw, ncUserData.getNsu_prev_salt());
    			if (encodePassword.equals(ncUserData.getNsu_prev_pw())) {
    				return "pw_same_with_prev";
    			}
            }
            
            SecureRandom sr = NcSecurityUtil.getSecureRandom();
            
            sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
    		byte[] salt = new byte[SALT_LENGTH];
    		sr.nextBytes(salt);

    		encodePassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(new_pw, Hex.encodeHexString(salt));
    		
    		int result = ncUserService.password_update(
				nsuser.getNsu_seq(),
				encodePassword,
				Hex.encodeHexString(salt)
			);
    		
    		String audit_page = MessageUtil.getMessage("user.changepw");
    		String audit_result = NcLog.getResultSF(result);
    		
    		ncAuditService.mng_log_insert(
				(String)session.getAttribute("loginUserId"),
				(String)session.getAttribute("clientIp"),
				audit_page, null, audit_result,"I"); // 감사로그 저장
			
			try {
				new_pw.close();
				new_pw_confirm.close();
				orig_pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if (result == 1) {
				ncUserService.last_login_update(nsuser.getNsu_seq()); // 마지막 로그인 일시 변경
				NcSecurityUtil.logout(req, res);// 로그아웃 처리
				return "true";
			}
			else {
				return "DB Error";
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "rsa_error";
		}
	}

	/**
	 * 관리자 정보 수정화면 호출
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String user_editVw(@RequestParam("user_id") String user_id, Model model) {
		NcUser ncUserData = ncUserService.getUserbyId(user_id);
		String email = ncUserData.getNsu_email();
		String email_id = "";
		String email_serv = "";
		int index = email.indexOf("@");
		if (index != -1) {
			email_id = email.substring(0, index);
			email_serv = email.substring(index + 1);
		}
		String result = "<div class='section03'>"+
			"<h2>필수입력</h2><p class='border_title'></p>"+
			"<ul>"+
				"<li style='margin-right:240px;'>"+
					"<label>이름</label>"+
					"<input type='text' id='nsu_name' name='nsu_name' value='"+ncUserData.getNsu_name()+"'/>"+
				"</li>"+
				"<li style='margin-right:266px;'>"+
					"<label>비밀번호</label>"+
					"<a href='javascript:$(\"#pwpopup\").fadeIn();' id='white-bottom-btn2' style='margin-left:8px;'><img src='/img/modify.png' style='margin:6px 8px 0 0px;'>비밀번호 변경</a>"+
				"</li>"+
				"<li>"+
					"<label>이메일 주소</label>"+
					"<input type='text' id='nsu_email_id' name='nsu_email_id' class='input_1' value='"+email_id+"'>@<input type='text' id='nsu_email_server' name='nsu_email_server' class='input_2' value='"+email_serv+"'> "+
					"<select id='email_sel' name='email_sel' onChange='selectValue(this.form, this.value)'>"+
						"<option value=''>직접선택</option>"+										
						"<option value='naver.com'>naver.com</option>"+
						"<option value='dreamwiz.com'>dreamwiz.com</option>"+
						"<option value='empal.com'>empal.com</option>"+
						"<option value='freechal.com'>freechal.com</option>"+
						"<option value='daum.net'>daum.net</option>"+
						"<option value='gmail.com'>gmail.com</option>"+
						"<option value='hanmail.net'>hanmail.net</option>"+
						"<option value='hanmir.com'>hanmir.com</option>"+
						"<option value='hotmail.com'>hotmail.com</option>"+
						"<option value='nate.com'>nate.com</option>"+
						"<option value='lycos.co.kr'>lycos.co.kr</option>"+
						"<option value='netian.com'>netian.com</option>"+
						"<option value='paran.com'>paran.com</option>"+
						"<option value='yahoo.com'>yahoo.com</option>"+
						"<option value='yahoo.co.kr'>yahoo.co.kr</option>"+
					"</select>"+
				"</li>"+
			"</ul>"+
		"</div>"+
		"<div class='section02' style='position:relative;'>"+
			"<h2>선택입력</h2><p class='border_title'></p>"+
			"<ul>"+
				"<li class='clearfix'>"+
					"<label style='padding-top:8px'>부서</label>"+
					"<input type='text' style='margin-left:12px' id='nsu_division' name='nsu_division' value='"+ncUserData.getNsu_division()+"'/>"+
				"</li>"+
				"<li style='position:absolute; right:44px; top:58px;'>"+
					"<label style='padding-top:8px'>직급</label>"+
					"<input type='text' style='margin-left:10px' id='nsu_position' name='nsu_position' value='"+ncUserData.getNsu_position()+"'/>"+
				"</li>"+
				"<li>"+
					"<label>유선전화</label>"+
					"<input type='text' id='nsu_tel' name='nsu_tel' placeholder='ex) 02-123-4567' value='"+ncUserData.getNsu_tel()+"'/>"+
				"</li>"+
				"<li>"+
					"<label>휴대전화</label>"+
					"<input type='text' id='nsu_mobile' name='nsu_mobile' placeholder='ex) 010-1234-5678' value='"+ncUserData.getNsu_mobile()+"'/>"+
				"</li>"+
				"<li>"+
					"<span class='text-main-color2'>설명</span>"+
					"<textarea id='nsu_desc' name='nsu_desc' style='width:405px;height:64px; padding-top:10px;'>"+ncUserData.getNsu_desc()+"</textarea>"+
				"</li>"+
			"</ul>"+
		"</div>";
		return result;
	}

	/**
	 * 관리자 정보 수정
	 * @param ndUserToUpdate
	 * @param email_server
	 * @param email_id
	 * @param session
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/user_edit.do", method = RequestMethod.POST)
	@ResponseBody
	public String user_edit(NcUser ndUserToUpdate, @RequestParam("nsu_email_server") String nsu_email_server, @RequestParam("nsu_email_id") String nsu_email_id, HttpSession session) {
		NcUser nsuser = ((NcUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser();
		ndUserToUpdate.setNsu_seq(nsuser.getNsu_seq());
		String email = nsu_email_id + "@" + nsu_email_server;
		ndUserToUpdate.setNsu_email(email);

		String audit_param = "이름 = "+ndUserToUpdate.getNsu_name()+",부서 = "+ndUserToUpdate.getNsu_division()+",직급 = "+ndUserToUpdate.getNsu_position()+",유선전화 = "+ndUserToUpdate.getNsu_tel()
				+",휴대전화 = "+ndUserToUpdate.getNsu_mobile()+",이메일 = "+ndUserToUpdate.getNsu_email()+",비고 = "+ndUserToUpdate.getNsu_desc();
				
		// 아이디 영 소문자
//				Pattern p1 = Pattern.compile("^[a-zA-Z0-9]*$");
//				Matcher m = p1.matcher(ndUserToUpdate.getNsu_name());
//				if (!m.find()) {
//					return "id_rule";
//				}
		int result = ncUserService.update(ndUserToUpdate);
		if (result == 1) {
			//ncTransferService.trans_system_user("U", ndUserToUpdate); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내 정보 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"내 정보 수정 ",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}

	/**
	 * 관리자 관리 정보 출력
	 * 
	 * @param model
	 * @return
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/user_list.do", method = RequestMethod.GET)
	public String user_list(@RequestParam(value="edit_flag", required=false) String edit_flag, Model model) throws InvalidKeySpecException, NoSuchAlgorithmException {
		ArrayList<NcUser> getSystemUser = ncUserService.getSystemUser();
		model.addAttribute("getSystemUser", getSystemUser);
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		model.addAttribute("getSystemUser", getSystemUser);
		model.addAttribute("ncuserdata", new NcUser());
		model.addAttribute("edit_flag", edit_flag);
		
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
		
		return "user/user_list";
	}

	/**
	 * 관리자 관리 계정 정보 삭제
	 * @param user_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/user_delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String user_delete(@RequestParam("user_seq") int user_seq, HttpSession session) {		
		NcUser ncUserData = ncUserService.getUserbySeq(user_seq);
		
		String audit_param = "관리자ID = "+ncUserData.getNsu_id();
		
		int result = ncUserService.yn_status_update(user_seq);
		if (result == 1) {
			//ncTransferService.trans_system_user("D", ndUserData); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 계정 삭제",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 계정 삭제",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}

	/**
	 * 관리자 계정 관리 페이지 호출
	 * 
	 * @param ndUserData
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user_regit.do", method = RequestMethod.GET)
	public String user_regit(NcUser ncUserData, Model model) {		
		// 메뉴 만들기 시작
		model.addAttribute("total_menu", ncMenuService.getTotalMenu());
		model.addAttribute("now_menu", ncMenuService.getNowMenu(400002));
		model.addAttribute("title", ncMenuService.getNowTitle(400002));
		model.addAttribute("menu_id", 400002);
		// 메뉴 만들기 끝
		model.addAttribute("ncuserdata", ncUserData);
		return "user/user_regit";			
	}
	
	/**
	 * 관리자 계정 추가
	 * @param ndUserToInsert
	 * @param email_id
	 * @param email_server
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/insert_user.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_user(NcUser ncUserToInsert, HttpSession session) {
		//관리자 등록 최대 2개까지
		if (ncUserService.getSystemUser().size() >= 2) {
			return "upto2";
		}
		
		
		//ID 검증
		String nsu_id = ncUserToInsert.getNsu_id();
		//ID 중복 체크
		if(ncUserService.getUserbyId(nsu_id) != null){
			return "id_duplicate";
		}
		
		// 비밀번호 검증
        String nsu_pw = decryptAndValidatePassword(ncUserToInsert.getNsu_pw(), ncUserToInsert.getNsu_pw_confirm(), nsu_id);
        if (nsu_pw.startsWith("pw_") || nsu_pw.equals("privateKey_error") || nsu_pw.equals("decrypt_rsa_error")) {
            return nsu_pw; // 검증 실패 시 오류 메시지 반환
        }
        
        // 이름 검증
//        String nsu_name = ncUserToInsert.getNsu_name();
//        if (nsu_name.length() > 64) {
//            return "name_not_length";
//        }
//
//        // 이메일 검증
//        String email = validateEmail(ncUserToInsert.getNsu_email_id(), ncUserToInsert.getNsu_email_server());
//        if (email == null) {
//            return "email_not_email";
//        }

//        ncUserToInsert.setNsu_email(email);
        
//        // 추가 정보 검증
//        if (ncUserToInsert.getNsu_division() != null && ncUserToInsert.getNsu_division().length() > 64) {
//            return "not_division_empty";
//        }
//        if (ncUserToInsert.getNsu_position() != null && ncUserToInsert.getNsu_position().length() > 64) {
//            return "not_position_empty";
//        }
        
		//PW 검증
//		String tmp_nsu_pw = "";
//		String tmp_nsu_pw_confirm = "";
//		String nsu_pw = "";					//비밀번호
//		String nsu_pw_confirm = "";			//비밀번호 확인
//		
//		PrivateKey privateKey = ncSecurity.getPrivateKey();
//		if (privateKey == null) {
//            return "privateKey_error";
//        }
//		
//		//RSA 복호화 및 keydown 시 적용됐던 rotate해제
//    	try {
//    		tmp_nsu_pw = RSAUtil.decryptRsa(privateKey, ncUserToInsert.getNsu_pw());
//    		tmp_nsu_pw_confirm = RSAUtil.decryptRsa(privateKey, ncUserToInsert.getNsu_pw_confirm());
//            
//			nsu_pw = NcSecurityUtil.getRotatedString(tmp_nsu_pw, Constants.ROTATE);
//			nsu_pw_confirm = NcSecurityUtil.getRotatedString(tmp_nsu_pw_confirm, Constants.ROTATE);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "decrypt_rsa_error";
//		}
//		
//		// 최소 9자 이상 127자 이하인지 체크
//		if (nsu_pw.length() < 9 || nsu_pw.length() > 127) {
//			try {
//				nsu_pw.close();
//				nsu_pw_confirm.close();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//			return "pw_not_enough_length";
//		}
//		// 영어 대소문자, 숫자, 특수문자의 최소 1개 이상 조합인지 확인 정규식
//		Pattern pw_p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
//		// 모든 특수문자와 공백 포함을 지원
//		// Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}|\\s]).{9,127}$");
//		Matcher pw_m = pw_p.matcher(nsu_pw);
//		if (!pw_m.find()) {
//			try {
//				nsu_pw.close();
//				nsu_pw_confirm.close();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//			return "pw_not_rule";
//		}
//		//ID와 같은지 검사
//		if (nsu_pw.indexOf(nsu_id) != -1) {
//			try {
//				nsu_pw.close();
//				nsu_pw_confirm.close();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//			return "pw_not_id";
//		}
//		// 3자리 이상 연속 문자, 숫자
//		Pattern pw_p_conti = Pattern.compile("(\\p{Print})\\1\\1+");
//		Matcher ps_m_conti = pw_p_conti.matcher(nsu_pw);
//		if (ps_m_conti.find()) {
//			try {
//				nsu_pw.close();
//				nsu_pw_confirm.close();
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//			return "pw_not_continuity";
//		}
//		// 키보드상 연속된 문자 또는 숫자 
//		String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
//		String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
//		String listKeyboardThreeNum = "123|234|345|456|567|678|789|890|321|432|543|654|765|876|987|098";
//		String[] arrKeyboardThreeNum = listKeyboardThreeNum.split("\\|");
//		//문자 배열과 숫자 배열 합치기
//		String[] arrCombined = new String[arrKeyboardThreeChar.length + arrKeyboardThreeNum.length];
//		System.arraycopy(arrKeyboardThreeChar, 0, arrCombined, 0, arrKeyboardThreeChar.length);
//		System.arraycopy(arrKeyboardThreeNum, 0, arrCombined, arrKeyboardThreeChar.length, arrKeyboardThreeNum.length);
//		// 패턴이 포함되어 있는지 확인
//		for (String pattern : arrCombined) {
//		    if (nsu_pw.toLowerCase().contains(pattern)) {
//		    	try {
//					nsu_pw.close();
//					nsu_pw_confirm.close();
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//		        return "pw_not_qwer";
//		    }
//		}
//		//비밀번호 확인란이 비밀번호와 일치 하는지
//		if (!nsu_pw.equals(nsu_pw_confirm)) {
//			return "pw_not_same_confirm";
//		}
		
		
		//이름 검증
//		String nsu_name = ncUserToInsert.getNsu_name();
//		// 64자 이하인지 체크
//		if (nsu_name.length() > 64) {
//			return "name_not_length";
//		}
		
		
		//이메일 검증
		String email_id = ncUserToInsert.getNsu_email_id();
		String email_server = ncUserToInsert.getNsu_email_server();
		//이메일 주소가 192자 보다 긴지
		byte[] emailBytes = (email_id+"@"+email_server).getBytes(StandardCharsets.UTF_8);
		if(emailBytes.length > 192) {
			return "email_too_long";
		}
		//이메일 주소 정규식 ex) test@naver.com OR test@nnsp.co.kr
		String email = email_id + "@" + email_server;
		Pattern email_p = Pattern.compile("[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\\.[_0-9a-zA-Z-]+){1,2}$");
		Matcher email_m = email_p.matcher(email);
		if (!email_m.find()) {
			return "email_not_email";
		}
		
		
		
		
		if(ncUserToInsert.getNsu_division() != "") {
			if(ncUserToInsert.getNsu_division().length()>64) {
			return "not_division_empty";
			}
		}
		if(ncUserToInsert.getNsu_position() != "") {
			if(ncUserToInsert.getNsu_position().length() > 64) {
			return "not_position_empty";
			}
		}
		
		
//		if(ncUserToInsert.getNsu_tel() != "") {
//			//같은 문자 숫자 체크
//			Pattern p4 = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
//			m = p4.matcher(ncUserToInsert.getNsu_tel());
//			if(!m.find()) {
//				return "not_valid_tel";
//			}
//		}
//		if(ncUserToInsert.getNsu_mobile() != "") {
//			Pattern p5 = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
//			m = p5.matcher(ncUserToInsert.getNsu_mobile());
//			if(!m.find()) {
//				return "not_valid_mob";
//			}
//		}
//		if(ncUserToInsert.getNsu_email() == "") {
//			return "not_email_empty";
//		}
		
		
		
		ncUserToInsert.setNsu_email(email);
		
		String id = ncUserToInsert.getNsu_id();
		String pw = nsu_pw;
		ncUserToInsert.setNsu_pw(null);
		int result = ncUserService.insert_system_user(ncUserToInsert);	
		
		//Password encrypt logic----------------------------------
		result += encryptPassword(id, pw);
		
//  		NcUser ncuser = ncUserService.getUserbyId(id);
//  		int seq = ncuser.getNsu_seq();
//
//  		SecureRandom sr = NcSecurityUtil.getSecureRandom();
//
//  		sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
//  		byte[] salt = new byte[SALT_LENGTH];
//  		sr.nextBytes(salt);
//  		ncuser.setNsu_salt(Hex.encodeHexString(salt));
//
//  		String encodePassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(pw, Hex.encodeHexString(salt));
//  		result += ncUserService.password_update(
//  			seq,
//  			encodePassword,
//  			Hex.encodeHexString(salt));
      	//--------------------------------------------------------
  		logAudit(session, ncUserToInsert, result);

//  		String audit_page = MessageUtil.getMessage("log.user.addadmin");
//  		String audit_result = NcLog.getResultSF(result == 2 ? 1 : 0);
//  		
//  		String audit_param =
//  				MessageUtil.getMessage("user.adminid") + " = " +
//  					ncUserToInsert.getNsu_id() + ", " +
//  				MessageUtil.getMessage("user.name") + " = " +
//  					ncUserToInsert.getNsu_name() + ", " +
//  				MessageUtil.getMessage("user.department") + " = " +
//  					ncUserToInsert.getNsu_division() +", " +
//  				MessageUtil.getMessage("user.rank") + " = " +
//  					ncUserToInsert.getNsu_position() +", " +
//  				MessageUtil.getMessage("user.wireline") + " = " +
//  					ncUserToInsert.getNsu_tel() +", " +
//  				MessageUtil.getMessage("user.cellphone") + " = " +
//  					ncUserToInsert.getNsu_mobile() +", " +
//  				MessageUtil.getMessage("user.email") + " = " +
//  					ncUserToInsert.getNsu_email() +", " +
//  				MessageUtil.getMessage("config.desc") + " = " +
//  					ncUserToInsert.getNsu_desc();
//  		ncAuditService.mng_log_insert(
//  				(String)session.getAttribute("loginUserId"),
//  				(String)session.getAttribute("clientIp"),
//  				audit_page, audit_param, audit_result, "I"); // 감사로그 저장
  		
//  		try {
//			nsu_pw.close();
//			nsu_pw_confirm.close();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

		return NcLog.getResultTD(result);
	}
	
	private String decryptAndValidatePassword(String encryptedPw, String encryptedPwConfirm, String nsu_id) {
        try {
            PrivateKey privateKey = ncSecurity.getPrivateKey();
            if (privateKey == null) {
                return "privateKey_error";
            }
            // RSA 복호화
            String tmp_nsu_pw = RSAUtil.decryptRsa(privateKey, encryptedPw);
            String tmp_nsu_pw_confirm = RSAUtil.decryptRsa(privateKey, encryptedPwConfirm);
            
            String nsu_pw = NcSecurityUtil.getRotatedString(tmp_nsu_pw, Constants.ROTATE);
            String nsu_pw_confirm = NcSecurityUtil.getRotatedString(tmp_nsu_pw_confirm, Constants.ROTATE);

            // 비밀번호 길이 검증
            if (nsu_pw.length() < 9 || nsu_pw.length() > 127) {
                return "pw_not_enough_length";
            }
            
			String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh"
				+ "|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy"
				+ "|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
			String[] arrKeyboardThreeChar = listKeyboardThreeChar.split("\\|");
			for (int j = 0; j < arrKeyboardThreeChar.length; j++) {
				if (nsu_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar[j] + ".*")) {
					return "pw_not_qwer";
				}
			}
			String listKeyboardThreeChar2 = "123|234|345|456|567|678|789|890|321"
				+ "|432|543|654|765|876|987|098";
			String[] arrKeyboardThreeChar2 = listKeyboardThreeChar2.split("\\|");
			for (int j = 0; j < arrKeyboardThreeChar2.length; j++) {
				if (nsu_pw.toLowerCase().matches(".*" + arrKeyboardThreeChar2[j] + ".*")) {
					return "pw_not_qwer";
				}
			}

            // 비밀번호 규칙 검증
            Pattern pw_p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?_~])(?=.*[0-9]).{9,127}$");
            Matcher pw_m = pw_p.matcher(nsu_pw);
            if (!pw_m.find()) {
                return "pw_not_rule";
            }

            // 비밀번호가 ID와 동일한지 확인
            if (nsu_pw.indexOf(nsu_id) != -1) {
                return "pw_not_id";
            }

            // 비밀번호 연속 문자열 검사
            Pattern pw_p_conti = Pattern.compile("(\\p{Print})\\1\\1+");
            Matcher ps_m_conti = pw_p_conti.matcher(nsu_pw);
            if (ps_m_conti.find()) {
                return "pw_not_continuity";
            }

            // 비밀번호 확인란과 비밀번호 일치 여부 검사
            if (!nsu_pw.equals(nsu_pw_confirm)) {
                return "pw_not_same_confirm";
            }

            return nsu_pw;
        } catch (Exception e) {
            e.printStackTrace();
            return "decrypt_rsa_error";
        }
    }

    @SuppressWarnings("unused")
	private String validateEmail(String email_id, String email_server) {
        String email = email_id + "@" + email_server;
        // 이메일 길이 검증
        if (email.getBytes(StandardCharsets.UTF_8).length > 192) {
            return null;
        }
        // 이메일 형식 검증
        Pattern email_p = Pattern.compile("[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\\\\.[_0-9a-zA-Z-]+){1,2}$");
        Matcher email_m = email_p.matcher(email);
        if (!email_m.find()) {
            return null;
        }
        return email;
    }
    
    private int encryptPassword(String id, String pw) {
        NcUser ncuser = ncUserService.getUserbyId(id);
        int seq = ncuser.getNsu_seq();
        
        SecureRandom sr = NcSecurityUtil.getSecureRandom();
        sr.setSeed(sr.generateSeed(RAND_SALT_LENGTH));
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        ncuser.setNsu_salt(Hex.encodeHexString(salt));

        String encodePassword = NcSecurityUtil.getSHA256Encrypt(pw, Hex.encodeHexString(salt));
        return ncUserService.password_update(seq, encodePassword, Hex.encodeHexString(salt));
    }

    private void logAudit(HttpSession session, NcUser ncUserToInsert, int result) {
        String audit_page = MessageUtil.getMessage("log.user.addadmin");
        String audit_result = NcLog.getResultSF(result == 2 ? 1 : 0);
        
        String audit_param =
                MessageUtil.getMessage("user.adminid") + " = " + ncUserToInsert.getNsu_id() + ", " +
                MessageUtil.getMessage("user.name") + " = " + ncUserToInsert.getNsu_name() + ", " +
                MessageUtil.getMessage("user.department") + " = " + ncUserToInsert.getNsu_division() +", " +
                MessageUtil.getMessage("user.rank") + " = " + ncUserToInsert.getNsu_position() +", " +
                MessageUtil.getMessage("user.wireline") + " = " + ncUserToInsert.getNsu_tel() +", " +
                MessageUtil.getMessage("user.cellphone") + " = " + ncUserToInsert.getNsu_mobile() +", " +
                MessageUtil.getMessage("user.email") + " = " + ncUserToInsert.getNsu_email() +", " +
                MessageUtil.getMessage("config.desc") + " = " + ncUserToInsert.getNsu_desc();
        
        ncAuditService.mng_log_insert(
                (String) session.getAttribute("loginUserId"),
                (String) session.getAttribute("clientIp"),
                audit_page, audit_param, audit_result, "I"); // 감사 로그 저장
    }

	
	/**
	 * 계정 등록 시 ID 유효성 검사	
	 * @param nsu_id
	 * @return
	 */
	@RequestMapping(value = "/id_check.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String id_check(NcUser ncUser) {
		
		
		return "not_present";
	}
	
	/**
	 * 계정 등록 시 Password 유효성 검사
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/pw_check.do", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String pw_check(HttpServletRequest req) {
		String new_pw = null, new_pw_confirm = null;

		PrivateKey privateKey = ncSecurity.getPrivateKey();
		if (privateKey == null) {
			return "rsa_error";
        }
        try {
			new_pw = RSAUtil.decryptRsa(privateKey, req.getParameter("nsu_pw"));
	        new_pw_confirm = RSAUtil.decryptRsa(privateKey, req.getParameter("nsu_pw_confirm"));
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

       String listKeyboardThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh"
       		+ "|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm|ewq|ewq|tre|ytr|uyt|iuy"
       		+ "|oiu|poi|dsa|fds|gfd|hgf|jhg|kjh|lkj|cxz|vcx|bvc|nbv|mnb";
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

       if (new_pw.indexOf(req.getParameter("nsu_id")) != -1) {
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
	 * 관리자 등록 설정 보안등급 체크
	 * @param nsu_secu_level
	 * @return
	 */
	@RequestMapping(value = "/regit_seculevel_check.do", method = RequestMethod.POST)
	@ResponseBody
	public String regit_seculevel_check(@RequestParam("nsu_secu_level") int nsu_secu_level) {
		ArrayList<NcUser> getNsuSecuLevel = ncUserService.getNsuSecuLevel();
		
		int admin = 0;
		int monitor = 0;
		
		// 관리자 및 모니터 계정 전체 개수
		for(int i=0; i<getNsuSecuLevel.size(); i++){
			if(getNsuSecuLevel.get(i).getNsu_secu_level() == 1) 
				admin++;
			else
				monitor++;			
		}
		if(nsu_secu_level == 1){
			if(admin > 1)
				return "admin_over";			
			else
				return "fine";			
		}else{
			if(monitor > 1)
				return "monitor_over";
			else
				return "fine";					
		}
	}
	
	/**
	 * Mypage 보안등급 설정
	 * @param nsu_name
	 * @param nsu_secu_level
	 * @return
	 */
	@RequestMapping(value = "/edit_seculevel_check.do", method = RequestMethod.POST)
	@ResponseBody
	public String edit_seculevel(@RequestParam("nsu_name") String nsu_name, @RequestParam("nsu_secu_level") int nsu_secu_level) {
		ArrayList<NcUser> getNsuSecuLevel = ncUserService.getNsuSecuLevel();
				
		int admin = 0;
		int monitor = 0;
		int id_property=0;
		String result = "";
		
		for(int i=0; i<getNsuSecuLevel.size(); i++){
			// 관리자 및 모니터 계정 전체 개수
			if(getNsuSecuLevel.get(i).getNsu_secu_level() == 1) 
				admin++;
			else 
				monitor++;
			// 현재 사용자의 보안등급 확인
			if(getNsuSecuLevel.get(i).getNsu_name().equals(nsu_name) && getNsuSecuLevel.get(i).getNsu_secu_level() == 1) 
				id_property=1;
			if(getNsuSecuLevel.get(i).getNsu_name().equals(nsu_name) && getNsuSecuLevel.get(i).getNsu_secu_level() == 2) 
				id_property=2;			
		}
	
		if(nsu_secu_level == 1){
			if(admin > 1){ // 관리자 계정 2개 이상 사용 금지
				if(id_property == 1) // 2개 중 현재 사용자 포함일 경우 통과				
					result = "fine";
				else
					result = "admin_over";				
			}else{ 
				return "fine";
			}
		}else{
			if(monitor > 1){ // 모니터 계정 2개 이상 사용 금지
				if(id_property == 2) // 2개중 현재 사용자 포함일 경우 통과
					result =  "fine";
				else
					result = "monitor_over";				
			}else{ 
				return "fine";
			}
		}
		return result;
	}
	
	/**
	 * 관리자 접속 IP 정보 페이지
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/access_ip_set.do", method = RequestMethod.GET)
	public String access_ip_set(Model model) {
		ArrayList<NcUser> getAccessIp = ncUserService.getAccessIp();
		model.addAttribute("getAccessIp", getAccessIp);
		
		// 메뉴 만들기 시작
		model.addAttribute("top_menu", ncMenuService.getTopMenu());
		model.addAttribute("left_menu", ncMenuService.getLeftMenu());
		model.addAttribute("tab_menu", ncMenuService.getTabMenu());
		// 메뉴 만들기 끝
		return "access_ip/access_ip_set";
	}

	/**
	 * 관리자 접속 IP 추가
	 * @param nai_ip
	 * @param nai_name
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/insert_access_ip.do", method = RequestMethod.POST)
	@ResponseBody
	public String insert_access_ip(@RequestParam("nai_ip") String nai_ip, @RequestParam("nai_name") String nai_name, HttpSession session) {
		ArrayList<NcUser> getAccessIp = ncUserService.getAccessIp();
		
		Pattern p = Pattern.compile("^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$");
		Matcher m = p.matcher(nai_ip);
		if (!m.find()) {
			return "접속장비 IP가 IP주소 형식이 아닙니다.";
		}
		
		if(getAccessIp.size()>1) return "over";
		
		NcUser accessIp = ncUserService.getByAccessIp(nai_ip);
		if(accessIp!=null) { // 중복된 ip가 있는지 확인
			return "dupli"; 
		}
		
		int result = ncUserService.insert_access_ip(nai_ip, nai_name);
		String audit_param = "접속장비IP = "+nai_ip+",접속장비명 = "+nai_name;
		if (result == 1) {
			//if(nai_div==2) ncTransferService.trans_access_ip("I", nai_ip, nai_name, nai_div, 0, ""); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 등록",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 등록",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}

	/**
	 * 관리자 접속 IP 사용 여부 변경
	 * @param nai_ip
	 * @param use_yn
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/edit_access_ip.do", method = RequestMethod.POST)
	@ResponseBody
	public String edit_access_ip(@RequestParam("nai_ip") String nai_ip, @RequestParam("use_yn") int use_yn,  @RequestParam("nai_div") int nai_div, HttpSession session) {
		int cnt = ncUserService.select_notuse_cnt(nai_ip);
		if(cnt<1) return "under";
		
		String audit_page = "";
		if(use_yn==1){
			use_yn = 0;
			audit_page = "관리자 접속 IP를 미사용 상태로 변경";			
		}else{
			use_yn = 1;
			audit_page = "관리자 접속 IP를 사용 상태로 변경";
		}
		Pattern p = Pattern.compile("^((0|1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-5]|[3-9][0-9]?)\\.){3}(1[0-9]{0,2}|2[0-9]?|2[0-4][0-9]|25[0-4]|[3-9][0-9]?)$");
		Matcher m = p.matcher(nai_ip);
		if (!m.find()) {
			return "접속장비 IP가 IP주소 형식이 아닙니다.";
		}
		int result = ncUserService.edit_access_ip(nai_ip, use_yn);
		
		String audit_param = "접속장비IP = "+nai_ip;
		if (result == 1) {
			//if(nai_div==2) ncTransferService.trans_access_ip("D", nai_ip, "", nai_div, use_yn, ""); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),audit_page,audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 접속 장비 수정 호출
	 * @param nai_ip
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ipinfo_editVw.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String ipinfo_editVw(@RequestParam("nai_ip") String nai_ip, Model model) {
		NcUser accessIp = ncUserService.getByAccessIp(nai_ip);
		
		String result = "<input type='hidden' name='org_nai_ip' value='"+accessIp.getNai_ip()+"' /><ul>"+
				"<li>"+
					"<label>"+MessageUtil.getMessage("accessip.connip")+"</label>"+
					"<input type='text' name='nai_ip' id='nai_ip' value='"+accessIp.getNai_ip()+"' maxlength='15' />"+
				"</li>"+
				"<li>"+
					"<label>"+MessageUtil.getMessage("accessip.connname")+"</label>"+
					"<input type='text' name='nai_name' id='nai_name' value='"+accessIp.getNai_name()+"' />"+
				"</li>"+
			"</ul>";
		return result;
	}
	
	/**
	 * 관리자 접속 IP 수정
	 * @param nai_ip
	 * @param nai_name
	 * @param org_nai_ip
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/update_ip_info.do", method = RequestMethod.POST)
	@ResponseBody
	public String update_ip_info(@RequestParam("nai_ip") String nai_ip, @RequestParam("nai_name") String nai_name, @RequestParam("org_nai_ip") String org_nai_ip, HttpSession session) {
		if(!nai_ip.equals(org_nai_ip)) {
			NcUser accessIp = ncUserService.getByAccessIp(nai_ip);
			if(accessIp!=null) { // 중복된 ip가 있는지 확인
				return "dupli"; 
			}
		}
		
		int result = ncUserService.edit_ip_info(nai_ip, nai_name, org_nai_ip);
		String audit_param = "접속장비IP = "+nai_ip+",접속장비명 = "+nai_name;
		if (result == 1) {
			//if(nai_div==2) ncTransferService.trans_access_ip("U", nai_ip, nai_name, nai_div, 0, org_nai_ip); // 수신 DB에서 실행할 쿼리파일 만들기
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 수정",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 수정",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	
	/**
	 * 전송 통제 정책 삭제
	 * @param nps_seq
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/delete_access_ip.do", produces="text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete_access_ip(@RequestParam("nai_ip") String nai_ip, HttpSession session) {
		int cnt = ncUserService.select_notuse_cnt(nai_ip);
		if(cnt<1) return "under";
		
		NcUser accessIp = ncUserService.getByAccessIp(nai_ip);
		
		int result = ncUserService.delete_access_ip(nai_ip);
		String audit_param = "접속장비IP = "+nai_ip+",접속장비명 = "+accessIp.getNai_name();
		if (result == 1) {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 삭제",audit_param,"S","I"); // 감사로그 저장
			return "true";
		} else {
			ncAuditService.mng_log_insert((String)session.getAttribute("loginUserId"),(String)session.getAttribute("clientIp"),"관리자 접속 IP 삭제",audit_param,"F","I"); // 감사로그 저장
			return "DB Error";
		}
	}
	
	/**
	 * 관리자 계정 사용여부 변경
	 * @param nsu_seq
	 * @param nsu_use_yn
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/user_useyn.do", method = RequestMethod.POST)
	@ResponseBody
	public String user_useyn(@RequestParam("nsu_seq") int nsu_seq, @RequestParam("nsu_use_yn") int nsu_use_yn, HttpSession session) {
		NcUser authUser = NcSecurityUtil.getAuthenticatedNtUser();
		NcUser ndUserData = ncUserService.getUserbySeq(nsu_seq);

		String content = "";
		int result = 0;
		if (Objects.nonNull(authUser) && authUser.getNsu_seq() != nsu_seq) {
			result = ncUserService.updateUserUseYN(nsu_seq, nsu_use_yn);
		}

		if(nsu_use_yn == 1) {
		content = MessageUtil.getMessage("log.user.changeuseyny");
		}else {
		content = MessageUtil.getMessage("log.user.changeuseynn");
		}

		String audit_page = MessageUtil.getMessage("log.user.changeuseyn");
		String audit_param = MessageUtil.getMessage("user.adminid") +
			" = " + ndUserData.getNsu_id()+", " + content;
		String audit_result = result == 1 ? "S" : "F";

		ncAuditService.mng_log_insert(
			(String)session.getAttribute("loginUserId"),
			(String)session.getAttribute("clientIp"),
			audit_page, audit_param, audit_result, "I"); // 감사로그 저장

		return result == 1 ? "true" : "DB Error";
	}
}