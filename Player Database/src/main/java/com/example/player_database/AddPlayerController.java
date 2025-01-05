package com.example.player_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPlayerController {

    private Database database;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField name, country, club, position, jourseyNumber, age, salary, height;
    private String _name, _country, _club, _position, _jourseyNumber, _age, _salary, _height;
//    //@FXML
//    private int _age, _salary;
//    //@FXML
//    private double _height;

//    public AddPlayerController(Database database) {
//        this.database = database;
//    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setDatabase(database);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submit(ActionEvent event) throws IOException {
        //private TextField name, country, club, position, jourseyNumber, age, salary, height;
        _name = name.getText().trim();
        _country = country.getText().trim();
        _club = club.getText().trim();
        _position = position.getText().trim();
        _jourseyNumber = jourseyNumber.getText().trim();
        _age = age.getText().trim();
        _salary = salary.getText().trim();
        _height = height.getText().trim();

        if(_name.isEmpty() || _country.isEmpty() || _club.isEmpty()
            || _position.isEmpty() || _jourseyNumber.isEmpty() || _age.isEmpty()
            || _salary.isEmpty() || _height.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Field Cannot Be Empty");
            alert.setContentText("Please enter a value in the TextField.");
            alert.showAndWait();

        } else if(database.searchByName(_name) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Player Exists");
            alert.setContentText("PLayer With the Same Name Exists");
            alert.showAndWait();
        }
        else {
            //Player(String name, String country, int age, double height, String club, String position,
            //        String number, int salary)
            Player newPlayer = new Player(_name, _country, Integer.parseInt(_age), Double.parseDouble(_height),
                                            _club, _position, _jourseyNumber, Integer.parseInt(_salary));

            if(database.addPlayer(newPlayer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Submission Successful");
                alert.setContentText("Your data has been successfully submitted.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something Went Wrong");
                alert.setHeaderText("Player Not Added!");
                alert.setContentText("Player is Not Added, Try again");
                alert.showAndWait();
            }


        }
        //System.out.println(name.getText() + country.getText() + club.getText() + position.getText() + jourseyNumber.getText() + age.getText() + salary.getText() + height.getText());
    }
}
