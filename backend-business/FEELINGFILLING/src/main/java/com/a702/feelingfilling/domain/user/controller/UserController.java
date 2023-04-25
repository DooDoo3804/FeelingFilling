package com.a702.feelingfilling.domain.user.controller;

import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	public UserService userService;

	@PostMapping
	public ResponseEntity<?> join(@RequestBody UserJoinDTO userJoinDTO){
		log.info("회원가입 요청 : " + userJoinDTO);
		Map<String, Object> resultMap;
		try{
			resultMap = new HashMap<>();
			userService.join(userJoinDTO);
			resultMap.put("message", "SUCCESS");
			return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
		}catch (Exception e){
			resultMap = new HashMap<>();
			resultMap.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(resultMap);
		}
	}


}
