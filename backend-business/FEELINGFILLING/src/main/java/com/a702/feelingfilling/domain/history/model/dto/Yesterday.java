package com.a702.feelingfilling.domain.history.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Yesterday extends Stat {
	int hour;
	public Yesterday(String emotion, int hour, int amount){
		super(emotion,amount);
		this.hour = hour;
		
	}
}
