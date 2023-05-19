package com.a702.feelingfilling.domain.chatting.controller;

import com.a702.feelingfilling.domain.chatting.exception.CustomException;
import com.a702.feelingfilling.domain.chatting.model.dto.AnalyzedResult;
import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import com.a702.feelingfilling.domain.chatting.service.ChattingService;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatting")
@Slf4j
@Api(tags = {"채팅 API"})
public class ChattingController {
  private final ChattingService chattingService;
  //1. 채팅입력
  @PostMapping
  public ResponseEntity<?> addChat(@RequestBody ChatInputDTO chatInputDTO){
    log.info("chatting 생성 : type - " +chatInputDTO.getType()+", content - "+chatInputDTO.getContent());
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("chatting",chattingService.createChat(chatInputDTO));
      return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
    }
  }//addChat

  //2. 채팅 삭제
  @DeleteMapping
  public ResponseEntity<?> deleteChat(@RequestParam ObjectId chattingId){
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
  @GetMapping
  public ResponseEntity<?> getChatList(@RequestParam int page){
    log.info("chatting 목록조회");
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("chattings",chattingService.getChatList(page));
      return ResponseEntity.ok().body(resultMap);
    }
    catch (CustomException e){
      resultMap.put("message","SUCCESS");
      resultMap.put("chattings",new ArrayList<ChattingDTO>());
      return ResponseEntity.ok().body(resultMap);
    }
    catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    }
  }//addChat


  //4. 텍스트 감정 분석 요청
  @GetMapping("/analyze")
  public ResponseEntity<?> analyze(@RequestHeader(value = "Authorization") String accessToken){
    log.info("텍스트 분석요청");
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("chatting",chattingService.analyze(accessToken));
      return ResponseEntity.ok().body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    }
  }//addChat

  //5. 음성분석 결과 전달
  @PostMapping("/voice")
  public ResponseEntity<?> voice(@RequestBody AnalyzedResult analyzedResult){
    log.info("음성 분석 결과 수신 - " +analyzedResult);
    Map<String,Object> resultMap = new HashMap<>();
    try{
      ChattingDTO voice = chattingService.voiceInput();
      ChattingDTO result = chattingService.voice(analyzedResult);
      resultMap.put("voice", voice);
      resultMap.put("result", result);
      resultMap.put("message","SUCCESS");
      return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
    }
  }//addChat

  //6. 분석 존재 여부
  @GetMapping("/exist")
  public ResponseEntity<?> exist(){
    log.info("분석여부 조회");
    Map<String,Object> resultMap = new HashMap<>();
    try{
      resultMap.put("message","SUCCESS");
      resultMap.put("exist",chattingService.exist());
      return ResponseEntity.ok().body(resultMap);
    }catch (Exception e){
      resultMap.put("message",e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    }
  }//exist
}
