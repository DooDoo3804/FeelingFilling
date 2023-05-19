package com.a702.feelingfilling.domain.history.service;

import com.a702.feelingfilling.domain.history.model.dto.*;

import java.util.List;

public interface LogService {
	//개인 통계
	List<LogDTO> getUserMonthLog(Integer userId, int year, int month);
	
}
