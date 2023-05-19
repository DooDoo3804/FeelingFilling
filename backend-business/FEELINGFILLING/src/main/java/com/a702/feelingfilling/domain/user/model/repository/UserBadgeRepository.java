package com.a702.feelingfilling.domain.user.model.repository;

import com.a702.feelingfilling.domain.user.model.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {
	List<UserBadge> findByUser_UserIdOrderByAchievedDateDesc(int userId);
}
