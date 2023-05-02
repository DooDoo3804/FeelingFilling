package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import com.a702.feelingfilling.domain.chatting.repository.ChattingRepository;
import com.a702.feelingfilling.domain.chatting.repository.SenderRepository;
import com.a702.feelingfilling.domain.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
//      int userId = userService.getLoginUserId();
      int userId = 2;
      Chatting newChat = Chatting.builder()
          .type(chatInputDTO.getType())
          .content(chatInputDTO.getContent())
          .chatDate(LocalDateTime.now())
          .mood("default")
          .amount(0)
          .userId(2)
          .build();
      chattingRepository.save(newChat);
      log.info("newChat : "+newChat.toString());
      //채팅을 사용자 리스트에 추가
      Update update = new Update();
      update.addToSet("chattings", newChat);
      mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(userId)),update,Sender.class);
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

  int PAGE_SIZE = 5;
  //3.채팅 목록 조회
  public List<ChattingDTO> getChatList(int page){
//    int userId = userService.getLoginUserId();
//    Query query = new Query();
//    query.addCriteria(Criteria.where("senderId").is(2));
//    ProjectionOperation project = Aggregation.project()
//        .and("chattings").slice(page, PAGE_SIZE).as("chattings");
//
//    Aggregation agg = Aggregation.newAggregation(project);
//    AggregationResults<Object> aggregate = mongoTemplate.aggregate(agg, "sender", Object.class);
////    Aggregation aggregation = Aggregation.newAggregation(
////        Aggregation.match(Criteria.where("_id").in),
////        Aggregation.bucket("chattings"),
////        Aggregation.skip(skip),
////        Aggregation.limit(PAGE_SIZE)
////    );
//    List<Object> chattings = aggregate.getMappedResults();
//    log.info(chattings.toString());
    log.info("-----------------------");
    List<Object> chattings2 = senderRepository.findAllBySenderIdAndPage(2, -1, 4);
    log.info(chattings2.toString());
    return null;
  }
}//ChattingServiceImpl
