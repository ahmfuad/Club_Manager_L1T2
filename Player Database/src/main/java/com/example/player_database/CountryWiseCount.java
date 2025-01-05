package com.example.player_database;


public class CountryWiseCount {
    String country;
    int count;

    public CountryWiseCount(String country, int count) {
        this.country = country;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}