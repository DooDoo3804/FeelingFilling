package com.example.billing.controller;

import com.example.billing.data.dto.*;
import com.example.billing.exception.AmountInvalidException;
import com.example.billing.service.KakaoPayService;
import com.example.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/billing")
public class BillingController {
    private final KakaoPayService kakaoPayService;

    private final UserService userService;

    @PostMapping("/subscription/active")
    public ResponseEntity<Map<String, Object>> startSubscription(@RequestBody ServiceUserDTO serviceUserDTO) {
        UserDTO userDTO = userService.createUser(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        KakaoReadyDTO kakaoReadyDTO = kakaoPayService.kakaoPayReady(userDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("url", kakaoReadyDTO.getNext_redirect_pc_url());
        return new ResponseEntity<>(map, HttpStatus.FOUND);
    }

    @GetMapping("/subscription/success")
    public ResponseEntity<Map<String, Object>> approveSubscription(int orderId, String pg_token) {
        KakaoApproveDTO kakaoApproveDTO = kakaoPayService.kakaoPayApprove(orderId, pg_token);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://success"));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/subscription/fail")
    public ResponseEntity<Map<String, Object>> failSubscription(int orderId) {
        kakaoPayService.subscriptionFail(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://fail"));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/subscription/cancel")
    public ResponseEntity<Map<String, Object>> cancelSubscription(int orderId) {
        kakaoPayService.subscriptionCancel(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://cancel"));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    @PostMapping("/subscription")
    public ResponseEntity<Map<String, Object>> paySubscription(@RequestBody ServiceUserAndAmountDTO serviceUserAndAmountDTO) {

        if(serviceUserAndAmountDTO.getAmount() < 1) throw new AmountInvalidException();

        kakaoPayService.kakaoPaySubscription(serviceUserAndAmountDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("message", "입금에 성공하였습니다.");
        map.put("amount", serviceUserAndAmountDTO.getAmount());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/subscription/inactive")
    public ResponseEntity<Map<String, Object>> subscriptionInactivate(@RequestBody ServiceUserDTO serviceUserDTO) {
        KakaoInactiveDTO kakaoInactiveDTO = kakaoPayService.kakaoPayInactivate(serviceUserDTO);

        Map<String, Object> map = new HashMap<>();
        if (kakaoInactiveDTO.getStatus().equals("INACTIVE")) {
            map.put("result", true);
            map.put("message", "정기 결제가 취소되었습니다.");
        } else {
            map.put("result", false);
            map.put("message", "정기 결제 취소 실패");
        }
        map.put("createdAt", kakaoInactiveDTO.getCreated_at());
        map.put("inactivatedAt", kakaoInactiveDTO.getInactivated_at());
        map.put("lastApprovedAt", kakaoInactiveDTO.getLast_approved_at());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/subscription/status")
    public ResponseEntity<Map<String, Object>> subscriptionStatus(@RequestBody ServiceUserDTO serviceUserDTO) {
        KakaoPayCheckDTO kakaoPayCheckDTO = kakaoPayService.kakaoPayCheck(serviceUserDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("available", kakaoPayCheckDTO.isAvailable());
        map.put("status", kakaoPayCheckDTO.getStatus());
        map.put("createdAt", kakaoPayCheckDTO.getCreated_at());
        map.put("inactivatedAt", kakaoPayCheckDTO.getInactivated_at());
        map.put("lastApprovedAt", kakaoPayCheckDTO.getLast_approved_at());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Map<String, Object>> cancelPayment(@RequestBody CancelDepositDTO cancelDepositDTO){
        if(cancelDepositDTO.getAmount() < 1) throw new AmountInvalidException();

        KakaoCancelDTO kakaoCancelDTO = kakaoPayService.kakaoPayCancel(cancelDepositDTO);


        Map<String, Object> map = new HashMap<>();
        if (kakaoCancelDTO.getStatus().equals("PART_CANCEL_PAYMENT")||kakaoCancelDTO.getStatus().equals(("CANCEL_PAYMENT"))) {
            map.put("result", true);
            map.put("message", "결제가 취소되었습니다.");
        } else {
            map.put("result", false);
            map.put("message", "결제 취소 실패");
        }
        map.put("approvedCancelAmount", kakaoCancelDTO.getApproved_cancel_amount().getTotal());
        map.put("createdAt", kakaoCancelDTO.getCanceled_at());
        map.put("approvedAt", kakaoCancelDTO.getApproved_at());
        map.put("canceledAt", kakaoCancelDTO.getCanceled_at());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
