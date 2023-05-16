package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.exception.CustomException;
import com.a702.feelingfilling.domain.chatting.model.dto.AnalyzedResult;
import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import com.a702.feelingfilling.domain.chatting.repository.ChattingRepository;
import com.a702.feelingfilling.domain.chatting.repository.SenderRepository;
import com.a702.feelingfilling.domain.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bytebuddy.asm.Advice.Local;
import net.minidev.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChattingServiceImpl implements ChattingService {
  private final SenderRepository senderRepository;
  private final MongoTemplate mongoTemplate;
  private final ChattingRepository chattingRepository;
  private final UserService userService;

  //1.채팅 입력
  @Override
  public ChattingDTO createChat(ChatInputDTO chatInputDTO) {
    try{
      int userId = userService.getLoginUserId();
      //먼저 마지막 날짜 비교
      addDate(userId);
      Chatting newChat = Chatting.builder()
          .type(chatInputDTO.getType())
          .content(chatInputDTO.getContent())
          .chatDate(LocalDateTime.now())
          .mood("default")
          .amount(0)
          .userId(userId)
          .isAnalysed(false)
          .build();
      log.info("---------------");
      chattingRepository.save(newChat);
      log.info("newChat : "+newChat.toString());
      //채팅을 사용자 리스트에 추가
      Query query = Query.query(Criteria.where("_id").is(userId));
      Update update = new Update();
      update.addToSet("chattings", newChat);
      mongoTemplate.updateMulti(query,update,Sender.class);
      //sender값에 채팅 갯수 업데이트하기
      update = new Update();
      update.inc("numOfChat");
      mongoTemplate.updateFirst(query,update,Sender.class);
      update = new Update();
      update.inc("numOfUnAnalysed");
      mongoTemplate.updateFirst(query,update,Sender.class);
      //---------------------------------------------------
      return ChattingDTO.fromEntity(newChat);
    }catch (Exception e){
      throw e;
    }
  }//newChat

  //2.채팅 삭제
  public void removeChat(ObjectId chattingId) {
    try{
      mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(userService.getLoginUserId())),
          new Update().pull("chattings", chattingId),
          Sender.class);
      chattingRepository.deleteById(chattingId);
    }catch (Exception e){
      throw e;
    }
  }//removeChat

  int PAGE_SIZE = 20;
  //3.채팅 목록 조회
  public List<ChattingDTO> getChatList(int page){
    log.info("page : " + page);
    if(page<=0) throw new RuntimeException("Page는 1이상입니다");

    int loginUserId = userService.getLoginUserId();
    Sender senderWithNum = senderRepository.findChatNumBySenderId(loginUserId);
    int totalNum = senderWithNum.getNumOfChat(); //총 채팅 개수
    log.info("채팅 Total Num : " + totalNum);
    if((page-1)*PAGE_SIZE>=totalNum) throw new RuntimeException("채팅이 없습니다.");
    int start = PAGE_SIZE*page;
    int targetNum = PAGE_SIZE;
    if((page-1)*PAGE_SIZE<totalNum&&page*PAGE_SIZE>totalNum) {
      start = totalNum;
      targetNum = totalNum-(page-1)*PAGE_SIZE;
    }
    Sender sender = senderRepository.findAllBySenderIdAndPage(loginUserId, -start, targetNum);
    List<Chatting> chattings = sender.getChattings();
    log.info(chattings.toString());
    List<ChattingDTO> result = chattings.stream()
        .map(m-> ChattingDTO.fromEntity(m))
        .collect(Collectors.toList());
    return result;
  }

  //4.텍스트 분석
  @Override
  public ChattingDTO analyze(String accessToken) {
    Query query;
    Update update;
    String article;
    int loginUserId;
    try{
      loginUserId = userService.getLoginUserId();
      Sender senderWithNum = senderRepository.findAnalyNumBySenderId(loginUserId);
      int num = senderWithNum.getNumOfUnAnalysed();
      log.info("분석 대상 채팅 수 : " + num);
      Sender sender = senderRepository.findAllBySenderIdAndPage(loginUserId, -num, num);
      StringBuilder sb = new StringBuilder();

      //분석안된 갯수 초기화해주기
      query = Query.query(Criteria.where("_id").is(loginUserId));
      update = new Update();
      update.set("numOfUnAnalysed",0);
      mongoTemplate.updateFirst(query,update,Sender.class);

      for(Chatting text : sender.getChattings()){
        if(text.isAnalysed()==true) continue;
        //DB정보 바꿔주기
        query = Query.query(Criteria.where("_id").is(text.getChattingId()));
        update = new Update();
        update.set("isAnalysed", true);
        mongoTemplate.updateFirst(query,update,Chatting.class);
        if(text.getType()==2 || text.getType()==3) continue;
        //전송할 값에 더하기
        sb.append(text.getContent());
        sb.append(" ");
      }
      article = sb.toString();
      log.info("article : " + article);
    }catch (Exception e){
      throw new RuntimeException("분석할 text가 없습니다.");
    }
    Chatting newChat = null;
    try{
      //Send Request to Django Server
      RestTemplate template = new RestTemplate();
      String uri = UriComponentsBuilder.fromHttpUrl("https://feelingfilling.store/feelings/text").toUriString();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("Authorization", accessToken);
      JSONObject body = new JSONObject();
      body.put("text",article);
      HttpEntity<?> entity = new HttpEntity<>(body.toString(), headers);
      log.info("요청보내기");
      ResponseEntity<Map> response = template.exchange(
          uri,
          HttpMethod.POST,
          entity,
          Map.class);
      Map<String,Object> responseBody = response.getBody();
      log.info(responseBody.toString());
      AnalyzedResult res = AnalyzedResult.resultMap(responseBody);
      if(!res.isSuccess()) throw new CustomException("fail");
      //응답 채팅 데이터에 저장하기
      addDate(loginUserId);
      newChat = Chatting.builder()
          .type(2)
          .content(res.getReact())
          .chatDate(LocalDateTime.now())
          .mood(res.getEmotion())
          .amount(res.getAmount())
          .userId(loginUserId)
          .isAnalysed(true)
          .build();
    }
    catch (Exception e){
      log.info(e.getMessage());
      newChat = Chatting.builder()
          .type(4)
          .content("감정 분석 실패")
          .chatDate(LocalDateTime.now())
          .mood("fail")
          .amount(0)
          .userId(loginUserId)
          .isAnalysed(true)
          .build();
    }
    finally {
      updateInfo(newChat,loginUserId);
      resetAnalyze(loginUserId);
      return ChattingDTO.fromEntity(newChat);
    }
  }

  @Override
  public ChattingDTO voiceInput(){
    try{
      int loginUserId = userService.getLoginUserId();
      addDate(loginUserId);
      Chatting voice;
      voice = Chatting.builder()
          .type(1)
          .content("음성 입력")
          .chatDate(LocalDateTime.now())
          .mood("voice")
          .amount(0)
          .userId(loginUserId)
          .isAnalysed(true)
          .build();
      updateInfo(voice,loginUserId);
      return ChattingDTO.fromEntity(voice);
    }catch (Exception e){
      throw new RuntimeException("음성 저장 실패");
    }
  }
  //음성 분석 결과 반영하기
  @Override
  public ChattingDTO voice(AnalyzedResult res) {
    try{
      int loginUserId = userService.getLoginUserId();
      Chatting newChat;
      if(!res.isSuccess()){
        newChat = Chatting.builder()
            .type(4)
            .content("음성 분석 실패")
            .chatDate(LocalDateTime.now())
            .mood("fail")
            .amount(0)
            .userId(loginUserId)
            .isAnalysed(true)
            .build();
      }else{
        newChat = Chatting.builder()
            .type(2)
            .content(res.getReact())
            .chatDate(LocalDateTime.now())
            .mood(res.getEmotion())
            .amount(res.getAmount())
            .userId(loginUserId)
            .isAnalysed(true)
            .build();
      }
      updateInfo(newChat,loginUserId);
      log.info("음성 분석 값 저장 완료");
      resetAnalyze(loginUserId);
      return ChattingDTO.fromEntity(newChat);
    }catch (Exception e){
      log.info(e.getMessage());
      throw e;
    }
  }

  //채팅 생성해서 추가할때 정보 업데이트 메서드
  public void updateInfo(Chatting newChat, int loginUserId){
    chattingRepository.save(newChat);
    log.info("newChat : "+newChat.toString());
    //채팅을 사용자 리스트에 추가
    Query query = Query.query(Criteria.where("_id").is(loginUserId));
    Update update = new Update();
    update.addToSet("chattings", newChat);
    mongoTemplate.updateMulti(query,update,Sender.class);
    //sender값에 채팅 갯수 업데이트하기
    update = new Update();
    update.inc("numOfChat");
    mongoTemplate.updateFirst(query,update,Sender.class);
  }


  //메세지 날짜 변경 시 추가 메서드
  public void addDate(int loginUserId){
    Sender senderWithDate = senderRepository.findLastDateBySenderId(loginUserId);
    LocalDate lastDate = senderWithDate.getLastDate(); //마지막날
    if(lastDate.equals(LocalDate.now())) return;
    LocalDate today = LocalDate.now();
    log.info("날짜 변경 데이터 저장 : " + today.toString());
    Chatting newChat = Chatting.builder()
        .type(3)
        .content(today.toString())
        .chatDate(LocalDateTime.now())
        .mood("default")
        .amount(0)
        .userId(loginUserId)
        .isAnalysed(false)
        .build();
    log.info("newChat : "+newChat.toString());
    //채팅을 사용자 리스트에 추가
    updateInfo(newChat, loginUserId);
    Query query = Query.query(Criteria.where("_id").is(loginUserId));
    Update update = new Update();
    update = new Update();
    update.inc("numOfUnAnalysed");
    mongoTemplate.updateFirst(query,update,Sender.class);
    update = new Update();
    update.set("lastDate", today);
    mongoTemplate.updateFirst(query,update,Sender.class);
  }
  void resetAnalyze(int loginUserId){
    Query query = Query.query(Criteria.where("_id").is(loginUserId));
    Update update = new Update();
    update.set("numOfUnAnalysed",0);
    mongoTemplate.updateFirst(query,update,Sender.class);
  }
}//ChattingServiceImpl
