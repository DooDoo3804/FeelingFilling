package com.a702.feelingfilling;

import javax.annotation.sql.DataSourceDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class FeelingfillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeelingfillingApplication.class, args);
	}

}
