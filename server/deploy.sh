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
