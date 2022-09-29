package com.sjw.bid.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("userAuthenticationEntryPoint")
@Slf4j
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

//	private UserAuthenticationExceptionResolver userAuthenticationExceptionResolver;

	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException authException)
		throws IOException, ServletException {
		log.info(authException.getMessage());
		resolver.resolveException(request, response, null, authException);
//		userAuthenticationExceptionResolver.resolveException(request, response, null, authException);
	}
}
