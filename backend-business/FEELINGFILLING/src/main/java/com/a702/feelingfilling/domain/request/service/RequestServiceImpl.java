package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.dto.*;
import com.a702.feelingfilling.domain.request.model.repository.RequestCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{

	@Autowired
	private RequestCustomRepository requestCustomRepository;
	
	private final String[] emotion = new String[]{"anger","joy","sadness"};
	private final String[] weekday = new String[]{"없음","일","월","화","수","목","금","토"};
	
	@Override
	public UserStat[] getUserThisMonth(Integer userId) {

		List<UserStat> stats = requestCustomRepository.getUserThisMonth(userId);
		
		UserStat[] thisMonth = new UserStat[3];
		int i = 0;
		
		for(int j = 0;j<3;j++){
			if(i< stats.size() && stats.get(i).getEmotion().equals(emotion[j])){
				System.out.println(stats.get(i));
				thisMonth[j] = stats.get(i);
				i++;
			}
			else{
				thisMonth[j] = new UserStat(emotion[j],0,0);
			}
		}
		
		return thisMonth;
	}
	
	public int getInd(int now, int m){
		return 5-(now-m+12)%12;
	}
	@Override
	public Month[][] getUserMonths(Integer userId) {

		List<Month> monthInterfaces = requestCustomRepository.getUserMonths(userId);

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
		for(Month m: monthInterfaces){
			while(i<3 && !m.getEmotion().equals(emotion[i]))i++;
			j = getInd(month,m.getMonth()%100);
			months[i][j].setEmotion(m.getEmotion());
			months[i][j].setAmount(m.getAmount());
		}
		
		return months;
	}
	
	@Override
	public EmotionHigh getEmotionHigh(Integer userId) {

		Integer date = requestCustomRepository.getHighDateWithUserId(userId);
		Integer hour = requestCustomRepository.getHighHourWithUserId(userId);
		Integer day = requestCustomRepository.getHighDayWithUserId(userId);
		
		return EmotionHigh.builder()
				.date(date==null?0:date)
				.hour(hour==null?-1:hour)
				.day(weekday[day==null?0:day])
				.build();
	}
	
	@Override
	public int getUserTotal(Integer userId) {

		return requestCustomRepository.getUserTotal(userId);
	}
	
	
	/***************
	 	전체 통계
	 **************/
	
	@Override
	public Stat[] getThisMonth() {

		List<Stat> stats = requestCustomRepository.getThisMonth();
		
		Stat[] thisMonth = new Stat[3];
		int i = 0;
		
		for(int j = 0;j<3;j++){
			if(i< stats.size() && stats.get(i).getEmotion().equals(emotion[j])){
				System.out.println(stats.get(i));
				thisMonth[j] = stats.get(i);
				i++;
			}
			else{
				thisMonth[j] = new Stat(emotion[j],0);
			}
		}
		
		return thisMonth;
	}
	
	@Override
	public Yesterday[][] getYesterday() {

		List<Yesterday> yesterdayInterfaces = requestCustomRepository.getYesterday();
		
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
		
		for(Yesterday y: yesterdayInterfaces){
			while(i<3 && !y.getEmotion().equals(emotion[i]))i++;
			yesterday[i][y.getHour()] = y;
		}
		
		return yesterday;
	}
	
	@Override
	public EmotionKing getEmotionKing() {
		EmotionKing emotionKing = requestCustomRepository.getEmotionKing();
		
		return emotionKing == null ? EmotionKing.builder().amount(0).count(0).build() : emotionKing;
	}
	
	@Override
	public Stat[] getTotal() {

		List<Stat> stats = requestCustomRepository.getTotal();
		
		Stat[] total = new Stat[3];
		int i = 0;
		
		for(int j = 0;j<3;j++){
			if(i< stats.size() && stats.get(i).getEmotion().equals(emotion[j])){
				total[j] = stats.get(i);
				i++;
			}
			else{
				total[j] = new Stat(emotion[j],0);
			}
		}
		
		return total;
	}
}
