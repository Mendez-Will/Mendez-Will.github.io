/*
 * File: AppointmentTest.java
 * Author: Wilfredo Mendez
 * Version: 2
 * 
 * This file contains unit tests for the Appointment class. It tests the creation of valid 
 * appointments as well as the validation logic for invalid appointment IDs, dates, and descriptions. 
 * The tests ensure that appointments adhere to the constraints specified in the Appointment class 
 * regarding ID format, date validity, and description length.
*/

package org.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class AppointmentTest {

    // Test the creation of a valid appointment
    @Test
    void testAppointmentCreation() {
        Appointment appointment = new Appointment("001", new Date(), "Test Appointment"); // Create a new valid appointment
        // Check that the appointment ID is set correctly
        Assertions.assertEquals("001", appointment.getAppointmentID());
        // Verify that the appointment date is not null
        Assertions.assertNotNull(appointment.getAppointmentDate());
        // Ensure that the description is set correctly
        Assertions.assertEquals("Test Appointment", appointment.getDescription());
    }

    // Test invalid appointment IDs
    @Test
    void testInvalidAppointmentID() {
        // Check that creating an appointment with a null ID throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment(null, new Date(), "Test Appointment"));
        // Check that creating an appointment with invalid characters in the ID throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment("invalid@", new Date(), "Test Appointment"));
        // Check that creating an appointment with an ID that's too long throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment("TooLongAppointmentID", new Date(), "Test Appointment"));
    }

    // Test invalid appointment dates
    @Test
    void testInvalidAppointmentDate() {
        // Check that creating an appointment with a past date throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment("001", new Date(System.currentTimeMillis() - 1000000), "Test Appointment"));
    }

    // Test invalid appointment descriptions
    @Test
    void testInvalidDescription() {
        // Check that creating an appointment with a null description throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment("001", new Date(), null));
        // Check that creating an appointment with a description that's too long throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Appointment("001", new Date(), "This description is too long and exceeds the limit of 50 characters."));
    }
}


