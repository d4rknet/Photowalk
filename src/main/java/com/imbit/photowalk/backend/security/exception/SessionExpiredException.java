package com.imbit.photowalk.backend.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends AuthenticationException {
	public SessionExpiredException() {
		super("The session is expired");
	}
}
