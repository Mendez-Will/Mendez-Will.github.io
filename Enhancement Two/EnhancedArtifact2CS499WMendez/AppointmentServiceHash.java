/*
 * File: AppointmentServiceHash.java
 * Author: Wilfredo Mendez
 * Version: 2
 *
 * The AppointmentServiceHash class manages a collection of appointments using
 * a HashMap. In this version, the key is the appointment ID (a unique
 * identifier), and the value is the corresponding Appointment object.
 * This design choice significantly improves performance by enabling constant
 * time complexity (O(1)) for adding, retrieving, and deleting appointments,
 * making it efficient for handling a large number of appointments.
 *
 * Time Complexity of Operations:
 * - Addition: O(1) since adding an appointment involves inserting the key-value pair.
 * - Deletion: O(1) by directly removing the entry based on the key.
 * - Lookup: O(1) for checking if a key exists or retrieving an appointment.
 * - ID Validation: O(1) by using `containsKey()` to check for duplicates.
 *
 * Implementation Trade-offs:
 * - Strengths:
 *   - Constant time operations make it well-suited for large datasets.
 *   - No performance degradation as more appointments are added.
 * - Weaknesses:
 *   - Does not maintain the order of appointments, which might be necessary in some cases.
 *   - Slightly more complex memory management compared to ArrayList.
 * Overall:
 * A HashMap-based approach is ideal for handling a dynamic and large
 * number of appointments where fast operations are required. However, it may not be
 * suited for some applications where specific ordering is required, as it does not
 * retain natural orders that comes with an ArrayList Implementation.
 *
 */

package org.company;

import java.util.HashMap;
import java.util.Map;

public class AppointmentServiceHash {
    // HashMap to store all appointments, with appointment ID as the key and
    // the corresponding Appointment object as the value.
    private Map<String, Appointment> appointmentMap;

    // Constructor that initializes the HashMap for storing appointments.
    public AppointmentServiceHash() {
        this.appointmentMap = new HashMap<>(); // Create a new empty HashMap for appointments
    }

    // Method to add a new appointment to the HashMap
    public void addAppointment(Appointment appointment) {
        // Validate that the appointment ID is unique before adding
        validateUniqueAppointmentID(appointment.getAppointmentID());
        // Add the appointment to the map using its ID as the key
        appointmentMap.put(appointment.getAppointmentID(), appointment);
    }

    // Method to delete an appointment by its unique ID
    public void deleteAppointment(String appointmentID) {
        // Remove the appointment from the map; throws an exception if not found
        if (appointmentMap.remove(appointmentID) == null) {
            // Handle case where the ID is not found, indicating it was already deleted or does not exist
            throw new IllegalArgumentException("This appointment was already deleted or does not exist");
        }
    }

    // Private method to check if an appointment ID is unique before adding a new appointment
    private void validateUniqueAppointmentID(String appointmentID) {
        // Check if the map already contains the given appointment ID
        if (appointmentMap.containsKey(appointmentID)) {
            // Prevent duplicate IDs by throwing an exception
            throw new IllegalArgumentException("An appointment with this ID already exists");
        }
    }

    // Method to return the entire collection of appointments as a map
    public Map<String, Appointment> getAppointmentMap() {
        // Provide access to the appointment map for retrieval or inspection
        return appointmentMap;
    }
}
