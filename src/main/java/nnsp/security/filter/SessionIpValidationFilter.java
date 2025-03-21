package nnsp.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionIpValidationFilter extends OncePerRequestFilter {

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
    // authentication / IP 유효성 검증
    SecurityContext ctx = SecurityContextHolder.getContext();
    if (Objects.nonNull(ctx)) {
      Authentication auth = ctx.getAuthentication();
      if (Objects.nonNull(auth)) {
        Object details = auth.getDetails();

        if (Objects.nonNull(details) && details instanceof NdUserDetails) {
          NdUserDetails ndUserDetails = (NdUserDetails) details;

          String sessionAddr = ndUserDetails.getRemoteAddr();
          String requestAddr = request.getRemoteAddr();

          if (!sessionAddr.equals(requestAddr)) {
            ndAuditService.mng_log_insert("SYSTEM", requestAddr,
              "비인가 요청",
              "재사용이 금지된 인증 정보의 재사용 시도",
              "F", "W");

            HttpSession session = request.getSession();
            session.setAttribute("InvalidSession", "IP");

            response.sendRedirect("/login.do");

            return;
          }
        }
      } else {
        String path = request.getRequestURI();
        if (!path.equals("/login.do") && !path.equals("/main.do") && !path.equals("/session_check.do")) {
          ndAuditService.mng_log_insert("SYSTEM", request.getRemoteAddr(),
            "비인가 요청",
            "재사용이 금지된 인증 정보의 재사용 시도",
            "F", "W");

          HttpSession session = request.getSession();
          session.setAttribute("InvalidSession", "UNAUTH");

          response.sendRedirect("/login.do");

          return;
        }
      }
    }
*/
    filterChain.doFilter(request, response);
  }
}
