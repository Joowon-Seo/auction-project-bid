package com.sjw.bid.controller;

import com.sjw.bid.dto.CreateUser;
import com.sjw.bid.dto.Login;
import com.sjw.bid.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/user/create")
	private CreateUser.Response createUser(
		@RequestBody @Valid CreateUser.Request request
	) {
		return CreateUser.Response.from(
			userService.createUser(request)
		);
	}

	@PostMapping("/user/login")
	private Login.Response login(
		@RequestBody @Valid Login.Request request
	) {
		return Login.Response.from(
			userService.login(request)
		);
	}
}
