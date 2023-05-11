package com.a702.feelingfilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.a702.feelingfilling.domain.chatting.repository")
public class FeelingfillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeelingfillingApplication.class, args);
	}

}
