package com.example.billing.service.impl;

import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.TimePeriodDTO;
import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import com.example.billing.data.loggingDB.repository.DepositLogRepository;
import com.example.billing.data.loggingDB.repository.KakaoPayApproveLogRepository;
import com.example.billing.data.loggingDB.repository.WithdrawalLogRepository;
import com.example.billing.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final KakaoPayApproveLogRepository kakaoPayApproveLogRepository;
    private final DepositLogRepository depositLogRepository;
    private final WithdrawalLogRepository withdrawalLogRepository;
    @Override
    public List<KakaoPayApproveLogDocument> findSubscriptionLogsByUser(ServiceUserDTO serviceUserDTO){
        List<KakaoPayApproveLogDocument> logs = kakaoPayApproveLogRepository.findByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        return logs;
    }

    @Override
    public List<DepositLogDocument> findDepositLogsByUser(ServiceUserDTO serviceUserDTO) {
        List<DepositLogDocument> logs = depositLogRepository.findByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        return logs;
    }

    @Override
    public List<WithdrawalLogDocument> findWithdrawalLogsByUser(ServiceUserDTO serviceUserDTO) {
        List<WithdrawalLogDocument> logs = withdrawalLogRepository.findByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());
        return logs;
    }

    @Override
    public List<KakaoPayApproveLogDocument> findSubscriptionLogsByPeriod(TimePeriodDTO timePeriodDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date from = format.parse(timePeriodDTO.getFrom());
            Date to = format.parse(timePeriodDTO.getTo());
            List<KakaoPayApproveLogDocument> logs = kakaoPayApproveLogRepository.findByCreatedDateBetween(from, to);
            return logs;
        }catch (ParseException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<DepositLogDocument> findDepositLogsByPeriod(TimePeriodDTO timePeriodDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date from = format.parse(timePeriodDTO.getFrom());
            Date to = format.parse(timePeriodDTO.getTo());
            List<DepositLogDocument> logs = depositLogRepository.findByCreatedDateBetween(from, to);
            return logs;
        }catch (ParseException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<WithdrawalLogDocument> findWithdrawalLogsByPeriod(TimePeriodDTO timePeriodDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date from = format.parse(timePeriodDTO.getFrom());
            Date to = format.parse(timePeriodDTO.getTo());
            List<WithdrawalLogDocument> logs = withdrawalLogRepository.findByCreatedDateBetween(from, to);
            return logs;
        }catch (ParseException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
