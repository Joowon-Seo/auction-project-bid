package com.sjw.bid.service;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.domain.user.UserLevel;
import com.sjw.bid.domain.user.UserStatus;
import com.sjw.bid.dto.CreateUser;
import com.sjw.bid.dto.UserDto;
import com.sjw.bid.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserDto createUser(CreateUser.Request request) {
		return UserDto.fromEntity(
			userRepository.save(User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.address(request.getAddress())
				.phone(request.getPhone())
				.password(request.getPassword())
				.account(request.getAccount())
				.user_level(UserLevel.UNAUTH)
				.user_status(UserStatus.NORMAL)
				.created_date(LocalDateTime.now())
				.modified_date(LocalDateTime.now())
				.build())
		);
	}

}
