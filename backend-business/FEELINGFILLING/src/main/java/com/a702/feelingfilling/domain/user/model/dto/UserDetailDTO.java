package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
public class UserDetailDTO {
	Integer userId;
	String nickname;
	int minimum;
	int maximum;
	String role;
	String joinDate;
	
	public static UserDetailDTO toDTO(User user){
		return UserDetailDTO.builder()
				.userId(user.getUserId())
				.nickname(user.getNickname())
				.minimum(user.getMinimum())
				.maximum(user.getMaximum())
				.role(user.getRole())
				.joinDate(user.getJoin_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.build();
	}
}
