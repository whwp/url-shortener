package com.example.springboot.urlshortener.service;

import com.example.springboot.urlshortener.entity.URLPair;

public interface URLShortenerService {

	public URLPair findUrlByShortUrl(String shortUrl);
	
	public String generateShortUrl(String longUrl);
	
	public String searchOrGenerateShortUrl(String longUrl);
	
}
