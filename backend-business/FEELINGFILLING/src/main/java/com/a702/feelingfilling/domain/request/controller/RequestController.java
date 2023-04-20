package com.a702.feelingfilling.domain.request.controller;

import com.a702.feelingfilling.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
	@Autowired
	RequestService requestService;
	
}
