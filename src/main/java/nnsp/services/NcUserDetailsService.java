package nnsp.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class NcUserDetailsService implements UserDetailsService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NcUserDetailsService.class);
	
	@Autowired
	private NcUserService ncUserService;
	@Autowired
	private NcAuditService ncAuditService;
	
	public NcUserDetails loadUserByUsername(String id) throws UsernameNotFoundException  {
		NcUser ncUser = ncUserService.getUserbyId(id);
		
		if(ncUser==null) 
			throw new UsernameNotFoundException(id+" not found");
		
		// 접속 ip 확인 시작
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String client_ip = req.getHeader("X-FORWARDED-FOR"); // client ip 구하기
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getHeader("Proxy-Client-IP");
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getHeader("WL-Proxy-Client-IP");  // 웹로직
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getRemoteAddr();
            
        boolean isAuthenticatedByIP  = false;
        ArrayList<NcUser> access_ip =  ncUserService.getUseAccessIp();
        
        for(int index=0; index < access_ip.size(); index++){
        	if(client_ip.equals(access_ip.get(index).getNai_ip())) {
        		isAuthenticatedByIP = true;
        		break;
        	}
		}

        if(!isAuthenticatedByIP) {
        	ncAuditService.mng_log_insert(ncUser.getNsu_id(),client_ip,"로그인",null,"F","W"); // 감사로그 저장
        	throw new AccessDeniedException("NOT_ACCESSIBLE_IP");
        }
        // 접속 ip 확인 끝

        ncUser.setSalt(ncUser.getNsu_reg_date()); // salt 값 넣기
		
		return new NcUserDetails(ncUser);
	}
}
