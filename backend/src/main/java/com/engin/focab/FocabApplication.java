package com.engin.focab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.engin.focab.*")

public class FocabApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocabApplication.class, args);
	}

}
