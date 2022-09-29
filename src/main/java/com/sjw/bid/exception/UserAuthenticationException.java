package com.sjw.bid.exception;

import com.sjw.bid.type.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class UserAuthenticationException extends RuntimeException {

	private ErrorCode errorCode;
	private String errorMessage;

	public UserAuthenticationException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
