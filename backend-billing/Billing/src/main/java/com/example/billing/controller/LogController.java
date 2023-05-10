package com.example.billing.controller;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController("/logs")
public class LogController {

    private final LoggingService loggingService;
    @PostMapping("/user/subscription")
    public ResponseEntity<Map<String,Object>> getSubscriptionLogsByUser(ServiceUserDTO serviceUserDTO){
        loggingService.findSubscriptionLogsByUser(serviceUserDTO);
    }

}
