package com.assignment.project.service;

import com.assignment.project.model.UrlMapping;
import com.assignment.project.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository repository;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    public UrlMapping createShortUrl(String originalUrl) {
        Optional<UrlMapping> existing = repository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            // Update timestamp to extend expiration time
            UrlMapping existingMapping = existing.get();
            existingMapping.setCreatedAt(Instant.now());
            return repository.save(existingMapping);
        }

        String shortCode = generateRandomCode(8);
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        mapping.setCreatedAt(Instant.now());

        return repository.save(mapping);
    }

    public Optional<UrlMapping> getOriginalUrl(String code) {
        return repository.findByShortCode(code);
    }

    public List<UrlMapping> getAllUrls() {
        return repository.findAll();
    }

    private String generateRandomCode(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
