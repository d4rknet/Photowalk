package com.imbit.photowalk.backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
	private String username;
	private String lastname;
	private String emailaddress;
	private String firstname;
	private String password;
}
