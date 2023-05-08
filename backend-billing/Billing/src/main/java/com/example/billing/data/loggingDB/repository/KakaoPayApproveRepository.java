package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.document.KakaoPayApproveDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoPayApproveRepository extends MongoRepository<KakaoPayApproveDocument, String> {
}
