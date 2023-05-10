package com.example.billing.service.impl;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import com.example.billing.data.loggingDB.repository.DepositLogRepository;
import com.example.billing.data.loggingDB.repository.KakaoPayApproveLogRepository;
import com.example.billing.data.loggingDB.repository.WithdrawalLogRepository;
import com.example.billing.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final KakaoPayApproveLogRepository kakaoPayApproveLogRepository;
    private final DepositLogRepository depositLogRepository;
    private final WithdrawalLogRepository withdrawalLogRepository;
    @Override
    public List<KakaoPayApproveLogDocument> findSubscriptionLogsByUser(ServiceUserDTO serviceUserDTO){
        kakaoPayApproveLogRepository.findByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        return null;
    }

    @Override
    public List<DepositLogDocument> findDepositLogsByUser(ServiceUserDTO serviceUserDTO) {

        return null;
    }

    @Override
    public List<WithdrawalLogDocument> findWithdrawalLogsByUser(ServiceUserDTO serviceUserDTO) {
        return null;
    }
}
