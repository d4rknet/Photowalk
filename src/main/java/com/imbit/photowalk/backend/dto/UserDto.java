package com.imbit.photowalk.backend.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

	private String username;
	private String firstname;


}
