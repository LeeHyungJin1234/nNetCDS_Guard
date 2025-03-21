package nnsp.common;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class RequestReplaceFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, FilterChain filterchain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		filterchain.doFilter(new RequestReplace((HttpServletRequest) httpservletrequest), httpservletresponse);
	}
}