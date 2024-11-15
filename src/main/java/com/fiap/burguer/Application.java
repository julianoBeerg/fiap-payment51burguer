package com.fiap.burguer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.fiap.burguer")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
