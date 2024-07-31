package com.backend.VNPT_Intern_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VnptInternProjectApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(VnptInternProjectApplication.class, args);
	}

}
