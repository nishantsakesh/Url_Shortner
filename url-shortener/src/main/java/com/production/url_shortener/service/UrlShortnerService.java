package com.production.url_shortener.service;

import com.production.url_shortener.model.UrlMapping;
import com.production.url_shortener.repository.UrlRepository;
import com.production.url_shortener.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlShortnerService {

    private final UrlRepository repository;

    @Autowired
    public UrlShortnerService(UrlRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(String longUrl) {
        if (!isValidUrl(longUrl)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        // save first to generate DB id
        UrlMapping mapping = new UrlMapping(longUrl);
        mapping = repository.save(mapping);

        // encode id and update
        String shortKey = Base62Encoder.encode(mapping.getId());
        mapping.setShortKey(shortKey);
        repository.save(mapping);

        return shortKey;
    }

    public String getLongUrl(String shortKey) {
        Optional<UrlMapping> mappingOptional = repository.findByShortKey(shortKey);
        
        if (mappingOptional.isEmpty()) {
            throw new IllegalArgumentException("This short url is invalid.");
        }
        
        return mappingOptional.get().getLongUrl();
    }

    private boolean isValidUrl(String url) {
        // simple validation to ensure it looks like a web url
        return url != null && url.matches("^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$");
    }
}
