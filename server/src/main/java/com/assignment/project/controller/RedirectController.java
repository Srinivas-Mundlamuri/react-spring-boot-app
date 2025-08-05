package com.assignment.project.controller;

import com.assignment.project.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.net.URI;

@RestController
public class RedirectController {

    private final UrlShortenerService service;

    public RedirectController(UrlShortenerService service) {
        this.service = service;
    }

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        return service.getOriginalUrl(shortCode)
                .map(mapping -> {
                    Duration duration = Duration.between(mapping.getCreatedAt(), Instant.now());
                    if (duration.toMinutes() >= 5) {
                        return ResponseEntity.badRequest().body("Link expired");
                    }
                    // Use 302 redirect to the original URL
                    return ResponseEntity.status(302)
                            .location(URI.create(mapping.getOriginalUrl()))
                            .build();
                })
                .orElse(ResponseEntity.status(404).body("Short URL not found"));
    }
}
