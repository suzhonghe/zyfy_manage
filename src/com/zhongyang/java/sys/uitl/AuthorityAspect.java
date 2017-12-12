package com.zhongyang.java.sys.uitl;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;

public class AuthorityAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub

		FireAuthority fireAuthority = AnnotationUtils.findAnnotation(
				invocation.getMethod(), FireAuthority.class);

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();
		
		System.out.println(session.getId());
		Employee emp = (Employee) session.getAttribute("zycfLoginEmp");
		if (emp == null || emp.getId() == null) {
//			response.sendRedirect(request.getContextPath()+"/login.html");
//			return null;
		}
		
		if (fireAuthority != null) {
			String priviliges = (String)session.getAttribute("emp_auths");
			
			for(Authorities au: fireAuthority.authorities()){
				
				if(AuthorityHelper.checkAuthority(au.getIndex(), priviliges))
					return invocation.proceed();
				else{
					
					throw new UException(SystemEnum.LOGIN_ERROR, "用户未授权");

				}
			}
			
			return invocation.proceed();
		}
		return invocation.proceed();
	}

}
