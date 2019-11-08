package com.example.springboot.urlshortener.service;

import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.urlshortener.dao.URLRepository;
import com.example.springboot.urlshortener.entity.URLPair;

@Service
public class URLShortenerServiceImpl implements URLShortenerService {

	private static final char[] charTable = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static final int BASE = charTable.length;
	private static final int SHORTENERSIZE = 6;

	private Logger logger = Logger.getLogger(getClass().getName());

	private URLRepository customerRepository;
	
	@Autowired
	public URLShortenerServiceImpl(URLRepository theCustomerRepository) {
		customerRepository = theCustomerRepository;
	}
	
	@Override
	public URLPair findUrlByShortUrl(String shortUrl) {	
		Optional<URLPair> result = customerRepository.findByShortUrl(shortUrl);
		
		URLPair theUrl = null;
		
		if (result.isPresent()) {
			theUrl = result.get();
		}
		else {
			//The short Url was not found.
			theUrl = null;
		}
		
		return theUrl;
	}
	

	@Override
	public String searchOrGenerateShortUrl(String longUrl) {
		Optional<URLPair> result = customerRepository.findByLongUrl(longUrl);
		
		URLPair theUrl = null;
		String shortUrl = null;

		if (result.isPresent()) { //The long Url was found in the database. 
			
			theUrl = result.get();
			shortUrl = theUrl.getShortUrl();
			logger.info("[Log info] the original long URL already exists in the database: " + longUrl);
		}
		else {
			//The long Url was not found in the database. 
			shortUrl = generateShortUrl(longUrl);
			theUrl = new URLPair(longUrl, shortUrl);
			customerRepository.save(theUrl);
			logger.info("[Log info] the original long URL does not exist in the database. Generate the new short URL: " + shortUrl);
		} 		

		return shortUrl;
		
	}

	@Override
	public String generateShortUrl(String longUrl) {

		Optional<URLPair> result = Optional.empty();
		
		String shortUrl = null;
		Random rand = new Random();
		StringBuilder temp = new StringBuilder();
		do {
			temp.setLength(0);
			for (int i = 0; i < SHORTENERSIZE; i++) {
				int index = rand.nextInt(BASE);
				temp.append(charTable[index]);
			}
			shortUrl = new String(temp);
			result = customerRepository.findByShortUrl(shortUrl);
		} while (result.isPresent());

		return shortUrl;
	}

}