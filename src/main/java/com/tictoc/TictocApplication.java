package com.tictoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tictoc"})
public class TictocApplication {

	public static void main(String[] args) {
		SpringApplication.run(TictocApplication.class, args);
	}

}
