package com.imbit.photowalk.backend.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthenticatedException extends AuthenticationException {
	public NotAuthenticatedException() {
		super("The call is not authenticated");
	}
}
