package com.vehiclerent.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Customer {
    //Number of Customer
    private static int customerCount = 0;

    //Identifier
    private String customerID;

    //Personal Information
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String address;
    private String drivingLicenseNumber;

    //Account Information
    private String password;
    private String dateRegistered;
    private boolean isActive;

    //Rental History
    private List <String> rentalHistory;


    //Constructor
    public Customer(String firstName, String lastName, String email, String contactNumber, String address, String drivingLicenseNumber, String password) {
        customerCount++;
        this.customerID = String.format("%04d",customerCount);
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email);
        setContactNumber(contactNumber);
        this.address = address;
        this.drivingLicenseNumber = drivingLicenseNumber;
        setPassword(password);
        this.dateRegistered = getCurrentFormattedDate();
        this.isActive = true;
        this.rentalHistory = new ArrayList<>();

    }

    //Getters and Setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Enhanced email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (Pattern.matches(emailRegex, email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        // Enhanced phone number validation
        String phoneRegex = "^\\d{10}$"; // Simple 10-digit format
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be empty");
        }
        if (Pattern.matches(phoneRegex, contactNumber)) {
            this.contactNumber = contactNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format (must be 10 digits)");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        if (drivingLicenseNumber == null || drivingLicenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Driving license number cannot be empty");
        }
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            if (isStrongPassword(password)) {
                // Store hashed password instead of plaintext
                this.password = hashPassword(password);
            } else {
                throw new IllegalArgumentException("Password must be at least 8 characters long, and include at least one uppercase letter, one lowercase letter, and one digit.");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    //Checks for a strong password
    public static boolean isStrongPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") && password.matches(".*\\d.*");
    }


    public String getDateRegistered() {
        return dateRegistered;
    }

    protected void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    // Method to get the current date in a formatted way
    private String getCurrentFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public boolean verifyPassword(String inputPassword) {
        try {
            //Hash the input password
            String hashedInput = hashPassword(inputPassword);
            return this.password.equals(hashedInput);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    // Simple password hashing
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());

        // Convert bytes to hex format
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivateAccount() {
        this.isActive = false;
    }

    public void reactivateAccount() {
        this.isActive = true;
    }

    public List<String> getRentalHistory() {
        return new ArrayList<>(rentalHistory); // Return a copy to preserve encapsulation
    }

    public void addRentalToHistory(String rentalID) {
        if (rentalID != null && !rentalID.trim().isEmpty()) {
            this.rentalHistory.add(rentalID);
        }
    }

    //Method to store the data as String values
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(customerID).append("|")
                .append(firstName).append("|")
                .append(lastName).append("|")
                .append(email).append("|")
                .append(contactNumber).append("|")
                .append(address).append("|")
                .append(drivingLicenseNumber).append("|")
                .append(password).append("|")
                .append(dateRegistered).append("|")
                .append(isActive).append("|");

        // Add rental history
        if (rentalHistory.isEmpty()) {
            sb.append("NONE");
        } else {
            sb.append(String.join(",", rentalHistory));
        }

        return sb.toString();
    }

    public static Customer fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid file string format");
        }

        // Create customer with basic information
        Customer customer = new Customer(
                parts[1], parts[2], parts[3],
                parts[4], parts[5], parts[6], "temp_password" // Temporary password that will be overridden
        );

        // Set the customer ID and override the auto-generated ID
        customer.customerID = parts[0];

        // Update the static counter if needed
        try {
            int id = Integer.parseInt(parts[0]);
            if (id > customerCount) {
                customerCount = id;
            }
        } catch (NumberFormatException e) {
            // If the ID format is non-numeric, just continue
        }

        // Set the hashed password directly (skip validation)
        customer.password = parts[7];
        customer.setDateRegistered(parts[8]);
        customer.isActive = Boolean.parseBoolean(parts[9]);

        // Handle rental history if it exists in the file string
        if (parts.length > 10 && !parts[10].equals("NONE")) {
            String[] rentalIds = parts[10].split(",");
            for (String rentalId : rentalIds) {
                customer.addRentalToHistory(rentalId);
            }
        }

        return customer;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID='" + customerID + '\'' +
                ", Name='" + firstName + " " + lastName + '\'' +
                ", Email='" + email + '\'' +
                ", Active=" + isActive +
                '}';
    }
}
