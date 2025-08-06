package com.assignment.project.controller;

import com.assignment.project.model.UrlMapping;
import com.assignment.project.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String url = body.get("url");
        if (url == null || !url.matches("^(http|https)://.*$")) {
            return ResponseEntity.badRequest().body("Invalid URL");
        }

        UrlMapping mapping = service.createShortUrl(url);
        String baseUrl = getBaseUrl(request);
        return ResponseEntity.ok(Map.of("shortUrl", baseUrl + "/r/" + mapping.getShortCode()));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(HttpServletRequest request) {
        try {
            List<UrlMapping> allUrls = service.getAllUrls();
            String baseUrl = getBaseUrl(request);
            
            List<Map<String, Object>> historyResponse = allUrls.stream()
                    .map(mapping -> {
                        Duration duration = Duration.between(mapping.getCreatedAt(), Instant.now());
                        boolean isExpired = duration.toMinutes() >= 5;
                        
                        Map<String, Object> item = new HashMap<>();
                        item.put("original", mapping.getOriginalUrl());
                        item.put("shortCode", mapping.getShortCode());
                        item.put("shortUrl", baseUrl + "/r/" + mapping.getShortCode()); // Full short URL
                        item.put("createdAt", mapping.getCreatedAt());
                        item.put("isExpired", isExpired);
                        item.put("expiresIn", Math.max(0, 5 - duration.toMinutes()));
                        return item;
                    })
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(historyResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Arrays.asList());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "API is working!", "timestamp", Instant.now()));
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        
        // Handle default ports
        if ((scheme.equals("http") && serverPort == 80) || 
            (scheme.equals("https") && serverPort == 443)) {
            return scheme + "://" + serverName;
        } else {
            return scheme + "://" + serverName + ":" + serverPort;
        }
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
    @DeleteMapping("/delete/{shortCode}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortCode) {
        try {
            boolean deleted = service.deleteUrl(shortCode);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "URL deleted successfully"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Short URL not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete URL"));
        }
    }
}
