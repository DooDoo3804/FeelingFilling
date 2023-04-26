package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.Builder;

@Builder
public class UserDTO {
	String nickname;
	int minimum;
	int maximum;
	
	public static UserDTO toDTO(User user){
		return UserDTO.builder()
				.nickname(user.getNickname())
				.minimum(user.getMinimum())
				.maximum(user.getMaximum())
				.build();
	}
}
