package com.a702.feelingfilling.domain.chatting.controller;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.service.ChattingService;
import com.a702.feelingfilling.global.response.ResponseDefault;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("chatting")
@Slf4j
public class ChattingController {
  private final ChattingService chattingService;
  @PostMapping
  public ResponseEntity<?> addChat(@RequestBody ChatInputDTO chatInputDTO){
    log.info("chatting 생성 : type - " +chatInputDTO.getType()+", content - "+chatInputDTO.getContent() );
    ResponseDefault responseDefault;
    try{
      chattingService.createChat(chatInputDTO);
      responseDefault = ResponseDefault.builder()
          .messege("SUCCESS")
          .build();
      return ResponseEntity.ok().body(responseDefault);
    }catch (Exception e){
      responseDefault = ResponseDefault.builder()
          .messege(e.getMessage())
          .build();
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }//addChat
}
