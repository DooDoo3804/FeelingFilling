package com.a702.feelingfilling.domain.request.service;

import com.a702.feelingfilling.domain.request.model.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
	RequestRepository requestRepository;
	
}
