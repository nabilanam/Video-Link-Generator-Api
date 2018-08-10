package com.nabilanam.api.uselessapis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

@SpringBootApplication
		(exclude =
				{JacksonAutoConfiguration.class},
				scanBasePackages = {
						"com.nabilanam.api.uselessapis",
						"com.nabilanam.downloader"})
public class UselessapisApplication {

	public static void main(String[] args) {
		SpringApplication.run(UselessapisApplication.class, args);
	}
}
