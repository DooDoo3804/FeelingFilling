package com.example.billing.controller;

import com.example.billing.data.dto.KakaoReadyDTO;
import com.example.billing.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/billing")
public class BillingController {
    private final KakaoPayService kakaoPayService;
    @PostMapping("/subscription/active")
    public ResponseEntity<Map<String,Object>> startSubscription(){
        KakaoReadyDTO kakaoReadyDTO= kakaoPayService.kakaoPayReady();
        Map<String, Object> map = new HashMap<>();

        map.put("kakao", kakaoReadyDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
