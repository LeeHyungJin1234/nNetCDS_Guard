package nnsp.common;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nnsp.services.NcAuditService;
import nnsp.services.NcUserService;
import nnsp.util.MessageUtil;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private NcUserService ncUserService;
	@Autowired
	private NcAuditService ncAuditService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		setDefaultTargetUrl("/main.do");
		
		NcUser ncUser = ((NcUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getNcUser(); // 현재 로그인 사용자 정보 가져오기
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String client_ip = req.getHeader("X-FORWARDED-FOR"); // client ip 구하기
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getHeader("Proxy-Client-IP");
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getHeader("WL-Proxy-Client-IP");  // 웹로직
        if (client_ip == null || client_ip.length() == 0) client_ip = req.getRemoteAddr();
        
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", ncUser.getNsu_id());
        session.setAttribute("loginUser", ncUser.getNsu_name());
		session.setAttribute("clientIp", client_ip);
		session.setAttribute("CSRF_TOKEN", UUID.randomUUID().toString());
		
		int nsu_seq = ncUser.getNsu_seq();
	    String last_login = ncUser.getNsu_last_login();
	    
	    if (nsu_seq != 1 || (nsu_seq == 1 && last_login != null))
	        ncUserService.last_login_update(ncUser.getNsu_seq()); // 최근 로그인 시간 변경
		
		ncUserService.lock_status_update("0", 0, 0, ncUser.getNsu_seq()); // 로그인 실패 횟수 0으로 변경
	    ncAuditService.mng_log_insert(
	      (String)session.getAttribute("loginUserId"),
	      (String)session.getAttribute("clientIp"),
	      MessageUtil.getMessage("log.login"),
	      null,"S","I"); // 감사로그 저장
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}