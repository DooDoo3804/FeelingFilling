package com.a702.feelingfilling.domain.request.model.repository;

import com.a702.feelingfilling.domain.request.model.dto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.a702.feelingfilling.domain.request.model.entity.QRequest.request;
@Repository
@RequiredArgsConstructor
public class RequestCustomRepositoryImpl implements RequestCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;
	
	
	/**************
	 	개인 통계
	 **************/
	
	@Override
	public List<UserStat> getUserThisMonth(Integer userId) {
		return jpaQueryFactory
				.select(Projections.constructor(UserStat.class,request.emotion,request.amount.sum().as("amount"),request.amount.count().intValue().as("count")))
				.from(request)
				.where(request.success.eq(true)
						.and(request.user.userId.eq(userId))
						.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
				.groupBy(request.emotion)
				.orderBy(request.emotion.asc()).fetch();
	}

	@Override
	public List<Month> getUserMonths(Integer userId) {
		LocalDateTime monthsAgo = LocalDateTime.now().minusMonths(6);
		return jpaQueryFactory
				.select(Projections.constructor(Month.class,request.emotion, request.requestTime.yearMonth(),request.amount.sum()))
				.from(request)
				.where(request.success.eq(true)
						.and(request.user.userId.eq(userId))
						.and(request.requestTime.yearMonth().goe(Expressions.asDateTime(monthsAgo).yearMonth())))
				.groupBy(request.emotion, request.requestTime.yearMonth())
				.orderBy(request.emotion.asc(), request.requestTime.yearMonth().asc())
				.fetch();
	}
	
	@Override
	public Integer getHighDateWithUserId(Integer userId) {

			return jpaQueryFactory
					.select(request.requestTime.dayOfMonth().as("date"))
					.from(request)
					.where(request.success.eq(true)
							.and(request.user.userId.eq(userId))
							.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
					.groupBy(request.requestTime.dayOfMonth())
					.orderBy(request.amount.count().desc())
					.fetchFirst();
	}

	@Override
	public Integer getHighHourWithUserId(Integer userId) {
		return jpaQueryFactory
				.select(request.requestTime.hour().as("hour"))
				.from(request)
				.where(request.success.eq(true)
						.and(request.user.userId.eq(userId))
						.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
				.groupBy(request.requestTime.hour())
				.orderBy(request.amount.count().desc())
				.fetchFirst();
	}

	@Override
	public Integer getHighDayWithUserId(Integer userId) {
		return jpaQueryFactory
				.select(request.requestTime.dayOfWeek().as("day"))
				.from(request)
				.where(request.success.eq(true)
						.and(request.user.userId.eq(userId))
						.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
				.groupBy(request.requestTime.dayOfWeek())
				.orderBy(request.amount.count().desc())
				.fetchFirst();
	}

	@Override
	public int getUserTotal(Integer userId) {
		return jpaQueryFactory
				.select(request.amount.sum().coalesce(0).as("sum"))
				.from(request)
				.where(request.success.eq(true).and(request.user.userId.eq(userId)))
				.orderBy(request.emotion.asc())
				.fetchFirst();
	}
	
	
	/**************
		전체 통계
	 **************/
	
	//1. 이번 달 저금
	@Override
	public List<Stat> getThisMonth() {
		return jpaQueryFactory
				.select(Projections.constructor(Stat.class,request.emotion,request.amount.sum().as("amount")))
				.from(request)
				.where(request.success.eq(true)
						.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
				.groupBy(request.emotion)
				.orderBy(request.emotion.asc()).fetch();
	}

	@Override
	public List<Yesterday> getYesterday() {
		return jpaQueryFactory
				.select(Projections.constructor(Yesterday.class,request.emotion, request.requestTime.hour().as("hour"),request.amount.sum().as("amount")))
				.from(request)
				.where(request.success.eq(true)
						.and(request.requestTime.year().eq(Expressions.dateTemplate(LocalDateTime.class, "ADDDATE(now(),-1)").year()))
						.and(request.requestTime.dayOfYear().eq(Expressions.dateTemplate(LocalDateTime.class, "ADDDATE(now(),-1)").dayOfYear())))
				.groupBy(request.emotion,request.requestTime.hour())
				.orderBy(request.emotion.asc(),request.requestTime.hour().asc())
				.fetch();
	}

	@Override
	public EmotionKing getEmotionKing() {

		return jpaQueryFactory
				.select(Projections.constructor(EmotionKing.class,request.amount.count().intValue().as("count"), request.amount.sum().as("amount")))
				.from(request)
				.where(request.success.eq(true)
						.and(request.requestTime.yearMonth().eq(Expressions.currentTimestamp().yearMonth())))
				.groupBy(request.user)
				.orderBy(request.amount.count().desc(),request.amount.sum().desc())
				.fetchFirst();
	}

	@Override
	public List<Stat> getTotal() {
		return jpaQueryFactory
				.select(Projections.constructor(Stat.class,request.emotion,request.amount.sum().as("amount")))
				.from(request)
				.where(request.success.eq(true))
				.groupBy(request.emotion)
				.orderBy(request.emotion.asc())
				.fetch();
	}
}
