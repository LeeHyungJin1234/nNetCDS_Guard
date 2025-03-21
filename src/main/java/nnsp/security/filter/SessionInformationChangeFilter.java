package nnsp.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionInformationChangeFilter extends OncePerRequestFilter {

  @Qualifier("sessionIdValidationFilter")
  @Autowired
  SessionIdValidationFilter sessionIdFilter;

  @Qualifier("customCsrfFilter")
  @Autowired
  CustomCsrfFilter customCsrfFilter;

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

    // Ajax 요청 및 동일 페이지에 대한 재요청이 아닌 경우 변경
    if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With")) &&
      /* !request.getRequestURL().toString().equals(request.getHeader("referer")) && */
      !request.getRequestURI().equals("/NdLogCsv.do") &&
      !request.getRequestURI().equals("/NdIcsCsv.do") &&
      !request.getRequestURI().equals("/NdIcsCsv_r.do")) {
      sessionIdFilter.changeSessionId(request);
      customCsrfFilter.changeCsrfToken(request, response);
    }

    filterChain.doFilter(request, response);
  }
}
