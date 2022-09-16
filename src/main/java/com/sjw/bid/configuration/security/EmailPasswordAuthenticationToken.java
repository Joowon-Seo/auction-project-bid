package com.sjw.bid.configuration.security;

import java.util.Collection;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

@EqualsAndHashCode(callSuper = false)
public class EmailPasswordAuthenticationToken extends
	AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String email;

	private String password;

	public EmailPasswordAuthenticationToken(String email, String password) {
		super(null);
		this.email = email;
		this.password = password;
		setAuthenticated(false);
	}

	public EmailPasswordAuthenticationToken(String email, String password,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.email = email;
		this.password = password;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.password;
	}

	@Override
	public Object getPrincipal() {
		return this.email;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.password = null;
	}
}
