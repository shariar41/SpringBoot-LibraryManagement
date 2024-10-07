package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.controllers", "com.services"})
public class LibraryManagementWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementWebApplication.class, args);
	}

}
