package com.a702.feelingfilling.global.jwt;

import com.a702.feelingfilling.domain.user.model.entity.UserToken;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import com.a702.feelingfilling.domain.user.model.redisRepository.UserTokenRedisRepository;
import com.a702.feelingfilling.global.exception.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService implements InitializingBean {

    @Value("${jwt.secret}")
    private String secretKey; //jwt에 쓰일 secretkey
    private final UserRepository userRepository;
    private final UserTokenRedisRepository userTokenRedisRepository;

    @Override
    public void afterPropertiesSet() { // secret 값을 decode 하여 key 값에 저장
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //토큰 생성 메서드
    public String generateAccessJwt(int userId, String nickName, String role) {
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

    public String generateRefreshJwt(int userId, String nickName, String role) {
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
        userTokenRedisRepository.save(UserToken.builder().userId(userId).token(refreshToken).build());
        log.info("Redis에 refresh 토큰 저장");
        return refreshToken;
    }

    public JwtTokens generateToken(int userId, String nickName, String role) {
        return new JwtTokens(
                generateAccessJwt(userId, nickName, role),
                generateRefreshJwt(userId, nickName, role));
    }

    //토큰 유효성 검증 메서드
    public boolean verifyToken(String token) {
        System.out.println("1");
        try {
            System.out.println("2");
            log.info("토큰 검증 시작");
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰 : {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            log.warn("잘못된 토큰 : {}", e.getMessage());
            throw e;
        }
    }

    //토큰 refresh 메서드
    public JwtTokens refreshAccessToken(String refreshToken) throws NotFoundException {
        try {
            //리프레쉬 토큰에 담긴 사용자 정보
            int id = getUserId(refreshToken);
            log.info("요청한 사용자 id : {}", id);
            UserToken userToken = userTokenRedisRepository.findById(String.valueOf(id)).orElseThrow(() -> new NotFoundException("user 정보를 찾을 수 없습니다."));
            String refreshTokenInDB = userToken.getToken();
            if (!refreshToken.equals(refreshTokenInDB)) {
                log.warn("RefreshToken이 일치하지 않습니다.");
                return null;
            }
            String nickName = getUserNickName(refreshToken);
            String role = getUserRole(refreshToken);
            return generateToken(id, nickName, role);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getUserId(String token) {
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
