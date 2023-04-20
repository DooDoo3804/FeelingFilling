package com.a702.feelingfilling.global.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDefault {
  private String messege;
  private Object data;
}
