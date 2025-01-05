package com.example.player_database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Database {
  private List<Player> playerDatabase = new ArrayList<>();
  private static final String INPUT_FILE_NAME = "src/main/java/com/example/player_database/players.txt";
  private static final String OUTPUT_FILE_NAME = "src/main/java/com/example/player_database/players.txt";

  public Database() {
      try {
          BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
          while (true) {
              String line = br.readLine();
              if (line == null) break;
              //System.out.println(line);            
              String[] player = line.split(",");
              
              Player temp = new Player(player[0], player[1], player[2], player[3], player[4],
                                        player[5], player[6], player[7]);
              playerDatabase.add(temp);
          }
          br.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  public List<Player> getPlayersList() {
    return playerDatabase;
  }

  // add player
  public boolean addPlayer(Player p) {
    playerDatabase.add(p);
    return true;
  }

  public void showPlayerInfo() {
    for(Player p: playerDatabase) {
      p.showInfo();
    }
  }


  // Search Player
  public Player searchByName(String x) {
    for(Player p: playerDatabase) {
      if(p.getName().equalsIgnoreCase(x)) {
        return p;
      }
    }
    return null;
  }


  public List<Player> searchByClubAndCountry(String club, String country) {
    List<Player> result = new ArrayList<>();

    

    if(club.equals("ANY")) {
      for(Player p: playerDatabase) {
        if(p.getCountry().equalsIgnoreCase(country)) {
          result.add(p);
        }
      }
    }
    else {
      for(Player p: playerDatabase) {
        if(p.getClub().equalsIgnoreCase(club) && p.getCountry().equalsIgnoreCase(country)) {
          result.add(p);
        }
      }
    }
    return result;
  }

  public List<Player> searchByPosition(String position) {
    List<Player> result = new ArrayList<>();

    for(Player p: playerDatabase) {
      if(p.getPosition().equalsIgnoreCase(position)) {
        result.add(p);
      }
    }

    return result;
  }

  public List<Player> searchBySalaryRange(int min, int max) {
    List<Player> result = new ArrayList<>();

    for(Player p: playerDatabase) {
      if(p.getSalary() >= min && p.getSalary() <= max) {
        result.add(p);
      }
    }

    return result;
  }

  public Map<String, Integer> countryWisePlayerCount() {
    Map<String, Integer> countryCount = new HashMap<>();
    //List<String> countries = new ArrayList<>();

    for(Player p: playerDatabase) {
      if(!countryCount.containsKey(p.getCountry())) {
        //countries.add(p.getCountry());
        countryCount.put(p.getCountry(), 1);
      }
      else {
        countryCount.put(p.getCountry(), countryCount.get(p.getCountry()) + 1);
      }
    }

    return countryCount;
  }


  // Search Clubs

  public List<Player> maxSalaryOfClub(String club) {
    List<Player> result = new ArrayList<>();
    int max = 0;

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club)) {
        if(p.getSalary() > max) {
          max = p.getSalary();
        }
      }
    }

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club) && p.getSalary() == max) {
        result.add(p);
      }
    }

    return result;
  }

  public List<Player> maxAgeOfClub(String club) {
    List<Player> result = new ArrayList<>();
    int max = 0;

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club)) {
        if(p.getAge() > max) {
          max = p.getAge();
        }
      }
    }

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club) && p.getAge() == max) {
        result.add(p);
      }
    }

    return result;
  }

  public List<Player> maxHeightOfClub(String club) {
    List<Player> result = new ArrayList<>();
    double max = 0;

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club)) {
        if(p.getHeight() > max) {
          max = p.getHeight();
        }
      }
    }

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club) && p.getHeight() == max) {
        result.add(p);
      }
    }

    return result;
  }

  public long totalSalaryOfClub(String club) {
    long total = 0;

    for(Player p: playerDatabase) {
      if(p.getClub().equalsIgnoreCase(club)) {
        total += p.getSalary();
      }
    }

    return total*52;
  }


  
  // Exit
  public boolean saveToFile() {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
      for(Player p: playerDatabase) {
        bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," + p.getClub() + "," + p.getPosition() + "," + p.getJourseyNumber() + "," + p.getSalary());
        bw.write(System.lineSeparator());
      }
      bw.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    //BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));

  }
  


}
