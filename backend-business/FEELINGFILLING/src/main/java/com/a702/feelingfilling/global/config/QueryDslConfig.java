package com.a702.feelingfilling.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory(){
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

		return new JPAQueryFactory(entityManager);
	}
}
