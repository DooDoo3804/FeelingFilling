package com.example.billing.service;

import com.example.billing.data.dto.UserDTO;

public interface UserService {
    public UserDTO createUser(String serviceName, int serviceUserId);

    public UserDTO getUserByServiceUserId(String serviceName, int serviceUserId);
}
