package com.imbit.photowalk.backend.security;


import com.imbit.photowalk.backend.security.exception.NotAuthenticatedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodSecurityAdvice {

	@Autowired
	private AuthenticationService authenticationProvider;

	@Pointcut("within(com.imbit.photowalk.backend.controller..*)")
	public void controller(){}


	@Before("controller() && @annotation(com.imbit.photowalk.backend.security.Authenticated)")
	public void authorizationCheck(JoinPoint point) throws Throwable {
		if (authenticationProvider.getCurrentUser() == null){
			throw new NotAuthenticatedException();
		}
	}

//	@Around("args()")
//	public Object injectCurrentUser(ProceedingJoinPoint joinPoint){
//
//	}

}
