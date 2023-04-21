package com.a702.feelingfilling.domain.chatting.controller;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.service.ChattingService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  //1. 채팅입력
  @PostMapping
  public ResponseEntity<?> addChat(@RequestBody ChatInputDTO chatInputDTO){
    log.info("chatting 생성 : type - " +chatInputDTO.getType()+", content - "+chatInputDTO.getContent());
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("chattings",chattingService.createChat(chatInputDTO));
      return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
    }
  }//addChat

  //2. 채팅 삭제
  @DeleteMapping
  public ResponseEntity<?> deleteChat(@PathVariable int chattingId){
    log.info("chatting 삭제 : Id - " +chattingId);
    Map<String,Object> resultMap = new HashMap<>();
    try{
      chattingService.removeChat(chattingId);
      resultMap.put("message","SUCCESS");
      return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
    }
  }//addChat

  //3.채팅 목록 조회
  @PostMapping
  public ResponseEntity<?> getChatList(){
    log.info("chatting 목록조회");
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("chattings",chattingService.getChatList());
      return ResponseEntity.ok().body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    }
  }//addChat
}
