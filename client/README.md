# 🔗 URL Shortener - React Frontend

This is the React frontend for the URL Shortener application built for the Full Stack Engineer assignment.

## 🚀 Quick Start

Make sure the backend server is running first:
```bash
# In the server directory
cd ../server
./mvnw spring-boot:run
```

Then start the React frontend:
```bash
# In this client directory
npm start
```

The React app will open at [http://localhost:3000](http://localhost:3000) and automatically proxy API calls to the backend server running on port 7070.

## ✨ Features

- **Modern UI**: Beautiful gradient design with animations
- **Real-time Validation**: URL format checking
- **Error Handling**: Clear error messages  
- **Copy to Clipboard**: One-click copying
- **Responsive Design**: Works on mobile and desktop
- **URL History**: Shows recent shortened URLs
- **Link Expiration**: 5-minute security timeout

## 🔧 Configuration

The app is configured with a proxy to `http://localhost:7070` in `package.json` to avoid CORS issues during development.

## 📱 Usage

1. Enter any valid URL (must start with http:// or https://)
2. Click "Shorten URL"
3. Copy the generated short URL
4. Share it anywhere - it will redirect to your original URL!

## 🔗 API Integration

The frontend communicates with the Spring Boot backend via these endpoints:
- `POST /api/url/shorten` - Create a short URL
- `GET /{shortCode}` - Redirect to original URL

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
