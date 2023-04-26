package com.a702.feelingfilling.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UserJoinDTO {
  int userId;
  String  nickname;
  int minimum;
  int maximum;

}
