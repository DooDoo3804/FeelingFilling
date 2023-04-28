package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.*;
import com.a702.feelingfilling.domain.request.model.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
	private RequestRepository requestRepository;
	private final String[] emotion = new String[]{"anger","joy","sadness"};
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
	
	public int getInd(int now, int m){
		int res = 5-(now-m+12)%12;
		return res;
	}
	@Override
	public Month[][] getUserMonths(Integer userId) {
		List<MonthInterface> monthInterfaces = requestRepository.getUserMonths(userId);

		int now = LocalDateTime.now().getMonthValue();
		int month = now;
		int year = LocalDateTime.now().getYear()*100;
		Month[][] months = new Month[3][6];
		for(int i = 5;i>=0;i--) {
			for(int j = 0;j<3;j++){
				months[j][i] = Month.builder()
						.month(year+now)
						.amount(0)
						.build();
			}
			now--;
			if(now==0){
				now = 12;
				year-=100;
			}
		}
		
		int i = 0;
		int j;
		
		for(MonthInterface monthInterface: monthInterfaces){
			while(i<3 && !monthInterface.getEmotion().equals(emotion[i]))i++;
			j = getInd(month,monthInterface.getMonth());
			months[i][j].setEmotion(monthInterface.getEmotion());
			months[i][j].setAmount(monthInterface.getAmount());
		}
//		List<Month> months = new ArrayList<>();
//
//		monthInterfaces.forEach(x -> months.add(Month.builder()
//				.emotion(x.getEmotion())
//				.month(x.getMonth())
//				.amount(x.getAmount())
//				.build()));
		
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
	public Yesterday[][] getYesterday() {
		List<YesterdayInterface> yesterdayInterfaces = requestRepository.getYesterday();
		
		int now = LocalDateTime.now().getMonthValue();
		
		Yesterday[][] yesterday = new Yesterday[3][24];
		for(int i = 23;i>=0;i--) {
			for(int j = 0;j<3;j++){
				yesterday[j][i] = Yesterday.builder()
						.hour(i)
						.amount(0)
						.build();
			}
		}
		
		int i = 0;
		int j;
		
		for(YesterdayInterface yesterdayInterface: yesterdayInterfaces){
			while(i<3 && !yesterdayInterface.getEmotion().equals(emotion[i]))i++;
			yesterday[i][yesterdayInterface.getHour()].setEmotion(yesterdayInterface.getEmotion());
			yesterday[i][yesterdayInterface.getHour()].setAmount(yesterdayInterface.getAmount());
		}
//		List<Yesterday> yesterday = new ArrayList<>();
//
//		yesterdayInterfaces.forEach(x -> yesterday.add(Yesterday.builder()
//				.emotion(x.getEmotion())
//				.hour(x.getHour())
//				.amount(x.getAmount())
//				.build()));
		
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
