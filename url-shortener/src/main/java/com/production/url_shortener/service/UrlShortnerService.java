package com.production.url_shortener.service;


import com.production.url_shortener.model.UrlMapping;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortnerService {
    private final Map<String, UrlMapping> storage = new HashMap<>();
    public String shortenUrl(String longUrl){
        String shortKey = UUID.randomUUID().toString().substring(0,6);
        UrlMapping mapping = new UrlMapping(shortKey , longUrl);
        storage.put(shortKey, mapping);
        return shortKey;
    }

    public String getLongUrl(String shortKey){
        UrlMapping mapping = storage.get(shortKey);
        if(mapping == null){
            throw new IllegalArgumentException("This short url is invalid.");
        }
        return mapping.getLongUrl();
    }
}
