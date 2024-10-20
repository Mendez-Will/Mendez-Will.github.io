// Created by Wilfredo Mendez III
// Created on 10/5/2024
// Version 1
// This code dynamically fetches animal data from an API and displays it in a table format.
// It also renders a Chart.js chart and a Plotly.js graph based on the fetched data.
// For future enhancements, consider connecting to a database directly to retrieve animal data.

document.addEventListener('DOMContentLoaded', () => {
    // Fetch data from the /api/animals endpoint
    fetch('/api/animals')
    .then(response => response.json()) // Convert response to JSON format
    .then(animals => {
        // Log the fetched animal data in the browser console for debugging
        console.log("Animal data fetched successfully:", animals);

        // Populate the HTML table with the fetched animal data
        const tableBody = document.getElementById('animals-table-body');
        animals.forEach(animal => {
            // Create a new table row for each animal entry
            const row = document.createElement('tr');
            // Insert the animal data into the respective table columns
            row.innerHTML = `
                <td>${animal.id}</td>
                <td>${animal.name}</td>
                <td>${animal.species}</td>
                <td>${animal.location.lat}, ${animal.location.lng}</td>
                <td>${animal.rescueType}</td>
            `;
            // Append the created row to the table body
            tableBody.appendChild(row);
        });

        // After populating the table, render the chart and graph
        renderChart(animals);       // Call the function to render a Chart.js chart
        renderPlotlyGraph(animals); // Call the function to render a Plotly.js graph
    })
    .catch(error => {
        // Log any errors encountered during the fetch operation in the console
        console.error('Error fetching animals:', error);
    });
});

// Function to render a Chart.js chart based on animal species count
function renderChart(animals) {
    // Log the animal data being passed to the chart rendering function
    console.log("Rendering Chart.js chart with animals:", animals);

    // Get the 2D drawing context for the chart canvas
    const ctx = document.getElementById('animalChart').getContext('2d');
    const speciesCount = {};

    // Loop through the animal data and count occurrences of each species
    animals.forEach(animal => {
        speciesCount[animal.species] = (speciesCount[animal.species] || 0) + 1;
    });

    // Prepare data for the chart, using species names as labels and their counts as data
    const chartData = {
        labels: Object.keys(speciesCount), // Species names
        datasets: [{
            label: 'Number of Animals by Species',
            data: Object.values(speciesCount), // Number of animals per species
            backgroundColor: 'rgba(75, 192, 192, 0.2)', // Bar color
            borderColor: 'rgba(75, 192, 192, 1)',       // Bar border color
            borderWidth: 1                              // Bar border width
        }]
    };

    // Create and display the bar chart using the Chart.js library
    const myChart = new Chart(ctx, {
        type: 'bar', // Specify the chart type as a bar chart
        data: chartData,
        options: {
            scales: {
                y: {
                    beginAtZero: true // Ensure the y-axis starts at zero
                }
            }
        }
    });
}

// Function to render a Plotly.js scatter plot using animal data
function renderPlotlyGraph(animals) {
    // Log the animal data being passed to the Plotly graph rendering function
    console.log("Rendering Plotly.js graph with animals:", animals);

    // Prepare data for the Plotly graph, using animal names for x-axis and latitude for y-axis
    const data = [{
        x: animals.map(animal => animal.name),  // X-axis data: animal names
        y: animals.map(animal => animal.location.lat), // Y-axis data: latitudes
        type: 'scatter' // Define the plot type as a scatter plot
    }];

    // Render the scatter plot using Plotly.js
    Plotly.newPlot('plotlyGraph', data);
}

// Notes for future development:
// 1. To connect to a database for fetching animal data instead of the API, 
//    consider replacing the fetch logic with a database query.
// 2. If using MongoDB, install Mongoose and create a model for data.
// 3. Fetch data using Mongoose's find() method and replace the fetch call.
// 4. Update the server code to expose an endpoint that retrieves data from the database.
