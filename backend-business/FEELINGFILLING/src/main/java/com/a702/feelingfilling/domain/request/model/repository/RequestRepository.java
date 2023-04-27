package com.a702.feelingfilling.domain.request.model.repository;

import com.a702.feelingfilling.domain.request.model.dto.*;
import com.a702.feelingfilling.domain.request.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
	//개인 통계
	
	//1. 이번 달 저금
	@Query(nativeQuery = true, value = "select emotion, count(amount) as count, sum(amount) as amount from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by emotion")
	List<StatInterface> getUserThisMonth(Integer userId);
	
	//2. 월별 추이
	@Query(nativeQuery = true, value = "select emotion, month(request_time) as month, sum(amount) as amount from request where success = 1 and user_id = ?1 and request_time>=date_sub(now(), interval 6 month) group by emotion, month(request_time) order by emotion, month(request_time)")
	List<MonthInterface> getUserMonths(Integer userId);
	
	//3. 이번 달 감정 최고조
	@Query(nativeQuery = true, value = "select day(request_time) as date from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by day(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighDateWithUserId(Integer userId);
	@Query(nativeQuery = true, value = "select hour(request_time) as hour from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by hour(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighHourWithUserId(Integer userId);
	@Query(nativeQuery = true, value = "select weekday(request_time) as day from request where success = 1 and user_id = ?1 and extract(year_month from request_time) = extract(year_month from now()) group by weekday(request_time) order by count(amount) desc limit 1")
	EmotionHighInterface getHighDayWithUserId(Integer userId);
	//4. 저금 누적액
	@Query(nativeQuery = true, value = "select ifnull(sum(amount),0) as amount from request where success = 1 and user_id = ?1")
	int getUserTotal(Integer userId);
	
	
	//전체 사용자 통계
	
	//1. 이번 달 저금
	@Query(nativeQuery = true, value = "select emotion, ifnull(sum(amount),0) as amount from request where success = 1 and extract(year_month from request_time) = extract(year_month from now()) group by emotion")
	List<StatInterface> getThisMonth();
	
	//2. 전날 저금 추이
	@Query(nativeQuery = true, value = "select emotion, hour(request_time) as hour, sum(amount) as amount from request where success = 1 and date(request_time) = date(now()) group by emotion, hour(request_time) order by emotion, hour(request_time)")
	List<YesterdayInterface> getYesterday();
	
	//3. 이번 달 감정왕
	@Query(nativeQuery = true, value = "select count(amount) as count, sum(amount) as amount from request where success = 1 and extract(year_month from request_time) = extract(year_month from now()) group by user_id order by count(amount) desc limit 1")
	StatInterface getEmotionKing();
	
	//4. 저금 누적액
	@Query(nativeQuery = true, value = "select emotion, ifnull(sum(amount),0) as amount from request where success = 1  group by emotion")
	List<StatInterface> getTotal();
}
