package com.imbit.photowalk.backend.security.exception;


public class BadCredentialsException extends AuthenticationException {
	public BadCredentialsException() {
		super("Username or password does match");
	}
}
