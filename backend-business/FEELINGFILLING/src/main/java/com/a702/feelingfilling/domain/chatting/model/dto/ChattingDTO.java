package com.a702.feelingfilling.domain.chatting.model.dto;


import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChattingDTO {
  String chattingId;
  String content;
  LocalDateTime chatDate;
  int type;
  String mood;
  int amount;

  public static ChattingDTO fromEntity(Chatting entity){
      return ChattingDTO.builder()
          .chattingId(entity.getChattingId().toString())
          .content(entity.getContent())
          .chatDate(entity.getChatDate())
          .type(entity.getType())
          .mood(entity.getMood())
          .amount(entity.getAmount())
          .build();
  }

}
