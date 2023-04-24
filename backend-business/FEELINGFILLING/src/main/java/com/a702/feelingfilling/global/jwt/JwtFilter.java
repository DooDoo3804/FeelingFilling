package com.a702.feelingfilling.global.jwt;

import com.a702.feelingfilling.domain.user.model.dto.UserLoginDTO;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtTokenService jwtTokenService;
  private final UserRepository userRepository;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String accessToken = request.getHeader("Auth");
    log.info("AccessToken from request header : " + accessToken);

    if (accessToken == null) {
      //토큰이 없으면
      log.info("토큰이 없습니다.");
      chain.doFilter(request, response);
      //로그인 페이지로 넘어갈거임
    } else {
      //토큰이 있으면 검증을 시도
      try {
        jwtTokenService.verifyToken(accessToken);
        //토큰 검증이 통과했다면
        log.info("유효한 토큰입니다.");
        if (jwtTokenService.getType(accessToken).equals("access")) {
          //토큰이 엑세스 토큰인지 먼저 확인한 후,
          //토큰에서 지금 접속한 사용자 정보 꺼내기 시도
          long id = jwtTokenService.getUserId(accessToken);
          String nickName = jwtTokenService.getUserNickName(accessToken);
          String role = jwtTokenService.getUserRole(
              accessToken); //토큰에 role을 담았는데, role이 변하는 기능을 넣으면 DB에서 조회한 값을 넣는게 맞는듯
          //유저정보 찾기에 성공하면 내려가고 실패하면 Exception 발생
          //객체에 담아서
          UserLoginDTO userLoginDTO = UserLoginDTO.builder()
              .id(id)
              .nickname(nickName)
              .role(role)
              .build();
          log.info("현재 사용자의 ID : {}, nickName : {}, role : {}", id, nickName, role);
          //SecurityContext에 권한과 함께 저장
          Authentication auth = getAuthentication(userLoginDTO, userLoginDTO.getRole());
          SecurityContextHolder.getContext().setAuthentication(auth);
          log.info("인증 권한 부여 완료");
          //인증 권한 받아서 다음 필터로 넘어가기 >> 프론트에서는 원하는 응답 잘 받을 수 있음.
          chain.doFilter(request, response);
        }else{
          //엑세스 토큰이 아닌 경우에는
          log.warn("not-access-token");
          setErrorResponse(response, "wrong_token");
        }
      } catch (ExpiredJwtException e) {
        //만료시에는 response에 401코드에 "expired"메세지
        //다음단계로 넘어가지 않아서 리다이렉트가 일어나지 않음
        setErrorResponse(response, "expired");
      } catch (JwtException e) {
        //토큰 검증에 실패했을때
        log.warn("wrong_token");
        setErrorResponse(response, "wrong_token");
      } catch (Exception e) {
        //사용자 정보 꺼내기에 실패한경우 >> secret key는 맞는데 토큰의 내용 형식이 이상한 경우
        log.warn("wrong_token");
        setErrorResponse(response, "wrong_token");
        //response에 401코드에 "wrong_token"메세지
      }
    }
  }

  public Authentication getAuthentication(UserLoginDTO member, String role) {
    return new UsernamePasswordAuthenticationToken(member, "",
        Arrays.asList(new SimpleGrantedAuthority(role)));
  }

  public void setErrorResponse(HttpServletResponse response, String message) throws IOException {
    response.setContentType("application/json; charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401에러

    //프론트에서 response message를 읽어서 분류해야함
    JSONObject responseJson = new JSONObject();
    responseJson.put("message", message);
    response.getWriter().print(responseJson);
  }
}
