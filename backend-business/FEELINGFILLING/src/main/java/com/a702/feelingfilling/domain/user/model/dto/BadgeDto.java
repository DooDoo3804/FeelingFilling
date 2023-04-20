package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.Badge;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
	int badgeId;
	LocalDateTime achievedDate;
	
	BadgeDto toDto(Badge badge){
		return BadgeDto.builder()
				.badgeId(badge.getBadgeId())
				.achievedDate(badge.getAchievedDate())
				.build();
		
	}
	
}
