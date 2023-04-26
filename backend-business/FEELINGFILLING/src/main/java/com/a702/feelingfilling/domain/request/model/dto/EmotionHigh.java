package com.a702.feelingfilling.domain.request.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmotionHigh {
	int date;
	int hour;
	String day;
}
