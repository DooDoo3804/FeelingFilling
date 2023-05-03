package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;

import java.util.List;

public interface UserService {
  void join(UserJoinDTO userJoinDTO);
//  UserDTO getUser();
//  UserDTO modifyUser(UserDTO userDTO);
//  boolean[] getUserBadge();
  UserDTO getUser(int userId);
  UserDTO modifyUser(UserDTO userDTO);
  List<Integer> getUserBadge(int userId);
}
