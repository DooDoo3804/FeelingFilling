package com.a702.feelingfilling.domain.user.model.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Builder
@Getter
public class UserToken {
    @Id
    private long userId;
    private String token;
}
