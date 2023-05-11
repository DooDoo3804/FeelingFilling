package com.example.billing.service;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.TimePeriodDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;

import java.util.List;

public interface LoggingService {
    public List<KakaoPayApproveLogDocument> findSubscriptionLogsByUser(ServiceUserDTO serviceUserDTO);
    public List<DepositLogDocument> findDepositLogsByUser(ServiceUserDTO serviceUserDTO);
    public List<WithdrawalLogDocument> findWithdrawalLogsByUser(ServiceUserDTO serviceUserDTO);
    public List<KakaoPayApproveLogDocument> findSubscriptionLogsByPeriod(TimePeriodDTO timePeriodDTO);
    public List<DepositLogDocument> findDepositLogsByPeriod(TimePeriodDTO timePeriodDTO);
    public List<WithdrawalLogDocument> findWithdrawalLogsByPeriod(TimePeriodDTO timePeriodDTO);

}
