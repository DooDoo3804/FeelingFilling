package com.a702.feelingfilling.domain.request.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Day {
	String emotion;
	int day;
	int amount;
}
