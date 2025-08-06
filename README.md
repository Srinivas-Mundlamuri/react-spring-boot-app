# ## 🌐 Live Demo

**� Try the live application:** [https://springboot-react-app-production.up.railway.app/](https://springboot-react-app-production.up.railway.app/)

*The application is deployed on Railway and ready to use!*

## 📋 Assignment OverviewShortener - Full Stack Application

## 🌐 Live Demo

**� Try the live application:** [https://springboot-react-app-production.up.railway.app/](https://springboot-react-app-production.up.railway.app/)

*The application is deployed on Railway and ready to use!*

## �📋 Assignment Overview

This is a complete URL Shortener application built for the Full Stack Engineer assignment. It demonstrates:

- **Backend**: Java Spring Boot with REST API
- **Frontend**: React/HTML with modern UI
- **Database**: H2 (in-memory) with JPA
- **Features**: URL shortening, redirection, expiration (5 minutes)
- **Deployment**: Live on Railway platform

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java 17 or higher** - [Download here](https://adoptium.net/)
- **Git** - [Download here](https://git-scm.com/)
- **Node.js and npm** (Optional - only for React development) - [Download here](https://nodejs.org/)

### 📥 Clone the Repository

```bash
# Clone the repository
git clone https://github.com/Srinivas-Mundlamuri/react-spring-boot-app.git

# Navigate to the project directory
cd react-spring-boot-app
```

### 🏃‍♂️ Quick Start Options

Choose one of the following options to run the application:

#### Option 1: Run Pre-built Application (Fastest)

The application comes with pre-built React files already integrated into the Spring Boot server.

```bash
# Navigate to server directory
cd server

# Run the application (this includes both frontend and backend)
./mvnw spring-boot:run
```

✅ **That's it!** Open your browser and go to **http://localhost:7070**

#### Option 2: Build and Run from Source

If you want to build the React frontend from source and sync it with the server:

```bash
# 1. Build the React frontend
cd client
npm install
npm run build

# 2. Sync the build with server (go back to project root)
cd ..
rm -rf server/src/main/resources/static/*
cp -r client/build/* server/src/main/resources/static/

# 3. Run the Spring Boot server
cd server
./mvnw spring-boot:run
```

✅ **Application running at**: **http://localhost:7070**

#### Option 3: Development Mode (Frontend + Backend Separately)

For development with hot reload:

```bash
# Terminal 1: Start the backend server
cd server
./mvnw spring-boot:run
# Backend runs at http://localhost:7070

# Terminal 2: Start React development server
cd client
npm install
npm start
# Frontend runs at http://localhost:3000
```

## 🎯 How to Test the Application

### Quick Demo (Recommended)

**Option A: Try the Live Version**

1. ✅ Visit the live application: [https://springboot-react-app-production.up.railway.app/](https://springboot-react-app-production.up.railway.app/)
2. ✅ Enter a URL: `https://www.google.com`
3. ✅ Click "Shorten URL" 
4. ✅ Copy the generated short URL (e.g., `https://springboot-react-app-production.up.railway.app/r/abc123`)
5. ✅ Open the short URL in a new tab → It redirects to Google! 🎉

**Option B: Run Locally**
1. ✅ Ensure the backend is running on port 7070
2. ✅ Open your browser and go to `http://localhost:7070`
3. ✅ Enter a URL: `https://www.google.com`
4. ✅ Click "Shorten URL" 
5. ✅ Copy the generated short URL (e.g., `http://localhost:7070/r/abc123`)
6. ✅ Open the short URL in a new tab → It redirects to Google! 🎉

### Backend API Testing (Comprehensive)

Test all the available features (replace with live URL if testing production):

**Live API Base URL:** `https://springboot-react-app-production.up.railway.app`  
**Local API Base URL:** `http://localhost:7070`

#### 1. **Create a short URL:**

```bash
# Live version
curl -X POST https://springboot-react-app-production.up.railway.app/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'

# Local version
curl -X POST http://localhost:7070/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'
```

Response:

```json
{"shortUrl":"https://springboot-react-app-production.up.railway.app/r/abc123"}
```

#### 2. **Fetch URL history:**

```bash
# Live version
curl https://springboot-react-app-production.up.railway.app/api/url/history

# Local version
curl http://localhost:7070/api/url/history
```

Response:

```json
[
  {
    "original": "https://www.google.com",
    "shortCode": "abc123",
    "shortUrl": "http://localhost:7070/r/abc123",
    "createdAt": "2025-08-06T13:55:00Z",
    "isExpired": false,
    "expiresIn": 4
  }
]
```

#### 3. **Test the redirect:**

```bash
curl -I http://localhost:7070/r/abc123
```

Response (if not expired):

```http
HTTP/1.1 302 
Location: https://www.google.com
```

Response (if expired):

```http
HTTP/1.1 400 
Content-Type: text/html
```

#### 4. **Delete a short URL:**

```bash
curl -X DELETE http://localhost:7070/api/url/delete/abc123
```

Response:

```json
{"message": "URL deleted successfully"}
```

#### 5. **Test API connectivity:**

```bash
curl http://localhost:7070/api/url/test
```

Response:

```json
{"message": "API is working!", "timestamp": "2025-08-06T13:55:00Z"}
```

#### 6. **Test error scenarios:**

```bash
# Test non-existent URL
curl -I http://localhost:7070/r/nonexistent

# Test expired URL (wait 5+ minutes after creation)
curl -I http://localhost:7070/r/abc123

# Test invalid URL creation
curl -X POST http://localhost:7070/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "invalid-url"}'
```

## 📁 Project Structure

```
react-spring-boot-app/
├── client/                          # React Frontend
│   ├── public/
│   │   ├── index.html              # Main HTML template
│   │   └── ...                     # Static assets
│   ├── src/
│   │   ├── App.js                  # Main React component
│   │   ├── App.css                 # Styles
│   │   └── index.js                # React entry point
│   ├── build/                      # Built React app (auto-generated)
│   └── package.json                # Frontend dependencies
│
├── server/                         # Spring Boot Backend
│   ├── src/main/java/com/assignment/project/
│   │   ├── controller/
│   │   │   ├── UrlShortenerController.java    # API endpoints
│   │   │   └── RedirectController.java        # Redirect handling
│   │   ├── model/
│   │   │   └── UrlMapping.java               # JPA Entity
│   │   ├── repository/
│   │   │   └── UrlMappingRepository.java     # Data access layer
│   │   ├── service/
│   │   │   └── UrlShortenerService.java      # Business logic
│   │   └── ProjectApplication.java           # Main Spring Boot class
│   ├── src/main/resources/
│   │   ├── application.properties            # Configuration
│   │   └── static/                          # Served React build files
│   ├── target/                              # Built JAR files
│   ├── pom.xml                             # Backend dependencies
│   ├── Procfile                            # Railway deployment config
│   ├── railway.json                        # Railway project config
│   └── system.properties                   # Java version for deployment
│
├── README.md                       # This file
└── DEPLOYMENT.md                   # Deployment instructions
```

## 🔧 Technical Details

### Key Features

1. **URL Creation**: Create short URLs from long URLs with validation
2. **URL Validation**: Ensures URLs start with http:// or https://
3. **URL History/Fetch**: View all created URLs with status and expiration info
4. **URL Deletion**: Delete unwanted short URLs from the system
5. **Duplicate Prevention**: Returns existing short URL for same original URL
6. **Expiration Handling**: Links expire after 5 minutes for security
7. **Expired URL Detection**: Shows expiration status and time remaining
8. **Not Found Handling**: Proper error messages for non-existent URLs
9. **Random Code Generation**: 8-character alphanumeric codes
10. **Database Persistence**: H2 in-memory database with JPA
11. **Copy to Clipboard**: Easy sharing of shortened URLs
12. **Real-time Status**: Shows if URLs are active or expired

### API Endpoints

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/` | Serve React frontend | HTML page |
| POST | `/api/url/shorten` | Create short URL | `{"shortUrl": "http://localhost:7070/r/abc123"}` |
| GET | `/api/url/history` | Fetch all URLs with status | Array of URL objects with expiration info |
| DELETE | `/api/url/delete/{shortCode}` | Delete a short URL | `{"message": "URL deleted successfully"}` |
| GET | `/r/{shortCode}` | Redirect to original URL | 302 redirect or error page |
| GET | `/api/url/{shortCode}` | Get original URL (API) | 302 redirect or error message |
| GET | `/api/url/test` | Test API connectivity | `{"message": "API is working!"}` |

### Expiration & Error Handling

#### Expired URLs
- URLs automatically expire after **5 minutes**
- Expired URLs show friendly error page with navigation back to app
- History shows expiration status and time remaining
- Error message: "⏰ Link Expired - This short URL has expired after 5 minutes for security reasons."

#### Not Found URLs
- Non-existent short codes return 404 with error page
- Error message: "🔍 Short URL Not Found - The requested short URL does not exist."
- Provides navigation back to create new URLs

#### Invalid URLs
- Input validation prevents invalid URL formats
- Only accepts URLs starting with `http://` or `https://`
- Real-time validation feedback in the UI

### Dependencies

- **Spring Boot 2.5.5**
- **Spring Data JPA**
- **H2 Database**
- **React 18** (for frontend)

### Database Schema

```sql
CREATE TABLE url_mappings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_code VARCHAR(16) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
);
```

## 🎨 UI Features

The application features:

- **Clean, modern design** with gradient backgrounds and animations
- **URL Input & Validation** with real-time feedback
- **One-click URL shortening** with loading indicators
- **Copy to clipboard** functionality for easy sharing
- **URL History Dashboard** showing all created URLs
- **Expiration Status** with countdown timers
- **Delete URLs** with confirmation dialogs
- **Responsive design** that works on mobile and desktop
- **Error handling** with clear, user-friendly messages
- **Professional color scheme** with intuitive icons
- **Real-time updates** when URLs are created or deleted
- **Duplicate detection** alerts for existing URLs
- **Status indicators** (Active/Expired) for each URL

### Frontend Features in Detail

- **URL Creation**: Enter any valid URL and get a shortened version instantly
- **URL Management**: View, copy, and delete your shortened URLs
- **History Tracking**: See all URLs you've created with timestamps
- **Expiration Monitoring**: Visual indicators show when URLs will expire
- **Error Prevention**: Input validation prevents invalid URL submission
- **User Feedback**: Success/error messages for all actions
- **Accessibility**: Keyboard navigation and screen reader support

## 🔒 Security Features

1. **URL Validation**: Only accepts valid HTTP/HTTPS URLs
2. **Time-based Expiration**: Links expire after 5 minutes
3. **Input Sanitization**: Prevents XSS attacks
4. **CORS Configuration**: Proper cross-origin handling

## 🧪 Testing Examples

### Example 1: Google

```
Input: https://www.google.com
Output: http://localhost:7070/xY9kLm2n
```

### Example 2: Long URL

```
Input: https://www.example.com/very/long/path/with/parameters?id=123&name=test
Output: http://localhost:7070/pQ7rT4vW
```

## ❓ Troubleshooting

### Common Issues

**Issue**: "Port 7070 already in use"

```bash
# Kill process using port 7070
lsof -ti:7070 | xargs kill -9
```

**Issue**: "Java not found"

- Make sure Java 17+ is installed
- Check with: `java -version`
- Download from: [https://adoptium.net/](https://adoptium.net/)

**Issue**: "npm not found"

- Install Node.js from: [https://nodejs.org/](https://nodejs.org/)
- Or skip React development and use the pre-built version

**Issue**: React build not reflecting changes

```bash
# Rebuild and sync
cd client && npm run build
cd .. && rm -rf server/src/main/resources/static/*
cp -r client/build/* server/src/main/resources/static/
cd server && ./mvnw spring-boot:run
```

### Verify Installation

```bash
# Check Java version
java -version

# Check if application is running
curl http://localhost:7070

# Check API endpoint
curl -X POST http://localhost:7070/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'
```

## 📝 Assignment Requirements ✅

- [x] **UI for URL input** - Beautiful React/HTML interface
- [x] **Generate short URL** - 8-character random codes
- [x] **Redirect functionality** - Works seamlessly
- [x] **Example working** - Google.com → localhost:7070/code → Google.com
- [x] **Code Quality** - Clean, modular, well-documented
- [x] **End-user Experience** - Professional UI/UX
- [x] **Technology Stack** - Java, Spring Boot, React, H2 Database

## 🎯 Bonus Features Implemented

### Core URL Management
1. **URL Creation** - Generate short URLs with validation
2. **URL Deletion** - Remove unwanted URLs with confirmation
3. **URL History/Fetch** - View all created URLs with status
4. **Duplicate Detection** - Reuses existing short URLs for same original URL

### Advanced Features
5. **Link Expiration** - 5-minute security timeout with countdown
6. **Expired URL Handling** - Friendly error pages for expired links
7. **Not Found Handling** - 404 error pages for non-existent URLs
8. **Real-time Status** - Shows active/expired status for all URLs

### User Experience
9. **Copy to Clipboard** - One-click sharing functionality
10. **Error Handling** - Comprehensive error messages and validation
11. **Loading States** - Visual feedback during operations
12. **Confirmation Dialogs** - Prevent accidental deletions

### Design & Accessibility
13. **Responsive Design** - Mobile-friendly interface
14. **Professional UI** - Modern design with animations
15. **Input Validation** - Real-time URL format checking
16. **Status Indicators** - Visual cues for URL states

### Technical Excellence
17. **Production Ready** - Railway deployment configuration
18. **API Testing** - Comprehensive endpoint testing
19. **Error Recovery** - Graceful handling of all error scenarios
20. **Database Management** - Efficient CRUD operations

---

**🎉 Ready to use!** The application is production-ready with comprehensive features and deployment configuration.

**Technologies Used**: Java, Spring Boot, React, H2 Database, HTML5, CSS3, JavaScript ES6+



