/*
 * File: AppointmentServiceArray.java
 * Author: Wilfredo Mendez
 * Version: 1
 *
 * AppointmentServiceArrayList defines how to manage a collection of appointments using
 * an ArrayList where each appointment is added to a list.
 * This implementation has linear time complexity for lookups, additions, and deletions,
 * making it less efficient than the HashMap approach.
 *
 * Time Complexity of Operations:
 * In this class, the time complexity for key operations is as follows:
 * - Addition: O(n) because ID uniqueness validation is required.
 * - Deletion: O(n) due to the need to search for the matching appointment.
 * - ID Validation: O(n) since every element must be checked to ensure uniqueness.
 * - Retrieval: O(n) when searching for specific elements, as there is no direct access.
 *
 * Implementation Trade-offs:
 * - Strengths:
 *   - Preserves insertion order, which can be helpful for chronological appointment tracking.
 *   - Simple to implement and sufficient for small datasets.
 * - Weaknesses:
 *   - Performance degrades as the dataset grows due to O(n) operations.
 *   - A HashMap (in a separate version) offers O(1) operations for addition, deletion,
 *     and lookups, making it more optimal for larger datasets where performance is critical.
 * -Overall:
 * ArrayList-based approach is easy to implement and works well for small
 * datasets, but its linear time complexity can make it inefficient for larger, dynamic data.
 */

package org.company;

import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceArray {
    // ArrayList to store all appointments
    // Each addition, deletion, or lookup will involve linear time (O(n)).
    private List<Appointment> appointmentList;

    // Constructor initializes the ArrayList for appointments
    public AppointmentServiceArray() {
        this.appointmentList = new ArrayList<>(); // Create a new empty ArrayList
    }

    // Adds a new appointment to the list
    // Time Complexity: O(n) due to ID uniqueness check, even though adding to the list itself is O(1).
    public void addAppointment(Appointment appointment) {
        validateUniqueAppointmentID(appointment.getAppointmentID()); // Check if the ID is unique
        appointmentList.add(appointment); // Add the appointment to the list
    }

    // Deletes an appointment by its ID
    // Time Complexity: O(n) for both searching the appointment and removing it.
    public void deleteAppointment(String appointmentID) {
        Appointment appointmentToRemove = null;

        // Search for the appointment by iterating through the list (O(n) time).
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointmentToRemove = appointment;
                break; // Exit loop once the appointment is found
            }
        }
        if (appointmentToRemove != null) {
            appointmentList.remove(appointmentToRemove); // Removal is O(n) due to shifting elements.
        } else {
            throw new IllegalArgumentException("This appointment was already deleted or does not exist");
        }
    }

    // Checks if an appointment ID is unique before adding a new appointment
    // Time Complexity: O(n) because it checks each element in the list for duplicates.
    private void validateUniqueAppointmentID(String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                throw new IllegalArgumentException("An appointment with this ID already exists");
            }
        }
    }

    // Returns the collection of all appointments
    // Time Complexity: O(1) as it simply returns the reference to the list.
    public List<Appointment> getAppointmentList() {
        return appointmentList; // Provide access to the appointment list
    }
}

