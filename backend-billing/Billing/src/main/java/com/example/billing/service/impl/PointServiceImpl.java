package com.example.billing.service.impl;

import com.example.billing.data.billingDB.entity.User;
import com.example.billing.data.billingDB.entity.Withdrawal;
import com.example.billing.data.billingDB.repository.UserRepository;
import com.example.billing.data.dto.ServiceUserAndAmountDTO;
import com.example.billing.data.dto.ServiceUserDTO;
import com.example.billing.data.dto.WithdrawalDTO;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import com.example.billing.data.loggingDB.repository.WithdrawalLogRepository;
import com.example.billing.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PointServiceImpl implements PointService {
    private final UserRepository userRepository;

    private final WithdrawalLogRepository withdrawalLogRepository;

    @Override
    public WithdrawalDTO withdrawPoint(ServiceUserAndAmountDTO serviceUserAndAmountDTO) {
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceUserAndAmountDTO.getServiceName(), serviceUserAndAmountDTO.getServiceUserId());
        int amount = serviceUserAndAmountDTO.getAmount();
        WithdrawalDTO withdrawalDTO;
        long balanceBefore = user.getPoint();
        if(balanceBefore >= amount){
            long balanceAfter = balanceBefore - amount;
            user.setPoint(balanceAfter);
            List<Withdrawal> withdrawalList = user.getWithdrawals();
            Withdrawal newWithdrawal = new Withdrawal("Kakao", amount);
            withdrawalList.add(newWithdrawal);

            /*
                실제 출금 로직
                           */
            withdrawalDTO = new WithdrawalDTO(true, amount, balanceBefore, balanceAfter, "출금에 성공하였습니다.");

            WithdrawalLogDocument withdrawalLog = WithdrawalLogDocument.builder()
                    .userId(user.getUserId())
                    .serviceName(user.getServiceName())
                    .serviceUserId(user.getServiceUserId())
                    .balance(user.getPoint())
                    .status("Success")
                    .build();

        }else{
            long balanceAfter = balanceBefore;
            withdrawalDTO = new WithdrawalDTO(false, amount, balanceBefore, balanceAfter, "잔액이 부족합니다.");
        }
        return withdrawalDTO;
    }

    @Override
    public long getPoint(ServiceUserDTO serviceUserDTO) {
        User user = userRepository.findUserByServiceNameAndServiceUserId(serviceUserDTO.getServiceName(), serviceUserDTO.getServiceUserId());

        return user.getPoint();
    }
}
