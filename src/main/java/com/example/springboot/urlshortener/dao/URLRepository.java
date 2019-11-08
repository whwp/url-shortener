package com.example.springboot.urlshortener.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.urlshortener.entity.URLPair;

public interface URLRepository extends CrudRepository<URLPair, String> {
	
	Optional<URLPair> findByLongUrl(String longUrl);
	
	Optional<URLPair> findByShortUrl(String shortUrl);
}
