package com.a702.feelingfilling.domain.request.model.dto;

import io.swagger.annotations.Api;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmotionKing {
	int count;
	int amount;
}
