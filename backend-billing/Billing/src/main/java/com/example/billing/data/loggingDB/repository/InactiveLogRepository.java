package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.DepositLogDocument;
import com.example.billing.data.loggingDB.document.InactiveLogDocumnet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InactiveLogRepository extends MongoRepository<InactiveLogDocumnet, String> {
    public List<InactiveLogDocumnet> findByServiceNameAndServiceUserId(String serviceName, int serviceUserId);

    public List<InactiveLogDocumnet> findByCreatedDateBetween(Date from, Date to);

}
