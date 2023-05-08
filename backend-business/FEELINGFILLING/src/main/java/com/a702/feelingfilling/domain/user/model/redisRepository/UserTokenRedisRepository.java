package com.a702.feelingfilling.domain.user.model.redisRepository;

import com.a702.feelingfilling.domain.user.model.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRedisRepository extends CrudRepository<UserToken, String> {
}
