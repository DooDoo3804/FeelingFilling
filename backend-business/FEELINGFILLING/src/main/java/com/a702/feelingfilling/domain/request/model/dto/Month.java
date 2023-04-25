package com.a702.feelingfilling.domain.request.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Month {
	String emotion;
	int month;
	int amount;
}
