package com.a702.feelingfilling.domain.user.controller;

import com.a702.feelingfilling.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	public UserService userService;
	
	
}
