package com.a702.feelingfilling.domain.request.model.repository;

import com.a702.feelingfilling.domain.request.model.dto.EmotionHighInterface;
import com.a702.feelingfilling.domain.request.model.dto.StatInterface;
import com.a702.feelingfilling.domain.request.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
	//개인 통계
	
	//1. 이번 달 저금

	//2. 월별 추이
	
	
	//3. 이번 달 감정 최고조
	@Query(nativeQuery = true, value = "select day(request_time) as date from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by day(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighDateWithUserId(Integer userId);
	@Query(nativeQuery = true, value = "select hour(request_time) as hour from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by hour(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighHourWithUserId(Integer userId);
	@Query(nativeQuery = true, value = "select weekday(request_time) as day from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by weekday(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighDayWithUserId(Integer userId);
	
	//4. 저금 누적액
	
	
	//전체 사용자 통계
	//1. 이번 달 저금
	//2. 전날 저금 추이
	//3. 이번 달 감정왕
	//4. 저금 누적액
}
