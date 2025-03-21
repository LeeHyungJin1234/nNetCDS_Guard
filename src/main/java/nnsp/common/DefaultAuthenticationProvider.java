package nnsp.common;

import java.security.PrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nnsp.security.Constants;
import nnsp.security.NcSecurityUtil;
import nnsp.security.RSAUtil;
import nnsp.services.NcAlarmService;
import nnsp.services.NcAuditService;
import nnsp.services.NcConfigService;
import nnsp.services.NcSecurity;
import nnsp.services.NcUserService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcConfig;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DefaultAuthenticationProvider implements AuthenticationProvider {  
 
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NcUserService ncUserService;
	@Autowired
	private NcAuditService ncAuditService;
	@Autowired
	private NcConfigService ncConfigService;
	@Autowired
	private NcAlarmService ncAlarmService;
	@Autowired
	NcSecurity ncSecurity;
	
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
    	String securedUsername = String.valueOf(auth.getPrincipal());
    	String securedPassword = String.valueOf(auth.getCredentials());
    	
    	HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    	
    	HttpSession session = req.getSession();
    	
    	PrivateKey privateKey = ncSecurity.getPrivateKey();
    	if (privateKey == null) {
            throw new AuthenticationServiceException("NOT_FIND_PRIVATEKEY");
        }
    	String username="", password="";
		try {
			username = RSAUtil.decryptRsa(privateKey, securedUsername);
			password = RSAUtil.decryptRsa(privateKey, securedPassword);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new AuthenticationServiceException("NOT_FIND_PRIVATEKEY");
		}
		session.setAttribute("loginUserId", username);
		    	
    	// 송신 서버에서는 관리자 권한인 계정만, 수신 서버에서는 모니터링 권한인 계정만 로그인 함
    	/*if(ndUser.getNsu_secu_level() != Config.getInstance().getDivCode())
    		throw new UsernameNotFoundException(username);*/
	  
    	// 접속 ip 확인 시작
		String client_ip = NcSecurityUtil.getRequestIP(req);
            
		boolean isAuthenticatedByIP = false;
		ArrayList<NcUser> access_ip = ncUserService.getUseAccessIp();
		for (int index = 0; index < access_ip.size(); index++) {
			if (client_ip.equals(access_ip.get(index).getNai_ip())) {
				isAuthenticatedByIP = true;
				break;
			}
		}

        if(!isAuthenticatedByIP) {
        	ncAuditService.mng_log_insert(username,client_ip,
        	        MessageUtil.getMessage("log.login"),
        	        MessageUtil.getMessage("log.notallowedip"),
        	        "F","W"); // 감사로그 저장
        	throw new AuthenticationServiceException("NOT_ACCESSIBLE_IP");
        }
        session.setAttribute("clientIp", client_ip); // 최초 id/pw 변경할때 쓰려고 담음
        // 접속 ip 확인 끝
    	
        
        NcUser ncUser = ncUserService.getUserbyId(username);
        if(ncUser==null) {
    		throw new UsernameNotFoundException(username); // login_error home에서 log 기록..
    	}
        
        ncUser.setNai_ip(client_ip);
        
        // 계정 사용 여부 확인
        if (ncUser.getNsu_use_yn() == 0) {
          ncAuditService.mng_log_insert(ncUser.getNsu_id(), client_ip,
            MessageUtil.getMessage("log.login"),
            MessageUtil.getMessage("log.unusedid"),
            "F", "W"); // 사용하지 않음으로 설정된 ID로 로그인 했을 때 감사기록 저장
          throw new AuthenticationServiceException("UNUSED_ACCOUNT");
        }
        
        // 계정 잠김 체크
    	if (ncUser.getNsu_lock_yn()==1) {
    		try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date1 = sdf.parse(sdf.format(new Date(System.currentTimeMillis()))); // 현재 시간
	        	Date date2 = sdf.parse(ncUser.getNsu_lock_date()); // 잠겨있어야 할 시간
	        
	        	Calendar cal1 = Calendar.getInstance();
	        	Calendar cal2 = Calendar.getInstance();
	        	cal1.setTime(date1);
	        	cal2.setTime(date2);
	        	
	        	if(cal1.before(cal2)){
	        		ncAuditService.mng_log_insert(ncUser.getNsu_id(),client_ip,
    	            MessageUtil.getMessage("log.login"),
    	            MessageUtil.getMessage("log.loginfaillockedaccount"),
	        		"F","W");
	        		
	        		throw new LockedException("lock_attempt");
	        	}
			}catch(ParseException ex){
	    		ex.printStackTrace();
	    	}
    	}

        // 클라이언트(브라우저) 메모리 평문 노출 방지 위해 변형된 값을 복구
        String pw = NcSecurityUtil.getRotatedString(password, Constants.ROTATE);
        
        // 패스워드 확인
        String encodedPassword = nnsp.security.NcSecurityUtil.getSHA256Encrypt(pw, ncUser.getNsu_salt());
        
        try {
        	password.close();
        	pw.close();
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
        
        if (!ncUser.getNsu_pw().equals(encodedPassword)) {
            NcConfig ntConfig = ncConfigService.getConfigLogIn();

            if (ncUser.getNsu_login_failcnt() >= ntConfig.getNcli_lock_failcnt()-1 &&
              0 == ncUser.getNsu_lock_yn()) {
              ncAuditService.mng_log_insert(ncUser.getNsu_id(),
                client_ip, MessageUtil.getMessage("log.login"),
                MessageUtil.getMessage("log.lockmessage"),"L","W"); // 감사로그 저장

              ncUserService.lock_status_update("1", ntConfig.getNcli_lock_date(),
                (ncUser.getNsu_login_failcnt()+1), ncUser.getNsu_seq()); // 설정된 시간 동안 계정 잠금
              ncUser.setNsu_lock_yn(1);

              String Detail = ncUser.getNsu_id() + " 연속로그인 실패로 인한 계정잠김.";
              ncAlarmService.login_alarm_insert("R" , Detail );
              // 관리자 통보 메일 큐에 저장
              ncAlarmService.insertEmailNotification(ncUser.getNsu_id(), Detail);

              throw new LockedException("id_lock");
            }
            
            throw new BadCredentialsException("Bad Credentials");
        }
    	
    	List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    	if(ncUser.getNsu_secu_level() == 1){ // 관리자
    		roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else if(ncUser.getNsu_secu_level() == 2){ // 모니터링
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
    	UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken("ROLE_ADMIN", null, roles); // admin 권한 중 1명만 로그인 하기위해
        result.setDetails(new NcUserDetails(ncUser));
        return result;
    }
    
    /**
     *  문자열 xor 암복호화
     * @param code
     * @return
     */
    public String crypto(String code){
    	byte keyChar[] = "656D2B6A209ACE857601D1042D8A1401413172549F906BB9B2675CF727A397968B255FAF2461C2376B120DAEC765ACEC53C35559B934FEB6CAFD7D759A7CE296".getBytes();
	  
    	byte codeChar[] = new byte[code.getBytes().length]; //code의 문자열 길이만큼의 배열을 만든다.
    	codeChar = code.getBytes(); //code를 Byte형으로 변환한다.
	   
    	for(int i=0, j=0; i< code.getBytes().length; i++){
    		codeChar[i] = (byte) (codeChar[i] ^ keyChar[j]); //code의 한문자와 key의 한문자를 ^(XOR)연산을 한후 byte형으로 변환한다.
    		j = (++j < keyChar.length ? j : 0); //j의 값이 key문자열의 길이보다 커질경우 0으로 아닐경우는 j의 값을 갖는다.
    	}
	 
    	return new String(codeChar) ; //byte배열인 code를 String으로 변환하여 반환한다.
    }
    
    /**
     * rsa 복호화
     * @param privateKey
     * @param securedValue
     * @return
     */
    public String decryptRsa(PrivateKey privateKey, String securedValue) {
    	String decryptedValue = "";
    	try{
    		Cipher cipher = Cipher.getInstance("RSA");
    		/**
	   		* 암호화 된 값은 byte 배열이다.
	   		* 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다. 
	   		* 서버측에서도 값을 받을 때 hex 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
	   		*/
    		byte[] encryptedBytes = hexToByteArray(securedValue);
    		cipher.init(Cipher.DECRYPT_MODE, privateKey);
    		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    		decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
    	}catch(Exception e){
    		logger.info("decryptRsa Exception Error : "+e.getMessage());
    	}
    	return decryptedValue;
    } 
    
    /**
     * 16진 문자열을 byte 배열로 변환
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
    	if (hex == null || hex.length() % 2 != 0) {
    		return new byte[]{};
    	}
    
    	byte[] bytes = new byte[hex.length() / 2];
    	for (int i = 0; i < hex.length(); i += 2) {
    		byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
    		bytes[(int) Math.floor(i / 2)] = value;
    	}
    	return bytes;
    }
}