package com.imbit.photowalk.backend.security;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class HttpFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			doHttpFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
		}
		//TODO decide what to do in error cases
	}

	public abstract void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException ;

	@Override
	public void destroy() {

	}
}
