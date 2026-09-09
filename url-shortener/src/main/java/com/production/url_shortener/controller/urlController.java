package com.production.url_shortener.controller;

import com.production.url_shortener.dto.UrlRequest;
import com.production.url_shortener.dto.UrlResponse;
import com.production.url_shortener.service.UrlShortnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlShortnerService urlService;

    public UrlController(UrlShortnerService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody UrlRequest request) {
        if (request.getLongUrl() == null || request.getLongUrl().isBlank()) {
            return ResponseEntity.badRequest().body("URL cannot be empty");
        }
        
        String shortKey = urlService.shortenUrl(request.getLongUrl());
        
        // In a real app, you would construct the full domain URL here
        // e.g., "http://localhost:8080/" + shortKey
        return ResponseEntity.ok(new UrlResponse(shortKey));
    }
}
