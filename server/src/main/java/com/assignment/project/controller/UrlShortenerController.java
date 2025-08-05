package com.assignment.project.controller;

import com.assignment.project.model.UrlMapping;
import com.assignment.project.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        if (url == null || !url.matches("^(http|https)://.*$")) {
            return ResponseEntity.badRequest().body("Invalid URL");
        }

        UrlMapping mapping = service.createShortUrl(url);
        return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:7070/" + mapping.getShortCode()));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        return service.getOriginalUrl(code)
                .map(mapping -> {
                    Duration duration = Duration.between(mapping.getCreatedAt(), Instant.now());
                    if (duration.toMinutes() >= 5) {
                        return ResponseEntity.badRequest().body("Link expired");
                    }
                    return ResponseEntity.status(302).header("Location", mapping.getOriginalUrl()).build();
                })
                .orElse(ResponseEntity.status(404).body("Short URL not found"));
    }
}
