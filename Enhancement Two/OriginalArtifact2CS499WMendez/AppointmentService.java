/*
 * File: AppointmentService.java
 * Author: Wilfredo Mendez
 * Version: 2
 * 
 *
 * AppointmentService defines how to manage a collection of appointments using 
 * a HashMap where the key is the appointment ID and the value is the Appointment object.
 * This change enhances performance by allowing constant time lookups for adding, 
 * retrieving, and deleting appointments. 
 *
*/

package com.company;

import java.util.HashMap;
import java.util.Map;

public class AppointmentService {
    // HashMap to store all appointments using their ID as the key
    private Map<String, Appointment> appointmentMap;

    // Constructor initializes the HashMap for appointments
    public AppointmentService() {
        this.appointmentMap = new HashMap<>(); // Create a new empty HashMap
    }

    // Adds a new appointment to the map
    public void addAppointment(Appointment appointment) {
        validateUniqueAppointmentID(appointment.getAppointmentID()); // Check if the ID is unique
        appointmentMap.put(appointment.getAppointmentID(), appointment); // Add the appointment to the map
    }

    // Deletes an appointment by its ID
    public void deleteAppointment(String appointmentID) {
        if (appointmentMap.remove(appointmentID) == null) {
            throw new IllegalArgumentException("This appointment was already deleted or does not exist"); // Handle case where ID is not found
        }
    }

    // Checks if an appointment ID is unique before adding a new appointment
    private void validateUniqueAppointmentID(String appointmentID) {
        if (appointmentMap.containsKey(appointmentID)) {
            throw new IllegalArgumentException("An appointment with this ID already exists"); // Prevent duplicate IDs
        }
    }

    // Returns the collection of all appointments
    public Map<String, Appointment> getAppointmentMap() {
        return appointmentMap; // Provide access to the appointment map
    }
}


