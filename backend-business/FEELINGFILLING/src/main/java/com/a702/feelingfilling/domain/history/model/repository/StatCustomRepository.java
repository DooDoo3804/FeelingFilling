package com.a702.feelingfilling.domain.history.model.repository;

import com.a702.feelingfilling.domain.history.model.dto.*;

import java.util.List;

public interface StatCustomRepository {
	
	//개인 통계

	//1.이번 달 저금
	List<UserStat> getUserThisMonth(Integer userId);

	//2. 월별 추이
	List<Month> getUserMonths(Integer userId);

	//3. 이번 달 감정 최고조
	Integer getHighDateWithUserId(Integer userId);
	Integer getHighHourWithUserId(Integer userId);
	Integer getHighDayWithUserId(Integer userId);

	//4. 저금 누적액
	int getUserTotal(Integer userId);


	//전체 사용자 통계

	//1. 이번 달 저금
	List<Stat> getThisMonth();

	//2. 전날 저금 추이
	List<Yesterday> getYesterday();

	//3. 이번 달 감정왕
	EmotionKing getEmotionKing();

	//4. 저금 누적액
	List<Stat> getTotal();
}
