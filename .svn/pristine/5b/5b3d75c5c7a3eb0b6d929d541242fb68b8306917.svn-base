package com.talentPool.common.filters;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportsResponseHeaderFilter implements javax.servlet.Filter {
	
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = filterConfig;
	}


	public void doFilter(final ServletRequest servletRequest,
			final ServletResponse servletResponse, FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// System.out.println("Entering Filter")
		response.setHeader("Cache-Control","max-age=0, no-cache, no-store, must-revalidate"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		response.addHeader("X-Frame-Options", "SAMEORIGIN");
		response.addHeader("X-Content-Type-Options", "nosniff");
		response.addHeader("X-XSS-Protection", "1; mode=block");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

