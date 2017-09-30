package com.imbit.photowalk.backend.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String username) {
		super("The user: \""+ username + "\" does not exists");
	}
}
