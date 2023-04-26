package com.a702.feelingfilling.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class JwtTokens {

  private String accessToken;
  private String refreshToken;

}
