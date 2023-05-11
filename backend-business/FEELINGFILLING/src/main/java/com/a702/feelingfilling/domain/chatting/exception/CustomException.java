package com.a702.feelingfilling.domain.chatting.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
  public CustomException(String message){
    super(message);
  }
}




