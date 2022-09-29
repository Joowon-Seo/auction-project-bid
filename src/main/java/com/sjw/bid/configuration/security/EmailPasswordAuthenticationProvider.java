package com.sjw.bid.configuration.security;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.exception.UserAuthenticationException;
import com.sjw.bid.type.UserLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Slf4j
public class EmailPasswordAuthenticationProvider implements
	AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final GrantedAuthoritiesMapper grantedAuthoritiesMapper;

	@Override
	public Authentication authenticate(Authentication authentication)
		throws AuthenticationException {
		String userId = String.valueOf(authentication.getPrincipal());
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

		if (!this.passwordEncoder.matches(
			authentication.getCredentials().toString(),
			userDetails.getPassword())) {
			throw new BadCredentialsException(userId);
		}

		log.info("비번 통과");

//		if (UserLevel.UNAUTH.equals(userDetails.getAuthorities())) {
//			throw new InternalAuthenticationServiceException("USER_HAS_NOT_AUTHENTICATED_THE_EMAIL");
//		}

		EmailPasswordAuthenticationToken certifiedToken = new EmailPasswordAuthenticationToken(
			userDetails.getUsername(),
			userDetails.getPassword(), grantedAuthoritiesMapper.mapAuthorities(
			userDetails.getAuthorities()));

		certifiedToken.setDetails(authentication.getDetails());

		return certifiedToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (EmailPasswordAuthenticationToken.class.isAssignableFrom(
			authentication));
	}
}
