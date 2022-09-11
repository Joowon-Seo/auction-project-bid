package com.sjw.bid.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
	INVALID_REQUEST("잘못된 요청입니다."),

	THIS_EMAIL_ALREADY_EXISTS("이미 사용중인 이메일 입니다.");

	private final String description;
}
