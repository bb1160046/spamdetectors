package com.spamdetector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spamdetector.dto.JwtResponse;
import com.spamdetector.dto.LoginRequest;
import com.spamdetector.dto.RegisterRequest;
import com.spamdetector.entity.User;
import com.spamdetector.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Transactional
	public void register(RegisterRequest dto) {
		if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
			throw new IllegalArgumentException("Phone already registered");
		}
		User user = new User();
		user.setName(dto.getName());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setEmail(dto.getEmail());
		userRepository.save(user);
	}

	public JwtResponse login(LoginRequest dto) {
		User user = userRepository.findByPhoneNumber(dto.getPhoneNumber())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Invalid credentials");
		}
		String token = jwtTokenProvider.generateToken(user.getPhoneNumber());
		return new JwtResponse(token);
	}
}
