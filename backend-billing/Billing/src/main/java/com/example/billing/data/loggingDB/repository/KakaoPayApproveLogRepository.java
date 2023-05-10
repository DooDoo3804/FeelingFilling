package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.KakaoPayApproveLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KakaoPayApproveLogRepository extends MongoRepository<KakaoPayApproveLogDocument, String> {
    public List<KakaoPayApproveLogDocument> findByServiceNameAndServiceUserId(String serviceName, int serviceUserId);
}
