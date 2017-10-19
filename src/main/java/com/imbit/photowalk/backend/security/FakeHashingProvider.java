package com.imbit.photowalk.backend.security;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FakeHashingProvider implements HashingProvider {

	@Override
	public String hashPassword(String password) {
		return password.toUpperCase();
	}

	@Override
	public boolean checkPassword(String hashedPassword, String clearPassword) {
		return Objects.equals(hashedPassword, clearPassword.toUpperCase());
	}
}
