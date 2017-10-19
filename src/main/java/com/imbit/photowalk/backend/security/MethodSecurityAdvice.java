package com.imbit.photowalk.backend.security;


import com.imbit.photowalk.backend.security.exception.AuthenticationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodSecurityAdvice {

	@Autowired
	private AuthenticationService authenticationProvider;

	@Before("@annotation(com.imbit.photowalk.backend.security.Authenticated)")
	public void authorizationCheck(JoinPoint point) throws Throwable {
		if (authenticationProvider.getCurrentUser() == null){
			throw new AuthenticationException("No session found");
		}
	}
}
