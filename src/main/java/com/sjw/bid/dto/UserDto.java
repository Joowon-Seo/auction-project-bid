package com.sjw.bid.dto;

import com.sjw.bid.domain.user.User;
import com.sjw.bid.type.UserLevel;
import com.sjw.bid.type.UserStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

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

	public static UserDto fromEntity(User user) {
		return UserDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.address(user.getAddress())
			.phone(user.getPhone())
			.password(user.getPassword())
			.account(user.getAccount())
			.user_level(user.getUser_level())
			.user_status(user.getUser_status())
			.created_date(user.getCreated_date())
			.modified_date(user.getModified_date())
			.build();
	}


}
