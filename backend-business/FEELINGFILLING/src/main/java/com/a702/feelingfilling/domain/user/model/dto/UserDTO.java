package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDTO {
	Integer userId;
	String nickname;
	int minimum;
	int maximum;
	
	public static UserDTO toDTO(User user){
		return UserDTO.builder()
				.userId(user.getUserId())
				.nickname(user.getNickname())
				.minimum(user.getMinimum())
				.maximum(user.getMaximum())
				.build();
	}
}
