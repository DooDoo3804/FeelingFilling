package com.a702.feelingfilling.domain.request.model.repository;

import com.a702.feelingfilling.domain.request.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

}
