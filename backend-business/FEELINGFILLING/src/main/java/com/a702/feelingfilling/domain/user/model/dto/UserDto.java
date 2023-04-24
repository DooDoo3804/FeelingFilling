package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.Builder;

@Builder
public class UserDto {
	long userId;
	String nickname;
	int minimum;
	int maximum;
	
	UserDto toDto(User user){
		return UserDto.builder()
				.userId(user.getUserId())
				.nickname(user.getNickname())
				.minimum(user.getMinimum())
				.maximum(user.getMaximum())
				.build();
	}
}
