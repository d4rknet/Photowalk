package com.imbit.photowalk.backend;

import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.dto.CredentialDto;
import com.imbit.photowalk.backend.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Optional;

@Controller
public class ViewController {

	private final UserRepository userRepository;


	@Autowired
	public ViewController(UserRepository userRepository) {
		this.userRepository = userRepository;
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
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		userRepository.save(user);
		return "success";
	}


	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("credentials", new CredentialDto());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("credentials") CredentialDto credential, Model model) {
		Optional<User> user = userRepository.findUserByUsername(credential.getUsername());
		if (!user.isPresent() || !Objects.equals(user.get().getPassword(), credential.getPassword())) {
			throw new RuntimeException("password or username dopes not match");
		}
		model.addAttribute("user", user.get());
		return "loggedIn";
	}


}
