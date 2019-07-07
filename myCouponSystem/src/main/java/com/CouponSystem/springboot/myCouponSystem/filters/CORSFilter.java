package com.CouponSystem.springboot.myCouponSystem.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

	public CORSFilter() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		// this fiilter only handles OPTIONS requests, and so if not OPTIONS,
		// doFilter(move along)
		if (!request.getMethod().equals("OPTIONS")) {
			// pass the request along the filter chain
			chain.doFilter(request, servletResponse);
			return;
		}

		if (request.getRequestURI().endsWith("/customers") && request.getMethod().equals("OPTIONS")) {
			// pass the request along the filter chain
			((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
			((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
//	        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
			((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
					"GET, OPTIONS, HEAD, PUT, POST, DELETE");
			((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers",
					"Origin, Accept, x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

			HttpServletResponse resp = (HttpServletResponse) servletResponse;
			resp.setStatus(200);
			return;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

}
