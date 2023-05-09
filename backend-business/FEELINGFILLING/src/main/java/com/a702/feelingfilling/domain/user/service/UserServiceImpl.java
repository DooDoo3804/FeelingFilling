package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import com.a702.feelingfilling.domain.chatting.repository.SenderRepository;
import com.a702.feelingfilling.domain.user.model.dto.*;
import com.a702.feelingfilling.domain.user.model.entity.User;
import com.a702.feelingfilling.domain.user.model.entity.UserBadge;
import com.a702.feelingfilling.domain.user.model.repository.UserBadgeRepository;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.jwt.JwtTokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;
    private final SenderRepository senderRepository;
    private final JwtTokenService jwtTokenService;
    private int badgeCnt = 15;

    @Override
    public UserKakaoResponseDTO kakaoLogin(UserKakaoRequestDTO kakaoDTO) {
        Optional<User> user = userRepository.findByIdOAuth2(kakaoDTO.getId());
        // 이미 가입한 유저
        if (user.isPresent()) {
            User realUser = user.get();
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
        User user = UserJoinDTO.toUser(userJoinDTO);
        userRepository.save(user);
        log.info("DB user 저장완료 : " + user.toString());
        senderRepository.save(Sender.builder().senderId(user.getUserId()).chattings(new ArrayList<>()).build());
        return jwtTokenService.generateToken(user.getUserId(), user.getNickname(), user.getRole());
    }

    @Override
    public int getLoginUserId() {
        UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getId();
    }

    //	public UserDTO getUser(){
//		UserLoginDTO loginUser = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User userEntity = userRepository.findByUserId(loginUser.getId());
    public UserDTO getUser(int userId) {
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

        for (UserBadge userBadge : badges) {
            badge.add(userBadge.getBadgeId());
        }

        return badge;
    }

}
