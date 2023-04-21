package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import java.util.List;

public interface ChattingService {
  ChattingDTO createChat(ChatInputDTO chatInputDTO);
  void removeChat(int chattingId);

  List<ChattingDTO> getChatList();
}
