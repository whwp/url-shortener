package com.example.springboot.urlshortener.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urlmodels")
public class URLPair {

	// define fields
	
	private String shortUrl;

	private String longUrl;	
	
	// define constructors
	
	public URLPair() {
		
	}
	
	public URLPair(String longUrl) {
		this.longUrl = longUrl;
	}


	public URLPair(String longUrl, String shortUrl) {
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;

	}

	// define getter/setter
	
	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	// define tostring
	
	@Override
	public String toString() {
		return "URL [longUrl=" + longUrl + ", shortUrl=" + shortUrl + "]";
	}

	
	


		
}











