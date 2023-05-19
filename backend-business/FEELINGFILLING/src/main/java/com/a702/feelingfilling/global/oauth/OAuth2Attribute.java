package com.a702.feelingfilling.global.oauth;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
  private Map<String, Object> attributes;
  private String attributeKey;
  private String name;
  private String picture;
  private String idOnly;

  public static OAuth2Attribute selectDomain(String provider, String attributeKey,
      Map<String, Object> attributes) {
    switch (provider) {
      case "google":
        return ofGoogle(attributeKey, attributes);
      case "kakao":
        return ofKakao("id", attributes);
      case "naver":
        return ofNaver("id", attributes);
      default:
        throw new RuntimeException();
    }
  }

  private static OAuth2Attribute ofGoogle(String attributeKey,
      Map<String, Object> attributes) {
    log.info("Google 로그인");
    return OAuth2Attribute.builder()
        .name((String) attributes.get("name"))
//        .email((String) attributes.get("email"))
        .picture((String)attributes.get("picture"))
        .idOnly("Google" + attributes.get("sub"))
        .attributes(attributes)
        .attributeKey(attributeKey)
        .build();
  }

  private static OAuth2Attribute ofKakao(String attributeKey,
      Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
    log.info("KaKao 로그인");

    return OAuth2Attribute.builder()
        .name((String) kakaoProfile.get("nickname"))
        .picture((String)kakaoProfile.get("profile_image_url"))
        .idOnly("Kakao" + attributes.get("id"))
        .attributes(kakaoAccount)
        .attributeKey(attributeKey)
        .build();
  }

  private static OAuth2Attribute ofNaver(String attributeKey,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    log.info("Naver 로그인");
    return OAuth2Attribute.builder()
        .name((String) response.get("name"))
        .picture((String) response.get("profile_image"))
        .idOnly("Naver" + response.get("id"))
        .attributes(response)
        .attributeKey(attributeKey)
        .build();
  }

  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("idOnly", idOnly);
    map.put("key", attributeKey);
    map.put("name", name);
//    map.put("email", email);
    map.put("picture", picture);
    return map;
  }
}
