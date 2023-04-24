/*
 * 카카오에서 받은 데이터 후처리 함수. 엑세스 토큰 + 사용자 정보
 * 이친구는 SecurityConfig에서 호출되어서 반환값을 넘겨줌
 *
 * */

package com.a702.feelingfilling.global.oauth;

import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

    OAuth2Attribute oAuth2Attribute =
        OAuth2Attribute.selectDomain(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    log.info("oAuth2Attribute : " + "{}", oAuth2Attribute);

    Map memberAttribute = oAuth2Attribute.convertToMap(); //맵으로 변경된 통일된 값

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        memberAttribute, "idOnly");
  }
}
