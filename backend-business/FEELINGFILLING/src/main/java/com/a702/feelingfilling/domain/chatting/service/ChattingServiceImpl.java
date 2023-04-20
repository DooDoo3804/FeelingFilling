package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import org.springframework.stereotype.Service;

@Service
public class ChattingServiceImpl implements ChattingService{

  @Override
  public void createChat(ChatInputDTO chatInputDTO) {
    Chatting newChat = Chatting.builder()
//        .user()    JWT 파싱 후 Security Context Holder에서 꺼내읽기
        .type(chatInputDTO.getType())
        .content(chatInputDTO.getContent())
        .build();
  }
}
