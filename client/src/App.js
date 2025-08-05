import React, { useState } from 'react';
import './App.css';

const App = () => {
  const [url, setUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [history, setHistory] = useState([]);
  const [historyLoading, setHistoryLoading] = useState(false);

  const isValidUrl = (string) => {
    try {
      const url = new URL(string);
      return url.protocol === 'http:' || url.protocol === 'https:';
    } catch {
      return false;
    }
  };

  const fetchHistory = async () => {
    setHistoryLoading(true);
    try {
      const response = await fetch('/api/url/history');
      if (response.ok) {
        const data = await response.json();
        const formattedHistory = data.map(item => ({
          original: item.original,
          short: item.shortUrl, // Use the full shortUrl from backend
          timestamp: new Date(item.createdAt),
          isExpired: item.isExpired,
          expiresIn: item.expiresIn
        })).sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
        setHistory(formattedHistory);
      }
    } catch (err) {
      console.error('Failed to fetch history:', err);
    } finally {
      setHistoryLoading(false);
    }
  };

  // Load history on component mount
  React.useEffect(() => {
    fetchHistory();
  }, []);

  const shortenUrl = async () => {
    if (!url.trim()) {
      setError('Please enter a URL');
      return;
    }

    if (!isValidUrl(url)) {
      setError('Please enter a valid URL (must start with http:// or https://)');
      return;
    }

    // Check if URL already exists in history
    const existingEntry = history.find(item => item.original === url);
    if (existingEntry) {
      setError('');
      alert('🔄 This URL was already shortened! Using existing short URL.');
    }

    setLoading(true);
    setError('');

    try {
      const response = await fetch('/api/url/shorten', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ url }),
      });

      if (response.ok) {
        const data = await response.json();
        setShortUrl(data.shortUrl);
        // Refresh history from backend to get latest data
        fetchHistory();
      } else {
        const errorData = await response.text();
        setError(errorData || 'Failed to shorten URL');
      }
    } catch (err) {
      setError('Network error. Please make sure the server is running on port 7070.');
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = async (text) => {
    try {
      await navigator.clipboard.writeText(text);
      alert('Copied to clipboard!');
    } catch (err) {
      // Fallback for older browsers
      const textArea = document.createElement('textarea');
      textArea.value = text;
      document.body.appendChild(textArea);
      textArea.select();
      document.execCommand('copy');
      document.body.removeChild(textArea);
      alert('Copied to clipboard!');
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      shortenUrl();
    }
  };

  return (
    <div className="app">
      <div className="container">
        <header className="header">
          <h1>🔗 URL Shortener</h1>
          <p>Transform long URLs into short, shareable links</p>
        </header>

        <div className="url-form">
          <div className="input-group">
            <input
              type="text"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder="Enter your URL here (e.g., https://www.google.com)"
              className="url-input"
              disabled={loading}
            />
            <button
              onClick={shortenUrl}
              disabled={loading || !url.trim()}
              className="shorten-btn"
            >
              {loading ? 'Shortening...' : 'Shorten URL'}
            </button>
          </div>

          {error && (
            <div className="error-message">
              ❌ {error}
            </div>
          )}

          {shortUrl && (
            <div className="result">
              <h3>✅ Your shortened URL:</h3>
              <div className="short-url-container">
                <input
                  type="text"
                  value={shortUrl}
                  readOnly
                  className="short-url-input"
                />
                <button
                  onClick={() => copyToClipboard(shortUrl)}
                  className="copy-btn"
                >
                  📋 Copy
                </button>
              </div>
              <p className="note">
                ⏰ <strong>Note:</strong> This link will expire in 5 minutes for security.
              </p>
            </div>
          )}
        </div>

        {history.length > 0 && (
          <div className="history">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3>📋 URL History</h3>
              <button 
                onClick={fetchHistory} 
                disabled={historyLoading}
                style={{ 
                  padding: '8px 16px', 
                  borderRadius: '6px', 
                  border: '1px solid #ddd',
                  background: '#f8f9fa',
                  cursor: 'pointer'
                }}
              >
                {historyLoading ? '🔄 Loading...' : '🔄 Refresh'}
              </button>
            </div>
            <div className="history-list">
              {history.map((item, index) => (
                <div key={index} className={`history-item ${item.isExpired ? 'expired' : ''}`}>
                  <div className="original-url">
                    <strong>Original:</strong> {item.original}
                  </div>
                  <div className="short-url">
                    <strong>Short:</strong> 
                    <span 
                      className="clickable-url" 
                      onClick={() => copyToClipboard(item.short)}
                      title="Click to copy"
                    >
                      {item.short}
                    </span>
                    {item.isExpired ? (
                      <span className="status-badge expired">❌ EXPIRED</span>
                    ) : (
                      <span className="status-badge active">
                        ✅ ACTIVE ({item.expiresIn}min left)
                      </span>
                    )}
                  </div>
                  <div className="timestamp">
                    {item.timestamp.toLocaleString()}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        <footer className="footer">
          <p>
            💡 <strong>How it works:</strong> Enter any URL, get a short link, and share it anywhere!
            <br />
            🔒 Links expire after 5 minutes for security.
          </p>
        </footer>
      </div>
    </div>
  );
};

export default App;