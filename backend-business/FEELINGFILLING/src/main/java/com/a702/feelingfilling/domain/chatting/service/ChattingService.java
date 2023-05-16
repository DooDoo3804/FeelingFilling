package com.a702.feelingfilling.domain.chatting.service;

import com.a702.feelingfilling.domain.chatting.model.dto.AnalyzedResult;
import com.a702.feelingfilling.domain.chatting.model.dto.ChatInputDTO;
import com.a702.feelingfilling.domain.chatting.model.dto.ChattingDTO;
import java.util.List;
import org.bson.types.ObjectId;

public interface ChattingService {
  ChattingDTO createChat(ChatInputDTO chatInputDTO);
  void removeChat(ObjectId chattingId);
  List<ChattingDTO> getChatList(int page);
  ChattingDTO analyze(String accessToken);

  ChattingDTO voice(AnalyzedResult analyzedResult);
  ChattingDTO voiceInput();
}
