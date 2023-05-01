package com.example.billing.data.loggingDB.repository;

import com.example.billing.data.loggingDB.entity.TestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends MongoRepository<TestDocument, String> {
}
