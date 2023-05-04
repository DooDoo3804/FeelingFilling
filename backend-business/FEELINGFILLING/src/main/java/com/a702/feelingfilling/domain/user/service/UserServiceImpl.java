package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserLoginDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.model.entity.User;
import com.a702.feelingfilling.domain.user.model.entity.UserBadge;
import com.a702.feelingfilling.domain.user.model.repository.UserBadgeRepository;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.jwt.JwtTokens;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserBadgeRepository userBadgeRepository;
	@Autowired
	UserRepository userRepository;

	JwtTokenService jwtTokenService;
	private int badgeCnt = 15;

	//1. 로그인
	@Override
	public void login(String jwt){
		try{
			RestTemplate template = new RestTemplate();
			String uri = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me").toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", jwt);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			log.info("요청보내기");
			ResponseEntity<JSONObject> response = template.exchange(
					uri,
					HttpMethod.GET,
					entity,
					JSONObject.class);
			log.info(response.getBody().toString());
			String kakaoId = "kakao" + response.getBody().get("id").toString();
			Map<String,Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");
			Map<String,String> kakaoProfile = (Map<String, String>) kakaoAccount.get("profile");

			// 최초 로그인일때 회원가입 처리 할거야
			int id;
			String role;
			String nickname;
			User userEntity = userRepository.findByIdOAuth2(kakaoId);
			if (userEntity != null && !userEntity.getRole().equals("ROLE_WAIT")) {
				log.info("기존 회원입니다.");
				id = userEntity.getUserId();
				role = userEntity.getRole();
				nickname = userEntity.getNickname();
				//토큰발급
				JwtTokens jwtTokens = jwtTokenService.generateToken(id, nickname, role);
				log.info("발급된 jwtToken : " + "{}", jwtTokens);
				//Response에 토큰 넣어 보내주기
			//--------------------------------------------------------

				log.info("Redirect 완료");
			} else if(userEntity != null && userEntity.getRole().equals("ROLE_WAIT")) {
				log.info("회원가입이 필요합니다.");
				//회원가입 페이지로 가야한다는 response
				//----------------------------------------------------------------
			} else {
				role = "ROLE_WAIT";
				nickname = kakaoProfile.get("nickname");
				log.info("최초 로그인 입니다");
				//회원가입시키자
				userEntity = User.builder()
						.idOAuth2(kakaoId)
						.role(role)
						.nickname(nickname)
						.join_date(LocalDateTime.now())
						.build();
				userRepository.save(userEntity);
				log.info("ROLE_WAIT 권한으로 임시 저장");
				//회원가입 페이지로 가야한다는 response
				//--------------------------------------------------------
			}
		}catch (Exception e){
			throw e;
		}
	}//login

	private void writeTokenResponse(HttpServletResponse response, JwtTokens token)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Auth", token.getAccessToken());
		response.addHeader("Refresh", token.getRefreshToken());
		response.setContentType("application/json;charset=UTF-8");
		log.info("Response에 담긴 jwtToken : " + "{}", token);
	}

	@Override
	public void join(UserJoinDTO userJoinDTO){
		User userEntity = userRepository.findByUserId(userJoinDTO.getUserId());
		if(userEntity == null) throw new IllegalArgumentException("KAKAO ID is not connected");
		int max = userJoinDTO.getMaximum();
		int min = userJoinDTO.getMinimum();
		if(min<0) throw new IllegalArgumentException("Unvalid Minimum Value");
		if(max<min) throw new IllegalArgumentException("Unvalid Maximum Value");
		userEntity.updateRange(userJoinDTO.getMaximum(), userJoinDTO.getMinimum());
		userEntity.updateJoinDate();
		userEntity.updateRole("ROLE_USER");
		userRepository.save(userEntity);
	}

	@Override
	public int getLoginUserId(){
		UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return loginUser.getId();
	}

//	public UserDTO getUser(){
//		UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User userEntity = userRepository.findByUserId(loginUser.getId());
	public UserDTO getUser(int userId){
		User userEntity = userRepository.findByUserId(userId);
		
		return UserDTO.toDTO(userEntity);
	}
	
	
	@Override
	public UserDTO modifyUser(UserDTO userDTO) {
		//int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		
		User user = userRepository.findByUserId(userDTO.getUserId());
		
		user.setMaximum(userDTO.getMaximum());
		user.setMinimum(userDTO.getMinimum());
		user.setNickname(userDTO.getNickname());
		
		return UserDTO.toDTO(userRepository.save(user));
	}
	
	@Override
	public List<Integer> getUserBadge(int userId) {
//	public List<Integer> getUserBadge() {
		//int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		List<Integer> badge = new ArrayList<>();
		List<UserBadge> badges = userBadgeRepository.findByUser_UserId(userId);
		
		for(UserBadge userBadge : badges){
			badge.add(userBadge.getBadgeId());
		}
		
		return badge;
	}

}
