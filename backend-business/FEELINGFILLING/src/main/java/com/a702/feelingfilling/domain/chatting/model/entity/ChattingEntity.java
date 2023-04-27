package com.a702.feelingfilling.domain.chatting.model.entity;

import com.a702.feelingfilling.domain.user.model.entity.User;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
@Document(collection = "chatting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
@DynamicInsert
public class ChattingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chatting_id")
  int chattingId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  User user;
  String content;
  @Column(name = "chat_date")
  @NotNull
  @CreationTimestamp
  LocalDateTime chatDate;
  @NotNull
  @ColumnDefault("0")
  int type;
}
