package com.sjw.bid.service;

import static com.sjw.bid.type.ErrorCode.INVALID_EMAIL_OR_PASSWORD;
import static com.sjw.bid.type.ErrorCode.STOPPED_USER;
import static com.sjw.bid.type.ErrorCode.THIS_EMAIL_ALREADY_EXISTS;
import static com.sjw.bid.type.ErrorCode.USER_DOES_NOT_EXIST;
import static com.sjw.bid.type.ErrorCode.USER_HAS_NOT_AUTHENTICATED_THE_EMAIL;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.dto.CreateUser;
import com.sjw.bid.dto.Login;
import com.sjw.bid.dto.ModifyAddress;
import com.sjw.bid.dto.ModifyPassword;
import com.sjw.bid.dto.UserDto;
import com.sjw.bid.exception.UserAuthenticationException;
import com.sjw.bid.exception.UserException;
import com.sjw.bid.repository.UserRepository;
import com.sjw.bid.type.UserLevel;
import com.sjw.bid.type.UserStatus;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UserDto createUser(CreateUser.Request request) {

		verificationEmailDuplication(request.getEmail());

		String passwordEncoded = encryptPassword(request.getPassword());

		return UserDto.fromEntity(
			userRepository.save(User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.address(request.getAddress())
				.phone(request.getPhone())
				.password(passwordEncoded)
				.account(request.getAccount())
				.user_level(UserLevel.UNAUTH)
				.user_status(UserStatus.NORMAL)
				.created_date(LocalDateTime.now())
				.modified_date(LocalDateTime.now())
				.build())
		);
	}


	private void verificationEmailDuplication(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new UserException(THIS_EMAIL_ALREADY_EXISTS);
		}
	}

	public UserDto login(Login.Request request) {

		Optional<User> userOptional = userRepository.findByEmail(
			request.getEmail());

		if (userOptional.isEmpty()) {
			throw new UserAuthenticationException(USER_DOES_NOT_EXIST);
		}

		User user = userOptional.get();

		log.info(user.getPassword().substring(0, 8));

		if (!passwordEncoder.matches(request.getPassword(),
			user.getPassword().substring(8))) {
			throw new UserAuthenticationException(INVALID_EMAIL_OR_PASSWORD);
		}

		if (UserLevel.UNAUTH.equals(user.getUser_level())) {
			throw new UserAuthenticationException(
				USER_HAS_NOT_AUTHENTICATED_THE_EMAIL);
		}

		if (UserStatus.BAN.equals(user.getUser_status())) {
			throw new UserAuthenticationException(STOPPED_USER);
		}

		return UserDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.address(user.getAddress())
			.phone(user.getPhone())
			.password(user.getPassword())
			.account(user.getAccount())
			.user_level(user.getUser_level())
			.user_status(user.getUser_status())
			.created_date(user.getCreated_date())
			.modified_date(user.getModified_date())
			.build();
	}

	public UserDto modifyPassword(ModifyPassword.Request request) {

		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new UserException(USER_DOES_NOT_EXIST));
		verifyPassword(user, request.getPassword());
		String newPassword = encryptPassword(request.getChangePassword());

		user.setPassword(newPassword);
		user.setModified_date(LocalDateTime.now());

		return UserDto.fromEntity(
			userRepository.save(user));
	}

	public UserDto modifyAddress(ModifyAddress.Request request) {

		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new UserException(USER_DOES_NOT_EXIST));
		verifyPassword(user, request.getPassword());

		String newAddress = request.getNewAddress();

		user.setAddress(newAddress);
		user.setModified_date(LocalDateTime.now());

		return UserDto.fromEntity(
			userRepository.save(user));
	}

	private void verifyPassword(User user, String password) {

		if (!passwordEncoder.matches(password,
			user.getPassword().substring(8))) {
			throw new UserException(INVALID_EMAIL_OR_PASSWORD);
		}
	}

	private String encryptPassword(String password) {

		return "{bcrypt}" + passwordEncoder.encode(password);
	}


}
