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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.CouponSystem.springboot.myCouponSystem.beans.CacheData;
import com.CouponSystem.springboot.myCouponSystem.cache.ICacheController;
import com.CouponSystem.springboot.myCouponSystem.consts.Consts;

@Component
public class LoginFilter implements Filter {

	@Autowired
	private ICacheController cache;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String path = req.getRequestURI();

		// These 2 functions, do not require a login
		if (path.endsWith("login")) {
			chain.doFilter(request, response);
			return;
		}

		// In REST, register command's url is combined by /customer (resource name)
		// and method type (POST)
		if (path.endsWith("/customers") && req.getMethod().toString().equals("POST")) {
			chain.doFilter(request, response);
			return;
		}

		String token = req.getParameter("token");
		CacheData cachedData = (CacheData) this.cache.get(token);
		// A valid login request
		if (cachedData != null) {

			// Add the user's cached data to the request
			req.setAttribute(Consts.USER_DATA_KEY, cachedData);

			// We're done, move to the next filter / controller
			chain.doFilter(request, response);
			return;
		}

		res.setStatus(401);
		res.setHeader("ErrorCause", "Couldn't find a login session");
	}

// ---------------------------------------------------------------------------------------------------

	public void init(FilterConfig arg0) throws ServletException {
	}

}
