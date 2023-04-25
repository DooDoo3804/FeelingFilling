package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;
import com.a702.feelingfilling.domain.user.model.entity.User;
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
	@Override
	public void join(UserJoinDTO userJoinDTO){
		User userEntity = userRepository.findByUserId(userJoinDTO.getUserId());
		if(userEntity == null) throw new IllegalArgumentException("KAKAO ID is not connected");
		int max = userJoinDTO.getMaximum();
		int min = userJoinDTO.getMinimum();
		if(min<0) throw new IllegalArgumentException("Unvalid Minimum Value");
		if(max<min) throw new IllegalArgumentException("Unvalid Maximum Value");
		userEntity.updateRange(userJoinDTO.getMaximum(), userJoinDTO.getMinimum());
		userEntity.updateJoinDate();
		userEntity.updateRole("ROLE_USER");
		userRepository.save(userEntity);
	}
}
