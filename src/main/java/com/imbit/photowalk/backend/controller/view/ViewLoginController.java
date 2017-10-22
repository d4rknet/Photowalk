package com.imbit.photowalk.backend.controller.view;

import com.imbit.photowalk.backend.security.Authenticated;
import com.imbit.photowalk.backend.security.AuthenticationService;
import com.imbit.photowalk.backend.security.exception.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ViewLoginController {

	private final AuthenticationService authenticationService;

	public ViewLoginController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
		String sessionId;
		try {
			sessionId = authenticationService.login(username, password);
		} catch (AuthenticationException e) {
			model.addAttribute("error", e.getMessage());
			return "login";
		}
		response.addCookie(new Cookie("JSESSIONID", sessionId));
		return "redirect:/";
	}

	@Authenticated
	@GetMapping("logout")
	public String logout(@CookieValue("JSESSIONID") String session ){
		authenticationService.logout(session);
		return "redirect:/";
	}
}
