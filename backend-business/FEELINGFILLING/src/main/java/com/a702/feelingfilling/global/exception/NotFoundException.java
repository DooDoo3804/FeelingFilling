package com.a702.feelingfilling.global.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }
}
