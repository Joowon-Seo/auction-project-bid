package com.sjw.bid.service;

import static com.sjw.bid.type.ErrorCode.STOPPED_USER;
import static com.sjw.bid.type.ErrorCode.USER_DOES_NOT_EXIST;
import static com.sjw.bid.type.ErrorCode.USER_HAS_NOT_AUTHENTICATED_THE_EMAIL;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.exception.UserException;
import com.sjw.bid.repository.UserRepository;
import com.sjw.bid.type.UserLevel;
import com.sjw.bid.type.UserStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email)
		throws UsernameNotFoundException {

		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			throw new UserException(USER_DOES_NOT_EXIST);
		}

		User user = userOptional.get();

		if (UserLevel.UNAUTH.equals(user.getUser_level())) {
			throw new UserException(USER_HAS_NOT_AUTHENTICATED_THE_EMAIL);
		}

		if (UserStatus.BAN.equals(user.getUser_status())) {
			throw new UserException(STOPPED_USER);
		}

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
			"ROLE_" + user.getUser_level().toString());
		grantedAuthorityList.add(simpleGrantedAuthority);

		return new org.springframework.security.core.userdetails.User(
			user.getEmail(), user.getPassword(), grantedAuthorityList);
	}
}
