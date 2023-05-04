package com.a702.feelingfilling.domain.history.model.dto;

import com.a702.feelingfilling.domain.history.model.entity.History;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Builder
@Setter
@Getter
public class LogDTO {
	String logTime;
	String emotion;
	int amount;
	int total;
	
	public static LogDTO toDTO(History history){
		return LogDTO.builder()
				.logTime(history.getRequestTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.emotion(history.getEmotion())
				.amount(history.getAmount())
				.total(history.getTotal())
				.build();
	}
	
}
