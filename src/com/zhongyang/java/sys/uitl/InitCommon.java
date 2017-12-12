package com.zhongyang.java.sys.uitl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitCommon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		ServletContext context = getServletContext();
		// super.init();
		 ApplicationContext ctx =
		 WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	//ApplicationContext ctx = (ApplicationContext) context
	//			.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcherServlet");

		Application.getInstance().runALL(ctx, this.getServletContext());

	}

	/*
	 * key application里面的keymethodupdate 仅仅做修改一个对象reload 重写加载application某key
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");
		String method = request.getParameter("method");
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		if (key != null && key.length() > 0 && method != null) {
			// if("update".equalsIgnoreCase(method)){
			// String obj=request.getParameter("obj");
			// Application.getInstance().update(this.getServletContext(), key,
			// ctx,obj);
			// }else if("reload".equalsIgnoreCase(method)){
			// Application.getInstance().reload(this.getServletContext(), key,
			// ctx);
			// }

		}
	}
}
