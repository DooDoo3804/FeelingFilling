package com.example.billing.controller;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.WithdrawalDTO;
import com.example.billing.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;
    @PostMapping("/")
    public ResponseEntity<Map<String,Object>> getPoint(@RequestBody ServiceUserDTO serviceUserDTO){
        long amount = pointService.getPoint(serviceUserDTO);
        Map<String, Object> map = new HashMap<>();

        map.put("amount", amount);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String,Object>> withdrawPoint(@RequestBody ServiceUserDTO serviceUserDTO, @RequestParam long amount){
        WithdrawalDTO withdrawalDTO = pointService.withdrawPoint(serviceUserDTO, amount);

        Map<String, Object> map = new HashMap<>();

        map.put("withdrawal", withdrawalDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
