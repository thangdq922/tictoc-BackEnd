package com.tictoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class } )
public class TictocApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictocApplication.class, args);
	}

}
