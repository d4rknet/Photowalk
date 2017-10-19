package com.imbit.photowalk.backend.security.exception;

public class SessionExpiredException extends AuthenticationException {
	public SessionExpiredException() {
		super("The session is expired");
	}
}
