package com.sjw.bid.service;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.repository.UserRepository;
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
			throw new UsernameNotFoundException("존재하지 않습니다.");
		}

		User user = userOptional.get();

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
			"ROLE_" + user.getUser_level().toString());
		grantedAuthorityList.add(simpleGrantedAuthority);

		return new org.springframework.security.core.userdetails.User(
			user.getEmail(), user.getPassword(), grantedAuthorityList);
	}
}
