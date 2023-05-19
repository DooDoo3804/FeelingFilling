package com.example.billing.service;

import com.example.billing.data.dto.ServiceUserAndAmountDTO;
import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.WithdrawalDTO;

public interface PointService {
    public WithdrawalDTO withdrawPoint(ServiceUserAndAmountDTO serviceUserAndAmountDTO);

    public long getPoint(ServiceUserDTO serviceUserDTO);
}
