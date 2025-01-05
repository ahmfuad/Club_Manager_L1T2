package com.example.player_database;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class Player {
  private String name, country, club, position, jourseyNumber;
  private int age, salary;
  private double height;
  private Button button;

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
          this.button = new Button("View");
          button.setOnAction( e -> {
                      //System.out.println(getName() + " " + getLastName() + " " + getEmail());
                      Alert a = new Alert(Alert.AlertType.INFORMATION);
                      a.setTitle("Player Info");
                      a.setHeaderText("Player Info");
                      a.setContentText(String.valueOf(this));
                      a.showAndWait();
                  }
          );
  }

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
          this.button = new Button("View");
          button.setOnAction( e -> {
                      //System.out.println(getName() + " " + getLastName() + " " + getEmail());
                      Alert a = new Alert(Alert.AlertType.INFORMATION);
                      a.setTitle("Player Info");
                      a.setContentText(String.valueOf(this));
                      a.showAndWait();
                  }
          );
  }


  public void showInfo() {
    System.out.println("=================================");
    System.out.println("Name: " + this.name);
    System.out.println("Country: " + this.country);
    System.out.println("Age: " + this.age);
    System.out.println("Height: " + this.height);
    System.out.println("Club: " + this.club);
    System.out.println("Position: " + this.position);
    System.out.println("Joursey Number: " + this.jourseyNumber);
    System.out.println("Weekly Salary: " + this.salary);
  }
    public void setName(String name) {
      this.name = name;
    }
    public void setCountry(String country) {
      this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public String getJourseyNumber() {
        return jourseyNumber;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public double getHeight() {
        return height;
    }

    public Button getButton() {
      return button;
    }

    @Override
    public String toString() {
        return "\n========Player Info========\n" + "Name: " + this.name + "\n" +
               "Country: " + this.country + "\n" +
               "Age: " + this.age + " yrs\n" +
               "Height: " + this.height + " m\n" +
               "Club: " + this.club + "\n" +
               "Position: " + this.position + "\n" +
               "Joursey Number: " + this.jourseyNumber + "\n" +
               "Weekly Salary: " + this.salary + "\n===========================";
    }
}
