package com.student.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.student")
@EnableJpaRepositories(basePackages = "com.student.rating.repository")
@EnableTransactionManagement
public class StudentRatingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StudentRatingApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StudentRatingApplication.class);
	}
}
