package com.zhongyang.java.sys.uitl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhongyang.java.system.Message;



public class AuthorityInteceptor implements HandlerInterceptor {

	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		HandlerMethod mHandler ;
		
		if(handler instanceof HandlerMethod)
			mHandler = (HandlerMethod) handler;
		else
			return true;
		
		FireAuthority fireAuthority = mHandler.getMethodAnnotation( FireAuthority.class);
		
		if(fireAuthority != null){
			System.out.println("++++++++++++++++"+fireAuthority.authorities());
			
			
			HttpSession session = request.getSession();
			
			List<String> empAuth = (List<String>)session.getAttribute("emp_auth");
			if(empAuth == null)
				return false;
			
			for(Authorities au:fireAuthority.authorities()){
				for(String auth:empAuth){
					if(au.getName().equals(auth))
						return true;
				}
				
			}
		}
		return  true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
