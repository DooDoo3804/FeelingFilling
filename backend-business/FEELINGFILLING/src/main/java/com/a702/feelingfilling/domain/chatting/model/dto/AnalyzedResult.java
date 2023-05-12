package com.a702.feelingfilling.domain.chatting.model.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyzedResult {
  private String react; //반응
  private String emotion; //감정
  private int amount; //금액
  private boolean success; //적립여부

  public static AnalyzedResult resultMap(Map response){
    return AnalyzedResult.builder()
        .react(response.get("react").toString())
        .emotion(response.get("emotion").toString())
        .amount((int) response.get("amount"))
        .success((boolean)response.get("success"))
        .build();
  }
}
