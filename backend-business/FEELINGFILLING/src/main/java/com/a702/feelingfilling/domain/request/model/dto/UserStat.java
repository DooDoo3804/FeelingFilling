package com.a702.feelingfilling.domain.request.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
public class UserStat {
	String emotion;
	int amount;
	int count;
}
