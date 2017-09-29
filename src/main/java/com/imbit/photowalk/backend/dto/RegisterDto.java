package com.imbit.photowalk.backend.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class RegisterDto {
	@JsonView
	private String username;
	private String lastname;
	private String emailaddress;
	private String firstname;
	private String password;
}
