package com.example.player_database;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


abstract class MenuBar {
  //protected Database allPlayers = new Database();
  
  // public MenuBar() {
  //   this.allPlayers = new Database();
  // }  
  abstract void showMenu();
}


class MainMenu extends MenuBar {

  private Database allPlayers;

  public MainMenu(Database players) {
    this.allPlayers = players;
  }


  private void addPlayer() {
    // String name, country, club, position, jourseyNumber;
    // int age, salary;
    // double height;
    
    Scanner scn = new Scanner(System.in);
    String name;
    while(true) {
      System.out.print("Enter the name of the player: ");
      name = scn.nextLine();

      if(name.isEmpty()) {
        System.out.println("Name cannot be empty.");
        continue;
      }
      
      Player x = allPlayers.searchByName(name);
      if(x != null) {
        System.out.println("Player with this name already exists.");
      }
      
      else {
        break;
      }
    }
    
    System.out.print("Enter the country of the player: ");
    String country = scn.nextLine();
    country = country.substring(0, 1).toUpperCase() + country.substring(1).toLowerCase();
    System.out.print("Enter the club of the player: ");
    String club = scn.nextLine();
    System.out.print("Enter the position of the player: ");
    String position = scn.nextLine();
    System.out.print("Enter the joursey number of the player: ");
    String jourseyNumber = scn.nextLine();
    int age = 0;
    while (true) {
        System.out.print("Enter the age of the player: ");
        if (scn.hasNextInt()) {
            age = scn.nextInt();
            break;
        } else {
            System.out.println("Please enter a valid age.");
            scn.next();
        }
    }

    double height = 0;
    while (true) {
        System.out.print("Enter the height of the player: ");
        if (scn.hasNextDouble()) {
            height = scn.nextDouble();
            break;
        } else {
            System.out.println("Please enter a valid height.");
            scn.next();
        }
    }

    int salary = 0;
    while (true) {
        System.out.print("Enter the salary of the player: ");
        if (scn.hasNextInt()) {
            salary = scn.nextInt();
            break;
        } else {
            System.out.println("Please enter a valid salary.");
            scn.next();
        }
    }
    //String name, String country, String age, String height, String club, String position,
    //String number, String salary
    Player newPlayer = new Player(name, country, age, height, club, position, jourseyNumber, salary);
    allPlayers.addPlayer(newPlayer);
    System.out.println("Player added successfully.");
  }


  private void saveAndExit() {

    if(allPlayers.saveToFile()) {
      System.out.println("Data saved successfully.");
    } else {
      System.out.println("Error saving data.");
    }
    
  }

  @Override
  public void showMenu() {
    int choice = 0; 
    do {

      System.out.println("\n=================================");
      System.out.println("         Main Menu");
      System.out.println("=================================");
      System.out.println("(1) Search Players");
      System.out.println("(2) Search Clubs");
      System.out.println("(3) Add Player");
      System.out.println("(4) Exit System");
      System.out.print("Enter your choice: ");
      Scanner scn = new Scanner(System.in);
      //int choice;
      try {
        choice = scn.nextInt();
      } catch (Exception e) {
        System.out.println("Invalid Choice. Try Again");
        continue;
      }

        //System.out.println(choice);
      switch(choice) {
        case 1:
          new SearchPlayers(allPlayers).showMenu();
          break;
        case 2:
          new SearchClubs(allPlayers).showMenu();
          break;        
        case 3:
          addPlayer();
          break;
        case 4:
          System.out.println("Saving Data........");
          saveAndExit();
          System.out.println("Exiting.....");
          System.exit(0);
          break;
        case 5:
          allPlayers.showPlayerInfo();
          break;
        default:
          System.out.println("Invalid Choice. Try Again");
      }
    } while(choice != 4);
  }

}

class SearchPlayers extends MenuBar {

  private Database allPlayers;

  public SearchPlayers(Database players) {
    this.allPlayers = players;
  }

  private void searchByClubAndCountry() {

    String country, club;
    Scanner scn = new Scanner(System.in);
    System.out.print("Enter the name of the country: ");
    country = scn.nextLine();
    System.out.print("Enter the name of the club: ");
    club = scn.nextLine();
    List<Player> p = allPlayers.searchByClubAndCountry(club, country);
    if(p.size() > 0) {
      for(Player pl: p) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No Player found with club " + club + " and country " + country + ".");
    }
  }


  private void searchByName() {
    System.out.print("Enter the name of the player: ");
    Scanner scn = new Scanner(System.in);
    String name = scn.nextLine();
    Player p = allPlayers.searchByName(name);
    if(p != null) {
      // p.showInfo();
      System.out.println(p);
    } else {
      System.out.println("No Player found with name " + name + ".");
    }
  }

  private void searchByPosition() {
    System.out.print("Enter the position of the player: ");
    Scanner scn = new Scanner(System.in);
    String position = scn.nextLine();
    List<Player> p = allPlayers.searchByPosition(position);
    if(p.size() > 0) {
      for(Player pl: p) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No Player found who plays as" + position + ".");
    }
  }

