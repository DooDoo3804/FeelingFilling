package com.a702.feelingfilling.domain.request.controller;

import com.a702.feelingfilling.domain.request.model.dto.EmotionHigh;
import com.a702.feelingfilling.domain.request.model.dto.Stat;
import com.a702.feelingfilling.domain.request.service.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	
	@Autowired
	RequestService requestService;
	
	@ApiOperation(value = "유저 통계", notes = "유저 통계 API", response = Map.class)
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserStat(@PathVariable Integer userId){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
			
			EmotionHigh emotionHigh = requestService.getEmotionHigh(userId);
			resultMap.put("emotionHigh",emotionHigh);
			logger.debug("사용자 이번 달 감정 최고 : ", emotionHigh);
			
			resultMap.put("message", SUCCESS);
			
		}
		catch (Exception e){
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("이번 달 감정 최고 조회 실패 : {} ",e);
		}
		
		return new ResponseEntity<>(resultMap,status);
	}
}
