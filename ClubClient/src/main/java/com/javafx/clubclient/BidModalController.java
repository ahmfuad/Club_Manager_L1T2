package com.javafx.clubclient;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BidModalController {

    @FXML
    private TextField bidAmountField;

    private int bidAmount;
    private boolean submitted = false;

    public int getBidAmount() {
        return bidAmount;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    @FXML
    private void handleSubmit() {
        try {
            bidAmount = Integer.parseInt(bidAmountField.getText().trim());
            submitted = true;
            Stage stage = (Stage) bidAmountField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            bidAmountField.setStyle("-fx-border-color: red;"); // Highlight field if invalid input
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) bidAmountField.getScene().getWindow();
        stage.close();
    }
}
