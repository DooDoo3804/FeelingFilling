package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.chatting.exception.CustomException;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import com.a702.feelingfilling.domain.chatting.repository.SenderRepository;
import com.a702.feelingfilling.domain.user.model.dto.*;
import com.a702.feelingfilling.domain.user.model.entity.User;
import com.a702.feelingfilling.domain.user.model.entity.UserBadge;
import com.a702.feelingfilling.domain.user.model.repository.UserBadgeRepository;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.jwt.JwtTokens;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;
    private final SenderRepository senderRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserKakaoResponseDTO kakaoLogin(UserKakaoRequestDTO kakaoDTO) {
        Optional<User> user = userRepository.findByIdOAuth2(kakaoDTO.getId());
        // 이미 가입한 유저
        if (user.isPresent()) {
            User realUser = user.get();
            log.info("기존 회원 입니다 : " + realUser.toString());
            JwtTokens jwtTokens = jwtTokenService.generateToken(realUser.getUserId(), realUser.getNickname(), realUser.getRole());
            return UserKakaoResponseDTO.builder().accessToken(jwtTokens.getAccessToken()).refreshToken(jwtTokens.getRefreshToken()).isNewJoin(false).build();
        } else {
            // 새로운 유저
            return UserKakaoResponseDTO.builder().isNewJoin(true).build();
        }
    }

    @Override
    public JwtTokens join(UserJoinDTO userJoinDTO) {
        int max = userJoinDTO.getMaximum();
        int min = userJoinDTO.getMinimum();
        if (min < 0) throw new IllegalArgumentException("Unvalid Minimum Value");
        if (max < min) throw new IllegalArgumentException("Unvalid Maximum Value");
        if(userRepository.findByIdOAuth2(userJoinDTO.getKakaoId()).isPresent()){
            throw new IllegalArgumentException("기존 회원입니다.");
        }
        User user = UserJoinDTO.toUser(userJoinDTO);
        userRepository.save(user);
        log.info("DB user 저장완료 : " + user.toString());
        Sender newSender = Sender.builder().senderId(user.getUserId()).chattings(new ArrayList<>())
            .numOfChat(0)
            .numOfUnAnalysed(0)
            .lastDate(LocalDate.now().minusDays(1))
            .build();
        senderRepository.save(newSender);
        log.info("sender저장");
        return jwtTokenService.generateToken(user.getUserId(), user.getNickname(), user.getRole());
    }

    @Override
    public int getLoginUserId() {
        UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getId();
    }
    @Override
    public UserDTO getUser(){
		    UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    User userEntity = userRepository.findByUserId(loginUser.getId());
        boolean isBilled;
        try{
            isBilled = getBillStatus(loginUser.getId());
        }
        catch(Exception e){
            isBilled = false;
        }
        return UserDTO.toDTO(userEntity, isBilled);
    }

    public boolean getBillStatus(int loginUserId){
        log.info("결제등록 여부 조회");
        RestTemplate template = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl("http://13.125.237.195:8702/billing/subscription/status").toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", accessToken);
        JSONObject body = new JSONObject();
        body.put("serviceName","FeelingFilling");
        body.put("serviceUserId",loginUserId);
        HttpEntity<?> entity = new HttpEntity<>(body.toString(), headers);
        log.info("요청보내기");
        ResponseEntity<Map> response = template.exchange(
            uri,
            HttpMethod.POST,
            entity,
            Map.class);
        Map<String,Object> responseBody = response.getBody();
        log.info(responseBody.toString());
        boolean isBilled = (boolean) responseBody.get("available");
        return isBilled;
    }
    @Override
    public UserDTO modifyUser(UserDTO userDTO) {
        int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        User user = userRepository.findByUserId(userId);

        user.setMaximum(userDTO.getMaximum());
        user.setMinimum(userDTO.getMinimum());
        user.setNickname(userDTO.getNickname());

        return UserDTO.toDTO(userRepository.save(user),getBillStatus(userId));
    }
    
    @Override
    public int deleteUser() {
        int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    User user = userRepository.findByUserId(userId);
        if(user==null)
            return -1;
        userRepository.delete(user);
        return userId;
    }
    
    @Override
    public List<Integer> getUserBadge() {
        int userId = ((UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<Integer> badge = new ArrayList<>();
        List<UserBadge> badges = userBadgeRepository.findByUser_UserIdOrderByAchievedDateDesc(userId);

        for (UserBadge userBadge : badges) {
            badge.add(userBadge.getBadgeId());
        }

        return badge;
    }
    
    @Override
    public List<UserBriefDTO> getUserListForAdmin() {
        List<UserBriefDTO> userDTOList = userRepository.findAll().stream().map(UserBriefDTO::toDTO).collect(Collectors.toList());
        return userDTOList;
    }
    
    @Override
    public UserDetailDTO getUserForAdmin(Integer userId) {
        User userEntity = userRepository.findByUserId(userId);
        
        return UserDetailDTO.toDTO(userEntity);
    }
    
    @Override
    public boolean deleteUserForAdmin(Integer userId) {
        User user = userRepository.findByUserId(userId);
        if(user==null)
            return false;
        userRepository.delete(user);
        return true;
    }

    @Override
    public String registerBill(){
        try{
            int loginUserId = getLoginUserId();
            RestTemplate template = new RestTemplate();
            String uri = UriComponentsBuilder.fromHttpUrl("http://13.125.237.195:8702/billing/subscription/active").toUriString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", accessToken);
            JSONObject body = new JSONObject();
            body.put("serviceName","FeelingFilling");
            body.put("serviceUserId",loginUserId);
            HttpEntity<?> entity = new HttpEntity<>(body.toString(), headers);
            log.info("요청보내기");
            ResponseEntity<Map> response = template.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Map.class);
            Map<String,Object> responseBody = response.getBody();
            log.info(responseBody.toString());
            return responseBody.get("url").toString();
        }catch (Exception e){
            throw new CustomException("결제 등록 요청 실패");
        }

    }
}
