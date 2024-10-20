package org.company;


/**
 * File: AppointmentServiceHashTest.java
 * Author: Wilfredo Mendez
 * Version: 2
 *
 * Description:
 * AppointmentServiceHashTest class is responsible for testing the functionality 
 * of the AppointmentServiceHash class. It includes various test cases to 
 * verify the correct handling of appointments, including adding, deleting, 
 * and retrieving appointments.
 *
 * Testing Strategy Overview:
 *    Test Setup: A fresh instance of AppointmentServiceHash is created before each test
 *    using the @BeforeEach annotation to ensure independent test cases.
 *
 * Test Cases:
 *    - Adding an Appointment: Ensures new appointments are correctly stored in the HashMap.
 *    - Handling Duplicates: Confirms that an exception is thrown when adding an appointment
 *      with an existing ID.
 *    - Deleting Appointments: Verifies that appointments are properly removed from the HashMap.
 *    - Handling Non-existent Deletions: Ensures exceptions are raised for invalid deletions.
 *    - Retrieving Appointments: Confirms that all appointments are correctly retrieved.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Test class for the AppointmentServiceHash
public class AppointmentServiceHashTest {
    private AppointmentServiceHash appointmentService;
    private Appointment appointment;

    // Setting up the test environment before each test
    @BeforeEach
    public void setUp() {
        // Initialize the AppointmentServiceHash instance
        appointmentService = new AppointmentServiceHash();
        // Create a new Appointment object for testing
        appointment = new Appointment("1", "Doctor's Appointment", "2024-10-10", "10:00 AM");
    }

    // Test for adding an appointment successfully
    @Test
    public void testAddAppointment() {
        // Add the appointment to the service
        appointmentService.addAppointment(appointment);
        // Assert that the appointment was added correctly
        assertEquals(appointment, appointmentService.getAppointmentMap().get(appointment.getAppointmentID()));
    }

    // Test for adding a duplicate appointment ID
    @Test
    public void testAddDuplicateAppointment() {
        // Add the first appointment
        appointmentService.addAppointment(appointment);
        // Attempt to add a second appointment with the same ID
        Appointment duplicateAppointment = new Appointment("1", "Follow-up Appointment", "2024-10-12", "11:00 AM");
        // Expect an IllegalArgumentException to be thrown
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.addAppointment(duplicateAppointment);
        });
    }

    // Test for deleting an appointment successfully
    @Test
    public void testDeleteAppointment() {
        // Add the appointment first
        appointmentService.addAppointment(appointment);
        // Now delete the appointment
        appointmentService.deleteAppointment(appointment.getAppointmentID());
        // Assert that the appointment has been deleted
        assertFalse(appointmentService.getAppointmentMap().containsKey(appointment.getAppointmentID()));
    }

    // Test for deleting a non-existent appointment
    @Test
    public void testDeleteNonExistentAppointment() {
        // Expect an IllegalArgumentException to be thrown when trying to delete a non-existent appointment
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.deleteAppointment("non-existent-id");
        });
    }

    // Test for retrieving all appointments
    @Test
    public void testGetAppointmentMap() {
        // Add the appointment to the service
        appointmentService.addAppointment(appointment);
        // Assert that the appointment map contains the added appointment
        assertEquals(1, appointmentService.getAppointmentMap().size());
        assertTrue(appointmentService.getAppointmentMap().containsKey(appointment.getAppointmentID()));
    }
}
