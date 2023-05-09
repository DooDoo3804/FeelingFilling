package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.WithdrawalLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface WithdrawalLogRepository extends MongoRepository<WithdrawalLogDocument, String> {
}
