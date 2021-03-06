package com.imbit.photowalk.backend.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.UserDto;
import com.imbit.photowalk.backend.controller.rest.View.UserDetailed;
import com.imbit.photowalk.backend.security.Authenticated;
import com.imbit.photowalk.backend.security.HashingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;
	private final HashingProvider hashingProvider;

	@Autowired
	public UserController(UserRepository userRepository, HashingProvider hashingProvider) {
		this.userRepository = userRepository;
		this.hashingProvider = hashingProvider;
	}


	@PostMapping
	public ResponseEntity addUser(@RequestBody UserDto userDto){
		User user = new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setUsername(userDto.getUsername());
		user.setEmailaddress(userDto.getEmailaddress());
		user.setPassword(hashingProvider.hashPassword(userDto.getPassword()));
		userRepository.save(user);
		return ResponseEntity.created(URI.create("/api/user/"+userDto.getUsername())).build();
	}

	@Authenticated
	@GetMapping
	public List<UserDto> getUsers(){
		return userRepository.findAll().stream().map(user -> UserDto.builder().username(user.getUsername()).build())
				.collect(toList());
	}

	@Authenticated
	@JsonView(UserDetailed.class)
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username){
		Optional<User> user = userRepository.findUserByUsername(username);
		if (!user.isPresent()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.ok(user.get());
		}
	}
}
