package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class BackendApplication {

	@PostConstruct
	public void init() {
		// Spring Boot TimeZone 設定する
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
