package com.a702.feelingfilling.domain.user.model.dto;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UserJoinDTO {
    String nickname;
    int minimum;
    int maximum;
    String kakaoId;

    public static User toUser(UserJoinDTO userJoinDTO) {
        return User.builder()
                .nickname(userJoinDTO.getNickname())
                .idOAuth2(userJoinDTO.getKakaoId())
                .role("ROLE_USER")
                .minimum(userJoinDTO.getMinimum())
                .maximum(userJoinDTO.getMaximum())
                .join_date(LocalDateTime.now())
                .build();
    }
}
