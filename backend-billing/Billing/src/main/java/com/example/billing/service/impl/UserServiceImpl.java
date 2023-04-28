package com.example.billing.service.impl;

import com.example.billing.data.billingDB.entity.User;
import com.example.billing.data.billingDB.repository.UserRepository;
import com.example.billing.data.dto.UserDTO;
import com.example.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(String serviceName, int serviceUserId) {
        User user = User.builder()
                .serviceName(serviceName)
                .serviceUserId(serviceUserId)
                .build();
        UserDTO userDTO = new UserDTO(userRepository.save(user));
        return userDTO;
    }

    @Override
    public UserDTO getUserByServiceUserId(String serviceName, int serviceUserId) {

        return null;
    }

    @Override
    public UserDTO getUserByUserId(int userId) {
        return null;
    }

    @Override
    public void updateUser(UserDTO user) {

    }
}
