const express = require('express');
const app = express();

app.use(express.static('public')); // Serve static files from the "public" directory


// Serve the main HTML page
app.get('/', (req, res) => {
  res.setHeader('Permissions-Policy', 'screen-wake-lock=(self), display-capture=(self http://localhost:3000 https://app-api.emmersion.ai)');
  res.sendFile(__dirname + '/public/index.html');
});

// Serve the HTML file for the iframe
app.get('/iframe', (req, res) => {
  res.sendFile(__dirname + '/public/iframe.html');
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
