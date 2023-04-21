package com.example.billing.controller;

import com.example.billing.data.billingDB.entity.Test;
import com.example.billing.data.billingDB.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestRepository testRepository;

    @Autowired
    public TestController(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @GetMapping("/go")
    public String test(){
        System.out.println("여기는?");
        return "성공";
    }
    @PostMapping("/")
    public String createTest(@RequestBody String name){
        System.out.println("왔다");
        Test test = new Test(name);

        testRepository.save(test);

        return "성공";
    }
}
