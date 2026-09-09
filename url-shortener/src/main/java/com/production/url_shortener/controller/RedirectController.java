package com.production.url_shortener.controller;

import com.production.url_shortener.service.UrlShortnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final UrlShortnerService urlService;

    public RedirectController(UrlShortnerService urlService) {
        this.urlService = urlService;
    }

    // Notice we map this directly to the root, e.g., localhost:8080/aB3x
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortKey) {
        String longUrl = urlService.getLongUrl(shortKey);
        
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
