package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.*;
import com.a702.feelingfilling.global.jwt.JwtTokens;

import java.util.List;

public interface UserService {
    UserKakaoResponseDTO kakaoLogin(UserKakaoRequestDTO kakaoDTO);

    JwtTokens join(UserJoinDTO userJoinDTO);

    int getLoginUserId();

    UserDTO getUser();

    UserDTO modifyUser(UserDTO userDTO);
    
//    boolean deleteUser();
    int deleteUser();

    List<Integer> getUserBadge();
    
    List<UserBriefDTO> getUserListForAdmin();
    UserDetailDTO getUserForAdmin(Integer userId);
    boolean deleteUserForAdmin(Integer userId);

    String registerBill();


}
