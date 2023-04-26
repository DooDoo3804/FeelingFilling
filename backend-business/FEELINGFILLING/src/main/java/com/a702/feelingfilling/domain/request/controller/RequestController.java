package com.a702.feelingfilling.domain.request.controller;

import com.a702.feelingfilling.domain.request.model.dto.EmotionHigh;
import com.a702.feelingfilling.domain.request.model.dto.Month;
import com.a702.feelingfilling.domain.request.model.dto.Stat;
import com.a702.feelingfilling.domain.request.model.dto.Yesterday;
import com.a702.feelingfilling.domain.request.service.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/stat")
@Api(tags = {"통계 API"})
@Slf4j
public class RequestController {
	
	public static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";
	private static final int burger = 6500;
	private static final int coffee = 4500;
	
	@Autowired
	private RequestService requestService;
	
	@ApiOperation(value = "유저 통계", notes = "유저 통계 API", response = Map.class)
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserStat(@PathVariable Integer userId){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
			//이번 달 저금
			List<Stat> stats = requestService.getUserThisMonth(userId);
			resultMap.put("userThisMonth",stats);
			logger.debug("사용자 이번 달 저금 : ", stats);
			
			//월별 추이
			List<Month> months = requestService.getUserMonths(userId);
			resultMap.put("userMonths",months);
			logger.debug("사용자 월별 추이", months);
			
			//이번 달 감정 최고조
			EmotionHigh emotionHigh = requestService.getEmotionHigh(userId);
			resultMap.put("emotionHigh",emotionHigh);
			logger.debug("사용자 이번 달 감정 최고 : ", emotionHigh);
			
			//저금 누적액
			int userTotal = requestService.getUserTotal(userId);
			resultMap.put("total",userTotal);
			resultMap.put("coffee", userTotal/coffee);
			resultMap.put("burger",userTotal/burger);
			logger.debug("사용자 적금 누적액 : ", userTotal);
			logger.debug("사용자 적금 누적액/커피값 : ", userTotal/coffee);
			logger.debug("사용자 적금 누적액/버거값 : ", userTotal/burger);
			
			resultMap.put("message", SUCCESS);
			
		}
		catch (Exception e){
			status = HttpStatus.BAD_REQUEST;
			resultMap.put("message", FAIL);
			logger.error("유저 통계 에러 : {} ",e);
		}
		
		return new ResponseEntity<>(resultMap,status);
	}	@ApiOperation(value = "전체 통계", notes = "전체 통계 API", response = Map.class)
	@GetMapping("/all")
	public ResponseEntity<?> getTotalStat(){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
			//이번 달 저금
			List<Stat> stats = requestService.getThisMonth();
			resultMap.put("totalThisMonth", stats);
			logger.debug("전체 사용자 이번 달 저금 : ", stats);
			
			//전날 추이
			List<Yesterday> yesterday = requestService.getYesterday();
			resultMap.put("yesterday",yesterday);
			logger.debug("전날 추이 : ", yesterday);
			
			//이번 달 감정왕
			Stat emotionKing = requestService.getEmotionKing();
			resultMap.put("emotionKing", emotionKing);
			logger.debug("이번 달 감정왕 : ", emotionKing);
			
			//전체 사용자 누적 적금액
			List<Stat> total = requestService.getTotal();
			resultMap.put("total", total);
			logger.debug("전체 사용자 누적 적금액 : ", total);
			
			resultMap.put("message", SUCCESS);
			
		}
		catch (Exception e){
			status = HttpStatus.BAD_REQUEST;
			resultMap.put("message", FAIL);
			logger.error("전체 사용자 통계 에러 : {} ",e);
		}
		
		return new ResponseEntity<>(resultMap,status);
	}
}
