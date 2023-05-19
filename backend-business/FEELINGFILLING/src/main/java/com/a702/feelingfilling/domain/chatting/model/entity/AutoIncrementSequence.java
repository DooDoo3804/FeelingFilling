package com.a702.feelingfilling.domain.chatting.model.entity;

import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "auto_sequence")
public class AutoIncrementSequence {

  @Id
  private String id;
  private Long seq;
}