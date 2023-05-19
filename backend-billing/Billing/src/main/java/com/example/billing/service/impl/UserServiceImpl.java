package com.example.billing.service.impl;

import com.example.billing.data.billingDB.entity.User;
import com.example.billing.data.billingDB.repository.UserRepository;
import com.example.billing.data.dto.UserDTO;
import com.example.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(String serviceName, int serviceUserId) {
        UserDTO userDTO = getUserByServiceUserId(serviceName, serviceUserId);
        if(userDTO == null){
            User user = User.builder()
                    .serviceName(serviceName)
                    .serviceUserId(serviceUserId)
                    .build();
            userDTO = new UserDTO(userRepository.save(user));
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserByServiceUserId(String serviceName, int serviceUserId) {
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceName, serviceUserId);
        try{
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
