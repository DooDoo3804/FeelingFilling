package com.a702.feelingfilling.domain.user.model.repository;

import com.a702.feelingfilling.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	User findByUserId(String userId);

	User findByIdOAuth2(String idOAuth2);
}
