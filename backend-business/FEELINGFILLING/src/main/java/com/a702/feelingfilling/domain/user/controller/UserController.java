package com.a702.feelingfilling.domain.user.controller;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.service.UserService;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(tags = {"회원 관리 API"})
public class UserController {
	@Autowired
	public UserService userService;
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXIST = "already exists";

	//1. 로그인
	@GetMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest request){
		Map<String, Object> resultMap;
		try{
			resultMap = new HashMap<>();
			String jwt = request.getHeader("Authorization");
			log.info("JWT from Kakao : " + jwt);
			userService.login(jwt);
			resultMap.put("message", "SUCCESS");
			return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
		}catch (Exception e){
			resultMap = new HashMap<>();
			resultMap.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(resultMap);
		}
	}//login

	//2. 회원가입
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
	}//join

	//3. 정보조회
	@GetMapping("/{userId}")
//	@PreAuthorize("hasRole('ROLE_AMDIN') or hasRole('ROLE_USER')" )
	public ResponseEntity<?> getUser(@PathVariable int userId){
		log.info("회원 정보 조회 요청");
		Map<String, Object> resultMap;
		try{
			resultMap = new HashMap<>();
//			resultMap.put("user", userService.getUser());
			resultMap.put("user", userService.getUser(userId));
			resultMap.put("message", "SUCCESS");
			return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
		}catch (Exception e){
			resultMap = new HashMap<>();
			resultMap.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(resultMap);
		}
	}
	
	//4. 정보수정
	@PutMapping
//	@PreAuthorize("hasRole('ROLE_AMDIN') or hasRole('ROLE_USER')" )
	public ResponseEntity<?> modifyUser(UserDTO userDTO){
		log.info("회원 정보 수정 요청");
		Map<String, Object> resultMap = new HashMap<>();
		try{
			resultMap.put("user", userService.modifyUser(userDTO));
			resultMap.put("message", "SUCCESS");
			return ResponseEntity.status(HttpStatus.OK).body(resultMap);
		}catch (Exception e){
			resultMap.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(resultMap);
		}
	}
	
	//5. 뱃지 조회
	@GetMapping("/badge/{userId}")
//	@PreAuthorize(("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')"))
	public ResponseEntity<?> getUserBadge(@PathVariable int userId){
		log.info("회원 뱃지 조회 요청");
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<>();
		try{
			List<Integer> badges = userService.getUserBadge(userId);
			resultMap.put("badges",badges);
			log.debug("유저 뱃지 조회 ,",badges);
			resultMap.put("message", SUCCESS);
		}
		catch (Exception e){
			log.error("유저 뱃지 조회 에러 : {}",e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
}
