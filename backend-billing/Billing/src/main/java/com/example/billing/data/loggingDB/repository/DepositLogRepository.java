package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.DepositLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositLogRepository extends MongoRepository<DepositLogDocument, String> {
}
