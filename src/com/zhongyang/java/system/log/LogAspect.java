package com.zhongyang.java.system.log;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LogAspect implements MethodInterceptor {
	
	Logger	logger=Logger.getLogger(LogAspect.class.getName());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		LogInterface logInterface = AnnotationUtils.findAnnotation(invocation.getMethod(), LogInterface.class);

		if (logInterface != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			logInterface.description();
			Object obj = invocation.proceed();
			logger.info(logInterface.description());
		}
		return invocation.proceed();
	}
}