  private void searchBySalaryRange() {
    
    Scanner scn = new Scanner(System.in);
    int min=0, max=0;
    while(true) {
      System.out.print("Enter the minimum salary: ");
      if(scn.hasNextInt()) {
        min = scn.nextInt();
        break;
      }
      else {
        System.out.println("Please enter a valid minimum salary.");
        scn.next();
      }
    }


    while(true) {
      System.out.print("Enter the maximum salary: ");
      if(scn.hasNextInt()) {
        max = scn.nextInt();
        break;
      }
      else {
        System.out.println("Please enter a valid maximum salary.");
        scn.next();
      }
    }
    
    List<Player> p = allPlayers.searchBySalaryRange(min, max);
    if(p.size() > 0) {
      for(Player pl: p) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No Player found with salary range " + min + " - " + max + ".");
    }
  }

  private void countryWisePlayerCount() {
    
    Map<String, Integer> result = allPlayers.countryWisePlayerCount();
    System.out.println("\n=================================");
    for(Map.Entry<String, Integer> entry: result.entrySet()) {
      System.out.println(entry.getKey() + ' ' + entry.getValue());
    }
    System.out.println("=================================");
  }
  

  @Override
  public void showMenu() {
    int choice = 0; 
    do {
      System.out.println("\n=================================");
      System.out.println("         Search Players");
      System.out.println("=================================");
      System.out.println("(1) By Player Name");
      System.out.println("(2) By Club and Country");
      System.out.println("(3) By Position");
      System.out.println("(4) By Salary Range");
      System.out.println("(5) Country-wise player count");
      System.out.println("(6) Back to Main Menu");
      System.out.println("=================================");
      System.out.print("Enter your choice: ");
      Scanner scn = new Scanner(System.in);
      try {
        choice = scn.nextInt();
      } catch (Exception e) {
        System.out.println("Invalid Choice. Try Again");
        continue;
      }
      

      switch(choice) {
        case 1:
          searchByName();
          break;
        case 2:
          searchByClubAndCountry();
          break;        
        case 3:
          searchByPosition();
          break;
        case 4:
          searchBySalaryRange();
          break;
        case 5:
          countryWisePlayerCount();
          break;
        case 6:
          break;
        default:
          System.out.println("Invalid Choice. Try Again");
      }
    } while(choice != 6);
  }

}

class SearchClubs extends MenuBar {

  private Database allPlayers;

  public SearchClubs(Database players) {
    this.allPlayers = players;
  }

  private void maxSalaryOfClub() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Enter the name of the club: ");
    String club = scn.nextLine();
    List<Player> result = allPlayers.maxSalaryOfClub(club);
    if(result.size() > 0) {
      for(Player pl: result) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No such club with this name");
    }
  }

  private void maxAgeOfClub() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Enter the name of the club: ");
    String club = scn.nextLine();
    List<Player> result = allPlayers.maxAgeOfClub(club);
    if(result.size() > 0) {
      for(Player pl: result) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No such club with this name");
    }
  }

  private void maxHeightOfClub() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Enter the name of the club: ");
    String club = scn.nextLine();
    List<Player> result = allPlayers.maxHeightOfClub(club);
    if(result.size() > 0) {
      for(Player pl: result) {
        System.out.println(pl);
      }
    } else {
      System.out.println("No such club with this name");
    }
  }

  private void totalSalaryOfClub() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Enter the name of the club: ");
    String club = scn.nextLine();
    long result = allPlayers.totalSalaryOfClub(club);
    if(result > 0) {
      System.out.println("Total Salary of the club " + club + " is " + result);
    } else {
      System.out.println("No such club with this name");
    }
  }

  @Override
  public void showMenu() {
    int choice = 0; 
    do {
      System.out.println("\n=================================");
      System.out.println("         Search Clubs");
      System.out.println("=================================");
      System.out.println("(1) Player(s) with the maximum salary of a club");
      System.out.println("(2) Player(s) with the maximum age of a club");
      System.out.println("(3) Player(s) with the maximum height of a club");
      System.out.println("(4) Total yearly salary of a club");
      System.out.println("(5) Back to Main Menu");
      System.out.println("=================================");
      System.out.print("Enter your choice: ");
      Scanner scn = new Scanner(System.in);
      try {
        choice = scn.nextInt();
      } catch (Exception e) {
        System.out.println("Invalid Choice. Try Again");
        continue;
      }
      
      switch(choice) {
        case 1:
          maxSalaryOfClub();
          break;
        case 2:
          maxAgeOfClub();
          break;        
        case 3:
          maxHeightOfClub();
          break;
        case 4:
          totalSalaryOfClub();
          break;
        case 5:
          break;
        default:
          System.out.println("Invalid Choice. Try Again");
      }
        } while(choice != 5);
    }
}