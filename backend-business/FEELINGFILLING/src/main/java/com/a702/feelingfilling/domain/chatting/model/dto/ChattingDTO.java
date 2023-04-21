package com.a702.feelingfilling.domain.chatting.model.dto;


import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChattingDTO {
  int chattingId;
  String content;
  LocalDateTime chatDate;
  int type;
  String mood;
  int amount;

  ChattingDTO fromEntity(Chatting entity){
    int type = entity.getType();
    if(type == 2){
      return ChattingDTO.builder()
          .chattingId(entity.getChattingId())
          .content(entity.getContent())
          .chatDate(entity.getChatDate())
          .type(entity.getType())
          .mood(entity.get)
          .amount()
          .build();
    }
    else{
      return ChattingDTO.builder()
          .chattingId(entity.getChattingId())
          .content(entity.getContent())
          .chatDate(entity.getChatDate())
          .type(entity.getType())
          .build();
    }
  }
}
