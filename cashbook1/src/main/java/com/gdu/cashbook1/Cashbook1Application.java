package com.gdu.cashbook1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cashbook1Application {
// @SpringBootApplication == @Configuration + EnableAutoConfiguration + @ComponentScan
	public static void main(String[] args) {
		SpringApplication.run(Cashbook1Application.class, args);
	}

}
