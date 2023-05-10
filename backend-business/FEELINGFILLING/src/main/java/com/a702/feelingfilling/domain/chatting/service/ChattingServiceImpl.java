package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.AnalyzedResult;
import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import com.a702.feelingfilling.domain.chatting.repository.ChattingRepository;
import com.a702.feelingfilling.domain.chatting.repository.SenderRepository;
import com.a702.feelingfilling.domain.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    int loginUserId = userService.getLoginUserId();
//    int loginUserId = 2;
    log.info("-----------------------");
    int start = PAGE_SIZE*page;
    Sender sender = senderRepository.findAllBySenderIdAndPage(loginUserId, -start, PAGE_SIZE);
    List<Chatting> chattings = sender.getChattings();
    log.info(chattings.toString());
    List<ChattingDTO> result = chattings.stream()
        .map(m-> ChattingDTO.fromEntity(m))
        .collect(Collectors.toList());
    return result;
  }

  //4.텍스트 분석
  @Override
  public AnalyzedResult analyze() {
    int loginUserId = userService.getLoginUserId();
    Sender senderWithNum = senderRepository.findBySenderId(loginUserId);
    int num = senderWithNum.getNumOfUnAnalysed();
    Sender sender = senderRepository.findAllBySenderIdAndPage(loginUserId, -num, num);
    StringBuilder sb = new StringBuilder();
    for(Chatting text : sender.getChattings()){
      sb.append(text.getContent());
      sb.append(" ");
    }
    String article = sb.toString();
    log.info("article : " + article);


    //분석안된 갯수 초기화해주기
    Query query = Query.query(Criteria.where("_id").is(loginUserId));
    Update update = new Update();
    update.set("numOfUnAnalysed",0);
    mongoTemplate.updateFirst(query,update,Sender.class);


    //Send Request to Django Server
    RestTemplate template = new RestTemplate();
    String uri = UriComponentsBuilder.fromHttpUrl("https://feelingfilling.store/feelings/text").toUriString();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
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
    //응답 채팅 데이터에 저장하기


    //--------------------------

    return AnalyzedResult.resultMap(responseBody);
  }
}//ChattingServiceImpl
