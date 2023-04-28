package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.*;

public interface RequestService {
	//개인 통계
	UserStat[] getUserThisMonth(Integer userId);
	Month[][] getUserMonths(Integer userId);
	EmotionHigh getEmotionHigh(Integer userId);
	int getUserTotal(Integer userId);
	
	//전체 통계
	Stat[] getThisMonth();
	Yesterday[][] getYesterday();
	EmotionKing getEmotionKing();
	Stat[] getTotal();
	
}
