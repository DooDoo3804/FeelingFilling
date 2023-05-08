package com.a702.feelingfilling.domain.user.model.repository;

import com.a702.feelingfilling.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserId(int userId);

    Optional<User> findByIdOAuth2(String idOAuth2);
}
