package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.repository.BadgeRepository;
import com.a702.feelingfilling.domain.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	BadgeRepository badgeRepository;
	
	@Autowired
	UserRepository userRepository;
}
