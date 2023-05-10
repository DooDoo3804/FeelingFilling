package com.a702.feelingfilling.domain.chatting.repository;

import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Chatting;
import com.a702.feelingfilling.domain.chatting.model.entity.Sender;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends MongoRepository<Sender, Integer> {

  @Query(value = "{'senderId':?0}", fields = "{'chattings':{'$slice':  [?1 , ?2] } }")
  Sender findAllBySenderIdAndPage(int senderId, Integer start, Integer num);

  @Query(value = "{'senderId':?0}", fields = "{'numOfUnAnalysed' :  1}")
  Sender findBySenderId(int loginUserId);

//  @Query(value = "{'senderId':?0}", fields = "{'$slice': ['chatting', '?1', '?2'] }")
//  List<Object> findAllBySenderIdAndPage(int senderId, Integer start, Integer num);
//    @Query("db.sender.aggregate([{ '$project' : { 'chattings': { '$slice': [ '$chattings', 3, 3 ] } } } ])")
//    List<Object> findAllBySenderIdAndPage(int senderId, Integer start, Integer num);
}
