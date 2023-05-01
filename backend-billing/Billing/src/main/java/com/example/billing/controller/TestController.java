package com.example.billing.controller;

import com.example.billing.data.loggingDB.entity.TestDocument;
import com.example.billing.data.loggingDB.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<TestDocument> tests = testRepository.findAll();
        return new ResponseEntity<List<TestDocument>>( tests, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> put() {
        TestDocument test = new TestDocument();
        test.setId("3");
        test.setTest("tttest");
        testRepository.save(test);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
