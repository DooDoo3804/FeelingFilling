package com.a702.feelingfilling.domain.request.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Stat {
	String emotion;
	int amount;
	int count;
}
