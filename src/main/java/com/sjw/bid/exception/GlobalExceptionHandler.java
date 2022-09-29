package com.sjw.bid.exception;

import static com.sjw.bid.type.ErrorCode.INVALID_EMAIL_OR_PASSWORD;
import static com.sjw.bid.type.ErrorCode.INVALID_REQUEST;
import static com.sjw.bid.type.ErrorCode.INVALID_SERVER_ERROR;

import com.sjw.bid.dto.ErrorResponse;
import com.sjw.bid.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ErrorResponse handleUserException(UserException e) {
		log.error("{} is occurred.", e.getErrorCode());

		return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
	}

	@ExceptionHandler({AuthenticationException.class})
	public ErrorResponse authenticationException(
		Exception e) {
		log.error("{} is occurred.", e.toString());

		return new ErrorResponse(ErrorCode.INVALID_EMAIL_OR_PASSWORD,
			e.getMessage());
	}
	@ExceptionHandler({UserAuthenticationException.class})
	public ErrorResponse userAuthenticationException(
		UserAuthenticationException e) {
		log.error("{} is occurred.", e.toString());

		return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException is occurred.", e);

		return new ErrorResponse(INVALID_REQUEST,
			INVALID_REQUEST.getDescription());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorResponse handleDataIntegrityViolationException(
		DataIntegrityViolationException e) {
		log.error("DataIntegrityViolationException is occurred.", e);

		return new ErrorResponse(INVALID_REQUEST,
			INVALID_REQUEST.getDescription());
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception e) {
		log.error("Exception is occurred.", e);

		return new ErrorResponse(
			INVALID_SERVER_ERROR,
			INVALID_SERVER_ERROR.getDescription());
	}

}
