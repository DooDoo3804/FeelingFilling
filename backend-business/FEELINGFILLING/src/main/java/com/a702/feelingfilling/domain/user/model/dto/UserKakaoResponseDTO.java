package com.a702.feelingfilling.domain.user.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserKakaoResponseDTO {
    private String accessToken;
    private String refreshToken;

    private boolean isNewJoin;
}
