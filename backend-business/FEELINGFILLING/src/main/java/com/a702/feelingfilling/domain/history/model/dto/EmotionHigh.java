package com.a702.feelingfilling.domain.history.model.dto;

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
