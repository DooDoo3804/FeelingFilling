package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.EmotionHigh;
import com.a702.feelingfilling.domain.request.model.dto.Stat;

import java.util.List;

public interface RequestService {
	
	List<Stat> getUserThisMonth(Integer userId);
	EmotionHigh getEmotionHigh(Integer userId);
}
