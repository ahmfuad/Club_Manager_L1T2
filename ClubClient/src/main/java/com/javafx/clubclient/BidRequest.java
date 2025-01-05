package com.javafx.clubclient;

import java.io.Serializable;

public class BidRequest implements Serializable {
    private Player player;

    public String getPrevClubName() {
        return prevClubName;
    }

    public void setPrevClubName(String prevClubName) {
        this.prevClubName = prevClubName;
    }

    private String prevClubName;
    private String clubName;
    private int bidAmount;

    public BidRequest(Player player, String prevClubName, String clubName, int bidAmount) {
        this.player = player;
        this.prevClubName = prevClubName;
        this.clubName = clubName;
        this.bidAmount = bidAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public String getClubName() {
        return clubName;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    @Override
    public String toString() {
        return "BidRequest{" +
                "player=" + player +
                ", clubName='" + clubName + '\'' +
                ", bidAmount=" + bidAmount +
                '}';
    }
}
