package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.CancellationLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CancellationLogRepository extends MongoRepository<CancellationLogDocument, String> {
    public List<CancellationLogDocument> findByServiceNameAndAndServiceUserId(String serviceName, int serviceUserId);
    public List<CancellationLogDocument> findByCreatedDateBetween(Date from, Date to);
}
