# URL Shortener - Deployment Guide

## 🚀 **LIVE DEPLOYMENT FOR BONUS POINTS**

This guide will help you deploy the URL Shortener application to get those bonus points! The application is now a single JAR file that contains both the React frontend and Spring Boot backend.

## Application Overview

- **Frontend**: React app served from Spring Boot static resources
- **Backend**: Spring Boot REST API with H2 database
- **Single Port**: Everything runs on one port (no CORS issues!)
- **Built JAR**: `server/target/notes-0.0.1-SNAPSHOT.jar`

## 🌐 Deployment Options

### Option 1: Railway (Recommended - Easiest)

Railway is perfect for Spring Boot applications and offers free tier.

#### Step 1: Setup Railway Account
1. Go to [railway.app](https://railway.app)
2. Sign up with GitHub account
3. Install Railway CLI: `npm install -g @railway/cli`

#### Step 2: Deploy
```bash
cd server
railway login
railway new  # Create new project
railway add  # Add service
railway up   # Deploy!
```

#### Files Already Created:
- ✅ `Procfile`: `web: java -Dserver.port=$PORT -jar target/*.jar`
- ✅ `system.properties`: `java.runtime.version=17`

### Option 2: Render

Render is another great free option for Java applications.

#### Step 1: Setup
1. Go to [render.com](https://render.com)
2. Connect your GitHub repository
3. Create new Web Service

#### Step 2: Configure
- **Build Command**: `cd server && ./mvnw clean package -DskipTests`
- **Start Command**: `cd server && java -Dserver.port=$PORT -jar target/*.jar`
- **Environment**: Java 17

### Option 3: Heroku

Classic choice, but requires credit card for verification.

#### Step 1: Setup
```bash
cd server
heroku create your-url-shortener-app
git add .
git commit -m "Deploy URL shortener"
git push heroku main
```

## 🧪 Local Testing

Before deploying, test the complete application:

```bash
cd server
java -jar target/notes-0.0.1-SNAPSHOT.jar
```

Open browser to `http://localhost:7070` - you should see the React frontend!

## 📋 How It Works

### URL Shortening Flow:
1. User enters URL in React frontend
2. Frontend sends POST to `/api/url/shorten`
3. Backend generates short code and saves to H2 database
4. Returns short URL like: `https://your-app.railway.app/r/abc12345`

### Redirect Flow:
1. User clicks short URL
2. Backend looks up original URL
3. Redirects user to original URL (if not expired)

### Key Endpoints:
- `GET /` → React frontend
- `POST /api/url/shorten` → Create short URL
- `GET /r/{shortCode}` → Redirect to original URL

## 🔧 Technical Details

### Database:
- H2 in-memory database
- Automatically creates `url_mapping` table
- Data persists during app lifetime

### Expiration:
- URLs expire after 5 minutes
- Configurable in `UrlShortenerService.java`

### Error Handling:
- Invalid URLs rejected
- Expired URLs return error message
- Missing URLs return 404

## 🎯 Testing Your Deployment

Once deployed, test these scenarios:

1. **Frontend Access**: Visit your app URL - should see React interface
2. **URL Shortening**: Enter a URL, click "Shorten URL"
3. **Copy to Clipboard**: Click copy button, paste somewhere
4. **Redirect**: Click the short URL - should redirect to original
5. **Expiration**: Wait 5 minutes, try short URL again

## 🏆 Bonus Points Achieved!

Your URL shortener will be publicly accessible at:
- Railway: `https://your-app-name.railway.app`
- Render: `https://your-app-name.onrender.com`
- Heroku: `https://your-app-name.herokuapp.com`

Perfect for demonstrating your full-stack skills!

## 📝 Quick Deploy Script

Save this as `deploy.sh` in the server directory:

```bash
#!/bin/bash
echo "🚀 Deploying URL Shortener..."

# Build the application
./mvnw clean package -DskipTests

echo "✅ Build complete!"
echo "📦 JAR file: target/notes-0.0.1-SNAPSHOT.jar"
echo ""
echo "Choose deployment option:"
echo "1. Railway: railway up"
echo "2. Render: Push to GitHub and configure"
echo "3. Heroku: git push heroku main"
echo ""
echo "🎯 Your app will be publicly accessible for bonus points!"
```

Run with: `chmod +x deploy.sh && ./deploy.sh`

---

**Ready for deployment! 🚀 Good luck with those bonus points!**
