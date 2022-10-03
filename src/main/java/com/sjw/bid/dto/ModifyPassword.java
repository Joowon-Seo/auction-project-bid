package com.sjw.bid.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ModifyPassword {

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private String email;

		@NotBlank(message = "비밀번호를 입력해주세요.")
		@Size(min = 8, max = 16, message = "비밀번호를 8자 이상 16자 이하로 입력해주세요.")
		private String password;
		private String changePassword;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private String email;
		private String name;

		public static ModifyPassword.Response from(UserDto userDto) {
			return ModifyPassword.Response.builder()
				.email(userDto.getEmail())
				.name(userDto.getName())
				.build();
		}

	}

}
