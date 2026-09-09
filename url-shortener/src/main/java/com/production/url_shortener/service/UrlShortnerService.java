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
        // Step 1: Save the entity to get the auto-incremented database ID
        UrlMapping mapping = new UrlMapping(longUrl);
        mapping = repository.save(mapping);

        // Step 2: Convert the ID to a Base62 string
        String shortKey = Base62Encoder.encode(mapping.getId());

        // Step 3: Update the entity with the generated shortKey
        mapping.setShortKey(shortKey);
        repository.save(mapping);

        return shortKey;
    }

    public String getLongUrl(String shortKey) {
        // Find the URL by its shortKey
        Optional<UrlMapping> mappingOptional = repository.findByShortKey(shortKey);
        
        if (mappingOptional.isEmpty()) {
            throw new IllegalArgumentException("This short url is invalid.");
        }
        
        return mappingOptional.get().getLongUrl();
    }
}
