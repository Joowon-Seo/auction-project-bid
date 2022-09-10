package com.sjw.bid.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

	@Getter
	@NoArgsConstructor
	public static class createUser {

		@NotBlank(message = "이름을 입력해주세요.")
		private String name;

		@NotBlank(message = "이메일 주소를 입력해주세요.")
		@Email(message = "올바른 이메일 형식으로 입력해주세요.")
		private String email;

		@NotBlank(message = "비밀번호를 입력해주세요.")
		@Size(min = 8, max = 16, message = "비밀번호를 8자 이상 16자 이하로 입력해주세요.")
		private String password;

		@NotBlank(message = "휴대폰 번호를 입력해주세요.")
		private String phone;

		@NotBlank(message = "주소를 입력해 주세요")
		private String address;
	}
}
