package com.example.billing.controller;

import com.example.billing.data.dto.ProcessResultDTO;
import com.example.billing.data.dto.ServiceUserAndAmountDTO;
import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.WithdrawalDTO;
import com.example.billing.exception.AmountInvalidException;
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
        map.put("result", true);
        map.put("message", "조회에 성공하였습니다.");
        map.put("amount", amount);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String,Object>> withdrawPoint(@RequestBody ServiceUserAndAmountDTO serviceUserAndAmountDTO){
        WithdrawalDTO withdrawalDTO = pointService.withdrawPoint(serviceUserAndAmountDTO);

        if(serviceUserAndAmountDTO.getAmount() < 1) throw new AmountInvalidException();

        Map<String, Object> map = new HashMap<>();

        ProcessResultDTO processResultDTO = new ProcessResultDTO(true, "출금에 성공하였습니다.");
        map.put("result", withdrawalDTO.isSuccess());
        map.put("message", withdrawalDTO.getMessage());
        map.put("amount", withdrawalDTO.getRequestedAmount());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
