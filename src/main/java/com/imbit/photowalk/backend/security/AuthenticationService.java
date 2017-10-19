package com.imbit.photowalk.backend.security;

import com.imbit.photowalk.backend.domain.entity.User;
import com.imbit.photowalk.backend.domain.repo.UserRepository;
import com.imbit.photowalk.backend.security.exception.BadCredentialsException;
import com.imbit.photowalk.backend.security.exception.SessionExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {
	@Value("${sessionExpiryTime}")
	private long sessionExpiryTime;

	private final HashingProvider hashingProvider;

	private final UserRepository userRepository;

	private final Map<String, Session> sessions;

	private ThreadLocal<User> currentUser = new ThreadLocal<>();

	@Autowired
	public AuthenticationService(UserRepository userRepository, HashingProvider hashingProvider) {
		this.userRepository = userRepository;
		this.hashingProvider = hashingProvider;

		sessions = new HashMap<>();
	}

	public String login(String username, String password) {
		User user = userRepository.findUserByUsername(username).orElseThrow(BadCredentialsException::new);
		if (!hashingProvider.checkPassword(user.getPassword(), password)) {
			throw new BadCredentialsException();
		}
		synchronized (sessions) {
			String uuid = UUID.randomUUID().toString();
			Session session = new Session();
			session.username = username;
			session.lastActivity = Instant.now();
			sessions.put(uuid, session);
			return uuid;
		}
	}

	public void logout(String sessionId) {
		sessions.remove(sessionId);
	}

	public void setSession(String sessionId) {
		Session session = sessions.get(sessionId);
		if (session == null) {
			throw new SessionExpiredException();
		}
		Instant now = Instant.now();
		long lastActivityDuration = Duration.between(now, session.lastActivity).toMinutes();
		if (lastActivityDuration > sessionExpiryTime) {
			sessions.remove(sessionId);
			throw new SessionExpiredException();
		}
		System.out.println("LOGIN with token!!");
		session.lastActivity = now;
		currentUser.set(userRepository.findUserByUsername(session.username).orElseThrow(() -> new RuntimeException("")));
	}

	public User getCurrentUser() {
		return currentUser.get();
	}

	public void removeCurrentUser() {
		currentUser.remove();
	}

	private class Session {
		String username;
		Instant lastActivity;
	}
}
