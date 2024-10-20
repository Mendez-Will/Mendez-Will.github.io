/*
 * Author: Wilfredo Mendez
 * Version: 2
 *
 * This class conducts unit tests between the AppointmentServiceArray and AppointmentServiceHash
 * classes, which now employ a HashMap for efficient management and storage of appointments using unique IDs.
 * The tests validate key functionalities, including adding new appointments, deleting existing appointments,
 * and comparing performance metrics between an ArrayList and a HashMap.
 *
 * Time Complexity:
 * - ArrayList: O(n) for deletion and retrieval since it requires searching through the list.
 * - HashMap: O(1) for insertion, deletion, and retrieval because of its key-value structure.
 *
 * Optimization Comparison:
 * - ArrayList performs better with smaller datasets where order matters or frequent
 *   iteration is required.
 * - HashMap is optimized for larger datasets where fast access and search are critical.
 */

package org.company;

import java.util.Calendar;
import java.util.Date;

public class AppointmentServiceTestComparison {
    public static void main(String[] args) {
        System.out.println("Starting tests...");

        // Instantiate appointment service classes for ArrayList and HashMap implementations
        AppointmentServiceArray serviceArray = new AppointmentServiceArray();
        AppointmentServiceHash serviceHash = new AppointmentServiceHash();

        // Variables to measure execution time
        long startTime;
        long endTime;

        // Create a future date for the appointments (1 day from now as an example)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Increment the current date by 1 day
        Date futureDate = calendar.getTime();

        // Measure performance for adding appointments to the ArrayList
        startTime = System.nanoTime();
        System.out.println("Testing adding and deleting appointments in ArrayList:");
        for (int i = 0; i < 1000; i++) {
            // Create and add a new appointment with a unique ID and future date
            serviceArray.addAppointment(new Appointment("ID" + i, futureDate, "Description " + i));
        }
        endTime = System.nanoTime();
        System.out.println("ArrayList add time: " + (endTime - startTime) + " ns");

        // Measure performance for deleting appointments from the ArrayList
        startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            // Delete appointments using their unique IDs
            serviceArray.deleteAppointment("ID" + i);
        }
        endTime = System.nanoTime();
        System.out.println("ArrayList delete time: " + (endTime - startTime) + " ns");

        // Measure performance for adding appointments to the HashMap
        startTime = System.nanoTime();
        System.out.println("Testing adding and deleting appointments in HashMap:");
        for (int i = 0; i < 1000; i++) {
            // Create and add a new appointment with a unique ID and future date
            serviceHash.addAppointment(new Appointment("ID" + i, futureDate, "Description " + i));
        }
        endTime = System.nanoTime();
        System.out.println("HashMap add time: " + (endTime - startTime) + " ns");

        // Measure performance for deleting appointments from the HashMap
        startTime = System.nanoTime();
        for (int i = 0; i < 500; i++) {
            // Delete appointments using their unique IDs
            serviceHash.deleteAppointment("ID" + i);
        }
        endTime = System.nanoTime();
        System.out.println("HashMap delete time: " + (endTime - startTime) + " ns");

        // Conduct performance testing with a larger dataset
        testPerformanceWithLargeDataSet(serviceArray, serviceHash);
    }


    // Method to test performance with a large dataset (10,000 appointments)
    private static void testPerformanceWithLargeDataSet(AppointmentServiceArray serviceArray, AppointmentServiceHash serviceHash) {
        System.out.println("Testing large dataset now...");
        int testSize = 10000; // Number of appointments to add for performance testing
        long startTime, endTime;

        // Measure performance of adding appointments to the ArrayList
        startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            // Ensure unique ID for each appointment in the large data set
            serviceArray.addAppointment(new Appointment("A" + i, getFutureDate(1), "Description " + i));
        }
        endTime = System.nanoTime();
        System.out.println("ArrayList add time for " + testSize + " appointments: " + (endTime - startTime) + " ns");

        // Measure performance of adding appointments to the HashMap
        startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            // Ensure unique ID for each appointment in the large data set
            serviceHash.addAppointment(new Appointment("H" + i, getFutureDate(1), "Description " + i));
        }
        endTime = System.nanoTime();
        System.out.println("HashMap add time for " + testSize + " appointments: " + (endTime - startTime) + " ns");
    }


    // Helper method to generate a future date given a number of days from today
    private static Date getFutureDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days); // Add specified days to the current date
        return calendar.getTime();
    }
}

