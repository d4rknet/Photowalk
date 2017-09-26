package com.imbit.photowalk.backend;

import com.imbit.photowalk.backend.domain.entity.Photowalk;
import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.PhotowalkRepository;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.RegisterDto;
import com.imbit.photowalk.backend.dto.PWCreateDto;
import com.imbit.photowalk.backend.dto.PhotowalkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ViewController {

	private final UserRepository userRepository;
	private final PhotowalkRepository photowalkRepository;


	@Autowired
	public ViewController(UserRepository userRepository, PhotowalkRepository photowalkRepository) {
		this.userRepository = userRepository;
		this.photowalkRepository = photowalkRepository;
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
		user.setPassword(dto.getPassword());
		userRepository.save(user);
		return "success";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = POST)
	public String login(@RequestParam String username,@RequestParam String password, Model model) {
		Optional<User> userOpt = userRepository.findUserByUsername(username);
		if (!userOpt.isPresent() || !Objects.equals(userOpt.get().getPassword(), password)) {
			throw new RuntimeException("password or username does not match");
		}
		model.addAttribute("user", userOpt.get());
		return "loggedIn";
	}

	@RequestMapping("/walks")
	public String showPhotowalks(Model model){
		model.addAttribute("users",photowalkRepository.findAll());
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
		photowalk.setDate(photowalkDto.getDate());
		photowalk.setDescription(photowalkDto.getDescription());
		photowalk.setStartpoint(photowalkDto.getStartpoint());
		photowalk.setEndpoint(photowalkDto.getEndpoint());
		photowalk.setDuration(photowalkDto.getDuration());
		photowalkRepository.save(photowalk);
		return "success2";
	}


}
