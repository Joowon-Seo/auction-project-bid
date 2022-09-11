package com.sjw.bid.service;

import static com.sjw.bid.type.ErrorCode.THIS_EMAIL_ALREADY_EXISTS;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.dto.CreateUser;
import com.sjw.bid.dto.UserDto;
import com.sjw.bid.exception.UserException;
import com.sjw.bid.repository.UserRepository;
import com.sjw.bid.type.UserLevel;
import com.sjw.bid.type.UserStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserDto createUser(CreateUser.Request request) {

		verificationEmailDuplication(request.getEmail());

		String passwordEncryption = passwordEncryption(request.getPassword());

		return UserDto.fromEntity(
			userRepository.save(User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.address(request.getAddress())
				.phone(request.getPhone())
				.password(passwordEncryption)
				.account(request.getAccount())
				.user_level(UserLevel.UNAUTH)
				.user_status(UserStatus.NORMAL)
				.created_date(LocalDateTime.now())
				.modified_date(LocalDateTime.now())
				.build())
		);
	}

	private String passwordEncryption(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	private void verificationEmailDuplication(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new UserException(THIS_EMAIL_ALREADY_EXISTS);
		}
	}


}
