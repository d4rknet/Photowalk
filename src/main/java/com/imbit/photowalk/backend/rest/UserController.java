package com.imbit.photowalk.backend.rest;

import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/user")
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



	@RequestMapping
	public List<UserDto> getUsers(){
		return userRepository.findAll().stream().map(user -> UserDto.builder().username(user.getUsername()).build())
				.collect(toList());
	}


	@RequestMapping(path = "/{username}")
	public ResponseEntity<UserDto> getUser(@PathVariable String username){
		Optional<User> user = userRepository.findUserByUsername(username);
		if (!user.isPresent()){
			return ResponseEntity.notFound().build();
		}else{
			return ResponseEntity.ok(mapToUserDto(user.get()));
		}
	}


	private UserDto mapToUserDto(User user){
		return UserDto.builder()
				.firstname(user.getFirstname())
				.username(user.getUsername())
				.lastname(user.getLastname())
				.emailaddress(user.getEmailaddress())
				.build();
	}

}
