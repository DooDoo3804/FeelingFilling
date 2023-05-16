package com.example.billing.controller;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.TimePeriodDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.InactiveLogDocumnet;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import com.example.billing.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/logs")
public class LogController {

    private final LoggingService loggingService;
    @PostMapping("/user/subscription")
    public ResponseEntity<Map<String,Object>> getSubscriptionLogsByUser(@RequestBody ServiceUserDTO serviceUserDTO){
        List<KakaoPayApproveLogDocument> logs = loggingService.findSubscriptionLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/deposit")
    public ResponseEntity<Map<String,Object>> getDepositLogsByUser(@RequestBody ServiceUserDTO serviceUserDTO){
        List<DepositLogDocument> logs = loggingService.findDepositLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/withdrawal")
    public ResponseEntity<Map<String,Object>> getWithdrawalLogsByUser(@RequestBody ServiceUserDTO serviceUserDTO){
        List<WithdrawalLogDocument> logs = loggingService.findWithdrawalLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/user/inactive")
    public ResponseEntity<Map<String,Object>> getInactiveLogsByUser(@RequestBody ServiceUserDTO serviceUserDTO){
        List<InactiveLogDocumnet> logs = loggingService.findInactiveLogsByUser(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/period/subscription")
    public ResponseEntity<Map<String,Object>> getSubscriptionLogsByPeriod(@RequestBody TimePeriodDTO timePeriodDTO){
        List<KakaoPayApproveLogDocument> logs = loggingService.findSubscriptionLogsByPeriod(timePeriodDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/period/deposit")
    public ResponseEntity<Map<String,Object>> getDepositLogsByPeriod(@RequestBody TimePeriodDTO timePeriodDTO){
        List<DepositLogDocument> logs = loggingService.findDepositLogsByPeriod(timePeriodDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/period/withdrawal")
    public ResponseEntity<Map<String,Object>> getWithdrawalLogsByPeriod(@RequestBody TimePeriodDTO timePeriodDTO){
        List<WithdrawalLogDocument> logs = loggingService.findWithdrawalLogsByPeriod(timePeriodDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/period/inactive")
    public ResponseEntity<Map<String,Object>> getInactiveLogsByPeriod(@RequestBody TimePeriodDTO timePeriodDTO){
        List<InactiveLogDocumnet> logs = loggingService.findInactiveLogsByPeriod(timePeriodDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("log", logs);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
