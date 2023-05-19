package com.a702.feelingfilling.domain.history.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Stat {
	String emotion;
	int amount;
}
