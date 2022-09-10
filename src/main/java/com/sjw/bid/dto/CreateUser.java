package com.sjw.bid.dto;

import com.sjw.bid.domain.user.UserLevel;
import com.sjw.bid.domain.user.UserStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CreateUser {

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

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

		@NotBlank(message = "계좌 번호를 입력해주세요.")
		private String account;

		@NotBlank(message = "주소를 입력해 주세요")
		private String address;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private Long id;
		private String email;
		private String name;
		private String address;
		private String phone;
		private String password;
		private String account;
		private UserLevel user_level;
		private UserStatus user_status;
		private LocalDateTime created_date;
		private LocalDateTime modified_date;

		public static Response from(UserDto userDto){
			return Response.builder()
				.id(userDto.getId())
				.email(userDto.getEmail())
				.name(userDto.getName())
				.address(userDto.getAddress())
				.phone(userDto.getPhone())
				.password(userDto.getPassword())
				.account(userDto.getAccount())
				.user_level(userDto.getUser_level())
				.user_status(userDto.getUser_status())
				.created_date(userDto.getCreated_date())
				.modified_date(userDto.getModified_date())
				.build();
		}
	}



}
