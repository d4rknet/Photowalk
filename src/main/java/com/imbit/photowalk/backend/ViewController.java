package com.imbit.photowalk.backend;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.Role;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.RoleRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.PWCreateDto;
import com.imbit.photowalk.backend.dto.PhotowalkDto;
import com.imbit.photowalk.backend.dto.RegisterDto;
import com.imbit.photowalk.backend.security.HashingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ViewController {

	private final UserRepository userRepository;
	private final PhotowalkRepository photowalkRepository;
	private final RoleRepository roleRepository;
	private final HashingProvider hashingProvider;


	@Autowired
	public ViewController(UserRepository userRepository,
						  PhotowalkRepository photowalkRepository,
						  RoleRepository roleRepository,
						  HashingProvider hashingProvider) {
		this.userRepository = userRepository;
		this.photowalkRepository = photowalkRepository;
		this.roleRepository = roleRepository;
		this.hashingProvider = hashingProvider;
	}

	@RequestMapping("/register")
	public String reg(Model model) {
		model.addAttribute("dto", new RegisterDto());
		return "register";
	}

	@PostMapping("/register")
	public String reg(@ModelAttribute RegisterDto dto, Model model) {
		User user = new User();
		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setEmailaddress(dto.getEmailaddress());
		user.setUsername(dto.getUsername());
		user.setPassword(hashingProvider.hashPassword(dto.getPassword()));
		userRepository.save(user);

		Role role = Role.builder()
				.name("ADMIN")
				.username(user.getUsername())
				.build();
		roleRepository.save(role);
		return "success";
	}

//	@RequestMapping("/")
//	public String index(Model model, Authentication authentication) {
//		model.addAttribute("username", authentication == null ? null : authentication.getName());
//		return "index";
//	}
	@RequestMapping("/")
	public String index(Model model, String username) {
		model.addAttribute("username", username);
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = POST)
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		Optional<User> userOpt = userRepository.findUserByUsername(username);
		if (!userOpt.isPresent() || !Objects.equals(userOpt.get().getPassword(), password)) {
			throw new RuntimeException("password or username does not match");
		}
		model.addAttribute("user", userOpt.get());
		return "loggedIn";
	}

	@RequestMapping("/walks")
	public String showPhotowalks(Model model) {
		model.addAttribute("photowalks", photowalkRepository.findAll());
		return "photowalks";
	}

	@RequestMapping("/createwalk")
	public String phot(Model model) {
		model.addAttribute("photowalkDto", new PWCreateDto());
		return "createwalk";
	}

	@PostMapping("/createwalk")
	public String phot(@ModelAttribute PhotowalkDto photowalkDto, Model model) {
		Photowalk photowalk = new Photowalk();
		photowalk.setName(photowalkDto.getName());
		//photowalk.setDatum(photowalkDto.getDatum());
		photowalk.setDescription(photowalkDto.getDescription());
		photowalk.setStartpoint(photowalkDto.getStartpoint());
		photowalk.setEndpoint(photowalkDto.getEndpoint());
		photowalk.setDuration(photowalkDto.getDuration());
		photowalkRepository.save(photowalk);
		return "redirect:/walks";
	}

	@RequestMapping("walks/{id}")
	public String detailWalk(@PathVariable String id, Model model) {
		Optional<Photowalk> photowalkOptional = photowalkRepository.findById(Integer.parseInt(id));
		model.addAttribute("walk", photowalkOptional.get());
		return "detail";
	}

//	@RequestMapping("walks/{id}/join")
//	public String joinWalk(@PathVariable String id) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String username = authentication.getName();
//		Optional<User> userOP = userRepository.findUserByUsername(username);
//		Optional<Photowalk> photowalkOptional = photowalkRepository.findById(Integer.parseInt(id));
//
//
//		//TODO handle no user/walk found
//
//		Photowalk photowalk = photowalkOptional.get();
//		photowalk.getApplicants().add(userOP.get());
//		photowalkRepository.save(photowalk);
//		return "redirect:/walks/" + id;
//	}
}
