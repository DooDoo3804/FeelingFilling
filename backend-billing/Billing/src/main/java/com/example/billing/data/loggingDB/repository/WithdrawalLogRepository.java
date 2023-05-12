package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WithdrawalLogRepository extends MongoRepository<WithdrawalLogDocument, String> {
    public List<WithdrawalLogDocument> findByServiceNameAndServiceUserId(String serviceName, int serviceUserId);
    public List<WithdrawalLogDocument> findByCreatedDateBetween(Date from, Date to);
}
