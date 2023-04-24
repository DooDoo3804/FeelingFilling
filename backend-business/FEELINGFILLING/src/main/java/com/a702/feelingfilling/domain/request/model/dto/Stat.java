package com.a702.feelingfilling.domain.request.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Stat {
	String emotion;
	int amount;
	int count;
}
