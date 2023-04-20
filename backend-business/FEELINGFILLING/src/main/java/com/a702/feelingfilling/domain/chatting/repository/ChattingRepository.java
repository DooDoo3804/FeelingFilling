package com.a702.feelingfilling.domain.chatting.repository;

import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Integer> {

}
