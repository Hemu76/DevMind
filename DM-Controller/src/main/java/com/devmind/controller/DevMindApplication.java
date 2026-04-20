package com.devmind.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.devmind.service.repository")
@EntityScan(basePackages = "com.devmind.model")
public class DevMindApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevMindApplication.class, args);
	}

}
