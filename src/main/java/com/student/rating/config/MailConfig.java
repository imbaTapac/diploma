package com.student.rating.config;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender javaMailSender() throws IOException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		ClassPathResource resource = new ClassPathResource("mail.properties");
		InputStream input = resource.getInputStream();
		Properties props = new Properties();
		props.load(input);
		mailSender.setJavaMailProperties(props);
		mailSender.setHost(props.getProperty("mail.smtp.host"));
		mailSender.setPort(parseInt(props.getProperty("mail.smtp.port")));
		mailSender.setUsername(props.getProperty("mail.smtp.username"));
		mailSender.setPassword(props.getProperty("mail.smtp.password"));
		return mailSender;
	}
}
