package com.example.springboot.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springboot.urlshortener.entity.URLPair;

@SpringBootApplication
public class URLShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(URLShortenerApplication.class, args);
	}

}