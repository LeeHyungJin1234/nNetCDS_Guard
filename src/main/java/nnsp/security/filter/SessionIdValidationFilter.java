package nnsp.security.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionIdValidationFilter extends OncePerRequestFilter {

  @Qualifier("sessionRegistry")
  @Autowired
  SessionRegistry sessionRegistry;

  @SuppressWarnings("unused")
private final Log Logger = LogFactory.getLog(getClass());

  /**
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
/*
    String path = request.getRequestURI();
    if ("/login_duplicate.do".equals(path) || "/login.do".equals(path) ||
      "/logout.do".equals(path) || "/session_check.do".equals(path) ||
        "/main.do".equals(path)) {
      filterChain.doFilter(request, response);
      return ;
    }

    boolean isDebugMode = Config.isDebugMode;

    // 세션 ID 유효성 검증
    if (!request.isRequestedSessionIdValid()) {
      ndAuditService.mng_log_insert("SYSTEM", request.getRemoteAddr(),
        "비인가 요청",
        "재사용이 금지된 인증 정보의 재사용 시도" + (isDebugMode ? ", Session ID," +
                " Current: " + request.getSession().getId() +
                " Requested: " + request.getRequestedSessionId() +
                " URI: " + path : ""),
        "F", "W");

      HttpSession session = request.getSession();
      session.setAttribute("InvalidSession", "ID");

      response.sendRedirect("/login.do");

      return ;
    }
*/
    filterChain.doFilter(request, response);
  }

  public void changeSessionId(HttpServletRequest request) {
    SecurityContext ctx = SecurityContextHolder.getContext();
    if (Objects.nonNull(ctx)) {
      Authentication auth = ctx.getAuthentication();
      if (Objects.nonNull(auth)) {
        HttpSession session = request.getSession();

        String sessionId = session.getId();

        String newSessionId = request.changeSessionId();

        sessionRegistry.removeSessionInformation(sessionId);
        sessionRegistry.registerNewSession(newSessionId, auth.getPrincipal());
      }
    }
  }
}
