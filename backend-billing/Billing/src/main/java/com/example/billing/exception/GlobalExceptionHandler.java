package com.example.billing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저입니다. 먼저 정기 결제 신청을 해 주세요.");
    }

    @ExceptionHandler(WrongDateFormatException.class)
    public ResponseEntity<String> handleWrongDateFormatException(WrongDateFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("시간 형식이 맞지 않습니다. yyyy-MM-dd HH:mm:ss 형식으로 요청해주세요.");
    }

    @ExceptionHandler(AmountInvalidException.class)
    public ResponseEntity<String> handleAmountInvalidException(AmountInvalidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 금액은 최소 1원 이상이어야 합니다.");
    }
}
