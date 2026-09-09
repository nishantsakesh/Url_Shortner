package com.production.url_shortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_mappings")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We removed nullable = false so we can save it first, get the ID, 
    // encode it, and then update the shortKey.
    @Column(unique = true)
    private String shortKey;

    @Column(nullable = false)
    private String longUrl;

    private LocalDateTime createdAt;

    public UrlMapping() {}

    public UrlMapping(String longUrl) {
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getShortKey() { return shortKey; }
    public void setShortKey(String shortKey) { this.shortKey = shortKey; }

    public String getLongUrl() { return longUrl; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
