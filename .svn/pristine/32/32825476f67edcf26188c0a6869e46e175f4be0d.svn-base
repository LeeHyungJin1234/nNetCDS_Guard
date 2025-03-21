package nnsp.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import nnsp.services.NcAuditService;

@Component
public class DefaultLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	NcAuditService ncAuditService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
		
		request.getSession().invalidate();
		request.getSession(true);
		response.sendRedirect( request.getContextPath() + "/login.do");
	}

}