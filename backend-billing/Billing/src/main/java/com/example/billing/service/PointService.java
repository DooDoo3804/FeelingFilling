package com.example.billing.service;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.WithdrawalDTO;

public interface PointService {
    public WithdrawalDTO withdrawPoint(ServiceUserDTO serviceUserDTO, long amount);

    public long getPoint(ServiceUserDTO serviceUserDTO);
}
