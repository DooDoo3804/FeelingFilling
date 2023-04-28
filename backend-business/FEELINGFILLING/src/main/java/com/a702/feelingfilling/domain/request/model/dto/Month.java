package com.a702.feelingfilling.domain.request.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
public class Month extends Stat {
	int month;
	public	Month(String emotion, int month, int amount){
		super(emotion, amount);
		this.month = month;
	}
}
