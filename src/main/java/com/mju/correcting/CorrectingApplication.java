package com.mju.correcting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CorrectingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorrectingApplication.class, args);
	}

}
