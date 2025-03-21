package nnsp.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import org.springframework.security.authentication.LockedException;

@Component
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		saveException(request, exception);
		
		RedirectStrategy redirectStrategy = getRedirectStrategy();
		
		if(exception instanceof LockedException){ // 계정잠김
			redirectStrategy.sendRedirect(request, response,
				"/login.do?message=" + exception.getMessage());
		}else{
			if("NOT_ACCESSIBLE_IP".equals(exception.getMessage())){ // 접속 허용 아이피 아님
				redirectStrategy.sendRedirect(request, response, "/login.do?message=notaccess_ip");
			}else if("NOT_FIND_PRIVATEKEY".equals(exception.getMessage())){ // 로그인 암/복호화 실패
				redirectStrategy.sendRedirect(request, response, "/login.do?message=notfind_key");
			}else if("UNUSED_ACCOUNT".equals(exception.getMessage())){ // 사용하지 않음으로 설정된 ID
				redirectStrategy.sendRedirect(request, response, "/login.do?message=login_error");
			}else{
				redirectStrategy.sendRedirect(request, response, "/login.do?message=login_error");
			}
		}
		
	}
}