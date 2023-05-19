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
public class UserOAuth2DTO {
  private long id;
  private String idOAuth2;
  private String name;
  private String img;
  private String role;

  public static UserOAuth2DTO toDTO(OAuth2User oAuth2User){
    Map attributes = oAuth2User.getAttributes();
    return UserOAuth2DTO.builder()
        .idOAuth2((String)attributes.get("idOnly"))
        .name((String)attributes.get("name"))
        .img((String)attributes.get("picture"))
        .build();
  }
}
