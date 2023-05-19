package com.a702.feelingfilling.domain.history.service;

import com.a702.feelingfilling.domain.history.model.dto.*;
import com.a702.feelingfilling.domain.history.model.repository.StatCustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StatServiceImpl implements StatService {

	@Autowired
	private StatCustomRepository statCustomRepository;
	
	private final String[] emotion = new String[]{"anger","joy","sadness"};
	private final String[] weekday = new String[]{"없음","일","월","화","수","목","금","토"};
	
	@Override
	public UserStat[] getUserThisMonth(Integer userId) {
		log.info("이번 달 저금량 조회 : "+userId);
		List<UserStat> stats = statCustomRepository.getUserThisMonth(userId);
		
		UserStat[] thisMonth = new UserStat[3];
		int i = 0;
		
		for(int j = 0;j<3;j++){
			if(i< stats.size() && stats.get(i).getEmotion().equals(emotion[j])){
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
		log.info("최근 6개월 저금 추이 조회 : "+userId);
		List<Month> monthInterfaces = statCustomRepository.getUserMonths(userId);

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
		log.info("이번 달 감정 최고조 조회 : "+userId);
		Integer date = statCustomRepository.getHighDateWithUserId(userId);
		Integer hour = statCustomRepository.getHighHourWithUserId(userId);
		Integer day = statCustomRepository.getHighDayWithUserId(userId);
		
		return EmotionHigh.builder()
				.date(date==null?0:date)
				.hour(hour==null?-1:hour)
				.day(weekday[day==null?0:day])
				.build();
	}
	
	@Override
	public int getUserTotal(Integer userId) {

		return statCustomRepository.getUserTotal(userId);
	}
	
	
	/***************
	 	전체 통계
	 **************/
	
	@Override
	public Stat[] getThisMonth() {
		log.info("전체 회원의 이번 달 저금량 조회");
		List<Stat> stats = statCustomRepository.getThisMonth();
		
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
		log.info("전체 회원의 전날 저금 추이");
		List<Yesterday> yesterdayInterfaces = statCustomRepository.getYesterday();
		
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
		log.info("이번 달 감정왕 조회");
		EmotionKing emotionKing = statCustomRepository.getEmotionKing();
		
		return emotionKing == null ? EmotionKing.builder().amount(0).count(0).build() : emotionKing;
	}
	
	@Override
	public Stat[] getTotal() {
		log.info("전체 회원의 저금량 조회");
		List<Stat> stats = statCustomRepository.getTotal();
		
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
