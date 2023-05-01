package com.a702.feelingfilling.domain.chatting.repository;

import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ChattingRepository extends MongoRepository<Chatting, ObjectId> {

}
