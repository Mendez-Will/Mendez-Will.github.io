// Created by Wilfredo Mendez III
// Created on 10/2/2024
// Version 1
// This code defines an Express router that handles GET requests to retrieve animal data.
// Currently, the data is read from a text file, parsed into JSON, and then sent as a response.
// To enhance this code, connect it to a database for more efficient data management.

const express = require('express'); // Import the Express framework
const router = express.Router();    // Create a new Express router
const fs = require('fs');           // Import file system module to read files
const path = require('path');       // Import path module to work with file paths
// Uncomment the following line if using a database like MongoDB
// const Animal = require('../models/Animal'); // Import the Animal model for database operations

// GET request handler to retrieve all animals
router.get('/', async (req, res) => {
    console.log("GET /api/animals called"); // Log when the /api/animals endpoint is accessed

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
    // Read the animals.txt file from the current directory
    fs.readFile(path.join(__dirname, 'animals.txt'), 'utf8', (err, data) => {
        if (err) {
            // Log error and respond with a 500 status code if there's an issue reading the file
            console.error('Error reading file:', err);
            return res.status(500).send('Server error'); // Send error response
        }

        try {
            // Parse the file data as JSON
            const animals = JSON.parse(data);
            res.json(animals); // Send the parsed JSON data as the response
        } catch (parseError) {
            // Log and return an error if there is an issue parsing the JSON data
            console.error('Error parsing JSON:', parseError);
            res.status(500).send('Error parsing data'); // Send error response
        }
    });
});

module.exports = router; // Export the router for use in other parts of the application

