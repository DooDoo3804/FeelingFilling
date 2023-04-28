package com.a702.feelingfilling.domain.chatting.model.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
@DynamicInsert
public class Chatting {
  @Id
  ObjectId chattingId;
  @NotNull
  @ColumnDefault("0")
  int type;
  String content;
  LocalDateTime chatDate;
}
