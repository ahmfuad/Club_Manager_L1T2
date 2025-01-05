package com.javafx.clubclient;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L; // For serialization compatibility

    private String name;
    private String country;
    private String club;
    private String position;
    private String jourseyNumber;
    private int age;
    private int salary;
    private double height;
    private boolean inMarketPlace;
    private long bid;


    public synchronized long getBid() {
        return bid;
    }

    public synchronized void setBid(long bid) {
        this.bid = bid;
    }



    // Constructor for string-based input
    public Player(String name, String country, String age, String height, String club, String position,
                  String number, String salary) {
        this.name = name;
        this.country = country;
        this.age = Integer.parseInt(age);
        this.height = Double.parseDouble(height);
        this.club = club;
        this.position = position;
        this.jourseyNumber = number;
        this.salary = Integer.parseInt(salary);
        this.inMarketPlace = false; // Default to not being on sale
    }

    // Constructor for direct input
    public Player(String name, String country, int age, double height, String club, String position,
                  String number, int salary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.jourseyNumber = number;
        this.salary = salary;
        this.inMarketPlace = false; // Default to not being on sale
    }

    // Synchronized setter for thread safety
    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized void setCountry(String country) {
        this.country = country;
    }

    public synchronized void setClub(String club) {
        this.club = club;
    }

    public synchronized void setInMarketPlace(boolean inMarketPlace) {
        this.inMarketPlace = inMarketPlace;
    }

    // Boolean getter should follow Java conventions
    public synchronized boolean isInMarketPlace() {
        return inMarketPlace;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getCountry() {
        return country;
    }

    public synchronized String getClub() {
        return club;
    }

    public synchronized String getPosition() {
        return position;
    }

    public synchronized String getJourseyNumber() {
        return jourseyNumber;
    }

    public synchronized int getAge() {
        return age;
    }

    public synchronized int getSalary() {
        return salary;
    }

    public synchronized double getHeight() {
        return height;
    }

    // Display player information
    public synchronized void showInfo() {
        System.out.println(toString());
    }

    @Override
    public synchronized String toString() {
        return "\n========Player Info========\n" +
                "Name: " + this.name + "\n" +
                "Country: " + this.country + "\n" +
                "Age: " + this.age + " yrs\n" +
                "Height: " + this.height + " m\n" +
                //"Club: " + this.club + "\n" +
                "Position: " + this.position + "\n" +
                "Joursey Number: " + this.jourseyNumber + "\n" +
                "Weekly Salary: " + this.salary + "\n" +
                //"Is on sale: " + ((this.inMarketPlace) ? "Yes" : "No") + "\n" +
                "===========================\n";
    }
}
