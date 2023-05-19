package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserBriefDTO {
	Integer userId;
	String nickname;
	
	public static UserBriefDTO toDTO(User user){
		return UserBriefDTO.builder()
				.userId(user.getUserId())
				.nickname(user.getNickname())
				.build();
	}
}
