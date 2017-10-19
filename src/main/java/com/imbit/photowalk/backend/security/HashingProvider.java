package com.imbit.photowalk.backend.security;

public interface HashingProvider {
	String hashPassword(String password);
	boolean checkPassword(String hashedPassword, String clearPassword);
}
