package com.example.springboot.urlshortener.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springboot.urlshortener.entity.URLPair;
import com.example.springboot.urlshortener.service.URLShortenerService;

@Controller
@RequestMapping("/shortener")
public class URLController {
	
	private Logger logger = Logger.getLogger(getClass().getName());

	private URLShortenerService customerService;
	
	public URLController(URLShortenerService theCustomerService) {
		customerService = theCustomerService;
	}
	
	@GetMapping("")
	public String showHomePage(Model theModel) {
		logger.info("[Log info] home page");
		// create model attribute to bind form data
		URLPair theUrl = new URLPair();
		theModel.addAttribute("url", theUrl);
		
		return "shortener/home-form";
	}
	
	@PostMapping("")
	public String shortenUrl(@ModelAttribute("url") URLPair theUrl) {
		logger.info("[Log info] going to get a shortened URL");
		// generate the short URL
		String longUrl = theUrl.getLongUrl();
		logger.info("[Log info] The long URL is: " + longUrl);
		//check the original long URL is valid URL or not
		UrlValidator urlValidator = new UrlValidator();
		
		if (!urlValidator.isValid(longUrl) 
			&& !urlValidator.isValid("https://" + longUrl) 
			&& !urlValidator.isValid("http://" + longUrl) 
			&& !urlValidator.isValid("https" + longUrl) 
			&& !urlValidator.isValid("http:" + longUrl)) {
			logger.info("[Log info] URL is invalid");
			return "shortener/invalid-url";
		} else {
			String shortUrl = customerService.searchOrGenerateShortUrl(longUrl);
			theUrl.setShortUrl(shortUrl);
			logger.info("[Log info] the short URL is: " + theUrl.getShortUrl());
			return "shortener/shortener-result";
		}
	}
	
	@GetMapping(path = "/{shortU}")
	public void redirect(@PathVariable String shortU, HttpServletResponse response) throws IOException {
		logger.info("[Log info] the short URL to be redirected is: " + shortU);
		URLPair theUrl = customerService.findUrlByShortUrl(shortU);
		if (theUrl == null) {
			logger.info("[Log info] the short URL to be redirected doesn't exist in the database.");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String longUrl = theUrl.getLongUrl();
		logger.info("[Log info] the original long URL is: " + longUrl);
		if (longUrl.startsWith("://")) {
			longUrl = "http" + longUrl;
		} else if (!longUrl.startsWith("http")) {
			longUrl = "http://" + longUrl;
		}
		logger.info("[Log info] redirect to: " + longUrl);
		response.sendRedirect(longUrl);
		return;
		}
	
}