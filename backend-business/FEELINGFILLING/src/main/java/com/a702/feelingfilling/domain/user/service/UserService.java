package com.a702.feelingfilling.domain.user.service;

import com.a702.feelingfilling.domain.user.model.dto.UserDTO;
import com.a702.feelingfilling.domain.user.model.dto.UserJoinDTO;

public interface UserService {
  void join(UserJoinDTO userJoinDTO);

  UserDTO getUser();
}
