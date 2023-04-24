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
	RequestRepository requestRepository;
	
	@Override
	public List<Stat> getUserThisMonth(Integer userId) {
		List<StatInterface> statInterface = requestRepository.getUserThisMonth(userId);
		
		List<Stat> stats = new ArrayList<>();
		statInterface.forEach(x->stats.add(Stat.builder()
				.emotion(x.getEmotion())
				.count(x.getCount())
				.amount(x.getAmount())
				.build()));
		return stats;
	}
	
	@Override
	public EmotionHigh getEmotionHigh(Integer userId) {
		
		EmotionHighInterface date = requestRepository.getHighDateWithUserId(userId);
		EmotionHighInterface hour = requestRepository.getHighHourWithUserId(userId);
		EmotionHighInterface day = requestRepository.getHighDayWithUserId(userId);
		
		
		return EmotionHigh.builder()
				.date(date!=null? date.getDate():0)
				.hour(hour!=null? hour.getHour():-1)
				.day(day!=null? convertDay(day.getDay()):"없음")
				.build();
	}
	
	public String convertDay(int d){
		String[] day = new String[]{"월","화","수","목","금","토","일"};
		
		return day[d];
	}
}
