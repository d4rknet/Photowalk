package com.imbit.photowalk.backend.security;

import com.imbit.photowalk.backend.security.exception.SessionExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class AuthenticationFilter extends HttpFilter {
	private static String[] PUBLIC_PATTERNS = new String[]{"/static/**"};
	private static String TOKEN_PATTERN = "/api/**";

	@Autowired
	private AuthenticationService authenticationProvider;


	private AntPathMatcher pathMatcher;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		pathMatcher = new AntPathMatcher();
	}

	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestPath = httpRequest.getServletPath();

		if (isPublic(requestPath)) {
			System.out.println("Public path");
		} else if (pathMatcher.match(TOKEN_PATTERN, requestPath)) {
			String token = httpRequest.getHeader("token");
			if (token != null) {
				authenticationProvider.setSession(token);
			}
		} else {
			if (request.getCookies() != null) {
				try {

				Stream.of(request.getCookies())
						.filter(cookie -> Objects.equals(cookie.getName(), "JSESSIONID"))
//						.filter(cookie -> !Objects.equals(cookie.getValue(), ""))
						.findAny()
						.ifPresent(cookie -> authenticationProvider.setSession(cookie.getValue()));
				}catch (SessionExpiredException e){
					System.out.println("Session expired");
					response.addCookie(new Cookie("JSESSIONID", ""));
				}
			}
		}
		chain.doFilter(request, response);
		authenticationProvider.removeCurrentUser();
	}

	private boolean isPublic(String path) {
		return Arrays.stream(PUBLIC_PATTERNS).anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	private boolean isPatternMatching(String patterns[], String path) {
		for (String pattern : patterns) {
			if (pathMatcher.match(pattern, path)) {
				return true;
			}
		}
		return false;
	}
}
