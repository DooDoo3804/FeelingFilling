package com.example.billing.controller;

import com.example.billing.data.dto.*;
import com.example.billing.service.KakaoPayService;
import com.example.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/billing")
public class BillingController {
    private final KakaoPayService kakaoPayService;

    private final UserService userService;

    @PostMapping("/subscription/active")
    public ResponseEntity<Map<String,Object>> startSubscription(@RequestBody ServiceUserDTO serviceUserDTO){
        UserDTO userDTO = userService.createUser(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        KakaoReadyDTO kakaoReadyDTO= kakaoPayService.kakaoPayReady(userDTO);
        Map<String, Object> map = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", kakaoReadyDTO.getNext_redirect_pc_url());
        System.out.println(kakaoReadyDTO);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/subscription/success")
    public ResponseEntity<Map<String,Object>> approveSubscription(int orderId,String pg_token){
        KakaoApproveDTO kakaoApproveDTO= kakaoPayService.kakaoPayApprove(orderId, pg_token);
        Map<String, Object> map = new HashMap<>();

        map.put("kakao", kakaoApproveDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/subscription")
    public ResponseEntity<Map<String, Object>> paySubscription(@RequestBody PaySubscriptionDTO paySubscriptionDTO){
       kakaoPayService.kakaoPaySubscription(paySubscriptionDTO);

       return new ResponseEntity<>(HttpStatus.OK);
    }
}
