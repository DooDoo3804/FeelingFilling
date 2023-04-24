package com.a702.feelingfilling.global.oauth;


import com.a702.feelingfilling.domain.user.model.dto.UserOAuth2DTO;
import com.a702.feelingfilling.domain.user.model.entity.User;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.global.jwt.JwtTokenService;
import com.a702.feelingfilling.global.jwt.JwtTokens;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
  private final JwtTokenService jwtTokenService;
  @Autowired
  private UserRepository userRepository;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    UserOAuth2DTO userOAuth2DTO = UserOAuth2DTO.toDTO(oAuth2User);
    log.info("현재 로그인된 사용자 정보 : {}", userOAuth2DTO.toString());
    //가입한 회원의 회원번호(식별을 위한 고유값)
    String idOAuth2 = userOAuth2DTO.getIdOAuth2();

    // 최초 로그인일때 회원가입 처리 할거야
    User userEntity = userRepository.findByIdOAuth2(idOAuth2);
    int id;
    String role;
    String nickname;
    if (userEntity == null) {
      role = "ROLE_USER";
      log.info("최초 로그인 입니다");
      //회원가입시키자

    } else {
      log.info("기존 회원입니다.");
      id = userEntity.getUserId();
      role = userEntity.getRole();
      nickname = userEntity.getNickname();
      //토큰발급
      JwtTokens jwtTokens = jwtTokenService.generateToken(id, nickname, role);
      log.info("발급된 jwtToken : " + "{}", jwtTokens);

      //Response에 토큰 넣기
      writeTokenResponse(response, jwtTokens);
      RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
      String refreshToken = jwtTokens.getRefreshToken();
      String targetUri = "" + "?auth="
          + jwtTokens.getAccessToken() + "&refresh=" + refreshToken;
      log.info("redirect_uri : {}", targetUri);
      redirectStrategy.sendRedirect(request, response, targetUri);
      log.info("Redirect 완료");
    }
  }

  private void writeTokenResponse(HttpServletResponse response, JwtTokens token)
      throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    response.addHeader("Auth", token.getAccessToken());
    response.addHeader("Refresh", token.getRefreshToken());
    response.setContentType("application/json;charset=UTF-8");
    log.info("Response에 담긴 jwtToken : " + "{}", token);
  }
}

