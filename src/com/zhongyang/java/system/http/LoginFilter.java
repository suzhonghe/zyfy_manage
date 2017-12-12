package com.zhongyang.java.system.http;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.sys.uitl.GlobalConstants;
import com.zhongyang.java.sys.uitl.HTTPUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFilter implements Filter {
	private Pattern pattern = null;
	private String exclude = null;
	private String loginPage = "/login";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		pattern = Pattern.compile(GlobalConstants.EXCLUDE_DIRECTORY_REGEX);
		// exclude=Resources.getString(GlobalConstants.EXCLUDE_URL_INIT);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String contextPath = httpServletRequest.getContextPath();

		
		String url = httpServletRequest.getServletPath();

		Employee emp = (Employee) httpServletRequest.getSession().getAttribute(
				"zycfLoginEmp");
		if (url.endsWith(".html")) {
			if(url.equals("/login.html"))
			{
				chain.doFilter(request, response);
				return;
			}
			if (emp == null && !url.equals("/login.html")) {
				httpServletResponse.sendRedirect("/admin/login.html");
				return;
			} 
		}
		
		 chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
	}

}
