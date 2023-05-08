package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserKakaoRequestDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserKakaoResponseDTO;
import com.a702.feelingfilling.global.jwt.JwtTokens;

import java.util.List;

public interface UserService {
    UserKakaoResponseDTO kakaoLogin(UserKakaoRequestDTO kakaoDTO);

    JwtTokens join(UserJoinDTO userJoinDTO);

    int getLoginUserId();

    UserDTO getUser(int userId);

    UserDTO modifyUser(UserDTO userDTO);

    List<Integer> getUserBadge(int userId);

}
