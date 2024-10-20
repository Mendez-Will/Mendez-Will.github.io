package org.company;

/**
 * File: AppointmentServiceArrayTest.java
 * Author: Wilfredo Mendez
 * Version: 2
 * 
 * Description:
 * This test class verifies the functionality of the AppointmentServiceArray class. 
 * It includes test cases to ensure appointments are added, handled for duplicate IDs, 
 * deleted correctly, and retrieved as expected. These tests help confirm the correctness 
 * and reliability of the appointment management system.
 *
   Testing Strategy Overview:
 *    Test Setup: The @BeforeEach annotation ensures that a fresh instance of
 *    AppointmentServiceArray is created before each test. This guarantees that
 *    each test case runs independently.
 *
 * Test Cases:
 *    - Adding an Appointment: Ensures new appointments are correctly stored.
 *    - Handling Duplicates: Confirms that adding an appointment with an existing
 *      ID throws the appropriate exception.
 *    - Deleting Non-existent Appointments: Verifies that invalid deletions raise
 *      an exception, preventing silent failures.
 *    - Retrieving All Appointments: Ensures the service returns the correct list
 *      of stored appointments.
 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Test class for the AppointmentServiceArray
public class AppointmentServiceArrayTest {
    private AppointmentServiceArray appointmentService;
    private Appointment appointment;

    // Setting up the test environment before each test
    @BeforeEach
    public void setUp() {
        // Initialize the AppointmentServiceArray instance
        appointmentService = new AppointmentServiceArray();
        // Create a new Appointment object for testing
        appointment = new Appointment("1", "Doctor's Appointment", "2024-10-10", "10:00 AM");
    }

    // Test for adding an appointment successfully
    @Test
    public void testAddAppointment() {
        // Add the appointment to the service
        appointmentService.addAppointment(appointment);
        // Assert that the appointment was added correctly
        assertEquals(1, appointmentService.getAppointmentList().size());
        assertEquals(appointment, appointmentService.getAppointmentList().get(0));
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
    public void testGetAppointmentList() {
        // Add the appointment to the service
        appointmentService.addAppointment(appointment);
        // Assert that the appointment list contains the added appointment
        assertEquals(1, appointmentService.getAppointmentList().size());
        assertTrue(appointmentService.getAppointmentList().contains(appointment));
    }
}
