package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.*;

import java.util.List;

public interface RequestService {
	//개인 통계
	List<Stat> getUserThisMonth(Integer userId);
	Month[][] getUserMonths(Integer userId);
	EmotionHigh getEmotionHigh(Integer userId);
	int getUserTotal(Integer userId);
	
	//전체 통계
	List<Stat> getThisMonth();
	List<Yesterday> getYesterday();
	Stat getEmotionKing();
	List<Stat> getTotal();
	
}
