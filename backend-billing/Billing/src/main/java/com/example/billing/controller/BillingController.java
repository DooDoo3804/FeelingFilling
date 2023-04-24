package com.example.billing.controller;

import com.example.billing.data.dto.KakaoApproveDTO;
import com.example.billing.data.dto.KakaoReadyDTO;
import com.example.billing.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", kakaoReadyDTO.getNext_redirect_pc_url());
        System.out.println(kakaoReadyDTO);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
//        map.put("kakao", kakaoReadyDTO);
//        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/subscription/success")
    public ResponseEntity<Map<String,Object>> approveSubscription(String pg_token){
        System.out.println("들어옴 + "+pg_token);
        KakaoApproveDTO kakaoApproveDTO= kakaoPayService.kakaoPayApprove(pg_token);
        Map<String, Object> map = new HashMap<>();

        map.put("kakao", kakaoApproveDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
