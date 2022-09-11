package com.sjw.bid.domain.user;

import com.sjw.bid.type.UserLevel;
import com.sjw.bid.type.UserStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(unique = true)
	private String email;

	private String name;

	private String address;

	private String phone;

	private String password;

	private String account;

	@Enumerated(EnumType.STRING)
	private UserLevel user_level;

	@Enumerated(EnumType.STRING)
	private UserStatus user_status;

	private LocalDateTime created_date;

	private LocalDateTime modified_date;


}
