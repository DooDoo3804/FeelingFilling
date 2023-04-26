package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.*;
import com.a702.feelingfilling.domain.request.model.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Override
	public List<Stat> getUserThisMonth(Integer userId) {
		List<StatInterface> statInterfaces = requestRepository.getUserThisMonth(userId);
		
		List<Stat> stats = new ArrayList<>();
		statInterfaces.forEach(x -> stats.add(Stat.builder()
				.emotion(x.getEmotion())
				.count(x.getCount())
				.amount(x.getAmount())
				.build()));
		return stats;
	}
	
	@Override
	public List<Month> getUserMonths(Integer userId) {
		List<MonthInterface> monthInterfaces = requestRepository.getUserMonths(userId);
		
		List<Month> months = new ArrayList<>();
		
		monthInterfaces.forEach(x -> months.add(Month.builder()
				.emotion(x.getEmotion())
				.month(x.getMonth())
				.amount(x.getAmount())
				.build()));
		
		return months;
	}
	
	@Override
	public EmotionHigh getEmotionHigh(Integer userId) {
		
		EmotionHighInterface date = requestRepository.getHighDateWithUserId(userId);
		EmotionHighInterface hour = requestRepository.getHighHourWithUserId(userId);
		EmotionHighInterface day = requestRepository.getHighDayWithUserId(userId);
		
		
		return EmotionHigh.builder()
				.date(date != null ? date.getDate() : 0)
				.hour(hour != null ? hour.getHour() : -1)
				.day(day != null ? convertDay(day.getDay()) : "없음")
				.build();
	}
	
	@Override
	public int getUserTotal(Integer userId) {
		return requestRepository.getUserTotal(userId);
	}
	
	@Override
	public List<Stat> getThisMonth() {
		List<StatInterface> statInterfaces = requestRepository.getThisMonth();
		
		List<Stat> stats = new ArrayList<>();
		statInterfaces.forEach(x -> stats.add(Stat.builder()
				.emotion(x.getEmotion())
				.amount(x.getAmount())
				.build()));
		return stats;
	}
	
	@Override
	public List<Yesterday> getYesterday() {
		List<YesterdayInterface> yesteredayInterfaces = requestRepository.getYesterday();
		
		List<Yesterday> yesterday = new ArrayList<>();
		
		yesteredayInterfaces.forEach(x -> yesterday.add(Yesterday.builder()
				.emotion(x.getEmotion())
				.hour(x.getHour())
				.amount(x.getAmount())
				.build()));
		
		return yesterday;
	}
	
	@Override
	public Stat getEmotionKing() {
		StatInterface statInterface = requestRepository.getEmotionKing();
		
		return Stat.builder()
				.amount(statInterface != null? statInterface.getAmount():0)
				.count(statInterface != null? statInterface.getCount() : 0)
				.build();
	}
	
	@Override
	public List<Stat> getTotal() {
		List<StatInterface> statInterfaces = requestRepository.getTotal();
		
		List<Stat> stats = new ArrayList<>();
		
		statInterfaces.forEach(x -> stats.add(Stat.builder()
				.emotion(x.getEmotion())
				.amount(x.getAmount())
				.build()));
		
		return stats;
	}
	
	public String convertDay(int d){
		String[] day = new String[]{"월","화","수","목","금","토","일"};
		
		return day[d];
	}
}
