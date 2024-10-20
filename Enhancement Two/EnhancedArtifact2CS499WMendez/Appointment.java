/*
 * File: Appointment.java
 * Author: Wilfredo Mendez
 * Version: 2
 * 
 * The Appointment class defines an appointment object with three main attributes: 
 * appointmentID, appointmentDate, and description. It also provides validation for 
 * these fields and ensures data integrity. No changes were made to this class as part 
 * of the AppointmentService enhancement, as it works with the new data structure,
 *  which focuses on switching to a HashMap for managing appointments.
 * 
*/

package org.company;

import java.util.Date;

public class Appointment {
    private String appointmentID;
    private Date appointmentDate;
    private String description;

    // Constructor to initialize the appointment with ID, date, and description
    public Appointment(String appointmentID, Date appointmentDate, String description) {
        setAppointmentID(appointmentID); // Validate and set the appointment ID
        setAppointmentDate(appointmentDate); // Validate and set the appointment date
        setDescription(description); // Validate and set the description
    }

    public Appointment(String number, String s, String date, String s1) {
    }

    // Sets the appointment ID with validation
    public void setAppointmentID(String appointmentID) {
        if (appointmentID == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null"); // User should always provide an ID
        } else if (!appointmentID.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("Appointment ID contains invalid characters"); // Only alphanumeric characters are allowed
        } else if (appointmentID.length() > 10) {
            throw new IllegalArgumentException("The appointment ID cannot be longer than 10 characters. Your current appointment ID is: " + appointmentID.length() + " long."); // Enforce ID length limit for consistency
        } else {
            this.appointmentID = appointmentID; // Set the ID if all checks pass
        }
    }

    // Sets the appointment date with validation
    public void setAppointmentDate(Date appointmentDate) {
        if (appointmentDate == null) {
            throw new IllegalArgumentException("Appointment date cannot be null"); // User must provide a date
        } else if (appointmentDate.before(new Date())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past"); // Prevent scheduling past dates
        } else {
            this.appointmentDate = appointmentDate; // Set the date if valid
        }
    }

    // Sets the appointment description with validation
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null"); // User must provide a description
        } else if (description.length() > 50) {
            throw new IllegalArgumentException("The description cannot be longer than 50 characters. You entered a description " + description.length() + " characters long"); // Limit description length for readability
        } else {
            this.description = description; // Set the description if valid
        }
    }

    // Getter for appointment ID
    public String getAppointmentID() {
        return appointmentID; // Return the appointment ID
    }

    // Getter for appointment date
    public Date getAppointmentDate() {
        return appointmentDate; // Return the appointment date
    }

    // Getter for appointment description
    public String getDescription() {
        return description; // Return the appointment description
    }
}

