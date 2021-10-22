package com.jamesmhare.namethatsong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NameThatSongApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameThatSongApplication.class, args);
	}

}
