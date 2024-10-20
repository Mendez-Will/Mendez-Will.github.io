// Created by Wilfredo Mendez III
// Created on 10/2/2024
// Version 3

// This server code uses Express to handle API requests and serves static frontend files.
// It reads animal data from a text file, parses it to JSON, and serves it through an API endpoint.
// To transition to using a database, you will need to replace the file reading logic with database queries.

const express = require('express'); // Import the Express framework
const cors = require('cors');       // Import CORS middleware for handling cross-origin requests
const path = require('path');       // Import path module to handle file paths
const fs = require('fs');           // Import file system module to read files
// Uncomment the following lines if using a database like MongoDB
// const mongoose = require('mongoose'); // Import Mongoose for MongoDB object modeling
// const Animal = require('./models/Animal'); // Import the Animal model for database operations

const app = express(); // Create an instance of the Express app
app.use(cors());       // Enable CORS to allow cross-origin access
app.use(express.json()); // Middleware to parse JSON request bodies

// Serve static files from the frontend directory
app.use(express.static(path.join(__dirname, '../frontend'))); 
console.log("Serving static files from frontend directory");

// Serve the index.html file when the root route ("/") is accessed
app.get('/', (req, res) => {
    console.log("Serving index.html"); // Log that index.html is being served
    res.sendFile(path.join(__dirname, '../frontend', 'index.html')); // Send the index.html file
});

// API endpoint to get animal data
app.get('/api/animals', async (req, res) => {
    console.log("GET /api/animals called"); // Log that the /api/animals route was called

    // Replace the following block with database retrieval logic
    // Uncomment the following lines for a MongoDB database connection:
    // try {
    //     const animals = await Animal.find(); // Fetch all animal records from the database
    //     console.log("Animals data retrieved successfully"); // Log successful data retrieval
    //     return res.json(animals); // Respond with the retrieved animal data in JSON format
    // } catch (dbError) {
    //     console.error('Error fetching data from database:', dbError); // Log database errors
    //     return res.status(500).send('Server error'); // Respond with a 500 status code
    // }

    // For now, we'll keep the file reading logic as a fallback
    // Read the animals.txt file from the backend directory
    fs.readFile(path.join(__dirname, '../backend/animals.txt'), 'utf8', (err, data) => {
        if (err) {
            // Log and return an error if there is an issue reading the file
            console.error('Error reading file:', err);
            return res.status(500).send('Server error'); // Respond with a 500 status code
        }

        try {
            // Parse the file data as JSON
            const animals = JSON.parse(data);
            console.log("Animals data read successfully"); // Log successful data read
            res.json(animals); // Respond with the parsed animal data in JSON format
        } catch (parseError) {
            // Log and return an error if the file data cannot be parsed as JSON
            console.error('Error parsing JSON:', parseError);
            return res.status(500).send('Error parsing data'); // Respond with a 500 status code
        }
    });
});

// Serve index.html for any unmatched routes (fallback for client-side routing)
app.get('*', (req, res) => {
    console.log(`Serving index.html for ${req.path}`); // Log the route being accessed
    res.sendFile(path.join(__dirname, '../frontend', 'index.html')); // Send index.html as fallback
});

// Start the server on port 5000 or a port defined in the environment variables
const PORT = process.env.PORT || 5000; 
app.listen(PORT, () => console.log(`Server running on port ${PORT}`)); // Log the server port

