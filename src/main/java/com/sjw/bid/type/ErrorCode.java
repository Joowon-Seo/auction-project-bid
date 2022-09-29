package com.sjw.bid.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
	INVALID_REQUEST("잘못된 요청입니다."),

	USER_DOES_NOT_EXIST("가입 정보가 없는 사용자입니다."),
	USER_HAS_NOT_AUTHENTICATED_THE_EMAIL("이메일 인증을 하지 않은 사용자입니다."),
	STOPPED_USER("중지된 사용자입니다."),
	INVALID_EMAIL_OR_PASSWORD("아이디 혹은 비밀번호가 잘못되었습니다."),
	THIS_EMAIL_ALREADY_EXISTS("이미 사용중인 이메일입니다.");


	private final String description;
}
