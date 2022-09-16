package com.sjw.bid.configuration.security;

import com.sjw.bid.configuration.security.EmailPasswordAuthenticationToken;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JsonEmailPasswordAuthenticationFilter extends
	AbstractAuthenticationProcessingFilter {

	protected JsonEmailPasswordAuthenticationFilter(
		RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response)
		throws AuthenticationException, IOException {

		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
				"Authentication method not supported: " + request.getMethod());
		}

		Map<String, Object> parseJsonMap = parseJsonMap(request);

		String email = (String) parseJsonMap.get("email");
		String password = (String) parseJsonMap.get("password");

		EmailPasswordAuthenticationToken emailPasswordAuthenticationToken = new EmailPasswordAuthenticationToken(
			email, password);
		emailPasswordAuthenticationToken.setDetails(
			super.authenticationDetailsSource.buildDetails(request));

		return super.getAuthenticationManager()
			.authenticate(emailPasswordAuthenticationToken);
	}

	private Map<String, Object> parseJsonMap(HttpServletRequest request)
		throws IOException {
		String body = request.getReader().lines().collect(Collectors.joining());
		GsonJsonParser gsonJsonParser = new GsonJsonParser();
		return gsonJsonParser.parseMap(body);
	}


}
