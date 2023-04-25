package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.EmotionHigh;
import com.a702.feelingfilling.domain.request.model.dto.Month;
import com.a702.feelingfilling.domain.request.model.dto.Stat;

import java.util.List;

public interface RequestService {
	//개인 통계
	List<Stat> getUserThisMonth(Integer userId);
	List<Month> getUserMonths(Integer userId);
	EmotionHigh getEmotionHigh(Integer userId);
	int getUserTotal(Integer userId);
	
}
