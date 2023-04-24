package com.a702.feelingfilling.global.jwt;

import com.a702.feelingfilling.domain.user.model.entity.User;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

  @Value("${jwt.secret}")
  private String secretKey; //jwt에 쓰일 secretkey
  private final UserRepository userRepository;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }//Base64로 인코딩

  //토큰 생성 메서드
  public String generateAccessJwt(long userId, String nickName, String role) {
    long tokenPeriod = 1000L * 60L * 1000L; //인증토큰의 만료시간 5분
    Claims claims = Jwts.claims();
    claims.put("role", role);
    claims.put("userId", userId);
    Date now = new Date();
    claims.put("nickName", nickName);
    claims.put("type", "access");
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenPeriod))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public String generateRefreshJwt(long userId, String nickName, String role) {
    long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L; //Refresh토큰 만료 3주
    Claims claims = Jwts.claims();
    claims.put("userId", userId);
    claims.put("role", role);
    Date now = new Date();
    claims.put("nickName", nickName);
    claims.put("type", "refresh");

    String refreshToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + refreshPeriod))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
    //DB에 리프레쉬 토큰 저장하기
//    User userTemp = userRepository.findById(userId);
//    userTemp.setRefreshToken(refreshToken);
//    userRepository.save(userTemp);
//    log.info("DB에 refresh 토큰 저장");
    return refreshToken;
  }


  public JwtTokens generateToken(long userId, String nickName, String role) {

    return new JwtTokens(
        generateAccessJwt(userId, nickName, role),
        generateRefreshJwt(userId, nickName, role));
  }

  //토큰 유효성 검증 메서드
  public boolean verifyToken(String token) {
    try {
      log.info("토큰 검증 시작");
      Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token).getBody();
      return true;
    } catch (ExpiredJwtException e) {
      log.warn("만료된 토큰 : {}", e.getMessage());
      throw e;
    } catch (JwtException e){
      log.warn("잘못된 토큰 : {}", e.getMessage());
      throw e;
    }
  }

  //토큰 refresh 메서드
  public JwtTokens refreshAccessToken(String refreshToken) {
    try{
      //리프레쉬 토큰에 담긴 사용자 정보
      long id = getUserId(refreshToken);
      log.info("요청한 사용자 id : {}",id);
//      User user = userRepository.findById(id);
//      String refreshTokenInDB = user.getRefreshToken();
//      if(!refreshToken.equals(refreshTokenInDB)){
//        log.warn("RefreshToken이 일치하지 않습니다.");
//        return null;
//      }
      String nickName = getUserNickName(refreshToken);
      String role = getUserRole(refreshToken);
      return generateToken(id, nickName, role);
    }
    catch (Exception e){
      throw e;
    }
  }

  public long getUserId(String token) {
    return Integer.parseInt(
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId")
            .toString());
  } //jwt토큰에서 userId 빼오는 메서드

  public String getUserNickName(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get("nickName").toString();
  } //jwt토큰에서 userNickname 빼오는 메서드

  public String getUserRole(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role")
        .toString();
  } //jwt토큰에서 role 빼오는 메서드

  public String getType(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get("type").toString();
  } //jwt토큰에서 type 빼오는 메서드
}
