package com.imbit.photowalk.backend.controller.view;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.Role;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.RoleRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.PWCreateDto;
import com.imbit.photowalk.backend.dto.PhotowalkDto;
import com.imbit.photowalk.backend.dto.RegisterDto;
import com.imbit.photowalk.backend.security.Authenticated;
import com.imbit.photowalk.backend.security.AuthenticationService;
import com.imbit.photowalk.backend.security.HashingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ViewController {

	private final UserRepository userRepository;
	private final PhotowalkRepository photowalkRepository;
	private final RoleRepository roleRepository;
	private final HashingProvider hashingProvider;
	private final AuthenticationService authenticationService;


	@Autowired
	public ViewController(UserRepository userRepository,
						  PhotowalkRepository photowalkRepository,
						  RoleRepository roleRepository,
						  HashingProvider hashingProvider,
	AuthenticationService authenticationService) {
		this.userRepository = userRepository;
		this.photowalkRepository = photowalkRepository;
		this.roleRepository = roleRepository;
		this.hashingProvider = hashingProvider;
		this.authenticationService = authenticationService;
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

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("username", authenticationService.getCurrentUser()== null ? null : authenticationService.getCurrentUser().getUsername());
		return "index";
	}

	@Authenticated
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

	@Authenticated
	@RequestMapping("walks/{id}/join")
	public String joinWalk(@PathVariable String id) {
		String username = authenticationService.getCurrentUser().getUsername();
		Optional<User> userOP = userRepository.findUserByUsername(username);
		Optional<Photowalk> photowalkOptional = photowalkRepository.findById(Integer.parseInt(id));

		//TODO handle no user/walk found

		Photowalk photowalk = photowalkOptional.get();
		photowalk.getApplicants().add(userOP.get());
		photowalkRepository.save(photowalk);
		return "redirect:/walks/" + id;
	}
}
