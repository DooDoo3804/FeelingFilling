package com.a702.feelingfilling.domain.history.model.repository;

import com.a702.feelingfilling.domain.history.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History,Integer> {
	List<History> findByUser_userIdAndRequestTimeBetweenOrderByRequestTimeDesc(Integer userId, LocalDateTime start, LocalDateTime end);
}
