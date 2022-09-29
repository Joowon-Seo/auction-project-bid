package com.sjw.bid.configuration.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException exception)
		throws IOException, ServletException {

		String errorMsg = "";

		if (exception instanceof BadCredentialsException) {
			errorMsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
		} else if (exception instanceof DisabledException) {
			errorMsg = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMsg = "이메일이 인증되지 않았습니다. 이메일을 확인해 주세요.";
		} else {
			errorMsg = "로그인에 실패하였습니다.";
		}

		super.onAuthenticationFailure(request, response, exception);
	}
}
