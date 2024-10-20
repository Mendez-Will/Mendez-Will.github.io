/*
 * File: AppointmentServiceTest.java
 * Author: Wilfredo Mendez
 * Version: 2
 * 
 * This file contains unit tests for the AppointmentService class, which now utilizes 
 * a HashMap to manage and store appointments by their unique IDs. The tests verify 
 * core functionality such as adding new appointments, preventing duplicates, and 
 * deleting existing appointments from the HashMap
*/

package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class AppointmentServiceTest {

    // Test adding an appointment to the service
    @Test
    void testAddAppointment() {
        AppointmentService service = new AppointmentService(); // Create a new service instance
        Appointment appointment = new Appointment("001", new Date(), "Test Appointment"); // Create a new appointment

        service.addAppointment(appointment); // Add the appointment to the service
        // Check that the map now contains 1 appointment
        Assertions.assertEquals(1, service.getAppointmentMap().size());
        // Verify that the appointment ID matches what was added
        Assertions.assertEquals("001", service.getAppointmentMap().get("001").getAppointmentID());
    }

    // Test adding a duplicate appointment
    @Test
    void testAddDuplicateAppointment() {
        AppointmentService service = new AppointmentService(); // Create a new service instance
        Appointment appointment1 = new Appointment("001", new Date(), "Test Appointment"); // First appointment
        Appointment appointment2 = new Appointment("001", new Date(), "Another Appointment"); // Second appointment with the same ID

        service.addAppointment(appointment1); // Add the first appointment
        // Check that adding a second appointment with the same ID throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppointment(appointment2));
    }

    // Test deleting an appointment
    @Test
    void testDeleteAppointment() {
        AppointmentService service = new AppointmentService(); // Create a new service instance
        Appointment appointment = new Appointment("001", new Date(), "Test Appointment"); // Create a new appointment

        service.addAppointment(appointment); // Add the appointment to the service
        Assertions.assertEquals(1, service.getAppointmentMap().size()); // Verify that the map contains 1 appointment
        service.deleteAppointment("001"); // Delete the appointment
        // Check that the map is now empty
        Assertions.assertEquals(0, service.getAppointmentMap().size());
    }

    // Test deleting a non-existent appointment
    @Test
    void testDeleteNonexistentAppointment() {
        AppointmentService service = new AppointmentService(); // Create a new service instance

        // Check that attempting to delete an appointment that doesn't exist throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment("001"));
    }
}

