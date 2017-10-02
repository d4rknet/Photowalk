package com.imbit.photowalk.backend.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.UserDto;
import com.imbit.photowalk.backend.rest.View.UserDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity addUser(@RequestBody UserDto userDto){
		User user = new User();
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setUsername(userDto.getUsername());
		user.setEmailaddress(userDto.getEmailaddress());
		user.setPassword(userDto.getPassword());
		userRepository.save(user);
		return ResponseEntity.created(URI.create("/api/user/"+userDto.getUsername())).build();
	}

	@RequestMapping(method = GET)
	public List<UserDto> getUsers(){
		return userRepository.findAll().stream().map(user -> UserDto.builder().username(user.getUsername()).build())
				.collect(toList());
	}

	@JsonView(UserDetailed.class)
	@RequestMapping(path = "/{username}", method = GET)
	public ResponseEntity<User> getUser(@PathVariable String username){
		Optional<User> user = userRepository.findUserByUsername(username);
		if (!user.isPresent()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.ok(user.get());
		}
	}
}
