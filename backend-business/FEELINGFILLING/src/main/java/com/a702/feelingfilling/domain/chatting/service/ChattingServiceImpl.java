package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.repository.ChattingRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChattingServiceImpl implements ChattingService {

  private final ChattingRepository chattingRepository;

  //1.채팅 입력
  @Override
  public ChattingDTO createChat(ChatInputDTO chatInputDTO) {
    try{
      Chatting newChat = Chatting.builder()
//        .user()    JWT 파싱 후 Security Context Holder에서 꺼내읽기
          .type(chatInputDTO.getType())
          .content(chatInputDTO.getContent())
          .build();
      chattingRepository.save(newChat);
      return
    }catch (Exception e){
      throw e;
    }
  }//newChat

  //2.채팅 삭제
  public void removeChat(int chattingId) {
    try{
      chattingRepository.deleteById(chattingId);
    }catch (Exception e){
      throw e;
    }
  }//removeChat

  //3.채팅 목록 조회
  public List<ChattingDTO> getChatList(){



  }
}//ChattingServiceImpl
