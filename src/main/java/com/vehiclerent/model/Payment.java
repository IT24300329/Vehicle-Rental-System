package com.vehiclerent.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class Payment {
    private String paymentID;
    private String customerID;
    private String customerName;
    private String vehicleID;
    private String vehicleName;
    private int rentalDays;
    private double pricePerDay;
    private boolean isPaid;
    private String paymentDate;
    private String paymentMethod;

    //Constructor with customer object
    public Payment(Customer customer, String vehicleID, String vehicleName, int rentalDays, double pricePerDay, boolean isPaid, String paymentMethod) {
        this.paymentID = generatePaymentID();
        this.customerID = customer.getCustomerID();
        this.customerName = customer.getFirstName() + " " + customer.getLastName();
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.rentalDays = rentalDays;
        this.pricePerDay = pricePerDay;
        this.isPaid = false;
        this.paymentMethod = "Not specified";
    }

    //Constructor with customer details (for file loading)
    public Payment(String customerID, String customerName, String vehicleID, String vehicleName,
                   int rentalDays, double pricePerDay) {
        this.paymentID = generatePaymentID();
        this.customerID = customerID;
        this.customerName = customerName;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.rentalDays = rentalDays;
        this.pricePerDay = pricePerDay;
        this.isPaid = false;
        this.paymentMethod = "Not specified";
    }

    //Create a unique payment ID
    private String generatePaymentID() {
        return "PMT - " +UUID.randomUUID().toString().substring(0,8);
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        if (rentalDays > 0) {
            this.rentalDays = rentalDays;
        }else{
            throw new IllegalArgumentException("Rental Days must be greater than 0");
        }
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay > 0) {
            this.pricePerDay = pricePerDay;
        }else{
            throw new IllegalArgumentException("Price per day must be greater than 0");
        }
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    private String getCurrentFormattedDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    //Calculate subtotal
    private double calculateTotal() {
        return rentalDays * pricePerDay;
    }

    public boolean processPayment(String paymentMethod) {
        if (!isPaid){
            this.paymentMethod = paymentMethod;
            this.paymentDate = getCurrentFormattedDate();
            this.isPaid = true;
            return true;
        }
        return false; //Already paid
    }

    public void generateReceipt(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.println("========================================");
        System.out.println("              RENTAL RECEIPT            ");
        System.out.println("========================================");
        System.out.println("Receipt ID: "+paymentDate);
        System.out.println("Date:"+(isPaid ? paymentDate : getCurrentFormattedDate()));
        System.out.println("----------------------------------------");
        System.out.println("Customer ID: "+customerID);
        System.out.println("Customer Name: "+customerName);
        System.out.println("----------------------------------------");
        System.out.println("Vehicle ID: "+vehicleID);
        System.out.println("Vehicle Name: "+vehicleName);
        System.out.println("Rental Days: "+rentalDays+ " days");
        System.out.println("Price per day: "+currencyFormat.format(pricePerDay));
        System.out.println("-----------------------------------------");
        System.out.println("TOTAL: "+currencyFormat.format(calculateTotal()));
        System.out.println("-----------------------------------------");
        System.out.println("Payment Method: "+(isPaid ? "PAID" : "UNPAID"));
        if(isPaid){
            System.out.println("Payment Method: "+paymentMethod);
            System.out.println("Payment Date: "+paymentDate);
        }
    }

    // Convert payment data to string format for file storage
    public String toFileString() {
        return paymentID + "|" + customerID + "|" + customerName + "|" +
                vehicleID + "|" + vehicleName + "|" + rentalDays + "|" +
                pricePerDay + "|"  + isPaid + "|" +
                (paymentDate != null ? paymentDate : "") + "|" + paymentMethod;
    }

    // Create a Payment object from a file string
    public static Payment fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid payment file string format");
        }

        Payment payment = new Payment(
                parts[1], // customerID
                parts[2], // customerName
                parts[3], // vehicleID
                parts[4], // vehicleName
                Integer.parseInt(parts[5]), // rentalDays
                Double.parseDouble(parts[6])  // pricePerDay
        );

        payment.paymentID = parts[0];
        payment.isPaid = Boolean.parseBoolean(parts[7]);
        payment.paymentDate = parts[8].isEmpty() ? null : parts[8];
        payment.paymentMethod = parts[9];

        return payment;
    }
}
