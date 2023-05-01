package com.a702.feelingfilling.domain.chatting.repository;

import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends MongoRepository<Sender, Integer> {
}
