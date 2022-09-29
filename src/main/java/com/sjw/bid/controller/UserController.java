package com.sjw.bid.controller;

import com.sjw.bid.dto.CreateUser;
import com.sjw.bid.dto.Login;
import com.sjw.bid.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(
		@RequestBody @Valid CreateUser.Request request
	) {
		return ResponseEntity.ok(CreateUser.Response.from(
			userService.createUser(request)
		));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(
		@RequestBody @Valid Login.Request request
	) {
		return ResponseEntity.ok(Login.Response.from(
			userService.login(request)
		));
	}
}
