package com.production.url_shortener.controller;


import com.production.url_shortener.service.UrlShortnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("api/v1/urls")
public class urlController {
    private final UrlShortnerService urlService;

    public urlController(UrlShortnerService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shortner")
    public ResponseEntity<Map<String, String>> shortenurl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("longUrl");
        if(longUrl == null || longUrl.isBlank()){
            return ResponseEntity.badRequest().body(Map.of("error", "url cant be empty"));
        }
        String shortKey = urlService.shortenUrl(longUrl);
        return ResponseEntity.ok(Map.of("shortUrl", shortKey));
    }

    @GetMapping("/{shortKey}")
        public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortKey){
            String longUrl = urlService.getLongUrl(shortKey);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create((longUrl)))
                    .build();

        }

}
