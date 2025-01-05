package com.javafx.clubclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class PlayerViewController {
    @FXML
    private Label playerInfoText;

    @FXML
    private Label playerAge;

    @FXML
    private Label playerCountry;

    @FXML
    private Label playerHeight;

    @FXML
    private Label playerJersey;

    @FXML
    private Label playerName;

    @FXML
    private Label playerPosition;

    @FXML
    private Label playerSalary;

    private Stage stage;

    // Set the stage for later use
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to set player info text
    public void setPlayerInfo(Player player, String clubName) {
        playerInfoText.setText(clubName);
        playerName.setText(player.getName());
        playerCountry.setText(player.getCountry());
        playerAge.setText(String.valueOf(player.getAge()));
        playerHeight.setText(String.valueOf(player.getHeight()) + " m");
        playerJersey.setText(player.getJourseyNumber());
        playerPosition.setText(player.getPosition());
        playerSalary.setText(String.valueOf(player.getSalary()) + "$");

    }

    @FXML
    private void closeModal() {
        if (stage != null) {
            stage.close();
        }
    }

}
