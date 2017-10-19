package com.imbit.photowalk.backend.rest;

import com.imbit.photowalk.backend.dto.CredentialDto;
import com.imbit.photowalk.backend.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sessions")
public class SessionController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	public HttpEntity<String> createSession(@RequestBody CredentialDto credentials){
		return ResponseEntity
				.ok(authenticationService.login(credentials.getUsername(), credentials.getPassword()));
	}

	@DeleteMapping("/{session}")
	public void deleteSession(@PathVariable String session){
		authenticationService.logout(session);
	}


}
