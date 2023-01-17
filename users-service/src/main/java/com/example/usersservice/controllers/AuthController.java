package com.example.usersservice.controllers;

import com.example.usersservice.payload.request.LoginRequest;
import com.example.usersservice.payload.request.SignupRequest;
import com.example.usersservice.services.AuthService;
import com.kastourik12.clients.users.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("inside users service : authenticating user");
		return authService.authenticateUser(loginRequest);
	}
	@GetMapping("/validateToken")
	public ResponseEntity<UserDTO> validateToken(@RequestParam(value = "token") String token) {
		logger.info("inside users service : validating token");
		return authService.validateToken(token);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		logger.info("inside Auth microserivce : registering user");
		return authService.saveUser(signUpRequest);
	}
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<?> verifyUser(@PathVariable String token) {
		logger.info("inside Auth microserivce : verifying user");
		return authService.verifyUser(token);
	}





}
