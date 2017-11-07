package com.imbit.photowalk.backend.controller.rest;

import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.dto.CredentialDto;
import com.imbit.photowalk.backend.dto.SessionDto;
import com.imbit.photowalk.backend.security.Authenticated;
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
	public HttpEntity<SessionDto> createSession(@RequestBody CredentialDto credentials){
		String token = authenticationService.login(credentials.getUsername(), credentials.getPassword());
		return ResponseEntity
				.ok(new SessionDto(token));
	}

	@DeleteMapping("/{session}")
	public void deleteSession(@PathVariable String session){
		authenticationService.logout(session);
	}

	@Authenticated
	@GetMapping
	public User getUserForSession(){
		return authenticationService.getCurrentUser();
	}

}
