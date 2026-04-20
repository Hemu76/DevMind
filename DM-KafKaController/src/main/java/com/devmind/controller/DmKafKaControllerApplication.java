package com.devmind.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.devmind.service.repository")
@EntityScan(basePackages = "com.devmind.model")
@EnableKafka
public class DmKafKaControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmKafKaControllerApplication.class, args);
	}

}
