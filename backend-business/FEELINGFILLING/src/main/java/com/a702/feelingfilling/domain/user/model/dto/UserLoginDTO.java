package com.a702.feelingfilling.domain.user.model.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UserLoginDTO {
  private long id;
  private String nickname;
  private String role;
}
