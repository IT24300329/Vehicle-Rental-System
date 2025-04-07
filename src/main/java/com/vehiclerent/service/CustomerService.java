package com.vehiclerent.service;

import com.vehiclerent.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerService {
    private List<Customer> customerList;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    public CustomerService() {
        this.customerList = new ArrayList<>();
    }

    //Registers a customer after validation
    public void registerCustomer(String firstName, String lastName, String email,
                                 String contactNumber, String address, String license, String password) {
        // Validate all inputs
        if (!validateCustomerData(firstName, lastName, email, contactNumber, license, password)) {
            return; // Validation failed, error message already printed
        }

        // Check if email already exists
        if (emailExists(email)) {
            System.out.println("Registration failed: Email already registered");
            return;
        }

        try {
            Customer customer = new Customer(firstName, lastName, email, contactNumber, address, license, password);
            customerList.add(customer);
            System.out.println("Customer registered successfully! ID: " + customer.getCustomerID());
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    //Validate all customer before registration
    private boolean validateCustomerData(String firstName, String lastName, String email,
                                         String contactNumber, String license, String password) {
        StringBuilder errors = new StringBuilder();

        if (firstName == null || firstName.trim().isEmpty()) {
            errors.append("First name is required. ");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errors.append("Last name is required. ");
        }

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("Valid email is required. ");
        }

        if (contactNumber == null || !PHONE_PATTERN.matcher(contactNumber).matches()) {
            errors.append("Valid 10-digit contact number is required. ");
        }

        if (license == null || license.trim().isEmpty()) {
            errors.append("License is required. ");
        }

        if (password == null || password.length() < 6) {
            errors.append("Password must be at least 6 characters long. ");
        }

        if (errors.length() > 0) {
            System.out.println("Registration failed: " + errors.toString());
            return false;
        }

        return true;
    }

    //Checks if there is already an email in the systsem
    private boolean emailExists(String email) {
        for (Customer c : customerList) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    //Authenticate a user with email and password
    public Customer authenticateCustomer(String email, String password) {
        if (email == null || password == null) {
            System.out.println("Authentication failed: Email and password are required");
            return null;
        }

        for (Customer c : customerList) {
            if (c.getEmail().equals(email) && c.getPassword().equals(password)) {
                return c;
            }
        }

        System.out.println("Authentication failed: Invalid email or password");
        return null;
    }

    //Find a customer by the ID
    public Customer findCustomerByID(String customerID) {
        if (customerID == null || customerID.trim().isEmpty()) {
            System.out.println("Customer ID cannot be empty");
            return null;
        }

        for (Customer c : customerList) {
            if (c.getCustomerID().equals(customerID)) {
                return c;
            }
        }

        System.out.println("No customer found with ID: " + customerID);
        return null;
    }

    //Find a customer with full name
    public List<Customer> findCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty");
            return new ArrayList<>();
        }

        List<Customer> matches = new ArrayList<>();
        for (Customer c : customerList) {
            if (c.getFullName().contains(name)) {
                matches.add(c);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No customers found with name containing: " + name);
        }

        return matches;
    }

    //Find a customer with exact email match
    public Customer findCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty");
            return null;
        }

        for (Customer c : customerList) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }

        System.out.println("No customer found with email: " + email);
        return null;
    }

    //Deactivate a customers account
    public boolean deactivateCustomer(String customerID) {
        Customer customer = findCustomerByID(customerID);
        if (customer != null) {
            customer.deactivateAccount();
            System.out.println("Customer account deactivated.");
            return true;
        }
        return false;
    }

    //Reactivate a customers account
    public boolean reactivateCustomer(String customerID) {
        Customer customer = findCustomerByID(customerID);
        if (customer != null) {
            customer.reactivateAccount();
            System.out.println("Customer account reactivated.");
            return true;
        }
        return false;
    }

    //Update customer details and infromation
    public boolean updateCustomer(String customerID, String firstName, String lastName,
                                  String contactNumber, String address) {
        Customer customer = findCustomerByID(customerID);
        if (customer == null) {
            return false;
        }

        if (firstName != null && !firstName.trim().isEmpty()) {
            customer.setFirstName(firstName);
        }

        if (lastName != null && !lastName.trim().isEmpty()) {
            customer.setLastName(lastName);
        }

        if (contactNumber != null) {
            if (PHONE_PATTERN.matcher(contactNumber).matches()) {
                customer.setContactNumber(contactNumber);
            } else {
                System.out.println("Invalid contact number format - must be 10 digits");
                return false;
            }
        }

        if (address != null && !address.trim().isEmpty()) {
            customer.setAddress(address);
        }

        System.out.println("Customer information updated successfully");
        return true;
    }

    //Change customer password
    public boolean changePassword(String customerID, String oldPassword, String newPassword) {
        Customer customer = findCustomerByID(customerID);
        if (customer == null) {
            return false;
        }

        if (!customer.getPassword().equals(oldPassword)) {
            System.out.println("Current password is incorrect");
            return false;
        }

        if (newPassword == null || newPassword.length() < 6) {
            System.out.println("New password must be at least 6 characters long");
            return false;
        }

        customer.setPassword(newPassword);
        System.out.println("Password changed successfully");
        return true;
    }

    //Print all customers
    public void printAllCustomers() {
        if (customerList.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }

        System.out.println("Customer List:");
        for (Customer c : customerList) {
            System.out.println("ID: " + c.getCustomerID() +
                    " | Name: " + c.getFirstName() + " " + c.getLastName() +
                    " | Email: " + c.getEmail() +
                    " | Active: " + c.isActive());
        }
    }

    //Get all active customers
    public List<Customer> getActiveCustomers() {
        List<Customer> activeCustomers = new ArrayList<>();
        for (Customer c : customerList) {
            if (c.isActive()) {
                activeCustomers.add(c);
            }
        }
        return activeCustomers;
    }

    //Get the whole list of customers
    public List<Customer> getCustomerList() {
        return new ArrayList<>(customerList);  // Return a copy to prevent external modification
    }

    //Get customer count
    public int getCustomerCount() {
        return customerList.size();
    }
}