package com.gdu.cashbook1;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@PropertySource("classpath:google.properties")
public class Cashbook1Application {
	@Value("${google.username}")
	public String username;
	@Value("${google.password}")
	public String password;
	// @SpringBootApplication == @Configuration + EnableAutoConfiguration + @ComponentScan
	public static void main(String[] args) {
		SpringApplication.run(Cashbook1Application.class, args);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl(); //JavaMailSenderImpl 자식타입
		javaMailSender.setHost("smtp.gmail.com"); //메일서버 이름을 적어야함
		javaMailSender.setPort(587); // 587 구글 메일서버 포트
		System.out.println(password);
		System.out.println(username);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		
		Properties prop = new Properties(); //Properties == HashMap<String, String>
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender;
	}

}
