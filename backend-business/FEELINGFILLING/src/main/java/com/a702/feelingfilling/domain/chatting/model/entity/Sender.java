package com.a702.feelingfilling.domain.chatting.model.entity;

import java.math.BigInteger;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "senders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
@DynamicInsert
public class Sender {
  @MongoId(FieldType.INT32)
  private int senderId;
  @DBRef
  private List<Chatting> chattings;
  private long numOfChat;
}
