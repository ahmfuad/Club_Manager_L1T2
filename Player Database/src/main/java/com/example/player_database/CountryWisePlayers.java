package com.example.player_database;

public class CountryWisePlayers {

    private String country;
    private int playerCount;

    // Constructor
    public CountryWisePlayers(String country, int playerCount) {
        this.country = country;
        this.playerCount = playerCount;
    }

    // Getters and Setters
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
};
