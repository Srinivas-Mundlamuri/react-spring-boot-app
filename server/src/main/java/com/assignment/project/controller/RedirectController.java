package com.assignment.project.controller;

import com.assignment.project.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletRequest request) {
        return service.getOriginalUrl(shortCode)
                .map(mapping -> {
                    Duration duration = Duration.between(mapping.getCreatedAt(), Instant.now());
                    if (duration.toMinutes() >= 5) {
                        // Return HTML page with navigation option for expired links
                        String frontendUrl = getFrontendUrl(request);
                        String html = createErrorPage(
                            "⏰ Link Expired", 
                            "This short URL has expired after 5 minutes for security reasons.",
                            "Create a new short URL",
                            frontendUrl
                        );
                        return ResponseEntity.badRequest()
                                .header("Content-Type", "text/html")
                                .body(html);
                    }
                    // Use 302 redirect to the original URL
                    return ResponseEntity.status(302)
                            .location(URI.create(mapping.getOriginalUrl()))
                            .build();
                })
                .orElse(ResponseEntity.status(404)
                        .header("Content-Type", "text/html")
                        .body(createErrorPage(
                            "❌ Short URL Not Found", 
                            "This short URL doesn't exist or may have been removed.",
                            "Create a new short URL",
                            getFrontendUrl(request)
                        )));
    }

    private String getFrontendUrl(HttpServletRequest request) {
        // Check if we're running in development mode (port 7070 usually means production build)
        // In development, React runs on port 3000
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        
        // If accessing via localhost:7070, check if React dev server is running on 3000
        if ("localhost".equals(serverName) && serverPort == 7070) {
            // Try to redirect to React dev server if available, otherwise stay on same origin
            return "http://localhost:3000";
        }
        
        // For production (Railway, Render, etc.), use same origin
        String scheme = request.getScheme();
        if ((scheme.equals("http") && serverPort == 80) || 
            (scheme.equals("https") && serverPort == 443)) {
            return scheme + "://" + serverName;
        } else {
            return scheme + "://" + serverName + ":" + serverPort;
        }
    }

    private String createErrorPage(String title, String message, String buttonText, String frontendUrl) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>" + title + "</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "            min-height: 100vh;\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        .container {\n" +
                "            background: white;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 16px;\n" +
                "            box-shadow: 0 20px 40px rgba(0,0,0,0.1);\n" +
                "            text-align: center;\n" +
                "            max-width: 500px;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "        .title {\n" +
                "            font-size: 2.5em;\n" +
                "            margin-bottom: 20px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .message {\n" +
                "            font-size: 1.2em;\n" +
                "            color: #666;\n" +
                "            margin-bottom: 30px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "            color: white;\n" +
                "            padding: 15px 30px;\n" +
                "            border: none;\n" +
                "            border-radius: 8px;\n" +
                "            font-size: 1.1em;\n" +
                "            font-weight: 600;\n" +
                "            cursor: pointer;\n" +
                "            text-decoration: none;\n" +
                "            display: inline-block;\n" +
                "            transition: transform 0.2s ease;\n" +
                "        }\n" +
                "        .btn:hover {\n" +
                "            transform: translateY(-2px);\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"title\">" + title + "</div>\n" +
                "        <div class=\"message\">" + message + "</div>\n" +
                "        <a href=\"" + frontendUrl + "\" class=\"btn\">🔗 " + buttonText + "</a>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
