package com.a702.feelingfilling.domain.history.service;

import com.a702.feelingfilling.domain.history.model.dto.LogDTO;
import com.a702.feelingfilling.domain.history.model.entity.History;
import com.a702.feelingfilling.domain.history.model.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private HistoryRepository historyRepository;
	
	private final String[] emotion = new String[]{"anger","joy","sadness"};
	private final String[] weekday = new String[]{"없음","일","월","화","수","목","금","토"};
	
	@Override
	public List<LogDTO> getUserMonthLog(Integer userId,int year, int month) {
		LocalDateTime start = LocalDateTime.of(year,month,1,0,0,0);
		LocalDateTime end = LocalDateTime.of(year,month+1,1,0,0,0).minusSeconds(1);
		
		
		List<History> historyList = historyRepository.findByUser_userIdAndRequestTimeBetweenOrderByRequestTimeDesc(userId, start,end);
		
		List<LogDTO> logs = new ArrayList<LogDTO>();
		historyList.forEach(x -> logs.add(LogDTO.toDTO(x)));
		
		return logs;
	}
}
